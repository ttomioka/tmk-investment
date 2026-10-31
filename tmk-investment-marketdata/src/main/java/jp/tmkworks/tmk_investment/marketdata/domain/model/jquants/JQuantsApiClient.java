package jp.tmkworks.tmk_investment.marketdata.domain.model.jquants;

import jp.tmkworks.tmk_investment.marketdata.domain.model.取得期間;
import jp.tmkworks.tmk_investment.marketdata.domain.model.jquants.株価.Jq日足四本値List;

public interface JQuantsApiClient {

  Jq日足四本値List getEquitiesBarDaily(取得期間 取得期間);
  
}
