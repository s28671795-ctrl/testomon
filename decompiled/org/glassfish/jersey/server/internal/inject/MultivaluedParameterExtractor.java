/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.inject;

import javax.ws.rs.core.MultivaluedMap;

public interface MultivaluedParameterExtractor<T> {
    public String getName();

    public String getDefaultValueString();

    public T extract(MultivaluedMap<String, String> var1);
}

