/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxb;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
import org.apache.cxf.jaxb.JAXBContextInitializer;

final class Utils {
    private Utils() {
    }

    static XmlAccessType getXmlAccessType(Class<?> cls) {
        XmlAccessorType accessorType = cls.getAnnotation(XmlAccessorType.class);
        if (accessorType == null && cls.getPackage() != null) {
            accessorType = cls.getPackage().getAnnotation(XmlAccessorType.class);
        }
        return accessorType != null ? accessorType.value() : XmlAccessType.PUBLIC_MEMBER;
    }

    static Collection<Field> getFields(Class<?> cls, XmlAccessType accessType) {
        return Utils.getFieldsInternal(cls, accessType);
    }

    private static Collection<Field> getFieldsInternal(Class<?> cls, XmlAccessType accessType) {
        HashSet<Field> fields = new HashSet<Field>();
        Class<?> superClass = cls.getSuperclass();
        if (superClass != null && !superClass.equals(Object.class) && !superClass.equals(Throwable.class)) {
            fields.addAll(Utils.getFieldsInternal(superClass, accessType));
        }
        for (Field field2 : cls.getDeclaredFields()) {
            if (!JAXBContextInitializer.isFieldAccepted(field2, accessType)) continue;
            fields.add(field2);
        }
        return fields;
    }

    private static Collection<Method> getMethods(Class<?> cls, XmlAccessType accessType, boolean acceptSetters) {
        return Utils.getMethodsInternal(cls, accessType, acceptSetters);
    }

    private static Collection<Method> getMethodsInternal(Class<?> cls, XmlAccessType accessType, boolean acceptSetters) {
        HashSet<Method> methods = new HashSet<Method>();
        Class<?> superClass = cls.getSuperclass();
        if (superClass != null && !superClass.equals(Object.class) && !superClass.equals(Throwable.class)) {
            methods.addAll(Utils.getMethodsInternal(superClass, accessType, acceptSetters));
        }
        for (Method method2 : cls.getDeclaredMethods()) {
            if (!Utils.isMethodAccepted(method2, accessType, acceptSetters)) continue;
            methods.add(method2);
        }
        return methods;
    }

    static Method getMethod(Class<?> cls, XmlAccessType accessType, String methodName, Class<?> ... paramTypes) {
        for (Method m4 : Utils.getMethods(cls, accessType, true)) {
            if (!m4.getName().equals(methodName) || !Arrays.equals(m4.getParameterTypes(), paramTypes)) continue;
            return m4;
        }
        return null;
    }

    static Field getField(Class<?> cls, XmlAccessType accessType, String fieldName) {
        for (Field f : Utils.getFields(cls, accessType)) {
            if (!f.getName().equals(fieldName)) continue;
            return f;
        }
        return null;
    }

    static Collection<Method> getGetters(Class<?> cls, XmlAccessType accessType) {
        return Utils.getMethods(cls, accessType, false);
    }

    static boolean isMethodAccepted(Method method2, XmlAccessType accessType, boolean acceptSetters) {
        if (method2.isBridge() || Modifier.isStatic(method2.getModifiers()) || method2.isAnnotationPresent(XmlTransient.class) || method2.getDeclaringClass().equals(Throwable.class) || "getClass".equals(method2.getName())) {
            return false;
        }
        if (accessType == XmlAccessType.PUBLIC_MEMBER && !Modifier.isPublic(method2.getModifiers())) {
            return false;
        }
        if (!Utils.isGetter(method2)) {
            if (Utils.isSetter(method2)) {
                if (!acceptSetters) {
                    return false;
                }
            } else {
                return false;
            }
        }
        if (accessType == XmlAccessType.NONE || accessType == XmlAccessType.FIELD) {
            return JAXBContextInitializer.checkJaxbAnnotation(method2.getAnnotations());
        }
        return true;
    }

    private static boolean isGetter(Method m4) {
        if (m4.getReturnType() != Void.class && m4.getReturnType() != Void.TYPE && m4.getParameterTypes().length == 0) {
            Method setter2 = Utils.getSetter(m4);
            if (setter2 != null) {
                return !setter2.isAnnotationPresent(XmlTransient.class);
            }
            if (Utils.getterIndex(m4.getName()) > -1) {
                return true;
            }
        }
        return false;
    }

    private static Method getSetter(Method m4) {
        int index = Utils.getterIndex(m4.getName());
        if (index != -1) {
            String setterName = "set" + m4.getName().substring(index);
            Class<?> paramTypes = m4.getReturnType();
            return Utils.getDeclaredMethod(m4.getDeclaringClass(), setterName, paramTypes);
        }
        return null;
    }

    private static boolean isSetter(Method m4) {
        boolean isVoidReturnType;
        Class<?> declaringClass = m4.getDeclaringClass();
        boolean bl = isVoidReturnType = m4.getReturnType() == Void.class || m4.getReturnType() == Void.TYPE;
        if (isVoidReturnType && m4.getParameterTypes().length == 1 && m4.getName().startsWith("set")) {
            String getterName = "get" + m4.getName().substring(3);
            Class<?> setterParamType = m4.getParameterTypes()[0];
            Method getter2 = Utils.getDeclaredMethod(declaringClass, getterName, new Class[0]);
            if (getter2 != null && getter2.getReturnType().equals(setterParamType) && !getter2.isAnnotationPresent(XmlTransient.class)) {
                return true;
            }
        }
        return false;
    }

