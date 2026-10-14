/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.jaxrs2.util;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.core.util.ParameterProcessor;
import io.swagger.v3.core.util.ReflectionUtils;
import io.swagger.v3.jaxrs2.ext.OpenAPIExtension;
import io.swagger.v3.jaxrs2.ext.OpenAPIExtensions;
import io.swagger.v3.oas.integration.api.OpenAPIConfiguration;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.parameters.Parameter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import org.apache.commons.lang3.StringUtils;

public class ReaderUtils {
    private static final String GET_METHOD = "get";
    private static final String POST_METHOD = "post";
    private static final String PUT_METHOD = "put";
    private static final String DELETE_METHOD = "delete";
    private static final String HEAD_METHOD = "head";
    private static final String OPTIONS_METHOD = "options";
    private static final String PATH_DELIMITER = "/";

    public static List<Parameter> collectConstructorParameters(Class<?> cls, Components components, Consumes classConsumes, JsonView jsonViewAnnotation) {
        if (cls.isLocalClass() || cls.isMemberClass() && !Modifier.isStatic(cls.getModifiers())) {
            return Collections.emptyList();
        }
        List<Parameter> selected = Collections.emptyList();
        int maxParamsCount = 0;
        for (Constructor<?> constructor : cls.getDeclaredConstructors()) {
            if (!ReflectionUtils.isConstructorCompatible(constructor) && !ReflectionUtils.isInject(Arrays.asList(constructor.getDeclaredAnnotations()))) continue;
            Type[] genericParameterTypes = constructor.getGenericParameterTypes();
            Annotation[][] annotations = constructor.getParameterAnnotations();
            int paramsCount = 0;
            ArrayList<Parameter> parameters = new ArrayList<Parameter>();
            for (int i = 0; i < genericParameterTypes.length; ++i) {
                List<Annotation> tmpAnnotations = Arrays.asList(annotations[i]);
                if (ReaderUtils.isContext(tmpAnnotations)) {
                    ++paramsCount;
                    continue;
                }
                Type genericParameterType = genericParameterTypes[i];
                List<Parameter> tmpParameters = ReaderUtils.collectParameters(genericParameterType, tmpAnnotations, components, classConsumes, jsonViewAnnotation);
                if (tmpParameters.isEmpty()) continue;
                for (Parameter tmpParameter : tmpParameters) {
                    Parameter processedParameter = ParameterProcessor.applyAnnotations(tmpParameter, genericParameterType, tmpAnnotations, components, classConsumes == null ? new String[]{} : classConsumes.value(), null, jsonViewAnnotation);
                    if (processedParameter == null) continue;
                    parameters.add(processedParameter);
                }
                ++paramsCount;
            }
            if (paramsCount < maxParamsCount) continue;
            maxParamsCount = paramsCount;
            selected = parameters;
        }
        return selected;
    }

    public static List<Parameter> collectFieldParameters(Class<?> cls, Components components, Consumes classConsumes, JsonView jsonViewAnnotation) {
        ArrayList<Parameter> parameters = new ArrayList<Parameter>();
        for (Field field2 : ReflectionUtils.getDeclaredFields(cls)) {
            List<Annotation> annotations = Arrays.asList(field2.getAnnotations());
            Type genericType = field2.getGenericType();
            parameters.addAll(ReaderUtils.collectParameters(genericType, annotations, components, classConsumes, jsonViewAnnotation));
        }
        return parameters;
    }

    private static List<Parameter> collectParameters(Type type, List<Annotation> annotations, Components components, Consumes classConsumes, JsonView jsonViewAnnotation) {
        Iterator<OpenAPIExtension> chain = OpenAPIExtensions.chain();
        return chain.hasNext() ? chain.next().extractParameters(annotations, (Type)type, new HashSet<Type>(), (Components)components, (Consumes)classConsumes, null, (boolean)false, (JsonView)jsonViewAnnotation, chain).parameters : Collections.emptyList();
    }

