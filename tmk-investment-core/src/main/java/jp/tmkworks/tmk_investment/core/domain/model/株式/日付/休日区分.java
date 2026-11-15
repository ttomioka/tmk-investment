package jp.tmkworks.tmk_investment.core.domain.model.株式.日付;

import com.fasterxml.jackson.annotation.JsonCreator;

import jp.tmkworks.tmk_investment.core.domain.model.common.lang.CodeBasedEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum 休日区分 implements CodeBasedEnum {

  非営業日("0"), 営業日("1"), 東証半日立会日("2"), 非営業日_祝日取引あり("3");

  private final String code;
  
  public boolean is営業日である() {
    return this == 休日区分.営業日;
  }
  
  @JsonCreator
  public static 休日区分 of(String code) {
    var created = CodeBasedEnum.fromCode(休日区分.class, code);
    return created;
  }

}
