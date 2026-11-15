package jp.tmkworks.tmk_investment.core.domain.model.株式.銘柄;

public record 銘柄(銘柄コード 銘柄コード, 会社名 会社名, 市場区分 市場区分, 商品区分 商品区分) {

  public boolean is国内証券() {
    return 商品区分.is国内株券();
  }

  public boolean is東証プライム銘柄() {
    return 市場区分.is東証プライム();
  }

  public boolean is事前分析対象銘柄() {
    boolean is国内株券 = 商品区分.is国内株券();
    boolean is東証プライム = 市場区分.is東証プライム();

    boolean is事前分析対象銘柄 = true;
    is事前分析対象銘柄 &= is国内株券;
    is事前分析対象銘柄 &= is東証プライム;
    return is事前分析対象銘柄;
  }

}
