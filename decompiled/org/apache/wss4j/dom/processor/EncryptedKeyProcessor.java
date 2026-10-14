/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.processor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.security.spec.MGF1ParameterSpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import org.apache.wss4j.common.bsp.BSPEnforcer;
import org.apache.wss4j.common.bsp.BSPRule;
import org.apache.wss4j.common.crypto.AlgorithmSuite;
import org.apache.wss4j.common.crypto.AlgorithmSuiteValidator;
import org.apache.wss4j.common.crypto.CryptoType;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.token.DOMX509IssuerSerial;
import org.apache.wss4j.common.util.KeyUtils;
import org.apache.wss4j.common.util.XMLUtils;
import org.apache.wss4j.dom.WSDataRef;
import org.apache.wss4j.dom.WSDocInfo;
import org.apache.wss4j.dom.engine.WSSecurityEngineResult;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.dom.processor.Processor;
import org.apache.wss4j.dom.str.EncryptedKeySTRParser;
import org.apache.wss4j.dom.str.STRParser;
import org.apache.wss4j.dom.str.STRParserParameters;
import org.apache.wss4j.dom.str.STRParserResult;
import org.apache.wss4j.dom.util.EncryptionUtils;
import org.apache.wss4j.dom.util.SignatureUtils;
import org.apache.wss4j.dom.util.WSSecurityUtil;
import org.apache.wss4j.dom.util.X509Util;
import org.apache.xml.security.algorithms.JCEMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class EncryptedKeyProcessor
implements Processor {
    private static final Logger LOG = LoggerFactory.getLogger(EncryptedKeyProcessor.class);
    private final Provider provider;

    public EncryptedKeyProcessor() {
        this(null);
    }

    public EncryptedKeyProcessor(Provider provider) {
        this.provider = provider;
    }

    @Override
    public List<WSSecurityEngineResult> handleToken(Element elem, RequestData data) throws WSSecurityException {
        return this.handleToken(elem, data, data.getAlgorithmSuite());
    }

    public List<WSSecurityEngineResult> handleToken(Element elem, RequestData data, AlgorithmSuite algorithmSuite) throws WSSecurityException {
        WSSecurityEngineResult result;
        LOG.debug("Found encrypted key element");
        String id = elem.getAttributeNS(null, "Id");
        if (!"".equals(id) && (result = data.getWsDocInfo().getResult(id)) != null && 4 == (Integer)result.get("action")) {
            return Collections.singletonList(result);
        }
        if (data.getCallbackHandler() == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "noCallback");
        }
        String encryptedKeyTransportMethod = X509Util.getEncAlgo(elem);
        if (encryptedKeyTransportMethod == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.UNSUPPORTED_ALGORITHM, "noEncAlgo");
        }
        if (!(!"http://www.w3.org/2001/04/xmlenc#rsa-1_5".equals(encryptedKeyTransportMethod) || data.isAllowRSA15KeyTransportAlgorithm() || algorithmSuite != null && algorithmSuite.getKeyWrapAlgorithms().contains("http://www.w3.org/2001/04/xmlenc#rsa-1_5"))) {
            LOG.debug("The Key transport method does not match the requirement");
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY);
        }
        this.checkBSPCompliance(elem, encryptedKeyTransportMethod, data.getBSPEnforcer());
        Element xencCipherValue = EncryptionUtils.getCipherValueFromEncryptedData(elem);
        if (xencCipherValue == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, "noCipher");
        }
        Element keyInfoChildElement = this.getKeyInfoChildElement(elem, data);
        X509Certificate[] certs = null;
        STRParser.REFERENCE_TYPE referenceType = null;
        PublicKey publicKey = null;
        boolean symmetricKeyWrap = EncryptedKeyProcessor.isSymmetricKeyWrap(encryptedKeyTransportMethod);
        if (!symmetricKeyWrap) {
            if ("SecurityTokenReference".equals(keyInfoChildElement.getLocalName()) && "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd".equals(keyInfoChildElement.getNamespaceURI())) {
                STRParserParameters parameters = new STRParserParameters();
                parameters.setData(data);
                parameters.setStrElement(keyInfoChildElement);
                EncryptedKeySTRParser strParser = new EncryptedKeySTRParser();
                STRParserResult parserResult = strParser.parseSecurityTokenReference(parameters);
                certs = parserResult.getCertificates();
                publicKey = parserResult.getPublicKey();
                referenceType = parserResult.getCertificatesReferenceType();
            } else {
                certs = this.getCertificatesFromX509Data(keyInfoChildElement, data);
                if (certs == null) {
                    XMLSignatureFactory signatureFactory;
                    if (this.provider == null) {
                        try {
                            signatureFactory = XMLSignatureFactory.getInstance("DOM", "ApacheXMLDSig");
                        }
                        catch (NoSuchProviderException ex) {
                            signatureFactory = XMLSignatureFactory.getInstance("DOM");
                        }
                    } else {
                        signatureFactory = XMLSignatureFactory.getInstance("DOM", this.provider);
                    }
                    publicKey = X509Util.parseKeyValue((Element)keyInfoChildElement.getParentNode(), signatureFactory);
                }
            }
            if (publicKey == null && (certs == null || certs.length < 1 || certs[0] == null)) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "noCertsFound", new Object[]{"decryption (KeyId)"});
            }
            if (certs != null && certs.length > 0) {
                publicKey = certs[0].getPublicKey();
            }
        }
        if (algorithmSuite != null) {
            AlgorithmSuiteValidator algorithmSuiteValidator = new AlgorithmSuiteValidator(algorithmSuite);
            if (!symmetricKeyWrap) {
                algorithmSuiteValidator.checkAsymmetricKeyLength(publicKey);
            }
            algorithmSuiteValidator.checkEncryptionKeyWrapAlgorithm(encryptedKeyTransportMethod);
        }
        byte[] encryptedEphemeralKey = null;
        byte[] decryptedBytes = null;
        Element refList = XMLUtils.getDirectChildElement(elem, "ReferenceList", "http://www.w3.org/2001/04/xmlenc#");
        String xopUri = EncryptionUtils.getXOPURIFromCipherValue(xencCipherValue);
        encryptedEphemeralKey = xopUri != null && xopUri.startsWith("cid:") ? WSSecurityUtil.getBytesFromAttachment(xopUri, data) : EncryptionUtils.getDecodedBase64EncodedData(xencCipherValue);
        if (symmetricKeyWrap) {
            decryptedBytes = EncryptedKeyProcessor.getSymmetricDecryptedBytes(data, data.getWsDocInfo(), keyInfoChildElement, refList, encryptedEphemeralKey);
        } else {
            PrivateKey privateKey = this.getPrivateKey(data, certs, publicKey);
            decryptedBytes = EncryptedKeyProcessor.getAsymmetricDecryptedBytes(data, data.getWsDocInfo(), encryptedKeyTransportMethod, encryptedEphemeralKey, refList, elem, privateKey);
        }
        List<WSDataRef> dataRefs = this.decryptDataRefs(refList, data.getWsDocInfo(), decryptedBytes, data);
        WSSecurityEngineResult result2 = new WSSecurityEngineResult(4, decryptedBytes, encryptedEphemeralKey, dataRefs, certs);
        result2.put("encrypted-key-transport-method", encryptedKeyTransportMethod);
        result2.put("token-element", elem);
        String tokenId = elem.getAttributeNS(null, "Id");
        if (!"".equals(tokenId)) {
            result2.put("id", tokenId);
        }
        if (referenceType != null) {
            result2.put("x509-reference-type", referenceType);
        }
        if (publicKey != null) {
            result2.put("public-key", publicKey);
        }
        data.getWsDocInfo().addResult(result2);
        data.getWsDocInfo().addTokenElement(elem);
        return Collections.singletonList(result2);
    }

    private PrivateKey getPrivateKey(RequestData data, X509Certificate[] certs, PublicKey publicKey) throws WSSecurityException {
        try {
            if (certs != null) {
                return data.getDecCrypto().getPrivateKey(certs[0], data.getCallbackHandler());
            }
            return data.getDecCrypto().getPrivateKey(publicKey, data.getCallbackHandler());
        }
        catch (WSSecurityException ex) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_CHECK, (Exception)ex);
        }
    }

    private static byte[] getSymmetricDecryptedBytes(RequestData data, WSDocInfo wsDocInfo, Element keyInfoChildElement, Element refList, byte[] encryptedEphemeralKey) throws WSSecurityException {
        String uri = EncryptedKeyProcessor.getFirstDataRefURI(refList);
        String algorithmURI = null;
        if (uri != null) {
            Element ee = EncryptionUtils.findEncryptedDataElement(refList.getOwnerDocument(), wsDocInfo, uri);
            algorithmURI = X509Util.getEncAlgo(ee);
        }
        return X509Util.getSecretKey(keyInfoChildElement, algorithmURI, data.getCallbackHandler(), encryptedEphemeralKey);
    }

    private static byte[] getAsymmetricDecryptedBytes(RequestData data, WSDocInfo wsDocInfo, String encryptedKeyTransportMethod, byte[] encryptedEphemeralKey, Element refList, Element encryptedKeyElement, PrivateKey privateKey) throws WSSecurityException {
        if (data.getDecCrypto() == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "noDecCryptoFile");
        }
        String cryptoProvider = data.getDecCrypto().getCryptoProvider();
        Cipher cipher = KeyUtils.getCipherInstance(encryptedKeyTransportMethod, cryptoProvider);
        try {
            OAEPParameterSpec oaepParameterSpec = null;
            if ("http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p".equals(encryptedKeyTransportMethod) || "http://www.w3.org/2009/xmlenc11#rsa-oaep".equals(encryptedKeyTransportMethod)) {
                String digestAlgorithm = EncryptionUtils.getDigestAlgorithm(encryptedKeyElement);
                String jceDigestAlgorithm = "SHA-1";
                if (digestAlgorithm != null && !"".equals(digestAlgorithm)) {
                    jceDigestAlgorithm = JCEMapper.translateURItoJCEID(digestAlgorithm);
                }
                MGF1ParameterSpec mgfParameterSpec = new MGF1ParameterSpec("SHA-1");
                if ("http://www.w3.org/2009/xmlenc11#rsa-oaep".equals(encryptedKeyTransportMethod)) {
                    String mgfAlgorithm = EncryptionUtils.getMGFAlgorithm(encryptedKeyElement);
                    if ("http://www.w3.org/2009/xmlenc11#mgf1sha224".equals(mgfAlgorithm)) {
                        mgfParameterSpec = new MGF1ParameterSpec("SHA-224");
                    } else if ("http://www.w3.org/2009/xmlenc11#mgf1sha256".equals(mgfAlgorithm)) {
                        mgfParameterSpec = new MGF1ParameterSpec("SHA-256");
                    } else if ("http://www.w3.org/2009/xmlenc11#mgf1sha384".equals(mgfAlgorithm)) {
                        mgfParameterSpec = new MGF1ParameterSpec("SHA-384");
                    } else if ("http://www.w3.org/2009/xmlenc11#mgf1sha512".equals(mgfAlgorithm)) {
                        mgfParameterSpec = new MGF1ParameterSpec("SHA-512");
                    }
                }
                PSource.PSpecified pSource = PSource.PSpecified.DEFAULT;
                byte[] pSourceBytes = EncryptionUtils.getPSource(encryptedKeyElement);
                if (pSourceBytes != null) {
                    pSource = new PSource.PSpecified(pSourceBytes);
                }
                oaepParameterSpec = new OAEPParameterSpec(jceDigestAlgorithm, "MGF1", mgfParameterSpec, pSource);
            }
            if (oaepParameterSpec == null) {
                cipher.init(4, privateKey);
            } else {
                cipher.init(4, (Key)privateKey, oaepParameterSpec);
            }
        }
        catch (Exception ex) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_CHECK, ex);
        }
        try {
            String keyAlgorithm = JCEMapper.translateURItoJCEID(encryptedKeyTransportMethod);
            return cipher.unwrap(encryptedEphemeralKey, keyAlgorithm, 3).getEncoded();
        }
        catch (IllegalStateException ex) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_CHECK, (Exception)ex);
        }
        catch (Exception ex) {
            return EncryptedKeyProcessor.getRandomKey(refList, wsDocInfo);
        }
    }

    private static boolean isSymmetricKeyWrap(String transportAlgorithm) {
        return "http://www.w3.org/2001/04/xmlenc#kw-aes128".equals(transportAlgorithm) || "http://www.w3.org/2001/04/xmlenc#kw-aes192".equals(transportAlgorithm) || "http://www.w3.org/2001/04/xmlenc#kw-aes256".equals(transportAlgorithm) || "http://www.w3.org/2001/04/xmlenc#kw-tripledes".equals(transportAlgorithm) || "http://www.w3.org/2001/04/xmldsig-more#kw-camellia128".equals(transportAlgorithm) || "http://www.w3.org/2001/04/xmldsig-more#kw-camellia192".equals(transportAlgorithm) || "http://www.w3.org/2001/04/xmldsig-more#kw-camellia256".equals(transportAlgorithm) || "http://www.w3.org/2007/05/xmldsig-more#kw-seed128".equals(transportAlgorithm);
    }

    private static byte[] getRandomKey(Element refList, WSDocInfo wsDocInfo) throws WSSecurityException {
        try {
            String alg = "AES";
            int size = 16;
            String uri = EncryptedKeyProcessor.getFirstDataRefURI(refList);
            if (uri != null) {
                Element ee = EncryptionUtils.findEncryptedDataElement(refList.getOwnerDocument(), wsDocInfo, uri);
                String algorithmURI = X509Util.getEncAlgo(ee);
                alg = JCEMapper.getJCEKeyAlgorithmFromURI(algorithmURI);
                size = KeyUtils.getKeyLength(algorithmURI);
            }
            KeyGenerator kgen = KeyGenerator.getInstance(alg);
            kgen.init(size * 8);
            SecretKey k = kgen.generateKey();
            return k.getEncoded();
        }
        catch (Throwable ex) {
            try {
                KeyGenerator kgen = KeyGenerator.getInstance("AES");
                kgen.init(128);
                SecretKey k = kgen.generateKey();
                return k.getEncoded();
            }
            catch (NoSuchAlgorithmException e) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_CHECK, (Exception)e);
            }
        }
    }

    private static String getFirstDataRefURI(Element refList) {
        if (refList != null) {
            for (Node node = refList.getFirstChild(); node != null; node = node.getNextSibling()) {
                if (1 != node.getNodeType() || !"http://www.w3.org/2001/04/xmlenc#".equals(node.getNamespaceURI()) || !"DataReference".equals(node.getLocalName())) continue;
                String dataRefURI = ((Element)node).getAttributeNS(null, "URI");
                return XMLUtils.getIDFromReference(dataRefURI);
            }
        }
        return null;
    }

    private Element getKeyInfoChildElement(Element xencEncryptedKey, RequestData data) throws WSSecurityException {
        Element keyInfo = XMLUtils.getDirectChildElement(xencEncryptedKey, "KeyInfo", "http://www.w3.org/2000/09/xmldsig#");
        if (keyInfo != null) {
            Element strElement = null;
            int result = 0;
            for (Node node = keyInfo.getFirstChild(); node != null; node = node.getNextSibling()) {
                if (1 != node.getNodeType()) continue;
                ++result;
                strElement = (Element)node;
            }
            if (result != 1) {
                data.getBSPEnforcer().handleBSPRule(BSPRule.R5424);
            }
            if (strElement == null) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, "noSecTokRef");
            }
            return strElement;
        }
        throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, "noKeyinfo");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private X509Certificate[] getCertificatesFromX509Data(Element keyInfoChildElement, RequestData data) throws WSSecurityException {
        if (!"http://www.w3.org/2000/09/xmldsig#".equals(keyInfoChildElement.getNamespaceURI())) return null;
        if (!"X509Data".equals(keyInfoChildElement.getLocalName())) return null;
        data.getBSPEnforcer().handleBSPRule(BSPRule.R5426);
        Element x509Child = this.getFirstElement(keyInfoChildElement);
        if (x509Child == null) return null;
        if (!"http://www.w3.org/2000/09/xmldsig#".equals(x509Child.getNamespaceURI())) return null;
        if ("X509IssuerSerial".equals(x509Child.getLocalName())) {
            DOMX509IssuerSerial issuerSerial = new DOMX509IssuerSerial(x509Child);
            CryptoType cryptoType = new CryptoType(CryptoType.TYPE.ISSUER_SERIAL);
            cryptoType.setIssuerSerial(issuerSerial.getIssuer(), issuerSerial.getSerialNumber());
            return data.getDecCrypto().getX509Certificates(cryptoType);
        }
        if (!"X509Certificate".equals(x509Child.getLocalName())) return null;
        byte[] token = EncryptionUtils.getDecodedBase64EncodedData(x509Child);
        if (token == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "invalidCertData", new Object[]{"0"});
        }
        try (ByteArrayInputStream in = new ByteArrayInputStream(token);){
            X509Certificate cert = data.getDecCrypto().loadCertificate(in);
            if (cert == null) return null;
            X509Certificate[] x509CertificateArray = new X509Certificate[]{cert};
            return x509CertificateArray;
        }
        catch (IOException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.SECURITY_TOKEN_UNAVAILABLE, e, "parseError");
        }
    }

    private Element getFirstElement(Element element) {
        for (Node currentChild = element.getFirstChild(); currentChild != null; currentChild = currentChild.getNextSibling()) {
            if (1 != currentChild.getNodeType()) continue;
            return (Element)currentChild;
        }
        return null;
    }

    private List<WSDataRef> decryptDataRefs(Element refList, WSDocInfo docInfo, byte[] decryptedBytes, RequestData data) throws WSSecurityException {
        if (refList == null) {
            return null;
        }
        ArrayList<WSDataRef> dataRefs = new ArrayList<WSDataRef>();
        for (Node node = refList.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (1 != node.getNodeType() || !"http://www.w3.org/2001/04/xmlenc#".equals(node.getNamespaceURI()) || !"DataReference".equals(node.getLocalName())) continue;
            String dataRefURI = ((Element)node).getAttributeNS(null, "URI");
            dataRefURI = XMLUtils.getIDFromReference(dataRefURI);
            WSDataRef dataRef = this.decryptDataRef(refList.getOwnerDocument(), dataRefURI, docInfo, decryptedBytes, data);
            dataRefs.add(dataRef);
        }
        return dataRefs;
    }

    private WSDataRef decryptDataRef(Document doc, String dataRefURI, WSDocInfo docInfo, byte[] decryptedData, RequestData data) throws WSSecurityException {
        String symEncAlgo;
        LOG.debug("found data reference: {}", (Object)dataRefURI);
        Element encryptedDataElement = EncryptionUtils.findEncryptedDataElement(doc, docInfo, dataRefURI);
        if (encryptedDataElement != null && data.isRequireSignedEncryptedDataElements()) {
            List<WSSecurityEngineResult> signedResults = docInfo.getResultsByTag(2);
            SignatureUtils.verifySignedElement(encryptedDataElement, signedResults);
        }
        if ((symEncAlgo = X509Util.getEncAlgo(encryptedDataElement)) == null) {
            LOG.debug("No encryption algorithm was specified in the request");
            throw new WSSecurityException(WSSecurityException.ErrorCode.UNSUPPORTED_ALGORITHM, "badEncAlgo", new Object[]{null});
        }
        if (!("http://www.w3.org/2001/04/xmlenc#tripledes-cbc".equals(symEncAlgo) || "http://www.w3.org/2001/04/xmlenc#aes128-cbc".equals(symEncAlgo) || "http://www.w3.org/2009/xmlenc11#aes128-gcm".equals(symEncAlgo) || "http://www.w3.org/2001/04/xmlenc#aes256-cbc".equals(symEncAlgo) || "http://www.w3.org/2009/xmlenc11#aes256-gcm".equals(symEncAlgo))) {
            data.getBSPEnforcer().handleBSPRule(BSPRule.R5620);
        }
        SecretKey symmetricKey = null;
        try {
            symmetricKey = KeyUtils.prepareSecretKey(symEncAlgo, decryptedData);
        }
        catch (IllegalArgumentException ex) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.UNSUPPORTED_ALGORITHM, ex, "badEncAlgo", new Object[]{symEncAlgo});
        }
        AlgorithmSuite algorithmSuite = data.getAlgorithmSuite();
        if (algorithmSuite != null) {
            AlgorithmSuiteValidator algorithmSuiteValidator = new AlgorithmSuiteValidator(algorithmSuite);
            algorithmSuiteValidator.checkSymmetricKeyLength(symmetricKey.getEncoded().length);
            algorithmSuiteValidator.checkSymmetricEncryptionAlgorithm(symEncAlgo);
        }
        return EncryptionUtils.decryptEncryptedData(doc, dataRefURI, encryptedDataElement, symmetricKey, symEncAlgo, data.getAttachmentCallbackHandler(), data.getEncryptionSerializer());
    }

    private void checkBSPCompliance(Element elem, String encAlgo, BSPEnforcer bspEnforcer) throws WSSecurityException {
        String attribute = elem.getAttributeNS(null, "Type");
        if (attribute != null && !"".equals(attribute)) {
            bspEnforcer.handleBSPRule(BSPRule.R3209);
        }
        if ((attribute = elem.getAttributeNS(null, "MimeType")) != null && !"".equals(attribute)) {
            bspEnforcer.handleBSPRule(BSPRule.R5622);
        }
        if ((attribute = elem.getAttributeNS(null, "Encoding")) != null && !"".equals(attribute)) {
            bspEnforcer.handleBSPRule(BSPRule.R5623);
        }
        if ((attribute = elem.getAttributeNS(null, "Recipient")) != null && !"".equals(attribute)) {
            bspEnforcer.handleBSPRule(BSPRule.R5602);
        }
        if (!("http://www.w3.org/2001/04/xmlenc#rsa-1_5".equals(encAlgo) || "http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p".equals(encAlgo) || "http://www.w3.org/2009/xmlenc11#rsa-oaep".equals(encAlgo))) {
            bspEnforcer.handleBSPRule(BSPRule.R5621);
        }
    }
}

