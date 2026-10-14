/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.oer.its;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;

public class Uint16
extends ASN1Object {
    private final int value;

    public Uint16(int n) {
        this.value = this.verify(n);
    }

    public Uint16(BigInteger bigInteger) {
        this.value = bigInteger.intValue();
    }

    public static Uint16 getInstance(Object object) {
        if (object instanceof Uint16) {
            return (Uint16)object;
        }
        return new Uint16(ASN1Integer.getInstance(object).getValue());
    }

    protected int verify(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Uint16 must be >= 0");
        }
        if (n > 65535) {
            throw new IllegalArgumentException("Uint16 must be <= 0xFFFF");
        }
        return n;
    }

    public ASN1Primitive toASN1Primitive() {
        return new ASN1Integer(this.value);
    }
}

