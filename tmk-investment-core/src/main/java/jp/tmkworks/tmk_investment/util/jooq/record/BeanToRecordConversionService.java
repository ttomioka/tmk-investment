package jp.tmkworks.tmk_investment.util.jooq.record;

import java.util.function.Function;

import jakarta.annotation.Nullable;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.GenericConversionService;

import jp.tmkworks.tmk_investment.core.domain.model.common.lang.CodeBasedEnum;

public class BeanToRecordConversionService extends GenericConversionService {
  private static volatile @Nullable BeanToRecordConversionService sharedInstance;

  public static BeanToRecordConversionService getSharedInstance() {
    BeanToRecordConversionService cs = sharedInstance;
    if (cs == null) {
      synchronized (BeanToRecordConversionService.class) {
        cs = sharedInstance;
        if (cs == null) {
          cs = new BeanToRecordConversionService();
          sharedInstance = cs;
        }
      }
    }
    return cs;
  }

  public <T extends Enum<T> & CodeBasedEnum> void addCodeBasedEnumConverter(Class<T> targetType) {
    addConverter(String.class, targetType, (Function<String, T>) code -> CodeBasedEnum.fromCode(targetType, code));
  }

  public <S, T> void addConverter(Class<S> sourceType, Class<T> targetType, Function<S, T> mapper) {
    boolean canConvert = canConvert(sourceType, targetType);
    if (canConvert)
      return;

    addConverter(sourceType, targetType, converter(mapper));
  }

  static <S, T> Converter<S, T> converter(Function<S, T> mapper) {
    return new Converter<S, T>() {
      @Override
      public T convert(S source) {
        return mapper.apply(source);
      }
    };
  }

}
