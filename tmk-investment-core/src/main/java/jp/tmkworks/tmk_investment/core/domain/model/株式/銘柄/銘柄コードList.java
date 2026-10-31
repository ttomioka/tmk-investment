package jp.tmkworks.tmk_investment.core.domain.model.株式.銘柄;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import jp.tmkworks.tmk_investment.core.domain.model.common.lang.util.AbstractStandardList;

public class 銘柄コードList extends AbstractStandardList<銘柄コード> {

  銘柄コードList(List<銘柄コード> list) {
    super(list);
  }

  public static Collector<銘柄コード, ?, 銘柄コードList> collector() {
    var collector = Collectors.collectingAndThen(Collectors.toList(), 銘柄コードList::new);
    return collector;
  }

  public 銘柄コードList limit(int limit) {
    var limited = list.stream()
        .limit(limit)
        .collect(銘柄コードList.collector());
    return limited;
  }

}
