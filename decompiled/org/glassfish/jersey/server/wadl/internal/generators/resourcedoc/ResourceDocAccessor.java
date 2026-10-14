/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.wadl.internal.generators.resourcedoc;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.jersey.server.internal.LocalizationMessages;
import org.glassfish.jersey.server.model.Parameter;
import org.glassfish.jersey.server.wadl.internal.generators.resourcedoc.model.AnnotationDocType;
import org.glassfish.jersey.server.wadl.internal.generators.resourcedoc.model.ClassDocType;
import org.glassfish.jersey.server.wadl.internal.generators.resourcedoc.model.MethodDocType;
import org.glassfish.jersey.server.wadl.internal.generators.resourcedoc.model.NamedValueType;
import org.glassfish.jersey.server.wadl.internal.generators.resourcedoc.model.ParamDocType;
import org.glassfish.jersey.server.wadl.internal.generators.resourcedoc.model.RepresentationDocType;
import org.glassfish.jersey.server.wadl.internal.generators.resourcedoc.model.ResourceDocType;
import org.glassfish.jersey.server.wadl.internal.generators.resourcedoc.model.ResponseDocType;

public class ResourceDocAccessor {
    private static final Logger LOGGER = Logger.getLogger(ResourceDocAccessor.class.getName());
    private ResourceDocType _resourceDoc;

    public ResourceDocAccessor(ResourceDocType resourceDoc) {
        this._resourceDoc = resourceDoc;
    }

    public ClassDocType getClassDoc(Class<?> resourceClass) {
        if (resourceClass == null) {
            return null;
        }
        for (ClassDocType classDocType : this._resourceDoc.getDocs()) {
            if (!resourceClass.getName().equals(classDocType.getClassName())) continue;
            return classDocType;
        }
        return null;
    }

    public MethodDocType getMethodDoc(Class<?> resourceClass, Method method2) {
        if (resourceClass == null || method2 == null) {
            return null;
        }
        ClassDocType classDoc = this.getClassDoc(resourceClass);
        if (classDoc == null) {
            return null;
        }
        MethodDocType candidate = null;
        int candidateCount = 0;
        String methodName = method2.getName();
        String methodSignature = this.computeSignature(method2);
        for (MethodDocType methodDocType : classDoc.getMethodDocs()) {
            String docMethodSignature;
            if (!methodName.equals(methodDocType.getMethodName())) continue;
            ++candidateCount;
            if (candidate == null) {
                candidate = methodDocType;
            }
            if ((docMethodSignature = methodDocType.getMethodSignature()) == null || !docMethodSignature.equals(methodSignature)) continue;
            return methodDocType;
        }
        if (candidate != null && candidateCount > 1 && LOGGER.isLoggable(Level.CONFIG)) {
            LOGGER.config(LocalizationMessages.WADL_RESOURCEDOC_AMBIGUOUS_METHOD_ENTRIES(resourceClass.getName(), methodName, methodSignature, candidateCount));
        }
        return candidate;
    }

    private String computeSignature(Method method2) {
        String methodAsString = method2.toGenericString();
        return methodAsString.substring(methodAsString.indexOf(40), methodAsString.lastIndexOf(41) + 1);
    }

    public ParamDocType getParamDoc(Class<?> resourceClass, Method method2, Parameter p) {
        MethodDocType methodDoc = this.getMethodDoc(resourceClass, method2);
        if (methodDoc != null) {
            for (ParamDocType paramDocType : methodDoc.getParamDocs()) {
                for (AnnotationDocType annotationDocType : paramDocType.getAnnotationDocs()) {
                    String sourceName;
                    Class<? extends Annotation> annotationType = p.getSourceAnnotation().annotationType();
                    if (annotationType == null || (sourceName = this.getSourceName(annotationDocType)) == null || !sourceName.equals(p.getSourceName())) continue;
                    return paramDocType;
                }
            }
        }
        return null;
    }

    public RepresentationDocType getRequestRepresentation(Class<?> resourceClass, Method method2, String mediaType) {
        if (mediaType == null) {
            return null;
        }
        MethodDocType methodDoc = this.getMethodDoc(resourceClass, method2);
        return methodDoc != null && methodDoc.getRequestDoc() != null && methodDoc.getRequestDoc().getRepresentationDoc() != null ? methodDoc.getRequestDoc().getRepresentationDoc() : null;
    }

    public ResponseDocType getResponse(Class<?> resourceClass, Method method2) {
        MethodDocType methodDoc = this.getMethodDoc(resourceClass, method2);
        return methodDoc != null && methodDoc.getResponseDoc() != null ? methodDoc.getResponseDoc() : null;
    }

    private String getSourceName(AnnotationDocType annotationDocType) {
        if (annotationDocType.hasAttributeDocs()) {
            for (NamedValueType namedValueType : annotationDocType.getAttributeDocs()) {
                if (!"value".equals(namedValueType.getName())) continue;
                return namedValueType.getValue();
            }
        }
        return null;
    }
}

