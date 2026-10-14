/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.stax.assertionStates;

import org.apache.wss4j.policy.model.AbstractSecurityAssertion;
import org.apache.wss4j.policy.stax.PolicyAsserter;
import org.apache.wss4j.policy.stax.assertionStates.SecurityContextTokenAssertionState;
import org.apache.wss4j.stax.securityEvent.WSSecurityEventConstants;
import org.apache.xml.security.stax.securityEvent.SecurityEventConstants;

public class SecureConversationTokenAssertionState
extends SecurityContextTokenAssertionState {
    public SecureConversationTokenAssertionState(AbstractSecurityAssertion assertion, boolean asserted, PolicyAsserter policyAsserter, boolean initiator) {
        super(assertion, asserted, policyAsserter, initiator);
    }

    @Override
    public SecurityEventConstants.Event[] getSecurityEventType() {
        return new SecurityEventConstants.Event[]{WSSecurityEventConstants.SECURITY_CONTEXT_TOKEN};
    }
}

