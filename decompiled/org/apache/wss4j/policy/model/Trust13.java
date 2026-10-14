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
import org.apache.wss4j.policy.model.Trust10;

public class Trust13
extends Trust10 {
    private boolean requireRequestSecurityTokenCollection;
    private boolean requireAppliesTo;
    private boolean scopePolicy15;
    private boolean mustSupportInteractiveChallenge;

    public Trust13(SPConstants.SPVersion version, Policy nestedPolicy) {
        super(version, nestedPolicy);
        this.parseNestedTrust13Policy(nestedPolicy, this);
    }

    @Override
    public QName getName() {
        return this.getVersion().getSPConstants().getTrust13();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Trust13)) {
            return false;
        }
        Trust13 that = (Trust13)object;
        if (this.requireRequestSecurityTokenCollection != that.requireRequestSecurityTokenCollection || this.requireAppliesTo != that.requireAppliesTo || this.scopePolicy15 != that.scopePolicy15 || this.mustSupportInteractiveChallenge != that.mustSupportInteractiveChallenge) {
            return false;
        }
        return super.equals(object);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + Boolean.hashCode(this.requireRequestSecurityTokenCollection);
        result = 31 * result + Boolean.hashCode(this.requireAppliesTo);
        result = 31 * result + Boolean.hashCode(this.scopePolicy15);
        result = 31 * result + Boolean.hashCode(this.mustSupportInteractiveChallenge);
        return 31 * result + super.hashCode();
    }

    @Override
    protected AbstractSecurityAssertion cloneAssertion(Policy nestedPolicy) {
        return new Trust13(this.getVersion(), nestedPolicy);
    }

    protected void parseNestedTrust13Policy(Policy nestedPolicy, Trust13 trust13) {
        Iterator<List<Assertion>> alternatives = nestedPolicy.getAlternatives();
        if (alternatives.hasNext()) {
            List<Assertion> assertions = alternatives.next();
            for (Assertion assertion : assertions) {
                String assertionName = assertion.getName().getLocalPart();
                String assertionNamespace = assertion.getName().getNamespaceURI();
                QName requireRSTC = this.getVersion().getSPConstants().getRequireRequestSecurityTokenCollection();
                if (requireRSTC.getLocalPart().equals(assertionName) && requireRSTC.getNamespaceURI().equals(assertionNamespace)) {
                    if (trust13.isRequireRequestSecurityTokenCollection()) {
                        throw new IllegalArgumentException("Invalid Policy");
                    }
                    trust13.setRequireRequestSecurityTokenCollection(true);
                    continue;
                }
                QName requireAppliesTo = this.getVersion().getSPConstants().getRequireAppliesTo();
                if (requireAppliesTo.getLocalPart().equals(assertionName) && requireAppliesTo.getNamespaceURI().equals(assertionNamespace)) {
                    if (trust13.isRequireAppliesTo()) {
                        throw new IllegalArgumentException("Invalid Policy");
                    }
                    trust13.setRequireAppliesTo(true);
                    continue;
                }
                QName scopePolicy15 = this.getVersion().getSPConstants().getScopePolicy15();
                if (scopePolicy15.getLocalPart().equals(assertionName) && scopePolicy15.getNamespaceURI().equals(assertionNamespace)) {
                    if (trust13.isScopePolicy15()) {
                        throw new IllegalArgumentException("Invalid Policy");
                    }
                    trust13.setScopePolicy15(true);
                    continue;
                }
                QName supportInteractiveChallenge = this.getVersion().getSPConstants().getMustSupportInteractiveChallenge();
                if (!supportInteractiveChallenge.getLocalPart().equals(assertionName) || !supportInteractiveChallenge.getNamespaceURI().equals(assertionNamespace)) continue;
                if (trust13.isMustSupportInteractiveChallenge()) {
                    throw new IllegalArgumentException("Invalid Policy");
                }
                trust13.setMustSupportInteractiveChallenge(true);
            }
        }
    }

    public boolean isRequireRequestSecurityTokenCollection() {
        return this.requireRequestSecurityTokenCollection;
    }

    protected void setRequireRequestSecurityTokenCollection(boolean requireRequestSecurityTokenCollection) {
        this.requireRequestSecurityTokenCollection = requireRequestSecurityTokenCollection;
    }

    public boolean isRequireAppliesTo() {
        return this.requireAppliesTo;
    }

    protected void setRequireAppliesTo(boolean requireAppliesTo) {
        this.requireAppliesTo = requireAppliesTo;
    }

    public boolean isScopePolicy15() {
        return this.scopePolicy15;
    }

    protected void setScopePolicy15(boolean scopePolicy15) {
        this.scopePolicy15 = scopePolicy15;
    }

    public boolean isMustSupportInteractiveChallenge() {
        return this.mustSupportInteractiveChallenge;
    }

    protected void setMustSupportInteractiveChallenge(boolean mustSupportInteractiveChallenge) {
        this.mustSupportInteractiveChallenge = mustSupportInteractiveChallenge;
    }
}

