/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.stax.assertionStates;

import java.util.LinkedList;
import javax.xml.namespace.QName;
import org.apache.wss4j.common.WSSPolicyException;
import org.apache.wss4j.policy.AssertionState;
import org.apache.wss4j.policy.model.AbstractSecurityAssertion;
import org.apache.wss4j.policy.model.Header;
import org.apache.wss4j.policy.model.SignedParts;
import org.apache.wss4j.policy.stax.Assertable;
import org.apache.wss4j.policy.stax.DummyPolicyAsserter;
import org.apache.wss4j.policy.stax.PolicyAsserter;
import org.apache.wss4j.stax.ext.WSSConstants;
import org.apache.wss4j.stax.securityEvent.SignedPartSecurityEvent;
import org.apache.wss4j.stax.securityEvent.WSSecurityEventConstants;
import org.apache.wss4j.stax.utils.WSSUtils;
import org.apache.xml.security.stax.securityEvent.SecurityEvent;
import org.apache.xml.security.stax.securityEvent.SecurityEventConstants;

public class SignedPartsAssertionState
extends AssertionState
implements Assertable {
    private int attachmentCount;
    private int signedAttachmentCount;
    private boolean signedAttachmentRequired;
    private PolicyAsserter policyAsserter;

    public SignedPartsAssertionState(AbstractSecurityAssertion assertion, PolicyAsserter policyAsserter, boolean asserted, int attachmentCount) {
        super(assertion, asserted);
        this.attachmentCount = attachmentCount;
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
        return new SecurityEventConstants.Event[]{WSSecurityEventConstants.SIGNED_PART};
    }

    @Override
    public boolean assertEvent(SecurityEvent securityEvent) throws WSSPolicyException {
        SignedPartSecurityEvent signedPartSecurityEvent = (SignedPartSecurityEvent)securityEvent;
        SignedParts signedParts = (SignedParts)this.getAssertion();
        if (signedParts.getAttachments() != null) {
            this.signedAttachmentRequired = true;
            if (signedPartSecurityEvent.isAttachment()) {
                ++this.signedAttachmentCount;
                this.setAsserted(true);
                this.policyAsserter.assertPolicy(this.getAssertion());
                return true;
            }
        }
        if (signedParts.isBody() && WSSUtils.pathMatches(WSSConstants.SOAP_11_BODY_PATH, signedPartSecurityEvent.getElementPath(), true, false)) {
            if (signedPartSecurityEvent.isSigned()) {
                this.setAsserted(true);
                this.policyAsserter.assertPolicy(this.getAssertion());
                return true;
            }
            this.setAsserted(false);
            this.setErrorMessage("Element " + WSSUtils.pathAsString(signedPartSecurityEvent.getElementPath()) + " must be signed");
            this.policyAsserter.unassertPolicy(this.getAssertion(), this.getErrorMessage());
            return false;
        }
        if (signedParts.isSignAllHeaders()) {
            if (signedPartSecurityEvent.isSigned()) {
                this.setAsserted(true);
                this.policyAsserter.assertPolicy(this.getAssertion());
                return true;
            }
            this.setAsserted(false);
            this.setErrorMessage("Element " + WSSUtils.pathAsString(signedPartSecurityEvent.getElementPath()) + " must be signed");
            this.policyAsserter.unassertPolicy(this.getAssertion(), this.getErrorMessage());
            return false;
        }
        for (int i = 0; i < signedParts.getHeaders().size(); ++i) {
            Header header = signedParts.getHeaders().get(i);
            QName headerQName = new QName(header.getNamespace(), header.getName() == null ? "" : header.getName());
            LinkedList<QName> header11Path = new LinkedList<QName>();
            header11Path.addAll(WSSConstants.SOAP_11_HEADER_PATH);
            header11Path.add(headerQName);
            if (!WSSUtils.pathMatches(header11Path, signedPartSecurityEvent.getElementPath(), true, header.getName() == null)) continue;
            if (signedPartSecurityEvent.isSigned()) {
                this.setAsserted(true);
                this.policyAsserter.assertPolicy(this.getAssertion());
                return true;
            }
            this.setAsserted(false);
            this.setErrorMessage("Element " + WSSUtils.pathAsString(signedPartSecurityEvent.getElementPath()) + " must be signed");
            this.policyAsserter.unassertPolicy(this.getAssertion(), this.getErrorMessage());
            return false;
        }
        this.policyAsserter.assertPolicy(this.getAssertion());
        return true;
    }

    @Override
    public boolean isAsserted() {
        if (this.signedAttachmentRequired && this.signedAttachmentCount < this.attachmentCount) {
            return false;
        }
        return super.isAsserted();
    }
}

