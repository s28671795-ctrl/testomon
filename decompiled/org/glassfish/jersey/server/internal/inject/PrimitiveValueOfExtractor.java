/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.inject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MultivaluedMap;
import org.glassfish.jersey.internal.inject.ExtractorException;
import org.glassfish.jersey.server.internal.inject.MultivaluedParameterExtractor;

final class PrimitiveValueOfExtractor
implements MultivaluedParameterExtractor<Object> {
    private final Method valueOf;
    private final String parameter;
    private final String defaultStringValue;
    private final Object defaultValue;
    private final Object defaultPrimitiveTypeValue;

    public PrimitiveValueOfExtractor(Method valueOf, String parameter, String defaultStringValue, Object defaultPrimitiveTypeValue) {
        this.valueOf = valueOf;
        this.parameter = parameter;
        this.defaultStringValue = defaultStringValue;
        this.defaultValue = defaultStringValue != null ? this.getValue(defaultStringValue) : null;
        this.defaultPrimitiveTypeValue = defaultPrimitiveTypeValue;
    }

    @Override
    public String getName() {
        return this.parameter;
    }

    @Override
    public String getDefaultValueString() {
        return this.defaultStringValue;
    }

    private Object getValue(String v) {
        try {
            return this.valueOf.invoke(null, v);
        }
        catch (InvocationTargetException ex) {
            Throwable target = ex.getTargetException();
            if (target instanceof WebApplicationException) {
                throw (WebApplicationException)target;
            }
            throw new ExtractorException(target);
        }
        catch (Exception ex) {
            throw new ProcessingException(ex);
        }
    }

    @Override
    public Object extract(MultivaluedMap<String, String> parameters) {
        String v = parameters.getFirst(this.parameter);
        if (v != null && !v.trim().isEmpty()) {
            return this.getValue(v);
        }
        if (this.defaultValue != null) {
            return this.defaultValue;
        }
        return this.defaultPrimitiveTypeValue;
    }
}

