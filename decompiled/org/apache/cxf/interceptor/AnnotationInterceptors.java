/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.interceptor;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.common.i18n.BundleUtils;
import org.apache.cxf.feature.Feature;
import org.apache.cxf.feature.Features;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.InFaultInterceptors;
import org.apache.cxf.interceptor.InInterceptors;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.interceptor.OutFaultInterceptors;
import org.apache.cxf.interceptor.OutInterceptors;
import org.apache.cxf.message.Message;

public class AnnotationInterceptors {
    private static final ResourceBundle BUNDLE = BundleUtils.getBundle(AnnotationInterceptors.class);
    private Class<?>[] clazzes;

    public AnnotationInterceptors(Class<?> ... clz) {
        this.clazzes = clz;
    }

    private <T> List<T> getAnnotationObject(Class<? extends Annotation> annotationClazz, Class<T> type) {
        for (Class<?> cls : this.clazzes) {
            Annotation annotation = cls.getAnnotation(annotationClazz);
            if (annotation == null) continue;
            return this.initializeAnnotationObjects(annotation, type);
        }
        return null;
    }

    private <T> List<T> initializeAnnotationObjects(Annotation annotation, Class<T> type) {
        ArrayList<T> list = new ArrayList<T>();
        for (String string : this.getAnnotationObjectNames(annotation)) {
            list.add(this.initializeAnnotationObject(string, type));
        }
        for (Class<T> clazz : this.getAnnotationObjectClasses(annotation, type)) {
            list.add(this.initializeAnnotationObject(clazz));
        }
        return list;
    }

    private <T> Class<? extends T>[] getAnnotationObjectClasses(Annotation ann, Class<T> type) {
        if (ann instanceof InFaultInterceptors) {
            return ((InFaultInterceptors)ann).classes();
        }
        if (ann instanceof InInterceptors) {
            return ((InInterceptors)ann).classes();
        }
        if (ann instanceof OutFaultInterceptors) {
            return ((OutFaultInterceptors)ann).classes();
        }
        if (ann instanceof OutInterceptors) {
            return ((OutInterceptors)ann).classes();
        }
        if (ann instanceof Features) {
            return ((Features)ann).classes();
        }
        throw new UnsupportedOperationException("Doesn't support the annotation: " + ann);
    }

    private String[] getAnnotationObjectNames(Annotation ann) {
        if (ann instanceof InFaultInterceptors) {
            return ((InFaultInterceptors)ann).interceptors();
        }
        if (ann instanceof InInterceptors) {
            return ((InInterceptors)ann).interceptors();
        }
        if (ann instanceof OutFaultInterceptors) {
            return ((OutFaultInterceptors)ann).interceptors();
        }
        if (ann instanceof OutInterceptors) {
            return ((OutInterceptors)ann).interceptors();
        }
        if (ann instanceof Features) {
            return ((Features)ann).features();
        }
        throw new UnsupportedOperationException("Doesn't support the annotation: " + ann);
    }

    private <T> T initializeAnnotationObject(String annObjectName, Class<T> type) {
        Object object = null;
        try {
            object = ClassLoaderUtils.loadClass(annObjectName, this.getClass()).newInstance();
            return type.cast(object);
        }
        catch (Throwable e) {
            throw new Fault(new org.apache.cxf.common.i18n.Message("COULD_NOT_CREATE_ANNOTATION_OBJECT", BUNDLE, annObjectName), e);
        }
    }

    private <T> T initializeAnnotationObject(Class<T> type) {
        Object object = null;
        try {
            object = type.newInstance();
            return type.cast(object);
        }
        catch (Throwable e) {
            throw new Fault(new org.apache.cxf.common.i18n.Message("COULD_NOT_CREATE_ANNOTATION_OBJECT", BUNDLE, type.getName()), e);
        }
    }

    private List<Interceptor<? extends Message>> getAnnotationInterceptorList(Class<? extends Annotation> t2) {
        List<Interceptor> i = this.getAnnotationObject(t2, Interceptor.class);
        if (i == null) {
            return null;
        }
        ArrayList<Interceptor<? extends Message>> m4 = new ArrayList<Interceptor<? extends Message>>();
        for (Interceptor i2 : i) {
            m4.add(i2);
        }
        return m4;
    }

    public List<Interceptor<? extends Message>> getInFaultInterceptors() {
        return this.getAnnotationInterceptorList(InFaultInterceptors.class);
    }

    public List<Interceptor<? extends Message>> getInInterceptors() {
        return this.getAnnotationInterceptorList(InInterceptors.class);
    }

    public List<Interceptor<? extends Message>> getOutFaultInterceptors() {
        return this.getAnnotationInterceptorList(OutFaultInterceptors.class);
    }

    public List<Interceptor<? extends Message>> getOutInterceptors() {
        return this.getAnnotationInterceptorList(OutInterceptors.class);
    }

    public List<Feature> getFeatures() {
        return this.getAnnotationObject(Features.class, Feature.class);
    }
}

