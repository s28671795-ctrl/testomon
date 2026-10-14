/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.ssl;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import org.ldaptive.ssl.AbstractCredentialReader;

public class X509CertificateCredentialReader
extends AbstractCredentialReader<X509Certificate> {
    @Override
    public X509Certificate read(InputStream is, String ... params) throws IOException, GeneralSecurityException {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        return (X509Certificate)cf.generateCertificate(this.getBufferedInputStream(is));
    }
}

