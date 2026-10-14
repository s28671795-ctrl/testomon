/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.core.util;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.SpecVersion;
import java.util.Map;
import java.util.Set;

public class OpenAPI30To31 {
    public void process(OpenAPI openAPI) {
        openAPI.openapi("3.1.0").jsonSchemaDialect("https://json-schema.org/draft/2020-12/schema").specVersion(SpecVersion.V31);
        this.removeReservedExtensionsName(openAPI.getExtensions());
        if (openAPI.getInfo() != null) {
            this.removeReservedExtensionsName(openAPI.getInfo().getExtensions());
        }
        if (openAPI.getPaths() != null) {
            this.removeReservedExtensionsName(openAPI.getPaths().getExtensions());
        }
        if (openAPI.getComponents() != null) {
            this.removeReservedExtensionsName(openAPI.getComponents().getExtensions());
        }
    }

    private void removeReservedExtensionsName(Map<String, Object> extensions) {
        if (extensions == null || extensions.isEmpty()) {
            return;
        }
        Set<String> keys2 = extensions.keySet();
        for (String key : keys2) {
            if (!key.startsWith("x-oas-") && !key.startsWith("x-oai-")) continue;
            extensions.remove(key);
        }
    }
}

