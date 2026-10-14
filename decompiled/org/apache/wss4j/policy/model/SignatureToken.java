/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.model;

import javax.xml.namespace.QName;
import org.apache.neethi.Policy;
import org.apache.wss4j.policy.SPConstants;
import org.apache.wss4j.policy.model.AbstractSecurityAssertion;
import org.apache.wss4j.policy.model.AbstractTokenWrapper;

public class SignatureToken
extends AbstractTokenWrapper {
    public SignatureToken(SPConstants.SPVersion version, Policy nestedPolicy) {
        super(version, nestedPolicy);
    }

    @Override
    public QName getName() {
        return this.getVersion().getSPConstants().getSignatureToken();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SignatureToken)) {
            return false;
        }
        return super.equals(object);
    }

    @Override
    public int hashCode() {
        int result = 17;
        return 31 * result + super.hashCode();
    }

    @Override
    protected AbstractSecurityAssertion cloneAssertion(Policy nestedPolicy) {
        return new SignatureToken(this.getVersion(), nestedPolicy);
    }
}

