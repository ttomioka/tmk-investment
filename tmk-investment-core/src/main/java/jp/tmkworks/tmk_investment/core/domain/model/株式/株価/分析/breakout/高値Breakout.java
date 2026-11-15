package jp.tmkworks.tmk_investment.core.domain.model.株式.株価.分析.breakout;

import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.分析.volatility.Volatility;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.分析.高値.高値履歴;
import jp.tmkworks.tmk_investment.core.domain.model.株式.足.日足.日足;

public record 高値Breakout(高値20日BOまでの価格 高値20日BOまでの価格, 高値20日BOまでのATR率 高値20日BOまでのATR率, 高値55日BOまでの価格 高値55日BOまでの価格, 高値55日BOまでのATR率 高値55日BOまでのATR率) {
  static final 高値20日BOまでの価格 計算不可_高値20日BOまでの価格 = new 高値20日BOまでの価格("-");
  static final 高値20日BOまでの価格 ブレイクアウト済み = new 高値20日BOまでの価格("reached");
  static final 高値55日BOまでの価格 計算不可_高値55日 = new 高値55日BOまでの価格("-");
  static final 高値55日BOまでの価格 ブレイクアウト済み_高値55日 = new 高値55日BOまでの価格("reached");

  static final 高値20日BOまでのATR率 計算不可_ATR_高値20日 = new 高値20日BOまでのATR率("-");
  static final 高値55日BOまでのATR率 計算不可_ATR_高値55日 = new 高値55日BOまでのATR率("-");

  public static 高値Breakout calculate(日足 日足, 高値履歴 高値履歴, Volatility volatility) {
    var 高値20日 = 高値履歴.高値20日();
    var 高値55日 = 高値履歴.高値55日();
    var atr = volatility.atr();

    var 高値20日BOまでの価格 = 価格差.subtract(高値20日, 日足, 高値20日BOまでの価格::new);
    var 高値20日BOまでのATR率 = 価格ATR率.calculate(高値20日BOまでの価格, atr,高値20日BOまでのATR率::new);
    var 高値55日BOまでの価格 = 価格差.subtract(高値55日, 日足, 高値55日BOまでの価格::new);
    var 高値55日BOまでのATR率 = 価格ATR率.calculate(高値55日BOまでの価格, atr,高値55日BOまでのATR率::new);

    var 高値Breakout = new 高値Breakout(高値20日BOまでの価格, 高値20日BOまでのATR率, 高値55日BOまでの価格, 高値55日BOまでのATR率);
    return 高値Breakout;
  }

}
