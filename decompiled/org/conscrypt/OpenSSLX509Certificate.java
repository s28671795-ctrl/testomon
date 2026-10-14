/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import javax.crypto.BadPaddingException;
import javax.security.auth.x500.X500Principal;
import org.conscrypt.NativeCrypto;
import org.conscrypt.OidData;
import org.conscrypt.OpenSSLBIOInputStream;
import org.conscrypt.OpenSSLKey;
import org.conscrypt.OpenSSLKeyHolder;
import org.conscrypt.OpenSSLProvider;
import org.conscrypt.OpenSSLX509CertificateFactory;
import org.conscrypt.Platform;
import org.conscrypt.X509PublicKey;

public final class OpenSSLX509Certificate
extends X509Certificate {
    private static final long serialVersionUID = 1992239142393372128L;
    private final transient long mContext;
    private transient Integer mHashCode;
    private final Date notBefore;
    private final Date notAfter;

    OpenSSLX509Certificate(long ctx) throws OpenSSLX509CertificateFactory.ParsingException {
        this.mContext = ctx;
        this.notBefore = OpenSSLX509Certificate.toDate(NativeCrypto.X509_get_notBefore(this.mContext, this));
        this.notAfter = OpenSSLX509Certificate.toDate(NativeCrypto.X509_get_notAfter(this.mContext, this));
    }

    private OpenSSLX509Certificate(long ctx, Date notBefore, Date notAfter) {
        this.mContext = ctx;
        this.notBefore = notBefore;
        this.notAfter = notAfter;
    }

    private static Date toDate(long asn1time) throws OpenSSLX509CertificateFactory.ParsingException {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.set(14, 0);
        NativeCrypto.ASN1_TIME_to_Calendar(asn1time, calendar);
        return calendar.getTime();
    }

    public static OpenSSLX509Certificate fromX509DerInputStream(InputStream is) throws OpenSSLX509CertificateFactory.ParsingException {
        OpenSSLBIOInputStream bis = new OpenSSLBIOInputStream(is, true);
        try {
            long certCtx = NativeCrypto.d2i_X509_bio(bis.getBioContext());
            if (certCtx == 0L) {
                OpenSSLX509Certificate openSSLX509Certificate = null;
                return openSSLX509Certificate;
            }
            OpenSSLX509Certificate openSSLX509Certificate = new OpenSSLX509Certificate(certCtx);
            return openSSLX509Certificate;
        }
        catch (Exception e) {
            throw new OpenSSLX509CertificateFactory.ParsingException(e);
        }
        finally {
            bis.release();
        }
    }

    public static OpenSSLX509Certificate fromX509Der(byte[] encoded) throws CertificateEncodingException {
        try {
            return new OpenSSLX509Certificate(NativeCrypto.d2i_X509(encoded));
        }
        catch (OpenSSLX509CertificateFactory.ParsingException e) {
            throw new CertificateEncodingException(e);
        }
    }

    public static List<OpenSSLX509Certificate> fromPkcs7DerInputStream(InputStream is) throws OpenSSLX509CertificateFactory.ParsingException {
        long[] certRefs;
        OpenSSLBIOInputStream bis = new OpenSSLBIOInputStream(is, true);
        try {
            certRefs = NativeCrypto.d2i_PKCS7_bio(bis.getBioContext(), 1);
        }
        catch (Exception e) {
            throw new OpenSSLX509CertificateFactory.ParsingException(e);
        }
        finally {
            bis.release();
        }
        if (certRefs == null) {
            return new ArrayList<OpenSSLX509Certificate>();
        }
        ArrayList<OpenSSLX509Certificate> certs = new ArrayList<OpenSSLX509Certificate>(certRefs.length);
        for (int i = 0; i < certRefs.length; ++i) {
            if (certRefs[i] == 0L) continue;
            certs.add(new OpenSSLX509Certificate(certRefs[i]));
        }
        return certs;
    }

    public static OpenSSLX509Certificate fromX509PemInputStream(InputStream is) throws OpenSSLX509CertificateFactory.ParsingException {
        OpenSSLBIOInputStream bis = new OpenSSLBIOInputStream(is, true);
        try {
            long certCtx = NativeCrypto.PEM_read_bio_X509(bis.getBioContext());
            if (certCtx == 0L) {
                OpenSSLX509Certificate openSSLX509Certificate = null;
                return openSSLX509Certificate;
            }
            OpenSSLX509Certificate openSSLX509Certificate = new OpenSSLX509Certificate(certCtx);
            return openSSLX509Certificate;
        }
        catch (Exception e) {
            throw new OpenSSLX509CertificateFactory.ParsingException(e);
        }
        finally {
            bis.release();
        }
    }

    public static List<OpenSSLX509Certificate> fromPkcs7PemInputStream(InputStream is) throws OpenSSLX509CertificateFactory.ParsingException {
        long[] certRefs;
        OpenSSLBIOInputStream bis = new OpenSSLBIOInputStream(is, true);
        try {
            certRefs = NativeCrypto.PEM_read_bio_PKCS7(bis.getBioContext(), 1);
        }
        catch (Exception e) {
            throw new OpenSSLX509CertificateFactory.ParsingException(e);
        }
        finally {
            bis.release();
        }
        ArrayList<OpenSSLX509Certificate> certs = new ArrayList<OpenSSLX509Certificate>(certRefs.length);
        for (int i = 0; i < certRefs.length; ++i) {
            if (certRefs[i] == 0L) continue;
            certs.add(new OpenSSLX509Certificate(certRefs[i]));
        }
        return certs;
    }

    public static OpenSSLX509Certificate fromCertificate(Certificate cert) throws CertificateEncodingException {
        if (cert instanceof OpenSSLX509Certificate) {
            return (OpenSSLX509Certificate)cert;
        }
        if (cert instanceof X509Certificate) {
            return OpenSSLX509Certificate.fromX509Der(cert.getEncoded());
        }
        throw new CertificateEncodingException("Only X.509 certificates are supported");
    }

    @Override
    public Set<String> getCriticalExtensionOIDs() {
        String[] critOids = NativeCrypto.get_X509_ext_oids(this.mContext, this, 1);
        if (critOids.length == 0 && NativeCrypto.get_X509_ext_oids(this.mContext, this, 0).length == 0) {
            return null;
        }
        return new HashSet<String>(Arrays.asList(critOids));
    }

    @Override
    public byte[] getExtensionValue(String oid) {
        return NativeCrypto.X509_get_ext_oid(this.mContext, this, oid);
    }

    @Override
    public Set<String> getNonCriticalExtensionOIDs() {
        String[] nonCritOids = NativeCrypto.get_X509_ext_oids(this.mContext, this, 0);
        if (nonCritOids.length == 0 && NativeCrypto.get_X509_ext_oids(this.mContext, this, 1).length == 0) {
            return null;
        }
        return new HashSet<String>(Arrays.asList(nonCritOids));
    }

    @Override
    public boolean hasUnsupportedCriticalExtension() {
        return (NativeCrypto.get_X509_ex_flags(this.mContext, this) & 0x200) != 0;
    }

    @Override
    public void checkValidity() throws CertificateExpiredException, CertificateNotYetValidException {
        this.checkValidity(new Date());
    }

    @Override
    public void checkValidity(Date date) throws CertificateExpiredException, CertificateNotYetValidException {
        if (this.getNotBefore().compareTo(date) > 0) {
            throw new CertificateNotYetValidException("Certificate not valid until " + this.getNotBefore().toString() + " (compared to " + date.toString() + ")");
        }
        if (this.getNotAfter().compareTo(date) < 0) {
            throw new CertificateExpiredException("Certificate expired at " + this.getNotAfter().toString() + " (compared to " + date.toString() + ")");
        }
    }

    @Override
    public int getVersion() {
        return (int)NativeCrypto.X509_get_version(this.mContext, this) + 1;
    }

    @Override
    public BigInteger getSerialNumber() {
        return new BigInteger(NativeCrypto.X509_get_serialNumber(this.mContext, this));
    }

    @Override
    public Principal getIssuerDN() {
        return this.getIssuerX500Principal();
    }

    @Override
    public Principal getSubjectDN() {
        return this.getSubjectX500Principal();
    }

    @Override
    public Date getNotBefore() {
        return (Date)this.notBefore.clone();
    }

    @Override
    public Date getNotAfter() {
        return (Date)this.notAfter.clone();
    }

    @Override
    public byte[] getTBSCertificate() throws CertificateEncodingException {
        return NativeCrypto.get_X509_cert_info_enc(this.mContext, this);
    }

    @Override
    public byte[] getSignature() {
        return NativeCrypto.get_X509_signature(this.mContext, this);
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
        return NativeCrypto.get_X509_sig_alg_oid(this.mContext, this);
    }

    @Override
    public byte[] getSigAlgParams() {
        return NativeCrypto.get_X509_sig_alg_parameter(this.mContext, this);
    }

    @Override
    public boolean[] getIssuerUniqueID() {
        return NativeCrypto.get_X509_issuerUID(this.mContext, this);
    }

    @Override
    public boolean[] getSubjectUniqueID() {
        return NativeCrypto.get_X509_subjectUID(this.mContext, this);
    }

    @Override
    public boolean[] getKeyUsage() {
        boolean[] kusage = NativeCrypto.get_X509_ex_kusage(this.mContext, this);
        if (kusage == null) {
            return null;
        }
        if (kusage.length >= 9) {
            return kusage;
        }
        boolean[] resized = new boolean[9];
        System.arraycopy(kusage, 0, resized, 0, kusage.length);
        return resized;
    }

    @Override
    public int getBasicConstraints() {
        if ((NativeCrypto.get_X509_ex_flags(this.mContext, this) & 0x10) == 0) {
            return -1;
        }
        int pathLen = NativeCrypto.get_X509_ex_pathlen(this.mContext, this);
        if (pathLen == -1) {
            return Integer.MAX_VALUE;
        }
        return pathLen;
    }

    @Override
    public byte[] getEncoded() throws CertificateEncodingException {
        return NativeCrypto.i2d_X509(this.mContext, this);
    }

    private void verifyOpenSSL(OpenSSLKey pkey) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        try {
            NativeCrypto.X509_verify(this.mContext, this, pkey.getNativeRef());
        }
        catch (RuntimeException e) {
            throw new CertificateException(e);
        }
        catch (BadPaddingException e) {
            throw new SignatureException();
        }
    }

    private void verifyInternal(PublicKey key, String sigProvider) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        Signature sig = sigProvider == null ? Signature.getInstance(this.getSigAlgName()) : Signature.getInstance(this.getSigAlgName(), sigProvider);
        sig.initVerify(key);
        sig.update(this.getTBSCertificate());
        if (!sig.verify(this.getSignature())) {
            throw new SignatureException("signature did not verify");
        }
    }

    @Override
    public void verify(PublicKey key) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        if (key instanceof OpenSSLKeyHolder) {
            OpenSSLKey pkey = ((OpenSSLKeyHolder)((Object)key)).getOpenSSLKey();
            this.verifyOpenSSL(pkey);
            return;
        }
        this.verifyInternal(key, null);
    }

    @Override
    public void verify(PublicKey key, String sigProvider) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        this.verifyInternal(key, sigProvider);
    }

    @Override
    public void verify(PublicKey key, Provider sigProvider) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        if (key instanceof OpenSSLKeyHolder && sigProvider instanceof OpenSSLProvider) {
            OpenSSLKey pkey = ((OpenSSLKeyHolder)((Object)key)).getOpenSSLKey();
            this.verifyOpenSSL(pkey);
            return;
        }
        Signature sig = sigProvider == null ? Signature.getInstance(this.getSigAlgName()) : Signature.getInstance(this.getSigAlgName(), sigProvider);
        sig.initVerify(key);
        sig.update(this.getTBSCertificate());
        if (!sig.verify(this.getSignature())) {
            throw new SignatureException("signature did not verify");
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public String toString() {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        long bioCtx = NativeCrypto.create_BIO_OutputStream(os);
        try {
            NativeCrypto.X509_print_ex(bioCtx, this.mContext, this, 0L, 0L);
            String string = os.toString();
            return string;
        }
        finally {
            NativeCrypto.BIO_free_all(bioCtx);
        }
    }

    @Override
    public PublicKey getPublicKey() {
        try {
            OpenSSLKey pkey = new OpenSSLKey(NativeCrypto.X509_get_pubkey(this.mContext, this));
            return pkey.getPublicKey();
        }
        catch (InvalidKeyException | NoSuchAlgorithmException pkey) {
            String oid = NativeCrypto.get_X509_pubkey_oid(this.mContext, this);
            byte[] encoded = NativeCrypto.i2d_X509_PUBKEY(this.mContext, this);
            try {
                KeyFactory kf = KeyFactory.getInstance(oid);
                return kf.generatePublic(new X509EncodedKeySpec(encoded));
            }
            catch (NoSuchAlgorithmException | InvalidKeySpecException generalSecurityException) {
                return new X509PublicKey(oid, encoded);
            }
        }
    }

    @Override
    public X500Principal getIssuerX500Principal() {
        byte[] issuer = NativeCrypto.X509_get_issuer_name(this.mContext, this);
        return new X500Principal(issuer);
    }

    @Override
    public X500Principal getSubjectX500Principal() {
        byte[] subject = NativeCrypto.X509_get_subject_name(this.mContext, this);
        return new X500Principal(subject);
    }

    @Override
    public List<String> getExtendedKeyUsage() throws CertificateParsingException {
        String[] extUsage = NativeCrypto.get_X509_ex_xkusage(this.mContext, this);
        if (extUsage == null) {
            return null;
        }
        return Arrays.asList(extUsage);
    }

    private static Collection<List<?>> alternativeNameArrayToList(Object[][] altNameArray) {
        if (altNameArray == null) {
            return null;
        }
        ArrayList<List<Object>> coll = new ArrayList<List<Object>>(altNameArray.length);
        for (int i = 0; i < altNameArray.length; ++i) {
            coll.add(Collections.unmodifiableList(Arrays.asList(altNameArray[i])));
        }
        return Collections.unmodifiableCollection(coll);
    }

    @Override
    public Collection<List<?>> getSubjectAlternativeNames() throws CertificateParsingException {
        return OpenSSLX509Certificate.alternativeNameArrayToList(NativeCrypto.get_X509_GENERAL_NAME_stack(this.mContext, this, 1));
    }

    @Override
    public Collection<List<?>> getIssuerAlternativeNames() throws CertificateParsingException {
        return OpenSSLX509Certificate.alternativeNameArrayToList(NativeCrypto.get_X509_GENERAL_NAME_stack(this.mContext, this, 2));
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof OpenSSLX509Certificate) {
            OpenSSLX509Certificate o = (OpenSSLX509Certificate)other;
            return NativeCrypto.X509_cmp(this.mContext, this, o.mContext, o) == 0;
        }
        return super.equals(other);
    }

    @Override
    public int hashCode() {
        if (this.mHashCode != null) {
            return this.mHashCode;
        }
        this.mHashCode = super.hashCode();
        return this.mHashCode;
    }

    public long getContext() {
        return this.mContext;
    }

    public OpenSSLX509Certificate withDeletedExtension(String oid) {
        OpenSSLX509Certificate copy = new OpenSSLX509Certificate(NativeCrypto.X509_dup(this.mContext, this), this.notBefore, this.notAfter);
        NativeCrypto.X509_delete_ext(copy.getContext(), copy, oid);
        return copy;
    }

    protected void finalize() throws Throwable {
        try {
            if (this.mContext != 0L) {
                NativeCrypto.X509_free(this.mContext, this);
            }
        }
        finally {
            super.finalize();
        }
    }
}

