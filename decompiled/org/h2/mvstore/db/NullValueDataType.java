/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore.db;

import java.nio.ByteBuffer;
import java.util.Arrays;
import org.h2.mvstore.WriteBuffer;
import org.h2.mvstore.type.DataType;
import org.h2.value.Value;
import org.h2.value.ValueNull;

public final class NullValueDataType
implements DataType<Value> {
    public static final NullValueDataType INSTANCE = new NullValueDataType();

    private NullValueDataType() {
    }

    @Override
    public int compare(Value value, Value value2) {
        return 0;
    }

    @Override
    public int binarySearch(Value value, Object object, int n, int n2) {
        return 0;
    }

    @Override
    public int getMemory(Value value) {
        return 0;
    }

    @Override
    public boolean isMemoryEstimationAllowed() {
        return true;
    }

    @Override
    public void write(WriteBuffer writeBuffer, Value value) {
    }

    @Override
    public void write(WriteBuffer writeBuffer, Object object, int n) {
    }

    @Override
    public Value read(ByteBuffer byteBuffer) {
        return ValueNull.INSTANCE;
    }

    @Override
    public void read(ByteBuffer byteBuffer, Object object, int n) {
        Arrays.fill((Value[])object, 0, n, ValueNull.INSTANCE);
    }

    public Value[] createStorage(int n) {
        return new Value[n];
    }
}

