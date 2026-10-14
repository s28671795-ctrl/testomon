/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore.tx;

import java.nio.ByteBuffer;
import org.h2.mvstore.DataUtils;
import org.h2.mvstore.WriteBuffer;
import org.h2.mvstore.tx.VersionedValueCommitted;
import org.h2.mvstore.tx.VersionedValueUncommitted;
import org.h2.mvstore.type.BasicDataType;
import org.h2.mvstore.type.DataType;
import org.h2.mvstore.type.MetaType;
import org.h2.mvstore.type.StatefulDataType;
import org.h2.value.VersionedValue;

public class VersionedValueType<T, D>
extends BasicDataType<VersionedValue<T>>
implements StatefulDataType<D> {
    private final DataType<T> valueType;
    private final Factory<D> factory = new Factory();

    public VersionedValueType(DataType<T> dataType) {
        this.valueType = dataType;
    }

    public VersionedValue<T>[] createStorage(int n) {
        return new VersionedValue[n];
    }

    @Override
    public int getMemory(VersionedValue<T> versionedValue) {
        if (versionedValue == null) {
            return 0;
        }
        int n = 48 + this.getValMemory(versionedValue.getCurrentValue());
        if (versionedValue.getOperationId() != 0L) {
            n += this.getValMemory(versionedValue.getCommittedValue());
        }
        return n;
    }

    private int getValMemory(T t2) {
        return t2 == null ? 0 : this.valueType.getMemory(t2);
    }

    @Override
    public void read(ByteBuffer byteBuffer, Object object, int n) {
        if (byteBuffer.get() == 0) {
            for (int i = 0; i < n; ++i) {
                ((VersionedValue[])this.cast((Object)object))[i] = VersionedValueCommitted.getInstance(this.valueType.read(byteBuffer));
            }
        } else {
            for (int i = 0; i < n; ++i) {
                ((VersionedValue[])this.cast((Object)object))[i] = this.read(byteBuffer);
            }
        }
    }

    @Override
    public VersionedValue<T> read(ByteBuffer byteBuffer) {
        long l = DataUtils.readVarLong(byteBuffer);
        if (l == 0L) {
            return VersionedValueCommitted.getInstance(this.valueType.read(byteBuffer));
        }
        byte by = byteBuffer.get();
        Object x = (by & 1) != 0 ? (Object)this.valueType.read(byteBuffer) : null;
        Object x2 = (by & 2) != 0 ? (Object)this.valueType.read(byteBuffer) : null;
        return VersionedValueUncommitted.getInstance(l, x, x2);
    }

    @Override
    public void write(WriteBuffer writeBuffer, Object object, int n) {
        VersionedValue versionedValue;
        int n2;
        boolean bl = true;
        for (n2 = 0; n2 < n; ++n2) {
            versionedValue = ((VersionedValue[])this.cast(object))[n2];
            if (versionedValue.getOperationId() == 0L && versionedValue.getCurrentValue() != null) continue;
            bl = false;
        }
        if (bl) {
            writeBuffer.put((byte)0);
            for (n2 = 0; n2 < n; ++n2) {
                versionedValue = ((VersionedValue[])this.cast(object))[n2];
                this.valueType.write(writeBuffer, versionedValue.getCurrentValue());
            }
        } else {
            writeBuffer.put((byte)1);
            for (n2 = 0; n2 < n; ++n2) {
                this.write(writeBuffer, ((VersionedValue[])this.cast(object))[n2]);
            }
        }
    }

    @Override
    public void write(WriteBuffer writeBuffer, VersionedValue<T> versionedValue) {
        long l = versionedValue.getOperationId();
        writeBuffer.putVarLong(l);
        if (l == 0L) {
            this.valueType.write(writeBuffer, versionedValue.getCurrentValue());
        } else {
            T t2 = versionedValue.getCommittedValue();
            int n = (versionedValue.getCurrentValue() == null ? 0 : 1) | (t2 == null ? 0 : 2);
            writeBuffer.put((byte)n);
            if (versionedValue.getCurrentValue() != null) {
                this.valueType.write(writeBuffer, versionedValue.getCurrentValue());
            }
            if (t2 != null) {
                this.valueType.write(writeBuffer, t2);
            }
        }
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof VersionedValueType)) {
            return false;
        }
        VersionedValueType versionedValueType = (VersionedValueType)object;
        return this.valueType.equals(versionedValueType.valueType);
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ this.valueType.hashCode();
    }

    @Override
    public void save(WriteBuffer writeBuffer, MetaType<D> metaType) {
        metaType.write(writeBuffer, this.valueType);
    }

    @Override
    public int compare(VersionedValue<T> versionedValue, VersionedValue<T> versionedValue2) {
        return this.valueType.compare(versionedValue.getCurrentValue(), versionedValue2.getCurrentValue());
    }

    @Override
    public Factory<D> getFactory() {
        return this.factory;
    }

    public static final class Factory<D>
    implements StatefulDataType.Factory<D> {
        @Override
        public DataType<?> create(ByteBuffer byteBuffer, MetaType<D> metaType, D d) {
            Object object = metaType.read(byteBuffer);
            return new VersionedValueType(object);
        }
    }
}

