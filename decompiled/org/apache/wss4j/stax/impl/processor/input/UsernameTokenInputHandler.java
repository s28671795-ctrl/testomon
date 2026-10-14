/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl.processor.input;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.Deque;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.apache.wss4j.binding.wss10.EncodedString;
import org.apache.wss4j.binding.wss10.PasswordString;
import org.apache.wss4j.binding.wss10.UsernameTokenType;
import org.apache.wss4j.binding.wsu10.AttributedDateTime;
import org.apache.wss4j.common.bsp.BSPRule;
import org.apache.wss4j.common.cache.ReplayCache;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.util.DateUtil;
import org.apache.wss4j.stax.ext.WSInboundSecurityContext;
import org.apache.wss4j.stax.ext.WSSConstants;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.wss4j.stax.securityEvent.UsernameTokenSecurityEvent;
import org.apache.wss4j.stax.securityToken.UsernameSecurityToken;
import org.apache.wss4j.stax.validate.TokenContext;
import org.apache.wss4j.stax.validate.UsernameTokenValidator;
import org.apache.wss4j.stax.validate.UsernameTokenValidatorImpl;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.AbstractInputSecurityHeaderHandler;
import org.apache.xml.security.stax.ext.InputProcessorChain;
import org.apache.xml.security.stax.ext.XMLSecurityProperties;
import org.apache.xml.security.stax.ext.XMLSecurityUtils;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.apache.xml.security.stax.impl.util.IDGenerator;
import org.apache.xml.security.stax.securityToken.InboundSecurityToken;
import org.apache.xml.security.stax.securityToken.SecurityTokenProvider;

