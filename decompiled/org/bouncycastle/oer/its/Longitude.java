/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.oer.its;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.oer.its.OneEightyDegreeInt;

public class Longitude
extends OneEightyDegreeInt {
    public Longitude(long l) {
        super(l);
    }

    public Longitude(BigInteger bigInteger) {
        super(bigInteger);
    }

    public Longitude(byte[] byArray) {
        super(byArray);
    }

    public static Longitude getInstance(Object object) {
        if (object instanceof Longitude) {
            return (Longitude)object;
        }
        if (object instanceof OneEightyDegreeInt) {
            return new Longitude(((OneEightyDegreeInt)object).getValue());
        }
        return new Longitude(ASN1Integer.getInstance(object).getValue());
    }
}

