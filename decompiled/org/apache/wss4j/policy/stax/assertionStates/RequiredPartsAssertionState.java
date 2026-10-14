/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.stax.assertionStates;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import javax.xml.namespace.QName;
import org.apache.wss4j.common.WSSPolicyException;
import org.apache.wss4j.policy.AssertionState;
import org.apache.wss4j.policy.model.AbstractSecurityAssertion;
import org.apache.wss4j.policy.model.Header;
import org.apache.wss4j.policy.model.RequiredParts;
import org.apache.wss4j.policy.stax.Assertable;
import org.apache.wss4j.policy.stax.DummyPolicyAsserter;
import org.apache.wss4j.policy.stax.PolicyAsserter;
import org.apache.wss4j.stax.ext.WSSConstants;
import org.apache.wss4j.stax.securityEvent.RequiredPartSecurityEvent;
import org.apache.wss4j.stax.securityEvent.WSSecurityEventConstants;
import org.apache.wss4j.stax.utils.WSSUtils;
import org.apache.xml.security.stax.securityEvent.SecurityEvent;
import org.apache.xml.security.stax.securityEvent.SecurityEventConstants;

public class RequiredPartsAssertionState
extends AssertionState
implements Assertable {
    private final Map<Header, Boolean> headers = new HashMap<Header, Boolean>();
    private PolicyAsserter policyAsserter;

    public RequiredPartsAssertionState(AbstractSecurityAssertion assertion, PolicyAsserter policyAsserter, boolean asserted) {
        super(assertion, asserted);
        RequiredParts requiredParts = (RequiredParts)assertion;
        for (int i = 0; i < requiredParts.getHeaders().size(); ++i) {
            Header header = requiredParts.getHeaders().get(i);
            this.headers.put(header, Boolean.FALSE);
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
        return new SecurityEventConstants.Event[]{WSSecurityEventConstants.REQUIRED_PART};
    }

    @Override
    public boolean assertEvent(SecurityEvent securityEvent) throws WSSPolicyException {
        RequiredPartSecurityEvent requiredPartSecurityEvent = (RequiredPartSecurityEvent)securityEvent;
        for (Map.Entry<Header, Boolean> next : this.headers.entrySet()) {
            Header header = next.getKey();
            QName headerQName = new QName(header.getNamespace(), header.getName() == null ? "" : header.getName());
            LinkedList<QName> header11Path = new LinkedList<QName>();
            header11Path.addAll(WSSConstants.SOAP_11_HEADER_PATH);
            header11Path.add(headerQName);
            if (!WSSUtils.pathMatches(header11Path, requiredPartSecurityEvent.getElementPath(), true, header.getName() == null)) continue;
            next.setValue(Boolean.TRUE);
            break;
        }
        this.policyAsserter.assertPolicy(this.getAssertion());
        return true;
    }

    @Override
    public boolean isAsserted() {
        this.clearErrorMessage();
        for (Map.Entry<Header, Boolean> next : this.headers.entrySet()) {
            if (!Boolean.FALSE.equals(next.getValue())) continue;
            this.setErrorMessage("Element " + next.getKey().toString() + " must be present");
            this.policyAsserter.unassertPolicy(this.getAssertion(), this.getErrorMessage());
            return false;
        }
        this.policyAsserter.assertPolicy(this.getAssertion());
        return true;
    }
}

