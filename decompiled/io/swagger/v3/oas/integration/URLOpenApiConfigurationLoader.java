/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.oas.integration;

import io.swagger.v3.oas.integration.api.OpenAPIConfiguration;
import io.swagger.v3.oas.integration.api.OpenApiConfigurationLoader;
import java.io.IOException;

public class URLOpenApiConfigurationLoader
implements OpenApiConfigurationLoader {
    @Override
    public OpenAPIConfiguration load(String path) throws IOException {
        return null;
    }

    @Override
    public boolean exists(String path) {
        return false;
    }
}

