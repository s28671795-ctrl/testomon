/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.processor;

import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.namespace.QName;
import org.apache.wss4j.common.crypto.AlgorithmSuite;
import org.apache.wss4j.common.crypto.AlgorithmSuiteValidator;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.principal.SAMLTokenPrincipalImpl;
import org.apache.wss4j.common.saml.SAMLKeyInfo;
import org.apache.wss4j.common.saml.SAMLUtil;
import org.apache.wss4j.common.saml.SamlAssertionWrapper;
import org.apache.wss4j.common.util.DOM2Writer;
import org.apache.wss4j.dom.WSDataRef;
import org.apache.wss4j.dom.engine.WSSecurityEngineResult;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.dom.processor.Processor;
import org.apache.wss4j.dom.saml.WSSSAMLKeyInfoProcessor;
import org.apache.wss4j.dom.util.EncryptionUtils;
import org.apache.wss4j.dom.validate.Credential;
import org.apache.wss4j.dom.validate.Validator;
import org.opensaml.xmlsec.signature.KeyInfo;
import org.opensaml.xmlsec.signature.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class SAMLTokenProcessor
implements Processor {
    private static final Logger LOG = LoggerFactory.getLogger(SAMLTokenProcessor.class);
    private XMLSignatureFactory signatureFactory;

    public SAMLTokenProcessor() {
        this.init(null);
    }

    public SAMLTokenProcessor(Provider provider) {
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
        LOG.debug("Found SAML Assertion element");
        Validator validator = data.getValidator(new QName(elem.getNamespaceURI(), elem.getLocalName()));
        SamlAssertionWrapper samlAssertion = new SamlAssertionWrapper(elem);
        XMLSignature xmlSignature = this.verifySignatureKeysAndAlgorithms(samlAssertion, data);
        List<WSDataRef> dataRefs = this.createDataRefs(elem, samlAssertion, xmlSignature);
        Credential credential = this.handleSAMLToken(samlAssertion, data, validator);
        samlAssertion = credential.getSamlAssertion();
        if (LOG.isDebugEnabled()) {
            LOG.debug("SAML Assertion issuer " + samlAssertion.getIssuerString());
            LOG.debug(DOM2Writer.nodeToString(elem));
        }
        String id = samlAssertion.getId();
        Element foundElement = data.getWsDocInfo().getTokenElement(id);
        if (elem.equals(foundElement)) {
            WSSecurityEngineResult result = data.getWsDocInfo().getResult(id);
            return Collections.singletonList(result);
        }
        if (foundElement != null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY_TOKEN, "duplicateError");
        }
        data.getWsDocInfo().addTokenElement(elem);
        WSSecurityEngineResult result = null;
        if (samlAssertion.isSigned()) {
            result = new WSSecurityEngineResult(16, samlAssertion);
            result.put("data-ref-uris", dataRefs);
            result.put("signature-value", samlAssertion.getSignatureValue());
        } else {
            result = new WSSecurityEngineResult(8, samlAssertion);
        }
        if (!"".equals(id)) {
            result.put("id", id);
        }
        if (validator != null) {
            result.put("validated-token", Boolean.TRUE);
            if (credential.getTransformedToken() != null) {
                result.put("transformed-token", credential.getTransformedToken());
                if (credential.getPrincipal() != null) {
                    result.put("principal", credential.getPrincipal());
                } else {
                    SAMLTokenPrincipalImpl samlPrincipal = new SAMLTokenPrincipalImpl(credential.getTransformedToken());
                    result.put("principal", samlPrincipal);
                }
            } else if (credential.getPrincipal() != null) {
                result.put("principal", credential.getPrincipal());
            } else {
                result.put("principal", new SAMLTokenPrincipalImpl(samlAssertion));
            }
            result.put("subject", credential.getSubject());
        }
        data.getWsDocInfo().addResult(result);
        return Collections.singletonList(result);
    }

    public Credential handleSAMLToken(SamlAssertionWrapper samlAssertion, RequestData data, Validator validator) throws WSSecurityException {
        samlAssertion.parseSubject(new WSSSAMLKeyInfoProcessor(data), data.getSigVerCrypto(), data.getCallbackHandler());
        Credential credential = new Credential();
        credential.setSamlAssertion(samlAssertion);
        if (validator != null) {
            return validator.validate(credential, data);
        }
        return credential;
    }

    private XMLSignature verifySignatureKeysAndAlgorithms(SamlAssertionWrapper samlAssertion, RequestData data) throws WSSecurityException {
        if (samlAssertion.isSigned()) {
            XMLSignature xmlSignature;
            Signature sig = samlAssertion.getSignature();
            KeyInfo keyInfo = sig.getKeyInfo();
            if (keyInfo == null) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "invalidSAMLsecurity", new Object[]{"cannot get certificate or key"});
            }
            SAMLKeyInfo samlKeyInfo = SAMLUtil.getCredentialFromKeyInfo(keyInfo.getDOM(), new WSSSAMLKeyInfoProcessor(data), data.getSigVerCrypto());
            PublicKey key = null;
            if (samlKeyInfo.getCerts() != null && samlKeyInfo.getCerts()[0] != null) {
                key = samlKeyInfo.getCerts()[0].getPublicKey();
            } else if (samlKeyInfo.getPublicKey() != null) {
                key = samlKeyInfo.getPublicKey();
            } else {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "invalidSAMLsecurity", new Object[]{"cannot get certificate or key"});
            }
            DOMValidateContext context = new DOMValidateContext(key, (Node)sig.getDOM());
            context.setProperty("org.apache.jcp.xml.dsig.secureValidation", Boolean.TRUE);
            context.setProperty("org.jcp.xml.dsig.secureValidation", Boolean.TRUE);
            if (data.getSignatureProvider() != null) {
                context.setProperty("org.jcp.xml.dsig.internal.dom.SignatureProvider", data.getSignatureProvider());
            }
            try {
                xmlSignature = this.signatureFactory.unmarshalXMLSignature(context);
            }
            catch (MarshalException ex) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_CHECK, ex, "invalidSAMLsecurity", new Object[]{"cannot get certificate or key"});
            }
            AlgorithmSuite algorithmSuite = data.getSamlAlgorithmSuite();
            if (algorithmSuite != null) {
                AlgorithmSuiteValidator algorithmSuiteValidator = new AlgorithmSuiteValidator(algorithmSuite);
                algorithmSuiteValidator.checkSignatureAlgorithms(xmlSignature);
                if (samlKeyInfo.getCerts() != null && samlKeyInfo.getCerts().length > 0) {
                    algorithmSuiteValidator.checkAsymmetricKeyLength(samlKeyInfo.getCerts());
                } else {
                    algorithmSuiteValidator.checkAsymmetricKeyLength(key);
                }
            }
            samlAssertion.verifySignature(samlKeyInfo);
            return xmlSignature;
        }
        return null;
    }

    private List<WSDataRef> createDataRefs(Element token, SamlAssertionWrapper samlAssertion, XMLSignature xmlSignature) {
        if (xmlSignature == null) {
            return null;
        }
        ArrayList<WSDataRef> protectedRefs = new ArrayList<WSDataRef>();
        String signatureMethod = xmlSignature.getSignedInfo().getSignatureMethod().getAlgorithm();
        for (Reference refObject : xmlSignature.getSignedInfo().getReferences()) {
            Reference reference = refObject;
            if (!"".equals(reference.getURI()) && !reference.getURI().equals(samlAssertion.getId()) && !reference.getURI().equals("#" + samlAssertion.getId())) continue;
            WSDataRef ref = new WSDataRef();
            ref.setWsuId(reference.getURI());
            ref.setProtectedElement(token);
            ref.setAlgorithm(signatureMethod);
            ref.setDigestAlgorithm(reference.getDigestMethod().getAlgorithm());
            ref.setDigestValue(reference.getDigestValue());
            List<Transform> transforms = reference.getTransforms();
            ArrayList<String> transformAlgorithms = new ArrayList<String>(transforms.size());
            for (Transform transform : transforms) {
                transformAlgorithms.add(transform.getAlgorithm());
            }
            ref.setTransformAlgorithms(transformAlgorithms);
            ref.setXpath(EncryptionUtils.getXPath(token));
            protectedRefs.add(ref);
        }
        return protectedRefs;
    }
}

