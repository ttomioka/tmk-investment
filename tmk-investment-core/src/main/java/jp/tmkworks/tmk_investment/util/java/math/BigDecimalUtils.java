package jp.tmkworks.tmk_investment.util.java.math;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

public class BigDecimalUtils {
  
  public static String normalize(String value) {
    boolean isNotCreatableBigDecimal = !BigDecimalUtils.isCreatableBigDecimal(value);
    if(isNotCreatableBigDecimal)
      return value;
    
    var bigDecimalValue = toBigDecimal(value);
    var stripTrailingZeros = bigDecimalValue.stripTrailingZeros();
    var plainString = stripTrailingZeros.toPlainString();
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

  public static boolean isAはBより小さい(BigDecimal a, BigDecimal b) {
    return a.compareTo(b) < 1;
  }
  
  public static BigDecimal createBigDecimalDefaultZero(final String str) {
    if (str == null) {
        return BigDecimal.ZERO;
    }
    // handle JDK1.3.1 bug where "" throws IndexOutOfBoundsException
    if (StringUtils.isBlank(str)) {
      return BigDecimal.ZERO;
    }
    return new BigDecimal(str);
}

}
