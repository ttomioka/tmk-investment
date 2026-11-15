package jp.tmkworks.tmk_investment.util.java.math;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.lang3.StringUtils;

public class BigDecimalUtils {

  public static String normalize(String value) {
    boolean isNotCreatableBigDecimal = !BigDecimalUtils.isCreatableBigDecimal(value);
    if (isNotCreatableBigDecimal)
      return value;

    var bigDecimalValue = toBigDecimal(value);
    var stripTrailingZeros = bigDecimalValue.stripTrailingZeros();
    var plainString = stripTrailingZeros.toPlainString();
    return plainString;
  }

  public static String normalize(String valueString, int scale, RoundingMode roundingMode) {
    boolean notCreatableBigDecimal = !BigDecimalUtils.isCreatableBigDecimal(valueString);
    if (notCreatableBigDecimal)
      return valueString;

    var value  = toBigDecimal(valueString);
    return normalize(value, scale, roundingMode);
  }
  
  public static String normalize(BigDecimal value, int scale, RoundingMode roundingMode) {
    value = value.setScale(scale, roundingMode);
    value = value.stripTrailingZeros();
    var plainString = value.toPlainString();
    return plainString;
  }

  public static BigDecimal toBigDecimal(String value) {
    return new BigDecimal(value);
  }

  public static boolean isCreatableBigDecimal(String value) {
    boolean isEmpty = StringUtils.isEmpty(value);
    if (isEmpty)
      return false;

    boolean isCreateBigDecimal;
    try {
      new BigDecimal(value);
      isCreateBigDecimal = true;
    } catch (NumberFormatException e) {
      isCreateBigDecimal = false;
    }
    return isCreateBigDecimal;
  }

  public static BigDecimal absoluteDifference(BigDecimal a, BigDecimal b) {
    var subtracted = a.subtract(b);
    var absoluteDifference = subtracted.abs();
    return absoluteDifference;
  }

}
