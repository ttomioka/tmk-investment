package jp.tmkworks.tmk_investment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PreanalysisApplication {

	public static void main(String[] args) {
	  System.setProperty("org.jooq.no-logo", "true");
		SpringApplication.run(PreanalysisApplication.class, args);
	}

}
