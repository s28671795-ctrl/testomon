/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.stax.enforcer;

import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import org.apache.wss4j.common.WSSPolicyException;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.policy.stax.enforcer.PolicyEnforcer;
import org.apache.wss4j.stax.ext.WSSConstants;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.wss4j.stax.impl.processor.input.SecurityHeaderInputProcessor;
import org.apache.wss4j.stax.securityEvent.EncryptedPartSecurityEvent;
import org.apache.wss4j.stax.securityEvent.RequiredElementSecurityEvent;
import org.apache.wss4j.stax.securityEvent.RequiredPartSecurityEvent;
import org.apache.wss4j.stax.securityEvent.SignedPartSecurityEvent;
import org.apache.wss4j.stax.utils.WSSUtils;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.AbstractInputProcessor;
import org.apache.xml.security.stax.ext.DocumentContext;
import org.apache.xml.security.stax.ext.InputProcessorChain;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.ext.XMLSecurityProperties;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.apache.xml.security.stax.ext.stax.XMLSecStartElement;
import org.apache.xml.security.stax.securityEvent.ContentEncryptedElementSecurityEvent;
import org.apache.xml.security.stax.securityEvent.EncryptedElementSecurityEvent;
import org.apache.xml.security.stax.securityEvent.SignedElementSecurityEvent;

