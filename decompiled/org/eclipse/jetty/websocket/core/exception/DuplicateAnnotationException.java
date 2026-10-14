/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.exception;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import org.eclipse.jetty.websocket.core.exception.InvalidWebSocketException;
import org.eclipse.jetty.websocket.core.internal.util.ReflectUtils;

public class DuplicateAnnotationException
extends InvalidWebSocketException {
    public static DuplicateAnnotationException build(Class<?> pojo, Class<? extends Annotation> annoClass, Method ... methods) {
        StringBuilder err = new StringBuilder();
        err.append("Duplicate @");
        err.append(annoClass.getSimpleName());
        err.append(" declarations in: ");
        err.append(pojo.getName());
        for (Method method2 : methods) {
            err.append(System.lineSeparator());
            ReflectUtils.append(err, method2);
        }
        return new DuplicateAnnotationException(err.toString());
    }

    public DuplicateAnnotationException(String message) {
        super(message);
    }

    public DuplicateAnnotationException(String message, Throwable cause) {
        super(message, cause);
    }
}

