/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl.processor.input;

import java.util.Deque;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.apache.wss4j.binding.wssc.AbstractSecurityContextTokenType;
import org.apache.wss4j.stax.ext.WSInboundSecurityContext;
import org.apache.wss4j.stax.ext.WSSConstants;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.wss4j.stax.securityEvent.SecurityContextTokenSecurityEvent;
import org.apache.wss4j.stax.validate.SecurityContextTokenValidator;
import org.apache.wss4j.stax.validate.SecurityContextTokenValidatorImpl;
import org.apache.wss4j.stax.validate.TokenContext;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.AbstractInputSecurityHeaderHandler;
import org.apache.xml.security.stax.ext.InputProcessorChain;
import org.apache.xml.security.stax.ext.XMLSecurityProperties;
import org.apache.xml.security.stax.ext.XMLSecurityUtils;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.apache.xml.security.stax.impl.util.IDGenerator;
import org.apache.xml.security.stax.securityToken.InboundSecurityToken;
import org.apache.xml.security.stax.securityToken.SecurityToken;
import org.apache.xml.security.stax.securityToken.SecurityTokenProvider;

public class SecurityContextTokenInputHandler
extends AbstractInputSecurityHeaderHandler {
    @Override
    public void handle(InputProcessorChain inputProcessorChain, XMLSecurityProperties securityProperties, Deque<XMLSecEvent> eventQueue, Integer index) throws XMLSecurityException {
        JAXBElement securityContextTokenTypeJAXBElement = (JAXBElement)this.parseStructure(eventQueue, index, securityProperties);
        final AbstractSecurityContextTokenType securityContextTokenType = (AbstractSecurityContextTokenType)securityContextTokenTypeJAXBElement.getValue();
        if (securityContextTokenType.getId() == null) {
            securityContextTokenType.setId(IDGenerator.generateID(null));
        }
        QName identifierElementName = new QName(securityContextTokenTypeJAXBElement.getName().getNamespaceURI(), WSSConstants.TAG_WSC0502_IDENTIFIER.getLocalPart());
        final String identifier = (String)XMLSecurityUtils.getQNameType(securityContextTokenType.getAny(), identifierElementName);
        WSInboundSecurityContext wsInboundSecurityContext = (WSInboundSecurityContext)inputProcessorChain.getSecurityContext();
        WSSSecurityProperties wssSecurityProperties = (WSSSecurityProperties)securityProperties;
        List<XMLSecEvent> xmlSecEvents = this.getResponsibleXMLSecEvents(eventQueue, index);
        List<QName> elementPath = this.getElementPath(eventQueue);
        TokenContext tokenContext = new TokenContext(wssSecurityProperties, wsInboundSecurityContext, xmlSecEvents, elementPath);
        QName elementName = securityContextTokenTypeJAXBElement.getName();
        SecurityContextTokenValidator securityContextTokenValidator = (SecurityContextTokenValidator)wssSecurityProperties.getValidator(elementName);
        if (securityContextTokenValidator == null) {
            securityContextTokenValidator = new SecurityContextTokenValidatorImpl();
        }
        final InboundSecurityToken securityContextToken = securityContextTokenValidator.validate(securityContextTokenType, identifier, tokenContext);
        SecurityTokenProvider<InboundSecurityToken> securityTokenProvider = new SecurityTokenProvider<InboundSecurityToken>(){

            @Override
            public InboundSecurityToken getSecurityToken() throws XMLSecurityException {
                return securityContextToken;
            }

            @Override
            public String getId() {
                return securityContextTokenType.getId();
            }
        };
        wsInboundSecurityContext.registerSecurityTokenProvider(securityContextTokenType.getId(), (SecurityTokenProvider<? extends InboundSecurityToken>)securityTokenProvider);
        SecurityTokenProvider<InboundSecurityToken> securityTokenProviderDirectReference = new SecurityTokenProvider<InboundSecurityToken>(){

            @Override
            public InboundSecurityToken getSecurityToken() throws XMLSecurityException {
                return securityContextToken;
            }

            @Override
            public String getId() {
                return identifier;
            }
        };
        wsInboundSecurityContext.registerSecurityTokenProvider(identifier, (SecurityTokenProvider<? extends InboundSecurityToken>)securityTokenProviderDirectReference);
        SecurityContextTokenSecurityEvent securityEvent = this.createTokenSecurityEvent(securityContextTokenType, securityTokenProvider);
        wsInboundSecurityContext.registerSecurityEvent(securityEvent);
    }

    private SecurityContextTokenSecurityEvent createTokenSecurityEvent(AbstractSecurityContextTokenType securityContextTokenType, SecurityTokenProvider<InboundSecurityToken> securityTokenProvider) throws XMLSecurityException {
        SecurityContextTokenSecurityEvent securityContextTokenSecurityEvent = new SecurityContextTokenSecurityEvent();
        securityContextTokenSecurityEvent.setSecurityToken((SecurityToken)securityTokenProvider.getSecurityToken());
        securityContextTokenSecurityEvent.setCorrelationID(securityContextTokenType.getId());
        return securityContextTokenSecurityEvent;
    }
}

