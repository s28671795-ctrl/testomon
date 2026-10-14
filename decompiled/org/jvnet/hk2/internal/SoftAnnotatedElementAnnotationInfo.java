/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.hk2.internal;

import java.lang.annotation.Annotation;
import java.lang.ref.SoftReference;
import java.lang.reflect.AnnotatedElement;
import org.jvnet.hk2.internal.AnnotatedElementAnnotationInfo;
import org.jvnet.hk2.internal.Utilities;

class SoftAnnotatedElementAnnotationInfo {
    private final SoftReference<Annotation[]> elementAnnotationsReference;
    private final SoftReference<Annotation[][]> paramAnnotationsReference;
    private final boolean hasParams;
    private final boolean isConstructor;

    SoftAnnotatedElementAnnotationInfo(Annotation[] elementAnnotation, boolean hasParams, Annotation[][] paramAnnotation, boolean isConstructor) {
        this.elementAnnotationsReference = new SoftReference<Annotation[]>(elementAnnotation);
        this.hasParams = hasParams;
        this.paramAnnotationsReference = new SoftReference<Annotation[][]>(paramAnnotation);
        this.isConstructor = isConstructor;
    }

    AnnotatedElementAnnotationInfo harden(AnnotatedElement ae) {
        Annotation[] hardenedElementAnnotations = this.elementAnnotationsReference.get();
        Annotation[][] hardenedParamAnnotations = this.paramAnnotationsReference.get();
        if (!Utilities.USE_SOFT_REFERENCE || hardenedElementAnnotations == null || hardenedParamAnnotations == null) {
            return Utilities.computeAEAI(ae);
        }
        return new AnnotatedElementAnnotationInfo(hardenedElementAnnotations, this.hasParams, hardenedParamAnnotations, this.isConstructor);
    }
}

