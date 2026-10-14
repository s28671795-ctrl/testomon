/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import org.ldaptive.AbstractRequestMessage;
import org.ldaptive.BindRequest;
import org.ldaptive.Credential;
import org.ldaptive.LdapUtils;
import org.ldaptive.asn1.ApplicationDERTag;
import org.ldaptive.asn1.ConstructedDEREncoder;
import org.ldaptive.asn1.ContextType;
import org.ldaptive.asn1.DEREncoder;
import org.ldaptive.asn1.IntegerType;
import org.ldaptive.asn1.OctetStringType;

public class SimpleBindRequest
extends AbstractRequestMessage
implements BindRequest {
    private String ldapDN;
    private String password;

    private SimpleBindRequest() {
    }

    public SimpleBindRequest(String name, String pass) {
        this.setLdapDN(name);
        this.setPassword(pass);
    }

    public SimpleBindRequest(String name, Credential cred) {
        this.setLdapDN(name);
        this.setPassword(cred.getString());
    }

    protected void setLdapDN(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("name cannot be null or empty");
        }
        this.ldapDN = name;
    }

    protected void setPassword(String pass) {
        if (pass == null || pass.isEmpty()) {
            throw new IllegalArgumentException("password cannot be null or empty");
        }
        this.password = pass;
    }

    @Override
    protected DEREncoder[] getRequestEncoders(int id) {
        return new DEREncoder[]{new IntegerType(id), new ConstructedDEREncoder(new ApplicationDERTag(0, true), new IntegerType(3), new OctetStringType(this.ldapDN), new ContextType(0, LdapUtils.utf8Encode(this.password, false)))};
    }

    @Override
    public String toString() {
        return super.toString() + ", " + "dn=" + this.ldapDN;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder
    extends AbstractRequestMessage.AbstractBuilder<Builder, SimpleBindRequest> {
        protected Builder() {
            super(new SimpleBindRequest());
        }

        @Override
        protected Builder self() {
            return this;
        }

        public Builder dn(String dn) {
            ((SimpleBindRequest)this.object).setLdapDN(dn);
            return this.self();
        }

        public Builder password(String password) {
            ((SimpleBindRequest)this.object).setPassword(password);
            return this.self();
        }

        public Builder password(Credential credential) {
            ((SimpleBindRequest)this.object).setPassword(credential.getString());
            return this.self();
        }
    }
}

