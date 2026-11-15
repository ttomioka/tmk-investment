package jp.tmkworks.tmk_investment.core.domain.model.株式.株価.分析.breakout;

import java.util.function.Function;

import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.分析.分析値;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.分析.高値.高値N日;
import jp.tmkworks.tmk_investment.core.domain.model.株式.足.日足.日足;

public class 価格差 extends 分析値 {
  public static final String ブレイクアウト済み = "reached";

  protected 価格差(String num) {
    super(num);
  }

  public static <R extends 価格差> R subtract(高値N日 高値N日, 日足 日足, Function<String, R> factory) {
    boolean is高値N日が算出されてない = 高値N日.is計算不可();
    if (is高値N日が算出されてない) 
      return factory.apply(計算不可);

    var 高値 = 日足.高値();
    var 終値 = 日足.終値();
    
    boolean is既にブレイクアウトしていた = 高値N日.isSameValue(高値);
    if (is既にブレイクアウトしていた)
      return factory.apply(計算不可);
    
    var a = 高値N日.decimalNumValue();
    var b = 終値.decimalNumValue();
    var value = a.minus(b);
    
    var valuestr = value.bigDecimalValue().toPlainString();
    var r = factory.apply(valuestr);
    return r;
  }

}
