/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.tools.reflect;

import java.lang.constant.Constable;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jooq.tools.reflect.Compile;
import org.jooq.tools.reflect.CompileOptions;
import org.jooq.tools.reflect.ReflectException;

public class Reflect {
    static final Constructor<MethodHandles.Lookup> CACHED_LOOKUP_CONSTRUCTOR;
    private final Class<?> type;
    private final Object object;

    public static Reflect compile(String name, String content) throws ReflectException {
        return Reflect.compile(name, content, new CompileOptions());
    }

    public static Reflect compile(String name, String content, CompileOptions options) throws ReflectException {
        return Reflect.onClass(Compile.compile(name, content, options));
    }

    @Deprecated
    public static Reflect on(String name) throws ReflectException {
        return Reflect.onClass(name);
    }

    @Deprecated
    public static Reflect on(String name, ClassLoader classLoader) throws ReflectException {
        return Reflect.onClass(name, classLoader);
    }

    @Deprecated
    public static Reflect on(Class<?> clazz) {
        return Reflect.onClass(clazz);
    }

    public static Reflect onClass(String name) throws ReflectException {
        return Reflect.onClass(Reflect.forName(name));
    }

    public static Reflect onClass(String name, ClassLoader classLoader) throws ReflectException {
        return Reflect.onClass(Reflect.forName(name, classLoader));
    }

    public static Reflect onClass(Class<?> clazz) {
        return new Reflect(clazz);
    }

    public static Reflect on(Object object) {
        return new Reflect(object == null ? Object.class : object.getClass(), object);
    }

    private static Reflect on(Class<?> type, Object object) {
        return new Reflect(type, object);
    }

    public static <T> T initValue(Class<T> type) {
        return (T)(type == Boolean.TYPE ? Boolean.FALSE : (type == Byte.TYPE ? Byte.valueOf((byte)0) : (type == Short.TYPE ? Short.valueOf((short)0) : (type == Integer.TYPE ? Integer.valueOf(0) : (type == Long.TYPE ? Long.valueOf(0L) : (type == Double.TYPE ? Double.valueOf(0.0) : (type == Float.TYPE ? (Constable)Float.valueOf(0.0f) : (Constable)(type == Character.TYPE ? Character.valueOf('\u0000') : null))))))));
    }

    public static <T extends AccessibleObject> T accessible(T accessible) {
        Member member;
        if (accessible == null) {
            return null;
        }
        if (accessible instanceof Member && Modifier.isPublic((member = (Member)((Object)accessible)).getModifiers()) && Modifier.isPublic(member.getDeclaringClass().getModifiers())) {
            return accessible;
        }
        if (!accessible.isAccessible()) {
            accessible.setAccessible(true);
        }
        return accessible;
    }

    private Reflect(Class<?> type) {
        this(type, type);
    }

    private Reflect(Class<?> type, Object object) {
        this.type = type;
        this.object = object;
    }

    public <T> T get() {
        return (T)this.object;
    }

    public Reflect set(String name, Object value) throws ReflectException {
        try {
            Field field2 = this.field0(name);
            if ((field2.getModifiers() & 0x10) == 16) {
                try {
                    Field modifiersField = Field.class.getDeclaredField("modifiers");
                    modifiersField.setAccessible(true);
                    modifiersField.setInt(field2, field2.getModifiers() & 0xFFFFFFEF);
                }
                catch (NoSuchFieldException noSuchFieldException) {
                    // empty catch block
                }
            }
            field2.set(this.object, Reflect.unwrap(value));
            return this;
        }
        catch (Exception e) {
            throw new ReflectException(e);
        }
    }

    public <T> T get(String name) throws ReflectException {
        return this.field(name).get();
    }

    public Reflect field(String name) throws ReflectException {
        try {
            Field field2 = this.field0(name);
            return Reflect.on(field2.getType(), field2.get(this.object));
        }
        catch (Exception e) {
            throw new ReflectException(e);
        }
    }

    private Field field0(String name) throws ReflectException {
        Class<?> t2 = this.type();
        try {
            return Reflect.accessible(t2.getField(name));
        }
        catch (NoSuchFieldException e) {
            while (true) {
                try {
                    return Reflect.accessible(t2.getDeclaredField(name));
                }
                catch (NoSuchFieldException noSuchFieldException) {
                    if ((t2 = t2.getSuperclass()) != null) continue;
                    throw new ReflectException(e);
                }
                break;
            }
        }
    }

