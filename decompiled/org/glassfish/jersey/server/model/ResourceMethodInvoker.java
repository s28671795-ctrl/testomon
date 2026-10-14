/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletionStage;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.WriterInterceptor;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.internal.inject.Injections;
import org.glassfish.jersey.internal.inject.Providers;
import org.glassfish.jersey.model.ContractProvider;
import org.glassfish.jersey.model.NameBound;
import org.glassfish.jersey.model.internal.ComponentBag;
import org.glassfish.jersey.model.internal.RankedComparator;
import org.glassfish.jersey.model.internal.RankedProvider;
import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.ContainerResponse;
import org.glassfish.jersey.server.internal.LocalizationMessages;
import org.glassfish.jersey.server.internal.ProcessingProviders;
import org.glassfish.jersey.server.internal.inject.ConfiguredValidator;
import org.glassfish.jersey.server.internal.process.Endpoint;
import org.glassfish.jersey.server.internal.process.RequestProcessingContext;
import org.glassfish.jersey.server.model.Invocable;
import org.glassfish.jersey.server.model.ResourceMethod;
import org.glassfish.jersey.server.model.ResourceMethodConfig;
import org.glassfish.jersey.server.model.internal.ResourceMethodDispatcherFactory;
import org.glassfish.jersey.server.model.internal.ResourceMethodInvocationHandlerFactory;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.spi.internal.ResourceMethodDispatcher;
import org.glassfish.jersey.server.spi.internal.ResourceMethodInvocationHandlerProvider;