    private static boolean isContext(List<Annotation> annotations) {
        for (Annotation annotation : annotations) {
            if (!(annotation instanceof Context)) continue;
            return true;
        }
        return false;
    }

    public static Optional<List<String>> getStringListFromStringArray(String[] array) {
        if (array == null) {
            return Optional.empty();
        }
        ArrayList<String> list = new ArrayList<String>();
        boolean isEmpty = true;
        for (String value : array) {
            if (StringUtils.isNotBlank(value)) {
                isEmpty = false;
            }
            list.add(value);
        }
        if (isEmpty) {
            return Optional.empty();
        }
        return Optional.of(list);
    }

    public static boolean isIgnored(String path, OpenAPIConfiguration config) {
        if (config.getIgnoredRoutes() == null) {
            return false;
        }
        for (String item : config.getIgnoredRoutes()) {
            int length = item.length();
            if (!path.startsWith(item) || path.length() != length && !path.startsWith(PATH_DELIMITER, length)) continue;
            return true;
        }
        return false;
    }

    public static String getPath(Path classLevelPath, Path methodLevelPath, String parentPath, boolean isSubresource) {
        if (classLevelPath == null && methodLevelPath == null && StringUtils.isEmpty(parentPath)) {
            return null;
        }
        StringBuilder b = new StringBuilder();
        ReaderUtils.appendPathComponent(parentPath, b);
        if (classLevelPath != null && !isSubresource) {
            ReaderUtils.appendPathComponent(classLevelPath.value(), b);
        }
        if (methodLevelPath != null) {
            ReaderUtils.appendPathComponent(methodLevelPath.value(), b);
        }
        return b.length() == 0 ? PATH_DELIMITER : b.toString();
    }

    private static void appendPathComponent(String component, StringBuilder to) {
        if (component == null || component.isEmpty() || PATH_DELIMITER.equals(component)) {
            return;
        }
        if (!(component.startsWith(PATH_DELIMITER) || to.length() != 0 && '/' == to.charAt(to.length() - 1))) {
            to.append(PATH_DELIMITER);
        }
        if (component.endsWith(PATH_DELIMITER)) {
            to.append(component, 0, component.length() - 1);
        } else {
            to.append(component);
        }
    }

    public static String extractOperationMethod(Method method2, Iterator<OpenAPIExtension> chain) {
        if (method2.getAnnotation(GET.class) != null) {
            return GET_METHOD;
        }
        if (method2.getAnnotation(PUT.class) != null) {
            return PUT_METHOD;
        }
        if (method2.getAnnotation(POST.class) != null) {
            return POST_METHOD;
        }
        if (method2.getAnnotation(DELETE.class) != null) {
            return DELETE_METHOD;
        }
        if (method2.getAnnotation(OPTIONS.class) != null) {
            return OPTIONS_METHOD;
        }
        if (method2.getAnnotation(HEAD.class) != null) {
            return HEAD_METHOD;
        }
        if (method2.getAnnotation(HttpMethod.class) != null) {
            HttpMethod httpMethod = method2.getAnnotation(HttpMethod.class);
            return httpMethod.value().toLowerCase();
        }
        if (!StringUtils.isEmpty(ReaderUtils.getHttpMethodFromCustomAnnotations(method2))) {
            return ReaderUtils.getHttpMethodFromCustomAnnotations(method2);
        }
        if (ReflectionUtils.getOverriddenMethod(method2) != null) {
            return ReaderUtils.extractOperationMethod(ReflectionUtils.getOverriddenMethod(method2), chain);
        }
        if (chain != null && chain.hasNext()) {
            return chain.next().extractOperationMethod(method2, chain);
        }
        return null;
    }

    public static String getHttpMethodFromCustomAnnotations(Method method2) {
        for (Annotation methodAnnotation : method2.getAnnotations()) {
            HttpMethod httpMethod = methodAnnotation.annotationType().getAnnotation(HttpMethod.class);
            if (httpMethod == null) continue;
            return httpMethod.value().toLowerCase();
        }
        return null;
    }
}

