package jp.tmkworks.tmk_investment.core.domain.model.株式.足.日足;

import jp.tmkworks.tmk_investment.core.domain.model.株式.日付.日付;

public interface 分析済日足Repository {

  void delete分析済取引価格(日付 日付);
  
  void save分析済取引価格(分析済日足List list);
  
void delete分析済調整価格(日付 日付);
  
  void save分析済調整価格(分析済日足List list);
  
}
