/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.Encoded;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.NameBinding;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.internal.Errors;
import org.glassfish.jersey.internal.util.Producer;
import org.glassfish.jersey.internal.util.ReflectionHelper;
import org.glassfish.jersey.internal.util.Tokenizer;
import org.glassfish.jersey.model.Parameter;
import org.glassfish.jersey.server.ManagedAsync;
import org.glassfish.jersey.server.internal.LocalizationMessages;
import org.glassfish.jersey.server.model.AnnotatedMethod;
import org.glassfish.jersey.server.model.ExtendedResource;
import org.glassfish.jersey.server.model.InvocableValidator;
import org.glassfish.jersey.server.model.MethodList;
import org.glassfish.jersey.server.model.Parameter;
import org.glassfish.jersey.server.model.Resource;
import org.glassfish.jersey.server.model.ResourceMethod;
import org.glassfish.jersey.server.model.ResourceMethodValidator;
import org.glassfish.jersey.server.model.internal.ModelHelper;
import org.glassfish.jersey.server.model.internal.SseTypeResolver;

final class IntrospectionModeller {
    private static final Logger LOGGER = Logger.getLogger(IntrospectionModeller.class.getName());
    private final Class<?> handlerClass;
    private final boolean disableValidation;

    public IntrospectionModeller(Class<?> handlerClass, boolean disableValidation) {
        this.handlerClass = handlerClass;
        this.disableValidation = disableValidation;
    }

    public Resource.Builder createResourceBuilder() {
        return Errors.processWithException(new Producer<Resource.Builder>(){

            @Override
            public Resource.Builder call() {
                return IntrospectionModeller.this.doCreateResourceBuilder();
            }
        });
    }

    private Resource.Builder doCreateResourceBuilder() {
        if (!this.disableValidation) {
            this.checkForNonPublicMethodIssues();
        }
        Class<?> annotatedResourceClass = ModelHelper.getAnnotatedResourceClass(this.handlerClass);
        Path rPathAnnotation = annotatedResourceClass.getAnnotation(Path.class);
        boolean keepEncodedParams = null != annotatedResourceClass.getAnnotation(Encoded.class);
        List<MediaType> defaultConsumedTypes = IntrospectionModeller.extractMediaTypes(annotatedResourceClass.getAnnotation(Consumes.class));
        List<MediaType> defaultProducedTypes = IntrospectionModeller.extractMediaTypes(annotatedResourceClass.getAnnotation(Produces.class));
        Collection<Class<? extends Annotation>> defaultNameBindings = ReflectionHelper.getAnnotationTypes(annotatedResourceClass, NameBinding.class);
        MethodList methodList = new MethodList(this.handlerClass);
        LinkedList<Parameter> resourceClassParameters = new LinkedList<Parameter>();
        this.checkResourceClassSetters(methodList, keepEncodedParams, resourceClassParameters);
        this.checkResourceClassFields(keepEncodedParams, InvocableValidator.isSingleton(this.handlerClass), resourceClassParameters);
        Resource.Builder resourceBuilder = null != rPathAnnotation ? Resource.builder(rPathAnnotation.value()) : Resource.builder();
        boolean extended = false;
        if (this.handlerClass.isAnnotationPresent(ExtendedResource.class)) {
            resourceBuilder.extended(true);
            extended = true;
        }
        resourceBuilder.name(this.handlerClass.getName());
        this.addResourceMethods(resourceBuilder, methodList, resourceClassParameters, keepEncodedParams, defaultConsumedTypes, defaultProducedTypes, defaultNameBindings, extended);
        this.addSubResourceMethods(resourceBuilder, methodList, resourceClassParameters, keepEncodedParams, defaultConsumedTypes, defaultProducedTypes, defaultNameBindings, extended);
        this.addSubResourceLocators(resourceBuilder, methodList, resourceClassParameters, keepEncodedParams, extended);
        if (LOGGER.isLoggable(Level.FINEST)) {
            LOGGER.finest(LocalizationMessages.NEW_AR_CREATED_BY_INTROSPECTION_MODELER(resourceBuilder.toString()));
        }
        return resourceBuilder;
    }

