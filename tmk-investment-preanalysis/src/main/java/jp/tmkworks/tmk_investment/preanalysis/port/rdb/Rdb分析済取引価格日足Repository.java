package jp.tmkworks.tmk_investment.preanalysis.port.rdb;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import jp.tmkworks.tmk_investment.core.domain.model.株式.日付.日付;
import jp.tmkworks.tmk_investment.core.domain.model.株式.足.日足.分析済日足List;
import jp.tmkworks.tmk_investment.core.domain.model.株式.足.日足.分析済日足Repository;
import jp.tmkworks.tmk_investment.core.port.rdb.Tables;
import jp.tmkworks.tmk_investment.core.port.rdb.tables.pojos.分析済取引価格四本値;
import jp.tmkworks.tmk_investment.core.port.rdb.tables.pojos.分析済調整価格四本値;
import jp.tmkworks.tmk_investment.util.jooq.JooqUtils;
import jp.tmkworks.tmk_investment.util.jooq.bean.BeanUtils2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class Rdb分析済取引価格日足Repository implements 分析済日足Repository {
  final DSLContext jooq;

  @Override
  public void delete分析済取引価格(日付 日付) {
    var table = Tables.分析済取引価格四本値;
    jooq.deleteFrom(table)
        .where(table.日付.eq(日付.value()))
        .execute();
  }

  @Override
  public void save分析済取引価格(分析済日足List list) {
    var table = Tables.分析済取引価格四本値;
    var newRecords = list.stream()
        .map(分析済 -> {
          var pojo = new 分析済取引価格四本値();
          BeanUtils2.copyProperties(分析済, pojo);
          BeanUtils2.copyProperties(分析済.日足(), pojo);
          BeanUtils2.copyProperties(分析済.高値履歴(), pojo);
          BeanUtils2.copyProperties(分析済.安値履歴(), pojo);
          BeanUtils2.copyProperties(分析済.volatility(), pojo);
          BeanUtils2.copyProperties(分析済.高値Breakout(), pojo);
          var newRecord = jooq.newRecord(table, pojo);
          return newRecord;
        })
        .toList();
    JooqUtils.partitionedBulkInsert(jooq, table, newRecords);
  }

  @Override
  public void delete分析済調整価格(日付 日付) {
    var table = Tables.分析済調整価格四本値;
    jooq.deleteFrom(table)
        .where(table.日付.eq(日付.value()))
        .execute();
  }

  @Override
  public void save分析済調整価格(分析済日足List list) {
    var table = Tables.分析済調整価格四本値;
    var newRecords = list.stream()
        .map(分析済 -> {
          var pojo = new 分析済調整価格四本値();
          BeanUtils2.copyProperties(分析済, pojo);
          BeanUtils2.copyProperties(分析済.日足(), pojo);
          BeanUtils2.copyProperties(分析済.高値履歴(), pojo);
          BeanUtils2.copyProperties(分析済.安値履歴(), pojo);
          BeanUtils2.copyProperties(分析済.volatility(), pojo);
          BeanUtils2.copyProperties(分析済.高値Breakout(), pojo);
          var newRecord = jooq.newRecord(table, pojo);
          return newRecord;
        })
        .toList();
    JooqUtils.partitionedBulkInsert(jooq, table, newRecords);
  }

}
