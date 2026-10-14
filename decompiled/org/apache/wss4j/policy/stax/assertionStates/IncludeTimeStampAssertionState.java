/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.stax.assertionStates;

import javax.xml.namespace.QName;
import org.apache.wss4j.policy.AssertionState;
import org.apache.wss4j.policy.model.AbstractBinding;
import org.apache.wss4j.policy.model.AbstractSecurityAssertion;
import org.apache.wss4j.policy.stax.Assertable;
import org.apache.wss4j.policy.stax.DummyPolicyAsserter;
import org.apache.wss4j.policy.stax.PolicyAsserter;
import org.apache.wss4j.stax.securityEvent.WSSecurityEventConstants;
import org.apache.xml.security.stax.securityEvent.SecurityEvent;
import org.apache.xml.security.stax.securityEvent.SecurityEventConstants;

public class IncludeTimeStampAssertionState
extends AssertionState
implements Assertable {
    private PolicyAsserter policyAsserter;

    public IncludeTimeStampAssertionState(AbstractSecurityAssertion assertion, PolicyAsserter policyAsserter, boolean asserted) {
        super(assertion, asserted);
        this.policyAsserter = policyAsserter;
        if (this.policyAsserter == null) {
            this.policyAsserter = new DummyPolicyAsserter();
        }
        if (asserted) {
            String namespace = this.getAssertion().getName().getNamespaceURI();
            policyAsserter.assertPolicy(new QName(namespace, "IncludeTimestamp"));
        }
    }

    @Override
    public SecurityEventConstants.Event[] getSecurityEventType() {
        return new SecurityEventConstants.Event[]{WSSecurityEventConstants.TIMESTAMP};
    }

    @Override
    public boolean assertEvent(SecurityEvent securityEvent) {
        boolean isIncludeTimestamp = ((AbstractBinding)this.getAssertion()).isIncludeTimestamp();
        String namespace = this.getAssertion().getName().getNamespaceURI();
        if (isIncludeTimestamp) {
            this.setAsserted(true);
            this.policyAsserter.assertPolicy(new QName(namespace, "IncludeTimestamp"));
        } else {
            this.setAsserted(false);
            this.setErrorMessage("Timestamp must not be present");
            this.policyAsserter.unassertPolicy(new QName(namespace, "IncludeTimestamp"), this.getErrorMessage());
        }
        return this.isAsserted();
    }
}

