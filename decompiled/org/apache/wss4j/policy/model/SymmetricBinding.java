/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.model;

import java.util.Iterator;
import java.util.List;
import javax.xml.namespace.QName;
import org.apache.neethi.Assertion;
import org.apache.neethi.Policy;
import org.apache.wss4j.policy.SPConstants;
import org.apache.wss4j.policy.model.AbstractSecurityAssertion;
import org.apache.wss4j.policy.model.AbstractSymmetricAsymmetricBinding;
import org.apache.wss4j.policy.model.EncryptionToken;
import org.apache.wss4j.policy.model.ProtectionToken;
import org.apache.wss4j.policy.model.SignatureToken;

public class SymmetricBinding
extends AbstractSymmetricAsymmetricBinding {
    private EncryptionToken encryptionToken;
    private SignatureToken signatureToken;
    private ProtectionToken protectionToken;

    public SymmetricBinding(SPConstants.SPVersion version, Policy nestedPolicy) {
        super(version, nestedPolicy);
        this.parseNestedPolicy(nestedPolicy, this);
    }

    @Override
    public QName getName() {
        return this.getVersion().getSPConstants().getSymmetricBinding();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SymmetricBinding)) {
            return false;
        }
        SymmetricBinding that = (SymmetricBinding)object;
        if (this.encryptionToken != null && !this.encryptionToken.equals(that.encryptionToken) || this.encryptionToken == null && that.encryptionToken != null) {
            return false;
        }
        if (this.signatureToken != null && !this.signatureToken.equals(that.signatureToken) || this.signatureToken == null && that.signatureToken != null) {
            return false;
        }
        if (this.protectionToken != null && !this.protectionToken.equals(that.protectionToken) || this.protectionToken == null && that.protectionToken != null) {
            return false;
        }
        return super.equals(object);
    }

    @Override
    public int hashCode() {
        int result = 17;
        if (this.encryptionToken != null) {
            result = 31 * result + this.encryptionToken.hashCode();
        }
        if (this.signatureToken != null) {
            result = 31 * result + this.signatureToken.hashCode();
        }
        if (this.protectionToken != null) {
            result = 31 * result + this.protectionToken.hashCode();
        }
        return 31 * result + super.hashCode();
    }

    @Override
    protected AbstractSecurityAssertion cloneAssertion(Policy nestedPolicy) {
        return new SymmetricBinding(this.getVersion(), nestedPolicy);
    }

    protected void parseNestedPolicy(Policy nestedPolicy, SymmetricBinding symmetricBinding) {
        Iterator<List<Assertion>> alternatives = nestedPolicy.getAlternatives();
        if (alternatives.hasNext()) {
            List<Assertion> assertions = alternatives.next();
            for (Assertion assertion : assertions) {
                String assertionName = assertion.getName().getLocalPart();
                String assertionNamespace = assertion.getName().getNamespaceURI();
                QName encryptionToken = this.getVersion().getSPConstants().getEncryptionToken();
                if (encryptionToken.getLocalPart().equals(assertionName) && encryptionToken.getNamespaceURI().equals(assertionNamespace)) {
                    if (symmetricBinding.getEncryptionToken() != null || symmetricBinding.getProtectionToken() != null) {
                        throw new IllegalArgumentException("Invalid Policy");
                    }
                    symmetricBinding.setEncryptionToken((EncryptionToken)assertion);
                    continue;
                }
                QName signatureToken = this.getVersion().getSPConstants().getSignatureToken();
                if (signatureToken.getLocalPart().equals(assertionName) && signatureToken.getNamespaceURI().equals(assertionNamespace)) {
                    if (symmetricBinding.getSignatureToken() != null || symmetricBinding.getProtectionToken() != null) {
                        throw new IllegalArgumentException("Invalid Policy");
                    }
                    symmetricBinding.setSignatureToken((SignatureToken)assertion);
                    continue;
                }
                QName protectionToken = this.getVersion().getSPConstants().getProtectionToken();
                if (!protectionToken.getLocalPart().equals(assertionName) || !protectionToken.getNamespaceURI().equals(assertionNamespace)) continue;
                if (symmetricBinding.getProtectionToken() != null || symmetricBinding.getEncryptionToken() != null || symmetricBinding.getSignatureToken() != null) {
                    throw new IllegalArgumentException("Invalid Policy");
                }
                symmetricBinding.setProtectionToken((ProtectionToken)assertion);
            }
        }
    }

    public EncryptionToken getEncryptionToken() {
        return this.encryptionToken;
    }

    protected void setEncryptionToken(EncryptionToken encryptionToken) {
        this.encryptionToken = encryptionToken;
    }

    public SignatureToken getSignatureToken() {
        return this.signatureToken;
    }

    protected void setSignatureToken(SignatureToken signatureToken) {
        this.signatureToken = signatureToken;
    }

    public ProtectionToken getProtectionToken() {
        return this.protectionToken;
    }

    protected void setProtectionToken(ProtectionToken protectionToken) {
        this.protectionToken = protectionToken;
    }
}

