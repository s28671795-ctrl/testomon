/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore.type;

import java.nio.ByteBuffer;
import org.h2.mvstore.DataUtils;
import org.h2.mvstore.WriteBuffer;
import org.h2.mvstore.type.BasicDataType;

public final class ByteArrayDataType
extends BasicDataType<byte[]> {
    public static final ByteArrayDataType INSTANCE = new ByteArrayDataType();

    private ByteArrayDataType() {
    }

    @Override
    public int getMemory(byte[] byArray) {
        return byArray.length;
    }

    @Override
    public void write(WriteBuffer writeBuffer, byte[] byArray) {
        writeBuffer.putVarInt(byArray.length);
        writeBuffer.put(byArray);
    }

    @Override
    public byte[] read(ByteBuffer byteBuffer) {
        int n = DataUtils.readVarInt(byteBuffer);
        byte[] byArray = new byte[n];
        byteBuffer.get(byArray);
        return byArray;
    }

    public byte[][] createStorage(int n) {
        return new byte[n][];
    }
}

