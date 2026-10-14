/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.jaxrs2;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.AnnotationMap;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.core.util.ParameterProcessor;
import io.swagger.v3.jaxrs2.ResolvedParameter;
import io.swagger.v3.jaxrs2.ext.AbstractOpenAPIExtension;
import io.swagger.v3.jaxrs2.ext.OpenAPIExtension;
import io.swagger.v3.jaxrs2.ext.OpenAPIExtensions;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.parameters.Parameter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import org.apache.commons.lang3.StringUtils;

public class DefaultParameterExtension
extends AbstractOpenAPIExtension {
    private static final String QUERY_PARAM = "query";
    private static final String HEADER_PARAM = "header";
    private static final String COOKIE_PARAM = "cookie";
    private static final String PATH_PARAM = "path";
    final ObjectMapper mapper = Json.mapper();

    @Override
    public ResolvedParameter extractParameters(List<Annotation> annotations, Type type, Set<Type> typesToSkip, Components components, Consumes classConsumes, Consumes methodConsumes, boolean includeRequestBody, JsonView jsonViewAnnotation, Iterator<OpenAPIExtension> chain) {
        io.swagger.v3.oas.models.parameters.Parameter unknownParameter;
        if (this.shouldIgnoreType(type, typesToSkip)) {
            return new ResolvedParameter();
        }
        io.swagger.v3.oas.models.parameters.Parameter parameter = null;
        for (Annotation annotation : annotations) {
            io.swagger.v3.oas.models.parameters.Parameter pp;
            Annotation param2;
            if (annotation instanceof QueryParam) {
                param2 = (QueryParam)annotation;
                io.swagger.v3.oas.models.parameters.Parameter qp = new io.swagger.v3.oas.models.parameters.Parameter();
                qp.setIn(QUERY_PARAM);
                qp.setName(param2.value());
                parameter = qp;
                continue;
            }
            if (annotation instanceof PathParam) {
                param2 = (PathParam)annotation;
                pp = new io.swagger.v3.oas.models.parameters.Parameter();
                pp.setIn(PATH_PARAM);
                pp.setName(param2.value());
                parameter = pp;
                continue;
            }
            if (annotation instanceof MatrixParam) {
                param2 = (MatrixParam)annotation;
                pp = new io.swagger.v3.oas.models.parameters.Parameter();
                pp.setIn(PATH_PARAM);
                pp.setStyle(Parameter.StyleEnum.MATRIX);
                pp.setName(param2.value());
                parameter = pp;
                continue;
            }
            if (annotation instanceof HeaderParam) {
                param2 = (HeaderParam)annotation;
                pp = new io.swagger.v3.oas.models.parameters.Parameter();
                pp.setIn(HEADER_PARAM);
                pp.setName(param2.value());
                parameter = pp;
                continue;
            }
            if (annotation instanceof CookieParam) {
                param2 = (CookieParam)annotation;
                pp = new io.swagger.v3.oas.models.parameters.Parameter();
                pp.setIn(COOKIE_PARAM);
                pp.setName(param2.value());
                parameter = pp;
                continue;
            }
            if (annotation instanceof Parameter) {
                if (((Parameter)annotation).hidden()) {
                    return new ResolvedParameter();
                }
                if (parameter == null) {
                    parameter = new io.swagger.v3.oas.models.parameters.Parameter();
                }
                if (!StringUtils.isNotBlank(((Parameter)annotation).ref())) continue;
                parameter.$ref(((Parameter)annotation).ref());
                continue;
            }
            ArrayList<io.swagger.v3.oas.models.parameters.Parameter> parameters = new ArrayList<io.swagger.v3.oas.models.parameters.Parameter>();
            ArrayList<io.swagger.v3.oas.models.parameters.Parameter> formParameters = new ArrayList<io.swagger.v3.oas.models.parameters.Parameter>();
            if (!this.handleAdditionalAnnotation(parameters, formParameters, annotation, type, typesToSkip, classConsumes, methodConsumes, components, includeRequestBody, jsonViewAnnotation)) continue;
            ResolvedParameter extractParametersResult = new ResolvedParameter();
            extractParametersResult.parameters.addAll(parameters);
            extractParametersResult.formParameters.addAll(formParameters);
            return extractParametersResult;
        }
        ArrayList<io.swagger.v3.oas.models.parameters.Parameter> parameters = new ArrayList<io.swagger.v3.oas.models.parameters.Parameter>();
        ResolvedParameter extractParametersResult = new ResolvedParameter();
        if (parameter != null && (StringUtils.isNotBlank(parameter.getIn()) || StringUtils.isNotBlank(parameter.get$ref()))) {
            parameters.add(parameter);
        } else if (includeRequestBody && (unknownParameter = ParameterProcessor.applyAnnotations(null, type, annotations, components, classConsumes == null ? new String[]{} : classConsumes.value(), methodConsumes == null ? new String[]{} : methodConsumes.value(), jsonViewAnnotation, this.openapi31)) != null) {
            if (StringUtils.isNotBlank(unknownParameter.getIn()) && !"form".equals(unknownParameter.getIn())) {
                extractParametersResult.parameters.add(unknownParameter);
            } else if ("form".equals(unknownParameter.getIn())) {
                unknownParameter.setIn(null);
                extractParametersResult.formParameters.add(unknownParameter);
            } else {
                extractParametersResult.requestBody = unknownParameter;
            }
        }
        for (io.swagger.v3.oas.models.parameters.Parameter p : parameters) {
            io.swagger.v3.oas.models.parameters.Parameter processedParameter = ParameterProcessor.applyAnnotations(p, type, annotations, components, classConsumes == null ? new String[]{} : classConsumes.value(), methodConsumes == null ? new String[]{} : methodConsumes.value(), jsonViewAnnotation, this.openapi31);
            if (processedParameter == null) continue;
            extractParametersResult.parameters.add(processedParameter);
        }
        return extractParametersResult;
    }

    private boolean handleAdditionalAnnotation(List<io.swagger.v3.oas.models.parameters.Parameter> parameters, List<io.swagger.v3.oas.models.parameters.Parameter> formParameters, Annotation annotation, Type type, Set<Type> typesToSkip, Consumes classConsumes, Consumes methodConsumes, Components components, boolean includeRequestBody, JsonView jsonViewAnnotation) {
        boolean processed = false;
        if (BeanParam.class.isAssignableFrom(annotation.getClass())) {
            BeanDescription beanDesc = this.mapper.getSerializationConfig().introspect(this.constructType(type));
            List<BeanPropertyDefinition> properties = beanDesc.findProperties();
            for (BeanPropertyDefinition propDef : properties) {
                AnnotationMap annotationMap;
                AnnotatedField field2 = propDef.getField();
                AnnotatedMethod setter2 = propDef.getSetter();
                AnnotatedMethod getter2 = propDef.getGetter();
                ArrayList<Annotation> paramAnnotations = new ArrayList<Annotation>();
                Iterator<OpenAPIExtension> extensions = OpenAPIExtensions.chain();
                JavaType paramType = null;
                if (field2 != null) {
                    paramType = field2.getType();
                    annotationMap = field2.getAllAnnotations();
                    if (annotationMap != null) {
                        for (Annotation fieldAnnotation : annotationMap.annotations()) {
                            if (paramAnnotations.contains(fieldAnnotation)) continue;
                            paramAnnotations.add(fieldAnnotation);
                        }
                    }
                }
                if (setter2 != null) {
                    if (paramType == null) {
                        paramType = setter2.getParameterType(0);
                    }
                    if ((annotationMap = setter2.getAllAnnotations()) != null) {
                        for (Annotation fieldAnnotation : annotationMap.annotations()) {
                            if (paramAnnotations.contains(fieldAnnotation)) continue;
                            paramAnnotations.add(fieldAnnotation);
                        }
                    }
                }
                if (getter2 != null) {
                    if (paramType == null) {
                        paramType = getter2.getType();
                    }
                    if ((annotationMap = getter2.getAllAnnotations()) != null) {
                        for (Annotation fieldAnnotation : annotationMap.annotations()) {
                            if (paramAnnotations.contains(fieldAnnotation)) continue;
                            paramAnnotations.add(fieldAnnotation);
                        }
                    }
                }
                if (paramType == null) continue;
                boolean hidden = false;
                for (Annotation a : paramAnnotations) {
                    if (a instanceof Schema) {
                        if (!((Schema)a).hidden()) continue;
                        hidden = true;
                        break;
                    }
                    if (!(a instanceof Hidden)) continue;
                    hidden = true;
                    break;
                }
                if (hidden) continue;
                ResolvedParameter resolvedParameter = extensions.next().extractParameters(paramAnnotations, paramType, typesToSkip, components, classConsumes, methodConsumes, includeRequestBody, jsonViewAnnotation, extensions);
                List<io.swagger.v3.oas.models.parameters.Parameter> extractedParameters = resolvedParameter.parameters;
                for (io.swagger.v3.oas.models.parameters.Parameter p : extractedParameters) {
                    io.swagger.v3.oas.models.parameters.Parameter processedParam = ParameterProcessor.applyAnnotations(p, paramType, paramAnnotations, components, classConsumes == null ? new String[]{} : classConsumes.value(), methodConsumes == null ? new String[]{} : methodConsumes.value(), jsonViewAnnotation);
                    if (processedParam == null) continue;
                    parameters.add(processedParam);
                }
                List<io.swagger.v3.oas.models.parameters.Parameter> extractedFormParameters = resolvedParameter.formParameters;
                for (io.swagger.v3.oas.models.parameters.Parameter p : extractedFormParameters) {
                    io.swagger.v3.oas.models.parameters.Parameter processedParam = ParameterProcessor.applyAnnotations(p, paramType, paramAnnotations, components, classConsumes == null ? new String[]{} : classConsumes.value(), methodConsumes == null ? new String[]{} : methodConsumes.value(), jsonViewAnnotation);
                    if (processedParam == null) continue;
                    formParameters.add(processedParam);
                }
                processed = true;
            }
        }
        return processed;
    }

    @Override
    protected boolean shouldIgnoreClass(Class<?> cls) {
        return cls.getName().startsWith("javax.ws.rs.");
    }
}

