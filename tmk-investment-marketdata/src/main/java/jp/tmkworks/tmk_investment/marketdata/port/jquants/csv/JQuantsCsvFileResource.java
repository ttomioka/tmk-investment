package jp.tmkworks.tmk_investment.marketdata.port.jquants.csv;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.Strings;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;

import jp.tmkworks.tmk_investment.core.domain.model.common.csv.CsvFileNotFoundException;
import jp.tmkworks.tmk_investment.core.domain.model.common.csv.CsvReadRuntimeException;
import jp.tmkworks.tmk_investment.marketdata.domain.model.取得期間;
import jp.tmkworks.tmk_investment.util.spring.core.io.GZIPFileSystemResource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class JQuantsCsvFileResource {
  final Path appDataRootDir;

  public InputStreamSource to上場銘柄CsvResource() {
    final Path 上場銘柄DirPath = Paths.get("./jquants/銘柄");
    final Path 上場銘柄Dir = appDataRootDir.resolve(上場銘柄DirPath);
    var filePaths = toRegularFiles(上場銘柄Dir);

    var filteredFilePath = filePaths.stream()
        .min(Comparator.naturalOrder())
        .orElseThrow(() -> new CsvFileNotFoundException().addContextValue("ディレクトリ", 上場銘柄Dir));

    var gzipInputStream = new GZIPFileSystemResource(filteredFilePath);
    var inputStreamResource = new InputStreamResource(gzipInputStream);
    return inputStreamResource;
  }

  public List<? extends InputStreamSource> to株価四本値CsvResources(取得期間 取得期間) {
    final Path 四本値DirPath = Paths.get("./jquants/株価");
    final Path 四本値Dir = appDataRootDir.resolve(四本値DirPath);
    var filePaths = toRegularFiles(四本値Dir);

    final var yyyyMM = DateTimeFormatter.ofPattern("yyyyMM");
    var filteredFilePaths = filePaths.stream()
        .filter(path -> {
          var fileNamePath = path.getFileName();
          var fileName = fileNamePath.toString();
          var yearMonthStr = RegExUtils.replaceAll(fileName, "\\D", "");
          var yearMonth = YearMonth.parse(yearMonthStr, yyyyMM);
          boolean containsRange = 取得期間.contains(yearMonth);
          return containsRange;
        })
        .toList();

    var inputStreamSources = filteredFilePaths.stream()
        .map(GZIPFileSystemResource::new)
        .toList();
    return inputStreamSources;
  }

  public static List<Path> toRegularFiles(Path dir) {
    List<Path> csvFiles;
    try (var paths = Files.walk(dir)) {
      csvFiles = paths.filter(Files::isRegularFile)
          .filter(path -> fileNameEndsWith(path, ".csv.gz"))
          .toList();
    } catch (IOException e) {
      throw new CsvReadRuntimeException(e);
    }
    return csvFiles;
  }

  static boolean fileNameEndsWith(Path path, String suffix) {
    var fileNamePath = path.getFileName();
    var fileNameString = fileNamePath.toString();
    boolean endsWith = Strings.CS.endsWith(fileNameString, suffix);
    return endsWith;
  }

}
