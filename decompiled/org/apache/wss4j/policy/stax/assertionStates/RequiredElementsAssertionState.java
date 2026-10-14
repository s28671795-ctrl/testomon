/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.stax.assertionStates;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import org.apache.wss4j.common.WSSPolicyException;
import org.apache.wss4j.policy.AssertionState;
import org.apache.wss4j.policy.model.AbstractSecurityAssertion;
import org.apache.wss4j.policy.model.RequiredElements;
import org.apache.wss4j.policy.model.XPath;
import org.apache.wss4j.policy.stax.Assertable;
import org.apache.wss4j.policy.stax.DummyPolicyAsserter;
import org.apache.wss4j.policy.stax.PolicyAsserter;
import org.apache.wss4j.policy.stax.PolicyUtils;
import org.apache.wss4j.stax.securityEvent.RequiredElementSecurityEvent;
import org.apache.wss4j.stax.securityEvent.WSSecurityEventConstants;
import org.apache.wss4j.stax.utils.WSSUtils;
import org.apache.xml.security.stax.securityEvent.SecurityEvent;
import org.apache.xml.security.stax.securityEvent.SecurityEventConstants;

public class RequiredElementsAssertionState
extends AssertionState
implements Assertable {
    private final Map<List<QName>, Boolean> pathElements = new HashMap<List<QName>, Boolean>();
    private PolicyAsserter policyAsserter;

    public RequiredElementsAssertionState(AbstractSecurityAssertion assertion, PolicyAsserter policyAsserter, boolean asserted) {
        super(assertion, asserted);
        if (assertion instanceof RequiredElements) {
            RequiredElements requiredElements = (RequiredElements)assertion;
            for (int i = 0; i < requiredElements.getXPaths().size(); ++i) {
                XPath xPath = requiredElements.getXPaths().get(i);
                List<QName> elements = PolicyUtils.getElementPath(xPath);
                this.pathElements.put(elements, Boolean.FALSE);
            }
        }
        this.policyAsserter = policyAsserter;
        if (this.policyAsserter == null) {
            this.policyAsserter = new DummyPolicyAsserter();
        }
        if (asserted) {
            policyAsserter.assertPolicy(this.getAssertion());
        }
    }

    public void addElement(List<QName> pathElement) {
        this.pathElements.put(pathElement, Boolean.FALSE);
    }

    @Override
    public SecurityEventConstants.Event[] getSecurityEventType() {
        return new SecurityEventConstants.Event[]{WSSecurityEventConstants.REQUIRED_ELEMENT};
    }

    @Override
    public boolean assertEvent(SecurityEvent securityEvent) throws WSSPolicyException {
        RequiredElementSecurityEvent requiredElementSecurityEvent = (RequiredElementSecurityEvent)securityEvent;
        for (Map.Entry<List<QName>, Boolean> next : this.pathElements.entrySet()) {
            List<QName> qNameList = next.getKey();
            if (!WSSUtils.pathMatches(qNameList, requiredElementSecurityEvent.getElementPath(), true, false)) continue;
            next.setValue(Boolean.TRUE);
            break;
        }
        this.policyAsserter.assertPolicy(this.getAssertion());
        return true;
    }

    @Override
    public boolean isAsserted() {
        this.clearErrorMessage();
        for (Map.Entry<List<QName>, Boolean> next : this.pathElements.entrySet()) {
            if (!Boolean.FALSE.equals(next.getValue())) continue;
            this.setErrorMessage("Element " + WSSUtils.pathAsString(next.getKey()) + " must be present");
            this.policyAsserter.unassertPolicy(this.getAssertion(), this.getErrorMessage());
            return false;
        }
        this.policyAsserter.assertPolicy(this.getAssertion());
        return true;
    }
}