    private void checkForNonPublicMethodIssues() {
        MethodList allDeclaredMethods = new MethodList(this.getAllDeclaredMethods(this.handlerClass));
        for (AnnotatedMethod m4 : allDeclaredMethods.withMetaAnnotation(HttpMethod.class).withoutAnnotation(Path.class).isNotPublic()) {
            Errors.warning(this.handlerClass, LocalizationMessages.NON_PUB_RES_METHOD(m4.getMethod().toGenericString()));
        }
        for (AnnotatedMethod m4 : allDeclaredMethods.withMetaAnnotation(HttpMethod.class).withAnnotation(Path.class).isNotPublic()) {
            Errors.warning(this.handlerClass, LocalizationMessages.NON_PUB_SUB_RES_METHOD(m4.getMethod().toGenericString()));
        }
        for (AnnotatedMethod m4 : allDeclaredMethods.withoutMetaAnnotation(HttpMethod.class).withAnnotation(Path.class).isNotPublic()) {
            Errors.warning(this.handlerClass, LocalizationMessages.NON_PUB_SUB_RES_LOC(m4.getMethod().toGenericString()));
        }
    }

    private void checkResourceClassSetters(MethodList methodList, boolean encodedFlag, Collection<Parameter> injectableParameters) {
        for (AnnotatedMethod method2 : methodList.withoutMetaAnnotation(HttpMethod.class).withoutAnnotation(Path.class).hasNumParams(1).hasReturnType(Void.TYPE).nameStartsWith("set")) {
            Parameter p = (Parameter)Parameter.create(this.handlerClass, method2.getMethod().getDeclaringClass(), encodedFlag || method2.isAnnotationPresent(Encoded.class), method2.getParameterTypes()[0], method2.getGenericParameterTypes()[0], method2.getAnnotations());
            if (null == p) continue;
            if (!this.disableValidation) {
                ResourceMethodValidator.validateParameter(p, method2.getMethod(), method2.getMethod().toGenericString(), "1", InvocableValidator.isSingleton(this.handlerClass));
            }
            if (p.getSource() == Parameter.Source.ENTITY) continue;
            injectableParameters.add(p);
        }
    }

    private void checkResourceClassFields(boolean encodedFlag, boolean isInSingleton, Collection<Parameter> injectableParameters) {
        for (Field field2 : AccessController.doPrivileged(ReflectionHelper.getDeclaredFieldsPA(this.handlerClass))) {
            Parameter p;
            if (field2.getDeclaredAnnotations().length <= 0 || null == (p = (Parameter)Parameter.create(this.handlerClass, field2.getDeclaringClass(), encodedFlag || field2.isAnnotationPresent(Encoded.class), field2.getType(), field2.getGenericType(), field2.getAnnotations()))) continue;
            if (!this.disableValidation) {
                ResourceMethodValidator.validateParameter(p, field2, field2.toGenericString(), field2.getName(), isInSingleton);
            }
            if (p.getSource() == Parameter.Source.ENTITY) continue;
            injectableParameters.add(p);
        }
    }

    private List<Method> getAllDeclaredMethods(final Class<?> clazz) {
        final LinkedList<Method> result = new LinkedList<Method>();
        AccessController.doPrivileged(new PrivilegedAction<Object>(){

            @Override
            public Object run() {
                for (Class current = clazz; current != Object.class && current != null; current = current.getSuperclass()) {
                    result.addAll(Arrays.asList(current.getDeclaredMethods()));
                }
                return null;
            }
        });
        return result;
    }

    private static List<MediaType> resolveConsumedTypes(AnnotatedMethod am, List<MediaType> defaultConsumedTypes) {
        if (am.isAnnotationPresent(Consumes.class)) {
            return IntrospectionModeller.extractMediaTypes(am.getAnnotation(Consumes.class));
        }
        return defaultConsumedTypes;
    }

    private static List<MediaType> resolveProducedTypes(AnnotatedMethod am, List<MediaType> defaultProducedTypes) {
        if (am.isAnnotationPresent(Produces.class)) {
            return IntrospectionModeller.extractMediaTypes(am.getAnnotation(Produces.class));
        }
        return defaultProducedTypes;
    }

    private static List<MediaType> extractMediaTypes(Consumes annotation) {
        return annotation != null ? IntrospectionModeller.extractMediaTypes(annotation.value()) : Collections.emptyList();
    }

    private static List<MediaType> extractMediaTypes(Produces annotation) {
        return annotation != null ? IntrospectionModeller.extractMediaTypes(annotation.value()) : Collections.emptyList();
    }

    private static List<MediaType> extractMediaTypes(String[] values2) {
        if (values2.length == 0) {
            return Collections.emptyList();
        }
        ArrayList<MediaType> types = new ArrayList<MediaType>(values2.length);
        for (String mtEntry : values2) {
            for (String mt : Tokenizer.tokenize(mtEntry, ",")) {
                types.add(MediaType.valueOf(mt));
            }
        }
        return types;
    }

