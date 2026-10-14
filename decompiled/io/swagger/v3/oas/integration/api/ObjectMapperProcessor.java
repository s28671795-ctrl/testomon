/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.oas.integration.api;

import com.fasterxml.jackson.databind.ObjectMapper;

public interface ObjectMapperProcessor {
    default public void processJsonObjectMapper(ObjectMapper mapper2) {
    }

    @Deprecated
    default public void processYamlObjectMapper(ObjectMapper mapper2) {
    }

    default public void processOutputJsonObjectMapper(ObjectMapper mapper2) {
    }

    default public void processOutputYamlObjectMapper(ObjectMapper mapper2) {
        this.processOutputJsonObjectMapper(mapper2);
    }
}

