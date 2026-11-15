package jp.tmkworks.tmk_investment.core.domain.model.株式.銘柄;

import com.fasterxml.jackson.annotation.JsonCreator;

import jp.tmkworks.tmk_investment.core.domain.model.common.lang.CodeBasedEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum 市場区分 implements CodeBasedEnum {
  プライム("0111", "プライム"), 
  スタンダード("0112", "スタンダード"), 
  グロース("0113", "グロース"), 
  その他("0109", "その他"), 
  TOKYO_PRO_MARKET("0105", "TOKYO PRO MARKET");

  final String code;
  final String label;
  
  @JsonCreator
  public static 市場区分 fromCode(String code) {
    var found = CodeBasedEnum.fromCode(市場区分.class, code);
    return found;
  }
  
  public boolean is東証プライム() {
    return this == プライム;
  }
  
}
