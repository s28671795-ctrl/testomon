/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.stax.assertionStates;

import java.util.List;
import javax.xml.namespace.QName;
import org.apache.wss4j.common.WSSPolicyException;
import org.apache.wss4j.policy.AssertionState;
import org.apache.wss4j.policy.model.AbstractSecurityAssertion;
import org.apache.wss4j.policy.model.AbstractSymmetricAsymmetricBinding;
import org.apache.wss4j.policy.stax.Assertable;
import org.apache.wss4j.policy.stax.DummyPolicyAsserter;
import org.apache.wss4j.policy.stax.PolicyAsserter;
import org.apache.wss4j.stax.securityEvent.EncryptedPartSecurityEvent;
import org.apache.wss4j.stax.securityEvent.SignedPartSecurityEvent;
import org.apache.wss4j.stax.securityEvent.WSSecurityEventConstants;
import org.apache.wss4j.stax.utils.WSSUtils;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.securityEvent.ContentEncryptedElementSecurityEvent;
import org.apache.xml.security.stax.securityEvent.EncryptedElementSecurityEvent;
import org.apache.xml.security.stax.securityEvent.SecurityEvent;
import org.apache.xml.security.stax.securityEvent.SecurityEventConstants;
import org.apache.xml.security.stax.securityEvent.SignedElementSecurityEvent;

