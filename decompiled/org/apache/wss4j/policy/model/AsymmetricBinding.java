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
import org.apache.wss4j.policy.model.InitiatorEncryptionToken;
import org.apache.wss4j.policy.model.InitiatorSignatureToken;
import org.apache.wss4j.policy.model.InitiatorToken;
import org.apache.wss4j.policy.model.RecipientEncryptionToken;
import org.apache.wss4j.policy.model.RecipientSignatureToken;
import org.apache.wss4j.policy.model.RecipientToken;

public class AsymmetricBinding
extends AbstractSymmetricAsymmetricBinding {
    private InitiatorToken initiatorToken;
    private InitiatorSignatureToken initiatorSignatureToken;
    private InitiatorEncryptionToken initiatorEncryptionToken;
    private RecipientToken recipientToken;
    private RecipientSignatureToken recipientSignatureToken;
    private RecipientEncryptionToken recipientEncryptionToken;

    public AsymmetricBinding(SPConstants.SPVersion version, Policy nestedPolicy) {
        super(version, nestedPolicy);
        this.parseNestedPolicy(nestedPolicy, this);
    }

    @Override
    public QName getName() {
        return this.getVersion().getSPConstants().getAsymmetricBinding();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof AsymmetricBinding)) {
            return false;
        }
        AsymmetricBinding that = (AsymmetricBinding)object;
        if (this.initiatorToken != null && !this.initiatorToken.equals(that.initiatorToken) || this.initiatorToken == null && that.initiatorToken != null) {
            return false;
        }
        if (this.initiatorSignatureToken != null && !this.initiatorSignatureToken.equals(that.initiatorSignatureToken) || this.initiatorSignatureToken == null && that.initiatorSignatureToken != null) {
            return false;
        }
        if (this.initiatorEncryptionToken != null && !this.initiatorEncryptionToken.equals(that.initiatorEncryptionToken) || this.initiatorEncryptionToken == null && that.initiatorEncryptionToken != null) {
            return false;
        }
        if (this.recipientToken != null && !this.recipientToken.equals(that.recipientToken) || this.recipientToken == null && that.recipientToken != null) {
            return false;
        }
        if (this.recipientSignatureToken != null && !this.recipientSignatureToken.equals(that.recipientSignatureToken) || this.recipientSignatureToken == null && that.recipientSignatureToken != null) {
            return false;
        }
        if (this.recipientEncryptionToken != null && !this.recipientEncryptionToken.equals(that.recipientEncryptionToken) || this.recipientEncryptionToken == null && that.recipientEncryptionToken != null) {
            return false;
        }
        return super.equals(object);
    }

    @Override
    public int hashCode() {
        int result = 17;
        if (this.initiatorToken != null) {
            result = 31 * result + this.initiatorToken.hashCode();
        }
        if (this.initiatorSignatureToken != null) {
            result = 31 * result + this.initiatorSignatureToken.hashCode();
        }
        if (this.initiatorEncryptionToken != null) {
            result = 31 * result + this.initiatorEncryptionToken.hashCode();
        }
        if (this.recipientToken != null) {
            result = 31 * result + this.recipientToken.hashCode();
        }
        if (this.recipientSignatureToken != null) {
            result = 31 * result + this.recipientSignatureToken.hashCode();
        }
        if (this.recipientEncryptionToken != null) {
            result = 31 * result + this.recipientEncryptionToken.hashCode();
        }
        return 31 * result + super.hashCode();
    }

    @Override
    protected AbstractSecurityAssertion cloneAssertion(Policy nestedPolicy) {
        return new AsymmetricBinding(this.getVersion(), nestedPolicy);
    }

    protected void parseNestedPolicy(Policy nestedPolicy, AsymmetricBinding asymmetricBinding) {
        Iterator<List<Assertion>> alternatives = nestedPolicy.getAlternatives();
        if (alternatives.hasNext()) {
            List<Assertion> assertions = alternatives.next();
            for (Assertion assertion : assertions) {
                String assertionName = assertion.getName().getLocalPart();
                String assertionNamespace = assertion.getName().getNamespaceURI();
                QName initiatorToken = this.getVersion().getSPConstants().getInitiatorToken();
                if (initiatorToken.getLocalPart().equals(assertionName) && initiatorToken.getNamespaceURI().equals(assertionNamespace)) {
                    if (asymmetricBinding.getInitiatorToken() != null || asymmetricBinding.getInitiatorSignatureToken() != null || asymmetricBinding.getInitiatorEncryptionToken() != null) {
                        throw new IllegalArgumentException("Invalid Policy");
                    }
                    InitiatorToken initiatorToken1 = (InitiatorToken)assertion;
                    asymmetricBinding.setInitiatorToken(initiatorToken1);
                    initiatorToken1.setParentAssertion(asymmetricBinding);
                    continue;
                }
                QName initiatorSigToken = this.getVersion().getSPConstants().getInitiatorSignatureToken();
                if (initiatorSigToken.getLocalPart().equals(assertionName) && initiatorSigToken.getNamespaceURI().equals(assertionNamespace)) {
                    if (asymmetricBinding.getInitiatorToken() != null || asymmetricBinding.getInitiatorSignatureToken() != null) {
                        throw new IllegalArgumentException("Invalid Policy");
                    }
                    InitiatorSignatureToken initiatorSignatureToken1 = (InitiatorSignatureToken)assertion;
                    asymmetricBinding.setInitiatorSignatureToken(initiatorSignatureToken1);
                    initiatorSignatureToken1.setParentAssertion(asymmetricBinding);
                    continue;
                }
                QName initiatorEncToken = this.getVersion().getSPConstants().getInitiatorEncryptionToken();
                if (initiatorEncToken.getLocalPart().equals(assertionName) && initiatorEncToken.getNamespaceURI().equals(assertionNamespace)) {
                    if (asymmetricBinding.getInitiatorToken() != null || asymmetricBinding.getInitiatorEncryptionToken() != null) {
                        throw new IllegalArgumentException("Invalid Policy");
                    }
                    InitiatorEncryptionToken initiatorEncryptionToken1 = (InitiatorEncryptionToken)assertion;
                    asymmetricBinding.setInitiatorEncryptionToken(initiatorEncryptionToken1);
                    initiatorEncryptionToken1.setParentAssertion(asymmetricBinding);
                    continue;
                }
                QName recipientToken = this.getVersion().getSPConstants().getRecipientToken();
                if (recipientToken.getLocalPart().equals(assertionName) && recipientToken.getNamespaceURI().equals(assertionNamespace)) {
                    if (asymmetricBinding.getRecipientToken() != null || asymmetricBinding.getRecipientSignatureToken() != null || asymmetricBinding.getRecipientEncryptionToken() != null) {
                        throw new IllegalArgumentException("Invalid Policy");
                    }
                    RecipientToken recipientToken1 = (RecipientToken)assertion;
                    asymmetricBinding.setRecipientToken(recipientToken1);
                    recipientToken1.setParentAssertion(asymmetricBinding);
                    continue;
                }
                QName recipientSigToken = this.getVersion().getSPConstants().getRecipientSignatureToken();
                if (recipientSigToken.getLocalPart().equals(assertionName) && recipientSigToken.getNamespaceURI().equals(assertionNamespace)) {
                    if (asymmetricBinding.getRecipientToken() != null || asymmetricBinding.getRecipientSignatureToken() != null) {
                        throw new IllegalArgumentException("Invalid Policy");
                    }
                    RecipientSignatureToken recipientSignatureToken1 = (RecipientSignatureToken)assertion;
                    asymmetricBinding.setRecipientSignatureToken(recipientSignatureToken1);
                    recipientSignatureToken1.setParentAssertion(asymmetricBinding);
                    continue;
                }
                QName recipientEncToken = this.getVersion().getSPConstants().getRecipientEncryptionToken();
                if (!recipientEncToken.getLocalPart().equals(assertionName) || !recipientEncToken.getNamespaceURI().equals(assertionNamespace)) continue;
                if (asymmetricBinding.getRecipientToken() != null || asymmetricBinding.getRecipientEncryptionToken() != null) {
                    throw new IllegalArgumentException("Invalid Policy");
                }
                RecipientEncryptionToken recipientEncryptionToken1 = (RecipientEncryptionToken)assertion;
                asymmetricBinding.setRecipientEncryptionToken(recipientEncryptionToken1);
                recipientEncryptionToken1.setParentAssertion(asymmetricBinding);
            }
        }
    }

    public InitiatorToken getInitiatorToken() {
        return this.initiatorToken;
    }

    protected void setInitiatorToken(InitiatorToken initiatorToken) {
        this.initiatorToken = initiatorToken;
    }

    public InitiatorSignatureToken getInitiatorSignatureToken() {
        return this.initiatorSignatureToken;
    }

    protected void setInitiatorSignatureToken(InitiatorSignatureToken initiatorSignatureToken) {
        this.initiatorSignatureToken = initiatorSignatureToken;
    }

    public InitiatorEncryptionToken getInitiatorEncryptionToken() {
        return this.initiatorEncryptionToken;
    }

    protected void setInitiatorEncryptionToken(InitiatorEncryptionToken initiatorEncryptionToken) {
        this.initiatorEncryptionToken = initiatorEncryptionToken;
    }

    public RecipientToken getRecipientToken() {
        return this.recipientToken;
    }

    protected void setRecipientToken(RecipientToken recipientToken) {
        this.recipientToken = recipientToken;
    }

    public RecipientSignatureToken getRecipientSignatureToken() {
        return this.recipientSignatureToken;
    }

    protected void setRecipientSignatureToken(RecipientSignatureToken recipientSignatureToken) {
        this.recipientSignatureToken = recipientSignatureToken;
    }

    public RecipientEncryptionToken getRecipientEncryptionToken() {
        return this.recipientEncryptionToken;
    }

    protected void setRecipientEncryptionToken(RecipientEncryptionToken recipientEncryptionToken) {
        this.recipientEncryptionToken = recipientEncryptionToken;
    }
}

