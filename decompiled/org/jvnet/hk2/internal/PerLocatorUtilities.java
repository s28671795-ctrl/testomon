/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.hk2.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.WeakHashMap;
import javax.inject.Inject;
import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.InjectionPointIndicator;
import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.utilities.general.Hk2ThreadLocal;
import org.glassfish.hk2.utilities.reflection.Pretty;
import org.jvnet.hk2.annotations.Service;
import org.jvnet.hk2.internal.AnnotatedElementAnnotationInfo;
import org.jvnet.hk2.internal.ProxyUtilities;
import org.jvnet.hk2.internal.ServiceLocatorImpl;
import org.jvnet.hk2.internal.SoftAnnotatedElementAnnotationInfo;
import org.jvnet.hk2.internal.Utilities;

public class PerLocatorUtilities {
    private final Hk2ThreadLocal<WeakHashMap<Class<?>, String>> threadLocalAutoAnalyzerNameCache = new Hk2ThreadLocal<WeakHashMap<Class<?>, String>>(){

        @Override
        protected WeakHashMap<Class<?>, String> initialValue() {
            return new WeakHashMap();
        }
    };
    private final Hk2ThreadLocal<WeakHashMap<AnnotatedElement, SoftAnnotatedElementAnnotationInfo>> threadLocalAnnotationCache = new Hk2ThreadLocal<WeakHashMap<AnnotatedElement, SoftAnnotatedElementAnnotationInfo>>(){

        @Override
        protected WeakHashMap<AnnotatedElement, SoftAnnotatedElementAnnotationInfo> initialValue() {
            return new WeakHashMap<AnnotatedElement, SoftAnnotatedElementAnnotationInfo>();
        }
    };
    private final Hk2ThreadLocal<WeakHashMap<AnnotatedElement, Boolean>> hasInjectCache = new Hk2ThreadLocal<WeakHashMap<AnnotatedElement, Boolean>>(){

        @Override
        protected WeakHashMap<AnnotatedElement, Boolean> initialValue() {
            return new WeakHashMap<AnnotatedElement, Boolean>();
        }
    };
    private volatile ProxyUtilities proxyUtilities;
    private final ServiceLocatorImpl parent;

    PerLocatorUtilities(ServiceLocatorImpl parent) {
        this.parent = parent;
    }

    boolean hasInjectAnnotation(AnnotatedElement annotated) {
        Annotation[][] allAnnotations;
        boolean isConstructor;
        WeakHashMap<AnnotatedElement, Boolean> cache = this.hasInjectCache.get();
        Boolean rv = cache.get(annotated);
        if (rv != null) {
            return rv;
        }
        for (Annotation anno : annotated.getAnnotations()) {
            if (anno.annotationType().getAnnotation(InjectionPointIndicator.class) != null) {
                cache.put(annotated, true);
                return true;
            }
            if (!this.parent.isInjectAnnotation(anno)) continue;
            cache.put(annotated, true);
            return true;
        }
        if (annotated instanceof Method) {
            Method m4 = (Method)annotated;
            isConstructor = false;
            allAnnotations = m4.getParameterAnnotations();
        } else if (annotated instanceof Constructor) {
            Constructor c = (Constructor)annotated;
            isConstructor = true;
            allAnnotations = c.getParameterAnnotations();
        } else {
            cache.put(annotated, false);
            return false;
        }
        Annotation[][] annotationArray = allAnnotations;
        int n = annotationArray.length;
        for (int i = 0; i < n; ++i) {
            Annotation[] allParamAnnotations;
            for (Annotation paramAnno : allParamAnnotations = annotationArray[i]) {
                if (paramAnno.annotationType().getAnnotation(InjectionPointIndicator.class) != null) {
                    cache.put(annotated, true);
                    return true;
                }
                if (!this.parent.isInjectAnnotation(paramAnno, isConstructor)) continue;
                cache.put(annotated, true);
                return true;
            }
        }
        cache.put(annotated, false);
        return false;
    }

