package jp.tmkworks.tmk_investment.core.domain.model.株式.銘柄;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import jp.tmkworks.tmk_investment.core.domain.model.common.singlefield.lang.StringValue;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class 銘柄コード extends StringValue {
  private static final ConcurrentMap<String, 銘柄コード> CACHE = new ConcurrentHashMap<>();

  public 銘柄コード(String value) {
    super(value);
  }

  @JsonCreator
  public static 銘柄コード of(String value) {
    return CACHE.computeIfAbsent(value, 銘柄コード::new);
  }

  @JsonValue
  @Override
  public String string() {
    return super.string();
  }

}