/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.extended;

import org.ldaptive.LdapUtils;
import org.ldaptive.asn1.DERBuffer;
import org.ldaptive.extended.UnsolicitedNotification;

public class NoticeOfDisconnection
extends UnsolicitedNotification {
    public static final String OID = "1.3.6.1.4.1.1466.20036";
    private static final int HASH_CODE_SEED = 10289;

    public NoticeOfDisconnection() {
        this.setResponseName(OID);
    }

    public NoticeOfDisconnection(DERBuffer buffer) {
        super(buffer);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        return o instanceof NoticeOfDisconnection && super.equals(o);
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(10289, new Object[]{this.getMessageID(), this.getControls(), this.getResultCode(), this.getMatchedDN(), this.getDiagnosticMessage(), this.getReferralURLs(), this.getResponseName(), this.getResponseValue()});
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder
    extends UnsolicitedNotification.Builder {
        protected Builder() {
            super(new NoticeOfDisconnection());
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}

