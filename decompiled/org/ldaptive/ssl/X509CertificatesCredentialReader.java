/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.ssl;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import org.ldaptive.ssl.AbstractCredentialReader;

public class X509CertificatesCredentialReader
extends AbstractCredentialReader<X509Certificate[]> {
    @Override
    public X509Certificate[] read(String path, String ... params) throws IOException, GeneralSecurityException {
        String[] paths = path.split(",");
        ArrayList<X509Certificate> certificateList = new ArrayList<X509Certificate>();
        for (String individualPath : paths) {
            X509Certificate[] parsedCertificates = (X509Certificate[])super.read(individualPath, params);
            certificateList.addAll(Arrays.asList(parsedCertificates));
        }
        return certificateList.toArray(new X509Certificate[0]);
    }

    @Override
    public X509Certificate[] read(InputStream is, String ... params) throws IOException, GeneralSecurityException {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        ArrayList<X509Certificate> certList = new ArrayList<X509Certificate>();
        InputStream bufIs = this.getBufferedInputStream(is);
        while (bufIs.available() > 0) {
            X509Certificate cert = (X509Certificate)cf.generateCertificate(bufIs);
            if (cert == null) continue;
            certList.add(cert);
        }
        return certList.toArray(new X509Certificate[0]);
    }
}

