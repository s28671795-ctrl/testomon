/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.trustmanager.trustoptions;

import java.security.KeyStore;
import javax.net.ssl.CertPathTrustManagerParameters;
import nl.altindag.ssl.trustmanager.trustoptions.TrustOptions;

@FunctionalInterface
public interface TrustStoreTrustOptions<R extends CertPathTrustManagerParameters>
extends TrustOptions<KeyStore, R> {
    @Override
    public R apply(KeyStore var1) throws Exception;
}

