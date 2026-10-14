/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.trustmanager.trustoptions;

import java.security.cert.TrustAnchor;
import java.util.Set;
import javax.net.ssl.CertPathTrustManagerParameters;
import nl.altindag.ssl.trustmanager.trustoptions.TrustOptions;

@FunctionalInterface
public interface TrustAnchorTrustOptions<R extends CertPathTrustManagerParameters>
extends TrustOptions<Set<TrustAnchor>, R> {
    @Override
    public R apply(Set<TrustAnchor> var1) throws Exception;
}

