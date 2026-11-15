package jp.tmkworks.tmk_investment.core.domain.model.株式.日付;

public record 東証取引日(日付 日付, 休日区分 休日区分) implements Comparable<東証取引日> {

  public boolean is営業日である() {
    return 休日区分.is営業日である();
  }

  public boolean isこの日は指定日より過去(日付 基準日) {
    return 日付.isこの日は指定日より過去(基準日);
  }

  @Override
  public int compareTo(東証取引日 o) {
    return 日付().compareTo(o.日付());
  }
}
