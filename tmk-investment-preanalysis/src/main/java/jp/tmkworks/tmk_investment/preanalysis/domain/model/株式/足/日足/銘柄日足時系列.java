package jp.tmkworks.tmk_investment.preanalysis.domain.model.株式.足.日足;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import jp.tmkworks.tmk_investment.core.domain.model.common.lang.util.AbstractStandardList;
import jp.tmkworks.tmk_investment.core.domain.model.株式.日付.日付;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.分析.breakout.価格ATR率;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.分析.breakout.価格差;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.分析.breakout.高値20日BOまでのATR率;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.分析.breakout.高値20日BOまでの価格;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.分析.breakout.高値55日BOまでのATR率;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.分析.breakout.高値55日BOまでの価格;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.分析.breakout.高値Breakout;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.分析.volatility.ATR;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.分析.volatility.TR;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.分析.volatility.Volatility;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.分析.安値.安値10日;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.分析.安値.安値20日;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.分析.安値.安値55日;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.分析.安値.安値N日;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.分析.安値.安値履歴;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.分析.高値.高値10日;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.分析.高値.高値20日;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.分析.高値.高値55日;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.分析.高値.高値N日;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.分析.高値.高値履歴;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.四本値.安値;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.四本値.高値;
import jp.tmkworks.tmk_investment.core.domain.model.株式.足.日足.分析済日足;
import jp.tmkworks.tmk_investment.core.domain.model.株式.足.日足.取引価格日足;
import jp.tmkworks.tmk_investment.core.domain.model.株式.足.日足.日足;
import jp.tmkworks.tmk_investment.core.domain.model.株式.足.日足.調整価格日足;
import jp.tmkworks.tmk_investment.core.domain.model.株式.銘柄.銘柄コード;

public class 銘柄日足時系列<T extends 日足> extends AbstractStandardList<T> {
  private final 銘柄コード 銘柄コード;

  final Comparator<日足> 取引日昇順 = Comparator.comparing(日足::日付);
  final Comparator<日足> 取引日降順 = 取引日昇順.reversed();

  銘柄日足時系列(銘柄コード 銘柄コード, List<T> list) {
    super(list);
    this.銘柄コード = 銘柄コード;
  }
  
  public static 銘柄日足時系列<取引価格日足> of(取引価格日足List list,銘柄コード 銘柄コード) {
    var selected = list.select(銘柄コード);
    var selectedList = selected.toList();
    var instance = new 銘柄日足時系列<取引価格日足>(銘柄コード, selectedList);
    return instance;
  }
  
  public static 銘柄日足時系列<調整価格日足> of(調整価格日足List list,銘柄コード 銘柄コード) {
    var selected = list.select(銘柄コード);
    var selectedList = selected.toList();
    var instance = new 銘柄日足時系列<調整価格日足>(銘柄コード, selectedList);
    return instance;
  }

  public 分析済日足 分析する(日付 基準日, 日付 基準日前日, ATR 前日ATR) {
    var 日足 = select(基準日);
    var 高値履歴 = calculate高値履歴(基準日);
    var 安値履歴 = calculate安値履歴(基準日);
    var volatility = calculateVolatility(基準日, 基準日前日, 前日ATR);
    var 高値Breakout = calculate高値Breakout(日足, 高値履歴, volatility);

    var bulit = 分析済日足.builder()
        .銘柄コード(銘柄コード)
        .日付(基準日)
        .日足(日足)
        .高値履歴(高値履歴)
        .安値履歴(安値履歴)
        .volatility(volatility)
        .高値Breakout(高値Breakout)
        .build();
    return bulit;
  }

  日足 select(日付 基準日) {
    var filtered = list.stream()
        .filter(時系列 -> 時系列.is同日(基準日))
        .findAny()
        .orElseThrow();
    return filtered;
  }

