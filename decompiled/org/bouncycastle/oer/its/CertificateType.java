/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.oer.its;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1Enumerated;

public class CertificateType
extends ASN1Enumerated {
    public static final CertificateType Explicit = new CertificateType(0);
    public static final CertificateType Implicit = new CertificateType(1);

    protected CertificateType(int n) {
        super(n);
    }

    public static CertificateType getInstance(Object object) {
        if (object instanceof CertificateType) {
            return (CertificateType)object;
        }
        BigInteger bigInteger = ASN1Enumerated.getInstance(object).getValue();
        switch (bigInteger.intValue()) {
            case 0: {
                return Explicit;
            }
            case 1: {
                return Implicit;
            }
        }
        throw new IllegalArgumentException("unaccounted enum value " + bigInteger);
    }
}

