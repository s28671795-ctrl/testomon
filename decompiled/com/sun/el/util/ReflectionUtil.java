/*
 * Decompiled with CFR 0.152.
 */
package com.sun.el.util;

import com.sun.el.lang.ELSupport;
import com.sun.el.util.MessageFactory;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import javax.el.ELException;
import javax.el.MethodNotFoundException;
import javax.el.PropertyNotFoundException;

public class ReflectionUtil {
    protected static final String[] EMPTY_STRING = new String[0];
    protected static final String[] PRIMITIVE_NAMES = new String[]{"boolean", "byte", "char", "double", "float", "int", "long", "short", "void"};
    protected static final Class[] PRIMITIVES = new Class[]{Boolean.TYPE, Byte.TYPE, Character.TYPE, Double.TYPE, Float.TYPE, Integer.TYPE, Long.TYPE, Short.TYPE, Void.TYPE};

    private ReflectionUtil() {
    }

    public static Class forName(String name) throws ClassNotFoundException {
        if (null == name || "".equals(name)) {
            return null;
        }
        Class<?> c = ReflectionUtil.forNamePrimitive(name);
        if (c == null) {
            if (name.endsWith("[]")) {
                String nc = name.substring(0, name.length() - 2);
                c = Class.forName(nc, true, Thread.currentThread().getContextClassLoader());
                c = Array.newInstance(c, 0).getClass();
            } else {
                c = Class.forName(name, true, Thread.currentThread().getContextClassLoader());
            }
        }
        return c;
    }

    protected static Class forNamePrimitive(String name) {
        int p;
        if (name.length() <= 8 && (p = Arrays.binarySearch(PRIMITIVE_NAMES, name)) >= 0) {
            return PRIMITIVES[p];
        }
        return null;
    }

    public static Class[] toTypeArray(String[] s2) throws ClassNotFoundException {
        if (s2 == null) {
            return null;
        }
        Class[] c = new Class[s2.length];
        for (int i = 0; i < s2.length; ++i) {
            c[i] = ReflectionUtil.forName(s2[i]);
        }
        return c;
    }

    public static String[] toTypeNameArray(Class[] c) {
        if (c == null) {
            return null;
        }
        String[] s2 = new String[c.length];
        for (int i = 0; i < c.length; ++i) {
            s2[i] = c[i].getName();
        }
        return s2;
    }

    public static Method getMethod(Object base, Object property, Class[] paramTypes) throws MethodNotFoundException {
        if (base == null || property == null) {
            throw new MethodNotFoundException(MessageFactory.get("error.method.notfound", base, property, ReflectionUtil.paramString(paramTypes)));
        }
        String methodName = property.toString();
        Method method2 = ReflectionUtil.getMethod(base.getClass(), methodName, paramTypes);
        if (method2 == null) {
            throw new MethodNotFoundException(MessageFactory.get("error.method.notfound", base, property, ReflectionUtil.paramString(paramTypes)));
        }
        return method2;
    }

    private static Method getMethod(Class<?> cl, String methodName, Class<?>[] paramTypes) {
        Method m4 = null;
        try {
            m4 = cl.getMethod(methodName, paramTypes);
        }
        catch (NoSuchMethodException ex) {
            return null;
        }
        Class<?> dclass = m4.getDeclaringClass();
        if (Modifier.isPublic(dclass.getModifiers())) {
            return m4;
        }
        for (Class<?> c : dclass.getInterfaces()) {
            m4 = ReflectionUtil.getMethod(c, methodName, paramTypes);
            if (m4 == null) continue;
            return m4;
        }
        Class<?> c = dclass.getSuperclass();
        if (c != null && (m4 = ReflectionUtil.getMethod(c, methodName, paramTypes)) != null) {
            return m4;
        }
        return null;
    }

    protected static final String paramString(Class[] types) {
        if (types != null) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < types.length; ++i) {
                sb.append(types[i].getName()).append(", ");
            }
            if (sb.length() > 2) {
                sb.setLength(sb.length() - 2);
            }
            return sb.toString();
        }
        return null;
    }

    public static PropertyDescriptor getPropertyDescriptor(Object base, Object property) throws ELException, PropertyNotFoundException {
        String name = ELSupport.coerceToString(property);
        Object p = null;
        try {
            PropertyDescriptor[] desc = Introspector.getBeanInfo(base.getClass()).getPropertyDescriptors();
            for (int i = 0; i < desc.length; ++i) {
                if (!desc[i].getName().equals(name)) continue;
                return desc[i];
            }
        }
        catch (IntrospectionException ie) {
            throw new ELException(ie);
        }
        throw new PropertyNotFoundException(MessageFactory.get("error.property.notfound", base, name));
    }
}

