/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt.ct;

import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.conscrypt.NativeCrypto;
import org.conscrypt.OpenSSLX509Certificate;
import org.conscrypt.ct.CTLogInfo;
import org.conscrypt.ct.CTLogStore;
import org.conscrypt.ct.CTVerificationResult;
import org.conscrypt.ct.CertificateEntry;
import org.conscrypt.ct.Serialization;
import org.conscrypt.ct.SerializationException;
import org.conscrypt.ct.SignedCertificateTimestamp;
import org.conscrypt.ct.VerifiedSCT;

public class CTVerifier {
    private final CTLogStore store;

    public CTVerifier(CTLogStore store) {
        this.store = store;
    }

    public CTVerificationResult verifySignedCertificateTimestamps(List<X509Certificate> chain, byte[] tlsData, byte[] ocspData) throws CertificateEncodingException {
        OpenSSLX509Certificate[] certs = new OpenSSLX509Certificate[chain.size()];
        int i = 0;
        for (X509Certificate cert : chain) {
            certs[i++] = OpenSSLX509Certificate.fromCertificate(cert);
        }
        return this.verifySignedCertificateTimestamps(certs, tlsData, ocspData);
    }

    public CTVerificationResult verifySignedCertificateTimestamps(OpenSSLX509Certificate[] chain, byte[] tlsData, byte[] ocspData) throws CertificateEncodingException {
        if (chain.length == 0) {
            throw new IllegalArgumentException("Chain of certificates mustn't be empty.");
        }
        OpenSSLX509Certificate leaf = chain[0];
        CTVerificationResult result = new CTVerificationResult();
        List<SignedCertificateTimestamp> tlsScts = this.getSCTsFromTLSExtension(tlsData);
        this.verifyExternalSCTs(tlsScts, leaf, result);
        List<SignedCertificateTimestamp> ocspScts = this.getSCTsFromOCSPResponse(ocspData, chain);
        this.verifyExternalSCTs(ocspScts, leaf, result);
        List<SignedCertificateTimestamp> embeddedScts = this.getSCTsFromX509Extension(chain[0]);
        this.verifyEmbeddedSCTs(embeddedScts, chain, result);
        return result;
    }

    private void verifyEmbeddedSCTs(List<SignedCertificateTimestamp> scts, OpenSSLX509Certificate[] chain, CTVerificationResult result) {
        if (scts.isEmpty()) {
            return;
        }
        CertificateEntry precertEntry = null;
        if (chain.length >= 2) {
            OpenSSLX509Certificate leaf = chain[0];
            OpenSSLX509Certificate issuer = chain[1];
            try {
                precertEntry = CertificateEntry.createForPrecertificate(leaf, issuer);
            }
            catch (CertificateException certificateException) {
                // empty catch block
            }
        }
        if (precertEntry == null) {
            this.markSCTsAsInvalid(scts, result);
            return;
        }
        for (SignedCertificateTimestamp sct : scts) {
            VerifiedSCT.Status status = this.verifySingleSCT(sct, precertEntry);
            result.add(new VerifiedSCT(sct, status));
        }
    }

    private void verifyExternalSCTs(List<SignedCertificateTimestamp> scts, OpenSSLX509Certificate leaf, CTVerificationResult result) {
        CertificateEntry x509Entry;
        if (scts.isEmpty()) {
            return;
        }
        try {
            x509Entry = CertificateEntry.createForX509Certificate(leaf);
        }
        catch (CertificateException e) {
            this.markSCTsAsInvalid(scts, result);
            return;
        }
        for (SignedCertificateTimestamp sct : scts) {
            VerifiedSCT.Status status = this.verifySingleSCT(sct, x509Entry);
            result.add(new VerifiedSCT(sct, status));
        }
    }

    private VerifiedSCT.Status verifySingleSCT(SignedCertificateTimestamp sct, CertificateEntry certEntry) {
        CTLogInfo log = this.store.getKnownLog(sct.getLogID());
        if (log == null) {
            return VerifiedSCT.Status.UNKNOWN_LOG;
        }
        return log.verifySingleSCT(sct, certEntry);
    }

    private void markSCTsAsInvalid(List<SignedCertificateTimestamp> scts, CTVerificationResult result) {
        for (SignedCertificateTimestamp sct : scts) {
            result.add(new VerifiedSCT(sct, VerifiedSCT.Status.INVALID_SCT));
        }
    }

    private static List<SignedCertificateTimestamp> getSCTsFromSCTList(byte[] data, SignedCertificateTimestamp.Origin origin) {
        byte[][] sctList;
        if (data == null) {
            return Collections.emptyList();
        }
        try {
            sctList = Serialization.readList(data, 2, 2);
        }
        catch (SerializationException e) {
            return Collections.emptyList();
        }
        ArrayList<SignedCertificateTimestamp> scts = new ArrayList<SignedCertificateTimestamp>();
        for (byte[] encodedSCT : sctList) {
            try {
                SignedCertificateTimestamp sct = SignedCertificateTimestamp.decode(encodedSCT, origin);
                scts.add(sct);
            }
            catch (SerializationException serializationException) {
                // empty catch block
            }
        }
        return scts;
    }

    private List<SignedCertificateTimestamp> getSCTsFromTLSExtension(byte[] data) {
        return CTVerifier.getSCTsFromSCTList(data, SignedCertificateTimestamp.Origin.TLS_EXTENSION);
    }

    private List<SignedCertificateTimestamp> getSCTsFromOCSPResponse(byte[] data, OpenSSLX509Certificate[] chain) {
        if (data == null || chain.length < 2) {
            return Collections.emptyList();
        }
        byte[] extData = NativeCrypto.get_ocsp_single_extension(data, "1.3.6.1.4.1.11129.2.4.5", chain[0].getContext(), chain[0], chain[1].getContext(), chain[1]);
        if (extData == null) {
            return Collections.emptyList();
        }
        try {
            return CTVerifier.getSCTsFromSCTList(Serialization.readDEROctetString(Serialization.readDEROctetString(extData)), SignedCertificateTimestamp.Origin.OCSP_RESPONSE);
        }
        catch (SerializationException e) {
            return Collections.emptyList();
        }
    }

    private List<SignedCertificateTimestamp> getSCTsFromX509Extension(OpenSSLX509Certificate leaf) {
        byte[] extData = leaf.getExtensionValue("1.3.6.1.4.1.11129.2.4.2");
        if (extData == null) {
            return Collections.emptyList();
        }
        try {
            return CTVerifier.getSCTsFromSCTList(Serialization.readDEROctetString(Serialization.readDEROctetString(extData)), SignedCertificateTimestamp.Origin.EMBEDDED);
        }
        catch (SerializationException e) {
            return Collections.emptyList();
        }
    }
}

