/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.oas.integration.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.core.converter.ModelConverter;
import io.swagger.v3.oas.integration.OpenApiConfigurationException;
import io.swagger.v3.oas.integration.api.ObjectMapperProcessor;
import io.swagger.v3.oas.integration.api.OpenAPIConfiguration;
import io.swagger.v3.oas.integration.api.OpenApiReader;
import io.swagger.v3.oas.integration.api.OpenApiScanner;
import io.swagger.v3.oas.models.OpenAPI;
import java.util.Set;

public interface OpenApiContext {
    public static final String OPENAPI_CONTEXT_ID_KEY = "openapi.context.id";
    public static final String OPENAPI_CONTEXT_ID_PREFIX = "openapi.context.id.";
    public static final String OPENAPI_CONTEXT_ID_DEFAULT = "openapi.context.id.default";

    public String getId();

    public OpenApiContext init() throws OpenApiConfigurationException;

    public OpenAPI read();

    public OpenAPIConfiguration getOpenApiConfiguration();

    public String getConfigLocation();

    public OpenApiContext getParent();

    public void setOpenApiScanner(OpenApiScanner var1);

    public void setOpenApiReader(OpenApiReader var1);

    public void setObjectMapperProcessor(ObjectMapperProcessor var1);

    public void setModelConverters(Set<ModelConverter> var1);

    public ObjectMapper getOutputJsonMapper();

    public ObjectMapper getOutputYamlMapper();

    public void setOutputJsonMapper(ObjectMapper var1);

    public void setOutputYamlMapper(ObjectMapper var1);
}

