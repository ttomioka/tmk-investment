package jp.tmkworks.tmk_investment.core.domain.model.common.singlefield.lang;

import java.io.Serializable;

import org.apache.commons.lang3.NotImplementedException;

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
  
  public String string() {
    return value;
  }

  @Override
  public String toString() {
    return value;
  }

}
