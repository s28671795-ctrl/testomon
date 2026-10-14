/*
 * Decompiled with CFR 0.152.
 */
package org.apache.jcp.xml.dsig.internal.dom;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.DSAKey;
import java.security.spec.AlgorithmParameterSpec;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.XMLSignContext;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLValidateContext;
import javax.xml.crypto.dsig.spec.SignatureMethodParameterSpec;
import org.apache.jcp.xml.dsig.internal.SignerOutputStream;
import org.apache.jcp.xml.dsig.internal.dom.AbstractDOMSignatureMethod;
import org.apache.jcp.xml.dsig.internal.dom.DOMHMACSignatureMethod;
import org.apache.jcp.xml.dsig.internal.dom.DOMSignedInfo;
import org.apache.jcp.xml.dsig.internal.dom.DOMUtils;
import org.apache.xml.security.algorithms.implementations.SignatureECDSA;
import org.apache.xml.security.utils.JavaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public abstract class DOMSignatureMethod
extends AbstractDOMSignatureMethod {
    private static final String DOM_SIGNATURE_PROVIDER = "org.jcp.xml.dsig.internal.dom.SignatureProvider";
    private static final Logger LOG = LoggerFactory.getLogger(DOMSignatureMethod.class);
    private SignatureMethodParameterSpec params;
    private Signature signature;
    static final String RSA_SHA224 = "http://www.w3.org/2001/04/xmldsig-more#rsa-sha224";
    static final String RSA_SHA256 = "http://www.w3.org/2001/04/xmldsig-more#rsa-sha256";
    static final String RSA_SHA384 = "http://www.w3.org/2001/04/xmldsig-more#rsa-sha384";
    static final String RSA_SHA512 = "http://www.w3.org/2001/04/xmldsig-more#rsa-sha512";
    static final String RSA_RIPEMD160 = "http://www.w3.org/2001/04/xmldsig-more#rsa-ripemd160";
    static final String ECDSA_SHA1 = "http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha1";
    static final String ECDSA_SHA224 = "http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha224";
    static final String ECDSA_SHA256 = "http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha256";
    static final String ECDSA_SHA384 = "http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha384";
    static final String ECDSA_SHA512 = "http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha512";
    static final String DSA_SHA256 = "http://www.w3.org/2009/xmldsig11#dsa-sha256";
    static final String ECDSA_RIPEMD160 = "http://www.w3.org/2007/05/xmldsig-more#ecdsa-ripemd160";
    static final String RSA_SHA1_MGF1 = "http://www.w3.org/2007/05/xmldsig-more#sha1-rsa-MGF1";
    static final String RSA_SHA224_MGF1 = "http://www.w3.org/2007/05/xmldsig-more#sha224-rsa-MGF1";
    static final String RSA_SHA256_MGF1 = "http://www.w3.org/2007/05/xmldsig-more#sha256-rsa-MGF1";
    static final String RSA_SHA384_MGF1 = "http://www.w3.org/2007/05/xmldsig-more#sha384-rsa-MGF1";
    static final String RSA_SHA512_MGF1 = "http://www.w3.org/2007/05/xmldsig-more#sha512-rsa-MGF1";
    static final String RSA_RIPEMD160_MGF1 = "http://www.w3.org/2007/05/xmldsig-more#ripemd160-rsa-MGF1";

    DOMSignatureMethod(AlgorithmParameterSpec params) throws InvalidAlgorithmParameterException {
        if (params != null && !(params instanceof SignatureMethodParameterSpec)) {
            throw new InvalidAlgorithmParameterException("params must be of type SignatureMethodParameterSpec");
        }
        this.checkParams((SignatureMethodParameterSpec)params);
        this.params = (SignatureMethodParameterSpec)params;
    }

    DOMSignatureMethod(Element smElem) throws MarshalException {
        Element paramsElem = DOMUtils.getFirstChildElement(smElem);
        if (paramsElem != null) {
            this.params = this.unmarshalParams(paramsElem);
        }
        try {
            this.checkParams(this.params);
        }
        catch (InvalidAlgorithmParameterException iape) {
            throw new MarshalException(iape);
        }
    }

    static SignatureMethod unmarshal(Element smElem) throws MarshalException {
        String alg = DOMUtils.getAttributeValue(smElem, "Algorithm");
        if (alg.equals("http://www.w3.org/2000/09/xmldsig#rsa-sha1")) {
            return new SHA1withRSA(smElem);
        }
        if (alg.equals(RSA_SHA224)) {
            return new SHA224withRSA(smElem);
        }
        if (alg.equals(RSA_SHA256)) {
            return new SHA256withRSA(smElem);
        }
        if (alg.equals(RSA_SHA384)) {
            return new SHA384withRSA(smElem);
        }
        if (alg.equals(RSA_SHA512)) {
            return new SHA512withRSA(smElem);
        }
        if (alg.equals(RSA_RIPEMD160)) {
            return new RIPEMD160withRSA(smElem);
        }
        if (alg.equals(RSA_SHA1_MGF1)) {
            return new SHA1withRSAandMGF1(smElem);
        }
        if (alg.equals(RSA_SHA224_MGF1)) {
            return new SHA224withRSAandMGF1(smElem);
        }
        if (alg.equals(RSA_SHA256_MGF1)) {
            return new SHA256withRSAandMGF1(smElem);
        }
        if (alg.equals(RSA_SHA384_MGF1)) {
            return new SHA384withRSAandMGF1(smElem);
        }
        if (alg.equals(RSA_SHA512_MGF1)) {
            return new SHA512withRSAandMGF1(smElem);
        }
        if (alg.equals(RSA_RIPEMD160_MGF1)) {
            return new RIPEMD160withRSAandMGF1(smElem);
        }
        if (alg.equals("http://www.w3.org/2000/09/xmldsig#dsa-sha1")) {
            return new SHA1withDSA(smElem);
        }
        if (alg.equals(DSA_SHA256)) {
            return new SHA256withDSA(smElem);
        }
        if (alg.equals(ECDSA_SHA1)) {
            return new SHA1withECDSA(smElem);
        }
        if (alg.equals(ECDSA_SHA224)) {
            return new SHA224withECDSA(smElem);
        }
        if (alg.equals(ECDSA_SHA256)) {
            return new SHA256withECDSA(smElem);
        }
        if (alg.equals(ECDSA_SHA384)) {
            return new SHA384withECDSA(smElem);
        }
        if (alg.equals(ECDSA_SHA512)) {
            return new SHA512withECDSA(smElem);
        }
        if (alg.equals(ECDSA_RIPEMD160)) {
            return new RIPEMD160withECDSA(smElem);
        }
        if (alg.equals("http://www.w3.org/2000/09/xmldsig#hmac-sha1")) {
            return new DOMHMACSignatureMethod.SHA1(smElem);
        }
        if (alg.equals("http://www.w3.org/2001/04/xmldsig-more#hmac-sha224")) {
            return new DOMHMACSignatureMethod.SHA224(smElem);
        }
        if (alg.equals("http://www.w3.org/2001/04/xmldsig-more#hmac-sha256")) {
            return new DOMHMACSignatureMethod.SHA256(smElem);
        }
        if (alg.equals("http://www.w3.org/2001/04/xmldsig-more#hmac-sha384")) {
            return new DOMHMACSignatureMethod.SHA384(smElem);
        }
        if (alg.equals("http://www.w3.org/2001/04/xmldsig-more#hmac-sha512")) {
            return new DOMHMACSignatureMethod.SHA512(smElem);
        }
        if (alg.equals("http://www.w3.org/2001/04/xmldsig-more#hmac-ripemd160")) {
            return new DOMHMACSignatureMethod.RIPEMD160(smElem);
        }
        throw new MarshalException("unsupported SignatureMethod algorithm: " + alg);
    }

    @Override
    public final AlgorithmParameterSpec getParameterSpec() {
        return this.params;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    boolean verify(Key key, SignedInfo si, byte[] sig, XMLValidateContext context) throws InvalidKeyException, SignatureException, XMLSignatureException {
        if (key == null) throw new NullPointerException();
        if (si == null) throw new NullPointerException();
        if (sig == null) {
            throw new NullPointerException();
        }
        if (!(key instanceof PublicKey)) {
            throw new InvalidKeyException("key must be PublicKey");
        }
        if (this.signature == null) {
            try {
                Provider p = (Provider)context.getProperty(DOM_SIGNATURE_PROVIDER);
                this.signature = p == null ? Signature.getInstance(this.getJCAAlgorithm()) : Signature.getInstance(this.getJCAAlgorithm(), p);
            }
            catch (NoSuchAlgorithmException nsae) {
                throw new XMLSignatureException(nsae);
            }
        }
        this.signature.initVerify((PublicKey)key);
        LOG.debug("Signature provider: {}", (Object)this.signature.getProvider());
        LOG.debug("Verifying with key: {}", (Object)key);
        LOG.debug("JCA Algorithm: {}", (Object)this.getJCAAlgorithm());
        LOG.debug("Signature Bytes length: {}", (Object)sig.length);
        try (SignerOutputStream outputStream2 = new SignerOutputStream(this.signature);){
            ((DOMSignedInfo)si).canonicalize(context, outputStream2);
            AbstractDOMSignatureMethod.Type type = this.getAlgorithmType();
            if (type == AbstractDOMSignatureMethod.Type.DSA) {
                int size = ((DSAKey)((Object)key)).getParams().getQ().bitLength();
                boolean bl = this.signature.verify(JavaUtils.convertDsaXMLDSIGtoASN1(sig, size / 8));
                return bl;
            }
            if (type == AbstractDOMSignatureMethod.Type.ECDSA) {
                boolean bl = this.signature.verify(SignatureECDSA.convertXMLDSIGtoASN1(sig));
                return bl;
            }
            boolean bl = this.signature.verify(sig);
            return bl;
        }
        catch (IOException ioe) {
            throw new XMLSignatureException(ioe);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    byte[] sign(Key key, SignedInfo si, XMLSignContext context) throws InvalidKeyException, XMLSignatureException {
        if (key == null) throw new NullPointerException();
        if (si == null) {
            throw new NullPointerException();
        }
        if (!(key instanceof PrivateKey)) {
            throw new InvalidKeyException("key must be PrivateKey");
        }
        if (this.signature == null) {
            try {
                Provider p = (Provider)context.getProperty(DOM_SIGNATURE_PROVIDER);
                this.signature = p == null ? Signature.getInstance(this.getJCAAlgorithm()) : Signature.getInstance(this.getJCAAlgorithm(), p);
            }
            catch (NoSuchAlgorithmException nsae) {
                throw new XMLSignatureException(nsae);
            }
        }
        this.signature.initSign((PrivateKey)key);
        LOG.debug("Signature provider: {}", (Object)this.signature.getProvider());
        LOG.debug("Signing with key: {}", (Object)key);
        LOG.debug("JCA Algorithm: {}", (Object)this.getJCAAlgorithm());
        try (SignerOutputStream outputStream2 = new SignerOutputStream(this.signature);){
            ((DOMSignedInfo)si).canonicalize(context, outputStream2);
            AbstractDOMSignatureMethod.Type type = this.getAlgorithmType();
            if (type == AbstractDOMSignatureMethod.Type.DSA) {
                int size = ((DSAKey)((Object)key)).getParams().getQ().bitLength();
                byte[] byArray = JavaUtils.convertDsaASN1toXMLDSIG(this.signature.sign(), size / 8);
                return byArray;
            }
            if (type == AbstractDOMSignatureMethod.Type.ECDSA) {
                byte[] byArray = SignatureECDSA.convertASN1toXMLDSIG(this.signature.sign());
                return byArray;
            }
            byte[] byArray = this.signature.sign();
            return byArray;
        }
        catch (SignatureException se) {
            throw new XMLSignatureException(se);
        }
        catch (IOException ioe) {
            throw new XMLSignatureException(ioe);
        }
    }

    static final class RIPEMD160withECDSA
    extends DOMSignatureMethod {
        RIPEMD160withECDSA(AlgorithmParameterSpec params) throws InvalidAlgorithmParameterException {
            super(params);
        }

        RIPEMD160withECDSA(Element dmElem) throws MarshalException {
            super(dmElem);
        }

        @Override
        public String getAlgorithm() {
            return DOMSignatureMethod.ECDSA_RIPEMD160;
        }

        @Override
        String getJCAAlgorithm() {
            return "RIPEMD160withECDSA";
        }

        @Override
        AbstractDOMSignatureMethod.Type getAlgorithmType() {
            return AbstractDOMSignatureMethod.Type.ECDSA;
        }
    }

    static final class SHA512withECDSA
    extends DOMSignatureMethod {
        SHA512withECDSA(AlgorithmParameterSpec params) throws InvalidAlgorithmParameterException {
            super(params);
        }

        SHA512withECDSA(Element dmElem) throws MarshalException {
            super(dmElem);
        }

        @Override
        public String getAlgorithm() {
            return DOMSignatureMethod.ECDSA_SHA512;
        }

        @Override
        String getJCAAlgorithm() {
            return "SHA512withECDSA";
        }

        @Override
        AbstractDOMSignatureMethod.Type getAlgorithmType() {
            return AbstractDOMSignatureMethod.Type.ECDSA;
        }
    }

    static final class SHA384withECDSA
    extends DOMSignatureMethod {
        SHA384withECDSA(AlgorithmParameterSpec params) throws InvalidAlgorithmParameterException {
            super(params);
        }

        SHA384withECDSA(Element dmElem) throws MarshalException {
            super(dmElem);
        }

        @Override
        public String getAlgorithm() {
            return DOMSignatureMethod.ECDSA_SHA384;
        }

        @Override
        String getJCAAlgorithm() {
            return "SHA384withECDSA";
        }

        @Override
        AbstractDOMSignatureMethod.Type getAlgorithmType() {
            return AbstractDOMSignatureMethod.Type.ECDSA;
        }
    }

    static final class SHA256withECDSA
    extends DOMSignatureMethod {
        SHA256withECDSA(AlgorithmParameterSpec params) throws InvalidAlgorithmParameterException {
            super(params);
        }

        SHA256withECDSA(Element dmElem) throws MarshalException {
            super(dmElem);
        }

        @Override
        public String getAlgorithm() {
            return DOMSignatureMethod.ECDSA_SHA256;
        }

        @Override
        String getJCAAlgorithm() {
            return "SHA256withECDSA";
        }

        @Override
        AbstractDOMSignatureMethod.Type getAlgorithmType() {
            return AbstractDOMSignatureMethod.Type.ECDSA;
        }
    }

    static final class SHA224withECDSA
    extends DOMSignatureMethod {
        SHA224withECDSA(AlgorithmParameterSpec params) throws InvalidAlgorithmParameterException {
            super(params);
        }

        SHA224withECDSA(Element dmElem) throws MarshalException {
            super(dmElem);
        }

        @Override
        public String getAlgorithm() {
            return DOMSignatureMethod.ECDSA_SHA224;
        }

        @Override
        String getJCAAlgorithm() {
            return "SHA224withECDSA";
        }

        @Override
        AbstractDOMSignatureMethod.Type getAlgorithmType() {
            return AbstractDOMSignatureMethod.Type.ECDSA;
        }
    }

    static final class SHA1withECDSA
    extends DOMSignatureMethod {
        SHA1withECDSA(AlgorithmParameterSpec params) throws InvalidAlgorithmParameterException {
            super(params);
        }

        SHA1withECDSA(Element dmElem) throws MarshalException {
            super(dmElem);
        }

        @Override
        public String getAlgorithm() {
            return DOMSignatureMethod.ECDSA_SHA1;
        }

        @Override
        String getJCAAlgorithm() {
            return "SHA1withECDSA";
        }

        @Override
        AbstractDOMSignatureMethod.Type getAlgorithmType() {
            return AbstractDOMSignatureMethod.Type.ECDSA;
        }
    }

    static final class SHA256withDSA
    extends DOMSignatureMethod {
        SHA256withDSA(AlgorithmParameterSpec params) throws InvalidAlgorithmParameterException {
            super(params);
        }

        SHA256withDSA(Element dmElem) throws MarshalException {
            super(dmElem);
        }

        @Override
        public String getAlgorithm() {
            return DOMSignatureMethod.DSA_SHA256;
        }

        @Override
        String getJCAAlgorithm() {
            return "SHA256withDSA";
        }

        @Override
        AbstractDOMSignatureMethod.Type getAlgorithmType() {
            return AbstractDOMSignatureMethod.Type.DSA;
        }
    }

    static final class SHA1withDSA
    extends DOMSignatureMethod {
        SHA1withDSA(AlgorithmParameterSpec params) throws InvalidAlgorithmParameterException {
            super(params);
        }

        SHA1withDSA(Element dmElem) throws MarshalException {
            super(dmElem);
        }

        @Override
        public String getAlgorithm() {
            return "http://www.w3.org/2000/09/xmldsig#dsa-sha1";
        }

        @Override
        String getJCAAlgorithm() {
            return "SHA1withDSA";
        }

        @Override
        AbstractDOMSignatureMethod.Type getAlgorithmType() {
            return AbstractDOMSignatureMethod.Type.DSA;
        }
    }

    static final class RIPEMD160withRSAandMGF1
    extends DOMSignatureMethod {
        RIPEMD160withRSAandMGF1(AlgorithmParameterSpec params) throws InvalidAlgorithmParameterException {
            super(params);
        }

        RIPEMD160withRSAandMGF1(Element dmElem) throws MarshalException {
            super(dmElem);
        }

        @Override
        public String getAlgorithm() {
            return DOMSignatureMethod.RSA_RIPEMD160_MGF1;
        }

        @Override
        String getJCAAlgorithm() {
            return "RIPEMD160withRSAandMGF1";
        }

        @Override
        AbstractDOMSignatureMethod.Type getAlgorithmType() {
            return AbstractDOMSignatureMethod.Type.RSA;
        }
    }

    static final class SHA512withRSAandMGF1
    extends DOMSignatureMethod {
        SHA512withRSAandMGF1(AlgorithmParameterSpec params) throws InvalidAlgorithmParameterException {
            super(params);
        }

        SHA512withRSAandMGF1(Element dmElem) throws MarshalException {
            super(dmElem);
        }

        @Override
        public String getAlgorithm() {
            return DOMSignatureMethod.RSA_SHA512_MGF1;
        }

        @Override
        String getJCAAlgorithm() {
            return "SHA512withRSAandMGF1";
        }

        @Override
        AbstractDOMSignatureMethod.Type getAlgorithmType() {
            return AbstractDOMSignatureMethod.Type.RSA;
        }
    }

    static final class SHA384withRSAandMGF1
    extends DOMSignatureMethod {
        SHA384withRSAandMGF1(AlgorithmParameterSpec params) throws InvalidAlgorithmParameterException {
            super(params);
        }

        SHA384withRSAandMGF1(Element dmElem) throws MarshalException {
            super(dmElem);
        }

        @Override
        public String getAlgorithm() {
            return DOMSignatureMethod.RSA_SHA384_MGF1;
        }

        @Override
        String getJCAAlgorithm() {
            return "SHA384withRSAandMGF1";
        }

        @Override
        AbstractDOMSignatureMethod.Type getAlgorithmType() {
            return AbstractDOMSignatureMethod.Type.RSA;
        }
    }

    static final class SHA256withRSAandMGF1
    extends DOMSignatureMethod {
        SHA256withRSAandMGF1(AlgorithmParameterSpec params) throws InvalidAlgorithmParameterException {
            super(params);
        }

        SHA256withRSAandMGF1(Element dmElem) throws MarshalException {
            super(dmElem);
        }

        @Override
        public String getAlgorithm() {
            return DOMSignatureMethod.RSA_SHA256_MGF1;
        }

        @Override
        String getJCAAlgorithm() {
            return "SHA256withRSAandMGF1";
        }

        @Override
        AbstractDOMSignatureMethod.Type getAlgorithmType() {
            return AbstractDOMSignatureMethod.Type.RSA;
        }
    }

    static final class SHA224withRSAandMGF1
    extends DOMSignatureMethod {
        SHA224withRSAandMGF1(AlgorithmParameterSpec params) throws InvalidAlgorithmParameterException {
            super(params);
        }

        SHA224withRSAandMGF1(Element dmElem) throws MarshalException {
            super(dmElem);
        }

        @Override
        public String getAlgorithm() {
            return DOMSignatureMethod.RSA_SHA224_MGF1;
        }

        @Override
        String getJCAAlgorithm() {
            return "SHA224withRSAandMGF1";
        }

        @Override
        AbstractDOMSignatureMethod.Type getAlgorithmType() {
            return AbstractDOMSignatureMethod.Type.RSA;
        }
    }

    static final class SHA1withRSAandMGF1
    extends DOMSignatureMethod {
        SHA1withRSAandMGF1(AlgorithmParameterSpec params) throws InvalidAlgorithmParameterException {
            super(params);
        }

        SHA1withRSAandMGF1(Element dmElem) throws MarshalException {
            super(dmElem);
        }

        @Override
        public String getAlgorithm() {
            return DOMSignatureMethod.RSA_SHA1_MGF1;
        }

        @Override
        String getJCAAlgorithm() {
            return "SHA1withRSAandMGF1";
        }

        @Override
        AbstractDOMSignatureMethod.Type getAlgorithmType() {
            return AbstractDOMSignatureMethod.Type.RSA;
        }
    }

    static final class RIPEMD160withRSA
    extends DOMSignatureMethod {
        RIPEMD160withRSA(AlgorithmParameterSpec params) throws InvalidAlgorithmParameterException {
            super(params);
        }

        RIPEMD160withRSA(Element dmElem) throws MarshalException {
            super(dmElem);
        }

        @Override
        public String getAlgorithm() {
            return DOMSignatureMethod.RSA_RIPEMD160;
        }

        @Override
        String getJCAAlgorithm() {
            return "RIPEMD160withRSA";
        }

        @Override
        AbstractDOMSignatureMethod.Type getAlgorithmType() {
            return AbstractDOMSignatureMethod.Type.RSA;
        }
    }

    static final class SHA512withRSA
    extends DOMSignatureMethod {
        SHA512withRSA(AlgorithmParameterSpec params) throws InvalidAlgorithmParameterException {
            super(params);
        }

        SHA512withRSA(Element dmElem) throws MarshalException {
            super(dmElem);
        }

        @Override
        public String getAlgorithm() {
            return DOMSignatureMethod.RSA_SHA512;
        }

        @Override
        String getJCAAlgorithm() {
            return "SHA512withRSA";
        }

        @Override
        AbstractDOMSignatureMethod.Type getAlgorithmType() {
            return AbstractDOMSignatureMethod.Type.RSA;
        }
    }

    static final class SHA384withRSA
    extends DOMSignatureMethod {
        SHA384withRSA(AlgorithmParameterSpec params) throws InvalidAlgorithmParameterException {
            super(params);
        }

        SHA384withRSA(Element dmElem) throws MarshalException {
            super(dmElem);
        }

        @Override
        public String getAlgorithm() {
            return DOMSignatureMethod.RSA_SHA384;
        }

        @Override
        String getJCAAlgorithm() {
            return "SHA384withRSA";
        }

        @Override
        AbstractDOMSignatureMethod.Type getAlgorithmType() {
            return AbstractDOMSignatureMethod.Type.RSA;
        }
    }

    static final class SHA256withRSA
    extends DOMSignatureMethod {
        SHA256withRSA(AlgorithmParameterSpec params) throws InvalidAlgorithmParameterException {
            super(params);
        }

        SHA256withRSA(Element dmElem) throws MarshalException {
            super(dmElem);
        }

        @Override
        public String getAlgorithm() {
            return DOMSignatureMethod.RSA_SHA256;
        }

        @Override
        String getJCAAlgorithm() {
            return "SHA256withRSA";
        }

        @Override
        AbstractDOMSignatureMethod.Type getAlgorithmType() {
            return AbstractDOMSignatureMethod.Type.RSA;
        }
    }

    static final class SHA224withRSA
    extends DOMSignatureMethod {
        SHA224withRSA(AlgorithmParameterSpec params) throws InvalidAlgorithmParameterException {
            super(params);
        }

        SHA224withRSA(Element dmElem) throws MarshalException {
            super(dmElem);
        }

        @Override
        public String getAlgorithm() {
            return DOMSignatureMethod.RSA_SHA224;
        }

        @Override
        String getJCAAlgorithm() {
            return "SHA224withRSA";
        }

        @Override
        AbstractDOMSignatureMethod.Type getAlgorithmType() {
            return AbstractDOMSignatureMethod.Type.RSA;
        }
    }

    static final class SHA1withRSA
    extends DOMSignatureMethod {
        SHA1withRSA(AlgorithmParameterSpec params) throws InvalidAlgorithmParameterException {
            super(params);
        }

        SHA1withRSA(Element dmElem) throws MarshalException {
            super(dmElem);
        }

        @Override
        public String getAlgorithm() {
            return "http://www.w3.org/2000/09/xmldsig#rsa-sha1";
        }

        @Override
        String getJCAAlgorithm() {
            return "SHA1withRSA";
        }

        @Override
        AbstractDOMSignatureMethod.Type getAlgorithmType() {
            return AbstractDOMSignatureMethod.Type.RSA;
        }
    }
}

