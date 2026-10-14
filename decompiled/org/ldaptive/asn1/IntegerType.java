/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.asn1;

import java.math.BigInteger;
import org.ldaptive.asn1.AbstractDERType;
import org.ldaptive.asn1.DERBuffer;
import org.ldaptive.asn1.DEREncoder;
import org.ldaptive.asn1.DERTag;
import org.ldaptive.asn1.UniversalDERTag;

public class IntegerType
extends AbstractDERType
implements DEREncoder {
    private final byte[] derItem;

    public IntegerType(BigInteger item) {
        super(UniversalDERTag.INT);
        this.derItem = item.toByteArray();
    }

    public IntegerType(int item) {
        super(UniversalDERTag.INT);
        this.derItem = BigInteger.valueOf(item).toByteArray();
    }

    public IntegerType(DERTag tag, BigInteger item) {
        super(tag);
        if (tag.isConstructed()) {
            throw new IllegalArgumentException("DER tag must not be constructed");
        }
        this.derItem = item.toByteArray();
    }

    public IntegerType(DERTag tag, int item) {
        super(tag);
        if (tag.isConstructed()) {
            throw new IllegalArgumentException("DER tag must not be constructed");
        }
        this.derItem = BigInteger.valueOf(item).toByteArray();
    }

    @Override
    public byte[] encode() {
        return this.encode(new byte[][]{this.derItem});
    }

    public static BigInteger decode(DERBuffer encoded) {
        return new BigInteger(encoded.getRemainingBytes());
    }

    public static BigInteger decodeUnsigned(DERBuffer encoded) {
        return new BigInteger(1, encoded.getRemainingBytes());
    }

    public static int decodeUnsignedPrimitive(DERBuffer encoded) {
        byte[] bytes = encoded.getRemainingBytes();
        if (bytes.length > 4) {
            throw new IllegalArgumentException("Buffer length must be <= 4 bytes");
        }
        int i = 0;
        for (byte b : bytes) {
            i <<= 8;
            i |= b & 0xFF;
        }
        return i;
    }

    public static byte[] toBytes(BigInteger i) {
        return i.toByteArray();
    }
}

