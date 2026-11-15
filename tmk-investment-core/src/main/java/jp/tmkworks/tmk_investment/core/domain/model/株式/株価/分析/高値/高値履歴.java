package jp.tmkworks.tmk_investment.core.domain.model.株式.株価.分析.高値;

import lombok.Builder;

@Builder
public record 高値履歴(高値10日 高値10日, 高値20日 高値20日, 高値55日 高値55日) {
  public static final 高値10日 計算不可_高値10日 = new 高値10日("-");
  public static final 高値20日 計算不可_高値20日 = new 高値20日("-");
  public static final 高値55日 計算不可_高値55日 = new 高値55日("-");
}
