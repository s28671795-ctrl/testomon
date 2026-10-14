/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl.processor.input;

import java.util.Deque;
import javax.xml.bind.JAXBElement;
import org.apache.wss4j.binding.wss11.SignatureConfirmationType;
import org.apache.wss4j.common.bsp.BSPRule;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.stax.ext.WSInboundSecurityContext;
import org.apache.wss4j.stax.securityEvent.SignatureConfirmationSecurityEvent;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.AbstractInputSecurityHeaderHandler;
import org.apache.xml.security.stax.ext.InputProcessorChain;
import org.apache.xml.security.stax.ext.XMLSecurityProperties;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;

public class SignatureConfirmationInputHandler
extends AbstractInputSecurityHeaderHandler {
    @Override
    public void handle(InputProcessorChain inputProcessorChain, XMLSecurityProperties securityProperties, Deque<XMLSecEvent> eventQueue, Integer index) throws XMLSecurityException {
        SignatureConfirmationType signatureConfirmationType = (SignatureConfirmationType)((JAXBElement)this.parseStructure(eventQueue, index, securityProperties)).getValue();
        this.checkBSPCompliance(inputProcessorChain, signatureConfirmationType);
        inputProcessorChain.getSecurityContext().putAsList(SignatureConfirmationType.class, signatureConfirmationType);
        SignatureConfirmationSecurityEvent signatureConfirmationSecurityEvent = new SignatureConfirmationSecurityEvent();
        signatureConfirmationSecurityEvent.setSignatureValue(signatureConfirmationType.getValue());
        inputProcessorChain.getSecurityContext().registerSecurityEvent(signatureConfirmationSecurityEvent);
    }

    private void checkBSPCompliance(InputProcessorChain inputProcessorChain, SignatureConfirmationType signatureConfirmationType) throws WSSecurityException {
        if (signatureConfirmationType.getId() == null) {
            ((WSInboundSecurityContext)inputProcessorChain.getSecurityContext()).handleBSPRule(BSPRule.R5441);
        }
    }
}

