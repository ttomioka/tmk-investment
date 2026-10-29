package jp.tmkworks.tmk_investment.util.jooq.record;

import java.lang.reflect.RecordComponent;

import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.apache.commons.lang3.function.FailableFunction;

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

    throw new ContextedRuntimeException("Recordコンストラクタの引数オブジェクトへの変換中に例外")
        .addContextValue("理由", "変換に対応していない型が来たため")
        .addContextValue("argType", argType)
        .addContextValue("argName", argName);
  }
  
}