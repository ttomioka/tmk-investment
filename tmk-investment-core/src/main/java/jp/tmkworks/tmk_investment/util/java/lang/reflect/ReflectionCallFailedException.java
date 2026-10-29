package jp.tmkworks.tmk_investment.util.java.lang.reflect;

import org.apache.commons.lang3.exception.ContextedRuntimeException;

/** 何らかのリフレクション操作による例外 */
public class ReflectionCallFailedException extends ContextedRuntimeException {

  public ReflectionCallFailedException(final Throwable cause) {
    super(cause);
  }

  public ReflectionCallFailedException(final String message, final Throwable cause) {
    super(message, cause);
  }

}
