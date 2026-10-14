/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import io.grpc.ExperimentalApi;
import io.grpc.NameResolver;
import io.grpc.NameResolverProvider;
import io.grpc.ServiceProviders;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

@ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/4159")
@ThreadSafe
public final class NameResolverRegistry {
    private static final Logger logger = Logger.getLogger(NameResolverRegistry.class.getName());
    private static NameResolverRegistry instance;
    private final NameResolver.Factory factory = new NameResolverFactory();
    private static final String UNKNOWN_SCHEME = "unknown";
    @GuardedBy(value="this")
    private String defaultScheme = "unknown";
    @GuardedBy(value="this")
    private final LinkedHashSet<NameResolverProvider> allProviders = new LinkedHashSet();
    @GuardedBy(value="this")
    private ImmutableMap<String, NameResolverProvider> effectiveProviders = ImmutableMap.of();

    public synchronized String getDefaultScheme() {
        return this.defaultScheme;
    }

    public NameResolverProvider getProviderForScheme(String scheme2) {
        if (scheme2 == null) {
            return null;
        }
        return this.providers().get(scheme2.toLowerCase(Locale.US));
    }

    public synchronized void register(NameResolverProvider provider) {
        this.addProvider(provider);
        this.refreshProviders();
    }

    private synchronized void addProvider(NameResolverProvider provider) {
        Preconditions.checkArgument(provider.isAvailable(), "isAvailable() returned false");
        this.allProviders.add(provider);
    }

    public synchronized void deregister(NameResolverProvider provider) {
        this.allProviders.remove(provider);
        this.refreshProviders();
    }

    private synchronized void refreshProviders() {
        HashMap<String, NameResolverProvider> refreshedProviders = new HashMap<String, NameResolverProvider>();
        int maxPriority2 = Integer.MIN_VALUE;
        String refreshedDefaultScheme = UNKNOWN_SCHEME;
        for (NameResolverProvider provider : this.allProviders) {
            String scheme2 = provider.getScheme();
            NameResolverProvider existing = (NameResolverProvider)refreshedProviders.get(scheme2);
            if (existing == null || existing.priority() < provider.priority()) {
                refreshedProviders.put(scheme2, provider);
            }
            if (maxPriority2 >= provider.priority()) continue;
            maxPriority2 = provider.priority();
            refreshedDefaultScheme = provider.getScheme();
        }
        this.effectiveProviders = ImmutableMap.copyOf(refreshedProviders);
        this.defaultScheme = refreshedDefaultScheme;
    }

    public static synchronized NameResolverRegistry getDefaultRegistry() {
        if (instance == null) {
            List<NameResolverProvider> providerList = ServiceProviders.loadAll(NameResolverProvider.class, NameResolverRegistry.getHardCodedClasses(), NameResolverProvider.class.getClassLoader(), new NameResolverPriorityAccessor());
            if (providerList.isEmpty()) {
                logger.warning("No NameResolverProviders found via ServiceLoader, including for DNS. This is probably due to a broken build. If using ProGuard, check your configuration");
            }
            instance = new NameResolverRegistry();
            for (NameResolverProvider provider : providerList) {
                logger.fine("Service loader found " + provider);
                instance.addProvider(provider);
            }
            instance.refreshProviders();
        }
        return instance;
    }

    @VisibleForTesting
    synchronized Map<String, NameResolverProvider> providers() {
        return this.effectiveProviders;
    }

    public NameResolver.Factory asFactory() {
        return this.factory;
    }

    @VisibleForTesting
    static List<Class<?>> getHardCodedClasses() {
        ArrayList list = new ArrayList();
        try {
            list.add(Class.forName("io.grpc.internal.DnsNameResolverProvider"));
        }
        catch (ClassNotFoundException e) {
            logger.log(Level.FINE, "Unable to find DNS NameResolver", e);
        }
        return Collections.unmodifiableList(list);
    }

    private static final class NameResolverPriorityAccessor
    implements ServiceProviders.PriorityAccessor<NameResolverProvider> {
        private NameResolverPriorityAccessor() {
        }

        @Override
        public boolean isAvailable(NameResolverProvider provider) {
            return provider.isAvailable();
        }

        @Override
        public int getPriority(NameResolverProvider provider) {
            return provider.priority();
        }
    }

    private final class NameResolverFactory
    extends NameResolver.Factory {
        private NameResolverFactory() {
        }

        @Override
        @Nullable
        public NameResolver newNameResolver(URI targetUri, NameResolver.Args args2) {
            NameResolverProvider provider = NameResolverRegistry.this.getProviderForScheme(targetUri.getScheme());
            return provider == null ? null : provider.newNameResolver(targetUri, args2);
        }

        @Override
        public String getDefaultScheme() {
            return NameResolverRegistry.this.getDefaultScheme();
        }
    }
}

