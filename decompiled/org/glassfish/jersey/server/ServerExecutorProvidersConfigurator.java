/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server;

import org.glassfish.jersey.internal.BootstrapBag;
import org.glassfish.jersey.internal.inject.Bindings;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.internal.inject.InstanceBinding;
import org.glassfish.jersey.model.internal.ComponentBag;
import org.glassfish.jersey.model.internal.ManagedObjectsFinalizer;
import org.glassfish.jersey.process.internal.AbstractExecutorProvidersConfigurator;
import org.glassfish.jersey.server.BackgroundScheduler;
import org.glassfish.jersey.server.BackgroundSchedulerLiteral;
import org.glassfish.jersey.server.ManagedAsyncExecutor;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerBootstrapBag;
import org.glassfish.jersey.spi.ExecutorServiceProvider;
import org.glassfish.jersey.spi.ScheduledExecutorServiceProvider;
import org.glassfish.jersey.spi.ScheduledThreadPoolExecutorProvider;
import org.glassfish.jersey.spi.ThreadPoolExecutorProvider;

class ServerExecutorProvidersConfigurator
extends AbstractExecutorProvidersConfigurator {
    ServerExecutorProvidersConfigurator() {
    }

    @Override
    public void init(InjectionManager injectionManager, BootstrapBag bootstrapBag) {
        ServerBootstrapBag serverBag = (ServerBootstrapBag)bootstrapBag;
        ResourceConfig runtimeConfig = serverBag.getRuntimeConfig();
        ComponentBag componentBag = runtimeConfig.getComponentBag();
        ManagedObjectsFinalizer finalizer = serverBag.getManagedObjectsFinalizer();
        DefaultBackgroundSchedulerProvider defaultScheduledExecutorProvider = new DefaultBackgroundSchedulerProvider();
        InstanceBinding schedulerBinding = (InstanceBinding)((InstanceBinding)Bindings.service(defaultScheduledExecutorProvider).to(ScheduledExecutorServiceProvider.class)).qualifiedBy(BackgroundSchedulerLiteral.INSTANCE);
        injectionManager.register(schedulerBinding);
        finalizer.registerForPreDestroyCall(defaultScheduledExecutorProvider);
        DefaultManagedAsyncExecutorProvider defaultAsyncExecutorProvider = new DefaultManagedAsyncExecutorProvider();
        InstanceBinding executorBinding = (InstanceBinding)Bindings.service(defaultAsyncExecutorProvider).to(ExecutorServiceProvider.class);
        injectionManager.register(executorBinding);
        finalizer.registerForPreDestroyCall(defaultAsyncExecutorProvider);
        this.registerExecutors(injectionManager, componentBag, defaultAsyncExecutorProvider, defaultScheduledExecutorProvider);
    }

    @ManagedAsyncExecutor
    private static class DefaultManagedAsyncExecutorProvider
    extends ThreadPoolExecutorProvider {
        public DefaultManagedAsyncExecutorProvider() {
            super("jersey-server-managed-async-executor");
        }
    }

    @BackgroundScheduler
    private static class DefaultBackgroundSchedulerProvider
    extends ScheduledThreadPoolExecutorProvider {
        public DefaultBackgroundSchedulerProvider() {
            super("jersey-background-task-scheduler");
        }

        @Override
        protected int getCorePoolSize() {
            return 1;
        }
    }
}

