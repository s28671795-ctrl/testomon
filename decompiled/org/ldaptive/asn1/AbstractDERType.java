/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.asn1;

import java.nio.ByteBuffer;
import org.ldaptive.asn1.DERTag;

public abstract class AbstractDERType {
    private static final int SHORT_FORM_INT_LENGTH = 127;
    private final int derTag;

    public AbstractDERType(DERTag tag) {
        this.derTag = tag.getTagByte();
    }

    /*
     * WARNING - void declaration
     */
    protected byte[] encode(byte[] ... items) {
        int itemLength = 0;
        if (items != null) {
            void var5_7;
            byte[][] byArray = items;
            int n = byArray.length;
            boolean bl = false;
            while (var5_7 < n) {
                byte[] b = byArray[var5_7];
                if (b != null) {
                    itemLength += b.length;
                }
                ++var5_7;
            }
        }
        byte[] lengthBytes = itemLength <= 127 ? new byte[]{(byte)itemLength} : new byte[]{-124, (byte)(itemLength >>> 24), (byte)(itemLength >>> 16), (byte)(itemLength >>> 8), (byte)itemLength};
        ByteBuffer encodedItem = ByteBuffer.allocate(itemLength + 1 + lengthBytes.length);
        encodedItem.put((byte)this.derTag);
        for (byte b : lengthBytes) {
            encodedItem.put(b);
        }
        if (items != null) {
            for (byte[] b : items) {
                if (b == null) continue;
                encodedItem.put(b);
            }
        }
        return encodedItem.array();
    }
}

