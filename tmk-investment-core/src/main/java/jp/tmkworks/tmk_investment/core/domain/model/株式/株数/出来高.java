package jp.tmkworks.tmk_investment.core.domain.model.株式.株数;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import jp.tmkworks.tmk_investment.core.domain.model.common.singlefield.lang.NumberStringValue;

public class 出来高 extends 株数 {

  protected 出来高(String value) {
    super(value);
  }
  
  @JsonCreator
  public static 出来高 of(String value) {
    var normalized = NumberStringValue.toStripTrailingZerosAndPlain(value);
    var created = new 出来高(normalized);
    return created;
  }
  
  @JsonCreator
  public static 出来高 of(BigDecimal value) {
    var normalized = NumberStringValue.toStripTrailingZerosAndPlain(value);
    var created = new 出来高(normalized);
    return created;
  }

  @JsonValue
  @Override
  public String string() {
    return super.string();
  }

}
