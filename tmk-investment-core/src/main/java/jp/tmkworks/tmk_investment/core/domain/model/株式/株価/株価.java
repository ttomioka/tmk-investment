package jp.tmkworks.tmk_investment.core.domain.model.株式.株価;

import java.math.BigDecimal;

import jp.tmkworks.tmk_investment.core.domain.model.common.singlefield.lang.NumberStringValue;

public class 株価 extends NumberStringValue implements Comparable<株価> {

  protected 株価(String value) {
    super(value);
  }

  @Override
  public int compareTo(株価 other) {
    return new BigDecimal(value).compareTo(new BigDecimal(other.value));
  }

}
