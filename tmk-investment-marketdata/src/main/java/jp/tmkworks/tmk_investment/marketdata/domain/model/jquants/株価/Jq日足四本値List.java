package jp.tmkworks.tmk_investment.marketdata.domain.model.jquants.株価;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import jp.tmkworks.tmk_investment.core.domain.model.common.lang.util.AbstractStandardList;

public class Jq日足四本値List extends AbstractStandardList<Jq日足四本値> {

  public Jq日足四本値List(List<Jq日足四本値> list) {
    super(list);
  }

  public static Collector<Jq日足四本値, ?, Jq日足四本値List> collector() {
    var collector = Collectors.collectingAndThen(Collectors.toList(), Jq日足四本値List::new);
    return collector;
  }

}
