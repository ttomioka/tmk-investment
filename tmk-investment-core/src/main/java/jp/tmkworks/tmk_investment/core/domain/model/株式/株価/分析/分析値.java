package jp.tmkworks.tmk_investment.core.domain.model.株式.株価.分析;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.ta4j.core.num.Num;

import jp.tmkworks.tmk_investment.core.domain.model.common.singlefield.lang.NumberStringValue;
import jp.tmkworks.tmk_investment.util.java.math.BigDecimalUtils;

public class 分析値 extends NumberStringValue implements Comparable<分析値> {
  private static final int scale = 2;
  private static final RoundingMode roundingMode = RoundingMode.HALF_UP;
  public static final String 計算不可 = "-";

  protected 分析値(String value) {
    super(BigDecimalUtils.normalize(value, scale, roundingMode));
  }
  
  protected 分析値(Num value) {
    this(value.bigDecimalValue());
  }
  
  protected 分析値(BigDecimal value) {
    this(value.toPlainString());
  }
  
  protected 分析値(分析値 分析値) {
    super(分析値.value());
  }
  
  public boolean is計算可能() {
    return BigDecimalUtils.isCreatableBigDecimal(value);
  }

  public boolean is計算不可() {
    return !is計算可能();
  }

  @Override
  public int compareTo(分析値 other) {
    return new BigDecimal(value).compareTo(new BigDecimal(other.value));
  }

}
