/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1Choice;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.oer.its.BitmapSsp;

public class ServiceSpecificPermissions
extends ASN1Object
implements ASN1Choice {
    public static final int opaque = 0;
    public static final int extension = 1;
    public static final int bitmapSsp = 2;
    private final int choice;
    private final ASN1Encodable object;

    public ServiceSpecificPermissions(int n, ASN1Encodable aSN1Encodable) {
        this.choice = n;
        this.object = aSN1Encodable;
    }

    public static ServiceSpecificPermissions getInstance(Object object) {
        if (object instanceof ServiceSpecificPermissions) {
            return (ServiceSpecificPermissions)object;
        }
        ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance(object);
        switch (aSN1TaggedObject.getTagNo()) {
            case 0: 
            case 1: {
                return new ServiceSpecificPermissions(aSN1TaggedObject.getTagNo(), DEROctetString.getInstance(aSN1TaggedObject.getObject()));
            }
            case 2: {
                return new ServiceSpecificPermissions(aSN1TaggedObject.getTagNo(), BitmapSsp.getInstance(aSN1TaggedObject.getObject()));
            }
        }
        throw new IllegalArgumentException("unknown choice " + aSN1TaggedObject.getTagNo());
    }

    public static Builder builder() {
        return new Builder();
    }

    public int getChoice() {
        return this.choice;
    }

    public ASN1Encodable getObject() {
        return this.object;
    }

    public ASN1Primitive toASN1Primitive() {
        return new DERTaggedObject(this.choice, this.object);
    }

    public static class Builder {
        private int choice;
        private ASN1Encodable object;

        public Builder setChoice(int n) {
            this.choice = n;
            return this;
        }

        public Builder setObject(ASN1Encodable aSN1Encodable) {
            this.object = aSN1Encodable;
            return this;
        }

        public Builder opaque() {
            return this.setChoice(0);
        }

        public Builder extension(byte[] byArray) {
            return this.setChoice(2).setObject(new DEROctetString(byArray));
        }

        public Builder bitmapSsp(ASN1OctetString aSN1OctetString) {
            return this.setChoice(2).setObject(aSN1OctetString);
        }

        public ServiceSpecificPermissions createServiceSpecificPermissions() {
            return new ServiceSpecificPermissions(this.choice, this.object);
        }
    }
}

