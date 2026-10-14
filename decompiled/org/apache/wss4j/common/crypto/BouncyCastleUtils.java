/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.crypto;

import java.security.cert.X509Certificate;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.x509.AuthorityKeyIdentifier;
import org.bouncycastle.asn1.x509.SubjectKeyIdentifier;

public final class BouncyCastleUtils {
    private BouncyCastleUtils() {
    }

    public static byte[] getAuthorityKeyIdentifierBytes(X509Certificate cert) {
        byte[] extensionValue = cert.getExtensionValue("2.5.29.35");
        if (extensionValue != null) {
            byte[] octets = ASN1OctetString.getInstance(extensionValue).getOctets();
            AuthorityKeyIdentifier authorityKeyIdentifier = AuthorityKeyIdentifier.getInstance(octets);
            return authorityKeyIdentifier.getKeyIdentifier();
        }
        return null;
    }

    public static byte[] getSubjectKeyIdentifierBytes(X509Certificate cert) {
        byte[] extensionValue = cert.getExtensionValue("2.5.29.14");
        if (extensionValue != null) {
            byte[] subjectOctets = ASN1OctetString.getInstance(extensionValue).getOctets();
            SubjectKeyIdentifier subjectKeyIdentifier = SubjectKeyIdentifier.getInstance(subjectOctets);
            return subjectKeyIdentifier.getKeyIdentifier();
        }
        return null;
    }
}

