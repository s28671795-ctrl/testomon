/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.extended;

import org.ldaptive.LdapUtils;
import org.ldaptive.asn1.DERBuffer;
import org.ldaptive.extended.ExtendedResponse;

public class UnsolicitedNotification
extends ExtendedResponse {
    private static final int HASH_CODE_SEED = 10331;

    public UnsolicitedNotification() {
        this.setMessageID(0);
    }

    public UnsolicitedNotification(DERBuffer buffer) {
        super(buffer);
    }

    @Override
    public void setMessageID(int id) {
        if (id != 0) {
            throw new IllegalArgumentException("Message ID must be zero");
        }
        super.setMessageID(id);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        return o instanceof UnsolicitedNotification && super.equals(o);
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(10331, new Object[]{this.getMessageID(), this.getControls(), this.getResultCode(), this.getMatchedDN(), this.getDiagnosticMessage(), this.getReferralURLs(), this.getResponseName(), this.getResponseValue()});
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder
    extends ExtendedResponse.Builder {
        protected Builder() {
            super(new UnsolicitedNotification());
        }

        protected Builder(UnsolicitedNotification n) {
            super(n);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}

