package jp.tmkworks.tmk_investment.marketdata.domain.model;

import java.time.LocalDate;

import jp.tmkworks.tmk_investment.core.domain.model.common.singlefield.time.LocalDateValue;

public class 取得対象日 extends LocalDateValue {

  public 取得対象日(LocalDate value) {
    super(value);
  }

  public static 取得対象日 of(String localDateStr) {
    return new 取得対象日(LocalDate.parse(localDateStr));
  }

}
