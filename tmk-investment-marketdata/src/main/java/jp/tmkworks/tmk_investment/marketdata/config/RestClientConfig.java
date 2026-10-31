package jp.tmkworks.tmk_investment.marketdata.config;

import java.net.URI;

import org.springframework.boot.restclient.RestClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import jp.tmkworks.tmk_investment.marketdata.domain.model.jquants.JQuantsApiClient;
import jp.tmkworks.tmk_investment.marketdata.domain.model.jquants.JQuantsApiKey;
import jp.tmkworks.tmk_investment.marketdata.port.jquants.api.JQuantsApiClientImpl;
import jp.tmkworks.tmk_investment.util.spring.httpclient.RestClientDetailAccessLogger;
import jp.tmkworks.tmk_investment.util.spring.httpclient.RestClientSimpleAccessLogger;
import tools.jackson.databind.json.JsonMapper;

@Configuration
public class RestClientConfig {

  @Bean
  RestClientCustomizer restClientCustomizer(JsonMapper mapper) {
    return restClientBuilder -> restClientBuilder.requestInterceptors(interceptors -> {
      interceptors.add(new RestClientSimpleAccessLogger());
      interceptors.add(new RestClientDetailAccessLogger());
    });
  }

  @Bean
  JQuantsApiClient jQuantsApiClient(RestClient.Builder builder, JsonMapper jsonMapper) {
    var baseUrl = URI.create("https://api.jquants.com");
    var apiKey = new JQuantsApiKey("QT6xUjFXxmTDmkF2vpfgvTJvpxqYFenZT-k-AqANAqE");
    return new JQuantsApiClientImpl(builder, baseUrl, apiKey, jsonMapper);
  }

}
