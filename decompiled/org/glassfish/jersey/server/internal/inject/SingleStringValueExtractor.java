/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.inject;

import javax.ws.rs.core.MultivaluedMap;
import org.glassfish.jersey.server.internal.inject.MultivaluedParameterExtractor;

final class SingleStringValueExtractor
implements MultivaluedParameterExtractor<String> {
    private final String paramName;
    private final String defaultValue;

    public SingleStringValueExtractor(String parameterName, String defaultValue) {
        this.paramName = parameterName;
        this.defaultValue = defaultValue;
    }

    @Override
    public String getName() {
        return this.paramName;
    }

    @Override
    public String getDefaultValueString() {
        return this.defaultValue;
    }

    @Override
    public String extract(MultivaluedMap<String, String> parameters) {
        String value = parameters.getFirst(this.paramName);
        return value != null ? value : this.defaultValue;
    }
}

