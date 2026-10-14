/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.trustmanager.validator;

import java.net.Socket;
import java.security.cert.X509Certificate;
import java.util.Objects;

@Deprecated
@FunctionalInterface
public interface ChainAndAuthTypeWithSocketValidator {
    public boolean test(X509Certificate[] var1, String var2, Socket var3);

    default public ChainAndAuthTypeWithSocketValidator and(ChainAndAuthTypeWithSocketValidator other) {
        Objects.requireNonNull(other);
        return (certificateChain, authType, socket) -> this.test(certificateChain, authType, socket) && other.test(certificateChain, authType, socket);
    }

    default public ChainAndAuthTypeWithSocketValidator or(ChainAndAuthTypeWithSocketValidator other) {
        Objects.requireNonNull(other);
        return (certificateChain, authType, socket) -> this.test(certificateChain, authType, socket) || other.test(certificateChain, authType, socket);
    }
}

