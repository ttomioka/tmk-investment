package jp.tmkworks.tmk_investment.core.domain.model.株式.足.日足;

import jp.tmkworks.tmk_investment.core.domain.model.株式.日付.日付;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.四本値.始値;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.四本値.安値;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.四本値.終値;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.四本値.高値;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株数.出来高;
import jp.tmkworks.tmk_investment.core.domain.model.株式.金額.売買代金;
import jp.tmkworks.tmk_investment.core.domain.model.株式.銘柄.銘柄コード;

public interface 日足 {

  日付 日付();

  銘柄コード 銘柄コード();

  始値 始値();

  高値 高値();

  安値 安値();

  終値 終値();

  出来高 出来高();

  売買代金 売買代金();

  default boolean is同日(日付 基準日) {
    return 日付().isこの日と同日(基準日);
  }

  default boolean is基準日より過去or同日(日付 基準日) {
    return 日付().isこの日は指定日より過去or同日(基準日);
  }

}
