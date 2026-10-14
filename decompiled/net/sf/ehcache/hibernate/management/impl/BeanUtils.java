/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.hibernate.management.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class BeanUtils {
    private static Method getMethod(Object bean, String propertyName) {
        StringBuilder sb = new StringBuilder("get").append(Character.toUpperCase(propertyName.charAt(0)));
        if (propertyName.length() > 1) {
            sb.append(propertyName.substring(1));
        }
        String getterName = sb.toString();
        for (Method m4 : bean.getClass().getMethods()) {
            if (!getterName.equals(m4.getName()) || m4.getParameterTypes().length != 0) continue;
            return m4;
        }
        return null;
    }

    private static Field getField(Object bean, String propertyName) {
        for (Field f : bean.getClass().getDeclaredFields()) {
            if (!propertyName.equals(f.getName())) continue;
            return f;
        }
        return null;
    }

    private static void validateArgs(Object bean, String propertyName) {
        if (bean == null) {
            throw new IllegalArgumentException("bean is null");
        }
        if (propertyName == null) {
            throw new IllegalArgumentException("propertyName is null");
        }
        if (propertyName.trim().length() == 0) {
            throw new IllegalArgumentException("propertyName is empty");
        }
    }

    public static Object getBeanProperty(Object bean, String propertyName) {
        Field field2;
        BeanUtils.validateArgs(bean, propertyName);
        Method getter2 = BeanUtils.getMethod(bean, propertyName);
        if (getter2 != null) {
            try {
                return getter2.invoke(bean, new Object[0]);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if ((field2 = BeanUtils.getField(bean, propertyName)) != null) {
            try {
                field2.setAccessible(true);
                return field2.get(bean);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return null;
    }

    public static long getLongBeanProperty(Object bean, String propertyName) throws NoSuchFieldException {
        BeanUtils.validateArgs(bean, propertyName);
        Object o = BeanUtils.getBeanProperty(bean, propertyName);
        if (o == null) {
            throw new NoSuchFieldException(propertyName);
        }
        if (!(o instanceof Number)) {
            throw new IllegalArgumentException(propertyName + " not an Number");
        }
        return ((Number)o).longValue();
    }
}

