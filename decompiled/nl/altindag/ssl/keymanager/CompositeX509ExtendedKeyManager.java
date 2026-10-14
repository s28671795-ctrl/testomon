/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.keymanager;

import java.net.Socket;
import java.net.URI;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.X509ExtendedKeyManager;
import nl.altindag.ssl.keymanager.CombinableX509KeyManager;
import nl.altindag.ssl.keymanager.RoutableX509KeyManager;
import nl.altindag.ssl.util.internal.HostUtils;

public final class CompositeX509ExtendedKeyManager
extends X509ExtendedKeyManager
implements CombinableX509KeyManager,
RoutableX509KeyManager {
    private final List<X509ExtendedKeyManager> keyManagers;
    private final Map<String, List<URI>> preferredAliasToHost;

    public CompositeX509ExtendedKeyManager(List<? extends X509ExtendedKeyManager> keyManagers) {
        this(keyManagers, Collections.emptyMap());
    }

    public CompositeX509ExtendedKeyManager(List<? extends X509ExtendedKeyManager> keyManagers, Map<String, List<URI>> preferredAliasToHost) {
        this.keyManagers = Collections.unmodifiableList(keyManagers);
        this.preferredAliasToHost = new HashMap<String, List<URI>>(preferredAliasToHost);
    }

    @Override
    public String chooseClientAlias(String[] keyType, Principal[] issuers, Socket socket) {
        return this.chooseClientAlias(socket, this::containsInetSocketAddress, HostUtils::extractHostAndPort, keyManager -> keyManager.chooseClientAlias(keyType, issuers, socket));
    }

    @Override
    public String chooseEngineClientAlias(String[] keyTypes, Principal[] issuers, SSLEngine sslEngine) {
        return this.chooseClientAlias(sslEngine, Objects::nonNull, HostUtils::extractHostAndPort, keyManager -> keyManager.chooseEngineClientAlias(keyTypes, issuers, sslEngine));
    }

    @Override
    public String chooseServerAlias(String keyType, Principal[] issuers, Socket socket) {
        return this.chooseServerAlias(socket, SSLSocket.class::isInstance, aSocket -> ((SSLSocket)aSocket).getHandshakeSession(), keyManager -> keyManager.chooseServerAlias(keyType, issuers, socket));
    }

    @Override
    public String chooseEngineServerAlias(String keyType, Principal[] issuers, SSLEngine sslEngine) {
        return this.chooseServerAlias(sslEngine, Objects::nonNull, SSLEngine::getHandshakeSession, keyManager -> keyManager.chooseEngineServerAlias(keyType, issuers, sslEngine));
    }

    @Override
    public PrivateKey getPrivateKey(String alias) {
        return this.extractInnerField(keyManager -> keyManager.getPrivateKey(alias), Objects::nonNull);
    }

    @Override
    public X509Certificate[] getCertificateChain(String alias) {
        return this.extractInnerField(keyManager -> keyManager.getCertificateChain(alias), chain -> chain != null && ((X509Certificate[])chain).length > 0);
    }

    @Override
    public String[] getClientAliases(String keyType, Principal[] issuers) {
        return this.getAliases(keyManager -> keyManager.getClientAliases(keyType, issuers));
    }

    @Override
    public String[] getServerAliases(String keyType, Principal[] issuers) {
        return this.getAliases(keyManager -> keyManager.getServerAliases(keyType, issuers));
    }

    @Override
    public List<X509ExtendedKeyManager> getInnerKeyManagers() {
        return this.keyManagers;
    }

    @Override
    public Map<String, List<URI>> getIdentityRoute() {
        return this.preferredAliasToHost;
    }
}

