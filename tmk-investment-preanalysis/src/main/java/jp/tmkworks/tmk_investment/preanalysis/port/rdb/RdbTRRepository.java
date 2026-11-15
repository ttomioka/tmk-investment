package jp.tmkworks.tmk_investment.preanalysis.port.rdb;

import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import jp.tmkworks.tmk_investment.core.domain.model.株式.日付.日付;
import jp.tmkworks.tmk_investment.core.domain.model.株式.株価.分析.volatility.ATR;
import jp.tmkworks.tmk_investment.core.domain.model.株式.銘柄.銘柄コード;
import jp.tmkworks.tmk_investment.core.port.rdb.Tables;
import jp.tmkworks.tmk_investment.core.port.rdb.tables.pojos.分析済取引価格四本値;
import jp.tmkworks.tmk_investment.core.port.rdb.tables.pojos.分析済調整価格四本値;
import jp.tmkworks.tmk_investment.preanalysis.domain.model.株式.株価.分析.volatility.TRRepository;
import jp.tmkworks.tmk_investment.preanalysis.domain.model.株式.株価.分析.volatility.銘柄ATRMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class RdbTRRepository implements TRRepository {
  final DSLContext jooq;

  @Override
  public 銘柄ATRMap find取引価格TRMap(日付 日付) {
    var table = Tables.分析済取引価格四本値;
    var map = jooq.selectFrom(table)
        .where(table.日付.eq(日付.value()))
        .fetchInto(分析済取引価格四本値.class)
        .stream()
        .map(分析済 -> {
          var a銘柄コード = 銘柄コード.of(分析済.get銘柄コード());
          var atr = new ATR(分析済.getAtr());
          return Pair.of(a銘柄コード, atr);
        })
        .collect(Collectors.toConcurrentMap(Pair::getKey, Pair::getValue));

    var 銘柄ATRMap = new 銘柄ATRMap(map);
    return 銘柄ATRMap;
  }

  @Override
  public 銘柄ATRMap find調整価格TRMap(日付 日付) {
    var table = Tables.分析済調整価格四本値;
    var map = jooq.selectFrom(table)
        .where(table.日付.eq(日付.value()))
        .fetchInto(分析済調整価格四本値.class)
        .stream()
        .map(分析済 -> {
          var a銘柄コード = 銘柄コード.of(分析済.get銘柄コード());
          var atr = new ATR(分析済.getAtr());
          return Pair.of(a銘柄コード, atr);
        })
        .collect(Collectors.toConcurrentMap(Pair::getKey, Pair::getValue));

    var 銘柄ATRMap = new 銘柄ATRMap(map);
    return 銘柄ATRMap;
  }

}
