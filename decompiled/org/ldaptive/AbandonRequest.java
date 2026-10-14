/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import org.ldaptive.AbstractRequestMessage;
import org.ldaptive.asn1.ApplicationDERTag;
import org.ldaptive.asn1.DEREncoder;
import org.ldaptive.asn1.DERTag;
import org.ldaptive.asn1.IntegerType;

public class AbandonRequest
extends AbstractRequestMessage {
    public static final int PROTOCOL_OP = 16;
    private int messageID;

    private AbandonRequest() {
    }

    public AbandonRequest(int id) {
        this.messageID = id;
    }

    public int getMessageID() {
        return this.messageID;
    }

    @Override
    protected DEREncoder[] getRequestEncoders(int id) {
        return new DEREncoder[]{new IntegerType(id), new IntegerType((DERTag)new ApplicationDERTag(16, false), this.messageID)};
    }

    @Override
    public String toString() {
        return super.toString() + ", " + "messageID=" + this.messageID;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder
    extends AbstractRequestMessage.AbstractBuilder<Builder, AbandonRequest> {
        protected Builder() {
            super(new AbandonRequest());
        }

        @Override
        protected Builder self() {
            return this;
        }

        public Builder id(int id) {
            ((AbandonRequest)this.object).messageID = id;
            return this.self();
        }
    }
}

