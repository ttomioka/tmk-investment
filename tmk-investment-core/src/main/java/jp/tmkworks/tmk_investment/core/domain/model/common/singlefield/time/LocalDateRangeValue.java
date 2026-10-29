package jp.tmkworks.tmk_investment.core.domain.model.common.singlefield.time;

import java.time.LocalDate;

import org.apache.commons.lang3.Range;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LocalDateRangeValue {
  final protected Range<LocalDate> value;

  public Range<LocalDate> value() {
    return value;
  }

  public LocalDate fromInclusive() {
    return value.getMinimum();
  }

  public LocalDate toInclusive() {
    return value.getMaximum();
  }

  @Override
  public String toString() {
    return value.toString();
  }
}
