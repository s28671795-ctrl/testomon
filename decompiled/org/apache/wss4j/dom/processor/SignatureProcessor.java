/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.processor;

import java.security.Key;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.Provider;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.security.spec.AlgorithmParameterSpec;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.xml.crypto.Data;
import javax.xml.crypto.NodeSetData;
import javax.xml.crypto.OctetStreamData;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dsig.Manifest;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLObject;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.crypto.dsig.spec.ExcC14NParameterSpec;
import javax.xml.crypto.dsig.spec.HMACParameterSpec;
import org.apache.wss4j.common.bsp.BSPEnforcer;
import org.apache.wss4j.common.bsp.BSPRule;
import org.apache.wss4j.common.cache.ReplayCache;
import org.apache.wss4j.common.crypto.AlgorithmSuite;
import org.apache.wss4j.common.crypto.AlgorithmSuiteValidator;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.crypto.CryptoType;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.principal.PublicKeyPrincipalImpl;
import org.apache.wss4j.common.principal.UsernameTokenPrincipal;
import org.apache.wss4j.common.principal.WSDerivedKeyTokenPrincipal;
import org.apache.wss4j.common.token.BinarySecurity;
import org.apache.wss4j.common.token.SecurityTokenReference;
import org.apache.wss4j.common.util.KeyUtils;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.WSDataRef;
import org.apache.wss4j.dom.WSDocInfo;
import org.apache.wss4j.dom.callback.CallbackLookup;
import org.apache.wss4j.dom.engine.WSSecurityEngineResult;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.dom.message.token.Timestamp;
import org.apache.wss4j.dom.processor.Processor;
import org.apache.wss4j.dom.str.STRParser;
import org.apache.wss4j.dom.str.STRParserParameters;
import org.apache.wss4j.dom.str.STRParserResult;
import org.apache.wss4j.dom.str.SignatureSTRParser;
import org.apache.wss4j.dom.transform.STRTransformUtil;
import org.apache.wss4j.dom.util.EncryptionUtils;
import org.apache.wss4j.dom.util.WSSecurityUtil;
import org.apache.wss4j.dom.util.X509Util;
import org.apache.wss4j.dom.validate.Credential;
import org.apache.wss4j.dom.validate.Validator;
import org.apache.xml.security.utils.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

