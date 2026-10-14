/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.stax.assertionStates;

import java.util.LinkedList;
import javax.xml.namespace.QName;
import org.apache.wss4j.common.WSSPolicyException;
import org.apache.wss4j.policy.AssertionState;
import org.apache.wss4j.policy.model.AbstractSecurityAssertion;
import org.apache.wss4j.policy.model.EncryptedParts;
import org.apache.wss4j.policy.model.Header;
import org.apache.wss4j.policy.stax.Assertable;
import org.apache.wss4j.policy.stax.DummyPolicyAsserter;
import org.apache.wss4j.policy.stax.PolicyAsserter;
import org.apache.wss4j.stax.ext.WSSConstants;
import org.apache.wss4j.stax.securityEvent.EncryptedPartSecurityEvent;
import org.apache.wss4j.stax.securityEvent.WSSecurityEventConstants;
import org.apache.wss4j.stax.utils.WSSUtils;
import org.apache.xml.security.stax.securityEvent.SecurityEvent;
import org.apache.xml.security.stax.securityEvent.SecurityEventConstants;

public class EncryptedPartsAssertionState
extends AssertionState
implements Assertable {
    private int attachmentCount;
    private int encryptedAttachmentCount;
    private boolean encryptedAttachmentRequired;
    private PolicyAsserter policyAsserter;

    public EncryptedPartsAssertionState(AbstractSecurityAssertion assertion, PolicyAsserter policyAsserter, boolean asserted, int attachmentCount) {
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
        return new SecurityEventConstants.Event[]{WSSecurityEventConstants.ENCRYPTED_PART};
    }

    @Override
    public boolean assertEvent(SecurityEvent securityEvent) throws WSSPolicyException {
        EncryptedPartSecurityEvent encryptedPartSecurityEvent = (EncryptedPartSecurityEvent)securityEvent;
        EncryptedParts encryptedParts = (EncryptedParts)this.getAssertion();
        if (encryptedParts.getAttachments() != null) {
            this.encryptedAttachmentRequired = true;
            if (encryptedPartSecurityEvent.isAttachment()) {
                ++this.encryptedAttachmentCount;
                this.setAsserted(true);
                this.policyAsserter.assertPolicy(this.getAssertion());
                return true;
            }
        }
        if (encryptedParts.isBody() && WSSUtils.isInSOAPBody(encryptedPartSecurityEvent.getElementPath())) {
            if (encryptedPartSecurityEvent.isEncrypted()) {
                this.setAsserted(true);
                this.policyAsserter.assertPolicy(this.getAssertion());
                return true;
            }
            this.setAsserted(false);
            this.setErrorMessage("SOAP-Body must be encrypted");
            this.policyAsserter.unassertPolicy(this.getAssertion(), this.getErrorMessage());
            return false;
        }
        for (int i = 0; i < encryptedParts.getHeaders().size(); ++i) {
            Header header = encryptedParts.getHeaders().get(i);
            QName headerQName = new QName(header.getNamespace(), header.getName() == null ? "" : header.getName());
            LinkedList<QName> header11Path = new LinkedList<QName>();
            header11Path.addAll(WSSConstants.SOAP_11_HEADER_PATH);
            header11Path.add(headerQName);
            if (!WSSUtils.pathMatches(header11Path, encryptedPartSecurityEvent.getElementPath(), true, header.getName() == null)) continue;
            if (encryptedPartSecurityEvent.isEncrypted()) {
                this.setAsserted(true);
                this.policyAsserter.assertPolicy(this.getAssertion());
                return true;
            }
            this.setAsserted(false);
            this.setErrorMessage("Element " + WSSUtils.pathAsString(encryptedPartSecurityEvent.getElementPath()) + " must be encrypted");
            this.policyAsserter.unassertPolicy(this.getAssertion(), this.getErrorMessage());
            return false;
        }
        this.policyAsserter.assertPolicy(this.getAssertion());
        return true;
    }

    @Override
    public boolean isAsserted() {
        if (this.encryptedAttachmentRequired && this.encryptedAttachmentCount < this.attachmentCount) {
            return false;
        }
        return super.isAsserted();
    }
}

