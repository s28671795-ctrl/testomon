/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl.processor.input;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import org.apache.wss4j.binding.wss10.KeyIdentifierType;
import org.apache.wss4j.binding.wss10.ReferenceType;
import org.apache.wss4j.binding.wss10.SecurityTokenReferenceType;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.stax.ext.WSInboundSecurityContext;
import org.apache.wss4j.stax.ext.WSSConstants;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.wss4j.stax.impl.securityToken.SecurityTokenReferenceImpl;
import org.apache.wss4j.stax.securityToken.WSSecurityTokenConstants;
import org.apache.wss4j.stax.utils.WSSUtils;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.AbstractInputProcessor;
import org.apache.xml.security.stax.ext.AbstractInputSecurityHeaderHandler;
import org.apache.xml.security.stax.ext.InputProcessorChain;
import org.apache.xml.security.stax.ext.XMLSecurityProperties;
import org.apache.xml.security.stax.ext.XMLSecurityUtils;
import org.apache.xml.security.stax.ext.stax.XMLSecEndElement;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.apache.xml.security.stax.ext.stax.XMLSecStartElement;
import org.apache.xml.security.stax.securityToken.InboundSecurityToken;
import org.apache.xml.security.stax.securityToken.SecurityTokenProvider;

public class SecurityTokenReferenceInputHandler
extends AbstractInputSecurityHeaderHandler {
    @Override
    public void handle(InputProcessorChain inputProcessorChain, XMLSecurityProperties securityProperties, Deque<XMLSecEvent> eventQueue, Integer index) throws XMLSecurityException {
        ReferenceType referenceType;
        SecurityTokenReferenceType securityTokenReferenceType = (SecurityTokenReferenceType)((JAXBElement)this.parseStructure(eventQueue, index, securityProperties)).getValue();
        QName attributeName = null;
        String attributeValue = null;
        KeyIdentifierType keyIdentifierType = (KeyIdentifierType)XMLSecurityUtils.getQNameType(securityTokenReferenceType.getAny(), WSSConstants.TAG_WSSE_KEY_IDENTIFIER);
        if (keyIdentifierType != null) {
            attributeValue = keyIdentifierType.getValue().trim();
            if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID".equals(keyIdentifierType.getValueType())) {
                attributeName = WSSConstants.ATT_NULL_ASSERTION_ID;
            } else if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID".equals(keyIdentifierType.getValueType())) {
                attributeName = WSSConstants.ATT_NULL_ID;
            }
        }
        if ((referenceType = (ReferenceType)XMLSecurityUtils.getQNameType(securityTokenReferenceType.getAny(), WSSConstants.TAG_WSSE_REFERENCE)) != null) {
            attributeValue = WSSUtils.dropReferenceMarker(referenceType.getURI());
            if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID".equals(referenceType.getValueType())) {
                attributeName = WSSConstants.ATT_NULL_ASSERTION_ID;
            } else if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID".equals(referenceType.getValueType())) {
                attributeName = WSSConstants.ATT_NULL_ID;
            }
        }
        if (attributeName == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.UNSUPPORTED_SECURITY_TOKEN);
        }
        InternalSecurityTokenReferenceInputProcessor internalSecurityTokenReferenceInputHandler = new InternalSecurityTokenReferenceInputProcessor(securityTokenReferenceType.getId(), attributeName, attributeValue, (WSSSecurityProperties)securityProperties);
        inputProcessorChain.addProcessor(internalSecurityTokenReferenceInputHandler);
    }

    static class InternalSecurityTokenReferenceInputProcessor
    extends AbstractInputProcessor {
        private final String securityTokenReferenceId;
        private final QName attribute;
        private final String attributeValue;
        private boolean refFound = false;
        private boolean end = false;
        private QName startElementName;
        private int startElementLevel;
        private final ArrayDeque<XMLSecEvent> xmlSecEventList = new ArrayDeque();

        InternalSecurityTokenReferenceInputProcessor(String securityTokenReferenceId, QName attribute, String attributeValue, WSSSecurityProperties securityProperties) {
            super(securityProperties);
            this.securityTokenReferenceId = securityTokenReferenceId;
            this.attribute = attribute;
            this.attributeValue = attributeValue;
        }

        @Override
        public XMLSecEvent processNextHeaderEvent(InputProcessorChain inputProcessorChain) throws XMLStreamException, XMLSecurityException {
            return inputProcessorChain.processHeaderEvent();
        }

        @Override
        public XMLSecEvent processNextEvent(final InputProcessorChain inputProcessorChain) throws XMLStreamException, XMLSecurityException {
            XMLSecEvent xmlSecEvent = inputProcessorChain.processEvent();
            switch (xmlSecEvent.getEventType()) {
                case 1: {
                    XMLSecStartElement xmlSecStartElement = xmlSecEvent.asStartElement();
                    Attribute attribute = xmlSecStartElement.getAttributeByName(this.attribute);
                    if (attribute == null || !this.attributeValue.equals(attribute.getValue())) break;
                    if (this.refFound) {
                        throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, "duplicateId");
                    }
                    this.refFound = true;
                    this.startElementName = xmlSecStartElement.getName();
                    List<QName> elementPath = xmlSecStartElement.getElementPath();
                    this.startElementLevel = elementPath.size();
                    break;
                }
                case 2: {
                    XMLSecEndElement xmlSecEndElement = xmlSecEvent.asEndElement();
                    if (xmlSecEndElement.getName().equals(this.startElementName) && xmlSecEndElement.getDocumentLevel() == this.startElementLevel) {
                        this.end = true;
                        this.xmlSecEventList.push(xmlSecEvent);
                        SecurityTokenProvider<InboundSecurityToken> securityTokenProvider = new SecurityTokenProvider<InboundSecurityToken>(){
                            private InboundSecurityToken securityToken;

                            @Override
                            public InboundSecurityToken getSecurityToken() throws XMLSecurityException {
                                if (this.securityToken != null) {
                                    return this.securityToken;
                                }
                                SecurityTokenProvider<? extends InboundSecurityToken> securityTokenProvider = inputProcessorChain.getSecurityContext().getSecurityTokenProvider(attributeValue);
                                InboundSecurityToken securityToken = securityTokenProvider.getSecurityToken();
                                this.securityToken = new SecurityTokenReferenceImpl(securityToken, xmlSecEventList, (WSInboundSecurityContext)inputProcessorChain.getSecurityContext(), securityTokenReferenceId, WSSecurityTokenConstants.KEYIDENTIFIER_SECURITY_TOKEN_DIRECT_REFERENCE);
                                return this.securityToken;
                            }

                            @Override
                            public String getId() {
                                return securityTokenReferenceId;
                            }
                        };
                        inputProcessorChain.getSecurityContext().registerSecurityTokenProvider(this.securityTokenReferenceId, (SecurityTokenProvider<? extends InboundSecurityToken>)securityTokenProvider);
                        return xmlSecEvent;
                    }
                    if (xmlSecEndElement.getDocumentLevel() != 3 || !xmlSecEndElement.getName().equals(WSSConstants.TAG_WSSE_SECURITY) || !WSSUtils.isInSecurityHeader(xmlSecEndElement, ((WSSSecurityProperties)this.getSecurityProperties()).getActor())) break;
                    inputProcessorChain.removeProcessor(this);
                }
            }
            if (this.refFound && !this.end) {
                this.xmlSecEventList.push(xmlSecEvent);
            }
            return xmlSecEvent;
        }
    }
}

