package jp.tmkworks.tmk_investment.marketdata.config;

import org.axonframework.messaging.commandhandling.CommandMessage;
import org.axonframework.messaging.core.interception.LoggingInterceptor;
import org.axonframework.messaging.eventhandling.processing.streaming.token.store.TokenStore;
import org.axonframework.messaging.eventhandling.processing.streaming.token.store.inmemory.InMemoryTokenStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig {

  @Bean
  TokenStore tokenStore() {
      return new InMemoryTokenStore();
  }
  
  @Bean
  LoggingInterceptor<CommandMessage> LoggingInterceptor() {
    return new LoggingInterceptor<CommandMessage>();
  }
  
}