    public String getAutoAnalyzerName(Class<?> c) {
        String retVal = this.threadLocalAutoAnalyzerNameCache.get().get(c);
        if (retVal != null) {
            return retVal;
        }
        Service s2 = c.getAnnotation(Service.class);
        if (s2 == null) {
            return null;
        }
        retVal = s2.analyzer();
        this.threadLocalAutoAnalyzerNameCache.get().put(c, retVal);
        return retVal;
    }

    public InjectionResolver<?> getInjectionResolver(ServiceLocatorImpl locator, Injectee injectee) throws IllegalStateException {
        return this.getInjectionResolver(locator, injectee.getParent(), injectee.getPosition());
    }

    InjectionResolver<?> getInjectionResolver(ServiceLocatorImpl locator, AnnotatedElement annotatedGuy) throws IllegalStateException {
        if (annotatedGuy instanceof Method || annotatedGuy instanceof Constructor) {
            throw new IllegalArgumentException("Annotated element '" + annotatedGuy + "' can be neither a Method nor a Constructor.");
        }
        return this.getInjectionResolver(locator, annotatedGuy, -1);
    }

    private InjectionResolver<?> getInjectionResolver(ServiceLocatorImpl locator, AnnotatedElement annotatedGuy, int position) throws IllegalStateException {
        boolean methodOrConstructor = annotatedGuy instanceof Method || annotatedGuy instanceof Constructor;
        Annotation injectAnnotation = this.getInjectAnnotation(locator, annotatedGuy, methodOrConstructor, position);
        Class injectType = injectAnnotation == null ? Inject.class : injectAnnotation.annotationType();
        InjectionResolver<?> retVal = locator.getInjectionResolver(injectType);
        if (retVal == null) {
            throw new IllegalStateException("There is no installed injection resolver for " + Pretty.clazz(injectType) + " for type " + annotatedGuy);
        }
        return retVal;
    }

    private Annotation getInjectAnnotation(ServiceLocatorImpl locator, AnnotatedElement annotated, boolean checkParams, int position) {
        AnnotatedElementAnnotationInfo annotationInfo = this.computeElementAnnotationInfo(annotated);
        if (checkParams && annotationInfo.hasParams) {
            for (Annotation paramAnno : annotationInfo.paramAnnotations[position]) {
                if (!locator.isInjectAnnotation(paramAnno, annotationInfo.isConstructor)) continue;
                return paramAnno;
            }
        }
        for (Annotation annotation : annotationInfo.elementAnnotations) {
            if (!locator.isInjectAnnotation(annotation)) continue;
            return annotation;
        }
        return null;
    }

    private AnnotatedElementAnnotationInfo computeElementAnnotationInfo(AnnotatedElement ae) {
        AnnotatedElementAnnotationInfo hard;
        SoftAnnotatedElementAnnotationInfo soft = this.threadLocalAnnotationCache.get().get(ae);
        if (soft != null) {
            hard = soft.harden(ae);
        } else {
            hard = Utilities.computeAEAI(ae);
            soft = hard.soften();
            this.threadLocalAnnotationCache.get().put(ae, soft);
        }
        return hard;
    }

    public synchronized void releaseCaches() {
        this.hasInjectCache.removeAll();
        if (this.proxyUtilities != null) {
            this.proxyUtilities.releaseCache();
        }
    }

    public void shutdown() {
        this.releaseCaches();
        this.threadLocalAutoAnalyzerNameCache.removeAll();
        this.threadLocalAnnotationCache.removeAll();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public ProxyUtilities getProxyUtilities() {
        if (this.proxyUtilities != null) {
            return this.proxyUtilities;
        }
        PerLocatorUtilities perLocatorUtilities = this;
        synchronized (perLocatorUtilities) {
            if (this.proxyUtilities != null) {
                return this.proxyUtilities;
            }
            this.proxyUtilities = new ProxyUtilities();
            return this.proxyUtilities;
        }
    }
}

