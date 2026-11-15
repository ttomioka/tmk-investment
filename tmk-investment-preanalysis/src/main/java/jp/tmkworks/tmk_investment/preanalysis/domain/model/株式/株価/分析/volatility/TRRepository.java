package jp.tmkworks.tmk_investment.preanalysis.domain.model.株式.株価.分析.volatility;

import jp.tmkworks.tmk_investment.core.domain.model.株式.日付.日付;

public interface TRRepository {

  銘柄ATRMap find取引価格TRMap(日付 日付);
  
  銘柄ATRMap find調整価格TRMap(日付 日付);

}