public class ProtectionOrderAssertionState
extends AssertionState
implements Assertable {
    private PolicyAsserter policyAsserter;

    public ProtectionOrderAssertionState(AbstractSecurityAssertion assertion, PolicyAsserter policyAsserter, boolean asserted) {
        super(assertion, asserted);
        this.policyAsserter = policyAsserter;
        if (this.policyAsserter == null) {
            this.policyAsserter = new DummyPolicyAsserter();
        }
        if (asserted) {
            String namespace = this.getAssertion().getName().getNamespaceURI();
            AbstractSymmetricAsymmetricBinding.ProtectionOrder protectionOrder = ((AbstractSymmetricAsymmetricBinding)this.getAssertion()).getProtectionOrder();
            switch (protectionOrder) {
                case SignBeforeEncrypting: {
                    policyAsserter.assertPolicy(new QName(namespace, "SignBeforeEncrypting"));
                    break;
                }
                case EncryptBeforeSigning: {
                    policyAsserter.assertPolicy(new QName(namespace, "EncryptBeforeSigning"));
                }
            }
        }
    }

    @Override
    public SecurityEventConstants.Event[] getSecurityEventType() {
        return new SecurityEventConstants.Event[]{SecurityEventConstants.SignedElement, WSSecurityEventConstants.SIGNED_PART, WSSecurityEventConstants.EncryptedElement, WSSecurityEventConstants.ENCRYPTED_PART, WSSecurityEventConstants.ContentEncrypted};
    }

    @Override
    public boolean assertEvent(SecurityEvent securityEvent) throws WSSPolicyException {
        AbstractSymmetricAsymmetricBinding.ProtectionOrder protectionOrder = ((AbstractSymmetricAsymmetricBinding)this.getAssertion()).getProtectionOrder();
        SecurityEventConstants.Event event = securityEvent.getSecurityEventType();
        if (WSSecurityEventConstants.SignedElement.equals(event)) {
            SignedElementSecurityEvent signedElementSecurityEvent = (SignedElementSecurityEvent)securityEvent;
            if (!signedElementSecurityEvent.isSigned()) {
                return true;
            }
            List<XMLSecurityConstants.ContentType> contentTypes = signedElementSecurityEvent.getProtectionOrder();
            this.testProtectionOrder(protectionOrder, contentTypes, signedElementSecurityEvent.getElementPath());
        } else if (WSSecurityEventConstants.SIGNED_PART.equals(event)) {
            SignedPartSecurityEvent signedPartSecurityEvent = (SignedPartSecurityEvent)securityEvent;
            if (!signedPartSecurityEvent.isSigned()) {
                return true;
            }
            List<XMLSecurityConstants.ContentType> contentTypes = signedPartSecurityEvent.getProtectionOrder();
            this.testProtectionOrder(protectionOrder, contentTypes, signedPartSecurityEvent.getElementPath());
        } else if (WSSecurityEventConstants.EncryptedElement.equals(event)) {
            EncryptedElementSecurityEvent encryptedElementSecurityEvent = (EncryptedElementSecurityEvent)securityEvent;
            if (!encryptedElementSecurityEvent.isEncrypted()) {
                return true;
            }
            List<XMLSecurityConstants.ContentType> contentTypes = encryptedElementSecurityEvent.getProtectionOrder();
            this.testProtectionOrder(protectionOrder, contentTypes, encryptedElementSecurityEvent.getElementPath());
        } else if (WSSecurityEventConstants.ENCRYPTED_PART.equals(event)) {
            EncryptedPartSecurityEvent encryptedPartSecurityEvent = (EncryptedPartSecurityEvent)securityEvent;
            if (!encryptedPartSecurityEvent.isEncrypted()) {
                return true;
            }
            List<XMLSecurityConstants.ContentType> contentTypes = encryptedPartSecurityEvent.getProtectionOrder();
            this.testProtectionOrder(protectionOrder, contentTypes, encryptedPartSecurityEvent.getElementPath());
        } else if (WSSecurityEventConstants.ContentEncrypted.equals(event)) {
            ContentEncryptedElementSecurityEvent contentEncryptedElementSecurityEvent = (ContentEncryptedElementSecurityEvent)securityEvent;
            if (!contentEncryptedElementSecurityEvent.isEncrypted()) {
                return true;
            }
            List<XMLSecurityConstants.ContentType> contentTypes = contentEncryptedElementSecurityEvent.getProtectionOrder();
            this.testProtectionOrder(protectionOrder, contentTypes, contentEncryptedElementSecurityEvent.getElementPath());
        }
        return this.isAsserted();
    }

    private void testProtectionOrder(AbstractSymmetricAsymmetricBinding.ProtectionOrder protectionOrder, List<XMLSecurityConstants.ContentType> contentTypes, List<QName> elementPath) {
        String namespace = this.getAssertion().getName().getNamespaceURI();
        switch (protectionOrder) {
            case SignBeforeEncrypting: {
                int lastSignature = contentTypes.lastIndexOf((Object)XMLSecurityConstants.ContentType.SIGNATURE);
                int firstEncryption = contentTypes.indexOf((Object)XMLSecurityConstants.ContentType.ENCRYPTION);
                if (firstEncryption >= 0 && firstEncryption < lastSignature) {
                    this.setAsserted(false);
                    this.setErrorMessage("Policy enforces " + (Object)((Object)protectionOrder) + " but the " + WSSUtils.pathAsString(elementPath) + " was encrypted and then signed");
                    this.policyAsserter.unassertPolicy(new QName(namespace, "SignBeforeEncrypting"), this.getErrorMessage());
                    break;
                }
                this.policyAsserter.assertPolicy(new QName(namespace, "SignBeforeEncrypting"));
                break;
            }
            case EncryptBeforeSigning: {
                int lastEncryption = contentTypes.lastIndexOf((Object)XMLSecurityConstants.ContentType.ENCRYPTION);
                int firstSignature = contentTypes.indexOf((Object)XMLSecurityConstants.ContentType.SIGNATURE);
                if (firstSignature >= 0 && firstSignature < lastEncryption) {
                    this.setAsserted(false);
                    this.setErrorMessage("Policy enforces " + (Object)((Object)protectionOrder) + " but the " + WSSUtils.pathAsString(elementPath) + " was signed and then encrypted");
                    this.policyAsserter.unassertPolicy(new QName(namespace, "EncryptBeforeSigning"), this.getErrorMessage());
                    break;
                }
                this.policyAsserter.assertPolicy(new QName(namespace, "EncryptBeforeSigning"));
            }
        }
    }
}

