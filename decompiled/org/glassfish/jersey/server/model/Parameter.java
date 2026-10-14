/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import javax.ws.rs.Encoded;
import org.glassfish.jersey.internal.util.ReflectionHelper;
import org.glassfish.jersey.model.Parameter;
import org.glassfish.jersey.model.internal.spi.ParameterServiceProvider;
import org.glassfish.jersey.server.Uri;
import org.glassfish.jersey.server.model.ParamQualifier;

public class Parameter
extends org.glassfish.jersey.model.Parameter
implements AnnotatedElement {
    public static <PARAMETER extends org.glassfish.jersey.model.Parameter> PARAMETER create(Class concreteClass, Class declaringClass, boolean encodeByDefault, Class<?> rawType, Type type, Annotation[] annotations) {
        return Parameter.create(concreteClass, declaringClass, encodeByDefault, rawType, type, annotations, Parameter.class);
    }

    protected static <PARAMETER extends org.glassfish.jersey.model.Parameter> List<PARAMETER> createList(Class concreteClass, Class declaringClass, boolean keepEncoded, Class[] parameterTypes, Type[] genericParameterTypes, Annotation[][] parameterAnnotations, Class<?> parameterClass) {
        ArrayList<PARAMETER> parameters = new ArrayList<PARAMETER>(parameterTypes.length);
        for (int i = 0; i < parameterTypes.length; ++i) {
            PARAMETER parameter = Parameter.create(concreteClass, declaringClass, keepEncoded, parameterTypes[i], genericParameterTypes[i], parameterAnnotations[i]);
            if (null == parameter) {
                return Collections.emptyList();
            }
            parameters.add(parameter);
        }
        return parameters;
    }

    public static <PARAMETER extends org.glassfish.jersey.model.Parameter> List<PARAMETER> create(Class concreteClass, Class declaringClass, Constructor<?> ctor, boolean keepEncoded) {
        return Parameter.createList(concreteClass, declaringClass, ctor, keepEncoded, Parameter.class);
    }

    public static <PARAMETER extends org.glassfish.jersey.model.Parameter> List<PARAMETER> create(Class concreteClass, Class declaringClass, Method javaMethod, boolean keepEncoded) {
        return Parameter.createList(concreteClass, declaringClass, javaMethod, keepEncoded, Parameter.class);
    }

    public static Parameter overrideSource(Parameter original, Parameter.Source source2) {
        return new Parameter(original.getAnnotations(), original.getSourceAnnotation(), source2, source2.name(), original.getRawType(), original.getType(), original.isEncoded(), original.getDefaultValue());
    }

    protected Parameter(Annotation[] markers, Annotation marker, Parameter.Source source2, String sourceName, Class<?> rawType, Type type, boolean encoded, String defaultValue) {
        super(markers, marker, source2, sourceName, rawType, type, encoded, defaultValue);
    }

    @Override
    public boolean isQualified() {
        for (Annotation a : this.getAnnotations()) {
            if (!a.annotationType().isAnnotationPresent(ParamQualifier.class)) continue;
            return true;
        }
        return false;
    }

    public static class ServerParameterService
    implements ParameterServiceProvider {
        @Override
        public Map<Class, Parameter.ParamAnnotationHelper> getParameterAnnotationHelperMap() {
            WeakHashMap<Class, Parameter.ParamAnnotationHelper> m4 = new WeakHashMap<Class, Parameter.ParamAnnotationHelper>();
            m4.put(Uri.class, new Parameter.ParamAnnotationHelper<Uri>(){

                @Override
                public String getValueOf(Uri a) {
                    return a.value();
                }

                @Override
                public Parameter.Source getSource() {
                    return Parameter.Source.URI;
                }
            });
            return m4;
        }

        public Parameter.ParamCreationFactory<Parameter> getParameterCreationFactory() {
            return new Parameter.ParamCreationFactory<Parameter>(){

                @Override
                public boolean isFor(Class<?> clazz) {
                    return clazz == Parameter.class;
                }

                @Override
                public Parameter createParameter(Annotation[] markers, Annotation marker, Parameter.Source source2, String sourceName, Class<?> rawType, Type type, boolean encoded, String defaultValue) {
                    return new Parameter(markers, marker, source2, sourceName, rawType, type, encoded, defaultValue);
                }

                @Override
                public Parameter createBeanParameter(Annotation[] markers, Annotation marker, Parameter.Source source2, String sourceName, Class<?> rawType, Type type, boolean encoded, String defaultValue) {
                    return new BeanParameter(markers, marker, sourceName, rawType, type, encoded, defaultValue);
                }
            };
        }
    }

    public static class BeanParameter
    extends Parameter {
        private final Collection<Parameter> parameters;

        private BeanParameter(Annotation[] markers, Annotation marker, String sourceName, Class<?> rawType, Type type, boolean encoded, String defaultValue) {
            super(markers, marker, Parameter.Source.BEAN_PARAM, sourceName, rawType, type, encoded, defaultValue);
            LinkedList<Parameter> parameters = new LinkedList<Parameter>();
            for (Field field2 : AccessController.doPrivileged(ReflectionHelper.getDeclaredFieldsPA(rawType))) {
                if (field2.getDeclaredAnnotations().length <= 0) continue;
                Parameter beanParamParameter = (Parameter)Parameter.create(rawType, field2.getDeclaringClass(), field2.isAnnotationPresent(Encoded.class), field2.getType(), field2.getGenericType(), field2.getAnnotations());
                parameters.add(beanParamParameter);
            }
            for (AccessibleObject accessibleObject : AccessController.doPrivileged(ReflectionHelper.getDeclaredConstructorsPA(rawType))) {
                for (org.glassfish.jersey.model.Parameter parameter : Parameter.create(rawType, rawType, accessibleObject, false)) {
                    parameters.add((Parameter)parameter);
                }
            }
            this.parameters = Collections.unmodifiableCollection(parameters);
        }

        public Collection<Parameter> getParameters() {
            return this.parameters;
        }
    }
}

