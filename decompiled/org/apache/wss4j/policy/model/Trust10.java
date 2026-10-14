/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.model;

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

public class Trust10
extends AbstractSecurityAssertion
implements PolicyContainingAssertion {
    private Policy nestedPolicy;
    private boolean mustSupportClientChallenge;
    private boolean mustSupportServerChallenge;
    private boolean requireClientEntropy;
    private boolean requireServerEntropy;
    private boolean mustSupportIssuedTokens;

    public Trust10(SPConstants.SPVersion version, Policy nestedPolicy) {
        super(version);
        this.nestedPolicy = nestedPolicy;
        this.parseNestedTrust10Policy(nestedPolicy, this);
    }

    @Override
    public Policy getPolicy() {
        return this.nestedPolicy;
    }

    @Override
    public QName getName() {
        return this.getVersion().getSPConstants().getTrust10();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Trust10)) {
            return false;
        }
        Trust10 that = (Trust10)object;
        if (this.mustSupportClientChallenge != that.mustSupportClientChallenge || this.mustSupportServerChallenge != that.mustSupportServerChallenge || this.requireClientEntropy != that.requireClientEntropy || this.requireServerEntropy != that.requireServerEntropy || this.mustSupportIssuedTokens != that.mustSupportIssuedTokens) {
            return false;
        }
        return super.equals(object);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + Boolean.hashCode(this.mustSupportClientChallenge);
        result = 31 * result + Boolean.hashCode(this.mustSupportServerChallenge);
        result = 31 * result + Boolean.hashCode(this.requireClientEntropy);
        result = 31 * result + Boolean.hashCode(this.requireServerEntropy);
        result = 31 * result + Boolean.hashCode(this.mustSupportIssuedTokens);
        return 31 * result + super.hashCode();
    }

    @Override
    public PolicyComponent normalize() {
        return super.normalize(this.getPolicy());
    }

    @Override
    public void serialize(XMLStreamWriter writer) throws XMLStreamException {
        super.serialize(writer, this.getPolicy());
    }

    @Override
    protected AbstractSecurityAssertion cloneAssertion(Policy nestedPolicy) {
        return new Trust10(this.getVersion(), nestedPolicy);
    }

    protected void parseNestedTrust10Policy(Policy nestedPolicy, Trust10 trust10) {
        Iterator<List<Assertion>> alternatives = nestedPolicy.getAlternatives();
        if (alternatives.hasNext()) {
            List<Assertion> assertions = alternatives.next();
            for (Assertion assertion : assertions) {
                String assertionName = assertion.getName().getLocalPart();
                String assertionNamespace = assertion.getName().getNamespaceURI();
                QName mustSupportClientChallenge = this.getVersion().getSPConstants().getMustSupportClientChallenge();
                if (mustSupportClientChallenge.getLocalPart().equals(assertionName) && mustSupportClientChallenge.getNamespaceURI().equals(assertionNamespace)) {
                    if (trust10.isMustSupportClientChallenge()) {
                        throw new IllegalArgumentException("Invalid Policy");
                    }
                    trust10.setMustSupportClientChallenge(true);
                    continue;
                }
                QName mustSupportServerChallenge = this.getVersion().getSPConstants().getMustSupportServerChallenge();
                if (mustSupportServerChallenge.getLocalPart().equals(assertionName) && mustSupportServerChallenge.getNamespaceURI().equals(assertionNamespace)) {
                    if (trust10.isMustSupportServerChallenge()) {
                        throw new IllegalArgumentException("Invalid Policy");
                    }
                    trust10.setMustSupportServerChallenge(true);
                    continue;
                }
                QName requireClientEntropy = this.getVersion().getSPConstants().getRequireClientEntropy();
                if (requireClientEntropy.getLocalPart().equals(assertionName) && requireClientEntropy.getNamespaceURI().equals(assertionNamespace)) {
                    if (trust10.isRequireClientEntropy()) {
                        throw new IllegalArgumentException("Invalid Policy");
                    }
                    trust10.setRequireClientEntropy(true);
                    continue;
                }
                QName requireServerEntropy = this.getVersion().getSPConstants().getRequireServerEntropy();
                if (requireServerEntropy.getLocalPart().equals(assertionName) && requireServerEntropy.getNamespaceURI().equals(assertionNamespace)) {
                    if (trust10.isRequireServerEntropy()) {
                        throw new IllegalArgumentException("Invalid Policy");
                    }
                    trust10.setRequireServerEntropy(true);
                    continue;
                }
                QName mustSupportIssuedTokens = this.getVersion().getSPConstants().getMustSupportIssuedTokens();
                if (!mustSupportIssuedTokens.getLocalPart().equals(assertionName) || !mustSupportIssuedTokens.getNamespaceURI().equals(assertionNamespace)) continue;
                if (trust10.isMustSupportIssuedTokens()) {
                    throw new IllegalArgumentException("Invalid Policy");
                }
                trust10.setMustSupportIssuedTokens(true);
            }
        }
    }

    public boolean isMustSupportClientChallenge() {
        return this.mustSupportClientChallenge;
    }

    protected void setMustSupportClientChallenge(boolean mustSupportClientChallenge) {
        this.mustSupportClientChallenge = mustSupportClientChallenge;
    }

    public boolean isMustSupportServerChallenge() {
        return this.mustSupportServerChallenge;
    }

    protected void setMustSupportServerChallenge(boolean mustSupportServerChallenge) {
        this.mustSupportServerChallenge = mustSupportServerChallenge;
    }

    public boolean isRequireClientEntropy() {
        return this.requireClientEntropy;
    }

    protected void setRequireClientEntropy(boolean requireClientEntropy) {
        this.requireClientEntropy = requireClientEntropy;
    }

    public boolean isRequireServerEntropy() {
        return this.requireServerEntropy;
    }

    protected void setRequireServerEntropy(boolean requireServerEntropy) {
        this.requireServerEntropy = requireServerEntropy;
    }

    public boolean isMustSupportIssuedTokens() {
        return this.mustSupportIssuedTokens;
    }

    protected void setMustSupportIssuedTokens(boolean mustSupportIssuedTokens) {
        this.mustSupportIssuedTokens = mustSupportIssuedTokens;
    }
}

