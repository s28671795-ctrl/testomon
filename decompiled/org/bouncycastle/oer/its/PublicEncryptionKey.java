/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.oer.its.BasePublicEncryptionKey;
import org.bouncycastle.oer.its.SymmAlgorithm;
import org.bouncycastle.oer.its.Utils;

public class PublicEncryptionKey
extends ASN1Object {
    private final SymmAlgorithm supportedSymmAlg;
    private final BasePublicEncryptionKey basePublicEncryptionKey;

    public PublicEncryptionKey(SymmAlgorithm symmAlgorithm2, BasePublicEncryptionKey basePublicEncryptionKey) {
        this.supportedSymmAlg = symmAlgorithm2;
        this.basePublicEncryptionKey = basePublicEncryptionKey;
    }

    public static PublicEncryptionKey getInstance(Object object) {
        if (object instanceof PublicEncryptionKey) {
            return (PublicEncryptionKey)object;
        }
        ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(object);
        return new PublicEncryptionKey(SymmAlgorithm.getInstance(aSN1Sequence.getObjectAt(0)), BasePublicEncryptionKey.getInstance(aSN1Sequence.getObjectAt(1)));
    }

    public SymmAlgorithm getSupportedSymmAlg() {
        return this.supportedSymmAlg;
    }

    public BasePublicEncryptionKey getBasePublicEncryptionKey() {
        return this.basePublicEncryptionKey;
    }

    public ASN1Primitive toASN1Primitive() {
        return Utils.toSequence(this.supportedSymmAlg, this.basePublicEncryptionKey);
    }
}

