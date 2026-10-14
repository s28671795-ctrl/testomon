/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.oer.its;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.oer.its.PsidSspRange;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class SequenceOfPsidSspRange
extends ASN1Object {
    private final List<PsidSspRange> items;

    public SequenceOfPsidSspRange(List<PsidSspRange> list) {
        this.items = Collections.unmodifiableList(list);
    }

    public static SequenceOfPsidSspRange getInstance(Object object) {
        if (object instanceof SequenceOfPsidSspRange) {
            return (SequenceOfPsidSspRange)object;
        }
        ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(object);
        Enumeration enumeration = aSN1Sequence.getObjects();
        ArrayList<PsidSspRange> arrayList = new ArrayList<PsidSspRange>();
        while (enumeration.hasMoreElements()) {
            arrayList.add(PsidSspRange.getInstance(enumeration.nextElement()));
        }
        return new SequenceOfPsidSspRange(arrayList);
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        Iterator<PsidSspRange> iterator2 = this.items.iterator();
        while (iterator2.hasNext()) {
            aSN1EncodableVector.add(iterator2.next());
        }
        return new DERSequence(aSN1EncodableVector);
    }

    public static class Builder {
        private ArrayList<PsidSspRange> psidSspRanges = new ArrayList();

        public Builder add(PsidSspRange ... psidSspRangeArray) {
            this.psidSspRanges.addAll(Arrays.asList(psidSspRangeArray));
            return this;
        }

        public SequenceOfPsidSspRange build() {
            return new SequenceOfPsidSspRange(this.psidSspRanges);
        }
    }
}

