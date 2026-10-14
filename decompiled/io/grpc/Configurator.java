/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import io.grpc.ManagedChannelBuilder;
import io.grpc.ServerBuilder;

interface Configurator {
    default public void configureChannelBuilder(ManagedChannelBuilder<?> channelBuilder) {
    }

    default public void configureServerBuilder(ServerBuilder<?> serverBuilder) {
    }
}

