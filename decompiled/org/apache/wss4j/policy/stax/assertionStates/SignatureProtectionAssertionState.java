/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.stax.assertionStates;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.xml.namespace.QName;
import org.apache.wss4j.common.WSSPolicyException;
import org.apache.wss4j.policy.AssertionState;
import org.apache.wss4j.policy.model.AbstractSecurityAssertion;
import org.apache.wss4j.policy.model.AbstractSymmetricAsymmetricBinding;
import org.apache.wss4j.policy.stax.Assertable;
import org.apache.wss4j.policy.stax.DummyPolicyAsserter;
import org.apache.wss4j.policy.stax.PolicyAsserter;
import org.apache.wss4j.stax.ext.WSSConstants;
import org.apache.wss4j.stax.securityEvent.WSSecurityEventConstants;
import org.apache.wss4j.stax.securityToken.WSSecurityTokenConstants;
import org.apache.wss4j.stax.utils.WSSUtils;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.securityEvent.EncryptedElementSecurityEvent;
import org.apache.xml.security.stax.securityEvent.SecurityEvent;
import org.apache.xml.security.stax.securityEvent.SecurityEventConstants;
import org.apache.xml.security.stax.securityEvent.TokenSecurityEvent;
import org.apache.xml.security.stax.securityToken.SecurityToken;
import org.apache.xml.security.stax.securityToken.SecurityTokenConstants;

