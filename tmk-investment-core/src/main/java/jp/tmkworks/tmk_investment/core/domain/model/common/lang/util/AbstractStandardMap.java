package jp.tmkworks.tmk_investment.core.domain.model.common.lang.util;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractStandardMap<K, V> {
  protected final Map<K, V> map = new ConcurrentHashMap<>();

  protected AbstractStandardMap(Map<K, V> map) {
    this.map.putAll(map);
  }

  public V getOrThrow(K key) {
    boolean containsKey = map.containsKey(key);
    if (containsKey)
      return map.get(key);

    throw new NoSuchElementException("key: " + key);
  }

}
