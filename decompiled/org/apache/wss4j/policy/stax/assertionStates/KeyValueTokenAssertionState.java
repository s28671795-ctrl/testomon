/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.stax.assertionStates;

import javax.xml.namespace.QName;
import org.apache.wss4j.common.WSSPolicyException;
import org.apache.wss4j.policy.model.AbstractSecurityAssertion;
import org.apache.wss4j.policy.model.AbstractToken;
import org.apache.wss4j.policy.model.KeyValueToken;
import org.apache.wss4j.policy.stax.PolicyAsserter;
import org.apache.wss4j.policy.stax.assertionStates.TokenAssertionState;
import org.apache.wss4j.stax.securityEvent.KeyValueTokenSecurityEvent;
import org.apache.wss4j.stax.securityEvent.WSSecurityEventConstants;
import org.apache.wss4j.stax.securityToken.KeyValueSecurityToken;
import org.apache.wss4j.stax.securityToken.RsaKeyValueSecurityToken;
import org.apache.xml.security.stax.securityEvent.SecurityEventConstants;
import org.apache.xml.security.stax.securityEvent.TokenSecurityEvent;
import org.apache.xml.security.stax.securityToken.SecurityToken;

public class KeyValueTokenAssertionState
extends TokenAssertionState {
    public KeyValueTokenAssertionState(AbstractSecurityAssertion assertion, boolean asserted, PolicyAsserter policyAsserter, boolean initiator) {
        super(assertion, asserted, policyAsserter, initiator);
        if (asserted) {
            KeyValueToken token = (KeyValueToken)this.getAssertion();
            String namespace = token.getName().getNamespaceURI();
            if (token.isRsaKeyValue()) {
                this.getPolicyAsserter().assertPolicy(new QName(namespace, "RsaKeyValue"));
            }
        }
    }

    @Override
    public SecurityEventConstants.Event[] getSecurityEventType() {
        return new SecurityEventConstants.Event[]{WSSecurityEventConstants.KeyValueToken};
    }

    @Override
    public boolean assertToken(TokenSecurityEvent<? extends SecurityToken> tokenSecurityEvent, AbstractToken abstractToken) throws WSSPolicyException {
        if (!(tokenSecurityEvent instanceof KeyValueTokenSecurityEvent)) {
            throw new WSSPolicyException("Expected a KeyValueTokenSecurityEvent but got " + tokenSecurityEvent.getClass().getName());
        }
        KeyValueTokenSecurityEvent keyValueTokenSecurityEvent = (KeyValueTokenSecurityEvent)tokenSecurityEvent;
        KeyValueToken keyValueToken = (KeyValueToken)abstractToken;
        String namespace = this.getAssertion().getName().getNamespaceURI();
        if (keyValueToken.isRsaKeyValue()) {
            if (!(keyValueTokenSecurityEvent.getSecurityToken() instanceof RsaKeyValueSecurityToken)) {
                this.setErrorMessage("Policy enforces that a RsaKeyValue must be present in the KeyValueToken but we got a " + ((KeyValueSecurityToken)keyValueTokenSecurityEvent.getSecurityToken()).getClass().getSimpleName());
                this.getPolicyAsserter().unassertPolicy(new QName(namespace, "RsaKeyValue"), this.getErrorMessage());
                return false;
            }
            this.getPolicyAsserter().assertPolicy(new QName(namespace, "RsaKeyValue"));
        }
        this.getPolicyAsserter().assertPolicy(this.getAssertion());
        return true;
    }
}

