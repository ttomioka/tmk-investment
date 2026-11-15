package jp.tmkworks.tmk_investment;

import org.axonframework.messaging.commandhandling.gateway.CommandGateway;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jp.tmkworks.tmk_investment.core.domain.model.株式.日付.日付;
import jp.tmkworks.tmk_investment.preanalysis.cqrs.command.差分分析CommandHandler.差分分析Command;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class CommandRunners {

  @Bean
  CommandLineRunner 差分分析Command(CommandGateway commandGateway) {
    return new CommandLineRunner() {

      @Override
      public void run(String... args) throws Exception {
        var a日付 = 日付.of("2026-11-11");
        var command = new 差分分析Command(a日付);
        commandGateway.send(command);
      }
    };
  }

}
