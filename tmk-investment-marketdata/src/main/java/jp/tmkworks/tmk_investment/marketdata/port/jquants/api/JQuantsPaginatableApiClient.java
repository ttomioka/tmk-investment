package jp.tmkworks.tmk_investment.marketdata.port.jquants.api;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.concurrent.TimedSemaphore;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import tools.jackson.databind.json.JsonMapper;

public class JQuantsPaginatableApiClient {
  private final RestClient restClient;
  private final JsonMapper jsonMapper;

  JQuantsPaginatableApiClient(RestClient restClient, JsonMapper jsonMapper) {
    this.restClient = restClient;
    this.jsonMapper = jsonMapper;
  }

  public <R> List<R> get(URI apiPath, LinkedMultiValueMap<String, String> queryParams, Class<R> responseClass) {
    final TimedSemaphore semaphore = createTimedSemaphore();

    try {
      List<R> dataList = new ArrayList<R>();
      while (true) {
        semaphore.acquire(); // 許可が出るまで待つ

        var uri = UriComponentsBuilder.newInstance()
            .path(apiPath.toString())
            .queryParams(queryParams)
            .build()
            .toUri();

        var topLevelResponse = restClient.get()
            .uri(uri)
            .retrieve()
            .body(JQuantsApiGetResponse.class);

        var responsedData = topLevelResponse.data()
            .stream()
            .map(row -> jsonMapper.convertValue(row, responseClass))
            .toList();
        dataList.addAll(responsedData);

        var paginationKey = topLevelResponse.paginationKey();
        boolean paginationKeyIsEmpty = StringUtils.isEmpty(paginationKey);
        if (paginationKeyIsEmpty)
          break;

        queryParams.set("pagination_key", paginationKey);
      }

      semaphore.shutdown();

      return dataList;

    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

  }

  TimedSemaphore createTimedSemaphore() {
    var semaphore = TimedSemaphore.builder()
        .setLimit(1)
        .setPeriod(3)
        .setTimeUnit(TimeUnit.SECONDS)
        .get();
    return semaphore;
  }

}
