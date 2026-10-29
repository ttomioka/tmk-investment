package jp.tmkworks.tmk_investment.util.spring.httpclient;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class RestClientSimpleAccessLogger implements ClientHttpRequestInterceptor {
  private static final Logger log = LoggerFactory.getLogger("rest-client.simple-access-log");

  @Override
  public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
    log.info("RestClient requested. URL: {} {}", request.getMethod(), request.getURI());

    var response = execution.execute(request, body);

    var httpStatusCode = response.getStatusCode();
    log.info("RestClient reponse received. status: {}", httpStatusCode);

    return response;
  }

}
