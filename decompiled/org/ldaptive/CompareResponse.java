/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import org.ldaptive.AbstractMessage;
import org.ldaptive.AbstractResult;
import org.ldaptive.LdapUtils;
import org.ldaptive.ResultCode;
import org.ldaptive.asn1.DERBuffer;
import org.ldaptive.asn1.DERParser;
import org.ldaptive.asn1.DERPath;

public class CompareResponse
extends AbstractResult {
    public static final int PROTOCOL_OP = 15;
    private static final int HASH_CODE_SEED = 10247;
    private static final DERPath RESULT_CODE_PATH = new DERPath("/SEQ/APP(15)/ENUM[0]");
    private static final DERPath MATCHED_DN_PATH = new DERPath("/SEQ/APP(15)/OCTSTR[1]");
    private static final DERPath DIAGNOSTIC_MESSAGE_PATH = new DERPath("/SEQ/APP(15)/OCTSTR[2]");
    private static final DERPath REFERRAL_PATH = new DERPath("/SEQ/APP(15)/CTX(3)/OCTSTR[0]");

    private CompareResponse() {
    }

    public CompareResponse(DERBuffer buffer) {
        DERParser parser = new DERParser();
        parser.registerHandler(AbstractMessage.MessageIDHandler.PATH, new AbstractMessage.MessageIDHandler(this));
        parser.registerHandler(RESULT_CODE_PATH, new AbstractResult.ResultCodeHandler(this));
        parser.registerHandler(MATCHED_DN_PATH, new AbstractResult.MatchedDNHandler(this));
        parser.registerHandler(DIAGNOSTIC_MESSAGE_PATH, new AbstractResult.DiagnosticMessageHandler(this));
        parser.registerHandler(REFERRAL_PATH, new AbstractResult.ReferralHandler(this));
        parser.registerHandler(AbstractMessage.ControlsHandler.PATH, new AbstractMessage.ControlsHandler(this));
        parser.parse(buffer);
    }

    public boolean isTrue() {
        return ResultCode.COMPARE_TRUE == this.getResultCode();
    }

    public boolean isFalse() {
        return ResultCode.COMPARE_FALSE == this.getResultCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        return o instanceof CompareResponse && super.equals(o);
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(10247, new Object[]{this.getMessageID(), this.getControls(), this.getResultCode(), this.getMatchedDN(), this.getDiagnosticMessage(), this.getReferralURLs()});
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder
    extends AbstractResult.AbstractBuilder<Builder, CompareResponse> {
        protected Builder() {
            super(new CompareResponse());
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}

