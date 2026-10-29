package jp.tmkworks.tmk_investment.core.domain.model.common.singlefield.time;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import jp.tmkworks.tmk_investment.core.domain.model.common.singlefield.SingleFieldValue;
import lombok.Getter;

public abstract class OffsetDateTimeValue implements SingleFieldValue<OffsetDateTime> ,Comparable<OffsetDateTimeValue> {
  @Getter
  protected final OffsetDateTime value;

  public OffsetDateTimeValue(OffsetDateTime value) {
    this.value = value.withOffsetSameInstant(ZoneOffset.ofHours(9));
  }

  @Override
  public OffsetDateTime value() {
    return value;
  }

  public String format() {
    var formatted = value. format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    return formatted;
  }

  public static String toString(OffsetDateTimeValue o) {
    return o.value.toString();
  }
  
  @Override
  public int compareTo(OffsetDateTimeValue o) {
    return value.compareTo(o.value);
  }
  
  @Override
  public String toString() {
    return value.toString();
  }

}
