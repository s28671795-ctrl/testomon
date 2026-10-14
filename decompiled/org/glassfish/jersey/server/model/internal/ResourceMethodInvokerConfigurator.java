/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.model.internal;

import java.util.Arrays;
import java.util.List;
import org.glassfish.jersey.internal.BootstrapBag;
import org.glassfish.jersey.internal.BootstrapConfigurator;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.server.ServerBootstrapBag;
import org.glassfish.jersey.server.internal.inject.ConfiguredValidator;
import org.glassfish.jersey.server.model.ResourceMethodInvoker;
import org.glassfish.jersey.server.model.internal.JavaResourceMethodDispatcherProvider;
import org.glassfish.jersey.server.model.internal.ResourceMethodDispatcherFactory;
import org.glassfish.jersey.server.model.internal.ResourceMethodInvocationHandlerFactory;
import org.glassfish.jersey.server.model.internal.VoidVoidDispatcherProvider;
import org.glassfish.jersey.server.spi.internal.ResourceMethodDispatcher;

public class ResourceMethodInvokerConfigurator
implements BootstrapConfigurator {
    @Override
    public void init(InjectionManager injectionManager, BootstrapBag bootstrapBag) {
    }

    @Override
    public void postInit(InjectionManager injectionManager, BootstrapBag bootstrapBag) {
        ServerBootstrapBag serverBag = (ServerBootstrapBag)bootstrapBag;
        List<ResourceMethodDispatcher.Provider> providers = Arrays.asList(new VoidVoidDispatcherProvider(serverBag.getResourceContext()), new JavaResourceMethodDispatcherProvider(serverBag.getValueParamProviders()));
        ResourceMethodInvoker.Builder builder = new ResourceMethodInvoker.Builder().injectionManager(injectionManager).resourceMethodDispatcherFactory(new ResourceMethodDispatcherFactory(providers)).resourceMethodInvocationHandlerFactory(new ResourceMethodInvocationHandlerFactory(injectionManager)).configuration(bootstrapBag.getConfiguration()).configurationValidator(() -> injectionManager.getInstance(ConfiguredValidator.class));
        serverBag.setResourceMethodInvokerBuilder(builder);
    }
}

