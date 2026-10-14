/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import org.ldaptive.AbstractRequestMessage;
import org.ldaptive.asn1.ApplicationDERTag;
import org.ldaptive.asn1.DEREncoder;
import org.ldaptive.asn1.IntegerType;
import org.ldaptive.asn1.NullType;

public class UnbindRequest
extends AbstractRequestMessage {
    public static final int PROTOCOL_OP = 2;

    @Override
    protected DEREncoder[] getRequestEncoders(int id) {
        return new DEREncoder[]{new IntegerType(id), new NullType(new ApplicationDERTag(2, false))};
    }
}

