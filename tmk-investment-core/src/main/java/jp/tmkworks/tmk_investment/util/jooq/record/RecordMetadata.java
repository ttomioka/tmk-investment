package jp.tmkworks.tmk_investment.util.jooq.record;

import java.lang.reflect.RecordComponent;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang3.function.Failable;

import jp.tmkworks.tmk_investment.util.java.lang.reflect.ReflectionUtils;

class RecordMetadata<T extends Record> {
  private static final ConcurrentMap<Class<? extends Record>, RecordMetadata<? extends Record>> CACHE = new ConcurrentHashMap<>();
  private final Class<T> recordClazz;
  private final RecordComponent[] recordComponents;

  private RecordMetadata(Class<T> recordClazz) {
    this.recordClazz = recordClazz;
    this.recordComponents = recordClazz.getRecordComponents();
  }

  @SuppressWarnings("unchecked")
  static <T extends Record> RecordMetadata<T> of(Class<T> beanClass) {
    return (RecordMetadata<T>) CACHE.computeIfAbsent(beanClass, (@SuppressWarnings("unused") var key) -> new RecordMetadata<>(beanClass));
  }

  T copyCreate(Object source) {
    var sourceObject = SourceObjectWrapper.of(source);
    var converter = new ArgumentValueConverter(sourceObject);
    var args = Arrays.stream(recordComponents)
        .parallel()
        .map(Failable.asFunction(converter))
        .toArray();
    var newRecord = ReflectionUtils.invokeConstructor(recordClazz, args);
    return newRecord;
  }

}
