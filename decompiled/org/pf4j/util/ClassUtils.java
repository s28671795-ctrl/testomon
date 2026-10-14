/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

public class ClassUtils {
    public static List<String> getAllInterfacesNames(Class<?> aClass) {
        return ClassUtils.toString(ClassUtils.getAllInterfaces(aClass));
    }

    public static List<Class<?>> getAllInterfaces(Class<?> aClass) {
        ArrayList list = new ArrayList();
        while (aClass != null) {
            Class<?>[] interfaces;
            for (Class<?> anInterface : interfaces = aClass.getInterfaces()) {
                if (!list.contains(anInterface)) {
                    list.add(anInterface);
                }
                List<Class<?>> superInterfaces = ClassUtils.getAllInterfaces(anInterface);
                for (Class<?> superInterface : superInterfaces) {
                    if (list.contains(superInterface)) continue;
                    list.add(superInterface);
                }
            }
            aClass = aClass.getSuperclass();
        }
        return list;
    }

    public static AnnotationMirror getAnnotationMirror(TypeElement typeElement, Class<?> annotationClass) {
        String annotationClassName = annotationClass.getName();
        for (AnnotationMirror annotationMirror : typeElement.getAnnotationMirrors()) {
            if (!annotationMirror.getAnnotationType().toString().equals(annotationClassName)) continue;
            return annotationMirror;
        }
        return null;
    }

    public static AnnotationValue getAnnotationValue(AnnotationMirror annotationMirror, String annotationParameter) {
        for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : annotationMirror.getElementValues().entrySet()) {
            if (!entry.getKey().getSimpleName().toString().equals(annotationParameter)) continue;
            return entry.getValue();
        }
        return null;
    }

    public static AnnotationValue getAnnotationValue(TypeElement typeElement, Class<?> annotationClass, String annotationParameter) {
        AnnotationMirror annotationMirror = ClassUtils.getAnnotationMirror(typeElement, annotationClass);
        return annotationMirror != null ? ClassUtils.getAnnotationValue(annotationMirror, annotationParameter) : null;
    }

    private static List<String> toString(List<Class<?>> classes) {
        ArrayList<String> list = new ArrayList<String>();
        for (Class<?> aClass : classes) {
            list.add(aClass.getSimpleName());
        }
        return list;
    }
}

