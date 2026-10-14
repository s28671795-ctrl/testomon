/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.extended;

import java.util.Arrays;
import org.ldaptive.AbstractMessage;
import org.ldaptive.LdapUtils;
import org.ldaptive.asn1.AbstractParseHandler;
import org.ldaptive.asn1.DERBuffer;
import org.ldaptive.asn1.DERParser;
import org.ldaptive.asn1.DERPath;
import org.ldaptive.asn1.OctetStringType;
import org.ldaptive.asn1.ParseHandler;

public class IntermediateResponse
extends AbstractMessage {
    public static final int PROTOCOL_OP = 25;
    private static final int HASH_CODE_SEED = 10267;
    private static final DERPath RESPONSE_NAME_PATH = new DERPath("/SEQ/APP(25)/CTX(0)");
    private static final DERPath RESPONSE_VALUE_PATH = new DERPath("/SEQ/APP(25)/CTX(1)");
    private String responseName;
    private byte[] responseValue;

    protected IntermediateResponse() {
    }

    public IntermediateResponse(DERBuffer buffer) {
        DERParser parser = new DERParser();
        parser.registerHandler(AbstractMessage.MessageIDHandler.PATH, new AbstractMessage.MessageIDHandler(this));
        parser.registerHandler(RESPONSE_NAME_PATH, new ResponseNameHandler(this));
        parser.registerHandler(RESPONSE_VALUE_PATH, this.getResponseValueParseHandler());
        parser.registerHandler(AbstractMessage.ControlsHandler.PATH, new AbstractMessage.ControlsHandler(this));
        parser.parse(buffer);
    }

    protected ParseHandler getResponseValueParseHandler() {
        return new ResponseValueHandler(this);
    }

    public String getResponseName() {
        return this.responseName;
    }

    protected void setResponseName(String name) {
        this.responseName = name;
    }

    public byte[] getResponseValue() {
        return this.responseValue;
    }

    protected void setResponseValue(byte[] value) {
        this.responseValue = value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof IntermediateResponse && super.equals(o)) {
            IntermediateResponse v = (IntermediateResponse)o;
            return LdapUtils.areEqual(this.responseName, v.responseName) && LdapUtils.areEqual(this.responseValue, v.responseValue);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(10267, this.getMessageID(), this.getControls(), this.responseName, this.responseValue);
    }

    @Override
    public String toString() {
        return super.toString() + ", " + "responseName=" + this.responseName + ", " + "responseValue=" + Arrays.toString(this.responseValue);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder
    extends AbstractMessage.AbstractBuilder<Builder, IntermediateResponse> {
        protected Builder() {
            super(new IntermediateResponse());
        }

        protected Builder(IntermediateResponse r) {
            super(r);
        }

        @Override
        protected Builder self() {
            return this;
        }

        public Builder responseName(String name) {
            ((IntermediateResponse)this.object).setResponseName(name);
            return this;
        }

        public Builder responseValue(byte[] value) {
            ((IntermediateResponse)this.object).setResponseValue(value);
            return this;
        }
    }

    protected static class ResponseValueHandler
    extends AbstractParseHandler<IntermediateResponse> {
        ResponseValueHandler(IntermediateResponse response) {
            super(response);
        }

        @Override
        public void handle(DERParser parser, DERBuffer encoded) {
            if (encoded.remaining() > 0) {
                DERParser p = new DERParser();
                p.readTag(encoded).getTagNo();
                p.readLength(encoded);
                ((IntermediateResponse)this.getObject()).setResponseValue(encoded.getRemainingBytes());
            }
        }
    }

    protected static class ResponseNameHandler
    extends AbstractParseHandler<IntermediateResponse> {
        ResponseNameHandler(IntermediateResponse response) {
            super(response);
        }

        @Override
        public void handle(DERParser parser, DERBuffer encoded) {
            if (encoded.remaining() > 0) {
                ((IntermediateResponse)this.getObject()).setResponseName(OctetStringType.decode(encoded));
            }
        }
    }
}

