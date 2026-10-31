package jp.tmkworks.tmk_investment.core.domain.model.株式.株数;

import jp.tmkworks.tmk_investment.core.domain.model.common.singlefield.lang.NumberStringValue;

public class 株数 extends NumberStringValue implements Comparable<株数> {

  protected 株数(String value) {
    super(value);
  }

  @Override
  public int compareTo(株数 o) {
    return 0;
  }

}
