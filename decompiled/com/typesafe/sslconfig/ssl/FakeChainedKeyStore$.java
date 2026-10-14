/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl;

import com.typesafe.sslconfig.ssl.FakeChainedKeyStore$CA$;
import com.typesafe.sslconfig.ssl.FakeChainedKeyStore$CA$Alias$;
import com.typesafe.sslconfig.ssl.FakeChainedKeyStore$KeystoreSettings$;
import com.typesafe.sslconfig.ssl.FakeChainedKeyStore$User$;
import com.typesafe.sslconfig.ssl.FakeChainedKeyStore$User$Alias$;
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
import scala.Predef$;
import sun.security.x509.AlgorithmId;
import sun.security.x509.BasicConstraintsExtension;
import sun.security.x509.CertificateAlgorithmId;
import sun.security.x509.CertificateExtensions;
import sun.security.x509.CertificateSerialNumber;
import sun.security.x509.CertificateValidity;
import sun.security.x509.CertificateVersion;
import sun.security.x509.CertificateX509Key;
import sun.security.x509.X500Name;
import sun.security.x509.X509CertImpl;
import sun.security.x509.X509CertInfo;

public final class FakeChainedKeyStore$ {
    public static final FakeChainedKeyStore$ MODULE$ = new FakeChainedKeyStore$();
    private static final char[] com$typesafe$sslconfig$ssl$FakeChainedKeyStore$$EMPTY_PASSWORD = Array$.MODULE$.emptyCharArray();

    public char[] com$typesafe$sslconfig$ssl$FakeChainedKeyStore$$EMPTY_PASSWORD() {
        return com$typesafe$sslconfig$ssl$FakeChainedKeyStore$$EMPTY_PASSWORD;
    }

    public String com$typesafe$sslconfig$ssl$FakeChainedKeyStore$$fileInDevModeDir(String filename) {
        return new StringBuilder(14).append("target").append(File.separatorChar).append("dev-mode").append(File.separatorChar).append(filename).toString();
    }

    /*
     * WARNING - void declaration
     */
    public KeyStore generateKeyStore() {
        void var1_1;
        KeyStore keyStore = KeyStore.getInstance(FakeChainedKeyStore$KeystoreSettings$.MODULE$.KeystoreType());
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(FakeChainedKeyStore$KeystoreSettings$.MODULE$.KeyPairAlgorithmName());
        keyPairGenerator.initialize(FakeChainedKeyStore$KeystoreSettings$.MODULE$.KeyPairKeyLength());
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        KeyPair certificateAuthorityKeyPair = keyPairGenerator.generateKeyPair();
        X509Certificate cacert = this.createCertificateAuthority(certificateAuthorityKeyPair);
        X509Certificate cert = this.createUserCertificate(keyPair, certificateAuthorityKeyPair);
        keyStore.load(null, FakeChainedKeyStore$KeystoreSettings$.MODULE$.keystorePassword());
        keyStore.setKeyEntry(FakeChainedKeyStore$CA$Alias$.MODULE$.PrivateKeyEntry(), keyPair.getPrivate(), FakeChainedKeyStore$CA$.MODULE$.keyPassword(), (Certificate[])((Object[])new Certificate[]{cacert}));
        keyStore.setCertificateEntry(FakeChainedKeyStore$CA$Alias$.MODULE$.trustedCertEntry(), cacert);
        keyStore.setKeyEntry(FakeChainedKeyStore$User$Alias$.MODULE$.PrivateKeyEntry(), keyPair.getPrivate(), FakeChainedKeyStore$User$.MODULE$.keyPassword(), (Certificate[])((Object[])new Certificate[]{cert}));
        keyStore.setCertificateEntry(FakeChainedKeyStore$User$Alias$.MODULE$.trustedCertEntry(), cert);
        return var1_1;
    }

    public X509Certificate createUserCertificate(KeyPair userKeyPair, KeyPair certificateAuthorityKeyPair) {
        X509CertInfo certInfo = new X509CertInfo();
        certInfo.set("serialNumber", new CertificateSerialNumber(new BigInteger(64, new SecureRandom())));
        certInfo.set("version", new CertificateVersion(2));
        Date validFrom = new Date();
        Date validTo = new Date(validFrom.getTime() + 1576800000000L);
        CertificateValidity validity = new CertificateValidity(validFrom, validTo);
        certInfo.set("validity", validity);
        X500Name certificateAuthorityName = new X500Name(FakeChainedKeyStore$CA$.MODULE$.DistinguishedName());
        certInfo.set("issuer", certificateAuthorityName);
        X500Name owner = new X500Name(FakeChainedKeyStore$User$.MODULE$.DistinguishedName());
        certInfo.set("subject", owner);
        certInfo.set("key", new CertificateX509Key(userKeyPair.getPublic()));
        AlgorithmId algorithm = AlgorithmId.get("SHA256WithRSA");
        certInfo.set("algorithmID", new CertificateAlgorithmId(algorithm));
        X509CertImpl cert = new X509CertImpl(certInfo);
        cert.sign(userKeyPair.getPrivate(), FakeChainedKeyStore$KeystoreSettings$.MODULE$.SignatureAlgorithmName());
        AlgorithmId actualAlgorithm = (AlgorithmId)cert.get("x509.algorithm");
        certInfo.set("algorithmID.algorithm", actualAlgorithm);
        X509CertImpl newCert = new X509CertImpl(certInfo);
        newCert.sign(certificateAuthorityKeyPair.getPrivate(), FakeChainedKeyStore$KeystoreSettings$.MODULE$.SignatureAlgorithmName());
        return newCert;
    }

    private X509Certificate createCertificateAuthority(KeyPair keyPair) {
        X509CertInfo certInfo = new X509CertInfo();
        certInfo.set("serialNumber", new CertificateSerialNumber(new BigInteger(64, new SecureRandom())));
        certInfo.set("version", new CertificateVersion(2));
        Date validFrom = new Date();
        Date validTo = new Date(validFrom.getTime() + 1576800000000L);
        CertificateValidity validity = new CertificateValidity(validFrom, validTo);
        certInfo.set("validity", validity);
        X500Name owner = new X500Name(FakeChainedKeyStore$CA$.MODULE$.DistinguishedName());
        certInfo.set("subject", owner);
        certInfo.set("issuer", owner);
        certInfo.set("key", new CertificateX509Key(keyPair.getPublic()));
        AlgorithmId algorithm = AlgorithmId.get("SHA256WithRSA");
        certInfo.set("algorithmID", new CertificateAlgorithmId(algorithm));
        CertificateExtensions caExtension = new CertificateExtensions();
        caExtension.set("BasicConstraints", new BasicConstraintsExtension(Predef$.MODULE$.boolean2Boolean(true), true, 0));
        certInfo.set("extensions", caExtension);
        X509CertImpl cert = new X509CertImpl(certInfo);
        cert.sign(keyPair.getPrivate(), FakeChainedKeyStore$KeystoreSettings$.MODULE$.SignatureAlgorithmName());
        AlgorithmId actualAlgorithm = (AlgorithmId)cert.get("x509.algorithm");
        certInfo.set("algorithmID.algorithm", actualAlgorithm);
        X509CertImpl newCert = new X509CertImpl(certInfo);
        newCert.sign(keyPair.getPrivate(), FakeChainedKeyStore$KeystoreSettings$.MODULE$.SignatureAlgorithmName());
        return newCert;
    }

    private FakeChainedKeyStore$() {
    }
}

