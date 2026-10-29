package jp.tmkworks.tmk_investment.util.jooq.record;

public class BeanToRecordUtils {

  public static <T extends Record> T copyCreate(Object source, Class<T> recordClazz) {
    var recordMetadata = RecordMetadata.of(recordClazz);
    var copyCreated = recordMetadata.copyCreate(source);
    return copyCreated;
  }

}
