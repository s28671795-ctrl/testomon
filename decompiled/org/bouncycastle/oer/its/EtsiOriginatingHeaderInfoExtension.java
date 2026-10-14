/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.oer.its;

import java.util.Iterator;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.oer.its.HeaderInfoContributorId;
import org.bouncycastle.oer.its.Utils;

public class EtsiOriginatingHeaderInfoExtension
extends ASN1Object {
    private final HeaderInfoContributorId etsiHeaderInfoContributorId;
    private final ASN1OctetString extension;

    public EtsiOriginatingHeaderInfoExtension(HeaderInfoContributorId headerInfoContributorId, ASN1OctetString aSN1OctetString) {
        this.etsiHeaderInfoContributorId = headerInfoContributorId;
        this.extension = aSN1OctetString;
    }

    public static EtsiOriginatingHeaderInfoExtension getInstance(Object object) {
        if (object instanceof EtsiOriginatingHeaderInfoExtension) {
            return (EtsiOriginatingHeaderInfoExtension)object;
        }
        Iterator<ASN1Encodable> iterator2 = ASN1Sequence.getInstance(object).iterator();
        HeaderInfoContributorId headerInfoContributorId = HeaderInfoContributorId.getInstance(iterator2.next());
        if (iterator2.hasNext()) {
            return new EtsiOriginatingHeaderInfoExtension(headerInfoContributorId, ASN1OctetString.getInstance(iterator2.next()));
        }
        return new EtsiOriginatingHeaderInfoExtension(headerInfoContributorId, null);
    }

    public ASN1Primitive toASN1Primitive() {
        return Utils.toSequence(this.etsiHeaderInfoContributorId, this.extension);
    }

    public HeaderInfoContributorId getEtsiHeaderInfoContributorId() {
        return this.etsiHeaderInfoContributorId;
    }

    public ASN1OctetString getExtension() {
        return this.extension;
    }
}

