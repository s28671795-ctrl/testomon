/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore.type;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import org.h2.mvstore.DataUtils;
import org.h2.mvstore.WriteBuffer;
import org.h2.mvstore.type.BasicDataType;
import org.h2.mvstore.type.DataType;
import org.h2.mvstore.type.StatefulDataType;

public final class MetaType<D>
extends BasicDataType<DataType<?>> {
    private final D database;
    private final Thread.UncaughtExceptionHandler exceptionHandler;
    private final Map<String, Object> cache = new HashMap<String, Object>();

    public MetaType(D d, Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.database = d;
        this.exceptionHandler = uncaughtExceptionHandler;
    }

    @Override
    public int compare(DataType<?> dataType, DataType<?> dataType2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getMemory(DataType<?> dataType) {
        return 24;
    }

    @Override
    public void write(WriteBuffer writeBuffer, DataType<?> dataType) {
        Object object;
        Class<?> clazz = dataType.getClass();
        StatefulDataType statefulDataType = null;
        if (dataType instanceof StatefulDataType && (object = (statefulDataType = (StatefulDataType)((Object)dataType)).getFactory()) != null) {
            clazz = object.getClass();
        }
        object = clazz.getName();
        int n = ((String)object).length();
        writeBuffer.putVarInt(n).putStringData((String)object, n);
        if (statefulDataType != null) {
            statefulDataType.save(writeBuffer, this);
        }
    }

    @Override
    public DataType<?> read(ByteBuffer byteBuffer) {
        int n = DataUtils.readVarInt(byteBuffer);
        String string = DataUtils.readString(byteBuffer, n);
        try {
            Object object;
            Object object2 = this.cache.get(string);
            if (object2 != null) {
                if (object2 instanceof StatefulDataType.Factory) {
                    return ((StatefulDataType.Factory)object2).create(byteBuffer, this, this.database);
                }
                return (DataType)object2;
            }
            Class<?> clazz = Class.forName(string);
            boolean bl = false;
            try {
                object = clazz.getDeclaredField("INSTANCE").get(null);
                bl = true;
            }
            catch (NullPointerException | ReflectiveOperationException exception) {
                object = clazz.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            }
            if (object instanceof StatefulDataType.Factory) {
                StatefulDataType.Factory factory2 = (StatefulDataType.Factory)object;
                this.cache.put(string, factory2);
                return factory2.create(byteBuffer, this, this.database);
            }
            if (bl) {
                this.cache.put(string, object);
            }
            return (DataType)object;
        }
        catch (IllegalArgumentException | ReflectiveOperationException | SecurityException exception) {
            if (this.exceptionHandler != null) {
                this.exceptionHandler.uncaughtException(Thread.currentThread(), exception);
            }
            throw new RuntimeException(exception);
        }
    }

    public DataType<?>[] createStorage(int n) {
        return new DataType[n];
    }
}

