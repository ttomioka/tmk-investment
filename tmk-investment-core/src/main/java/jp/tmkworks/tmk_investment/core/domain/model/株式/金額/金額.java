package jp.tmkworks.tmk_investment.core.domain.model.株式.金額;

import jp.tmkworks.tmk_investment.core.domain.model.common.singlefield.lang.NumberStringValue;

public class 金額 extends NumberStringValue implements Comparable<金額> {

  protected 金額(String value) {
    super(value);
  }

  @Override
  public int compareTo(金額 o) {
    return 0;
  }

}
