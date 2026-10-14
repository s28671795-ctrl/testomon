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

public class SignedParts
extends AbstractSecuredParts {
    private boolean signAllHeaders;

    public SignedParts(SPConstants.SPVersion version, boolean body2, Attachments attachments, List<Header> headers, boolean signAllHeaders) {
        super(version, body2, attachments, headers);
        this.signAllHeaders = signAllHeaders;
    }

    @Override
    public QName getName() {
        return this.getVersion().getSPConstants().getSignedParts();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SignedParts)) {
            return false;
        }
        SignedParts that = (SignedParts)object;
        if (this.signAllHeaders != that.signAllHeaders) {
            return false;
        }
        return super.equals(object);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + Boolean.hashCode(this.signAllHeaders);
        return 31 * result + super.hashCode();
    }

    @Override
    protected AbstractSecurityAssertion cloneAssertion(Policy nestedPolicy) {
        return new SignedParts(this.getVersion(), this.isBody(), this.getAttachments() == null ? null : (Attachments)this.getAttachments().clone(nestedPolicy), this.getHeaders(), this.isSignAllHeaders());
    }

    public boolean isSignAllHeaders() {
        return this.signAllHeaders;
    }

    protected void setSignAllHeaders(boolean signAllHeaders) {
        this.signAllHeaders = signAllHeaders;
    }
}

