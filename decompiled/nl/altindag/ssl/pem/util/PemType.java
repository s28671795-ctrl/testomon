/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.pem.util;

import java.util.Arrays;
import java.util.List;
import nl.altindag.ssl.pem.exception.PemParseException;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.openssl.PEMEncryptedKeyPair;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.X509TrustedCertificateBlock;
import org.bouncycastle.pkcs.PKCS8EncryptedPrivateKeyInfo;

enum PemType {
    CERTIFICATE(X509CertificateHolder.class, X509TrustedCertificateBlock.class),
    KEY(PrivateKeyInfo.class, PKCS8EncryptedPrivateKeyInfo.class, PEMKeyPair.class, PEMEncryptedKeyPair.class);

    private final List<Class<?>> supportedTypes;

    private PemType(Class<?> ... classes) {
        this.supportedTypes = Arrays.asList(classes);
    }

    static PemType from(Object object) {
        for (PemType pemType : PemType.values()) {
            for (Class<?> supportedType : pemType.supportedTypes) {
                if (!supportedType.isInstance(object)) continue;
                return pemType;
            }
        }
        throw new PemParseException(String.format("The provided [%s] pem type is not (yet) supported", object.getClass().getName()));
    }
}

