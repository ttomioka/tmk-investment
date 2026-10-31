package jp.tmkworks.tmk_investment.util.jooq.bean;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.core.ResolvableType;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import com.fasterxml.jackson.annotation.JsonValue;

public class BeanUtils2 {

  public static void copyProperties(Object source, Object target, String @Nullable... ignoreProperties) throws BeansException {
    Assert.notNull(source, "Source must not be null");
    Assert.notNull(target, "Target must not be null");

    Class<?> actualEditable = target.getClass();
    PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(actualEditable);
    Set<String> ignoredProps = (ignoreProperties != null ? new HashSet<>(Arrays.asList(ignoreProperties)) : null);
    var sourceResults = (actualEditable != source.getClass() ? CachedIntrospectionResults.forClass(source.getClass()) : null);

    // ターゲット側の書き込みメソッドで回す
    for (PropertyDescriptor targetPd : targetPds) {
      Method writeMethod = targetPd.getWriteMethod();
      if (writeMethod != null && (ignoredProps == null || !ignoredProps.contains(targetPd.getName()))) {
        PropertyDescriptor sourcePd = (sourceResults != null ? sourceResults.getPropertyDescriptor(targetPd.getName()) : targetPd);
        if (sourcePd != null) {
          Method readMethod = sourcePd.getReadMethod();
          if (readMethod != null) {
            if (isAssignable(writeMethod, readMethod, sourcePd, targetPd)) {
              try {
                ReflectionUtils.makeAccessible(readMethod);
                Object value = readMethod.invoke(source);
                ReflectionUtils.makeAccessible(writeMethod);
                writeMethod.invoke(target, value);
                continue;
              } catch (Throwable ex) {
                throw new FatalBeanException("Could not copy property '" + targetPd.getName() + "' from source to target", ex);
              }
            }

            Type paramType = writeMethod.getGenericParameterTypes()[0];
            if (paramType instanceof Class<?>) {
              try {
                ReflectionUtils.makeAccessible(readMethod);
                Object value = readMethod.invoke(source);
                var converted = findJsonValueMethod(value.getClass()).get().invoke(value);
                
                if(converted instanceof String && paramType.equals(Integer.class)) {
                  converted = Integer.valueOf((String)converted);
                } else if(converted instanceof String && paramType.equals(Long.class)) {
                  converted = Long.valueOf((String)converted);
                }
                
                ReflectionUtils.makeAccessible(writeMethod);
                writeMethod.invoke(target, converted);
              } catch (Throwable ex) {
                throw new FatalBeanException("Could not copy property '" + targetPd.getName() + "' from source to target", ex);
              }
            }

          }
        }
      }
    }
  }

  public static Optional<Method> findJsonValueMethod(Class<?> type) {
    return Arrays.stream(type.getDeclaredMethods())
        .filter(method -> method.isAnnotationPresent(JsonValue.class))
        .findFirst();
  }

  private static boolean isAssignable(Method writeMethod, Method readMethod, PropertyDescriptor sourcePd, PropertyDescriptor targetPd) {

    Type paramType = writeMethod.getGenericParameterTypes()[0];
    if (paramType instanceof Class<?> clazz) {
      return ClassUtils.isAssignable(clazz, readMethod.getReturnType());
    } else if (paramType.equals(readMethod.getGenericReturnType())) {
      return true;
    } else {
      ResolvableType sourceType = ((GenericTypeAwarePropertyDescriptor) sourcePd).getReadMethodType();
      ResolvableType targetType = ((GenericTypeAwarePropertyDescriptor) targetPd).getWriteMethodType();
      // Ignore generic types in assignable check if either ResolvableType has
      // unresolvable generics.
      return (sourceType.hasUnresolvableGenerics() || targetType.hasUnresolvableGenerics()
          ? ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())
          : targetType.isAssignableFrom(sourceType));
    }
  }
}
