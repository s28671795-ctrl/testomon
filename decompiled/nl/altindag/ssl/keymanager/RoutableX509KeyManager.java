/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.keymanager;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.net.ssl.ExtendedSSLSession;
import javax.net.ssl.SNIServerName;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509ExtendedKeyManager;
import javax.net.ssl.X509KeyManager;
import nl.altindag.ssl.keymanager.CombinableX509KeyManager;

interface RoutableX509KeyManager
extends CombinableX509KeyManager,
X509KeyManager {
    public static final Predicate<String> NON_NULL = Objects::nonNull;

    public Map<String, List<URI>> getIdentityRoute();

    default public <T> String chooseClientAlias(T object, Predicate<T> predicate, Function<T, Map.Entry<String, Integer>> hostToPortExtractor, Function<X509ExtendedKeyManager, String> aliasExtractor) {
        return this.chooseAlias(() -> this.getPreferredClientAlias(object, predicate, hostToPortExtractor), aliasExtractor);
    }

    default public <T> String getPreferredClientAlias(T object, Predicate<T> predicate, Function<T, Map.Entry<String, Integer>> hostToPortExtractor) {
        if (this.getIdentityRoute().isEmpty()) {
            return null;
        }
        if (predicate.test(object)) {
            Map.Entry<String, Integer> hostToPort = hostToPortExtractor.apply(object);
            return this.getPreferredClientAlias(hostToPort.getKey(), hostToPort.getValue());
        }
        return null;
    }

    default public String getPreferredClientAlias(String peerHost, int peerPort) {
        return this.getIdentityRoute().entrySet().stream().filter(entry -> ((List)entry.getValue()).stream().anyMatch(uri -> uri.getHost().contains(peerHost))).filter(entry -> ((List)entry.getValue()).stream().anyMatch(uri -> uri.getPort() == peerPort)).findFirst().map(Map.Entry::getKey).orElse(null);
    }

    default public <T> String chooseServerAlias(T object, Predicate<T> predicate, Function<T, SSLSession> sslSessionExtractor, Function<X509ExtendedKeyManager, String> aliasExtractor) {
        return this.chooseAlias(() -> this.getPreferredServerAlias(object, predicate, sslSessionExtractor), aliasExtractor);
    }

    default public <T> String getPreferredServerAlias(T object, Predicate<T> predicate, Function<T, SSLSession> sslSessionExtractor) {
        SSLSession sslSession;
        if (this.getIdentityRoute().isEmpty()) {
            return null;
        }
        if (predicate.test(object) && (sslSession = sslSessionExtractor.apply(object)) instanceof ExtendedSSLSession) {
            List<SNIServerName> requestedServerNames = ((ExtendedSSLSession)sslSession).getRequestedServerNames();
            Set<String> hostnames = requestedServerNames.stream().map(sniServerName -> new String(sniServerName.getEncoded())).collect(Collectors.toSet());
            return this.getPreferredServerAlias(hostnames);
        }
        return null;
    }

    default public String getPreferredServerAlias(Set<String> hostnames) {
        return this.getIdentityRoute().entrySet().stream().filter(entry -> ((List)entry.getValue()).stream().anyMatch(uri -> hostnames.stream().anyMatch(hostname -> uri.getHost().contains((CharSequence)hostname)))).findFirst().map(Map.Entry::getKey).orElse(null);
    }

    default public String chooseAlias(Supplier<String> preferredAliasSupplier, Function<X509ExtendedKeyManager, String> aliasExtractor) {
        String preferredAlias = preferredAliasSupplier.get();
        if (preferredAlias != null) {
            return this.extractInnerField(aliasExtractor, NON_NULL.and(preferredAlias::equals));
        }
        return this.extractInnerField(aliasExtractor, NON_NULL);
    }

    default public boolean containsInetSocketAddress(Socket socket) {
        return socket != null && socket.getRemoteSocketAddress() instanceof InetSocketAddress;
    }
}

