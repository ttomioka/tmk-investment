package jp.tmkworks.tmk_investment.core.domain.model.株式.株価.四本値;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import jp.tmkworks.tmk_investment.core.domain.model.common.singlefield.lang.NumberStringValue;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.株価;

public class 安値 extends 株価 {

  安値(String value) {
    super(value);
  }

  @JsonCreator
  public static 安値 of(String value) {
    var normalized = NumberStringValue.toStripTrailingZerosAndPlain(value);
    var created = new 安値(normalized);
    return created;
  }
  
  @JsonCreator
  public static 安値 of(BigDecimal value) {
    var normalized = NumberStringValue.toStripTrailingZerosAndPlain(value);
    var created = new 安値(normalized);
    return created;
  }

  @JsonValue
  @Override
  public String string() {
    return super.string();
  }
}
