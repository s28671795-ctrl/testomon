/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.oer.its;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.oer.its.PsidSsp;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class SequenceOfPsidSsp
extends ASN1Object {
    private final List<PsidSsp> items;

    public SequenceOfPsidSsp(List<PsidSsp> list) {
        this.items = Collections.unmodifiableList(list);
    }

    public static SequenceOfPsidSsp getInstance(Object object) {
        if (object instanceof SequenceOfPsidSsp) {
            return (SequenceOfPsidSsp)object;
        }
        ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(object);
        Enumeration enumeration = aSN1Sequence.getObjects();
        ArrayList<PsidSsp> arrayList = new ArrayList<PsidSsp>();
        while (enumeration.hasMoreElements()) {
            arrayList.add(PsidSsp.getInstance(enumeration.nextElement()));
        }
        return new Builder().setItems(arrayList).createSequenceOfPsidSsp();
    }

    public static Builder builder() {
        return new Builder();
    }

    public List<PsidSsp> getItems() {
        return this.items;
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        Iterator<PsidSsp> iterator2 = this.items.iterator();
        while (iterator2.hasNext()) {
            aSN1EncodableVector.add(iterator2.next());
        }
        return new DERSequence(aSN1EncodableVector);
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static class Builder {
        private List<PsidSsp> items = new ArrayList<PsidSsp>();

        public Builder setItems(List<PsidSsp> list) {
            this.items = list;
            return this;
        }

        public Builder setItem(PsidSsp ... psidSspArray) {
            for (int i = 0; i != psidSspArray.length; ++i) {
                PsidSsp psidSsp = psidSspArray[i];
                this.items.add(psidSsp);
            }
            return this;
        }

        public SequenceOfPsidSsp createSequenceOfPsidSsp() {
            return new SequenceOfPsidSsp(this.items);
        }
    }
}

