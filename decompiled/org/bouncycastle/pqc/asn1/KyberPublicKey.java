/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.asn1;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.util.Arrays;

public class KyberPublicKey
extends ASN1Object {
    private byte[] t;
    private byte[] rho;

    public KyberPublicKey(byte[] byArray, byte[] byArray2) {
        this.t = byArray;
        this.rho = byArray2;
    }

    public KyberPublicKey(ASN1Sequence aSN1Sequence) {
        this.t = Arrays.clone(ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(0)).getOctets());
        this.rho = Arrays.clone(ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(1)).getOctets());
    }

    public byte[] getT() {
        return Arrays.clone(this.t);
    }

    public byte[] getRho() {
        return Arrays.clone(this.rho);
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(new DEROctetString(this.t));
        aSN1EncodableVector.add(new DEROctetString(this.rho));
        return new DERSequence(aSN1EncodableVector);
    }

    public static KyberPublicKey getInstance(Object object) {
        if (object instanceof KyberPublicKey) {
            return (KyberPublicKey)object;
        }
        if (object != null) {
            return new KyberPublicKey(ASN1Sequence.getInstance(object));
        }
        return null;
    }
}

