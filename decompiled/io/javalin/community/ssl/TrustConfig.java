/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.community.ssl;

import java.io.InputStream;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.List;
import nl.altindag.ssl.util.CertificateUtils;
import nl.altindag.ssl.util.KeyStoreUtils;

public class TrustConfig {
    public List<Certificate> certificates = new ArrayList<Certificate>();
    public List<KeyStore> keyStores = new ArrayList<KeyStore>();

    public void certificateFromPath(String certificatePath) {
        this.certificates.addAll(CertificateUtils.loadCertificate(Paths.get(certificatePath, new String[0])));
    }

    public void certificateFromClasspath(String certificateFile) {
        this.certificates.addAll(CertificateUtils.loadCertificate(certificateFile));
    }

    public void certificateFromInputStream(InputStream certificateInputStream) {
        this.certificates.addAll(CertificateUtils.loadCertificate(certificateInputStream));
    }

    public void p7bCertificateFromString(String certificateString) {
        this.certificates.addAll(CertificateUtils.parseP7bCertificate(certificateString));
    }

    public void pemFromString(String certificateString) {
        this.certificates.addAll(CertificateUtils.parsePemCertificate(certificateString));
    }

    public void trustStoreFromPath(String trustStorePath, String trustStorePassword) {
        this.keyStores.add(KeyStoreUtils.loadKeyStore(Paths.get(trustStorePath, new String[0]), trustStorePassword.toCharArray()));
    }

    public void trustStoreFromInputStream(InputStream trustStoreInputStream, String trustStorePassword) {
        this.keyStores.add(KeyStoreUtils.loadKeyStore(trustStoreInputStream, trustStorePassword.toCharArray()));
    }

    public void trustStoreFromClasspath(String trustStoreFile, String trustStorePassword) {
        this.keyStores.add(KeyStoreUtils.loadKeyStore(trustStoreFile, trustStorePassword.toCharArray()));
    }
}

