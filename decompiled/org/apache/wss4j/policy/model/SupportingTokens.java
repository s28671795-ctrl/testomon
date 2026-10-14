/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.apache.neethi.Assertion;
import org.apache.neethi.Policy;
import org.apache.neethi.PolicyComponent;
import org.apache.neethi.PolicyContainingAssertion;
import org.apache.wss4j.policy.SPConstants;
import org.apache.wss4j.policy.model.AbstractSecurityAssertion;
import org.apache.wss4j.policy.model.AbstractToken;
import org.apache.wss4j.policy.model.AlgorithmSuite;
import org.apache.wss4j.policy.model.EncryptedElements;
import org.apache.wss4j.policy.model.EncryptedParts;
import org.apache.wss4j.policy.model.SignedElements;
import org.apache.wss4j.policy.model.SignedParts;

public class SupportingTokens
extends AbstractSecurityAssertion
implements PolicyContainingAssertion {
    private QName supportingTokenType;
    private final List<AbstractToken> tokens = new ArrayList<AbstractToken>();
    private AlgorithmSuite algorithmSuite;
    private SignedParts signedParts;
    private SignedElements signedElements;
    private EncryptedParts encryptedParts;
    private EncryptedElements encryptedElements;
    private Policy nestedPolicy;

    public SupportingTokens(SPConstants.SPVersion version, QName supportingTokenType, Policy nestedPolicy) {
        super(version);
        this.supportingTokenType = supportingTokenType;
        this.nestedPolicy = nestedPolicy;
        this.parseNestedPolicy(nestedPolicy, this);
    }

    @Override
    public QName getName() {
        return this.supportingTokenType;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SupportingTokens)) {
            return false;
        }
        SupportingTokens that = (SupportingTokens)object;
        if (this.supportingTokenType != null && !this.supportingTokenType.equals(that.supportingTokenType) || this.supportingTokenType == null && that.supportingTokenType != null) {
            return false;
        }
        if (this.algorithmSuite != null && !this.algorithmSuite.equals(that.algorithmSuite) || this.algorithmSuite == null && that.algorithmSuite != null) {
            return false;
        }
        if (this.signedParts != null && !this.signedParts.equals(that.signedParts) || this.signedParts == null && that.signedParts != null) {
            return false;
        }
        if (this.signedElements != null && !this.signedElements.equals(that.signedElements) || this.signedElements == null && that.signedElements != null) {
            return false;
        }
        if (this.encryptedParts != null && !this.encryptedParts.equals(that.encryptedParts) || this.encryptedParts == null && that.encryptedParts != null) {
            return false;
        }
        if (this.encryptedElements != null && !this.encryptedElements.equals(that.encryptedElements) || this.encryptedElements == null && that.encryptedElements != null) {
            return false;
        }
        if (!this.tokens.equals(that.tokens)) {
            return false;
        }
        return super.equals(object);
    }

    @Override
    public int hashCode() {
        int result = 17;
        if (this.supportingTokenType != null) {
            result = 31 * result + this.supportingTokenType.hashCode();
        }
        if (this.algorithmSuite != null) {
            result = 31 * result + this.algorithmSuite.hashCode();
        }
        if (this.signedParts != null) {
            result = 31 * result + this.signedParts.hashCode();
        }
        if (this.signedElements != null) {
            result = 31 * result + this.signedElements.hashCode();
        }
        if (this.encryptedParts != null) {
            result = 31 * result + this.encryptedParts.hashCode();
        }
        if (this.encryptedElements != null) {
            result = 31 * result + this.encryptedElements.hashCode();
        }
        result = 31 * result + this.tokens.hashCode();
        return 31 * result + super.hashCode();
    }

    @Override
    public void serialize(XMLStreamWriter writer) throws XMLStreamException {
        super.serialize(writer, this.getPolicy());
    }

    @Override
    public PolicyComponent normalize() {
        return super.normalize(this.getPolicy());
    }

    @Override
    protected AbstractSecurityAssertion cloneAssertion(Policy nestedPolicy) {
        return new SupportingTokens(this.getVersion(), this.getName(), nestedPolicy);
    }

    @Override
    public Policy getPolicy() {
        return this.nestedPolicy;
    }

    protected void parseNestedPolicy(Policy nestedPolicy, SupportingTokens supportingTokens) {
        Iterator<List<Assertion>> alternatives = nestedPolicy.getAlternatives();
        if (alternatives.hasNext()) {
            List<Assertion> assertions = alternatives.next();
            for (Assertion assertion : assertions) {
                String assertionName = assertion.getName().getLocalPart();
                String assertionNamespace = assertion.getName().getNamespaceURI();
                if (assertion instanceof AbstractToken) {
                    AbstractToken abstractToken = (AbstractToken)assertion;
                    supportingTokens.addToken(abstractToken);
                    abstractToken.setParentAssertion(supportingTokens);
                    continue;
                }
                QName algSuite = this.getVersion().getSPConstants().getAlgorithmSuite();
                if (algSuite.getLocalPart().equals(assertionName) && algSuite.getNamespaceURI().equals(assertionNamespace)) {
                    if (supportingTokens.getAlgorithmSuite() != null) {
                        throw new IllegalArgumentException("Invalid Policy");
                    }
                    supportingTokens.setAlgorithmSuite((AlgorithmSuite)assertion);
                    continue;
                }
                QName signedParts = this.getVersion().getSPConstants().getSignedParts();
                if (signedParts.getLocalPart().equals(assertionName) && signedParts.getNamespaceURI().equals(assertionNamespace)) {
                    if (supportingTokens.getSignedParts() != null) {
                        throw new IllegalArgumentException("Invalid Policy");
                    }
                    supportingTokens.setSignedParts((SignedParts)assertion);
                    continue;
                }
                QName signedElements = this.getVersion().getSPConstants().getSignedElements();
                if (signedElements.getLocalPart().equals(assertionName) && signedElements.getNamespaceURI().equals(assertionNamespace)) {
                    if (supportingTokens.getSignedElements() != null) {
                        throw new IllegalArgumentException("Invalid Policy");
                    }
                    supportingTokens.setSignedElements((SignedElements)assertion);
                    continue;
                }
                QName encryptedParts = this.getVersion().getSPConstants().getEncryptedParts();
                if (encryptedParts.getLocalPart().equals(assertionName) && encryptedParts.getNamespaceURI().equals(assertionNamespace)) {
                    if (supportingTokens.getEncryptedParts() != null) {
                        throw new IllegalArgumentException("Invalid Policy");
                    }
                    supportingTokens.setEncryptedParts((EncryptedParts)assertion);
                    continue;
                }
                QName encryptedElements = this.getVersion().getSPConstants().getEncryptedElements();
                if (!encryptedElements.getLocalPart().equals(assertionName) || !encryptedElements.getNamespaceURI().equals(assertionNamespace)) continue;
                if (supportingTokens.getEncryptedElements() != null) {
                    throw new IllegalArgumentException("Invalid Policy");
                }
                supportingTokens.setEncryptedElements((EncryptedElements)assertion);
            }
        }
    }

    public List<AbstractToken> getTokens() {
        return this.tokens;
    }

    public void addToken(AbstractToken token) {
        this.tokens.add(token);
    }

    public AlgorithmSuite getAlgorithmSuite() {
        return this.algorithmSuite;
    }

    protected void setAlgorithmSuite(AlgorithmSuite algorithmSuite) {
        this.algorithmSuite = algorithmSuite;
    }

    public SignedParts getSignedParts() {
        return this.signedParts;
    }

    protected void setSignedParts(SignedParts signedParts) {
        this.signedParts = signedParts;
    }

    public SignedElements getSignedElements() {
        return this.signedElements;
    }

    protected void setSignedElements(SignedElements signedElements) {
        this.signedElements = signedElements;
    }

    public EncryptedParts getEncryptedParts() {
        return this.encryptedParts;
    }

    protected void setEncryptedParts(EncryptedParts encryptedParts) {
        this.encryptedParts = encryptedParts;
    }

    public EncryptedElements getEncryptedElements() {
        return this.encryptedElements;
    }

    protected void setEncryptedElements(EncryptedElements encryptedElements) {
        this.encryptedElements = encryptedElements;
    }

    public boolean isEncryptedToken() {
        QName name = this.getName();
        return name != null && name.getLocalPart().contains("Encrypted");
    }

    public boolean isEndorsing() {
        QName name = this.getName();
        return name != null && name.getLocalPart().contains("Endorsing");
    }
}

