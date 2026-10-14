/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.oas.models.media;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.SpecVersion;
import io.swagger.v3.oas.models.annotations.OpenAPI30;
import io.swagger.v3.oas.models.annotations.OpenAPI31;
import io.swagger.v3.oas.models.media.Discriminator;
import io.swagger.v3.oas.models.media.XML;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Schema<T> {
    public static final String BIND_TYPE_AND_TYPES = "bind-type";
    protected T _default;
    private String name;
    private String title = null;
    private BigDecimal multipleOf = null;
    private BigDecimal maximum = null;
    @OpenAPI30
    private Boolean exclusiveMaximum = null;
    private BigDecimal minimum = null;
    @OpenAPI30
    private Boolean exclusiveMinimum = null;
    private Integer maxLength = null;
    private Integer minLength = null;
    private String pattern = null;
    private Integer maxItems = null;
    private Integer minItems = null;
    private Boolean uniqueItems = null;
    private Integer maxProperties = null;
    private Integer minProperties = null;
    private List<String> required = null;
    @OpenAPI30
    private String type = null;
    private Schema not = null;
    private Map<String, Schema> properties = null;
    private Object additionalProperties = null;
    private String description = null;
    private String format = null;
    private String $ref = null;
    @OpenAPI30
    private Boolean nullable = null;
    private Boolean readOnly = null;
    private Boolean writeOnly = null;
    protected T example = null;
    private ExternalDocumentation externalDocs = null;
    private Boolean deprecated = null;
    private XML xml = null;
    private Map<String, Object> extensions = null;
    protected List<T> _enum = null;
    private Discriminator discriminator = null;
    private boolean exampleSetFlag;
    @OpenAPI31
    private List<Schema> prefixItems = null;
    private List<Schema> allOf = null;
    private List<Schema> anyOf = null;
    private List<Schema> oneOf = null;
    private Schema<?> items = null;
    protected T _const;
    private SpecVersion specVersion = SpecVersion.V30;
    @OpenAPI31
    private Set<String> types;
    @OpenAPI31
    private Map<String, Schema> patternProperties = null;
    @OpenAPI31
    private BigDecimal exclusiveMaximumValue = null;
    @OpenAPI31
    private BigDecimal exclusiveMinimumValue = null;
    @OpenAPI31
    private Schema contains = null;
    @OpenAPI31
    private String $id;
    @OpenAPI31
    private String $schema;
    @OpenAPI31
    private String $anchor;
    @OpenAPI31
    private String $vocabulary;
    @OpenAPI31
    private String $dynamicAnchor;
    @OpenAPI31
    private String contentEncoding;
    @OpenAPI31
    private String contentMediaType;
    @OpenAPI31
    private Schema contentSchema;
    @OpenAPI31
    private Schema propertyNames;
    @OpenAPI31
    private Schema unevaluatedProperties;
    @OpenAPI31
    private Integer maxContains;
    @OpenAPI31
    private Integer minContains;
    @OpenAPI31
    private Schema additionalItems;
    @OpenAPI31
    private Schema unevaluatedItems;
    @OpenAPI31
    private Schema _if;
    @OpenAPI31
    private Schema _else;
    @OpenAPI31
    private Schema then;
    @OpenAPI31
    private Map<String, Schema> dependentSchemas;
    @OpenAPI31
    private Map<String, List<String>> dependentRequired;
    @OpenAPI31
    private String $comment;
    @OpenAPI31
    private List<T> examples;
    @OpenAPI31
    private Boolean booleanSchemaValue;
    @OpenAPI31
    protected Map<String, Object> jsonSchema = null;
    @OpenAPI31
    protected transient Object jsonSchemaImpl = null;

    @JsonIgnore
    public SpecVersion getSpecVersion() {
        return this.specVersion;
    }

    public void setSpecVersion(SpecVersion specVersion) {
        this.specVersion = specVersion;
    }

    public Schema specVersion(SpecVersion specVersion) {
        this.setSpecVersion(specVersion);
        return this;
    }

    @OpenAPI31
    public Schema getContains() {
        return this.contains;
    }

    @OpenAPI31
    public void setContains(Schema contains) {
        this.contains = contains;
    }

    @OpenAPI31
    public String get$id() {
        return this.$id;
    }

    @OpenAPI31
    public void set$id(String $id) {
        this.$id = $id;
    }

    @OpenAPI31
    public String get$schema() {
        return this.$schema;
    }

    @OpenAPI31
    public void set$schema(String $schema) {
        this.$schema = $schema;
    }

    @OpenAPI31
    public String get$anchor() {
        return this.$anchor;
    }

    @OpenAPI31
    public void set$anchor(String $anchor) {
        this.$anchor = $anchor;
    }

    @OpenAPI31
    public BigDecimal getExclusiveMaximumValue() {
        return this.exclusiveMaximumValue;
    }

    @OpenAPI31
    public void setExclusiveMaximumValue(BigDecimal exclusiveMaximumValue) {
        this.exclusiveMaximumValue = exclusiveMaximumValue;
    }

    @OpenAPI31
    public Schema exclusiveMaximumValue(BigDecimal exclusiveMaximumValue) {
        this.exclusiveMaximumValue = exclusiveMaximumValue;
        return this;
    }

    @OpenAPI31
    public BigDecimal getExclusiveMinimumValue() {
        return this.exclusiveMinimumValue;
    }

    @OpenAPI31
    public void setExclusiveMinimumValue(BigDecimal exclusiveMinimumValue) {
        this.exclusiveMinimumValue = exclusiveMinimumValue;
    }

    @OpenAPI31
    public Schema exclusiveMinimumValue(BigDecimal exclusiveMinimumValue) {
        this.exclusiveMinimumValue = exclusiveMinimumValue;
        return this;
    }

    @OpenAPI31
    public Map<String, Schema> getPatternProperties() {
        return this.patternProperties;
    }

    @OpenAPI31
    public void setPatternProperties(Map<String, Schema> patternProperties) {
        this.patternProperties = patternProperties;
    }

    @OpenAPI31
    public Schema patternProperties(Map<String, Schema> patternProperties) {
        this.patternProperties = patternProperties;
        return this;
    }

    @OpenAPI31
    public Schema addPatternProperty(String key, Schema patternPropertiesItem) {
        if (this.patternProperties == null) {
            this.patternProperties = new LinkedHashMap<String, Schema>();
        }
        this.patternProperties.put(key, patternPropertiesItem);
        return this;
    }

    @OpenAPI31
    public Schema contains(Schema contains) {
        this.contains = contains;
        return this;
    }

    @OpenAPI31
    public Schema $id(String $id) {
        this.$id = $id;
        return this;
    }

    @OpenAPI31
    public Set<String> getTypes() {
        return this.types;
    }

    @OpenAPI31
    public void setTypes(Set<String> types) {
        this.types = types;
    }

    @OpenAPI31
    public boolean addType(String type) {
        if (this.types == null) {
            this.types = new LinkedHashSet<String>();
        }
        return this.types.add(type);
    }

    @OpenAPI31
    public Schema $schema(String $schema) {
        this.$schema = $schema;
        return this;
    }

    @OpenAPI31
    public String get$vocabulary() {
        return this.$vocabulary;
    }

    @OpenAPI31
    public void set$vocabulary(String $vocabulary) {
        this.$vocabulary = $vocabulary;
    }

    @OpenAPI31
    public Schema $vocabulary(String $vocabulary) {
        this.$vocabulary = $vocabulary;
        return this;
    }

    @OpenAPI31
    public String get$dynamicAnchor() {
        return this.$dynamicAnchor;
    }

    @OpenAPI31
    public void set$dynamicAnchor(String $dynamicAnchor) {
        this.$dynamicAnchor = $dynamicAnchor;
    }

    @OpenAPI31
    public Schema $dynamicAnchor(String $dynamicAnchor) {
        this.$dynamicAnchor = $dynamicAnchor;
        return this;
    }

    @OpenAPI31
    public Schema $anchor(String $anchor) {
        this.$anchor = $anchor;
        return this;
    }

    @OpenAPI31
    public Schema types(Set<String> types) {
        this.types = types;
        return this;
    }

    @OpenAPI31
    public Map<String, Object> getJsonSchema() {
        return this.jsonSchema;
    }

    @OpenAPI31
    public void setJsonSchema(Map<String, Object> jsonSchema) {
        this.jsonSchema = jsonSchema;
    }

    @OpenAPI31
    public Schema jsonSchema(Map<String, Object> jsonSchema) {
        this.jsonSchema = jsonSchema;
        return this;
    }

    @OpenAPI31
    public Object getJsonSchemaImpl() {
        return this.jsonSchemaImpl;
    }

    @OpenAPI31
    public void setJsonSchemaImpl(Object jsonSchemaImpl) {
        this.jsonSchemaImpl = jsonSchemaImpl;
    }

    @OpenAPI31
    public Schema jsonSchemaImpl(Object jsonSchemaImpl) {
        this.setJsonSchemaImpl(jsonSchemaImpl);
        return this;
    }

    public Schema() {
    }

    protected Schema(String type, String format) {
        this.type = type;
        this.addType(type);
        this.format = format;
    }

    public Schema(SpecVersion specVersion) {
        this.specVersion = specVersion;
    }

    protected Schema(String type, String format, SpecVersion specVersion) {
        this.type = type;
        this.addType(type);
        this.format = format;
        this.specVersion = specVersion;
    }

    public List<Schema> getAllOf() {
        return this.allOf;
    }

    public void setAllOf(List<Schema> allOf) {
        this.allOf = allOf;
    }

    public Schema allOf(List<Schema> allOf) {
        this.allOf = allOf;
        return this;
    }

    public Schema addAllOfItem(Schema allOfItem) {
        if (this.allOf == null) {
            this.allOf = new ArrayList<Schema>();
        }
        this.allOf.add(allOfItem);
        return this;
    }

    public List<Schema> getAnyOf() {
        return this.anyOf;
    }

    public void setAnyOf(List<Schema> anyOf) {
        this.anyOf = anyOf;
    }

    public Schema anyOf(List<Schema> anyOf) {
        this.anyOf = anyOf;
        return this;
    }

    public Schema addAnyOfItem(Schema anyOfItem) {
        if (this.anyOf == null) {
            this.anyOf = new ArrayList<Schema>();
        }
        this.anyOf.add(anyOfItem);
        return this;
    }

    public List<Schema> getOneOf() {
        return this.oneOf;
    }

    public void setOneOf(List<Schema> oneOf) {
        this.oneOf = oneOf;
    }

    public Schema oneOf(List<Schema> oneOf) {
        this.oneOf = oneOf;
        return this;
    }

    public Schema addOneOfItem(Schema oneOfItem) {
        if (this.oneOf == null) {
            this.oneOf = new ArrayList<Schema>();
        }
        this.oneOf.add(oneOfItem);
        return this;
    }

    public Schema<?> getItems() {
        return this.items;
    }

    public void setItems(Schema<?> items) {
        this.items = items;
    }

    public Schema items(Schema<?> items) {
        this.items = items;
        return this;
    }

    @JsonIgnore
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Schema name(String name) {
        this.setName(name);
        return this;
    }

    public Discriminator getDiscriminator() {
        return this.discriminator;
    }

    public void setDiscriminator(Discriminator discriminator) {
        this.discriminator = discriminator;
    }

    public Schema discriminator(Discriminator discriminator) {
        this.discriminator = discriminator;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Schema title(String title) {
        this.title = title;
        return this;
    }

    public T getDefault() {
        return this._default;
    }

    public void setDefault(Object _default) {
        this._default = this.cast(_default);
    }

    protected T cast(Object value) {
        return (T)value;
    }

    public List<T> getEnum() {
        return this._enum;
    }

    public void setEnum(List<T> _enum) {
        this._enum = _enum;
    }

    public void addEnumItemObject(T _enumItem) {
        if (this._enum == null) {
            this._enum = new ArrayList<T>();
        }
        this._enum.add(this.cast(_enumItem));
    }

    public BigDecimal getMultipleOf() {
        return this.multipleOf;
    }

    public void setMultipleOf(BigDecimal multipleOf) {
        this.multipleOf = multipleOf;
    }

    public Schema multipleOf(BigDecimal multipleOf) {
        this.multipleOf = multipleOf;
        return this;
    }

    public BigDecimal getMaximum() {
        return this.maximum;
    }

    public void setMaximum(BigDecimal maximum) {
        this.maximum = maximum;
    }

    public Schema maximum(BigDecimal maximum) {
        this.maximum = maximum;
        return this;
    }

    @OpenAPI30
    public Boolean getExclusiveMaximum() {
        return this.exclusiveMaximum;
    }

    @OpenAPI30
    public void setExclusiveMaximum(Boolean exclusiveMaximum) {
        this.exclusiveMaximum = exclusiveMaximum;
    }

    @OpenAPI30
    public Schema exclusiveMaximum(Boolean exclusiveMaximum) {
        this.exclusiveMaximum = exclusiveMaximum;
        return this;
    }

    public BigDecimal getMinimum() {
        return this.minimum;
    }

    public void setMinimum(BigDecimal minimum) {
        this.minimum = minimum;
    }

    public Schema minimum(BigDecimal minimum) {
        this.minimum = minimum;
        return this;
    }

    public Boolean getExclusiveMinimum() {
        return this.exclusiveMinimum;
    }

    public void setExclusiveMinimum(Boolean exclusiveMinimum) {
        this.exclusiveMinimum = exclusiveMinimum;
    }

    public Schema exclusiveMinimum(Boolean exclusiveMinimum) {
        this.exclusiveMinimum = exclusiveMinimum;
        return this;
    }

    public Integer getMaxLength() {
        return this.maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public Schema maxLength(Integer maxLength) {
        this.maxLength = maxLength;
        return this;
    }

    public Integer getMinLength() {
        return this.minLength;
    }

    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }

    public Schema minLength(Integer minLength) {
        this.minLength = minLength;
        return this;
    }

    public String getPattern() {
        return this.pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public Schema pattern(String pattern) {
        this.pattern = pattern;
        return this;
    }

    public Integer getMaxItems() {
        return this.maxItems;
    }

    public void setMaxItems(Integer maxItems) {
        this.maxItems = maxItems;
    }

    public Schema maxItems(Integer maxItems) {
        this.maxItems = maxItems;
        return this;
    }

    public Integer getMinItems() {
        return this.minItems;
    }

    public void setMinItems(Integer minItems) {
        this.minItems = minItems;
    }

    public Schema minItems(Integer minItems) {
        this.minItems = minItems;
        return this;
    }

    public Boolean getUniqueItems() {
        return this.uniqueItems;
    }

    public void setUniqueItems(Boolean uniqueItems) {
        this.uniqueItems = uniqueItems;
    }

    public Schema uniqueItems(Boolean uniqueItems) {
        this.uniqueItems = uniqueItems;
        return this;
    }

    public Integer getMaxProperties() {
        return this.maxProperties;
    }

    public void setMaxProperties(Integer maxProperties) {
        this.maxProperties = maxProperties;
    }

    public Schema maxProperties(Integer maxProperties) {
        this.maxProperties = maxProperties;
        return this;
    }

    public Integer getMinProperties() {
        return this.minProperties;
    }

    public void setMinProperties(Integer minProperties) {
        this.minProperties = minProperties;
    }

    public Schema minProperties(Integer minProperties) {
        this.minProperties = minProperties;
        return this;
    }

    public List<String> getRequired() {
        return this.required;
    }

    public void setRequired(List<String> required) {
        ArrayList<String> list = new ArrayList<String>();
        if (required != null) {
            for (String req : required) {
                if (this.properties != null && !this.properties.containsKey(req)) continue;
                list.add(req);
            }
        }
        Collections.sort(list);
        if (list.isEmpty()) {
            list = null;
        }
        this.required = list;
    }

    public Schema required(List<String> required) {
        this.required = required;
        return this;
    }

    public Schema addRequiredItem(String requiredItem) {
        if (this.required == null) {
            this.required = new ArrayList<String>();
        }
        this.required.add(requiredItem);
        Collections.sort(this.required);
        return this;
    }

    public String getType() {
        boolean bindTypes = Boolean.valueOf(System.getProperty(BIND_TYPE_AND_TYPES, "false"));
        if (bindTypes && this.type == null && this.types != null && this.types.size() == 1) {
            return this.types.iterator().next();
        }
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Schema type(String type) {
        this.type = type;
        return this;
    }

    public Schema getNot() {
        return this.not;
    }

    public void setNot(Schema not) {
        this.not = not;
    }

    public Schema not(Schema not) {
        this.not = not;
        return this;
    }

    public Map<String, Schema> getProperties() {
        return this.properties;
    }

    public void setProperties(Map<String, Schema> properties) {
        this.properties = properties;
    }

    public Schema properties(Map<String, Schema> properties) {
        this.properties = properties;
        return this;
    }

    @Deprecated
    public Schema addProperties(String key, Schema property) {
        return this.addProperty(key, property);
    }

    public Schema addProperty(String key, Schema property) {
        if (this.properties == null) {
            this.properties = new LinkedHashMap<String, Schema>();
        }
        this.properties.put(key, property);
        return this;
    }

    public Object getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperties(Object additionalProperties) {
        if (additionalProperties != null && !(additionalProperties instanceof Boolean) && !(additionalProperties instanceof Schema)) {
            throw new IllegalArgumentException("additionalProperties must be either a Boolean or a Schema instance");
        }
        this.additionalProperties = additionalProperties;
    }

    public Schema additionalProperties(Object additionalProperties) {
        this.setAdditionalProperties(additionalProperties);
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Schema description(String description) {
        this.description = description;
        return this;
    }

    public String getFormat() {
        return this.format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Schema format(String format) {
        this.format = format;
        return this;
    }

    public String get$ref() {
        return this.$ref;
    }

    public void set$ref(String $ref) {
        if ($ref != null && !$ref.startsWith("#") && $ref.indexOf(46) == -1 && $ref.indexOf(47) == -1) {
            $ref = "#/components/schemas/" + $ref;
        }
        this.$ref = $ref;
    }

    public Schema $ref(String $ref) {
        this.set$ref($ref);
        return this;
    }

    public Schema raw$ref(String $ref) {
        this.$ref = $ref;
        return this;
    }

    @OpenAPI30
    public Boolean getNullable() {
        return this.nullable;
    }

    @OpenAPI30
    public void setNullable(Boolean nullable) {
        this.nullable = nullable;
    }

    @OpenAPI30
    public Schema nullable(Boolean nullable) {
        this.nullable = nullable;
        return this;
    }

    public Boolean getReadOnly() {
        return this.readOnly;
    }

    public void setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly;
    }

    public Schema readOnly(Boolean readOnly) {
        this.readOnly = readOnly;
        return this;
    }

    public Boolean getWriteOnly() {
        return this.writeOnly;
    }

    public void setWriteOnly(Boolean writeOnly) {
        this.writeOnly = writeOnly;
    }

    public Schema writeOnly(Boolean writeOnly) {
        this.writeOnly = writeOnly;
        return this;
    }

    public Object getExample() {
        return this.example;
    }

    public void setExample(Object example) {
        this.example = this.cast(example);
        if (example == null || this.example != null) {
            this.exampleSetFlag = true;
        }
    }

    public Schema example(Object example) {
        this.setExample(example);
        return this;
    }

    public ExternalDocumentation getExternalDocs() {
        return this.externalDocs;
    }

    public void setExternalDocs(ExternalDocumentation externalDocs) {
        this.externalDocs = externalDocs;
    }

    public Schema externalDocs(ExternalDocumentation externalDocs) {
        this.externalDocs = externalDocs;
        return this;
    }

    public Boolean getDeprecated() {
        return this.deprecated;
    }

    public void setDeprecated(Boolean deprecated2) {
        this.deprecated = deprecated2;
    }

    public Schema deprecated(Boolean deprecated2) {
        this.deprecated = deprecated2;
        return this;
    }

    public XML getXml() {
        return this.xml;
    }

    public void setXml(XML xml) {
        this.xml = xml;
    }

    public Schema xml(XML xml) {
        this.xml = xml;
        return this;
    }

    public boolean getExampleSetFlag() {
        return this.exampleSetFlag;
    }

    public void setExampleSetFlag(boolean exampleSetFlag) {
        this.exampleSetFlag = exampleSetFlag;
    }

    @OpenAPI31
    public List<Schema> getPrefixItems() {
        return this.prefixItems;
    }

    @OpenAPI31
    public void setPrefixItems(List<Schema> prefixItems) {
        this.prefixItems = prefixItems;
    }

    @OpenAPI31
    public Schema prefixItems(List<Schema> prefixItems) {
        this.prefixItems = prefixItems;
        return this;
    }

    @OpenAPI31
    public Schema addPrefixItem(Schema prefixItem) {
        if (this.prefixItems == null) {
            this.prefixItems = new ArrayList<Schema>();
        }
        this.prefixItems.add(prefixItem);
        return this;
    }

    @OpenAPI31
    public String getContentEncoding() {
        return this.contentEncoding;
    }

    @OpenAPI31
    public void setContentEncoding(String contentEncoding) {
        this.contentEncoding = contentEncoding;
    }

    @OpenAPI31
    public Schema contentEncoding(String contentEncoding) {
        this.contentEncoding = contentEncoding;
        return this;
    }

    @OpenAPI31
    public String getContentMediaType() {
        return this.contentMediaType;
    }

    @OpenAPI31
    public void setContentMediaType(String contentMediaType) {
        this.contentMediaType = contentMediaType;
    }

    @OpenAPI31
    public Schema contentMediaType(String contentMediaType) {
        this.contentMediaType = contentMediaType;
        return this;
    }

    @OpenAPI31
    public Schema getContentSchema() {
        return this.contentSchema;
    }

    @OpenAPI31
    public void setContentSchema(Schema contentSchema) {
        this.contentSchema = contentSchema;
    }

    @OpenAPI31
    public Schema contentSchema(Schema contentSchema) {
        this.contentSchema = contentSchema;
        return this;
    }

    @OpenAPI31
    public Schema getPropertyNames() {
        return this.propertyNames;
    }

    @OpenAPI31
    public void setPropertyNames(Schema propertyNames) {
        this.propertyNames = propertyNames;
    }

    @OpenAPI31
    public Schema propertyNames(Schema propertyNames) {
        this.propertyNames = propertyNames;
        return this;
    }

    @OpenAPI31
    public Schema getUnevaluatedProperties() {
        return this.unevaluatedProperties;
    }

    @OpenAPI31
    public void setUnevaluatedProperties(Schema unevaluatedProperties) {
        this.unevaluatedProperties = unevaluatedProperties;
    }

    @OpenAPI31
    public Schema unevaluatedProperties(Schema unevaluatedProperties) {
        this.unevaluatedProperties = unevaluatedProperties;
        return this;
    }

    @OpenAPI31
    public Integer getMaxContains() {
        return this.maxContains;
    }

    @OpenAPI31
    public void setMaxContains(Integer maxContains) {
        this.maxContains = maxContains;
    }

    @OpenAPI31
    public Schema maxContains(Integer maxContains) {
        this.maxContains = maxContains;
        return this;
    }

    @OpenAPI31
    public Integer getMinContains() {
        return this.minContains;
    }

    @OpenAPI31
    public void setMinContains(Integer minContains) {
        this.minContains = minContains;
    }

    @OpenAPI31
    public Schema minContains(Integer minContains) {
        this.minContains = minContains;
        return this;
    }

    @OpenAPI31
    public Schema getAdditionalItems() {
        return this.additionalItems;
    }

    @OpenAPI31
    public void setAdditionalItems(Schema additionalItems) {
        this.additionalItems = additionalItems;
    }

    @OpenAPI31
    public Schema additionalItems(Schema additionalItems) {
        this.additionalItems = additionalItems;
        return this;
    }

    @OpenAPI31
    public Schema getUnevaluatedItems() {
        return this.unevaluatedItems;
    }

    @OpenAPI31
    public void setUnevaluatedItems(Schema unevaluatedItems) {
        this.unevaluatedItems = unevaluatedItems;
    }

    @OpenAPI31
    public Schema unevaluatedItems(Schema unevaluatedItems) {
        this.unevaluatedItems = unevaluatedItems;
        return this;
    }

    @OpenAPI31
    public Schema getIf() {
        return this._if;
    }

    @OpenAPI31
    public void setIf(Schema _if) {
        this._if = _if;
    }

    @OpenAPI31
    public Schema _if(Schema _if) {
        this._if = _if;
        return this;
    }

    @OpenAPI31
    public Schema getElse() {
        return this._else;
    }

    @OpenAPI31
    public void setElse(Schema _else) {
        this._else = _else;
    }

    @OpenAPI31
    public Schema _else(Schema _else) {
        this._else = _else;
        return this;
    }

    @OpenAPI31
    public Schema getThen() {
        return this.then;
    }

    @OpenAPI31
    public void setThen(Schema then) {
        this.then = then;
    }

    @OpenAPI31
    public Schema then(Schema then) {
        this.then = then;
        return this;
    }

    @OpenAPI31
    public Map<String, Schema> getDependentSchemas() {
        return this.dependentSchemas;
    }

    @OpenAPI31
    public void setDependentSchemas(Map<String, Schema> dependentSchemas) {
        this.dependentSchemas = dependentSchemas;
    }

    @OpenAPI31
    public Schema dependentSchemas(Map<String, Schema> dependentSchemas) {
        this.dependentSchemas = dependentSchemas;
        return this;
    }

    @OpenAPI31
    public Map<String, List<String>> getDependentRequired() {
        return this.dependentRequired;
    }

    @OpenAPI31
    public void setDependentRequired(Map<String, List<String>> dependentRequired) {
        this.dependentRequired = dependentRequired;
    }

    @OpenAPI31
    public Schema dependentRequired(Map<String, List<String>> dependentRequired) {
        this.dependentRequired = dependentRequired;
        return this;
    }

    @OpenAPI31
    public String get$comment() {
        return this.$comment;
    }

    @OpenAPI31
    public void set$comment(String $comment) {
        this.$comment = $comment;
    }

    @OpenAPI31
    public Schema $comment(String $comment) {
        this.$comment = $comment;
        return this;
    }

    @OpenAPI31
    public List<T> getExamples() {
        return this.examples;
    }

    @OpenAPI31
    public void setExamples(List<T> examples) {
        this.examples = examples;
    }

    @OpenAPI31
    public Schema<T> examples(List<T> examples) {
        this.examples = examples;
        return this;
    }

    @OpenAPI31
    public void addExample(T example) {
        if (this.examples == null) {
            this.examples = new ArrayList<T>();
        }
        this.examples.add(example);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Schema schema = (Schema)o;
        return Objects.equals(this.title, schema.title) && Objects.equals(this.multipleOf, schema.multipleOf) && Objects.equals(this.maximum, schema.maximum) && Objects.equals(this.exclusiveMaximum, schema.exclusiveMaximum) && Objects.equals(this.exclusiveMaximumValue, schema.exclusiveMaximumValue) && Objects.equals(this.minimum, schema.minimum) && Objects.equals(this.exclusiveMinimum, schema.exclusiveMinimum) && Objects.equals(this.exclusiveMinimumValue, schema.exclusiveMinimumValue) && Objects.equals(this.maxLength, schema.maxLength) && Objects.equals(this.minLength, schema.minLength) && Objects.equals(this.pattern, schema.pattern) && Objects.equals(this.maxItems, schema.maxItems) && Objects.equals(this.minItems, schema.minItems) && Objects.equals(this.uniqueItems, schema.uniqueItems) && Objects.equals(this.maxProperties, schema.maxProperties) && Objects.equals(this.minProperties, schema.minProperties) && Objects.equals(this.required, schema.required) && Objects.equals(this.type, schema.type) && Objects.equals(this.not, schema.not) && Objects.equals(this.properties, schema.properties) && Objects.equals(this.additionalProperties, schema.additionalProperties) && Objects.equals(this.description, schema.description) && Objects.equals(this.format, schema.format) && Objects.equals(this.$ref, schema.$ref) && Objects.equals(this.nullable, schema.nullable) && Objects.equals(this.readOnly, schema.readOnly) && Objects.equals(this.writeOnly, schema.writeOnly) && Objects.equals(this.example, schema.example) && Objects.equals(this.externalDocs, schema.externalDocs) && Objects.equals(this.deprecated, schema.deprecated) && Objects.equals(this.xml, schema.xml) && Objects.equals(this.extensions, schema.extensions) && Objects.equals(this.discriminator, schema.discriminator) && Objects.equals(this._enum, schema._enum) && Objects.equals(this.contains, schema.contains) && Objects.equals(this.patternProperties, schema.patternProperties) && Objects.equals(this.$id, schema.$id) && Objects.equals(this.$anchor, schema.$anchor) && Objects.equals(this.$schema, schema.$schema) && Objects.equals(this.$vocabulary, schema.$vocabulary) && Objects.equals(this.$dynamicAnchor, schema.$dynamicAnchor) && Objects.equals(this.types, schema.types) && Objects.equals(this.allOf, schema.allOf) && Objects.equals(this.anyOf, schema.anyOf) && Objects.equals(this.oneOf, schema.oneOf) && Objects.equals(this._const, schema._const) && Objects.equals(this._default, schema._default) && Objects.equals(this.contentEncoding, schema.contentEncoding) && Objects.equals(this.contentMediaType, schema.contentMediaType) && Objects.equals(this.contentSchema, schema.contentSchema) && Objects.equals(this.propertyNames, schema.propertyNames) && Objects.equals(this.unevaluatedProperties, schema.unevaluatedProperties) && Objects.equals(this.maxContains, schema.maxContains) && Objects.equals(this.minContains, schema.minContains) && Objects.equals(this.additionalItems, schema.additionalItems) && Objects.equals(this.unevaluatedItems, schema.unevaluatedItems) && Objects.equals(this._if, schema._if) && Objects.equals(this._else, schema._else) && Objects.equals(this.then, schema.then) && Objects.equals(this.dependentRequired, schema.dependentRequired) && Objects.equals(this.dependentSchemas, schema.dependentSchemas) && Objects.equals(this.$comment, schema.$comment) && Objects.equals(this.examples, schema.examples) && Objects.equals(this.prefixItems, schema.prefixItems) && Objects.equals(this.items, schema.items);
    }

    public int hashCode() {
        return Objects.hash(this.title, this.multipleOf, this.maximum, this.exclusiveMaximum, this.exclusiveMaximumValue, this.minimum, this.exclusiveMinimum, this.exclusiveMinimumValue, this.maxLength, this.minLength, this.pattern, this.maxItems, this.minItems, this.uniqueItems, this.maxProperties, this.minProperties, this.required, this.type, this.not, this.properties, this.additionalProperties, this.description, this.format, this.$ref, this.nullable, this.readOnly, this.writeOnly, this.example, this.externalDocs, this.deprecated, this.xml, this.extensions, this.discriminator, this._enum, this._default, this.patternProperties, this.$id, this.$anchor, this.$schema, this.$vocabulary, this.$dynamicAnchor, this.types, this.allOf, this.anyOf, this.oneOf, this._const, this.contentEncoding, this.contentMediaType, this.contentSchema, this.propertyNames, this.unevaluatedProperties, this.maxContains, this.minContains, this.additionalItems, this.unevaluatedItems, this._if, this._else, this.then, this.dependentRequired, this.dependentSchemas, this.$comment, this.examples, this.prefixItems, this.items);
    }

    public Map<String, Object> getExtensions() {
        return this.extensions;
    }

    public void addExtension(String name, Object value) {
        if (name == null || name.isEmpty() || this.specVersion == SpecVersion.V30 && !name.startsWith("x-")) {
            return;
        }
        if (this.extensions == null) {
            this.extensions = new LinkedHashMap<String, Object>();
        }
        this.extensions.put(name, value);
    }

    public void setExtensions(Map<String, Object> extensions) {
        this.extensions = extensions;
    }

    public Schema extensions(Map<String, Object> extensions) {
        this.extensions = extensions;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Schema {\n");
        Object typeStr = this.specVersion == SpecVersion.V30 ? this.type : this.types;
        sb.append("    type: ").append(this.toIndentedString(typeStr)).append("\n");
        sb.append("    format: ").append(this.toIndentedString(this.format)).append("\n");
        sb.append("    $ref: ").append(this.toIndentedString(this.$ref)).append("\n");
        sb.append("    description: ").append(this.toIndentedString(this.description)).append("\n");
        sb.append("    title: ").append(this.toIndentedString(this.title)).append("\n");
        sb.append("    multipleOf: ").append(this.toIndentedString(this.multipleOf)).append("\n");
        sb.append("    maximum: ").append(this.toIndentedString(this.maximum)).append("\n");
        Comparable<Boolean> exclusiveMaximumStr = this.specVersion == SpecVersion.V30 ? this.exclusiveMaximum : this.exclusiveMaximumValue;
        sb.append("    exclusiveMaximum: ").append(this.toIndentedString(exclusiveMaximumStr)).append("\n");
        sb.append("    minimum: ").append(this.toIndentedString(this.minimum)).append("\n");
        Comparable<Boolean> exclusiveMinimumStr = this.specVersion == SpecVersion.V30 ? this.exclusiveMinimum : this.exclusiveMinimumValue;
        sb.append("    exclusiveMinimum: ").append(this.toIndentedString(exclusiveMinimumStr)).append("\n");
        sb.append("    maxLength: ").append(this.toIndentedString(this.maxLength)).append("\n");
        sb.append("    minLength: ").append(this.toIndentedString(this.minLength)).append("\n");
        sb.append("    pattern: ").append(this.toIndentedString(this.pattern)).append("\n");
        sb.append("    maxItems: ").append(this.toIndentedString(this.maxItems)).append("\n");
        sb.append("    minItems: ").append(this.toIndentedString(this.minItems)).append("\n");
        sb.append("    uniqueItems: ").append(this.toIndentedString(this.uniqueItems)).append("\n");
        sb.append("    maxProperties: ").append(this.toIndentedString(this.maxProperties)).append("\n");
        sb.append("    minProperties: ").append(this.toIndentedString(this.minProperties)).append("\n");
        sb.append("    required: ").append(this.toIndentedString(this.required)).append("\n");
        sb.append("    not: ").append(this.toIndentedString(this.not)).append("\n");
        sb.append("    properties: ").append(this.toIndentedString(this.properties)).append("\n");
        sb.append("    additionalProperties: ").append(this.toIndentedString(this.additionalProperties)).append("\n");
        sb.append("    nullable: ").append(this.toIndentedString(this.nullable)).append("\n");
        sb.append("    readOnly: ").append(this.toIndentedString(this.readOnly)).append("\n");
        sb.append("    writeOnly: ").append(this.toIndentedString(this.writeOnly)).append("\n");
        sb.append("    example: ").append(this.toIndentedString(this.example)).append("\n");
        sb.append("    externalDocs: ").append(this.toIndentedString(this.externalDocs)).append("\n");
        sb.append("    deprecated: ").append(this.toIndentedString(this.deprecated)).append("\n");
        sb.append("    discriminator: ").append(this.toIndentedString(this.discriminator)).append("\n");
        sb.append("    xml: ").append(this.toIndentedString(this.xml)).append("\n");
        if (this.specVersion == SpecVersion.V31) {
            sb.append("    patternProperties: ").append(this.toIndentedString(this.patternProperties)).append("\n");
            sb.append("    contains: ").append(this.toIndentedString(this.contains)).append("\n");
            sb.append("    $id: ").append(this.toIndentedString(this.$id)).append("\n");
            sb.append("    $anchor: ").append(this.toIndentedString(this.$anchor)).append("\n");
            sb.append("    $schema: ").append(this.toIndentedString(this.$schema)).append("\n");
            sb.append("    $vocabulary: ").append(this.toIndentedString(this.$vocabulary)).append("\n");
            sb.append("    $dynamicAnchor: ").append(this.toIndentedString(this.$dynamicAnchor)).append("\n");
            sb.append("    const: ").append(this.toIndentedString(this._const)).append("\n");
            sb.append("    contentEncoding: ").append(this.toIndentedString(this.contentEncoding)).append("\n");
            sb.append("    contentMediaType: ").append(this.toIndentedString(this.contentMediaType)).append("\n");
            sb.append("    contentSchema: ").append(this.toIndentedString(this.contentSchema)).append("\n");
            sb.append("    propertyNames: ").append(this.toIndentedString(this.propertyNames)).append("\n");
            sb.append("    unevaluatedProperties: ").append(this.toIndentedString(this.unevaluatedProperties)).append("\n");
            sb.append("    maxContains: ").append(this.toIndentedString(this.maxContains)).append("\n");
            sb.append("    minContains: ").append(this.toIndentedString(this.minContains)).append("\n");
            sb.append("    additionalItems: ").append(this.toIndentedString(this.additionalItems)).append("\n");
            sb.append("    unevaluatedItems: ").append(this.toIndentedString(this.unevaluatedItems)).append("\n");
            sb.append("    _if: ").append(this.toIndentedString(this._if)).append("\n");
            sb.append("    _else: ").append(this.toIndentedString(this._else)).append("\n");
            sb.append("    then: ").append(this.toIndentedString(this.then)).append("\n");
            sb.append("    dependentRequired: ").append(this.toIndentedString(this.dependentRequired)).append("\n");
            sb.append("    dependentSchemas: ").append(this.toIndentedString(this.dependentSchemas)).append("\n");
            sb.append("    $comment: ").append(this.toIndentedString(this.$comment)).append("\n");
            sb.append("    prefixItems: ").append(this.toIndentedString(this.prefixItems)).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }

    protected String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

    public Schema _default(T _default) {
        this._default = _default;
        return this;
    }

    public Schema _enum(List<T> _enum) {
        this._enum = _enum;
        return this;
    }

    public Schema exampleSetFlag(boolean exampleSetFlag) {
        this.exampleSetFlag = exampleSetFlag;
        return this;
    }

    @OpenAPI31
    public T getConst() {
        return this._const;
    }

    @OpenAPI31
    public void setConst(Object _const) {
        this._const = this.cast(_const);
    }

    @OpenAPI31
    public Schema _const(Object _const) {
        this._const = this.cast(_const);
        return this;
    }

    @OpenAPI31
    public Boolean getBooleanSchemaValue() {
        return this.booleanSchemaValue;
    }

    @OpenAPI31
    public void setBooleanSchemaValue(Boolean booleanSchemaValue) {
        this.booleanSchemaValue = booleanSchemaValue;
    }

    @OpenAPI31
    public Schema booleanSchemaValue(Boolean booleanSchemaValue) {
        this.booleanSchemaValue = booleanSchemaValue;
        return this;
    }
}

