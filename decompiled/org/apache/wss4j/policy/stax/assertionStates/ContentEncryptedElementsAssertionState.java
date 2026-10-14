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
import org.apache.wss4j.policy.model.ContentEncryptedElements;
import org.apache.wss4j.policy.model.XPath;
import org.apache.wss4j.policy.stax.Assertable;
import org.apache.wss4j.policy.stax.DummyPolicyAsserter;
import org.apache.wss4j.policy.stax.PolicyAsserter;
import org.apache.wss4j.policy.stax.PolicyUtils;
import org.apache.wss4j.stax.securityEvent.WSSecurityEventConstants;
import org.apache.wss4j.stax.utils.WSSUtils;
import org.apache.xml.security.stax.securityEvent.ContentEncryptedElementSecurityEvent;
import org.apache.xml.security.stax.securityEvent.SecurityEvent;
import org.apache.xml.security.stax.securityEvent.SecurityEventConstants;

public class ContentEncryptedElementsAssertionState
extends AssertionState
implements Assertable {
    private final List<List<QName>> pathElements = new ArrayList<List<QName>>();
    private PolicyAsserter policyAsserter;

    public ContentEncryptedElementsAssertionState(AbstractSecurityAssertion assertion, PolicyAsserter policyAsserter, boolean asserted) {
        super(assertion, asserted);
        ContentEncryptedElements contentEncryptedElements = (ContentEncryptedElements)assertion;
        for (int i = 0; i < contentEncryptedElements.getXPaths().size(); ++i) {
            XPath xPath = contentEncryptedElements.getXPaths().get(i);
            List<QName> elements = PolicyUtils.getElementPath(xPath);
            this.pathElements.add(elements);
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
        return new SecurityEventConstants.Event[]{WSSecurityEventConstants.ContentEncrypted};
    }

    @Override
    public boolean assertEvent(SecurityEvent securityEvent) throws WSSPolicyException {
        ContentEncryptedElementSecurityEvent contentEncryptedElementSecurityEvent = (ContentEncryptedElementSecurityEvent)securityEvent;
        for (List<QName> pathElements : this.pathElements) {
            if (!WSSUtils.pathMatches(pathElements, contentEncryptedElementSecurityEvent.getElementPath(), true, false)) continue;
            if (contentEncryptedElementSecurityEvent.isEncrypted()) {
                this.setAsserted(true);
                this.policyAsserter.assertPolicy(this.getAssertion());
                return true;
            }
            this.setAsserted(false);
            this.setErrorMessage("Content of element " + WSSUtils.pathAsString(contentEncryptedElementSecurityEvent.getElementPath()) + " must be encrypted");
            this.policyAsserter.unassertPolicy(this.getAssertion(), this.getErrorMessage());
            return false;
        }
        this.policyAsserter.assertPolicy(this.getAssertion());
        return true;
    }
}

