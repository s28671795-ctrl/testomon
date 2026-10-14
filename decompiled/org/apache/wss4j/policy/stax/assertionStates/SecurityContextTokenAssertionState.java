/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.stax.assertionStates;

import javax.xml.namespace.QName;
import org.apache.wss4j.common.WSSPolicyException;
import org.apache.wss4j.policy.model.AbstractSecurityAssertion;
import org.apache.wss4j.policy.model.AbstractToken;
import org.apache.wss4j.policy.model.SecurityContextToken;
import org.apache.wss4j.policy.stax.PolicyAsserter;
import org.apache.wss4j.policy.stax.assertionStates.TokenAssertionState;
import org.apache.wss4j.stax.securityEvent.SecurityContextTokenSecurityEvent;
import org.apache.wss4j.stax.securityEvent.WSSecurityEventConstants;
import org.apache.xml.security.stax.securityEvent.SecurityEventConstants;
import org.apache.xml.security.stax.securityEvent.TokenSecurityEvent;
import org.apache.xml.security.stax.securityToken.SecurityToken;

public class SecurityContextTokenAssertionState
extends TokenAssertionState {
    public SecurityContextTokenAssertionState(AbstractSecurityAssertion assertion, boolean asserted, PolicyAsserter policyAsserter, boolean initiator) {
        super(assertion, asserted, policyAsserter, initiator);
        if (asserted) {
            SecurityContextToken token = (SecurityContextToken)this.getAssertion();
            String namespace = token.getName().getNamespaceURI();
            if (token.isRequireExternalUriReference()) {
                this.getPolicyAsserter().assertPolicy(new QName(namespace, "RequireExternalUriReference"));
            }
            if (token.isSc10SecurityContextToken()) {
                this.getPolicyAsserter().assertPolicy(new QName(namespace, "SC10SecurityContextToken"));
            }
            if (token.isSc13SecurityContextToken()) {
                this.getPolicyAsserter().assertPolicy(new QName(namespace, "SC13SecurityContextToken"));
            }
        }
    }

    @Override
    public SecurityEventConstants.Event[] getSecurityEventType() {
        return new SecurityEventConstants.Event[]{WSSecurityEventConstants.SECURITY_CONTEXT_TOKEN};
    }

    @Override
    public boolean assertToken(TokenSecurityEvent<? extends SecurityToken> tokenSecurityEvent, AbstractToken abstractToken) throws WSSPolicyException {
        if (!(tokenSecurityEvent instanceof SecurityContextTokenSecurityEvent)) {
            throw new WSSPolicyException("Expected a SecurityContextTokenSecurityEvent but got " + tokenSecurityEvent.getClass().getName());
        }
        SecurityContextTokenSecurityEvent securityContextTokenSecurityEvent = (SecurityContextTokenSecurityEvent)tokenSecurityEvent;
        SecurityContextToken securityContextToken = (SecurityContextToken)abstractToken;
        if (securityContextToken.getIssuerName() != null && !securityContextToken.getIssuerName().equals(securityContextTokenSecurityEvent.getIssuerName())) {
            this.setErrorMessage("IssuerName in Policy (" + securityContextToken.getIssuerName() + ") didn't match with the one in the SecurityContextToken (" + securityContextTokenSecurityEvent.getIssuerName() + ")");
            this.getPolicyAsserter().unassertPolicy(this.getAssertion(), this.getErrorMessage());
            return false;
        }
        String namespace = this.getAssertion().getName().getNamespaceURI();
        if (securityContextToken.isRequireExternalUriReference()) {
            if (!securityContextTokenSecurityEvent.isExternalUriRef()) {
                this.setErrorMessage("Policy enforces externalUriRef but we didn't got one");
                this.getPolicyAsserter().unassertPolicy(new QName(namespace, "RequireExternalUriReference"), this.getErrorMessage());
                return false;
            }
            this.getPolicyAsserter().assertPolicy(new QName(namespace, "RequireExternalUriReference"));
        }
        if (securityContextToken.isSc10SecurityContextToken()) {
            this.getPolicyAsserter().assertPolicy(new QName(namespace, "SC10SecurityContextToken"));
        }
        if (securityContextToken.isSc13SecurityContextToken()) {
            this.getPolicyAsserter().assertPolicy(new QName(namespace, "SC13SecurityContextToken"));
        }
        this.getPolicyAsserter().assertPolicy(this.getAssertion());
        return true;
    }
}

