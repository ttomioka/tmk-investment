package jp.tmkworks.tmk_investment.marketdata.domain.model.jquants.株価;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record 株価四本値(
    @JsonProperty("Code") String 銘柄コード,
    @JsonProperty("Date") LocalDate 日付,
    @JsonProperty("O") String 始値,
    @JsonProperty("H") String 高値,
    @JsonProperty("L") String 安値,
    @JsonProperty("C") String 終値,
    @JsonProperty("UL") String ストップ高,
    @JsonProperty("LL") String ストップ安,
    @JsonProperty("Vo") String 出来高,
    @JsonProperty("Va") String 売買代金,
    @JsonProperty("AdjFactor") String 調整係数,
    
    @JsonProperty("AdjO") String 調整済始値,
    @JsonProperty("AdjH") String 調整済高値,
    @JsonProperty("AdjL") String 調整済安値,
    @JsonProperty("AdjC") String 調整済終値,
    @JsonProperty("AdjVo") String 調整済出来高
) {}
