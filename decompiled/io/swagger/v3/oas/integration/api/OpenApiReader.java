/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.oas.integration.api;

import io.swagger.v3.oas.integration.api.OpenAPIConfiguration;
import io.swagger.v3.oas.models.OpenAPI;
import java.util.Map;
import java.util.Set;

public interface OpenApiReader {
    public void setConfiguration(OpenAPIConfiguration var1);

    public OpenAPI read(Set<Class<?>> var1, Map<String, Object> var2);
}

