/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.vms.http;

import com.google.inject.Inject;
import com.incoresoft.middleware.http.AppRole;
import com.incoresoft.plugin.commons.openapi.OpenApiHandler;
import io.javalin.Javalin;
import io.javalin.openapi.plugin.redoc.ReDocHandler;
import io.javalin.openapi.plugin.swagger.SwaggerHandler;

public abstract class DocsController {
    private final Javalin javalin;
    private final String pluginName;

    @Inject
    public void init() {
        this.javalin.get("openapi/" + this.pluginName, new OpenApiHandler(this.getClass().getPackage().getName(), this.getClass().getClassLoader()), AppRole.ANYONE);
        this.javalin.get("swagger/" + this.pluginName, new SwaggerHandler(this.pluginName, "/openapi/" + this.pluginName, "5.9.0", null, ""), AppRole.ANYONE);
        this.javalin.get("redoc/" + this.pluginName, new ReDocHandler(this.pluginName, "/openapi/" + this.pluginName, "2.0.0", "", ""), AppRole.ANYONE);
    }

    public DocsController(Javalin javalin, String pluginName) {
        this.javalin = javalin;
        this.pluginName = pluginName;
    }
}

