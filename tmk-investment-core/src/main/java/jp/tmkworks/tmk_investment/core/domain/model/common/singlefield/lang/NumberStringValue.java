package jp.tmkworks.tmk_investment.core.domain.model.common.singlefield.lang;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.ta4j.core.num.DecimalNum;

import com.fasterxml.jackson.annotation.JsonValue;

import jp.tmkworks.tmk_investment.core.domain.model.common.singlefield.SingleFieldValue;

public class NumberStringValue extends Number implements SingleFieldValue<String>, Serializable {
  protected final String value;

  protected NumberStringValue(String value) {
    this.value = value;
  }

  @Override
  public String value() {
    return value;
  }

  @Override
  public int intValue() {
    return 0;
  }

  @Override
  public long longValue() {
    return 0;
  }

  @Override
  public float floatValue() {
    throw new NotImplementedException();
  }

  @Override
  public double doubleValue() {
    throw new NotImplementedException();
  }
  
  public BigDecimal bigDecimalValue() {
    return new BigDecimal(value);
  }
  
  public DecimalNum decimalNumValue() {
    return DecimalNum.valueOf(value);
  }
  
  public BigDecimal multiply(NumberStringValue other) {
    var a = bigDecimalValue();
    var b = other.bigDecimalValue();
    var result = a.multiply(b);
    return result;
  }

  @JsonValue
  public String string() {
    return value;
  }

  @Override
  public String toString() {
    return value;
  }

  public static String toStripTrailingZerosAndPlain(String str) {
    if (StringUtils.isEmpty(str)) {
      throw new NumberFormatException("A blank string is not a valid number");
    }

    var bigDecimal = new BigDecimal(str);
    bigDecimal = bigDecimal.stripTrailingZeros();
    var toStripTrailingZerosAndPlain = bigDecimal.toPlainString();
    return toStripTrailingZerosAndPlain;
  }
  
  public static String toStripTrailingZerosAndPlain(BigDecimal bigDecimal) {
    bigDecimal = bigDecimal.stripTrailingZeros();
    var toStripTrailingZerosAndPlain = bigDecimal.toPlainString();
    return toStripTrailingZerosAndPlain;
  }

}
