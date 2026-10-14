/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.inject;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.function.Function;
import javax.inject.Provider;
import javax.ws.rs.Encoded;
import org.glassfish.jersey.internal.inject.Injectee;
import org.glassfish.jersey.internal.inject.InjectionResolver;
import org.glassfish.jersey.internal.util.ReflectionHelper;
import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.model.Parameter;
import org.glassfish.jersey.server.spi.internal.ValueParamProvider;

public class ParamInjectionResolver<A extends Annotation>
implements InjectionResolver<A> {
    private final ValueParamProvider valueParamProvider;
    private final Class<A> annotation;
    private final Provider<ContainerRequest> request;

    public ParamInjectionResolver(ValueParamProvider valueParamProvider, Class<A> annotation, Provider<ContainerRequest> request) {
        this.valueParamProvider = valueParamProvider;
        this.annotation = annotation;
        this.request = request;
    }

    @Override
    public Object resolve(Injectee injectee) {
        AnnotatedElement annotated = injectee.getParent();
        Annotation[] annotations = annotated.getClass().equals(Constructor.class) ? ((Constructor)annotated).getParameterAnnotations()[injectee.getPosition()] : annotated.getDeclaredAnnotations();
        Class<?> componentClass = injectee.getInjecteeClass();
        Type genericType = injectee.getRequiredType();
        Type targetGenericType = injectee.isFactory() ? ReflectionHelper.getTypeArgument(genericType, 0) : genericType;
        Class targetType = ReflectionHelper.erasure(targetGenericType);
        Parameter parameter = (Parameter)Parameter.create(componentClass, componentClass, this.hasEncodedAnnotation(injectee), targetType, targetGenericType, annotations);
        Function<ContainerRequest, ?> valueProvider = this.valueParamProvider.getValueProvider(parameter);
        if (valueProvider != null) {
            if (injectee.isFactory()) {
                return () -> valueProvider.apply(this.request.get());
            }
            return valueProvider.apply(this.request.get());
        }
        return null;
    }

    private boolean hasEncodedAnnotation(Injectee injectee) {
        AnnotatedElement element = injectee.getParent();
        boolean isConstructor = element instanceof Constructor;
        boolean isMethod = element instanceof Method;
        if (isConstructor || isMethod) {
            Annotation[] annotations = isMethod ? ((Method)element).getParameterAnnotations()[injectee.getPosition()] : ((Constructor)element).getParameterAnnotations()[injectee.getPosition()];
            for (Annotation annotation : annotations) {
                if (!annotation.annotationType().equals(Encoded.class)) continue;
                return true;
            }
        }
        if (element.isAnnotationPresent(Encoded.class)) {
            return true;
        }
        Class<?> clazz = injectee.getInjecteeClass();
        return clazz.isAnnotationPresent(Encoded.class);
    }

    @Override
    public boolean isConstructorParameterIndicator() {
        return true;
    }

    @Override
    public boolean isMethodParameterIndicator() {
        return false;
    }

    @Override
    public Class<A> getAnnotation() {
        return this.annotation;
    }
}

