package jp.tmkworks.tmk_investment.core.domain.model.株式.金額;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import jp.tmkworks.tmk_investment.core.domain.model.common.singlefield.lang.NumberStringValue;

public class 売買代金 extends 金額 {

  protected 売買代金(String value) {
    super(value);
  }
  
  @JsonCreator
  public static 売買代金 of(String value) {
    var normalized = NumberStringValue.toStripTrailingZerosAndPlain(value);
    var created = new 売買代金(normalized);
    return created;
  }
  
  @JsonCreator
  public static 売買代金 of(BigDecimal value) {
    var normalized = NumberStringValue.toStripTrailingZerosAndPlain(value);
    var created = new 売買代金(normalized);
    return created;
  }

  @JsonValue
  @Override
  public String string() {
    return super.string();
  }

}
