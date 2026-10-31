package jp.tmkworks.tmk_investment.core.domain.model.common.singlefield.time;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jp.tmkworks.tmk_investment.core.domain.model.common.singlefield.SingleFieldValue;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
public abstract class LocalDateValue implements SingleFieldValue<LocalDate>, Comparable<LocalDateValue> {
  @Getter
  protected final LocalDate value;

  protected LocalDateValue(LocalDate value) {
    this.value = value;
  }
  
  protected LocalDateValue(LocalDateValue localDateValue) {
    this.value = localDateValue == null ? null : localDateValue.value;
  }

  @Override
  public LocalDate value() {
    return value;
  }
  
  public LocalDate getLocalDate() {
    return value;
  }
  
  public String format(DateTimeFormatter formatter) {
    return value.format(formatter);
  }
  
  public String formatIsoLocalDate() {
    return value.format(DateTimeFormatter.ISO_LOCAL_DATE);
  }
  
  @Override
  public String toString() {
    return value.toString();
  }

  public static String toString(LocalDateValue o) {
    return o.value.toString();
  }
  
  public static boolean graterThanOrEquals(LocalDateValue aValue, LocalDateValue bValue) {
    var a = aValue.value;
    var b = bValue.value;
    return a.compareTo(b) >= 0;
  }
  
  public static boolean graterThan(LocalDateValue aValue, LocalDateValue bValue) {
    var a = aValue.value;
    var b = bValue.value;
    return a.compareTo(b) > 0;
  }

  public static boolean lessThanOrEquals(LocalDateValue aValue, LocalDateValue bValue) {
    var a = aValue.value;
    var b = bValue.value;
    return a.compareTo(b) <= 0;
  }
  
  public static boolean lessThan(LocalDateValue aValue, LocalDateValue bValue) {
    var a = aValue.value;
    var b = bValue.value;
    return a.compareTo(b) < 0;
  }
  
  public static boolean isAはBより過去or同日(LocalDateValue a, LocalDateValue b) {
    return lessThanOrEquals(a, b);
  }
  
  public static boolean isAはBより過去(LocalDateValue a, LocalDateValue b) {
    return lessThan(a, b);
  }
  
  public static boolean isAとB同日(LocalDateValue a, LocalDateValue b) {
    return equals(a, b);
  }
  
  public boolean isこの日は指定日より未来or同日(LocalDateValue b) {
    return graterThanOrEquals(this, b);
  }
  
  public boolean isこの日は指定日より未来(LocalDateValue b) {
    return graterThan(this, b);
  }
  
  public boolean isこの日と同日(LocalDate b) {
    return equals(this, b);
  }
  
  public static boolean equals(LocalDateValue aValue, LocalDateValue bValue) {
    var a = aValue.value;
    var b = bValue.value;
    return a.compareTo(b) == 0;
  }
  
  public static boolean equals(LocalDateValue aValue, LocalDate bValue) {
    var a = aValue.value;
    return a.compareTo(bValue) == 0;
  }
  
  @Override
  public int compareTo(LocalDateValue o) {
    return value.compareTo(o.value);
  }

}
