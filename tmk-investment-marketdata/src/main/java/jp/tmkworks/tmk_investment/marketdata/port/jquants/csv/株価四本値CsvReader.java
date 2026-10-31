package jp.tmkworks.tmk_investment.marketdata.port.jquants.csv;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tools.jackson.dataformat.csv.CsvMapper;
import tools.jackson.dataformat.csv.CsvSchema;

@Component
@RequiredArgsConstructor
@Slf4j
public class 株価四本値CsvReader implements InitializingBean {
  final JQuantsCsvFileResource jQuantsCsvFileResource;
  final CsvMapper csvMapper;
  CsvSchema csvSchema;

  @Override
  public void afterPropertiesSet() throws Exception {
    csvSchema = csvMapper.schemaWithHeader();
  }

//  public 調整前株価四本値List read(取得期間 取得期間) {
//    var csvResources = jQuantsCsvFileResource.to株価四本値CsvResources(取得期間);
//    log.info("対象CSVリソース数 :{}", csvResources.size());
//
//    List<調整前株価四本値> csvList = new ArrayList<>();
//    for (var csvResource : csvResources) {
//      try (var inStream = csvResource.getInputStream()) {
//        var tmpCsvList = csvMapper.readerFor(調整前株価四本値.class)
//            .with(csvSchema)
//            .<調整前株価四本値>readValues(inStream)
//            .readAll();
//        csvList.addAll(tmpCsvList);
//      } catch (IOException e) {
//        throw new CsvReadRuntimeException(e);
//      }
//    }
//    
//    var 調整前List = new 調整前株価四本値List(csvList);
//    return 調整前List;
//  }

}
