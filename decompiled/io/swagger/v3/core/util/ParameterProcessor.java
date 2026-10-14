/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.core.util;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.converter.ResolvedSchema;
import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.core.util.AnnotationsUtils;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.Explode;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.parameters.Parameter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParameterProcessor {
    static Logger LOGGER = LoggerFactory.getLogger(ParameterProcessor.class);
    public static final String MEDIA_TYPE = "*/*";

    public static io.swagger.v3.oas.models.parameters.Parameter applyAnnotations(io.swagger.v3.oas.models.parameters.Parameter parameter, Type type, List<Annotation> annotations, Components components, String[] classTypes, String[] methodTypes, JsonView jsonViewAnnotation) {
        return ParameterProcessor.applyAnnotations(parameter, type, annotations, components, classTypes, methodTypes, jsonViewAnnotation, false);
    }

    public static io.swagger.v3.oas.models.parameters.Parameter applyAnnotations(io.swagger.v3.oas.models.parameters.Parameter parameter, Type type, List<Annotation> annotations, Components components, String[] classTypes, String[] methodTypes, JsonView jsonViewAnnotation, boolean openapi31) {
        io.swagger.v3.oas.models.media.ArraySchema as;
        String name2;
        AnnotationsHelper helper = new AnnotationsHelper(annotations, type);
        if (helper.isContext()) {
            return null;
        }
        if (parameter == null) {
            parameter = new io.swagger.v3.oas.models.parameters.Parameter();
        }
        ArrayList<Annotation> reworkedAnnotations = new ArrayList<Annotation>(annotations);
        Annotation paramSchemaOrArrayAnnotation = ParameterProcessor.getParamSchemaAnnotation(annotations);
        if (paramSchemaOrArrayAnnotation != null) {
            reworkedAnnotations.add(paramSchemaOrArrayAnnotation);
        }
        AnnotatedType annotatedType = new AnnotatedType().type(type).resolveAsRef(true).skipOverride(true).jsonViewAnnotation(jsonViewAnnotation).ctxAnnotations(reworkedAnnotations.toArray(new Annotation[reworkedAnnotations.size()]));
        ResolvedSchema resolvedSchema = ModelConverters.getInstance(openapi31).resolveAsResolvedSchema(annotatedType);
        if (resolvedSchema.schema != null) {
            parameter.setSchema(resolvedSchema.schema);
        }
        resolvedSchema.referencedSchemas.forEach(components::addSchemas);
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().getName().equals("javax.ws.rs.FormParam")) {
                try {
                    name2 = (String)annotation.annotationType().getMethod("value", new Class[0]).invoke((Object)annotation, new Object[0]);
                    if (StringUtils.isNotBlank(name2)) {
                        parameter.setName(name2);
                    }
                }
                catch (Exception name2) {
                    // empty catch block
                }
                parameter.setIn("form");
                continue;
            }
            if (!annotation.annotationType().getName().endsWith("FormDataParam")) continue;
            try {
                name2 = (String)annotation.annotationType().getMethod("value", new Class[0]).invoke((Object)annotation, new Object[0]);
                if (StringUtils.isNotBlank(name2)) {
                    parameter.setName(name2);
                }
            }
            catch (Exception name3) {
                // empty catch block
            }
            parameter.setIn("form");
        }
        for (Annotation annotation : annotations) {
            if (annotation instanceof Parameter) {
                Optional<Content> content;
                Map<String, Object> extensionMap;
                Parameter p = (Parameter)annotation;
                if (p.hidden()) {
                    return null;
                }
                if (StringUtils.isNotBlank(p.ref())) {
                    parameter = new io.swagger.v3.oas.models.parameters.Parameter().$ref(p.ref());
                    return parameter;
                }
                if (StringUtils.isNotBlank(p.description())) {
                    parameter.setDescription(p.description());
                }
                if (StringUtils.isNotBlank(p.name())) {
                    parameter.setName(p.name());
                }
                if (StringUtils.isNotBlank(p.in().toString())) {
                    parameter.setIn(p.in().toString());
                }
                if (StringUtils.isNotBlank(p.example())) {
                    try {
                        parameter.setExample(Json.mapper().readTree(p.example()));
                    }
                    catch (IOException e) {
                        parameter.setExample(p.example());
                    }
                }
                if (p.deprecated()) {
                    parameter.setDeprecated(p.deprecated());
                }
                if (p.required()) {
                    parameter.setRequired(p.required());
                }
                if (p.allowEmptyValue()) {
                    parameter.setAllowEmptyValue(p.allowEmptyValue());
                }
                if (p.allowReserved()) {
                    parameter.setAllowReserved(p.allowReserved());
                }
                LinkedHashMap<String, Example> exampleMap = new LinkedHashMap<String, Example>();
                if (p.examples().length == 1 && StringUtils.isBlank(p.examples()[0].name())) {
                    Optional<Example> exampleOptional = AnnotationsUtils.getExample(p.examples()[0], true);
                    if (exampleOptional.isPresent()) {
                        parameter.setExample(exampleOptional.get());
                    }
                } else {
                    for (ExampleObject exampleObject : p.examples()) {
                        AnnotationsUtils.getExample(exampleObject).ifPresent(example -> exampleMap.put(exampleObject.name(), (Example)example));
                    }
                }
                if (exampleMap.size() > 0) {
                    parameter.setExamples(exampleMap);
                }
                if (p.extensions().length > 0 && (extensionMap = AnnotationsUtils.getExtensions(openapi31, p.extensions())) != null && !extensionMap.isEmpty()) {
                    extensionMap.forEach(parameter::addExtension);
                }
                if ((content = AnnotationsUtils.getContent(p.content(), classTypes, methodTypes, parameter.getSchema(), null, jsonViewAnnotation)).isPresent()) {
                    parameter.setContent(content.get());
                    parameter.setSchema(null);
                }
                ParameterProcessor.setParameterStyle(parameter, p);
                ParameterProcessor.setParameterExplode(parameter, p);
                continue;
            }
            if (annotation.annotationType().getName().equals("javax.ws.rs.PathParam")) {
                try {
                    name2 = (String)annotation.annotationType().getMethod("value", new Class[0]).invoke((Object)annotation, new Object[0]);
                    if (!StringUtils.isNotBlank(name2)) continue;
                    parameter.setName(name2);
                }
                catch (Exception name4) {}
                continue;
            }
            if (annotation.annotationType().getName().equals("javax.validation.constraints.Size")) {
                try {
                    Integer max;
                    if (parameter.getSchema() == null) {
                        parameter.setSchema(new io.swagger.v3.oas.models.media.ArraySchema());
                    }
                    if (!(parameter.getSchema() instanceof io.swagger.v3.oas.models.media.ArraySchema)) continue;
                    as = (io.swagger.v3.oas.models.media.ArraySchema)parameter.getSchema();
                    Integer min2 = (Integer)annotation.annotationType().getMethod("min", new Class[0]).invoke((Object)annotation, new Object[0]);
                    if (min2 != null) {
                        as.setMinItems(min2);
                    }
                    if ((max = (Integer)annotation.annotationType().getMethod("max", new Class[0]).invoke((Object)annotation, new Object[0])) == null) continue;
                    as.setMaxItems(max);
                }
                catch (Exception e) {
                    LOGGER.error("failed on " + annotation.annotationType().getName(), e);
                }
                continue;
            }
            if (!ModelResolver.NOT_NULL_ANNOTATIONS.contains(annotation.annotationType().getSimpleName())) continue;
            parameter.setRequired(true);
        }
        String defaultValue = helper.getDefaultValue();
        io.swagger.v3.oas.models.media.Schema paramSchema = parameter.getSchema();
        if (paramSchema == null && parameter.getContent() != null && !parameter.getContent().values().isEmpty()) {
            paramSchema = ((MediaType)parameter.getContent().values().iterator().next()).getSchema();
        }
        if (paramSchema != null) {
            if (paramSchema instanceof io.swagger.v3.oas.models.media.ArraySchema) {
                as = (io.swagger.v3.oas.models.media.ArraySchema)paramSchema;
                if (defaultValue != null) {
                    as.getItems().setDefault(defaultValue);
                }
            } else if (defaultValue != null) {
                paramSchema.setDefault(defaultValue);
            }
        }
        return parameter;
    }

    public static void setParameterExplode(io.swagger.v3.oas.models.parameters.Parameter parameter, Parameter p) {
        if (ParameterProcessor.isExplodable(p, parameter)) {
            if (Explode.TRUE.equals((Object)p.explode())) {
                parameter.setExplode(Boolean.TRUE);
            } else if (Explode.FALSE.equals((Object)p.explode())) {
                parameter.setExplode(Boolean.FALSE);
            }
        }
    }

    private static boolean isExplodable(Parameter p, io.swagger.v3.oas.models.parameters.Parameter parameter) {
        Class<?> implementation;
        Schema schema = p.schema();
        boolean explode = true;
        if ("form".equals(parameter.getIn())) {
            return true;
        }
        if (schema != null && (implementation = schema.implementation()) == Void.class && !schema.type().equals("object") && !schema.type().equals("array")) {
            explode = false;
        }
        return explode;
    }

    public static void setParameterStyle(io.swagger.v3.oas.models.parameters.Parameter parameter, Parameter p) {
        if (StringUtils.isNotBlank(p.style().toString())) {
            parameter.setStyle(Parameter.StyleEnum.valueOf(p.style().toString().toUpperCase()));
        }
    }

    public static Annotation getParamSchemaAnnotation(List<Annotation> annotations) {
        if (annotations == null) {
            return null;
        }
        Schema rootSchema = null;
        ArraySchema rootArraySchema = null;
        Schema contentSchema = null;
        Schema paramSchema = null;
        ArraySchema paramArraySchema = null;
        for (Annotation annotation : annotations) {
            if (annotation instanceof Schema) {
                rootSchema = (Schema)annotation;
                continue;
            }
            if (annotation instanceof ArraySchema) {
                rootArraySchema = (ArraySchema)annotation;
                continue;
            }
            if (!(annotation instanceof Parameter)) continue;
            Parameter paramAnnotation = (Parameter)annotation;
            if (paramAnnotation.content().length > 0 && AnnotationsUtils.hasSchemaAnnotation(paramAnnotation.content()[0].schema())) {
                contentSchema = paramAnnotation.content()[0].schema();
            }
            if (AnnotationsUtils.hasSchemaAnnotation(paramAnnotation.schema())) {
                paramSchema = paramAnnotation.schema();
            }
            if (!AnnotationsUtils.hasArrayAnnotation(paramAnnotation.array())) continue;
            paramArraySchema = paramAnnotation.array();
        }
        if (rootSchema != null || rootArraySchema != null) {
            return null;
        }
        if (contentSchema != null) {
            return contentSchema;
        }
        if (paramSchema != null) {
            return paramSchema;
        }
        if (paramArraySchema != null) {
            return paramArraySchema;
        }
        return null;
    }

    public static Type getParameterType(Parameter paramAnnotation) {
        return ParameterProcessor.getParameterType(paramAnnotation, false);
    }

    public static Type getParameterType(Parameter paramAnnotation, boolean nullIfNotFound) {
        if (paramAnnotation == null) {
            return null;
        }
        Schema contentSchema = null;
        Schema paramSchema = null;
        ArraySchema paramArraySchema = null;
        if (paramAnnotation.content().length > 0 && AnnotationsUtils.hasSchemaAnnotation(paramAnnotation.content()[0].schema())) {
            contentSchema = paramAnnotation.content()[0].schema();
        }
        if (AnnotationsUtils.hasSchemaAnnotation(paramAnnotation.schema())) {
            paramSchema = paramAnnotation.schema();
        }
        if (AnnotationsUtils.hasArrayAnnotation(paramAnnotation.array())) {
            paramArraySchema = paramAnnotation.array();
        }
        if (contentSchema != null) {
            return AnnotationsUtils.getSchemaType(contentSchema, nullIfNotFound);
        }
        if (paramSchema != null) {
            return AnnotationsUtils.getSchemaType(paramSchema, nullIfNotFound);
        }
        if (paramArraySchema != null) {
            return AnnotationsUtils.getSchemaType(paramArraySchema.schema(), nullIfNotFound);
        }
        if (nullIfNotFound) {
            return null;
        }
        return String.class;
    }

    private static class AnnotationsHelper {
        private boolean context;
        private String defaultValue;

        public AnnotationsHelper(List<Annotation> annotations, Type _type) {
            String rsDefault = null;
            if (annotations != null) {
                for (Annotation item : annotations) {
                    if ("javax.ws.rs.core.Context".equals(item.annotationType().getName())) {
                        this.context = true;
                        continue;
                    }
                    if (!"javax.ws.rs.DefaultValue".equals(item.annotationType().getName())) continue;
                    try {
                        rsDefault = (String)item.annotationType().getMethod("value", new Class[0]).invoke((Object)item, new Object[0]);
                    }
                    catch (Exception ex) {
                        LOGGER.error("Invocation of value method failed", ex);
                    }
                }
            }
            this.defaultValue = rsDefault;
        }

        public boolean isContext() {
            return this.context;
        }

        public String getDefaultValue() {
            return this.defaultValue;
        }
    }
}

