/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.core.jackson;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.core.type.ResolvedType;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyMetadata;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.util.Annotations;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverter;
import io.swagger.v3.core.converter.ModelConverterContext;
import io.swagger.v3.core.jackson.AbstractModelConverter;
import io.swagger.v3.core.jackson.JAXBAnnotationsHelper;
import io.swagger.v3.core.jackson.TypeNameResolver;
import io.swagger.v3.core.util.AnnotationsUtils;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.core.util.ObjectMapperFactory;
import io.swagger.v3.core.util.PrimitiveType;
import io.swagger.v3.core.util.RefUtils;
import io.swagger.v3.core.util.ReferenceTypeUtils;
import io.swagger.v3.core.util.ReflectionUtils;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.StringToClassMapItem;
import io.swagger.v3.oas.annotations.media.DependentRequired;
import io.swagger.v3.oas.annotations.media.DependentSchema;
import io.swagger.v3.oas.annotations.media.DependentSchemas;
import io.swagger.v3.oas.annotations.media.DiscriminatorMapping;
import io.swagger.v3.oas.annotations.media.PatternProperties;
import io.swagger.v3.oas.annotations.media.PatternProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperties;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.ComposedSchema;
import io.swagger.v3.oas.models.media.Discriminator;
import io.swagger.v3.oas.models.media.IntegerSchema;
import io.swagger.v3.oas.models.media.JsonSchema;
import io.swagger.v3.oas.models.media.MapSchema;
import io.swagger.v3.oas.models.media.NumberSchema;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.media.UUIDSchema;
import io.swagger.v3.oas.models.media.XML;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchema;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModelResolver
extends AbstractModelConverter
implements ModelConverter {
    Logger LOGGER = LoggerFactory.getLogger(ModelResolver.class);
    public static List<String> NOT_NULL_ANNOTATIONS = Arrays.asList("NotNull", "NonNull", "NotBlank", "NotEmpty");
    public static final String SET_PROPERTY_OF_COMPOSED_MODEL_AS_SIBLING = "composed-model-properties-as-sibiling";
    public static final String SET_PROPERTY_OF_ENUMS_AS_REF = "enums-as-ref";
    public static boolean composedModelPropertiesAsSibling = System.getProperty("composed-model-properties-as-sibiling") != null;
    public static boolean enumsAsRef = System.getProperty("enums-as-ref") != null;
    private boolean openapi31;

    public ModelResolver(ObjectMapper mapper2) {
        super(mapper2);
    }

    public ModelResolver(ObjectMapper mapper2, TypeNameResolver typeNameResolver) {
        super(mapper2, typeNameResolver);
    }

    public ObjectMapper objectMapper() {
        return this._mapper;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public Schema resolve(AnnotatedType annotatedType, ModelConverterContext context, Iterator<ModelConverter> next) {
        Discriminator discriminator;
        XML xml;
        Schema resolvedModel;
        boolean isComposedSchema;
        PrimitiveType primitiveType;
        Schema schema;
        String asName;
        io.swagger.v3.oas.annotations.media.Schema resolvedSchemaAnnotation;
        boolean isPrimitive = false;
        Schema model = null;
        ArrayList requiredProps = new ArrayList();
        if (annotatedType == null) {
            return null;
        }
        if (this.shouldIgnoreClass(annotatedType.getType())) {
            return null;
        }
        JavaType type = annotatedType.getType() instanceof JavaType ? (JavaType)annotatedType.getType() : this._mapper.constructType(annotatedType.getType());
        Annotation resolvedSchemaOrArrayAnnotation = AnnotationsUtils.mergeSchemaAnnotations(annotatedType.getCtxAnnotations(), type);
        io.swagger.v3.oas.annotations.media.Schema schema2 = resolvedSchemaOrArrayAnnotation == null ? null : (resolvedSchemaAnnotation = resolvedSchemaOrArrayAnnotation instanceof io.swagger.v3.oas.annotations.media.ArraySchema ? ((io.swagger.v3.oas.annotations.media.ArraySchema)resolvedSchemaOrArrayAnnotation).schema() : (io.swagger.v3.oas.annotations.media.Schema)resolvedSchemaOrArrayAnnotation);
        io.swagger.v3.oas.annotations.media.ArraySchema resolvedArrayAnnotation = resolvedSchemaOrArrayAnnotation == null ? null : (resolvedSchemaOrArrayAnnotation instanceof io.swagger.v3.oas.annotations.media.ArraySchema ? (io.swagger.v3.oas.annotations.media.ArraySchema)resolvedSchemaOrArrayAnnotation : null);
        BeanDescription recurBeanDesc = this._mapper.getSerializationConfig().introspect(type);
        HashSet<String> visited = new HashSet<String>();
        JsonSerialize jsonSerialize = recurBeanDesc.getClassAnnotations().get(JsonSerialize.class);
        while (jsonSerialize != null && !Void.class.equals(jsonSerialize.as()) && !visited.contains(asName = jsonSerialize.as().getName())) {
            visited.add(asName);
            recurBeanDesc = this._mapper.getSerializationConfig().introspect(this._mapper.constructType(jsonSerialize.as()));
            jsonSerialize = recurBeanDesc.getClassAnnotations().get(JsonSerialize.class);
        }
        BeanDescription beanDesc = recurBeanDesc;
        String name = annotatedType.getName();
        if (StringUtils.isBlank(name)) {
            if (!annotatedType.isSkipSchemaName() && resolvedSchemaAnnotation != null && !resolvedSchemaAnnotation.name().isEmpty()) {
                name = resolvedSchemaAnnotation.name();
            }
            if (StringUtils.isBlank(name) && (type.isEnumType() || !ReflectionUtils.isSystemType(type))) {
                name = this._typeName(type, beanDesc);
            }
        }
        name = this.decorateModelName(annotatedType, name);
        String schemaRefFromAnnotation = null;
        if (resolvedSchemaAnnotation != null && StringUtils.isNotEmpty(resolvedSchemaAnnotation.ref())) {
            if (resolvedArrayAnnotation == null) {
                schemaRefFromAnnotation = resolvedSchemaAnnotation.ref();
                if (!this.openapi31) {
                    return new JsonSchema().$ref(resolvedSchemaAnnotation.ref()).name(name);
                }
            } else {
                schema = new ArraySchema();
                this.resolveArraySchema(annotatedType, (ArraySchema)schema, resolvedArrayAnnotation);
                return ((ArraySchema)schema).items(new Schema().$ref(resolvedSchemaAnnotation.ref()).name(name));
            }
        }
        if (!annotatedType.isSkipOverride() && resolvedSchemaAnnotation != null && !Void.class.equals(resolvedSchemaAnnotation.implementation())) {
            Class<?> cls = resolvedSchemaAnnotation.implementation();
            this.LOGGER.debug("overriding datatype from {} to {}", (Object)type, (Object)cls.getName());
            Annotation[] ctxAnnotation = null;
            if (resolvedArrayAnnotation != null && annotatedType.getCtxAnnotations() != null) {
                ArrayList<Annotation> annList = new ArrayList<Annotation>();
                for (Annotation a : annotatedType.getCtxAnnotations()) {
                    if (a instanceof ArraySchema) continue;
                    annList.add(a);
                }
                annList.add(resolvedSchemaAnnotation);
                ctxAnnotation = annList.toArray(new Annotation[annList.size()]);
            } else {
                ctxAnnotation = annotatedType.getCtxAnnotations();
            }
            AnnotatedType aType = new AnnotatedType().type(cls).ctxAnnotations(ctxAnnotation).parent(annotatedType.getParent()).name(annotatedType.getName()).resolveAsRef(annotatedType.isResolveAsRef()).jsonViewAnnotation(annotatedType.getJsonViewAnnotation()).propertyName(annotatedType.getPropertyName()).components(annotatedType.getComponents()).skipOverride(true);
            if (resolvedArrayAnnotation != null) {
                ArraySchema schema3 = new ArraySchema();
                this.resolveArraySchema(annotatedType, schema3, resolvedArrayAnnotation);
                Schema innerSchema = null;
                Schema primitive = PrimitiveType.createProperty(cls);
                if (primitive != null) {
                    innerSchema = primitive;
                } else {
                    innerSchema = context.resolve(aType);
                    if (innerSchema != null && this.isObjectSchema(innerSchema) && StringUtils.isNotBlank(innerSchema.getName())) {
                        if (context.getDefinedModels().containsKey(innerSchema.getName())) {
                            innerSchema = new Schema().$ref(RefUtils.constructRef(innerSchema.getName()));
                        }
                    } else if (innerSchema != null && innerSchema.get$ref() != null) {
                        innerSchema = new Schema().$ref(StringUtils.isNotEmpty(innerSchema.get$ref()) ? innerSchema.get$ref() : innerSchema.getName());
                    }
                }
                schema3.setItems(innerSchema);
                return schema3;
            }
            Schema implSchema = context.resolve(aType);
            if (implSchema != null && aType.isResolveAsRef() && this.isObjectSchema(implSchema) && StringUtils.isNotBlank(implSchema.getName())) {
                if (context.getDefinedModels().containsKey(implSchema.getName())) {
                    implSchema = new Schema().$ref(RefUtils.constructRef(implSchema.getName()));
                }
            } else if (implSchema != null && implSchema.get$ref() != null) {
                implSchema = new Schema().$ref(StringUtils.isNotEmpty(implSchema.get$ref()) ? implSchema.get$ref() : implSchema.getName());
            }
            return implSchema;
        }
        if (model == null && !annotatedType.isSkipOverride() && resolvedSchemaAnnotation != null && StringUtils.isNotEmpty(resolvedSchemaAnnotation.type()) && !resolvedSchemaAnnotation.type().equals("object")) {
            primitiveType = PrimitiveType.fromTypeAndFormat(resolvedSchemaAnnotation.type(), resolvedSchemaAnnotation.format());
            if (primitiveType == null) {
                primitiveType = PrimitiveType.fromType(type);
            }
            if (primitiveType == null) {
                primitiveType = PrimitiveType.fromName(resolvedSchemaAnnotation.type());
            }
            if (primitiveType != null) {
                Schema primitive;
                model = primitive = primitiveType.createProperty();
                isPrimitive = true;
            }
        }
        if (model == null && type.isEnumType()) {
            model = new StringSchema();
            this._addEnumProps(type.getRawClass(), model);
            isPrimitive = true;
        }
        if (model == null && (primitiveType = PrimitiveType.fromType(type)) != null) {
            model = PrimitiveType.fromType(type).createProperty();
            isPrimitive = true;
        }
        if (!annotatedType.isSkipJsonIdentity()) {
            JsonIdentityInfo jsonIdentityInfo = AnnotationsUtils.getAnnotation(JsonIdentityInfo.class, annotatedType.getCtxAnnotations());
            if (jsonIdentityInfo == null) {
                jsonIdentityInfo = type.getRawClass().getAnnotation(JsonIdentityInfo.class);
            }
            if (model == null && jsonIdentityInfo != null) {
                JsonIdentityReference jsonIdentityReference = AnnotationsUtils.getAnnotation(JsonIdentityReference.class, annotatedType.getCtxAnnotations());
                if (jsonIdentityReference == null) {
                    jsonIdentityReference = type.getRawClass().getAnnotation(JsonIdentityReference.class);
                }
                if ((model = new GeneratorWrapper().processJsonIdentity(annotatedType, context, this._mapper, jsonIdentityInfo, jsonIdentityReference)) != null) {
                    return model;
                }
            }
        }
        if (model == null && annotatedType.getJsonUnwrappedHandler() != null && (model = annotatedType.getJsonUnwrappedHandler().apply(annotatedType)) == null) {
            return null;
        }
        if ("Object".equals(name)) {
            schema = new Schema();
            if (schemaRefFromAnnotation != null) {
                schema.raw$ref(schemaRefFromAnnotation);
            }
            return schema;
        }
        List<Class<?>> composedSchemaReferencedClasses = this.getComposedSchemaReferencedClasses(type.getRawClass(), annotatedType.getCtxAnnotations(), resolvedSchemaAnnotation);
        boolean bl = isComposedSchema = composedSchemaReferencedClasses != null;
        if (isPrimitive) {
            XML xml2 = this.resolveXml(beanDesc.getClassInfo(), annotatedType.getCtxAnnotations(), resolvedSchemaAnnotation);
            if (xml2 != null) {
                model.xml(xml2);
            }
            this.applyBeanValidatorAnnotations(model, annotatedType.getCtxAnnotations(), null, false);
            this.resolveSchemaMembers(model, annotatedType, context, next);
            if (resolvedArrayAnnotation != null) {
                ArraySchema schema4 = new ArraySchema();
                this.resolveArraySchema(annotatedType, schema4, resolvedArrayAnnotation);
                schema4.setItems(model);
                return schema4;
            }
            if (type.isEnumType() && this.shouldResolveEnumAsRef(resolvedSchemaAnnotation)) {
                context.defineModel(name, model, annotatedType, null);
                model = new Schema().$ref("#/components/schemas/" + name);
            }
            if (!isComposedSchema) {
                if (schemaRefFromAnnotation != null && model != null) {
                    model.raw$ref(schemaRefFromAnnotation);
                }
                return model;
            }
        }
        if ((resolvedModel = context.resolve(annotatedType)) != null && name != null && name.equals(resolvedModel.getName())) {
            return resolvedModel;
        }
        Type jsonValueType = this.findJsonValueType(beanDesc);
        if (jsonValueType != null) {
            AnnotatedType aType = new AnnotatedType().type(jsonValueType).parent(annotatedType.getParent()).name(annotatedType.getName()).schemaProperty(annotatedType.isSchemaProperty()).resolveAsRef(annotatedType.isResolveAsRef()).jsonViewAnnotation(annotatedType.getJsonViewAnnotation()).propertyName(annotatedType.getPropertyName()).ctxAnnotations(annotatedType.getCtxAnnotations()).components(annotatedType.getComponents()).skipOverride(true);
            return context.resolve(aType);
        }
        if (type.isContainerType()) {
            isComposedSchema = false;
            JavaType keyType = type.getKeyType();
            JavaType valueType = type.getContentType();
            String pName = null;
            if (valueType != null) {
                BeanDescription valueTypeBeanDesc = this._mapper.getSerializationConfig().introspect(valueType);
                pName = this._typeName(valueType, valueTypeBeanDesc);
            }
            ArrayList<io.swagger.v3.oas.annotations.media.Schema> strippedCtxAnnotations = new ArrayList<io.swagger.v3.oas.annotations.media.Schema>();
            if (resolvedSchemaAnnotation != null) {
                strippedCtxAnnotations.add(0, resolvedSchemaAnnotation);
            }
            if (annotatedType.getCtxAnnotations() != null) {
                strippedCtxAnnotations.addAll(Arrays.stream(annotatedType.getCtxAnnotations()).filter(ass -> !ass.annotationType().getName().startsWith("io.swagger") && !ass.annotationType().getName().startsWith("javax.validation.constraints")).collect(Collectors.toList()));
            }
            if (keyType != null && valueType != null) {
                if (ReflectionUtils.isSystemType(type) && !annotatedType.isSchemaProperty() && !annotatedType.isResolveAsRef()) {
                    context.resolve(new AnnotatedType().components(annotatedType.getComponents()).type(valueType).jsonViewAnnotation(annotatedType.getJsonViewAnnotation()));
                    return null;
                }
                Schema addPropertiesSchema = context.resolve(new AnnotatedType().type(valueType).schemaProperty(annotatedType.isSchemaProperty()).ctxAnnotations(strippedCtxAnnotations.toArray(new Annotation[0])).skipSchemaName(true).resolveAsRef(annotatedType.isResolveAsRef()).jsonViewAnnotation(annotatedType.getJsonViewAnnotation()).propertyName(annotatedType.getPropertyName()).components(annotatedType.getComponents()).parent(annotatedType.getParent()));
                if (addPropertiesSchema != null) {
                    if (StringUtils.isNotBlank(addPropertiesSchema.getName())) {
                        pName = addPropertiesSchema.getName();
                    }
                    if (this.isObjectSchema(addPropertiesSchema) && pName != null) {
                        if (context.getDefinedModels().containsKey(pName)) {
                            addPropertiesSchema = new Schema().$ref(RefUtils.constructRef(pName));
                        }
                    } else if (addPropertiesSchema.get$ref() != null) {
                        addPropertiesSchema = new Schema().$ref(StringUtils.isNotEmpty(addPropertiesSchema.get$ref()) ? addPropertiesSchema.get$ref() : addPropertiesSchema.getName());
                    }
                }
                Annotation[] mapModel = new MapSchema().additionalProperties(addPropertiesSchema);
                mapModel.name(name);
                model = mapModel;
            } else if (valueType != null) {
                if (ReflectionUtils.isSystemType(type) && !annotatedType.isSchemaProperty() && !annotatedType.isResolveAsRef()) {
                    context.resolve(new AnnotatedType().components(annotatedType.getComponents()).type(valueType).jsonViewAnnotation(annotatedType.getJsonViewAnnotation()));
                    return null;
                }
                Schema items = context.resolve(new AnnotatedType().type(valueType).schemaProperty(annotatedType.isSchemaProperty()).ctxAnnotations(strippedCtxAnnotations.toArray(new Annotation[0])).skipSchemaName(true).resolveAsRef(annotatedType.isResolveAsRef()).propertyName(annotatedType.getPropertyName()).jsonViewAnnotation(annotatedType.getJsonViewAnnotation()).components(annotatedType.getComponents()).parent(annotatedType.getParent()));
                if (items == null) {
                    return null;
                }
                if (annotatedType.isSchemaProperty() && annotatedType.getCtxAnnotations() != null && annotatedType.getCtxAnnotations().length > 0 && !"object".equals(items.getType())) {
                    for (Annotation annotation : annotatedType.getCtxAnnotations()) {
                        XmlElement xmlElement;
                        if (!(annotation instanceof XmlElement) || (xmlElement = (XmlElement)annotation) == null || xmlElement.name() == null || "".equals(xmlElement.name()) || "##default".equals(xmlElement.name())) continue;
                        XML xML = items.getXml() != null ? items.getXml() : new XML();
                        xML.setName(xmlElement.name());
                        items.setXml(xML);
                    }
                }
                if (StringUtils.isNotBlank(items.getName())) {
                    pName = items.getName();
                }
                if (this.isObjectSchema(items) && pName != null) {
                    if (context.getDefinedModels().containsKey(pName)) {
                        items = new Schema().$ref(RefUtils.constructRef(pName));
                    }
                } else if (items.get$ref() != null) {
                    items = new Schema().$ref(StringUtils.isNotEmpty(items.get$ref()) ? items.get$ref() : items.getName());
                }
                ArraySchema arrayModel = new ArraySchema().items(items);
                if (this._isSetType(type.getRawClass())) {
                    arrayModel.setUniqueItems(true);
                }
                arrayModel.name(name);
                model = arrayModel;
            } else if (ReflectionUtils.isSystemType(type) && !annotatedType.isSchemaProperty() && !annotatedType.isResolveAsRef()) {
                return null;
            }
        } else if (isComposedSchema) {
            model = new ComposedSchema().type("object").name(name);
        } else {
            AnnotatedType aType = ReferenceTypeUtils.unwrapReference(annotatedType);
            if (aType != null) {
                model = context.resolve(aType);
                return model;
            }
            model = new Schema().type("object").name(name);
        }
        if (!type.isContainerType() && StringUtils.isNotBlank(name)) {
            context.defineModel(name, model, annotatedType, null);
        }
        if ((xml = this.resolveXml(beanDesc.getClassInfo(), annotatedType.getCtxAnnotations(), resolvedSchemaAnnotation)) != null) {
            model.xml(xml);
        }
        if (!(model instanceof ArraySchema) || model instanceof ArraySchema && resolvedArrayAnnotation == null) {
            this.resolveSchemaMembers(model, annotatedType, context, next);
        }
        XmlAccessorType xmlAccessorTypeAnnotation = beanDesc.getClassAnnotations().get(XmlAccessorType.class);
        Set<String> propertiesToIgnore = this.resolveIgnoredProperties(beanDesc.getClassAnnotations(), annotatedType.getCtxAnnotations());
        ArrayList<Schema> props = new ArrayList<Schema>();
        LinkedHashMap<String, Schema> modelProps = new LinkedHashMap<String, Schema>();
        List<BeanPropertyDefinition> properties = beanDesc.findProperties();
        List<String> ignoredProps = this.getIgnoredProperties(beanDesc);
        properties.removeIf(p -> ignoredProps.contains(p.getName()));
        for (BeanPropertyDefinition propDef : properties) {
            void var28_46;
            Annotation propSchemaOrArray;
            void var28_44;
            Object altName;
            Member innerMember;
            JsonProperty jsonPropertyAnn;
            Schema property = null;
            String string = propDef.getName();
            Annotation[] annotations = null;
            AnnotatedMember member = propDef.getPrimaryMember();
            if (member == null) {
                BeanDescription deserBeanDesc = this._mapper.getDeserializationConfig().introspect(type);
                List<BeanPropertyDefinition> deserProperties = deserBeanDesc.findProperties();
                for (BeanPropertyDefinition prop : deserProperties) {
                    if (!StringUtils.isNotBlank(prop.getInternalName()) || !prop.getInternalName().equals(propDef.getInternalName())) continue;
                    member = prop.getPrimaryMember();
                    break;
                }
            }
            if (!(propDef.getPrimaryMember() == null || (jsonPropertyAnn = propDef.getPrimaryMember().getAnnotation(JsonProperty.class)) != null && jsonPropertyAnn.value().equals(string) || member == null || (innerMember = member.getMember()) == null || (altName = innerMember.getName()) == null)) {
                int length = ((String)altName).length();
                for (String prefix : Arrays.asList("get", "is")) {
                    int offset = prefix.length();
                    if (!((String)altName).startsWith(prefix) || length <= offset || Character.isUpperCase(((String)altName).charAt(offset))) continue;
                    Object object = altName;
                    break;
                }
            }
            PropertyMetadata md = propDef.getMetadata();
            if (member == null || this.ignore(member, xmlAccessorTypeAnnotation, (String)var28_44, propertiesToIgnore, propDef)) continue;
            ArrayList<Annotation> annotationList = new ArrayList<Annotation>();
            altName = member.annotations().iterator();
            while (altName.hasNext()) {
                Annotation a = (Annotation)altName.next();
                annotationList.add(a);
            }
            annotations = annotationList.toArray(new Annotation[annotationList.size()]);
            if (this.hiddenByJsonView(annotations, annotatedType)) continue;
            JavaType propType = member.getType();
            if (propType != null && "void".equals(propType.getRawClass().getName()) && member instanceof AnnotatedMethod) {
                propType = ((AnnotatedMethod)member).getParameterType(0);
            }
            String propSchemaName = null;
            io.swagger.v3.oas.annotations.media.Schema ctxSchema = AnnotationsUtils.getSchemaAnnotation(annotations);
            if (AnnotationsUtils.hasSchemaAnnotation(ctxSchema) && !StringUtils.isBlank(ctxSchema.name())) {
                propSchemaName = ctxSchema.name();
            }
            io.swagger.v3.oas.annotations.media.ArraySchema ctxArraySchema = AnnotationsUtils.getArraySchemaAnnotation(annotations);
            if (propSchemaName == null && AnnotationsUtils.hasArrayAnnotation(ctxArraySchema) && AnnotationsUtils.hasSchemaAnnotation(ctxArraySchema.schema()) && !StringUtils.isBlank(ctxArraySchema.schema().name())) {
                propSchemaName = ctxArraySchema.schema().name();
            }
            if (StringUtils.isNotBlank(propSchemaName)) {
                String string2 = propSchemaName;
            }
            io.swagger.v3.oas.annotations.media.Schema propResolvedSchemaAnnotation = (propSchemaOrArray = AnnotationsUtils.mergeSchemaAnnotations(annotations, propType)) == null ? null : (propSchemaOrArray instanceof io.swagger.v3.oas.annotations.media.ArraySchema ? ((io.swagger.v3.oas.annotations.media.ArraySchema)propSchemaOrArray).schema() : (io.swagger.v3.oas.annotations.media.Schema)propSchemaOrArray);
            Schema.AccessMode accessMode = this.resolveAccessMode(propDef, type, propResolvedSchemaAnnotation);
            Schema.RequiredMode requiredMode = this.resolveRequiredMode(propResolvedSchemaAnnotation);
            Annotation[] ctxAnnotation31 = null;
            if (this.openapi31) {
                ArrayList<Annotation> ctxAnnotations31List = new ArrayList<Annotation>();
                if (annotations != null) {
                    for (Annotation a : annotations) {
                        if (a instanceof io.swagger.v3.oas.annotations.media.Schema || a instanceof io.swagger.v3.oas.annotations.media.ArraySchema) continue;
                        ctxAnnotations31List.add(a);
                    }
                    ctxAnnotation31 = ctxAnnotations31List.toArray(new Annotation[ctxAnnotations31List.size()]);
                }
            }
            AnnotatedType aType = new AnnotatedType().type(propType).ctxAnnotations(this.openapi31 ? ctxAnnotation31 : annotations).parent(model).resolveAsRef(annotatedType.isResolveAsRef()).jsonViewAnnotation(annotatedType.getJsonViewAnnotation()).skipSchemaName(true).schemaProperty(true).components(annotatedType.getComponents()).propertyName((String)var28_46);
            AnnotatedMember propMember = member;
            aType.jsonUnwrappedHandler(t2 -> {
                JsonUnwrapped uw = propMember.getAnnotation(JsonUnwrapped.class);
                if (uw != null && uw.enabled()) {
                    t2.ctxAnnotations(null).jsonUnwrappedHandler(null).resolveAsRef(false);
                    this.handleUnwrapped(props, context.resolve((AnnotatedType)t2), uw.prefix(), uw.suffix(), requiredProps);
                    return null;
                }
                return new Schema();
            });
            property = context.resolve(aType);
            property = this.clone(property);
            if (this.openapi31) {
                Optional<Schema> reResolvedProperty = AnnotationsUtils.getSchemaFromAnnotation(ctxSchema, annotatedType.getComponents(), null, this.openapi31, property, context);
                if (reResolvedProperty.isPresent()) {
                    property = reResolvedProperty.get();
                }
                if ((reResolvedProperty = AnnotationsUtils.getArraySchema(ctxArraySchema, annotatedType.getComponents(), null, this.openapi31, property)).isPresent()) {
                    property = reResolvedProperty.get();
                }
            }
            if (property == null) continue;
            Boolean required = md.getRequired();
            if (required != null && !Boolean.FALSE.equals(required)) {
                this.addRequiredItem(model, (String)var28_46);
            } else if (propDef.isRequired()) {
                this.addRequiredItem(model, (String)var28_46);
            }
            if ((property.get$ref() == null || this.openapi31) && accessMode != null) {
                switch (accessMode) {
                    case AUTO: {
                        break;
                    }
                    case READ_ONLY: {
                        property.readOnly(true);
                        break;
                    }
                    case READ_WRITE: {
                        break;
                    }
                    case WRITE_ONLY: {
                        property.writeOnly(true);
                        break;
                    }
                }
            }
            BeanDescription propBeanDesc = this._mapper.getSerializationConfig().introspect(propType);
            if (property != null && !propType.isContainerType()) {
                if (this.isObjectSchema(property)) {
                    String pName = this._typeName(propType, propBeanDesc);
                    if (StringUtils.isNotBlank(property.getName())) {
                        pName = property.getName();
                    }
                    if (context.getDefinedModels().containsKey(pName)) {
                        property = new Schema().$ref(RefUtils.constructRef(pName));
                        property = this.clone(property);
                        if (this.openapi31) {
                            Optional<Schema> reResolvedProperty = AnnotationsUtils.getSchemaFromAnnotation(ctxSchema, annotatedType.getComponents(), null, this.openapi31, property, context);
                            if (reResolvedProperty.isPresent()) {
                                property = reResolvedProperty.get();
                            }
                            if ((reResolvedProperty = AnnotationsUtils.getArraySchema(ctxArraySchema, annotatedType.getComponents(), null, this.openapi31, property)).isPresent()) {
                                property = reResolvedProperty.get();
                            }
                        }
                    }
                } else if (property.get$ref() != null) {
                    if (!this.openapi31) {
                        property = new Schema().$ref(StringUtils.isNotEmpty(property.get$ref()) ? property.get$ref() : property.getName());
                    } else if (StringUtils.isEmpty(property.get$ref())) {
                        property.$ref(property.getName());
                    }
                }
            }
            property.setName((String)var28_46);
            JAXBAnnotationsHelper.apply(propBeanDesc.getClassInfo(), annotations, property);
            if (property != null && Schema.RequiredMode.REQUIRED.equals((Object)requiredMode)) {
                this.addRequiredItem(model, property.getName());
            }
            boolean applyNotNullAnnotations = Schema.RequiredMode.AUTO.equals((Object)requiredMode);
            annotations = this.addGenericTypeArgumentAnnotationsForOptionalField(propDef, annotations);
            this.applyBeanValidatorAnnotations(propDef, property, annotations, model, applyNotNullAnnotations);
            props.add(property);
        }
        for (Schema prop : props) {
            modelProps.put(prop.getName(), prop);
        }
        if (modelProps.size() > 0) {
            if (model.getProperties() == null) {
                model.setProperties(modelProps);
            } else {
                for (String key : modelProps.keySet()) {
                    model.addProperty(key, (Schema)modelProps.get(key));
                }
            }
            for (String propName : requiredProps) {
                this.addRequiredItem(model, propName);
            }
        }
        if (!type.isContainerType() && StringUtils.isNotBlank(name)) {
            context.defineModel(name, model, annotatedType, null);
        }
        if (!this.resolveSubtypes(model, beanDesc, context, annotatedType.getJsonViewAnnotation())) {
            model.setDiscriminator(null);
        }
        if ((discriminator = this.resolveDiscriminator(type, context)) != null) {
            model.setDiscriminator(discriminator);
        }
        if (resolvedSchemaAnnotation != null) {
            Class<?> not;
            String ref = resolvedSchemaAnnotation.ref();
            if (!StringUtils.isBlank(ref)) {
                model.$ref(ref);
            }
            if (!Void.class.equals(not = resolvedSchemaAnnotation.not())) {
                model.not(new Schema().$ref(context.resolve(new AnnotatedType().components(annotatedType.getComponents()).type(not).jsonViewAnnotation(annotatedType.getJsonViewAnnotation())).getName()));
            }
            if (resolvedSchemaAnnotation.requiredProperties() != null && resolvedSchemaAnnotation.requiredProperties().length > 0 && StringUtils.isNotBlank(resolvedSchemaAnnotation.requiredProperties()[0])) {
                for (String prop : resolvedSchemaAnnotation.requiredProperties()) {
                    this.addRequiredItem(model, prop);
                }
            }
        }
        Map<String, Schema> patternProperties = this.resolvePatternProperties(type, annotatedType.getCtxAnnotations(), context);
        if (model != null && patternProperties != null && !patternProperties.isEmpty()) {
            if (model.getPatternProperties() == null) {
                model.patternProperties(patternProperties);
            } else {
                model.getPatternProperties().putAll(patternProperties);
            }
        }
        Map<String, Schema> schemaProperties = this.resolveSchemaProperties(type, annotatedType.getCtxAnnotations(), context);
        if (model != null && schemaProperties != null && !schemaProperties.isEmpty()) {
            if (model.getProperties() == null) {
                model.properties(schemaProperties);
            } else {
                model.getProperties().putAll(schemaProperties);
            }
        }
        if (this.openapi31) {
            Map<String, Schema> map = this.resolveDependentSchemas(type, annotatedType.getCtxAnnotations(), context, annotatedType.getComponents(), annotatedType.getJsonViewAnnotation(), this.openapi31);
            if (model != null && map != null && !map.isEmpty()) {
                if (model.getDependentSchemas() == null) {
                    model.dependentSchemas(map);
                } else {
                    model.getDependentSchemas().putAll(map);
                }
            }
        }
        if (isComposedSchema) {
            ComposedSchema composedSchema = (ComposedSchema)model;
            Class<?>[] allOf = resolvedSchemaAnnotation.allOf();
            Class<?>[] anyOf = resolvedSchemaAnnotation.anyOf();
            Class<?>[] oneOf = resolvedSchemaAnnotation.oneOf();
            List<Class> allOfFiltered = Stream.of(allOf).distinct().filter(c -> !this.shouldIgnoreClass((Type)c)).filter(c -> !c.equals(Void.class)).collect(Collectors.toList());
            allOfFiltered.forEach(c -> {
                Schema allOfRef = context.resolve(new AnnotatedType().components(annotatedType.getComponents()).type((Type)c).jsonViewAnnotation(annotatedType.getJsonViewAnnotation()));
                Schema refSchema = new Schema().$ref("#/components/schemas/" + allOfRef.getName());
                if (StringUtils.isBlank(allOfRef.getName())) {
                    refSchema = allOfRef;
                }
                if (composedSchema.getAllOf() == null || !composedSchema.getAllOf().contains(refSchema)) {
                    composedSchema.addAllOfItem(refSchema);
                }
                if (this.isSubtype(beanDesc.getClassInfo(), (Class<?>)c)) {
                    this.removeParentProperties(composedSchema, allOfRef);
                }
            });
            List<Class> anyOfFiltered = Stream.of(anyOf).distinct().filter(c -> !this.shouldIgnoreClass((Type)c)).filter(c -> !c.equals(Void.class)).collect(Collectors.toList());
            anyOfFiltered.forEach(c -> {
                Schema anyOfRef = context.resolve(new AnnotatedType().components(annotatedType.getComponents()).type((Type)c).jsonViewAnnotation(annotatedType.getJsonViewAnnotation()));
                if (StringUtils.isNotBlank(anyOfRef.getName())) {
                    composedSchema.addAnyOfItem(new Schema().$ref("#/components/schemas/" + anyOfRef.getName()));
                } else {
                    composedSchema.addAnyOfItem(anyOfRef);
                }
                if (this.isSubtype(beanDesc.getClassInfo(), (Class<?>)c)) {
                    this.removeParentProperties(composedSchema, anyOfRef);
                }
            });
            List<Class> oneOfFiltered = Stream.of(oneOf).distinct().filter(c -> !this.shouldIgnoreClass((Type)c)).filter(c -> !c.equals(Void.class)).collect(Collectors.toList());
            oneOfFiltered.forEach(c -> {
                Schema oneOfRef = context.resolve(new AnnotatedType().components(annotatedType.getComponents()).type((Type)c).jsonViewAnnotation(annotatedType.getJsonViewAnnotation()));
                if (oneOfRef != null) {
                    if (StringUtils.isBlank(oneOfRef.getName())) {
                        composedSchema.addOneOfItem(oneOfRef);
                    } else {
                        composedSchema.addOneOfItem(new Schema().$ref("#/components/schemas/" + oneOfRef.getName()));
                    }
                    if (this.isSubtype(beanDesc.getClassInfo(), (Class<?>)c)) {
                        this.removeParentProperties(composedSchema, oneOfRef);
                    }
                }
            });
            if (!(composedModelPropertiesAsSibling || composedSchema.getAllOf() == null || composedSchema.getAllOf().isEmpty() || composedSchema.getProperties() == null || composedSchema.getProperties().isEmpty())) {
                ObjectSchema propSchema = new ObjectSchema();
                propSchema.properties(composedSchema.getProperties());
                composedSchema.setProperties(null);
                composedSchema.addAllOfItem(propSchema);
            }
        }
        if (!type.isContainerType() && StringUtils.isNotBlank(name)) {
            context.defineModel(name, model, annotatedType, null);
        }
        if (model != null && annotatedType.isResolveAsRef() && (isComposedSchema || this.isObjectSchema(model)) && StringUtils.isNotBlank(model.getName())) {
            if (context.getDefinedModels().containsKey(model.getName())) {
                model = new Schema().$ref(RefUtils.constructRef(model.getName()));
            }
        } else if (model != null && model.get$ref() != null) {
            model = new Schema().$ref(StringUtils.isNotEmpty(model.get$ref()) ? model.get$ref() : model.getName());
        }
        if (model != null && resolvedArrayAnnotation != null) {
            if (!"array".equals(model.getType())) {
                ArraySchema arraySchema = new ArraySchema();
                arraySchema.setItems(model);
                this.resolveArraySchema(annotatedType, arraySchema, resolvedArrayAnnotation);
                return arraySchema;
            }
            if (model instanceof ArraySchema) {
                this.resolveArraySchema(annotatedType, (ArraySchema)model, resolvedArrayAnnotation);
            }
        }
        this.resolveDiscriminatorProperty(type, context, model);
        model = this.resolveWrapping(type, context, model);
        return model;
    }

    private Annotation[] addGenericTypeArgumentAnnotationsForOptionalField(BeanPropertyDefinition propDef, Annotation[] annotations) {
        boolean isNotOptionalType = Optional.ofNullable(propDef).map(BeanPropertyDefinition::getField).map(AnnotatedField::getAnnotated).map(field2 -> !field2.getType().equals(Optional.class)).orElse(false);
        if (isNotOptionalType) {
            return annotations;
        }
        Stream<Annotation> genericTypeArgumentAnnotations = this.extractGenericTypeArgumentAnnotations(propDef);
        return (Annotation[])Stream.concat(Stream.of(annotations), genericTypeArgumentAnnotations).toArray(Annotation[]::new);
    }

    private Stream<Annotation> extractGenericTypeArgumentAnnotations(BeanPropertyDefinition propDef) {
        return Optional.ofNullable(propDef).map(BeanPropertyDefinition::getField).map(AnnotatedField::getAnnotated).map(this::getGenericTypeArgumentAnnotations).orElseGet(() -> Stream.of(new Annotation[0]));
    }

    private Stream<Annotation> getGenericTypeArgumentAnnotations(Field field2) {
        return Optional.of(field2.getAnnotatedType()).filter(annotatedType -> annotatedType instanceof AnnotatedParameterizedType).map(annotatedType -> (AnnotatedParameterizedType)annotatedType).map(AnnotatedParameterizedType::getAnnotatedActualTypeArguments).map(types -> Stream.of(types).flatMap(type -> Stream.of(type.getAnnotations()))).orElseGet(() -> Stream.of(new Annotation[0]));
    }

    private boolean shouldResolveEnumAsRef(io.swagger.v3.oas.annotations.media.Schema resolvedSchemaAnnotation) {
        return resolvedSchemaAnnotation != null && resolvedSchemaAnnotation.enumAsRef() || enumsAsRef;
    }

    protected Type findJsonValueType(BeanDescription beanDesc) {
        block3: {
            try {
                Method m4 = BeanDescription.class.getMethod("findJsonValueAccessor", new Class[0]);
                AnnotatedMember jsonValueMember = (AnnotatedMember)m4.invoke((Object)beanDesc, new Object[0]);
                if (jsonValueMember != null) {
                    return jsonValueMember.getType();
                }
            }
            catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                this.LOGGER.warn("jackson BeanDescription.findJsonValueAccessor not found, this could lead to inaccurate result, please update jackson to 2.9+");
                AnnotatedMethod jsonValueMethod = beanDesc.findJsonValueMethod();
                if (jsonValueMethod == null) break block3;
                return jsonValueMethod.getType();
            }
        }
        return null;
    }

    private Schema clone(Schema property) {
        return AnnotationsUtils.clone(property, this.openapi31);
    }

    private boolean isSubtype(AnnotatedClass childClass, Class<?> parentClass) {
        BeanDescription parentDesc = this._mapper.getSerializationConfig().introspectClassAnnotations(parentClass);
        List<NamedType> subTypes = this._intr.findSubtypes(parentDesc.getClassInfo());
        if (subTypes == null) {
            return false;
        }
        for (NamedType subtype : subTypes) {
            Class<?> subtypeType = subtype.getType();
            if (!childClass.getRawType().isAssignableFrom(subtypeType)) continue;
            return true;
        }
        return false;
    }

    protected boolean _isOptionalType(JavaType propType) {
        return Arrays.asList("com.google.common.base.Optional", "java.util.Optional").contains(propType.getRawClass().getCanonicalName());
    }

    protected void _addEnumProps(Class<?> propClass, Schema property) {
        boolean useIndex = this._mapper.isEnabled(SerializationFeature.WRITE_ENUMS_USING_INDEX);
        boolean useToString = this._mapper.isEnabled(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        Optional<Method> jsonValueMethod = Arrays.stream(propClass.getDeclaredMethods()).filter(m4 -> m4.isAnnotationPresent(JsonValue.class)).filter(m4 -> m4.getAnnotation(JsonValue.class).value()).findFirst();
        Optional<Field> jsonValueField = Arrays.stream(propClass.getDeclaredFields()).filter(f -> f.isAnnotationPresent(JsonValue.class)).filter(f -> f.getAnnotation(JsonValue.class).value()).findFirst();
        jsonValueMethod.ifPresent(m4 -> m4.setAccessible(true));
        jsonValueField.ifPresent(m4 -> m4.setAccessible(true));
        Class<?> enumClass = propClass;
        Enum[] enumConstants = (Enum[])enumClass.getEnumConstants();
        if (enumConstants != null) {
            String[] enumValues = this._intr.findEnumValues(propClass, enumConstants, new String[enumConstants.length]);
            for (Enum en : enumConstants) {
                Field enumField = ReflectionUtils.findField(en.name(), enumClass);
                if (null != enumField && enumField.isAnnotationPresent(Hidden.class)) continue;
                String enumValue = enumValues[en.ordinal()];
                String methodValue = jsonValueMethod.flatMap(m4 -> ReflectionUtils.safeInvoke(m4, en, new Object[0])).map(Object::toString).orElse(null);
                String fieldValue = jsonValueField.flatMap(f -> ReflectionUtils.safeGet(f, en)).map(Object::toString).orElse(null);
                String n = methodValue != null ? methodValue : (fieldValue != null ? fieldValue : (enumValue != null ? enumValue : (useIndex ? String.valueOf(en.ordinal()) : (useToString ? en.toString() : this._intr.findEnumValue(en)))));
                if (!(property instanceof StringSchema)) continue;
                StringSchema sp2 = (StringSchema)property;
                sp2.addEnumItem(n);
            }
        }
    }

    protected boolean ignore(Annotated member, XmlAccessorType xmlAccessorTypeAnnotation, String propName, Set<String> propertiesToIgnore) {
        return this.ignore(member, xmlAccessorTypeAnnotation, propName, propertiesToIgnore, null);
    }

    protected boolean hasHiddenAnnotation(Annotated annotated) {
        return annotated.hasAnnotation(Hidden.class) || annotated.hasAnnotation(io.swagger.v3.oas.annotations.media.Schema.class) && annotated.getAnnotation(io.swagger.v3.oas.annotations.media.Schema.class).hidden();
    }

    protected boolean ignore(Annotated member, XmlAccessorType xmlAccessorTypeAnnotation, String propName, Set<String> propertiesToIgnore, BeanPropertyDefinition propDef) {
        if (propertiesToIgnore.contains(propName)) {
            return true;
        }
        if (member.hasAnnotation(JsonIgnore.class) && member.getAnnotation(JsonIgnore.class).value()) {
            return true;
        }
        if (this.hasHiddenAnnotation(member)) {
            return true;
        }
        if (propDef != null) {
            if (propDef.hasGetter() && this.hasHiddenAnnotation(propDef.getGetter())) {
                return true;
            }
            if (propDef.hasSetter() && this.hasHiddenAnnotation(propDef.getSetter())) {
                return true;
            }
            if (propDef.hasConstructorParameter() && this.hasHiddenAnnotation(propDef.getConstructorParameter())) {
                return true;
            }
            if (propDef.hasField() && this.hasHiddenAnnotation(propDef.getField())) {
                return true;
            }
        }
        if (xmlAccessorTypeAnnotation == null) {
            return false;
        }
        return xmlAccessorTypeAnnotation.value().equals((Object)XmlAccessType.NONE) && !member.hasAnnotation(XmlElement.class) && !member.hasAnnotation(XmlAttribute.class) && !member.hasAnnotation(XmlElementRef.class) && !member.hasAnnotation(XmlElementRefs.class) && !member.hasAnnotation(JsonProperty.class);
    }

    private void handleUnwrapped(List<Schema> props, Schema innerModel, String prefix, String suffix, List<String> requiredProps) {
        if (StringUtils.isBlank(suffix) && StringUtils.isBlank(prefix)) {
            if (innerModel.getProperties() != null) {
                props.addAll(innerModel.getProperties().values());
                if (innerModel.getRequired() != null) {
                    requiredProps.addAll(innerModel.getRequired());
                }
            }
        } else {
            if (prefix == null) {
                prefix = "";
            }
            if (suffix == null) {
                suffix = "";
            }
            if (innerModel.getProperties() != null) {
                for (Schema prop : innerModel.getProperties().values()) {
                    try {
                        Schema clonedProp = Json.mapper().readValue(Json.pretty(prop), Schema.class);
                        clonedProp.setName(prefix + prop.getName() + suffix);
                        props.add(clonedProp);
                    }
                    catch (IOException e) {
                        this.LOGGER.error("Exception cloning property", e);
                        return;
                    }
                }
            }
        }
    }

    protected void applyBeanValidatorAnnotations(BeanPropertyDefinition propDef, Schema property, Annotation[] annotations, Schema parent, boolean applyNotNullAnnotations) {
        this.applyBeanValidatorAnnotations(property, annotations, parent, applyNotNullAnnotations);
        if (Objects.nonNull(property.getItems())) {
            Annotation[] genericTypeArgumentAnnotations = (Annotation[])this.extractGenericTypeArgumentAnnotations(propDef).toArray(Annotation[]::new);
            this.applyBeanValidatorAnnotations(property.getItems(), genericTypeArgumentAnnotations, property, applyNotNullAnnotations);
        }
    }

    protected void applyBeanValidatorAnnotations(Schema property, Annotation[] annotations, Schema parent, boolean applyNotNullAnnotations) {
        Annotation max;
        Annotation min2;
        boolean requiredItem;
        HashMap<String, Annotation> annos = new HashMap<String, Annotation>();
        if (annotations != null) {
            for (Annotation anno : annotations) {
                annos.put(anno.annotationType().getName(), anno);
            }
        }
        if (parent != null && annotations != null && applyNotNullAnnotations && (requiredItem = Arrays.stream(annotations).anyMatch(annotation -> NOT_NULL_ANNOTATIONS.contains(annotation.annotationType().getSimpleName())))) {
            this.addRequiredItem(parent, property.getName());
        }
        if (annos.containsKey("javax.validation.constraints.Min") && ("integer".equals(property.getType()) || "number".equals(property.getType()))) {
            min2 = (Min)annos.get("javax.validation.constraints.Min");
            property.setMinimum(new BigDecimal(min2.value()));
        }
        if (annos.containsKey("javax.validation.constraints.Max") && ("integer".equals(property.getType()) || "number".equals(property.getType()))) {
            max = (Max)annos.get("javax.validation.constraints.Max");
            property.setMaximum(new BigDecimal(max.value()));
        }
        if (annos.containsKey("javax.validation.constraints.Size")) {
            Size size = (Size)annos.get("javax.validation.constraints.Size");
            if ("integer".equals(property.getType()) || "number".equals(property.getType())) {
                property.setMinimum(new BigDecimal(size.min()));
                property.setMaximum(new BigDecimal(size.max()));
            } else if (property instanceof StringSchema) {
                StringSchema sp2 = (StringSchema)property;
                sp2.minLength(size.min());
                sp2.maxLength(size.max());
            } else if (property instanceof ArraySchema) {
                ArraySchema sp3 = (ArraySchema)property;
                sp3.setMinItems(size.min());
                sp3.setMaxItems(size.max());
            }
        }
        if (annos.containsKey("javax.validation.constraints.DecimalMin")) {
            min2 = (DecimalMin)annos.get("javax.validation.constraints.DecimalMin");
            if (property instanceof NumberSchema) {
                NumberSchema ap = (NumberSchema)property;
                ap.setMinimum(new BigDecimal(min2.value()));
                ap.setExclusiveMinimum(!min2.inclusive());
            }
        }
        if (annos.containsKey("javax.validation.constraints.DecimalMax")) {
            max = (DecimalMax)annos.get("javax.validation.constraints.DecimalMax");
            if (property instanceof NumberSchema) {
                NumberSchema ap = (NumberSchema)property;
                ap.setMaximum(new BigDecimal(max.value()));
                ap.setExclusiveMaximum(!max.inclusive());
            }
        }
        if (annos.containsKey("javax.validation.constraints.Pattern")) {
            Pattern pattern = (Pattern)annos.get("javax.validation.constraints.Pattern");
            if (property instanceof StringSchema) {
                property.setPattern(pattern.regexp());
            }
            if (property.getItems() != null && property.getItems() instanceof StringSchema) {
                property.getItems().setPattern(pattern.regexp());
            }
        }
    }

    private boolean resolveSubtypes(Schema model, BeanDescription bean, ModelConverterContext context, JsonView jsonViewAnnotation) {
        List<NamedType> types = this._intr.findSubtypes(bean.getClassInfo());
        if (types == null) {
            return false;
        }
        this.removeSelfFromSubTypes(types, bean);
        this.removeSuperClassAndInterfaceSubTypes(types, bean);
        boolean count = false;
        AnnotatedElement beanClass = bean.getClassInfo().getAnnotated();
        for (NamedType subtype : types) {
            Class<?> subtypeType = subtype.getType();
            if (!((Class)beanClass).isAssignableFrom(subtypeType)) continue;
            Schema subtypeModel = context.resolve(new AnnotatedType().type(subtypeType).jsonViewAnnotation(jsonViewAnnotation));
            if (StringUtils.isBlank(subtypeModel.getName()) || subtypeModel.getName().equals(model.getName())) {
                subtypeModel.setName(this._typeNameResolver.nameForType(this._mapper.constructType(subtypeType), TypeNameResolver.Options.SKIP_API_MODEL));
            }
            ComposedSchema composedSchema = null;
            if (!(subtypeModel instanceof ComposedSchema)) {
                composedSchema = (ComposedSchema)new ComposedSchema().title(subtypeModel.getTitle()).name(subtypeModel.getName()).deprecated(subtypeModel.getDeprecated()).additionalProperties(subtypeModel.getAdditionalProperties()).description(subtypeModel.getDescription()).discriminator(subtypeModel.getDiscriminator()).exclusiveMaximum(subtypeModel.getExclusiveMaximum()).exclusiveMinimum(subtypeModel.getExclusiveMinimum()).externalDocs(subtypeModel.getExternalDocs()).format(subtypeModel.getFormat()).maximum(subtypeModel.getMaximum()).maxItems(subtypeModel.getMaxItems()).maxLength(subtypeModel.getMaxLength()).maxProperties(subtypeModel.getMaxProperties()).minimum(subtypeModel.getMinimum()).minItems(subtypeModel.getMinItems()).minLength(subtypeModel.getMinLength()).minProperties(subtypeModel.getMinProperties()).multipleOf(subtypeModel.getMultipleOf()).not(subtypeModel.getNot()).nullable(subtypeModel.getNullable()).pattern(subtypeModel.getPattern()).properties(subtypeModel.getProperties()).readOnly(subtypeModel.getReadOnly()).required(subtypeModel.getRequired()).type(subtypeModel.getType()).uniqueItems(subtypeModel.getUniqueItems()).writeOnly(subtypeModel.getWriteOnly()).xml(subtypeModel.getXml()).extensions(subtypeModel.getExtensions());
                if (subtypeModel.getExample() != null || subtypeModel.getExampleSetFlag()) {
                    composedSchema.example(subtypeModel.getExample());
                }
                composedSchema.setEnum(subtypeModel.getEnum());
            } else {
                composedSchema = (ComposedSchema)subtypeModel;
            }
            Schema refSchema = new Schema().$ref("#/components/schemas/" + model.getName());
            if (composedSchema.getAllOf() == null || !composedSchema.getAllOf().contains(refSchema)) {
                composedSchema.addAllOfItem(refSchema);
            }
            this.removeParentProperties(composedSchema, model);
            if (!(composedModelPropertiesAsSibling || composedSchema.getAllOf() == null || composedSchema.getAllOf().isEmpty() || composedSchema.getProperties() == null || composedSchema.getProperties().isEmpty())) {
                ObjectSchema propSchema = new ObjectSchema();
                propSchema.properties(composedSchema.getProperties());
                composedSchema.setProperties(null);
                composedSchema.addAllOfItem(propSchema);
            }
            Class<?> currentType = subtype.getType();
            if (!StringUtils.isNotBlank(composedSchema.getName())) continue;
            context.defineModel(composedSchema.getName(), (Schema)composedSchema, new AnnotatedType().type(currentType), null);
        }
        return count;
    }

    private void removeSelfFromSubTypes(List<NamedType> types, BeanDescription bean) {
        Class<?> beanClass = bean.getType().getRawClass();
        types.removeIf(type -> beanClass.equals(type.getType()));
    }

    private void removeSuperClassAndInterfaceSubTypes(List<NamedType> types, BeanDescription bean) {
        Class<?> beanClass = bean.getType().getRawClass();
        Class<?> superClass = beanClass.getSuperclass();
        if (superClass != null && !superClass.equals(Object.class)) {
            this.removeSuperSubTypes(types, superClass);
        }
        if (!types.isEmpty()) {
            Class<?>[] superInterfaces;
            for (Class<?> superInterface : superInterfaces = beanClass.getInterfaces()) {
                this.removeSuperSubTypes(types, superInterface);
                if (types.isEmpty()) break;
            }
        }
    }

    private void removeSuperSubTypes(List<NamedType> resultTypes, Class<?> superClass) {
        JavaType superType = this._mapper.constructType(superClass);
        BeanDescription superBean = this._mapper.getSerializationConfig().introspect(superType);
        List<NamedType> superTypes = this._intr.findSubtypes(superBean.getClassInfo());
        if (superTypes != null) {
            resultTypes.removeAll(superTypes);
        }
    }

    private void removeParentProperties(Schema child, Schema parent) {
        Map<String, Schema> baseProps = parent.getProperties();
        Map<String, Schema> subtypeProps = child.getProperties();
        if (baseProps != null && subtypeProps != null) {
            for (Map.Entry<String, Schema> entry : baseProps.entrySet()) {
                if (!entry.getValue().equals(subtypeProps.get(entry.getKey()))) continue;
                subtypeProps.remove(entry.getKey());
            }
        }
        if (subtypeProps == null || subtypeProps.isEmpty()) {
            child.setProperties(null);
        }
    }

    protected List<Class<?>> getComposedSchemaReferencedClasses(Class<?> clazz, Annotation[] ctxAnnotations, io.swagger.v3.oas.annotations.media.Schema schemaAnnotation) {
        if (schemaAnnotation != null) {
            Class<?>[] allOf = schemaAnnotation.allOf();
            Class<?>[] anyOf = schemaAnnotation.anyOf();
            Class<?>[] oneOf = schemaAnnotation.oneOf();
            List<Class<?>> parentClasses = Stream.of(allOf, anyOf, oneOf).flatMap(Stream::of).distinct().filter(c -> !this.shouldIgnoreClass((Type)c)).filter(c -> !c.equals(Void.class)).collect(Collectors.toList());
            if (!parentClasses.isEmpty()) {
                return parentClasses;
            }
        }
        return null;
    }

    protected String resolveDescription(Annotated a, Annotation[] annotations, io.swagger.v3.oas.annotations.media.Schema schema) {
        if (schema != null && !"".equals(schema.description())) {
            return schema.description();
        }
        return null;
    }

    protected String resolveTitle(Annotated a, Annotation[] annotations, io.swagger.v3.oas.annotations.media.Schema schema) {
        if (schema != null && StringUtils.isNotBlank(schema.title())) {
            return schema.title();
        }
        return null;
    }

    protected String resolveFormat(Annotated a, Annotation[] annotations, io.swagger.v3.oas.annotations.media.Schema schema) {
        if (schema != null && StringUtils.isNotBlank(schema.format())) {
            return schema.format();
        }
        return null;
    }

    protected Map<String, Schema> resolvePatternProperties(JavaType a, Annotation[] annotations, ModelConverterContext context) {
        PatternProperty singleProp;
        LinkedHashMap<String, PatternProperty> propList = new LinkedHashMap<String, PatternProperty>();
        PatternProperties props = a.getRawClass().getAnnotation(PatternProperties.class);
        if (props != null && props.value().length > 0) {
            for (PatternProperty prop : props.value()) {
                propList.put(prop.regex(), prop);
            }
        }
        if ((singleProp = a.getRawClass().getAnnotation(PatternProperty.class)) != null) {
            propList.put(singleProp.regex(), singleProp);
        }
        if ((props = AnnotationsUtils.getAnnotation(PatternProperties.class, annotations)) != null && props.value().length > 0) {
            for (PatternProperty prop : props.value()) {
                propList.put(prop.regex(), prop);
            }
        }
        if ((singleProp = AnnotationsUtils.getAnnotation(PatternProperty.class, annotations)) != null) {
            propList.put(singleProp.regex(), singleProp);
        }
        if (propList.isEmpty()) {
            return null;
        }
        LinkedHashMap<String, Schema> patternProperties = new LinkedHashMap<String, Schema>();
        for (PatternProperty prop : propList.values()) {
            String key = prop.regex();
            if (StringUtils.isBlank(key)) continue;
            Annotation[] propAnnotations = new Annotation[]{prop.schema(), prop.array()};
            AnnotatedType propType = new AnnotatedType().type((Type)((Object)String.class)).ctxAnnotations(propAnnotations).resolveAsRef(true);
            Schema resolvedPropSchema = context.resolve(propType);
            if (resolvedPropSchema == null) continue;
            patternProperties.put(key, resolvedPropSchema);
        }
        return patternProperties;
    }

    protected Map<String, Schema> resolveSchemaProperties(JavaType a, Annotation[] annotations, ModelConverterContext context) {
        SchemaProperty singleProp;
        LinkedHashMap<String, SchemaProperty> propList = new LinkedHashMap<String, SchemaProperty>();
        SchemaProperties props = a.getRawClass().getAnnotation(SchemaProperties.class);
        if (props != null && props.value().length > 0) {
            for (SchemaProperty prop : props.value()) {
                propList.put(prop.name(), prop);
            }
        }
        if ((singleProp = a.getRawClass().getAnnotation(SchemaProperty.class)) != null) {
            propList.put(singleProp.name(), singleProp);
        }
        if ((props = AnnotationsUtils.getAnnotation(SchemaProperties.class, annotations)) != null && props.value().length > 0) {
            for (SchemaProperty prop : props.value()) {
                propList.put(prop.name(), prop);
            }
        }
        if ((singleProp = AnnotationsUtils.getAnnotation(SchemaProperty.class, annotations)) != null) {
            propList.put(singleProp.name(), singleProp);
        }
        if (propList.isEmpty()) {
            return null;
        }
        LinkedHashMap<String, Schema> schemaProperties = new LinkedHashMap<String, Schema>();
        for (SchemaProperty prop : propList.values()) {
            String key = prop.name();
            if (StringUtils.isBlank(key)) continue;
            Annotation[] propAnnotations = new Annotation[]{prop.schema(), prop.array()};
            AnnotatedType propType = new AnnotatedType().type((Type)((Object)String.class)).ctxAnnotations(propAnnotations).resolveAsRef(true);
            Schema resolvedPropSchema = context.resolve(propType);
            if (resolvedPropSchema == null) continue;
            schemaProperties.put(key, resolvedPropSchema);
        }
        return schemaProperties;
    }

    protected Map<String, Schema> resolveDependentSchemas(JavaType a, Annotation[] annotations, ModelConverterContext context, Components components, JsonView jsonViewAnnotation, boolean openapi31) {
        DependentSchema singleDependentSchema;
        LinkedHashMap<String, DependentSchema> dependentSchemaMap = new LinkedHashMap<String, DependentSchema>();
        DependentSchemas dependentSchemasAnnotation = a.getRawClass().getAnnotation(DependentSchemas.class);
        if (dependentSchemasAnnotation != null && dependentSchemasAnnotation.value().length > 0) {
            for (DependentSchema dependentSchemaAnnotation : dependentSchemasAnnotation.value()) {
                dependentSchemaMap.put(dependentSchemaAnnotation.name(), dependentSchemaAnnotation);
            }
        }
        if ((singleDependentSchema = a.getRawClass().getAnnotation(DependentSchema.class)) != null) {
            dependentSchemaMap.put(singleDependentSchema.name(), singleDependentSchema);
        }
        if ((dependentSchemasAnnotation = AnnotationsUtils.getAnnotation(DependentSchemas.class, annotations)) != null && dependentSchemasAnnotation.value().length > 0) {
            for (DependentSchema dependentSchemaAnnotation : dependentSchemasAnnotation.value()) {
                dependentSchemaMap.put(dependentSchemaAnnotation.name(), dependentSchemaAnnotation);
            }
        }
        if ((singleDependentSchema = AnnotationsUtils.getAnnotation(DependentSchema.class, annotations)) != null) {
            dependentSchemaMap.put(singleDependentSchema.name(), singleDependentSchema);
        }
        if (dependentSchemaMap.isEmpty()) {
            return null;
        }
        LinkedHashMap<String, Schema> dependentSchemas = new LinkedHashMap<String, Schema>();
        for (DependentSchema dependentSchemaAnnotation : dependentSchemaMap.values()) {
            String name = dependentSchemaAnnotation.name();
            if (StringUtils.isBlank(name)) continue;
            Annotation[] propAnnotations = new Annotation[]{dependentSchemaAnnotation.schema(), dependentSchemaAnnotation.array()};
            Schema existingSchema = null;
            Optional<Schema> resolvedPropSchema = AnnotationsUtils.getSchemaFromAnnotation(dependentSchemaAnnotation.schema(), components, jsonViewAnnotation, openapi31, null, context);
            if (resolvedPropSchema.isPresent()) {
                existingSchema = resolvedPropSchema.get();
                dependentSchemas.put(name, existingSchema);
            }
            if (!(resolvedPropSchema = AnnotationsUtils.getArraySchema(dependentSchemaAnnotation.array(), components, jsonViewAnnotation, openapi31, existingSchema)).isPresent()) continue;
            dependentSchemas.put(name, resolvedPropSchema.get());
        }
        return dependentSchemas;
    }

    protected Object resolveDefaultValue(Annotated a, Annotation[] annotations, io.swagger.v3.oas.annotations.media.Schema schema) {
        if (schema != null && !schema.defaultValue().isEmpty()) {
            try {
                ObjectMapper mapper2 = ObjectMapperFactory.buildStrictGenericObjectMapper();
                return mapper2.readTree(schema.defaultValue());
            }
            catch (IOException e) {
                return schema.defaultValue();
            }
        }
        if (a == null) {
            return null;
        }
        XmlElement elem = a.getAnnotation(XmlElement.class);
        if (elem == null && annotations != null) {
            for (Annotation ann : annotations) {
                if (!(ann instanceof XmlElement)) continue;
                elem = (XmlElement)ann;
                break;
            }
        }
        if (elem != null && !elem.defaultValue().isEmpty() && !"\u0000".equals(elem.defaultValue())) {
            return elem.defaultValue();
        }
        return null;
    }

    protected Object resolveExample(Annotated a, Annotation[] annotations, io.swagger.v3.oas.annotations.media.Schema schema) {
        if (schema != null && !schema.example().isEmpty()) {
            try {
                ObjectMapper mapper2 = ObjectMapperFactory.buildStrictGenericObjectMapper();
                return mapper2.readTree(schema.example());
            }
            catch (IOException e) {
                return schema.example();
            }
        }
        return null;
    }

    protected Schema.RequiredMode resolveRequiredMode(io.swagger.v3.oas.annotations.media.Schema schema) {
        if (schema != null && !schema.requiredMode().equals((Object)Schema.RequiredMode.AUTO)) {
            return schema.requiredMode();
        }
        if (schema != null && schema.required()) {
            return Schema.RequiredMode.REQUIRED;
        }
        return Schema.RequiredMode.AUTO;
    }

    protected Schema.AccessMode resolveAccessMode(BeanPropertyDefinition propDef, JavaType type, io.swagger.v3.oas.annotations.media.Schema schema) {
        if (schema != null && !schema.accessMode().equals((Object)Schema.AccessMode.AUTO)) {
            return schema.accessMode();
        }
        if (schema != null && schema.readOnly()) {
            return Schema.AccessMode.READ_ONLY;
        }
        if (schema != null && schema.writeOnly()) {
            return Schema.AccessMode.WRITE_ONLY;
        }
        if (propDef == null) {
            return null;
        }
        JsonProperty.Access access = null;
        if (propDef instanceof POJOPropertyBuilder) {
            access = ((POJOPropertyBuilder)propDef).findAccess();
        }
        boolean hasGetter = propDef.hasGetter();
        boolean hasSetter = propDef.hasSetter();
        boolean hasConstructorParameter = propDef.hasConstructorParameter();
        boolean hasField = propDef.hasField();
        if (access == null) {
            BeanDescription beanDesc = this._mapper.getDeserializationConfig().introspect(type);
            List<BeanPropertyDefinition> properties = beanDesc.findProperties();
            for (BeanPropertyDefinition prop : properties) {
                if (!StringUtils.isNotBlank(prop.getInternalName()) || !prop.getInternalName().equals(propDef.getInternalName())) continue;
                if (prop instanceof POJOPropertyBuilder) {
                    access = ((POJOPropertyBuilder)prop).findAccess();
                }
                hasGetter = hasGetter || prop.hasGetter();
                hasSetter = hasSetter || prop.hasSetter();
                hasConstructorParameter = hasConstructorParameter || prop.hasConstructorParameter();
                hasField = hasField || prop.hasField();
                break;
            }
        }
        if (access == null) {
            if (!hasGetter && !hasField && (hasConstructorParameter || hasSetter)) {
                return Schema.AccessMode.WRITE_ONLY;
            }
            return null;
        }
        switch (access) {
            case AUTO: {
                return Schema.AccessMode.AUTO;
            }
            case READ_ONLY: {
                return Schema.AccessMode.READ_ONLY;
            }
            case READ_WRITE: {
                return Schema.AccessMode.READ_WRITE;
            }
            case WRITE_ONLY: {
                return Schema.AccessMode.WRITE_ONLY;
            }
        }
        return Schema.AccessMode.AUTO;
    }

    protected Boolean resolveReadOnly(Annotated a, Annotation[] annotations, io.swagger.v3.oas.annotations.media.Schema schema) {
        if (schema != null && schema.accessMode().equals((Object)Schema.AccessMode.READ_ONLY)) {
            return true;
        }
        if (schema != null && schema.accessMode().equals((Object)Schema.AccessMode.WRITE_ONLY)) {
            return null;
        }
        if (schema != null && schema.accessMode().equals((Object)Schema.AccessMode.READ_WRITE)) {
            return null;
        }
        if (schema != null && schema.readOnly()) {
            return schema.readOnly();
        }
        return null;
    }

    protected Boolean resolveNullable(Annotated a, Annotation[] annotations, io.swagger.v3.oas.annotations.media.Schema schema) {
        if (schema != null && schema.nullable()) {
            return schema.nullable();
        }
        return null;
    }

    protected BigDecimal resolveMultipleOf(Annotated a, Annotation[] annotations, io.swagger.v3.oas.annotations.media.Schema schema) {
        if (schema != null && schema.multipleOf() != 0.0) {
            return new BigDecimal(schema.multipleOf());
        }
        return null;
    }

    protected Integer resolveMaxLength(Annotated a, Annotation[] annotations, io.swagger.v3.oas.annotations.media.Schema schema) {
        if (schema != null && schema.maxLength() != Integer.MAX_VALUE && schema.maxLength() > 0) {
            return schema.maxLength();
        }
        return null;
    }

    protected Integer resolveMinLength(Annotated a, Annotation[] annotations, io.swagger.v3.oas.annotations.media.Schema schema) {
        if (schema != null && schema.minLength() > 0) {
            return schema.minLength();
        }
        return null;
    }

    protected BigDecimal resolveMinimum(Annotated a, Annotation[] annotations, io.swagger.v3.oas.annotations.media.Schema schema) {
        if (schema != null && NumberUtils.isCreatable(schema.minimum())) {
            String filteredMinimum = schema.minimum().replace(",", "");
            return new BigDecimal(filteredMinimum);
        }
        return null;
    }

    protected BigDecimal resolveMaximum(Annotated a, Annotation[] annotations, io.swagger.v3.oas.annotations.media.Schema schema) {
        if (schema != null && NumberUtils.isCreatable(schema.maximum())) {
            String filteredMaximum = schema.maximum().replace(",", "");
            return new BigDecimal(filteredMaximum);
        }
        return null;
    }

    protected Boolean resolveExclusiveMinimum(Annotated a, Annotation[] annotations, io.swagger.v3.oas.annotations.media.Schema schema) {
        if (schema != null && schema.exclusiveMinimum()) {
            return schema.exclusiveMinimum();
        }
        return null;
    }

    protected Boolean resolveExclusiveMaximum(Annotated a, Annotation[] annotations, io.swagger.v3.oas.annotations.media.Schema schema) {
        if (schema != null && schema.exclusiveMaximum()) {
            return schema.exclusiveMaximum();
        }
        return null;
    }

    protected String resolvePattern(Annotated a, Annotation[] annotations, io.swagger.v3.oas.annotations.media.Schema schema) {
        if (schema != null && StringUtils.isNotBlank(schema.pattern())) {
            return schema.pattern();
        }
        return null;
    }

    protected Integer resolveMinProperties(Annotated a, Annotation[] annotations, io.swagger.v3.oas.annotations.media.Schema schema) {
        if (schema != null && schema.minProperties() > 0) {
            return schema.minProperties();
        }
        return null;
    }

    protected Integer resolveMaxProperties(Annotated a, Annotation[] annotations, io.swagger.v3.oas.annotations.media.Schema schema) {
        if (schema != null && schema.maxProperties() > 0) {
            return schema.maxProperties();
        }
        return null;
    }

    protected List<String> resolveRequiredProperties(Annotated a, Annotation[] annotations, io.swagger.v3.oas.annotations.media.Schema schema) {
        if (schema != null && schema.requiredProperties() != null && schema.requiredProperties().length > 0 && StringUtils.isNotBlank(schema.requiredProperties()[0])) {
            return Arrays.asList(schema.requiredProperties());
        }
        return null;
    }

    protected Boolean resolveWriteOnly(Annotated a, Annotation[] annotations, io.swagger.v3.oas.annotations.media.Schema schema) {
        if (schema != null && schema.accessMode().equals((Object)Schema.AccessMode.READ_ONLY)) {
            return null;
        }
        if (schema != null && schema.accessMode().equals((Object)Schema.AccessMode.WRITE_ONLY)) {
            return true;
        }
        if (schema != null && schema.accessMode().equals((Object)Schema.AccessMode.READ_WRITE)) {
            return null;
        }
        if (schema != null && schema.writeOnly()) {
            return schema.writeOnly();
        }
        return null;
    }

    protected ExternalDocumentation resolveExternalDocumentation(Annotated a, Annotation[] annotations, io.swagger.v3.oas.annotations.media.Schema schema) {
        ExternalDocumentation external = null;
        if (a != null) {
            io.swagger.v3.oas.annotations.ExternalDocumentation externalDocumentation = a.getAnnotation(io.swagger.v3.oas.annotations.ExternalDocumentation.class);
            external = this.resolveExternalDocumentation(externalDocumentation);
        }
        if (external == null && schema != null) {
            external = this.resolveExternalDocumentation(schema.externalDocs());
        }
        return external;
    }

    protected ExternalDocumentation resolveExternalDocumentation(io.swagger.v3.oas.annotations.ExternalDocumentation externalDocumentation) {
        if (externalDocumentation == null) {
            return null;
        }
        boolean isEmpty = true;
        ExternalDocumentation external = new ExternalDocumentation();
        if (StringUtils.isNotBlank(externalDocumentation.description())) {
            isEmpty = false;
            external.setDescription(externalDocumentation.description());
        }
        if (StringUtils.isNotBlank(externalDocumentation.url())) {
            isEmpty = false;
            external.setUrl(externalDocumentation.url());
        }
        if (isEmpty) {
            return null;
        }
        return external;
    }

    protected Boolean resolveDeprecated(Annotated a, Annotation[] annotations, io.swagger.v3.oas.annotations.media.Schema schema) {
        if (schema != null && schema.deprecated()) {
            return schema.deprecated();
        }
        return null;
    }

    protected List<String> resolveAllowableValues(Annotated a, Annotation[] annotations, io.swagger.v3.oas.annotations.media.Schema schema) {
        if (schema != null && schema.allowableValues() != null && schema.allowableValues().length > 0) {
            return Arrays.asList(schema.allowableValues());
        }
        return null;
    }

    protected Map<String, Object> resolveExtensions(Annotated a, Annotation[] annotations, io.swagger.v3.oas.annotations.media.Schema schema) {
        if (schema != null && schema.extensions() != null && schema.extensions().length > 0) {
            return AnnotationsUtils.getExtensions(this.openapi31, schema.extensions());
        }
        return null;
    }

    protected void resolveDiscriminatorProperty(JavaType type, ModelConverterContext context, Schema model) {
        String typeInfoProp;
        JsonTypeInfo typeInfo = type.getRawClass().getDeclaredAnnotation(JsonTypeInfo.class);
        if (typeInfo != null && StringUtils.isNotBlank(typeInfoProp = typeInfo.property())) {
            Schema modelToUpdate = model;
            if (StringUtils.isNotBlank(model.get$ref())) {
                modelToUpdate = context.getDefinedModels().get(model.get$ref().substring(21));
            }
            if (modelToUpdate.getProperties() == null || !modelToUpdate.getProperties().keySet().contains(typeInfoProp)) {
                Schema discriminatorSchema = new StringSchema().name(typeInfoProp);
                modelToUpdate.addProperties(typeInfoProp, discriminatorSchema);
                if (modelToUpdate.getRequired() == null || !modelToUpdate.getRequired().contains(typeInfoProp)) {
                    modelToUpdate.addRequiredItem(typeInfoProp);
                }
            }
        }
    }

    protected Schema resolveWrapping(JavaType type, ModelConverterContext context, Schema model) {
        JsonTypeInfo typeInfo = type.getRawClass().getDeclaredAnnotation(JsonTypeInfo.class);
        if (typeInfo != null) {
            JsonTypeInfo.Id id = typeInfo.use();
            JsonTypeInfo.As as = typeInfo.include();
            if (JsonTypeInfo.As.WRAPPER_OBJECT.equals((Object)as)) {
                String name = model.getName();
                if (JsonTypeInfo.Id.CLASS.equals((Object)id)) {
                    name = type.getRawClass().getName();
                }
                JsonTypeName typeName = type.getRawClass().getDeclaredAnnotation(JsonTypeName.class);
                if (JsonTypeInfo.Id.NAME.equals((Object)id) && typeName != null) {
                    name = typeName.value();
                }
                if (JsonTypeInfo.Id.NAME.equals((Object)id) && name == null) {
                    name = type.getRawClass().getSimpleName();
                }
                ObjectSchema wrapperSchema = new ObjectSchema();
                wrapperSchema.name(model.getName());
                wrapperSchema.addProperties(name, model);
                return wrapperSchema;
            }
        }
        return model;
    }

    protected Discriminator resolveDiscriminator(JavaType type, ModelConverterContext context) {
        JsonTypeInfo typeInfo;
        String disc;
        io.swagger.v3.oas.annotations.media.Schema declaredSchemaAnnotation = AnnotationsUtils.getSchemaDeclaredAnnotation(type.getRawClass());
        String string = disc = declaredSchemaAnnotation == null ? "" : declaredSchemaAnnotation.discriminatorProperty();
        if (disc.isEmpty() && (typeInfo = type.getRawClass().getDeclaredAnnotation(JsonTypeInfo.class)) != null) {
            disc = typeInfo.property();
        }
        if (!disc.isEmpty()) {
            DiscriminatorMapping[] mappings;
            Discriminator discriminator = new Discriminator().propertyName(disc);
            if (declaredSchemaAnnotation != null && (mappings = declaredSchemaAnnotation.discriminatorMapping()) != null && mappings.length > 0) {
                for (DiscriminatorMapping mapping : mappings) {
                    if (mapping.value().isEmpty() || mapping.schema().equals(Void.class)) continue;
                    discriminator.mapping(mapping.value(), RefUtils.constructRef(context.resolve(new AnnotatedType().type(mapping.schema())).getName()));
                }
            }
            return discriminator;
        }
        return null;
    }

    protected XML resolveXml(Annotated a, Annotation[] annotations, io.swagger.v3.oas.annotations.media.Schema schema) {
        XmlRootElement rootAnnotation = null;
        XmlSchema xmlSchema = null;
        if (a != null) {
            Package aPackage;
            rootAnnotation = a.getAnnotation(XmlRootElement.class);
            Class<?> rawType = a.getRawType();
            if (rawType != null && (aPackage = rawType.getPackage()) != null) {
                xmlSchema = aPackage.getAnnotation(XmlSchema.class);
            }
        }
        if (rootAnnotation == null && annotations != null) {
            for (Annotation ann : annotations) {
                if (!(ann instanceof XmlRootElement)) continue;
                rootAnnotation = (XmlRootElement)ann;
                break;
            }
        }
        if (rootAnnotation != null && !"".equals(rootAnnotation.name()) && !"##default".equals(rootAnnotation.name())) {
            XML xml = new XML().name(rootAnnotation.name());
            if (xmlSchema != null && this.isNonTrivialXmlNamespace(xmlSchema.namespace())) {
                xml.namespace(xmlSchema.namespace());
            }
            if (this.isNonTrivialXmlNamespace(rootAnnotation.namespace())) {
                xml.namespace(rootAnnotation.namespace());
            }
            return xml;
        }
        return null;
    }

    private boolean isNonTrivialXmlNamespace(String namespace) {
        return namespace != null && !"".equals(namespace) && !"##default".equals(namespace);
    }

    protected Set<String> resolveIgnoredProperties(Annotations a, Annotation[] annotations) {
        HashSet<String> propertiesToIgnore = new HashSet<String>();
        JsonIgnoreProperties ignoreProperties = a.get(JsonIgnoreProperties.class);
        if (ignoreProperties != null && !ignoreProperties.allowGetters()) {
            propertiesToIgnore.addAll(Arrays.asList(ignoreProperties.value()));
        }
        propertiesToIgnore.addAll(this.resolveIgnoredProperties(annotations));
        return propertiesToIgnore;
    }

    protected Set<String> resolveIgnoredProperties(Annotation[] annotations) {
        HashSet<String> propertiesToIgnore = new HashSet<String>();
        if (annotations != null) {
            for (Annotation annotation : annotations) {
                if (!(annotation instanceof JsonIgnoreProperties) || ((JsonIgnoreProperties)annotation).allowGetters()) continue;
                propertiesToIgnore.addAll(Arrays.asList(((JsonIgnoreProperties)annotation).value()));
                break;
            }
        }
        return propertiesToIgnore;
    }

    protected Integer resolveMinItems(AnnotatedType a, io.swagger.v3.oas.annotations.media.ArraySchema arraySchema) {
        if (arraySchema != null && arraySchema.minItems() < Integer.MAX_VALUE) {
            return arraySchema.minItems();
        }
        return null;
    }

    protected Integer resolveMaxItems(AnnotatedType a, io.swagger.v3.oas.annotations.media.ArraySchema arraySchema) {
        if (arraySchema != null && arraySchema.maxItems() > 0) {
            return arraySchema.maxItems();
        }
        return null;
    }

    protected Boolean resolveUniqueItems(AnnotatedType a, io.swagger.v3.oas.annotations.media.ArraySchema arraySchema) {
        if (arraySchema != null && arraySchema.uniqueItems()) {
            return arraySchema.uniqueItems();
        }
        return null;
    }

    protected Map<String, Object> resolveExtensions(AnnotatedType a, io.swagger.v3.oas.annotations.media.ArraySchema arraySchema) {
        if (arraySchema != null && arraySchema.extensions() != null && arraySchema.extensions().length > 0) {
            return AnnotationsUtils.getExtensions(this.openapi31, arraySchema.extensions());
        }
        return null;
    }

    protected Integer resolveMaxContains(AnnotatedType a, io.swagger.v3.oas.annotations.media.ArraySchema arraySchema) {
        if (arraySchema != null && arraySchema.maxContains() > 0) {
            return arraySchema.maxContains();
        }
        return null;
    }

    protected Integer resolveMinContains(AnnotatedType a, io.swagger.v3.oas.annotations.media.ArraySchema arraySchema) {
        if (arraySchema != null && arraySchema.minContains() > 0) {
            return arraySchema.minContains();
        }
        return null;
    }

    protected BigDecimal resolveExclusiveMaximumValue(Annotated a, Annotation[] annotations, io.swagger.v3.oas.annotations.media.Schema schema) {
        if (schema != null && schema.exclusiveMaximumValue() > 0) {
            return new BigDecimal(schema.exclusiveMaximumValue());
        }
        return null;
    }

    protected BigDecimal resolveExclusiveMinimumValue(Annotated a, Annotation[] annotations, io.swagger.v3.oas.annotations.media.Schema schema) {
        if (schema != null && schema.exclusiveMinimumValue() > 0) {
            return new BigDecimal(schema.exclusiveMinimumValue());
        }
        return null;
    }

    protected String resolveId(Annotated a, Annotation[] annotations, io.swagger.v3.oas.annotations.media.Schema schema) {
        if (schema != null && StringUtils.isNotBlank(schema.$id())) {
            return schema.$id();
        }
        return null;
    }

    protected String resolve$schema(Annotated a, Annotation[] annotations, io.swagger.v3.oas.annotations.media.Schema schema) {
        if (schema != null && StringUtils.isNotBlank(schema.$schema())) {
            return schema.$schema();
        }
        return null;
    }

    protected String resolve$anchor(Annotated a, Annotation[] annotations, io.swagger.v3.oas.annotations.media.Schema schema) {
        if (schema != null && StringUtils.isNotBlank(schema.$anchor())) {
            return schema.$anchor();
        }
        return null;
    }

    protected String resolve$comment(Annotated a, Annotation[] annotations, io.swagger.v3.oas.annotations.media.Schema schema) {
        if (schema != null && StringUtils.isNotBlank(schema.$comment())) {
            return schema.$comment();
        }
        return null;
    }

    protected String resolve$vocabulary(Annotated a, Annotation[] annotations, io.swagger.v3.oas.annotations.media.Schema schema) {
        if (schema != null && StringUtils.isNotBlank(schema.$vocabulary())) {
            return schema.$vocabulary();
        }
        return null;
    }

    protected String resolve$dynamicAnchor(Annotated a, Annotation[] annotations, io.swagger.v3.oas.annotations.media.Schema schema) {
        if (schema != null && StringUtils.isNotBlank(schema.$dynamicAnchor())) {
            return schema.$dynamicAnchor();
        }
        return null;
    }

    protected String resolveContentEncoding(Annotated a, Annotation[] annotations, io.swagger.v3.oas.annotations.media.Schema schema) {
        if (schema != null && StringUtils.isNotBlank(schema.contentEncoding())) {
            return schema.contentEncoding();
        }
        return null;
    }

    protected String resolveContentMediaType(Annotated a, Annotation[] annotations, io.swagger.v3.oas.annotations.media.Schema schema) {
        if (schema != null && StringUtils.isNotBlank(schema.contentMediaType())) {
            return schema.contentMediaType();
        }
        return null;
    }

    protected void resolveContains(AnnotatedType annotatedType, ArraySchema arraySchema, io.swagger.v3.oas.annotations.media.ArraySchema arraySchemaAnnotation) {
        Integer minContains;
        io.swagger.v3.oas.annotations.media.Schema containsAnnotation = arraySchemaAnnotation.contains();
        Schema contains = new Schema();
        if (containsAnnotation.types().length > 0) {
            for (String type : containsAnnotation.types()) {
                contains.addType(type);
            }
        }
        arraySchema.setContains(contains);
        this.resolveSchemaMembers(contains, null, null, containsAnnotation);
        Integer maxContains = this.resolveMaxContains(annotatedType, arraySchemaAnnotation);
        if (maxContains != null) {
            arraySchema.setMaxContains(maxContains);
        }
        if ((minContains = this.resolveMinContains(annotatedType, arraySchemaAnnotation)) != null) {
            arraySchema.setMinContains(minContains);
        }
    }

    protected void resolveUnevaluatedItems(AnnotatedType annotatedType, ArraySchema arraySchema, io.swagger.v3.oas.annotations.media.ArraySchema arraySchemaAnnotation) {
        io.swagger.v3.oas.annotations.media.Schema unevaluatedItemsAnnotation = arraySchemaAnnotation.unevaluatedItems();
        Schema unevaluatedItems = new Schema();
        if (StringUtils.isNotBlank(unevaluatedItemsAnnotation.type())) {
            unevaluatedItems.addType(unevaluatedItemsAnnotation.type());
        }
        if (unevaluatedItemsAnnotation.types().length > 0) {
            for (String type : unevaluatedItemsAnnotation.types()) {
                unevaluatedItems.addType(type);
            }
        }
        arraySchema.setUnevaluatedItems(unevaluatedItems);
        this.resolveSchemaMembers(unevaluatedItems, null, null, unevaluatedItemsAnnotation);
    }

    protected String resolveConst(Annotated a, Annotation[] annotations, io.swagger.v3.oas.annotations.media.Schema schema) {
        if (schema != null && StringUtils.isNotBlank(schema._const())) {
            return schema._const();
        }
        return null;
    }

    protected Map<String, List<String>> resolveDependentRequired(Annotated a, Annotation[] annotations, io.swagger.v3.oas.annotations.media.Schema schema) {
        if (schema.dependentRequiredMap().length == 0) {
            return null;
        }
        HashMap<String, List<String>> dependentRequiredMap = new HashMap<String, List<String>>();
        for (DependentRequired dependentRequired : schema.dependentRequiredMap()) {
            String name = dependentRequired.name();
            if (dependentRequired.value().length == 0) continue;
            List<String> values2 = Arrays.asList(dependentRequired.value());
            dependentRequiredMap.put(name, values2);
        }
        return dependentRequiredMap;
    }

    protected Map<String, Schema> resolveDependentSchemas(Annotated a, Annotation[] annotations, io.swagger.v3.oas.annotations.media.Schema schemaAnnotation, AnnotatedType annotatedType, ModelConverterContext context, Iterator<ModelConverter> next) {
        if (schemaAnnotation.dependentSchemas().length == 0) {
            return null;
        }
        HashMap<String, Schema> dependentSchemas = new HashMap<String, Schema>();
        for (StringToClassMapItem mapItem : schemaAnnotation.dependentSchemas()) {
            Schema schema;
            String key = mapItem.key();
            if (mapItem.value() == null || Void.class.equals(mapItem.value()) || (schema = this.resolve(new AnnotatedType(mapItem.value()), context, next)) == null) continue;
            dependentSchemas.put(key, schema);
        }
        return dependentSchemas;
    }

    protected Map<String, Schema> resolvePatternProperties(Annotated a, Annotation[] annotations, io.swagger.v3.oas.annotations.media.Schema schemaAnnotation, AnnotatedType annotatedType, ModelConverterContext context, Iterator<ModelConverter> next) {
        if (schemaAnnotation.patternProperties().length == 0) {
            return null;
        }
        HashMap<String, Schema> patternPropertyMap = new HashMap<String, Schema>();
        for (StringToClassMapItem patternPropertyItem : schemaAnnotation.patternProperties()) {
            Schema schema;
            String key = patternPropertyItem.key();
            if (Void.class.equals(patternPropertyItem.value()) || (schema = this.resolve(new AnnotatedType(patternPropertyItem.value()), context, next)) == null) continue;
            patternPropertyMap.put(key, schema);
        }
        return patternPropertyMap;
    }

    protected Map<String, Schema> resolveProperties(Annotated a, Annotation[] annotations, io.swagger.v3.oas.annotations.media.Schema schemaAnnotation, AnnotatedType annotatedType, ModelConverterContext context, Iterator<ModelConverter> next) {
        if (schemaAnnotation.properties().length == 0) {
            return null;
        }
        HashMap<String, Schema> propertyMap = new HashMap<String, Schema>();
        for (StringToClassMapItem propertyItem : schemaAnnotation.properties()) {
            Schema schema;
            String key = propertyItem.key();
            if (Void.class.equals(propertyItem.value()) || (schema = this.resolve(new AnnotatedType(propertyItem.value()), context, next)) == null) continue;
            propertyMap.put(key, schema);
        }
        return propertyMap;
    }

    protected void resolveSchemaMembers(Schema schema, AnnotatedType annotatedType) {
        this.resolveSchemaMembers(schema, annotatedType, null, null);
    }

    protected void resolveSchemaMembers(Schema schema, AnnotatedType annotatedType, ModelConverterContext context, Iterator<ModelConverter> next) {
        JavaType type = annotatedType.getType() instanceof JavaType ? (JavaType)annotatedType.getType() : this._mapper.constructType(annotatedType.getType());
        Annotation resolvedSchemaOrArrayAnnotation = AnnotationsUtils.mergeSchemaAnnotations(annotatedType.getCtxAnnotations(), type);
        io.swagger.v3.oas.annotations.media.Schema schemaAnnotation = resolvedSchemaOrArrayAnnotation == null ? null : (resolvedSchemaOrArrayAnnotation instanceof io.swagger.v3.oas.annotations.media.ArraySchema ? ((io.swagger.v3.oas.annotations.media.ArraySchema)resolvedSchemaOrArrayAnnotation).schema() : (io.swagger.v3.oas.annotations.media.Schema)resolvedSchemaOrArrayAnnotation);
        BeanDescription beanDesc = this._mapper.getSerializationConfig().introspect(type);
        AnnotatedClass a = beanDesc.getClassInfo();
        Annotation[] annotations = annotatedType.getCtxAnnotations();
        this.resolveSchemaMembers(schema, a, annotations, schemaAnnotation);
        if (this.openapi31 && schema != null && schemaAnnotation != null) {
            Map<String, Schema> properties;
            Map<String, Schema> patternProperties;
            Map<String, Schema> dependentSchemas;
            if (!Void.class.equals(schemaAnnotation.contentSchema())) {
                Schema contentSchema = this.resolve(new AnnotatedType(schemaAnnotation.contentSchema()), context, next);
                contentSchema = this.buildRefSchemaIfObject(contentSchema, context);
                schema.setContentSchema(contentSchema);
            }
            if (!Void.class.equals(schemaAnnotation.propertyNames())) {
                Schema propertyNames = this.resolve(new AnnotatedType(schemaAnnotation.propertyNames()), context, next);
                propertyNames = this.buildRefSchemaIfObject(propertyNames, context);
                schema.setPropertyNames(propertyNames);
            }
            if (!Void.class.equals(schemaAnnotation._if())) {
                Schema ifSchema = this.resolve(new AnnotatedType(schemaAnnotation._if()), context, next);
                ifSchema = this.buildRefSchemaIfObject(ifSchema, context);
                schema.setIf(ifSchema);
            }
            if (!Void.class.equals(schemaAnnotation._else())) {
                Schema elseSchema = this.resolve(new AnnotatedType(schemaAnnotation._else()), context, next);
                elseSchema = this.buildRefSchemaIfObject(elseSchema, context);
                schema.setElse(elseSchema);
            }
            if (!Void.class.equals(schemaAnnotation.then())) {
                Schema thenSchema = this.resolve(new AnnotatedType(schemaAnnotation.then()), context, next);
                thenSchema = this.buildRefSchemaIfObject(thenSchema, context);
                schema.setThen(thenSchema);
            }
            if (!Void.class.equals(schemaAnnotation.unevaluatedProperties())) {
                Schema unevaluatedProperties = this.resolve(new AnnotatedType(schemaAnnotation.unevaluatedProperties()), context, next);
                unevaluatedProperties = this.buildRefSchemaIfObject(unevaluatedProperties, context);
                schema.setUnevaluatedProperties(unevaluatedProperties);
            }
            if (schemaAnnotation.additionalProperties().equals((Object)Schema.AdditionalPropertiesValue.TRUE)) {
                schema.additionalProperties(true);
            } else if (schemaAnnotation.additionalProperties().equals((Object)Schema.AdditionalPropertiesValue.FALSE)) {
                schema.additionalProperties(false);
            } else if (!schemaAnnotation.additionalPropertiesSchema().equals(Void.class)) {
                Schema additionalPropertiesSchema = this.resolve(new AnnotatedType(schemaAnnotation.additionalPropertiesSchema()), context, next);
                additionalPropertiesSchema = this.buildRefSchemaIfObject(additionalPropertiesSchema, context);
                schema.additionalProperties(additionalPropertiesSchema);
            }
            Map<String, List<String>> dependentRequired = this.resolveDependentRequired(a, annotations, schemaAnnotation);
            if (dependentRequired != null && !dependentRequired.isEmpty()) {
                schema.setDependentRequired(dependentRequired);
            }
            if ((dependentSchemas = this.resolveDependentSchemas(a, annotations, schemaAnnotation, annotatedType, context, next)) != null) {
                LinkedHashMap<String, Schema> processedDependentSchemas = new LinkedHashMap<String, Schema>();
                for (String key : dependentSchemas.keySet()) {
                    Schema val = dependentSchemas.get(key);
                    processedDependentSchemas.put(key, this.buildRefSchemaIfObject(val, context));
                }
                if (processedDependentSchemas != null && !processedDependentSchemas.isEmpty()) {
                    if (schema.getDependentSchemas() == null) {
                        schema.setDependentSchemas(processedDependentSchemas);
                    } else {
                        schema.getDependentSchemas().putAll(processedDependentSchemas);
                    }
                }
            }
            if ((patternProperties = this.resolvePatternProperties(a, annotations, schemaAnnotation, annotatedType, context, next)) != null && !patternProperties.isEmpty()) {
                for (String key : patternProperties.keySet()) {
                    schema.addPatternProperty(key, this.buildRefSchemaIfObject(patternProperties.get(key), context));
                }
            }
            if ((properties = this.resolveProperties(a, annotations, schemaAnnotation, annotatedType, context, next)) != null && !properties.isEmpty()) {
                for (String key : properties.keySet()) {
                    schema.addProperty(key, this.buildRefSchemaIfObject(properties.get(key), context));
                }
            }
        }
    }

    protected void resolveSchemaMembers(Schema schema, Annotated a, Annotation[] annotations, io.swagger.v3.oas.annotations.media.Schema schemaAnnotation) {
        Map<String, Object> extensions;
        List<String> allowableValues;
        Boolean deprecated2;
        ExternalDocumentation externalDocs;
        Boolean writeOnly;
        List<String> requiredProperties;
        Integer maxProperties;
        Integer minProperties;
        String pattern;
        Boolean exclusiveMaximum;
        Boolean exclusiveMinimum;
        BigDecimal maximum;
        BigDecimal minimum;
        Integer minLength;
        Integer maxLength;
        BigDecimal multipleOf;
        Boolean nullable;
        Boolean readOnly;
        Object example;
        Object defaultValue;
        String format;
        String title;
        String description = this.resolveDescription(a, annotations, schemaAnnotation);
        if (StringUtils.isNotBlank(description)) {
            schema.description(description);
        }
        if (StringUtils.isNotBlank(title = this.resolveTitle(a, annotations, schemaAnnotation))) {
            schema.title(title);
        }
        if (StringUtils.isNotBlank(format = this.resolveFormat(a, annotations, schemaAnnotation)) && StringUtils.isBlank(schema.getFormat())) {
            schema.format(format);
        }
        if ((defaultValue = this.resolveDefaultValue(a, annotations, schemaAnnotation)) != null) {
            schema.setDefault(defaultValue);
        }
        if ((example = this.resolveExample(a, annotations, schemaAnnotation)) != null) {
            schema.example(example);
        }
        if ((readOnly = this.resolveReadOnly(a, annotations, schemaAnnotation)) != null) {
            schema.readOnly(readOnly);
        }
        if ((nullable = this.resolveNullable(a, annotations, schemaAnnotation)) != null) {
            schema.nullable(nullable);
        }
        if ((multipleOf = this.resolveMultipleOf(a, annotations, schemaAnnotation)) != null) {
            schema.multipleOf(multipleOf);
        }
        if ((maxLength = this.resolveMaxLength(a, annotations, schemaAnnotation)) != null) {
            schema.maxLength(maxLength);
        }
        if ((minLength = this.resolveMinLength(a, annotations, schemaAnnotation)) != null) {
            schema.minLength(minLength);
        }
        if ((minimum = this.resolveMinimum(a, annotations, schemaAnnotation)) != null) {
            schema.minimum(minimum);
        }
        if ((maximum = this.resolveMaximum(a, annotations, schemaAnnotation)) != null) {
            schema.maximum(maximum);
        }
        if ((exclusiveMinimum = this.resolveExclusiveMinimum(a, annotations, schemaAnnotation)) != null) {
            schema.exclusiveMinimum(exclusiveMinimum);
        }
        if ((exclusiveMaximum = this.resolveExclusiveMaximum(a, annotations, schemaAnnotation)) != null) {
            schema.exclusiveMaximum(exclusiveMaximum);
        }
        if (StringUtils.isNotBlank(pattern = this.resolvePattern(a, annotations, schemaAnnotation))) {
            schema.pattern(pattern);
        }
        if ((minProperties = this.resolveMinProperties(a, annotations, schemaAnnotation)) != null) {
            schema.minProperties(minProperties);
        }
        if ((maxProperties = this.resolveMaxProperties(a, annotations, schemaAnnotation)) != null) {
            schema.maxProperties(maxProperties);
        }
        if ((requiredProperties = this.resolveRequiredProperties(a, annotations, schemaAnnotation)) != null) {
            for (String prop : requiredProperties) {
                this.addRequiredItem(schema, prop);
            }
        }
        if ((writeOnly = this.resolveWriteOnly(a, annotations, schemaAnnotation)) != null) {
            schema.writeOnly(writeOnly);
        }
        if ((externalDocs = this.resolveExternalDocumentation(a, annotations, schemaAnnotation)) != null) {
            schema.externalDocs(externalDocs);
        }
        if ((deprecated2 = this.resolveDeprecated(a, annotations, schemaAnnotation)) != null) {
            schema.deprecated(deprecated2);
        }
        if ((allowableValues = this.resolveAllowableValues(a, annotations, schemaAnnotation)) != null) {
            for (String string : allowableValues) {
                schema.addEnumItemObject(string);
            }
        }
        if ((extensions = this.resolveExtensions(a, annotations, schemaAnnotation)) != null) {
            extensions.forEach(schema::addExtension);
        }
        if (this.openapi31 && schemaAnnotation != null) {
            String _const;
            String contentMediaType;
            String contentEncoding;
            String $dynamicAnchor;
            String $vocabulary;
            String $comment;
            String $anchor;
            String $schema;
            String $id;
            BigDecimal exclusiveMinimumValue;
            for (String type : schemaAnnotation.types()) {
                schema.addType(type);
            }
            BigDecimal bigDecimal = this.resolveExclusiveMaximumValue(a, annotations, schemaAnnotation);
            if (bigDecimal != null) {
                schema.setExclusiveMaximumValue(bigDecimal);
            }
            if ((exclusiveMinimumValue = this.resolveExclusiveMinimumValue(a, annotations, schemaAnnotation)) != null) {
                schema.setExclusiveMinimumValue(exclusiveMinimumValue);
            }
            if (($id = this.resolveId(a, annotations, schemaAnnotation)) != null) {
                schema.set$id($id);
            }
            if (($schema = this.resolve$schema(a, annotations, schemaAnnotation)) != null) {
                schema.set$schema($schema);
            }
            if (($anchor = this.resolve$anchor(a, annotations, schemaAnnotation)) != null) {
                schema.set$anchor($anchor);
            }
            if (($comment = this.resolve$comment(a, annotations, schemaAnnotation)) != null) {
                schema.set$comment($comment);
            }
            if (($vocabulary = this.resolve$vocabulary(a, annotations, schemaAnnotation)) != null) {
                schema.set$vocabulary($vocabulary);
            }
            if (($dynamicAnchor = this.resolve$dynamicAnchor(a, annotations, schemaAnnotation)) != null) {
                schema.$dynamicAnchor($dynamicAnchor);
            }
            if ((contentEncoding = this.resolveContentEncoding(a, annotations, schemaAnnotation)) != null) {
                schema.setContentEncoding(contentEncoding);
            }
            if ((contentMediaType = this.resolveContentMediaType(a, annotations, schemaAnnotation)) != null) {
                schema.setContentMediaType(contentMediaType);
            }
            if (schemaAnnotation.examples().length > 0) {
                if (schema.getExamples() == null || schema.getExamples().isEmpty()) {
                    schema.setExamples(Arrays.asList(schemaAnnotation.examples()));
                } else {
                    schema.getExamples().addAll(Arrays.asList(schemaAnnotation.examples()));
                }
            }
            if ((_const = this.resolveConst(a, annotations, schemaAnnotation)) != null) {
                schema.setConst(_const);
            }
        }
    }

    protected void addRequiredItem(Schema model, String propName) {
        if (model == null || propName == null || StringUtils.isBlank(propName)) {
            return;
        }
        if (model.getRequired() == null || model.getRequired().isEmpty()) {
            model.addRequiredItem(propName);
        }
        if (model.getRequired().stream().noneMatch(propName::equals)) {
            model.addRequiredItem(propName);
        }
    }

    protected boolean shouldIgnoreClass(Type type) {
        if (type instanceof Class) {
            Class cls = (Class)type;
            if (cls.getName().equals("javax.ws.rs.Response")) {
                return true;
            }
        } else if (type instanceof ResolvedType) {
            ResolvedType rt = (ResolvedType)((Object)type);
            this.LOGGER.trace("Can't check class {}, {}", (Object)type, (Object)rt.getRawClass().getName());
            if (rt.getRawClass().equals(Class.class)) {
                return true;
            }
        }
        return false;
    }

    private List<String> getIgnoredProperties(BeanDescription beanDescription) {
        AnnotationIntrospector introspector = this._mapper.getSerializationConfig().getAnnotationIntrospector();
        JsonIgnoreProperties.Value v = introspector.findPropertyIgnorals(beanDescription.getClassInfo());
        Set<String> ignored = null;
        if (v != null) {
            ignored = v.findIgnoredForSerialization();
        }
        return ignored == null ? Collections.emptyList() : new ArrayList<String>(ignored);
    }

    protected String decorateModelName(AnnotatedType type, String originalName) {
        if (StringUtils.isBlank(originalName)) {
            return originalName;
        }
        String name = originalName;
        if (type.getJsonViewAnnotation() != null && type.getJsonViewAnnotation().value().length > 0) {
            String COMBINER = "-or-";
            StringBuilder sb = new StringBuilder();
            for (Class<?> view : type.getJsonViewAnnotation().value()) {
                sb.append(view.getSimpleName()).append(COMBINER);
            }
            String suffix = sb.substring(0, sb.length() - COMBINER.length());
            name = originalName + "_" + suffix;
        }
        return name;
    }

    protected boolean hiddenByJsonView(Annotation[] annotations, AnnotatedType type) {
        JsonView jsonView = type.getJsonViewAnnotation();
        if (jsonView == null) {
            return false;
        }
        Class<?>[] filters = jsonView.value();
        boolean containsJsonViewAnnotation = !type.isIncludePropertiesWithoutJSONView();
        for (Annotation ant : annotations) {
            if (!(ant instanceof JsonView)) continue;
            containsJsonViewAnnotation = true;
            Class<?>[] views = ((JsonView)ant).value();
            for (Class<?> f : filters) {
                for (Class<?> v : views) {
                    if (v != f && !v.isAssignableFrom(f)) continue;
                    return false;
                }
            }
        }
        return containsJsonViewAnnotation;
    }

    private void resolveArraySchema(AnnotatedType annotatedType, ArraySchema schema, io.swagger.v3.oas.annotations.media.ArraySchema resolvedArrayAnnotation) {
        Map<String, Object> extensions;
        Boolean uniqueItems;
        Integer maxItems;
        Integer minItems = this.resolveMinItems(annotatedType, resolvedArrayAnnotation);
        if (minItems != null) {
            schema.minItems(minItems);
        }
        if ((maxItems = this.resolveMaxItems(annotatedType, resolvedArrayAnnotation)) != null) {
            schema.maxItems(maxItems);
        }
        if ((uniqueItems = this.resolveUniqueItems(annotatedType, resolvedArrayAnnotation)) != null) {
            schema.uniqueItems(uniqueItems);
        }
        if ((extensions = this.resolveExtensions(annotatedType, resolvedArrayAnnotation)) != null) {
            schema.extensions(extensions);
        }
        if (resolvedArrayAnnotation != null) {
            if (AnnotationsUtils.hasSchemaAnnotation(resolvedArrayAnnotation.arraySchema())) {
                this.resolveSchemaMembers((Schema)schema, null, null, resolvedArrayAnnotation.arraySchema());
            }
            if (this.openapi31) {
                if (AnnotationsUtils.hasSchemaAnnotation(resolvedArrayAnnotation.contains())) {
                    this.resolveContains(annotatedType, schema, resolvedArrayAnnotation);
                }
                if (AnnotationsUtils.hasSchemaAnnotation(resolvedArrayAnnotation.unevaluatedItems())) {
                    this.resolveUnevaluatedItems(annotatedType, schema, resolvedArrayAnnotation);
                }
                if (resolvedArrayAnnotation.prefixItems().length > 0) {
                    for (io.swagger.v3.oas.annotations.media.Schema prefixItemAnnotation : resolvedArrayAnnotation.prefixItems()) {
                        Schema prefixItem = new Schema();
                        if (StringUtils.isNotBlank(prefixItemAnnotation.type())) {
                            prefixItem.addType(prefixItemAnnotation.type());
                        }
                        this.resolveSchemaMembers(prefixItem, null, null, prefixItemAnnotation);
                        schema.addPrefixItem(prefixItem);
                    }
                }
                if (schema.getItems() != null && AnnotationsUtils.hasSchemaAnnotation(resolvedArrayAnnotation.items())) {
                    for (String type : resolvedArrayAnnotation.items().types()) {
                        schema.getItems().addType(type);
                    }
                }
            }
        }
    }

    public ModelResolver openapi31(boolean openapi31) {
        this.openapi31 = openapi31;
        return this;
    }

    @Override
    public boolean isOpenapi31() {
        return this.openapi31;
    }

    public void setOpenapi31(boolean openapi31) {
        this.openapi31 = openapi31;
    }

    protected boolean isObjectSchema(Schema schema) {
        return "object".equals(schema.getType()) || schema.getType() == null && schema.getProperties() != null && !schema.getProperties().isEmpty();
    }

    protected Schema buildRefSchemaIfObject(Schema schema, ModelConverterContext context) {
        if (schema == null) {
            return null;
        }
        Schema result = schema;
        if (this.isObjectSchema(schema) && StringUtils.isNotBlank(schema.getName()) && context.getDefinedModels().containsKey(schema.getName())) {
            result = new Schema().$ref(RefUtils.constructRef(schema.getName()));
        }
        return result;
    }

    private class GeneratorWrapper {
        private final List<Base> wrappers = new ArrayList<Base>();

        private GeneratorWrapper() {
        }

        public Schema processJsonIdentity(AnnotatedType type, ModelConverterContext context, ObjectMapper mapper2, JsonIdentityInfo identityInfo, JsonIdentityReference identityReference) {
            Base wrapper;
            Base base = wrapper = identityInfo != null ? this.getWrapper(identityInfo.generator()) : null;
            if (wrapper == null) {
                return null;
            }
            if (identityReference != null && identityReference.alwaysAsId()) {
                return wrapper.processAsId(identityInfo.property(), type, context, mapper2);
            }
            return wrapper.processAsProperty(identityInfo.property(), type, context, mapper2);
        }

        private Base getWrapper(Class<? extends ObjectIdGenerator> generator) {
            if (ObjectIdGenerators.PropertyGenerator.class.isAssignableFrom(generator)) {
                return new PropertyGeneratorWrapper(generator);
            }
            if (ObjectIdGenerators.IntSequenceGenerator.class.isAssignableFrom(generator)) {
                return new IntGeneratorWrapper(generator);
            }
            if (ObjectIdGenerators.UUIDGenerator.class.isAssignableFrom(generator)) {
                return new UUIDGeneratorWrapper(generator);
            }
            if (ObjectIdGenerators.None.class.isAssignableFrom(generator)) {
                return new NoneGeneratorWrapper(generator);
            }
            return null;
        }

        protected Schema process(Schema id, String propertyName, AnnotatedType type, ModelConverterContext context) {
            Schema model = context.resolve(type = this.removeJsonIdentityAnnotations(type));
            if (model == null) {
                model = ModelResolver.this.resolve(type, context, null);
            }
            model.addProperties(propertyName, id);
            return new Schema().$ref(StringUtils.isNotEmpty(model.get$ref()) ? model.get$ref() : model.getName());
        }

        private AnnotatedType removeJsonIdentityAnnotations(AnnotatedType type) {
            return new AnnotatedType().jsonUnwrappedHandler(type.getJsonUnwrappedHandler()).jsonViewAnnotation(type.getJsonViewAnnotation()).name(type.getName()).parent(type.getParent()).resolveAsRef(false).schemaProperty(type.isSchemaProperty()).skipOverride(type.isSkipOverride()).skipSchemaName(type.isSkipSchemaName()).type(type.getType()).skipJsonIdentity(true).propertyName(type.getPropertyName()).components(type.getComponents()).ctxAnnotations(AnnotationsUtils.removeAnnotations(type.getCtxAnnotations(), JsonIdentityInfo.class, JsonIdentityReference.class));
        }

        private abstract class Base<T> {
            private final Class<? extends ObjectIdGenerator> generator;

            Base(Class<? extends ObjectIdGenerator> generator) {
                this.generator = generator;
            }

            protected abstract Schema processAsProperty(String var1, AnnotatedType var2, ModelConverterContext var3, ObjectMapper var4);

            protected abstract Schema processAsId(String var1, AnnotatedType var2, ModelConverterContext var3, ObjectMapper var4);
        }

        private final class NoneGeneratorWrapper
        extends Base<ObjectIdGenerators.None> {
            public NoneGeneratorWrapper(Class<? extends ObjectIdGenerator> generator) {
                super(generator);
            }

            @Override
            protected Schema processAsProperty(String propertyName, AnnotatedType type, ModelConverterContext context, ObjectMapper mapper2) {
                return null;
            }

            @Override
            protected Schema processAsId(String propertyName, AnnotatedType type, ModelConverterContext context, ObjectMapper mapper2) {
                return null;
            }
        }

        private final class UUIDGeneratorWrapper
        extends Base<ObjectIdGenerators.UUIDGenerator> {
            public UUIDGeneratorWrapper(Class<? extends ObjectIdGenerator> generator) {
                super(generator);
            }

            @Override
            protected Schema processAsProperty(String propertyName, AnnotatedType type, ModelConverterContext context, ObjectMapper mapper2) {
                UUIDSchema id = new UUIDSchema();
                return GeneratorWrapper.this.process(id, propertyName, type, context);
            }

            @Override
            protected Schema processAsId(String propertyName, AnnotatedType type, ModelConverterContext context, ObjectMapper mapper2) {
                return new UUIDSchema();
            }
        }

        private final class IntGeneratorWrapper
        extends Base<ObjectIdGenerators.IntSequenceGenerator> {
            public IntGeneratorWrapper(Class<? extends ObjectIdGenerator> generator) {
                super(generator);
            }

            @Override
            protected Schema processAsProperty(String propertyName, AnnotatedType type, ModelConverterContext context, ObjectMapper mapper2) {
                IntegerSchema id = new IntegerSchema();
                return GeneratorWrapper.this.process(id, propertyName, type, context);
            }

            @Override
            protected Schema processAsId(String propertyName, AnnotatedType type, ModelConverterContext context, ObjectMapper mapper2) {
                return new IntegerSchema();
            }
        }

        private final class PropertyGeneratorWrapper
        extends Base<ObjectIdGenerators.PropertyGenerator> {
            public PropertyGeneratorWrapper(Class<? extends ObjectIdGenerator> generator) {
                super(generator);
            }

            @Override
            protected Schema processAsProperty(String propertyName, AnnotatedType type, ModelConverterContext context, ObjectMapper mapper2) {
                return null;
            }

            @Override
            protected Schema processAsId(String propertyName, AnnotatedType type, ModelConverterContext context, ObjectMapper mapper2) {
                JavaType javaType = type.getType() instanceof JavaType ? (JavaType)type.getType() : mapper2.constructType(type.getType());
                BeanDescription beanDesc = mapper2.getSerializationConfig().introspect(javaType);
                for (BeanPropertyDefinition def : beanDesc.findProperties()) {
                    String name = def.getName();
                    if (name == null || !name.equals(propertyName)) continue;
                    AnnotatedMember propMember = def.getPrimaryMember();
                    JavaType propType = propMember.getType();
                    if (PrimitiveType.fromType(propType) != null) {
                        return PrimitiveType.createProperty(propType);
                    }
                    ArrayList<Annotation> list = new ArrayList<Annotation>();
                    for (Annotation a : propMember.annotations()) {
                        list.add(a);
                    }
                    Annotation[] annotations = list.toArray(new Annotation[list.size()]);
                    AnnotatedType aType = new AnnotatedType().type(propType).ctxAnnotations(annotations).jsonViewAnnotation(type.getJsonViewAnnotation()).schemaProperty(true).components(type.getComponents()).propertyName(type.getPropertyName());
                    return context.resolve(aType);
                }
                return null;
            }
        }
    }
}

