package jp.tmkworks.tmk_investment.util.jooq.record;

import java.lang.reflect.Method;

import org.apache.commons.lang3.exception.ContextedRuntimeException;

import jp.tmkworks.tmk_investment.util.java.lang.reflect.ReflectionUtils;

class SourceObjectWrapper {
  final Object sourceObject;
  final BeanClassInfo beanClassInfo;

  private SourceObjectWrapper(Object sourceObject, BeanClassInfo beanClassInfo) {
    this.sourceObject = sourceObject;
    this.beanClassInfo = beanClassInfo;
  }

  static SourceObjectWrapper of(Object sourceObject) {
    var beanInfo = BeanClassInfo.of(sourceObject.getClass());
    var wrapper = new SourceObjectWrapper(sourceObject, beanInfo);
    return wrapper;
  }

  class ReadMethod {
    final Object sourceObject;
    final Method method;

    private ReadMethod(Object sourceObject, Method method) {
      super();
      this.sourceObject = sourceObject;
      this.method = method;
    }

    public Object invoke() {
      return ReflectionUtils.invokeExactMethod(sourceObject, method);
    }

  }

  ReadMethod getReadMethod(String argName) {
    var propertyDescriptor = beanClassInfo.getPropertyDescriptor(argName);
    if(propertyDescriptor == null) {
      throw new ContextedRuntimeException("PropertyDescriptorが取得できなかったため例外")
      .addContextValue("argName", argName);
    }
    
    var method = propertyDescriptor.getReadMethod();
    var readMethod = new ReadMethod(sourceObject, method);
    return readMethod;
  }

}
