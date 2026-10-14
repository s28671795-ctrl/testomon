/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.stax.assertionStates;

import org.apache.wss4j.common.WSSPolicyException;
import org.apache.wss4j.policy.model.AbstractSecurityAssertion;
import org.apache.wss4j.policy.model.AbstractToken;
import org.apache.wss4j.policy.model.SpnegoContextToken;
import org.apache.wss4j.policy.stax.PolicyAsserter;
import org.apache.wss4j.policy.stax.assertionStates.TokenAssertionState;
import org.apache.wss4j.stax.securityEvent.SecurityContextTokenSecurityEvent;
import org.apache.wss4j.stax.securityEvent.WSSecurityEventConstants;
import org.apache.xml.security.stax.securityEvent.SecurityEventConstants;
import org.apache.xml.security.stax.securityEvent.TokenSecurityEvent;
import org.apache.xml.security.stax.securityToken.SecurityToken;

public class SpnegoContextTokenAssertionState
extends TokenAssertionState {
    public SpnegoContextTokenAssertionState(AbstractSecurityAssertion assertion, boolean asserted, PolicyAsserter policyAsserter, boolean initiator) {
        super(assertion, asserted, policyAsserter, initiator);
        if (asserted) {
            this.getPolicyAsserter().assertPolicy(this.getAssertion());
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
        SpnegoContextToken spnegoContextToken = (SpnegoContextToken)abstractToken;
        SecurityContextTokenSecurityEvent spnegoContextTokenSecurityEvent = (SecurityContextTokenSecurityEvent)tokenSecurityEvent;
        if (spnegoContextToken.getIssuerName() != null && !spnegoContextToken.getIssuerName().equals(spnegoContextTokenSecurityEvent.getIssuerName())) {
            this.setErrorMessage("IssuerName in Policy (" + spnegoContextToken.getIssuerName() + ") didn't match with the one in the IssuedToken (" + spnegoContextTokenSecurityEvent.getIssuerName() + ")");
            this.getPolicyAsserter().unassertPolicy(this.getAssertion(), this.getErrorMessage());
            return false;
        }
        this.getPolicyAsserter().assertPolicy(this.getAssertion());
        return true;
    }
}

