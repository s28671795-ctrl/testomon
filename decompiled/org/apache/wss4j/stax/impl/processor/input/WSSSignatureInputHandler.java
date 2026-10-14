/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl.processor.input;

import java.math.BigInteger;
import java.util.List;
import org.apache.wss4j.binding.wss10.SecurityTokenReferenceType;
import org.apache.wss4j.common.bsp.BSPRule;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.stax.ext.WSInboundSecurityContext;
import org.apache.wss4j.stax.ext.WSSConstants;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.wss4j.stax.impl.processor.input.WSSSignatureReferenceVerifyInputProcessor;
import org.apache.wss4j.stax.securityToken.WSSecurityTokenConstants;
import org.apache.wss4j.stax.utils.WSSUtils;
import org.apache.wss4j.stax.validate.SignatureTokenValidator;
import org.apache.wss4j.stax.validate.SignatureTokenValidatorImpl;
import org.apache.xml.security.binding.excc14n.InclusiveNamespaces;
import org.apache.xml.security.binding.xmldsig.CanonicalizationMethodType;
import org.apache.xml.security.binding.xmldsig.ManifestType;
import org.apache.xml.security.binding.xmldsig.ObjectType;
import org.apache.xml.security.binding.xmldsig.SignatureType;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.InboundSecurityContext;
import org.apache.xml.security.stax.ext.InputProcessorChain;
import org.apache.xml.security.stax.ext.XMLSecurityProperties;
import org.apache.xml.security.stax.ext.XMLSecurityUtils;
import org.apache.xml.security.stax.impl.processor.input.AbstractSignatureInputHandler;
import org.apache.xml.security.stax.securityEvent.AlgorithmSuiteSecurityEvent;
import org.apache.xml.security.stax.securityEvent.SignatureValueSecurityEvent;
import org.apache.xml.security.stax.securityEvent.TokenSecurityEvent;
import org.apache.xml.security.stax.securityToken.InboundSecurityToken;
import org.apache.xml.security.stax.securityToken.SecurityTokenFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WSSSignatureInputHandler
extends AbstractSignatureInputHandler {
    private static final transient Logger LOG = LoggerFactory.getLogger(WSSSignatureInputHandler.class);

    @Override
    protected AbstractSignatureInputHandler.SignatureVerifier newSignatureVerifier(InputProcessorChain inputProcessorChain, XMLSecurityProperties securityProperties, SignatureType signatureType) throws XMLSecurityException {
        if (signatureType.getKeyInfo() == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY);
        }
        this.checkBSPCompliance(inputProcessorChain, signatureType);
        String algorithm = signatureType.getSignedInfo().getSignatureMethod().getAlgorithm();
        if (securityProperties.getSignatureAlgorithm() != null && !securityProperties.getSignatureAlgorithm().equals(algorithm)) {
            LOG.debug("The Signature method does not match the requirement");
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY);
        }
        WSInboundSecurityContext securityContext = (WSInboundSecurityContext)inputProcessorChain.getSecurityContext();
        SignatureValueSecurityEvent signatureValueSecurityEvent = new SignatureValueSecurityEvent();
        signatureValueSecurityEvent.setSignatureValue(signatureType.getSignatureValue().getValue());
        signatureValueSecurityEvent.setCorrelationID(signatureType.getId());
        securityContext.registerSecurityEvent(signatureValueSecurityEvent);
        AlgorithmSuiteSecurityEvent algorithmSuiteSecurityEvent = new AlgorithmSuiteSecurityEvent();
        algorithmSuiteSecurityEvent.setAlgorithmURI(signatureType.getSignedInfo().getCanonicalizationMethod().getAlgorithm());
        algorithmSuiteSecurityEvent.setAlgorithmUsage(WSSConstants.SigC14n);
        algorithmSuiteSecurityEvent.setCorrelationID(signatureType.getId());
        securityContext.registerSecurityEvent(algorithmSuiteSecurityEvent);
        return new WSSSignatureVerifier(signatureType, inputProcessorChain.getSecurityContext(), securityProperties);
    }

    private void checkBSPCompliance(InputProcessorChain inputProcessorChain, SignatureType signatureType) throws WSSecurityException {
        InclusiveNamespaces inclusiveNamespacesType;
        SecurityTokenReferenceType securityTokenReferenceType;
        List<Object> keyInfoContent;
        BigInteger hmacOutputLength;
        String algorithm = signatureType.getSignedInfo().getSignatureMethod().getAlgorithm();
        WSInboundSecurityContext securityContext = (WSInboundSecurityContext)inputProcessorChain.getSecurityContext();
        if (!("http://www.w3.org/2000/09/xmldsig#hmac-sha1".equals(algorithm) || "http://www.w3.org/2000/09/xmldsig#rsa-sha1".equals(algorithm) || "http://www.w3.org/2001/04/xmldsig-more#hmac-sha256".equals(algorithm) || "http://www.w3.org/2001/04/xmldsig-more#hmac-sha384".equals(algorithm) || "http://www.w3.org/2001/04/xmldsig-more#hmac-sha512".equals(algorithm) || "http://www.w3.org/2001/04/xmldsig-more#rsa-sha256".equals(algorithm) || "http://www.w3.org/2001/04/xmldsig-more#rsa-sha384".equals(algorithm) || "http://www.w3.org/2001/04/xmldsig-more#rsa-sha512".equals(algorithm))) {
            securityContext.handleBSPRule(BSPRule.R5421);
        }
        if ((hmacOutputLength = (BigInteger)XMLSecurityUtils.getQNameType(signatureType.getSignedInfo().getSignatureMethod().getContent(), WSSConstants.TAG_dsig_HMACOutputLength)) != null) {
            securityContext.handleBSPRule(BSPRule.R5401);
        }
        if ((keyInfoContent = signatureType.getKeyInfo().getContent()).size() != 1) {
            securityContext.handleBSPRule(BSPRule.R5402);
        }
        if ((securityTokenReferenceType = (SecurityTokenReferenceType)XMLSecurityUtils.getQNameType(keyInfoContent, WSSConstants.TAG_WSSE_SECURITY_TOKEN_REFERENCE)) == null) {
            securityContext.handleBSPRule(BSPRule.R5417);
        }
        for (ObjectType objectType : signatureType.getObject()) {
            ManifestType manifestType = (ManifestType)XMLSecurityUtils.getQNameType(objectType.getContent(), WSSConstants.TAG_dsig_Manifest);
            if (manifestType == null) continue;
            securityContext.handleBSPRule(BSPRule.R5403);
        }
        CanonicalizationMethodType canonicalizationMethodType = signatureType.getSignedInfo().getCanonicalizationMethod();
        if (!"http://www.w3.org/2001/10/xml-exc-c14n#".equals(canonicalizationMethodType.getAlgorithm())) {
            securityContext.handleBSPRule(BSPRule.R5404);
        }
        if ((inclusiveNamespacesType = (InclusiveNamespaces)XMLSecurityUtils.getQNameType(canonicalizationMethodType.getContent(), WSSConstants.TAG_c14nExcl_InclusiveNamespaces)) != null && inclusiveNamespacesType.getPrefixList().isEmpty()) {
            securityContext.handleBSPRule(BSPRule.R5406);
        }
    }

    @Override
    protected void addSignatureReferenceInputProcessorToChain(InputProcessorChain inputProcessorChain, XMLSecurityProperties securityProperties, SignatureType signatureType, InboundSecurityToken inboundSecurityToken) throws XMLSecurityException {
        inputProcessorChain.addProcessor(new WSSSignatureReferenceVerifyInputProcessor(inputProcessorChain, signatureType, inboundSecurityToken, securityProperties));
    }

    public class WSSSignatureVerifier
    extends AbstractSignatureInputHandler.SignatureVerifier {
        public WSSSignatureVerifier(SignatureType signatureType, InboundSecurityContext inboundSecurityContext, XMLSecurityProperties securityProperties) throws XMLSecurityException {
            super(signatureType, inboundSecurityContext, securityProperties);
        }

        @Override
        protected InboundSecurityToken retrieveSecurityToken(SignatureType signatureType, XMLSecurityProperties securityProperties, InboundSecurityContext inboundSecurityContext) throws XMLSecurityException {
            InboundSecurityToken inboundSecurityToken = SecurityTokenFactory.getInstance().getSecurityToken(signatureType.getKeyInfo(), WSSecurityTokenConstants.KeyUsage_Signature_Verification, securityProperties, inboundSecurityContext);
            SignatureTokenValidator signatureTokenValidator = (SignatureTokenValidator)((WSSSecurityProperties)securityProperties).getValidator(WSSConstants.TAG_dsig_Signature);
            if (signatureTokenValidator == null) {
                signatureTokenValidator = new SignatureTokenValidatorImpl();
            }
            signatureTokenValidator.validate(inboundSecurityToken, (WSSSecurityProperties)securityProperties);
            inboundSecurityToken.addTokenUsage(WSSecurityTokenConstants.TokenUsage_Signature);
            TokenSecurityEvent<? extends InboundSecurityToken> tokenSecurityEvent = WSSUtils.createTokenSecurityEvent(inboundSecurityToken, signatureType.getId());
            inboundSecurityContext.registerSecurityEvent(tokenSecurityEvent);
            return inboundSecurityToken;
        }
    }
}

