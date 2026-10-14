/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.jaxrs2.integration;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import io.swagger.v3.core.filter.OpenAPISpecFilter;
import io.swagger.v3.core.filter.SpecFilter;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.core.util.Yaml;
import io.swagger.v3.jaxrs2.integration.JaxrsOpenApiContextBuilder;
import io.swagger.v3.oas.integration.GenericOpenApiContextBuilder;
import io.swagger.v3.oas.integration.OpenApiConfigurationException;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.integration.api.OpenApiContext;
import io.swagger.v3.oas.models.OpenAPI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

public class SwaggerLoader {
    private String outputFormat;
    private String resourcePackages;
    private String resourceClasses;
    private String filterClass;
    private String readerClass;
    private String contextId;
    private String scannerClass;
    private Boolean prettyPrint = false;
    private Boolean readAllResources = Boolean.TRUE;
    private String ignoredRoutes;
    private String openapiAsString;
    private String objectMapperProcessorClass;
    private String defaultResponseCode;
    private String modelConverterClasses;
    private Boolean sortOutput = false;
    private Boolean alwaysResolveAppPath = false;
    private Boolean skipResolveAppPath = false;
    private Boolean openAPI31 = false;
    private Boolean convertToOpenAPI31 = false;

    public String getObjectMapperProcessorClass() {
        return this.objectMapperProcessorClass;
    }

    public void setObjectMapperProcessorClass(String objectMapperProcessorClass) {
        this.objectMapperProcessorClass = objectMapperProcessorClass;
    }

    public String getDefaultResponseCode() {
        return this.defaultResponseCode;
    }

    public void setDefaultResponseCode(String defaultResponseCode) {
        this.defaultResponseCode = defaultResponseCode;
    }

    public String getModelConverterClasses() {
        return this.modelConverterClasses;
    }

    public void setModelConverterClasses(String modelConverterClasses) {
        this.modelConverterClasses = modelConverterClasses;
    }

    public String getOutputFormat() {
        return this.outputFormat;
    }

    public void setOutputFormat(String outputFormat) {
        this.outputFormat = outputFormat;
    }

    public String getResourcePackages() {
        return this.resourcePackages;
    }

    public void setResourcePackages(String resourcePackages) {
        this.resourcePackages = resourcePackages;
    }

    public String getResourceClasses() {
        return this.resourceClasses;
    }

    public void setResourceClasses(String resourceClasses) {
        this.resourceClasses = resourceClasses;
    }

    public String getFilterClass() {
        return this.filterClass;
    }

    public void setFilterClass(String filterClass) {
        this.filterClass = filterClass;
    }

    public String getContextId() {
        return this.contextId;
    }

    public void setContextId(String contextId) {
        this.contextId = contextId;
    }

    public String getReaderClass() {
        return this.readerClass;
    }

    public void setReaderClass(String readerClass) {
        this.readerClass = readerClass;
    }

    public String getScannerClass() {
        return this.scannerClass;
    }

    public void setScannerClass(String scannerClass) {
        this.scannerClass = scannerClass;
    }

    public Boolean getPrettyPrint() {
        return this.prettyPrint;
    }

    public void setPrettyPrint(Boolean prettyPrint) {
        this.prettyPrint = prettyPrint;
    }

    public Boolean getReadAllResources() {
        return this.readAllResources;
    }

    public void setReadAllResources(Boolean readAllResources) {
        this.readAllResources = readAllResources;
    }

    public String getIgnoredRoutes() {
        return this.ignoredRoutes;
    }

    public void setIgnoredRoutes(String ignoredRoutes) {
        this.ignoredRoutes = ignoredRoutes;
    }

    public String getOpenapiAsString() {
        return this.openapiAsString;
    }

    public void setOpenapiAsString(String openapiAsString) {
        this.openapiAsString = openapiAsString;
    }

    public Boolean getSortOutput() {
        return this.sortOutput;
    }

    public void setSortOutput(Boolean sortOutput) {
        this.sortOutput = sortOutput;
    }

    public Boolean getAlwaysResolveAppPath() {
        return this.alwaysResolveAppPath;
    }

    public void setAlwaysResolveAppPath(Boolean alwaysResolveAppPath) {
        this.alwaysResolveAppPath = alwaysResolveAppPath;
    }

    public Boolean getSkipResolveAppPath() {
        return this.skipResolveAppPath;
    }

    public void setSkipResolveAppPath(Boolean skipResolveAppPath) {
        this.skipResolveAppPath = skipResolveAppPath;
    }

    public Boolean getOpenAPI31() {
        return this.openAPI31;
    }

    public Boolean getConvertToOpenAPI31() {
        return this.convertToOpenAPI31;
    }

