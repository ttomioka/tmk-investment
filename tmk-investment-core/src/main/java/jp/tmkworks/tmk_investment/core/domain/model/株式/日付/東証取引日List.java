package jp.tmkworks.tmk_investment.core.domain.model.株式.日付;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.commons.lang3.Range;

public class 東証取引日List {
  final List<東証取引日> list = new CopyOnWriteArrayList<>();
  final Range<LocalTime> 取引時間 = Range.of(LocalTime.of(9, 0), LocalTime.of(15, 30));

  public 東証取引日List(List<東証取引日> list) {
    this.list.addAll(list);
  }

  public static Collector<東証取引日, ?, 東証取引日List> collector() {
    var collector = Collectors.collectingAndThen(Collectors.toList(), 東証取引日List::new);
    return collector;
  }

  public 日付 select前営業日(日付 基準日) {
    var 前営業日 = selectN日前営業日(基準日, 1);
    return 前営業日;
  }
  
  public 日付 select55日前営業日(日付 基準日) {
    var 前営業日 = selectN日前営業日(基準日, 55);
    return 前営業日;
  }

  日付 selectN日前営業日(日付 基準日, int n) {
    var N日前営業日 = list.stream()
        .filter(東証取引日::is営業日である)
        .filter(東証取引日 -> 東証取引日.isこの日は指定日より過去(基準日))
        .sorted(Comparator.reverseOrder())
        .limit(n)
        .max(Comparator.reverseOrder())
        .map(東証取引日::日付)
        .orElseThrow();
    return N日前営業日;
  }

}