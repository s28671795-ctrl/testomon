/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.wadl.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.Priority;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.process.Inflector;
import org.glassfish.jersey.server.ExtendedUriInfo;
import org.glassfish.jersey.server.model.ModelProcessor;
import org.glassfish.jersey.server.model.ResourceModel;
import org.glassfish.jersey.server.model.internal.ModelProcessorUtil;

@Priority(value=0x7FFFFFFF)
public class OptionsMethodProcessor
implements ModelProcessor {
    private final List<ModelProcessorUtil.Method> methodList = new ArrayList<ModelProcessorUtil.Method>();

    public OptionsMethodProcessor() {
        this.methodList.add(new ModelProcessorUtil.Method("OPTIONS", MediaType.WILDCARD_TYPE, MediaType.TEXT_PLAIN_TYPE, PlainTextOptionsInflector.class));
        this.methodList.add(new ModelProcessorUtil.Method("OPTIONS", MediaType.WILDCARD_TYPE, MediaType.WILDCARD_TYPE, GenericOptionsInflector.class));
    }

    @Override
    public ResourceModel processResourceModel(ResourceModel resourceModel, Configuration configuration) {
        return ModelProcessorUtil.enhanceResourceModel(resourceModel, false, this.methodList, true).build();
    }

    @Override
    public ResourceModel processSubResource(ResourceModel subResourceModel, Configuration configuration) {
        return ModelProcessorUtil.enhanceResourceModel(subResourceModel, true, this.methodList, true).build();
    }

    private static class GenericOptionsInflector
    implements Inflector<ContainerRequestContext, Response> {
        @Inject
        private Provider<ExtendedUriInfo> extendedUriInfo;

        private GenericOptionsInflector() {
        }

        @Override
        public Response apply(ContainerRequestContext containerRequestContext) {
            Set<String> allowedMethods = ModelProcessorUtil.getAllowedMethods(this.extendedUriInfo.get().getMatchedRuntimeResources().get(0));
            return Response.ok().allow(allowedMethods).header("Content-Length", "0").type(containerRequestContext.getAcceptableMediaTypes().get(0)).build();
        }
    }

    private static class PlainTextOptionsInflector
    implements Inflector<ContainerRequestContext, Response> {
        @Inject
        private Provider<ExtendedUriInfo> extendedUriInfo;

        private PlainTextOptionsInflector() {
        }

        @Override
        public Response apply(ContainerRequestContext containerRequestContext) {
            Set<String> allowedMethods = ModelProcessorUtil.getAllowedMethods(this.extendedUriInfo.get().getMatchedRuntimeResources().get(0));
            String allowedList = allowedMethods.toString();
            String optionsBody = allowedList.substring(1, allowedList.length() - 1);
            return Response.ok((Object)optionsBody, MediaType.TEXT_PLAIN_TYPE).allow(allowedMethods).build();
        }
    }
}

