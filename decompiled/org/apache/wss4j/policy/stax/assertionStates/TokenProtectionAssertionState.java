/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.stax.assertionStates;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.xml.namespace.QName;
import org.apache.neethi.Assertion;
import org.apache.wss4j.common.WSSPolicyException;
import org.apache.wss4j.policy.AssertionState;
import org.apache.wss4j.policy.model.AbstractSymmetricAsymmetricBinding;
import org.apache.wss4j.policy.stax.Assertable;
import org.apache.wss4j.policy.stax.DummyPolicyAsserter;
import org.apache.wss4j.policy.stax.PolicyAsserter;
import org.apache.wss4j.stax.ext.WSSConstants;
import org.apache.wss4j.stax.securityEvent.WSSecurityEventConstants;
import org.apache.wss4j.stax.securityToken.WSSecurityTokenConstants;
import org.apache.wss4j.stax.utils.WSSUtils;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.securityEvent.SecurityEvent;
import org.apache.xml.security.stax.securityEvent.SecurityEventConstants;
import org.apache.xml.security.stax.securityEvent.SignedElementSecurityEvent;
import org.apache.xml.security.stax.securityEvent.TokenSecurityEvent;
import org.apache.xml.security.stax.securityToken.InboundSecurityToken;
import org.apache.xml.security.stax.securityToken.SecurityToken;
import org.apache.xml.security.stax.securityToken.SecurityTokenConstants;

