package jp.tmkworks.tmk_investment.core.domain.model.common.lang;

import java.util.Arrays;

public interface CodeBasedEnum {

  String getCode();

  public static <T extends Enum<T> & CodeBasedEnum> T fromCode(Class<T> enumType, String code) {
    return Arrays.stream(enumType.getEnumConstants())
        .filter(e -> e.getCode()
            .equals(code))
        .findFirst()
        .orElseThrow();
  }

}
