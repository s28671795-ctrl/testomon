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
import org.apache.wss4j.policy.model.Wss10;

public class Wss11
extends Wss10 {
    private boolean mustSupportRefThumbprint;
    private boolean mustSupportRefEncryptedKey;
    private boolean requireSignatureConfirmation;

    public Wss11(SPConstants.SPVersion version, Policy nestedPolicy) {
        super(version, nestedPolicy);
        this.parseNestedWss11Policy(nestedPolicy, this);
    }

    @Override
    public QName getName() {
        return this.getVersion().getSPConstants().getWss11();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Wss11)) {
            return false;
        }
        Wss11 that = (Wss11)object;
        if (this.mustSupportRefThumbprint != that.mustSupportRefThumbprint || this.mustSupportRefEncryptedKey != that.mustSupportRefEncryptedKey || this.requireSignatureConfirmation != that.requireSignatureConfirmation) {
            return false;
        }
        return super.equals(object);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + Boolean.hashCode(this.mustSupportRefThumbprint);
        result = 31 * result + Boolean.hashCode(this.mustSupportRefEncryptedKey);
        result = 31 * result + Boolean.hashCode(this.requireSignatureConfirmation);
        return 31 * result + super.hashCode();
    }

    @Override
    protected AbstractSecurityAssertion cloneAssertion(Policy nestedPolicy) {
        return new Wss11(this.getVersion(), nestedPolicy);
    }

    protected void parseNestedWss11Policy(Policy nestedPolicy, Wss11 wss11) {
        Iterator<List<Assertion>> alternatives = nestedPolicy.getAlternatives();
        if (alternatives.hasNext()) {
            List<Assertion> assertions = alternatives.next();
            for (Assertion assertion : assertions) {
                String assertionName = assertion.getName().getLocalPart();
                String assertionNamespace = assertion.getName().getNamespaceURI();
                QName mustSupportRefThumbprint = this.getVersion().getSPConstants().getMustSupportRefThumbprint();
                if (mustSupportRefThumbprint.getLocalPart().equals(assertionName) && mustSupportRefThumbprint.getNamespaceURI().equals(assertionNamespace)) {
                    if (wss11.isMustSupportRefThumbprint()) {
                        throw new IllegalArgumentException("Invalid Policy");
                    }
                    wss11.setMustSupportRefThumbprint(true);
                    continue;
                }
                QName mustSupportRefEncryptedKey = this.getVersion().getSPConstants().getMustSupportRefEncryptedKey();
                if (mustSupportRefEncryptedKey.getLocalPart().equals(assertionName) && mustSupportRefEncryptedKey.getNamespaceURI().equals(assertionNamespace)) {
                    if (wss11.isMustSupportRefEncryptedKey()) {
                        throw new IllegalArgumentException("Invalid Policy");
                    }
                    wss11.setMustSupportRefEncryptedKey(true);
                    continue;
                }
                QName requireSigConf = this.getVersion().getSPConstants().getRequireSignatureConfirmation();
                if (!requireSigConf.getLocalPart().equals(assertionName) || !requireSigConf.getNamespaceURI().equals(assertionNamespace)) continue;
                if (wss11.isRequireSignatureConfirmation()) {
                    throw new IllegalArgumentException("Invalid Policy");
                }
                wss11.setRequireSignatureConfirmation(true);
            }
        }
    }

    public boolean isMustSupportRefThumbprint() {
        return this.mustSupportRefThumbprint;
    }

    protected void setMustSupportRefThumbprint(boolean mustSupportRefThumbprint) {
        this.mustSupportRefThumbprint = mustSupportRefThumbprint;
    }

    public boolean isMustSupportRefEncryptedKey() {
        return this.mustSupportRefEncryptedKey;
    }

    protected void setMustSupportRefEncryptedKey(boolean mustSupportRefEncryptedKey) {
        this.mustSupportRefEncryptedKey = mustSupportRefEncryptedKey;
    }

    public boolean isRequireSignatureConfirmation() {
        return this.requireSignatureConfirmation;
    }

    protected void setRequireSignatureConfirmation(boolean requireSignatureConfirmation) {
        this.requireSignatureConfirmation = requireSignatureConfirmation;
    }
}

