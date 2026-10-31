package jp.tmkworks.tmk_investment.marketdata;

import org.axonframework.messaging.commandhandling.gateway.CommandGateway;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jp.tmkworks.tmk_investment.marketdata.cqrs.command.jquants.JQuantsApiCommandHandler.JQuants株価四本値取得Command;
import jp.tmkworks.tmk_investment.marketdata.cqrs.command.jquants.JQuantsCsvCommandHandler.JQuantsCsv株価四本値取込Commnad;
import jp.tmkworks.tmk_investment.marketdata.domain.model.取得期間;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class CommandRunners {
  
  CommandLineRunner Csv株価四本値更新CommandRunner(CommandGateway commandGateway) {
    return new CommandLineRunner() {

      @Override
      public void run(String... args) throws Exception {
        var a取得期間 = 取得期間.of("2026-09-01", "2026-09-30");
        var command = new JQuantsCsv株価四本値取込Commnad(a取得期間); 
        commandGateway.send(command);
      }
    };
  }
  
  @Bean
  CommandLineRunner Api株価四本値更新CommandRunner(CommandGateway commandGateway) {
    return new CommandLineRunner() {

      @Override
      public void run(String... args) throws Exception {
        var a取得対象日 = 取得期間.of("2026-10-30", "2026-10-30");
        var command = new JQuants株価四本値取得Command(a取得対象日);
        commandGateway.sendAndWait(command);
      }
    };
  }

}
