package jp.tmkworks.tmk_investment.preanalysis.cqrs.command;

import org.axonframework.messaging.commandhandling.annotation.CommandHandler;
import org.axonframework.messaging.eventhandling.gateway.EventAppender;
import org.springframework.stereotype.Component;

import jp.tmkworks.tmk_investment.core.domain.model.株式.日付.日付;
import jp.tmkworks.tmk_investment.core.domain.model.株式.日付.東証取引カレンダーRepository;
import jp.tmkworks.tmk_investment.core.domain.model.株式.足.日足.分析済日足List;
import jp.tmkworks.tmk_investment.core.domain.model.株式.足.日足.分析済日足Repository;
import jp.tmkworks.tmk_investment.core.domain.model.株式.銘柄.銘柄Repository;
import jp.tmkworks.tmk_investment.preanalysis.domain.model.株式.株価.分析.volatility.TRRepository;
import jp.tmkworks.tmk_investment.preanalysis.domain.model.株式.足.日足.日足Repository;
import jp.tmkworks.tmk_investment.preanalysis.domain.model.株式.足.日足.銘柄日足時系列;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class 差分分析CommandHandler {
  final 銘柄Repository 銘柄Repo;
  final 東証取引カレンダーRepository 東証取引カレンダーRepo;
  final 日足Repository 日足Repo;
  final TRRepository trRepo;
  final 分析済日足Repository 分析済日足Repo;

  public record 差分分析Command(日付 分析対象日) {
  }

  @CommandHandler
  public void handle(差分分析Command command, EventAppender appender) {
    final var all銘柄List = 銘柄Repo.all();
    final var 分析対象銘柄List = all銘柄List.select事前分析対象銘柄();
    final var all東証取引カレンダーList = 東証取引カレンダーRepo.all();

    var 分析対象日 = command.分析対象日();
    var 分析対象日前日 = all東証取引カレンダーList.select前営業日(分析対象日);
    var 分析対象日55日前 = all東証取引カレンダーList.select55日前営業日(分析対象日);
    log.info("準備完了");
    
    log.info("取引価格処理 start.");
    var 取引価格ATRMap = trRepo.find取引価格TRMap(分析対象日前日);
    var 取引価格日足List = 日足Repo.find取引価格四本値(分析対象日, 分析対象日55日前);
    var a分析済取引価格日足List = 分析対象銘柄List
        .stream()
        .map(銘柄 -> {
          var 銘柄コード = 銘柄.銘柄コード();
          var atr = 取引価格ATRMap.getOrThrow(銘柄コード);
          var a銘柄日足時系列 = 銘柄日足時系列.of(取引価格日足List, 銘柄コード);
          var 分析済取引価格日足 = a銘柄日足時系列.分析する(分析対象日, 分析対象日前日, atr);
          return 分析済取引価格日足;
        })
        .collect(分析済日足List.collector());
    分析済日足Repo.delete分析済取引価格(分析対象日);
    分析済日足Repo.save分析済取引価格(a分析済取引価格日足List);
    log.info("取引価格処理 end.");
    
    
    log.info("調整価格処理 start.");
    var 調整価格ATRMap = trRepo.find調整価格TRMap(分析対象日前日);
    var 調整価格日足List = 日足Repo.find調整価格四本値(分析対象日, 分析対象日55日前);
    var 分析済調整価格日足List = 分析対象銘柄List
        .stream()
        .map(銘柄 -> {
          var 銘柄コード = 銘柄.銘柄コード();
          var atr = 調整価格ATRMap.getOrThrow(銘柄コード);
          var a銘柄日足時系列 = 銘柄日足時系列.of(調整価格日足List, 銘柄コード);
          var 分析済取引価格日足 = a銘柄日足時系列.分析する(分析対象日, 分析対象日前日, atr);
          return 分析済取引価格日足;
        })
        .collect(分析済日足List.collector());
    分析済日足Repo.delete分析済調整価格(分析対象日);
    分析済日足Repo.save分析済調整価格(分析済調整価格日足List);
    log.info("調整価格処理 end.");
  }

}
