package jp.tmkworks.tmk_investment.core.domain.model.株式.株価.分析.高値;

import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.株価;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.四本値.高値;

public class 高値N日 extends 株価 {

  protected 高値N日(String value) {
    super(value);
  }

  protected 高値N日(株価 株価) {
    super(株価);
  }

  public boolean isSameValue(高値 高値) {
    boolean is共に計算可能 = is計算不可() && 高値.is計算可能();
    if (!is共に計算可能)
      return false;

    var a = this.bigDecimalValue();
    var b = 高値.bigDecimalValue();
    boolean isSameValue  = a.compareTo(b) == 0;
    return isSameValue;
  }

}
