/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.inject;

import javax.ws.rs.core.MultivaluedMap;
import org.glassfish.jersey.internal.inject.ExtractorException;
import org.glassfish.jersey.server.internal.LocalizationMessages;
import org.glassfish.jersey.server.internal.inject.MultivaluedParameterExtractor;

class PrimitiveCharacterExtractor
implements MultivaluedParameterExtractor<Object> {
    final String parameter;
    final String defaultStringValue;
    final Object defaultPrimitiveTypeValue;

    public PrimitiveCharacterExtractor(String parameter, String defaultStringValue, Object defaultPrimitiveTypeValue) {
        this.parameter = parameter;
        this.defaultStringValue = defaultStringValue;
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

    @Override
    public Object extract(MultivaluedMap<String, String> parameters) {
        String v = parameters.getFirst(this.parameter);
        if (v != null && !v.trim().isEmpty()) {
            if (v.length() == 1) {
                return Character.valueOf(v.charAt(0));
            }
            throw new ExtractorException(LocalizationMessages.ERROR_PARAMETER_INVALID_CHAR_VALUE(v));
        }
        if (this.defaultStringValue != null && !this.defaultStringValue.trim().isEmpty()) {
            if (this.defaultStringValue.length() == 1) {
                return Character.valueOf(this.defaultStringValue.charAt(0));
            }
            throw new ExtractorException(LocalizationMessages.ERROR_PARAMETER_INVALID_CHAR_VALUE(this.defaultStringValue));
        }
        return this.defaultPrimitiveTypeValue;
    }
}

