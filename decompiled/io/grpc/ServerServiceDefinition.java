/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import com.google.common.base.Preconditions;
import io.grpc.Internal;
import io.grpc.MethodDescriptor;
import io.grpc.ServerCallHandler;
import io.grpc.ServerMethodDefinition;
import io.grpc.ServiceDescriptor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class ServerServiceDefinition {
    private final ServiceDescriptor serviceDescriptor;
    private final Map<String, ServerMethodDefinition<?, ?>> methods;

    public static Builder builder(String serviceName) {
        return new Builder(serviceName);
    }

    public static Builder builder(ServiceDescriptor serviceDescriptor) {
        return new Builder(serviceDescriptor);
    }

    private ServerServiceDefinition(ServiceDescriptor serviceDescriptor, Map<String, ServerMethodDefinition<?, ?>> methods) {
        this.serviceDescriptor = Preconditions.checkNotNull(serviceDescriptor, "serviceDescriptor");
        this.methods = Collections.unmodifiableMap(new HashMap(methods));
    }

    public ServiceDescriptor getServiceDescriptor() {
        return this.serviceDescriptor;
    }

    public Collection<ServerMethodDefinition<?, ?>> getMethods() {
        return this.methods.values();
    }

    @Internal
    public ServerMethodDefinition<?, ?> getMethod(String methodName) {
        return this.methods.get(methodName);
    }

    public static final class Builder {
        private final String serviceName;
        private final ServiceDescriptor serviceDescriptor;
        private final Map<String, ServerMethodDefinition<?, ?>> methods = new HashMap();

        private Builder(String serviceName) {
            this.serviceName = Preconditions.checkNotNull(serviceName, "serviceName");
            this.serviceDescriptor = null;
        }

        private Builder(ServiceDescriptor serviceDescriptor) {
            this.serviceDescriptor = Preconditions.checkNotNull(serviceDescriptor, "serviceDescriptor");
            this.serviceName = serviceDescriptor.getName();
        }

        public <ReqT, RespT> Builder addMethod(MethodDescriptor<ReqT, RespT> method2, ServerCallHandler<ReqT, RespT> handler) {
            return this.addMethod(ServerMethodDefinition.create(Preconditions.checkNotNull(method2, "method must not be null"), Preconditions.checkNotNull(handler, "handler must not be null")));
        }

        public <ReqT, RespT> Builder addMethod(ServerMethodDefinition<ReqT, RespT> def) {
            MethodDescriptor<ReqT, RespT> method2 = def.getMethodDescriptor();
            Preconditions.checkArgument(this.serviceName.equals(method2.getServiceName()), "Method name should be prefixed with service name and separated with '/'. Expected service name: '%s'. Actual fully qualifed method name: '%s'.", (Object)this.serviceName, (Object)method2.getFullMethodName());
            String name = method2.getFullMethodName();
            Preconditions.checkState(!this.methods.containsKey(name), "Method by same name already registered: %s", (Object)name);
            this.methods.put(name, def);
            return this;
        }

        public ServerServiceDefinition build() {
            ServiceDescriptor serviceDescriptor = this.serviceDescriptor;
            if (serviceDescriptor == null) {
                ArrayList methodDescriptors = new ArrayList(this.methods.size());
                for (ServerMethodDefinition serverMethodDefinition : this.methods.values()) {
                    methodDescriptors.add(serverMethodDefinition.getMethodDescriptor());
                }
                serviceDescriptor = new ServiceDescriptor(this.serviceName, methodDescriptors);
            }
            HashMap tmpMethods = new HashMap(this.methods);
            for (MethodDescriptor methodDescriptor : serviceDescriptor.getMethods()) {
                ServerMethodDefinition removed = (ServerMethodDefinition)tmpMethods.remove(methodDescriptor.getFullMethodName());
                if (removed == null) {
                    throw new IllegalStateException("No method bound for descriptor entry " + methodDescriptor.getFullMethodName());
                }
                if (removed.getMethodDescriptor() == methodDescriptor) continue;
                throw new IllegalStateException("Bound method for " + methodDescriptor.getFullMethodName() + " not same instance as method in service descriptor");
            }
            if (tmpMethods.size() > 0) {
                throw new IllegalStateException("No entry in descriptor matching bound method " + ((ServerMethodDefinition)tmpMethods.values().iterator().next()).getMethodDescriptor().getFullMethodName());
            }
            return new ServerServiceDefinition(serviceDescriptor, this.methods);
        }
    }
}

