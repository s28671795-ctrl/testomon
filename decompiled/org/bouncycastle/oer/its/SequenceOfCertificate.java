/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.oer.its;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.oer.its.Certificate;
import org.bouncycastle.oer.its.Utils;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class SequenceOfCertificate
extends ASN1Object {
    private final List<Certificate> certificates;

    public SequenceOfCertificate(List<Certificate> list) {
        this.certificates = Collections.unmodifiableList(list);
    }

    public static SequenceOfCertificate getInstance(Object object) {
        if (object instanceof SequenceOfCertificate) {
            return (SequenceOfCertificate)object;
        }
        Iterator<ASN1Encodable> iterator2 = ASN1Sequence.getInstance(object).iterator();
        ArrayList<Certificate> arrayList = new ArrayList<Certificate>();
        while (iterator2.hasNext()) {
            arrayList.add(Certificate.getInstance(iterator2.next()));
        }
        return new SequenceOfCertificate(arrayList);
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        return Utils.toSequence(this.certificates);
    }

    public List<Certificate> getCertificates() {
        return this.certificates;
    }

    public static class Builder {
        List<Certificate> certificates = new ArrayList<Certificate>();

        public Builder add(Certificate ... certificateArray) {
            this.certificates.addAll(Arrays.asList(certificateArray));
            return this;
        }

        public SequenceOfCertificate build() {
            return new SequenceOfCertificate(this.certificates);
        }
    }
}

