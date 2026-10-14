/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.model;

import javax.xml.namespace.QName;
import org.apache.neethi.Policy;
import org.apache.wss4j.policy.SPConstants;
import org.apache.wss4j.policy.model.AbstractSecurityAssertion;
import org.apache.wss4j.policy.model.AbstractTokenWrapper;

public class RecipientToken
extends AbstractTokenWrapper {
    public RecipientToken(SPConstants.SPVersion version, Policy nestedPolicy) {
        super(version, nestedPolicy);
    }

    @Override
    public QName getName() {
        return this.getVersion().getSPConstants().getRecipientToken();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof RecipientToken)) {
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
        return new RecipientToken(this.getVersion(), nestedPolicy);
    }
}

