package jp.tmkworks.tmk_investment.marketdata.cqrs.command.jquants;

import org.axonframework.messaging.commandhandling.annotation.Command;
import org.axonframework.messaging.commandhandling.annotation.CommandHandler;
import org.springframework.stereotype.Component;

import jp.tmkworks.tmk_investment.marketdata.domain.model.取得期間;
import jp.tmkworks.tmk_investment.marketdata.domain.model.jquants.JQuantsRepository;
import jp.tmkworks.tmk_investment.marketdata.domain.model.jquants.株価.株価四本値調整Converter;
import jp.tmkworks.tmk_investment.marketdata.port.jquants.csv.株価四本値CsvReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j(topic = "JQuantsCsvCommandHandler")
public class JQuantsCsvCommandHandler {
  final JQuantsRepository jQuantsRepository;
//  final 東証取引カレンダーCsvReader 東証取引カレンダーCsvReader;
//  final 上場銘柄CsvReader 上場銘柄CsvReader;
  final 株価四本値CsvReader 株価四本値CsvReader;
  final 株価四本値調整Converter 株価四本値調整Service = new 株価四本値調整Converter();

//  @Command
//  public record JQuants東証取引カレンダーCsv読込Command() {
//  }
//
//  @CommandHandler
//  public void handle(JQuants東証取引カレンダーCsv読込Command command) {
//    log.info("Command Received. {}", command);
//
//    var csvList = 東証取引カレンダーCsvReader.read();
//    jQuantsRepository.save東証取引カレンダー(csvList);
//
//    log.info("Command executed.");
//  }
//
//  @Command
//  public record JQuantsCsv上場銘柄更新Command() {
//  }
//
//  @CommandHandler
//  public void handle(JQuantsCsv上場銘柄更新Command command) {
//    var readList = 上場銘柄CsvReader.read();
//    log.info("【上場銘柄】CSVファイル 読込完了.");
//
//    jQuantsRepository.deleteAll上場銘柄();
//    log.info("【上場銘柄】DBテーブルデータ 削除完了.");
//
//    jQuantsRepository.save上場銘柄(readList);
//    log.info("【上場銘柄】CSVデータ 登録完了.");
//  }

  @Command
  public record JQuantsCsv株価四本値取込Commnad(取得期間 取得期間) {
  }

  @CommandHandler
  public void handle(JQuantsCsv株価四本値取込Commnad command) {
    var 取得期間 = command.取得期間();

    jQuantsRepository.delete株価四本値(取得期間);
    log.info("【株価四本値】DBテーブルデータ 削除完了. 対象期間: {}", 取得期間.value());

//    var 調整前List = 株価四本値CsvReader.read(取得期間);
//    銘柄コードPrefix.stream()
//        .forEach(prefix -> {
//          log.info("prefix: {} 対応開始.", prefix.getPrefix());
//
//          var selected調整前List = 調整前List.select(prefix);
//          var 調整済List = 株価四本値調整Service.convert(selected調整前List);
//          jQuantsRepository.save株価四本値(調整済List);
//
//          log.info("prefix: {} 対応終了.", prefix.getPrefix());
//        });

    log.info("【株価四本値】CSVデータ 登録完了. 対象期間: {}", 取得期間.value());
  }

}
