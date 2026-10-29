package jp.tmkworks.tmk_investment.util.jooq.bean;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.core.ResolvableType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

public class BeanUtils2 {
  final static GenericConversionService genericConversionService = new GenericConversionService();

//  static {
//    genericConversionService.addConverter(StringValue.class, String.class, converter(StringValue::string));
//    genericConversionService.addConverter(LocalDateValue.class, LocalDate.class, converter(LocalDateValue::value));
//    genericConversionService.addConverter(IntValue.class, Integer.class, converter(IntValue::value));
//    genericConversionService.addConverter(IntValue.class, String.class, converter(IntValue::string));
//    genericConversionService.addConverter(LongValue.class, String.class, converter(LongValue::string));
//    genericConversionService.addConverter(AnalysisNumberValue.class, String.class, converter(AnalysisNumberValue::string));
//    genericConversionService.addConverter(CodeBasedEnum.class, String.class, converter(CodeBasedEnum::getCode));
//  }

  static <S, T> Converter<S, T> converter(Function<S, T> mapper) {
    return new Converter<S, T>() {
      @Override
      public T convert(S source) {
        return mapper.apply(source);
      }
    };
  }

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
              } catch (Throwable ex) {
                throw new FatalBeanException("Could not copy property '" + targetPd.getName() + "' from source to target", ex);
              }
            }

            var sourceType = readMethod.getReturnType();
            Type paramType = writeMethod.getGenericParameterTypes()[0];
            if (paramType instanceof Class<?> clazz) {
              boolean canConvert = genericConversionService.canConvert(sourceType, clazz);
              if (canConvert) {
                try {
                  ReflectionUtils.makeAccessible(readMethod);
                  Object value = readMethod.invoke(source);
                  var converted = genericConversionService.convert(value, clazz);
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
