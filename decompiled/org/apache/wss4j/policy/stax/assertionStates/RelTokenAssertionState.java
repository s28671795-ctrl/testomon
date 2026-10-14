/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.stax.assertionStates;

import org.apache.wss4j.common.WSSPolicyException;
import org.apache.wss4j.policy.model.AbstractSecurityAssertion;
import org.apache.wss4j.policy.model.AbstractToken;
import org.apache.wss4j.policy.model.RelToken;
import org.apache.wss4j.policy.stax.PolicyAsserter;
import org.apache.wss4j.policy.stax.assertionStates.TokenAssertionState;
import org.apache.wss4j.stax.securityEvent.RelTokenSecurityEvent;
import org.apache.wss4j.stax.securityEvent.WSSecurityEventConstants;
import org.apache.xml.security.stax.securityEvent.SecurityEventConstants;
import org.apache.xml.security.stax.securityEvent.TokenSecurityEvent;
import org.apache.xml.security.stax.securityToken.SecurityToken;

public class RelTokenAssertionState
extends TokenAssertionState {
    public RelTokenAssertionState(AbstractSecurityAssertion assertion, boolean asserted, PolicyAsserter policyAsserter, boolean initiator) {
        super(assertion, asserted, policyAsserter, initiator);
    }

    @Override
    public SecurityEventConstants.Event[] getSecurityEventType() {
        return new SecurityEventConstants.Event[]{WSSecurityEventConstants.REL_TOKEN};
    }

    @Override
    public boolean assertToken(TokenSecurityEvent<? extends SecurityToken> tokenSecurityEvent, AbstractToken abstractToken) throws WSSPolicyException {
        if (!(tokenSecurityEvent instanceof RelTokenSecurityEvent)) {
            throw new WSSPolicyException("Expected a RelTokenSecurityEvent but got " + tokenSecurityEvent.getClass().getName());
        }
        RelTokenSecurityEvent relTokenSecurityEvent = (RelTokenSecurityEvent)tokenSecurityEvent;
        RelToken relToken = (RelToken)abstractToken;
        if (relToken.getIssuerName() != null && !relToken.getIssuerName().equals(relTokenSecurityEvent.getIssuerName())) {
            this.setErrorMessage("IssuerName in Policy (" + relToken.getIssuerName() + ") didn't match with the one in the RelToken (" + relTokenSecurityEvent.getIssuerName() + ")");
            this.getPolicyAsserter().unassertPolicy(this.getAssertion(), this.getErrorMessage());
            return false;
        }
        this.getPolicyAsserter().assertPolicy(this.getAssertion());
        return true;
    }
}

