/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.oas.integration.api;

import io.swagger.v3.oas.integration.OpenApiConfigurationException;
import io.swagger.v3.oas.integration.api.OpenApiContext;

public interface OpenApiContextBuilder {
    public OpenApiContext buildContext(boolean var1) throws OpenApiConfigurationException;
}

