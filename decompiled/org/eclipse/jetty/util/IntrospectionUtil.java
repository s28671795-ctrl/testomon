/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.util;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

public class IntrospectionUtil {
    public static boolean isJavaBeanCompliantSetter(Method method2) {
        if (method2 == null) {
            return false;
        }
        if (method2.getReturnType() != Void.TYPE) {
            return false;
        }
        if (!method2.getName().startsWith("set")) {
            return false;
        }
        return method2.getParameterCount() == 1;
    }

    public static Method findMethod(Class<?> clazz, String methodName, Class<?>[] args2, boolean checkInheritance, boolean strictArgs) throws NoSuchMethodException {
        if (clazz == null) {
            throw new NoSuchMethodException("No class");
        }
        if (methodName == null || methodName.trim().equals("")) {
            throw new NoSuchMethodException("No method name");
        }
        Method method2 = null;
        Method[] methods = clazz.getDeclaredMethods();
        for (int i = 0; i < methods.length && method2 == null; ++i) {
            if (!methods[i].getName().equals(methodName) || !IntrospectionUtil.checkParams(methods[i].getParameterTypes(), args2 == null ? new Class[]{} : args2, strictArgs)) continue;
            method2 = methods[i];
        }
        if (method2 != null) {
            return method2;
        }
        if (checkInheritance) {
            return IntrospectionUtil.findInheritedMethod(clazz.getPackage(), clazz.getSuperclass(), methodName, args2, strictArgs);
        }
        throw new NoSuchMethodException("No such method " + methodName + " on class " + clazz.getName());
    }

    public static Field findField(Class<?> clazz, String targetName, Class<?> targetType, boolean checkInheritance, boolean strictType) throws NoSuchFieldException {
        if (clazz == null) {
            throw new NoSuchFieldException("No class");
        }
        if (targetName == null) {
            throw new NoSuchFieldException("No field name");
        }
        try {
            Field field2 = clazz.getDeclaredField(targetName);
            if (strictType ? field2.getType().equals(targetType) : field2.getType().isAssignableFrom(targetType)) {
                return field2;
            }
            if (checkInheritance) {
                return IntrospectionUtil.findInheritedField(clazz.getPackage(), clazz.getSuperclass(), targetName, targetType, strictType);
            }
            throw new NoSuchFieldException("No field with name " + targetName + " in class " + clazz.getName() + " of type " + targetType);
        }
        catch (NoSuchFieldException e) {
            return IntrospectionUtil.findInheritedField(clazz.getPackage(), clazz.getSuperclass(), targetName, targetType, strictType);
        }
    }

    public static boolean isInheritable(Package pack, Member member) {
        if (pack == null) {
            return false;
        }
        if (member == null) {
            return false;
        }
        int modifiers = member.getModifiers();
        if (Modifier.isPublic(modifiers)) {
            return true;
        }
        if (Modifier.isProtected(modifiers)) {
            return true;
        }
        return !Modifier.isPrivate(modifiers) && pack.equals(member.getDeclaringClass().getPackage());
    }

    public static boolean checkParams(Class<?>[] formalParams, Class<?>[] actualParams, boolean strict) {
        int j;
        if (formalParams == null) {
            return actualParams == null;
        }
        if (actualParams == null) {
            return false;
        }
        if (formalParams.length != actualParams.length) {
            return false;
        }
        if (formalParams.length == 0) {
            return true;
        }
        if (strict) {
            for (j = 0; j < formalParams.length && formalParams[j].equals(actualParams[j]); ++j) {
            }
        } else {
            while (j < formalParams.length && formalParams[j].isAssignableFrom(actualParams[j])) {
                ++j;
            }
        }
        return j == formalParams.length;
    }

    public static boolean isSameSignature(Method methodA, Method methodB) {
        if (methodA == null) {
            return false;
        }
        if (methodB == null) {
            return false;
        }
        List<Class<Class<?>>> parameterTypesA = Arrays.asList(methodA.getParameterTypes());
        List<Class<?>> parameterTypesB = Arrays.asList(methodB.getParameterTypes());
        return methodA.getName().equals(methodB.getName()) && parameterTypesA.containsAll(parameterTypesB);
    }

    public static boolean isTypeCompatible(Class<?> formalType, Class<?> actualType, boolean strict) {
        if (formalType == null) {
            return actualType == null;
        }
        if (actualType == null) {
            return false;
        }
        if (strict) {
            return formalType.equals(actualType);
        }
        return formalType.isAssignableFrom(actualType);
    }

    public static boolean containsSameMethodSignature(Method method2, Class<?> c, boolean checkPackage) {
        if (checkPackage && !c.getPackage().equals(method2.getDeclaringClass().getPackage())) {
            return false;
        }
        boolean samesig = false;
        Method[] methods = c.getDeclaredMethods();
        for (int i = 0; i < methods.length && !samesig; ++i) {
            if (!IntrospectionUtil.isSameSignature(method2, methods[i])) continue;
            samesig = true;
        }
        return samesig;
    }

    public static boolean containsSameFieldName(Field field2, Class<?> c, boolean checkPackage) {
        if (checkPackage && !c.getPackage().equals(field2.getDeclaringClass().getPackage())) {
            return false;
        }
        boolean sameName = false;
        Field[] fields = c.getDeclaredFields();
        for (int i = 0; i < fields.length && !sameName; ++i) {
            if (!fields[i].getName().equals(field2.getName())) continue;
            sameName = true;
        }
        return sameName;
    }

    protected static Method findInheritedMethod(Package pack, Class<?> clazz, String methodName, Class<?>[] args2, boolean strictArgs) throws NoSuchMethodException {
        if (clazz == null) {
            throw new NoSuchMethodException("No class");
        }
        if (methodName == null) {
            throw new NoSuchMethodException("No method name");
        }
        Method method2 = null;
        Method[] methods = clazz.getDeclaredMethods();
        for (int i = 0; i < methods.length && method2 == null; ++i) {
            if (!methods[i].getName().equals(methodName) || !IntrospectionUtil.isInheritable(pack, methods[i]) || !IntrospectionUtil.checkParams(methods[i].getParameterTypes(), args2, strictArgs)) continue;
            method2 = methods[i];
        }
        if (method2 != null) {
            return method2;
        }
        return IntrospectionUtil.findInheritedMethod(clazz.getPackage(), clazz.getSuperclass(), methodName, args2, strictArgs);
    }

    protected static Field findInheritedField(Package pack, Class<?> clazz, String fieldName, Class<?> fieldType, boolean strictType) throws NoSuchFieldException {
        if (clazz == null) {
            throw new NoSuchFieldException("No class");
        }
        if (fieldName == null) {
            throw new NoSuchFieldException("No field name");
        }
        try {
            Field field2 = clazz.getDeclaredField(fieldName);
            if (IntrospectionUtil.isInheritable(pack, field2) && IntrospectionUtil.isTypeCompatible(fieldType, field2.getType(), strictType)) {
                return field2;
            }
            return IntrospectionUtil.findInheritedField(clazz.getPackage(), clazz.getSuperclass(), fieldName, fieldType, strictType);
        }
        catch (NoSuchFieldException e) {
            return IntrospectionUtil.findInheritedField(clazz.getPackage(), clazz.getSuperclass(), fieldName, fieldType, strictType);
        }
    }
}

