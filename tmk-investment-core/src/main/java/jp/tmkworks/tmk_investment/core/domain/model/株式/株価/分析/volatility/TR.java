package jp.tmkworks.tmk_investment.core.domain.model.株式.株価.分析.volatility;

import org.ta4j.core.num.Num;

import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.株価;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.分析.分析値;
import jp.tmkworks.tmk_investment.core.domain.model.株式.足.日足.日足;

public class TR extends 分析値 {
  private static final TR 計算不可 = new TR("-");

  TR(String value) {
    super(value);
  }

  TR(Num num) {
    super(num.bigDecimalValue());
  }

  public static TR calculate(日足 基準日日足, 日足 基準日前日日足) {
    boolean データ不足で計算不可 = 基準日前日日足 == null;
    if (データ不足で計算不可)
      return 計算不可;

    var 当日高値 = 基準日日足.高値();
    var 当日安値 = 基準日日足.安値();
    var 前日終値 = 基準日前日日足.終値();

    var H_L = absoluteDifference(当日高値, 当日安値);
    var H_PDC = absoluteDifference(当日高値, 前日終値);
    var PDC_L = absoluteDifference(前日終値, 当日安値);

    var trNum = max(H_L, H_PDC, PDC_L);
    var tr = new TR(trNum);
    return tr;
  }

  static Num absoluteDifference(株価 a, 株価 b) {
    var aNum = a.decimalNumValue();
    var bNum = b.decimalNumValue();
    var difference = aNum.minus(bNum);
    var absoluteDifference = difference.abs();
    return absoluteDifference;
  }

  static Num max(Num a, Num b, Num c) {
    var max = a.max(b);
    max = max.max(c);
    return max;
  }

}
