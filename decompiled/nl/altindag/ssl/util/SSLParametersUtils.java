/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.util;

import java.util.Optional;
import javax.net.ssl.SSLParameters;

public final class SSLParametersUtils {
    private SSLParametersUtils() {
    }

    public static SSLParameters copy(SSLParameters source2) {
        SSLParameters target = new SSLParameters();
        target.setProtocols(source2.getProtocols());
        target.setCipherSuites(source2.getCipherSuites());
        if (source2.getWantClientAuth()) {
            target.setWantClientAuth(true);
        }
        if (source2.getNeedClientAuth()) {
            target.setNeedClientAuth(true);
        }
        return target;
    }

    public static SSLParameters merge(SSLParameters baseSslParameters, SSLParameters alternativeSslParameters) {
        boolean needClientAuth;
        boolean wantClientAuth;
        SSLParameters target = new SSLParameters();
        String[] ciphers = Optional.ofNullable(baseSslParameters.getCipherSuites()).filter(array -> ((String[])array).length != 0).orElseGet(alternativeSslParameters::getCipherSuites);
        String[] protocols = Optional.ofNullable(baseSslParameters.getProtocols()).filter(array -> ((String[])array).length != 0).orElseGet(alternativeSslParameters::getProtocols);
        target.setCipherSuites(ciphers);
        target.setProtocols(protocols);
        boolean bl = wantClientAuth = baseSslParameters.getWantClientAuth() ? baseSslParameters.getWantClientAuth() : alternativeSslParameters.getWantClientAuth();
        if (wantClientAuth) {
            target.setWantClientAuth(true);
        }
        boolean bl2 = needClientAuth = baseSslParameters.getNeedClientAuth() ? baseSslParameters.getNeedClientAuth() : alternativeSslParameters.getNeedClientAuth();
        if (needClientAuth) {
            target.setNeedClientAuth(true);
        }
        return target;
    }
}

