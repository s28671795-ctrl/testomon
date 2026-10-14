/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.plexus.util;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.codehaus.plexus.util.StringUtils;

public final class ReflectionUtils {
    public static Field getFieldByNameIncludingSuperclasses(String fieldName, Class<?> clazz) {
        Field retValue;
        block2: {
            retValue = null;
            try {
                retValue = clazz.getDeclaredField(fieldName);
            }
            catch (NoSuchFieldException e) {
                Class<?> superclass = clazz.getSuperclass();
                if (superclass == null) break block2;
                retValue = ReflectionUtils.getFieldByNameIncludingSuperclasses(fieldName, superclass);
            }
        }
        return retValue;
    }

    public static List<Field> getFieldsIncludingSuperclasses(Class<?> clazz) {
        ArrayList<Field> fields = new ArrayList<Field>(Arrays.asList(clazz.getDeclaredFields()));
        Class<?> superclass = clazz.getSuperclass();
        if (superclass != null) {
            fields.addAll(ReflectionUtils.getFieldsIncludingSuperclasses(superclass));
        }
        return fields;
    }

    public static Method getSetter(String fieldName, Class<?> clazz) {
        Method[] methods = clazz.getMethods();
        fieldName = "set" + StringUtils.capitalizeFirstLetter(fieldName);
        for (Method method2 : methods) {
            if (!method2.getName().equals(fieldName) || !ReflectionUtils.isSetter(method2)) continue;
            return method2;
        }
        return null;
    }

    public static List<Method> getSetters(Class<?> clazz) {
        Method[] methods = clazz.getMethods();
        ArrayList<Method> list = new ArrayList<Method>();
        for (Method method2 : methods) {
            if (!ReflectionUtils.isSetter(method2)) continue;
            list.add(method2);
        }
        return list;
    }

    public static Class<?> getSetterType(Method method2) {
        if (!ReflectionUtils.isSetter(method2)) {
            throw new RuntimeException("The method " + method2.getDeclaringClass().getName() + "." + method2.getName() + " is not a setter.");
        }
        return method2.getParameterTypes()[0];
    }

    public static void setVariableValueInObject(Object object, String variable, Object value) throws IllegalAccessException {
        Field field2 = ReflectionUtils.getFieldByNameIncludingSuperclasses(variable, object.getClass());
        field2.setAccessible(true);
        field2.set(object, value);
    }

    public static Object getValueIncludingSuperclasses(String variable, Object object) throws IllegalAccessException {
        Field field2 = ReflectionUtils.getFieldByNameIncludingSuperclasses(variable, object.getClass());
        field2.setAccessible(true);
        return field2.get(object);
    }

    public static Map<String, Object> getVariablesAndValuesIncludingSuperclasses(Object object) throws IllegalAccessException {
        HashMap<String, Object> map = new HashMap<String, Object>();
        ReflectionUtils.gatherVariablesAndValuesIncludingSuperclasses(object, map);
        return map;
    }

    public static boolean isSetter(Method method2) {
        return method2.getReturnType().equals(Void.TYPE) && !Modifier.isStatic(method2.getModifiers()) && method2.getParameterTypes().length == 1;
    }

    private static void gatherVariablesAndValuesIncludingSuperclasses(Object object, Map<String, Object> map) throws IllegalAccessException {
        Class<?> clazz = object.getClass();
        if (Float.parseFloat(System.getProperty("java.specification.version")) >= 11.0f && Class.class.getCanonicalName().equals(clazz.getCanonicalName())) {
            return;
        }
        AccessibleObject[] fields = clazz.getDeclaredFields();
        AccessibleObject.setAccessible(fields, true);
        for (AccessibleObject field2 : fields) {
            map.put(((Field)field2).getName(), ((Field)field2).get(object));
        }
        Class<?> superclass = clazz.getSuperclass();
        if (!Object.class.equals(superclass)) {
            ReflectionUtils.gatherVariablesAndValuesIncludingSuperclasses(superclass, map);
        }
    }
}

