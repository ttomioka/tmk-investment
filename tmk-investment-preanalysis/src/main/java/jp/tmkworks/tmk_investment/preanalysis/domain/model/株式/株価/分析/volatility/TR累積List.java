package jp.tmkworks.tmk_investment.preanalysis.domain.model.株式.株価.分析.volatility;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.分析.volatility.TR;

class TR累積List {
  final List<TR> list = new CopyOnWriteArrayList<>();
//  private static final BigDecimal TWENTY = new BigDecimal(20);

  void add(TR e) {
    list.add(e);
  }

  boolean isATR計算不可能() {
    return list.size() <= 20;
  }

  boolean isFirstATR計算可能() {
    return list.size() == 21;
  }

//  public ATR firstATR() {
//    var sum = list.stream()
//        .filter(分析値::is計算可)
//        .map(分析値::toBigDecimal)
//        .reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
//    var atr = ATR.divideToScale2(sum, TWENTY);
//    return atr;
//  }

  public TR 最新TR() {
    return list.getLast();
  }

}