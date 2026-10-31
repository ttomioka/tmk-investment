package jp.tmkworks.tmk_investment.marketdata.port.jquants.csv;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.jackson.ObjectValueDeserializer;

import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;

public class DefaultZeroDeserializer extends ObjectValueDeserializer<String> {

  @Override
  protected String deserializeObject(JsonParser jsonParser, DeserializationContext context, JsonNode tree) {
    String value = jsonParser.getValueAsString();
    String convert = StringUtils.isEmpty(value) ? "0" : value;
    return convert;
  }

}