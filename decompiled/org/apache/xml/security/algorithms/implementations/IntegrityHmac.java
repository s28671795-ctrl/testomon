/*
 * Decompiled with CFR 0.152.
 */
package org.apache.xml.security.algorithms.implementations;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import org.apache.xml.security.algorithms.JCEMapper;
import org.apache.xml.security.algorithms.MessageDigestAlgorithm;
import org.apache.xml.security.algorithms.SignatureAlgorithmSpi;
import org.apache.xml.security.signature.XMLSignatureException;
import org.apache.xml.security.utils.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public abstract class IntegrityHmac
extends SignatureAlgorithmSpi {
    private static final Logger LOG = LoggerFactory.getLogger(IntegrityHmac.class);
    private Mac macAlgorithm;
    private int HMACOutputLength;
    private boolean HMACOutputLengthSet = false;

    @Override
    public abstract String engineGetURI();

    abstract int getDigestLength();

    public IntegrityHmac() throws XMLSignatureException {
        String algorithmID = JCEMapper.translateURItoJCEID(this.engineGetURI());
        LOG.debug("Created IntegrityHmacSHA1 using {}", (Object)algorithmID);
        try {
            this.macAlgorithm = Mac.getInstance(algorithmID);
        }
        catch (NoSuchAlgorithmException ex) {
            Object[] exArgs = new Object[]{algorithmID, ex.getLocalizedMessage()};
            throw new XMLSignatureException("algorithms.NoSuchAlgorithm", exArgs);
        }
    }

    @Override
    protected void engineSetParameter(AlgorithmParameterSpec params) throws XMLSignatureException {
        throw new XMLSignatureException("empty", new Object[]{"Incorrect method call"});
    }

    @Override
    public void reset() {
        this.HMACOutputLength = 0;
        this.HMACOutputLengthSet = false;
        this.macAlgorithm.reset();
    }

    @Override
    protected boolean engineVerify(byte[] signature) throws XMLSignatureException {
        try {
            if (this.HMACOutputLengthSet && this.HMACOutputLength < this.getDigestLength()) {
                LOG.debug("HMACOutputLength must not be less than {}", (Object)this.getDigestLength());
                Object[] exArgs = new Object[]{String.valueOf(this.getDigestLength())};
                throw new XMLSignatureException("algorithms.HMACOutputLengthMin", exArgs);
            }
            byte[] completeResult = this.macAlgorithm.doFinal();
            return MessageDigestAlgorithm.isEqual(completeResult, signature);
        }
        catch (IllegalStateException ex) {
            throw new XMLSignatureException(ex);
        }
    }

    @Override
    protected void engineInitVerify(Key secretKey) throws XMLSignatureException {
        if (!(secretKey instanceof SecretKey)) {
            String supplied = null;
            if (secretKey != null) {
                supplied = secretKey.getClass().getName();
            }
            String needed = SecretKey.class.getName();
            Object[] exArgs = new Object[]{supplied, needed};
            throw new XMLSignatureException("algorithms.WrongKeyForThisOperation", exArgs);
        }
        try {
            this.macAlgorithm.init(secretKey);
        }
        catch (InvalidKeyException ex) {
            Mac mac = this.macAlgorithm;
            try {
                this.macAlgorithm = Mac.getInstance(this.macAlgorithm.getAlgorithm());
            }
            catch (Exception e) {
                LOG.debug("Exception when reinstantiating Mac: {}", e);
                this.macAlgorithm = mac;
            }
            throw new XMLSignatureException(ex);
        }
    }

    @Override
    protected byte[] engineSign() throws XMLSignatureException {
        try {
            if (this.HMACOutputLengthSet && this.HMACOutputLength < this.getDigestLength()) {
                LOG.debug("HMACOutputLength must not be less than {}", (Object)this.getDigestLength());
                Object[] exArgs = new Object[]{String.valueOf(this.getDigestLength())};
                throw new XMLSignatureException("algorithms.HMACOutputLengthMin", exArgs);
            }
            return this.macAlgorithm.doFinal();
        }
        catch (IllegalStateException ex) {
            throw new XMLSignatureException(ex);
        }
    }

    @Override
    protected void engineInitSign(Key secretKey) throws XMLSignatureException {
        this.engineInitSign(secretKey, (AlgorithmParameterSpec)null);
    }

    @Override
    protected void engineInitSign(Key secretKey, AlgorithmParameterSpec algorithmParameterSpec) throws XMLSignatureException {
        if (!(secretKey instanceof SecretKey)) {
            String supplied = null;
            if (secretKey != null) {
                supplied = secretKey.getClass().getName();
            }
            String needed = SecretKey.class.getName();
            Object[] exArgs = new Object[]{supplied, needed};
            throw new XMLSignatureException("algorithms.WrongKeyForThisOperation", exArgs);
        }
        try {
            if (algorithmParameterSpec == null) {
                this.macAlgorithm.init(secretKey);
            } else {
                this.macAlgorithm.init(secretKey, algorithmParameterSpec);
            }
        }
        catch (InvalidKeyException ex) {
            throw new XMLSignatureException(ex);
        }
        catch (InvalidAlgorithmParameterException ex) {
            throw new XMLSignatureException(ex);
        }
    }

    @Override
    protected void engineInitSign(Key secretKey, SecureRandom secureRandom) throws XMLSignatureException {
        throw new XMLSignatureException("algorithms.CannotUseSecureRandomOnMAC");
    }

    @Override
    protected void engineUpdate(byte[] input) throws XMLSignatureException {
        try {
            this.macAlgorithm.update(input);
        }
        catch (IllegalStateException ex) {
            throw new XMLSignatureException(ex);
        }
    }

    @Override
    protected void engineUpdate(byte input) throws XMLSignatureException {
        try {
            this.macAlgorithm.update(input);
        }
        catch (IllegalStateException ex) {
            throw new XMLSignatureException(ex);
        }
    }

    @Override
    protected void engineUpdate(byte[] buf, int offset, int len) throws XMLSignatureException {
        try {
            this.macAlgorithm.update(buf, offset, len);
        }
        catch (IllegalStateException ex) {
            throw new XMLSignatureException(ex);
        }
    }

    @Override
    protected String engineGetJCEAlgorithmString() {
        return this.macAlgorithm.getAlgorithm();
    }

    @Override
    protected String engineGetJCEProviderName() {
        return this.macAlgorithm.getProvider().getName();
    }

    @Override
    protected void engineSetHMACOutputLength(int HMACOutputLength) {
        this.HMACOutputLength = HMACOutputLength;
        this.HMACOutputLengthSet = true;
    }

    @Override
    protected void engineGetContextFromElement(Element element) {
        String hmacLength;
        super.engineGetContextFromElement(element);
        if (element == null) {
            throw new IllegalArgumentException("element null");
        }
        Element n = XMLUtils.selectDsNode(element.getFirstChild(), "HMACOutputLength", 0);
        if (n != null && (hmacLength = XMLUtils.getFullTextChildrenFromNode(n)) != null && !"".equals(hmacLength)) {
            this.HMACOutputLength = Integer.parseInt(hmacLength);
            this.HMACOutputLengthSet = true;
        }
    }

    public void engineAddContextToElement(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("null element");
        }
        if (this.HMACOutputLengthSet) {
            Document doc = element.getOwnerDocument();
            Element HMElem = XMLUtils.createElementInSignatureSpace(doc, "HMACOutputLength");
            Text HMText = doc.createTextNode("" + this.HMACOutputLength);
            HMElem.appendChild(HMText);
            XMLUtils.addReturnToElement(element);
            element.appendChild(HMElem);
            XMLUtils.addReturnToElement(element);
        }
    }

    public static class IntegrityHmacMD5
    extends IntegrityHmac {
        @Override
        public String engineGetURI() {
            return "http://www.w3.org/2001/04/xmldsig-more#hmac-md5";
        }

        @Override
        int getDigestLength() {
            return 128;
        }
    }

    public static class IntegrityHmacRIPEMD160
    extends IntegrityHmac {
        @Override
        public String engineGetURI() {
            return "http://www.w3.org/2001/04/xmldsig-more#hmac-ripemd160";
        }

        @Override
        int getDigestLength() {
            return 160;
        }
    }

    public static class IntegrityHmacSHA512
    extends IntegrityHmac {
        @Override
        public String engineGetURI() {
            return "http://www.w3.org/2001/04/xmldsig-more#hmac-sha512";
        }

        @Override
        int getDigestLength() {
            return 512;
        }
    }

    public static class IntegrityHmacSHA384
    extends IntegrityHmac {
        @Override
        public String engineGetURI() {
            return "http://www.w3.org/2001/04/xmldsig-more#hmac-sha384";
        }

        @Override
        int getDigestLength() {
            return 384;
        }
    }

    public static class IntegrityHmacSHA256
    extends IntegrityHmac {
        @Override
        public String engineGetURI() {
            return "http://www.w3.org/2001/04/xmldsig-more#hmac-sha256";
        }

        @Override
        int getDigestLength() {
            return 256;
        }
    }

    public static class IntegrityHmacSHA224
    extends IntegrityHmac {
        @Override
        public String engineGetURI() {
            return "http://www.w3.org/2001/04/xmldsig-more#hmac-sha224";
        }

        @Override
        int getDigestLength() {
            return 224;
        }
    }

    public static class IntegrityHmacSHA1
    extends IntegrityHmac {
        @Override
        public String engineGetURI() {
            return "http://www.w3.org/2000/09/xmldsig#hmac-sha1";
        }

        @Override
        int getDigestLength() {
            return 160;
        }
    }
}

