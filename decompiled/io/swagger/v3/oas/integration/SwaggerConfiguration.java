/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.oas.integration;

import io.swagger.v3.oas.integration.api.OpenAPIConfiguration;
import io.swagger.v3.oas.models.OpenAPI;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class SwaggerConfiguration
implements OpenAPIConfiguration {
    Map<String, Object> userDefinedOptions;
    private OpenAPI openAPI;
    private String id;
    private Set<String> resourcePackages;
    private Set<String> resourceClasses;
    private String filterClass;
    private String readerClass;
    private String scannerClass;
    private Boolean prettyPrint;
    private Boolean readAllResources = Boolean.TRUE;
    private Collection<String> ignoredRoutes;
    private Long cacheTTL = -1L;
    private Set<String> modelConverterClasses;
    private String objectMapperProcessorClass;
    private Boolean sortOutput;
    private Boolean alwaysResolveAppPath;
    private Boolean skipResolveAppPath;
    private Boolean openAPI31 = false;
    private Boolean convertToOpenAPI31;
    private String defaultResponseCode;

    @Override
    public String getDefaultResponseCode() {
        return this.defaultResponseCode;
    }

    public void setDefaultResponseCode(String defaultResponseCode) {
        this.defaultResponseCode = defaultResponseCode;
    }

    public SwaggerConfiguration defaultResponseCode(String defaultResponseCode) {
        this.defaultResponseCode = defaultResponseCode;
        return this;
    }

    @Override
    public Long getCacheTTL() {
        return this.cacheTTL;
    }

    public void setCacheTTL(Long cacheTTL) {
        this.cacheTTL = cacheTTL;
    }

    public SwaggerConfiguration cacheTTL(Long cacheTTL) {
        this.cacheTTL = cacheTTL;
        return this;
    }

    @Override
    public Boolean isReadAllResources() {
        return this.readAllResources;
    }

    public void setReadAllResources(Boolean readAllResources) {
        this.readAllResources = readAllResources;
    }

    public SwaggerConfiguration readAllResources(Boolean readAllResources) {
        this.readAllResources = readAllResources;
        return this;
    }

    @Override
    public Collection<String> getIgnoredRoutes() {
        return this.ignoredRoutes;
    }

    public void setIgnoredRoutes(Collection<String> ignoredRoutes) {
        this.ignoredRoutes = ignoredRoutes;
    }

    public SwaggerConfiguration ignoredRoutes(Collection<String> ignoredRoutes) {
        this.ignoredRoutes = ignoredRoutes;
        return this;
    }

    @Override
    public Boolean isPrettyPrint() {
        return this.prettyPrint;
    }

    public void setPrettyPrint(Boolean prettyPrint) {
        this.prettyPrint = prettyPrint;
    }

    public SwaggerConfiguration prettyPrint(Boolean prettyPrint) {
        this.prettyPrint = prettyPrint;
        return this;
    }

    @Override
    public OpenAPI getOpenAPI() {
        return this.openAPI;
    }

    public void setOpenAPI(OpenAPI openAPI) {
        this.openAPI = openAPI;
    }

    public SwaggerConfiguration openAPI(OpenAPI openAPI) {
        this.openAPI = openAPI;
        return this;
    }

    @Override
    public String getReaderClass() {
        return this.readerClass;
    }

    public void setReaderClass(String readerClass) {
        this.readerClass = readerClass;
    }

    @Override
    public String getScannerClass() {
        return this.scannerClass;
    }

    public void setScannerClass(String scannerClass) {
        this.scannerClass = scannerClass;
    }

    @Override
    public Map<String, Object> getUserDefinedOptions() {
        return this.userDefinedOptions;
    }

    public void setUserDefinedOptions(Map<String, Object> userDefinedOptions) {
        this.userDefinedOptions = userDefinedOptions;
    }

    public SwaggerConfiguration scannerClass(String scannerClass) {
        this.scannerClass = scannerClass;
        return this;
    }

    public SwaggerConfiguration readerClass(String readerClass) {
        this.readerClass = readerClass;
        return this;
    }

    public SwaggerConfiguration userDefinedOptions(Map<String, Object> userDefinedOptions) {
        this.userDefinedOptions = userDefinedOptions;
        return this;
    }

    @Override
    public Set<String> getResourcePackages() {
        return this.resourcePackages;
    }

    public void setResourcePackages(Set<String> resourcePackages) {
        this.resourcePackages = resourcePackages;
    }

    public SwaggerConfiguration resourcePackages(Set<String> resourcePackages) {
        this.resourcePackages = resourcePackages;
        return this;
    }

    @Override
    public Set<String> getResourceClasses() {
        return this.resourceClasses;
    }

    public void setResourceClasses(Set<String> resourceClasses) {
        this.resourceClasses = resourceClasses;
    }

    public SwaggerConfiguration resourceClasses(Set<String> resourceClasses) {
        this.resourceClasses = resourceClasses;
        return this;
    }

    @Override
    public String getFilterClass() {
        return this.filterClass;
    }

    public void setFilterClass(String filterClass) {
        this.filterClass = filterClass;
    }

    public SwaggerConfiguration filterClass(String filterClass) {
        this.filterClass = filterClass;
        return this;
    }

    public SwaggerConfiguration id(String id) {
        this.id = id;
        return this;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SwaggerConfiguration objectMapperProcessorClass(String objectMapperProcessorClass) {
        this.objectMapperProcessorClass = objectMapperProcessorClass;
        return this;
    }

    @Override
    public String getObjectMapperProcessorClass() {
        return this.objectMapperProcessorClass;
    }

    public void setObjectMapperProcessorClass(String objectMapperProcessorClass) {
        this.objectMapperProcessorClass = objectMapperProcessorClass;
    }

    @Override
    public Set<String> getModelConverterClasses() {
        return this.modelConverterClasses;
    }

    public void setModelConverterClassess(Set<String> modelConverterClasses) {
        this.modelConverterClasses = modelConverterClasses;
    }

    public SwaggerConfiguration modelConverterClasses(Set<String> modelConverterClasses) {
        this.modelConverterClasses = modelConverterClasses;
        return this;
    }

    @Override
    public Boolean isSortOutput() {
        return this.sortOutput;
    }

    public void setSortOutput(Boolean sortOutput) {
        this.sortOutput = sortOutput;
    }

    public SwaggerConfiguration sortOutput(Boolean sortOutput) {
        this.setSortOutput(sortOutput);
        return this;
    }

    @Override
    public Boolean isAlwaysResolveAppPath() {
        return this.alwaysResolveAppPath;
    }

    public void setAlwaysResolveAppPath(Boolean alwaysResolveAppPath) {
        this.alwaysResolveAppPath = alwaysResolveAppPath;
    }

    public SwaggerConfiguration alwaysResolveAppPath(Boolean alwaysResolveAppPath) {
        this.setAlwaysResolveAppPath(alwaysResolveAppPath);
        return this;
    }

    @Override
    public Boolean isSkipResolveAppPath() {
        return this.skipResolveAppPath;
    }

    public void setSkipResolveAppPath(Boolean skipResolveAppPath) {
        this.skipResolveAppPath = skipResolveAppPath;
    }

    public SwaggerConfiguration skipResolveAppPath(Boolean skipResolveAppPath) {
        this.setSkipResolveAppPath(skipResolveAppPath);
        return this;
    }

    @Override
    public Boolean isOpenAPI31() {
        return this.openAPI31;
    }

    public void setOpenAPI31(Boolean openAPI31) {
        this.openAPI31 = openAPI31;
    }

    public SwaggerConfiguration openAPI31(Boolean openAPI31) {
        this.openAPI31 = openAPI31;
        return this;
    }

    @Override
    public Boolean isConvertToOpenAPI31() {
        return this.convertToOpenAPI31;
    }

    public void setConvertToOpenAPI31(Boolean convertToOpenAPI31) {
        this.convertToOpenAPI31 = convertToOpenAPI31;
        if (Boolean.TRUE.equals(convertToOpenAPI31)) {
            this.openAPI31 = true;
        }
    }

    public SwaggerConfiguration convertToOpenAPI31(Boolean convertToOpenAPI31) {
        this.setConvertToOpenAPI31(convertToOpenAPI31);
        return this;
    }
}

