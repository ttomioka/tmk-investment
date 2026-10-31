package jp.tmkworks.tmk_investment.core.domain.model.common.csv;

import org.apache.commons.lang3.exception.ContextedRuntimeException;

public class CsvFileNotFoundException extends ContextedRuntimeException {
  
  public CsvFileNotFoundException() {
    super("CSVファイルが見つからないため例外");
  }

  public CsvFileNotFoundException(final Throwable cause) {
    super(cause);
  }

}
