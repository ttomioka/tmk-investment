package jp.tmkworks.tmk_investment.preanalysis.port.rdb;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import jp.tmkworks.tmk_investment.core.domain.model.株式.日付.日付;
import jp.tmkworks.tmk_investment.core.domain.model.株式.足.日足.取引価格日足;
import jp.tmkworks.tmk_investment.core.domain.model.株式.足.日足.調整価格日足;
import jp.tmkworks.tmk_investment.core.port.rdb.Tables;
import jp.tmkworks.tmk_investment.core.port.rdb.tables.pojos.V取引価格四本値;
import jp.tmkworks.tmk_investment.core.port.rdb.tables.pojos.V調整価格四本値;
import jp.tmkworks.tmk_investment.preanalysis.domain.model.株式.足.日足.取引価格日足List;
import jp.tmkworks.tmk_investment.preanalysis.domain.model.株式.足.日足.日足Repository;
import jp.tmkworks.tmk_investment.preanalysis.domain.model.株式.足.日足.調整価格日足List;
import jp.tmkworks.tmk_investment.util.jooq.record.BeanToRecordUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class Rdb日足Repository implements 日足Repository {
  final DSLContext jooq;

  @Override
  public 取引価格日足List find取引価格四本値(日付 分析対象日, 日付 分析対象日55日前) {
    var table = Tables.V_取引価格四本値;
    var min = 分析対象日55日前.value();
    var max = 分析対象日.value();

    var list = jooq.selectFrom(table)
        .where(table.日付.between(min, max))
        .fetchInto(V取引価格四本値.class)
        .stream()
        .map(pojo -> BeanToRecordUtils.copyCreate(pojo, 取引価格日足.class))
        .collect(取引価格日足List.collector());
    return list;
  }

  @Override
  public 調整価格日足List find調整価格四本値(日付 分析対象日, 日付 分析対象日55日前) {
    var table = Tables.V_調整価格四本値;
    var min = 分析対象日55日前.value();
    var max = 分析対象日.value();

    var list = jooq.selectFrom(table)
        .where(table.日付.between(min, max))
        .fetchInto(V調整価格四本値.class)
        .stream()
        .map(pojo -> BeanToRecordUtils.copyCreate(pojo, 調整価格日足.class))
        .collect(調整価格日足List.collector());
    return list;
  }

}
