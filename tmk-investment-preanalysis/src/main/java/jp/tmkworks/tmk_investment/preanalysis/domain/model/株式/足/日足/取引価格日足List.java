package jp.tmkworks.tmk_investment.preanalysis.domain.model.株式.足.日足;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import jp.tmkworks.tmk_investment.core.domain.model.common.lang.util.AbstractStandardList;
import jp.tmkworks.tmk_investment.core.domain.model.株式.足.日足.取引価格日足;
import jp.tmkworks.tmk_investment.core.domain.model.株式.銘柄.銘柄コード;

public class 取引価格日足List extends AbstractStandardList<取引価格日足> {

  protected 取引価格日足List(List<取引価格日足> list) {
    super(list);
  }

  public static Collector<取引価格日足, ?, 取引価格日足List> collector() {
    var collector = Collectors.collectingAndThen(Collectors.toList(), 取引価格日足List::new);
    return collector;
  }

  public 取引価格日足List select(銘柄コード 銘柄コード) {
    var selected = list.stream()
        .filter(日足 -> Objects.equals(日足.銘柄コード(), 銘柄コード))
        .collect(取引価格日足List.collector());
    return selected;
  }

}
