/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.oas.integration.api;

import io.swagger.v3.oas.models.OpenAPI;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface OpenAPIConfiguration {
    public Set<String> getResourcePackages();

    public Set<String> getResourceClasses();

    public String getReaderClass();

    public String getScannerClass();

    public String getFilterClass();

    public Collection<String> getIgnoredRoutes();

    public OpenAPI getOpenAPI();

    public Map<String, Object> getUserDefinedOptions();

    public Boolean isReadAllResources();

    public Boolean isPrettyPrint();

    public Long getCacheTTL();

    public String getObjectMapperProcessorClass();

    public Set<String> getModelConverterClasses();

    public Boolean isSortOutput();

    public Boolean isAlwaysResolveAppPath();

    public Boolean isSkipResolveAppPath();

    public Boolean isOpenAPI31();

    public Boolean isConvertToOpenAPI31();

    public String getDefaultResponseCode();
}

