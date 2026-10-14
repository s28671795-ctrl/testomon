/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.asn1;

import org.ldaptive.asn1.AbstractDERType;
import org.ldaptive.asn1.DEREncoder;
import org.ldaptive.asn1.DERTag;

public class NullType
extends AbstractDERType
implements DEREncoder {
    public NullType(DERTag tag) {
        super(tag);
    }

    @Override
    public byte[] encode() {
        return this.encode(new byte[][]{(byte[])null});
    }
}

