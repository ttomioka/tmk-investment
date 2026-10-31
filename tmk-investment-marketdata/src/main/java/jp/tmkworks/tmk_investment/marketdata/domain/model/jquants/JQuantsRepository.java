package jp.tmkworks.tmk_investment.marketdata.domain.model.jquants;

import jp.tmkworks.tmk_investment.marketdata.domain.model.取得期間;
import jp.tmkworks.tmk_investment.marketdata.domain.model.jquants.株価.Jq日足四本値List;

public interface JQuantsRepository {
  
//  void save東証取引カレンダー(List<東証取引カレンダー> list);
//  
//  void deleteAll上場銘柄();
//  
//  void save上場銘柄(List<上場銘柄> list);
  
//  void deleteAll株価四本値();
  
  void delete株価四本値(取得期間 取得期間);
  
  void save株価四本値(Jq日足四本値List list);

}
