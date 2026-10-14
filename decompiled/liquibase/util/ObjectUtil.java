/*
 * Decompiled with CFR 0.152.
 */
package liquibase.util;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.beans.PropertyVetoException;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.logging.Level;
import liquibase.Scope;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.statement.DatabaseFunction;
import liquibase.statement.SequenceCurrentValueFunction;
import liquibase.statement.SequenceNextValueFunction;
import liquibase.util.ISODateFormat;
import liquibase.util.StringClauses;
import liquibase.util.StringUtil;
import org.apache.commons.lang3.ObjectUtils;

public class ObjectUtil {
    private static final List<BeanIntrospector> introspectors = new ArrayList<BeanIntrospector>(Arrays.asList(new DefaultBeanIntrospector(), new FluentPropertyBeanIntrospector()));
    private static final Map<Class<?>, Method[]> methodCache = new ConcurrentHashMap();
    public static String ARGUMENT_KEY = "key";

    public static Object getProperty(Object object, String propertyName) throws UnexpectedLiquibaseException {
        Method readMethod = ObjectUtil.getReadMethod(object, propertyName);
        if (readMethod == null) {
            throw new UnexpectedLiquibaseException(String.format("Property [%s] was not found for object type [%s]", propertyName, object.getClass().getName()));
        }
        try {
            return readMethod.invoke(object, new Object[0]);
        }
        catch (IllegalAccessException | InvocationTargetException e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }

    public static Class getPropertyType(Object object, String propertyName) {
        if (object == null) {
            return null;
        }
        Method readMethod = ObjectUtil.getReadMethod(object, propertyName);
        if (readMethod == null) {
            return null;
        }
        return readMethod.getReturnType();
    }

    public static boolean hasProperty(Object object, String propertyName) {
        return ObjectUtil.hasReadProperty(object, propertyName) && ObjectUtil.hasWriteProperty(object, propertyName);
    }

    public static boolean hasReadProperty(Object object, String propertyName) {
        return ObjectUtil.getReadMethod(object, propertyName) != null;
    }

    public static boolean hasWriteProperty(Object object, String propertyName) {
        return ObjectUtil.getWriteMethod(object, propertyName) != null;
    }

    public static void setProperty(Object object, String propertyName, String propertyValue) {
        Method method2 = ObjectUtil.getWriteMethod(object, propertyName);
        if (method2 == null) {
            throw new UnexpectedLiquibaseException(String.format("Property [%s] was not found for object type [%s]", propertyName, object.getClass().getName()));
        }
        Class<?> parameterType = method2.getParameterTypes()[0];
        Object finalValue = propertyValue;
        if (parameterType.equals(Boolean.class) || parameterType.equals(Boolean.TYPE)) {
            finalValue = Boolean.valueOf(propertyValue);
        } else if (parameterType.equals(Integer.class)) {
            finalValue = Integer.valueOf(propertyValue);
        } else if (parameterType.equals(Long.class)) {
            finalValue = Long.valueOf(propertyValue);
        } else if (parameterType.equals(BigInteger.class)) {
            finalValue = new BigInteger(propertyValue);
        } else if (parameterType.equals(BigDecimal.class)) {
            finalValue = new BigDecimal(propertyValue);
        } else if (parameterType.equals(DatabaseFunction.class)) {
            finalValue = new DatabaseFunction(propertyValue);
        } else if (parameterType.equals(SequenceNextValueFunction.class)) {
            finalValue = new SequenceNextValueFunction(propertyValue);
        } else if (parameterType.equals(SequenceCurrentValueFunction.class)) {
            finalValue = new SequenceCurrentValueFunction(propertyValue);
        } else if (Enum.class.isAssignableFrom(parameterType)) {
            finalValue = Enum.valueOf(parameterType, propertyValue);
        }
        try {
            method2.invoke(object, finalValue);
        }
        catch (IllegalAccessException | InvocationTargetException e) {
            throw new UnexpectedLiquibaseException(e);
        }
        catch (IllegalArgumentException e) {
            if (finalValue != null) {
                throw new UnexpectedLiquibaseException("Cannot call " + method2 + " with value of type " + finalValue.getClass().getName());
            }
            throw new UnexpectedLiquibaseException("Cannot call " + method2 + " with a null argument");
        }
    }

    public static void setProperty(Object object, String propertyName, Object propertyValue) {
        Method method2 = ObjectUtil.getWriteMethod(object, propertyName);
        if (method2 == null) {
            throw new UnexpectedLiquibaseException(String.format("Property [%s] was not found for object type [%s]", propertyName, object.getClass().getName()));
        }
        try {
            if (propertyValue == null) {
                ObjectUtil.setProperty(object, propertyName, null);
                return;
            }
            if (!method2.getParameterTypes()[0].isAssignableFrom(propertyValue.getClass())) {
                ObjectUtil.setProperty(object, propertyName, propertyValue.toString());
                return;
            }
            method2.invoke(object, propertyValue);
        }
        catch (IllegalAccessException | InvocationTargetException e) {
            throw new UnexpectedLiquibaseException(e);
        }
        catch (IllegalArgumentException e) {
            throw new UnexpectedLiquibaseException("Cannot call " + method2 + " with value of type " + (propertyValue == null ? "null" : propertyValue.getClass().getName()));
        }
    }

    private static Method getReadMethod(Object object, String propertyName) {
        Method[] methods;
        String getMethodName = "get" + propertyName.substring(0, 1).toUpperCase(Locale.ENGLISH) + propertyName.substring(1);
        String isMethodName = "is" + propertyName.substring(0, 1).toUpperCase(Locale.ENGLISH) + propertyName.substring(1);
        for (Method method2 : methods = ObjectUtil.getMethods(object)) {
            if (!method2.getName().equals(getMethodName) && !method2.getName().equals(isMethodName) || method2.getParameterTypes().length != 0) continue;
            return method2;
        }
        return null;
    }

    private static Method getWriteMethod(Object object, String propertyName) {
        Method[] methods;
        String methodName = "set" + propertyName.substring(0, 1).toUpperCase(Locale.ENGLISH) + propertyName.substring(1);
        for (Method method2 : methods = ObjectUtil.getMethods(object)) {
            if (!method2.getName().equals(methodName) || method2.getParameterTypes().length != 1) continue;
            return method2;
        }
        return null;
    }

    private static Method[] getMethods(Object object) {
        return methodCache.computeIfAbsent(object.getClass(), k -> object.getClass().getMethods());
    }

    public static <T> T convert(Object object, Class<T> targetClass) throws IllegalArgumentException {
        return ObjectUtil.convert(object, targetClass, null);
    }

    public static <T> T convert(Object object, Class<T> targetClass, String name) throws IllegalArgumentException {
        if (object == null) {
            return null;
        }
        if (targetClass.isAssignableFrom(object.getClass())) {
            return (T)object;
        }
        try {
            if (Enum.class.isAssignableFrom(targetClass)) {
                try {
                    return Enum.valueOf(targetClass, object.toString().toUpperCase());
                }
                catch (Exception e) {
                    TreeSet<String> values2 = new TreeSet<String>();
                    for (Enum value : (Enum[])targetClass.getEnumConstants()) {
                        values2.add(value.name());
                    }
                    String exceptionMessage = StringUtil.isEmpty(name) ? "Invalid value '" + object + "'." : "The " + name.toLowerCase() + " value '" + object + "' is not valid.";
                    throw new IllegalArgumentException(exceptionMessage + " Acceptable values are '" + StringUtil.join(values2, "', '") + "'");
                }
            }
            if (Number.class.isAssignableFrom(targetClass)) {
                if (object instanceof Number) {
                    Number number = (Number)object;
                    String numberAsString = number.toString();
                    numberAsString = numberAsString.replaceFirst("\\.0+$", "");
                    if (targetClass.equals(Byte.class)) {
                        long value = Long.parseLong(numberAsString);
                        if (value < -128L || value > 127L) {
                            ObjectUtil.raiseOverflowException(number, targetClass);
                        }
                        return (T)Byte.valueOf(number.byteValue());
                    }
                    if (targetClass.equals(Short.class)) {
                        long value = Long.parseLong(numberAsString);
                        if (value < -32768L || value > 32767L) {
                            ObjectUtil.raiseOverflowException(number, targetClass);
                        }
                        return (T)Short.valueOf(number.shortValue());
                    }
                    if (targetClass.equals(Integer.class)) {
                        long value = Long.parseLong(numberAsString);
                        if (value < Integer.MIN_VALUE || value > Integer.MAX_VALUE) {
                            ObjectUtil.raiseOverflowException(number, targetClass);
                        }
                        return (T)Integer.valueOf(number.intValue());
                    }
                    if (targetClass.equals(Long.class)) {
                        return (T)Long.valueOf(numberAsString);
                    }
                    if (targetClass.equals(Float.class)) {
                        return (T)Float.valueOf(number.floatValue());
                    }
                    if (targetClass.equals(Double.class)) {
                        return (T)Double.valueOf(number.doubleValue());
                    }
                    if (targetClass.equals(BigInteger.class)) {
                        return (T)new BigInteger(numberAsString);
                    }
                    if (targetClass.equals(BigDecimal.class)) {
                        return (T)new BigDecimal(numberAsString);
                    }
                    return ObjectUtil.raiseUnknownConversionException(object, targetClass);
                }
                if (object instanceof String) {
                    String string = (String)object;
                    if (string.contains(".")) {
                        string = string.replaceFirst("\\.0+$", "");
                    }
                    if (string.equals("")) {
                        string = "0";
                    }
                    if (targetClass.equals(Byte.class)) {
                        return (T)Byte.decode(string);
                    }
                    if (targetClass.equals(Short.class)) {
                        return (T)Short.decode(string);
                    }
                    if (targetClass.equals(Integer.class)) {
                        return (T)Integer.decode(string);
                    }
                    if (targetClass.equals(Long.class)) {
                        return (T)Long.decode(string);
                    }
                    if (targetClass.equals(Float.class)) {
                        return (T)Float.valueOf(string);
                    }
                    if (targetClass.equals(Double.class)) {
                        return (T)Double.valueOf(string);
                    }
                    if (targetClass.equals(BigInteger.class)) {
                        return (T)new BigInteger(string);
                    }
                    if (targetClass.equals(BigDecimal.class)) {
                        return (T)new BigDecimal(string);
                    }
                    return ObjectUtil.raiseUnknownConversionException(object, targetClass);
                }
                return ObjectUtil.raiseUnknownConversionException(object, targetClass);
            }
            if (targetClass.isAssignableFrom(Boolean.class)) {
                String lowerCase = object.toString().toLowerCase();
                boolean isTruthy = Arrays.asList("true", "t", "1", "1.0", "yes", "y", "on").contains(lowerCase);
                boolean isFalsy = Arrays.asList("false", "f", "0", "0.0", "no", "n", "off").contains(lowerCase);
                if (!isTruthy && !isFalsy) {
                    String key = (String)((Object)Scope.getCurrentScope().get(ARGUMENT_KEY, String.class));
                    String messageString = key != null ? "\nWARNING:  The input for '" + key + "' is '" + object + "', which is not valid.  Options: 'true' or 'false'." : "\nWARNING:  The input '" + object + "' is not valid.  Options: 'true' or 'false'.";
                    throw new IllegalArgumentException(messageString);
                }
                if (isTruthy) {
                    return (T)Boolean.TRUE;
                }
                return (T)Boolean.FALSE;
            }
            if (targetClass.isAssignableFrom(String.class)) {
                return (T)object.toString();
            }
            if (targetClass.isAssignableFrom(List.class)) {
                if (object instanceof List) {
                    return (T)object;
                }
                if (object instanceof Collection) {
                    return (T)new ArrayList((Collection)object);
                }
                if (object instanceof Object[]) {
                    return (T)new ArrayList<Object>(Arrays.asList((Object[])object));
                }
                return (T)object;
            }
            if (targetClass.isAssignableFrom(StringClauses.class)) {
                return (T)new StringClauses().append(object.toString());
            }
            if (targetClass.isAssignableFrom(Class.class)) {
                try {
                    return (T)Class.forName(object.toString());
                }
                catch (ClassNotFoundException e) {
                    throw new IllegalArgumentException(e);
                }
            }
            if (targetClass.isAssignableFrom(File.class)) {
                return (T)new File(object.toString());
            }
            if (targetClass.equals(UUID.class)) {
                return (T)UUID.fromString(object.toString());
            }
            if (Date.class.isAssignableFrom(targetClass)) {
                return (T)new ISODateFormat().parse(object.toString());
            }
            if (Level.class.isAssignableFrom(targetClass)) {
                return (T)Level.parse(object.toString().toUpperCase());
            }
            return (T)object;
        }
        catch (NumberFormatException | ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static <T> T raiseUnknownConversionException(Object object, Class<T> targetClass) {
        throw new IllegalArgumentException("Could not convert '" + object + "' of type " + object.getClass().getName() + " to unknown target class " + targetClass.getName());
    }

    private static void raiseOverflowException(Number number, Class targetClass) {
        throw new IllegalArgumentException("Could not convert '" + number + "' of type " + number.getClass().getName() + " to target class " + targetClass.getName() + ": overflow");
    }

    @Deprecated
    public static <T> T defaultIfNull(T value, T defaultValue) {
        return ObjectUtils.defaultIfNull(value, defaultValue);
    }

    public static <T, U> T defaultIfNull(U object, T defaultValue, Function<U, T> getter2) {
        if (object == null) {
            return defaultValue;
        }
        return getter2.apply(object);
    }

    public static PropertyDescriptor[] getDescriptors(Class<?> targetClass) throws IntrospectionException {
        IntrospectionContext context = new IntrospectionContext(targetClass);
        for (BeanIntrospector introspector : introspectors) {
            introspector.introspect(context);
        }
        return context.getDescriptors();
    }

    private static class IntrospectionContext {
        private final Class<?> targetClass;
        private final Map<String, PropertyDescriptor> descriptors = new ConcurrentHashMap<String, PropertyDescriptor>();

        public IntrospectionContext(Class<?> targetClass) {
            if (targetClass == null) {
                throw new NullPointerException();
            }
            this.targetClass = targetClass;
        }

        public void addDescriptor(PropertyDescriptor descriptor) {
            this.descriptors.put(descriptor.getName(), descriptor);
        }

        public void addDescriptors(PropertyDescriptor[] descriptors) {
            for (PropertyDescriptor descriptor : descriptors) {
                this.addDescriptor(descriptor);
            }
        }

        public PropertyDescriptor getDescriptor(String name) {
            return this.descriptors.get(name);
        }

        public PropertyDescriptor[] getDescriptors() {
            return this.descriptors.values().toArray(new PropertyDescriptor[0]);
        }

        public Class<?> getTargetClass() {
            return this.targetClass;
        }
    }

    private static interface BeanIntrospector {
        public void introspect(IntrospectionContext var1) throws IntrospectionException;
    }

    private static class DefaultBeanIntrospector
    implements BeanIntrospector {
        private DefaultBeanIntrospector() {
        }

        @Override
        public void introspect(IntrospectionContext context) throws IntrospectionException {
            PropertyDescriptor[] descriptors = Introspector.getBeanInfo(context.getTargetClass()).getPropertyDescriptors();
            if (descriptors != null) {
                context.addDescriptors(descriptors);
            }
        }
    }

    private static class FluentPropertyBeanIntrospector
    implements BeanIntrospector {
        private FluentPropertyBeanIntrospector() {
        }

        @Override
        public void introspect(IntrospectionContext context) throws IntrospectionException {
            block2: for (Method method2 : context.getTargetClass().getMethods()) {
                try {
                    String propertyName;
                    Class<?>[] argTypes = method2.getParameterTypes();
                    int argCount = argTypes.length;
                    if (argCount != 1 || !method2.getName().startsWith("set") || "class".equals(propertyName = Introspector.decapitalize(method2.getName().substring(3)))) continue;
                    PropertyDescriptor pd = context.getDescriptor(propertyName);
                    boolean setWriteMethod = false;
                    if (pd == null) {
                        pd = new PropertyDescriptor(propertyName, null, method2);
                        context.addDescriptor(pd);
                        setWriteMethod = true;
                    } else if (pd.getWriteMethod() == null && pd.getReadMethod() != null && pd.getReadMethod().getReturnType() == argTypes[0]) {
                        pd.setWriteMethod(method2);
                        setWriteMethod = true;
                    }
                    if (!setWriteMethod) continue;
                    for (Class<?> type : method2.getExceptionTypes()) {
                        if (type != PropertyVetoException.class) continue;
                        pd.setConstrained(true);
                        continue block2;
                    }
                }
                catch (IntrospectionException introspectionException) {
                    // empty catch block
                }
            }
        }
    }
}

