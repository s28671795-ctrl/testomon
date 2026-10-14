/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore.tx;

import java.nio.ByteBuffer;
import org.h2.mvstore.DataUtils;
import org.h2.mvstore.MVMap;
import org.h2.mvstore.WriteBuffer;
import org.h2.mvstore.tx.TransactionStore;
import org.h2.mvstore.type.BasicDataType;
import org.h2.value.VersionedValue;

final class Record<K, V> {
    static final Record<?, ?> COMMIT_MARKER = new Record(-1, null, null);
    final int mapId;
    final K key;
    final VersionedValue<V> oldValue;

    Record(int n, K k, VersionedValue<V> versionedValue) {
        this.mapId = n;
        this.key = k;
        this.oldValue = versionedValue;
    }

    public String toString() {
        return "mapId=" + this.mapId + ", key=" + this.key + ", value=" + this.oldValue;
    }

    static final class Type<K, V>
    extends BasicDataType<Record<K, V>> {
        private final TransactionStore transactionStore;

        Type(TransactionStore transactionStore) {
            this.transactionStore = transactionStore;
        }

        @Override
        public int getMemory(Record<K, V> record) {
            int n = 52;
            if (record.mapId >= 0) {
                MVMap mVMap = this.transactionStore.getMap(record.mapId);
                n += mVMap.getKeyType().getMemory(record.key) + mVMap.getValueType().getMemory(record.oldValue);
            }
            return n;
        }

        @Override
        public int compare(Record<K, V> record, Record<K, V> record2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void write(WriteBuffer writeBuffer, Record<K, V> record) {
            writeBuffer.putVarInt(record.mapId);
            if (record.mapId >= 0) {
                MVMap mVMap = this.transactionStore.getMap(record.mapId);
                mVMap.getKeyType().write(writeBuffer, record.key);
                VersionedValue versionedValue = record.oldValue;
                if (versionedValue == null) {
                    writeBuffer.put((byte)0);
                } else {
                    writeBuffer.put((byte)1);
                    mVMap.getValueType().write(writeBuffer, versionedValue);
                }
            }
        }

        @Override
        public Record<K, V> read(ByteBuffer byteBuffer) {
            int n = DataUtils.readVarInt(byteBuffer);
            if (n < 0) {
                return COMMIT_MARKER;
            }
            MVMap mVMap = this.transactionStore.getMap(n);
            Object k = mVMap.getKeyType().read(byteBuffer);
            VersionedValue versionedValue = null;
            if (byteBuffer.get() == 1) {
                versionedValue = mVMap.getValueType().read(byteBuffer);
            }
            return new Record(n, k, versionedValue);
        }

        public Record<K, V>[] createStorage(int n) {
            return new Record[n];
        }
    }
}

