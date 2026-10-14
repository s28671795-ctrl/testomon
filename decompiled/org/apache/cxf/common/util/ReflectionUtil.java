/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.util;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import org.apache.cxf.common.classloader.ClassLoaderUtils;

public final class ReflectionUtil {
    private static Method springBeanUtilsDescriptorFetcher;
    private static boolean springChecked;

    private ReflectionUtil() {
    }

    public static <T> T accessDeclaredField(final Field f, final Object o, final Class<T> responseClass) {
        return AccessController.doPrivileged(new PrivilegedAction<T>(){

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public T run() {
                boolean b = f.isAccessible();
                try {
                    f.setAccessible(true);
                    Object t2 = responseClass.cast(f.get(o));
                    return t2;
                }
                catch (IllegalAccessException | SecurityException e) {
                    Object t3 = null;
                    return t3;
                }
                finally {
                    f.setAccessible(b);
                }
            }
        });
    }

    public static <T> T accessDeclaredField(final String fieldName, final Class<?> cls, final Object o, final Class<T> responseClass) {
        return AccessController.doPrivileged(new PrivilegedAction<T>(){

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public T run() {
                Field f = ReflectionUtil.getDeclaredField(cls, fieldName);
                boolean b = f.isAccessible();
                try {
                    f.setAccessible(true);
                    Object t2 = responseClass.cast(f.get(o));
                    return t2;
                }
                catch (IllegalAccessException | SecurityException e) {
                    Object t3 = null;
                    return t3;
                }
                finally {
                    f.setAccessible(b);
                }
            }
        });
    }

    public static Field getDeclaredField(final Class<?> cls, final String name) {
        return AccessController.doPrivileged(new PrivilegedAction<Field>(){

            @Override
            public Field run() {
                try {
                    return cls.getDeclaredField(name);
                }
                catch (NoSuchFieldException | SecurityException e) {
                    return null;
                }
            }
        });
    }

    public static <T> Constructor<T> getDeclaredConstructor(final Class<T> cls, final Class<?> ... args2) {
        return (Constructor)AccessController.doPrivileged(new PrivilegedAction<Constructor<T>>(){

            @Override
            public Constructor<T> run() {
                try {
                    return cls.getDeclaredConstructor(args2);
                }
                catch (NoSuchMethodException | SecurityException e) {
                    return null;
                }
            }
        });
    }

    public static <T> Constructor<T> getConstructor(final Class<T> cls, final Class<?> ... args2) {
        return (Constructor)AccessController.doPrivileged(new PrivilegedAction<Constructor<T>>(){

            @Override
            public Constructor<T> run() {
                try {
                    return cls.getConstructor(args2);
                }
                catch (NoSuchMethodException | SecurityException e) {
                    return null;
                }
            }
        });
    }

    public static <T> Constructor<T>[] getDeclaredConstructors(final Class<T> cls) {
        return AccessController.doPrivileged(new PrivilegedAction<Constructor<T>[]>(){

            @Override
            public Constructor<T>[] run() {
                try {
                    return cls.getDeclaredConstructors();
                }
                catch (SecurityException e) {
                    return null;
                }
            }
        });
    }

    public static Method[] getDeclaredMethods(final Class<?> cls) {
        return AccessController.doPrivileged(new PrivilegedAction<Method[]>(){

            @Override
            public Method[] run() {
                return cls.getDeclaredMethods();
            }
        });
    }

    public static Method getDeclaredMethod(final Class<?> clazz, final String name, final Class<?> ... parameterTypes) throws NoSuchMethodException {
        try {
            return AccessController.doPrivileged(new PrivilegedExceptionAction<Method>(){

                @Override
                public Method run() throws Exception {
                    return clazz.getDeclaredMethod(name, parameterTypes);
                }
            });
        }
        catch (PrivilegedActionException pae) {
            Exception e = pae.getException();
            if (e instanceof NoSuchMethodException) {
                throw (NoSuchMethodException)e;
            }
            throw new SecurityException(e);
        }
    }

