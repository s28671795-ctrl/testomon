/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import org.ldaptive.AbstractRequestMessage;
import org.ldaptive.BindRequest;
import org.ldaptive.asn1.ApplicationDERTag;
import org.ldaptive.asn1.ConstructedDEREncoder;
import org.ldaptive.asn1.ContextType;
import org.ldaptive.asn1.DEREncoder;
import org.ldaptive.asn1.IntegerType;
import org.ldaptive.asn1.OctetStringType;

public class AnonymousBindRequest
extends AbstractRequestMessage
implements BindRequest {
    @Override
    protected DEREncoder[] getRequestEncoders(int id) {
        return new DEREncoder[]{new IntegerType(id), new ConstructedDEREncoder(new ApplicationDERTag(0, true), new IntegerType(3), new OctetStringType(""), new ContextType(0, (byte[])null))};
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder
    extends AbstractRequestMessage.AbstractBuilder<Builder, AnonymousBindRequest> {
        protected Builder() {
            super(new AnonymousBindRequest());
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}

