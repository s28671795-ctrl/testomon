/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.Encoded;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import org.glassfish.jersey.internal.util.ReflectionHelper;

public class AnnotatedMethod
implements AnnotatedElement {
    private static final Set<Class<? extends Annotation>> METHOD_META_ANNOTATIONS = AnnotatedMethod.getSet(HttpMethod.class);
    private static final Set<Class<? extends Annotation>> METHOD_ANNOTATIONS = AnnotatedMethod.getSet(Path.class, Produces.class, Consumes.class);
    private static final Set<Class<? extends Annotation>> PARAMETER_ANNOTATIONS = AnnotatedMethod.getSet(Context.class, Encoded.class, DefaultValue.class, MatrixParam.class, QueryParam.class, CookieParam.class, HeaderParam.class, PathParam.class, FormParam.class);
    private final Method m;
    private final Method am;
    private final Annotation[] methodAnnotations;
    private final Annotation[][] parameterAnnotations;

    @SafeVarargs
    private static Set<Class<? extends Annotation>> getSet(Class<? extends Annotation> ... cs) {
        HashSet<Class<? extends Annotation>> s2 = new HashSet<Class<? extends Annotation>>();
        s2.addAll(Arrays.asList(cs));
        return s2;
    }

    public AnnotatedMethod(Method method2) {
        this.m = method2;
        this.am = AnnotatedMethod.findAnnotatedMethod(method2);
        if (method2.equals(this.am)) {
            this.methodAnnotations = method2.getAnnotations();
            this.parameterAnnotations = method2.getParameterAnnotations();
        } else {
            this.methodAnnotations = AnnotatedMethod.mergeMethodAnnotations(method2, this.am);
            this.parameterAnnotations = AnnotatedMethod.mergeParameterAnnotations(method2, this.am);
        }
    }

    public Method getMethod() {
        return this.am;
    }

    public Method getDeclaredMethod() {
        return this.m;
    }

    public Annotation[][] getParameterAnnotations() {
        return (Annotation[][])this.parameterAnnotations.clone();
    }

    public Class<?>[] getParameterTypes() {
        return this.am.getParameterTypes();
    }

    public TypeVariable<Method>[] getTypeParameters() {
        return this.am.getTypeParameters();
    }

    public Type[] getGenericParameterTypes() {
        return this.am.getGenericParameterTypes();
    }

    public <T extends Annotation> List<T> getMetaMethodAnnotations(Class<T> annotation) {
        ArrayList<T> ma = new ArrayList<T>();
        for (Annotation a : this.methodAnnotations) {
            T metaAnnotation = a.annotationType().getAnnotation(annotation);
            if (metaAnnotation == null) continue;
            ma.add(metaAnnotation);
        }
        return ma;
    }

    public String toString() {
        return this.m.toString();
    }

    @Override
    public boolean isAnnotationPresent(Class<? extends Annotation> annotationType) {
        for (Annotation ma : this.methodAnnotations) {
            if (ma.annotationType() != annotationType) continue;
            return true;
        }
        return false;
    }

    @Override
    public <T extends Annotation> T getAnnotation(Class<T> annotationType) {
        for (Annotation ma : this.methodAnnotations) {
            if (ma.annotationType() != annotationType) continue;
            return (T)((Annotation)annotationType.cast(ma));
        }
        return this.am.getAnnotation(annotationType);
    }

    @Override
    public Annotation[] getAnnotations() {
        return (Annotation[])this.methodAnnotations.clone();
    }

    @Override
    public Annotation[] getDeclaredAnnotations() {
        return this.getAnnotations();
    }

    private static Annotation[] mergeMethodAnnotations(Method m4, Method am) {
        List<Annotation> al = AnnotatedMethod.asList(m4.getAnnotations());
        for (Annotation a : am.getAnnotations()) {
            if (m4.isAnnotationPresent(a.getClass())) continue;
            al.add(a);
        }
        return al.toArray(new Annotation[al.size()]);
    }

    private static Annotation[][] mergeParameterAnnotations(Method m4, Method am) {
        Annotation[][] methodParamAnnotations = m4.getParameterAnnotations();
        Annotation[][] annotatedMethodParamAnnotations = am.getParameterAnnotations();
        ArrayList<List<Annotation>> methodParamAnnotationsList = new ArrayList<List<Annotation>>();
        for (int i = 0; i < methodParamAnnotations.length; ++i) {
            List<Annotation> al = AnnotatedMethod.asList(methodParamAnnotations[i]);
            for (Annotation a : annotatedMethodParamAnnotations[i]) {
                if (!AnnotatedMethod.annotationNotInList(a.getClass(), al)) continue;
                al.add(a);
            }
            methodParamAnnotationsList.add(al);
        }
        Annotation[][] mergedAnnotations = new Annotation[methodParamAnnotations.length][];
        for (int i = 0; i < methodParamAnnotations.length; ++i) {
            List paramAnnotations = (List)methodParamAnnotationsList.get(i);
            mergedAnnotations[i] = paramAnnotations.toArray(new Annotation[paramAnnotations.size()]);
        }
        return mergedAnnotations;
    }

    private static boolean annotationNotInList(Class<? extends Annotation> ca, List<Annotation> la) {
        for (Annotation a : la) {
            if (ca != a.getClass()) continue;
            return false;
        }
        return true;
    }

    private static Method findAnnotatedMethod(Method m4) {
        Method am = AnnotatedMethod.findAnnotatedMethod(m4.getDeclaringClass(), m4);
        return am != null ? am : m4;
    }

    private static Method findAnnotatedMethod(Class<?> c, Method m4) {
        Method sm;
        if (c == Object.class) {
            return null;
        }
        if ((m4 = AccessController.doPrivileged(ReflectionHelper.findMethodOnClassPA(c, m4))) == null) {
            return null;
        }
        if (AnnotatedMethod.hasAnnotations(m4)) {
            return m4;
        }
        Class<?> sc = c.getSuperclass();
        if (sc != null && sc != Object.class && (sm = AnnotatedMethod.findAnnotatedMethod(sc, m4)) != null) {
            return sm;
        }
        for (Class<?> ic : c.getInterfaces()) {
            Method im = AnnotatedMethod.findAnnotatedMethod(ic, m4);
            if (im == null) continue;
            return im;
        }
        return null;
    }

    private static boolean hasAnnotations(Method m4) {
        return AnnotatedMethod.hasMetaMethodAnnotations(m4) || AnnotatedMethod.hasMethodAnnotations(m4) || AnnotatedMethod.hasParameterAnnotations(m4);
    }

    private static boolean hasMetaMethodAnnotations(Method m4) {
        for (Class<? extends Annotation> ac : METHOD_META_ANNOTATIONS) {
            for (Annotation a : m4.getAnnotations()) {
                if (a.annotationType().getAnnotation(ac) == null) continue;
                return true;
            }
        }
        return false;
    }

    private static boolean hasMethodAnnotations(Method m4) {
        for (Class<? extends Annotation> ac : METHOD_ANNOTATIONS) {
            if (!m4.isAnnotationPresent(ac)) continue;
            return true;
        }
        return false;
    }

    private static boolean hasParameterAnnotations(Method m4) {
        Annotation[][] annotationArray = m4.getParameterAnnotations();
        int n = annotationArray.length;
        for (int i = 0; i < n; ++i) {
            Annotation[] as;
            for (Annotation a : as = annotationArray[i]) {
                if (!PARAMETER_ANNOTATIONS.contains(a.annotationType())) continue;
                return true;
            }
        }
        return false;
    }

    @SafeVarargs
    private static <T> List<T> asList(T ... ts) {
        ArrayList<T> l = new ArrayList<T>();
        l.addAll(Arrays.asList(ts));
        return l;
    }
}

