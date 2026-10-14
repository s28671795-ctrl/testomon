/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.asn1;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.pqc.asn1.KyberPublicKey;
import org.bouncycastle.util.Arrays;

public class KyberPrivateKey
extends ASN1Object {
    private int version;
    private byte[] s;
    private KyberPublicKey publicKey;
    private byte[] hpk;
    private byte[] nonce;

    public KyberPrivateKey(int n, byte[] byArray, byte[] byArray2, byte[] byArray3, KyberPublicKey kyberPublicKey) {
        this.version = n;
        this.s = byArray;
        this.publicKey = kyberPublicKey;
        this.hpk = byArray2;
        this.nonce = byArray3;
    }

    public KyberPrivateKey(int n, byte[] byArray, byte[] byArray2, byte[] byArray3) {
        this(n, byArray, byArray2, byArray3, null);
    }

    public int getVersion() {
        return this.version;
    }

    public byte[] getS() {
        return Arrays.clone(this.s);
    }

    public KyberPublicKey getPublicKey() {
        return this.publicKey;
    }

    public byte[] getHpk() {
        return Arrays.clone(this.hpk);
    }

    public byte[] getNonce() {
        return Arrays.clone(this.nonce);
    }

    private KyberPrivateKey(ASN1Sequence aSN1Sequence) {
        this.version = ASN1Integer.getInstance(aSN1Sequence.getObjectAt(0)).intValueExact();
        if (this.version != 0) {
            throw new IllegalArgumentException("unrecognized version");
        }
        this.s = Arrays.clone(ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(1)).getOctets());
        int n = 1;
        if (aSN1Sequence.size() == 5) {
            n = 0;
            this.publicKey = KyberPublicKey.getInstance(aSN1Sequence.getObjectAt(2));
        }
        this.hpk = Arrays.clone(ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(3 - n)).getOctets());
        this.nonce = Arrays.clone(ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(4 - n)).getOctets());
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(new ASN1Integer(this.version));
        aSN1EncodableVector.add(new DEROctetString(this.s));
        if (this.publicKey != null) {
            aSN1EncodableVector.add(new KyberPublicKey(this.publicKey.getT(), this.publicKey.getRho()));
        }
        aSN1EncodableVector.add(new DEROctetString(this.hpk));
        aSN1EncodableVector.add(new DEROctetString(this.nonce));
        return new DERSequence(aSN1EncodableVector);
    }

    public static KyberPrivateKey getInstance(Object object) {
        if (object instanceof KyberPrivateKey) {
            return (KyberPrivateKey)object;
        }
        if (object != null) {
            return new KyberPrivateKey(ASN1Sequence.getInstance(object));
        }
        return null;
    }
}

