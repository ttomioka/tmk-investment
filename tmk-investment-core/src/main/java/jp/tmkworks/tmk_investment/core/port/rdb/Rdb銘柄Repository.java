package jp.tmkworks.tmk_investment.core.port.rdb;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import jp.tmkworks.tmk_investment.core.domain.model.株式.銘柄.銘柄;
import jp.tmkworks.tmk_investment.core.domain.model.株式.銘柄.銘柄List;
import jp.tmkworks.tmk_investment.core.domain.model.株式.銘柄.銘柄Repository;
import jp.tmkworks.tmk_investment.core.port.rdb.tables.pojos.V銘柄;
import jp.tmkworks.tmk_investment.util.jooq.record.BeanToRecordUtils;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class Rdb銘柄Repository implements 銘柄Repository {
  final DSLContext jooq;

  @Override
  public 銘柄List all() {
    var table = Tables.V_銘柄;
    var selectFrom = jooq.selectFrom(table);

    var list = selectFrom.fetchInto(V銘柄.class)
        .stream()
        .map(pojo -> BeanToRecordUtils.copyCreate(pojo, 銘柄.class))
        .collect(銘柄List.collector());
    return list;
  }

}
