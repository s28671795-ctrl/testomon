/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletConfig
 */
package io.swagger.v3.jaxrs2.integration;

import io.swagger.v3.jaxrs2.integration.ServletConfigContextUtils;
import io.swagger.v3.oas.integration.ClasspathOpenApiConfigurationLoader;
import io.swagger.v3.oas.integration.FileOpenApiConfigurationLoader;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.integration.api.OpenAPIConfigBuilder;
import io.swagger.v3.oas.integration.api.OpenAPIConfiguration;
import io.swagger.v3.oas.integration.api.OpenApiConfigurationLoader;
import java.io.IOException;
import javax.servlet.ServletConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServletOpenApiConfigurationLoader
implements OpenApiConfigurationLoader {
    private static Logger LOGGER = LoggerFactory.getLogger(ServletOpenApiConfigurationLoader.class);
    private ServletConfig servletConfig;
    private FileOpenApiConfigurationLoader fileOpenApiConfigurationLoader = new FileOpenApiConfigurationLoader();
    private ClasspathOpenApiConfigurationLoader classpathOpenApiConfigurationLoader = new ClasspathOpenApiConfigurationLoader();

    public ServletOpenApiConfigurationLoader(ServletConfig servletConfig) {
        this.servletConfig = servletConfig;
    }

    @Override
    public OpenAPIConfiguration load(String path) throws IOException {
        String builderClassName;
        if (this.servletConfig == null) {
            return null;
        }
        if (StringUtils.isBlank(path)) {
            SwaggerConfiguration configuration = new SwaggerConfiguration().resourcePackages(ServletConfigContextUtils.resolveResourcePackages(this.servletConfig)).filterClass(ServletConfigContextUtils.getInitParam(this.servletConfig, "openApi.configuration.filterClass")).resourceClasses(ServletConfigContextUtils.resolveResourceClasses(this.servletConfig)).readAllResources(ServletConfigContextUtils.getBooleanInitParam(this.servletConfig, "openApi.configuration.readAllResources")).prettyPrint(ServletConfigContextUtils.getBooleanInitParam(this.servletConfig, "openApi.configuration.prettyPrint")).sortOutput(ServletConfigContextUtils.getBooleanInitParam(this.servletConfig, "openApi.configuration.sortOutput")).alwaysResolveAppPath(ServletConfigContextUtils.getBooleanInitParam(this.servletConfig, "openApi.configuration.alwaysResolveAppPath")).skipResolveAppPath(ServletConfigContextUtils.getBooleanInitParam(this.servletConfig, "openApi.configuration.skipResolveAppPath")).readerClass(ServletConfigContextUtils.getInitParam(this.servletConfig, "openApi.configuration.readerClass")).cacheTTL(ServletConfigContextUtils.getLongInitParam(this.servletConfig, "openApi.configuration.cacheTTL")).scannerClass(ServletConfigContextUtils.getInitParam(this.servletConfig, "openApi.configuration.scannerClass")).objectMapperProcessorClass(ServletConfigContextUtils.getInitParam(this.servletConfig, "openApi.configuration.objectMapperProcessorClass")).defaultResponseCode(ServletConfigContextUtils.getInitParam(this.servletConfig, "openApi.configuration.defaultResponseCode")).openAPI31(ServletConfigContextUtils.getBooleanInitParam(this.servletConfig, "openApi.configuration.openAPI31")).convertToOpenAPI31(ServletConfigContextUtils.getBooleanInitParam(this.servletConfig, "openApi.configuration.convertToOpenAPI31")).modelConverterClasses(ServletConfigContextUtils.resolveModelConverterClasses(this.servletConfig));
            return configuration;
        }
        String location = ServletConfigContextUtils.getInitParam(this.servletConfig, path);
        if (!StringUtils.isBlank(location)) {
            if (this.classpathOpenApiConfigurationLoader.exists(location)) {
                return this.classpathOpenApiConfigurationLoader.load(location);
            }
            if (this.fileOpenApiConfigurationLoader.exists(location)) {
                return this.fileOpenApiConfigurationLoader.load(location);
            }
        }
        if (StringUtils.isNotBlank(builderClassName = ServletConfigContextUtils.getInitParam(this.servletConfig, "openApi.configuration.builderClass"))) {
            try {
                Class<?> cls = this.getClass().getClassLoader().loadClass(builderClassName);
                OpenAPIConfigBuilder builder = (OpenAPIConfigBuilder)cls.newInstance();
                return builder.build();
            }
            catch (Exception e) {
                LOGGER.error("error loading builder: " + e.getMessage(), e);
            }
        }
        return null;
    }

    @Override
    public boolean exists(String path) {
        if (this.servletConfig == null) {
            return false;
        }
        if (StringUtils.isBlank(path)) {
            if (ServletConfigContextUtils.resolveResourcePackages(this.servletConfig) != null) {
                return true;
            }
            if (ServletConfigContextUtils.getInitParam(this.servletConfig, "openApi.configuration.filterClass") != null) {
                return true;
            }
            if (ServletConfigContextUtils.resolveResourceClasses(this.servletConfig) != null) {
                return true;
            }
            if (ServletConfigContextUtils.getBooleanInitParam(this.servletConfig, "openApi.configuration.readAllResources") != null) {
                return true;
            }
            if (ServletConfigContextUtils.getBooleanInitParam(this.servletConfig, "openApi.configuration.prettyPrint") != null) {
                return true;
            }
            if (ServletConfigContextUtils.getBooleanInitParam(this.servletConfig, "openApi.configuration.sortOutput") != null) {
                return true;
            }
            if (ServletConfigContextUtils.getBooleanInitParam(this.servletConfig, "openApi.configuration.alwaysResolveAppPath") != null) {
                return true;
            }
            if (ServletConfigContextUtils.getBooleanInitParam(this.servletConfig, "openApi.configuration.skipResolveAppPath") != null) {
                return true;
            }
            if (ServletConfigContextUtils.getInitParam(this.servletConfig, "openApi.configuration.readerClass") != null) {
                return true;
            }
            if (ServletConfigContextUtils.getLongInitParam(this.servletConfig, "openApi.configuration.cacheTTL") != null) {
                return true;
            }
            if (ServletConfigContextUtils.getInitParam(this.servletConfig, "openApi.configuration.scannerClass") != null) {
                return true;
            }
            if (ServletConfigContextUtils.getInitParam(this.servletConfig, "openApi.configuration.objectMapperProcessorClass") != null) {
                return true;
            }
            if (ServletConfigContextUtils.getInitParam(this.servletConfig, "openApi.configuration.defaultResponseCode") != null) {
                return true;
            }
            if (ServletConfigContextUtils.getInitParam(this.servletConfig, "openApi.configuration.convertToOpenAPI31") != null) {
                return true;
            }
            return ServletConfigContextUtils.resolveModelConverterClasses(this.servletConfig) != null;
        }
        String location = ServletConfigContextUtils.getInitParam(this.servletConfig, path);
        if (!StringUtils.isBlank(location)) {
            if (this.classpathOpenApiConfigurationLoader.exists(location)) {
                return true;
            }
            return this.fileOpenApiConfigurationLoader.exists(location);
        }
        return false;
    }
}

