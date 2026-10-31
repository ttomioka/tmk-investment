package jp.tmkworks.tmk_investment.core.domain.model.株式.日付;

import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import jp.tmkworks.tmk_investment.core.domain.model.common.singlefield.time.LocalDateValue;

public class 日付 extends LocalDateValue {
  private static final Map<LocalDate, 日付> CACHE = new ConcurrentHashMap<>();

  protected 日付(LocalDate value) {
    super(value);
  }

  @JsonCreator
  public static 日付 of(String text) {
    var localDate = LocalDate.parse(text);
    return of(localDate);
  }

  static 日付 of(LocalDate localDate) {
    return CACHE.computeIfAbsent(localDate, 日付::new);
  }
  
  @JsonValue
  @Override
  public LocalDate value() {
    return super.value();
  }

}