public class PolicyInputProcessor
extends AbstractInputProcessor {
    private final PolicyEnforcer policyEnforcer;
    private boolean initDone = false;
    private boolean transportSecurityActive = false;

    public PolicyInputProcessor(PolicyEnforcer policyEnforcer, XMLSecurityProperties securityProperties) {
        super(securityProperties);
        this.setPhase(XMLSecurityConstants.Phase.POSTPROCESSING);
        this.addBeforeProcessor(SecurityHeaderInputProcessor.class.getName());
        this.policyEnforcer = policyEnforcer;
    }

    @Override
    public XMLSecEvent processNextHeaderEvent(InputProcessorChain inputProcessorChain) throws XMLStreamException, XMLSecurityException {
        XMLSecEvent xmlSecEvent = inputProcessorChain.processHeaderEvent();
        this.init(inputProcessorChain);
        List<QName> elementPath = null;
        if (!this.transportSecurityActive) {
            elementPath = xmlSecEvent.getElementPath();
            if (!inputProcessorChain.getDocumentContext().isInEncryptedContent() && WSSUtils.isInSecurityHeader(xmlSecEvent, elementPath, ((WSSSecurityProperties)this.getSecurityProperties()).getActor())) {
                this.testEncryptionPolicy(xmlSecEvent, elementPath);
            }
        }
        if (xmlSecEvent.getEventType() == 1) {
            int documentLevel;
            XMLSecStartElement xmlSecStartElement = xmlSecEvent.asStartElement();
            if (elementPath == null) {
                elementPath = xmlSecStartElement.getElementPath();
            }
            if ((documentLevel = elementPath.size()) == 3 && WSSUtils.isInSOAPHeader(elementPath)) {
                RequiredPartSecurityEvent requiredPartSecurityEvent = new RequiredPartSecurityEvent();
                requiredPartSecurityEvent.setElementPath(elementPath);
                this.policyEnforcer.registerSecurityEvent(requiredPartSecurityEvent);
                RequiredElementSecurityEvent requiredElementSecurityEvent = new RequiredElementSecurityEvent();
                requiredElementSecurityEvent.setElementPath(elementPath);
                this.policyEnforcer.registerSecurityEvent(requiredElementSecurityEvent);
            } else if (documentLevel > 3) {
                RequiredElementSecurityEvent requiredElementSecurityEvent = new RequiredElementSecurityEvent();
                requiredElementSecurityEvent.setElementPath(elementPath);
                this.policyEnforcer.registerSecurityEvent(requiredElementSecurityEvent);
            }
        }
        return xmlSecEvent;
    }

    @Override
    public XMLSecEvent processNextEvent(InputProcessorChain inputProcessorChain) throws XMLStreamException, XMLSecurityException {
        XMLSecStartElement xmlSecStartElement;
        int documentLevel;
        XMLSecEvent xmlSecEvent = inputProcessorChain.processEvent();
        List<QName> elementPath = null;
        if (1 == xmlSecEvent.getEventType() && (documentLevel = (xmlSecStartElement = xmlSecEvent.asStartElement()).getDocumentLevel()) > 3) {
            RequiredElementSecurityEvent requiredElementSecurityEvent = new RequiredElementSecurityEvent();
            elementPath = xmlSecStartElement.getElementPath();
            requiredElementSecurityEvent.setElementPath(elementPath);
            this.policyEnforcer.registerSecurityEvent(requiredElementSecurityEvent);
        }
        if (!this.transportSecurityActive) {
            DocumentContext documentContext = inputProcessorChain.getDocumentContext();
            boolean inEncryptedContent = documentContext.isInEncryptedContent();
            boolean inSignedContent = documentContext.isInSignedContent();
            if (!inEncryptedContent || !inSignedContent) {
                if (elementPath == null) {
                    elementPath = xmlSecEvent.getElementPath();
                }
                if (!inEncryptedContent && !WSSUtils.isInSecurityHeader(xmlSecEvent, elementPath, ((WSSSecurityProperties)this.getSecurityProperties()).getActor())) {
                    this.testEncryptionPolicy(xmlSecEvent, elementPath);
                }
                if (!inSignedContent) {
                    this.testSignaturePolicy(xmlSecEvent, elementPath);
                }
            }
        }
        return xmlSecEvent;
    }

    @Override
    public void doFinal(InputProcessorChain inputProcessorChain) throws XMLStreamException, XMLSecurityException {
        super.doFinal(inputProcessorChain);
        try {
            this.policyEnforcer.doFinal();
        }
        catch (WSSPolicyException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, (Exception)e);
        }
    }

    private void testSignaturePolicy(XMLSecEvent xmlSecEvent, List<QName> elementPath) throws WSSecurityException {
        if (xmlSecEvent.getEventType() == 1) {
            int documentLevel = elementPath.size();
            if (documentLevel == 3 && WSSUtils.isInSOAPHeader(elementPath)) {
                SignedPartSecurityEvent signedPartSecurityEvent = new SignedPartSecurityEvent(null, false, null);
                signedPartSecurityEvent.setElementPath(elementPath);
                this.policyEnforcer.registerSecurityEvent(signedPartSecurityEvent);
            } else if (documentLevel == 2 && WSSUtils.isInSOAPBody(elementPath)) {
                SignedPartSecurityEvent signedPartSecurityEvent = new SignedPartSecurityEvent(null, false, null);
                signedPartSecurityEvent.setElementPath(elementPath);
                this.policyEnforcer.registerSecurityEvent(signedPartSecurityEvent);
            } else if (documentLevel > 3) {
                SignedElementSecurityEvent signedElementSecurityEvent = new SignedElementSecurityEvent(null, false, null);
                signedElementSecurityEvent.setElementPath(elementPath);
                this.policyEnforcer.registerSecurityEvent(signedElementSecurityEvent);
            }
        }
    }

    private void testEncryptionPolicy(XMLSecEvent xmlSecEvent, List<QName> elementPath) throws WSSecurityException {
        switch (xmlSecEvent.getEventType()) {
            case 1: {
                int documentLevel = elementPath.size();
                if (documentLevel == 3 && WSSUtils.isInSOAPHeader(elementPath)) {
                    EncryptedPartSecurityEvent encryptedPartSecurityEvent = new EncryptedPartSecurityEvent(null, false, null);
                    encryptedPartSecurityEvent.setElementPath(elementPath);
                    this.policyEnforcer.registerSecurityEvent(encryptedPartSecurityEvent);
                    break;
                }
                if (documentLevel == 3 && WSSUtils.isInSOAPBody(elementPath)) {
                    EncryptedPartSecurityEvent encryptedPartSecurityEvent = new EncryptedPartSecurityEvent(null, false, null);
                    encryptedPartSecurityEvent.setElementPath(elementPath);
                    this.policyEnforcer.registerSecurityEvent(encryptedPartSecurityEvent);
                    break;
                }
                if (documentLevel <= 3) break;
                EncryptedElementSecurityEvent encryptedElementSecurityEvent = new EncryptedElementSecurityEvent(null, false, null);
                encryptedElementSecurityEvent.setCorrelationID(this.getId(xmlSecEvent));
                encryptedElementSecurityEvent.setElementPath(elementPath);
                this.policyEnforcer.registerSecurityEvent(encryptedElementSecurityEvent);
                ContentEncryptedElementSecurityEvent contentEncryptedElementSecurityEvent = new ContentEncryptedElementSecurityEvent(null, false, null);
                contentEncryptedElementSecurityEvent.setElementPath(xmlSecEvent.getParentXMLSecStartElement().getElementPath());
                this.policyEnforcer.registerSecurityEvent(contentEncryptedElementSecurityEvent);
                break;
            }
            case 3: 
            case 4: 
            case 9: {
                ContentEncryptedElementSecurityEvent contentEncryptedElementSecurityEvent = new ContentEncryptedElementSecurityEvent(null, false, null);
                contentEncryptedElementSecurityEvent.setElementPath(xmlSecEvent.getElementPath());
                this.policyEnforcer.registerSecurityEvent(contentEncryptedElementSecurityEvent);
            }
        }
    }

    protected String getId(XMLSecEvent xmlSecEvent) {
        XMLSecStartElement xmlSecStartElement = xmlSecEvent.asStartElement();
        if (!xmlSecStartElement.getOnElementDeclaredAttributes().isEmpty()) {
            Attribute attribute = xmlSecStartElement.getAttributeByName(WSSConstants.ATT_WSU_ID);
            if (attribute != null) {
                return attribute.getValue();
            }
            attribute = xmlSecStartElement.getAttributeByName(WSSConstants.ATT_NULL_Id);
            if (attribute != null) {
                return attribute.getValue();
            }
            attribute = xmlSecStartElement.getAttributeByName(WSSConstants.ATT_NULL_ID);
            if (attribute != null) {
                return attribute.getValue();
            }
            attribute = xmlSecStartElement.getAttributeByName(WSSConstants.ATT_NULL_ASSERTION_ID);
            if (attribute != null) {
                return attribute.getValue();
            }
        }
        return null;
    }

    protected void init(InputProcessorChain inputProcessorChain) {
        if (!this.initDone) {
            this.initDone = true;
            this.transportSecurityActive = Boolean.TRUE.equals(inputProcessorChain.getSecurityContext().get("transportSecurityActive"));
            inputProcessorChain.getSecurityContext().put("secureProcessing.AllowRSA15KeyTransportAlgorithm", Boolean.TRUE);
            inputProcessorChain.getSecurityContext().put("secureProcessing.AllowUsernameTokenNoPassword", Boolean.TRUE.toString());
        }
    }
}

