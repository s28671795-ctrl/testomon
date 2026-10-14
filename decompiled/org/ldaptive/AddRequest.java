/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;
import org.ldaptive.AbstractRequestMessage;
import org.ldaptive.LdapAttribute;
import org.ldaptive.asn1.ApplicationDERTag;
import org.ldaptive.asn1.ConstructedDEREncoder;
import org.ldaptive.asn1.DEREncoder;
import org.ldaptive.asn1.IntegerType;
import org.ldaptive.asn1.OctetStringType;
import org.ldaptive.asn1.UniversalDERTag;

public class AddRequest
extends AbstractRequestMessage {
    public static final int PROTOCOL_OP = 8;
    private String ldapDn;
    private LdapAttribute[] attributes;

    private AddRequest() {
    }

    public AddRequest(String dn, LdapAttribute ... attrs) {
        this.ldapDn = dn;
        this.attributes = attrs;
    }

    public AddRequest(String dn, Collection<LdapAttribute> attrs) {
        this.ldapDn = dn;
        this.attributes = (LdapAttribute[])attrs.toArray(LdapAttribute[]::new);
    }

    public String getDn() {
        return this.ldapDn;
    }

    public LdapAttribute[] getAttributes() {
        return this.attributes;
    }

    @Override
    protected DEREncoder[] getRequestEncoders(int id) {
        return new DEREncoder[]{new IntegerType(id), new ConstructedDEREncoder(new ApplicationDERTag(8, true), new OctetStringType(this.ldapDn), new ConstructedDEREncoder(UniversalDERTag.SEQ, (DEREncoder[])Stream.of(this.attributes).map(a -> new ConstructedDEREncoder(UniversalDERTag.SEQ, new OctetStringType(a.getName()), new ConstructedDEREncoder(UniversalDERTag.SET, (DEREncoder[])a.getBinaryValues().stream().map(OctetStringType::new).toArray(DEREncoder[]::new)))).toArray(DEREncoder[]::new)))};
    }

    @Override
    public String toString() {
        return super.toString() + ", " + "dn=" + this.ldapDn + ", " + "attributes=" + Arrays.toString(this.attributes);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder
    extends AbstractRequestMessage.AbstractBuilder<Builder, AddRequest> {
        protected Builder() {
            super(new AddRequest());
        }

        @Override
        protected Builder self() {
            return this;
        }

        public Builder dn(String dn) {
            ((AddRequest)this.object).ldapDn = dn;
            return this.self();
        }

        public Builder attributes(LdapAttribute ... attrs) {
            ((AddRequest)this.object).attributes = attrs;
            return this.self();
        }

        public Builder attributes(Collection<LdapAttribute> attrs) {
            ((AddRequest)this.object).attributes = (LdapAttribute[])attrs.toArray(LdapAttribute[]::new);
            return this.self();
        }
    }
}

