/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore.type;

import java.nio.ByteBuffer;
import org.h2.mvstore.DataUtils;
import org.h2.mvstore.WriteBuffer;
import org.h2.mvstore.type.BasicDataType;

public class StringDataType
extends BasicDataType<String> {
    public static final StringDataType INSTANCE = new StringDataType();
    private static final String[] EMPTY_STRING_ARR = new String[0];

    public String[] createStorage(int n) {
        return n == 0 ? EMPTY_STRING_ARR : new String[n];
    }

    @Override
    public int compare(String string, String string2) {
        return string.compareTo(string2);
    }

    @Override
    public int binarySearch(String string, Object object, int n, int n2) {
        String[] stringArray = (String[])this.cast(object);
        int n3 = 0;
        int n4 = n - 1;
        int n5 = n2 - 1;
        if (n5 < 0 || n5 > n4) {
            n5 = n4 >>> 1;
        }
        while (n3 <= n4) {
            int n6 = string.compareTo(stringArray[n5]);
            if (n6 > 0) {
                n3 = n5 + 1;
            } else if (n6 < 0) {
                n4 = n5 - 1;
            } else {
                return n5;
            }
            n5 = n3 + n4 >>> 1;
        }
        return -(n3 + 1);
    }

    @Override
    public int getMemory(String string) {
        return 24 + 2 * string.length();
    }

    @Override
    public String read(ByteBuffer byteBuffer) {
        return DataUtils.readString(byteBuffer);
    }

    @Override
    public void write(WriteBuffer writeBuffer, String string) {
        int n = string.length();
        writeBuffer.putVarInt(n).putStringData(string, n);
    }
}

