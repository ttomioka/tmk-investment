package jp.tmkworks.tmk_investment.marketdata.port.jquants.api;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public record JQuantsApiGetResponse(
    @JsonProperty("data") List<Map<String, Object>> data,
    @JsonProperty("pagination_key") String paginationKey
) {}
