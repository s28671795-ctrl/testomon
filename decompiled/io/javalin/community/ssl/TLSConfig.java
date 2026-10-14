/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.community.ssl;

import java.util.Arrays;

public final class TLSConfig {
    private static final String GUIDELINES_VERSION = "5.5";
    public static TLSConfig MODERN = new TLSConfig(new String[]{"TLS_AES_128_GCM_SHA256", "TLS_AES_256_GCM_SHA384", "TLS_CHACHA20_POLY1305_SHA256"}, new String[]{"TLSv1.3"});
    public static TLSConfig INTERMEDIATE = new TLSConfig(new String[]{"TLS_AES_128_GCM_SHA256", "TLS_AES_256_GCM_SHA384", "TLS_CHACHA20_POLY1305_SHA256", "TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256", "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256", "TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384", "TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384", "TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256", "TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256", "TLS_DHE_RSA_WITH_AES_256_GCM_SHA384", "TLS_DHE_RSA_WITH_AES_128_GCM_SHA256"}, new String[]{"TLSv1.2", "TLSv1.3"});
    public static TLSConfig OLD = new TLSConfig(new String[]{"TLS_AES_128_GCM_SHA256", "TLS_AES_256_GCM_SHA384", "TLS_CHACHA20_POLY1305_SHA256", "TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256", "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256", "TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384", "TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384", "TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256", "TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256", "TLS_DHE_RSA_WITH_AES_128_GCM_SHA256", "TLS_DHE_RSA_WITH_AES_256_GCM_SHA384", "TLS_DHE_RSA_WITH_CHACHA20_POLY1305_SHA256", "TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256", "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256", "TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA", "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA", "TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA384", "TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384", "TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA", "TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA", "TLS_DHE_RSA_WITH_AES_128_CBC_SHA256", "TLS_DHE_RSA_WITH_AES_256_CBC_SHA256", "TLS_RSA_WITH_AES_128_GCM_SHA256", "TLS_RSA_WITH_AES_256_GCM_SHA384", "TLS_RSA_WITH_AES_128_CBC_SHA256", "TLS_RSA_WITH_AES_256_CBC_SHA256", "TLS_RSA_WITH_AES_128_CBC_SHA", "TLS_RSA_WITH_AES_256_CBC_SHA", "TLS_RSA_WITH_3DES_EDE_CBC_SHA"}, new String[]{"TLSv1", "TLSv1.1", "TLSv1.2", "TLSv1.3"});
    private final String[] cipherSuites;
    private final String[] protocols;

    public TLSConfig(String[] cipherSuites, String[] protocols) {
        this.cipherSuites = cipherSuites;
        this.protocols = protocols;
    }

    public String[] getCipherSuites() {
        return this.cipherSuites;
    }

    public String[] getProtocols() {
        return this.protocols;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TLSConfig)) {
            return false;
        }
        TLSConfig other = (TLSConfig)o;
        if (!Arrays.deepEquals(this.getCipherSuites(), other.getCipherSuites())) {
            return false;
        }
        return Arrays.deepEquals(this.getProtocols(), other.getProtocols());
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + Arrays.deepHashCode(this.getCipherSuites());
        result = result * 59 + Arrays.deepHashCode(this.getProtocols());
        return result;
    }

    public String toString() {
        return "TLSConfig(cipherSuites=" + Arrays.deepToString(this.getCipherSuites()) + ", protocols=" + Arrays.deepToString(this.getProtocols()) + ")";
    }
}

