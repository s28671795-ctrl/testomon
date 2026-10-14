/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.crypto;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.security.MessageDigest;
import java.security.NoSuchProviderException;
import java.security.cert.CertPath;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.security.auth.x500.X500Principal;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.crypto.DERDecoder;
import org.apache.wss4j.common.crypto.X509SubjectPublicKeyInfo;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class CryptoBase
implements Crypto {
    public static final String SKI_OID = "2.5.29.14";
    public static final String NAME_CONSTRAINTS_OID = "2.5.29.30";
    private static final Logger LOG = LoggerFactory.getLogger(CryptoBase.class);
    private static final Constructor<?> BC_509CLASS_CONS;
    protected CertificateFactory certificateFactory;
    private String defaultAlias;
    private String cryptoProvider;
    private String trustProvider;

    protected CryptoBase() {
    }

    @Override
    public String getCryptoProvider() {
        return this.cryptoProvider;
    }

    @Override
    public void setCryptoProvider(String provider) {
        this.cryptoProvider = provider;
    }

    @Override
    public void setTrustProvider(String provider) {
        this.trustProvider = provider;
    }

    @Override
    public String getTrustProvider() {
        return this.trustProvider;
    }

    @Override
    public String getDefaultX509Identifier() throws WSSecurityException {
        return this.defaultAlias;
    }

    @Override
    public void setDefaultX509Identifier(String identifier) {
        this.defaultAlias = identifier;
    }

    @Override
    public void setCertificateFactory(CertificateFactory certFactory) {
        this.certificateFactory = certFactory;
    }

    @Override
    public CertificateFactory getCertificateFactory() throws WSSecurityException {
        if (this.certificateFactory != null) {
            return this.certificateFactory;
        }
        try {
            String provider = this.getCryptoProvider();
            this.certificateFactory = provider == null || provider.length() == 0 ? CertificateFactory.getInstance("X.509") : CertificateFactory.getInstance("X.509", provider);
        }
        catch (CertificateException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.SECURITY_TOKEN_UNAVAILABLE, e, "unsupportedCertType");
        }
        catch (NoSuchProviderException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.SECURITY_TOKEN_UNAVAILABLE, e, "noSecProvider");
        }
        return this.certificateFactory;
    }

    @Override
    public X509Certificate loadCertificate(InputStream in) throws WSSecurityException {
        try {
            CertificateFactory certFactory = this.getCertificateFactory();
            return (X509Certificate)certFactory.generateCertificate(in);
        }
        catch (CertificateException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.SECURITY_TOKEN_UNAVAILABLE, e, "parseError");
        }
    }

    @Override
    public byte[] getSKIBytesFromCert(X509Certificate cert) throws WSSecurityException {
        byte[] derEncodedValue = cert.getExtensionValue(SKI_OID);
        if (cert.getVersion() < 3 || derEncodedValue == null) {
            X509SubjectPublicKeyInfo spki = new X509SubjectPublicKeyInfo(cert.getPublicKey());
            byte[] value = spki.getSubjectPublicKey();
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-1");
                return digest.digest(value);
            }
            catch (Exception ex) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.UNSUPPORTED_SECURITY_TOKEN, ex, "noSKIHandling", new Object[]{"No SKI certificate extension and no SHA1 message digest available"});
            }
        }
        DERDecoder extVal = new DERDecoder(derEncodedValue);
        extVal.expect((byte)4);
        extVal.getLength();
        extVal.expect((byte)4);
        int keyIDLen = extVal.getLength();
        return extVal.getBytes(keyIDLen);
    }

    @Override
    public byte[] getBytesFromCertificates(X509Certificate[] certs) throws WSSecurityException {
        try {
            CertPath path = this.getCertificateFactory().generateCertPath(Arrays.asList(certs));
            return path.getEncoded();
        }
        catch (CertificateEncodingException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.SECURITY_TOKEN_UNAVAILABLE, e, "encodeError");
        }
        catch (CertificateException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.SECURITY_TOKEN_UNAVAILABLE, e, "parseError");
        }
    }

    @Override
    public X509Certificate[] getCertificatesFromBytes(byte[] data) throws WSSecurityException {
        CertPath path = null;
        try (ByteArrayInputStream in = new ByteArrayInputStream(data);){
            path = this.getCertificateFactory().generateCertPath(in);
        }
        catch (IOException | CertificateException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.SECURITY_TOKEN_UNAVAILABLE, e, "parseError");
        }
        List<? extends Certificate> l = path.getCertificates();
        X509Certificate[] certs = new X509Certificate[l.size()];
        int i = 0;
        Iterator<? extends Certificate> iterator2 = l.iterator();
        while (iterator2.hasNext()) {
            certs[i++] = (X509Certificate)iterator2.next();
        }
        return certs;
    }

    protected Object createBCX509Name(String s2) {
        if (BC_509CLASS_CONS != null) {
            try {
                return BC_509CLASS_CONS.newInstance(s2);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return new X500Principal(s2);
    }

    protected boolean matchesSubjectDnPattern(X509Certificate cert, Collection<Pattern> subjectDNPatterns) {
        if (cert == null) {
            LOG.debug("The certificate is null so no constraints matching was possible");
            return false;
        }
        String subjectName = cert.getSubjectX500Principal().getName();
        if (subjectDNPatterns == null || subjectDNPatterns.isEmpty()) {
            LOG.warn("No Subject DN Certificate Constraints were defined. This could be a security issue");
            return true;
        }
        return this.matchesName(subjectName, subjectDNPatterns);
    }

    protected boolean matchesIssuerDnPattern(X509Certificate cert, Collection<Pattern> issuerDNPatterns) {
        if (cert == null) {
            LOG.debug("The certificate is null so no constraints matching was possible");
            return false;
        }
        String issuerDn = cert.getIssuerDN().getName();
        return this.matchesName(issuerDn, issuerDNPatterns);
    }

    protected boolean matchesName(String name, Collection<Pattern> patterns) {
        if (patterns != null && !patterns.isEmpty()) {
            if (name == null || name.isEmpty()) {
                LOG.debug("The name is null so no constraints matching was possible");
                return false;
            }
            boolean subjectMatch = false;
            for (Pattern subjectDNPattern : patterns) {
                Matcher matcher = subjectDNPattern.matcher(name);
                if (!matcher.matches()) continue;
                LOG.debug("Name {} matches with pattern {}", (Object)name, (Object)subjectDNPattern);
                subjectMatch = true;
                break;
            }
            if (!subjectMatch) {
                return false;
            }
        }
        return true;
    }

    protected byte[] getNameConstraints(X509Certificate cert) throws WSSecurityException {
        byte[] bytes = cert.getExtensionValue(NAME_CONSTRAINTS_OID);
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        switch (bytes[0]) {
            case 4: {
                DERDecoder extVal = new DERDecoder(bytes);
                extVal.expect((byte)4);
                int seqLen = extVal.getLength();
                return extVal.getBytes(seqLen);
            }
            case 48: {
                return bytes;
            }
        }
        throw new IllegalArgumentException("Invalid type for NameConstraints; must be Sequence or OctetString-encoded Sequence");
    }

    static {
        Constructor<?> cons = null;
        try {
            Class<?> c = Class.forName("org.bouncycastle.asn1.x500.X500Name");
            cons = c.getConstructor(String.class);
        }
        catch (Exception exception) {
            // empty catch block
        }
        BC_509CLASS_CONS = cons;
    }
}

