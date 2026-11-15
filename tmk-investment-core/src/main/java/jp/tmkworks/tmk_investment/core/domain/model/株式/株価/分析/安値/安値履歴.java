package jp.tmkworks.tmk_investment.core.domain.model.株式.株価.分析.安値;

import lombok.Builder;

@Builder
public record 安値履歴(安値10日 安値10日, 安値20日 安値20日, 安値55日 安値55日) {
  public static final 安値10日 計算不可_安値10日 = new 安値10日("-");
  public static final 安値20日 計算不可_安値20日 = new 安値20日("-");
  public static final 安値55日 計算不可_安値55日 = new 安値55日("-");

}
