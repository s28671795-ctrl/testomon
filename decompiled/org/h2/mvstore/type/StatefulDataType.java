/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore.type;

import java.nio.ByteBuffer;
import org.h2.mvstore.WriteBuffer;
import org.h2.mvstore.type.DataType;
import org.h2.mvstore.type.MetaType;

public interface StatefulDataType<D> {
    public void save(WriteBuffer var1, MetaType<D> var2);

    public Factory<D> getFactory();

    public static interface Factory<D> {
        public DataType<?> create(ByteBuffer var1, MetaType<D> var2, D var3);
    }
}

