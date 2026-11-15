package jp.tmkworks.tmk_investment.core.domain.model.株式.株価;

import java.math.BigDecimal;
import java.math.RoundingMode;

import jp.tmkworks.tmk_investment.core.domain.model.common.singlefield.lang.NumberStringValue;
import jp.tmkworks.tmk_investment.util.java.math.BigDecimalUtils;

public class 株価 extends NumberStringValue implements Comparable<株価> {
  private static final int scale = 2;
  private static final RoundingMode roundingMode = RoundingMode.HALF_UP;

  protected 株価(String value) {
    super(BigDecimalUtils.normalize(value, scale, roundingMode));
  }

  protected 株価(株価 株価) {
    super(株価.value());
  }

  @Override
  public int compareTo(株価 other) {
    return new BigDecimal(value).compareTo(new BigDecimal(other.value));
  }
  
  public boolean is計算可能() {
    return BigDecimalUtils.isCreatableBigDecimal(value);
  }

  public boolean is計算不可() {
    return !is計算可能();
  }

}
