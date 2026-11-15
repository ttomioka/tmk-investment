package jp.tmkworks.tmk_investment.preanalysis.domain.model.株式.足.日足;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import jp.tmkworks.tmk_investment.core.domain.model.common.lang.util.AbstractStandardList;
import jp.tmkworks.tmk_investment.core.domain.model.株式.足.日足.調整価格日足;
import jp.tmkworks.tmk_investment.core.domain.model.株式.銘柄.銘柄コード;

public class 調整価格日足List extends AbstractStandardList<調整価格日足> {

  protected 調整価格日足List(List<調整価格日足> list) {
    super(list);
  }

  public static Collector<調整価格日足, ?, 調整価格日足List> collector() {
    var collector = Collectors.collectingAndThen(Collectors.toList(), 調整価格日足List::new);
    return collector;
  }

  public 調整価格日足List select(銘柄コード 銘柄コード) {
    var selected = list.stream()
        .filter(日足 -> Objects.equals(日足.銘柄コード(), 銘柄コード))
        .collect(調整価格日足List.collector());
    return selected;
  }

}
