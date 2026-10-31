package jp.tmkworks.tmk_investment.core.domain.model.common.csv;

import org.apache.commons.lang3.exception.ContextedRuntimeException;

public class CsvReadRuntimeException extends ContextedRuntimeException {

  public CsvReadRuntimeException(final Throwable cause) {
    super(cause);
  }

}
