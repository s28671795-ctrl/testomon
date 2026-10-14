/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import org.ldaptive.LdapUtils;
import org.ldaptive.Message;
import org.ldaptive.ResultCode;

public interface Result
extends Message {
    public static final boolean ENCODE_CNTRL_CHARS = Boolean.valueOf(System.getProperty("org.ldaptive.response.ENCODE_CNTRL_CHARS", "false"));

    public ResultCode getResultCode();

    public String getMatchedDN();

    public String getDiagnosticMessage();

    public String[] getReferralURLs();

    default public boolean isSuccess() {
        return ResultCode.SUCCESS == this.getResultCode();
    }

    default public String getEncodedDiagnosticMessage() {
        if (this.getDiagnosticMessage() != null && !"".equals(this.getDiagnosticMessage()) && ENCODE_CNTRL_CHARS) {
            return LdapUtils.percentEncodeControlChars(this.getDiagnosticMessage());
        }
        return this.getDiagnosticMessage();
    }
}