public class SignatureProcessor
implements Processor {
    private static final Logger LOG = LoggerFactory.getLogger(SignatureProcessor.class);
    private XMLSignatureFactory signatureFactory;

    public SignatureProcessor() {
        this.init(null);
    }

    public SignatureProcessor(Provider provider) {
        this.init(provider);
    }

    private void init(Provider provider) {
        if (provider == null) {
            try {
                this.signatureFactory = XMLSignatureFactory.getInstance("DOM", "ApacheXMLDSig");
            }
            catch (NoSuchProviderException ex) {
                this.signatureFactory = XMLSignatureFactory.getInstance("DOM");
            }
        } else {
            this.signatureFactory = XMLSignatureFactory.getInstance("DOM", provider);
        }
    }

    @Override
    public List<WSSecurityEngineResult> handleToken(Element elem, RequestData data) throws WSSecurityException {
        LOG.debug("Found signature element");
        Element keyInfoElement = org.apache.wss4j.common.util.XMLUtils.getDirectChildElement(elem, "KeyInfo", "http://www.w3.org/2000/09/xmldsig#");
        X509Certificate[] certs = null;
        Principal principal = null;
        PublicKey publicKey = null;
        byte[] secretKey = null;
        String signatureMethod = SignatureProcessor.getSignatureMethod(elem);
        STRParser.REFERENCE_TYPE referenceType = null;
        Credential credential = new Credential();
        Validator validator = data.getValidator(WSConstants.SIGNATURE);
        if (keyInfoElement == null) {
            certs = this.getDefaultCerts(data.getSigVerCrypto());
            principal = certs[0].getSubjectX500Principal();
        } else {
            int result = 0;
            Node child = null;
            for (Node node = keyInfoElement.getFirstChild(); node != null; node = node.getNextSibling()) {
                if (1 != node.getNodeType()) continue;
                ++result;
                child = (Element)node;
            }
            if (result != 1) {
                data.getBSPEnforcer().handleBSPRule(BSPRule.R5402);
            }
            if (!"SecurityTokenReference".equals(child.getLocalName()) || !"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd".equals(child.getNamespaceURI())) {
                data.getBSPEnforcer().handleBSPRule(BSPRule.R5417);
                publicKey = X509Util.parseKeyValue(keyInfoElement, this.signatureFactory);
                if (validator != null) {
                    credential.setPublicKey(publicKey);
                    principal = new PublicKeyPrincipalImpl(publicKey);
                    credential.setPrincipal(principal);
                    credential = validator.validate(credential, data);
                }
            } else {
                STRParserParameters parameters = new STRParserParameters();
                parameters.setData(data);
                parameters.setStrElement((Element)child);
                if (signatureMethod != null) {
                    parameters.setDerivationKeyLength(KeyUtils.getKeyLength(signatureMethod));
                }
                SignatureSTRParser strParser = new SignatureSTRParser();
                STRParserResult parserResult = strParser.parseSecurityTokenReference(parameters);
                principal = parserResult.getPrincipal();
                certs = parserResult.getCertificates();
                publicKey = parserResult.getPublicKey();
                secretKey = parserResult.getSecretKey();
                referenceType = parserResult.getCertificatesReferenceType();
                boolean trusted = parserResult.isTrustedCredential();
                if (trusted) {
                    LOG.debug("Direct Trust for SAML/BST credential");
                }
                if (!(trusted || publicKey == null && certs == null || validator == null)) {
                    credential.setPublicKey(publicKey);
                    credential.setCertificates(certs);
                    credential.setPrincipal(principal);
                    credential = validator.validate(credential, data);
                }
            }
        }
        if ((certs == null || certs.length == 0 || certs[0] == null) && secretKey == null && publicKey == null) {
            LOG.debug("No certificates or keys were found with which to validate the signature");
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_CHECK);
        }
        AlgorithmSuite algorithmSuite = data.getAlgorithmSuite();
        if (algorithmSuite != null) {
            AlgorithmSuiteValidator algorithmSuiteValidator = new AlgorithmSuiteValidator(algorithmSuite);
            if (principal instanceof WSDerivedKeyTokenPrincipal) {
                algorithmSuiteValidator.checkDerivedKeyAlgorithm(((WSDerivedKeyTokenPrincipal)principal).getAlgorithm());
                algorithmSuiteValidator.checkSignatureDerivedKeyLength(((WSDerivedKeyTokenPrincipal)principal).getLength());
            } else if (certs != null && certs.length > 0) {
                algorithmSuiteValidator.checkAsymmetricKeyLength(certs);
            } else if (publicKey != null) {
                algorithmSuiteValidator.checkAsymmetricKeyLength(publicKey);
            } else if (secretKey != null) {
                algorithmSuiteValidator.checkSymmetricKeyLength(secretKey.length);
            }
        }
        XMLSignature xmlSignature = this.verifyXMLSignature(elem, certs, publicKey, secretKey, signatureMethod, data, data.getWsDocInfo());
        byte[] signatureValue = xmlSignature.getSignatureValue().getValue();
        String c14nMethod = xmlSignature.getSignedInfo().getCanonicalizationMethod().getAlgorithm();
        List<WSDataRef> dataRefs = this.buildProtectedRefs(elem.getOwnerDocument(), xmlSignature.getSignedInfo(), data, data.getWsDocInfo());
        if (dataRefs.isEmpty()) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_CHECK);
        }
        int actionPerformed = 2;
        if (principal instanceof UsernameTokenPrincipal) {
            actionPerformed = 64;
        }
        WSSecurityEngineResult result = new WSSecurityEngineResult(actionPerformed, principal, certs, dataRefs, signatureValue);
        result.put("signature-method", signatureMethod);
        result.put("canonicalization-method", c14nMethod);
        String tokenId = elem.getAttributeNS(null, "Id");
        if (!"".equals(tokenId)) {
            result.put("id", tokenId);
        }
        result.put("secret", secretKey);
        result.put("public-key", publicKey);
        result.put("x509-reference-type", referenceType);
        result.put("token-element", elem);
        if (validator != null) {
            result.put("validated-token", Boolean.TRUE);
            if (credential != null) {
                result.put("subject", credential.getSubject());
            }
        }
        data.getWsDocInfo().addResult(result);
        data.getWsDocInfo().addTokenElement(elem);
        return Collections.singletonList(result);
    }

    private X509Certificate[] getDefaultCerts(Crypto crypto) throws WSSecurityException {
        if (crypto == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "noSigCryptoFile");
        }
        if (crypto.getDefaultX509Identifier() != null) {
            CryptoType cryptoType = new CryptoType(CryptoType.TYPE.ALIAS);
            cryptoType.setAlias(crypto.getDefaultX509Identifier());
            return crypto.getX509Certificates(cryptoType);
        }
        throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, "unsupportedKeyInfo");
    }

    private XMLSignature verifyXMLSignature(Element elem, X509Certificate[] certs, PublicKey publicKey, byte[] secretKey, String signatureMethod, RequestData data, WSDocInfo wsDocInfo) throws WSSecurityException {
        LOG.debug("Verify XML Signature");
        Key key = null;
        key = certs != null && certs[0] != null ? certs[0].getPublicKey() : (publicKey != null ? publicKey : KeyUtils.prepareSecretKey(signatureMethod, secretKey));
        if (data.isExpandXopInclude()) {
            List<Element> includeElements = org.apache.wss4j.common.util.XMLUtils.findElements(elem.getFirstChild(), "Include", "http://www.w3.org/2004/08/xop/include");
            WSSecurityUtil.inlineAttachments(includeElements, data.getAttachmentCallbackHandler(), true);
        }
        DOMValidateContext context = new DOMValidateContext(key, (Node)elem);
        context.setProperty("javax.xml.crypto.dsig.cacheReference", Boolean.TRUE);
        context.setProperty("org.apache.jcp.xml.dsig.secureValidation", Boolean.TRUE);
        context.setProperty("org.jcp.xml.dsig.secureValidation", Boolean.TRUE);
        context.setProperty("transform_ws_doc_info", wsDocInfo);
        if (data.getSignatureProvider() != null) {
            context.setProperty("org.jcp.xml.dsig.internal.dom.SignatureProvider", data.getSignatureProvider());
        }
        context.setProperty("AttachmentContentTransform.attachmentCallbackHandler", data.getAttachmentCallbackHandler());
        try {
            XMLSignature xmlSignature = this.signatureFactory.unmarshalXMLSignature(context);
            this.checkBSPCompliance(xmlSignature, data.getBSPEnforcer());
            AlgorithmSuite algorithmSuite = data.getAlgorithmSuite();
            if (algorithmSuite != null) {
                AlgorithmSuiteValidator algorithmSuiteValidator = new AlgorithmSuiteValidator(algorithmSuite);
                algorithmSuiteValidator.checkSignatureAlgorithms(xmlSignature);
            }
            this.testMessageReplay(elem, xmlSignature.getSignatureValue().getValue(), key, data, wsDocInfo);
            this.setElementsOnContext(xmlSignature, context, data, wsDocInfo);
            boolean signatureOk = xmlSignature.validate(context);
            if (signatureOk) {
                return xmlSignature;
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("XML Signature verification has failed");
                boolean signatureValidationCheck = xmlSignature.getSignatureValue().validate(context);
                LOG.debug("Signature Validation check: " + signatureValidationCheck);
                for (Reference reference : xmlSignature.getSignedInfo().getReferences()) {
                    boolean referenceValidationCheck = reference.validate(context);
                    String id = reference.getId();
                    if (id == null) {
                        id = reference.getURI();
                    }
                    LOG.debug("Reference " + id + " check: " + referenceValidationCheck);
                }
            }
        }
        catch (WSSecurityException ex) {
            throw ex;
        }
        catch (Exception ex) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_CHECK, ex);
        }
        throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_CHECK);
    }

    private void setElementsOnContext(XMLSignature xmlSignature, DOMValidateContext context, RequestData data, WSDocInfo wsDocInfo) throws WSSecurityException {
        Iterator<Reference> referenceIterator = xmlSignature.getSignedInfo().getReferences().iterator();
        CallbackLookup callbackLookup = wsDocInfo.getCallbackLookup();
        while (referenceIterator.hasNext()) {
            Reference reference = referenceIterator.next();
            String uri = reference.getURI();
            Element element = callbackLookup.getAndRegisterElement(uri, null, true, context);
            if (element == null) {
                wsDocInfo.setTokenOnContext(uri, context);
                continue;
            }
            if ("BinarySecurityToken".equals(element.getLocalName()) && "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd".equals(element.getNamespaceURI()) && this.isXopInclude(element)) {
                this.handleXopInclude(element, wsDocInfo);
                continue;
            }
            if (!data.isExpandXopInclude() || element.getFirstChild() == null) continue;
            List<Element> includeElements = org.apache.wss4j.common.util.XMLUtils.findElements(element.getFirstChild(), "Include", "http://www.w3.org/2004/08/xop/include");
            for (Element includeElement : includeElements) {
                String xopURI = includeElement.getAttributeNS(null, "href");
                if (xopURI == null) continue;
                byte[] attachmentBytes = WSSecurityUtil.getBytesFromAttachment(xopURI, data);
                String encodedBytes = XMLUtils.encodeToString(attachmentBytes);
                Text newCipherValueChild = includeElement.getOwnerDocument().createTextNode(encodedBytes);
                includeElement.getParentNode().replaceChild(newCipherValueChild, includeElement);
            }
        }
    }

    private boolean isXopInclude(Element element) {
        String xopUri;
        Element elementChild = org.apache.wss4j.common.util.XMLUtils.getDirectChildElement(element, "Include", "http://www.w3.org/2004/08/xop/include");
        return elementChild != null && elementChild.hasAttributeNS(null, "href") && (xopUri = elementChild.getAttributeNS(null, "href")) != null && xopUri.startsWith("cid:");
    }

    private void handleXopInclude(Element element, WSDocInfo wsDocInfo) {
        Map<Integer, List<WSSecurityEngineResult>> actionResults = wsDocInfo.getActionResults();
        if (actionResults != null && actionResults.containsKey(4096)) {
            for (WSSecurityEngineResult result : actionResults.get(4096)) {
                Element token = (Element)result.get("token-element");
                if (!element.equals(token)) continue;
                BinarySecurity binarySecurity = (BinarySecurity)result.get("binary-security-token");
                binarySecurity.encodeRawToken();
                return;
            }
        }
    }

    private static String getSignatureMethod(Element signatureElement) {
        Element signatureMethodElement;
        Element signedInfoElement = org.apache.wss4j.common.util.XMLUtils.getDirectChildElement(signatureElement, "SignedInfo", "http://www.w3.org/2000/09/xmldsig#");
        if (signedInfoElement != null && (signatureMethodElement = org.apache.wss4j.common.util.XMLUtils.getDirectChildElement(signedInfoElement, "SignatureMethod", "http://www.w3.org/2000/09/xmldsig#")) != null) {
            return signatureMethodElement.getAttributeNS(null, "Algorithm");
        }
        return null;
    }

    private List<WSDataRef> buildProtectedRefs(Document doc, SignedInfo signedInfo, RequestData requestData, WSDocInfo wsDocInfo) throws WSSecurityException {
        ArrayList<WSDataRef> protectedRefs = new ArrayList<WSDataRef>(signedInfo.getReferences().size());
        for (Reference reference : signedInfo.getReferences()) {
            Reference siRef = reference;
            String uri = siRef.getURI();
            if ("".equals(uri)) continue;
            Element se = this.dereferenceSTR(doc, siRef, requestData, wsDocInfo);
            boolean attachment = false;
            if (se == null) {
                Data dereferencedData = siRef.getDereferencedData();
                if (dereferencedData instanceof NodeSetData) {
                    NodeSetData data = (NodeSetData)dereferencedData;
                    for (Node n : data) {
                        if (!(n instanceof Element)) continue;
                        se = (Element)n;
                        break;
                    }
                } else if (dereferencedData instanceof OctetStreamData) {
                    se = doc.createElementNS("http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1", "attachment");
                    attachment = true;
                }
            }
            if (se == null) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_CHECK);
            }
            WSDataRef ref = new WSDataRef();
            ref.setWsuId(uri);
            ref.setProtectedElement(se);
            ref.setAlgorithm(signedInfo.getSignatureMethod().getAlgorithm());
            ref.setDigestAlgorithm(siRef.getDigestMethod().getAlgorithm());
            ref.setDigestValue(siRef.getDigestValue());
            ref.setAttachment(attachment);
            List<Transform> transforms = siRef.getTransforms();
            ArrayList<String> transformAlgorithms = new ArrayList<String>(transforms.size());
            for (Transform transform : transforms) {
                transformAlgorithms.add(transform.getAlgorithm());
            }
            ref.setTransformAlgorithms(transformAlgorithms);
            ref.setXpath(EncryptionUtils.getXPath(se));
            protectedRefs.add(ref);
        }
        return protectedRefs;
    }

    private Element dereferenceSTR(Document doc, Reference siRef, RequestData requestData, WSDocInfo wsDocInfo) throws WSSecurityException {
        for (Transform transformObject : siRef.getTransforms()) {
            SecurityTokenReference secTokenRef;
            Element se;
            NodeSetData data;
            Transform transform = transformObject;
            if (!"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#STR-Transform".equals(transform.getAlgorithm()) || (data = (NodeSetData)siRef.getDereferencedData()) == null) continue;
            Iterator iter = data.iterator();
            Node securityTokenReference = null;
            while (iter.hasNext()) {
                Node node = (Node)iter.next();
                if (!"SecurityTokenReference".equals(node.getLocalName())) continue;
                securityTokenReference = node;
                break;
            }
            if (securityTokenReference == null || (se = STRTransformUtil.dereferenceSTR(doc, secTokenRef = new SecurityTokenReference((Element)securityTokenReference, requestData.getBSPEnforcer()), wsDocInfo)) == null) continue;
            return se;
        }
        return null;
    }

    private void testMessageReplay(Element signatureElement, byte[] signatureValue, Key key, RequestData requestData, WSDocInfo wsDocInfo) throws WSSecurityException {
        ReplayCache replayCache = requestData.getTimestampReplayCache();
        if (replayCache == null) {
            return;
        }
        List<WSSecurityEngineResult> foundResults = wsDocInfo.getResultsByTag(32);
        Timestamp timeStamp = null;
        if (foundResults.isEmpty()) {
            for (Node sibling = signatureElement.getNextSibling(); sibling != null; sibling = sibling.getNextSibling()) {
                if (!(sibling instanceof Element) || !"Timestamp".equals(sibling.getLocalName()) || !"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd".equals(sibling.getNamespaceURI())) continue;
                timeStamp = new Timestamp((Element)sibling, requestData.getBSPEnforcer());
                break;
            }
        } else {
            timeStamp = (Timestamp)foundResults.get(0).get("timestamp");
        }
        if (timeStamp == null) {
            return;
        }
        String identifier = timeStamp.getCreatedString() + "" + Arrays.hashCode(signatureValue) + "" + Arrays.hashCode(key.getEncoded());
        if (replayCache.contains(identifier)) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, "invalidTimestamp", new Object[]{"A replay attack has been detected"});
        }
        if (timeStamp.getExpires() != null) {
            replayCache.add(identifier, 1L + Duration.between(Instant.now(), timeStamp.getExpires()).getSeconds());
        } else {
            replayCache.add(identifier);
        }
    }

    private void checkBSPCompliance(XMLSignature xmlSignature, BSPEnforcer bspEnforcer) throws WSSecurityException {
        AlgorithmParameterSpec parameterSpec;
        for (XMLObject object : xmlSignature.getObjects()) {
            if (!(object instanceof XMLObject)) continue;
            XMLObject xmlObject = object;
            for (XMLStructure xmlStructure : xmlObject.getContent()) {
                if (!(xmlStructure instanceof Manifest)) continue;
                bspEnforcer.handleBSPRule(BSPRule.R5403);
            }
        }
        String c14nMethod = xmlSignature.getSignedInfo().getCanonicalizationMethod().getAlgorithm();
        if (!"http://www.w3.org/2001/10/xml-exc-c14n#".equals(c14nMethod)) {
            bspEnforcer.handleBSPRule(BSPRule.R5404);
        }
        if ((parameterSpec = xmlSignature.getSignedInfo().getSignatureMethod().getParameterSpec()) instanceof HMACParameterSpec) {
            bspEnforcer.handleBSPRule(BSPRule.R5401);
        }
        if ((parameterSpec = xmlSignature.getSignedInfo().getCanonicalizationMethod().getParameterSpec()) != null && !(parameterSpec instanceof ExcC14NParameterSpec)) {
            bspEnforcer.handleBSPRule(BSPRule.R5404);
        }
        for (Reference refObject : xmlSignature.getSignedInfo().getReferences()) {
            Reference reference = refObject;
            if (reference.getTransforms().isEmpty()) {
                bspEnforcer.handleBSPRule(BSPRule.R5416);
            }
            for (int i = 0; i < reference.getTransforms().size(); ++i) {
                Transform transform = reference.getTransforms().get(i);
                String algorithm = transform.getAlgorithm();
                if (!("http://www.w3.org/2001/10/xml-exc-c14n#".equals(algorithm) || "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#STR-Transform".equals(algorithm) || "http://www.w3.org/2002/06/xmldsig-filter2".equals(algorithm) || "http://www.w3.org/2000/09/xmldsig#enveloped-signature".equals(algorithm) || "http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Complete-Signature-Transform".equals(algorithm) || "http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Content-Signature-Transform".equals(algorithm))) {
                    bspEnforcer.handleBSPRule(BSPRule.R5423);
                }
                if (!(i != reference.getTransforms().size() - 1 || "http://www.w3.org/2001/10/xml-exc-c14n#".equals(algorithm) || "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#STR-Transform".equals(algorithm) || "http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Complete-Signature-Transform".equals(algorithm) || "http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Content-Signature-Transform".equals(algorithm))) {
                    bspEnforcer.handleBSPRule(BSPRule.R5412);
                }
                if (!"http://www.w3.org/2001/10/xml-exc-c14n#".equals(algorithm) || (parameterSpec = transform.getParameterSpec()) == null || parameterSpec instanceof ExcC14NParameterSpec) continue;
                bspEnforcer.handleBSPRule(BSPRule.R5407);
            }
        }
    }
}

