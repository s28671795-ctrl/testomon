/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.oer.its;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.oer.its.NinetyDegreeInt;

public class Latitude
extends NinetyDegreeInt {
    public Latitude(long l) {
        super(l);
    }

    public Latitude(BigInteger bigInteger) {
        super(bigInteger);
    }

    public Latitude(byte[] byArray) {
        super(byArray);
    }

    public static Latitude getInstance(Object object) {
        if (object instanceof Latitude) {
            return (Latitude)object;
        }
        if (object instanceof NinetyDegreeInt) {
            return new Latitude(((NinetyDegreeInt)object).getValue());
        }
        return new Latitude(ASN1Integer.getInstance(object).getValue());
    }
}

