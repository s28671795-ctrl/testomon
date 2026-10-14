/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.stax.assertionStates;

import javax.xml.namespace.QName;
import org.apache.wss4j.common.WSSPolicyException;
import org.apache.wss4j.common.saml.SamlAssertionWrapper;
import org.apache.wss4j.policy.model.AbstractSecurityAssertion;
import org.apache.wss4j.policy.model.AbstractToken;
import org.apache.wss4j.policy.model.SamlToken;
import org.apache.wss4j.policy.stax.PolicyAsserter;
import org.apache.wss4j.policy.stax.assertionStates.TokenAssertionState;
import org.apache.wss4j.stax.securityEvent.SamlTokenSecurityEvent;
import org.apache.wss4j.stax.securityEvent.WSSecurityEventConstants;
import org.apache.wss4j.stax.securityToken.SamlSecurityToken;
import org.apache.wss4j.stax.securityToken.WSSecurityTokenConstants;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.securityEvent.SecurityEventConstants;
import org.apache.xml.security.stax.securityEvent.TokenSecurityEvent;
import org.apache.xml.security.stax.securityToken.SecurityToken;
import org.apache.xml.security.stax.securityToken.SecurityTokenConstants;
import org.opensaml.saml.common.SAMLVersion;

public class SamlTokenAssertionState
extends TokenAssertionState {
    public SamlTokenAssertionState(AbstractSecurityAssertion assertion, boolean asserted, PolicyAsserter policyAsserter, boolean initiator) {
        super(assertion, asserted, policyAsserter, initiator);
        if (asserted) {
            SamlToken token = (SamlToken)this.getAssertion();
            String namespace = token.getName().getNamespaceURI();
            if (token.isRequireKeyIdentifierReference()) {
                this.getPolicyAsserter().assertPolicy(new QName(namespace, "RequireKeyIdentifierReference"));
            }
            if (token.getSamlTokenType() != null) {
                this.getPolicyAsserter().assertPolicy(new QName(namespace, token.getSamlTokenType().name()));
            }
        }
    }

    @Override
    public SecurityEventConstants.Event[] getSecurityEventType() {
        return new SecurityEventConstants.Event[]{WSSecurityEventConstants.SAML_TOKEN};
    }

    @Override
    public boolean assertToken(TokenSecurityEvent<? extends SecurityToken> tokenSecurityEvent, AbstractToken abstractToken) throws WSSPolicyException, XMLSecurityException {
        if (!(tokenSecurityEvent instanceof SamlTokenSecurityEvent)) {
            throw new WSSPolicyException("Expected a SamlTokenSecurityEvent but got " + tokenSecurityEvent.getClass().getName());
        }
        SamlTokenSecurityEvent samlTokenSecurityEvent = (SamlTokenSecurityEvent)tokenSecurityEvent;
        SamlToken samlToken = (SamlToken)abstractToken;
        if (samlToken.getIssuerName() != null && !samlToken.getIssuerName().equals(samlTokenSecurityEvent.getIssuerName())) {
            this.setErrorMessage("IssuerName in Policy (" + samlToken.getIssuerName() + ") didn't match with the one in the SamlToken (" + samlTokenSecurityEvent.getIssuerName() + ")");
            this.getPolicyAsserter().unassertPolicy(this.getAssertion(), this.getErrorMessage());
            return false;
        }
        String namespace = this.getAssertion().getName().getNamespaceURI();
        if (samlToken.isRequireKeyIdentifierReference()) {
            SecurityTokenConstants.KeyIdentifier keyIdentifier = ((SamlSecurityToken)samlTokenSecurityEvent.getSecurityToken()).getKeyIdentifier();
            if (!WSSecurityTokenConstants.KeyIdentifier_X509KeyIdentifier.equals(keyIdentifier)) {
                this.setErrorMessage("Policy enforces KeyIdentifierReference but we got " + ((SamlSecurityToken)samlTokenSecurityEvent.getSecurityToken()).getTokenType());
                this.getPolicyAsserter().unassertPolicy(new QName(namespace, "RequireKeyIdentifierReference"), this.getErrorMessage());
                return false;
            }
            this.getPolicyAsserter().assertPolicy(new QName(namespace, "RequireKeyIdentifierReference"));
        }
        if (samlToken.getSamlTokenType() != null) {
            SamlAssertionWrapper samlAssertionWrapper = samlTokenSecurityEvent.getSamlAssertionWrapper();
            switch (samlToken.getSamlTokenType()) {
                case WssSamlV11Token10: {
                    if (samlAssertionWrapper.getSamlVersion() != SAMLVersion.VERSION_11) {
                        this.setErrorMessage("Policy enforces SamlVersion11Profile10 but we got " + samlAssertionWrapper.getSamlVersion());
                        this.getPolicyAsserter().unassertPolicy(new QName(namespace, samlToken.getSamlTokenType().name()), this.getErrorMessage());
                        return false;
                    }
                    this.getPolicyAsserter().assertPolicy(new QName(namespace, samlToken.getSamlTokenType().name()));
                    break;
                }
                case WssSamlV11Token11: {
                    if (samlAssertionWrapper.getSamlVersion() != SAMLVersion.VERSION_11) {
                        this.setErrorMessage("Policy enforces SamlVersion11Profile11 but we got " + samlAssertionWrapper.getSamlVersion());
                        this.getPolicyAsserter().unassertPolicy(new QName(namespace, samlToken.getSamlTokenType().name()), this.getErrorMessage());
                        return false;
                    }
                    this.getPolicyAsserter().assertPolicy(new QName(namespace, samlToken.getSamlTokenType().name()));
                    break;
                }
                case WssSamlV20Token11: {
                    if (samlAssertionWrapper.getSamlVersion() != SAMLVersion.VERSION_20) {
                        this.setErrorMessage("Policy enforces SamlVersion20Profile11 but we got " + samlAssertionWrapper.getSamlVersion());
                        this.getPolicyAsserter().unassertPolicy(new QName(namespace, samlToken.getSamlTokenType().name()), this.getErrorMessage());
                        return false;
                    }
                    this.getPolicyAsserter().assertPolicy(new QName(namespace, samlToken.getSamlTokenType().name()));
                    break;
                }
                case WssSamlV10Token10: 
                case WssSamlV10Token11: {
                    this.setErrorMessage("Unsupported token type: " + (Object)((Object)samlToken.getSamlTokenType()));
                    this.getPolicyAsserter().unassertPolicy(new QName(namespace, samlToken.getSamlTokenType().name()), this.getErrorMessage());
                    return false;
                }
            }
        }
        this.getPolicyAsserter().assertPolicy(this.getAssertion());
        return true;
    }
}