    public Map<String, Reflect> fields() {
        LinkedHashMap<String, Reflect> result = new LinkedHashMap<String, Reflect>();
        Class<?> t2 = this.type();
        do {
            for (Field field2 : t2.getDeclaredFields()) {
                String name;
                if (!(this.type != this.object ^ Modifier.isStatic(field2.getModifiers())) || result.containsKey(name = field2.getName())) continue;
                result.put(name, this.field(name));
            }
        } while ((t2 = t2.getSuperclass()) != null);
        return result;
    }

    public Reflect call(String name) throws ReflectException {
        return this.call(name, new Object[0]);
    }

    public Reflect call(String name, Object ... args2) throws ReflectException {
        Class<?>[] types = Reflect.types(args2);
        try {
            Method method2 = this.exactMethod(name, types);
            return Reflect.on(method2, this.object, args2);
        }
        catch (NoSuchMethodException e) {
            try {
                Method method3 = this.similarMethod(name, types);
                return Reflect.on(method3, this.object, args2);
            }
            catch (NoSuchMethodException e1) {
                throw new ReflectException(e1);
            }
        }
    }

    private Method exactMethod(String name, Class<?>[] types) throws NoSuchMethodException {
        Class<?> t2 = this.type();
        try {
            return t2.getMethod(name, types);
        }
        catch (NoSuchMethodException e) {
            while (true) {
                try {
                    return t2.getDeclaredMethod(name, types);
                }
                catch (NoSuchMethodException noSuchMethodException) {
                    if ((t2 = t2.getSuperclass()) != null) continue;
                    throw new NoSuchMethodException();
                }
                break;
            }
        }
    }

    private Method similarMethod(String name, Class<?>[] types) throws NoSuchMethodException {
        Class<?> t2 = this.type();
        for (Method method2 : t2.getMethods()) {
            if (!this.isSimilarSignature(method2, name, types)) continue;
            return method2;
        }
        do {
            for (Method method2 : t2.getDeclaredMethods()) {
                if (!this.isSimilarSignature(method2, name, types)) continue;
                return method2;
            }
        } while ((t2 = t2.getSuperclass()) != null);
        throw new NoSuchMethodException("No similar method " + name + " with params " + Arrays.toString(types) + " could be found on type " + String.valueOf(this.type()) + ".");
    }

    private boolean isSimilarSignature(Method possiblyMatchingMethod, String desiredMethodName, Class<?>[] desiredParamTypes) {
        return possiblyMatchingMethod.getName().equals(desiredMethodName) && this.match(possiblyMatchingMethod.getParameterTypes(), desiredParamTypes);
    }

    public Reflect create() throws ReflectException {
        return this.create(new Object[0]);
    }

    public Reflect create(Object ... args2) throws ReflectException {
        Class<?>[] types = Reflect.types(args2);
        try {
            Constructor<?> constructor = this.type().getDeclaredConstructor(types);
            return Reflect.on(constructor, args2);
        }
        catch (NoSuchMethodException e) {
            for (Constructor<?> constructor : this.type().getDeclaredConstructors()) {
                if (!this.match(constructor.getParameterTypes(), types)) continue;
                return Reflect.on(constructor, args2);
            }
            throw new ReflectException(e);
        }
    }

    public <P> P as(Class<P> proxyType) {
        return this.as(proxyType, new Class[0]);
    }

    public <P> P as(final Class<P> proxyType, Class<?> ... additionalInterfaces) {
        final boolean isMap = this.object instanceof Map;
        InvocationHandler handler = new InvocationHandler(){

            @Override
            public Object invoke(Object proxy, Method method2, Object[] args2) throws Throwable {
                String name = method2.getName();
                try {
                    return Reflect.on(Reflect.this.type, Reflect.this.object).call(name, args2).get();
                }
                catch (ReflectException e) {
                    if (isMap) {
                        int length;
                        Map map = (Map)Reflect.this.object;
                        int n = length = args2 == null ? 0 : args2.length;
                        if (length == 0 && name.startsWith("get")) {
                            return map.get(Reflect.property(name.substring(3)));
                        }
                        if (length == 0 && name.startsWith("is")) {
                            return map.get(Reflect.property(name.substring(2)));
                        }
                        if (length == 1 && name.startsWith("set")) {
                            map.put(Reflect.property(name.substring(3)), args2[0]);
                            return null;
                        }
                    }
                    if (method2.isDefault()) {
                        MethodHandles.Lookup proxyLookup = null;
                        if (CACHED_LOOKUP_CONSTRUCTOR == null) {
                            proxyLookup = MethodHandles.privateLookupIn(proxyType, MethodHandles.lookup()).in(proxyType);
                            if (proxyLookup == null) {
                                proxyLookup = (MethodHandles.Lookup)Reflect.onClass(MethodHandles.class).call("privateLookupIn", proxyType, MethodHandles.lookup()).call("in", proxyType).get();
                            }
                        } else {
                            proxyLookup = CACHED_LOOKUP_CONSTRUCTOR.newInstance(proxyType);
                        }
                        return proxyLookup.unreflectSpecial(method2, proxyType).bindTo(proxy).invokeWithArguments(args2);
                    }
                    throw e;
                }
            }
        };
        Class[] interfaces = new Class[1 + additionalInterfaces.length];
        interfaces[0] = proxyType;
        System.arraycopy(additionalInterfaces, 0, interfaces, 1, additionalInterfaces.length);
        return (P)Proxy.newProxyInstance(proxyType.getClassLoader(), interfaces, handler);
    }

