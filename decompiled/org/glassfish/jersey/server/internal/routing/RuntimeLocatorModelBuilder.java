/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.routing;

import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Configuration;
import org.glassfish.jersey.internal.Errors;
import org.glassfish.jersey.internal.guava.CacheBuilder;
import org.glassfish.jersey.internal.guava.CacheLoader;
import org.glassfish.jersey.internal.guava.LoadingCache;
import org.glassfish.jersey.message.MessageBodyWorkers;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.internal.JerseyResourceContext;
import org.glassfish.jersey.server.internal.LocalizationMessages;
import org.glassfish.jersey.server.internal.routing.LocatorRouting;
import org.glassfish.jersey.server.internal.routing.Router;
import org.glassfish.jersey.server.internal.routing.RuntimeModelBuilder;
import org.glassfish.jersey.server.internal.routing.SubResourceLocatorRouter;
import org.glassfish.jersey.server.model.ComponentModelValidator;
import org.glassfish.jersey.server.model.ModelProcessor;
import org.glassfish.jersey.server.model.ModelValidationException;
import org.glassfish.jersey.server.model.Resource;
import org.glassfish.jersey.server.model.ResourceMethod;
import org.glassfish.jersey.server.model.ResourceModel;
import org.glassfish.jersey.server.model.ResourceModelComponent;
import org.glassfish.jersey.server.model.internal.ModelErrors;
import org.glassfish.jersey.server.spi.internal.ValueParamProvider;

final class RuntimeLocatorModelBuilder {
    private static final Logger LOGGER = Logger.getLogger(RuntimeLocatorModelBuilder.class.getName());
    private final Configuration config;
    private final RuntimeModelBuilder runtimeModelBuilder;
    private final MessageBodyWorkers messageBodyWorkers;
    private final Collection<ValueParamProvider> valueSuppliers;
    private final JerseyResourceContext resourceContext;
    private final Iterable<ModelProcessor> modelProcessors;
    private final Function<Class<?>, ?> createServiceFunction;
    private final LoadingCache<LocatorCacheKey, LocatorRouting> cache;
    private final boolean disableValidation;
    private final boolean ignoreValidationErrors;
    private final boolean enableJerseyResourceCaching;

    RuntimeLocatorModelBuilder(Configuration config, MessageBodyWorkers messageBodyWorkers, Collection<ValueParamProvider> valueSuppliers, JerseyResourceContext resourceContext, RuntimeModelBuilder runtimeModelBuilder, Iterable<ModelProcessor> modelProcessors, Function<Class<?>, ?> createServiceFunction) {
        this.config = config;
        this.messageBodyWorkers = messageBodyWorkers;
        this.valueSuppliers = valueSuppliers;
        this.runtimeModelBuilder = runtimeModelBuilder;
        this.resourceContext = resourceContext;
        this.modelProcessors = modelProcessors;
        this.createServiceFunction = createServiceFunction;
        this.disableValidation = ServerProperties.getValue(config.getProperties(), "jersey.config.server.resource.validation.disable", Boolean.FALSE, Boolean.class);
        this.ignoreValidationErrors = ServerProperties.getValue(config.getProperties(), "jersey.config.server.resource.validation.ignoreErrors", Boolean.FALSE, Boolean.class);
        this.enableJerseyResourceCaching = ServerProperties.getValue(config.getProperties(), "jersey.config.server.subresource.cache.jersey.resource.enabled", Boolean.FALSE, Boolean.class);
        int size = ServerProperties.getValue(config.getProperties(), "jersey.config.server.subresource.cache.size", 64, Integer.class);
        int age = ServerProperties.getValue(config.getProperties(), "jersey.config.server.subresource.cache.age", -1, Integer.class);
        CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder();
        if (size > 0) {
            cacheBuilder.maximumSize(size);
        } else {
            LOGGER.log(Level.CONFIG, LocalizationMessages.SUBRES_LOC_CACHE_INVALID_SIZE(size, 64));
            cacheBuilder.maximumSize(64L);
        }
        if (age > 0) {
            cacheBuilder.expireAfterAccess(age, TimeUnit.SECONDS);
        }
        this.cache = cacheBuilder.build(new CacheLoader<LocatorCacheKey, LocatorRouting>(){

            @Override
            public LocatorRouting load(LocatorCacheKey key) throws Exception {
                return key.clazz != null ? RuntimeLocatorModelBuilder.this.createRouting(key.clazz) : RuntimeLocatorModelBuilder.this.buildRouting(key.resource);
            }
        });
    }

