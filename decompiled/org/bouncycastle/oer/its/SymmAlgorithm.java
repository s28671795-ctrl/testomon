/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.oer.its;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1Enumerated;

public class SymmAlgorithm
extends ASN1Enumerated {
    public static SymmAlgorithm aes128Ccm = new SymmAlgorithm(0);

    public SymmAlgorithm(int n) {
        super(n);
        if (n != 0) {
            throw new IllegalArgumentException("ordinal can only be zero");
        }
    }

    public static SymmAlgorithm getInstance(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof SymmAlgorithm) {
            return (SymmAlgorithm)object;
        }
        BigInteger bigInteger = ASN1Enumerated.getInstance(object).getValue();
        switch (bigInteger.intValue()) {
            case 0: {
                return aes128Ccm;
            }
        }
        throw new IllegalArgumentException("unaccounted enum value " + bigInteger);
    }
}