    private static String property(String string) {
        int length = string.length();
        if (length == 0) {
            return "";
        }
        if (length == 1) {
            return string.toLowerCase();
        }
        return string.substring(0, 1).toLowerCase() + string.substring(1);
    }

    private boolean match(Class<?>[] declaredTypes, Class<?>[] actualTypes) {
        if (declaredTypes.length == actualTypes.length) {
            for (int i = 0; i < actualTypes.length; ++i) {
                if (actualTypes[i] == NULL.class || Reflect.wrapper(declaredTypes[i]).isAssignableFrom(Reflect.wrapper(actualTypes[i]))) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.object.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof Reflect) {
            return this.object.equals(((Reflect)obj).get());
        }
        return false;
    }

    public String toString() {
        return String.valueOf(this.object);
    }

    private static Reflect on(Constructor<?> constructor, Object ... args2) throws ReflectException {
        try {
            return Reflect.on(constructor.getDeclaringClass(), Reflect.accessible(constructor).newInstance(args2));
        }
        catch (Exception e) {
            throw new ReflectException(e);
        }
    }

    private static Reflect on(Method method2, Object object, Object ... args2) throws ReflectException {
        try {
            Reflect.accessible(method2);
            if (method2.getReturnType() == Void.TYPE) {
                method2.invoke(object, args2);
                return Reflect.on(object);
            }
            return Reflect.on(method2.invoke(object, args2));
        }
        catch (Exception e) {
            throw new ReflectException(e);
        }
    }

    private static Object unwrap(Object object) {
        if (object instanceof Reflect) {
            return ((Reflect)object).get();
        }
        return object;
    }

    private static Class<?>[] types(Object ... values2) {
        if (values2 == null) {
            return new Class[0];
        }
        Class[] result = new Class[values2.length];
        for (int i = 0; i < values2.length; ++i) {
            Object value = values2[i];
            result[i] = value == null ? NULL.class : value.getClass();
        }
        return result;
    }

    private static Class<?> forName(String name) throws ReflectException {
        try {
            return Class.forName(name);
        }
        catch (Exception e) {
            throw new ReflectException(e);
        }
    }

    private static Class<?> forName(String name, ClassLoader classLoader) throws ReflectException {
        try {
            return Class.forName(name, true, classLoader);
        }
        catch (Exception e) {
            throw new ReflectException(e);
        }
    }

    public Class<?> type() {
        return this.type;
    }

    public static <T> Class<T> wrapper(Class<T> type) {
        if (type == null) {
            return null;
        }
        if (type.isPrimitive()) {
            if (Boolean.TYPE == type) {
                return Boolean.class;
            }
            if (Integer.TYPE == type) {
                return Integer.class;
            }
            if (Long.TYPE == type) {
                return Long.class;
            }
            if (Short.TYPE == type) {
                return Short.class;
            }
            if (Byte.TYPE == type) {
                return Byte.class;
            }
            if (Double.TYPE == type) {
                return Double.class;
            }
            if (Float.TYPE == type) {
                return Float.class;
            }
            if (Character.TYPE == type) {
                return Character.class;
            }
            if (Void.TYPE == type) {
                return Void.class;
            }
        }
        return type;
    }

    static {
        Object result = null;
        CACHED_LOOKUP_CONSTRUCTOR = result;
    }

    private static class NULL {
        private NULL() {
        }
    }
}

