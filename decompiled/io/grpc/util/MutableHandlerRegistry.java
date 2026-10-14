/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.util;

import io.grpc.BindableService;
import io.grpc.ExperimentalApi;
import io.grpc.HandlerRegistry;
import io.grpc.MethodDescriptor;
import io.grpc.ServerMethodDefinition;
import io.grpc.ServerServiceDefinition;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public final class MutableHandlerRegistry
extends HandlerRegistry {
    private final ConcurrentMap<String, ServerServiceDefinition> services = new ConcurrentHashMap<String, ServerServiceDefinition>();

    @Nullable
    public ServerServiceDefinition addService(ServerServiceDefinition service2) {
        return this.services.put(service2.getServiceDescriptor().getName(), service2);
    }

    @Nullable
    public ServerServiceDefinition addService(BindableService bindableService) {
        return this.addService(bindableService.bindService());
    }

    public boolean removeService(ServerServiceDefinition service2) {
        return this.services.remove(service2.getServiceDescriptor().getName(), service2);
    }

    @Override
    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/2222")
    public List<ServerServiceDefinition> getServices() {
        return Collections.unmodifiableList(new ArrayList(this.services.values()));
    }

    @Override
    @Nullable
    public ServerMethodDefinition<?, ?> lookupMethod(String methodName, @Nullable String authority) {
        String serviceName = MethodDescriptor.extractFullServiceName(methodName);
        if (serviceName == null) {
            return null;
        }
        ServerServiceDefinition service2 = (ServerServiceDefinition)this.services.get(serviceName);
        if (service2 == null) {
            return null;
        }
        return service2.getMethod(methodName);
    }
}

