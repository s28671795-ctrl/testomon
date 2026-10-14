/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.oer.its.Utils;

public class BitmapSspRange
extends ASN1Object {
    private final ASN1OctetString sspValue;
    private final ASN1OctetString sspBitmask;

    public BitmapSspRange(ASN1OctetString aSN1OctetString, ASN1OctetString aSN1OctetString2) {
        this.sspValue = aSN1OctetString;
        this.sspBitmask = aSN1OctetString2;
    }

    public static BitmapSspRange getInstance(Object object) {
        if (object instanceof BitmapSspRange) {
            return (BitmapSspRange)object;
        }
        if (object != null) {
            ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(object);
            return new BitmapSspRange(ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(0)), ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(1)));
        }
        return null;
    }

    public ASN1OctetString getSspValue() {
        return this.sspValue;
    }

    public ASN1OctetString getSspBitmask() {
        return this.sspBitmask;
    }

    public ASN1Primitive toASN1Primitive() {
        return Utils.toSequence(this.sspValue, this.sspBitmask);
    }
}