  高値履歴 calculate高値履歴(日付 基準日) {
    var a高値10日 = calculate高値N日(基準日, 10, 高値10日::new, 高値履歴.計算不可_高値10日);
    var a高値20日 = calculate高値N日(基準日, 20, 高値20日::new, 高値履歴.計算不可_高値20日);
    var a高値55日 = calculate高値N日(基準日, 55, 高値55日::new, 高値履歴.計算不可_高値55日);

    var built = 高値履歴.builder()
        .高値10日(a高値10日)
        .高値20日(a高値20日)
        .高値55日(a高値55日)
        .build();
    return built;
  }

  <U extends 高値N日> U calculate高値N日(日付 基準日, int 営業日日数, Function<高値, U> mapper, U データ不足時) {
    var filteredList = list.stream()
        .filter(時系列 -> 時系列.is基準日より過去or同日(基準日))
        .sorted(Comparator.comparing(日足::日付).reversed()) 
        .limit(営業日日数)
        .toList();

    boolean isデータ件数が不足している = filteredList.size() < 営業日日数;
    if (isデータ件数が不足している)
      return データ不足時;

    var max = filteredList.stream()
        .map(日足::高値)
        .max(Comparator.naturalOrder())
        .map(mapper)
        .orElseThrow();
    return max;
  }

  安値履歴 calculate安値履歴(日付 基準日) {
    var a安値10日 = calculate安値N日(基準日, 10, 安値10日::new, 安値履歴.計算不可_安値10日);
    var a安値20日 = calculate安値N日(基準日, 20, 安値20日::new, 安値履歴.計算不可_安値20日);
    var a安値55日 = calculate安値N日(基準日, 55, 安値55日::new, 安値履歴.計算不可_安値55日);

    var built = 安値履歴.builder()
        .安値10日(a安値10日)
        .安値20日(a安値20日)
        .安値55日(a安値55日)
        .build();
    return built;
  }

  <U extends 安値N日> U calculate安値N日(日付 基準日, int 営業日日数, Function<安値, U> mapper, U データ不足時) {
    var filteredList = list.stream()
        .filter(時系列 -> 時系列.is基準日より過去or同日(基準日))
        .sorted(Comparator.comparing(日足::日付).reversed()) 
        .limit(営業日日数)
        .toList();

    boolean isデータ件数が不足している = filteredList.size() < 営業日日数;
    if (isデータ件数が不足している)
      return データ不足時;

    var min = filteredList.stream()
        .map(日足::安値)
        .min(Comparator.naturalOrder())
        .map(mapper)
        .orElseThrow();
    return min;
  }

  Volatility calculateVolatility(日付 基準日, 日付 基準日前日, ATR 前日ATR) {
    var 基準日日足 = select(基準日);
    var 基準日前日日足 = select(基準日前日);
    var 基準日TR = TR.calculate(基準日日足, 基準日前日日足);
    var aATR = ATR.calculate(前日ATR, 基準日TR);

    var volatility = new Volatility(基準日TR, aATR);
    return volatility;
  }
  
  static 高値Breakout calculate高値Breakout(日足 日足, 高値履歴 高値履歴, Volatility volatility) {
    var 高値20日 = 高値履歴.高値20日();
    var 高値55日 = 高値履歴.高値55日();
    var atr = volatility.atr();

    var 高値20日BOまでの価格 = 価格差.subtract(高値20日, 日足, 高値20日BOまでの価格::new);
    var 高値20日BOまでのATR率 = 価格ATR率.calculate(高値20日BOまでの価格, atr,高値20日BOまでのATR率::new);
    var 高値55日BOまでの価格 = 価格差.subtract(高値55日, 日足, 高値55日BOまでの価格::new);
    var 高値55日BOまでのATR率 = 価格ATR率.calculate(高値55日BOまでの価格, atr,高値55日BOまでのATR率::new);

    var 高値Breakout = new 高値Breakout(高値20日BOまでの価格, 高値20日BOまでのATR率, 高値55日BOまでの価格, 高値55日BOまでのATR率);
    return 高値Breakout;
  }

}