    private static void introspectAsyncFeatures(AnnotatedMethod am, ResourceMethod.Builder resourceMethodBuilder) {
        if (am.isAnnotationPresent(ManagedAsync.class)) {
            resourceMethodBuilder.managedAsync();
        }
        Annotation[][] annotationArray = am.getParameterAnnotations();
        int n = annotationArray.length;
        for (int i = 0; i < n; ++i) {
            Annotation[] annotationArray2;
            for (Annotation annotation : annotationArray2 = annotationArray[i]) {
                if (annotation.annotationType() != Suspended.class) continue;
                resourceMethodBuilder.suspended(0L, TimeUnit.MILLISECONDS);
            }
        }
        for (Annotation[] annotationArray3 : am.getParameterTypes()) {
            if (!SseTypeResolver.isSseSinkParam(annotationArray3)) continue;
            resourceMethodBuilder.sse();
        }
    }

    private void addResourceMethods(Resource.Builder resourceBuilder, MethodList methodList, List<Parameter> resourceClassParameters, boolean encodedParameters, List<MediaType> defaultConsumedTypes, List<MediaType> defaultProducedTypes, Collection<Class<? extends Annotation>> defaultNameBindings, boolean extended) {
        for (AnnotatedMethod am : methodList.withMetaAnnotation(HttpMethod.class).withoutAnnotation(Path.class)) {
            ResourceMethod.Builder methodBuilder = resourceBuilder.addMethod(am.getMetaMethodAnnotations(HttpMethod.class).get(0).value()).consumes(IntrospectionModeller.resolveConsumedTypes(am, defaultConsumedTypes)).produces(IntrospectionModeller.resolveProducedTypes(am, defaultProducedTypes)).encodedParameters(encodedParameters || am.isAnnotationPresent(Encoded.class)).nameBindings(defaultNameBindings).nameBindings(am.getAnnotations()).handledBy(this.handlerClass, am.getMethod()).handlingMethod(am.getDeclaredMethod()).handlerParameters(resourceClassParameters).extended(extended || am.isAnnotationPresent(ExtendedResource.class));
            IntrospectionModeller.introspectAsyncFeatures(am, methodBuilder);
        }
    }

    private void addSubResourceMethods(Resource.Builder resourceBuilder, MethodList methodList, List<Parameter> resourceClassParameters, boolean encodedParameters, List<MediaType> defaultConsumedTypes, List<MediaType> defaultProducedTypes, Collection<Class<? extends Annotation>> defaultNameBindings, boolean extended) {
        for (AnnotatedMethod am : methodList.withMetaAnnotation(HttpMethod.class).withAnnotation(Path.class)) {
            Resource.Builder childResourceBuilder = resourceBuilder.addChildResource(am.getAnnotation(Path.class).value());
            ResourceMethod.Builder methodBuilder = childResourceBuilder.addMethod(am.getMetaMethodAnnotations(HttpMethod.class).get(0).value()).consumes(IntrospectionModeller.resolveConsumedTypes(am, defaultConsumedTypes)).produces(IntrospectionModeller.resolveProducedTypes(am, defaultProducedTypes)).encodedParameters(encodedParameters || am.isAnnotationPresent(Encoded.class)).nameBindings(defaultNameBindings).nameBindings(am.getAnnotations()).handledBy(this.handlerClass, am.getMethod()).handlingMethod(am.getDeclaredMethod()).handlerParameters(resourceClassParameters).extended(extended || am.isAnnotationPresent(ExtendedResource.class));
            IntrospectionModeller.introspectAsyncFeatures(am, methodBuilder);
        }
    }

    private void addSubResourceLocators(Resource.Builder resourceBuilder, MethodList methodList, List<Parameter> resourceClassParameters, boolean encodedParameters, boolean extended) {
        for (AnnotatedMethod am : methodList.withoutMetaAnnotation(HttpMethod.class).withAnnotation(Path.class)) {
            String path = am.getAnnotation(Path.class).value();
            Resource.Builder builder = resourceBuilder;
            if (path != null && !path.isEmpty() && !"/".equals(path)) {
                builder = resourceBuilder.addChildResource(path);
            }
            builder.addMethod().encodedParameters(encodedParameters || am.isAnnotationPresent(Encoded.class)).handledBy(this.handlerClass, am.getMethod()).handlingMethod(am.getDeclaredMethod()).handlerParameters(resourceClassParameters).extended(extended || am.isAnnotationPresent(ExtendedResource.class));
        }
    }
}

