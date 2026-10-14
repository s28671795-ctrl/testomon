/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;
import org.ldaptive.AbstractRequestMessage;
import org.ldaptive.AttributeModification;
import org.ldaptive.asn1.ApplicationDERTag;
import org.ldaptive.asn1.ConstructedDEREncoder;
import org.ldaptive.asn1.DEREncoder;
import org.ldaptive.asn1.DERTag;
import org.ldaptive.asn1.IntegerType;
import org.ldaptive.asn1.OctetStringType;
import org.ldaptive.asn1.UniversalDERTag;

public class ModifyRequest
extends AbstractRequestMessage {
    public static final int PROTOCOL_OP = 6;
    private static final byte[] EMPTY_BYTE = new byte[0];
    private String ldapDn;
    private AttributeModification[] modifications;

    private ModifyRequest() {
    }

    public ModifyRequest(String entry, AttributeModification ... mod) {
        this.ldapDn = entry;
        this.modifications = mod;
    }

    public String getDn() {
        return this.ldapDn;
    }

    public AttributeModification[] getModifications() {
        return this.modifications;
    }

    @Override
    protected DEREncoder[] getRequestEncoders(int id) {
        return new DEREncoder[]{new IntegerType(id), new ConstructedDEREncoder(new ApplicationDERTag(6, true), new OctetStringType(this.ldapDn), new ConstructedDEREncoder(UniversalDERTag.SEQ, (DEREncoder[])Stream.of(this.modifications).map(m4 -> new ConstructedDEREncoder(UniversalDERTag.SEQ, new IntegerType((DERTag)UniversalDERTag.ENUM, m4.getOperation().ordinal()), new ConstructedDEREncoder(UniversalDERTag.SEQ, new OctetStringType(m4.getAttribute().getName()), new ConstructedDEREncoder(UniversalDERTag.SET, this.getAttributeValueEncoders(m4.getAttribute().getBinaryValues()))))).toArray(DEREncoder[]::new)))};
    }

    private DEREncoder[] getAttributeValueEncoders(Collection<byte[]> values2) {
        if (values2 == null || values2.size() == 0) {
            return new DEREncoder[]{() -> EMPTY_BYTE};
        }
        return (DEREncoder[])values2.stream().map(OctetStringType::new).toArray(DEREncoder[]::new);
    }

    @Override
    public String toString() {
        return super.toString() + ", " + "dn=" + this.ldapDn + ", " + "modifications=" + Arrays.toString(this.modifications);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder
    extends AbstractRequestMessage.AbstractBuilder<Builder, ModifyRequest> {
        protected Builder() {
            super(new ModifyRequest());
        }

        @Override
        protected Builder self() {
            return this;
        }

        public Builder dn(String dn) {
            ((ModifyRequest)this.object).ldapDn = dn;
            return this.self();
        }

        public Builder modifications(AttributeModification ... mod) {
            ((ModifyRequest)this.object).modifications = mod;
            return this.self();
        }

        public Builder modifications(Collection<AttributeModification> mod) {
            ((ModifyRequest)this.object).modifications = (AttributeModification[])mod.toArray(AttributeModification[]::new);
            return this.self();
        }
    }
}

