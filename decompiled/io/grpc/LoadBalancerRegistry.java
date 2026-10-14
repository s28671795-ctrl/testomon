/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import io.grpc.ExperimentalApi;
import io.grpc.LoadBalancerProvider;
import io.grpc.ServiceProviders;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

@ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1771")
@ThreadSafe
public final class LoadBalancerRegistry {
    private static final Logger logger = Logger.getLogger(LoadBalancerRegistry.class.getName());
    private static LoadBalancerRegistry instance;
    private static final Iterable<Class<?>> HARDCODED_CLASSES;
    private final LinkedHashSet<LoadBalancerProvider> allProviders = new LinkedHashSet();
    private final LinkedHashMap<String, LoadBalancerProvider> effectiveProviders = new LinkedHashMap();

    public synchronized void register(LoadBalancerProvider provider) {
        this.addProvider(provider);
        this.refreshProviderMap();
    }

    private synchronized void addProvider(LoadBalancerProvider provider) {
        Preconditions.checkArgument(provider.isAvailable(), "isAvailable() returned false");
        this.allProviders.add(provider);
    }

    public synchronized void deregister(LoadBalancerProvider provider) {
        this.allProviders.remove(provider);
        this.refreshProviderMap();
    }

    private synchronized void refreshProviderMap() {
        this.effectiveProviders.clear();
        for (LoadBalancerProvider provider : this.allProviders) {
            String policy = provider.getPolicyName();
            LoadBalancerProvider existing = this.effectiveProviders.get(policy);
            if (existing != null && existing.getPriority() >= provider.getPriority()) continue;
            this.effectiveProviders.put(policy, provider);
        }
    }

    public static synchronized LoadBalancerRegistry getDefaultRegistry() {
        if (instance == null) {
            List<LoadBalancerProvider> providerList = ServiceProviders.loadAll(LoadBalancerProvider.class, HARDCODED_CLASSES, LoadBalancerProvider.class.getClassLoader(), new LoadBalancerPriorityAccessor());
            instance = new LoadBalancerRegistry();
            for (LoadBalancerProvider provider : providerList) {
                logger.fine("Service loader found " + provider);
                instance.addProvider(provider);
            }
            instance.refreshProviderMap();
        }
        return instance;
    }

    @Nullable
    public synchronized LoadBalancerProvider getProvider(String policy) {
        return this.effectiveProviders.get(Preconditions.checkNotNull(policy, "policy"));
    }

    @VisibleForTesting
    synchronized Map<String, LoadBalancerProvider> providers() {
        return new LinkedHashMap<String, LoadBalancerProvider>(this.effectiveProviders);
    }

    @VisibleForTesting
    static List<Class<?>> getHardCodedClasses() {
        ArrayList list = new ArrayList();
        try {
            list.add(Class.forName("io.grpc.internal.PickFirstLoadBalancerProvider"));
        }
        catch (ClassNotFoundException e) {
            logger.log(Level.WARNING, "Unable to find pick-first LoadBalancer", e);
        }
        try {
            list.add(Class.forName("io.grpc.util.SecretRoundRobinLoadBalancerProvider$Provider"));
        }
        catch (ClassNotFoundException e) {
            logger.log(Level.FINE, "Unable to find round-robin LoadBalancer", e);
        }
        return Collections.unmodifiableList(list);
    }

    static {
        HARDCODED_CLASSES = LoadBalancerRegistry.getHardCodedClasses();
    }

    private static final class LoadBalancerPriorityAccessor
    implements ServiceProviders.PriorityAccessor<LoadBalancerProvider> {
        LoadBalancerPriorityAccessor() {
        }

        @Override
        public boolean isAvailable(LoadBalancerProvider provider) {
            return provider.isAvailable();
        }

        @Override
        public int getPriority(LoadBalancerProvider provider) {
            return provider.getPriority();
        }
    }
}

