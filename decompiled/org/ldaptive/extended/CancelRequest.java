/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.extended;

import org.ldaptive.asn1.ConstructedDEREncoder;
import org.ldaptive.asn1.IntegerType;
import org.ldaptive.asn1.UniversalDERTag;
import org.ldaptive.extended.ExtendedRequest;

public class CancelRequest
extends ExtendedRequest {
    public static final String OID = "1.3.6.1.1.8";

    public CancelRequest(int id) {
        super(OID);
        ConstructedDEREncoder se = new ConstructedDEREncoder(UniversalDERTag.SEQ, new IntegerType(id));
        this.setRequestValue(se.encode());
    }
}

