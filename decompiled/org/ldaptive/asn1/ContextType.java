/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.asn1;

import java.math.BigInteger;
import org.ldaptive.asn1.AbstractDERType;
import org.ldaptive.asn1.BooleanType;
import org.ldaptive.asn1.ContextDERTag;
import org.ldaptive.asn1.DEREncoder;
import org.ldaptive.asn1.IntegerType;
import org.ldaptive.asn1.OctetStringType;

public class ContextType
extends AbstractDERType
implements DEREncoder {
    private final byte[] derItem;

    public ContextType(int index, byte[] item) {
        super(new ContextDERTag(index, false));
        this.derItem = item;
    }

    public ContextType(int index, String item) {
        this(index, OctetStringType.toBytes(item));
    }

    public ContextType(int index, boolean item) {
        this(index, BooleanType.toBytes(item));
    }

    public ContextType(int index, BigInteger item) {
        this(index, IntegerType.toBytes(item));
    }

    @Override
    public byte[] encode() {
        return this.encode(new byte[][]{this.derItem});
    }
}

