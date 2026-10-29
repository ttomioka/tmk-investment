package jp.tmkworks.tmk_investment.util.java.lang.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.apache.commons.lang3.reflect.MethodUtils;

public class ReflectionUtils {

  public static <T> T invokeConstructor(final Class<T> cls, final Object... args) {
    try {
      return ConstructorUtils.invokeConstructor(cls, args);
    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
      throw new ReflectionCallFailedException("invokeConstructor call failed.", e);
    }
  }

  public static Object invokeExactMethod(final Object object, Method method, final Object... args) {
    Object value;
    try {
      final String methodName = method.getName();
      value = MethodUtils.invokeExactMethod(object, methodName, args);
    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
      throw new ReflectionCallFailedException("invokeExactMethod call failed.", e);
    }
    return value;
  }

}
