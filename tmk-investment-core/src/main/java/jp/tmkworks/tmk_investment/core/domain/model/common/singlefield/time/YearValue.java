package jp.tmkworks.tmk_investment.core.domain.model.common.singlefield.time;

import java.time.Year;

import jp.tmkworks.tmk_investment.core.domain.model.common.singlefield.SingleFieldValue;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
public abstract class YearValue implements SingleFieldValue<Year> {
  @Getter
  protected final Year value;

  public YearValue(Year value) {
    this.value = value;
  }

  @Override
  public Year value() {
    return value;
  }
  
  @Override
  public String toString() {
    return value.toString();
  }

}