public class TokenProtectionAssertionState
extends AssertionState
implements Assertable {
    private final List<SignedElementSecurityEvent> signedElementEvents = new ArrayList<SignedElementSecurityEvent>();
    private final List<TokenSecurityEvent<? extends SecurityToken>> tokenSecurityEvents = new ArrayList<TokenSecurityEvent<? extends SecurityToken>>();
    private PolicyAsserter policyAsserter;

    public TokenProtectionAssertionState(Assertion assertion, PolicyAsserter policyAsserter, boolean initialAssertionState) {
        super(assertion, initialAssertionState);
        this.policyAsserter = policyAsserter;
        if (this.policyAsserter == null) {
            this.policyAsserter = new DummyPolicyAsserter();
        }
        if (initialAssertionState) {
            String namespace = this.getAssertion().getName().getNamespaceURI();
            policyAsserter.assertPolicy(new QName(namespace, "ProtectTokens"));
        }
    }

    @Override
    public SecurityEventConstants.Event[] getSecurityEventType() {
        return new SecurityEventConstants.Event[]{SecurityEventConstants.SignedElement, WSSecurityEventConstants.EncryptedKeyToken, WSSecurityEventConstants.ISSUED_TOKEN, WSSecurityEventConstants.KERBEROS_TOKEN, SecurityEventConstants.KeyValueToken, WSSecurityEventConstants.REL_TOKEN, WSSecurityEventConstants.SAML_TOKEN, WSSecurityEventConstants.SECURITY_CONTEXT_TOKEN, WSSecurityEventConstants.USERNAME_TOKEN, SecurityEventConstants.X509Token, WSSecurityEventConstants.OPERATION};
    }

    @Override
    public boolean assertEvent(SecurityEvent securityEvent) throws WSSPolicyException, XMLSecurityException {
        AbstractSymmetricAsymmetricBinding abstractSymmetricAsymmetricBinding = (AbstractSymmetricAsymmetricBinding)this.getAssertion();
        boolean protectTokens = abstractSymmetricAsymmetricBinding.isProtectTokens();
        String namespace = this.getAssertion().getName().getNamespaceURI();
        if (securityEvent instanceof SignedElementSecurityEvent) {
            SignedElementSecurityEvent signedElementSecurityEvent = (SignedElementSecurityEvent)securityEvent;
            if (signedElementSecurityEvent.isSigned()) {
                this.signedElementEvents.add(signedElementSecurityEvent);
            }
        } else if (securityEvent instanceof TokenSecurityEvent) {
            TokenSecurityEvent tokenSecurityEvent = (TokenSecurityEvent)securityEvent;
            this.tokenSecurityEvents.add(tokenSecurityEvent);
        } else {
            for (int i = 0; i < this.tokenSecurityEvents.size(); ++i) {
                TokenSecurityEvent<? extends SecurityToken> tokenSecurityEvent = this.tokenSecurityEvents.get(i);
                SecurityToken securityToken = this.getEffectiveSignatureToken(tokenSecurityEvent.getSecurityToken());
                if (((InboundSecurityToken)securityToken).isIncludedInMessage() && this.isSignatureToken(securityToken)) {
                    boolean signsItsSignatureToken = this.signsItsSignatureToken(securityToken);
                    if (protectTokens && !signsItsSignatureToken) {
                        this.setAsserted(false);
                        this.setErrorMessage("Token " + WSSUtils.pathAsString(((InboundSecurityToken)securityToken).getElementPath()) + " must be signed by its signature.");
                        this.policyAsserter.unassertPolicy(new QName(namespace, "ProtectTokens"), this.getErrorMessage());
                        return false;
                    }
                    if (!protectTokens && signsItsSignatureToken) {
                        this.setAsserted(false);
                        this.setErrorMessage("Token " + WSSUtils.pathAsString(((InboundSecurityToken)securityToken).getElementPath()) + " must not be signed by its signature.");
                        this.policyAsserter.unassertPolicy(new QName(namespace, "ProtectTokens"), this.getErrorMessage());
                        return false;
                    }
                }
                if (this.isEndorsingToken(securityToken) && !this.signsMainSignature(securityToken)) {
                    this.setAsserted(false);
                    this.setErrorMessage("Token " + WSSUtils.pathAsString(((InboundSecurityToken)securityToken).getElementPath()) + " must sign the main signature.");
                    this.policyAsserter.unassertPolicy(new QName(namespace, "ProtectTokens"), this.getErrorMessage());
                    return false;
                }
                if (!this.isMainSignatureToken(securityToken) || this.signsSignedSupportingTokens(securityToken)) continue;
                this.setAsserted(false);
                this.setErrorMessage("Main signature must sign the Signed*Supporting-Tokens.");
                this.policyAsserter.unassertPolicy(new QName(namespace, "ProtectTokens"), this.getErrorMessage());
                return false;
            }
        }
        this.policyAsserter.assertPolicy(new QName(namespace, "ProtectTokens"));
        return true;
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

    private boolean isEndorsingToken(SecurityToken securityToken) throws XMLSecurityException {
        SecurityToken rootToken = WSSUtils.getRootToken(securityToken);
        List<SecurityTokenConstants.TokenUsage> tokenUsages = rootToken.getTokenUsages();
        for (int i = 0; i < tokenUsages.size(); ++i) {
            SecurityTokenConstants.TokenUsage tokenUsage = tokenUsages.get(i);
            if (!tokenUsage.getName().contains("Endorsing")) continue;
            return true;
        }
        return false;
    }

    private boolean isSignedSupportingToken(SecurityToken securityToken) throws XMLSecurityException {
        SecurityToken rootToken = WSSUtils.getRootToken(securityToken);
        List<SecurityTokenConstants.TokenUsage> tokenUsages = rootToken.getTokenUsages();
        for (int i = 0; i < tokenUsages.size(); ++i) {
            SecurityTokenConstants.TokenUsage tokenUsage = tokenUsages.get(i);
            if (!tokenUsage.getName().contains("Signed")) continue;
            return true;
        }
        return false;
    }

    private boolean isMainSignatureToken(SecurityToken securityToken) throws XMLSecurityException {
        SecurityToken rootToken = WSSUtils.getRootToken(securityToken);
        List<SecurityTokenConstants.TokenUsage> tokenUsages = rootToken.getTokenUsages();
        return tokenUsages.contains(WSSecurityTokenConstants.TOKENUSAGE_MAIN_SIGNATURE);
    }

    private boolean signsMainSignature(SecurityToken securityToken) throws XMLSecurityException {
        LinkedList<QName> signaturePath = new LinkedList<QName>();
        signaturePath.addAll(WSSConstants.WSSE_SECURITY_HEADER_PATH);
        signaturePath.add(WSSConstants.TAG_dsig_Signature);
        for (int i = 0; i < this.signedElementEvents.size(); ++i) {
            SecurityToken signingSecurityToken;
            SignedElementSecurityEvent signedElementSecurityEvent = this.signedElementEvents.get(i);
            if (!WSSUtils.pathMatches(signedElementSecurityEvent.getElementPath(), signaturePath, true, false) || (signingSecurityToken = this.getEffectiveSignatureToken(signedElementSecurityEvent.getSecurityToken())) == null || !signingSecurityToken.getId().equals(securityToken.getId())) continue;
            return true;
        }
        return false;
    }

    private boolean signsItsSignatureToken(SecurityToken securityToken) throws XMLSecurityException {
        for (int i = 0; i < this.signedElementEvents.size(); ++i) {
            SignedElementSecurityEvent signedElementSecurityEvent = this.signedElementEvents.get(i);
            if (!WSSUtils.pathMatches(signedElementSecurityEvent.getElementPath(), ((InboundSecurityToken)securityToken).getElementPath(), false, false)) continue;
            SecurityToken signingSecurityToken = signedElementSecurityEvent.getSecurityToken();
            if (!(signingSecurityToken = this.getEffectiveSignatureToken(signingSecurityToken)).getId().equals(securityToken.getId())) continue;
            for (int j = 0; j < this.tokenSecurityEvents.size(); ++j) {
                TokenSecurityEvent<? extends SecurityToken> tokenSecurityEvent = this.tokenSecurityEvents.get(j);
                SecurityToken st = this.getEffectiveSignatureToken(tokenSecurityEvent.getSecurityToken());
                if (signedElementSecurityEvent.getXmlSecEvent() != ((InboundSecurityToken)st).getXMLSecEvent() || !st.getId().equals(securityToken.getId())) continue;
                return true;
            }
        }
        return false;
    }

    private boolean signsSignedSupportingTokens(SecurityToken securityToken) throws XMLSecurityException {
        LinkedList<SecurityToken> signedSupportingTokens = new LinkedList<SecurityToken>();
        LinkedList<SignedElementSecurityEvent> signedElements = new LinkedList<SignedElementSecurityEvent>();
        for (int i = 0; i < this.tokenSecurityEvents.size(); ++i) {
            TokenSecurityEvent<? extends SecurityToken> tokenSecurityEvent = this.tokenSecurityEvents.get(i);
            SecurityToken supportingToken = tokenSecurityEvent.getSecurityToken();
            if (!this.isSignedSupportingToken(supportingToken) || signedSupportingTokens.contains(supportingToken)) continue;
            signedSupportingTokens.add(supportingToken);
            List<QName> elementPath = ((InboundSecurityToken)supportingToken).getElementPath();
            boolean found = false;
            for (int j = 0; j < this.signedElementEvents.size(); ++j) {
                SecurityToken elementSignatureToken;
                SignedElementSecurityEvent signedElementSecurityEvent = this.signedElementEvents.get(j);
                if (!WSSUtils.pathMatches(signedElementSecurityEvent.getElementPath(), elementPath, false, false) || (elementSignatureToken = this.getEffectiveSignatureToken(signedElementSecurityEvent.getSecurityToken())) == null || !elementSignatureToken.getId().equals(securityToken.getId())) continue;
                if (!signedElements.contains(signedElementSecurityEvent)) {
                    signedElements.add(signedElementSecurityEvent);
                }
                found = true;
            }
            if (found) continue;
            return false;
        }
        return signedSupportingTokens.size() <= signedElements.size();
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
}

