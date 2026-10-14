/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl.processor.input;

import java.util.Arrays;
import java.util.List;
import javax.xml.stream.XMLStreamException;
import org.apache.wss4j.binding.wss11.SignatureConfirmationType;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.stax.ext.WSSConstants;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.AbstractInputProcessor;
import org.apache.xml.security.stax.ext.InputProcessorChain;
import org.apache.xml.security.stax.ext.stax.XMLSecEndElement;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.apache.xml.security.stax.securityEvent.SecurityEvent;
import org.apache.xml.security.stax.securityEvent.SignatureValueSecurityEvent;

public class SignatureConfirmationInputProcessor
extends AbstractInputProcessor {
    public SignatureConfirmationInputProcessor(WSSSecurityProperties securityProperties) {
        super(securityProperties);
    }

    @Override
    public XMLSecEvent processNextHeaderEvent(InputProcessorChain inputProcessorChain) throws XMLStreamException, XMLSecurityException {
        XMLSecEndElement xmlSecEndElement;
        XMLSecEvent xmlSecEvent = inputProcessorChain.processHeaderEvent();
        if (xmlSecEvent.getEventType() == 2 && (xmlSecEndElement = xmlSecEvent.asEndElement()).getName().equals(WSSConstants.TAG_WSSE_SECURITY)) {
            inputProcessorChain.removeProcessor(this);
            List signatureValueSecurityEventList = inputProcessorChain.getSecurityContext().getAsList(SecurityEvent.class);
            List signatureConfirmationTypeList = inputProcessorChain.getSecurityContext().getAsList(SignatureConfirmationType.class);
            if (signatureValueSecurityEventList == null || signatureValueSecurityEventList.isEmpty()) {
                if (signatureConfirmationTypeList == null || signatureConfirmationTypeList.size() != 1) {
                    throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY);
                }
                if (((SignatureConfirmationType)signatureConfirmationTypeList.get(0)).getValue() != null) {
                    throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY);
                }
            }
            if (signatureConfirmationTypeList == null) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY);
            }
            for (int i = 0; i < signatureValueSecurityEventList.size(); ++i) {
                SignatureValueSecurityEvent signatureValueSecurityEvent = (SignatureValueSecurityEvent)signatureValueSecurityEventList.get(i);
                byte[] signatureValue = signatureValueSecurityEvent.getSignatureValue();
                boolean found = false;
                for (int j = 0; j < signatureConfirmationTypeList.size(); ++j) {
                    SignatureConfirmationType signatureConfirmationType = (SignatureConfirmationType)signatureConfirmationTypeList.get(j);
                    byte[] sigConfValue = signatureConfirmationType.getValue();
                    if (!Arrays.equals(signatureValue, sigConfValue)) continue;
                    found = true;
                }
                if (found) continue;
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY);
            }
        }
        return xmlSecEvent;
    }

    @Override
    public XMLSecEvent processNextEvent(InputProcessorChain inputProcessorChain) throws XMLStreamException, XMLSecurityException {
        return null;
    }
}

