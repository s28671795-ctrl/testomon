/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.oas.integration;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.core.converter.ModelConverter;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.filter.OpenAPI31SpecFilter;
import io.swagger.v3.core.filter.SpecFilter;
import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.core.jackson.PathsSerializer;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.core.util.Json31;
import io.swagger.v3.core.util.Yaml;
import io.swagger.v3.core.util.Yaml31;
import io.swagger.v3.oas.integration.ClasspathOpenApiConfigurationLoader;
import io.swagger.v3.oas.integration.ContextUtils;
import io.swagger.v3.oas.integration.FileOpenApiConfigurationLoader;
import io.swagger.v3.oas.integration.GenericOpenApiScanner;
import io.swagger.v3.oas.integration.IntegrationObjectMapperFactory;
import io.swagger.v3.oas.integration.OpenApiConfigurationException;
import io.swagger.v3.oas.integration.OpenApiContextLocator;
import io.swagger.v3.oas.integration.ServiceOpenApiConfigurationLoader;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.integration.URLOpenApiConfigurationLoader;
import io.swagger.v3.oas.integration.api.ObjectMapperProcessor;
import io.swagger.v3.oas.integration.api.OpenAPIConfiguration;
import io.swagger.v3.oas.integration.api.OpenApiConfigurationLoader;
import io.swagger.v3.oas.integration.api.OpenApiContext;
import io.swagger.v3.oas.integration.api.OpenApiReader;
import io.swagger.v3.oas.integration.api.OpenApiScanner;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.media.Schema;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenericOpenApiContext<T extends GenericOpenApiContext>
implements OpenApiContext {
    private static Logger LOGGER = LoggerFactory.getLogger(GenericOpenApiContext.class);
    protected Set<String> resourcePackages;
    protected Set<String> resourceClasses;
    protected String id = "openapi.context.id.default";
    protected OpenApiContext parent;
    protected String configLocation;
    private OpenAPIConfiguration openApiConfiguration;
    private OpenApiReader openApiReader;
    private OpenApiScanner openApiScanner;
    private OpenApiReader providedOpenApiReader;
    private ObjectMapperProcessor objectMapperProcessor;
    private Set<ModelConverter> modelConverters;
    private ObjectMapper outputJsonMapper;
    private ObjectMapper outputYamlMapper;
    private ConcurrentHashMap<String, Cache> cache = new ConcurrentHashMap();
    private long cacheTTL = -1L;
    private Boolean openAPI31;
    private Boolean convertToOpenAPI31;

    public long getCacheTTL() {
        return this.cacheTTL;
    }

    public void setCacheTTL(long cacheTTL) {
        this.cacheTTL = cacheTTL;
    }

    public T cacheTTL(long cacheTTL) {
        this.cacheTTL = cacheTTL;
        return (T)this;
    }

    public OpenApiReader getOpenApiReader() {
        return this.openApiReader;
    }

    @Override
    public void setOpenApiReader(OpenApiReader openApiReader) {
        this.openApiReader = openApiReader;
        this.providedOpenApiReader = openApiReader;
    }

    public OpenApiScanner getOpenApiScanner() {
        return this.openApiScanner;
    }

    @Override
    public void setOpenApiScanner(OpenApiScanner openApiScanner) {
        this.openApiScanner = openApiScanner;
    }

    public final T openApiReader(OpenApiReader openApiReader) {
        this.setOpenApiReader(openApiReader);
        return (T)this;
    }

    public final T openApiScanner(OpenApiScanner openApiScanner) {
        this.openApiScanner = openApiScanner;
        return (T)this;
    }

    public Set<String> getResourcePackages() {
        return this.resourcePackages;
    }

    public void setResourcePackages(Set<String> resourcePackages) {
        this.resourcePackages = resourcePackages;
    }

    public T resourcePackages(Set<String> resourcePackages) {
        this.resourcePackages = resourcePackages;
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

    public T openApiConfiguration(OpenAPIConfiguration openApiConfiguration) {
        this.openApiConfiguration = openApiConfiguration;
        return (T)this;
    }

    @Override
    public String getConfigLocation() {
        return this.configLocation;
    }

    public void setConfigLocation(String configLocation) {
        this.configLocation = configLocation;
    }

    public final T configLocation(String configLocation) {
        this.configLocation = configLocation;
        return (T)this;
    }

    @Override
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public final T id(String id) {
        this.id = id;
        return (T)this;
    }

    @Override
    public OpenApiContext getParent() {
        return this.parent;
    }

    public void setParent(OpenApiContext parent) {
        this.parent = parent;
    }

    public final T parent(OpenApiContext parent) {
        this.parent = parent;
        return (T)this;
    }

    public ObjectMapperProcessor getObjectMapperProcessor() {
        return this.objectMapperProcessor;
    }

    @Override
    public void setObjectMapperProcessor(ObjectMapperProcessor objectMapperProcessor) {
        this.objectMapperProcessor = objectMapperProcessor;
    }

    public final T objectMapperProcessor(ObjectMapperProcessor objectMapperProcessor) {
        this.objectMapperProcessor = objectMapperProcessor;
        return (T)this;
    }

    public Set<ModelConverter> getModelConverters() {
        return this.modelConverters;
    }

    @Override
    public void setModelConverters(Set<ModelConverter> modelConverters) {
        this.modelConverters = modelConverters;
    }

    public final T modelConverters(Set<ModelConverter> modelConverters) {
        this.modelConverters = modelConverters;
        return (T)this;
    }

    @Override
    public ObjectMapper getOutputJsonMapper() {
        return this.outputJsonMapper;
    }

    @Override
    public void setOutputJsonMapper(ObjectMapper outputJsonMapper) {
        this.outputJsonMapper = outputJsonMapper;
    }

    public final T outputJsonMapper(ObjectMapper outputJsonMapper) {
        this.outputJsonMapper = outputJsonMapper;
        return (T)this;
    }

    @Override
    public ObjectMapper getOutputYamlMapper() {
        return this.outputYamlMapper;
    }

    @Override
    public void setOutputYamlMapper(ObjectMapper outputYamlMapper) {
        this.outputYamlMapper = outputYamlMapper;
    }

    public final T outputYamlMapper(ObjectMapper outputYamlMapper) {
        this.outputYamlMapper = outputYamlMapper;
        return (T)this;
    }

    public Boolean isOpenAPI31() {
        return this.openAPI31;
    }

    public void setOpenAPI31(Boolean v) {
        this.openAPI31 = this.openAPI31;
    }

    public T openAPI31(Boolean openAPI31) {
        this.openAPI31 = openAPI31;
        return (T)this;
    }

    public Boolean isConvertToOpenAPI31() {
        return this.convertToOpenAPI31;
    }

    public void setConvertToOpenAPI31(Boolean convertToOpenAPI31) {
        this.convertToOpenAPI31 = convertToOpenAPI31;
        if (Boolean.TRUE.equals(convertToOpenAPI31)) {
            this.openAPI31 = true;
        }
    }

    public T convertToOpenAPI31(Boolean convertToOpenAPI31) {
        this.setConvertToOpenAPI31(convertToOpenAPI31);
        return (T)this;
    }

    protected void register() {
        OpenApiContextLocator.getInstance().putOpenApiContext(this.id, this);
    }

    @Override
    public OpenAPIConfiguration getOpenApiConfiguration() {
        return this.openApiConfiguration;
    }

    public void setOpenApiConfiguration(OpenAPIConfiguration openApiConfiguration) {
        this.openApiConfiguration = openApiConfiguration;
    }

    protected OpenApiReader buildReader(OpenAPIConfiguration openApiConfiguration) throws Exception {
        OpenApiReader reader;
        if (StringUtils.isNotBlank(openApiConfiguration.getReaderClass())) {
            Class<?> cls = this.getClass().getClassLoader().loadClass(openApiConfiguration.getReaderClass());
            reader = (OpenApiReader)cls.newInstance();
        } else {
            reader = new OpenApiReader(){
                OpenAPIConfiguration openApiConfiguration;

                @Override
                public void setConfiguration(OpenAPIConfiguration openApiConfiguration) {
                    this.openApiConfiguration = openApiConfiguration;
                }

                @Override
                public OpenAPI read(Set<Class<?>> classes, Map<String, Object> resources) {
                    return this.openApiConfiguration.getOpenAPI();
                }
            };
        }
        reader.setConfiguration(openApiConfiguration);
        return reader;
    }

    protected OpenApiScanner buildScanner(OpenAPIConfiguration openApiConfiguration) throws Exception {
        OpenApiScanner scanner;
        if (StringUtils.isNotBlank(openApiConfiguration.getScannerClass())) {
            Class<?> cls = this.getClass().getClassLoader().loadClass(openApiConfiguration.getScannerClass());
            scanner = (OpenApiScanner)cls.newInstance();
        } else {
            scanner = new GenericOpenApiScanner();
        }
        scanner.setConfiguration(openApiConfiguration);
        return scanner;
    }

    protected ObjectMapperProcessor buildObjectMapperProcessor(OpenAPIConfiguration openApiConfiguration) throws Exception {
        ObjectMapperProcessor objectMapperProcessor = null;
        if (StringUtils.isNotBlank(openApiConfiguration.getObjectMapperProcessorClass())) {
            Class<?> cls = this.getClass().getClassLoader().loadClass(openApiConfiguration.getObjectMapperProcessorClass());
            objectMapperProcessor = (ObjectMapperProcessor)cls.newInstance();
        }
        return objectMapperProcessor;
    }

    protected Set<ModelConverter> buildModelConverters(OpenAPIConfiguration openApiConfiguration) throws Exception {
        if (openApiConfiguration.getModelConverterClasses() != null && !openApiConfiguration.getModelConverterClasses().isEmpty()) {
            LinkedHashSet<ModelConverter> modelConverters = new LinkedHashSet<ModelConverter>();
            for (String converterClass : openApiConfiguration.getModelConverterClasses()) {
                Class<?> cls = this.getClass().getClassLoader().loadClass(converterClass);
                ModelConverter converter = (ModelConverter)cls.newInstance();
                modelConverters.add(converter);
            }
            return modelConverters;
        }
        return null;
    }

    protected List<ImmutablePair<String, String>> getKnownLocations() {
        return Arrays.asList(new ImmutablePair<String, String>("classpath", "openapi-configuration.yaml"), new ImmutablePair<String, String>("classpath", "openapi-configuration.json"), new ImmutablePair<String, String>("file", "openapi-configuration.yaml"), new ImmutablePair<String, String>("file", "openapi-configuration.json"), new ImmutablePair<String, String>("classpath", "openapi.yaml"), new ImmutablePair<String, String>("classpath", "openapi.json"), new ImmutablePair<String, String>("file", "openapi.yaml"), new ImmutablePair<String, String>("file", "openapi.json"), new ImmutablePair<String, String>("service", ""));
    }

    protected Map<String, OpenApiConfigurationLoader> getLocationLoaders() {
        HashMap<String, OpenApiConfigurationLoader> map = new HashMap<String, OpenApiConfigurationLoader>();
        map.put("classpath", new ClasspathOpenApiConfigurationLoader());
        map.put("file", new FileOpenApiConfigurationLoader());
        map.put("url", new URLOpenApiConfigurationLoader());
        map.put("service", new ServiceOpenApiConfigurationLoader());
        return map;
    }

    protected OpenAPIConfiguration loadConfiguration() throws OpenApiConfigurationException {
        Map<String, OpenApiConfigurationLoader> loaders = this.getLocationLoaders();
        try {
            if (StringUtils.isNotEmpty(this.configLocation)) {
                if (loaders.get("classpath").exists(this.configLocation)) {
                    return loaders.get("classpath").load(this.configLocation);
                }
                if (loaders.get("file").exists(this.configLocation)) {
                    return loaders.get("file").load(this.configLocation);
                }
            }
            List<ImmutablePair<String, String>> knownLocations = this.getKnownLocations();
            for (ImmutablePair<String, String> location : knownLocations) {
                if (!loaders.get(location.left).exists((String)location.right)) continue;
                try {
                    return loaders.get(location.left).load((String)location.right);
                }
                catch (IOException iOException) {
                }
            }
            return null;
        }
        catch (Exception e) {
            throw new OpenApiConfigurationException(e.getMessage(), e);
        }
    }

    public T init() throws OpenApiConfigurationException {
        if (this.openApiConfiguration == null) {
            this.openApiConfiguration = this.loadConfiguration();
        }
        if (this.openApiConfiguration == null) {
            this.openApiConfiguration = new SwaggerConfiguration().resourcePackages(this.resourcePackages).resourceClasses(this.resourceClasses);
            ((SwaggerConfiguration)this.openApiConfiguration).setId(this.id);
            ((SwaggerConfiguration)this.openApiConfiguration).setOpenAPI31(this.openAPI31);
            ((SwaggerConfiguration)this.openApiConfiguration).setConvertToOpenAPI31(this.convertToOpenAPI31);
        }
        this.openApiConfiguration = this.mergeParentConfiguration(this.openApiConfiguration, this.parent);
        try {
            if (this.openApiReader == null) {
                this.openApiReader = this.buildReader(ContextUtils.deepCopy(this.openApiConfiguration));
            }
            if (this.openApiScanner == null) {
                this.openApiScanner = this.buildScanner(ContextUtils.deepCopy(this.openApiConfiguration));
            }
            if (this.objectMapperProcessor == null) {
                this.objectMapperProcessor = this.buildObjectMapperProcessor(ContextUtils.deepCopy(this.openApiConfiguration));
            }
            if (this.modelConverters == null || this.modelConverters.isEmpty()) {
                this.modelConverters = this.buildModelConverters(ContextUtils.deepCopy(this.openApiConfiguration));
            }
            if (this.outputJsonMapper == null) {
                this.outputJsonMapper = Boolean.TRUE.equals(this.openApiConfiguration.isOpenAPI31()) ? Json31.mapper().copy() : Json.mapper().copy();
            }
            if (this.outputYamlMapper == null) {
                this.outputYamlMapper = Boolean.TRUE.equals(this.openApiConfiguration.isOpenAPI31()) ? Yaml31.mapper().copy() : Yaml.mapper().copy();
            }
            if (this.openApiConfiguration.isSortOutput() != null && this.openApiConfiguration.isSortOutput().booleanValue()) {
                this.outputJsonMapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
                this.outputJsonMapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
                this.outputYamlMapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
                this.outputYamlMapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
                if (Boolean.TRUE.equals(this.openApiConfiguration.isOpenAPI31())) {
                    this.outputJsonMapper.addMixIn(OpenAPI.class, SortedOpenAPIMixin31.class);
                    this.outputJsonMapper.addMixIn(Schema.class, SortedSchemaMixin31.class);
                    this.outputYamlMapper.addMixIn(OpenAPI.class, SortedOpenAPIMixin31.class);
                    this.outputYamlMapper.addMixIn(Schema.class, SortedSchemaMixin31.class);
                } else {
                    this.outputJsonMapper.addMixIn(OpenAPI.class, SortedOpenAPIMixin.class);
                    this.outputJsonMapper.addMixIn(Schema.class, SortedSchemaMixin.class);
                    this.outputYamlMapper.addMixIn(OpenAPI.class, SortedOpenAPIMixin.class);
                    this.outputYamlMapper.addMixIn(Schema.class, SortedSchemaMixin.class);
                }
            }
        }
        catch (Exception e) {
            LOGGER.error("error initializing context: " + e.getMessage(), e);
            throw new OpenApiConfigurationException("error initializing context: " + e.getMessage(), e);
        }
        try {
            if (this.objectMapperProcessor != null) {
                ObjectMapper mapper2 = IntegrationObjectMapperFactory.createJson();
                this.objectMapperProcessor.processJsonObjectMapper(mapper2);
                ModelConverters.getInstance(Boolean.TRUE.equals(this.openApiConfiguration.isOpenAPI31())).addConverter(new ModelResolver(mapper2));
                this.objectMapperProcessor.processOutputJsonObjectMapper(this.outputJsonMapper);
                this.objectMapperProcessor.processOutputYamlObjectMapper(this.outputYamlMapper);
            }
        }
        catch (Exception e) {
            LOGGER.error("error configuring objectMapper: " + e.getMessage(), e);
            throw new OpenApiConfigurationException("error configuring objectMapper: " + e.getMessage(), e);
        }
        try {
            if (this.modelConverters != null && !this.modelConverters.isEmpty()) {
                for (ModelConverter converter : this.modelConverters) {
                    ModelConverters.getInstance(Boolean.TRUE.equals(this.openApiConfiguration.isOpenAPI31())).addConverter(converter);
                }
            }
        }
        catch (Exception e) {
            LOGGER.error("error configuring model converters: " + e.getMessage(), e);
            throw new OpenApiConfigurationException("error configuring model converters: " + e.getMessage(), e);
        }
        if (this.openApiConfiguration.getCacheTTL() != null) {
            this.cacheTTL = this.openApiConfiguration.getCacheTTL();
        }
        if (this.openApiConfiguration.isOpenAPI31() != null && this.openAPI31 == null) {
            this.openAPI31 = this.openApiConfiguration.isOpenAPI31();
        }
        if (this.openApiConfiguration.isConvertToOpenAPI31() != null && this.convertToOpenAPI31 == null) {
            this.convertToOpenAPI31 = this.openApiConfiguration.isConvertToOpenAPI31();
        }
        this.register();
        return (T)this;
    }

    private OpenAPIConfiguration mergeParentConfiguration(OpenAPIConfiguration config, OpenApiContext parent) {
        if (parent == null || parent.getOpenApiConfiguration() == null) {
            return config;
        }
        OpenAPIConfiguration parentConfig = parent.getOpenApiConfiguration();
        SwaggerConfiguration merged = null;
        merged = config instanceof SwaggerConfiguration ? (SwaggerConfiguration)config : (SwaggerConfiguration)ContextUtils.deepCopy(config);
        if (merged.getResourceClasses() == null) {
            merged.setResourceClasses(parentConfig.getResourceClasses());
        }
        if (merged.getFilterClass() == null) {
            merged.setFilterClass(parentConfig.getFilterClass());
        }
        if (merged.getIgnoredRoutes() == null) {
            merged.setIgnoredRoutes(parentConfig.getIgnoredRoutes());
        }
        if (merged.getOpenAPI() == null) {
            merged.setOpenAPI(parentConfig.getOpenAPI());
        }
        if (merged.getReaderClass() == null) {
            merged.setReaderClass(parentConfig.getReaderClass());
        }
        if (merged.getResourcePackages() == null) {
            merged.setResourcePackages(parentConfig.getResourcePackages());
        }
        if (merged.getScannerClass() == null) {
            merged.setScannerClass(parentConfig.getScannerClass());
        }
        if (merged.getCacheTTL() == null) {
            merged.setCacheTTL(parentConfig.getCacheTTL());
        }
        if (merged.getUserDefinedOptions() == null) {
            merged.setUserDefinedOptions(parentConfig.getUserDefinedOptions());
        }
        if (merged.isPrettyPrint() == null) {
            merged.setPrettyPrint(parentConfig.isPrettyPrint());
        }
        if (merged.isSortOutput() == null) {
            merged.setSortOutput(parentConfig.isSortOutput());
        }
        if (merged.isAlwaysResolveAppPath() == null) {
            merged.setAlwaysResolveAppPath(parentConfig.isAlwaysResolveAppPath());
        }
        if (merged.isSkipResolveAppPath() == null) {
            merged.setSkipResolveAppPath(parentConfig.isSkipResolveAppPath());
        }
        if (merged.isReadAllResources() == null) {
            merged.setReadAllResources(parentConfig.isReadAllResources());
        }
        if (merged.getObjectMapperProcessorClass() == null) {
            merged.setObjectMapperProcessorClass(parentConfig.getObjectMapperProcessorClass());
        }
        if (merged.getModelConverterClasses() == null) {
            merged.setModelConverterClassess(parentConfig.getModelConverterClasses());
        }
        if (merged.isOpenAPI31() == null) {
            merged.setOpenAPI31(parentConfig.isOpenAPI31());
        }
        if (merged.isConvertToOpenAPI31() == null) {
            merged.setConvertToOpenAPI31(parentConfig.isConvertToOpenAPI31());
        }
        if (merged.getDefaultResponseCode() == null) {
            merged.setDefaultResponseCode(parentConfig.getDefaultResponseCode());
        }
        return merged;
    }

    @Override
    public OpenAPI read() {
        if (this.cacheTTL == 0L) {
            this.resetReader();
            OpenAPI openAPI = this.getOpenApiReader().read(this.getOpenApiScanner().classes(), this.getOpenApiScanner().resources());
            if (Boolean.TRUE.equals(this.convertToOpenAPI31)) {
                openAPI = new SpecFilter().filter(openAPI, new OpenAPI31SpecFilter(), null, null, null);
            }
            return openAPI;
        }
        Cache cached = this.cache.get("openapi");
        if (cached == null || cached.isStale(this.cacheTTL)) {
            cached = new Cache();
            cached.createdAt = System.currentTimeMillis();
            this.resetReader();
            cached.openApi = this.getOpenApiReader().read(this.getOpenApiScanner().classes(), this.getOpenApiScanner().resources());
            if (Boolean.TRUE.equals(this.convertToOpenAPI31)) {
                cached.openApi = new SpecFilter().filter(cached.openApi, new OpenAPI31SpecFilter(), null, null, null);
            }
            this.cache.put("openapi", cached);
        }
        return cached.openApi;
    }

    protected void resetReader() {
        if (this.providedOpenApiReader == null) {
            try {
                this.openApiReader = this.buildReader(ContextUtils.deepCopy(this.openApiConfiguration));
            }
            catch (Exception e) {
                LOGGER.error("error building reader: " + e.getMessage(), e);
            }
        }
    }

    @JsonPropertyOrder(value={"type", "format"}, alphabetic=true)
    static abstract class SortedSchemaMixin31 {
        SortedSchemaMixin31() {
        }

        @JsonAnyGetter
        @JsonPropertyOrder(alphabetic=true)
        public abstract Map<String, Object> getExtensions();

        @JsonIgnore
        public abstract Map<String, Object> getJsonSchema();

        @JsonIgnore
        public abstract Boolean getNullable();

        @JsonIgnore
        public abstract Boolean getExclusiveMinimum();

        @JsonIgnore
        public abstract Boolean getExclusiveMaximum();

        @JsonProperty(value="exclusiveMinimum")
        public abstract BigDecimal getExclusiveMinimumValue();

        @JsonProperty(value="exclusiveMaximum")
        public abstract BigDecimal getExclusiveMaximumValue();

        @JsonIgnore
        public abstract String getType();

        @JsonProperty(value="type")
        public abstract Set<String> getTypes();

        @JsonAnySetter
        public abstract void addExtension(String var1, Object var2);

        @JsonIgnore
        public abstract boolean getExampleSetFlag();

        @JsonInclude(value=JsonInclude.Include.NON_NULL, content=JsonInclude.Include.ALWAYS)
        public abstract Object getExample();

        @JsonIgnore
        public abstract Object getJsonSchemaImpl();
    }

    @JsonPropertyOrder(value={"openapi", "info", "externalDocs", "servers", "security", "tags", "paths", "components", "webhooks"}, alphabetic=true)
    static abstract class SortedOpenAPIMixin31 {
        SortedOpenAPIMixin31() {
        }

        @JsonAnyGetter
        @JsonPropertyOrder(alphabetic=true)
        public abstract Map<String, Object> getExtensions();

        @JsonAnySetter
        public abstract void addExtension(String var1, Object var2);

        @JsonSerialize(using=PathsSerializer.class)
        public abstract Paths getPaths();
    }

    @JsonPropertyOrder(value={"type", "format"}, alphabetic=true)
    static abstract class SortedSchemaMixin {
        SortedSchemaMixin() {
        }

        @JsonAnyGetter
        @JsonPropertyOrder(alphabetic=true)
        public abstract Map<String, Object> getExtensions();

        @JsonAnySetter
        public abstract void addExtension(String var1, Object var2);

        @JsonIgnore
        public abstract boolean getExampleSetFlag();

        @JsonInclude(value=JsonInclude.Include.NON_NULL, content=JsonInclude.Include.ALWAYS)
        public abstract Object getExample();

        @JsonIgnore
        public abstract Map<String, Object> getJsonSchema();

        @JsonIgnore
        public abstract BigDecimal getExclusiveMinimumValue();

        @JsonIgnore
        public abstract BigDecimal getExclusiveMaximumValue();

        @JsonIgnore
        public abstract Map<String, Schema> getPatternProperties();

        @JsonIgnore
        public abstract Schema getContains();

        @JsonIgnore
        public abstract String get$id();

        @JsonIgnore
        public abstract String get$anchor();

        @JsonIgnore
        public abstract String get$schema();

        @JsonIgnore
        public abstract Set<String> getTypes();

        @JsonIgnore
        public abstract Object getJsonSchemaImpl();
    }

    @JsonPropertyOrder(value={"openapi", "info", "externalDocs", "servers", "security", "tags", "paths", "components"}, alphabetic=true)
    static abstract class SortedOpenAPIMixin {
        SortedOpenAPIMixin() {
        }

        @JsonAnyGetter
        @JsonPropertyOrder(alphabetic=true)
        public abstract Map<String, Object> getExtensions();

        @JsonAnySetter
        public abstract void addExtension(String var1, Object var2);

        @JsonSerialize(using=PathsSerializer.class)
        public abstract Paths getPaths();
    }

    static class Cache {
        long createdAt = 0L;
        OpenAPI openApi;

        Cache() {
        }

        boolean isStale(long cacheTTL) {
            return cacheTTL > 0L && System.currentTimeMillis() - this.createdAt > cacheTTL;
        }
    }
}

