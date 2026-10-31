package jp.tmkworks.tmk_investment.core.domain.model.株式.足.日足;

import jp.tmkworks.tmk_investment.core.domain.model.株式.日付.日付;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.四本値.始値;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株数.出来高;
import jp.tmkworks.tmk_investment.core.domain.model.株式.金額.売買代金;
import jp.tmkworks.tmk_investment.core.domain.model.株式.銘柄.銘柄コード;

public record 日足(

    日付 日付,
    銘柄コード 銘柄コード,
    始値 始値,
    出来高 出来高,
    売買代金 売買代金
    
    
    
) {

}
