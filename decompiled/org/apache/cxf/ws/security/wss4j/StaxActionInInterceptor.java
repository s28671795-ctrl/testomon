/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j;

import java.util.List;
import java.util.logging.Logger;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.ws.security.wss4j.StaxSecurityContextInInterceptor;
import org.apache.cxf.ws.security.wss4j.WSS4JUtils;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.stax.ext.WSSConstants;
import org.apache.wss4j.stax.securityEvent.OperationSecurityEvent;
import org.apache.wss4j.stax.securityEvent.WSSecurityEventConstants;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.securityEvent.SecurityEvent;
import org.apache.xml.security.stax.securityEvent.SecurityEventConstants;

public class StaxActionInInterceptor
extends AbstractPhaseInterceptor<SoapMessage> {
    private static final Logger LOG = LogUtils.getL7dLogger(StaxActionInInterceptor.class);
    private final List<XMLSecurityConstants.Action> inActions;

    public StaxActionInInterceptor(List<XMLSecurityConstants.Action> inActions) {
        super("pre-protocol");
        this.inActions = inActions;
        this.getBefore().add(StaxSecurityContextInInterceptor.class.getName());
    }

    @Override
    public void handleMessage(SoapMessage soapMessage) throws Fault {
        OperationSecurityEvent securityEvent;
        if (this.inActions == null || this.inActions.isEmpty()) {
            return;
        }
        List incomingSecurityEventList = (List)soapMessage.get(SecurityEvent.class.getName() + ".in");
        if (incomingSecurityEventList == null) {
            LOG.warning("Security processing failed (actions mismatch)");
            WSSecurityException ex = new WSSecurityException(WSSecurityException.ErrorCode.SECURITY_ERROR);
            throw WSS4JUtils.createSoapFault(soapMessage, soapMessage.getVersion(), ex);
        }
        if (MessageUtils.isRequestor(soapMessage) && this.isEventInResults(WSSecurityEventConstants.NO_SECURITY, incomingSecurityEventList) && (securityEvent = (OperationSecurityEvent)this.findEvent(WSSecurityEventConstants.OPERATION, incomingSecurityEventList)) != null && soapMessage.getVersion().getFault().equals(securityEvent.getOperation())) {
            LOG.warning("Request does not contain Security header, but it's a fault.");
            return;
        }
        for (XMLSecurityConstants.Action action : this.inActions) {
            SecurityEventConstants.Event requiredEvent = null;
            if (WSSConstants.TIMESTAMP.equals(action)) {
                requiredEvent = WSSecurityEventConstants.TIMESTAMP;
            } else if (WSSConstants.USERNAMETOKEN.equals(action)) {
                requiredEvent = WSSecurityEventConstants.USERNAME_TOKEN;
            } else if (XMLSecurityConstants.SIGNATURE.equals(action)) {
                requiredEvent = WSSecurityEventConstants.SignatureValue;
            } else if (WSSConstants.SAML_TOKEN_SIGNED.equals(action) || WSSConstants.SAML_TOKEN_UNSIGNED.equals(action)) {
                requiredEvent = WSSecurityEventConstants.SAML_TOKEN;
            }
            if (requiredEvent != null && !this.isEventInResults(requiredEvent, incomingSecurityEventList)) {
                LOG.warning("Security processing failed (actions mismatch)");
                WSSecurityException ex = new WSSecurityException(WSSecurityException.ErrorCode.SECURITY_ERROR);
                throw WSS4JUtils.createSoapFault(soapMessage, soapMessage.getVersion(), ex);
            }
            if (!XMLSecurityConstants.ENCRYPT.equals(action)) continue;
            boolean foundEncryptionPart = this.isEventInResults(WSSecurityEventConstants.ENCRYPTED_PART, incomingSecurityEventList);
            if (!foundEncryptionPart) {
                foundEncryptionPart = this.isEventInResults(WSSecurityEventConstants.EncryptedElement, incomingSecurityEventList);
            }
            if (foundEncryptionPart) continue;
            LOG.warning("Security processing failed (actions mismatch)");
            WSSecurityException ex = new WSSecurityException(WSSecurityException.ErrorCode.SECURITY_ERROR);
            throw WSS4JUtils.createSoapFault(soapMessage, soapMessage.getVersion(), ex);
        }
    }

    private boolean isEventInResults(SecurityEventConstants.Event event, List<SecurityEvent> incomingSecurityEventList) {
        for (SecurityEvent incomingEvent : incomingSecurityEventList) {
            if (event != incomingEvent.getSecurityEventType()) continue;
            return true;
        }
        return false;
    }

    private SecurityEvent findEvent(SecurityEventConstants.Event event, List<SecurityEvent> incomingSecurityEventList) {
        for (SecurityEvent incomingEvent : incomingSecurityEventList) {
            if (event != incomingEvent.getSecurityEventType()) continue;
            return incomingEvent;
        }
        return null;
    }
}

