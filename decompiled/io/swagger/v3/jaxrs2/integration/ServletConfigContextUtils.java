/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletConfig
 */
package io.swagger.v3.jaxrs2.integration;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.ServletConfig;
import org.apache.commons.lang3.StringUtils;

public class ServletConfigContextUtils {
    public static final String OPENAPI_CONFIGURATION_RESOURCEPACKAGE_KEY = "openApi.configuration.resourcePackages";
    public static final String OPENAPI_CONFIGURATION_LOCATION_KEY = "openApi.configuration.location";
    public static final String JERSEY1_PACKAGE_KEY = "com.sun.jersey.config.property.packages";
    public static final String JERSEY2_PACKAGE_KEY = "jersey.config.server.provider.packages";
    public static final String JERSEY2_CLASSES_KEY = "jersey.config.server.provider.classnames";
    public static final String OPENAPI_CONFIGURATION_READER_KEY = "openApi.configuration.readerClass";
    public static final String OPENAPI_CONFIGURATION_SCANNER_KEY = "openApi.configuration.scannerClass";
    public static final String OPENAPI_CONFIGURATION_BUILDER_KEY = "openApi.configuration.builderClass";
    public static final String OPENAPI_CONFIGURATION_PRETTYPRINT_KEY = "openApi.configuration.prettyPrint";
    public static final String OPENAPI_CONFIGURATION_READALLRESOURCES_KEY = "openApi.configuration.readAllResources";
    public static final String OPENAPI_CONFIGURATION_RESOURCECLASSES_KEY = "openApi.configuration.resourceClasses";
    public static final String OPENAPI_CONFIGURATION_FILTER_KEY = "openApi.configuration.filterClass";
    public static final String OPENAPI_CONFIGURATION_CACHE_TTL_KEY = "openApi.configuration.cacheTTL";
    public static final String OPENAPI_CONFIGURATION_SORTOUTPUT_KEY = "openApi.configuration.sortOutput";
    public static final String OPENAPI_CONFIGURATION_ALWAYSRESOLVEAPPPATH_KEY = "openApi.configuration.alwaysResolveAppPath";
    public static final String OPENAPI_CONFIGURATION_SKIPRESOLVEAPPPATH_KEY = "openApi.configuration.skipResolveAppPath";
    public static final String OPENAPI_CONFIGURATION_OBJECT_MAPPER_PROCESSOR_KEY = "openApi.configuration.objectMapperProcessorClass";
    public static final String OPENAPI_CONFIGURATION_DEFAULT_RESPONSE_CODE_KEY = "openApi.configuration.defaultResponseCode";
    public static final String OPENAPI_CONFIGURATION_MODEL_CONVERTERS_KEY = "openApi.configuration.modelConverterClasses";
    public static final String OPENAPI_CONFIGURATION_OPENAPI_31_KEY = "openApi.configuration.openAPI31";
    public static final String OPENAPI_CONFIGURATION_CONVERT_TO_OPENAPI_31_KEY = "openApi.configuration.convertToOpenAPI31";

    public static Set<String> resolveResourcePackages(ServletConfig servletConfig) {
        if (!ServletConfigContextUtils.isServletConfigAvailable(servletConfig)) {
            return null;
        }
        String resourcePackage = ServletConfigContextUtils.getInitParam(servletConfig, OPENAPI_CONFIGURATION_RESOURCEPACKAGE_KEY);
        if (resourcePackage == null && (resourcePackage = ServletConfigContextUtils.getInitParam(servletConfig, JERSEY1_PACKAGE_KEY)) != null) {
            resourcePackage = resourcePackage.replace(';', ',');
        }
        if (resourcePackage == null && (resourcePackage = ServletConfigContextUtils.getInitParam(servletConfig, JERSEY2_PACKAGE_KEY)) != null) {
            resourcePackage = resourcePackage.replace(';', ',');
        }
        if (StringUtils.isBlank(resourcePackage)) {
            return null;
        }
        return Arrays.stream(resourcePackage.split(",")).collect(Collectors.toSet());
    }

    public static Set<String> resolveResourceClasses(ServletConfig servletConfig) {
        if (!ServletConfigContextUtils.isServletConfigAvailable(servletConfig)) {
            return null;
        }
        String resourceClasses = ServletConfigContextUtils.getInitParam(servletConfig, OPENAPI_CONFIGURATION_RESOURCECLASSES_KEY);
        if (resourceClasses == null && (resourceClasses = ServletConfigContextUtils.getInitParam(servletConfig, JERSEY2_CLASSES_KEY)) != null) {
            resourceClasses = resourceClasses.replace(';', ',');
        }
        if (StringUtils.isBlank(resourceClasses)) {
            return null;
        }
        return Arrays.stream(resourceClasses.split(",")).collect(Collectors.toSet());
    }

    public static Set<String> resolveModelConverterClasses(ServletConfig servletConfig) {
        if (!ServletConfigContextUtils.isServletConfigAvailable(servletConfig)) {
            return null;
        }
        String modelConverterClasses = ServletConfigContextUtils.getInitParam(servletConfig, OPENAPI_CONFIGURATION_MODEL_CONVERTERS_KEY);
        if (modelConverterClasses != null) {
            modelConverterClasses = modelConverterClasses.replace(';', ',');
        }
        if (StringUtils.isBlank(modelConverterClasses)) {
            return null;
        }
        return new LinkedHashSet<String>(Arrays.stream(modelConverterClasses.split(",")).collect(Collectors.toSet()));
    }

    public static String getInitParam(ServletConfig sc, String paramKey) {
        if (!ServletConfigContextUtils.isServletConfigAvailable(sc)) {
            return null;
        }
        return sc.getInitParameter(paramKey);
    }

    public static Boolean getBooleanInitParam(ServletConfig sc, String paramKey) {
        String param2 = ServletConfigContextUtils.getInitParam(sc, paramKey);
        if (StringUtils.isBlank(param2)) {
            return null;
        }
        return Boolean.parseBoolean(param2);
    }

    public static Long getLongInitParam(ServletConfig sc, String paramKey) {
        String param2 = ServletConfigContextUtils.getInitParam(sc, paramKey);
        if (StringUtils.isBlank(param2)) {
            return null;
        }
        try {
            return Long.parseLong(param2);
        }
        catch (NumberFormatException e) {
            return null;
        }
    }

    public static String getContextIdFromServletConfig(ServletConfig config) {
        String ctxId = null;
        if (ServletConfigContextUtils.isServletConfigAvailable(config) && StringUtils.isBlank(ctxId = ServletConfigContextUtils.getInitParam(config, "openapi.context.id"))) {
            ctxId = "openapi.context.id.servlet." + config.getServletName();
        }
        if (StringUtils.isBlank(ctxId)) {
            ctxId = "openapi.context.id.default";
        }
        return ctxId;
    }

    public static boolean isServletConfigAvailable(ServletConfig sc) {
        if (sc == null) {
            return false;
        }
        try {
            sc.getInitParameter("test");
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
}

