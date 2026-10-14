/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore.type;

import java.nio.ByteBuffer;
import org.h2.mvstore.DataUtils;
import org.h2.mvstore.WriteBuffer;
import org.h2.mvstore.type.DataType;

public abstract class BasicDataType<T>
implements DataType<T> {
    @Override
    public abstract int getMemory(T var1);

    @Override
    public abstract void write(WriteBuffer var1, T var2);

    @Override
    public abstract T read(ByteBuffer var1);

    @Override
    public int compare(T t2, T t3) {
        throw DataUtils.newUnsupportedOperationException("Can not compare");
    }

    @Override
    public boolean isMemoryEstimationAllowed() {
        return true;
    }

    @Override
    public int binarySearch(T t2, Object object, int n, int n2) {
        T[] TArray = this.cast(object);
        int n3 = 0;
        int n4 = n - 1;
        int n5 = n2 - 1;
        if (n5 < 0 || n5 > n4) {
            n5 = n4 >>> 1;
        }
        while (n3 <= n4) {
            int n6 = this.compare(t2, TArray[n5]);
            if (n6 > 0) {
                n3 = n5 + 1;
            } else if (n6 < 0) {
                n4 = n5 - 1;
            } else {
                return n5;
            }
            n5 = n3 + n4 >>> 1;
        }
        return ~n3;
    }

    @Override
    public void write(WriteBuffer writeBuffer, Object object, int n) {
        for (int i = 0; i < n; ++i) {
            this.write(writeBuffer, this.cast(object)[i]);
        }
    }

    @Override
    public void read(ByteBuffer byteBuffer, Object object, int n) {
        for (int i = 0; i < n; ++i) {
            this.cast((Object)object)[i] = this.read(byteBuffer);
        }
    }

    public int hashCode() {
        return this.getClass().getName().hashCode();
    }

    @Override
    public boolean equals(Object object) {
        return object != null && this.getClass().equals(object.getClass());
    }

    protected final T[] cast(Object object) {
        return (Object[])object;
    }
}

