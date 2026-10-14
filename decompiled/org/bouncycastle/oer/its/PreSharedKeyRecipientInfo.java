/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.oer.its.HashedId;

public class PreSharedKeyRecipientInfo
extends HashedId {
    public PreSharedKeyRecipientInfo(byte[] byArray) {
        super(byArray);
    }

    public static PreSharedKeyRecipientInfo getInstance(Object object) {
        if (object instanceof PreSharedKeyRecipientInfo) {
            return (PreSharedKeyRecipientInfo)object;
        }
        ASN1OctetString aSN1OctetString = ASN1OctetString.getInstance(object);
        return new PreSharedKeyRecipientInfo(aSN1OctetString.getOctets());
    }
}

