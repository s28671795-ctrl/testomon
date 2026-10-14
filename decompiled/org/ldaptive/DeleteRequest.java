/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import org.ldaptive.AbstractRequestMessage;
import org.ldaptive.asn1.ApplicationDERTag;
import org.ldaptive.asn1.DEREncoder;
import org.ldaptive.asn1.DERTag;
import org.ldaptive.asn1.IntegerType;
import org.ldaptive.asn1.OctetStringType;

public class DeleteRequest
extends AbstractRequestMessage {
    public static final int PROTOCOL_OP = 10;
    private String ldapDn;

    private DeleteRequest() {
    }

    public DeleteRequest(String dn) {
        this.ldapDn = dn;
    }

    public String getDn() {
        return this.ldapDn;
    }

    @Override
    protected DEREncoder[] getRequestEncoders(int id) {
        return new DEREncoder[]{new IntegerType(id), new OctetStringType((DERTag)new ApplicationDERTag(10, false), this.ldapDn)};
    }

    @Override
    public String toString() {
        return super.toString() + ", " + "dn=" + this.ldapDn;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder
    extends AbstractRequestMessage.AbstractBuilder<Builder, DeleteRequest> {
        protected Builder() {
            super(new DeleteRequest());
        }

        @Override
        protected Builder self() {
            return this;
        }

        public Builder dn(String dn) {
            ((DeleteRequest)this.object).ldapDn = dn;
            return this.self();
        }
    }
}

