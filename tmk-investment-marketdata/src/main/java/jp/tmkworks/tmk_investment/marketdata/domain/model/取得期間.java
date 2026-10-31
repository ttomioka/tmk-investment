package jp.tmkworks.tmk_investment.marketdata.domain.model;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.Range;

import jp.tmkworks.tmk_investment.core.domain.model.common.singlefield.time.LocalDateRangeValue;

public class 取得期間 extends LocalDateRangeValue {

  public 取得期間(Range<LocalDate> value) {
    super(value);
  }

  public static 取得期間 of(LocalDate fromInclusive, LocalDate toInclusive) {
    var value = Range.of(fromInclusive, toInclusive);
    var バックテスト対象期間 = new 取得期間(value);
    return バックテスト対象期間;
  }

  public static 取得期間 of(String fromInclusiveDate, String toInclusiveDate) {
    var fromInclusive = LocalDate.parse(fromInclusiveDate);
    var toInclusive = LocalDate.parse(toInclusiveDate);
    return of(fromInclusive, toInclusive);
  }

  public boolean contains(YearMonth yearMonth) {
    var yearMonthStartDay = yearMonth.atDay(1);
    boolean contains = value.contains(yearMonthStartDay);
    return contains;
  }

  public List<取得対象日> to取得対象日List() {
    var converted = allRangeDates().stream()
        .map(取得対象日::new)
        .toList();
    return converted;
  }

  private List<LocalDate> allRangeDates() {
    var allRangeDates = new ArrayList<LocalDate>();

    var localDate = value.getMinimum();
    while (true) {
      boolean notContains = !value.contains(localDate);
      if (notContains)
        break;

      allRangeDates.add(localDate);
      localDate = localDate.plusDays(1);
    }

    return allRangeDates;
  }

}
