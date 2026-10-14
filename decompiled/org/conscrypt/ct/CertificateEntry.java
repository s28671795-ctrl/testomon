/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt.ct;

import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import org.conscrypt.OpenSSLX509Certificate;
import org.conscrypt.ct.Serialization;
import org.conscrypt.ct.SerializationException;

public class CertificateEntry {
    private final LogEntryType entryType;
    private final byte[] issuerKeyHash;
    private final byte[] certificate;

    private CertificateEntry(LogEntryType entryType, byte[] certificate, byte[] issuerKeyHash) {
        if (entryType == LogEntryType.PRECERT_ENTRY && issuerKeyHash == null) {
            throw new IllegalArgumentException("issuerKeyHash missing for precert entry.");
        }
        if (entryType == LogEntryType.X509_ENTRY && issuerKeyHash != null) {
            throw new IllegalArgumentException("unexpected issuerKeyHash for X509 entry.");
        }
        if (issuerKeyHash != null && issuerKeyHash.length != 32) {
            throw new IllegalArgumentException("issuerKeyHash must be 32 bytes long");
        }
        this.entryType = entryType;
        this.issuerKeyHash = issuerKeyHash;
        this.certificate = certificate;
    }

    public static CertificateEntry createForPrecertificate(byte[] tbsCertificate, byte[] issuerKeyHash) {
        return new CertificateEntry(LogEntryType.PRECERT_ENTRY, tbsCertificate, issuerKeyHash);
    }

    public static CertificateEntry createForPrecertificate(OpenSSLX509Certificate leaf, OpenSSLX509Certificate issuer) throws CertificateException {
        try {
            if (!leaf.getNonCriticalExtensionOIDs().contains("1.3.6.1.4.1.11129.2.4.2")) {
                throw new CertificateException("Certificate does not contain embedded signed timestamps");
            }
            OpenSSLX509Certificate preCert = leaf.withDeletedExtension("1.3.6.1.4.1.11129.2.4.2");
            byte[] tbs = preCert.getTBSCertificate();
            byte[] issuerKey = issuer.getPublicKey().getEncoded();
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(issuerKey);
            byte[] issuerKeyHash = md.digest();
            return CertificateEntry.createForPrecertificate(tbs, issuerKeyHash);
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static CertificateEntry createForX509Certificate(byte[] x509Certificate) {
        return new CertificateEntry(LogEntryType.X509_ENTRY, x509Certificate, null);
    }

    public static CertificateEntry createForX509Certificate(X509Certificate cert) throws CertificateEncodingException {
        return CertificateEntry.createForX509Certificate(cert.getEncoded());
    }

    public LogEntryType getEntryType() {
        return this.entryType;
    }

    public byte[] getCertificate() {
        return this.certificate;
    }

    public byte[] getIssuerKeyHash() {
        return this.issuerKeyHash;
    }

    public void encode(OutputStream output) throws SerializationException {
        Serialization.writeNumber(output, this.entryType.ordinal(), 2);
        if (this.entryType == LogEntryType.PRECERT_ENTRY) {
            Serialization.writeFixedBytes(output, this.issuerKeyHash);
        }
        Serialization.writeVariableBytes(output, this.certificate, 3);
    }

    public static enum LogEntryType {
        X509_ENTRY,
        PRECERT_ENTRY;

    }
}

