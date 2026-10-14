/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server;

import java.util.Collection;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import org.glassfish.jersey.internal.BootstrapBag;
import org.glassfish.jersey.internal.util.collection.LazyValue;
import org.glassfish.jersey.server.ApplicationHandler;
import org.glassfish.jersey.server.ResourceBag;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.internal.JerseyResourceContext;
import org.glassfish.jersey.server.internal.ProcessingProviders;
import org.glassfish.jersey.server.internal.inject.MultivaluedParameterExtractorProvider;
import org.glassfish.jersey.server.model.ModelProcessor;
import org.glassfish.jersey.server.model.ResourceMethodInvoker;
import org.glassfish.jersey.server.model.ResourceModel;
import org.glassfish.jersey.server.spi.ComponentProvider;
import org.glassfish.jersey.server.spi.internal.ValueParamProvider;

public class ServerBootstrapBag
extends BootstrapBag {
    private Application application;
    private ApplicationHandler applicationHandler;
    private Collection<ValueParamProvider> valueParamProviders;
    private MultivaluedParameterExtractorProvider multivaluedParameterExtractorProvider;
    private ProcessingProviders processingProviders;
    private JerseyResourceContext resourceContext;
    private LazyValue<Collection<ComponentProvider>> componentProviders;
    private ResourceMethodInvoker.Builder resourceMethodInvokerBuilder;
    private ResourceBag resourceBag;
    private ResourceModel resourceModel;
    private Collection<ModelProcessor> modelProcessors;

    public Collection<ModelProcessor> getModelProcessors() {
        return this.modelProcessors;
    }

    public void setModelProcessors(Collection<ModelProcessor> modelProcessors) {
        this.modelProcessors = modelProcessors;
    }

    public ResourceBag getResourceBag() {
        ServerBootstrapBag.requireNonNull(this.resourceBag, ResourceBag.class);
        return this.resourceBag;
    }

    public void setResourceBag(ResourceBag resourceBag) {
        this.resourceBag = resourceBag;
    }

    public ResourceConfig getRuntimeConfig() {
        return (ResourceConfig)this.getConfiguration();
    }

    public Application getApplication() {
        ServerBootstrapBag.requireNonNull(this.application, Application.class);
        return this.application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public ApplicationHandler getApplicationHandler() {
        ServerBootstrapBag.requireNonNull(this.applicationHandler, ApplicationHandler.class);
        return this.applicationHandler;
    }

    public void setApplicationHandler(ApplicationHandler applicationHandler) {
        this.applicationHandler = applicationHandler;
    }

    public ProcessingProviders getProcessingProviders() {
        ServerBootstrapBag.requireNonNull(this.processingProviders, ProcessingProviders.class);
        return this.processingProviders;
    }

    public void setProcessingProviders(ProcessingProviders processingProviders) {
        this.processingProviders = processingProviders;
    }

    public MultivaluedParameterExtractorProvider getMultivaluedParameterExtractorProvider() {
        ServerBootstrapBag.requireNonNull(this.multivaluedParameterExtractorProvider, MultivaluedParameterExtractorProvider.class);
        return this.multivaluedParameterExtractorProvider;
    }

    public void setMultivaluedParameterExtractorProvider(MultivaluedParameterExtractorProvider provider) {
        this.multivaluedParameterExtractorProvider = provider;
    }

    public Collection<ValueParamProvider> getValueParamProviders() {
        ServerBootstrapBag.requireNonNull(this.valueParamProviders, new GenericType<Collection<ValueParamProvider>>(){}.getType());
        return this.valueParamProviders;
    }

    public void setValueParamProviders(Collection<ValueParamProvider> valueParamProviders) {
        this.valueParamProviders = valueParamProviders;
    }

    public JerseyResourceContext getResourceContext() {
        ServerBootstrapBag.requireNonNull(this.resourceContext, JerseyResourceContext.class);
        return this.resourceContext;
    }

    public void setResourceContext(JerseyResourceContext resourceContext) {
        this.resourceContext = resourceContext;
    }

    public LazyValue<Collection<ComponentProvider>> getComponentProviders() {
        ServerBootstrapBag.requireNonNull(this.componentProviders, new GenericType<LazyValue<Collection<ComponentProvider>>>(){}.getType());
        return this.componentProviders;
    }

    public void setComponentProviders(LazyValue<Collection<ComponentProvider>> componentProviders) {
        this.componentProviders = componentProviders;
    }

    public ResourceMethodInvoker.Builder getResourceMethodInvokerBuilder() {
        ServerBootstrapBag.requireNonNull(this.resourceMethodInvokerBuilder, ResourceMethodInvoker.Builder.class);
        return this.resourceMethodInvokerBuilder;
    }

    public void setResourceMethodInvokerBuilder(ResourceMethodInvoker.Builder resourceMethodInvokerBuilder) {
        this.resourceMethodInvokerBuilder = resourceMethodInvokerBuilder;
    }

    public ResourceModel getResourceModel() {
        ServerBootstrapBag.requireNonNull(this.resourceModel, ResourceModel.class);
        return this.resourceModel;
    }

    public void setResourceModel(ResourceModel resourceModel) {
        this.resourceModel = resourceModel;
    }
}

