package jp.tmkworks.tmk_investment.util.spring.httpclient;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

/** レスポンスをコピーするためのラッパークラス */
public class BufferingClientHttpResponseWrapper implements ClientHttpResponse {

  /** レスポンス */
  private final ClientHttpResponse response;

  /** ボディのコピー */
  private final byte[] body;

  BufferingClientHttpResponseWrapper(ClientHttpResponse response) {
    this.response = response;
    byte[] body;
    try {
      body = StreamUtils.copyToByteArray(response.getBody());
    } catch (IOException e) {
      body = new byte[] {};
    }
    this.body = body;
  }

  BufferingClientHttpResponseWrapper(ClientHttpResponse response, byte[] body) {
    this.response = response;
    this.body = body == null ? new byte[] {} : body.clone();
  }

  @Override
  public HttpStatusCode getStatusCode() throws IOException {
    return response.getStatusCode();
  }

  @Override
  public String getStatusText() throws IOException {
    return response.getStatusText();
  }

  @Override
  public HttpHeaders getHeaders() {
    return response.getHeaders();
  }

  @Override
  public InputStream getBody() throws IOException {
    return new ByteArrayInputStream(body);
  }

  @Override
  public void close() {
    response.close();
  }
}
