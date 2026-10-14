/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.trustmanager.validator;

import java.security.cert.X509Certificate;
import java.util.Objects;

@Deprecated
@FunctionalInterface
public interface ChainAndAuthTypeValidator {
    public boolean test(X509Certificate[] var1, String var2);

    default public ChainAndAuthTypeValidator and(ChainAndAuthTypeValidator other) {
        Objects.requireNonNull(other);
        return (certificateChain, authType) -> this.test(certificateChain, authType) && other.test(certificateChain, authType);
    }

    default public ChainAndAuthTypeValidator or(ChainAndAuthTypeValidator other) {
        Objects.requireNonNull(other);
        return (certificateChain, authType) -> this.test(certificateChain, authType) || other.test(certificateChain, authType);
    }
}

