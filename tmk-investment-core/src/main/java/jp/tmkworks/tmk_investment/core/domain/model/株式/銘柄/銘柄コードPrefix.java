package jp.tmkworks.tmk_investment.core.domain.model.株式.銘柄;

import java.util.Arrays;
import java.util.stream.Stream;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum 銘柄コードPrefix {
  ONE("1"), TWO("2"), THREE("3"), FOUR("4"), FIVE("5"), SIX("6"), SEVEN("7"), EIGHT("8"), NINE("9");

  @Getter final String prefix;

  public static Stream<銘柄コードPrefix> stream(){
    return Arrays.stream(values());
  }
  
}
