/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import org.ldaptive.AbstractRequestMessage;
import org.ldaptive.asn1.ApplicationDERTag;
import org.ldaptive.asn1.ConstructedDEREncoder;
import org.ldaptive.asn1.DEREncoder;
import org.ldaptive.asn1.IntegerType;
import org.ldaptive.asn1.OctetStringType;
import org.ldaptive.asn1.UniversalDERTag;

public class CompareRequest
extends AbstractRequestMessage {
    public static final int PROTOCOL_OP = 14;
    private String ldapDn;
    private String attributeDesc;
    private String assertionValue;

    private CompareRequest() {
    }

    public CompareRequest(String dn, String name, String value) {
        this.ldapDn = dn;
        this.attributeDesc = name;
        this.assertionValue = value;
    }

    public String getDn() {
        return this.ldapDn;
    }

    public String getName() {
        return this.attributeDesc;
    }

    public String getValue() {
        return this.assertionValue;
    }

    @Override
    protected DEREncoder[] getRequestEncoders(int id) {
        return new DEREncoder[]{new IntegerType(id), new ConstructedDEREncoder(new ApplicationDERTag(14, true), new OctetStringType(this.ldapDn), new ConstructedDEREncoder(UniversalDERTag.SEQ, new OctetStringType(this.attributeDesc), new OctetStringType(this.assertionValue)))};
    }

    @Override
    public String toString() {
        return super.toString() + ", " + "dn=" + this.ldapDn + ", " + "attributeDesc=" + this.attributeDesc + ", " + "assertionValue=" + ("userPassword".equals(this.attributeDesc) ? "<suppressed>" : this.assertionValue);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder
    extends AbstractRequestMessage.AbstractBuilder<Builder, CompareRequest> {
        protected Builder() {
            super(new CompareRequest());
        }

        @Override
        protected Builder self() {
            return this;
        }

        public Builder dn(String dn) {
            ((CompareRequest)this.object).ldapDn = dn;
            return this.self();
        }

        public Builder name(String name) {
            ((CompareRequest)this.object).attributeDesc = name;
            return this.self();
        }

        public Builder value(String value) {
            ((CompareRequest)this.object).assertionValue = value;
            return this.self();
        }
    }
}

