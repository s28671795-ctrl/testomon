/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.sasl;

import org.ldaptive.AbstractRequestMessage;
import org.ldaptive.BindRequest;
import org.ldaptive.LdapUtils;
import org.ldaptive.asn1.ApplicationDERTag;
import org.ldaptive.asn1.ConstructedDEREncoder;
import org.ldaptive.asn1.ContextDERTag;
import org.ldaptive.asn1.DEREncoder;
import org.ldaptive.asn1.IntegerType;
import org.ldaptive.asn1.OctetStringType;

public class SaslBindRequest
extends AbstractRequestMessage
implements BindRequest {
    private String saslMechanism;
    private byte[] saslCredentials;

    private SaslBindRequest() {
    }

    public SaslBindRequest(String mechanism) {
        this(mechanism, (byte[])null);
    }

    public SaslBindRequest(String mechanism, String credentials) {
        this(mechanism, LdapUtils.utf8Encode(credentials));
    }

    public SaslBindRequest(String mechanism, byte[] credentials) {
        this.saslMechanism = mechanism;
        this.saslCredentials = credentials;
    }

    @Override
    protected DEREncoder[] getRequestEncoders(int id) {
        ConstructedDEREncoder saslMechanismEncoder = this.saslCredentials == null ? new ConstructedDEREncoder(new ContextDERTag(3, true), new OctetStringType(this.saslMechanism)) : new ConstructedDEREncoder(new ContextDERTag(3, true), new OctetStringType(this.saslMechanism), new OctetStringType(this.saslCredentials));
        return new DEREncoder[]{new IntegerType(id), new ConstructedDEREncoder(new ApplicationDERTag(0, true), new IntegerType(3), new OctetStringType(""), saslMechanismEncoder)};
    }

    @Override
    public String toString() {
        return super.toString() + ", " + "saslMechanism=" + this.saslMechanism;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder
    extends AbstractRequestMessage.AbstractBuilder<Builder, SaslBindRequest> {
        protected Builder() {
            super(new SaslBindRequest());
        }

        @Override
        protected Builder self() {
            return this;
        }

        public Builder mechanism(String mechanism) {
            ((SaslBindRequest)this.object).saslMechanism = mechanism;
            return this.self();
        }

        public Builder credentials(byte[] credentials) {
            ((SaslBindRequest)this.object).saslCredentials = credentials;
            return this.self();
        }

        public Builder credentials(String credentials) {
            ((SaslBindRequest)this.object).saslCredentials = LdapUtils.utf8Encode(credentials);
            return this.self();
        }
    }
}

