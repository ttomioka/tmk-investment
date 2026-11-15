package jp.tmkworks.tmk_investment.core.port.rdb;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import jp.tmkworks.tmk_investment.core.domain.model.株式.日付.東証取引カレンダーRepository;
import jp.tmkworks.tmk_investment.core.domain.model.株式.日付.東証取引日;
import jp.tmkworks.tmk_investment.core.domain.model.株式.日付.東証取引日List;
import jp.tmkworks.tmk_investment.core.port.rdb.tables.pojos.Jq東証取引カレンダー;
import jp.tmkworks.tmk_investment.util.jooq.record.BeanToRecordUtils;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class Rdb東証取引カレンダーRepository implements 東証取引カレンダーRepository {
  final DSLContext jooq;

  @Override
  public 東証取引日List all() {
    var table = Tables.JQ_東証取引カレンダー;
    var selectFrom = jooq.selectFrom(table);

    var found = selectFrom.fetchInto(Jq東証取引カレンダー.class)
        .stream()
        .map(pojo -> BeanToRecordUtils.copyCreate(pojo, 東証取引日.class))
        .collect(東証取引日List.collector());
    return found;
  }

}
