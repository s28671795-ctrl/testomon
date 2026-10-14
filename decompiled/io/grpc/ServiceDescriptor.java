/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import io.grpc.ExperimentalApi;
import io.grpc.MethodDescriptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import javax.annotation.Nullable;

public final class ServiceDescriptor {
    private final String name;
    private final Collection<MethodDescriptor<?, ?>> methods;
    private final Object schemaDescriptor;

    public ServiceDescriptor(String name, MethodDescriptor<?, ?> ... methods) {
        this(name, Arrays.asList(methods));
    }

    public ServiceDescriptor(String name, Collection<MethodDescriptor<?, ?>> methods) {
        this(ServiceDescriptor.newBuilder(name).addAllMethods(Preconditions.checkNotNull(methods, "methods")));
    }

    private ServiceDescriptor(Builder b) {
        this.name = b.name;
        ServiceDescriptor.validateMethodNames(this.name, b.methods);
        this.methods = Collections.unmodifiableList(new ArrayList(b.methods));
        this.schemaDescriptor = b.schemaDescriptor;
    }

    public String getName() {
        return this.name;
    }

    public Collection<MethodDescriptor<?, ?>> getMethods() {
        return this.methods;
    }

    @Nullable
    @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/2222")
    public Object getSchemaDescriptor() {
        return this.schemaDescriptor;
    }

    static void validateMethodNames(String serviceName, Collection<MethodDescriptor<?, ?>> methods) {
        HashSet<String> allNames = new HashSet<String>(methods.size());
        for (MethodDescriptor<?, ?> method2 : methods) {
            Preconditions.checkNotNull(method2, "method");
            String methodServiceName = method2.getServiceName();
            Preconditions.checkArgument(serviceName.equals(methodServiceName), "service names %s != %s", (Object)methodServiceName, (Object)serviceName);
            Preconditions.checkArgument(allNames.add(method2.getFullMethodName()), "duplicate name %s", (Object)method2.getFullMethodName());
        }
    }

    public static Builder newBuilder(String name) {
        return new Builder(name);
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("name", this.name).add("schemaDescriptor", this.schemaDescriptor).add("methods", this.methods).omitNullValues().toString();
    }

    public static final class Builder {
        private String name;
        private List<MethodDescriptor<?, ?>> methods = new ArrayList();
        private Object schemaDescriptor;

        private Builder(String name) {
            this.setName(name);
        }

        @ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/2666")
        public Builder setName(String name) {
            this.name = Preconditions.checkNotNull(name, "name");
            return this;
        }

        public Builder addMethod(MethodDescriptor<?, ?> method2) {
            this.methods.add(Preconditions.checkNotNull(method2, "method"));
            return this;
        }

        private Builder addAllMethods(Collection<MethodDescriptor<?, ?>> methods) {
            this.methods.addAll(methods);
            return this;
        }

        public Builder setSchemaDescriptor(@Nullable Object schemaDescriptor) {
            this.schemaDescriptor = schemaDescriptor;
            return this;
        }

        public ServiceDescriptor build() {
            return new ServiceDescriptor(this);
        }
    }
}

