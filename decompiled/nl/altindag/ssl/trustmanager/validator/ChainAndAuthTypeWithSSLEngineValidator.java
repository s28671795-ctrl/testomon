/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.trustmanager.validator;

import java.security.cert.X509Certificate;
import java.util.Objects;
import javax.net.ssl.SSLEngine;

@Deprecated
@FunctionalInterface
public interface ChainAndAuthTypeWithSSLEngineValidator {
    public boolean test(X509Certificate[] var1, String var2, SSLEngine var3);

    default public ChainAndAuthTypeWithSSLEngineValidator and(ChainAndAuthTypeWithSSLEngineValidator other) {
        Objects.requireNonNull(other);
        return (certificateChain, authType, sslEngine) -> this.test(certificateChain, authType, sslEngine) && other.test(certificateChain, authType, sslEngine);
    }

    default public ChainAndAuthTypeWithSSLEngineValidator or(ChainAndAuthTypeWithSSLEngineValidator other) {
        Objects.requireNonNull(other);
        return (certificateChain, authType, sslEngine) -> this.test(certificateChain, authType, sslEngine) || other.test(certificateChain, authType, sslEngine);
    }
}

