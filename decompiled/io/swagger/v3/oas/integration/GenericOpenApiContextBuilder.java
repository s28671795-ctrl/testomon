/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.oas.integration;

import io.swagger.v3.oas.integration.GenericOpenApiContext;
import io.swagger.v3.oas.integration.OpenApiConfigurationException;
import io.swagger.v3.oas.integration.OpenApiContextLocator;
import io.swagger.v3.oas.integration.api.OpenAPIConfiguration;
import io.swagger.v3.oas.integration.api.OpenApiContext;
import io.swagger.v3.oas.integration.api.OpenApiContextBuilder;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;

public class GenericOpenApiContextBuilder<T extends GenericOpenApiContextBuilder>
implements OpenApiContextBuilder {
    protected String ctxId;
    protected String configLocation;
    protected Set<String> resourcePackages;
    protected Set<String> resourceClasses;
    protected OpenAPIConfiguration openApiConfiguration;

    @Override
    public OpenApiContext buildContext(boolean init2) throws OpenApiConfigurationException {
        OpenApiContext ctx;
        if (StringUtils.isBlank(this.ctxId)) {
            this.ctxId = "openapi.context.id.default";
        }
        if ((ctx = OpenApiContextLocator.getInstance().getOpenApiContext(this.ctxId)) == null) {
            OpenApiContext rootCtx = OpenApiContextLocator.getInstance().getOpenApiContext("openapi.context.id.default");
            ctx = ((GenericOpenApiContext)((GenericOpenApiContext)new GenericOpenApiContext().openApiConfiguration(this.openApiConfiguration)).id(this.ctxId)).parent(rootCtx);
            if (ctx.getConfigLocation() == null && this.configLocation != null) {
                ((GenericOpenApiContext)ctx).configLocation(this.configLocation);
            }
            if (((GenericOpenApiContext)ctx).getResourcePackages() == null && this.resourcePackages != null) {
                ((GenericOpenApiContext)ctx).resourcePackages(this.resourcePackages);
            }
            if (((GenericOpenApiContext)ctx).getResourceClasses() == null && this.resourceClasses != null) {
                ((GenericOpenApiContext)ctx).resourceClasses(this.resourceClasses);
            }
            if (init2) {
                ctx.init();
            }
        }
        return ctx;
    }

    public String getCtxId() {
        return this.ctxId;
    }

    public void setCtxId(String ctxId) {
        this.ctxId = ctxId;
    }

    public String getConfigLocation() {
        return this.configLocation;
    }

    public void setConfigLocation(String configLocation) {
        this.configLocation = configLocation;
    }

    public Set<String> getResourcePackages() {
        return this.resourcePackages;
    }

    public void setResourcePackages(Set<String> resourcePackages) {
        this.resourcePackages = resourcePackages;
    }

    public OpenAPIConfiguration getOpenApiConfiguration() {
        return this.openApiConfiguration;
    }

    public void setOpenApiConfiguration(OpenAPIConfiguration openApiConfiguration) {
        this.openApiConfiguration = openApiConfiguration;
    }

    public T ctxId(String ctxId) {
        this.ctxId = ctxId;
        return (T)this;
    }

    public T configLocation(String configLocation) {
        this.configLocation = configLocation;
        return (T)this;
    }

    public T resourcePackages(Set<String> resourcePackages) {
        this.resourcePackages = resourcePackages;
        return (T)this;
    }

    public T openApiConfiguration(OpenAPIConfiguration openApiConfiguration) {
        this.openApiConfiguration = openApiConfiguration;
        return (T)this;
    }

    public Set<String> getResourceClasses() {
        return this.resourceClasses;
    }

    public void setResourceClasses(Set<String> resourceClasses) {
        this.resourceClasses = resourceClasses;
    }

    public T resourceClasses(Set<String> resourceClasses) {
        this.resourceClasses = resourceClasses;
        return (T)this;
    }
}

