package jp.tmkworks.tmk_investment.core.domain.model.common.lang.util;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

public abstract class AbstractStandardList<T> {
  protected final List<T> list = new CopyOnWriteArrayList<>();

  protected AbstractStandardList(List<T> list) {
    this.list.addAll(list);
  }
  
  public List<T> toList(){
    return new CopyOnWriteArrayList<>(list);
  }

  public int size() {
    return list.size();
  }

  public Stream<T> stream() {
    return list.stream();
  }

}
