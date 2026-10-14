/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.oas.integration.api;

import io.swagger.v3.oas.integration.api.OpenAPIConfiguration;
import java.util.Map;
import java.util.Set;

public interface OpenApiScanner {
    public void setConfiguration(OpenAPIConfiguration var1);

    public Set<Class<?>> classes();

    public Map<String, Object> resources();
}