    public void setOpenAPI31(Boolean openAPI31) {
        this.openAPI31 = openAPI31;
    }

    public void setConvertToOpenAPI31(Boolean convertToOpenAPI31) {
        this.convertToOpenAPI31 = convertToOpenAPI31;
    }

    public Map<String, String> resolve() throws Exception {
        HashSet<String> ignoredRoutesSet = null;
        if (StringUtils.isNotBlank(this.ignoredRoutes)) {
            ignoredRoutesSet = new HashSet<String>(Arrays.asList(this.ignoredRoutes.split(",")));
        }
        HashSet<String> resourceClassesSet = null;
        if (StringUtils.isNotBlank(this.resourceClasses)) {
            resourceClassesSet = new HashSet<String>(Arrays.asList(this.resourceClasses.split(",")));
        }
        HashSet<String> resourcePackagesSet = null;
        if (StringUtils.isNotBlank(this.resourcePackages)) {
            resourcePackagesSet = new HashSet<String>(Arrays.asList(this.resourcePackages.split(",")));
        }
        LinkedHashSet<String> modelConverterSet = null;
        if (StringUtils.isNotBlank(this.modelConverterClasses)) {
            modelConverterSet = new LinkedHashSet<String>(Arrays.asList(this.modelConverterClasses.split(",")));
        }
        OpenAPI openAPIInput = null;
        if (StringUtils.isNotBlank(this.openapiAsString)) {
            try {
                openAPIInput = Json.mapper().readValue(this.openapiAsString, OpenAPI.class);
            }
            catch (Exception e) {
                try {
                    openAPIInput = Yaml.mapper().readValue(this.openapiAsString, OpenAPI.class);
                }
                catch (Exception e1) {
                    throw new Exception("Error reading/deserializing openapi input: " + e.getMessage(), e);
                }
            }
        }
        SwaggerConfiguration config = new SwaggerConfiguration().filterClass(this.filterClass).ignoredRoutes(ignoredRoutesSet).prettyPrint(this.prettyPrint).readAllResources(this.readAllResources).openAPI(openAPIInput).readerClass(this.readerClass).scannerClass(this.scannerClass).resourceClasses(resourceClassesSet).resourcePackages(resourcePackagesSet).objectMapperProcessorClass(this.objectMapperProcessorClass).defaultResponseCode(this.defaultResponseCode).modelConverterClasses(modelConverterSet).sortOutput(this.sortOutput).alwaysResolveAppPath(this.alwaysResolveAppPath).skipResolveAppPath(this.skipResolveAppPath).openAPI31(this.openAPI31).convertToOpenAPI31(this.convertToOpenAPI31);
        try {
            Object builder = new JaxrsOpenApiContextBuilder().openApiConfiguration(config);
            if (StringUtils.isNotBlank(this.contextId)) {
                ((GenericOpenApiContextBuilder)builder).ctxId(this.contextId);
            }
            OpenApiContext context = ((GenericOpenApiContextBuilder)builder).buildContext(true);
            OpenAPI openAPI = context.read();
            if (StringUtils.isNotBlank(this.filterClass)) {
                try {
                    OpenAPISpecFilter filterImpl = (OpenAPISpecFilter)this.getClass().getClassLoader().loadClass(this.filterClass).newInstance();
                    SpecFilter f = new SpecFilter();
                    openAPI = f.filter(openAPI, filterImpl, new HashMap<String, List<String>>(), new HashMap<String, String>(), new HashMap<String, List<String>>());
                }
                catch (Exception e) {
                    throw new Exception("Error applying filter to API specification: " + e.getMessage(), e);
                }
            }
            String openapiJson = null;
            String openapiYaml = null;
            if ("JSON".equals(this.outputFormat) || "JSONANDYAML".equals(this.outputFormat)) {
                openapiJson = this.prettyPrint != null && this.prettyPrint != false ? context.getOutputJsonMapper().writer(new DefaultPrettyPrinter()).writeValueAsString(openAPI) : context.getOutputJsonMapper().writeValueAsString(openAPI);
            }
            if ("YAML".equals(this.outputFormat) || "JSONANDYAML".equals(this.outputFormat)) {
                openapiYaml = this.prettyPrint != null && this.prettyPrint != false ? context.getOutputYamlMapper().writer(new DefaultPrettyPrinter()).writeValueAsString(openAPI) : context.getOutputYamlMapper().writeValueAsString(openAPI);
            }
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("JSON", openapiJson);
            map.put("YAML", openapiYaml);
            return map;
        }
        catch (OpenApiConfigurationException e) {
            throw new Exception("Error resolving API specification: " + e.getMessage(), e);
        }
        catch (Exception e) {
            throw new Exception("Error resolving API specification: " + e.getMessage(), e);
        }
    }
}

