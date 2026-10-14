/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.asn1;

import java.nio.ByteBuffer;
import java.util.UUID;
import org.ldaptive.asn1.AbstractDERType;
import org.ldaptive.asn1.DERBuffer;
import org.ldaptive.asn1.DEREncoder;
import org.ldaptive.asn1.DERTag;
import org.ldaptive.asn1.UniversalDERTag;

public class UuidType
extends AbstractDERType
implements DEREncoder {
    private static final int UUID_LENGTH = 16;
    private final byte[] derItem;

    public UuidType(UUID item) {
        super(UniversalDERTag.OCTSTR);
        this.derItem = UuidType.toBytes(item);
    }

    public UuidType(DERTag tag, UUID item) {
        super(tag);
        if (tag.isConstructed()) {
            throw new IllegalArgumentException("DER tag must not be constructed");
        }
        this.derItem = UuidType.toBytes(item);
    }

    @Override
    public byte[] encode() {
        return this.encode(new byte[][]{this.derItem});
    }

    public static UUID decode(DERBuffer encoded) {
        long mostSig = UuidType.readLong(encoded);
        long leastSig = UuidType.readLong(encoded);
        return new UUID(mostSig, leastSig);
    }

    protected static long readLong(DERBuffer buffer) {
        return (long)buffer.get() << 56 | ((long)buffer.get() & 0xFFL) << 48 | ((long)buffer.get() & 0xFFL) << 40 | ((long)buffer.get() & 0xFFL) << 32 | ((long)buffer.get() & 0xFFL) << 24 | ((long)buffer.get() & 0xFFL) << 16 | ((long)buffer.get() & 0xFFL) << 8 | (long)buffer.get() & 0xFFL;
    }

    public static byte[] toBytes(UUID uuid) {
        ByteBuffer buffer = ByteBuffer.wrap(new byte[16]);
        buffer.putLong(uuid.getMostSignificantBits());
        buffer.putLong(uuid.getLeastSignificantBits());
        return buffer.array();
    }
}

