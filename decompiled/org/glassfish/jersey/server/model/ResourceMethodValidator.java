/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.ws.rs.BeanParam;
import javax.ws.rs.CookieParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import org.glassfish.jersey.internal.Errors;
import org.glassfish.jersey.model.Parameter;
import org.glassfish.jersey.server.internal.LocalizationMessages;
import org.glassfish.jersey.server.model.AbstractResourceModelVisitor;
import org.glassfish.jersey.server.model.Invocable;
import org.glassfish.jersey.server.model.Parameter;
import org.glassfish.jersey.server.model.ResourceMethod;
import org.glassfish.jersey.server.model.internal.SseTypeResolver;
import org.glassfish.jersey.server.spi.internal.ParamValueFactoryWithSource;
import org.glassfish.jersey.server.spi.internal.ParameterValueHelper;
import org.glassfish.jersey.server.spi.internal.ValueParamProvider;

class ResourceMethodValidator
extends AbstractResourceModelVisitor {
    private final Collection<ValueParamProvider> valueParamProviders;
    private static final Set<Class> PARAM_ANNOTATION_SET = ResourceMethodValidator.createParamAnnotationSet();

    ResourceMethodValidator(Collection<ValueParamProvider> valueParamProviders) {
        this.valueParamProviders = valueParamProviders;
    }

    @Override
    public void visitResourceMethod(ResourceMethod method2) {
        switch (method2.getType()) {
            case RESOURCE_METHOD: {
                this.visitJaxrsResourceMethod(method2);
                break;
            }
            case SUB_RESOURCE_LOCATOR: {
                this.visitSubResourceLocator(method2);
            }
        }
    }

    private void visitJaxrsResourceMethod(ResourceMethod method2) {
        this.checkMethod(method2);
    }

    private void checkMethod(ResourceMethod method2) {
        String path;
        Path pathAnnotation;
        Type responseType;
        this.checkValueProviders(method2);
        Invocable invocable = method2.getInvocable();
        this.checkParameters(method2);
        if ("GET".equals(method2.getHttpMethod())) {
            int isSse;
            long eventSinkCount = invocable.getParameters().stream().filter(parameter -> SseTypeResolver.isSseSinkParam(parameter.getRawType())).count();
            int n = isSse = eventSinkCount > 0L ? 1 : 0;
            if (eventSinkCount > 1L) {
                Errors.warning(method2, LocalizationMessages.MULTIPLE_EVENT_SINK_INJECTION(invocable.getHandlingMethod()));
            }
            if (Void.TYPE == invocable.getHandlingMethod().getReturnType() && !method2.isSuspendDeclared() && isSse == 0) {
                Errors.hint(method2, LocalizationMessages.GET_RETURNS_VOID(invocable.getHandlingMethod()));
            }
            if (invocable.requiresEntity() && !invocable.isInflector()) {
                Errors.warning(method2, LocalizationMessages.GET_CONSUMES_ENTITY(invocable.getHandlingMethod()));
            }
            for (Parameter p : invocable.getParameters()) {
                if (!p.isAnnotationPresent(FormParam.class)) continue;
                Errors.fatal(method2, LocalizationMessages.GET_CONSUMES_FORM_PARAM(invocable.getHandlingMethod()));
                break;
            }
            if (isSse != 0 && Void.TYPE != invocable.getHandlingMethod().getReturnType()) {
                Errors.fatal(method2, LocalizationMessages.EVENT_SINK_RETURNS_TYPE(invocable.getHandlingMethod()));
            }
        }
        LinkedList<String> httpMethodAnnotations = new LinkedList<String>();
        for (Annotation a : invocable.getHandlingMethod().getDeclaredAnnotations()) {
            if (null == a.annotationType().getAnnotation(HttpMethod.class)) continue;
            httpMethodAnnotations.add(a.toString());
        }
        if (httpMethodAnnotations.size() > 1) {
            Errors.fatal(method2, LocalizationMessages.MULTIPLE_HTTP_METHOD_DESIGNATORS(invocable.getHandlingMethod(), ((Object)httpMethodAnnotations).toString()));
        }
        if (!ResourceMethodValidator.isConcreteType(responseType = invocable.getResponseType())) {
            Errors.warning(invocable.getHandlingMethod(), LocalizationMessages.TYPE_OF_METHOD_NOT_RESOLVABLE_TO_CONCRETE_TYPE(responseType, invocable.getHandlingMethod().toGenericString()));
        }
        if ((pathAnnotation = invocable.getHandlingMethod().getAnnotation(Path.class)) != null && ((path = pathAnnotation.value()) == null || path.isEmpty() || "/".equals(path))) {
            Errors.warning(invocable.getHandlingMethod(), LocalizationMessages.METHOD_EMPTY_PATH_ANNOTATION(invocable.getHandlingMethod().getName(), invocable.getHandler().getHandlerClass().getName()));
        }
        if (httpMethodAnnotations.size() != 0) {
            this.checkUnexpectedAnnotations(method2);
        }
    }

    private void checkUnexpectedAnnotations(ResourceMethod resourceMethod) {
        Invocable invocable = resourceMethod.getInvocable();
        for (Annotation annotation : invocable.getHandlingMethod().getDeclaredAnnotations()) {
            if (!PARAM_ANNOTATION_SET.contains(annotation.annotationType())) continue;
            Errors.fatal(resourceMethod, LocalizationMessages.METHOD_UNEXPECTED_ANNOTATION(invocable.getHandlingMethod().getName(), invocable.getHandler().getHandlerClass().getName(), annotation.annotationType().getName()));
        }
    }

    private void checkValueProviders(ResourceMethod method2) {
        List<ParamValueFactoryWithSource<?>> valueProviders = ParameterValueHelper.createValueProviders(this.valueParamProviders, method2.getInvocable());
        if (valueProviders.contains(null)) {
            int index = valueProviders.indexOf(null);
            Errors.fatal(method2, LocalizationMessages.ERROR_PARAMETER_MISSING_VALUE_PROVIDER(index, method2.getInvocable().getHandlingMethod()));
        }
    }

    private void visitSubResourceLocator(ResourceMethod locator) {
        this.checkParameters(locator);
        this.checkValueProviders(locator);
        Invocable invocable = locator.getInvocable();
        if (Void.TYPE == invocable.getRawResponseType()) {
            Errors.fatal(locator, LocalizationMessages.SUBRES_LOC_RETURNS_VOID(invocable.getHandlingMethod()));
        }
        if (invocable.getHandlingMethod().getAnnotation(Path.class) != null) {
            this.checkUnexpectedAnnotations(locator);
        }
    }

    private void checkParameters(ResourceMethod method2) {
        Invocable invocable = method2.getInvocable();
        Method handlingMethod = invocable.getHandlingMethod();
        int paramCount = 0;
        int nonAnnotatedParameters = 0;
        for (Parameter p : invocable.getParameters()) {
            ResourceMethodValidator.validateParameter(p, handlingMethod, handlingMethod.toGenericString(), Integer.toString(++paramCount), false);
            if (method2.getType() == ResourceMethod.JaxrsType.SUB_RESOURCE_LOCATOR && Parameter.Source.ENTITY == p.getSource()) {
                Errors.fatal(method2, LocalizationMessages.SUBRES_LOC_HAS_ENTITY_PARAM(invocable.getHandlingMethod()));
                continue;
            }
            if (p.getAnnotations().length != 0 || ++nonAnnotatedParameters <= 1) continue;
            Errors.fatal(method2, LocalizationMessages.AMBIGUOUS_NON_ANNOTATED_PARAMETER(invocable.getHandlingMethod(), invocable.getHandlingMethod().getDeclaringClass()));
        }
    }

    private boolean isSseInjected(Invocable invocable) {
        return invocable.getParameters().stream().anyMatch(parameter -> SseTypeResolver.isSseSinkParam(parameter.getRawType()));
    }

    private static Set<Class> createParamAnnotationSet() {
        HashSet<Class> set = new HashSet<Class>(6);
        set.add(HeaderParam.class);
        set.add(CookieParam.class);
        set.add(MatrixParam.class);
        set.add(QueryParam.class);
        set.add(PathParam.class);
        set.add(BeanParam.class);
        return Collections.unmodifiableSet(set);
    }

    static void validateParameter(final Parameter parameter, final Object source2, final String reportedSourceName, final String reportedParameterName, final boolean injectionsForbidden) {
        Errors.processWithException(new Runnable(){

            @Override
            public void run() {
                Type paramType;
                Annotation[] annotations;
                int counter = 0;
                for (Annotation a : annotations = parameter.getAnnotations()) {
                    if (!PARAM_ANNOTATION_SET.contains(a.annotationType())) continue;
                    if (injectionsForbidden) {
                        Errors.fatal(source2, LocalizationMessages.SINGLETON_INJECTS_PARAMETER(reportedSourceName, reportedParameterName));
                        break;
                    }
                    if (++counter <= 1) continue;
                    Errors.warning(source2, LocalizationMessages.AMBIGUOUS_PARAMETER(reportedSourceName, reportedParameterName));
                    break;
                }
                if (!ResourceMethodValidator.isConcreteType(paramType = parameter.getType())) {
                    Errors.warning(source2, LocalizationMessages.PARAMETER_UNRESOLVABLE(reportedParameterName, paramType, reportedSourceName));
                }
            }
        });
    }

    private static boolean isConcreteType(Type t2) {
        if (t2 instanceof ParameterizedType) {
            return ResourceMethodValidator.isConcreteParameterizedType((ParameterizedType)t2);
        }
        return t2 instanceof Class;
    }

    private static boolean isConcreteParameterizedType(ParameterizedType pt) {
        boolean isConcrete = true;
        for (Type t2 : pt.getActualTypeArguments()) {
            isConcrete &= ResourceMethodValidator.isConcreteType(t2);
        }
        return isConcrete;
    }
}

