/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.routing;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;
import java.security.PrivilegedAction;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.SecurityContext;
import org.glassfish.jersey.server.SubjectSecurityContext;
import org.glassfish.jersey.server.internal.JerseyResourceContext;
import org.glassfish.jersey.server.internal.LocalizationMessages;
import org.glassfish.jersey.server.internal.process.MappableException;
import org.glassfish.jersey.server.internal.process.RequestProcessingContext;
import org.glassfish.jersey.server.internal.routing.LocatorRouting;
import org.glassfish.jersey.server.internal.routing.Router;
import org.glassfish.jersey.server.internal.routing.RoutingContext;
import org.glassfish.jersey.server.internal.routing.RuntimeLocatorModelBuilder;
import org.glassfish.jersey.server.model.Resource;
import org.glassfish.jersey.server.model.ResourceMethod;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.spi.internal.ParamValueFactoryWithSource;
import org.glassfish.jersey.server.spi.internal.ParameterValueHelper;
import org.glassfish.jersey.server.spi.internal.ValueParamProvider;

final class SubResourceLocatorRouter
implements Router {
    private final ResourceMethod locatorModel;
    private final List<ParamValueFactoryWithSource<?>> valueProviders;
    private final RuntimeLocatorModelBuilder runtimeLocatorBuilder;
    private final JerseyResourceContext resourceContext;
    private final Function<Class<?>, ?> createFunction;

    SubResourceLocatorRouter(Function<Class<?>, ?> createServiceFunction, Collection<ValueParamProvider> valueSuppliers, ResourceMethod locatorModel, JerseyResourceContext resourceContext, RuntimeLocatorModelBuilder runtimeLocatorBuilder) {
        this.runtimeLocatorBuilder = runtimeLocatorBuilder;
        this.locatorModel = locatorModel;
        this.resourceContext = resourceContext;
        this.createFunction = createServiceFunction;
        this.valueProviders = ParameterValueHelper.createValueProviders(valueSuppliers, locatorModel.getInvocable());
    }

    @Override
    public Router.Continuation apply(RequestProcessingContext processingContext) {
        LocatorRouting routing;
        Object subResourceInstance = this.getResource(processingContext);
        if (subResourceInstance == null) {
            throw new NotFoundException();
        }
        RoutingContext routingContext = processingContext.routingContext();
        if (subResourceInstance instanceof Resource) {
            routing = this.runtimeLocatorBuilder.getRouting((Resource)subResourceInstance);
        } else {
            Class locatorClass = subResourceInstance.getClass();
            if (locatorClass.isAssignableFrom(Class.class) && !this.runtimeLocatorBuilder.isCached(locatorClass = (Class)subResourceInstance)) {
                subResourceInstance = this.createFunction.apply(locatorClass);
            }
            routingContext.pushMatchedResource(subResourceInstance);
            this.resourceContext.bindResourceIfSingleton(subResourceInstance);
            routing = this.runtimeLocatorBuilder.getRouting(locatorClass);
        }
        routingContext.pushLocatorSubResource(routing.locator.getResources().get(0));
        processingContext.triggerEvent(RequestEvent.Type.SUBRESOURCE_LOCATED);
        return Router.Continuation.of(processingContext, routing.router);
    }

    private Object getResource(RequestProcessingContext context) {
        Object resource = context.routingContext().peekMatchedResource();
        Method handlingMethod = this.locatorModel.getInvocable().getHandlingMethod();
        Object[] parameterValues = ParameterValueHelper.getParameterValues(this.valueProviders, context.request());
        context.triggerEvent(RequestEvent.Type.LOCATOR_MATCHED);
        PrivilegedAction<Object> invokeMethodAction = () -> {
            try {
                return handlingMethod.invoke(resource, parameterValues);
            }
            catch (IllegalAccessException | IllegalArgumentException | UndeclaredThrowableException ex) {
                throw new ProcessingException(LocalizationMessages.ERROR_RESOURCE_JAVA_METHOD_INVOCATION(), ex);
            }
            catch (InvocationTargetException ex) {
                Throwable cause = ex.getCause();
                if (cause instanceof WebApplicationException) {
                    throw (WebApplicationException)cause;
                }
                throw new MappableException(cause);
            }
            catch (Throwable t2) {
                throw new ProcessingException(t2);
            }
        };
        SecurityContext securityContext = context.request().getSecurityContext();
        return securityContext instanceof SubjectSecurityContext ? ((SubjectSecurityContext)securityContext).doAsSubject(invokeMethodAction) : invokeMethodAction.run();
    }
}

