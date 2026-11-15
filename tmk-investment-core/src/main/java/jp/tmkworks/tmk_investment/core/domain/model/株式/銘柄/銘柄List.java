package jp.tmkworks.tmk_investment.core.domain.model.株式.銘柄;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.commons.lang3.Strings;

import jp.tmkworks.tmk_investment.core.domain.model.common.lang.util.AbstractStandardList;

public class 銘柄List extends AbstractStandardList<銘柄> {

  銘柄List(List<銘柄> list) {
    super(list);
  }

  public static Collector<銘柄, ?, 銘柄List> collector() {
    var collector = Collectors.collectingAndThen(Collectors.toList(), 銘柄List::new);
    return collector;
  }

  public 銘柄List select事前分析対象銘柄() {
    var selected = list.stream()
        .filter(銘柄::is事前分析対象銘柄)
        .filter(銘柄 -> !Strings.CS.equals(銘柄.銘柄コード().string(),"81410"))
        .collect(Collectors.collectingAndThen(Collectors.toList(), 銘柄List::new));
    return selected;
  }

  public 銘柄List select(銘柄コードList 銘柄コードList) {
    var selected = list.stream()
        .filter(銘柄 -> 銘柄コードList.contains(銘柄.銘柄コード()))
        .collect(Collectors.collectingAndThen(Collectors.toList(), 銘柄List::new));
    return selected;
  }

}
