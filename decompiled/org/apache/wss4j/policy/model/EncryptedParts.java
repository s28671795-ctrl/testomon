/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.model;

import java.util.List;
import javax.xml.namespace.QName;
import org.apache.neethi.Policy;
import org.apache.wss4j.policy.SPConstants;
import org.apache.wss4j.policy.model.AbstractSecuredParts;
import org.apache.wss4j.policy.model.AbstractSecurityAssertion;
import org.apache.wss4j.policy.model.Attachments;
import org.apache.wss4j.policy.model.Header;

public class EncryptedParts
extends AbstractSecuredParts {
    public EncryptedParts(SPConstants.SPVersion version, boolean body2, Attachments attachments, List<Header> headers) {
        super(version, body2, attachments, headers);
    }

    @Override
    public QName getName() {
        return this.getVersion().getSPConstants().getEncryptedParts();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof EncryptedParts)) {
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
        return new EncryptedParts(this.getVersion(), this.isBody(), this.getAttachments() == null ? null : (Attachments)this.getAttachments().clone(nestedPolicy), this.getHeaders());
    }
}

