/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletConfig
 */
package io.swagger.v3.jaxrs2.integration;

import io.swagger.v3.oas.integration.StringOpenApiConfigurationLoader;
import io.swagger.v3.oas.integration.api.OpenAPIConfiguration;
import java.io.IOException;
import javax.servlet.ServletConfig;
import org.apache.commons.lang3.StringUtils;

public class ServletPathConfigurationLoader
implements StringOpenApiConfigurationLoader {
    private ServletConfig servletConfig;

    public ServletPathConfigurationLoader(ServletConfig servletConfig) {
        this.servletConfig = servletConfig;
    }

    @Override
    public OpenAPIConfiguration load(String path) throws IOException {
        if (this.servletConfig == null) {
            return null;
        }
        if (StringUtils.isBlank(path)) {
            return null;
        }
        String sanitized = path.startsWith("/") ? path : "/" + path;
        String configString = this.readInputStreamToString(this.servletConfig.getServletContext().getResourceAsStream(sanitized));
        return this.deserializeConfig(path, configString);
    }

    @Override
    public boolean exists(String path) {
        if (this.servletConfig == null) {
            return false;
        }
        if (StringUtils.isBlank(path)) {
            return false;
        }
        String sanitized = path.startsWith("/") ? path : "/" + path;
        return this.servletConfig.getServletContext().getResourceAsStream(sanitized) != null;
    }
}

