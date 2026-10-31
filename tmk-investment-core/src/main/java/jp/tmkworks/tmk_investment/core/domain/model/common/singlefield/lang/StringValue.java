package jp.tmkworks.tmk_investment.core.domain.model.common.singlefield.lang;

import java.io.Serializable;

import jp.tmkworks.tmk_investment.core.domain.model.common.singlefield.SingleFieldValue;

public abstract class StringValue implements SingleFieldValue<String>, Serializable {
  protected final String value;

  protected StringValue(String value) {
    this.value = value;
  }

  @Override
  public String value() {
    return value;
  }

  public String string() {
    return value;
  }
  
  public String getString() {
    return value;
  }

  @Override
  public String toString() {
    return value;
  }

}
