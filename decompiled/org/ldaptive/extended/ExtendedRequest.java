/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.extended;

import java.util.Arrays;
import org.ldaptive.AbstractRequestMessage;
import org.ldaptive.asn1.ApplicationDERTag;
import org.ldaptive.asn1.ConstructedDEREncoder;
import org.ldaptive.asn1.ContextDERTag;
import org.ldaptive.asn1.DEREncoder;
import org.ldaptive.asn1.DERTag;
import org.ldaptive.asn1.IntegerType;
import org.ldaptive.asn1.OctetStringType;

public class ExtendedRequest
extends AbstractRequestMessage {
    public static final int PROTOCOL_OP = 23;
    private String requestName;
    private byte[] requestValue;

    private ExtendedRequest() {
    }

    public ExtendedRequest(String name) {
        this.requestName = name;
    }

    public ExtendedRequest(String name, byte[] value) {
        this.requestName = name;
        this.requestValue = value;
    }

    protected void setRequestValue(byte[] value) {
        this.requestValue = value;
    }

    @Override
    protected DEREncoder[] getRequestEncoders(int id) {
        if (this.requestValue == null) {
            return new DEREncoder[]{new IntegerType(id), new ConstructedDEREncoder(new ApplicationDERTag(23, true), new OctetStringType((DERTag)new ContextDERTag(0, false), this.requestName))};
        }
        return new DEREncoder[]{new IntegerType(id), new ConstructedDEREncoder(new ApplicationDERTag(23, true), new OctetStringType((DERTag)new ContextDERTag(0, false), this.requestName), new OctetStringType((DERTag)new ContextDERTag(1, false), this.requestValue))};
    }

    @Override
    public String toString() {
        return super.toString() + ", " + "requestName=" + this.requestName + ", " + "requestValue=" + Arrays.toString(this.requestValue);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder
    extends AbstractRequestMessage.AbstractBuilder<Builder, ExtendedRequest> {
        protected Builder() {
            super(new ExtendedRequest());
        }

        protected Builder(ExtendedRequest r) {
            super(r);
        }

        @Override
        protected Builder self() {
            return this;
        }

        public Builder name(String name) {
            ((ExtendedRequest)this.object).requestName = name;
            return this.self();
        }

        public Builder value(byte[] value) {
            ((ExtendedRequest)this.object).requestValue = value;
            return this.self();
        }
    }
}

