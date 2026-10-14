/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import org.ldaptive.AbstractMessage;
import org.ldaptive.AbstractResult;
import org.ldaptive.LdapUtils;
import org.ldaptive.asn1.AbstractParseHandler;
import org.ldaptive.asn1.DERBuffer;
import org.ldaptive.asn1.DERParser;
import org.ldaptive.asn1.DERPath;

public class BindResponse
extends AbstractResult {
    public static final int PROTOCOL_OP = 1;
    private static final int HASH_CODE_SEED = 10243;
    private static final DERPath RESULT_CODE_PATH = new DERPath("/SEQ/APP(1)/ENUM[0]");
    private static final DERPath MATCHED_DN_PATH = new DERPath("/SEQ/APP(1)/OCTSTR[1]");
    private static final DERPath DIAGNOSTIC_MESSAGE_PATH = new DERPath("/SEQ/APP(1)/OCTSTR[2]");
    private static final DERPath REFERRAL_PATH = new DERPath("/SEQ/APP(1)/CTX(3)/OCTSTR[0]");
    private static final DERPath SASL_CREDENTIALS_PATH = new DERPath("/SEQ/APP(1)/CTX(7)");
    private byte[] serverSaslCreds;

    private BindResponse() {
    }

    public BindResponse(DERBuffer buffer) {
        DERParser parser = new DERParser();
        parser.registerHandler(AbstractMessage.MessageIDHandler.PATH, new AbstractMessage.MessageIDHandler(this));
        parser.registerHandler(RESULT_CODE_PATH, new AbstractResult.ResultCodeHandler(this));
        parser.registerHandler(MATCHED_DN_PATH, new AbstractResult.MatchedDNHandler(this));
        parser.registerHandler(DIAGNOSTIC_MESSAGE_PATH, new AbstractResult.DiagnosticMessageHandler(this));
        parser.registerHandler(REFERRAL_PATH, new AbstractResult.ReferralHandler(this));
        parser.registerHandler(SASL_CREDENTIALS_PATH, new SASLCredsHandler(this));
        parser.registerHandler(AbstractMessage.ControlsHandler.PATH, new AbstractMessage.ControlsHandler(this));
        parser.parse(buffer);
    }

    public byte[] getServerSaslCreds() {
        return this.serverSaslCreds;
    }

    public void setServerSaslCreds(byte[] creds) {
        this.serverSaslCreds = creds;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof BindResponse && super.equals(o)) {
            BindResponse v = (BindResponse)o;
            return LdapUtils.areEqual(this.serverSaslCreds, v.serverSaslCreds);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(10243, new Object[]{this.getMessageID(), this.getControls(), this.getResultCode(), this.getMatchedDN(), this.getDiagnosticMessage(), this.getReferralURLs(), this.serverSaslCreds});
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder
    extends AbstractResult.AbstractBuilder<Builder, BindResponse> {
        protected Builder() {
            super(new BindResponse());
        }

        @Override
        protected Builder self() {
            return this;
        }

        public Builder serverSaslCreds(byte[] creds) {
            ((BindResponse)this.object).setServerSaslCreds(creds);
            return this;
        }
    }

    protected static class SASLCredsHandler
    extends AbstractParseHandler<BindResponse> {
        SASLCredsHandler(BindResponse response) {
            super(response);
        }

        @Override
        public void handle(DERParser parser, DERBuffer encoded) {
            if (encoded.remaining() > 0) {
                ((BindResponse)this.getObject()).setServerSaslCreds(encoded.getRemainingBytes());
            }
        }
    }
}

