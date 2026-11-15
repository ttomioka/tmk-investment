package jp.tmkworks.tmk_investment.preanalysis.domain.model.株式.足.日足;

import jp.tmkworks.tmk_investment.core.domain.model.株式.日付.日付;

public interface 日足Repository {

  取引価格日足List find取引価格四本値(日付 分析対象日, 日付 分析対象日55日前);
  
  調整価格日足List find調整価格四本値(日付 分析対象日, 日付 分析対象日55日前);

}
