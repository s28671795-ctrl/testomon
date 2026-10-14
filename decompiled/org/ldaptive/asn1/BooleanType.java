/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.asn1;

import org.ldaptive.asn1.AbstractDERType;
import org.ldaptive.asn1.DERBuffer;
import org.ldaptive.asn1.DEREncoder;
import org.ldaptive.asn1.DERTag;
import org.ldaptive.asn1.UniversalDERTag;

public class BooleanType
extends AbstractDERType
implements DEREncoder {
    private static final byte TRUE_BYTE = -1;
    private static final byte FALSE_BYTE = 0;
    private final byte[] derItem;

    public BooleanType(boolean item) {
        super(UniversalDERTag.BOOL);
        this.derItem = BooleanType.toBytes(item);
    }

    public BooleanType(DERTag tag, boolean item) {
        super(tag);
        if (tag.isConstructed()) {
            throw new IllegalArgumentException("DER tag must not be constructed");
        }
        this.derItem = BooleanType.toBytes(item);
    }

    @Override
    public byte[] encode() {
        return this.encode(new byte[][]{this.derItem});
    }

    public static boolean decode(DERBuffer encoded) {
        byte[] bytes = encoded.getRemainingBytes();
        if (bytes.length > 1) {
            throw new IllegalArgumentException("Boolean cannot be longer than 1 byte");
        }
        if (bytes[0] == -1) {
            return true;
        }
        if (bytes[0] == 0) {
            return false;
        }
        throw new IllegalArgumentException("Invalid boolean value: " + bytes[0]);
    }

    public static byte[] toBytes(boolean b) {
        return new byte[]{b ? (byte)-1 : 0};
    }
}

