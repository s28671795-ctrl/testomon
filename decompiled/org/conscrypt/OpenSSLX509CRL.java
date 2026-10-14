/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CRLException;
import java.security.cert.Certificate;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLEntry;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.security.auth.x500.X500Principal;
import org.conscrypt.NativeCrypto;
import org.conscrypt.OidData;
import org.conscrypt.OpenSSLBIOInputStream;
import org.conscrypt.OpenSSLKey;
import org.conscrypt.OpenSSLKeyHolder;
import org.conscrypt.OpenSSLX509CRLEntry;
import org.conscrypt.OpenSSLX509Certificate;
import org.conscrypt.OpenSSLX509CertificateFactory;
import org.conscrypt.Platform;

final class OpenSSLX509CRL
extends X509CRL {
    private final long mContext;
    private final Date thisUpdate;
    private final Date nextUpdate;

    private OpenSSLX509CRL(long ctx) throws OpenSSLX509CertificateFactory.ParsingException {
        this.mContext = ctx;
        this.thisUpdate = OpenSSLX509CRL.toDate(NativeCrypto.X509_CRL_get_lastUpdate(this.mContext, this));
        this.nextUpdate = OpenSSLX509CRL.toDate(NativeCrypto.X509_CRL_get_nextUpdate(this.mContext, this));
    }

    static Date toDate(long asn1time) throws OpenSSLX509CertificateFactory.ParsingException {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.set(14, 0);
        NativeCrypto.ASN1_TIME_to_Calendar(asn1time, calendar);
        return calendar.getTime();
    }

    static OpenSSLX509CRL fromX509DerInputStream(InputStream is) throws OpenSSLX509CertificateFactory.ParsingException {
        OpenSSLBIOInputStream bis = new OpenSSLBIOInputStream(is, true);
        try {
            long crlCtx = NativeCrypto.d2i_X509_CRL_bio(bis.getBioContext());
            if (crlCtx == 0L) {
                OpenSSLX509CRL openSSLX509CRL = null;
                return openSSLX509CRL;
            }
            OpenSSLX509CRL openSSLX509CRL = new OpenSSLX509CRL(crlCtx);
            return openSSLX509CRL;
        }
        catch (Exception e) {
            throw new OpenSSLX509CertificateFactory.ParsingException(e);
        }
        finally {
            bis.release();
        }
    }

    static List<OpenSSLX509CRL> fromPkcs7DerInputStream(InputStream is) throws OpenSSLX509CertificateFactory.ParsingException {
        long[] certRefs;
        OpenSSLBIOInputStream bis = new OpenSSLBIOInputStream(is, true);
        try {
            certRefs = NativeCrypto.d2i_PKCS7_bio(bis.getBioContext(), 2);
        }
        catch (Exception e) {
            throw new OpenSSLX509CertificateFactory.ParsingException(e);
        }
        finally {
            bis.release();
        }
        ArrayList<OpenSSLX509CRL> certs = new ArrayList<OpenSSLX509CRL>(certRefs.length);
        for (int i = 0; i < certRefs.length; ++i) {
            if (certRefs[i] == 0L) continue;
            certs.add(new OpenSSLX509CRL(certRefs[i]));
        }
        return certs;
    }

    static OpenSSLX509CRL fromX509PemInputStream(InputStream is) throws OpenSSLX509CertificateFactory.ParsingException {
        OpenSSLBIOInputStream bis = new OpenSSLBIOInputStream(is, true);
        try {
            long crlCtx = NativeCrypto.PEM_read_bio_X509_CRL(bis.getBioContext());
            if (crlCtx == 0L) {
                OpenSSLX509CRL openSSLX509CRL = null;
                return openSSLX509CRL;
            }
            OpenSSLX509CRL openSSLX509CRL = new OpenSSLX509CRL(crlCtx);
            return openSSLX509CRL;
        }
        catch (Exception e) {
            throw new OpenSSLX509CertificateFactory.ParsingException(e);
        }
        finally {
            bis.release();
        }
    }

    static List<OpenSSLX509CRL> fromPkcs7PemInputStream(InputStream is) throws OpenSSLX509CertificateFactory.ParsingException {
        long[] certRefs;
        OpenSSLBIOInputStream bis = new OpenSSLBIOInputStream(is, true);
        try {
            certRefs = NativeCrypto.PEM_read_bio_PKCS7(bis.getBioContext(), 2);
        }
        catch (Exception e) {
            throw new OpenSSLX509CertificateFactory.ParsingException(e);
        }
        finally {
            bis.release();
        }
        ArrayList<OpenSSLX509CRL> certs = new ArrayList<OpenSSLX509CRL>(certRefs.length);
        for (int i = 0; i < certRefs.length; ++i) {
            if (certRefs[i] == 0L) continue;
            certs.add(new OpenSSLX509CRL(certRefs[i]));
        }
        return certs;
    }

    @Override
    public Set<String> getCriticalExtensionOIDs() {
        String[] critOids = NativeCrypto.get_X509_CRL_ext_oids(this.mContext, this, 1);
        if (critOids.length == 0 && NativeCrypto.get_X509_CRL_ext_oids(this.mContext, this, 0).length == 0) {
            return null;
        }
        return new HashSet<String>(Arrays.asList(critOids));
    }

    @Override
    public byte[] getExtensionValue(String oid) {
        return NativeCrypto.X509_CRL_get_ext_oid(this.mContext, this, oid);
    }

    @Override
    public Set<String> getNonCriticalExtensionOIDs() {
        String[] nonCritOids = NativeCrypto.get_X509_CRL_ext_oids(this.mContext, this, 0);
        if (nonCritOids.length == 0 && NativeCrypto.get_X509_CRL_ext_oids(this.mContext, this, 1).length == 0) {
            return null;
        }
        return new HashSet<String>(Arrays.asList(nonCritOids));
    }

    @Override
    public boolean hasUnsupportedCriticalExtension() {
        String[] criticalOids;
        for (String oid : criticalOids = NativeCrypto.get_X509_CRL_ext_oids(this.mContext, this, 1)) {
            long extensionRef = NativeCrypto.X509_CRL_get_ext(this.mContext, this, oid);
            if (NativeCrypto.X509_supported_extension(extensionRef) == 1) continue;
            return true;
        }
        return false;
    }

    @Override
    public byte[] getEncoded() throws CRLException {
        return NativeCrypto.i2d_X509_CRL(this.mContext, this);
    }

    private void verifyOpenSSL(OpenSSLKey pkey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        try {
            NativeCrypto.X509_CRL_verify(this.mContext, this, pkey.getNativeRef());
        }
        catch (BadPaddingException | IllegalBlockSizeException e) {
            throw new SignatureException(e);
        }
    }

    private void verifyInternal(PublicKey key, String sigProvider) throws CRLException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        String sigAlg = this.getSigAlgName();
        if (sigAlg == null) {
            sigAlg = this.getSigAlgOID();
        }
        Signature sig = sigProvider == null ? Signature.getInstance(sigAlg) : Signature.getInstance(sigAlg, sigProvider);
        sig.initVerify(key);
        sig.update(this.getTBSCertList());
        if (!sig.verify(this.getSignature())) {
            throw new SignatureException("signature did not verify");
        }
    }

    @Override
    public void verify(PublicKey key) throws CRLException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        if (key instanceof OpenSSLKeyHolder) {
            OpenSSLKey pkey = ((OpenSSLKeyHolder)((Object)key)).getOpenSSLKey();
            this.verifyOpenSSL(pkey);
            return;
        }
        this.verifyInternal(key, null);
    }

    @Override
    public void verify(PublicKey key, String sigProvider) throws CRLException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        this.verifyInternal(key, sigProvider);
    }

    @Override
    public int getVersion() {
        return (int)NativeCrypto.X509_CRL_get_version(this.mContext, this) + 1;
    }

    @Override
    public Principal getIssuerDN() {
        return this.getIssuerX500Principal();
    }

    @Override
    public X500Principal getIssuerX500Principal() {
        byte[] issuer = NativeCrypto.X509_CRL_get_issuer_name(this.mContext, this);
        return new X500Principal(issuer);
    }

    @Override
    public Date getThisUpdate() {
        return (Date)this.thisUpdate.clone();
    }

    @Override
    public Date getNextUpdate() {
        return (Date)this.nextUpdate.clone();
    }

    @Override
    public X509CRLEntry getRevokedCertificate(BigInteger serialNumber) {
        long revokedRef = NativeCrypto.X509_CRL_get0_by_serial(this.mContext, this, serialNumber.toByteArray());
        if (revokedRef == 0L) {
            return null;
        }
        try {
            return new OpenSSLX509CRLEntry(NativeCrypto.X509_REVOKED_dup(revokedRef));
        }
        catch (OpenSSLX509CertificateFactory.ParsingException e) {
            return null;
        }
    }

    @Override
    public X509CRLEntry getRevokedCertificate(X509Certificate certificate) {
        if (certificate instanceof OpenSSLX509Certificate) {
            OpenSSLX509Certificate osslCert = (OpenSSLX509Certificate)certificate;
            long x509RevokedRef = NativeCrypto.X509_CRL_get0_by_cert(this.mContext, this, osslCert.getContext(), osslCert);
            if (x509RevokedRef == 0L) {
                return null;
            }
            try {
                return new OpenSSLX509CRLEntry(NativeCrypto.X509_REVOKED_dup(x509RevokedRef));
            }
            catch (OpenSSLX509CertificateFactory.ParsingException e) {
                return null;
            }
        }
        return this.getRevokedCertificate(certificate.getSerialNumber());
    }

    @Override
    public Set<? extends X509CRLEntry> getRevokedCertificates() {
        long[] entryRefs = NativeCrypto.X509_CRL_get_REVOKED(this.mContext, this);
        if (entryRefs == null || entryRefs.length == 0) {
            return null;
        }
        HashSet<OpenSSLX509CRLEntry> crlSet = new HashSet<OpenSSLX509CRLEntry>();
        for (long entryRef : entryRefs) {
            try {
                crlSet.add(new OpenSSLX509CRLEntry(entryRef));
            }
            catch (OpenSSLX509CertificateFactory.ParsingException parsingException) {
                // empty catch block
            }
        }
        return crlSet;
    }

    @Override
    public byte[] getTBSCertList() throws CRLException {
        return NativeCrypto.get_X509_CRL_crl_enc(this.mContext, this);
    }

    @Override
    public byte[] getSignature() {
        return NativeCrypto.get_X509_CRL_signature(this.mContext, this);
    }

    @Override
    public String getSigAlgName() {
        String oid = this.getSigAlgOID();
        String algName = OidData.oidToAlgorithmName(oid);
        if (algName != null) {
            return algName;
        }
        algName = Platform.oidToAlgorithmName(oid);
        if (algName != null) {
            return algName;
        }
        return oid;
    }

    @Override
    public String getSigAlgOID() {
        return NativeCrypto.get_X509_CRL_sig_alg_oid(this.mContext, this);
    }

    @Override
    public byte[] getSigAlgParams() {
        return NativeCrypto.get_X509_CRL_sig_alg_parameter(this.mContext, this);
    }

    @Override
    public boolean isRevoked(Certificate cert) {
        OpenSSLX509Certificate osslCert;
        if (!(cert instanceof X509Certificate)) {
            return false;
        }
        if (cert instanceof OpenSSLX509Certificate) {
            osslCert = (OpenSSLX509Certificate)cert;
        } else {
            try {
                osslCert = OpenSSLX509Certificate.fromX509DerInputStream(new ByteArrayInputStream(cert.getEncoded()));
            }
            catch (Exception e) {
                throw new RuntimeException("cannot convert certificate", e);
            }
        }
        long x509RevokedRef = NativeCrypto.X509_CRL_get0_by_cert(this.mContext, this, osslCert.getContext(), osslCert);
        return x509RevokedRef != 0L;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public String toString() {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        long bioCtx = NativeCrypto.create_BIO_OutputStream(os);
        try {
            NativeCrypto.X509_CRL_print(bioCtx, this.mContext, this);
            String string = os.toString();
            return string;
        }
        finally {
            NativeCrypto.BIO_free_all(bioCtx);
        }
    }

    protected void finalize() throws Throwable {
        try {
            if (this.mContext != 0L) {
                NativeCrypto.X509_CRL_free(this.mContext, this);
            }
        }
        finally {
            super.finalize();
        }
    }
}

