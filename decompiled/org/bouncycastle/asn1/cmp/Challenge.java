/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.asn1.cmp;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.GeneralName;

public class Challenge
extends ASN1Object {
    private final AlgorithmIdentifier owf;
    private final ASN1OctetString witness;
    private final ASN1OctetString challenge;

    private Challenge(ASN1Sequence aSN1Sequence) {
        int n = 0;
        this.owf = aSN1Sequence.size() == 3 ? AlgorithmIdentifier.getInstance(aSN1Sequence.getObjectAt(n++)) : null;
        this.witness = ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(n++));
        this.challenge = ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(n));
    }

    public Challenge(byte[] byArray, byte[] byArray2) {
        this(null, byArray, byArray2);
    }

    public Challenge(AlgorithmIdentifier algorithmIdentifier, byte[] byArray, byte[] byArray2) {
        this.owf = algorithmIdentifier;
        this.witness = new DEROctetString(byArray);
        this.challenge = new DEROctetString(byArray2);
    }

    public static Challenge getInstance(Object object) {
        if (object instanceof Challenge) {
            return (Challenge)object;
        }
        if (object != null) {
            return new Challenge(ASN1Sequence.getInstance(object));
        }
        return null;
    }

    public AlgorithmIdentifier getOwf() {
        return this.owf;
    }

    public byte[] getWitness() {
        return this.witness.getOctets();
    }

    public byte[] getChallenge() {
        return this.challenge.getOctets();
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(3);
        this.addOptional(aSN1EncodableVector, this.owf);
        aSN1EncodableVector.add(this.witness);
        aSN1EncodableVector.add(this.challenge);
        return new DERSequence(aSN1EncodableVector);
    }

    private void addOptional(ASN1EncodableVector aSN1EncodableVector, ASN1Encodable aSN1Encodable) {
        if (aSN1Encodable != null) {
            aSN1EncodableVector.add(aSN1Encodable);
        }
    }

    public static class Rand
    extends ASN1Object {
        private final ASN1Integer _int;
        private final GeneralName sender;

        public Rand(ASN1Integer aSN1Integer, GeneralName generalName) {
            this._int = aSN1Integer;
            this.sender = generalName;
        }

        public Rand(ASN1Sequence aSN1Sequence) {
            if (aSN1Sequence.size() != 2) {
                throw new IllegalArgumentException("expected sequence size of 2");
            }
            this._int = ASN1Integer.getInstance(aSN1Sequence.getObjectAt(0));
            this.sender = GeneralName.getInstance(aSN1Sequence.getObjectAt(1));
        }

        public static Rand getInstance(Object object) {
            if (object instanceof Rand) {
                return (Rand)object;
            }
            if (object != null) {
                return new Rand(ASN1Sequence.getInstance(object));
            }
            return null;
        }

        public ASN1Integer getInt() {
            return this._int;
        }

        public GeneralName getSender() {
            return this.sender;
        }

        @Override
        public ASN1Primitive toASN1Primitive() {
            return new DERSequence(new ASN1Encodable[]{this._int, this.sender});
        }
    }
}

