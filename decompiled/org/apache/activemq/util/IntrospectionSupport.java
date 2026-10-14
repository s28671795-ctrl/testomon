/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.net.ssl.SSLServerSocket;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.util.StringArrayConverter;
import org.apache.activemq.util.StringToListOfActiveMQDestinationConverter;
import org.apache.activemq.util.TypeConversionSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class IntrospectionSupport {
    private static final Logger LOG = LoggerFactory.getLogger(IntrospectionSupport.class);

    private IntrospectionSupport() {
    }

    public static boolean getProperties(Object target, Map props, String optionPrefix) {
        Method[] methods;
        boolean rc = false;
        if (target == null) {
            throw new IllegalArgumentException("target was null.");
        }
        if (props == null) {
            throw new IllegalArgumentException("props was null.");
        }
        if (optionPrefix == null) {
            optionPrefix = "";
        }
        Class<?> clazz = target.getClass();
        for (Method method2 : methods = clazz.getMethods()) {
            String name = method2.getName();
            Class<?> type = method2.getReturnType();
            Class<?>[] params = method2.getParameterTypes();
            if (!name.startsWith("is") && !name.startsWith("get") || params.length != 0 || type == null) continue;
            try {
                String strValue;
                Object value = method2.invoke(target, new Object[0]);
                if (value == null || (strValue = IntrospectionSupport.convertToString(value, type)) == null) continue;
                name = name.startsWith("get") ? name.substring(3, 4).toLowerCase(Locale.ENGLISH) + name.substring(4) : name.substring(2, 3).toLowerCase(Locale.ENGLISH) + name.substring(3);
                props.put(optionPrefix + name, strValue);
                rc = true;
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return rc;
    }

    public static boolean setProperties(Object target, Map<String, ?> props, String optionPrefix) {
        boolean rc = false;
        if (target == null) {
            throw new IllegalArgumentException("target was null.");
        }
        if (props == null) {
            throw new IllegalArgumentException("props was null.");
        }
        Iterator<String> iter = props.keySet().iterator();
        while (iter.hasNext()) {
            String name = iter.next();
            if (!name.startsWith(optionPrefix)) continue;
            Object value = props.get(name);
            if (!IntrospectionSupport.setProperty(target, name = name.substring(optionPrefix.length()), value)) continue;
            iter.remove();
            rc = true;
        }
        return rc;
    }

    public static Map<String, Object> extractProperties(Map props, String optionPrefix) {
        if (props == null) {
            throw new IllegalArgumentException("props was null.");
        }
        HashMap<String, Object> rc = new HashMap<String, Object>(props.size());
        Iterator iter = props.keySet().iterator();
        while (iter.hasNext()) {
            String name = (String)iter.next();
            if (!name.startsWith(optionPrefix)) continue;
            Object value = props.get(name);
            name = name.substring(optionPrefix.length());
            rc.put(name, value);
            iter.remove();
        }
        return rc;
    }

    public static boolean setProperties(Object target, Map<?, ?> props) {
        return IntrospectionSupport.setProperties(target, props, true);
    }

    public static boolean setProperties(Object target, Map<?, ?> props, boolean removeIfSet) {
        boolean rc = false;
        if (target == null) {
            throw new IllegalArgumentException("target was null.");
        }
        if (props == null) {
            throw new IllegalArgumentException("props was null.");
        }
        Iterator<Map.Entry<?, ?>> iter = props.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<?, ?> entry = iter.next();
            if (!IntrospectionSupport.setProperty(target, (String)entry.getKey(), entry.getValue())) continue;
            if (removeIfSet) {
                iter.remove();
            }
            rc = true;
        }
        return rc;
    }

    public static boolean setProperty(Object target, String name, Object value) {
        try {
            Method setter2;
            Class<Object> clazz = target.getClass();
            if (target instanceof SSLServerSocket) {
                clazz = SSLServerSocket.class;
            }
            if ((setter2 = IntrospectionSupport.findSetterMethod(clazz, name)) == null) {
                return false;
            }
            if (value == null || value.getClass() == setter2.getParameterTypes()[0]) {
                setter2.invoke(target, value);
            } else {
                setter2.invoke(target, IntrospectionSupport.convert(value, setter2.getParameterTypes()[0]));
            }
            return true;
        }
        catch (Exception e) {
            LOG.error(String.format("Could not set property %s on %s", name, target), e);
            return false;
        }
    }

    private static Object convert(Object value, Class to) {
        List<ActiveMQDestination> answer;
        if (value == null) {
            if (Boolean.TYPE.isAssignableFrom(to)) {
                return Boolean.FALSE;
            }
            return null;
        }
        if (to.isAssignableFrom(value.getClass())) {
            return to.cast(value);
        }
        if (to.isAssignableFrom(String[].class)) {
            return StringArrayConverter.convertToStringArray(value);
        }
        if (value.getClass().equals(String.class) && to.equals(List.class) && (answer = StringToListOfActiveMQDestinationConverter.convertToActiveMQDestination(value)) != null) {
            return answer;
        }
        TypeConversionSupport.Converter converter = TypeConversionSupport.lookupConverter(value.getClass(), to);
        if (converter != null) {
            return converter.convert(value);
        }
        throw new IllegalArgumentException("Cannot convert from " + value.getClass() + " to " + to + " with value " + value);
    }

    public static String convertToString(Object value, Class to) {
        String answer;
        if (value == null) {
            return null;
        }
        if (value instanceof String) {
            return (String)value;
        }
        if (String[].class.isInstance(value)) {
            String[] array = (String[])value;
            return StringArrayConverter.convertToString(array);
        }
        if (List.class.isInstance(value) && (answer = StringToListOfActiveMQDestinationConverter.convertFromActiveMQDestination(value)) != null) {
            return answer;
        }
        TypeConversionSupport.Converter converter = TypeConversionSupport.lookupConverter(value.getClass(), String.class);
        if (converter != null) {
            return (String)converter.convert(value);
        }
        throw new IllegalArgumentException("Cannot convert from " + value.getClass() + " to " + to + " with value " + value);
    }

    public static Method findSetterMethod(Class clazz, String name) {
        Method[] methods;
        name = "set" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
        for (Method method2 : methods = clazz.getMethods()) {
            Class<?>[] params = method2.getParameterTypes();
            if (!method2.getName().equals(name) || params.length != 1) continue;
            return method2;
        }
        return null;
    }

    public static Method findGetterMethod(Class clazz, String name) {
        Method[] methods;
        name = "get" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
        for (Method method2 : methods = clazz.getMethods()) {
            Class<?>[] params = method2.getParameterTypes();
            if (!method2.getName().equals(name) || params.length != 0) continue;
            return method2;
        }
        return null;
    }

    public static String toString(Object target) {
        return IntrospectionSupport.toString(target, Object.class, null);
    }

    public static String toString(Object target, Class stopClass) {
        return IntrospectionSupport.toString(target, stopClass, null);
    }

    public static String toString(Object target, Class stopClass, Map<String, Object> overrideFields) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        IntrospectionSupport.addFields(target, target.getClass(), stopClass, map);
        if (overrideFields != null) {
            for (String key : overrideFields.keySet()) {
                Object value = overrideFields.get(key);
                map.put(key, value);
            }
        }
        StringBuffer buffer = new StringBuffer(IntrospectionSupport.simpleName(target.getClass()));
        buffer.append(" {");
        Set<Map.Entry<String, Object>> entrySet = map.entrySet();
        boolean first = true;
        for (Map.Entry<String, Object> entry : entrySet) {
            Object value = entry.getValue();
            String key = entry.getKey();
            if (first) {
                first = false;
            } else {
                buffer.append(", ");
            }
            buffer.append((Object)key);
            buffer.append(" = ");
            IntrospectionSupport.appendToString(buffer, key, value);
        }
        buffer.append("}");
        return buffer.toString();
    }

    protected static void appendToString(StringBuffer buffer, Object key, Object value) {
        if (value instanceof ActiveMQDestination) {
            ActiveMQDestination destination = (ActiveMQDestination)value;
            buffer.append(destination.getQualifiedName());
        } else if (key.toString().toLowerCase(Locale.ENGLISH).contains("password")) {
            buffer.append("*****");
        } else {
            buffer.append(value);
        }
    }

    public static String simpleName(Class clazz) {
        String name = clazz.getName();
        int p = name.lastIndexOf(".");
        if (p >= 0) {
            name = name.substring(p + 1);
        }
        return name;
    }

    private static void addFields(Object target, Class startClass, Class<Object> stopClass, LinkedHashMap<String, Object> map) {
        Field[] fields;
        if (startClass != stopClass) {
            IntrospectionSupport.addFields(target, startClass.getSuperclass(), stopClass, map);
        }
        for (Field field2 : fields = startClass.getDeclaredFields()) {
            if (Modifier.isStatic(field2.getModifiers()) || Modifier.isTransient(field2.getModifiers()) || Modifier.isPrivate(field2.getModifiers())) continue;
            try {
                field2.setAccessible(true);
                List<Object> o = field2.get(target);
                if (o != null && o.getClass().isArray()) {
                    try {
                        o = Arrays.asList((Object[])o);
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
                map.put(field2.getName(), o);
            }
            catch (Exception e) {
                LOG.debug("Error getting field " + field2 + " on class " + startClass + ". This exception is ignored.", e);
            }
        }
    }
}

