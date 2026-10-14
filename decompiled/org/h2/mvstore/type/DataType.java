/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore.type;

import java.nio.ByteBuffer;
import java.util.Comparator;
import org.h2.mvstore.WriteBuffer;

public interface DataType<T>
extends Comparator<T> {
    @Override
    public int compare(T var1, T var2);

    public int binarySearch(T var1, Object var2, int var3, int var4);

    public int getMemory(T var1);

    public boolean isMemoryEstimationAllowed();

    public void write(WriteBuffer var1, T var2);

    public void write(WriteBuffer var1, Object var2, int var3);

    public T read(ByteBuffer var1);

    public void read(ByteBuffer var1, Object var2, int var3);

    public T[] createStorage(int var1);
}

