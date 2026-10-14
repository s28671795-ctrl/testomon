/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.extended;

import java.util.Arrays;
import org.ldaptive.AbstractMessage;
import org.ldaptive.AbstractResult;
import org.ldaptive.LdapUtils;
import org.ldaptive.asn1.AbstractParseHandler;
import org.ldaptive.asn1.DERBuffer;
import org.ldaptive.asn1.DERParser;
import org.ldaptive.asn1.DERPath;
import org.ldaptive.asn1.OctetStringType;

public class ExtendedResponse
extends AbstractResult {
    public static final int PROTOCOL_OP = 24;
    private static final int HASH_CODE_SEED = 10259;
    private static final DERPath RESULT_CODE_PATH = new DERPath("/SEQ/APP(24)/ENUM[0]");
    private static final DERPath MATCHED_DN_PATH = new DERPath("/SEQ/APP(24)/OCTSTR[1]");
    private static final DERPath DIAGNOSTIC_MESSAGE_PATH = new DERPath("/SEQ/APP(24)/OCTSTR[2]");
    private static final DERPath REFERRAL_PATH = new DERPath("/SEQ/APP(24)/CTX(3)/OCTSTR[0]");
    private static final DERPath NAME_PATH = new DERPath("/SEQ/APP(24)/CTX(10)");
    private static final DERPath VALUE_PATH = new DERPath("/SEQ/APP(24)/CTX(11)");
    private String responseName;
    private byte[] responseValue;

    protected ExtendedResponse() {
    }

    public ExtendedResponse(DERBuffer buffer) {
        DERParser parser = new DERParser();
        parser.registerHandler(AbstractMessage.MessageIDHandler.PATH, new AbstractMessage.MessageIDHandler(this));
        parser.registerHandler(RESULT_CODE_PATH, new AbstractResult.ResultCodeHandler(this));
        parser.registerHandler(MATCHED_DN_PATH, new AbstractResult.MatchedDNHandler(this));
        parser.registerHandler(DIAGNOSTIC_MESSAGE_PATH, new AbstractResult.DiagnosticMessageHandler(this));
        parser.registerHandler(REFERRAL_PATH, new AbstractResult.ReferralHandler(this));
        parser.registerHandler(NAME_PATH, new ResponseNameHandler(this));
        parser.registerHandler(VALUE_PATH, new ResponseValueHandler(this));
        parser.registerHandler(AbstractMessage.ControlsHandler.PATH, new AbstractMessage.ControlsHandler(this));
        parser.parse(buffer);
    }

    public String getResponseName() {
        return this.responseName;
    }

    public void setResponseName(String name) {
        this.responseName = name;
    }

    public byte[] getResponseValue() {
        return this.responseValue;
    }

    public void setResponseValue(byte[] value) {
        this.responseValue = value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof ExtendedResponse && super.equals(o)) {
            ExtendedResponse v = (ExtendedResponse)o;
            return LdapUtils.areEqual(this.responseName, v.responseName) && LdapUtils.areEqual(this.responseValue, v.responseValue);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(10259, new Object[]{this.getMessageID(), this.getControls(), this.getResultCode(), this.getMatchedDN(), this.getDiagnosticMessage(), this.getReferralURLs(), this.responseName, this.responseValue});
    }

    @Override
    public String toString() {
        return super.toString() + ", " + "responseName=" + this.responseName + ", " + "responseValue=" + Arrays.toString(this.responseValue);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder
    extends AbstractResult.AbstractBuilder<Builder, ExtendedResponse> {
        protected Builder() {
            super(new ExtendedResponse());
        }

        protected Builder(ExtendedResponse r) {
            super(r);
        }

        @Override
        protected Builder self() {
            return this;
        }

        public Builder responseName(String name) {
            ((ExtendedResponse)this.object).responseName = name;
            return this;
        }

        public Builder responseValue(byte[] value) {
            ((ExtendedResponse)this.object).responseValue = value;
            return this;
        }
    }

    protected static class ResponseValueHandler
    extends AbstractParseHandler<ExtendedResponse> {
        ResponseValueHandler(ExtendedResponse response) {
            super(response);
        }

        @Override
        public void handle(DERParser parser, DERBuffer encoded) {
            ((ExtendedResponse)this.getObject()).setResponseValue(encoded.getRemainingBytes());
        }
    }

    protected static class ResponseNameHandler
    extends AbstractParseHandler<ExtendedResponse> {
        ResponseNameHandler(ExtendedResponse response) {
            super(response);
        }

        @Override
        public void handle(DERParser parser, DERBuffer encoded) {
            ((ExtendedResponse)this.getObject()).setResponseName(OctetStringType.decode(encoded));
        }
    }
}

