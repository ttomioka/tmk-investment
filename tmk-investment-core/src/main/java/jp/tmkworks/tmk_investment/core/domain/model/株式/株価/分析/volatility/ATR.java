package jp.tmkworks.tmk_investment.core.domain.model.株式.株価.分析.volatility;

import java.math.BigDecimal;

import org.ta4j.core.num.DecimalNumFactory;
import org.ta4j.core.num.Num;

import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.分析.分析値;

public class ATR extends 分析値 {
  @SuppressWarnings("unused")
  private static final ATR 計算不可 = new ATR("-");
  private static final Num NINE_TEEN = DecimalNumFactory.getInstance().numOf("19");
  private static final Num TWENTY = DecimalNumFactory.getInstance().numOf("20");

  public ATR(String value) {
    super(value);
  }
  
  ATR(BigDecimal value) {
    super(value);
  }
  
  ATR(Num num) {
    super(num.bigDecimalValue());
  }
  
  public static ATR calculate(ATR 前日ATR,TR 当日TR) {
    var a = 前日ATR.multiply19();
    var b = 当日TR.decimalNumValue();

    var top = a.plus(b);
    var buttom = TWENTY;
    var atrNum = top.dividedBy(buttom);
    
    var atr = new ATR(atrNum);
    return atr;
  }
  
  Num multiply19() {
    var a = decimalNumValue();
    var b = NINE_TEEN;
    var multiplied = a.multipliedBy(b);
    return multiplied;
  }

}
