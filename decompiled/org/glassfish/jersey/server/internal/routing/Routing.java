/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.routing;

import java.util.Collection;
import java.util.function.Function;
import javax.ws.rs.core.Configuration;
import org.glassfish.jersey.message.MessageBodyWorkers;
import org.glassfish.jersey.process.internal.ChainableStage;
import org.glassfish.jersey.process.internal.Stage;
import org.glassfish.jersey.server.internal.JerseyResourceContext;
import org.glassfish.jersey.server.internal.ProcessingProviders;
import org.glassfish.jersey.server.internal.process.RequestProcessingContext;
import org.glassfish.jersey.server.internal.routing.MatchedEndpointExtractorStage;
import org.glassfish.jersey.server.internal.routing.RoutingStage;
import org.glassfish.jersey.server.internal.routing.RuntimeModelBuilder;
import org.glassfish.jersey.server.model.ModelProcessor;
import org.glassfish.jersey.server.model.ResourceMethodInvoker;
import org.glassfish.jersey.server.model.RuntimeResourceModel;
import org.glassfish.jersey.server.spi.internal.ValueParamProvider;

public final class Routing {
    private Routing() {
        throw new AssertionError((Object)"No instances allowed.");
    }

    public static Stage<RequestProcessingContext> matchedEndpointExtractor() {
        return new MatchedEndpointExtractorStage();
    }

    public static Builder forModel(RuntimeResourceModel resourceModel) {
        return new Builder(resourceModel);
    }

    public static final class Builder {
        private final RuntimeResourceModel resourceModel;
        private JerseyResourceContext resourceContext;
        private Configuration config;
        private MessageBodyWorkers entityProviders;
        private Collection<ValueParamProvider> valueSuppliers;
        private Iterable<ModelProcessor> modelProcessors;
        private Function<Class<?>, ?> createServiceFunction;
        private ProcessingProviders processingProviders;
        private ResourceMethodInvoker.Builder resourceMethodInvokerBuilder;

        private Builder(RuntimeResourceModel resourceModel) {
            if (resourceModel == null) {
                throw new NullPointerException("Resource model must not be null.");
            }
            this.resourceModel = resourceModel;
        }

        public Builder resourceContext(JerseyResourceContext resourceContext) {
            this.resourceContext = resourceContext;
            return this;
        }

        public Builder configuration(Configuration config) {
            this.config = config;
            return this;
        }

        public Builder entityProviders(MessageBodyWorkers workers) {
            this.entityProviders = workers;
            return this;
        }

        public Builder valueSupplierProviders(Collection<ValueParamProvider> valueSuppliers) {
            this.valueSuppliers = valueSuppliers;
            return this;
        }

        public Builder processingProviders(ProcessingProviders processingProviders) {
            this.processingProviders = processingProviders;
            return this;
        }

        public Builder modelProcessors(Iterable<ModelProcessor> modelProcessors) {
            this.modelProcessors = modelProcessors;
            return this;
        }

        public Builder createService(Function<Class<?>, ?> createServiceFunction) {
            this.createServiceFunction = createServiceFunction;
            return this;
        }

        public Builder resourceMethodInvokerBuilder(ResourceMethodInvoker.Builder resourceMethodInvokerBuilder) {
            this.resourceMethodInvokerBuilder = resourceMethodInvokerBuilder;
            return this;
        }

        public ChainableStage<RequestProcessingContext> buildStage() {
            if (this.resourceContext == null) {
                throw new NullPointerException("Resource context is not set.");
            }
            if (this.config == null) {
                throw new NullPointerException("Runtime configuration is not set.");
            }
            if (this.entityProviders == null) {
                throw new NullPointerException("Entity providers are not set.");
            }
            if (this.valueSuppliers == null) {
                throw new NullPointerException("Value supplier providers are not set.");
            }
            if (this.modelProcessors == null) {
                throw new NullPointerException("Model processors are not set.");
            }
            if (this.createServiceFunction == null) {
                throw new NullPointerException("Create function is not set.");
            }
            if (this.processingProviders == null) {
                throw new NullPointerException("Processing providers are not set.");
            }
            if (this.resourceMethodInvokerBuilder == null) {
                throw new NullPointerException("ResourceMethodInvokerBuilder is not set.");
            }
            RuntimeModelBuilder runtimeModelBuilder = new RuntimeModelBuilder(this.resourceContext, this.config, this.entityProviders, this.valueSuppliers, this.processingProviders, this.resourceMethodInvokerBuilder, this.modelProcessors, this.createServiceFunction);
            return new RoutingStage(runtimeModelBuilder.buildModel(this.resourceModel, false));
        }
    }
}

