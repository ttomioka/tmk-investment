package jp.tmkworks.tmk_investment.core.domain.model.株式.銘柄;

import com.fasterxml.jackson.annotation.JsonCreator;

import jp.tmkworks.tmk_investment.core.domain.model.common.singlefield.lang.StringValue;

public class 会社名 extends StringValue {

  @JsonCreator
  public 会社名(String value) {
    super(value);
  }
  
}