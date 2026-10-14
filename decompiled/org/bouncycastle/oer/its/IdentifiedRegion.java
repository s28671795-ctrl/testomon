/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1Choice;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.oer.its.CountryAndRegions;
import org.bouncycastle.oer.its.CountryOnly;
import org.bouncycastle.oer.its.RegionAndSubregions;
import org.bouncycastle.oer.its.RegionInterface;

public class IdentifiedRegion
extends ASN1Object
implements ASN1Choice,
RegionInterface {
    public static final int countryOnly = 0;
    public static final int countryAndRegions = 1;
    public static final int countAndSubregions = 2;
    public static final int extension = 3;
    private int choice;
    private ASN1Encodable region;

    public IdentifiedRegion(int n, ASN1Encodable aSN1Encodable) {
        this.choice = n;
        this.region = aSN1Encodable;
    }

    public static IdentifiedRegion getInstance(Object object) {
        if (object instanceof IdentifiedRegion) {
            return (IdentifiedRegion)object;
        }
        ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance(object);
        int n = aSN1TaggedObject.getTagNo();
        object = aSN1TaggedObject.getObject();
        switch (n) {
            case 0: {
                return new IdentifiedRegion(n, CountryOnly.getInstance(object));
            }
            case 1: {
                return new IdentifiedRegion(n, CountryAndRegions.getInstance(object));
            }
            case 2: {
                return new IdentifiedRegion(n, RegionAndSubregions.getInstance(object));
            }
            case 3: {
                return new IdentifiedRegion(n, DEROctetString.getInstance(object));
            }
        }
        throw new IllegalArgumentException("unknown choice " + n);
    }

    public ASN1Primitive toASN1Primitive() {
        return new DERTaggedObject(this.choice, (ASN1Object)this.region).toASN1Primitive();
    }
}

