package jp.tmkworks.tmk_investment.core.domain.model.株式.株価.調整;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import jp.tmkworks.tmk_investment.core.domain.model.common.singlefield.lang.NumberStringValue;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.株価;

public class 調整済始値 extends 株価 {

  調整済始値(String value) {
    super(value);
  }

  @JsonCreator
  public static 調整済始値 of(String value) {
    var normalized = NumberStringValue.toStripTrailingZerosAndPlain(value);
    var 始値 = new 調整済始値(normalized);
    return 始値;
  }
  
  @JsonCreator
  public static 調整済始値 of(BigDecimal value) {
    var normalized = NumberStringValue.toStripTrailingZerosAndPlain(value);
    var created = new 調整済始値(normalized);
    return created;
  }

  @JsonValue
  @Override
  public String string() {
    return super.string();
  }
}
