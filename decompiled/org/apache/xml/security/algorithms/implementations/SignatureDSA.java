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
import java.security.interfaces.DSAKey;
import java.security.spec.AlgorithmParameterSpec;
import org.apache.xml.security.algorithms.JCEMapper;
import org.apache.xml.security.algorithms.SignatureAlgorithmSpi;
import org.apache.xml.security.signature.XMLSignatureException;
import org.apache.xml.security.utils.JavaUtils;
import org.apache.xml.security.utils.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SignatureDSA
extends SignatureAlgorithmSpi {
    public static final String URI = "http://www.w3.org/2000/09/xmldsig#dsa-sha1";
    private static final Logger LOG = LoggerFactory.getLogger(SignatureDSA.class);
    private Signature signatureAlgorithm;
    private int size;

    @Override
    protected String engineGetURI() {
        return URI;
    }

    public SignatureDSA() throws XMLSignatureException {
        String algorithmID = JCEMapper.translateURItoJCEID(this.engineGetURI());
        LOG.debug("Created SignatureDSA using {}", (Object)algorithmID);
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
            if (LOG.isDebugEnabled()) {
                LOG.debug("Called DSA.verify() on " + XMLUtils.encodeToString(signature));
            }
            byte[] jcebytes = JavaUtils.convertDsaXMLDSIGtoASN1(signature, this.size / 8);
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
        this.size = ((DSAKey)((Object)publicKey)).getParams().getQ().bitLength();
    }

    @Override
    protected byte[] engineSign() throws XMLSignatureException {
        try {
            byte[] jcebytes = this.signatureAlgorithm.sign();
            return JavaUtils.convertDsaASN1toXMLDSIG(jcebytes, this.size / 8);
        }
        catch (IOException ex) {
            throw new XMLSignatureException(ex);
        }
        catch (SignatureException ex) {
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
        this.size = ((DSAKey)((Object)privateKey)).getParams().getQ().bitLength();
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
        throw new XMLSignatureException("algorithms.CannotUseAlgorithmParameterSpecOnDSA");
    }

    public static class SHA256
    extends SignatureDSA {
        @Override
        public String engineGetURI() {
            return "http://www.w3.org/2009/xmldsig11#dsa-sha256";
        }
    }
}

