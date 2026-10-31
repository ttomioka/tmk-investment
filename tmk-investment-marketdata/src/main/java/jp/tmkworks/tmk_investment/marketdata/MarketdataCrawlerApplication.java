package jp.tmkworks.tmk_investment.marketdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import jp.tmkworks.tmk_investment.marketdata.config.AxonConfig;

@SpringBootApplication
@Import(AxonConfig.class)
public class MarketdataCrawlerApplication {

	public static void main(String[] args) {
	  System.setProperty("org.jooq.no-logo", "true");
		SpringApplication.run(MarketdataCrawlerApplication.class, args);
	}

}
