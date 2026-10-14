/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.oas.integration;

import io.swagger.v3.core.util.Json;
import io.swagger.v3.core.util.Yaml;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.integration.api.OpenAPIConfiguration;
import io.swagger.v3.oas.integration.api.OpenApiConfigurationLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface StringOpenApiConfigurationLoader
extends OpenApiConfigurationLoader {
    public static final Logger LOGGER = LoggerFactory.getLogger(StringOpenApiConfigurationLoader.class);

    default public OpenAPIConfiguration deserializeConfig(String path, String configAsString) {
        try {
            if (path.toLowerCase().endsWith("json")) {
                return Json.mapper().readValue(configAsString, SwaggerConfiguration.class);
            }
            return Yaml.mapper().readValue(configAsString, SwaggerConfiguration.class);
        }
        catch (Exception e) {
            LOGGER.error("exception reading config: " + e.getMessage(), e);
            return null;
        }
    }
}

