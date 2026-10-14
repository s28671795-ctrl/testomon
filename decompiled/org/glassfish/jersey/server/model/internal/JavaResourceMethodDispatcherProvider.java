/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.model.internal;

import java.io.Flushable;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.sse.SseEventSink;
import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.internal.inject.ConfiguredValidator;
import org.glassfish.jersey.server.model.Invocable;
import org.glassfish.jersey.server.model.Parameter;
import org.glassfish.jersey.server.model.internal.AbstractJavaResourceMethodDispatcher;
import org.glassfish.jersey.server.model.internal.SseTypeResolver;
import org.glassfish.jersey.server.spi.internal.ParamValueFactoryWithSource;
import org.glassfish.jersey.server.spi.internal.ParameterValueHelper;
import org.glassfish.jersey.server.spi.internal.ResourceMethodDispatcher;
import org.glassfish.jersey.server.spi.internal.ValueParamProvider;

class JavaResourceMethodDispatcherProvider
implements ResourceMethodDispatcher.Provider {
    private final Collection<ValueParamProvider> allValueProviders;

    JavaResourceMethodDispatcherProvider(Collection<ValueParamProvider> allValueProviders) {
        this.allValueProviders = allValueProviders;
    }

    @Override
    public ResourceMethodDispatcher create(Invocable resourceMethod, InvocationHandler invocationHandler, ConfiguredValidator validator) {
        List<ParamValueFactoryWithSource<?>> valueProviders = ParameterValueHelper.createValueProviders(this.allValueProviders, resourceMethod);
        Class<?> returnType = resourceMethod.getHandlingMethod().getReturnType();
        AbstractMethodParamInvoker resourceMethodDispatcher = null;
        if (Response.class.isAssignableFrom(returnType)) {
            resourceMethodDispatcher = new ResponseOutInvoker(resourceMethod, invocationHandler, valueProviders, validator);
        } else if (returnType != Void.TYPE) {
            resourceMethodDispatcher = returnType == Object.class || GenericEntity.class.isAssignableFrom(returnType) ? new ObjectOutInvoker(resourceMethod, invocationHandler, valueProviders, validator) : new TypeOutInvoker(resourceMethod, invocationHandler, valueProviders, validator);
        } else {
            int i = 0;
            for (Parameter parameter : resourceMethod.getParameters()) {
                if (SseTypeResolver.isSseSinkParam(parameter.getRawType())) {
                    resourceMethodDispatcher = new SseEventSinkInvoker(resourceMethod, invocationHandler, valueProviders, validator, i);
                    break;
                }
                ++i;
            }
            if (resourceMethodDispatcher == null) {
                resourceMethodDispatcher = new VoidOutInvoker(resourceMethod, invocationHandler, valueProviders, validator);
            }
        }
        return resourceMethodDispatcher;
    }

    private static final class TypeOutInvoker
    extends AbstractMethodParamInvoker {
        private final Type t;

        TypeOutInvoker(Invocable resourceMethod, InvocationHandler handler, List<ParamValueFactoryWithSource<?>> valueProviders, ConfiguredValidator validator) {
            super(resourceMethod, handler, valueProviders, validator);
            this.t = resourceMethod.getHandlingMethod().getGenericReturnType();
        }

        @Override
        protected Response doDispatch(Object resource, ContainerRequest containerRequest) throws ProcessingException {
            Object o = this.invoke(containerRequest, resource, this.getParamValues(containerRequest));
            if (o != null) {
                if (o instanceof Response) {
                    return (Response)Response.class.cast(o);
                }
                return Response.ok().entity(o).build();
            }
            return Response.noContent().build();
        }
    }

    private static final class ObjectOutInvoker
    extends AbstractMethodParamInvoker {
        ObjectOutInvoker(Invocable resourceMethod, InvocationHandler handler, List<ParamValueFactoryWithSource<?>> valueProviders, ConfiguredValidator validator) {
            super(resourceMethod, handler, valueProviders, validator);
        }

        @Override
        protected Response doDispatch(Object resource, ContainerRequest containerRequest) throws ProcessingException {
            Object o = this.invoke(containerRequest, resource, this.getParamValues(containerRequest));
            if (o instanceof Response) {
                return (Response)Response.class.cast(o);
            }
            if (o != null) {
                return Response.ok().entity(o).build();
            }
            return Response.noContent().build();
        }
    }

    private static final class ResponseOutInvoker
    extends AbstractMethodParamInvoker {
        ResponseOutInvoker(Invocable resourceMethod, InvocationHandler handler, List<ParamValueFactoryWithSource<?>> valueProviders, ConfiguredValidator validator) {
            super(resourceMethod, handler, valueProviders, validator);
        }

        @Override
        protected Response doDispatch(Object resource, ContainerRequest containerRequest) throws ProcessingException {
            return (Response)Response.class.cast(this.invoke(containerRequest, resource, this.getParamValues(containerRequest)));
        }
    }

    private static final class VoidOutInvoker
    extends AbstractMethodParamInvoker {
        VoidOutInvoker(Invocable resourceMethod, InvocationHandler handler, List<ParamValueFactoryWithSource<?>> valueProviders, ConfiguredValidator validator) {
            super(resourceMethod, handler, valueProviders, validator);
        }

        @Override
        protected Response doDispatch(Object resource, ContainerRequest containerRequest) throws ProcessingException {
            this.invoke(containerRequest, resource, this.getParamValues(containerRequest));
            return Response.noContent().build();
        }
    }

    private static final class SseEventSinkInvoker
    extends AbstractMethodParamInvoker {
        private final int parameterIndex;

        SseEventSinkInvoker(Invocable resourceMethod, InvocationHandler handler, List<ParamValueFactoryWithSource<?>> valueProviders, ConfiguredValidator validator, int parameterIndex) {
            super(resourceMethod, handler, valueProviders, validator);
            this.parameterIndex = parameterIndex;
        }

        @Override
        protected Response doDispatch(Object resource, ContainerRequest request) throws ProcessingException {
            Object[] paramValues = this.getParamValues(request);
            this.invoke(request, resource, paramValues);
            SseEventSink eventSink = (SseEventSink)paramValues[this.parameterIndex];
            if (eventSink == null) {
                throw new IllegalArgumentException("SseEventSink parameter detected, but not found.");
            }
            if (eventSink instanceof Flushable) {
                try {
                    ((Flushable)((Object)eventSink)).flush();
                }
                catch (IOException iOException) {
                    // empty catch block
                }
            }
            return Response.ok().entity(eventSink).build();
        }
    }

    private static abstract class AbstractMethodParamInvoker
    extends AbstractJavaResourceMethodDispatcher {
        private final List<ParamValueFactoryWithSource<?>> valueProviders;

        AbstractMethodParamInvoker(Invocable resourceMethod, InvocationHandler handler, List<ParamValueFactoryWithSource<?>> valueProviders, ConfiguredValidator validator) {
            super(resourceMethod, handler, validator);
            this.valueProviders = valueProviders;
        }

        final Object[] getParamValues(ContainerRequest request) {
            return ParameterValueHelper.getParameterValues(this.valueProviders, request);
        }
    }
}

