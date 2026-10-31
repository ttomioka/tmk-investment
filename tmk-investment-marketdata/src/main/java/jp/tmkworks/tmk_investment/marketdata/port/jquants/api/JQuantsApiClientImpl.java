package jp.tmkworks.tmk_investment.marketdata.port.jquants.api;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClient;

import jp.tmkworks.tmk_investment.marketdata.domain.model.取得対象日;
import jp.tmkworks.tmk_investment.marketdata.domain.model.取得期間;
import jp.tmkworks.tmk_investment.marketdata.domain.model.jquants.JQuantsApiClient;
import jp.tmkworks.tmk_investment.marketdata.domain.model.jquants.JQuantsApiKey;
import jp.tmkworks.tmk_investment.marketdata.domain.model.jquants.株価.Jq日足四本値;
import jp.tmkworks.tmk_investment.marketdata.domain.model.jquants.株価.Jq日足四本値List;
import tools.jackson.databind.json.JsonMapper;

public class JQuantsApiClientImpl implements JQuantsApiClient {
  final JQuantsPaginatableApiClient paginatableApiClient;

  public JQuantsApiClientImpl(RestClient.Builder builder, URI baseUri, JQuantsApiKey apiKey, JsonMapper jsonMapper) {
    var restClient = builder.baseUrl(baseUri)
        .defaultHeaders(httpHeaders -> {
          httpHeaders.add("x-api-key", apiKey.string());
          httpHeaders.add(HttpHeaders.ACCEPT_ENCODING, "gzip");
        })
        .build();
    this.paginatableApiClient = new JQuantsPaginatableApiClient(restClient, jsonMapper);
  }

  @Override
  public Jq日足四本値List getEquitiesBarDaily(取得期間 取得期間) {
    // from,toのみの指定では取得できないため、取得対象日で回す
    var list = 取得期間.to取得対象日List()
        .stream()
        .map(this::getEquitiesBarDaily)
        .flatMap(List::stream)
        .filter( Jq日足四本値::isNotNull)
        .collect(Jq日足四本値List.collector());
    return list;
  }

  List<Jq日足四本値> getEquitiesBarDaily(取得対象日 取得対象日) {
    final URI API_PATH = URI.create("/v2/equities/bars/daily");
    final var queryParams = new LinkedMultiValueMap<String, String>();
    queryParams.add("date", 取得対象日.formatIsoLocalDate());
    var responsed = paginatableApiClient.get(API_PATH, queryParams, Jq日足四本値.class);
    return responsed;
  }

}
