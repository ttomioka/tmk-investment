package jp.tmkworks.tmk_investment.preanalysis.domain.model.株式.株価.分析.volatility;

import java.util.Map;

import jp.tmkworks.tmk_investment.core.domain.model.common.lang.util.AbstractStandardMap;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.分析.volatility.ATR;
import jp.tmkworks.tmk_investment.core.domain.model.株式.銘柄.銘柄コード;

public class 銘柄ATRMap extends AbstractStandardMap<銘柄コード, ATR> {

  public 銘柄ATRMap(Map<銘柄コード, ATR> map) {
    super(map);
  }

}
