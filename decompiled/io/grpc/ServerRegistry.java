/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import io.grpc.Internal;
import io.grpc.ServerBuilder;
import io.grpc.ServerCredentials;
import io.grpc.ServerProvider;
import io.grpc.ServiceProviders;
import java.util.ArrayList;
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
public final class ServerRegistry {
    private static final Logger logger = Logger.getLogger(ServerRegistry.class.getName());
    private static ServerRegistry instance;
    @GuardedBy(value="this")
    private final LinkedHashSet<ServerProvider> allProviders = new LinkedHashSet();
    @GuardedBy(value="this")
    private List<ServerProvider> effectiveProviders = Collections.emptyList();

    public synchronized void register(ServerProvider provider) {
        this.addProvider(provider);
        this.refreshProviders();
    }

    private synchronized void addProvider(ServerProvider provider) {
        Preconditions.checkArgument(provider.isAvailable(), "isAvailable() returned false");
        this.allProviders.add(provider);
    }

    public synchronized void deregister(ServerProvider provider) {
        this.allProviders.remove(provider);
        this.refreshProviders();
    }

    private synchronized void refreshProviders() {
        ArrayList<ServerProvider> providers = new ArrayList<ServerProvider>(this.allProviders);
        Collections.sort(providers, Collections.reverseOrder(new Comparator<ServerProvider>(){

            @Override
            public int compare(ServerProvider o1, ServerProvider o2) {
                return o1.priority() - o2.priority();
            }
        }));
        this.effectiveProviders = Collections.unmodifiableList(providers);
    }

    public static synchronized ServerRegistry getDefaultRegistry() {
        if (instance == null) {
            List<ServerProvider> providerList = ServiceProviders.loadAll(ServerProvider.class, ServerRegistry.getHardCodedClasses(), ServerProvider.class.getClassLoader(), new ServerPriorityAccessor());
            instance = new ServerRegistry();
            for (ServerProvider provider : providerList) {
                logger.fine("Service loader found " + provider);
                instance.addProvider(provider);
            }
            instance.refreshProviders();
        }
        return instance;
    }

    @VisibleForTesting
    synchronized List<ServerProvider> providers() {
        return this.effectiveProviders;
    }

    ServerProvider provider() {
        List<ServerProvider> providers = this.providers();
        return providers.isEmpty() ? null : providers.get(0);
    }

    @VisibleForTesting
    static List<Class<?>> getHardCodedClasses() {
        ArrayList list = new ArrayList();
        try {
            list.add(Class.forName("io.grpc.okhttp.OkHttpServerProvider"));
        }
        catch (ClassNotFoundException e) {
            logger.log(Level.FINE, "Unable to find OkHttpServerProvider", e);
        }
        return Collections.unmodifiableList(list);
    }

    ServerBuilder<?> newServerBuilderForPort(int port, ServerCredentials creds) {
        List<ServerProvider> providers = this.providers();
        if (providers.isEmpty()) {
            throw new ProviderNotFoundException("No functional server found. Try adding a dependency on the grpc-netty, grpc-netty-shaded, or grpc-okhttp artifact");
        }
        StringBuilder error = new StringBuilder();
        for (ServerProvider provider : this.providers()) {
            ServerProvider.NewServerBuilderResult result = provider.newServerBuilderForPort(port, creds);
            if (result.getServerBuilder() != null) {
                return result.getServerBuilder();
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

    private static final class ServerPriorityAccessor
    implements ServiceProviders.PriorityAccessor<ServerProvider> {
        private ServerPriorityAccessor() {
        }

        @Override
        public boolean isAvailable(ServerProvider provider) {
            return provider.isAvailable();
        }

        @Override
        public int getPriority(ServerProvider provider) {
            return provider.priority();
        }
    }
}