    public static Method getMethod(final Class<?> clazz, final String name, final Class<?> ... parameterTypes) throws NoSuchMethodException {
        try {
            return AccessController.doPrivileged(new PrivilegedExceptionAction<Method>(){

                @Override
                public Method run() throws Exception {
                    return clazz.getMethod(name, parameterTypes);
                }
            });
        }
        catch (PrivilegedActionException pae) {
            Exception e = pae.getException();
            if (e instanceof NoSuchMethodException) {
                throw (NoSuchMethodException)e;
            }
            throw new SecurityException(e);
        }
    }

    public static Field[] getDeclaredFields(final Class<?> cls) {
        return AccessController.doPrivileged(new PrivilegedAction<Field[]>(){

            @Override
            public Field[] run() {
                return cls.getDeclaredFields();
            }
        });
    }

    public static <T extends AccessibleObject> T setAccessible(final T o) {
        return (T)((AccessibleObject)AccessController.doPrivileged(new PrivilegedAction<T>(){

            @Override
            public T run() {
                o.setAccessible(true);
                return o;
            }
        }));
    }

    public static <T extends AccessibleObject> T setAccessible(final T o, final boolean b) {
        return (T)((AccessibleObject)AccessController.doPrivileged(new PrivilegedAction<T>(){

            @Override
            public T run() {
                o.setAccessible(b);
                return o;
            }
        }));
    }

    public static PropertyDescriptor[] getPropertyDescriptorsAvoidSunBug(Class<?> refClass, BeanInfo beanInfo, Class<?> beanClass, PropertyDescriptor[] propertyDescriptors) {
        if (!springChecked) {
            try {
                springChecked = true;
                Class<?> cls = ClassLoaderUtils.loadClass("org.springframework.beans.BeanUtils", refClass);
                springBeanUtilsDescriptorFetcher = cls.getMethod("getPropertyDescriptor", Class.class, String.class);
            }
            catch (Exception cls) {
                // empty catch block
            }
        }
        if (springBeanUtilsDescriptorFetcher != null) {
            if (propertyDescriptors != null) {
                ArrayList<PropertyDescriptor> descriptors = new ArrayList<PropertyDescriptor>(propertyDescriptors.length);
                for (int i = 0; i < propertyDescriptors.length; ++i) {
                    PropertyDescriptor propertyDescriptor = propertyDescriptors[i];
                    try {
                        propertyDescriptor = (PropertyDescriptor)springBeanUtilsDescriptorFetcher.invoke(null, beanClass, propertyDescriptor.getName());
                        if (propertyDescriptor == null) continue;
                        descriptors.add(propertyDescriptor);
                        continue;
                    }
                    catch (IllegalAccessException | IllegalArgumentException e) {
                        throw new RuntimeException(e);
                    }
                    catch (InvocationTargetException e) {
                        throw new RuntimeException(e.getCause());
                    }
                }
                return descriptors.toArray(new PropertyDescriptor[0]);
            }
            return null;
        }
        return beanInfo.getPropertyDescriptors();
    }

    public static <T extends Annotation> T getAnnotationForMethodOrContainingClass(Method m4, Class<T> annotationType) {
        T annotation = m4.getAnnotation(annotationType);
        if (annotation != null) {
            return annotation;
        }
        annotation = m4.getDeclaringClass().getAnnotation(annotationType);
        if (annotation != null) {
            return annotation;
        }
        for (Class<?> intf : m4.getDeclaringClass().getInterfaces()) {
            annotation = ReflectionUtil.getAnnotationForInterface(intf, annotationType);
            if (annotation == null) continue;
            return annotation;
        }
        return null;
    }

    private static <T extends Annotation> T getAnnotationForInterface(Class<?> intf, Class<T> annotationType) {
        T annotation = intf.getAnnotation(annotationType);
        if (annotation != null) {
            return annotation;
        }
        int n = 0;
        Class<?>[] classArray = intf.getInterfaces();
        int n2 = classArray.length;
        if (n < n2) {
            Class<?> intf2 = classArray[n];
            return ReflectionUtil.getAnnotationForInterface(intf2, annotationType);
        }
        return null;
    }
}

