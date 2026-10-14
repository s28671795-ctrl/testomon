/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.stax.assertionStates;

import javax.xml.namespace.QName;
import org.apache.wss4j.common.WSSPolicyException;
import org.apache.wss4j.policy.AssertionState;
import org.apache.wss4j.policy.model.AbstractSecurityAssertion;
import org.apache.wss4j.policy.model.Wss11;
import org.apache.wss4j.policy.stax.Assertable;
import org.apache.wss4j.policy.stax.DummyPolicyAsserter;
import org.apache.wss4j.policy.stax.PolicyAsserter;
import org.apache.wss4j.stax.securityEvent.WSSecurityEventConstants;
import org.apache.xml.security.stax.securityEvent.SecurityEvent;
import org.apache.xml.security.stax.securityEvent.SecurityEventConstants;

public class SignatureConfirmationAssertionState
extends AssertionState
implements Assertable {
    private PolicyAsserter policyAsserter;

    public SignatureConfirmationAssertionState(AbstractSecurityAssertion assertion, PolicyAsserter policyAsserter, boolean asserted) {
        super(assertion, asserted);
        this.policyAsserter = policyAsserter;
        if (this.policyAsserter == null) {
            this.policyAsserter = new DummyPolicyAsserter();
        }
        if (asserted) {
            String namespace = this.getAssertion().getName().getNamespaceURI();
            policyAsserter.assertPolicy(new QName(namespace, "RequireSignatureConfirmation"));
        }
    }

    @Override
    public SecurityEventConstants.Event[] getSecurityEventType() {
        return new SecurityEventConstants.Event[]{WSSecurityEventConstants.SIGNATURE_CONFIRMATION};
    }

    @Override
    public boolean assertEvent(SecurityEvent securityEvent) throws WSSPolicyException {
        Wss11 wss11 = (Wss11)this.getAssertion();
        String namespace = this.getAssertion().getName().getNamespaceURI();
        if (wss11.isRequireSignatureConfirmation()) {
            this.policyAsserter.assertPolicy(new QName(namespace, "RequireSignatureConfirmation"));
            this.setAsserted(true);
        } else {
            this.setAsserted(false);
            this.setErrorMessage("Signature confirmation elements must not be used");
            this.policyAsserter.unassertPolicy(new QName(namespace, "RequireSignatureConfirmation"), this.getErrorMessage());
        }
        return this.isAsserted();
    }
}

