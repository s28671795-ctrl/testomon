/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl.processor.input;

import java.util.Deque;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.apache.wss4j.binding.wss10.BinarySecurityTokenType;
import org.apache.wss4j.common.bsp.BSPRule;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.stax.ext.WSInboundSecurityContext;
import org.apache.wss4j.stax.ext.WSSConstants;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.wss4j.stax.securityEvent.KerberosTokenSecurityEvent;
import org.apache.wss4j.stax.securityEvent.X509TokenSecurityEvent;
import org.apache.wss4j.stax.securityToken.KerberosServiceSecurityToken;
import org.apache.wss4j.stax.securityToken.X509SecurityToken;
import org.apache.wss4j.stax.validate.BinarySecurityTokenValidator;
import org.apache.wss4j.stax.validate.BinarySecurityTokenValidatorImpl;
import org.apache.wss4j.stax.validate.TokenContext;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.AbstractInputSecurityHeaderHandler;
import org.apache.xml.security.stax.ext.InputProcessorChain;
import org.apache.xml.security.stax.ext.XMLSecurityProperties;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.apache.xml.security.stax.impl.util.IDGenerator;
import org.apache.xml.security.stax.securityEvent.TokenSecurityEvent;
import org.apache.xml.security.stax.securityToken.InboundSecurityToken;
import org.apache.xml.security.stax.securityToken.SecurityTokenProvider;

public class BinarySecurityTokenInputHandler
extends AbstractInputSecurityHeaderHandler {
    @Override
    public void handle(InputProcessorChain inputProcessorChain, XMLSecurityProperties securityProperties, Deque<XMLSecEvent> eventQueue, Integer index) throws XMLSecurityException {
        TokenSecurityEvent tokenSecurityEvent;
        BinarySecurityTokenType binarySecurityTokenType = (BinarySecurityTokenType)((JAXBElement)this.parseStructure(eventQueue, index, securityProperties)).getValue();
        this.checkBSPCompliance(inputProcessorChain, binarySecurityTokenType);
        if (binarySecurityTokenType.getId() == null) {
            binarySecurityTokenType.setId(IDGenerator.generateID(null));
        }
        WSInboundSecurityContext wsInboundSecurityContext = (WSInboundSecurityContext)inputProcessorChain.getSecurityContext();
        WSSSecurityProperties wssSecurityProperties = (WSSSecurityProperties)securityProperties;
        List<QName> elementPath = this.getElementPath(eventQueue);
        List<XMLSecEvent> xmlSecEvents = this.getResponsibleXMLSecEvents(eventQueue, index);
        TokenContext tokenContext = new TokenContext(wssSecurityProperties, wsInboundSecurityContext, xmlSecEvents, elementPath);
        BinarySecurityTokenValidator binarySecurityTokenValidator = (BinarySecurityTokenValidator)wssSecurityProperties.getValidator(WSSConstants.TAG_WSSE_BINARY_SECURITY_TOKEN);
        if (binarySecurityTokenValidator == null) {
            binarySecurityTokenValidator = new BinarySecurityTokenValidatorImpl();
        }
        final InboundSecurityToken binarySecurityToken = binarySecurityTokenValidator.validate(binarySecurityTokenType, tokenContext);
        SecurityTokenProvider<InboundSecurityToken> securityTokenProvider = new SecurityTokenProvider<InboundSecurityToken>(){

            @Override
            public InboundSecurityToken getSecurityToken() throws XMLSecurityException {
                return binarySecurityToken;
            }

            @Override
            public String getId() {
                return binarySecurityToken.getId();
            }
        };
        wsInboundSecurityContext.registerSecurityTokenProvider(binarySecurityTokenType.getId(), (SecurityTokenProvider<? extends InboundSecurityToken>)securityTokenProvider);
        if (binarySecurityTokenType.getValueType().startsWith("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0")) {
            X509TokenSecurityEvent x509TokenSecurityEvent = new X509TokenSecurityEvent();
            x509TokenSecurityEvent.setSecurityToken((X509SecurityToken)((Object)binarySecurityToken));
            tokenSecurityEvent = x509TokenSecurityEvent;
        } else if (binarySecurityTokenType.getValueType().startsWith("http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#")) {
            KerberosTokenSecurityEvent kerberosTokenSecurityEvent = new KerberosTokenSecurityEvent();
            kerberosTokenSecurityEvent.setSecurityToken((KerberosServiceSecurityToken)((Object)binarySecurityToken));
            tokenSecurityEvent = kerberosTokenSecurityEvent;
        } else {
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY_TOKEN, "invalidValueType", new Object[]{binarySecurityTokenType.getValueType()});
        }
        tokenSecurityEvent.setCorrelationID(binarySecurityTokenType.getId());
        wsInboundSecurityContext.registerSecurityEvent(tokenSecurityEvent);
    }

    private void checkBSPCompliance(InputProcessorChain inputProcessorChain, BinarySecurityTokenType binarySecurityTokenType) throws WSSecurityException {
        WSInboundSecurityContext securityContext = (WSInboundSecurityContext)inputProcessorChain.getSecurityContext();
        if (binarySecurityTokenType.getEncodingType() == null) {
            securityContext.handleBSPRule(BSPRule.R3029);
        }
        if (!"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary".equals(binarySecurityTokenType.getEncodingType())) {
            securityContext.handleBSPRule(BSPRule.R3030);
        }
        if (binarySecurityTokenType.getValueType() == null) {
            securityContext.handleBSPRule(BSPRule.R3031);
        }
    }
}

