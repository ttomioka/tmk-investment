package jp.tmkworks.tmk_investment.marketdata.cqrs.command.jquants;

import org.axonframework.messaging.commandhandling.annotation.Command;
import org.axonframework.messaging.commandhandling.annotation.CommandHandler;
import org.springframework.stereotype.Component;

import jp.tmkworks.tmk_investment.marketdata.domain.model.取得期間;
import jp.tmkworks.tmk_investment.marketdata.domain.model.jquants.JQuantsApiClient;
import jp.tmkworks.tmk_investment.marketdata.domain.model.jquants.JQuantsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JQuantsApiCommandHandler {
  final JQuantsRepository repository;
  final JQuantsApiClient apiClient;

  @Command
  public record JQuants株価四本値取得Command(取得期間 取得期間) {
  }

  @CommandHandler
  public void handle(JQuants株価四本値取得Command command) {
    var 取得期間 = command.取得期間();

    repository.delete株価四本値(取得期間);
    log.info("【株価四本値】DBテーブルデータ 削除完了. 対象期間: {}", 取得期間.value());

    var jq日足四本値List = apiClient.getEquitiesBarDaily(取得期間);
    log.info("【株価四本値】APIデータ 取得完了.");

    repository.save株価四本値(jq日足四本値List);
    log.info("【株価四本値】APIデータ 登録完了.");
  }

}
