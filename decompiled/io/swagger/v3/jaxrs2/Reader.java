/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.jaxrs2;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;
import com.fasterxml.jackson.databind.type.TypeFactory;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.converter.ResolvedSchema;
import io.swagger.v3.core.util.AnnotationsUtils;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.core.util.Json31;
import io.swagger.v3.core.util.ParameterProcessor;
import io.swagger.v3.core.util.PathUtils;
import io.swagger.v3.core.util.ReflectionUtils;
import io.swagger.v3.jaxrs2.OperationParser;
import io.swagger.v3.jaxrs2.ReaderListener;
import io.swagger.v3.jaxrs2.ResolvedParameter;
import io.swagger.v3.jaxrs2.SecurityParser;
import io.swagger.v3.jaxrs2.ext.OpenAPIExtension;
import io.swagger.v3.jaxrs2.ext.OpenAPIExtensions;
import io.swagger.v3.jaxrs2.util.ReaderUtils;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Webhook;
import io.swagger.v3.oas.annotations.Webhooks;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.integration.ContextUtils;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.integration.api.OpenAPIConfiguration;
import io.swagger.v3.oas.integration.api.OpenApiReader;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.callbacks.Callback;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.Encoding;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.servers.Server;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Reader
implements OpenApiReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(Reader.class);
    public static final String DEFAULT_MEDIA_TYPE_VALUE = "*/*";
    public static final String DEFAULT_DESCRIPTION = "default response";
    protected OpenAPIConfiguration config;
    private Application application;
    private OpenAPI openAPI;
    private Components components;
    private Paths paths;
    private Set<io.swagger.v3.oas.models.tags.Tag> openApiTags;
    private String defaultResponseKey = "default";
    private static final String GET_METHOD = "get";
    private static final String POST_METHOD = "post";
    private static final String PUT_METHOD = "put";
    private static final String DELETE_METHOD = "delete";
    private static final String PATCH_METHOD = "patch";
    private static final String TRACE_METHOD = "trace";
    private static final String HEAD_METHOD = "head";
    private static final String OPTIONS_METHOD = "options";

    public Reader() {
        this(new OpenAPI(), new Paths(), new LinkedHashSet<io.swagger.v3.oas.models.tags.Tag>(), new Components());
    }

    public Reader(OpenAPI openAPI) {
        this(openAPI, new Paths(), new LinkedHashSet<io.swagger.v3.oas.models.tags.Tag>(), new Components());
    }

    public Reader(OpenAPIConfiguration openApiConfiguration) {
        this(new OpenAPI(), new Paths(), new LinkedHashSet<io.swagger.v3.oas.models.tags.Tag>(), new Components(), openApiConfiguration);
    }

    protected Reader(OpenAPI openAPI, Paths paths, Set<io.swagger.v3.oas.models.tags.Tag> openApiTags, Components components) {
        this(openAPI, paths, openApiTags, components, new SwaggerConfiguration().openAPI(openAPI));
    }

    protected Reader(OpenAPI openAPI, Paths paths, Set<io.swagger.v3.oas.models.tags.Tag> openApiTags, Components components, OpenAPIConfiguration openApiConfiguration) {
        this.openAPI = openAPI;
        this.paths = paths;
        this.openApiTags = openApiTags;
        this.components = components;
        this.setConfiguration(openApiConfiguration);
    }

    public OpenAPI getOpenAPI() {
        return this.openAPI;
    }

    protected Set<io.swagger.v3.oas.models.tags.Tag> getOpenApiTags() {
        return this.openApiTags;
    }

    protected Components getComponents() {
        return this.components;
    }

    protected Paths getPaths() {
        return this.paths;
    }

    public OpenAPI read(Class<?> cls) {
        return this.read(cls, this.resolveApplicationPath(), null, false, null, null, new LinkedHashSet<String>(), new ArrayList<io.swagger.v3.oas.models.parameters.Parameter>(), new HashSet());
    }

    public OpenAPI read(Set<Class<?>> classes) {
        TreeSet sortedClasses = new TreeSet((class1, class2) -> {
            if (class1.equals(class2)) {
                return 0;
            }
            if (class1.isAssignableFrom((Class<?>)class2)) {
                return -1;
            }
            if (class2.isAssignableFrom((Class<?>)class1)) {
                return 1;
            }
            return class1.getName().compareTo(class2.getName());
        });
        sortedClasses.addAll(classes);
        HashMap<Class, ReaderListener> listeners = new HashMap<Class, ReaderListener>();
        String appPath = "";
        for (Class clazz : sortedClasses) {
            ApplicationPath applicationPath;
            if (ReaderListener.class.isAssignableFrom(clazz) && !listeners.containsKey(clazz)) {
                try {
                    listeners.put(clazz, (ReaderListener)clazz.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]));
                }
                catch (Exception exception) {
                    LOGGER.error("Failed to create ReaderListener", exception);
                }
            }
            if (this.config == null || !Boolean.TRUE.equals(this.config.isAlwaysResolveAppPath()) || Boolean.TRUE.equals(this.config.isSkipResolveAppPath()) || !Application.class.isAssignableFrom(clazz) || (applicationPath = ReflectionUtils.getAnnotation(clazz, ApplicationPath.class)) == null) continue;
            appPath = applicationPath.value();
        }
        for (ReaderListener readerListener : listeners.values()) {
            try {
                readerListener.beforeScan(this, this.openAPI);
            }
            catch (Exception exception) {
                LOGGER.error("Unexpected error invoking beforeScan listener [" + readerListener.getClass().getName() + "]", exception);
            }
        }
        String appPathRuntime = this.resolveApplicationPath();
        if (StringUtils.isNotBlank(appPathRuntime)) {
            appPath = appPathRuntime;
        }
        for (Class clazz : sortedClasses) {
            this.read(clazz, appPath, null, false, null, null, new LinkedHashSet<String>(), new ArrayList<io.swagger.v3.oas.models.parameters.Parameter>(), new HashSet());
        }
        for (ReaderListener readerListener : listeners.values()) {
            try {
                readerListener.afterScan(this, this.openAPI);
            }
            catch (Exception e) {
                LOGGER.error("Unexpected error invoking afterScan listener [" + readerListener.getClass().getName() + "]", e);
            }
        }
        return this.openAPI;
    }

    @Override
    public void setConfiguration(OpenAPIConfiguration openApiConfiguration) {
        if (openApiConfiguration != null) {
            this.config = ContextUtils.deepCopy(openApiConfiguration);
            if (openApiConfiguration.getOpenAPI() != null) {
                this.openAPI = this.config.getOpenAPI();
                if (this.openAPI.getComponents() != null) {
                    this.components = this.openAPI.getComponents();
                }
            }
            this.defaultResponseKey = StringUtils.isBlank(this.config.getDefaultResponseCode()) ? "default" : this.config.getDefaultResponseCode();
        }
    }

    @Override
    public OpenAPI read(Set<Class<?>> classes, Map<String, Object> resources) {
        return this.read(classes);
    }

    protected String resolveApplicationPath() {
        if (this.application != null && !Boolean.TRUE.equals(this.config.isSkipResolveAppPath())) {
            ApplicationPath applicationPath;
            Class<?> applicationToScan = this.application.getClass();
            while ((applicationPath = applicationToScan.getAnnotation(ApplicationPath.class)) == null && !applicationToScan.getSuperclass().equals(Application.class)) {
                applicationToScan = applicationToScan.getSuperclass();
            }
            if (applicationPath != null && StringUtils.isNotBlank(applicationPath.value())) {
                return applicationPath.value();
            }
            try {
                Application retrievedApp;
                Application innerApp = this.application;
                Method m4 = this.application.getClass().getMethod("getApplication", new Class[0]);
                while (m4 != null && (retrievedApp = (Application)m4.invoke((Object)innerApp, new Object[0])) != null && !retrievedApp.getClass().equals(innerApp.getClass())) {
                    innerApp = retrievedApp;
                    applicationPath = innerApp.getClass().getAnnotation(ApplicationPath.class);
                    if (applicationPath != null && StringUtils.isNotBlank(applicationPath.value())) {
                        return applicationPath.value();
                    }
                    m4 = innerApp.getClass().getMethod("getApplication", new Class[0]);
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return "";
    }

    public OpenAPI read(Class<?> cls, String parentPath, String parentMethod, boolean isSubresource, RequestBody parentRequestBody, ApiResponses parentResponses, Set<String> parentTags, List<io.swagger.v3.oas.models.parameters.Parameter> parentParameters, Set<Class<?>> scannedResources) {
        Optional<List<SecurityRequirement>> requirementsObject;
        Hidden hidden = cls.getAnnotation(Hidden.class);
        Path apiPath = ReflectionUtils.getAnnotation(cls, Path.class);
        if (Boolean.TRUE.equals(this.config.isOpenAPI31())) {
            this.openAPI.setOpenapi("3.1.0");
        }
        if (hidden != null) {
            return this.openAPI;
        }
        io.swagger.v3.oas.annotations.responses.ApiResponse[] classResponses = (io.swagger.v3.oas.annotations.responses.ApiResponse[])ReflectionUtils.getRepeatableAnnotationsArray(cls, io.swagger.v3.oas.annotations.responses.ApiResponse.class);
        List<SecurityScheme> apiSecurityScheme = ReflectionUtils.getRepeatableAnnotations(cls, SecurityScheme.class);
        List<io.swagger.v3.oas.annotations.security.SecurityRequirement> apiSecurityRequirements = ReflectionUtils.getRepeatableAnnotations(cls, io.swagger.v3.oas.annotations.security.SecurityRequirement.class);
        Webhooks webhooksAnnotation = ReflectionUtils.getAnnotation(cls, Webhooks.class);
        io.swagger.v3.oas.annotations.ExternalDocumentation apiExternalDocs = ReflectionUtils.getAnnotation(cls, io.swagger.v3.oas.annotations.ExternalDocumentation.class);
        Tag[] apiTags = (Tag[])ReflectionUtils.getRepeatableAnnotationsArray(cls, Tag.class);
        io.swagger.v3.oas.annotations.servers.Server[] apiServers = (io.swagger.v3.oas.annotations.servers.Server[])ReflectionUtils.getRepeatableAnnotationsArray(cls, io.swagger.v3.oas.annotations.servers.Server.class);
        Consumes classConsumes = ReflectionUtils.getAnnotation(cls, Consumes.class);
        Produces classProduces = ReflectionUtils.getAnnotation(cls, Produces.class);
        boolean classDeprecated = ReflectionUtils.getAnnotation(cls, Deprecated.class) != null;
        OpenAPIDefinition openAPIDefinition = ReflectionUtils.getAnnotation(cls, OpenAPIDefinition.class);
        if (openAPIDefinition != null) {
            AnnotationsUtils.getInfo(openAPIDefinition.info()).ifPresent(info -> this.openAPI.setInfo((Info)info));
            SecurityParser.getSecurityRequirements(openAPIDefinition.security()).ifPresent(s2 -> this.openAPI.setSecurity((List<SecurityRequirement>)s2));
            AnnotationsUtils.getExternalDocumentation(openAPIDefinition.externalDocs()).ifPresent(docs -> this.openAPI.setExternalDocs((ExternalDocumentation)docs));
            AnnotationsUtils.getTags(openAPIDefinition.tags(), false).ifPresent(tags -> this.openApiTags.addAll((Collection<io.swagger.v3.oas.models.tags.Tag>)tags));
            AnnotationsUtils.getServers(openAPIDefinition.servers()).ifPresent(servers -> this.openAPI.setServers((List<Server>)servers));
            if (openAPIDefinition.extensions().length > 0) {
                this.openAPI.setExtensions(AnnotationsUtils.getExtensions(this.config.isOpenAPI31(), openAPIDefinition.extensions()));
            }
        }
        if (apiSecurityScheme != null) {
            for (SecurityScheme securitySchemeAnnotation : apiSecurityScheme) {
                Optional<SecurityParser.SecuritySchemePair> securityScheme = SecurityParser.getSecurityScheme(securitySchemeAnnotation);
                if (!securityScheme.isPresent()) continue;
                HashMap<String, io.swagger.v3.oas.models.security.SecurityScheme> securitySchemeMap = new HashMap<String, io.swagger.v3.oas.models.security.SecurityScheme>();
                if (!StringUtils.isNotBlank(securityScheme.get().key)) continue;
                securitySchemeMap.put(securityScheme.get().key, securityScheme.get().securityScheme);
                if (this.components.getSecuritySchemes() != null && !this.components.getSecuritySchemes().isEmpty()) {
                    this.components.getSecuritySchemes().putAll(securitySchemeMap);
                    continue;
                }
                this.components.setSecuritySchemes(securitySchemeMap);
            }
        }
        ArrayList<SecurityRequirement> classSecurityRequirements = new ArrayList();
        if (apiSecurityRequirements != null && (requirementsObject = SecurityParser.getSecurityRequirements(apiSecurityRequirements.toArray(new io.swagger.v3.oas.annotations.security.SecurityRequirement[apiSecurityRequirements.size()]))).isPresent()) {
            classSecurityRequirements = requirementsObject.get();
        }
        LinkedHashSet<String> classTags = new LinkedHashSet<String>();
        if (apiTags != null) {
            AnnotationsUtils.getTags(apiTags, false).ifPresent(tags -> tags.stream().map(io.swagger.v3.oas.models.tags.Tag::getName).forEach(classTags::add));
        }
        if (isSubresource && parentTags != null) {
            classTags.addAll(parentTags);
        }
        ArrayList<Server> classServers = new ArrayList<Server>();
        if (apiServers != null) {
            AnnotationsUtils.getServers(apiServers).ifPresent(classServers::addAll);
        }
        Optional<ExternalDocumentation> classExternalDocumentation = AnnotationsUtils.getExternalDocumentation(apiExternalDocs);
        JavaType classType = TypeFactory.defaultInstance().constructType(cls);
        BeanDescription bd = Boolean.TRUE.equals(this.config.isOpenAPI31()) ? Json31.mapper().getSerializationConfig().introspect(classType) : Json.mapper().getSerializationConfig().introspect(classType);
        ArrayList<io.swagger.v3.oas.models.parameters.Parameter> globalParameters = new ArrayList<io.swagger.v3.oas.models.parameters.Parameter>();
        globalParameters.addAll(ReaderUtils.collectConstructorParameters(cls, this.components, classConsumes, null));
        globalParameters.addAll(ReaderUtils.collectFieldParameters(cls, this.components, classConsumes, null));
        List methods = Arrays.stream(cls.getMethods()).sorted(new MethodComparator()).collect(Collectors.toList());
        for (Method method2 : methods) {
            ResolvedParameter resolvedParameter;
            Object paramType;
            Parameter paramAnnotation;
            Object type;
            io.swagger.v3.oas.models.Operation operation;
            JsonView jsonViewAnnotationForRequestBody;
            JsonView jsonViewAnnotation;
            Operation apiOperation;
            LinkedHashMap<String, String> regexMap;
            if (this.isOperationHidden(method2)) continue;
            AnnotatedMethod annotatedMethod = bd.findMethod(method2.getName(), method2.getParameterTypes());
            Produces methodProduces = ReflectionUtils.getAnnotation(method2, Produces.class);
            Consumes methodConsumes = ReflectionUtils.getAnnotation(method2, Consumes.class);
            if (this.isMethodOverridden(method2, cls)) continue;
            boolean methodDeprecated = ReflectionUtils.getAnnotation(method2, Deprecated.class) != null;
            Path methodPath = ReflectionUtils.getAnnotation(method2, Path.class);
            String operationPath = ReaderUtils.getPath(apiPath, methodPath, parentPath, isSubresource);
            if (this.ignoreOperationPath(operationPath, parentPath) && !isSubresource || (operationPath = PathUtils.parsePath(operationPath, regexMap = new LinkedHashMap<String, String>())) == null || this.config != null && ReaderUtils.isIgnored(operationPath, this.config)) continue;
            Class<?> subResource = this.getSubResourceWithJaxRsSubresourceLocatorSpecs(method2);
            String httpMethod = ReaderUtils.extractOperationMethod(method2, OpenAPIExtensions.chain());
            String string = httpMethod = httpMethod == null && isSubresource ? parentMethod : httpMethod;
            if (StringUtils.isBlank(httpMethod) && subResource == null) continue;
            if (StringUtils.isBlank(httpMethod) && subResource != null) {
                Type returnType = method2.getGenericReturnType();
                if (annotatedMethod != null && annotatedMethod.getType() != null) {
                    returnType = annotatedMethod.getType();
                }
                if (this.shouldIgnoreClass(returnType.getTypeName()) && !method2.getGenericReturnType().equals(subResource)) continue;
            }
            if ((apiOperation = ReflectionUtils.getAnnotation(method2, Operation.class)) != null && apiOperation.ignoreJsonView()) {
                jsonViewAnnotation = null;
                jsonViewAnnotationForRequestBody = null;
            } else {
                jsonViewAnnotation = ReflectionUtils.getAnnotation(method2, JsonView.class);
                jsonViewAnnotationForRequestBody = (JsonView)Arrays.stream(ReflectionUtils.getParameterAnnotations(method2)).filter(arr -> Arrays.stream(arr).anyMatch(annotation -> annotation.annotationType().equals(io.swagger.v3.oas.annotations.parameters.RequestBody.class))).flatMap(Arrays::stream).filter(annotation -> annotation.annotationType().equals(JsonView.class)).reduce((a, b) -> null).orElse(jsonViewAnnotation);
            }
            if ((operation = this.parseMethod(method2, globalParameters, methodProduces, classProduces, methodConsumes, classConsumes, classSecurityRequirements, classExternalDocumentation, classTags, classServers, isSubresource, parentRequestBody, parentResponses, jsonViewAnnotation, classResponses, annotatedMethod)) == null) continue;
            if (classDeprecated || methodDeprecated) {
                operation.setDeprecated(true);
            }
            ArrayList<io.swagger.v3.oas.models.parameters.Parameter> operationParameters = new ArrayList<io.swagger.v3.oas.models.parameters.Parameter>();
            ArrayList<io.swagger.v3.oas.models.parameters.Parameter> formParameters = new ArrayList<io.swagger.v3.oas.models.parameters.Parameter>();
            Annotation[][] paramAnnotations = ReflectionUtils.getParameterAnnotations(method2);
            if (annotatedMethod == null) {
                Type[] genericParameterTypes = method2.getGenericParameterTypes();
                for (int i = 0; i < genericParameterTypes.length; ++i) {
                    type = TypeFactory.defaultInstance().constructType(genericParameterTypes[i], cls);
                    paramAnnotation = AnnotationsUtils.getAnnotation(Parameter.class, paramAnnotations[i]);
                    paramType = ParameterProcessor.getParameterType(paramAnnotation, true);
                    if (paramType == null) {
                        paramType = type;
                    } else if (!(paramType instanceof Class)) {
                        paramType = type;
                    }
                    resolvedParameter = this.getParameters((Type)paramType, Arrays.asList(paramAnnotations[i]), operation, classConsumes, methodConsumes, jsonViewAnnotation);
                    operationParameters.addAll(resolvedParameter.parameters);
                    formParameters.addAll(resolvedParameter.formParameters);
                    if (resolvedParameter.requestBody == null) continue;
                    this.processRequestBody(resolvedParameter.requestBody, operation, methodConsumes, classConsumes, operationParameters, paramAnnotations[i], (Type)type, jsonViewAnnotationForRequestBody, null);
                }
            } else {
                for (int i = 0; i < annotatedMethod.getParameterCount(); ++i) {
                    AnnotatedParameter param2 = annotatedMethod.getParameter(i);
                    type = TypeFactory.defaultInstance().constructType(param2.getParameterType(), cls);
                    paramAnnotation = AnnotationsUtils.getAnnotation(Parameter.class, paramAnnotations[i]);
                    paramType = ParameterProcessor.getParameterType(paramAnnotation, true);
                    if (paramType == null) {
                        paramType = type;
                    } else if (!(paramType instanceof Class)) {
                        paramType = type;
                    }
                    resolvedParameter = this.getParameters((Type)paramType, Arrays.asList(paramAnnotations[i]), operation, classConsumes, methodConsumes, jsonViewAnnotation);
                    operationParameters.addAll(resolvedParameter.parameters);
                    formParameters.addAll(resolvedParameter.formParameters);
                    if (resolvedParameter.requestBody == null) continue;
                    this.processRequestBody(resolvedParameter.requestBody, operation, methodConsumes, classConsumes, operationParameters, paramAnnotations[i], (Type)type, jsonViewAnnotationForRequestBody, null);
                }
            }
            if (!formParameters.isEmpty()) {
                Iterator<io.swagger.v3.oas.models.parameters.Parameter> mergedSchema = new ObjectSchema();
                LinkedHashMap<String, Encoding> encoding = new LinkedHashMap<String, Encoding>();
                for (io.swagger.v3.oas.models.parameters.Parameter formParam : formParameters) {
                    if (formParam.getExplode() != null || formParam.getStyle() != null && Encoding.StyleEnum.fromString(formParam.getStyle().toString()) != null) {
                        Encoding e = new Encoding();
                        if (formParam.getExplode() != null) {
                            e.explode(formParam.getExplode());
                        }
                        if (formParam.getStyle() != null && Encoding.StyleEnum.fromString(formParam.getStyle().toString()) != null) {
                            e.style(Encoding.StyleEnum.fromString(formParam.getStyle().toString()));
                        }
                        encoding.put(formParam.getName(), e);
                    }
                    ((Schema)((Object)mergedSchema)).addProperty(formParam.getName(), formParam.getSchema());
                    if (formParam.getSchema() != null && StringUtils.isNotBlank(formParam.getDescription()) && StringUtils.isBlank(formParam.getSchema().getDescription())) {
                        formParam.getSchema().description(formParam.getDescription());
                    }
                    if (null == formParam.getRequired() || !formParam.getRequired().booleanValue()) continue;
                    ((Schema)((Object)mergedSchema)).addRequiredItem(formParam.getName());
                }
                io.swagger.v3.oas.models.parameters.Parameter merged = new io.swagger.v3.oas.models.parameters.Parameter().schema((Schema)((Object)mergedSchema));
                this.processRequestBody(merged, operation, methodConsumes, classConsumes, operationParameters, new Annotation[0], null, jsonViewAnnotationForRequestBody, encoding);
            }
            if (!operationParameters.isEmpty()) {
                for (io.swagger.v3.oas.models.parameters.Parameter operationParameter : operationParameters) {
                    operation.addParametersItem(operationParameter);
                }
            }
            if (parentParameters != null) {
                for (io.swagger.v3.oas.models.parameters.Parameter parentParameter : parentParameters) {
                    operation.addParametersItem(parentParameter);
                }
            }
            if (subResource != null && !scannedResources.contains(subResource)) {
                scannedResources.add(subResource);
                this.read(subResource, operationPath, httpMethod, true, operation.getRequestBody(), operation.getResponses(), classTags, operation.getParameters(), scannedResources);
                scannedResources.remove(subResource);
                continue;
            }
            Iterator<OpenAPIExtension> chain = OpenAPIExtensions.chain();
            if (chain.hasNext()) {
                OpenAPIExtension extension = chain.next();
                extension.decorateOperation(operation, method2, chain);
            }
            PathItem pathItemObject = this.openAPI.getPaths() != null && this.openAPI.getPaths().get(operationPath) != null ? (PathItem)this.openAPI.getPaths().get(operationPath) : new PathItem();
            if (StringUtils.isBlank(httpMethod)) continue;
            this.setPathItemOperation(pathItemObject, httpMethod, operation);
            this.applyPathParamsPatterns(operation, regexMap);
            this.paths.addPathItem(operationPath, pathItemObject);
            if (this.openAPI.getPaths() != null) {
                this.paths.putAll(this.openAPI.getPaths());
            }
            this.openAPI.setPaths(this.paths);
        }
        if (webhooksAnnotation != null && webhooksAnnotation.value().length > 0) {
            HashMap<String, PathItem> webhooks = new HashMap<String, PathItem>();
            for (Webhook webhookAnnotation : webhooksAnnotation.value()) {
                Operation apiOperation = webhookAnnotation.operation();
                PathItem pathItemObject = new PathItem();
                io.swagger.v3.oas.models.Operation operation = new io.swagger.v3.oas.models.Operation();
                this.setOperationObjectFromApiOperationAnnotation(operation, apiOperation, null, classProduces, null, classConsumes, null);
                pathItemObject.post(operation);
                webhooks.put(webhookAnnotation.name(), pathItemObject);
            }
            if (!webhooks.isEmpty()) {
                this.openAPI.setWebhooks(webhooks);
            }
        }
        if (!this.isEmptyComponents(this.components) && this.openAPI.getComponents() == null) {
            this.openAPI.setComponents(this.components);
        }
        AnnotationsUtils.getTags(apiTags, true).ifPresent(tags -> this.openApiTags.addAll((Collection<io.swagger.v3.oas.models.tags.Tag>)tags));
        if (!this.openApiTags.isEmpty()) {
            LinkedHashSet<io.swagger.v3.oas.models.tags.Tag> tagsSet = new LinkedHashSet<io.swagger.v3.oas.models.tags.Tag>();
            if (this.openAPI.getTags() != null) {
                for (io.swagger.v3.oas.models.tags.Tag tag : this.openAPI.getTags()) {
                    if (!tagsSet.stream().noneMatch(t2 -> t2.getName().equals(tag.getName()))) continue;
                    tagsSet.add(tag);
                }
            }
            for (io.swagger.v3.oas.models.tags.Tag tag : this.openApiTags) {
                if (!tagsSet.stream().noneMatch(t2 -> t2.getName().equals(tag.getName()))) continue;
                tagsSet.add(tag);
            }
            this.openAPI.setTags(new ArrayList<io.swagger.v3.oas.models.tags.Tag>(tagsSet));
        }
        return this.openAPI;
    }

    protected void applyPathParamsPatterns(io.swagger.v3.oas.models.Operation operation, Map<String, String> patternsMap) {
        if (operation.getParameters() == null) {
            return;
        }
        operation.getParameters().stream().filter(p -> patternsMap.containsKey(p.getName())).filter(p -> "path".equals(p.getIn())).filter(p -> p.getSchema() != null).filter(p -> StringUtils.isBlank(p.getSchema().getPattern())).filter(p -> !Parameter.StyleEnum.MATRIX.equals((Object)p.getStyle())).filter(p -> "string".equals(p.getSchema().getType()) || p.getSchema().getTypes() != null && p.getSchema().getTypes().contains("string")).forEach(p -> p.getSchema().setPattern((String)patternsMap.get(p.getName())));
    }

    protected Content processContent(Content content, Schema<?> schema, Consumes methodConsumes, Consumes classConsumes) {
        if (content == null) {
            content = new Content();
        }
        if (methodConsumes != null) {
            for (String value : methodConsumes.value()) {
                this.setMediaTypeToContent(schema, content, value);
            }
        } else if (classConsumes != null) {
            for (String value : classConsumes.value()) {
                this.setMediaTypeToContent(schema, content, value);
            }
        } else {
            this.setMediaTypeToContent(schema, content, DEFAULT_MEDIA_TYPE_VALUE);
        }
        return content;
    }

    protected void processRequestBody(io.swagger.v3.oas.models.parameters.Parameter requestBodyParameter, io.swagger.v3.oas.models.Operation operation, Consumes methodConsumes, Consumes classConsumes, List<io.swagger.v3.oas.models.parameters.Parameter> operationParameters, Annotation[] paramAnnotations, Type type, JsonView jsonViewAnnotation, Map<String, Encoding> encoding) {
        Content content;
        RequestBody requestBody;
        io.swagger.v3.oas.annotations.parameters.RequestBody requestBodyAnnotation = this.getRequestBody(Arrays.asList(paramAnnotations));
        if (requestBodyAnnotation != null) {
            Optional<RequestBody> optionalRequestBody = OperationParser.getRequestBody(requestBodyAnnotation, classConsumes, methodConsumes, this.components, jsonViewAnnotation, this.config.isOpenAPI31());
            if (optionalRequestBody.isPresent()) {
                requestBody = optionalRequestBody.get();
                if (StringUtils.isBlank(requestBody.get$ref()) && (requestBody.getContent() == null || requestBody.getContent().isEmpty())) {
                    if (requestBodyParameter.getSchema() != null) {
                        content = this.processContent(requestBody.getContent(), requestBodyParameter.getSchema(), methodConsumes, classConsumes);
                        requestBody.setContent(content);
                    }
                } else if (StringUtils.isBlank(requestBody.get$ref()) && requestBody.getContent() != null && !requestBody.getContent().isEmpty() && requestBodyParameter.getSchema() != null) {
                    LinkedHashMap<String, MediaType> reresolvedMediaTypes = new LinkedHashMap<String, MediaType>();
                    for (String key : requestBody.getContent().keySet()) {
                        MediaType mediaType = (MediaType)requestBody.getContent().get(key);
                        if (mediaType.getSchema() == null) {
                            if (requestBodyParameter.getSchema() == null) {
                                mediaType.setSchema(new Schema());
                            } else {
                                mediaType.setSchema(requestBodyParameter.getSchema());
                            }
                        } else if (mediaType.getSchema() != null && requestBodyAnnotation.useParameterTypeSchema() && requestBodyParameter.getSchema() != null) {
                            MediaType newMediaType = this.clone(mediaType);
                            Schema parameterSchema = this.clone(requestBodyParameter.getSchema());
                            Optional<io.swagger.v3.oas.annotations.media.Content> content2 = Arrays.stream(requestBodyAnnotation.content()).filter(c -> c.mediaType().equals(key)).findFirst();
                            if (content2.isPresent()) {
                                Optional<Schema> reResolvedSchema = AnnotationsUtils.getSchemaFromAnnotation(content2.get().schema(), this.components, null, this.config.isOpenAPI31(), parameterSchema);
                                if (reResolvedSchema.isPresent()) {
                                    parameterSchema = reResolvedSchema.get();
                                }
                                if ((reResolvedSchema = AnnotationsUtils.getArraySchema(content2.get().array(), this.components, null, this.config.isOpenAPI31(), parameterSchema)).isPresent()) {
                                    parameterSchema = reResolvedSchema.get();
                                }
                            }
                            newMediaType.schema(parameterSchema);
                            reresolvedMediaTypes.put(key, newMediaType);
                        }
                        if (!StringUtils.isBlank(mediaType.getSchema().getType()) && !requestBodyAnnotation.useParameterTypeSchema()) continue;
                        mediaType.getSchema().setType(requestBodyParameter.getSchema().getType());
                    }
                    requestBody.getContent().putAll(reresolvedMediaTypes);
                }
                operation.setRequestBody(requestBody);
            }
        } else if (operation.getRequestBody() == null) {
            boolean isRequestBodyEmpty = true;
            requestBody = new RequestBody();
            if (StringUtils.isNotBlank(requestBodyParameter.get$ref())) {
                requestBody.set$ref(requestBodyParameter.get$ref());
                isRequestBodyEmpty = false;
            }
            if (StringUtils.isNotBlank(requestBodyParameter.getDescription())) {
                requestBody.setDescription(requestBodyParameter.getDescription());
                isRequestBodyEmpty = false;
            }
            if (Boolean.TRUE.equals(requestBodyParameter.getRequired())) {
                requestBody.setRequired(requestBodyParameter.getRequired());
                isRequestBodyEmpty = false;
            }
            if (requestBodyParameter.getSchema() != null) {
                content = this.processContent(null, requestBodyParameter.getSchema(), methodConsumes, classConsumes);
                requestBody.setContent(content);
                isRequestBodyEmpty = false;
            }
            if (!isRequestBodyEmpty) {
                operation.setRequestBody(requestBody);
            }
        }
        if (operation.getRequestBody() != null && operation.getRequestBody().getContent() != null && encoding != null && !encoding.isEmpty()) {
            Content content3 = operation.getRequestBody().getContent();
            for (String mediaKey : content3.keySet()) {
                if (!mediaKey.equals("application/x-www-form-urlencoded") && !mediaKey.equals("multipart/form-data")) continue;
                MediaType m4 = (MediaType)content3.get(mediaKey);
                m4.encoding(encoding);
            }
        }
    }

    private io.swagger.v3.oas.annotations.parameters.RequestBody getRequestBody(List<Annotation> annotations) {
        if (annotations == null) {
            return null;
        }
        for (Annotation a : annotations) {
            if (!(a instanceof io.swagger.v3.oas.annotations.parameters.RequestBody)) continue;
            return (io.swagger.v3.oas.annotations.parameters.RequestBody)a;
        }
        return null;
    }

    private void setMediaTypeToContent(Schema<?> schema, Content content, String value) {
        MediaType mediaTypeObject = new MediaType();
        mediaTypeObject.setSchema(schema);
        content.addMediaType(value, mediaTypeObject);
    }

    public io.swagger.v3.oas.models.Operation parseMethod(Method method2, List<io.swagger.v3.oas.models.parameters.Parameter> globalParameters, JsonView jsonViewAnnotation) {
        JavaType classType = TypeFactory.defaultInstance().constructType(method2.getDeclaringClass());
        return this.parseMethod(classType.getClass(), method2, globalParameters, null, null, null, null, new ArrayList<SecurityRequirement>(), Optional.empty(), new HashSet<String>(), new ArrayList<Server>(), false, null, null, jsonViewAnnotation, null, null);
    }

    public io.swagger.v3.oas.models.Operation parseMethod(Method method2, List<io.swagger.v3.oas.models.parameters.Parameter> globalParameters, Produces methodProduces, Produces classProduces, Consumes methodConsumes, Consumes classConsumes, List<SecurityRequirement> classSecurityRequirements, Optional<ExternalDocumentation> classExternalDocs, Set<String> classTags, List<Server> classServers, boolean isSubresource, RequestBody parentRequestBody, ApiResponses parentResponses, JsonView jsonViewAnnotation, io.swagger.v3.oas.annotations.responses.ApiResponse[] classResponses) {
        JavaType classType = TypeFactory.defaultInstance().constructType(method2.getDeclaringClass());
        return this.parseMethod(classType.getClass(), method2, globalParameters, methodProduces, classProduces, methodConsumes, classConsumes, classSecurityRequirements, classExternalDocs, classTags, classServers, isSubresource, parentRequestBody, parentResponses, jsonViewAnnotation, classResponses, null);
    }

    public io.swagger.v3.oas.models.Operation parseMethod(Method method2, List<io.swagger.v3.oas.models.parameters.Parameter> globalParameters, Produces methodProduces, Produces classProduces, Consumes methodConsumes, Consumes classConsumes, List<SecurityRequirement> classSecurityRequirements, Optional<ExternalDocumentation> classExternalDocs, Set<String> classTags, List<Server> classServers, boolean isSubresource, RequestBody parentRequestBody, ApiResponses parentResponses, JsonView jsonViewAnnotation, io.swagger.v3.oas.annotations.responses.ApiResponse[] classResponses, AnnotatedMethod annotatedMethod) {
        JavaType classType = TypeFactory.defaultInstance().constructType(method2.getDeclaringClass());
        return this.parseMethod(classType.getClass(), method2, globalParameters, methodProduces, classProduces, methodConsumes, classConsumes, classSecurityRequirements, classExternalDocs, classTags, classServers, isSubresource, parentRequestBody, parentResponses, jsonViewAnnotation, classResponses, annotatedMethod);
    }

    protected io.swagger.v3.oas.models.Operation parseMethod(Class<?> cls, Method method2, List<io.swagger.v3.oas.models.parameters.Parameter> globalParameters, Produces methodProduces, Produces classProduces, Consumes methodConsumes, Consumes classConsumes, List<SecurityRequirement> classSecurityRequirements, Optional<ExternalDocumentation> classExternalDocs, Set<String> classTags, List<Server> classServers, boolean isSubresource, RequestBody parentRequestBody, ApiResponses parentResponses, JsonView jsonViewAnnotation, io.swagger.v3.oas.annotations.responses.ApiResponse[] classResponses, AnnotatedMethod annotatedMethod) {
        Object requirementsObject;
        io.swagger.v3.oas.models.Operation operation = new io.swagger.v3.oas.models.Operation();
        Operation apiOperation = ReflectionUtils.getAnnotation(method2, Operation.class);
        List<io.swagger.v3.oas.annotations.security.SecurityRequirement> apiSecurity = ReflectionUtils.getRepeatableAnnotations(method2, io.swagger.v3.oas.annotations.security.SecurityRequirement.class);
        List<io.swagger.v3.oas.annotations.callbacks.Callback> apiCallbacks = ReflectionUtils.getRepeatableAnnotations(method2, io.swagger.v3.oas.annotations.callbacks.Callback.class);
        List<io.swagger.v3.oas.annotations.servers.Server> apiServers = ReflectionUtils.getRepeatableAnnotations(method2, io.swagger.v3.oas.annotations.servers.Server.class);
        List<Tag> apiTags = ReflectionUtils.getRepeatableAnnotations(method2, Tag.class);
        List<Parameter> apiParameters = ReflectionUtils.getRepeatableAnnotations(method2, Parameter.class);
        List<io.swagger.v3.oas.annotations.responses.ApiResponse> apiResponses = ReflectionUtils.getRepeatableAnnotations(method2, io.swagger.v3.oas.annotations.responses.ApiResponse.class);
        io.swagger.v3.oas.annotations.parameters.RequestBody apiRequestBody = ReflectionUtils.getAnnotation(method2, io.swagger.v3.oas.annotations.parameters.RequestBody.class);
        io.swagger.v3.oas.annotations.responses.ApiResponse[] operationApiResponses = new io.swagger.v3.oas.annotations.responses.ApiResponse[]{};
        if (apiOperation != null) {
            operationApiResponses = apiOperation.responses();
        }
        io.swagger.v3.oas.annotations.ExternalDocumentation apiExternalDocumentation = ReflectionUtils.getAnnotation(method2, io.swagger.v3.oas.annotations.ExternalDocumentation.class);
        LinkedHashMap<String, Callback> callbacks = new LinkedHashMap<String, Callback>();
        if (apiCallbacks != null) {
            for (io.swagger.v3.oas.annotations.callbacks.Callback callback : apiCallbacks) {
                Map<String, Callback> currentCallbacks = this.getCallbacks(callback, methodProduces, classProduces, methodConsumes, classConsumes, jsonViewAnnotation);
                callbacks.putAll(currentCallbacks);
            }
        }
        if (!callbacks.isEmpty()) {
            operation.setCallbacks(callbacks);
        }
        classSecurityRequirements.forEach(operation::addSecurityItem);
        if (apiSecurity != null && ((Optional)(requirementsObject = SecurityParser.getSecurityRequirements(apiSecurity.toArray(new io.swagger.v3.oas.annotations.security.SecurityRequirement[apiSecurity.size()])))).isPresent()) {
            ((Optional)requirementsObject).get().stream().filter(r -> operation.getSecurity() == null || !operation.getSecurity().contains(r)).forEach(operation::addSecurityItem);
        }
        if (classServers != null) {
            classServers.forEach(operation::addServersItem);
        }
        if (apiServers != null) {
            AnnotationsUtils.getServers(apiServers.toArray(new io.swagger.v3.oas.annotations.servers.Server[apiServers.size()])).ifPresent(servers -> servers.forEach(operation::addServersItem));
        }
        AnnotationsUtils.getExternalDocumentation(apiExternalDocumentation).ifPresent(operation::setExternalDocs);
        if (apiTags != null) {
            apiTags.stream().filter(t2 -> operation.getTags() == null || operation.getTags() != null && !operation.getTags().contains(t2.name())).map(Tag::name).forEach(operation::addTagsItem);
            AnnotationsUtils.getTags(apiTags.toArray(new Tag[apiTags.size()]), true).ifPresent(tags -> this.openApiTags.addAll((Collection<io.swagger.v3.oas.models.tags.Tag>)tags));
        }
        if (globalParameters != null) {
            for (io.swagger.v3.oas.models.parameters.Parameter parameter : globalParameters) {
                operation.addParametersItem(parameter);
            }
        }
        if (apiParameters != null) {
            this.getParametersListFromAnnotation(apiParameters.toArray(new Parameter[apiParameters.size()]), classConsumes, methodConsumes, operation, jsonViewAnnotation).ifPresent(p -> p.forEach(operation::addParametersItem));
        }
        if (apiRequestBody != null && operation.getRequestBody() == null) {
            OperationParser.getRequestBody(apiRequestBody, classConsumes, methodConsumes, this.components, jsonViewAnnotation, this.config.isOpenAPI31()).ifPresent(operation::setRequestBody);
        }
        if (StringUtils.isBlank(operation.getOperationId())) {
            operation.setOperationId(this.getOperationId(method2.getName()));
        }
        if (classResponses != null && classResponses.length > 0) {
            OperationParser.getApiResponses(classResponses, classProduces, methodProduces, this.components, jsonViewAnnotation, this.config.isOpenAPI31(), this.defaultResponseKey).ifPresent(responses -> {
                if (operation.getResponses() == null) {
                    operation.setResponses((ApiResponses)responses);
                } else {
                    responses.forEach(operation.getResponses()::addApiResponse);
                }
            });
        }
        if (apiOperation != null) {
            this.setOperationObjectFromApiOperationAnnotation(operation, apiOperation, methodProduces, classProduces, methodConsumes, classConsumes, jsonViewAnnotation);
        }
        if (apiResponses != null && !apiResponses.isEmpty()) {
            OperationParser.getApiResponses(apiResponses.toArray(new io.swagger.v3.oas.annotations.responses.ApiResponse[apiResponses.size()]), classProduces, methodProduces, this.components, jsonViewAnnotation, this.config.isOpenAPI31(), this.defaultResponseKey).ifPresent(responses -> {
                if (operation.getResponses() == null) {
                    operation.setResponses((ApiResponses)responses);
                } else {
                    responses.forEach(operation.getResponses()::addApiResponse);
                }
            });
        }
        if (classTags != null) {
            classTags.stream().filter(t2 -> operation.getTags() == null || operation.getTags() != null && !operation.getTags().contains(t2)).forEach(operation::addTagsItem);
        }
        if (operation.getExternalDocs() == null) {
            classExternalDocs.ifPresent(operation::setExternalDocs);
        }
        if (isSubresource && parentRequestBody != null) {
            if (operation.getRequestBody() == null) {
                operation.requestBody(parentRequestBody);
            } else {
                Content content = operation.getRequestBody().getContent();
                if (content == null) {
                    content = parentRequestBody.getContent();
                    operation.getRequestBody().setContent(content);
                } else if (parentRequestBody.getContent() != null) {
                    for (String parentMediaType : parentRequestBody.getContent().keySet()) {
                        if (content.get(parentMediaType) != null) continue;
                        content.addMediaType(parentMediaType, (MediaType)parentRequestBody.getContent().get(parentMediaType));
                    }
                }
            }
        }
        Type returnType = method2.getGenericReturnType();
        if (annotatedMethod != null && annotatedMethod.getType() != null) {
            returnType = this.extractTypeFromMethod(annotatedMethod);
        }
        Class<?> clazz = this.getSubResourceWithJaxRsSubresourceLocatorSpecs(method2);
        Schema returnTypeSchema = null;
        if (!this.shouldIgnoreClass(returnType.getTypeName()) && !method2.getGenericReturnType().equals(clazz)) {
            ResolvedSchema resolvedSchema = ModelConverters.getInstance(this.config.isOpenAPI31()).resolveAsResolvedSchema(new AnnotatedType(returnType).resolveAsRef(true).jsonViewAnnotation(jsonViewAnnotation).components(this.components));
            if (resolvedSchema.schema != null) {
                Map<String, Schema> schemaMap;
                returnTypeSchema = resolvedSchema.schema;
                Content content = new Content();
                MediaType mediaType = new MediaType().schema(returnTypeSchema);
                AnnotationsUtils.applyTypes(classProduces == null ? new String[]{} : classProduces.value(), methodProduces == null ? new String[]{} : methodProduces.value(), content, mediaType);
                if (operation.getResponses() == null) {
                    operation.responses(new ApiResponses().addApiResponse(this.defaultResponseKey, new ApiResponse().description(DEFAULT_DESCRIPTION).content(content)));
                }
                if (operation.getResponses().get(this.defaultResponseKey) != null && StringUtils.isBlank(((ApiResponse)operation.getResponses().get(this.defaultResponseKey)).get$ref())) {
                    if (((ApiResponse)operation.getResponses().get(this.defaultResponseKey)).getContent() == null) {
                        ((ApiResponse)operation.getResponses().get(this.defaultResponseKey)).content(content);
                    } else {
                        for (String key2 : ((ApiResponse)operation.getResponses().get(this.defaultResponseKey)).getContent().keySet()) {
                            if (((MediaType)((ApiResponse)operation.getResponses().get(this.defaultResponseKey)).getContent().get(key2)).getSchema() != null) continue;
                            ((MediaType)((ApiResponse)operation.getResponses().get(this.defaultResponseKey)).getContent().get(key2)).setSchema(returnTypeSchema);
                        }
                    }
                }
                if ((schemaMap = resolvedSchema.referencedSchemas) != null) {
                    schemaMap.forEach((key, schema) -> this.components.addSchemas((String)key, (Schema)schema));
                }
            }
        }
        if (operation.getResponses() == null || operation.getResponses().isEmpty()) {
            Content content = this.resolveEmptyContent(classProduces, methodProduces);
            ApiResponse apiResponseObject = new ApiResponse().description(DEFAULT_DESCRIPTION).content(content);
            operation.setResponses(new ApiResponses().addApiResponse(this.defaultResponseKey, apiResponseObject));
        }
        if (returnTypeSchema != null) {
            this.resolveResponseSchemaFromReturnType(operation, classResponses, returnTypeSchema, classProduces, methodProduces);
            if (apiResponses != null) {
                this.resolveResponseSchemaFromReturnType(operation, apiResponses.toArray(new io.swagger.v3.oas.annotations.responses.ApiResponse[0]), returnTypeSchema, classProduces, methodProduces);
            } else if (operationApiResponses != null && operationApiResponses.length > 0) {
                this.resolveResponseSchemaFromReturnType(operation, operationApiResponses, returnTypeSchema, classProduces, methodProduces);
            }
        }
        return operation;
    }

    private Type extractTypeFromMethod(AnnotatedMethod annotatedMethod) {
        if (CompletionStage.class.isAssignableFrom(annotatedMethod.getType().getRawClass())) {
            return annotatedMethod.getType().getBindings().getBoundType(0);
        }
        return annotatedMethod.getType();
    }

    protected Content resolveEmptyContent(Produces classProduces, Produces methodProduces) {
        Content content = new Content();
        MediaType mediaType = new MediaType();
        AnnotationsUtils.applyTypes(classProduces == null ? new String[]{} : classProduces.value(), methodProduces == null ? new String[]{} : methodProduces.value(), content, mediaType);
        return content;
    }

    private MediaType clone(MediaType mediaType) {
        if (mediaType == null) {
            return mediaType;
        }
        try {
            mediaType = this.config.isOpenAPI31().booleanValue() ? Json31.mapper().readValue(Json31.pretty(mediaType), MediaType.class) : Json.mapper().readValue(Json.pretty(mediaType), MediaType.class);
        }
        catch (IOException e) {
            LOGGER.error("Could not clone mediaType", e);
        }
        return mediaType;
    }

    private Schema<?> clone(Schema<?> schema) {
        return AnnotationsUtils.clone(schema, this.config.isOpenAPI31());
    }

    protected void resolveResponseSchemaFromReturnType(io.swagger.v3.oas.models.Operation operation, io.swagger.v3.oas.annotations.responses.ApiResponse[] responses, Schema<?> schema, Produces classProduces, Produces methodProduces) {
        if (responses != null) {
            for (io.swagger.v3.oas.annotations.responses.ApiResponse response : responses) {
                ApiResponse opResponse;
                LinkedHashMap<String, MediaType> reresolvedMediaTypes = new LinkedHashMap<String, MediaType>();
                if (!response.useReturnTypeSchema() || (opResponse = (ApiResponse)operation.getResponses().get(response.responseCode())) == null) continue;
                if (opResponse.getContent() != null) {
                    for (String key : opResponse.getContent().keySet()) {
                        MediaType mediaType = this.clone((MediaType)opResponse.getContent().get(key));
                        Schema existingSchema = this.clone(schema);
                        Optional<io.swagger.v3.oas.annotations.media.Content> content = Arrays.stream(response.content()).filter(c -> c.mediaType().equals(key)).findFirst();
                        if (content.isPresent()) {
                            Optional<Schema> reResolvedSchema = AnnotationsUtils.getSchemaFromAnnotation(content.get().schema(), this.components, null, this.config.isOpenAPI31(), existingSchema);
                            if (reResolvedSchema.isPresent()) {
                                existingSchema = reResolvedSchema.get();
                            }
                            if ((reResolvedSchema = AnnotationsUtils.getArraySchema(content.get().array(), this.components, null, this.config.isOpenAPI31(), existingSchema)).isPresent()) {
                                existingSchema = reResolvedSchema.get();
                            }
                        }
                        mediaType.schema(existingSchema);
                        reresolvedMediaTypes.put(key, mediaType);
                    }
                } else {
                    Content content = this.resolveEmptyContent(classProduces, methodProduces);
                    for (MediaType mediaType : content.values()) {
                        mediaType.schema(schema);
                    }
                    opResponse.content(content);
                }
                opResponse.getContent().putAll(reresolvedMediaTypes);
            }
        }
    }

    private boolean shouldIgnoreClass(String className) {
        if (StringUtils.isBlank(className)) {
            return true;
        }
        boolean ignore = false;
        String rawClassName = className;
        if (rawClassName.startsWith("[")) {
            rawClassName = className.replace("[simple type, class ", "");
            rawClassName = rawClassName.substring(0, rawClassName.length() - 1);
        }
        ignore = (ignore = rawClassName.startsWith("javax.ws.rs.")) || rawClassName.equalsIgnoreCase("void");
        ignore = ignore || ModelConverters.getInstance(this.config.isOpenAPI31()).isRegisteredAsSkippedClass(rawClassName);
        return ignore;
    }

    private Map<String, Callback> getCallbacks(io.swagger.v3.oas.annotations.callbacks.Callback apiCallback, Produces methodProduces, Produces classProduces, Consumes methodConsumes, Consumes classConsumes, JsonView jsonViewAnnotation) {
        HashMap<String, Callback> callbackMap = new HashMap<String, Callback>();
        if (apiCallback == null) {
            return callbackMap;
        }
        Callback callbackObject = new Callback();
        if (StringUtils.isNotBlank(apiCallback.ref())) {
            callbackObject.set$ref(apiCallback.ref());
            callbackMap.put(apiCallback.name(), callbackObject);
            return callbackMap;
        }
        PathItem pathItemObject = new PathItem();
        for (Operation callbackOperation : apiCallback.operation()) {
            io.swagger.v3.oas.models.Operation callbackNewOperation = new io.swagger.v3.oas.models.Operation();
            this.setOperationObjectFromApiOperationAnnotation(callbackNewOperation, callbackOperation, methodProduces, classProduces, methodConsumes, classConsumes, jsonViewAnnotation);
            this.setPathItemOperation(pathItemObject, callbackOperation.method(), callbackNewOperation);
        }
        callbackObject.addPathItem(apiCallback.callbackUrlExpression(), pathItemObject);
        callbackMap.put(apiCallback.name(), callbackObject);
        return callbackMap;
    }

    private void setPathItemOperation(PathItem pathItemObject, String method2, io.swagger.v3.oas.models.Operation operation) {
        switch (method2) {
            case "post": {
                pathItemObject.post(operation);
                break;
            }
            case "get": {
                pathItemObject.get(operation);
                break;
            }
            case "delete": {
                pathItemObject.delete(operation);
                break;
            }
            case "put": {
                pathItemObject.put(operation);
                break;
            }
            case "patch": {
                pathItemObject.patch(operation);
                break;
            }
            case "trace": {
                pathItemObject.trace(operation);
                break;
            }
            case "head": {
                pathItemObject.head(operation);
                break;
            }
            case "options": {
                pathItemObject.options(operation);
                break;
            }
        }
    }

    protected void setOperationObjectFromApiOperationAnnotation(io.swagger.v3.oas.models.Operation operation, Operation apiOperation, Produces methodProduces, Produces classProduces, Consumes methodConsumes, Consumes classConsumes, JsonView jsonViewAnnotation) {
        Map<String, Object> extensions;
        if (StringUtils.isNotBlank(apiOperation.summary())) {
            operation.setSummary(apiOperation.summary());
        }
        if (StringUtils.isNotBlank(apiOperation.description())) {
            operation.setDescription(apiOperation.description());
        }
        if (StringUtils.isNotBlank(apiOperation.operationId())) {
            operation.setOperationId(this.getOperationId(apiOperation.operationId()));
        }
        if (apiOperation.deprecated()) {
            operation.setDeprecated(apiOperation.deprecated());
        }
        boolean openapi31 = Boolean.TRUE.equals(this.config.isOpenAPI31());
        ReaderUtils.getStringListFromStringArray(apiOperation.tags()).ifPresent(tags -> tags.stream().filter(t2 -> operation.getTags() == null || operation.getTags() != null && !operation.getTags().contains(t2)).forEach(operation::addTagsItem));
        if (operation.getExternalDocs() == null) {
            AnnotationsUtils.getExternalDocumentation(apiOperation.externalDocs(), openapi31).ifPresent(operation::setExternalDocs);
        }
        OperationParser.getApiResponses(apiOperation.responses(), classProduces, methodProduces, this.components, jsonViewAnnotation, openapi31, this.defaultResponseKey).ifPresent(responses -> {
            if (operation.getResponses() == null) {
                operation.setResponses((ApiResponses)responses);
            } else {
                responses.forEach(operation.getResponses()::addApiResponse);
            }
        });
        AnnotationsUtils.getServers(apiOperation.servers()).ifPresent(servers -> servers.forEach(operation::addServersItem));
        this.getParametersListFromAnnotation(apiOperation.parameters(), classConsumes, methodConsumes, operation, jsonViewAnnotation).ifPresent(p -> p.forEach(operation::addParametersItem));
        Optional<List<SecurityRequirement>> requirementsObject = SecurityParser.getSecurityRequirements(apiOperation.security());
        if (requirementsObject.isPresent()) {
            requirementsObject.get().stream().filter(r -> operation.getSecurity() == null || !operation.getSecurity().contains(r)).forEach(operation::addSecurityItem);
        }
        if (apiOperation.requestBody() != null && operation.getRequestBody() == null) {
            OperationParser.getRequestBody(apiOperation.requestBody(), classConsumes, methodConsumes, this.components, jsonViewAnnotation, this.config.isOpenAPI31()).ifPresent(operation::setRequestBody);
        }
        if (apiOperation.extensions().length > 0 && (extensions = AnnotationsUtils.getExtensions(openapi31, apiOperation.extensions())) != null) {
            if (openapi31) {
                extensions.forEach(operation::addExtension31);
            } else {
                extensions.forEach(operation::addExtension);
            }
        }
    }

    protected String getOperationId(String operationId) {
        boolean operationIdUsed = this.existOperationId(operationId);
        String operationIdToFind = null;
        int counter = 0;
        while (operationIdUsed) {
            operationIdToFind = String.format("%s_%d", operationId, ++counter);
            operationIdUsed = this.existOperationId(operationIdToFind);
        }
        if (operationIdToFind != null) {
            operationId = operationIdToFind;
        }
        return operationId;
    }

    private boolean existOperationId(String operationId) {
        if (this.openAPI == null) {
            return false;
        }
        if (this.openAPI.getPaths() == null || this.openAPI.getPaths().isEmpty()) {
            return false;
        }
        for (PathItem path : this.openAPI.getPaths().values()) {
            Set<String> pathOperationIds = this.extractOperationIdFromPathItem(path);
            if (!pathOperationIds.contains(operationId)) continue;
            return true;
        }
        return false;
    }

    protected Optional<List<io.swagger.v3.oas.models.parameters.Parameter>> getParametersListFromAnnotation(Parameter[] parameters, Consumes classConsumes, Consumes methodConsumes, io.swagger.v3.oas.models.Operation operation, JsonView jsonViewAnnotation) {
        if (parameters == null) {
            return Optional.empty();
        }
        ArrayList<io.swagger.v3.oas.models.parameters.Parameter> parametersObject = new ArrayList<io.swagger.v3.oas.models.parameters.Parameter>();
        for (Parameter parameter : parameters) {
            ResolvedParameter resolvedParameter = this.getParameters(ParameterProcessor.getParameterType(parameter), Collections.singletonList(parameter), operation, classConsumes, methodConsumes, jsonViewAnnotation);
            parametersObject.addAll(resolvedParameter.parameters);
        }
        if (parametersObject.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(parametersObject);
    }

    protected ResolvedParameter getParameters(Type type, List<Annotation> annotations, io.swagger.v3.oas.models.Operation operation, Consumes classConsumes, Consumes methodConsumes, JsonView jsonViewAnnotation) {
        Iterator<OpenAPIExtension> chain = OpenAPIExtensions.chain();
        if (!chain.hasNext()) {
            return new ResolvedParameter();
        }
        LOGGER.debug("getParameters for {}", (Object)type);
        HashSet<Type> typesToSkip = new HashSet<Type>();
        OpenAPIExtension extension = chain.next();
        LOGGER.debug("trying extension {}", (Object)extension);
        extension.setOpenAPI31(Boolean.TRUE.equals(this.config.isOpenAPI31()));
        return extension.extractParameters(annotations, type, typesToSkip, this.components, classConsumes, methodConsumes, true, jsonViewAnnotation, chain);
    }

    private Set<String> extractOperationIdFromPathItem(PathItem path) {
        HashSet<String> ids = new HashSet<String>();
        if (path.getGet() != null && StringUtils.isNotBlank(path.getGet().getOperationId())) {
            ids.add(path.getGet().getOperationId());
        }
        if (path.getPost() != null && StringUtils.isNotBlank(path.getPost().getOperationId())) {
            ids.add(path.getPost().getOperationId());
        }
        if (path.getPut() != null && StringUtils.isNotBlank(path.getPut().getOperationId())) {
            ids.add(path.getPut().getOperationId());
        }
        if (path.getDelete() != null && StringUtils.isNotBlank(path.getDelete().getOperationId())) {
            ids.add(path.getDelete().getOperationId());
        }
        if (path.getOptions() != null && StringUtils.isNotBlank(path.getOptions().getOperationId())) {
            ids.add(path.getOptions().getOperationId());
        }
        if (path.getHead() != null && StringUtils.isNotBlank(path.getHead().getOperationId())) {
            ids.add(path.getHead().getOperationId());
        }
        if (path.getPatch() != null && StringUtils.isNotBlank(path.getPatch().getOperationId())) {
            ids.add(path.getPatch().getOperationId());
        }
        return ids;
    }

    private boolean isEmptyComponents(Components components) {
        if (components == null) {
            return true;
        }
        if (components.getSchemas() != null && !components.getSchemas().isEmpty()) {
            return false;
        }
        if (components.getSecuritySchemes() != null && !components.getSecuritySchemes().isEmpty()) {
            return false;
        }
        if (components.getCallbacks() != null && !components.getCallbacks().isEmpty()) {
            return false;
        }
        if (components.getExamples() != null && !components.getExamples().isEmpty()) {
            return false;
        }
        if (components.getExtensions() != null && !components.getExtensions().isEmpty()) {
            return false;
        }
        if (components.getHeaders() != null && !components.getHeaders().isEmpty()) {
            return false;
        }
        if (components.getLinks() != null && !components.getLinks().isEmpty()) {
            return false;
        }
        if (components.getParameters() != null && !components.getParameters().isEmpty()) {
            return false;
        }
        if (components.getRequestBodies() != null && !components.getRequestBodies().isEmpty()) {
            return false;
        }
        if (components.getResponses() != null && !components.getResponses().isEmpty()) {
            return false;
        }
        return components.getPathItems() == null || components.getPathItems().isEmpty();
    }

    protected boolean isOperationHidden(Method method2) {
        Operation apiOperation = ReflectionUtils.getAnnotation(method2, Operation.class);
        if (apiOperation != null && apiOperation.hidden()) {
            return true;
        }
        Hidden hidden = method2.getAnnotation(Hidden.class);
        if (hidden != null) {
            return true;
        }
        return this.config != null && !Boolean.TRUE.equals(this.config.isReadAllResources()) && apiOperation == null;
    }

    protected boolean isMethodOverridden(Method method2, Class<?> cls) {
        return ReflectionUtils.isOverriddenMethod(method2, cls);
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    protected boolean ignoreOperationPath(String path, String parentPath) {
        return false;
    }

    protected boolean ignoreOperationPathStrict(String path, String parentPath) {
        if (StringUtils.isBlank(path) && StringUtils.isBlank(parentPath)) {
            return true;
        }
        if (StringUtils.isNotBlank(path) && StringUtils.isBlank(parentPath)) {
            return false;
        }
        if (StringUtils.isBlank(path) && StringUtils.isNotBlank(parentPath)) {
            return false;
        }
        if (parentPath != null && !parentPath.isEmpty() && !"/".equals(parentPath)) {
            if (!parentPath.startsWith("/")) {
                parentPath = "/" + parentPath;
            }
            if (parentPath.endsWith("/")) {
                parentPath = parentPath.substring(0, parentPath.length() - 1);
            }
        }
        if (path != null && !path.isEmpty() && !"/".equals(path)) {
            if (!path.startsWith("/")) {
                path = "/" + path;
            }
            if (path.endsWith("/")) {
                path = path.substring(0, path.length() - 1);
            }
        }
        return path.equals(parentPath);
    }

    protected Class<?> getSubResourceWithJaxRsSubresourceLocatorSpecs(Method method2) {
        Class<?> type;
        Class<?> rawType = method2.getReturnType();
        if (Class.class.equals(rawType)) {
            type = Reader.getClassArgument(method2.getGenericReturnType());
            if (type == null) {
                return null;
            }
        } else {
            type = rawType;
        }
        if (method2.getAnnotation(Path.class) != null && ReaderUtils.extractOperationMethod(method2, null) == null) {
            return type;
        }
        return null;
    }

    private static Class<?> getClassArgument(Type cls) {
        if (cls instanceof ParameterizedType) {
            ParameterizedType parameterized = (ParameterizedType)cls;
            Type[] args2 = parameterized.getActualTypeArguments();
            if (args2.length != 1) {
                LOGGER.error("Unexpected class definition: {}", (Object)cls);
                return null;
            }
            Type first = args2[0];
            if (first instanceof Class) {
                return (Class)first;
            }
            return null;
        }
        LOGGER.error("Unknown class definition: {}", (Object)cls);
        return null;
    }

    private static class MethodComparator
    implements Comparator<Method> {
        private MethodComparator() {
        }

        @Override
        public int compare(Method m1, Method m22) {
            int val = m1.getName().compareTo(m22.getName());
            if (val == 0 && (val = m1.getParameterTypes().length - m22.getParameterTypes().length) == 0) {
                Class<?>[] types1 = m1.getParameterTypes();
                Class<?>[] types2 = m22.getParameterTypes();
                for (int i = 0; i < types1.length && (val = types1[i].getName().compareTo(types2[i].getName())) == 0; ++i) {
                }
            }
            return val;
        }
    }
}