    Router getRouter(ResourceMethod resourceMethod) {
        return new SubResourceLocatorRouter(this.createServiceFunction, this.valueSuppliers, resourceMethod, this.resourceContext, this);
    }

    LocatorRouting getRouting(Class<?> locatorClass) {
        try {
            return this.cache.get(new LocatorCacheKey(locatorClass));
        }
        catch (ExecutionException ee) {
            LOGGER.log(Level.FINE, LocalizationMessages.SUBRES_LOC_CACHE_LOAD_FAILED(locatorClass), ee);
            return this.createRouting(locatorClass);
        }
    }

    LocatorRouting getRouting(Resource subresource) {
        if (this.enableJerseyResourceCaching) {
            try {
                return this.cache.get(new LocatorCacheKey(subresource));
            }
            catch (ExecutionException ee) {
                LOGGER.log(Level.FINE, LocalizationMessages.SUBRES_LOC_CACHE_LOAD_FAILED(subresource), ee);
                return this.buildRouting(subresource);
            }
        }
        return this.buildRouting(subresource);
    }

    boolean isCached(Class<?> srlClass) {
        return this.cache.getIfPresent(srlClass) != null;
    }

    private LocatorRouting createRouting(Class<?> locatorClass) {
        Resource.Builder builder = Resource.builder(locatorClass, this.disableValidation);
        if (builder == null) {
            builder = Resource.builder().name(locatorClass.getName());
        }
        return this.buildRouting(builder.build());
    }

    private LocatorRouting buildRouting(Resource subResource) {
        ResourceModel model = new ResourceModel.Builder(true).addResource(subResource).build();
        ResourceModel enhancedModel = this.enhance(model);
        if (!this.disableValidation) {
            this.validateResource(enhancedModel);
        }
        Resource enhancedLocator = enhancedModel.getResources().get(0);
        for (Class<?> handlerClass : enhancedLocator.getHandlerClasses()) {
            this.resourceContext.bindResource(handlerClass);
        }
        return new LocatorRouting(enhancedModel, this.runtimeModelBuilder.buildModel(enhancedModel.getRuntimeResourceModel(), true));
    }

    private void validateResource(final ResourceModelComponent component) {
        Errors.process(new Runnable(){

            @Override
            public void run() {
                ComponentModelValidator validator = new ComponentModelValidator(RuntimeLocatorModelBuilder.this.valueSuppliers, RuntimeLocatorModelBuilder.this.messageBodyWorkers);
                validator.validate(component);
                if (Errors.fatalIssuesFound() && !RuntimeLocatorModelBuilder.this.ignoreValidationErrors) {
                    throw new ModelValidationException(LocalizationMessages.ERROR_VALIDATION_SUBRESOURCE(), ModelErrors.getErrorsAsResourceModelIssues());
                }
            }
        });
    }

    private ResourceModel enhance(ResourceModel subResourceModel) {
        for (ModelProcessor modelProcessor : this.modelProcessors) {
            subResourceModel = modelProcessor.processSubResource(subResourceModel, this.config);
            this.validateSubResource(subResourceModel);
        }
        return subResourceModel;
    }

    private void validateSubResource(ResourceModel subResourceModel) {
        if (subResourceModel.getResources().size() != 1) {
            throw new ProcessingException(LocalizationMessages.ERROR_SUB_RESOURCE_LOCATOR_MORE_RESOURCES(subResourceModel.getResources().size()));
        }
    }

    private static class LocatorCacheKey {
        private final Class<?> clazz;
        private final Resource resource;

        public LocatorCacheKey(Class<?> clazz) {
            this(clazz, null);
        }

        public LocatorCacheKey(Resource resource) {
            this(null, resource);
        }

        private LocatorCacheKey(Class<?> clazz, Resource resource) {
            this.clazz = clazz;
            this.resource = resource;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            LocatorCacheKey that = (LocatorCacheKey)o;
            if (this.clazz != null ? !this.clazz.equals(that.clazz) : that.clazz != null) {
                return false;
            }
            return !(this.resource != null ? !this.resource.equals(that.resource) : that.resource != null);
        }

        public int hashCode() {
            int result = this.clazz != null ? this.clazz.hashCode() : 0;
            result = 31 * result + (this.resource != null ? this.resource.hashCode() : 0);
            return result;
        }
    }
}

