/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.security.PublicKey;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.security.auth.x500.X500Principal;

public final class TrustedCertificateIndex {
    private final Map<X500Principal, List<TrustAnchor>> subjectToTrustAnchors = new HashMap<X500Principal, List<TrustAnchor>>();

    public TrustedCertificateIndex() {
    }

    public TrustedCertificateIndex(Set<TrustAnchor> anchors) {
        this.index(anchors);
    }

    private void index(Set<TrustAnchor> anchors) {
        for (TrustAnchor anchor : anchors) {
            this.index(anchor);
        }
    }

    public TrustAnchor index(X509Certificate cert) {
        TrustAnchor anchor = new TrustAnchor(cert, null);
        this.index(anchor);
        return anchor;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void index(TrustAnchor anchor) {
        X509Certificate cert = anchor.getTrustedCert();
        X500Principal subject = cert != null ? cert.getSubjectX500Principal() : anchor.getCA();
        Map<X500Principal, List<TrustAnchor>> map = this.subjectToTrustAnchors;
        synchronized (map) {
            List<TrustAnchor> anchors = this.subjectToTrustAnchors.get(subject);
            if (anchors == null) {
                anchors = new ArrayList<TrustAnchor>(1);
                this.subjectToTrustAnchors.put(subject, anchors);
            } else if (cert != null) {
                for (TrustAnchor entry : anchors) {
                    if (!cert.equals(entry.getTrustedCert())) continue;
                    return;
                }
            }
            anchors.add(anchor);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void reset() {
        Map<X500Principal, List<TrustAnchor>> map = this.subjectToTrustAnchors;
        synchronized (map) {
            this.subjectToTrustAnchors.clear();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void reset(Set<TrustAnchor> anchors) {
        Map<X500Principal, List<TrustAnchor>> map = this.subjectToTrustAnchors;
        synchronized (map) {
            this.reset();
            this.index(anchors);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public TrustAnchor findByIssuerAndSignature(X509Certificate cert) {
        X500Principal issuer = cert.getIssuerX500Principal();
        Map<X500Principal, List<TrustAnchor>> map = this.subjectToTrustAnchors;
        synchronized (map) {
            List<TrustAnchor> anchors = this.subjectToTrustAnchors.get(issuer);
            if (anchors == null) {
                return null;
            }
            for (TrustAnchor anchor : anchors) {
                try {
                    X509Certificate caCert = anchor.getTrustedCert();
                    PublicKey publicKey = caCert != null ? caCert.getPublicKey() : anchor.getCAPublicKey();
                    cert.verify(publicKey);
                    return anchor;
                }
                catch (Exception exception) {
                }
            }
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public TrustAnchor findBySubjectAndPublicKey(X509Certificate cert) {
        X500Principal subject = cert.getSubjectX500Principal();
        Map<X500Principal, List<TrustAnchor>> map = this.subjectToTrustAnchors;
        synchronized (map) {
            List<TrustAnchor> anchors = this.subjectToTrustAnchors.get(subject);
            if (anchors == null) {
                return null;
            }
            return TrustedCertificateIndex.findBySubjectAndPublicKey(cert, anchors);
        }
    }

    private static TrustAnchor findBySubjectAndPublicKey(X509Certificate cert, Collection<TrustAnchor> anchors) {
        PublicKey certPublicKey = cert.getPublicKey();
        for (TrustAnchor anchor : anchors) {
            try {
                X509Certificate caCert = anchor.getTrustedCert();
                PublicKey caPublicKey = caCert != null ? caCert.getPublicKey() : anchor.getCAPublicKey();
                if (caPublicKey.equals(certPublicKey)) {
                    return anchor;
                }
                if (!"X.509".equals(caPublicKey.getFormat()) || !"X.509".equals(certPublicKey.getFormat())) continue;
                byte[] caPublicKeyEncoded = caPublicKey.getEncoded();
                byte[] certPublicKeyEncoded = certPublicKey.getEncoded();
                if (certPublicKeyEncoded == null || caPublicKeyEncoded == null || !Arrays.equals(caPublicKeyEncoded, certPublicKeyEncoded)) continue;
                return anchor;
            }
            catch (Exception exception) {
            }
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Set<TrustAnchor> findAllByIssuerAndSignature(X509Certificate cert) {
        X500Principal issuer = cert.getIssuerX500Principal();
        Map<X500Principal, List<TrustAnchor>> map = this.subjectToTrustAnchors;
        synchronized (map) {
            List<TrustAnchor> anchors = this.subjectToTrustAnchors.get(issuer);
            if (anchors == null) {
                return Collections.emptySet();
            }
            HashSet<TrustAnchor> result = new HashSet<TrustAnchor>();
            for (TrustAnchor anchor : anchors) {
                try {
                    X509Certificate caCert = anchor.getTrustedCert();
                    PublicKey publicKey = caCert != null ? caCert.getPublicKey() : anchor.getCAPublicKey();
                    if (publicKey == null) continue;
                    cert.verify(publicKey);
                    result.add(anchor);
                }
                catch (Exception exception) {}
            }
            return result;
        }
    }
}