    private static int getterIndex(String methodName) {
        if (methodName.startsWith("is")) {
            return 2;
        }
        if (methodName.startsWith("get")) {
            return 3;
        }
        return -1;
    }

    private static Method getDeclaredMethod(Class<?> cls, String methodName, Class<?> ... paramTypes) {
        try {
            return cls.getDeclaredMethod(methodName, paramTypes);
        }
        catch (Exception e) {
            return null;
        }
    }

    static Class<?> getFieldType(Field f) {
        XmlJavaTypeAdapter adapter = Utils.getFieldXJTA(f);
        if (adapter == null && f.getGenericType() instanceof ParameterizedType && ((ParameterizedType)f.getGenericType()).getActualTypeArguments().length == 1) {
            return null;
        }
        Class<?> adapterType = (Class<?>)Utils.getTypeFromXmlAdapter(adapter);
        return adapterType != null ? adapterType : f.getType();
    }

    static Class<?> getMethodReturnType(Method m4) {
        XmlJavaTypeAdapter adapter = Utils.getMethodXJTA(m4);
        if (adapter == null && m4.getGenericReturnType() instanceof ParameterizedType && ((ParameterizedType)m4.getGenericReturnType()).getActualTypeArguments().length < 2) {
            return null;
        }
        Class<?> adapterType = (Class<?>)Utils.getTypeFromXmlAdapter(adapter);
        return adapterType != null ? adapterType : m4.getReturnType();
    }

    static Object getFieldValue(Field f, Object target) throws Exception {
        XmlJavaTypeAdapter adapterAnnotation = Utils.getFieldXJTA(f);
        XmlAdapter adapter = Utils.getXmlAdapter(adapterAnnotation);
        return adapter != null ? adapter.marshal(f.get(target)) : f.get(target);
    }

    static Object getMethodValue(Method m4, Object target) throws Exception {
        XmlJavaTypeAdapter adapterAnnotation = Utils.getMethodXJTA(m4);
        XmlAdapter adapter = Utils.getXmlAdapter(adapterAnnotation);
        return adapter != null ? adapter.marshal(m4.invoke(target, new Object[0])) : m4.invoke(target, new Object[0]);
    }

    static void setFieldValue(Field f, Object target, Object value) throws Exception {
        XmlJavaTypeAdapter xjta = Utils.getFieldXJTA(f);
        XmlAdapter adapter = Utils.getXmlAdapter(xjta);
        f.set(target, adapter != null ? adapter.unmarshal(value) : value);
    }

    static void setMethodValue(Method getter2, Method setter2, Object target, Object value) throws Exception {
        XmlJavaTypeAdapter xjta = Utils.getMethodXJTA(getter2);
        XmlAdapter adapter = Utils.getXmlAdapter(xjta);
        setter2.invoke(target, adapter != null ? adapter.unmarshal(value) : value);
    }

    static XmlAdapter getXmlAdapter(XmlJavaTypeAdapter adapterAnnotation) throws InstantiationException, IllegalAccessException {
        return adapterAnnotation != null ? adapterAnnotation.value().newInstance() : null;
    }

    static XmlJavaTypeAdapter getFieldXJTA(Field f) {
        XmlJavaTypeAdapters adapters;
        Package packageDeclaration;
        XmlJavaTypeAdapter adapter = f.getAnnotation(XmlJavaTypeAdapter.class);
        if (adapter == null) {
            adapter = f.getType().getAnnotation(XmlJavaTypeAdapter.class);
        }
        if (adapter == null && (packageDeclaration = f.getDeclaringClass().getPackage()) != null && (adapters = packageDeclaration.getAnnotation(XmlJavaTypeAdapters.class)) != null) {
            for (XmlJavaTypeAdapter candidate : adapters.value()) {
                if (candidate == null || !candidate.type().equals(f.getType())) continue;
                adapter = candidate;
                break;
            }
        }
        return adapter;
    }

    static XmlJavaTypeAdapter getMethodXJTA(Method m4) {
        XmlJavaTypeAdapters adapters;
        Package packageDeclaration;
        Method setter2;
        XmlJavaTypeAdapter adapter = m4.getAnnotation(XmlJavaTypeAdapter.class);
        if (adapter == null && (setter2 = Utils.getSetter(m4)) != null) {
            adapter = setter2.getAnnotation(XmlJavaTypeAdapter.class);
        }
        if (adapter == null) {
            adapter = m4.getReturnType().getAnnotation(XmlJavaTypeAdapter.class);
        }
        if (adapter == null && (packageDeclaration = m4.getDeclaringClass().getPackage()) != null && (adapters = packageDeclaration.getAnnotation(XmlJavaTypeAdapters.class)) != null) {
            for (XmlJavaTypeAdapter candidate : adapters.value()) {
                if (candidate == null || !candidate.type().equals(m4.getGenericReturnType())) continue;
                adapter = candidate;
                break;
            }
        }
        return adapter;
    }

    static Type getTypeFromXmlAdapter(XmlJavaTypeAdapter xjta) {
        if (xjta != null) {
            Class<? extends XmlAdapter> c2;
            Type sp2 = c2.getGenericSuperclass();
            for (c2 = xjta.value(); !XmlAdapter.class.equals(c2) && c2 != null; c2 = c2.getSuperclass()) {
                sp2 = c2.getGenericSuperclass();
            }
            if (sp2 instanceof ParameterizedType) {
                return ((ParameterizedType)sp2).getActualTypeArguments()[0];
            }
        }
        return null;
    }
}

