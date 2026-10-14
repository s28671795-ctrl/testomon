/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import javax.xml.namespace.QName;
import org.apache.wss4j.common.bsp.BSPRule;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.stax.ext.WSInboundSecurityContext;
import org.apache.wss4j.stax.ext.WSSConstants;
import org.apache.wss4j.stax.securityEvent.HttpsTokenSecurityEvent;
import org.apache.wss4j.stax.securityEvent.WSSecurityEventConstants;
import org.apache.wss4j.stax.securityToken.HttpsSecurityToken;
import org.apache.wss4j.stax.securityToken.WSSecurityTokenConstants;
import org.apache.wss4j.stax.utils.WSSUtils;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.impl.InboundSecurityContextImpl;
import org.apache.xml.security.stax.securityEvent.AlgorithmSuiteSecurityEvent;
import org.apache.xml.security.stax.securityEvent.ContentEncryptedElementSecurityEvent;
import org.apache.xml.security.stax.securityEvent.EncryptedElementSecurityEvent;
import org.apache.xml.security.stax.securityEvent.SecurityEvent;
import org.apache.xml.security.stax.securityEvent.SecurityEventConstants;
import org.apache.xml.security.stax.securityEvent.SignedElementSecurityEvent;
import org.apache.xml.security.stax.securityEvent.TokenSecurityEvent;
import org.apache.xml.security.stax.securityToken.InboundSecurityToken;
import org.apache.xml.security.stax.securityToken.SecurityToken;
import org.apache.xml.security.stax.securityToken.SecurityTokenConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InboundWSSecurityContextImpl
extends InboundSecurityContextImpl
implements WSInboundSecurityContext {
    private static final Logger LOG = LoggerFactory.getLogger(InboundWSSecurityContextImpl.class);
    private final Deque<SecurityEvent> securityEventQueue = new ArrayDeque<SecurityEvent>();
    private boolean operationSecurityEventOccured = false;
    private boolean messageEncryptionTokenOccured = false;
    private boolean allowRSA15KeyTransportAlgorithm = false;
    private boolean disableBSPEnforcement;
    private List<BSPRule> ignoredBSPRules = Collections.emptyList();

    @Override
    public synchronized void registerSecurityEvent(SecurityEvent securityEvent) throws XMLSecurityException {
        if (WSSecurityEventConstants.AlgorithmSuite.equals(securityEvent.getSecurityEventType())) {
            this.forwardSecurityEvent(securityEvent);
            return;
        }
        if (this.operationSecurityEventOccured) {
            TokenSecurityEvent tokenSecurityEvent;
            if (!this.messageEncryptionTokenOccured && securityEvent instanceof TokenSecurityEvent && ((InboundSecurityToken)(tokenSecurityEvent = (TokenSecurityEvent)securityEvent).getSecurityToken()).getTokenUsages().contains(WSSecurityTokenConstants.TokenUsage_Encryption)) {
                InboundSecurityToken securityToken = WSSUtils.getRootToken((InboundSecurityToken)tokenSecurityEvent.getSecurityToken());
                TokenSecurityEvent<? extends InboundSecurityToken> newTokenSecurityEvent = WSSUtils.createTokenSecurityEvent(securityToken, tokenSecurityEvent.getCorrelationID());
                this.setTokenUsage(newTokenSecurityEvent, WSSecurityTokenConstants.TOKENUSAGE_MAIN_ENCRYPTION);
                securityEvent = newTokenSecurityEvent;
                this.messageEncryptionTokenOccured = true;
            }
            this.forwardSecurityEvent(securityEvent);
            return;
        }
        if (WSSecurityEventConstants.OPERATION.equals(securityEvent.getSecurityEventType())) {
            this.operationSecurityEventOccured = true;
            this.identifySecurityTokenDependenciesAndUsage(this.securityEventQueue);
            Iterator<SecurityEvent> securityEventIterator = this.securityEventQueue.descendingIterator();
            while (securityEventIterator.hasNext()) {
                SecurityEvent prevSecurityEvent = securityEventIterator.next();
                this.forwardSecurityEvent(prevSecurityEvent);
            }
            this.forwardSecurityEvent(securityEvent);
            this.securityEventQueue.clear();
            return;
        }
        this.securityEventQueue.push(securityEvent);
    }

    @Override
    protected void forwardSecurityEvent(SecurityEvent securityEvent) throws XMLSecurityException {
        if (!this.allowRSA15KeyTransportAlgorithm && SecurityEventConstants.AlgorithmSuite.equals(securityEvent.getSecurityEventType())) {
            AlgorithmSuiteSecurityEvent algorithmSuiteSecurityEvent = (AlgorithmSuiteSecurityEvent)securityEvent;
            Boolean allowRSA15 = (Boolean)this.get("secureProcessing.AllowRSA15KeyTransportAlgorithm");
            if ((allowRSA15 == null || !allowRSA15.booleanValue()) && "http://www.w3.org/2001/04/xmlenc#rsa-1_5".equals(algorithmSuiteSecurityEvent.getAlgorithmURI())) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_CHECK, "secureProcessing.AllowRSA15KeyTransportAlgorithm");
            }
        }
        try {
            super.forwardSecurityEvent(securityEvent);
        }
        catch (WSSecurityException e) {
            throw e;
        }
        catch (XMLSecurityException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_CHECK, (Exception)e);
        }
    }

    private void identifySecurityTokenDependenciesAndUsage(Deque<SecurityEvent> securityEventDeque) throws XMLSecurityException {
        TokenSecurityEvent<? extends InboundSecurityToken> supportingToken;
        InboundSecurityToken messageSignatureToken;
        TokenSecurityEvent tokenSecurityEvent;
        MessageTokens messageTokens = new MessageTokens();
        HttpsTokenSecurityEvent httpsTokenSecurityEvent = null;
        ArrayList<TokenSecurityEvent<? extends InboundSecurityToken>> tokenSecurityEvents = new ArrayList<TokenSecurityEvent<? extends InboundSecurityToken>>();
        for (SecurityEvent securityEvent : securityEventDeque) {
            if (!(securityEvent instanceof TokenSecurityEvent)) continue;
            tokenSecurityEvent = (TokenSecurityEvent)securityEvent;
            if (WSSecurityEventConstants.HTTPS_TOKEN.equals(securityEvent.getSecurityEventType())) {
                HttpsTokenSecurityEvent actHttpsTokenSecurityEvent = (HttpsTokenSecurityEvent)tokenSecurityEvent;
                ((HttpsSecurityToken)actHttpsTokenSecurityEvent.getSecurityToken()).getTokenUsages().clear();
                ((HttpsSecurityToken)actHttpsTokenSecurityEvent.getSecurityToken()).addTokenUsage(WSSecurityTokenConstants.TOKENUSAGE_MAIN_SIGNATURE);
                messageTokens.messageSignatureTokens = this.addTokenSecurityEvent(actHttpsTokenSecurityEvent, messageTokens.messageSignatureTokens);
                HttpsTokenSecurityEvent clonedHttpsTokenSecurityEvent = new HttpsTokenSecurityEvent();
                clonedHttpsTokenSecurityEvent.setAuthenticationType(actHttpsTokenSecurityEvent.getAuthenticationType());
                clonedHttpsTokenSecurityEvent.setIssuerName(actHttpsTokenSecurityEvent.getIssuerName());
                clonedHttpsTokenSecurityEvent.setSecurityToken((HttpsSecurityToken)actHttpsTokenSecurityEvent.getSecurityToken());
                ((HttpsSecurityToken)clonedHttpsTokenSecurityEvent.getSecurityToken()).addTokenUsage(WSSecurityTokenConstants.TOKENUSAGE_MAIN_ENCRYPTION);
                messageTokens.messageEncryptionTokens = this.addTokenSecurityEvent(actHttpsTokenSecurityEvent, messageTokens.messageEncryptionTokens);
                httpsTokenSecurityEvent = clonedHttpsTokenSecurityEvent;
                continue;
            }
            tokenSecurityEvents.add(tokenSecurityEvent);
        }
        for (int i = 0; i < tokenSecurityEvents.size(); ++i) {
            tokenSecurityEvent = (TokenSecurityEvent)tokenSecurityEvents.get(i);
            InboundSecurityToken securityToken = WSSUtils.getRootToken((InboundSecurityToken)tokenSecurityEvent.getSecurityToken());
            if (!this.containsSecurityToken(messageTokens.supportingTokens, securityToken)) {
                TokenSecurityEvent<? extends InboundSecurityToken> newTokenSecurityEvent = WSSUtils.createTokenSecurityEvent(securityToken, tokenSecurityEvent.getCorrelationID());
                messageTokens.supportingTokens = this.addTokenSecurityEvent(newTokenSecurityEvent, messageTokens.supportingTokens);
                securityEventDeque.offer(newTokenSecurityEvent);
            }
            securityEventDeque.remove(tokenSecurityEvent);
        }
        this.parseSupportingTokens(messageTokens, httpsTokenSecurityEvent, securityEventDeque);
        if (messageTokens.messageSignatureTokens.isEmpty() && (tokenSecurityEvent = this.getTokenSecurityEvent(messageSignatureToken = this.getSupportingTokenSigningToken(messageTokens, securityEventDeque), tokenSecurityEvents)) != null) {
            this.removeTokenSecurityEvent(tokenSecurityEvent, messageTokens.supportingTokens);
            this.removeTokenSecurityEvent(tokenSecurityEvent, messageTokens.signedSupportingTokens);
            this.removeTokenSecurityEvent(tokenSecurityEvent, messageTokens.endorsingSupportingTokens);
            this.removeTokenSecurityEvent(tokenSecurityEvent, messageTokens.signedEndorsingSupportingTokens);
            this.removeTokenSecurityEvent(tokenSecurityEvent, messageTokens.signedEncryptedSupportingTokens);
            this.removeTokenSecurityEvent(tokenSecurityEvent, messageTokens.encryptedSupportingTokens);
            this.removeTokenSecurityEvent(tokenSecurityEvent, messageTokens.endorsingEncryptedSupportingTokens);
            this.removeTokenSecurityEvent(tokenSecurityEvent, messageTokens.signedEndorsingEncryptedSupportingTokens);
            messageTokens.messageSignatureTokens = this.addTokenSecurityEvent(tokenSecurityEvent, messageTokens.messageSignatureTokens);
        }
        if (messageTokens.messageSignatureTokens.isEmpty()) {
            Iterator<TokenSecurityEvent<? extends InboundSecurityToken>> iterator2 = messageTokens.supportingTokens.iterator();
            while (iterator2.hasNext()) {
                supportingToken = iterator2.next();
                if (!supportingToken.getSecurityToken().getTokenUsages().contains(WSSecurityTokenConstants.TokenUsage_Signature)) continue;
                iterator2.remove();
                messageTokens.messageSignatureTokens = this.addTokenSecurityEvent(supportingToken, messageTokens.messageSignatureTokens);
                break;
            }
        }
        if (messageTokens.messageEncryptionTokens.isEmpty()) {
            Iterator<TokenSecurityEvent<? extends InboundSecurityToken>> iterator3 = messageTokens.supportingTokens.iterator();
            while (iterator3.hasNext()) {
                supportingToken = iterator3.next();
                if (!supportingToken.getSecurityToken().getTokenUsages().contains(WSSecurityTokenConstants.TokenUsage_Encryption)) continue;
                iterator3.remove();
                messageTokens.messageEncryptionTokens = this.addTokenSecurityEvent(supportingToken, messageTokens.messageEncryptionTokens);
                break;
            }
        }
        if (!messageTokens.messageEncryptionTokens.isEmpty()) {
            this.messageEncryptionTokenOccured = true;
        }
        this.setTokenUsage(messageTokens.messageSignatureTokens, WSSecurityTokenConstants.TOKENUSAGE_MAIN_SIGNATURE);
        this.setTokenUsage(messageTokens.messageEncryptionTokens, WSSecurityTokenConstants.TOKENUSAGE_MAIN_ENCRYPTION);
        this.setTokenUsage(messageTokens.supportingTokens, WSSecurityTokenConstants.TOKENUSAGE_SUPPORTING_TOKENS);
        this.setTokenUsage(messageTokens.signedSupportingTokens, WSSecurityTokenConstants.TOKENUSAGE_SIGNED_SUPPORTING_TOKENS);
        this.setTokenUsage(messageTokens.endorsingSupportingTokens, WSSecurityTokenConstants.TOKENUSAGE_ENDORSING_SUPPORTING_TOKENS);
        this.setTokenUsage(messageTokens.signedEndorsingSupportingTokens, WSSecurityTokenConstants.TOKENUSAGE_SIGNED_ENDORSING_SUPPORTING_TOKENS);
        this.setTokenUsage(messageTokens.signedEncryptedSupportingTokens, WSSecurityTokenConstants.TOKENUSAGE_SIGNED_ENCRYPTED_SUPPORTING_TOKENS);
        this.setTokenUsage(messageTokens.encryptedSupportingTokens, WSSecurityTokenConstants.TOKENUSAGE_ENCRYPTED_SUPPORTING_TOKENS);
        this.setTokenUsage(messageTokens.endorsingEncryptedSupportingTokens, WSSecurityTokenConstants.TOKENUSAGE_ENDORSING_ENCRYPTED_SUPPORTING_TOKENS);
        this.setTokenUsage(messageTokens.signedEndorsingEncryptedSupportingTokens, WSSecurityTokenConstants.TOKENUSAGE_SIGNED_ENDORSING_ENCRYPTED_SUPPORTING_TOKENS);
    }

    private void parseSupportingTokens(MessageTokens messageTokens, HttpsTokenSecurityEvent httpsTokenSecurityEvent, Deque<SecurityEvent> securityEventDeque) throws XMLSecurityException {
        Iterator<TokenSecurityEvent<? extends InboundSecurityToken>> supportingTokensIterator = messageTokens.supportingTokens.iterator();
        while (supportingTokensIterator.hasNext()) {
            TokenSecurityEvent<? extends InboundSecurityToken> tokenSecurityEvent = supportingTokensIterator.next();
            List<InboundSecurityToken> signingSecurityTokens = this.isSignedToken(tokenSecurityEvent, securityEventDeque, httpsTokenSecurityEvent);
            ArrayList<QName> signatureElementPath = new ArrayList<QName>(4);
            signatureElementPath.addAll(WSSConstants.WSSE_SECURITY_HEADER_PATH);
            signatureElementPath.add(WSSConstants.TAG_dsig_Signature);
            boolean signsSignature = this.signsElement(tokenSecurityEvent, signatureElementPath, securityEventDeque);
            boolean encryptsSignature = this.encryptsElement(tokenSecurityEvent, signatureElementPath, securityEventDeque);
            ArrayList<QName> signatureConfirmationElementPath = new ArrayList<QName>(4);
            signatureConfirmationElementPath.addAll(WSSConstants.WSSE_SECURITY_HEADER_PATH);
            signatureConfirmationElementPath.add(WSSConstants.TAG_WSSE11_SIG_CONF);
            boolean signsSignatureConfirmation = this.signsElement(tokenSecurityEvent, signatureConfirmationElementPath, securityEventDeque);
            boolean encryptsSignatureConfirmation = this.encryptsElement(tokenSecurityEvent, signatureConfirmationElementPath, securityEventDeque);
            ArrayList<QName> timestampElementPath = new ArrayList<QName>(4);
            timestampElementPath.addAll(WSSConstants.WSSE_SECURITY_HEADER_PATH);
            timestampElementPath.add(WSSConstants.TAG_WSU_TIMESTAMP);
            boolean signsTimestamp = this.signsElement(tokenSecurityEvent, timestampElementPath, securityEventDeque);
            ArrayList<QName> usernameTokenElementPath = new ArrayList<QName>(4);
            usernameTokenElementPath.addAll(WSSConstants.WSSE_SECURITY_HEADER_PATH);
            usernameTokenElementPath.add(WSSConstants.TAG_WSSE_USERNAME_TOKEN);
            boolean encryptsUsernameToken = this.encryptsElement(tokenSecurityEvent, usernameTokenElementPath, securityEventDeque);
            boolean transportSecurityActive = Boolean.TRUE.equals(this.get("transportSecurityActive"));
            List<InboundSecurityToken> encryptingSecurityTokens = this.isEncryptedToken(tokenSecurityEvent, securityEventDeque, httpsTokenSecurityEvent);
            boolean signatureUsage = tokenSecurityEvent.getSecurityToken().getTokenUsages().contains(WSSecurityTokenConstants.TokenUsage_Signature);
            boolean encryptionUsage = tokenSecurityEvent.getSecurityToken().getTokenUsages().contains(WSSecurityTokenConstants.TokenUsage_Encryption);
            if (!transportSecurityActive && signsSignatureConfirmation && signsTimestamp && !signsSignature) {
                supportingTokensIterator.remove();
                messageTokens.messageSignatureTokens = this.addTokenSecurityEvent(tokenSecurityEvent, messageTokens.messageSignatureTokens);
                if (!encryptionUsage) continue;
                messageTokens.messageEncryptionTokens = this.addTokenSecurityEvent(tokenSecurityEvent, messageTokens.messageEncryptionTokens);
                continue;
            }
            if (!transportSecurityActive && signsSignatureConfirmation && !signsSignature) {
                supportingTokensIterator.remove();
                messageTokens.messageSignatureTokens = this.addTokenSecurityEvent(tokenSecurityEvent, messageTokens.messageSignatureTokens);
                if (!encryptionUsage) continue;
                messageTokens.messageEncryptionTokens = this.addTokenSecurityEvent(tokenSecurityEvent, messageTokens.messageEncryptionTokens);
                continue;
            }
            if (!transportSecurityActive && signsTimestamp && !signsSignature) {
                supportingTokensIterator.remove();
                messageTokens.messageSignatureTokens = this.addTokenSecurityEvent(tokenSecurityEvent, messageTokens.messageSignatureTokens);
                if (!encryptionUsage) continue;
                messageTokens.messageEncryptionTokens = this.addTokenSecurityEvent(tokenSecurityEvent, messageTokens.messageEncryptionTokens);
                continue;
            }
            if (!transportSecurityActive && (encryptsSignature || encryptsSignatureConfirmation || encryptsUsernameToken)) {
                supportingTokensIterator.remove();
                messageTokens.messageEncryptionTokens = this.addTokenSecurityEvent(tokenSecurityEvent, messageTokens.messageEncryptionTokens);
                continue;
            }
            if (signsSignature && !signingSecurityTokens.isEmpty() && !encryptingSecurityTokens.isEmpty()) {
                supportingTokensIterator.remove();
                messageTokens.signedEndorsingEncryptedSupportingTokens = this.addTokenSecurityEvent(tokenSecurityEvent, messageTokens.signedEndorsingEncryptedSupportingTokens);
                continue;
            }
            if (transportSecurityActive && signsTimestamp && !signingSecurityTokens.isEmpty() && !encryptingSecurityTokens.isEmpty()) {
                supportingTokensIterator.remove();
                messageTokens.signedEndorsingEncryptedSupportingTokens = this.addTokenSecurityEvent(tokenSecurityEvent, messageTokens.signedEndorsingEncryptedSupportingTokens);
                continue;
            }
            if (signsSignature && signingSecurityTokens.isEmpty() && !encryptingSecurityTokens.isEmpty()) {
                supportingTokensIterator.remove();
                messageTokens.endorsingEncryptedSupportingTokens = this.addTokenSecurityEvent(tokenSecurityEvent, messageTokens.endorsingEncryptedSupportingTokens);
                continue;
            }
            if (signsSignature && !signingSecurityTokens.isEmpty()) {
                supportingTokensIterator.remove();
                messageTokens.signedEndorsingSupportingTokens = this.addTokenSecurityEvent(tokenSecurityEvent, messageTokens.signedEndorsingSupportingTokens);
                continue;
            }
            if (signatureUsage && !signingSecurityTokens.isEmpty()) {
                supportingTokensIterator.remove();
                messageTokens.signedEndorsingSupportingTokens = this.addTokenSecurityEvent(tokenSecurityEvent, messageTokens.signedEndorsingSupportingTokens);
                continue;
            }
            if (signsSignature) {
                supportingTokensIterator.remove();
                messageTokens.endorsingSupportingTokens = this.addTokenSecurityEvent(tokenSecurityEvent, messageTokens.endorsingSupportingTokens);
                continue;
            }
            if (!signingSecurityTokens.isEmpty() && !encryptingSecurityTokens.isEmpty()) {
                supportingTokensIterator.remove();
                messageTokens.signedEncryptedSupportingTokens = this.addTokenSecurityEvent(tokenSecurityEvent, messageTokens.signedEncryptedSupportingTokens);
                continue;
            }
            if (!signingSecurityTokens.isEmpty()) {
                supportingTokensIterator.remove();
                messageTokens.signedSupportingTokens = this.addTokenSecurityEvent(tokenSecurityEvent, messageTokens.signedSupportingTokens);
                continue;
            }
            if (encryptingSecurityTokens.isEmpty()) continue;
            supportingTokensIterator.remove();
            messageTokens.encryptedSupportingTokens = this.addTokenSecurityEvent(tokenSecurityEvent, messageTokens.encryptedSupportingTokens);
        }
    }

    private void removeTokenSecurityEvent(TokenSecurityEvent<? extends InboundSecurityToken> tokenSecurityEvent, List<TokenSecurityEvent<? extends InboundSecurityToken>> tokenSecurityEventList) {
        for (int i = 0; i < tokenSecurityEventList.size(); ++i) {
            TokenSecurityEvent<? extends InboundSecurityToken> securityEvent = tokenSecurityEventList.get(i);
            if (!securityEvent.getSecurityToken().getId().equals(tokenSecurityEvent.getSecurityToken().getId())) continue;
            tokenSecurityEventList.remove(securityEvent);
            return;
        }
    }

    private List<TokenSecurityEvent<? extends InboundSecurityToken>> addTokenSecurityEvent(TokenSecurityEvent<? extends InboundSecurityToken> tokenSecurityEvent, List<TokenSecurityEvent<? extends InboundSecurityToken>> tokenSecurityEventList) {
        if (tokenSecurityEventList == Collections.emptyList()) {
            tokenSecurityEventList = new ArrayList<TokenSecurityEvent<? extends InboundSecurityToken>>();
        }
        tokenSecurityEventList.add(tokenSecurityEvent);
        return tokenSecurityEventList;
    }

    private boolean containsSecurityToken(List<TokenSecurityEvent<? extends InboundSecurityToken>> supportingTokens, SecurityToken securityToken) {
        if (securityToken != null) {
            for (int i = 0; i < supportingTokens.size(); ++i) {
                TokenSecurityEvent<? extends InboundSecurityToken> tokenSecurityEvent = supportingTokens.get(i);
                if (!tokenSecurityEvent.getSecurityToken().getId().equals(securityToken.getId())) continue;
                return true;
            }
        }
        return false;
    }

    private TokenSecurityEvent<? extends InboundSecurityToken> getTokenSecurityEvent(InboundSecurityToken securityToken, List<TokenSecurityEvent<? extends InboundSecurityToken>> tokenSecurityEvents) throws XMLSecurityException {
        if (securityToken != null) {
            for (int i = 0; i < tokenSecurityEvents.size(); ++i) {
                TokenSecurityEvent<? extends InboundSecurityToken> tokenSecurityEvent = tokenSecurityEvents.get(i);
                if (!tokenSecurityEvent.getSecurityToken().getId().equals(securityToken.getId())) continue;
                return tokenSecurityEvent;
            }
        }
        return null;
    }

    private InboundSecurityToken getSupportingTokenSigningToken(MessageTokens messageTokens, Deque<SecurityEvent> securityEventDeque) throws XMLSecurityException {
        List<InboundSecurityToken> signingSecurityTokens;
        TokenSecurityEvent<? extends InboundSecurityToken> tokenSecurityEvent;
        int i;
        for (i = 0; i < messageTokens.signedSupportingTokens.size(); ++i) {
            tokenSecurityEvent = messageTokens.signedSupportingTokens.get(i);
            signingSecurityTokens = this.getSigningToken(tokenSecurityEvent, securityEventDeque);
            if (signingSecurityTokens.size() != 1) continue;
            return signingSecurityTokens.get(0);
        }
        for (i = 0; i < messageTokens.signedEndorsingSupportingTokens.size(); ++i) {
            tokenSecurityEvent = messageTokens.signedEndorsingSupportingTokens.get(i);
            signingSecurityTokens = this.getSigningToken(tokenSecurityEvent, securityEventDeque);
            if (signingSecurityTokens.size() != 1) continue;
            return signingSecurityTokens.get(0);
        }
        for (i = 0; i < messageTokens.signedEncryptedSupportingTokens.size(); ++i) {
            tokenSecurityEvent = messageTokens.signedEncryptedSupportingTokens.get(i);
            signingSecurityTokens = this.getSigningToken(tokenSecurityEvent, securityEventDeque);
            if (signingSecurityTokens.size() != 1) continue;
            return signingSecurityTokens.get(0);
        }
        for (i = 0; i < messageTokens.signedEndorsingEncryptedSupportingTokens.size(); ++i) {
            tokenSecurityEvent = messageTokens.signedEndorsingEncryptedSupportingTokens.get(i);
            signingSecurityTokens = this.getSigningToken(tokenSecurityEvent, securityEventDeque);
            if (signingSecurityTokens.size() != 1) continue;
            return signingSecurityTokens.get(0);
        }
        return null;
    }

    private List<InboundSecurityToken> getSigningToken(TokenSecurityEvent<? extends SecurityToken> tokenSecurityEvent, Deque<SecurityEvent> securityEventDeque) throws XMLSecurityException {
        ArrayList<InboundSecurityToken> signingSecurityTokens = new ArrayList<InboundSecurityToken>();
        for (SecurityEvent securityEvent : securityEventDeque) {
            SignedElementSecurityEvent signedElementSecurityEvent;
            if (!WSSecurityEventConstants.SignedElement.equals(securityEvent.getSecurityEventType()) || !(signedElementSecurityEvent = (SignedElementSecurityEvent)securityEvent).isSigned() || !WSSUtils.pathMatches(signedElementSecurityEvent.getElementPath(), ((InboundSecurityToken)tokenSecurityEvent.getSecurityToken()).getElementPath(), true, false)) continue;
            signingSecurityTokens.add((InboundSecurityToken)signedElementSecurityEvent.getSecurityToken());
        }
        return signingSecurityTokens;
    }

    private void setTokenUsage(List<TokenSecurityEvent<? extends InboundSecurityToken>> tokenSecurityEvents, SecurityTokenConstants.TokenUsage tokenUsage) throws XMLSecurityException {
        for (int i = 0; i < tokenSecurityEvents.size(); ++i) {
            TokenSecurityEvent<? extends InboundSecurityToken> tokenSecurityEvent = tokenSecurityEvents.get(i);
            this.setTokenUsage(tokenSecurityEvent, tokenUsage);
        }
    }

    private void setTokenUsage(TokenSecurityEvent<? extends InboundSecurityToken> tokenSecurityEvent, SecurityTokenConstants.TokenUsage tokenUsage) throws XMLSecurityException {
        tokenSecurityEvent.getSecurityToken().getTokenUsages().remove(WSSecurityTokenConstants.TOKENUSAGE_SUPPORTING_TOKENS);
        tokenSecurityEvent.getSecurityToken().getTokenUsages().remove(WSSecurityTokenConstants.TokenUsage_Signature);
        tokenSecurityEvent.getSecurityToken().getTokenUsages().remove(WSSecurityTokenConstants.TokenUsage_Encryption);
        tokenSecurityEvent.getSecurityToken().addTokenUsage(tokenUsage);
    }

    private List<InboundSecurityToken> isSignedToken(TokenSecurityEvent<? extends SecurityToken> tokenSecurityEvent, Deque<SecurityEvent> securityEventDeque, HttpsTokenSecurityEvent httpsTokenSecurityEvent) throws XMLSecurityException {
        ArrayList<InboundSecurityToken> securityTokenList = new ArrayList<InboundSecurityToken>();
        if (httpsTokenSecurityEvent != null) {
            securityTokenList.add((InboundSecurityToken)httpsTokenSecurityEvent.getSecurityToken());
            return securityTokenList;
        }
        for (SecurityEvent securityEvent : securityEventDeque) {
            SignedElementSecurityEvent signedElementSecurityEvent;
            if (!WSSecurityEventConstants.SignedElement.equals(securityEvent.getSecurityEventType()) || !(signedElementSecurityEvent = (SignedElementSecurityEvent)securityEvent).isSigned() || tokenSecurityEvent.getSecurityToken() == null || signedElementSecurityEvent.getXmlSecEvent() == null || signedElementSecurityEvent.getXmlSecEvent() != ((InboundSecurityToken)tokenSecurityEvent.getSecurityToken()).getXMLSecEvent() || securityTokenList.contains((InboundSecurityToken)signedElementSecurityEvent.getSecurityToken())) continue;
            securityTokenList.add((InboundSecurityToken)signedElementSecurityEvent.getSecurityToken());
        }
        return securityTokenList;
    }

    private List<InboundSecurityToken> isEncryptedToken(TokenSecurityEvent<? extends SecurityToken> tokenSecurityEvent, Deque<SecurityEvent> securityEventDeque, HttpsTokenSecurityEvent httpsTokenSecurityEvent) throws XMLSecurityException {
        ArrayList<InboundSecurityToken> securityTokenList = new ArrayList<InboundSecurityToken>();
        if (httpsTokenSecurityEvent != null) {
            securityTokenList.add((InboundSecurityToken)httpsTokenSecurityEvent.getSecurityToken());
            return securityTokenList;
        }
        for (SecurityEvent securityEvent : securityEventDeque) {
            EncryptedElementSecurityEvent encryptedElementSecurityEvent;
            if (!WSSecurityEventConstants.EncryptedElement.equals(securityEvent.getSecurityEventType()) || !(encryptedElementSecurityEvent = (EncryptedElementSecurityEvent)securityEvent).isEncrypted() || tokenSecurityEvent.getSecurityToken() == null || encryptedElementSecurityEvent.getXmlSecEvent() == null || encryptedElementSecurityEvent.getXmlSecEvent() != ((InboundSecurityToken)tokenSecurityEvent.getSecurityToken()).getXMLSecEvent() || securityTokenList.contains((InboundSecurityToken)encryptedElementSecurityEvent.getSecurityToken())) continue;
            securityTokenList.add((InboundSecurityToken)encryptedElementSecurityEvent.getSecurityToken());
        }
        return securityTokenList;
    }

    private boolean signsElement(TokenSecurityEvent<? extends SecurityToken> tokenSecurityEvent, List<QName> elementPath, Deque<SecurityEvent> securityEventDeque) throws XMLSecurityException {
        for (SecurityEvent securityEvent : securityEventDeque) {
            SignedElementSecurityEvent signedElementSecurityEvent;
            if (!WSSecurityEventConstants.SignedElement.equals(securityEvent.getSecurityEventType()) || !(signedElementSecurityEvent = (SignedElementSecurityEvent)securityEvent).isSigned() || !this.matchesTokenOrWrappedTokenId(tokenSecurityEvent.getSecurityToken(), signedElementSecurityEvent.getSecurityToken().getId(), SecurityTokenConstants.TokenUsage_Signature) || !WSSUtils.pathMatches(elementPath, signedElementSecurityEvent.getElementPath(), true, false)) continue;
            return true;
        }
        return false;
    }

    private boolean matchesTokenOrWrappedTokenId(SecurityToken securityToken, String id, SecurityTokenConstants.TokenUsage tokenUsage) throws XMLSecurityException {
        if (securityToken.getId().equals(id) && securityToken.getTokenUsages().contains(tokenUsage)) {
            return true;
        }
        List<? extends SecurityToken> wrappedTokens = securityToken.getWrappedTokens();
        for (int i = 0; i < wrappedTokens.size(); ++i) {
            boolean match = this.matchesTokenOrWrappedTokenId(wrappedTokens.get(i), id, tokenUsage);
            if (!match) continue;
            return match;
        }
        return false;
    }

    private boolean encryptsElement(TokenSecurityEvent<? extends SecurityToken> tokenSecurityEvent, List<QName> elementPath, Deque<SecurityEvent> securityEventDeque) throws XMLSecurityException {
        for (SecurityEvent securityEvent : securityEventDeque) {
            if (WSSecurityEventConstants.EncryptedElement.equals(securityEvent.getSecurityEventType())) {
                EncryptedElementSecurityEvent encryptedElementSecurityEvent = (EncryptedElementSecurityEvent)securityEvent;
                if (!encryptedElementSecurityEvent.isEncrypted() || !encryptedElementSecurityEvent.getSecurityToken().getId().equals(tokenSecurityEvent.getSecurityToken().getId()) || !WSSUtils.pathMatches(elementPath, encryptedElementSecurityEvent.getElementPath(), true, false)) continue;
                return true;
            }
            if (!WSSecurityEventConstants.ContentEncrypted.equals(securityEvent.getSecurityEventType())) continue;
            ContentEncryptedElementSecurityEvent contentEncryptedElementSecurityEvent = (ContentEncryptedElementSecurityEvent)securityEvent;
            String tokenId = tokenSecurityEvent.getSecurityToken().getId();
            if (!contentEncryptedElementSecurityEvent.isEncrypted() || !contentEncryptedElementSecurityEvent.getSecurityToken().getId().equals(tokenId) || contentEncryptedElementSecurityEvent.getXmlSecEvent() != ((InboundSecurityToken)tokenSecurityEvent.getSecurityToken()).getXMLSecEvent() || !WSSUtils.pathMatches(elementPath, contentEncryptedElementSecurityEvent.getElementPath(), true, false)) continue;
            return true;
        }
        return false;
    }

    @Override
    public void handleBSPRule(BSPRule bspRule) throws WSSecurityException {
        if (this.disableBSPEnforcement) {
            return;
        }
        if (!this.ignoredBSPRules.contains((Object)bspRule)) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, "empty", new Object[]{"BSP:" + bspRule.name() + ": " + bspRule.getMsg()});
        }
        LOG.warn("BSP:" + bspRule.name() + ": " + bspRule.getMsg());
    }

    @Override
    public void ignoredBSPRules(List<BSPRule> bspRules) {
        this.ignoredBSPRules = new ArrayList<BSPRule>(bspRules);
    }

    public boolean isDisableBSPEnforcement() {
        return this.disableBSPEnforcement;
    }

    public void setDisableBSPEnforcement(boolean disableBSPEnforcement) {
        this.disableBSPEnforcement = disableBSPEnforcement;
    }

    public boolean isAllowRSA15KeyTransportAlgorithm() {
        return this.allowRSA15KeyTransportAlgorithm;
    }

    public void setAllowRSA15KeyTransportAlgorithm(boolean allowRSA15KeyTransportAlgorithm) {
        this.allowRSA15KeyTransportAlgorithm = allowRSA15KeyTransportAlgorithm;
    }

    private static class MessageTokens {
        List<TokenSecurityEvent<? extends InboundSecurityToken>> messageSignatureTokens = Collections.emptyList();
        List<TokenSecurityEvent<? extends InboundSecurityToken>> messageEncryptionTokens = Collections.emptyList();
        List<TokenSecurityEvent<? extends InboundSecurityToken>> supportingTokens = Collections.emptyList();
        List<TokenSecurityEvent<? extends InboundSecurityToken>> signedSupportingTokens = Collections.emptyList();
        List<TokenSecurityEvent<? extends InboundSecurityToken>> endorsingSupportingTokens = Collections.emptyList();
        List<TokenSecurityEvent<? extends InboundSecurityToken>> signedEndorsingSupportingTokens = Collections.emptyList();
        List<TokenSecurityEvent<? extends InboundSecurityToken>> signedEncryptedSupportingTokens = Collections.emptyList();
        List<TokenSecurityEvent<? extends InboundSecurityToken>> encryptedSupportingTokens = Collections.emptyList();
        List<TokenSecurityEvent<? extends InboundSecurityToken>> endorsingEncryptedSupportingTokens = Collections.emptyList();
        List<TokenSecurityEvent<? extends InboundSecurityToken>> signedEndorsingEncryptedSupportingTokens = Collections.emptyList();

        private MessageTokens() {
        }
    }
}

