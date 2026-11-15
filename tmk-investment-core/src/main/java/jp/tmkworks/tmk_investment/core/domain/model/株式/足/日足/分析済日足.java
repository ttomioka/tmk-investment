package jp.tmkworks.tmk_investment.core.domain.model.株式.足.日足;

import jp.tmkworks.tmk_investment.core.domain.model.株式.日付.日付;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.分析.breakout.高値Breakout;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.分析.volatility.Volatility;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.分析.安値.安値履歴;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.分析.高値.高値履歴;
import jp.tmkworks.tmk_investment.core.domain.model.株式.銘柄.銘柄コード;
import lombok.Builder;

@Builder
public record 分析済日足(
    銘柄コード 銘柄コード,
    日付 日付,
    日足 日足,
    高値履歴 高値履歴,
    安値履歴 安値履歴,
    Volatility volatility,
    高値Breakout 高値Breakout
) {}