public class UsernameTokenInputHandler
extends AbstractInputSecurityHeaderHandler {
    @Override
    public void handle(InputProcessorChain inputProcessorChain, XMLSecurityProperties securityProperties, Deque<XMLSecEvent> eventQueue, Integer index) throws XMLSecurityException {
        final UsernameTokenType usernameTokenType = (UsernameTokenType)((JAXBElement)this.parseStructure(eventQueue, index, securityProperties)).getValue();
        List<XMLSecEvent> xmlSecEvents = this.getResponsibleXMLSecEvents(eventQueue, index);
        this.checkBSPCompliance(inputProcessorChain, usernameTokenType, xmlSecEvents);
        if (usernameTokenType.getId() == null) {
            usernameTokenType.setId(IDGenerator.generateID(null));
        }
        WSSSecurityProperties wssSecurityProperties = (WSSSecurityProperties)securityProperties;
        Instant created = this.verifyCreated(wssSecurityProperties, usernameTokenType);
        ReplayCache replayCache = wssSecurityProperties.getNonceReplayCache();
        EncodedString encodedNonce = (EncodedString)XMLSecurityUtils.getQNameType(usernameTokenType.getAny(), WSSConstants.TAG_WSSE_NONCE);
        if (encodedNonce != null && replayCache != null) {
            String nonce = encodedNonce.getValue();
            if (replayCache.contains(nonce)) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_AUTHENTICATION);
            }
            int utTTL = wssSecurityProperties.getUtTTL();
            if (created == null || utTTL <= 0) {
                replayCache.add(nonce);
            } else {
                replayCache.add(nonce, (long)utTTL + 1L);
            }
        }
        WSInboundSecurityContext wsInboundSecurityContext = (WSInboundSecurityContext)inputProcessorChain.getSecurityContext();
        List<QName> elementPath = this.getElementPath(eventQueue);
        TokenContext tokenContext = new TokenContext(wssSecurityProperties, wsInboundSecurityContext, xmlSecEvents, elementPath);
        UsernameTokenValidator usernameTokenValidator = (UsernameTokenValidator)wssSecurityProperties.getValidator(WSSConstants.TAG_WSSE_USERNAME_TOKEN);
        if (usernameTokenValidator == null) {
            usernameTokenValidator = new UsernameTokenValidatorImpl();
        }
        final Object usernameSecurityToken = usernameTokenValidator.validate(usernameTokenType, tokenContext);
        SecurityTokenProvider<InboundSecurityToken> securityTokenProvider = new SecurityTokenProvider<InboundSecurityToken>(){

            @Override
            public InboundSecurityToken getSecurityToken() throws XMLSecurityException {
                return (InboundSecurityToken)((Object)usernameSecurityToken);
            }

            @Override
            public String getId() {
                return usernameTokenType.getId();
            }
        };
        inputProcessorChain.getSecurityContext().registerSecurityTokenProvider(usernameTokenType.getId(), (SecurityTokenProvider<? extends InboundSecurityToken>)securityTokenProvider);
        UsernameTokenSecurityEvent usernameTokenSecurityEvent = new UsernameTokenSecurityEvent();
        usernameTokenSecurityEvent.setSecurityToken((UsernameSecurityToken)securityTokenProvider.getSecurityToken());
        usernameTokenSecurityEvent.setCorrelationID(usernameTokenType.getId());
        inputProcessorChain.getSecurityContext().registerSecurityEvent(usernameTokenSecurityEvent);
    }

    private void checkBSPCompliance(InputProcessorChain inputProcessorChain, UsernameTokenType usernameTokenType, List<XMLSecEvent> xmlSecEvents) throws WSSecurityException {
        EncodedString encodedNonce;
        WSInboundSecurityContext securityContext = (WSInboundSecurityContext)inputProcessorChain.getSecurityContext();
        if (usernameTokenType.getAny() == null) {
            securityContext.handleBSPRule(BSPRule.R3031);
        }
        int passwordIndex = -1;
        int createdIndex = -1;
        int nonceIndex = -1;
        for (int i = 0; i < xmlSecEvents.size(); ++i) {
            XMLSecEvent xmlSecEvent = xmlSecEvents.get(i);
            if (xmlSecEvent.getEventType() != 1 || xmlSecEvent.asStartElement().getName().equals(WSSConstants.TAG_WSSE_USERNAME_TOKEN)) continue;
            if (xmlSecEvent.asStartElement().getName().equals(WSSConstants.TAG_WSSE_PASSWORD)) {
                if (passwordIndex != -1) {
                    securityContext.handleBSPRule(BSPRule.R4222);
                }
                passwordIndex = i;
                continue;
            }
            if (xmlSecEvent.asStartElement().getName().equals(WSSConstants.TAG_WSU_CREATED)) {
                if (createdIndex != -1) {
                    securityContext.handleBSPRule(BSPRule.R4223);
                }
                createdIndex = i;
                continue;
            }
            if (!xmlSecEvent.asStartElement().getName().equals(WSSConstants.TAG_WSSE_NONCE)) continue;
            if (nonceIndex != -1) {
                securityContext.handleBSPRule(BSPRule.R4225);
            }
            nonceIndex = i;
        }
        PasswordString passwordType = (PasswordString)XMLSecurityUtils.getQNameType(usernameTokenType.getAny(), WSSConstants.TAG_WSSE_PASSWORD);
        if (passwordType != null && passwordType.getType() == null) {
            securityContext.handleBSPRule(BSPRule.R4201);
        }
        if ((encodedNonce = (EncodedString)XMLSecurityUtils.getQNameType(usernameTokenType.getAny(), WSSConstants.TAG_WSSE_NONCE)) != null) {
            if (encodedNonce.getEncodingType() == null) {
                securityContext.handleBSPRule(BSPRule.R4220);
            } else if (!"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary".equals(encodedNonce.getEncodingType())) {
                securityContext.handleBSPRule(BSPRule.R4221);
            }
        }
    }

    private Instant verifyCreated(WSSSecurityProperties wssSecurityProperties, UsernameTokenType usernameTokenType) throws WSSecurityException {
        int ttl = wssSecurityProperties.getUtTTL();
        int futureTTL = wssSecurityProperties.getUtFutureTTL();
        AttributedDateTime attributedDateTimeCreated = (AttributedDateTime)XMLSecurityUtils.getQNameType(usernameTokenType.getAny(), WSSConstants.TAG_WSU_CREATED);
        if (attributedDateTimeCreated != null) {
            ZonedDateTime created;
            try {
                created = ZonedDateTime.parse(attributedDateTimeCreated.getValue());
            }
            catch (DateTimeParseException e) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, (Exception)e);
            }
            if (!DateUtil.verifyCreated(created.toInstant(), ttl, futureTTL)) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.MESSAGE_EXPIRED);
            }
            return created.toInstant();
        }
        return null;
    }
}

