package jp.tmkworks.tmk_investment.core.domain.model.株式.株価.調整;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import jp.tmkworks.tmk_investment.core.domain.model.common.singlefield.lang.NumberStringValue;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.四本値.始値;

public class 調整係数 extends NumberStringValue {
  static final 調整係数 ONE = new 調整係数("1");

  調整係数(String value) {
    super(value);
  }

  public static 調整係数 one() {
    return ONE;
  }

  @JsonCreator
  public static 調整係数 of(String value) {
    var normalized = NumberStringValue.toStripTrailingZerosAndPlain(value);
    var created = new 調整係数(normalized);
    return created;
  }
  
  @JsonCreator
  public static 調整係数 of(BigDecimal value) {
    var normalized = NumberStringValue.toStripTrailingZerosAndPlain(value);
    var created = new 調整係数(normalized);
    return created;
  }

  @JsonValue
  @Override
  public String string() {
    return super.string();
  }
  
  public 調整係数 累積する(調整係数 other) {
//    var value = toBigDecimal();
//    var otherValue = other.toBigDecimal();
//    var muliplied = value.multiply(otherValue);
//    muliplied = muliplied.setScale(3, RoundingMode.HALF_UP);
//    var 累積 = new 調整係数(muliplied.toPlainString());
//    return 累積;
    return null;
  }
  
  public 調整済始値 調整する(始値 始値) {
    var result = this.multiply(始値);
    var value = toStripTrailingZerosAndPlain(result);
    var 調整済 = new 調整済始値(value);
    return 調整済;
  }

}
