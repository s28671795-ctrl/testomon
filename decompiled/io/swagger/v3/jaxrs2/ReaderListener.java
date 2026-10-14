/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.jaxrs2;

import io.swagger.v3.oas.integration.api.OpenApiReader;
import io.swagger.v3.oas.models.OpenAPI;

public interface ReaderListener {
    public void beforeScan(OpenApiReader var1, OpenAPI var2);

    public void afterScan(OpenApiReader var1, OpenAPI var2);
}

