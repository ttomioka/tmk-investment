package jp.tmkworks.tmk_investment.util.jooq.record;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.RecordComponent;
import java.util.Arrays;
import java.util.Optional;

import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.apache.commons.lang3.reflect.MethodUtils;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * <ul>
 *   <li>SingleFieldValueは対応可能</li>
 *   <li>EnumはenumConversionServiceに登録する</li>
 * </ul>
 */
class ArgumentValueConverter implements FailableFunction<RecordComponent, Object, ContextedRuntimeException> {
  private final SourceObjectWrapper sourceObject;

  ArgumentValueConverter(SourceObjectWrapper sourceObject) {
    this.sourceObject = sourceObject;
  }

  @Override
  public Object apply(RecordComponent argMetadata) {
    var argType = argMetadata.getType();
    String argName = argMetadata.getName();

    var readMethod = sourceObject.getReadMethod(argName);
    if (readMethod == null) {
      return null;
    }

    var sourceValue = readMethod.invoke();
    var sourceType = sourceValue.getClass();

    var conversionService = BeanToRecordConversionService.getSharedInstance();
    boolean canConvert = conversionService.canConvert(sourceType, argType);
    if (canConvert) {
      var argValue = conversionService.convert(sourceValue, argType);
      return argValue;
    }

//    boolean isSingleFieldValue = ClassUtils.isAssignable(argType, SingleFieldValue.class);
//    boolean isSingleIntFieldValue = ClassUtils.isAssignable(argType, SingleIntFieldValue.class);
//    boolean isSingleLongFieldValue = ClassUtils.isAssignable(argType, SingleLongFieldValue.class);
//    boolean acceptableType = isSingleFieldValue || isSingleIntFieldValue || isSingleLongFieldValue;
//    if (acceptableType) {
//      var argValue = ReflectionUtils.invokeConstructor(argType, sourceValue);
//      return argValue;
//    }
    
    var jsonCreatorMethod = findJsonCreatorMethod(argType).orElse(null);
    if(jsonCreatorMethod != null) {
      boolean isStatic = Modifier.isStatic(jsonCreatorMethod.getModifiers());
      if(isStatic) {
        try {
          return MethodUtils.invokeStaticMethod(argType, jsonCreatorMethod.getName(), sourceValue);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
          e.printStackTrace();
        }
      }
    }
    
    var jsonCreatorConstructor = findJsonCreatorConstructor(argType).orElse(null);
    if(jsonCreatorConstructor != null) {
        try {
          return ConstructorUtils.invokeConstructor(argType, sourceValue);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
          e.printStackTrace();
        } catch (InstantiationException e) {
          e.printStackTrace();
        }
    }
    

    throw new ContextedRuntimeException("Recordコンストラクタの引数オブジェクトへの変換中に例外")
        .addContextValue("理由", "変換に対応していない型が来たため")
        .addContextValue("argType", argType)
        .addContextValue("argName", argName);
  }
  
  public static Optional<Method> findJsonCreatorMethod(Class<?> type) {
    return Arrays.stream(type.getDeclaredMethods())
        .filter(method -> method.isAnnotationPresent(JsonCreator.class))
        .findFirst();
  }
  
  public static Optional<Constructor<?>> findJsonCreatorConstructor(Class<?> type) {
    return Arrays.stream(type.getConstructors())
        .filter(method -> method.isAnnotationPresent(JsonCreator.class))
        .findFirst();
  }
  
  
}