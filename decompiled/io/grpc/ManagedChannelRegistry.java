/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import io.grpc.ChannelCredentials;
import io.grpc.Internal;
import io.grpc.ManagedChannelBuilder;
import io.grpc.ManagedChannelProvider;
import io.grpc.NameResolverProvider;
import io.grpc.NameResolverRegistry;
import io.grpc.ServiceProviders;
import java.net.SocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

@Internal
@ThreadSafe
public final class ManagedChannelRegistry {
    private static final Logger logger = Logger.getLogger(ManagedChannelRegistry.class.getName());
    private static ManagedChannelRegistry instance;
    @GuardedBy(value="this")
    private final LinkedHashSet<ManagedChannelProvider> allProviders = new LinkedHashSet();
    @GuardedBy(value="this")
    private List<ManagedChannelProvider> effectiveProviders = Collections.emptyList();

    public synchronized void register(ManagedChannelProvider provider) {
        this.addProvider(provider);
        this.refreshProviders();
    }

    private synchronized void addProvider(ManagedChannelProvider provider) {
        Preconditions.checkArgument(provider.isAvailable(), "isAvailable() returned false");
        this.allProviders.add(provider);
    }

    public synchronized void deregister(ManagedChannelProvider provider) {
        this.allProviders.remove(provider);
        this.refreshProviders();
    }

    private synchronized void refreshProviders() {
        ArrayList<ManagedChannelProvider> providers = new ArrayList<ManagedChannelProvider>(this.allProviders);
        Collections.sort(providers, Collections.reverseOrder(new Comparator<ManagedChannelProvider>(){

            @Override
            public int compare(ManagedChannelProvider o1, ManagedChannelProvider o2) {
                return o1.priority() - o2.priority();
            }
        }));
        this.effectiveProviders = Collections.unmodifiableList(providers);
    }

    public static synchronized ManagedChannelRegistry getDefaultRegistry() {
        if (instance == null) {
            List<ManagedChannelProvider> providerList = ServiceProviders.loadAll(ManagedChannelProvider.class, ManagedChannelRegistry.getHardCodedClasses(), ManagedChannelProvider.class.getClassLoader(), new ManagedChannelPriorityAccessor());
            instance = new ManagedChannelRegistry();
            for (ManagedChannelProvider provider : providerList) {
                logger.fine("Service loader found " + provider);
                instance.addProvider(provider);
            }
            instance.refreshProviders();
        }
        return instance;
    }

    @VisibleForTesting
    synchronized List<ManagedChannelProvider> providers() {
        return this.effectiveProviders;
    }

    ManagedChannelProvider provider() {
        List<ManagedChannelProvider> providers = this.providers();
        return providers.isEmpty() ? null : providers.get(0);
    }

    @VisibleForTesting
    static List<Class<?>> getHardCodedClasses() {
        ArrayList list = new ArrayList();
        try {
            list.add(Class.forName("io.grpc.okhttp.OkHttpChannelProvider"));
        }
        catch (ClassNotFoundException e) {
            logger.log(Level.FINE, "Unable to find OkHttpChannelProvider", e);
        }
        try {
            list.add(Class.forName("io.grpc.netty.NettyChannelProvider"));
        }
        catch (ClassNotFoundException e) {
            logger.log(Level.FINE, "Unable to find NettyChannelProvider", e);
        }
        try {
            list.add(Class.forName("io.grpc.netty.UdsNettyChannelProvider"));
        }
        catch (ClassNotFoundException e) {
            logger.log(Level.FINE, "Unable to find UdsNettyChannelProvider", e);
        }
        return Collections.unmodifiableList(list);
    }

    ManagedChannelBuilder<?> newChannelBuilder(String target, ChannelCredentials creds) {
        return this.newChannelBuilder(NameResolverRegistry.getDefaultRegistry(), target, creds);
    }

    @VisibleForTesting
    ManagedChannelBuilder<?> newChannelBuilder(NameResolverRegistry nameResolverRegistry, String target, ChannelCredentials creds) {
        NameResolverProvider nameResolverProvider = null;
        try {
            URI uri = new URI(target);
            nameResolverProvider = nameResolverRegistry.getProviderForScheme(uri.getScheme());
        }
        catch (URISyntaxException uri) {
            // empty catch block
        }
        if (nameResolverProvider == null) {
            nameResolverProvider = nameResolverRegistry.getProviderForScheme(nameResolverRegistry.getDefaultScheme());
        }
        Collection<Object> nameResolverSocketAddressTypes = nameResolverProvider != null ? nameResolverProvider.getProducedSocketAddressTypes() : Collections.emptySet();
        List<ManagedChannelProvider> providers = this.providers();
        if (providers.isEmpty()) {
            throw new ProviderNotFoundException("No functional channel service provider found. Try adding a dependency on the grpc-okhttp, grpc-netty, or grpc-netty-shaded artifact");
        }
        StringBuilder error = new StringBuilder();
        for (ManagedChannelProvider provider : this.providers()) {
            Collection<Class<? extends SocketAddress>> channelProviderSocketAddressTypes = provider.getSupportedSocketAddressTypes();
            if (!channelProviderSocketAddressTypes.containsAll(nameResolverSocketAddressTypes)) {
                error.append("; ");
                error.append(provider.getClass().getName());
                error.append(": does not support 1 or more of ");
                error.append(Arrays.toString(nameResolverSocketAddressTypes.toArray()));
                continue;
            }
            ManagedChannelProvider.NewChannelBuilderResult result = provider.newChannelBuilder(target, creds);
            if (result.getChannelBuilder() != null) {
                return result.getChannelBuilder();
            }
            error.append("; ");
            error.append(provider.getClass().getName());
            error.append(": ");
            error.append(result.getError());
        }
        throw new ProviderNotFoundException(error.substring(2));
    }

    public static final class ProviderNotFoundException
    extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public ProviderNotFoundException(String msg) {
            super(msg);
        }
    }

    private static final class ManagedChannelPriorityAccessor
    implements ServiceProviders.PriorityAccessor<ManagedChannelProvider> {
        private ManagedChannelPriorityAccessor() {
        }

        @Override
        public boolean isAvailable(ManagedChannelProvider provider) {
            return provider.isAvailable();
        }

        @Override
        public int getPriority(ManagedChannelProvider provider) {
            return provider.priority();
        }
    }
}

