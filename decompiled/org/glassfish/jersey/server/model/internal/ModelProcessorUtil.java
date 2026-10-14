/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.model.internal;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.process.Inflector;
import org.glassfish.jersey.server.model.Resource;
import org.glassfish.jersey.server.model.ResourceMethod;
import org.glassfish.jersey.server.model.ResourceModel;
import org.glassfish.jersey.server.model.RuntimeResource;

public final class ModelProcessorUtil {
    private ModelProcessorUtil() {
        throw new AssertionError((Object)"Instantiation not allowed.");
    }

    public static Set<String> getAllowedMethods(RuntimeResource resource) {
        boolean getFound = false;
        HashSet<String> allowedMethods = new HashSet<String>();
        for (ResourceMethod resourceMethod : resource.getResourceMethods()) {
            String httpMethod = resourceMethod.getHttpMethod();
            allowedMethods.add(httpMethod);
            if (getFound || !httpMethod.equals("GET")) continue;
            getFound = true;
        }
        allowedMethods.add("OPTIONS");
        if (getFound) {
            allowedMethods.add("HEAD");
        }
        return allowedMethods;
    }

    private static boolean isMethodOverridden(ResourceMethod resourceMethod, String httpMethod, MediaType consumes, MediaType produces) {
        if (!resourceMethod.getHttpMethod().equals(httpMethod)) {
            return false;
        }
        boolean consumesMatch = ModelProcessorUtil.overrides(resourceMethod.getConsumedTypes(), consumes);
        boolean producesMatch = ModelProcessorUtil.overrides(resourceMethod.getProducedTypes(), produces);
        return consumesMatch && producesMatch;
    }

    private static boolean overrides(List<MediaType> mediaTypes, MediaType mediaType) {
        if (mediaTypes.isEmpty()) {
            return true;
        }
        for (MediaType mt : mediaTypes) {
            if (!ModelProcessorUtil.overrides(mt, mediaType)) continue;
            return true;
        }
        return false;
    }

    private static boolean overrides(MediaType mt1, MediaType mt2) {
        return mt1.isWildcardType() || mt1.getType().equals(mt2.getType()) && (mt1.isWildcardSubtype() || mt1.getSubtype().equals(mt2.getSubtype()));
    }

    public static ResourceModel.Builder enhanceResourceModel(ResourceModel resourceModel, boolean subResourceModel, List<Method> methods, boolean extendedFlag) {
        ResourceModel.Builder newModelBuilder = new ResourceModel.Builder(resourceModel, subResourceModel);
        for (RuntimeResource resource : resourceModel.getRuntimeResourceModel().getRuntimeResources()) {
            ModelProcessorUtil.enhanceResource(resource, newModelBuilder, methods, extendedFlag);
        }
        return newModelBuilder;
    }

    public static void enhanceResource(RuntimeResource resource, ResourceModel.Builder enhancedModelBuilder, List<Method> methods, boolean extended) {
        Resource firstResource = resource.getResources().get(0);
        if (ModelProcessorUtil.methodsSuitableForResource(firstResource, methods)) {
            for (Method method2 : methods) {
                Resource parentResource;
                HashSet<MediaType> produces = new HashSet<MediaType>(method2.produces);
                for (ResourceMethod resourceMethod : resource.getResourceMethods()) {
                    for (MediaType produce : method2.produces) {
                        if (!ModelProcessorUtil.isMethodOverridden(resourceMethod, method2.httpMethod, (MediaType)method2.consumes.get(0), produce)) continue;
                        produces.remove(produce);
                    }
                }
                if (produces.isEmpty() || (parentResource = resource.getParentResources().get(0)) != null && method2.path != null) continue;
                Resource.Builder resourceBuilder = Resource.builder(firstResource.getPath());
                Resource.Builder builder = method2.path != null ? resourceBuilder.addChildResource(method2.path) : resourceBuilder;
                ResourceMethod.Builder methodBuilder = builder.addMethod(method2.httpMethod).consumes(method2.consumes).produces(produces);
                if (method2.inflector != null) {
                    methodBuilder.handledBy(method2.inflector);
                } else {
                    methodBuilder.handledBy(method2.inflectorClass);
                }
                methodBuilder.extended(extended);
                Resource newResource = resourceBuilder.build();
                if (parentResource != null) {
                    Resource.Builder parentBuilder = Resource.builder(parentResource.getPath());
                    parentBuilder.addChildResource(newResource);
                    enhancedModelBuilder.addResource(parentBuilder.build());
                    continue;
                }
                enhancedModelBuilder.addResource(newResource);
            }
        }
        for (RuntimeResource child : resource.getChildRuntimeResources()) {
            ModelProcessorUtil.enhanceResource(child, enhancedModelBuilder, methods, extended);
        }
    }

    private static boolean methodsSuitableForResource(Resource resource, List<Method> methods) {
        if (!resource.getResourceMethods().isEmpty()) {
            return true;
        }
        if (resource.getHandlerInstances().isEmpty() && resource.getHandlerClasses().isEmpty()) {
            for (Method method2 : methods) {
                if ("HEAD".equals(method2.httpMethod) || "OPTIONS".equals(method2.httpMethod)) continue;
                return true;
            }
        }
        return false;
    }

    public static class Method {
        private final String httpMethod;
        private final String path;
        private final List<MediaType> consumes;
        private final List<MediaType> produces;
        private final Class<? extends Inflector<ContainerRequestContext, Response>> inflectorClass;
        private final Inflector<ContainerRequestContext, Response> inflector;

        public Method(String path, String httpMethod, MediaType consumes, MediaType produces, Class<? extends Inflector<ContainerRequestContext, Response>> inflector) {
            this(path, httpMethod, Collections.singletonList(consumes), Collections.singletonList(produces), inflector);
        }

        public Method(String path, String httpMethod, List<MediaType> consumes, List<MediaType> produces, Class<? extends Inflector<ContainerRequestContext, Response>> inflectorClass) {
            this.path = path;
            this.httpMethod = httpMethod;
            this.consumes = consumes;
            this.produces = produces;
            this.inflectorClass = inflectorClass;
            this.inflector = null;
        }

        public Method(String httpMethod, MediaType consumes, MediaType produces, Class<? extends Inflector<ContainerRequestContext, Response>> inflector) {
            this(null, httpMethod, consumes, produces, inflector);
        }

        public Method(String httpMethod, List<MediaType> consumes, List<MediaType> produces, Class<? extends Inflector<ContainerRequestContext, Response>> inflector) {
            this(null, httpMethod, consumes, produces, inflector);
        }

        public Method(String path, String httpMethod, List<MediaType> consumes, List<MediaType> produces, Inflector<ContainerRequestContext, Response> inflector) {
            this.path = path;
            this.httpMethod = httpMethod;
            this.consumes = consumes;
            this.produces = produces;
            this.inflectorClass = null;
            this.inflector = inflector;
        }

        public Method(String path, String httpMethod, MediaType consumes, MediaType produces, Inflector<ContainerRequestContext, Response> inflector) {
            this(path, httpMethod, Collections.singletonList(consumes), Collections.singletonList(produces), inflector);
        }

        public Method(String httpMethod, MediaType consumes, MediaType produces, Inflector<ContainerRequestContext, Response> inflector) {
            this(null, httpMethod, consumes, produces, inflector);
        }

        public Method(String httpMethod, List<MediaType> consumes, List<MediaType> produces, Inflector<ContainerRequestContext, Response> inflector) {
            this(null, httpMethod, consumes, produces, inflector);
        }
    }
}

