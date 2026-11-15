package jp.tmkworks.tmk_investment.core.domain.model.株式.足.日足;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import jp.tmkworks.tmk_investment.core.domain.model.common.lang.util.AbstractStandardList;

public class 分析済日足List extends AbstractStandardList<分析済日足> {

  分析済日足List(List<分析済日足> list) {
    super(list);
  }

  public static Collector<分析済日足, ?, 分析済日足List> collector() {
    var collector = Collectors.collectingAndThen(Collectors.toList(), 分析済日足List::new);
    return collector;
  }

}
