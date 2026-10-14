/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.oer.its;

import java.util.Collections;
import java.util.List;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.oer.its.IdentifiedRegion;
import org.bouncycastle.oer.its.Utils;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class SequenceOfIdentifiedRegion
extends ASN1Object {
    private final List<IdentifiedRegion> identifiedRegions;

    public SequenceOfIdentifiedRegion(List<IdentifiedRegion> list) {
        this.identifiedRegions = Collections.unmodifiableList(list);
    }

    public static SequenceOfIdentifiedRegion getInstance(Object object) {
        if (object instanceof SequenceOfIdentifiedRegion) {
            return (SequenceOfIdentifiedRegion)object;
        }
        return new SequenceOfIdentifiedRegion(Utils.fillList(IdentifiedRegion.class, ASN1Sequence.getInstance(object)));
    }

    public List<IdentifiedRegion> getIdentifiedRegions() {
        return this.identifiedRegions;
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        return new DERSequence(this.identifiedRegions.toArray(new ASN1Encodable[0]));
    }
}

