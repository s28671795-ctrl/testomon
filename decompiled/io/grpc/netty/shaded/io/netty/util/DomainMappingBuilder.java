/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.util;

import io.grpc.netty.shaded.io.netty.util.DomainNameMapping;
import io.grpc.netty.shaded.io.netty.util.DomainNameMappingBuilder;

@Deprecated
public final class DomainMappingBuilder<V> {
    private final DomainNameMappingBuilder<V> builder;

    public DomainMappingBuilder(V defaultValue) {
        this.builder = new DomainNameMappingBuilder<V>(defaultValue);
    }

    public DomainMappingBuilder(int initialCapacity, V defaultValue) {
        this.builder = new DomainNameMappingBuilder<V>(initialCapacity, defaultValue);
    }

    public DomainMappingBuilder<V> add(String hostname, V output) {
        this.builder.add(hostname, output);
        return this;
    }

    public DomainNameMapping<V> build() {
        return this.builder.build();
    }
}

