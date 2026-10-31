package jp.tmkworks.tmk_investment.marketdata.port.rdb;

import static jp.tmkworks.tmk_investment.core.port.rdb.Tables.JQ_株価四本値;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import jp.tmkworks.tmk_investment.core.port.rdb.tables.pojos.Jq株価四本値;
import jp.tmkworks.tmk_investment.marketdata.domain.model.取得期間;
import jp.tmkworks.tmk_investment.marketdata.domain.model.jquants.JQuantsRepository;
import jp.tmkworks.tmk_investment.marketdata.domain.model.jquants.株価.Jq日足四本値List;
import jp.tmkworks.tmk_investment.util.jooq.JooqUtils;
import jp.tmkworks.tmk_investment.util.jooq.bean.BeanUtils2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class RdbJQuantsRepository implements JQuantsRepository {
  final DSLContext jooq;

  @Override
  public void delete株価四本値(取得期間 取得期間) {
    var range = 取得期間.value();
    var from = range.getMinimum();
    var to = range.getMaximum();

    var table = JQ_株価四本値;
    var deleteFrom = jooq.deleteFrom(table);
    var deleteFromWhere = deleteFrom.where(table.日付.between(from, to));
    deleteFromWhere.execute();
  }

  @Override
  public void save株価四本値(Jq日足四本値List list) {
    var table = JQ_株価四本値;
    var newRecords = list.stream()
        .map(source -> {
          var pojo = new Jq株価四本値();
          BeanUtils2.copyProperties(source, pojo);
          var newRecord = jooq.newRecord(table, pojo);
          return newRecord;
        })
        .toList();
    JooqUtils.partitionedBulkInsert(jooq, table, newRecords);
  }

}
