package jp.tmkworks.tmk_investment.core.domain.model.株式.銘柄;

import com.fasterxml.jackson.annotation.JsonCreator;

import jp.tmkworks.tmk_investment.core.domain.model.common.lang.CodeBasedEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum 商品区分 implements CodeBasedEnum {
  国内株券("011", "国内株券"), 
  優先出資証券("012", "優先出資証券"), 
  REIT("013", "REIT"), 
  ETF("014", "ETF"), 
  外国株券("021", "外国株券"),
  外国REIT("022", "外国REIT"), 
  外国ETF("023", "外国ETF"), 
  外国株預託証券("024", "外国株預託証券");

  final String code;
  final String label;
  
  @JsonCreator
  public static 商品区分 fromCode(String code) {
    var found = CodeBasedEnum.fromCode(商品区分.class, code);
    return found;
  }
  
  public boolean is国内株券() {
    return this == 国内株券;
  }
  
}
