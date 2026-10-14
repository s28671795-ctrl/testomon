/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletConfig
 *  javax.servlet.ServletContext
 */
package io.swagger.v3.jaxrs2.integration;

import io.swagger.v3.jaxrs2.integration.JaxrsOpenApiContext;
import io.swagger.v3.jaxrs2.integration.ServletConfigContextUtils;
import io.swagger.v3.jaxrs2.integration.ServletOpenApiConfigurationLoader;
import io.swagger.v3.jaxrs2.integration.ServletPathConfigurationLoader;
import io.swagger.v3.jaxrs2.integration.api.WebOpenApiContext;
import io.swagger.v3.oas.integration.api.OpenApiConfigurationLoader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlWebOpenApiContext<T extends XmlWebOpenApiContext<T>>
extends JaxrsOpenApiContext<T>
implements WebOpenApiContext {
    private ServletContext servletContext;
    private ServletConfig servletConfig;
    Logger LOGGER = LoggerFactory.getLogger(XmlWebOpenApiContext.class);

    @Override
    public ServletContext getServletContext() {
        return this.servletContext;
    }

    @Override
    public ServletConfig getServletConfig() {
        return this.servletConfig;
    }

    public T servletConfig(ServletConfig servletConfig) {
        if (!ServletConfigContextUtils.isServletConfigAvailable(servletConfig)) {
            return (T)this;
        }
        this.servletConfig = servletConfig;
        this.servletContext = servletConfig.getServletContext();
        return (T)this;
    }

    @Override
    protected List<ImmutablePair<String, String>> getKnownLocations() {
        LinkedList<ImmutablePair<String, String>> locations = new LinkedList<ImmutablePair<String, String>>(Arrays.asList(new ImmutablePair<String, String>("servlet", "openApi.configuration.location"), new ImmutablePair<String, String>("servletpath", "openapi-configuration.yaml"), new ImmutablePair<String, String>("servletpath", "openapi-configuration.json"), new ImmutablePair<String, String>("servletpath", "WEB-INF/openapi-configuration.yaml"), new ImmutablePair<String, String>("servletpath", "WEB-INF/openapi-configuration.json"), new ImmutablePair<String, String>("servletpath", "openapi.yaml"), new ImmutablePair<String, String>("servletpath", "openapi.json"), new ImmutablePair<String, String>("servletpath", "WEB-INF/openapi.yaml"), new ImmutablePair<String, String>("servletpath", "WEB-INF/openapi.json")));
        locations.addAll(super.getKnownLocations());
        locations.add(new ImmutablePair<String, String>("servlet", ""));
        return locations;
    }

    @Override
    protected Map<String, OpenApiConfigurationLoader> getLocationLoaders() {
        Map<String, OpenApiConfigurationLoader> map = super.getLocationLoaders();
        map.put("servlet", new ServletOpenApiConfigurationLoader(this.servletConfig));
        map.put("servletpath", new ServletPathConfigurationLoader(this.servletConfig));
        return map;
    }
}

