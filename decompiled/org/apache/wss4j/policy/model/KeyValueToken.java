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

public class KeyValueToken
extends AbstractToken {
    private boolean rsaKeyValue;

    public KeyValueToken(SPConstants.SPVersion version, SPConstants.IncludeTokenType includeTokenType, Policy nestedPolicy) {
        super(version, includeTokenType, null, null, null, nestedPolicy);
        this.setIncludeTokenType(includeTokenType);
        this.parseNestedPolicy(nestedPolicy, this);
    }

    @Override
    public QName getName() {
        return this.getVersion().getSPConstants().getKeyValueToken();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof KeyValueToken)) {
            return false;
        }
        KeyValueToken that = (KeyValueToken)object;
        if (this.rsaKeyValue != that.rsaKeyValue) {
            return false;
        }
        return super.equals(object);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + Boolean.hashCode(this.rsaKeyValue);
        return 31 * result + super.hashCode();
    }

    @Override
    protected AbstractSecurityAssertion cloneAssertion(Policy nestedPolicy) {
        return new KeyValueToken(this.getVersion(), this.getIncludeTokenType(), nestedPolicy);
    }

    protected void parseNestedPolicy(Policy nestedPolicy, KeyValueToken keyValueToken) {
        Iterator<List<Assertion>> alternatives = nestedPolicy.getAlternatives();
        if (alternatives.hasNext()) {
            List<Assertion> assertions = alternatives.next();
            for (Assertion assertion : assertions) {
                String assertionName = assertion.getName().getLocalPart();
                String assertionNamespace = assertion.getName().getNamespaceURI();
                QName rsaKeyValue = this.getVersion().getSPConstants().getRsaKeyValue();
                if (!rsaKeyValue.getLocalPart().equals(assertionName) || !rsaKeyValue.getNamespaceURI().equals(assertionNamespace)) continue;
                if (keyValueToken.isRsaKeyValue()) {
                    throw new IllegalArgumentException("Invalid Policy");
                }
                keyValueToken.setRsaKeyValue(true);
            }
        }
    }

    public boolean isRsaKeyValue() {
        return this.rsaKeyValue;
    }

    protected void setRsaKeyValue(boolean rsaKeyValue) {
        this.rsaKeyValue = rsaKeyValue;
    }
}

