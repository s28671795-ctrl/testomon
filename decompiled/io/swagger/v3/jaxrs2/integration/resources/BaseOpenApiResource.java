/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletConfig
 */
package io.swagger.v3.jaxrs2.integration.resources;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import io.swagger.v3.core.filter.OpenAPISpecFilter;
import io.swagger.v3.core.filter.SpecFilter;
import io.swagger.v3.jaxrs2.integration.JaxrsOpenApiContextBuilder;
import io.swagger.v3.jaxrs2.integration.ServletConfigContextUtils;
import io.swagger.v3.oas.integration.GenericOpenApiContextBuilder;
import io.swagger.v3.oas.integration.api.OpenAPIConfiguration;
import io.swagger.v3.oas.integration.api.OpenApiContext;
import io.swagger.v3.oas.models.OpenAPI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletConfig;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseOpenApiResource {
    private static Logger LOGGER = LoggerFactory.getLogger(BaseOpenApiResource.class);
    protected String configLocation;
    protected Set<String> resourcePackages;
    protected OpenAPIConfiguration openApiConfiguration;

    protected String getContextId(ServletConfig config) {
        return ServletConfigContextUtils.getContextIdFromServletConfig(config);
    }

    protected Response getOpenApi(HttpHeaders headers, ServletConfig config, Application app, UriInfo uriInfo, String type) throws Exception {
        boolean pretty;
        String ctxId = this.getContextId(config);
        OpenApiContext ctx = ((GenericOpenApiContextBuilder)((GenericOpenApiContextBuilder)((GenericOpenApiContextBuilder)((GenericOpenApiContextBuilder)((GenericOpenApiContextBuilder)((JaxrsOpenApiContextBuilder)new JaxrsOpenApiContextBuilder().servletConfig(config)).application(app)).resourcePackages(this.resourcePackages)).configLocation(this.configLocation)).openApiConfiguration(this.openApiConfiguration)).ctxId(ctxId)).buildContext(true);
        OpenAPI oas = ctx.read();
        boolean bl = pretty = ctx.getOpenApiConfiguration() != null && Boolean.TRUE.equals(ctx.getOpenApiConfiguration().isPrettyPrint());
        if (oas != null && ctx.getOpenApiConfiguration() != null && ctx.getOpenApiConfiguration().getFilterClass() != null) {
            try {
                OpenAPISpecFilter filterImpl = (OpenAPISpecFilter)Class.forName(ctx.getOpenApiConfiguration().getFilterClass()).newInstance();
                SpecFilter f = new SpecFilter();
                oas = f.filter(oas, filterImpl, BaseOpenApiResource.getQueryParams(uriInfo.getQueryParameters()), BaseOpenApiResource.getCookies(headers), BaseOpenApiResource.getHeaders(headers));
            }
            catch (Exception e) {
                LOGGER.error("failed to load filter", e);
            }
        }
        if (oas == null) {
            return Response.status(404).build();
        }
        if (StringUtils.isNotBlank(type) && type.trim().equalsIgnoreCase("yaml")) {
            return Response.status(Response.Status.OK).entity(pretty ? ctx.getOutputYamlMapper().writer(new DefaultPrettyPrinter()).writeValueAsString(oas) : ctx.getOutputYamlMapper().writeValueAsString(oas)).type("application/yaml").build();
        }
        return Response.status(Response.Status.OK).entity(pretty ? ctx.getOutputJsonMapper().writer(new DefaultPrettyPrinter()).writeValueAsString(oas) : ctx.getOutputJsonMapper().writeValueAsString(oas)).type(MediaType.APPLICATION_JSON_TYPE).build();
    }

    private static Map<String, List<String>> getQueryParams(MultivaluedMap<String, String> params) {
        HashMap<String, List<String>> output = new HashMap<String, List<String>>();
        if (params != null) {
            params.forEach(output::put);
        }
        return output;
    }

    private static Map<String, String> getCookies(HttpHeaders headers) {
        HashMap<String, String> output = new HashMap<String, String>();
        if (headers != null) {
            headers.getCookies().forEach((k, v) -> output.put((String)k, v.getValue()));
        }
        return output;
    }

    private static Map<String, List<String>> getHeaders(HttpHeaders headers) {
        HashMap<String, List<String>> output = new HashMap<String, List<String>>();
        if (headers != null) {
            headers.getRequestHeaders().forEach(output::put);
        }
        return output;
    }

    public String getConfigLocation() {
        return this.configLocation;
    }

    public void setConfigLocation(String configLocation) {
        this.configLocation = configLocation;
    }

    public BaseOpenApiResource configLocation(String configLocation) {
        this.setConfigLocation(configLocation);
        return this;
    }

    public Set<String> getResourcePackages() {
        return this.resourcePackages;
    }

    public void setResourcePackages(Set<String> resourcePackages) {
        this.resourcePackages = resourcePackages;
    }

    public BaseOpenApiResource resourcePackages(Set<String> resourcePackages) {
        this.setResourcePackages(resourcePackages);
        return this;
    }

    public OpenAPIConfiguration getOpenApiConfiguration() {
        return this.openApiConfiguration;
    }

    public void setOpenApiConfiguration(OpenAPIConfiguration openApiConfiguration) {
        this.openApiConfiguration = openApiConfiguration;
    }

    public BaseOpenApiResource openApiConfiguration(OpenAPIConfiguration openApiConfiguration) {
        this.setOpenApiConfiguration(openApiConfiguration);
        return this;
    }
}

