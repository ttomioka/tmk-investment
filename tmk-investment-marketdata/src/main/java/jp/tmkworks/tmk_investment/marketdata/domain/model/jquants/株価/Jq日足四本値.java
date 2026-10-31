package jp.tmkworks.tmk_investment.marketdata.domain.model.jquants.株価;

import com.fasterxml.jackson.annotation.JsonProperty;

import jp.tmkworks.tmk_investment.core.domain.model.株式.日付.日付;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.四本値.始値;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.四本値.安値;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.四本値.終値;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.四本値.高値;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.調整.調整係数;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.調整.調整済始値;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.調整.調整済安値;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.調整.調整済終値;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.調整.調整済高値;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株数.出来高;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株数.調整済出来高;
import jp.tmkworks.tmk_investment.core.domain.model.株式.金額.売買代金;
import jp.tmkworks.tmk_investment.core.domain.model.株式.銘柄.銘柄コード;

public record Jq日足四本値(
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
    
    @JsonProperty("AdjFactor") 調整係数 調整係数,
    @JsonProperty("AdjO") 調整済始値 調整済始値,
    @JsonProperty("AdjH") 調整済高値 調整済高値,
    @JsonProperty("AdjL") 調整済安値 調整済安値,
    @JsonProperty("AdjC") 調整済終値 調整済終値,
    @JsonProperty("AdjVo") 調整済出来高 調整済出来高
) {
  
  public boolean isNotNull() {
    return 出来高 != null;
  }
  
  
}