public class ResourceMethodInvoker
implements Endpoint,
ResourceInfo {
    private final ResourceMethod method;
    private final Annotation[] methodAnnotations;
    private final Type invocableResponseType;
    private final boolean canUseInvocableResponseType;
    private final boolean isCompletionStageResponseType;
    private final Type completionStageResponseType;
    private final ResourceMethodDispatcher dispatcher;
    private final Method resourceMethod;
    private final Class<?> resourceClass;
    private final List<RankedProvider<ContainerRequestFilter>> requestFilters = new ArrayList<RankedProvider<ContainerRequestFilter>>();
    private final List<RankedProvider<ContainerResponseFilter>> responseFilters = new ArrayList<RankedProvider<ContainerResponseFilter>>();
    private final Iterable<ReaderInterceptor> readerInterceptors;
    private final Iterable<WriterInterceptor> writerInterceptors;

    private ResourceMethodInvoker(ResourceMethodDispatcher.Provider dispatcherProvider, ResourceMethodInvocationHandlerProvider invocationHandlerProvider, ResourceMethod method2, ProcessingProviders processingProviders, InjectionManager injectionManager, Configuration globalConfig, ConfiguredValidator validator) {
        this.method = method2;
        Invocable invocable = method2.getInvocable();
        this.dispatcher = dispatcherProvider.create(invocable, invocationHandlerProvider.create(invocable), validator);
        this.resourceMethod = invocable.getHandlingMethod();
        this.resourceClass = invocable.getHandler().getHandlerClass();
        final ResourceMethodConfig config = new ResourceMethodConfig(globalConfig.getProperties());
        for (DynamicFeature dynamicFeature : processingProviders.getDynamicFeatures()) {
            dynamicFeature.configure(this, config);
        }
        ComponentBag componentBag = config.getComponentBag();
        ArrayList<Object> providers = new ArrayList<Object>(componentBag.getInstances(ComponentBag.excludeMetaProviders(injectionManager)));
        Set<Class<?>> providerClasses = componentBag.getClasses(ComponentBag.excludeMetaProviders(injectionManager));
        if (!providerClasses.isEmpty()) {
            injectionManager = Injections.createInjectionManager(injectionManager);
            injectionManager.register(new AbstractBinder(){

                @Override
                protected void configure() {
                    this.bind(config).to(Configuration.class);
                }
            });
            for (Class<?> providerClass : providerClasses) {
                providers.add(injectionManager.createAndInitialize(providerClass));
            }
        }
        LinkedList<RankedProvider<ReaderInterceptor>> _readerInterceptors = new LinkedList<RankedProvider<ReaderInterceptor>>();
        LinkedList _writerInterceptors = new LinkedList();
        LinkedList<RankedProvider<ContainerRequestFilter>> _requestFilters = new LinkedList<RankedProvider<ContainerRequestFilter>>();
        LinkedList<RankedProvider<ContainerResponseFilter>> _responseFilters = new LinkedList<RankedProvider<ContainerResponseFilter>>();
        for (Object e : providers) {
            ContractProvider model = componentBag.getModel(e.getClass());
            Set<Class<?>> contracts = model.getContracts();
            if (contracts.contains(WriterInterceptor.class)) {
                _writerInterceptors.add(new RankedProvider<WriterInterceptor>((WriterInterceptor)e, model.getPriority(WriterInterceptor.class)));
            }
            if (contracts.contains(ReaderInterceptor.class)) {
                _readerInterceptors.add(new RankedProvider<ReaderInterceptor>((ReaderInterceptor)e, model.getPriority(ReaderInterceptor.class)));
            }
            if (contracts.contains(ContainerRequestFilter.class)) {
                _requestFilters.add(new RankedProvider<ContainerRequestFilter>((ContainerRequestFilter)e, model.getPriority(ContainerRequestFilter.class)));
            }
            if (!contracts.contains(ContainerResponseFilter.class)) continue;
            _responseFilters.add(new RankedProvider<ContainerResponseFilter>((ContainerResponseFilter)e, model.getPriority(ContainerResponseFilter.class)));
        }
        _readerInterceptors.addAll(StreamSupport.stream(processingProviders.getGlobalReaderInterceptors().spliterator(), false).collect(Collectors.toList()));
        _writerInterceptors.addAll(StreamSupport.stream(processingProviders.getGlobalWriterInterceptors().spliterator(), false).collect(Collectors.toList()));
        if (this.resourceMethod != null) {
            this.addNameBoundFiltersAndInterceptors(processingProviders, _requestFilters, _responseFilters, _readerInterceptors, _writerInterceptors, method2);
        }
        this.readerInterceptors = Collections.unmodifiableList(StreamSupport.stream(Providers.sortRankedProviders(new RankedComparator(), _readerInterceptors).spliterator(), false).collect(Collectors.toList()));
        this.writerInterceptors = Collections.unmodifiableList(StreamSupport.stream(Providers.sortRankedProviders(new RankedComparator(), _writerInterceptors).spliterator(), false).collect(Collectors.toList()));
        this.requestFilters.addAll(_requestFilters);
        this.responseFilters.addAll(_responseFilters);
        this.methodAnnotations = invocable.getHandlingMethod().getDeclaredAnnotations();
        this.invocableResponseType = invocable.getResponseType();
        this.canUseInvocableResponseType = this.invocableResponseType != null && Void.TYPE != this.invocableResponseType && Void.class != this.invocableResponseType && (!(this.invocableResponseType instanceof Class) || !Response.class.isAssignableFrom((Class)this.invocableResponseType));
        this.isCompletionStageResponseType = ParameterizedType.class.isInstance(this.invocableResponseType) && CompletionStage.class.isAssignableFrom((Class)((ParameterizedType)this.invocableResponseType).getRawType());
        this.completionStageResponseType = this.isCompletionStageResponseType ? ((ParameterizedType)this.invocableResponseType).getActualTypeArguments()[0] : null;
    }

    private <T> void addNameBoundProviders(Collection<RankedProvider<T>> targetCollection, NameBound nameBound, MultivaluedMap<Class<? extends Annotation>, RankedProvider<T>> nameBoundProviders, MultivaluedMap<RankedProvider<T>, Class<? extends Annotation>> nameBoundProvidersInverse) {
        MultivaluedHashMap<RankedProvider, Class<? extends Annotation>> foundBindingsMap = new MultivaluedHashMap<RankedProvider, Class<? extends Annotation>>();
        for (Class<? extends Annotation> clazz : nameBound.getNameBindings()) {
            Iterable providers = (Iterable)nameBoundProviders.get(clazz);
            if (providers == null) continue;
            for (RankedProvider provider : providers) {
                foundBindingsMap.add(provider, clazz);
            }
        }
        for (Map.Entry entry : foundBindingsMap.entrySet()) {
            RankedProvider provider = (RankedProvider)entry.getKey();
            List foundBindings = (List)entry.getValue();
            List providerBindings = (List)nameBoundProvidersInverse.get(provider);
            if (foundBindings.size() != providerBindings.size()) continue;
            targetCollection.add(provider);
        }
    }

    private void addNameBoundFiltersAndInterceptors(ProcessingProviders processingProviders, Collection<RankedProvider<ContainerRequestFilter>> targetRequestFilters, Collection<RankedProvider<ContainerResponseFilter>> targetResponseFilters, Collection<RankedProvider<ReaderInterceptor>> targetReaderInterceptors, Collection<RankedProvider<WriterInterceptor>> targetWriterInterceptors, NameBound nameBound) {
        this.addNameBoundProviders(targetRequestFilters, nameBound, processingProviders.getNameBoundRequestFilters(), processingProviders.getNameBoundRequestFiltersInverse());
        this.addNameBoundProviders(targetResponseFilters, nameBound, processingProviders.getNameBoundResponseFilters(), processingProviders.getNameBoundResponseFiltersInverse());
        this.addNameBoundProviders(targetReaderInterceptors, nameBound, processingProviders.getNameBoundReaderInterceptors(), processingProviders.getNameBoundReaderInterceptorsInverse());
        this.addNameBoundProviders(targetWriterInterceptors, nameBound, processingProviders.getNameBoundWriterInterceptors(), processingProviders.getNameBoundWriterInterceptorsInverse());
    }

    @Override
    public Method getResourceMethod() {
        return this.resourceMethod;
    }

    @Override
    public Class<?> getResourceClass() {
        return this.resourceClass;
    }

    @Override
    public ContainerResponse apply(RequestProcessingContext processingContext) {
        Object entityFuture;
        ContainerRequest request = processingContext.request();
        Object resource = processingContext.routingContext().peekMatchedResource();
        if ((this.method.isSuspendDeclared() || this.method.isManagedAsyncDeclared() || this.method.isSse()) && !processingContext.asyncContext().suspend()) {
            throw new ProcessingException(LocalizationMessages.ERROR_SUSPENDING_ASYNC_REQUEST());
        }
        if (this.method.isManagedAsyncDeclared()) {
            processingContext.asyncContext().invokeManaged(() -> {
                Response response = this.invoke(processingContext, resource);
                if (this.method.isSuspendDeclared()) {
                    return null;
                }
                return response;
            });
            return null;
        }
        Response response = this.invoke(processingContext, resource);
        if (this.method.isSse()) {
            return null;
        }
        if (response.hasEntity() && (entityFuture = response.getEntity()) instanceof CompletionStage) {
            CompletionStage completionStage = (CompletionStage)entityFuture;
            if (!processingContext.asyncContext().suspend()) {
                throw new ProcessingException(LocalizationMessages.ERROR_SUSPENDING_ASYNC_REQUEST());
            }
            completionStage.whenComplete(this.whenComplete(processingContext));
            return null;
        }
        return new ContainerResponse(request, response);
    }

    private BiConsumer whenComplete(RequestProcessingContext processingContext) {
        return (entity, exception) -> {
            if (exception != null) {
                if (exception instanceof CancellationException) {
                    processingContext.asyncContext().resume(Response.status(Response.Status.SERVICE_UNAVAILABLE).build());
                } else {
                    processingContext.asyncContext().resume((Throwable)exception);
                }
            } else {
                processingContext.asyncContext().resume(entity);
            }
        };
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Response invoke(RequestProcessingContext context, Object resource) {
        Response jaxrsResponse;
        context.triggerEvent(RequestEvent.Type.RESOURCE_METHOD_START);
        context.push(response -> {
            if (response == null || response.isMappedFromException()) {
                return response;
            }
            Annotation[] entityAnn = response.getEntityAnnotations();
            if (this.methodAnnotations.length > 0) {
                if (entityAnn.length == 0) {
                    response.setEntityAnnotations(this.methodAnnotations);
                } else {
                    Annotation[] mergedAnn = Arrays.copyOf(this.methodAnnotations, this.methodAnnotations.length + entityAnn.length);
                    System.arraycopy(entityAnn, 0, mergedAnn, this.methodAnnotations.length, entityAnn.length);
                    response.setEntityAnnotations(mergedAnn);
                }
            }
            if (this.canUseInvocableResponseType && response.hasEntity() && !(response.getEntityType() instanceof ParameterizedType)) {
                response.setEntityType(this.unwrapInvocableResponseType(context.request()));
            }
            return response;
        });
        try {
            jaxrsResponse = this.dispatcher.dispatch(resource, context.request());
        }
        finally {
            context.triggerEvent(RequestEvent.Type.RESOURCE_METHOD_FINISHED);
        }
        if (jaxrsResponse == null) {
            jaxrsResponse = Response.noContent().build();
        }
        return jaxrsResponse;
    }

    private Type unwrapInvocableResponseType(ContainerRequest request) {
        if (this.isCompletionStageResponseType && request.resolveProperty("jersey.config.server.unwrap.completion.stage.writer.enable", Boolean.FALSE).booleanValue()) {
            return this.completionStageResponseType;
        }
        return this.invocableResponseType;
    }

    public Iterable<RankedProvider<ContainerRequestFilter>> getRequestFilters() {
        return this.requestFilters;
    }

    public Iterable<RankedProvider<ContainerResponseFilter>> getResponseFilters() {
        return this.responseFilters;
    }

    public Iterable<WriterInterceptor> getWriterInterceptors() {
        return this.writerInterceptors;
    }

    public Iterable<ReaderInterceptor> getReaderInterceptors() {
        return this.readerInterceptors;
    }

    public String toString() {
        return this.method.getInvocable().getHandlingMethod().toString();
    }

    public static class Builder {
        private ResourceMethodDispatcherFactory resourceMethodDispatcherFactory;
        private ResourceMethodInvocationHandlerFactory resourceMethodInvocationHandlerFactory;
        private InjectionManager injectionManager;
        private Configuration configuration;
        private Supplier<ConfiguredValidator> configurationValidator;

        public Builder resourceMethodDispatcherFactory(ResourceMethodDispatcherFactory resourceMethodDispatcherFactory) {
            this.resourceMethodDispatcherFactory = resourceMethodDispatcherFactory;
            return this;
        }

        public Builder resourceMethodInvocationHandlerFactory(ResourceMethodInvocationHandlerFactory resourceMethodInvocationHandlerFactory) {
            this.resourceMethodInvocationHandlerFactory = resourceMethodInvocationHandlerFactory;
            return this;
        }

        public Builder injectionManager(InjectionManager injectionManager) {
            this.injectionManager = injectionManager;
            return this;
        }

        public Builder configuration(Configuration configuration) {
            this.configuration = configuration;
            return this;
        }

        public Builder configurationValidator(Supplier<ConfiguredValidator> configurationValidator) {
            this.configurationValidator = configurationValidator;
            return this;
        }

        public ResourceMethodInvoker build(ResourceMethod method2, ProcessingProviders processingProviders) {
            if (this.resourceMethodDispatcherFactory == null) {
                throw new NullPointerException("ResourceMethodDispatcherFactory is not set.");
            }
            if (this.resourceMethodInvocationHandlerFactory == null) {
                throw new NullPointerException("ResourceMethodInvocationHandlerFactory is not set.");
            }
            if (this.injectionManager == null) {
                throw new NullPointerException("DI injection manager is not set.");
            }
            if (this.configuration == null) {
                throw new NullPointerException("Configuration is not set.");
            }
            if (this.configurationValidator == null) {
                throw new NullPointerException("Configuration validator is not set.");
            }
            return new ResourceMethodInvoker(this.resourceMethodDispatcherFactory, this.resourceMethodInvocationHandlerFactory, method2, processingProviders, this.injectionManager, this.configuration, this.configurationValidator.get());
        }
    }
}

