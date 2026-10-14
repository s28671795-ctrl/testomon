/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.stax.assertionStates;

import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.apache.wss4j.common.WSSPolicyException;
import org.apache.wss4j.policy.AssertionState;
import org.apache.wss4j.policy.model.AbstractSecurityAssertion;
import org.apache.wss4j.policy.model.SignedElements;
import org.apache.wss4j.policy.model.XPath;
import org.apache.wss4j.policy.stax.Assertable;
import org.apache.wss4j.policy.stax.DummyPolicyAsserter;
import org.apache.wss4j.policy.stax.PolicyAsserter;
import org.apache.wss4j.policy.stax.PolicyUtils;
import org.apache.wss4j.stax.securityEvent.WSSecurityEventConstants;
import org.apache.wss4j.stax.utils.WSSUtils;
import org.apache.xml.security.stax.securityEvent.AbstractSecuredElementSecurityEvent;
import org.apache.xml.security.stax.securityEvent.SecurityEvent;
import org.apache.xml.security.stax.securityEvent.SecurityEventConstants;

public class SignedElementsAssertionState
extends AssertionState
implements Assertable {
    private final List<List<QName>> pathElements = new ArrayList<List<QName>>();
    private PolicyAsserter policyAsserter;

    public SignedElementsAssertionState(AbstractSecurityAssertion assertion, PolicyAsserter policyAsserter, boolean asserted) {
        super(assertion, asserted);
        if (assertion instanceof SignedElements) {
            SignedElements signedElements = (SignedElements)assertion;
            for (int i = 0; i < signedElements.getXPaths().size(); ++i) {
                XPath xPath = signedElements.getXPaths().get(i);
                List<QName> elements = PolicyUtils.getElementPath(xPath);
                this.pathElements.add(elements);
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

    @Override
    public SecurityEventConstants.Event[] getSecurityEventType() {
        return new SecurityEventConstants.Event[]{SecurityEventConstants.SignedElement, WSSecurityEventConstants.SIGNED_PART};
    }

    public void addElement(List<QName> pathElement) {
        this.pathElements.add(pathElement);
    }

    @Override
    public boolean assertEvent(SecurityEvent securityEvent) throws WSSPolicyException {
        AbstractSecuredElementSecurityEvent signedSecurityEvent = (AbstractSecuredElementSecurityEvent)securityEvent;
        for (List<QName> pathElements : this.pathElements) {
            if (!WSSUtils.pathMatches(pathElements, signedSecurityEvent.getElementPath(), true, false)) continue;
            if (signedSecurityEvent.isSigned()) {
                this.setAsserted(true);
                this.policyAsserter.assertPolicy(this.getAssertion());
                return true;
            }
            this.setAsserted(false);
            this.setErrorMessage("Element " + WSSUtils.pathAsString(signedSecurityEvent.getElementPath()) + " must be signed");
            this.policyAsserter.unassertPolicy(this.getAssertion(), this.getErrorMessage());
            return false;
        }
        this.policyAsserter.assertPolicy(this.getAssertion());
        return true;
    }
}

