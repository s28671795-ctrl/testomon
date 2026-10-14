/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl;

import com.typesafe.sslconfig.ssl.FakeKeyStore$KeystoreSettings$;
import com.typesafe.sslconfig.ssl.FakeKeyStore$SelfSigned$;
import com.typesafe.sslconfig.ssl.FakeKeyStore$SelfSigned$Alias$;
import java.io.File;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Date;
import scala.Array$;
import sun.security.x509.AlgorithmId;
import sun.security.x509.CertificateAlgorithmId;
import sun.security.x509.CertificateSerialNumber;
import sun.security.x509.CertificateValidity;
import sun.security.x509.CertificateVersion;
import sun.security.x509.CertificateX509Key;
import sun.security.x509.X500Name;
import sun.security.x509.X509CertImpl;
import sun.security.x509.X509CertInfo;

public final class FakeKeyStore$ {
    public static final FakeKeyStore$ MODULE$ = new FakeKeyStore$();
    private static final char[] com$typesafe$sslconfig$ssl$FakeKeyStore$$EMPTY_PASSWORD = Array$.MODULE$.emptyCharArray();

    public char[] com$typesafe$sslconfig$ssl$FakeKeyStore$$EMPTY_PASSWORD() {
        return com$typesafe$sslconfig$ssl$FakeKeyStore$$EMPTY_PASSWORD;
    }

    public String com$typesafe$sslconfig$ssl$FakeKeyStore$$fileInDevModeDir(String filename) {
        return new StringBuilder(14).append("target").append(File.separatorChar).append("dev-mode").append(File.separatorChar).append(filename).toString();
    }

    /*
     * WARNING - void declaration
     */
    public KeyStore generateKeyStore() {
        void var1_1;
        KeyStore keyStore = KeyStore.getInstance(FakeKeyStore$KeystoreSettings$.MODULE$.KeystoreType());
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(FakeKeyStore$KeystoreSettings$.MODULE$.KeyPairAlgorithmName());
        keyPairGenerator.initialize(FakeKeyStore$KeystoreSettings$.MODULE$.KeyPairKeyLength());
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        X509Certificate cert = this.createSelfSignedCertificate(keyPair);
        keyStore.load(null, FakeKeyStore$KeystoreSettings$.MODULE$.keystorePassword());
        keyStore.setKeyEntry(FakeKeyStore$SelfSigned$Alias$.MODULE$.PrivateKeyEntry(), keyPair.getPrivate(), FakeKeyStore$SelfSigned$.MODULE$.keyPassword(), (Certificate[])((Object[])new Certificate[]{cert}));
        keyStore.setCertificateEntry(FakeKeyStore$SelfSigned$Alias$.MODULE$.trustedCertEntry(), cert);
        return var1_1;
    }

    public X509Certificate createSelfSignedCertificate(KeyPair keyPair) {
        X509CertInfo certInfo = new X509CertInfo();
        certInfo.set("serialNumber", new CertificateSerialNumber(new BigInteger(64, new SecureRandom())));
        certInfo.set("version", new CertificateVersion(2));
        Date validFrom = new Date();
        Date validTo = new Date(validFrom.getTime() + 1576800000000L);
        CertificateValidity validity = new CertificateValidity(validFrom, validTo);
        certInfo.set("validity", validity);
        X500Name owner = new X500Name(FakeKeyStore$SelfSigned$.MODULE$.DistinguishedName());
        certInfo.set("subject", owner);
        certInfo.set("issuer", owner);
        certInfo.set("key", new CertificateX509Key(keyPair.getPublic()));
        AlgorithmId algorithm = AlgorithmId.get("SHA256WithRSA");
        certInfo.set("algorithmID", new CertificateAlgorithmId(algorithm));
        X509CertImpl cert = new X509CertImpl(certInfo);
        cert.sign(keyPair.getPrivate(), FakeKeyStore$KeystoreSettings$.MODULE$.SignatureAlgorithmName());
        AlgorithmId actualAlgorithm = (AlgorithmId)cert.get("x509.algorithm");
        certInfo.set("algorithmID.algorithm", actualAlgorithm);
        X509CertImpl newCert = new X509CertImpl(certInfo);
        newCert.sign(keyPair.getPrivate(), FakeKeyStore$KeystoreSettings$.MODULE$.SignatureAlgorithmName());
        return newCert;
    }

    private FakeKeyStore$() {
    }
}

