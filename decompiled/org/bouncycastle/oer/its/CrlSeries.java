/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.oer.its;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.oer.its.Uint16;

public class CrlSeries
extends Uint16 {
    public CrlSeries(int n) {
        super(n);
    }

    public CrlSeries(BigInteger bigInteger) {
        super(bigInteger);
    }

    public static CrlSeries getInstance(Object object) {
        if (object instanceof CrlSeries) {
            return (CrlSeries)object;
        }
        return new CrlSeries(ASN1Integer.getInstance(object).getValue());
    }
}