public class SignatureProtectionAssertionState
extends AssertionState
implements Assertable {
    private final List<EncryptedElementSecurityEvent> encryptedElementEvents = new ArrayList<EncryptedElementSecurityEvent>();
    private final List<TokenSecurityEvent<? extends SecurityToken>> tokenSecurityEvents = new ArrayList<TokenSecurityEvent<? extends SecurityToken>>();
    private final List<List<QName>> elementPaths = new ArrayList<List<QName>>();
    private PolicyAsserter policyAsserter;

    public SignatureProtectionAssertionState(AbstractSecurityAssertion assertion, PolicyAsserter policyAsserter, boolean asserted) {
        super(assertion, asserted);
        LinkedList<QName> signature11Path = new LinkedList<QName>();
        signature11Path.addAll(WSSConstants.SOAP_11_HEADER_PATH);
        signature11Path.add(WSSConstants.TAG_WSSE_SECURITY);
        signature11Path.add(WSSConstants.TAG_dsig_Signature);
        this.elementPaths.add(signature11Path);
        LinkedList<QName> signatureConfirmation11Path = new LinkedList<QName>();
        signatureConfirmation11Path.addAll(WSSConstants.SOAP_11_HEADER_PATH);
        signatureConfirmation11Path.add(WSSConstants.TAG_WSSE_SECURITY);
        signatureConfirmation11Path.add(WSSConstants.TAG_WSSE11_SIG_CONF);
        this.elementPaths.add(signatureConfirmation11Path);
        this.policyAsserter = policyAsserter;
        if (this.policyAsserter == null) {
            this.policyAsserter = new DummyPolicyAsserter();
        }
        if (asserted) {
            String namespace = this.getAssertion().getName().getNamespaceURI();
            policyAsserter.assertPolicy(new QName(namespace, "EncryptSignature"));
        }
    }

    @Override
    public SecurityEventConstants.Event[] getSecurityEventType() {
        return new SecurityEventConstants.Event[]{WSSecurityEventConstants.EncryptedElement, WSSecurityEventConstants.EncryptedKeyToken, WSSecurityEventConstants.ISSUED_TOKEN, WSSecurityEventConstants.KERBEROS_TOKEN, SecurityEventConstants.KeyValueToken, WSSecurityEventConstants.REL_TOKEN, WSSecurityEventConstants.SAML_TOKEN, WSSecurityEventConstants.SECURITY_CONTEXT_TOKEN, WSSecurityEventConstants.USERNAME_TOKEN, SecurityEventConstants.X509Token, WSSecurityEventConstants.OPERATION};
    }

    @Override
    public boolean assertEvent(SecurityEvent securityEvent) throws WSSPolicyException {
        if (securityEvent instanceof EncryptedElementSecurityEvent) {
            EncryptedElementSecurityEvent encryptedElementSecurityEvent = (EncryptedElementSecurityEvent)securityEvent;
            for (List<QName> qNameList : this.elementPaths) {
                if (!WSSUtils.pathMatches(qNameList, encryptedElementSecurityEvent.getElementPath(), true, false)) continue;
                this.encryptedElementEvents.add(encryptedElementSecurityEvent);
            }
        } else if (securityEvent instanceof TokenSecurityEvent) {
            TokenSecurityEvent tokenSecurityEvent = (TokenSecurityEvent)securityEvent;
            this.tokenSecurityEvents.add(tokenSecurityEvent);
        }
        return true;
    }

    @Override
    public boolean isAsserted() {
        this.clearErrorMessage();
        if (this.encryptedElementEvents.size() == 1) {
            return this.testEncryptedSignature(this.encryptedElementEvents.get(0));
        }
        if (this.encryptedElementEvents.size() > 1) {
            String endorsingSignatureId = this.findEndorsingSignatureId();
            for (EncryptedElementSecurityEvent encryptedElementSecurityEvent : this.encryptedElementEvents) {
                String elementId = encryptedElementSecurityEvent.getCorrelationID();
                if (endorsingSignatureId != null && endorsingSignatureId.equals(elementId) || this.testEncryptedSignature(encryptedElementSecurityEvent)) continue;
                return false;
            }
        }
        return true;
    }

    private String findEndorsingSignatureId() {
        for (int i = 0; i < this.tokenSecurityEvents.size(); ++i) {
            TokenSecurityEvent<? extends SecurityToken> tokenSecurityEvent = this.tokenSecurityEvents.get(i);
            try {
                SecurityToken securityToken = this.getEffectiveSignatureToken(tokenSecurityEvent.getSecurityToken());
                if (!this.isSignatureToken(securityToken) || this.isMainSignatureToken(securityToken)) continue;
                return tokenSecurityEvent.getCorrelationID();
            }
            catch (XMLSecurityException e) {
                return null;
            }
        }
        return null;
    }

    private boolean isSignatureToken(SecurityToken securityToken) {
        List<SecurityTokenConstants.TokenUsage> tokenUsages = securityToken.getTokenUsages();
        for (int i = 0; i < tokenUsages.size(); ++i) {
            SecurityTokenConstants.TokenUsage tokenUsage = tokenUsages.get(i);
            if (!WSSecurityTokenConstants.TokenUsage_Signature.equals(tokenUsage) && !WSSecurityTokenConstants.TOKENUSAGE_MAIN_SIGNATURE.equals(tokenUsage) && !tokenUsage.getName().contains("Endorsing")) continue;
            return true;
        }
        return false;
    }

    private boolean isMainSignatureToken(SecurityToken securityToken) throws XMLSecurityException {
        SecurityToken rootToken = WSSUtils.getRootToken(securityToken);
        List<SecurityTokenConstants.TokenUsage> tokenUsages = rootToken.getTokenUsages();
        return tokenUsages.contains(WSSecurityTokenConstants.TOKENUSAGE_MAIN_SIGNATURE);
    }

    private SecurityToken getEffectiveSignatureToken(SecurityToken securityToken) throws XMLSecurityException {
        SecurityToken tmp = WSSUtils.getRootToken(securityToken);
        List<? extends SecurityToken> wrappedTokens = tmp.getWrappedTokens();
        for (int i = 0; i < wrappedTokens.size(); ++i) {
            SecurityToken token = wrappedTokens.get(i);
            if (!this.isSignatureToken(token)) continue;
            if (WSSecurityTokenConstants.DerivedKeyToken.equals(token.getTokenType())) {
                return tmp;
            }
            tmp = token;
        }
        return tmp;
    }

    private boolean testEncryptedSignature(EncryptedElementSecurityEvent encryptedElementSecurityEvent) {
        AbstractSymmetricAsymmetricBinding abstractSymmetricAsymmetricBinding = (AbstractSymmetricAsymmetricBinding)this.getAssertion();
        String namespace = this.getAssertion().getName().getNamespaceURI();
        if (encryptedElementSecurityEvent.isEncrypted()) {
            if (abstractSymmetricAsymmetricBinding.isEncryptSignature()) {
                this.setAsserted(true);
                this.policyAsserter.assertPolicy(new QName(namespace, "EncryptSignature"));
                return true;
            }
            this.setAsserted(false);
            this.setErrorMessage("Element " + WSSUtils.pathAsString(encryptedElementSecurityEvent.getElementPath()) + " must not be encrypted");
            this.policyAsserter.unassertPolicy(new QName(namespace, "EncryptSignature"), this.getErrorMessage());
            return false;
        }
        if (abstractSymmetricAsymmetricBinding.isEncryptSignature()) {
            this.setAsserted(false);
            this.setErrorMessage("Element " + WSSUtils.pathAsString(encryptedElementSecurityEvent.getElementPath()) + " must be encrypted");
            this.policyAsserter.unassertPolicy(new QName(namespace, "EncryptSignature"), this.getErrorMessage());
            return false;
        }
        this.setAsserted(true);
        this.policyAsserter.assertPolicy(new QName(namespace, "EncryptSignature"));
        return true;
    }
}

