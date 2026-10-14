/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j.policyvalidators;

import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathFactoryConfigurationException;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.helpers.MapNamespaceContext;
import org.apache.cxf.message.Message;
import org.apache.cxf.security.transport.TLSSessionInfo;
import org.apache.cxf.ws.policy.AssertionInfo;
import org.apache.cxf.ws.policy.AssertionInfoMap;
import org.apache.cxf.ws.security.policy.PolicyUtils;
import org.apache.cxf.ws.security.wss4j.policyvalidators.AbstractSecurityPolicyValidator;
import org.apache.cxf.ws.security.wss4j.policyvalidators.PolicyValidatorParameters;
import org.apache.wss4j.common.saml.SAMLKeyInfo;
import org.apache.wss4j.common.saml.SamlAssertionWrapper;
import org.apache.wss4j.common.token.BinarySecurity;
import org.apache.wss4j.common.token.PKIPathSecurity;
import org.apache.wss4j.common.token.X509Security;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.WSDataRef;
import org.apache.wss4j.dom.engine.WSSecurityEngineResult;
import org.apache.wss4j.dom.handler.WSHandlerResult;
import org.apache.wss4j.dom.message.token.KerberosSecurity;
import org.apache.wss4j.policy.model.AbstractSecuredParts;
import org.apache.wss4j.policy.model.AbstractSecurityAssertion;
import org.apache.wss4j.policy.model.AbstractToken;
import org.apache.wss4j.policy.model.EncryptedElements;
import org.apache.wss4j.policy.model.EncryptedParts;
import org.apache.wss4j.policy.model.Header;
import org.apache.wss4j.policy.model.IssuedToken;
import org.apache.wss4j.policy.model.RequiredElements;
import org.apache.wss4j.policy.model.SignedElements;
import org.apache.wss4j.policy.model.SignedParts;
import org.apache.wss4j.policy.model.SupportingTokens;
import org.apache.wss4j.policy.model.XPath;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public abstract class AbstractSupportingTokenPolicyValidator
extends AbstractSecurityPolicyValidator {
    private static final Logger LOG = LogUtils.getL7dLogger(AbstractSupportingTokenPolicyValidator.class);
    private SignedElements signedElements;
    private EncryptedElements encryptedElements;
    private SignedParts signedParts;
    private EncryptedParts encryptedParts;
    private boolean enforceEncryptedTokens = true;

    protected abstract boolean isSigned();

    protected abstract boolean isEncrypted();

    protected abstract boolean isEndorsing();

    protected boolean processUsernameTokens(PolicyValidatorParameters parameters, boolean derived) {
        if (!parameters.isUtWithCallbacks()) {
            return true;
        }
        if (parameters.getUsernameTokenResults().isEmpty()) {
            return false;
        }
        ArrayList<WSSecurityEngineResult> tokenResults = new ArrayList<WSSecurityEngineResult>();
        tokenResults.addAll(parameters.getUsernameTokenResults());
        if (this.isSigned() && !this.areTokensSigned(tokenResults, parameters.getSignedResults(), parameters.getEncryptedResults(), parameters.getMessage())) {
            return false;
        }
        if (this.isEncrypted() && !this.areTokensEncrypted(tokenResults, parameters.getEncryptedResults())) {
            return false;
        }
        if (derived && parameters.getResults().getActionResults().containsKey(2048)) {
            for (WSSecurityEngineResult wser : parameters.getUsernameTokenResults()) {
                WSSecurityEngineResult dktResult;
                byte[] secret = (byte[])wser.get("secret");
                if (secret == null || (dktResult = this.getMatchingDerivedKey(secret, parameters.getResults())) == null) continue;
                tokenResults.add(dktResult);
            }
        }
        return (!this.isEndorsing() || this.checkEndorsed(tokenResults, parameters.getSignedResults(), parameters.getMessage(), parameters.getTimestampElement())) && this.validateSignedEncryptedPolicies(tokenResults, parameters.getSignedResults(), parameters.getEncryptedResults(), parameters.getMessage());
    }

    protected boolean processSAMLTokens(PolicyValidatorParameters parameters, boolean derived) {
        if (parameters.getSamlResults().isEmpty()) {
            return false;
        }
        ArrayList<WSSecurityEngineResult> tokenResults = new ArrayList<WSSecurityEngineResult>();
        tokenResults.addAll(parameters.getSamlResults());
        if (this.isSigned() && !this.areTokensSigned(tokenResults, parameters.getSignedResults(), parameters.getEncryptedResults(), parameters.getMessage())) {
            return false;
        }
        if (this.isEncrypted() && !this.areTokensEncrypted(tokenResults, parameters.getEncryptedResults())) {
            return false;
        }
        if (derived && parameters.getResults().getActionResults().containsKey(2048)) {
            ArrayList<WSSecurityEngineResult> dktResults = new ArrayList<WSSecurityEngineResult>(tokenResults.size());
            for (WSSecurityEngineResult wser : tokenResults) {
                WSSecurityEngineResult dktResult;
                SamlAssertionWrapper assertion = (SamlAssertionWrapper)wser.get("saml-assertion");
                if (assertion == null || assertion.getSubjectKeyInfo() == null || assertion.getSubjectKeyInfo().getSecret() == null || (dktResult = this.getMatchingDerivedKey(assertion.getSubjectKeyInfo().getSecret(), parameters.getResults())) == null) continue;
                dktResults.add(dktResult);
            }
            tokenResults.addAll(dktResults);
        }
        if (this.isEndorsing() && !this.checkEndorsed(tokenResults, parameters.getSignedResults(), parameters.getMessage(), parameters.getTimestampElement())) {
            return false;
        }
        return this.validateSignedEncryptedPolicies(tokenResults, parameters.getSignedResults(), parameters.getEncryptedResults(), parameters.getMessage());
    }

    protected boolean processKerberosTokens(PolicyValidatorParameters parameters, boolean derived) {
        ArrayList<WSSecurityEngineResult> tokenResults = null;
        if (parameters.getResults().getActionResults().containsKey(4096)) {
            tokenResults = new ArrayList<WSSecurityEngineResult>();
            for (WSSecurityEngineResult wser : parameters.getResults().getActionResults().get(4096)) {
                BinarySecurity binarySecurity = (BinarySecurity)wser.get("binary-security-token");
                if (!(binarySecurity instanceof KerberosSecurity)) continue;
                tokenResults.add(wser);
            }
        }
        if (tokenResults == null || tokenResults.isEmpty()) {
            return false;
        }
        if (this.isSigned() && !this.areTokensSigned(tokenResults, parameters.getSignedResults(), parameters.getEncryptedResults(), parameters.getMessage())) {
            return false;
        }
        if (this.isEncrypted() && !this.areTokensEncrypted(tokenResults, parameters.getEncryptedResults())) {
            return false;
        }
        if (derived && parameters.getResults().getActionResults().containsKey(2048)) {
            ArrayList<WSSecurityEngineResult> dktResults = new ArrayList<WSSecurityEngineResult>(tokenResults.size());
            for (WSSecurityEngineResult wser : tokenResults) {
                byte[] secret = (byte[])wser.get("secret");
                WSSecurityEngineResult dktResult = this.getMatchingDerivedKey(secret, parameters.getResults());
                if (dktResult == null) continue;
                dktResults.add(dktResult);
            }
            tokenResults.addAll(dktResults);
        }
        if (this.isEndorsing() && !this.checkEndorsed(tokenResults, parameters.getSignedResults(), parameters.getMessage(), parameters.getTimestampElement())) {
            return false;
        }
        return this.validateSignedEncryptedPolicies(tokenResults, parameters.getSignedResults(), parameters.getEncryptedResults(), parameters.getMessage());
    }

    protected boolean processX509Tokens(PolicyValidatorParameters parameters, boolean derived) {
        ArrayList<WSSecurityEngineResult> tokenResults = null;
        if (parameters.getResults().getActionResults().containsKey(4096)) {
            tokenResults = new ArrayList<WSSecurityEngineResult>();
            for (WSSecurityEngineResult wser : parameters.getResults().getActionResults().get(4096)) {
                BinarySecurity binarySecurity = (BinarySecurity)wser.get("binary-security-token");
                if (!(binarySecurity instanceof X509Security) && !(binarySecurity instanceof PKIPathSecurity)) continue;
                tokenResults.add(wser);
            }
        }
        if (tokenResults == null || tokenResults.isEmpty()) {
            return false;
        }
        if (this.isSigned() && !this.areTokensSigned(tokenResults, parameters.getSignedResults(), parameters.getEncryptedResults(), parameters.getMessage())) {
            return false;
        }
        if (this.isEncrypted() && !this.areTokensEncrypted(tokenResults, parameters.getEncryptedResults())) {
            return false;
        }
        if (derived && parameters.getResults().getActionResults().containsKey(2048)) {
            ArrayList<WSSecurityEngineResult> dktResults = new ArrayList<WSSecurityEngineResult>(tokenResults.size());
            for (WSSecurityEngineResult wser : tokenResults) {
                WSSecurityEngineResult resultToStore = this.processX509DerivedTokenResult(wser, parameters.getResults());
                if (resultToStore == null) continue;
                dktResults.add(resultToStore);
            }
            tokenResults.addAll(dktResults);
        }
        if (this.isEndorsing() && !this.checkEndorsed(tokenResults, parameters.getSignedResults(), parameters.getMessage(), parameters.getTimestampElement())) {
            return false;
        }
        return this.validateSignedEncryptedPolicies(tokenResults, parameters.getSignedResults(), parameters.getEncryptedResults(), parameters.getMessage());
    }

    protected boolean processKeyValueTokens(PolicyValidatorParameters parameters) {
        ArrayList<WSSecurityEngineResult> tokenResults = null;
        if (parameters.getSignedResults() != null && !parameters.getSignedResults().isEmpty()) {
            tokenResults = new ArrayList<WSSecurityEngineResult>();
            for (WSSecurityEngineResult wser : parameters.getSignedResults()) {
                PublicKey publicKey = (PublicKey)wser.get("public-key");
                if (publicKey == null) continue;
                tokenResults.add(wser);
            }
        }
        if (tokenResults == null || tokenResults.isEmpty()) {
            return false;
        }
        if (this.isSigned() && !this.areTokensSigned(tokenResults, parameters.getSignedResults(), parameters.getEncryptedResults(), parameters.getMessage())) {
            return false;
        }
        if (this.isEncrypted() && !this.areTokensEncrypted(tokenResults, parameters.getEncryptedResults())) {
            return false;
        }
        if (this.isEndorsing() && !this.checkEndorsed(tokenResults, parameters.getSignedResults(), parameters.getMessage(), parameters.getTimestampElement())) {
            return false;
        }
        return this.validateSignedEncryptedPolicies(tokenResults, parameters.getSignedResults(), parameters.getEncryptedResults(), parameters.getMessage());
    }

    private boolean validateSignedEncryptedPolicies(List<WSSecurityEngineResult> tokenResults, List<WSSecurityEngineResult> signedResults, List<WSSecurityEngineResult> encryptedResults, Message message) {
        if (!this.validateSignedEncryptedParts(this.signedParts, false, signedResults, tokenResults, message)) {
            return false;
        }
        if (!this.validateSignedEncryptedParts(this.encryptedParts, true, encryptedResults, tokenResults, message)) {
            return false;
        }
        if (!this.validateSignedEncryptedElements(this.signedElements, signedResults, tokenResults, message)) {
            return false;
        }
        return this.validateSignedEncryptedElements(this.encryptedElements, encryptedResults, tokenResults, message);
    }

    protected boolean processSCTokens(PolicyValidatorParameters parameters, boolean derived) {
        if (!parameters.getResults().getActionResults().containsKey(1024)) {
            return false;
        }
        ArrayList<WSSecurityEngineResult> tokenResults = new ArrayList<WSSecurityEngineResult>();
        tokenResults.addAll((Collection)parameters.getResults().getActionResults().get(1024));
        if (this.isSigned() && !this.areTokensSigned(tokenResults, parameters.getSignedResults(), parameters.getEncryptedResults(), parameters.getMessage())) {
            return false;
        }
        if (this.isEncrypted() && !this.areTokensEncrypted(tokenResults, parameters.getEncryptedResults())) {
            return false;
        }
        if (derived && parameters.getResults().getActionResults().containsKey(2048)) {
            ArrayList<WSSecurityEngineResult> dktResults = new ArrayList<WSSecurityEngineResult>(tokenResults.size());
            for (WSSecurityEngineResult wser : tokenResults) {
                byte[] secret = (byte[])wser.get("secret");
                WSSecurityEngineResult dktResult = this.getMatchingDerivedKey(secret, parameters.getResults());
                if (dktResult == null) continue;
                dktResults.add(dktResult);
            }
            tokenResults.addAll(dktResults);
        }
        if (this.isEndorsing() && !this.checkEndorsed(tokenResults, parameters.getSignedResults(), parameters.getMessage(), parameters.getTimestampElement())) {
            return false;
        }
        return this.validateSignedEncryptedPolicies(tokenResults, parameters.getSignedResults(), parameters.getEncryptedResults(), parameters.getMessage());
    }

    private WSSecurityEngineResult processX509DerivedTokenResult(WSSecurityEngineResult result, WSHandlerResult results) {
        byte[] secret;
        WSSecurityEngineResult dktResult;
        X509Certificate cert = (X509Certificate)result.get("x509-certificate");
        WSSecurityEngineResult encrResult = this.getMatchingEncryptedKey(cert, results);
        if (encrResult != null && (dktResult = this.getMatchingDerivedKey(secret = (byte[])encrResult.get("secret"), results)) != null) {
            return dktResult;
        }
        return null;
    }

    private WSSecurityEngineResult getMatchingDerivedKey(byte[] secret, WSHandlerResult results) {
        for (WSSecurityEngineResult wser : results.getActionResults().get(2048)) {
            byte[] dktSecret = (byte[])wser.get("secret");
            if (!Arrays.equals(secret, dktSecret)) continue;
            return wser;
        }
        return null;
    }

    private WSSecurityEngineResult getMatchingEncryptedKey(X509Certificate cert, WSHandlerResult results) {
        if (results.getActionResults().containsKey(4)) {
            for (WSSecurityEngineResult wser : results.getActionResults().get(4)) {
                X509Certificate encrCert = (X509Certificate)wser.get("x509-certificate");
                if (!cert.equals(encrCert)) continue;
                return wser;
            }
        }
        return null;
    }

    protected boolean isTLSInUse(Message message) {
        TLSSessionInfo tlsInfo = message.get(TLSSessionInfo.class);
        return tlsInfo != null;
    }

    private boolean checkEndorsed(List<WSSecurityEngineResult> tokenResults, List<WSSecurityEngineResult> signedResults, Message message, Element timestamp) {
        boolean endorsingSatisfied = false;
        if (this.isTLSInUse(message)) {
            endorsingSatisfied = this.checkTimestampIsSigned(tokenResults, signedResults, timestamp);
        }
        if (!endorsingSatisfied) {
            endorsingSatisfied = this.checkSignatureIsSigned(tokenResults, signedResults);
        }
        return endorsingSatisfied;
    }

    private boolean areTokensSigned(List<WSSecurityEngineResult> tokens, List<WSSecurityEngineResult> signedResults, List<WSSecurityEngineResult> encryptedResults, Message message) {
        if (!this.isTLSInUse(message)) {
            for (WSSecurityEngineResult wser : tokens) {
                Element tokenElement = (Element)wser.get("token-element");
                if (tokenElement != null && this.isTokenSigned(tokenElement, signedResults, encryptedResults)) continue;
                return false;
            }
        }
        return true;
    }

    private boolean areTokensEncrypted(List<WSSecurityEngineResult> tokens, List<WSSecurityEngineResult> encryptedResults) {
        if (this.enforceEncryptedTokens) {
            for (WSSecurityEngineResult wser : tokens) {
                Element tokenElement = (Element)wser.get("token-element");
                if (tokenElement != null && this.isTokenEncrypted(tokenElement, encryptedResults)) continue;
                return false;
            }
        }
        return true;
    }

    private boolean checkTimestampIsSigned(List<WSSecurityEngineResult> tokenResults, List<WSSecurityEngineResult> signedResults, Element timestamp) {
        for (WSSecurityEngineResult signedResult : signedResults) {
            List<WSDataRef> sl = CastUtils.cast((List)signedResult.get("data-ref-uris"));
            if (sl == null) continue;
            for (WSDataRef dataRef : sl) {
                if (timestamp != dataRef.getProtectedElement() || !this.checkSignatureOrEncryptionResult(signedResult, tokenResults)) continue;
                return true;
            }
        }
        return false;
    }

    private boolean checkSignatureIsSigned(List<WSSecurityEngineResult> tokenResults, List<WSSecurityEngineResult> signedResults) {
        for (WSSecurityEngineResult signedResult : signedResults) {
            List<WSDataRef> sl = CastUtils.cast((List)signedResult.get("data-ref-uris"));
            if (sl == null || sl.isEmpty()) continue;
            for (WSDataRef dataRef : sl) {
                QName signedQName = dataRef.getName();
                if (!WSConstants.SIGNATURE.equals(signedQName) || !this.checkSignatureOrEncryptionResult(signedResult, tokenResults)) continue;
                return true;
            }
        }
        return false;
    }

    private boolean checkSignatureOrEncryptionResult(WSSecurityEngineResult result, List<WSSecurityEngineResult> tokenResult) {
        X509Certificate cert = (X509Certificate)result.get("x509-certificate");
        byte[] secret = (byte[])result.get("secret");
        PublicKey publicKey = (PublicKey)result.get("public-key");
        for (WSSecurityEngineResult token : tokenResult) {
            Integer actInt = (Integer)token.get("action");
            BinarySecurity binarySecurity = (BinarySecurity)token.get("binary-security-token");
            if (binarySecurity instanceof X509Security || binarySecurity instanceof PKIPathSecurity) {
                X509Certificate foundCert = (X509Certificate)token.get("x509-certificate");
                if (!foundCert.equals(cert)) continue;
                return true;
            }
            if (actInt == 16 || actInt == 8) {
                SamlAssertionWrapper assertionWrapper = (SamlAssertionWrapper)token.get("saml-assertion");
                SAMLKeyInfo samlKeyInfo = assertionWrapper.getSubjectKeyInfo();
                if (samlKeyInfo == null) continue;
                X509Certificate[] subjectCerts = samlKeyInfo.getCerts();
                byte[] subjectSecretKey = samlKeyInfo.getSecret();
                PublicKey subjectPublicKey = samlKeyInfo.getPublicKey();
                if (!(cert != null && subjectCerts != null && cert.equals(subjectCerts[0]) || subjectSecretKey != null && Arrays.equals(subjectSecretKey, secret)) && (subjectPublicKey == null || !subjectPublicKey.equals(publicKey))) continue;
                return true;
            }
            if (publicKey != null) {
                PublicKey foundPublicKey = (PublicKey)token.get("public-key");
                if (!publicKey.equals(foundPublicKey)) continue;
                return true;
            }
            byte[] foundSecret = (byte[])token.get("secret");
            byte[] derivedKey = (byte[])token.get("encrypted-ephemeral-key-bytes");
            if ((foundSecret == null || !Arrays.equals(foundSecret, secret)) && (derivedKey == null || !Arrays.equals(derivedKey, secret))) continue;
            return true;
        }
        return false;
    }

    private boolean validateSignedEncryptedParts(AbstractSecuredParts parts, boolean content, List<WSSecurityEngineResult> protResults, List<WSSecurityEngineResult> tokenResults, Message message) {
        if (parts == null) {
            return true;
        }
        if (parts.isBody()) {
            SOAPMessage soapMessage = message.getContent(SOAPMessage.class);
            SOAPBody soapBody = null;
            try {
                soapBody = soapMessage.getSOAPBody();
            }
            catch (SOAPException ex) {
                LOG.log(Level.FINE, ex.getMessage(), ex);
                return false;
            }
            if (!this.checkProtectionResult(soapBody, content, protResults, tokenResults)) {
                return false;
            }
        }
        for (Header h2 : parts.getHeaders()) {
            SOAPMessage soapMessage = message.getContent(SOAPMessage.class);
            SOAPHeader soapHeader = null;
            try {
                soapHeader = soapMessage.getSOAPHeader();
            }
            catch (SOAPException ex) {
                LOG.log(Level.FINE, ex.getMessage(), ex);
                return false;
            }
            List<Element> elements = h2.getName() == null ? DOMUtils.getChildrenWithNamespace(soapHeader, h2.getNamespace()) : DOMUtils.getChildrenWithName(soapHeader, h2.getNamespace(), h2.getName());
            for (Element el : elements) {
                if (this.checkProtectionResult(el = (Element)DOMUtils.getDomElement(el), false, protResults, tokenResults)) continue;
                return false;
            }
        }
        return true;
    }

    private boolean checkProtectionResult(Element elementToProtect, boolean content, List<WSSecurityEngineResult> protResults, List<WSSecurityEngineResult> tokenResults) {
        elementToProtect = (Element)DOMUtils.getDomElement(elementToProtect);
        for (WSSecurityEngineResult result : protResults) {
            List<WSDataRef> dataRefs = CastUtils.cast((List)result.get("data-ref-uris"));
            if (dataRefs == null) continue;
            for (WSDataRef dataRef : dataRefs) {
                if (elementToProtect != dataRef.getProtectedElement() || content != dataRef.isContent() || !this.checkSignatureOrEncryptionResult(result, tokenResults)) continue;
                return true;
            }
        }
        return false;
    }

    private boolean validateSignedEncryptedElements(RequiredElements elements, List<WSSecurityEngineResult> protResults, List<WSSecurityEngineResult> tokenResults, Message message) {
        if (elements == null) {
            return true;
        }
        List<XPath> xpaths = elements.getXPaths();
        if (xpaths != null && !xpaths.isEmpty()) {
            SOAPMessage soapMessage = message.getContent(SOAPMessage.class);
            Element soapEnvelope = soapMessage.getSOAPPart().getDocumentElement();
            XPathFactory factory2 = XPathFactory.newInstance();
            try {
                factory2.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", Boolean.TRUE);
            }
            catch (XPathFactoryConfigurationException xPathFactoryConfigurationException) {
                // empty catch block
            }
            javax.xml.xpath.XPath xpath = factory2.newXPath();
            MapNamespaceContext namespaceContext = new MapNamespaceContext();
            for (XPath xPath : xpaths) {
                Map<String, String> namespaceMap = xPath.getPrefixNamespaceMap();
                if (namespaceMap == null) continue;
                namespaceContext.addNamespaces(namespaceMap);
            }
            xpath.setNamespaceContext(namespaceContext);
            for (XPath xPath : xpaths) {
                if (this.checkXPathResult(soapEnvelope, xpath, xPath.getXPath(), protResults, tokenResults)) continue;
                return false;
            }
        }
        return true;
    }

    private boolean checkXPathResult(Element soapEnvelope, javax.xml.xpath.XPath xpath, String xPathString, List<WSSecurityEngineResult> protResults, List<WSSecurityEngineResult> tokenResults) {
        NodeList list;
        try {
            list = (NodeList)xpath.evaluate(xPathString, soapEnvelope, XPathConstants.NODESET);
        }
        catch (XPathExpressionException e) {
            LOG.log(Level.FINE, e.getMessage(), e);
            return false;
        }
        if (list.getLength() != 0) {
            for (int x = 0; x < list.getLength(); ++x) {
                Element el = (Element)list.item(x);
                if (this.checkProtectionResult(el, false, protResults, tokenResults)) continue;
                return false;
            }
        }
        return true;
    }

    private boolean isTokenSigned(Element token, List<WSSecurityEngineResult> signedResults, List<WSSecurityEngineResult> encryptedResults) {
        for (WSSecurityEngineResult signedResult : signedResults) {
            List<WSDataRef> dataRefs = CastUtils.cast((List)signedResult.get("data-ref-uris"));
            for (WSDataRef dataRef : dataRefs) {
                if (token != dataRef.getProtectedElement() && !this.isEncryptedTokenSigned(token, dataRef, encryptedResults)) continue;
                return true;
            }
        }
        return false;
    }

    private boolean isEncryptedTokenSigned(Element token, WSDataRef signedRef, List<WSSecurityEngineResult> encryptedResults) {
        if (signedRef.getProtectedElement() != null && "EncryptedData".equals(signedRef.getProtectedElement().getLocalName()) && "http://www.w3.org/2001/04/xmlenc#".equals(signedRef.getProtectedElement().getNamespaceURI())) {
            String encryptedDataId = signedRef.getProtectedElement().getAttributeNS(null, "Id");
            for (WSSecurityEngineResult result : encryptedResults) {
                List<WSDataRef> encryptedDataRefs = CastUtils.cast((List)result.get("data-ref-uris"));
                if (encryptedDataRefs == null) continue;
                for (WSDataRef encryptedDataRef : encryptedDataRefs) {
                    if (token != encryptedDataRef.getProtectedElement() || encryptedDataRef.getWsuId() == null || !encryptedDataRef.getWsuId().equals(encryptedDataId)) continue;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isTokenEncrypted(Element token, List<WSSecurityEngineResult> encryptedResults) {
        for (WSSecurityEngineResult result : encryptedResults) {
            List<WSDataRef> dataRefs = CastUtils.cast((List)result.get("data-ref-uris"));
            if (dataRefs == null) continue;
            for (WSDataRef dataRef : dataRefs) {
                if (token != dataRef.getProtectedElement()) continue;
                return true;
            }
        }
        return false;
    }

    public void setSignedElements(SignedElements signedElements) {
        this.signedElements = signedElements;
    }

    public void setEncryptedElements(EncryptedElements encryptedElements) {
        this.encryptedElements = encryptedElements;
    }

    public void setSignedParts(SignedParts signedParts) {
        this.signedParts = signedParts;
    }

    public void setEncryptedParts(EncryptedParts encryptedParts) {
        this.encryptedParts = encryptedParts;
    }

    protected void assertSecurePartsIfTokenNotRequired(SupportingTokens supportingToken, AssertionInfoMap aim) {
        String namespace = supportingToken.getName().getNamespaceURI();
        if (supportingToken.getSignedParts() != null) {
            this.assertSecurePartsIfTokenNotRequired(supportingToken.getSignedParts(), new QName(namespace, "SignedParts"), aim);
        }
        if (supportingToken.getSignedElements() != null) {
            this.assertSecurePartsIfTokenNotRequired(supportingToken.getSignedElements(), new QName(namespace, "SignedElements"), aim);
        }
        if (supportingToken.getEncryptedParts() != null) {
            this.assertSecurePartsIfTokenNotRequired(supportingToken.getEncryptedParts(), new QName(namespace, "EncryptedParts"), aim);
        }
        if (supportingToken.getEncryptedElements() != null) {
            this.assertSecurePartsIfTokenNotRequired(supportingToken.getEncryptedElements(), new QName(namespace, "EncryptedElements"), aim);
        }
    }

    protected void assertSecurePartsIfTokenNotRequired(AbstractSecurityAssertion securedPart, QName name, AssertionInfoMap aim) {
        Collection ais = (Collection)aim.get(name);
        if (ais != null && !ais.isEmpty()) {
            for (AssertionInfo ai : ais) {
                if (!ai.getAssertion().equals(securedPart)) continue;
                ai.setAsserted(true);
            }
        }
    }

    public boolean isEnforceEncryptedTokens() {
        return this.enforceEncryptedTokens;
    }

    public void setEnforceEncryptedTokens(boolean enforceEncryptedTokens) {
        this.enforceEncryptedTokens = enforceEncryptedTokens;
    }

    protected void assertDerivedKeys(AbstractToken token, AssertionInfoMap aim) {
        AbstractToken.DerivedKeys derivedKeys = token.getDerivedKeys();
        if (derivedKeys != null) {
            PolicyUtils.assertPolicy(aim, new QName(token.getName().getNamespaceURI(), derivedKeys.name()));
        }
    }

    protected static boolean isSamlTokenRequiredForIssuedToken(IssuedToken issuedToken) {
        Element template = issuedToken.getRequestSecurityTokenTemplate();
        if (template != null) {
            Element child = DOMUtils.getFirstElement(template);
            while (child != null) {
                if ("TokenType".equals(child.getLocalName())) {
                    String content = child.getTextContent();
                    return "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1".equals(content) || "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0".equals(content);
                }
                child = DOMUtils.getNextElement(child);
            }
        }
        return false;
    }
}

