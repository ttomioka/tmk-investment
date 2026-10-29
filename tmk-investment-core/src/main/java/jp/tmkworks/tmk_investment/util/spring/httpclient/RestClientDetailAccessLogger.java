package jp.tmkworks.tmk_investment.util.spring.httpclient;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.text.StringSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class RestClientDetailAccessLogger implements ClientHttpRequestInterceptor {
  private static final Logger log = LoggerFactory.getLogger("rest-client.access-detail-log");

  @Override
  public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
    boolean isDebugEnabled = log.isDebugEnabled();
    if (isDebugEnabled) {
      var originalResponse = execution.execute(request, body);
      var clonedResponse = logResponse(request, body, originalResponse);
      return clonedResponse;
    }
    
    return execution.execute(request, body);
  }

  static ClientHttpResponse logResponse(HttpRequest request, byte[] body, ClientHttpResponse originalResponse) throws IOException {

    try (var copiedResponse = new BufferingClientHttpResponseWrapper(originalResponse)) {
      var httpMethod = request.getMethod();
      var uri = request.getURI();
      var requestHeaders = request.getHeaders();
      var requestBody = new String(body, StandardCharsets.UTF_8);

      var httpStatusCode = originalResponse.getStatusCode();
      var responseHeaders = originalResponse.getHeaders();
      byte[] copiedBody = StreamUtils.copyToByteArray(copiedResponse.getBody());
      String bodyJsonString = new String(copiedBody, StandardCharsets.UTF_8);

      String source = """

          --- Request ---
          ${httpMethod} ${uri}
          ${requestHeaders}
          
          ${requestBody}
          ---
          --- Response ---
          ${httpStatusCode}
          ${responseHeader}

          ${responseBody}
          ---
          """;

      var valueMap = new HashMap<String, Object>();
      valueMap.put("httpMethod", httpMethod);
      valueMap.put("uri", uri);
      valueMap.put("requestHeaders", toLoggingMessageHttpHeaders(requestHeaders));
      valueMap.put("requestBody", prettyPrintJsonString(requestBody));
      valueMap.put("httpStatusCode", httpStatusCode);
      valueMap.put("responseHeader", toLoggingMessageHttpHeaders(responseHeaders));
      valueMap.put("responseBody", prettyPrintJsonString(bodyJsonString));
      var message = StringSubstitutor.replace(source, valueMap);
      log.debug("{}", message);

      var clonedResponse = new BufferingClientHttpResponseWrapper(originalResponse, copiedBody);
      return clonedResponse;
    }
  }

  static String toLoggingMessageHttpHeaders(HttpHeaders headers) {
    var message = headers.headerSet()
        .stream()
        .sorted(Comparator.comparing(Entry::getKey))
        .map(entry -> entry.getKey() + ": " + toListString(entry.getValue()))
        .collect(Collectors.joining("\n"));
    return message;
  }

  static String toListString(List<?> list) {
    return list.size() == 1 ? Objects.toString(list.getFirst()) : list.toString();
  }

  static String prettyPrintJsonString(String json) {
    final var gson = new GsonBuilder().setPrettyPrinting()
        .create();

    String updatedJson = "";
    try {
      var jsonElement = JsonParser.parseString(json);
      updatedJson = gson.toJson(jsonElement);
    } catch (JsonSyntaxException e) {
      e.printStackTrace();
    }
    return updatedJson;
  }

}
