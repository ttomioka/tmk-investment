package jp.tmkworks.tmk_investment.core.domain.model.株式.株価.分析.breakout;

import java.util.function.Function;

import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.分析.分析値;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.分析.volatility.ATR;

public class 価格ATR率 extends 分析値 {

  protected 価格ATR率(String value) {
    super(value);
  }

  public static <T extends 価格ATR率> T calculate(価格差 価格差, ATR atr, Function<String, T> factory) {
    boolean is価格差が算出されてない = 価格差.is計算不可();
    if (is価格差が算出されてない) 
      return factory.apply(計算不可);
    
    var a = 価格差.decimalNumValue();
    var b = atr.decimalNumValue();
    var divided = a.dividedBy(b);
    var dividedStr = divided.bigDecimalValue()
        .toPlainString();

    var ATR率 = factory.apply(dividedStr);
    return ATR率;
  }

}
