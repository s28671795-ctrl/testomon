/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.stax.assertionStates;

import java.util.List;
import javax.xml.namespace.QName;
import org.apache.wss4j.common.WSSPolicyException;
import org.apache.wss4j.policy.AssertionState;
import org.apache.wss4j.policy.model.AbstractSecurityAssertion;
import org.apache.wss4j.policy.stax.Assertable;
import org.apache.wss4j.policy.stax.DummyPolicyAsserter;
import org.apache.wss4j.policy.stax.PolicyAsserter;
import org.apache.wss4j.stax.securityEvent.WSSecurityEventConstants;
import org.apache.wss4j.stax.utils.WSSUtils;
import org.apache.xml.security.stax.securityEvent.AbstractSecuredElementSecurityEvent;
import org.apache.xml.security.stax.securityEvent.SecurityEvent;
import org.apache.xml.security.stax.securityEvent.SecurityEventConstants;

public class OnlySignEntireHeadersAndBodyAssertionState
extends AssertionState
implements Assertable {
    private String roleOrActor;
    private PolicyAsserter policyAsserter;

    public OnlySignEntireHeadersAndBodyAssertionState(AbstractSecurityAssertion assertion, PolicyAsserter policyAsserter, boolean asserted, String roleOrActor) {
        super(assertion, asserted);
        this.roleOrActor = roleOrActor;
        this.policyAsserter = policyAsserter;
        if (this.policyAsserter == null) {
            this.policyAsserter = new DummyPolicyAsserter();
        }
        if (asserted) {
            String namespace = this.getAssertion().getName().getNamespaceURI();
            policyAsserter.assertPolicy(new QName(namespace, "OnlySignEntireHeadersAndBody"));
        }
    }

    @Override
    public SecurityEventConstants.Event[] getSecurityEventType() {
        return new SecurityEventConstants.Event[]{WSSecurityEventConstants.SIGNED_PART, WSSecurityEventConstants.SignedElement};
    }

    @Override
    public boolean assertEvent(SecurityEvent securityEvent) throws WSSPolicyException {
        String namespace = this.getAssertion().getName().getNamespaceURI();
        AbstractSecuredElementSecurityEvent abstractSecuredElementSecurityEvent = (AbstractSecuredElementSecurityEvent)securityEvent;
        if (abstractSecuredElementSecurityEvent.isSigned() && !abstractSecuredElementSecurityEvent.isAttachment()) {
            List<QName> elementPath = abstractSecuredElementSecurityEvent.getElementPath();
            if (elementPath.size() == 4 && WSSUtils.isInSecurityHeader(abstractSecuredElementSecurityEvent.getXmlSecEvent(), elementPath, this.roleOrActor)) {
                this.setAsserted(true);
                this.policyAsserter.assertPolicy(new QName(namespace, "OnlySignEntireHeadersAndBody"));
                return true;
            }
            if (elementPath.size() == 3 && WSSUtils.isInSOAPHeader(elementPath)) {
                this.setAsserted(true);
                this.policyAsserter.assertPolicy(new QName(namespace, "OnlySignEntireHeadersAndBody"));
                return true;
            }
            if (elementPath.size() == 2 && WSSUtils.isInSOAPBody(elementPath)) {
                this.setAsserted(true);
                this.policyAsserter.assertPolicy(new QName(namespace, "OnlySignEntireHeadersAndBody"));
                return true;
            }
            this.setAsserted(false);
            this.setErrorMessage("OnlySignEntireHeadersAndBody not fulfilled, offending element: " + WSSUtils.pathAsString(elementPath));
            this.policyAsserter.unassertPolicy(new QName(namespace, "OnlySignEntireHeadersAndBody"), this.getErrorMessage());
            return false;
        }
        this.policyAsserter.assertPolicy(new QName(namespace, "OnlySignEntireHeadersAndBody"));
        return true;
    }
}

