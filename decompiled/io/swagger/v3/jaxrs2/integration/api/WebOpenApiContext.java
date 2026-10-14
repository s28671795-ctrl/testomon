/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletConfig
 *  javax.servlet.ServletContext
 */
package io.swagger.v3.jaxrs2.integration.api;

import io.swagger.v3.oas.integration.api.OpenApiContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

public interface WebOpenApiContext
extends OpenApiContext {
    public ServletContext getServletContext();

    public ServletConfig getServletConfig();
}

