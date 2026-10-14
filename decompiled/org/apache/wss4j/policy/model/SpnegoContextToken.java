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
import org.apache.wss4j.policy.model.AbstractToken;
import org.w3c.dom.Element;

public class SpnegoContextToken
extends AbstractToken {
    private boolean mustNotSendCancel;
    private boolean mustNotSendAmend;
    private boolean mustNotSendRenew;

    public SpnegoContextToken(SPConstants.SPVersion version, SPConstants.IncludeTokenType includeTokenType, Element issuer, String issuerName, Element claims, Policy nestedPolicy) {
        super(version, includeTokenType, issuer, issuerName, claims, nestedPolicy);
        this.parseNestedPolicy(nestedPolicy, this);
    }

    @Override
    public QName getName() {
        return this.getVersion().getSPConstants().getSpnegoContextToken();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SpnegoContextToken)) {
            return false;
        }
        SpnegoContextToken that = (SpnegoContextToken)object;
        if (this.mustNotSendCancel != that.mustNotSendCancel || this.mustNotSendAmend != that.mustNotSendAmend || this.mustNotSendRenew != that.mustNotSendRenew) {
            return false;
        }
        return super.equals(object);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + Boolean.hashCode(this.mustNotSendCancel);
        result = 31 * result + Boolean.hashCode(this.mustNotSendAmend);
        result = 31 * result + Boolean.hashCode(this.mustNotSendRenew);
        return 31 * result + super.hashCode();
    }

    @Override
    protected AbstractSecurityAssertion cloneAssertion(Policy nestedPolicy) {
        return new SpnegoContextToken(this.getVersion(), this.getIncludeTokenType(), this.getIssuer(), this.getIssuerName(), this.getClaims(), nestedPolicy);
    }

    protected void parseNestedPolicy(Policy nestedPolicy, SpnegoContextToken spnegoContextToken) {
        Iterator<List<Assertion>> alternatives = nestedPolicy.getAlternatives();
        if (alternatives.hasNext()) {
            List<Assertion> assertions = alternatives.next();
            for (Assertion assertion : assertions) {
                String assertionName = assertion.getName().getLocalPart();
                String assertionNamespace = assertion.getName().getNamespaceURI();
                AbstractToken.DerivedKeys derivedKeys = AbstractToken.DerivedKeys.lookUp(assertionName);
                if (derivedKeys != null) {
                    if (spnegoContextToken.getDerivedKeys() != null) {
                        throw new IllegalArgumentException("Invalid Policy");
                    }
                    spnegoContextToken.setDerivedKeys(derivedKeys);
                    continue;
                }
                QName mustNotSendCancel = this.getVersion().getSPConstants().getMustNotSendCancel();
                if (mustNotSendCancel.getLocalPart().equals(assertionName) && mustNotSendCancel.getNamespaceURI().equals(assertionNamespace)) {
                    if (spnegoContextToken.isMustNotSendCancel()) {
                        throw new IllegalArgumentException("Invalid Policy");
                    }
                    spnegoContextToken.setMustNotSendCancel(true);
                    continue;
                }
                QName mustNotSendAmend = this.getVersion().getSPConstants().getMustNotSendAmend();
                if (mustNotSendAmend.getLocalPart().equals(assertionName) && mustNotSendAmend.getNamespaceURI().equals(assertionNamespace)) {
                    if (spnegoContextToken.isMustNotSendAmend()) {
                        throw new IllegalArgumentException("Invalid Policy");
                    }
                    spnegoContextToken.setMustNotSendAmend(true);
                    continue;
                }
                QName mustNotSendRenew = this.getVersion().getSPConstants().getMustNotSendRenew();
                if (!mustNotSendRenew.getLocalPart().equals(assertionName) || !mustNotSendRenew.getNamespaceURI().equals(assertionNamespace)) continue;
                if (spnegoContextToken.isMustNotSendRenew()) {
                    throw new IllegalArgumentException("Invalid Policy");
                }
                spnegoContextToken.setMustNotSendRenew(true);
            }
        }
    }

    public boolean isMustNotSendCancel() {
        return this.mustNotSendCancel;
    }

    protected void setMustNotSendCancel(boolean mustNotSendCancel) {
        this.mustNotSendCancel = mustNotSendCancel;
    }

    public boolean isMustNotSendAmend() {
        return this.mustNotSendAmend;
    }

    protected void setMustNotSendAmend(boolean mustNotSendAmend) {
        this.mustNotSendAmend = mustNotSendAmend;
    }

    public boolean isMustNotSendRenew() {
        return this.mustNotSendRenew;
    }

    protected void setMustNotSendRenew(boolean mustNotSendRenew) {
        this.mustNotSendRenew = mustNotSendRenew;
    }
}

