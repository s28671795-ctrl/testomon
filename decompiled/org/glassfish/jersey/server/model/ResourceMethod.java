/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.ws.rs.NameBinding;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.message.internal.MediaTypes;
import org.glassfish.jersey.model.NameBound;
import org.glassfish.jersey.process.Inflector;
import org.glassfish.jersey.server.model.Consuming;
import org.glassfish.jersey.server.model.Invocable;
import org.glassfish.jersey.server.model.MethodHandler;
import org.glassfish.jersey.server.model.Parameter;
import org.glassfish.jersey.server.model.Producing;
import org.glassfish.jersey.server.model.Resource;
import org.glassfish.jersey.server.model.ResourceModelComponent;
import org.glassfish.jersey.server.model.ResourceModelVisitor;
import org.glassfish.jersey.server.model.Suspendable;
import org.glassfish.jersey.uri.PathPattern;

public final class ResourceMethod
implements ResourceModelComponent,
Producing,
Consuming,
Suspendable,
NameBound {
    private final Data data;
    private final Resource parent;

    static List<ResourceMethod> transform(Resource parent, List<Data> list) {
        return list.stream().map(data1 -> data1 == null ? null : new ResourceMethod(parent, (Data)data1)).collect(Collectors.toList());
    }

    ResourceMethod(Resource parent, Data data) {
        this.parent = parent;
        this.data = data;
    }

    Data getData() {
        return this.data;
    }

    public Resource getParent() {
        return this.parent;
    }

    public JaxrsType getType() {
        return this.data.getType();
    }

    public String getHttpMethod() {
        return this.data.getHttpMethod();
    }

    public Invocable getInvocable() {
        return this.data.getInvocable();
    }

    public boolean isExtended() {
        return this.data.extended;
    }

    @Override
    public List<MediaType> getConsumedTypes() {
        return this.data.getConsumedTypes();
    }

    @Override
    public List<MediaType> getProducedTypes() {
        return this.data.getProducedTypes();
    }

    @Override
    public long getSuspendTimeout() {
        return this.data.getSuspendTimeout();
    }

    @Override
    public TimeUnit getSuspendTimeoutUnit() {
        return this.data.getSuspendTimeoutUnit();
    }

    @Override
    public boolean isSuspendDeclared() {
        return this.data.isSuspended();
    }

    public boolean isSse() {
        return this.data.isSse();
    }

    @Override
    public boolean isManagedAsyncDeclared() {
        return this.data.isManagedAsync();
    }

    @Override
    public List<? extends ResourceModelComponent> getComponents() {
        return Arrays.asList(this.data.getInvocable());
    }

    @Override
    public void accept(ResourceModelVisitor visitor) {
        visitor.visitResourceMethod(this);
    }

    @Override
    public boolean isNameBound() {
        return !this.data.getNameBindings().isEmpty();
    }

    @Override
    public Collection<Class<? extends Annotation>> getNameBindings() {
        return this.data.getNameBindings();
    }

    public String toString() {
        return "ResourceMethod{" + this.data.toString() + '}';
    }

    static class Data {
        private final JaxrsType type;
        private final String httpMethod;
        private final List<MediaType> consumedTypes;
        private final List<MediaType> producedTypes;
        private final boolean managedAsync;
        private final boolean suspended;
        private final boolean sse;
        private final long suspendTimeout;
        private final TimeUnit suspendTimeoutUnit;
        private final Invocable invocable;
        private final Collection<Class<? extends Annotation>> nameBindings;
        private final boolean extended;

        private Data(String httpMethod, Collection<MediaType> consumedTypes, Collection<MediaType> producedTypes, boolean managedAsync, boolean suspended, boolean sse, long suspendTimeout, TimeUnit suspendTimeoutUnit, Invocable invocable, Collection<Class<? extends Annotation>> nameBindings, boolean extended) {
            this.managedAsync = managedAsync;
            this.type = JaxrsType.classify(httpMethod);
            this.httpMethod = httpMethod == null ? httpMethod : httpMethod.toUpperCase(Locale.ROOT);
            this.consumedTypes = Collections.unmodifiableList(new ArrayList<MediaType>(consumedTypes));
            this.producedTypes = Collections.unmodifiableList(new ArrayList<MediaType>(producedTypes));
            this.invocable = invocable;
            this.suspended = suspended;
            this.sse = sse;
            this.suspendTimeout = suspendTimeout;
            this.suspendTimeoutUnit = suspendTimeoutUnit;
            this.nameBindings = Collections.unmodifiableCollection(new ArrayList<Class<? extends Annotation>>(nameBindings));
            this.extended = extended;
        }

        JaxrsType getType() {
            return this.type;
        }

        String getHttpMethod() {
            return this.httpMethod;
        }

        List<MediaType> getConsumedTypes() {
            return this.consumedTypes;
        }

        List<MediaType> getProducedTypes() {
            return this.producedTypes;
        }

        boolean isManagedAsync() {
            return this.managedAsync;
        }

        boolean isSuspended() {
            return this.suspended;
        }

        boolean isSse() {
            return this.sse;
        }

        long getSuspendTimeout() {
            return this.suspendTimeout;
        }

        TimeUnit getSuspendTimeoutUnit() {
            return this.suspendTimeoutUnit;
        }

        Invocable getInvocable() {
            return this.invocable;
        }

        boolean isExtended() {
            return this.extended;
        }

        Collection<Class<? extends Annotation>> getNameBindings() {
            return this.nameBindings;
        }

        public String toString() {
            return "httpMethod=" + this.httpMethod + ", consumedTypes=" + this.consumedTypes + ", producedTypes=" + this.producedTypes + ", suspended=" + this.suspended + ", suspendTimeout=" + this.suspendTimeout + ", suspendTimeoutUnit=" + (Object)((Object)this.suspendTimeoutUnit) + ", invocable=" + this.invocable + ", nameBindings=" + this.nameBindings;
        }
    }

    public static final class Builder {
        private final Resource.Builder parent;
        private String httpMethod;
        private final Set<MediaType> consumedTypes;
        private final Set<MediaType> producedTypes;
        private boolean managedAsync;
        private boolean sse;
        private boolean suspended;
        private long suspendTimeout;
        private TimeUnit suspendTimeoutUnit;
        private Class<?> handlerClass;
        private Object handlerInstance;
        private final Collection<Parameter> handlerParameters;
        private Method definitionMethod;
        private Method handlingMethod;
        private boolean encodedParams;
        private Type routingResponseType;
        private final Collection<Class<? extends Annotation>> nameBindings;
        private boolean extended;

        Builder(Resource.Builder parent) {
            this.parent = parent;
            this.httpMethod = null;
            this.consumedTypes = new LinkedHashSet<MediaType>();
            this.producedTypes = new LinkedHashSet<MediaType>();
            this.suspended = false;
            this.suspendTimeout = 0L;
            this.suspendTimeoutUnit = TimeUnit.MILLISECONDS;
            this.handlerParameters = new LinkedList<Parameter>();
            this.encodedParams = false;
            this.nameBindings = new LinkedHashSet<Class<? extends Annotation>>();
        }

        Builder(Resource.Builder parent, ResourceMethod originalMethod) {
            this.parent = parent;
            this.consumedTypes = new LinkedHashSet<MediaType>(originalMethod.getConsumedTypes());
            this.producedTypes = new LinkedHashSet<MediaType>(originalMethod.getProducedTypes());
            this.suspended = originalMethod.isSuspendDeclared();
            this.suspendTimeout = originalMethod.getSuspendTimeout();
            this.suspendTimeoutUnit = originalMethod.getSuspendTimeoutUnit();
            this.handlerParameters = new LinkedHashSet<Parameter>(originalMethod.getInvocable().getHandler().getParameters());
            this.nameBindings = originalMethod.getNameBindings();
            this.httpMethod = originalMethod.getHttpMethod();
            this.managedAsync = originalMethod.isManagedAsyncDeclared();
            Invocable invocable = originalMethod.getInvocable();
            this.handlingMethod = invocable.getHandlingMethod();
            this.encodedParams = false;
            this.routingResponseType = invocable.getRoutingResponseType();
            this.extended = originalMethod.isExtended();
            Method handlerMethod = invocable.getDefinitionMethod();
            MethodHandler handler = invocable.getHandler();
            if (handler.isClassBased()) {
                this.handledBy(handler.getHandlerClass(), handlerMethod);
            } else {
                this.handledBy(handler.getHandlerInstance(), handlerMethod);
            }
        }

        public Builder httpMethod(String name) {
            this.httpMethod = name;
            return this;
        }

        public Builder produces(String ... types) {
            return this.produces(MediaTypes.createFrom(types));
        }

        public Builder produces(MediaType ... types) {
            return this.produces(Arrays.asList(types));
        }

        public Builder produces(Collection<MediaType> types) {
            this.producedTypes.addAll(types);
            return this;
        }

        public Builder consumes(String ... types) {
            return this.consumes(MediaTypes.createFrom(types));
        }

        public Builder consumes(MediaType ... types) {
            return this.consumes(Arrays.asList(types));
        }

        public Builder consumes(Collection<MediaType> types) {
            this.consumedTypes.addAll(types);
            return this;
        }

        public Builder nameBindings(Collection<Class<? extends Annotation>> nameBindings) {
            for (Class<? extends Annotation> nameBinding : nameBindings) {
                if (nameBinding.getAnnotation(NameBinding.class) == null) continue;
                this.nameBindings.add(nameBinding);
            }
            return this;
        }

        @SafeVarargs
        public final Builder nameBindings(Class<? extends Annotation> ... nameBindings) {
            return this.nameBindings(Arrays.asList(nameBindings));
        }

        public Builder nameBindings(Annotation ... nameBindings) {
            return this.nameBindings(Arrays.stream(nameBindings).map(Annotation::annotationType).collect(Collectors.toList()));
        }

        public Builder suspended(long timeout2, TimeUnit unit) {
            this.suspended = true;
            this.suspendTimeout = timeout2;
            this.suspendTimeoutUnit = unit;
            return this;
        }

        public Builder sse() {
            this.sse = true;
            return this;
        }

        public Builder managedAsync() {
            this.managedAsync = true;
            return this;
        }

        public Builder encodedParameters(boolean value) {
            this.encodedParams = value;
            return this;
        }

        public Builder handledBy(Class<?> handlerClass, Method method2) {
            this.handlerInstance = null;
            this.handlerClass = handlerClass;
            this.definitionMethod = method2;
            return this;
        }

        public Builder handledBy(Object handlerInstance, Method method2) {
            this.handlerClass = null;
            this.handlerInstance = handlerInstance;
            this.definitionMethod = method2;
            return this;
        }

        public Builder handledBy(Inflector<ContainerRequestContext, ?> inflector) {
            return this.handledBy(inflector, Invocable.APPLY_INFLECTOR_METHOD);
        }

        public Builder handledBy(Class<? extends Inflector> inflectorClass) {
            return this.handledBy(inflectorClass, Invocable.APPLY_INFLECTOR_METHOD);
        }

        public Builder handlerParameters(Collection<Parameter> parameters) {
            this.handlerParameters.addAll(parameters);
            return this;
        }

        public Builder handlingMethod(Method handlingMethod) {
            this.handlingMethod = handlingMethod;
            return this;
        }

        public Builder routingResponseType(Type routingResponseType) {
            this.routingResponseType = routingResponseType;
            return this;
        }

        public Builder extended(boolean extended) {
            this.extended = extended;
            return this;
        }

        public ResourceMethod build() {
            Data methodData = new Data(this.httpMethod, this.consumedTypes, this.producedTypes, this.managedAsync, this.suspended, this.sse, this.suspendTimeout, this.suspendTimeoutUnit, this.createInvocable(), this.nameBindings, this.parent.isExtended() || this.extended);
            this.parent.onBuildMethod(this, methodData);
            return new ResourceMethod(null, methodData);
        }

        private Invocable createInvocable() {
            assert (this.handlerClass != null || this.handlerInstance != null);
            MethodHandler handler = this.handlerClass != null ? MethodHandler.create(this.handlerClass, this.encodedParams, this.handlerParameters) : MethodHandler.create(this.handlerInstance, this.handlerParameters);
            return Invocable.create(handler, this.definitionMethod, this.handlingMethod, this.encodedParams, this.routingResponseType);
        }
    }

    public static enum JaxrsType {
        RESOURCE_METHOD{

            @Override
            PathPattern createPatternFor(String pathTemplate) {
                return PathPattern.END_OF_PATH_PATTERN;
            }
        }
        ,
        SUB_RESOURCE_METHOD{

            @Override
            PathPattern createPatternFor(String pathTemplate) {
                return new PathPattern(pathTemplate, PathPattern.RightHandPath.capturingZeroSegments);
            }
        }
        ,
        SUB_RESOURCE_LOCATOR{

            @Override
            PathPattern createPatternFor(String pathTemplate) {
                return new PathPattern(pathTemplate, PathPattern.RightHandPath.capturingZeroOrMoreSegments);
            }
        };


        abstract PathPattern createPatternFor(String var1);

        private static JaxrsType classify(String httpMethod) {
            if (httpMethod != null && !httpMethod.isEmpty()) {
                return RESOURCE_METHOD;
            }
            return SUB_RESOURCE_LOCATOR;
        }
    }
}

