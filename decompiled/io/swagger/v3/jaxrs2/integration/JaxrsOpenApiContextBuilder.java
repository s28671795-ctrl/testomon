/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletConfig
 */
package io.swagger.v3.jaxrs2.integration;

import io.swagger.v3.jaxrs2.integration.JaxrsOpenApiContext;
import io.swagger.v3.jaxrs2.integration.XmlWebOpenApiContext;
import io.swagger.v3.oas.integration.GenericOpenApiContext;
import io.swagger.v3.oas.integration.GenericOpenApiContextBuilder;
import io.swagger.v3.oas.integration.OpenApiConfigurationException;
import io.swagger.v3.oas.integration.OpenApiContextLocator;
import io.swagger.v3.oas.integration.api.OpenApiContext;
import javax.servlet.ServletConfig;
import javax.ws.rs.core.Application;
import org.apache.commons.lang3.StringUtils;

public class JaxrsOpenApiContextBuilder<T extends JaxrsOpenApiContextBuilder>
extends GenericOpenApiContextBuilder<JaxrsOpenApiContextBuilder> {
    protected Application application;
    protected ServletConfig servletConfig;

    @Override
    public OpenApiContext buildContext(boolean init2) throws OpenApiConfigurationException {
        OpenApiContext ctx;
        if (StringUtils.isBlank(this.ctxId)) {
            this.ctxId = "openapi.context.id.default";
        }
        if ((ctx = OpenApiContextLocator.getInstance().getOpenApiContext(this.ctxId)) == null) {
            OpenApiContext rootCtx = OpenApiContextLocator.getInstance().getOpenApiContext("openapi.context.id.default");
            ctx = ((GenericOpenApiContext)((GenericOpenApiContext)((GenericOpenApiContext)((JaxrsOpenApiContext)new XmlWebOpenApiContext().servletConfig(this.servletConfig)).app(this.application)).openApiConfiguration(this.openApiConfiguration)).id(this.ctxId)).parent(rootCtx);
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

    public Application getApplication() {
        return this.application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public ServletConfig getServletConfig() {
        return this.servletConfig;
    }

    public void setServletConfig(ServletConfig servletConfig) {
        this.servletConfig = servletConfig;
    }

    public T application(Application application) {
        this.application = application;
        return (T)this;
    }

    public T servletConfig(ServletConfig servletConfig) {
        this.servletConfig = servletConfig;
        return (T)this;
    }
}

