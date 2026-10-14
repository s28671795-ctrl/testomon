/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.core.util;

import com.fasterxml.jackson.databind.JavaType;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.util.Json;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ReferenceTypeUtils {
    private static Logger LOGGER = LoggerFactory.getLogger(ReferenceTypeUtils.class);

    public static boolean _isReferenceType(JavaType jtype) {
        return Arrays.asList("com.google.common.base.Optional", "java.util.Optional").contains(jtype.getRawClass().getCanonicalName()) || jtype.isReferenceType();
    }

    public static AnnotatedType unwrapReference(AnnotatedType type) {
        if (type == null) {
            return type;
        }
        if (type.getType() == null) {
            return null;
        }
        try {
            JavaType jtype = type.getType() instanceof JavaType ? (JavaType)type.getType() : Json.mapper().constructType(type.getType());
            if (ReferenceTypeUtils._isReferenceType(jtype)) {
                AnnotatedType aType = new AnnotatedType().type(jtype.containedType(0)).name(type.getName()).parent(type.getParent()).jsonUnwrappedHandler(type.getJsonUnwrappedHandler()).skipOverride(type.isSkipOverride()).schemaProperty(type.isSchemaProperty()).ctxAnnotations(type.getCtxAnnotations()).resolveAsRef(type.isResolveAsRef()).jsonViewAnnotation(type.getJsonViewAnnotation()).skipSchemaName(type.isSkipSchemaName()).skipJsonIdentity(type.isSkipJsonIdentity()).components(type.getComponents()).propertyName(type.getPropertyName());
                return aType;
            }
            return null;
        }
        catch (Exception e) {
            LOGGER.error("Error unwrapping optional", e);
            return null;
        }
    }
}

