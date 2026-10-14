/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.util;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Iterator;
import org.apache.cxf.common.util.ReflectionUtil;

public class ReflectionInvokationHandler
implements InvocationHandler {
    private Object target;

    public ReflectionInvokationHandler(Object obj) {
        this.target = obj;
    }

    public Object getTarget() {
        return this.target;
    }

    @Override
    public Object invoke(Object proxy, Method method2, Object[] args2) throws Throwable {
        WrapReturn wr = method2.getAnnotation(WrapReturn.class);
        Class<?> targetClass = this.target.getClass();
        Class<?>[] parameterTypes = this.getParameterTypes(method2, args2);
        try {
            Method m4;
            try {
                m4 = targetClass.getMethod(method2.getName(), parameterTypes);
            }
            catch (NoSuchMethodException nsme) {
                boolean[] optionals = new boolean[method2.getParameterTypes().length];
                int i = 0;
                int optionalNumber = 0;
                for (Annotation[] a : method2.getParameterAnnotations()) {
                    optionals[i] = false;
                    for (Annotation potential : a) {
                        if (!Optional.class.equals(potential.annotationType())) continue;
                        optionals[i] = true;
                        ++optionalNumber;
                        break;
                    }
                    ++i;
                }
                Class[] newParams = new Class[args2.length - optionalNumber];
                Object[] newArgs = new Object[args2.length - optionalNumber];
                int argI = 0;
                for (int j = 0; j < parameterTypes.length; ++j) {
                    if (optionals[j]) continue;
                    newArgs[argI] = args2[j];
                    newParams[argI] = parameterTypes[j];
                    ++argI;
                }
                m4 = targetClass.getMethod(method2.getName(), newParams);
                args2 = newArgs;
                parameterTypes = newParams;
            }
            ReflectionUtil.setAccessible(m4);
            return ReflectionInvokationHandler.wrapReturn(wr, m4.invoke(this.target, args2));
        }
        catch (InvocationTargetException e) {
            throw e.getCause();
        }
        catch (NoSuchMethodException e) {
            for (Method m22 : targetClass.getMethods()) {
                if (!m22.getName().equals(method2.getName()) || m22.getParameterTypes().length != method2.getParameterTypes().length) continue;
                boolean found = true;
                for (int x = 0; x < m22.getParameterTypes().length; ++x) {
                    if (args2[x] == null || m22.getParameterTypes()[x].isInstance(args2[x])) continue;
                    found = false;
                }
                if (!found) continue;
                ReflectionUtil.setAccessible(m22);
                return ReflectionInvokationHandler.wrapReturn(wr, m22.invoke(this.target, args2));
            }
            throw e;
        }
    }

    private Class<?>[] getParameterTypes(Method method2, Object[] args2) {
        Class<?>[] types = method2.getParameterTypes();
        Annotation[][] parAnnotations = method2.getParameterAnnotations();
        for (int x = 0; x < types.length; ++x) {
            UnwrapParam p = this.getUnwrapParam(parAnnotations[x]);
            if (p == null) continue;
            String s2 = p.methodName();
            String tn = p.typeMethodName();
            try {
                Method m4 = args2[x].getClass().getMethod(s2, new Class[0]);
                if ("#default".equals(tn)) {
                    types[x] = m4.getReturnType();
                } else {
                    Method m22 = args2[x].getClass().getMethod(tn, new Class[0]);
                    types[x] = (Class)ReflectionUtil.setAccessible(m22).invoke(args2[x], new Object[0]);
                }
                args2[x] = ReflectionUtil.setAccessible(m4).invoke(args2[x], new Object[0]);
                continue;
            }
            catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        return types;
    }

    private UnwrapParam getUnwrapParam(Annotation[] annotations) {
        for (Annotation a : annotations) {
            if (!(a instanceof UnwrapParam)) continue;
            return (UnwrapParam)a;
        }
        return null;
    }

    private static Object wrapReturn(WrapReturn wr, Object t2) {
        if (wr == null || t2 == null) {
            return t2;
        }
        if (wr.iterator()) {
            return new WrapperIterator(wr.value(), (Iterator)t2);
        }
        return ReflectionInvokationHandler.createProxyWrapper(t2, wr.value());
    }

    public static <T> T createProxyWrapper(Object target, Class<T> inf) {
        ReflectionInvokationHandler h2 = new ReflectionInvokationHandler(target);
        return inf.cast(Proxy.newProxyInstance(inf.getClassLoader(), new Class[]{inf}, h2));
    }

    private static class WrapperIterator
    implements Iterator<Object> {
        Class<?> cls;
        Iterator<?> internal;

        WrapperIterator(Class<?> c, Iterator<?> it) {
            this.internal = it;
            this.cls = c;
        }

        @Override
        public boolean hasNext() {
            return this.internal.hasNext();
        }

        @Override
        public Object next() {
            Object obj = this.internal.next();
            return ReflectionInvokationHandler.createProxyWrapper(obj, this.cls);
        }

        @Override
        public void remove() {
            this.internal.remove();
        }
    }

    @Target(value={ElementType.PARAMETER})
    @Retention(value=RetentionPolicy.RUNTIME)
    public static @interface UnwrapParam {
        public String methodName() default "getValue";

        public String typeMethodName() default "#default";
    }

    @Target(value={ElementType.METHOD})
    @Retention(value=RetentionPolicy.RUNTIME)
    public static @interface WrapReturn {
        public Class<?> value();

        public boolean iterator() default false;
    }

    @Target(value={ElementType.PARAMETER})
    @Retention(value=RetentionPolicy.RUNTIME)
    public static @interface Optional {
    }
}

