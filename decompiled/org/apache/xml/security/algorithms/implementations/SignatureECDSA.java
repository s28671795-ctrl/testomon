/*
 * Decompiled with CFR 0.152.
 */
package org.apache.xml.security.algorithms.implementations;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.AlgorithmParameterSpec;
import org.apache.xml.security.algorithms.JCEMapper;
import org.apache.xml.security.algorithms.SignatureAlgorithmSpi;
import org.apache.xml.security.algorithms.implementations.ECDSAUtils;
import org.apache.xml.security.signature.XMLSignatureException;
import org.apache.xml.security.utils.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SignatureECDSA
extends SignatureAlgorithmSpi {
    private static final Logger LOG = LoggerFactory.getLogger(SignatureECDSA.class);
    private Signature signatureAlgorithm;

    @Override
    public abstract String engineGetURI();

    public static byte[] convertASN1toXMLDSIG(byte[] asn1Bytes) throws IOException {
        return ECDSAUtils.convertASN1toXMLDSIG(asn1Bytes);
    }

    public static byte[] convertXMLDSIGtoASN1(byte[] xmldsigBytes) throws IOException {
        return ECDSAUtils.convertXMLDSIGtoASN1(xmldsigBytes);
    }

    public SignatureECDSA() throws XMLSignatureException {
        String algorithmID = JCEMapper.translateURItoJCEID(this.engineGetURI());
        LOG.debug("Created SignatureECDSA using {}", (Object)algorithmID);
        String provider = JCEMapper.getProviderId();
        try {
            this.signatureAlgorithm = provider == null ? Signature.getInstance(algorithmID) : Signature.getInstance(algorithmID, provider);
        }
        catch (NoSuchAlgorithmException ex) {
            Object[] exArgs = new Object[]{algorithmID, ex.getLocalizedMessage()};
            throw new XMLSignatureException("algorithms.NoSuchAlgorithm", exArgs);
        }
        catch (NoSuchProviderException ex) {
            Object[] exArgs = new Object[]{algorithmID, ex.getLocalizedMessage()};
            throw new XMLSignatureException("algorithms.NoSuchAlgorithm", exArgs);
        }
    }

    @Override
    protected void engineSetParameter(AlgorithmParameterSpec params) throws XMLSignatureException {
        try {
            this.signatureAlgorithm.setParameter(params);
        }
        catch (InvalidAlgorithmParameterException ex) {
            throw new XMLSignatureException(ex);
        }
    }

    @Override
    protected boolean engineVerify(byte[] signature) throws XMLSignatureException {
        try {
            byte[] jcebytes = SignatureECDSA.convertXMLDSIGtoASN1(signature);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Called ECDSA.verify() on " + XMLUtils.encodeToString(signature));
            }
            return this.signatureAlgorithm.verify(jcebytes);
        }
        catch (SignatureException ex) {
            throw new XMLSignatureException(ex);
        }
        catch (IOException ex) {
            throw new XMLSignatureException(ex);
        }
    }

    @Override
    protected void engineInitVerify(Key publicKey) throws XMLSignatureException {
        if (!(publicKey instanceof PublicKey)) {
            String supplied = null;
            if (publicKey != null) {
                supplied = publicKey.getClass().getName();
            }
            String needed = PublicKey.class.getName();
            Object[] exArgs = new Object[]{supplied, needed};
            throw new XMLSignatureException("algorithms.WrongKeyForThisOperation", exArgs);
        }
        try {
            this.signatureAlgorithm.initVerify((PublicKey)publicKey);
        }
        catch (InvalidKeyException ex) {
            Signature sig = this.signatureAlgorithm;
            try {
                this.signatureAlgorithm = Signature.getInstance(this.signatureAlgorithm.getAlgorithm());
            }
            catch (Exception e) {
                LOG.debug("Exception when reinstantiating Signature: {}", e);
                this.signatureAlgorithm = sig;
            }
            throw new XMLSignatureException(ex);
        }
    }

    @Override
    protected byte[] engineSign() throws XMLSignatureException {
        try {
            byte[] jcebytes = this.signatureAlgorithm.sign();
            return SignatureECDSA.convertASN1toXMLDSIG(jcebytes);
        }
        catch (SignatureException ex) {
            throw new XMLSignatureException(ex);
        }
        catch (IOException ex) {
            throw new XMLSignatureException(ex);
        }
    }

    @Override
    protected void engineInitSign(Key privateKey, SecureRandom secureRandom) throws XMLSignatureException {
        if (!(privateKey instanceof PrivateKey)) {
            String supplied = null;
            if (privateKey != null) {
                supplied = privateKey.getClass().getName();
            }
            String needed = PrivateKey.class.getName();
            Object[] exArgs = new Object[]{supplied, needed};
            throw new XMLSignatureException("algorithms.WrongKeyForThisOperation", exArgs);
        }
        try {
            if (secureRandom == null) {
                this.signatureAlgorithm.initSign((PrivateKey)privateKey);
            } else {
                this.signatureAlgorithm.initSign((PrivateKey)privateKey, secureRandom);
            }
        }
        catch (InvalidKeyException ex) {
            throw new XMLSignatureException(ex);
        }
    }

    @Override
    protected void engineInitSign(Key privateKey) throws XMLSignatureException {
        this.engineInitSign(privateKey, (SecureRandom)null);
    }

    @Override
    protected void engineUpdate(byte[] input) throws XMLSignatureException {
        try {
            this.signatureAlgorithm.update(input);
        }
        catch (SignatureException ex) {
            throw new XMLSignatureException(ex);
        }
    }

    @Override
    protected void engineUpdate(byte input) throws XMLSignatureException {
        try {
            this.signatureAlgorithm.update(input);
        }
        catch (SignatureException ex) {
            throw new XMLSignatureException(ex);
        }
    }

    @Override
    protected void engineUpdate(byte[] buf, int offset, int len) throws XMLSignatureException {
        try {
            this.signatureAlgorithm.update(buf, offset, len);
        }
        catch (SignatureException ex) {
            throw new XMLSignatureException(ex);
        }
    }

    @Override
    protected String engineGetJCEAlgorithmString() {
        return this.signatureAlgorithm.getAlgorithm();
    }

    @Override
    protected String engineGetJCEProviderName() {
        return this.signatureAlgorithm.getProvider().getName();
    }

    @Override
    protected void engineSetHMACOutputLength(int HMACOutputLength) throws XMLSignatureException {
        throw new XMLSignatureException("algorithms.HMACOutputLengthOnlyForHMAC");
    }

    @Override
    protected void engineInitSign(Key signingKey, AlgorithmParameterSpec algorithmParameterSpec) throws XMLSignatureException {
        throw new XMLSignatureException("algorithms.CannotUseAlgorithmParameterSpecOnRSA");
    }

    public static class SignatureECDSARIPEMD160
    extends SignatureECDSA {
        @Override
        public String engineGetURI() {
            return "http://www.w3.org/2007/05/xmldsig-more#ecdsa-ripemd160";
        }
    }

    public static class SignatureECDSASHA512
    extends SignatureECDSA {
        @Override
        public String engineGetURI() {
            return "http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha512";
        }
    }

    public static class SignatureECDSASHA384
    extends SignatureECDSA {
        @Override
        public String engineGetURI() {
            return "http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha384";
        }
    }

    public static class SignatureECDSASHA256
    extends SignatureECDSA {
        @Override
        public String engineGetURI() {
            return "http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha256";
        }
    }

    public static class SignatureECDSASHA224
    extends SignatureECDSA {
        @Override
        public String engineGetURI() {
            return "http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha224";
        }
    }

    public static class SignatureECDSASHA1
    extends SignatureECDSA {
        @Override
        public String engineGetURI() {
            return "http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha1";
        }
    }
}

