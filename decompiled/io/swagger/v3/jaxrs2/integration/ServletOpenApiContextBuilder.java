/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletConfig
 */
package io.swagger.v3.jaxrs2.integration;

import io.swagger.v3.jaxrs2.integration.XmlWebOpenApiContext;
import io.swagger.v3.oas.integration.GenericOpenApiContext;
import io.swagger.v3.oas.integration.GenericOpenApiContextBuilder;
import io.swagger.v3.oas.integration.OpenApiConfigurationException;
import io.swagger.v3.oas.integration.OpenApiContextLocator;
import io.swagger.v3.oas.integration.api.OpenApiContext;
import javax.servlet.ServletConfig;
import org.apache.commons.lang3.StringUtils;

public class ServletOpenApiContextBuilder<T extends ServletOpenApiContextBuilder>
extends GenericOpenApiContextBuilder<ServletOpenApiContextBuilder> {
    protected ServletConfig servletConfig;

    @Override
    public OpenApiContext buildContext(boolean init2) throws OpenApiConfigurationException {
        OpenApiContext ctx;
        if (StringUtils.isBlank(this.ctxId)) {
            this.ctxId = "openapi.context.id.default";
        }
        if ((ctx = OpenApiContextLocator.getInstance().getOpenApiContext(this.ctxId)) == null) {
            OpenApiContext rootCtx = OpenApiContextLocator.getInstance().getOpenApiContext("openapi.context.id.default");
            ctx = ((GenericOpenApiContext)((GenericOpenApiContext)((GenericOpenApiContext)new XmlWebOpenApiContext().servletConfig(this.servletConfig)).openApiConfiguration(this.openApiConfiguration)).id(this.ctxId)).parent(rootCtx);
            if (ctx.getConfigLocation() == null && this.configLocation != null) {
                ((XmlWebOpenApiContext)ctx).configLocation(this.configLocation);
            }
            if (((XmlWebOpenApiContext)ctx).getResourcePackages() == null && this.resourcePackages != null) {
                ((XmlWebOpenApiContext)ctx).resourcePackages(this.resourcePackages);
            }
            if (((XmlWebOpenApiContext)ctx).getResourceClasses() == null && this.resourceClasses != null) {
                ((XmlWebOpenApiContext)ctx).resourceClasses(this.resourceClasses);
            }
            if (init2) {
                ctx.init();
            }
        }
        return ctx;
    }

    public ServletConfig getServletConfig() {
        return this.servletConfig;
    }

    public void setServletConfig(ServletConfig servletConfig) {
        this.servletConfig = servletConfig;
    }

    public T servletConfig(ServletConfig servletConfig) {
        this.servletConfig = servletConfig;
        return (T)this;
    }
}

