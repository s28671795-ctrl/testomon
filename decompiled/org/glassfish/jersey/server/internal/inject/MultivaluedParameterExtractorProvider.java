/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.inject;

import org.glassfish.jersey.model.Parameter;
import org.glassfish.jersey.server.internal.inject.MultivaluedParameterExtractor;

public interface MultivaluedParameterExtractorProvider {
    public MultivaluedParameterExtractor<?> get(Parameter var1);
}

