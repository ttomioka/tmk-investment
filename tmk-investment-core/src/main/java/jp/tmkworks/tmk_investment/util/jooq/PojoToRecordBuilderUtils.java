package jp.tmkworks.tmk_investment.util.jooq;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.Strings;
import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.commons.lang3.tuple.Pair;

/**
 * SELECT用 DB行 → RecordのBuilderクラス
 */
public class PojoToRecordBuilderUtils {

  public static void copy(Object source, Object recordBuilder) {
    var sourceValueMap = pojoToMap(source);
    var builderMethodAndParameterTypeMap = builderMethodAndParameterTypeMap(recordBuilder);

    for (var entry : builderMethodAndParameterTypeMap.entrySet()) {
      var builderMethodName = entry.getKey();
      var sourceValue = sourceValueMap.getOrDefault(builderMethodName, null);
      if (sourceValue == null)
        continue;

      var parameterValue = createParameterValue(sourceValue, entry.getValue()
          .getRight());
      invokeExactMethod(recordBuilder, builderMethodName, parameterValue);
    }
  }

  /**
   * Recordの求めている型へ変換
   */
  private static Object createParameterValue(Object sourceValue, Class<?> builderMethodParameterType) {
    ConvertPattern convertPattern = ConvertPattern.of(sourceValue, builderMethodParameterType);

    if (convertPattern == ConvertPattern.STRING_TO_ENUM) {
      var enumValue = convertStringToEnum(sourceValue, builderMethodParameterType);
      return enumValue;
    }

    if (convertPattern == ConvertPattern.STRING_TO_BIGDECIMAL) {
      var baseValue = new BigDecimal(Objects.toString(sourceValue));
      var parameterValue = invokeSingleParameterConstructor(builderMethodParameterType, baseValue);
      return parameterValue;
    }

    if (convertPattern == ConvertPattern.STRING_TO_BIGDECIMAL) {
      var baseValue = OffsetDateTime.parse(Objects.toString(sourceValue));
      var parameterValue = invokeSingleParameterConstructor(builderMethodParameterType, baseValue);
      return parameterValue;
    }

    var parameterValue = invokeSingleParameterConstructor(builderMethodParameterType, sourceValue);
    return parameterValue;
  }

  enum ConvertPattern {
    STRING_TO_ENUM, STRING_TO_BIGDECIMAL, STRING_TO_OFFSETDATETIME, DIRECT;

    static ConvertPattern of(Object sourceValue, Class<?> builderMethodParameterType) {
      boolean sourceValueIsString = sourceValue instanceof String;
      boolean distinationIsEnum = builderMethodParameterType.isEnum();
      if (sourceValueIsString && distinationIsEnum)
        return STRING_TO_ENUM;

      Class<?> parameterBasedConstructorType = parameterBasedConstructorType(builderMethodParameterType);
      boolean constructorTypeIsBigDecimal = BigDecimal.class.equals(parameterBasedConstructorType);
      boolean constructorTypeIsOffsetDateTime = OffsetDateTime.class.equals(parameterBasedConstructorType);

      if (sourceValueIsString && constructorTypeIsBigDecimal)
        return STRING_TO_BIGDECIMAL;

      if (sourceValueIsString && constructorTypeIsOffsetDateTime)
        return STRING_TO_OFFSETDATETIME;

      return DIRECT;
    }

  }

  static Class<?> parameterBasedConstructorType(Class<?> builderMethodParameterType) {
    var constructors = builderMethodParameterType.getConstructors();
    var constructor = constructors[0];
    var constructorParameterTypes = constructor.getParameterTypes();
    var constructorParameterType = constructorParameterTypes[0];
    return constructorParameterType;
  }

  static Object convertStringToEnum(Object originalRawValue, Class<?> targetParameterType) {
    var factoryMethod = Arrays.stream(targetParameterType.getMethods())
        .filter(m -> Strings.CS.equals(m.getName(), "fromCode"))
        .findFirst()
        .orElse(null);

    var args = new Object[] { originalRawValue };
    var parameterTypes = new Class<?>[] { String.class };
    var enumValue = invokeStaticMethod(targetParameterType, factoryMethod.getName(), args, parameterTypes);
    return enumValue;
  }

  static Map<String, Object> pojoToMap(Object source) {
    var originalValueMap = new HashMap<String, Object>();

    try {
      var sourceClass = source.getClass();
      var fields = FieldUtils.getAllFields(sourceClass);
      for (var field : fields) {
        int mod = field.getModifiers();
        boolean staticField = Modifier.isStatic(mod);
        if (staticField)
          continue;

        var fieldName = field.getName();
        var originalValue = FieldUtils.readField(field, source, true);
        originalValueMap.put(fieldName, originalValue);
      }
    } catch (IllegalAccessException e) {
      throw new ContextedRuntimeException("PojoをMapに変換中に例外", e);
    }

    return originalValueMap;
  }

  static String[] objectMethod;
  static {
    objectMethod = Arrays.stream(new Object().getClass()
        .getMethods())
        .map(Method::getName)
        .toList()
        .toArray(String[]::new);
  }

  static Map<String, Pair<Method, Class<?>>> builderMethodAndParameterTypeMap(Object recordBuilder) {
    var builderMethodAndParameterTypeMap = new HashMap<String, Pair<Method, Class<?>>>();

    var recordBuilderClass = recordBuilder.getClass();
    var methods = recordBuilderClass.getMethods();
    for (Method method : methods) {
      var methodName = method.getName();
      if (Strings.CS.containsAny(methodName, objectMethod) || Strings.CS.equals(methodName, "build"))
        continue;

      var parameters = method.getParameters();
      var firstParameter = parameters[0];
      var firstParameterType = firstParameter.getType();

      Pair<Method, Class<?>> pair = Pair.of(method, firstParameterType);
      builderMethodAndParameterTypeMap.put(methodName, pair);
    }
    return builderMethodAndParameterTypeMap;
  }

  static void invokeExactMethod(final Object object, final String methodName, final Object... args) {
    try {
      MethodUtils.invokeExactMethod(object, methodName, args);
    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
      throw new ContextedRuntimeException(e);
    }
  }

  static Object invokeStaticMethod(final Class<?> cls, final String methodName, final Object[] args, final Class<?>[] parameterTypes) {
    try {
      return MethodUtils.invokeStaticMethod(cls, methodName, args, parameterTypes);
    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
      throw new ContextedRuntimeException(e);
    }
  }

  static <T> T invokeSingleParameterConstructor(Class<T> type, Object parameterValue) {
    var args = new Object[] { parameterValue };
    var parameterTypes = new Class<?>[] { parameterValue.getClass() };
    var constructed = invokeConstructor(type, args, parameterTypes);
    return constructed;
  }

  static <T> T invokeConstructor(final Class<T> cls, final Object[] args, final Class<?>[] parameterTypes) {
    try {
      return ConstructorUtils.invokeConstructor(cls, args, parameterTypes);
    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
      throw new ContextedRuntimeException(e);
    }
  }

}
