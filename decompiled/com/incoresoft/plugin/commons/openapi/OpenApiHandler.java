/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.openapi;

import com.incoresoft.plugin.commons.openapi.OpenAPIExtension;
import com.incoresoft.plugin.commons.openapi.OpenApiParser;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.swagger.v3.oas.models.OpenAPI;
import org.jetbrains.annotations.NotNull;

public class OpenApiHandler
implements Handler {
    private final String packageToScan;
    private final ClassLoader classLoader;

    public OpenApiHandler(String packageToScan, ClassLoader classLoader) {
        this.packageToScan = packageToScan;
        this.classLoader = classLoader;
    }

    @Override
    public void handle(@NotNull Context context) {
        OpenAPI parse = new OpenApiParser().parse(this.packageToScan, this.classLoader);
        context.result(OpenAPIExtension.toJSON(parse)).contentType("application/json");
    }
}

