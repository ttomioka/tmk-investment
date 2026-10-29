package jp.tmkworks.tmk_investment.util.jooq.record;

import java.lang.reflect.RecordComponent;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

class RecordComponentInfo<T extends Record> {
  private static final ConcurrentMap<Class<? extends Record>, RecordComponentInfo<? extends Record>> CACHE = new ConcurrentHashMap<>();
  private final RecordComponent[] recordComponents;

  private RecordComponentInfo(Class<T> recordClazz) {
    this.recordComponents = recordClazz.getRecordComponents();
  }

  @SuppressWarnings("unchecked")
  static <T extends Record> RecordComponentInfo<T> of(Class<T> beanClass) {
    return (RecordComponentInfo<T>) CACHE.computeIfAbsent(beanClass, (@SuppressWarnings("unused") var key) -> new RecordComponentInfo<>(beanClass));
  }

  RecordComponent[] getRecordComponents() {
    return recordComponents;
  }
}
