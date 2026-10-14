/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.pool.sizeof.filter;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public final class AnnotationProxyFactory {
    private AnnotationProxyFactory() {
    }

    public static <T extends Annotation> T getAnnotationProxy(Annotation customAnnotation, Class<T> referenceAnnotation) {
        AnnotationInvocationHandler handler = new AnnotationInvocationHandler(customAnnotation);
        return (T)((Annotation)Proxy.newProxyInstance(referenceAnnotation.getClassLoader(), new Class[]{referenceAnnotation}, handler));
    }

    private static class AnnotationInvocationHandler
    implements InvocationHandler {
        private final Annotation customAnnotation;

        public AnnotationInvocationHandler(Annotation customAnnotation) {
            this.customAnnotation = customAnnotation;
        }

        @Override
        public Object invoke(Object proxy, Method method2, Object[] args2) throws Throwable {
            Method methodOnCustom = this.getMatchingMethodOnGivenAnnotation(method2);
            if (methodOnCustom != null) {
                return methodOnCustom.invoke((Object)this.customAnnotation, args2);
            }
            Object defaultValue = method2.getDefaultValue();
            if (defaultValue != null) {
                return defaultValue;
            }
            throw new UnsupportedOperationException("The method \"" + method2.getName() + "\" does not exist in the custom annotation, and there is no default value for it in the reference annotation, please implement this method in your custom annotation.");
        }

        private Method getMatchingMethodOnGivenAnnotation(Method method2) {
            try {
                Method customMethod = this.customAnnotation.getClass().getDeclaredMethod(method2.getName(), method2.getParameterTypes());
                if (customMethod.getReturnType().isAssignableFrom(method2.getReturnType())) {
                    return customMethod;
                }
                return null;
            }
            catch (NoSuchMethodException e) {
                return null;
            }
        }
    }
}

