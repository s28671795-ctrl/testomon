/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.oer.its;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1Integer;

public class HeaderInfoContributorId
extends ASN1Integer {
    public HeaderInfoContributorId(long l) {
        super(l);
    }

    public HeaderInfoContributorId(BigInteger bigInteger) {
        super(bigInteger);
    }

    public HeaderInfoContributorId(byte[] byArray) {
        super(byArray);
    }

    public static HeaderInfoContributorId getInstance(Object object) {
        if (object instanceof HeaderInfoContributorId) {
            return (HeaderInfoContributorId)object;
        }
        ASN1Integer aSN1Integer = ASN1Integer.getInstance(object);
        return new HeaderInfoContributorId(aSN1Integer.getValue());
    }
}

