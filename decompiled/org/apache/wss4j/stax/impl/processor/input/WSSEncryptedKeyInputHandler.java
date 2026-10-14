/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl.processor.input;

import org.apache.wss4j.binding.wss10.ObjectFactory;
import org.apache.wss4j.binding.wss10.ReferenceType;
import org.apache.wss4j.binding.wss10.SecurityTokenReferenceType;
import org.apache.wss4j.common.bsp.BSPRule;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.util.AttachmentUtils;
import org.apache.wss4j.stax.ext.WSInboundSecurityContext;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.wss4j.stax.impl.processor.input.DecryptInputProcessor;
import org.apache.xml.security.binding.xmldsig.KeyInfoType;
import org.apache.xml.security.binding.xmlenc.EncryptedKeyType;
import org.apache.xml.security.binding.xmlenc.EncryptionMethodType;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.InputProcessorChain;
import org.apache.xml.security.stax.ext.XMLSecurityProperties;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.apache.xml.security.stax.impl.processor.input.XMLEncryptedKeyInputHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WSSEncryptedKeyInputHandler
extends XMLEncryptedKeyInputHandler {
    private static final transient Logger LOG = LoggerFactory.getLogger(WSSEncryptedKeyInputHandler.class);

    @Override
    public void handle(InputProcessorChain inputProcessorChain, EncryptedKeyType encryptedKeyType, XMLSecEvent responsibleXMLSecStartXMLEvent, XMLSecurityProperties securityProperties) throws XMLSecurityException {
        this.checkBSPCompliance(inputProcessorChain, encryptedKeyType);
        EncryptionMethodType encryptionMethodType = encryptedKeyType.getEncryptionMethod();
        if (securityProperties.getEncryptionKeyTransportAlgorithm() != null && encryptionMethodType != null) {
            String encryptionMethod = encryptionMethodType.getAlgorithm();
            if (!securityProperties.getEncryptionKeyTransportAlgorithm().equals(encryptionMethod)) {
                LOG.debug("The Key transport method does not match the requirement");
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY);
            }
        }
        super.handle(inputProcessorChain, encryptedKeyType, responsibleXMLSecStartXMLEvent, securityProperties);
    }

    @Override
    protected void handleReferenceList(InputProcessorChain inputProcessorChain, EncryptedKeyType encryptedKeyType, XMLSecurityProperties securityProperties) throws XMLSecurityException {
        KeyInfoType keyInfoType = new KeyInfoType();
        SecurityTokenReferenceType securityTokenReferenceType = new SecurityTokenReferenceType();
        ReferenceType referenceType = new ReferenceType();
        referenceType.setURI("#" + encryptedKeyType.getId());
        ObjectFactory objectFactory = new ObjectFactory();
        securityTokenReferenceType.getAny().add(objectFactory.createReference(referenceType));
        keyInfoType.getContent().add(objectFactory.createSecurityTokenReference(securityTokenReferenceType));
        inputProcessorChain.addProcessor(new DecryptInputProcessor(keyInfoType, encryptedKeyType.getReferenceList(), (WSSSecurityProperties)securityProperties, (WSInboundSecurityContext)inputProcessorChain.getSecurityContext()));
    }

    protected void checkBSPCompliance(InputProcessorChain inputProcessorChain, EncryptedKeyType encryptedKeyType) throws XMLSecurityException {
        EncryptionMethodType encryptionMethodType;
        WSInboundSecurityContext securityContext = (WSInboundSecurityContext)inputProcessorChain.getSecurityContext();
        if (encryptedKeyType.getType() != null) {
            securityContext.handleBSPRule(BSPRule.R3209);
        }
        if (encryptedKeyType.getMimeType() != null) {
            securityContext.handleBSPRule(BSPRule.R5622);
        }
        if (encryptedKeyType.getEncoding() != null) {
            securityContext.handleBSPRule(BSPRule.R5623);
        }
        if (encryptedKeyType.getRecipient() != null) {
            securityContext.handleBSPRule(BSPRule.R5602);
        }
        if ((encryptionMethodType = encryptedKeyType.getEncryptionMethod()) == null) {
            securityContext.handleBSPRule(BSPRule.R5603);
        } else {
            String encryptionMethod = encryptionMethodType.getAlgorithm();
            if (!("http://www.w3.org/2001/04/xmlenc#rsa-1_5".equals(encryptionMethod) || "http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p".equals(encryptionMethod) || "http://www.w3.org/2009/xmlenc11#rsa-oaep".equals(encryptionMethod))) {
                securityContext.handleBSPRule(BSPRule.R5621);
            }
        }
    }

    @Override
    protected byte[] getBytesFromAttachment(String xopUri, XMLSecurityProperties securityProperties) throws XMLSecurityException {
        WSSSecurityProperties securityProps = (WSSSecurityProperties)securityProperties;
        return AttachmentUtils.getBytesFromAttachment(xopUri, securityProps.getAttachmentCallbackHandler(), true);
    }
}

