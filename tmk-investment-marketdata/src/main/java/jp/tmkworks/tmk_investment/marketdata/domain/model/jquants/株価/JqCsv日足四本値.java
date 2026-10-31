package jp.tmkworks.tmk_investment.marketdata.domain.model.jquants.株価;

import com.fasterxml.jackson.annotation.JsonProperty;

import jp.tmkworks.tmk_investment.core.domain.model.株式.日付.日付;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.四本値.始値;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.四本値.安値;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.四本値.終値;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.四本値.高値;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.調整.調整係数;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株数.出来高;
import jp.tmkworks.tmk_investment.core.domain.model.株式.金額.売買代金;
import jp.tmkworks.tmk_investment.core.domain.model.株式.銘柄.銘柄コード;

public record JqCsv日足四本値(
    @JsonProperty("Code") 銘柄コード 銘柄コード,
    @JsonProperty("Date") 日付 日付,
    @JsonProperty("O") 始値 始値,
    @JsonProperty("H") 高値 高値,
    @JsonProperty("L") 安値 安値,
    @JsonProperty("C") 終値 終値,
    @JsonProperty("UL") String ストップ高,
    @JsonProperty("LL") String ストップ安,
    @JsonProperty("Vo") 出来高 出来高,
    @JsonProperty("Va") 売買代金 売買代金,
    @JsonProperty("AdjFactor") 調整係数 調整係数
) {
  
  
  Jq日足四本値 toJq日足四本値(調整係数 調整係数) {
    return null;
  }
  
}
