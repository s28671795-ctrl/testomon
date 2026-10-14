/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore.type;

import java.nio.ByteBuffer;
import org.h2.mvstore.DataUtils;
import org.h2.mvstore.WriteBuffer;
import org.h2.mvstore.type.BasicDataType;

public class LongDataType
extends BasicDataType<Long> {
    public static final LongDataType INSTANCE = new LongDataType();
    private static final Long[] EMPTY_LONG_ARR = new Long[0];

    private LongDataType() {
    }

    @Override
    public int getMemory(Long l) {
        return 8;
    }

    @Override
    public void write(WriteBuffer writeBuffer, Long l) {
        writeBuffer.putVarLong(l);
    }

    @Override
    public Long read(ByteBuffer byteBuffer) {
        return DataUtils.readVarLong(byteBuffer);
    }

    public Long[] createStorage(int n) {
        return n == 0 ? EMPTY_LONG_ARR : new Long[n];
    }

    @Override
    public int compare(Long l, Long l2) {
        return Long.compare(l, l2);
    }

    @Override
    public int binarySearch(Long l, Object object, int n, int n2) {
        long l2 = l;
        Long[] longArray = (Long[])this.cast(object);
        int n3 = 0;
        int n4 = n - 1;
        int n5 = n2 - 1;
        if (n5 < 0 || n5 > n4) {
            n5 = n4 >>> 1;
        }
        return LongDataType.binarySearch(l2, longArray, n3, n4, n5);
    }

    private static int binarySearch(long l, Long[] longArray, int n, int n2, int n3) {
        while (n <= n2) {
            long l2 = longArray[n3];
            if (l > l2) {
                n = n3 + 1;
            } else if (l < l2) {
                n2 = n3 - 1;
            } else {
                return n3;
            }
            n3 = n + n2 >>> 1;
        }
        return -(n + 1);
    }
}

