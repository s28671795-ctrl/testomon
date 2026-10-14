/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import io.grpc.HandlerRegistry;
import io.grpc.ServerMethodDefinition;
import io.grpc.ServerServiceDefinition;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

final class InternalHandlerRegistry
extends HandlerRegistry {
    private final List<ServerServiceDefinition> services;
    private final Map<String, ServerMethodDefinition<?, ?>> methods;

    private InternalHandlerRegistry(List<ServerServiceDefinition> services, Map<String, ServerMethodDefinition<?, ?>> methods) {
        this.services = services;
        this.methods = methods;
    }

    @Override
    public List<ServerServiceDefinition> getServices() {
        return this.services;
    }

    @Override
    @Nullable
    public ServerMethodDefinition<?, ?> lookupMethod(String methodName, @Nullable String authority) {
        return this.methods.get(methodName);
    }

    static final class Builder {
        private final HashMap<String, ServerServiceDefinition> services = new LinkedHashMap<String, ServerServiceDefinition>();

        Builder() {
        }

        Builder addService(ServerServiceDefinition service2) {
            this.services.put(service2.getServiceDescriptor().getName(), service2);
            return this;
        }

        InternalHandlerRegistry build() {
            HashMap map = new HashMap();
            for (ServerServiceDefinition service2 : this.services.values()) {
                for (ServerMethodDefinition<?, ?> method2 : service2.getMethods()) {
                    map.put(method2.getMethodDescriptor().getFullMethodName(), method2);
                }
            }
            return new InternalHandlerRegistry(Collections.unmodifiableList(new ArrayList<ServerServiceDefinition>(this.services.values())), Collections.unmodifiableMap(map));
        }
    }
}

