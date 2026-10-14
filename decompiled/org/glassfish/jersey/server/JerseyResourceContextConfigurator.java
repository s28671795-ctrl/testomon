/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server;

import java.util.function.Consumer;
import java.util.function.Function;
import javax.ws.rs.container.ResourceContext;
import org.glassfish.jersey.internal.BootstrapBag;
import org.glassfish.jersey.internal.BootstrapConfigurator;
import org.glassfish.jersey.internal.inject.Binding;
import org.glassfish.jersey.internal.inject.Bindings;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.internal.inject.Injections;
import org.glassfish.jersey.internal.inject.InstanceBinding;
import org.glassfish.jersey.server.ExtendedResourceContext;
import org.glassfish.jersey.server.ServerBootstrapBag;
import org.glassfish.jersey.server.internal.JerseyResourceContext;

class JerseyResourceContextConfigurator
implements BootstrapConfigurator {
    JerseyResourceContextConfigurator() {
    }

    @Override
    public void init(InjectionManager injectionManager, BootstrapBag bootstrapBag) {
        ServerBootstrapBag serverBag = (ServerBootstrapBag)bootstrapBag;
        Consumer<Binding> registerBinding = injectionManager::register;
        Function<Class, Object> getOrCreateInstance = clazz -> Injections.getOrCreate(injectionManager, clazz);
        Consumer<Object> injectInstance = injectionManager::inject;
        JerseyResourceContext resourceContext = new JerseyResourceContext(getOrCreateInstance, injectInstance, registerBinding);
        InstanceBinding resourceContextBinding = (InstanceBinding)((InstanceBinding)Bindings.service(resourceContext).to(ResourceContext.class)).to(ExtendedResourceContext.class);
        injectionManager.register(resourceContextBinding);
        serverBag.setResourceContext(resourceContext);
    }

    @Override
    public void postInit(InjectionManager injectionManager, BootstrapBag bootstrapBag) {
    }
}

