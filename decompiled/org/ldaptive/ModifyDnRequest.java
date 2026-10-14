/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import org.ldaptive.AbstractRequestMessage;
import org.ldaptive.asn1.ApplicationDERTag;
import org.ldaptive.asn1.BooleanType;
import org.ldaptive.asn1.ConstructedDEREncoder;
import org.ldaptive.asn1.ContextDERTag;
import org.ldaptive.asn1.DEREncoder;
import org.ldaptive.asn1.DERTag;
import org.ldaptive.asn1.IntegerType;
import org.ldaptive.asn1.OctetStringType;

public class ModifyDnRequest
extends AbstractRequestMessage {
    public static final int PROTOCOL_OP = 12;
    private String oldModifyDn;
    private String newModifyRDn;
    private boolean deleteOldRDn;
    private String newSuperiorDn;

    private ModifyDnRequest() {
    }

    public ModifyDnRequest(String oldDN, String newRDN, boolean delete) {
        this(oldDN, newRDN, delete, null);
    }

    public ModifyDnRequest(String oldDN, String newRDN, boolean delete, String newSuperior) {
        this.oldModifyDn = oldDN;
        this.newModifyRDn = newRDN;
        this.deleteOldRDn = delete;
        this.newSuperiorDn = newSuperior;
    }

    public String getOldDn() {
        return this.oldModifyDn;
    }

    public String getNewRDn() {
        return this.newModifyRDn;
    }

    public boolean isDeleteOldRDn() {
        return this.deleteOldRDn;
    }

    public String getNewSuperiorDn() {
        return this.newSuperiorDn;
    }

    @Override
    protected DEREncoder[] getRequestEncoders(int id) {
        if (this.newSuperiorDn == null) {
            return new DEREncoder[]{new IntegerType(id), new ConstructedDEREncoder(new ApplicationDERTag(12, true), new OctetStringType(this.oldModifyDn), new OctetStringType(this.newModifyRDn), new BooleanType(this.deleteOldRDn))};
        }
        return new DEREncoder[]{new IntegerType(id), new ConstructedDEREncoder(new ApplicationDERTag(12, true), new OctetStringType(this.oldModifyDn), new OctetStringType(this.newModifyRDn), new BooleanType(this.deleteOldRDn), new OctetStringType((DERTag)new ContextDERTag(0, false), this.newSuperiorDn))};
    }

    @Override
    public String toString() {
        return super.toString() + ", " + "oldModifyDn=" + this.oldModifyDn + ", " + "newModifyRDn=" + this.newModifyRDn + ", " + "delete=" + this.deleteOldRDn + ", " + "superior=" + this.newSuperiorDn;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder
    extends AbstractRequestMessage.AbstractBuilder<Builder, ModifyDnRequest> {
        protected Builder() {
            super(new ModifyDnRequest());
        }

        @Override
        protected Builder self() {
            return this;
        }

        public Builder oldDN(String dn) {
            ((ModifyDnRequest)this.object).oldModifyDn = dn;
            return this.self();
        }

        public Builder newRDN(String dn) {
            ((ModifyDnRequest)this.object).newModifyRDn = dn;
            return this.self();
        }

        public Builder delete(boolean delete) {
            ((ModifyDnRequest)this.object).deleteOldRDn = delete;
            return this.self();
        }

        public Builder superior(String dn) {
            ((ModifyDnRequest)this.object).newSuperiorDn = dn;
            return this.self();
        }
    }
}

