package jp.tmkworks.tmk_investment.marketdata.config;

import java.nio.file.Paths;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jp.tmkworks.tmk_investment.marketdata.port.jquants.csv.JQuantsCsvFileResource;
import tools.jackson.dataformat.csv.CsvMapper;

@Configuration
public class CsvConfig {

  @Bean
  CsvMapper cavMapper() {
    var cavMapper = CsvMapper.builder()
        .build();
    return cavMapper;
  }

  @Bean
  JQuantsCsvFileResource jQuantsCsvFileResource() {
    var rootDirectory = Paths.get("C:/workspace/data/tmk-investment");
    var fileResource = new JQuantsCsvFileResource(rootDirectory);
    return fileResource;
  }

}
