/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.props;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.ldaptive.props.PropertyInvoker;
import org.ldaptive.props.PropertyValueParser;

public abstract class AbstractPropertyInvoker
implements PropertyInvoker {
    private static final Map<String, Map<String, Method[]>> PROPERTIES_CACHE = new HashMap<String, Map<String, Method[]>>();
    private Class<?> clazz;
    private Map<String, Method[]> properties;

    protected synchronized void initialize(Class<?> c) {
        String cacheKey = c.getName();
        if (PROPERTIES_CACHE.containsKey(cacheKey)) {
            this.properties = PROPERTIES_CACHE.get(cacheKey);
        } else {
            Method[] m4;
            String pName;
            String mName;
            this.properties = new HashMap<String, Method[]>();
            for (Method method3 : c.getMethods()) {
                if (method3.isBridge()) continue;
                if (method3.getName().startsWith("get") && method3.getParameterTypes().length == 0) {
                    mName = method3.getName().substring(3);
                    pName = mName.substring(0, 1).toLowerCase() + mName.substring(1);
                    if (this.properties.containsKey(pName)) {
                        m4 = this.properties.get(pName);
                        m4[0] = method3;
                        continue;
                    }
                    this.properties.put(pName, new Method[]{method3, null});
                    continue;
                }
                if (method3.getName().startsWith("is") && method3.getParameterTypes().length == 0) {
                    mName = method3.getName().substring(2);
                    pName = mName.substring(0, 1).toLowerCase() + mName.substring(1);
                    if (this.properties.containsKey(pName)) {
                        m4 = this.properties.get(pName);
                        if (m4[0] != null) continue;
                        m4[0] = method3;
                        continue;
                    }
                    this.properties.put(pName, new Method[]{method3, null});
                    continue;
                }
                if (!"initialize".equals(method3.getName()) || method3.getParameterTypes().length != 0) continue;
                String pName2 = method3.getName();
                this.properties.put(pName2, new Method[]{method3, method3});
            }
            for (Method method3 : c.getMethods()) {
                if (method3.isBridge() || !method3.getName().startsWith("set") || method3.getParameterTypes().length != 1 || !this.properties.containsKey(pName = (mName = method3.getName().substring(3)).substring(0, 1).toLowerCase() + mName.substring(1)) || (m4 = this.properties.get(pName))[0] == null || !method3.getParameterTypes()[0].equals(m4[0].getReturnType())) continue;
                m4[1] = method3;
            }
            this.properties.values().removeIf(method2 -> method2[0] == null || method2[1] == null);
            PROPERTIES_CACHE.put(cacheKey, Collections.unmodifiableMap(this.properties));
        }
        this.clazz = c;
    }

    @Override
    public void setProperty(Object object, String name, String value) {
        Method setter2;
        Method getter2;
        if (!this.clazz.isInstance(object)) {
            throw new IllegalArgumentException("Illegal attempt to set property for class " + this.clazz.getName() + " on object of type " + object.getClass().getName());
        }
        Method method2 = getter2 = this.properties.get(name) != null ? this.properties.get(name)[0] : null;
        if (getter2 == null) {
            throw new IllegalArgumentException("No getter method found for " + name + " on object " + this.clazz.getName());
        }
        Method method3 = setter2 = this.properties.get(name) != null ? this.properties.get(name)[1] : null;
        if (setter2 == null) {
            throw new IllegalArgumentException("No setter method found for " + name + " on object " + this.clazz.getName());
        }
        AbstractPropertyInvoker.invokeMethod(setter2, object, this.convertValue(getter2.getReturnType(), value));
    }

    protected abstract Object convertValue(Class<?> var1, String var2);

    @Override
    public boolean hasProperty(String name) {
        return this.properties.containsKey(name);
    }

    @Override
    public Set<String> getProperties() {
        return Collections.unmodifiableSet(this.properties.keySet());
    }

    public static <T> T instantiateType(T type, String className) {
        try {
            try {
                Class<?> clazz = AbstractPropertyInvoker.createClass(className);
                Constructor<?> con = clazz.getDeclaredConstructor(null);
                Object t2 = con.newInstance(new Object[0]);
                return (T)t2;
            }
            catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
                throw new IllegalArgumentException(e);
            }
        }
        catch (RuntimeException e) {
            throw new IllegalArgumentException("Error instantiating type " + type + " using  " + className, e);
        }
    }

    public static Class<?> createClass(String className) {
        try {
            return Class.forName(className);
        }
        catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(String.format("Could not find class '%s'", className), e);
        }
    }

    protected Object convertSimpleType(Class<?> type, String value) {
        Object newValue = value;
        if (Class.class.isAssignableFrom(type)) {
            newValue = this.createTypeFromPropertyValue(Class.class, value);
        } else if (Class[].class.isAssignableFrom(type)) {
            newValue = this.createArrayTypeFromPropertyValue(Class.class, value);
        } else if (type.isEnum()) {
            newValue = AbstractPropertyInvoker.getEnum(type, value);
        } else if (type.isArray() && type.getComponentType().isEnum()) {
            newValue = this.createArrayEnumFromPropertyValue(type.getComponentType(), value);
        } else if (String[].class == type) {
            newValue = value.split(",");
        } else if (Object[].class == type) {
            newValue = value.split(",");
        } else if (Map.class == type) {
            newValue = Stream.of(value.split(",")).map(s2 -> s2.split("=")).collect(Collectors.toMap(v -> v[0], v -> v[1]));
        } else if (Float.TYPE == type || Float.class == type) {
            newValue = Float.valueOf(Float.parseFloat(value));
        } else if (Integer.TYPE == type || Integer.class == type) {
            newValue = Integer.parseInt(value);
        } else if (Long.TYPE == type || Long.class == type) {
            newValue = Long.parseLong(value);
        } else if (Short.TYPE == type || Short.class == type) {
            newValue = Short.parseShort(value);
        } else if (Double.TYPE == type || Double.class == type) {
            newValue = Double.parseDouble(value);
        } else if (Boolean.TYPE == type || Boolean.class == type) {
            newValue = Boolean.valueOf(value);
        } else if (Duration.class == type) {
            newValue = Duration.parse(value);
        }
        return newValue;
    }

    protected static Enum<?> getEnum(Class<?> clazz, String value) {
        for (Object o : clazz.getEnumConstants()) {
            Enum e = (Enum)o;
            if (!e.name().equals(value)) continue;
            return e;
        }
        throw new IllegalArgumentException(String.format("Unknown enum value %s for %s", value, clazz));
    }

    protected Object createTypeFromPropertyValue(Class<?> c, String s2) {
        Class<?> newObject;
        if ("null".equals(s2)) {
            newObject = null;
        } else if (PropertyValueParser.isConfig(s2)) {
            PropertyValueParser configParser = new PropertyValueParser(s2);
            newObject = configParser.initializeType();
        } else {
            newObject = Class.class == c ? AbstractPropertyInvoker.createClass(s2) : AbstractPropertyInvoker.instantiateType(c, s2);
        }
        return newObject;
    }

    protected Object createArrayTypeFromPropertyValue(Class<?> c, String s2) {
        Object newObject;
        if ("null".equals(s2)) {
            newObject = null;
        } else if (s2.contains("},")) {
            String[] classes = s2.split("\\},");
            newObject = Array.newInstance(c, classes.length);
            for (int i = 0; i < classes.length; ++i) {
                classes[i] = classes[i] + "}";
                if (!PropertyValueParser.isConfig(classes[i])) {
                    throw new IllegalArgumentException(String.format("Could not parse property string: %s", classes[i]));
                }
                PropertyValueParser configParser = new PropertyValueParser(classes[i]);
                Array.set(newObject, i, configParser.initializeType());
            }
        } else {
            String[] classes = s2.split(",");
            newObject = Array.newInstance(c, classes.length);
            for (int i = 0; i < classes.length; ++i) {
                if (PropertyValueParser.isConfig(classes[i])) {
                    PropertyValueParser configParser = new PropertyValueParser(classes[i]);
                    Array.set(newObject, i, configParser.initializeType());
                    continue;
                }
                if (Class.class == c) {
                    Array.set(newObject, i, AbstractPropertyInvoker.createClass(classes[i]));
                    continue;
                }
                Array.set(newObject, i, AbstractPropertyInvoker.instantiateType(c, classes[i]));
            }
        }
        return newObject;
    }

    protected Object createArrayEnumFromPropertyValue(Class<?> c, String s2) {
        Object newObject;
        if ("null".equals(s2)) {
            newObject = null;
        } else {
            String[] values2 = s2.split(",");
            newObject = Array.newInstance(c, values2.length);
            for (int i = 0; i < values2.length; ++i) {
                Array.set(newObject, i, AbstractPropertyInvoker.getEnum(c, values2[i]));
            }
        }
        return newObject;
    }

    public static Object invokeMethod(Method method2, Object object, Object arg) {
        try {
            try {
                Object[] params = new Object[]{arg};
                if (arg == null && method2.getParameterTypes().length == 0) {
                    params = null;
                }
                return method2.invoke(object, params);
            }
            catch (IllegalAccessException | InvocationTargetException e) {
                throw new IllegalArgumentException(e);
            }
        }
        catch (RuntimeException e) {
            throw new IllegalArgumentException("Error invoking " + method2 + " on " + object + " with param " + arg, e);
        }
    }
}

