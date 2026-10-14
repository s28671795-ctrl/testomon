/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.ssl;

import com.incoresoft.ssl.CertificateAndKey;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.crypto.util.PublicKeyFactory;
import org.bouncycastle.crypto.util.SubjectPublicKeyInfoFactory;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

public class SSLCertificateGenerator {
    public SSLCertificateGenerator() {
        throw new AssertionError((Object)"Cannot instantiate utility class");
    }

    public static CertificateAndKey createIncoresoftIssuedCertificate(List<String> ips, List<String> domainList, Duration validFor) throws Exception {
        CertificateAndKey incoresoftCertificate = CertificateAndKey.read(SSLCertificateGenerator.class.getResourceAsStream("/IncoresoftCA.key"), SSLCertificateGenerator.class.getResourceAsStream("/IncoresoftCA.crt"));
        return SSLCertificateGenerator.createCertificate(ips, domainList, incoresoftCertificate, false, validFor);
    }

    public static CertificateAndKey createCertificate(List<String> ips, List<String> domainList, CertificateAndKey issuer, boolean isCA, Duration validFor) throws Exception {
        PrivateKey issuerKey;
        X500Name issuerName;
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        KeyPair certKeyPair = keyGen.generateKeyPair();
        X500Name name = new X500Name("CN=localhost");
        BigInteger serialNumber = BigInteger.probablePrime(128, new SecureRandom());
        Instant validFrom = Instant.now();
        Instant validUntil = validFrom.plus(validFor);
        if (issuer == null) {
            issuerName = name;
            issuerKey = certKeyPair.getPrivate();
        } else {
            issuerName = new X500Name("C=UA, O=Incoresoft, CN=Incoresoft CA");
            issuerKey = issuer.privateKey();
        }
        JcaX509v3CertificateBuilder builder = new JcaX509v3CertificateBuilder(issuerName, serialNumber, Date.from(validFrom), Date.from(validUntil), name, certKeyPair.getPublic());
        if (isCA) {
            builder.addExtension(Extension.basicConstraints, true, new BasicConstraints(isCA));
        }
        if (ips != null) {
            for (String ip2 : ips) {
                builder.addExtension(Extension.subjectAlternativeName, false, new GeneralNames(new GeneralName(7, ip2)));
            }
        }
        if (domainList != null) {
            for (String domain : domainList) {
                builder.addExtension(Extension.subjectAlternativeName, false, new GeneralNames(new GeneralName(2, domain)));
            }
        }
        if (issuer != null) {
            JcaX509CertificateHolder holder = new JcaX509CertificateHolder(issuer.certificate());
            JcaX509ExtensionUtils extUtils = new JcaX509ExtensionUtils();
            builder.addExtension(Extension.authorityKeyIdentifier, false, extUtils.createAuthorityKeyIdentifier(holder));
            SubjectPublicKeyInfo entityKeyInfo = SubjectPublicKeyInfoFactory.createSubjectPublicKeyInfo(PublicKeyFactory.createKey(certKeyPair.getPublic().getEncoded()));
            builder.addExtension(Extension.subjectKeyIdentifier, false, extUtils.createSubjectKeyIdentifier(entityKeyInfo));
            builder.addExtension(Extension.basicConstraints, false, new BasicConstraints(false));
            builder.addExtension(Extension.keyUsage, false, new KeyUsage(160));
        }
        ContentSigner signer = new JcaContentSignerBuilder("SHA256WithRSA").build(issuerKey);
        X509CertificateHolder certHolder = builder.build(signer);
        X509Certificate cert = new JcaX509CertificateConverter().getCertificate(certHolder);
        if (issuer != null) {
            return new CertificateAndKey(certKeyPair.getPrivate(), cert, issuer.certificate());
        }
        return new CertificateAndKey(certKeyPair.getPrivate(), cert, new X509Certificate[0]);
    }
}

