/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import io.grpc.Configurator;
import io.grpc.ConfiguratorRegistry;
import io.grpc.Internal;
import io.grpc.InternalConfigurator;
import io.grpc.ManagedChannelBuilder;
import io.grpc.ServerBuilder;
import java.util.List;

@Internal
public final class InternalConfiguratorRegistry {
    private InternalConfiguratorRegistry() {
    }

    public static void setConfigurators(List<InternalConfigurator> configurators) {
        ConfiguratorRegistry.getDefaultRegistry().setConfigurators(configurators);
    }

    public static List<?> getConfigurators() {
        return ConfiguratorRegistry.getDefaultRegistry().getConfigurators();
    }

    public static void configureChannelBuilder(ManagedChannelBuilder<?> channelBuilder) {
        for (Configurator configurator2 : ConfiguratorRegistry.getDefaultRegistry().getConfigurators()) {
            configurator2.configureChannelBuilder(channelBuilder);
        }
    }

    public static void configureServerBuilder(ServerBuilder<?> serverBuilder) {
        for (Configurator configurator2 : ConfiguratorRegistry.getDefaultRegistry().getConfigurators()) {
            configurator2.configureServerBuilder(serverBuilder);
        }
    }

    public static boolean wasSetConfiguratorsCalled() {
        return ConfiguratorRegistry.getDefaultRegistry().wasSetConfiguratorsCalled();
    }
}

