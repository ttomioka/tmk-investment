package jp.tmkworks.tmk_investment.util.jooq.record;

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

final class BeanClassInfo {
  private static final ConcurrentMap<Class<?>, BeanClassInfo> CACHE = new ConcurrentHashMap<>();
  private final Map<String, PropertyDescriptor> properties;

  private BeanClassInfo(Class<?> beanClass) {
    this.properties = Arrays.stream(BeanUtils.getPropertyDescriptors(beanClass))
        .collect(Collectors.toUnmodifiableMap(PropertyDescriptor::getName, Function.identity()));
  }

  static BeanClassInfo of(Class<?> beanClass) {
    return CACHE.computeIfAbsent(beanClass, BeanClassInfo::new);
  }

  PropertyDescriptor getPropertyDescriptor(String name) {
    String uncapitalize = StringUtils.uncapitalize(name);
    return properties.get(uncapitalize);
  }
}