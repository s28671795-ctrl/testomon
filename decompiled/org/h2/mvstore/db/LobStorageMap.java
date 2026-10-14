/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore.db;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicLong;
import org.h2.engine.Database;
import org.h2.message.DbException;
import org.h2.mvstore.DataUtils;
import org.h2.mvstore.MVMap;
import org.h2.mvstore.MVStore;
import org.h2.mvstore.MVStoreException;
import org.h2.mvstore.StreamStore;
import org.h2.mvstore.WriteBuffer;
import org.h2.mvstore.db.NullValueDataType;
import org.h2.mvstore.db.Store;
import org.h2.mvstore.tx.TransactionStore;
import org.h2.mvstore.type.BasicDataType;
import org.h2.mvstore.type.ByteArrayDataType;
import org.h2.mvstore.type.LongDataType;
import org.h2.store.CountingReaderInputStream;
import org.h2.store.LobStorageInterface;
import org.h2.store.RangeInputStream;
import org.h2.util.IOUtils;
import org.h2.util.Utils;
import org.h2.value.Value;
import org.h2.value.ValueBlob;
import org.h2.value.ValueClob;
import org.h2.value.ValueLob;
import org.h2.value.ValueNull;
import org.h2.value.lob.LobData;
import org.h2.value.lob.LobDataDatabase;

public final class LobStorageMap
implements LobStorageInterface {
    private static final boolean TRACE = false;
    private final Database database;
    final MVStore mvStore;
    private final AtomicLong nextLobId = new AtomicLong(0L);
    private final ThreadPoolExecutor cleanupExecutor;
    private final MVMap<Long, BlobMeta> lobMap;
    private final MVMap<Long, byte[]> tempLobMap;
    private final MVMap<BlobReference, Value> refMap;
    private final StreamStore streamStore;
    private final Queue<LobRemovalInfo> pendingLobRemovals = new ConcurrentLinkedQueue<LobRemovalInfo>();

    public static MVMap<Long, BlobMeta> openLobMap(TransactionStore transactionStore) {
        return transactionStore.openMap("lobMap", LongDataType.INSTANCE, BlobMeta.Type.INSTANCE);
    }

    public static MVMap<Long, byte[]> openLobDataMap(TransactionStore transactionStore) {
        return transactionStore.openMap("lobData", LongDataType.INSTANCE, ByteArrayDataType.INSTANCE);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public LobStorageMap(Database database) {
        this.database = database;
        Store store = database.getStore();
        TransactionStore transactionStore = store.getTransactionStore();
        this.mvStore = store.getMvStore();
        if (this.mvStore.isVersioningRequired()) {
            this.cleanupExecutor = Utils.createSingleThreadExecutor("H2-lob-cleaner", new SynchronousQueue<Runnable>());
            this.mvStore.setOldestVersionTracker(l -> {
                if (this.needCleanup()) {
                    try {
                        this.cleanupExecutor.execute(() -> {
                            try {
                                this.cleanup(l);
                            }
                            catch (MVStoreException mVStoreException) {
                                this.mvStore.panic(mVStoreException);
                            }
                        });
                    }
                    catch (RejectedExecutionException rejectedExecutionException) {
                        // empty catch block
                    }
                }
            });
        } else {
            this.cleanupExecutor = null;
        }
        MVStore.TxCounter txCounter = this.mvStore.registerVersionUsage();
        try {
            this.lobMap = LobStorageMap.openLobMap(transactionStore);
            this.tempLobMap = transactionStore.openMap("tempLobMap", LongDataType.INSTANCE, ByteArrayDataType.INSTANCE);
            this.refMap = transactionStore.openMap("lobRef", BlobReference.Type.INSTANCE, NullValueDataType.INSTANCE);
            MVMap<Long, byte[]> mVMap = LobStorageMap.openLobDataMap(transactionStore);
            this.streamStore = new StreamStore(mVMap);
            if (!database.isReadOnly()) {
                Long l2 = mVMap.lastKey();
                if (l2 != null) {
                    this.streamStore.setNextKey(l2 + 1L);
                }
                Long l3 = this.lobMap.lastKey();
                Long l4 = this.tempLobMap.lastKey();
                long l5 = 1L;
                if (l3 != null) {
                    l5 = l3 + 1L;
                }
                if (l4 != null) {
                    l5 = Math.max(l5, l4 + 1L);
                }
                this.nextLobId.set(l5);
            }
        }
        finally {
            this.mvStore.deregisterVersionUsage(txCounter);
        }
    }

    @Override
    public ValueBlob createBlob(InputStream inputStream2, long l) {
        MVStore.TxCounter txCounter = this.mvStore.registerVersionUsage();
        try {
            if (l != -1L && l <= (long)this.database.getMaxLengthInplaceLob()) {
                byte[] byArray = new byte[(int)l];
                int n = IOUtils.readFully(inputStream2, byArray, (int)l);
                if ((long)n > l) {
                    throw new IllegalStateException("len > blobLength, " + n + " > " + l);
                }
                if (n < byArray.length) {
                    byArray = Arrays.copyOf(byArray, n);
                }
                ValueBlob valueBlob = ValueBlob.createSmall(byArray);
                return valueBlob;
            }
            if (l != -1L) {
                inputStream2 = new RangeInputStream(inputStream2, 0L, l);
            }
            ValueBlob valueBlob = this.createBlob(inputStream2);
            return valueBlob;
        }
        catch (IllegalStateException illegalStateException) {
            throw DbException.get(90007, illegalStateException, new String[0]);
        }
        catch (IOException iOException) {
            throw DbException.convertIOException(iOException, null);
        }
        finally {
            this.mvStore.deregisterVersionUsage(txCounter);
        }
    }

    @Override
    public ValueClob createClob(Reader reader, long l) {
        MVStore.TxCounter txCounter = this.mvStore.registerVersionUsage();
        try {
            if (l != -1L && l * 3L <= (long)this.database.getMaxLengthInplaceLob()) {
                char[] cArray = new char[(int)l];
                int n = IOUtils.readFully(reader, cArray, (int)l);
                if ((long)n > l) {
                    throw new IllegalStateException("len > blobLength, " + n + " > " + l);
                }
                byte[] byArray = new String(cArray, 0, n).getBytes(StandardCharsets.UTF_8);
                if (byArray.length > this.database.getMaxLengthInplaceLob()) {
                    throw new IllegalStateException("len > maxinplace, " + byArray.length + " > " + this.database.getMaxLengthInplaceLob());
                }
                ValueClob valueClob = ValueClob.createSmall(byArray, n);
                return valueClob;
            }
            if (l < 0L) {
                l = Long.MAX_VALUE;
            }
            CountingReaderInputStream countingReaderInputStream = new CountingReaderInputStream(reader, l);
            ValueBlob valueBlob = this.createBlob(countingReaderInputStream);
            LobData lobData = valueBlob.getLobData();
            ValueClob valueClob = new ValueClob(lobData, valueBlob.octetLength(), countingReaderInputStream.getLength());
            return valueClob;
        }
        catch (IllegalStateException illegalStateException) {
            throw DbException.get(90007, illegalStateException, new String[0]);
        }
        catch (IOException iOException) {
            throw DbException.convertIOException(iOException, null);
        }
        finally {
            this.mvStore.deregisterVersionUsage(txCounter);
        }
    }

    private ValueBlob createBlob(InputStream inputStream2) throws IOException {
        byte[] byArray;
        try {
            byArray = this.streamStore.put(inputStream2);
        }
        catch (Exception exception) {
            throw DataUtils.convertToIOException(exception);
        }
        long l = this.generateLobId();
        long l2 = this.streamStore.length(byArray);
        this.tempLobMap.put(l, byArray);
        BlobReference blobReference = new BlobReference(byArray, l);
        this.refMap.put(blobReference, ValueNull.INSTANCE);
        ValueBlob valueBlob = new ValueBlob(new LobDataDatabase(this.database, -2, l), l2);
        return valueBlob;
    }

    private long generateLobId() {
        return this.nextLobId.getAndIncrement();
    }

    @Override
    public boolean isReadOnly() {
        return this.database.isReadOnly();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ValueLob copyLob(ValueLob valueLob, int n) {
        MVStore.TxCounter txCounter = this.mvStore.registerVersionUsage();
        try {
            ValueLob valueLob2;
            Object object;
            byte[] byArray;
            LobDataDatabase lobDataDatabase = (LobDataDatabase)valueLob.getLobData();
            int n2 = valueLob.getValueType();
            long l = lobDataDatabase.getLobId();
            long l2 = valueLob.octetLength();
            if (LobStorageMap.isTemporaryLob(lobDataDatabase.getTableId())) {
                byArray = this.tempLobMap.get(l);
            } else {
                BlobMeta blobMeta = this.lobMap.get(l);
                byArray = blobMeta.streamStoreId;
            }
            long l3 = this.generateLobId();
            if (LobStorageMap.isTemporaryLob(n)) {
                this.tempLobMap.put(l3, byArray);
            } else {
                object = new BlobMeta(byArray, n, n2 == 3 ? valueLob.charLength() : l2, 0L);
                this.lobMap.put(l3, (BlobMeta)object);
            }
            object = new BlobReference(byArray, l3);
            this.refMap.put((BlobReference)object, ValueNull.INSTANCE);
            LobDataDatabase lobDataDatabase2 = new LobDataDatabase(this.database, n, l3);
            ValueLob valueLob3 = valueLob2 = n2 == 7 ? new ValueBlob(lobDataDatabase2, l2) : new ValueClob(lobDataDatabase2, l2, valueLob.charLength());
            return valueLob3;
        }
        finally {
            this.mvStore.deregisterVersionUsage(txCounter);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public InputStream getInputStream(long l, long l2) throws IOException {
        MVStore.TxCounter txCounter = this.mvStore.registerVersionUsage();
        try {
            Object object;
            byte[] byArray = this.tempLobMap.get(l);
            if (byArray == null) {
                object = this.lobMap.get(l);
                byArray = ((BlobMeta)object).streamStoreId;
            }
            if (byArray == null) {
                throw DbException.get(90039, "" + l);
            }
            object = this.streamStore.get(byArray);
            LobInputStream lobInputStream = new LobInputStream((InputStream)object);
            return lobInputStream;
        }
        finally {
            this.mvStore.deregisterVersionUsage(txCounter);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public InputStream getInputStream(long l, int n, long l2) throws IOException {
        MVStore.TxCounter txCounter = this.mvStore.registerVersionUsage();
        try {
            Object object;
            byte[] byArray;
            if (LobStorageMap.isTemporaryLob(n)) {
                byArray = this.tempLobMap.get(l);
            } else {
                object = this.lobMap.get(l);
                byArray = ((BlobMeta)object).streamStoreId;
            }
            if (byArray == null) {
                throw DbException.get(90039, "" + l);
            }
            object = this.streamStore.get(byArray);
            LobInputStream lobInputStream = new LobInputStream((InputStream)object);
            return lobInputStream;
        }
        finally {
            this.mvStore.deregisterVersionUsage(txCounter);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void removeAllForTable(int n) {
        if (this.mvStore.isClosed()) {
            return;
        }
        MVStore.TxCounter txCounter = this.mvStore.registerVersionUsage();
        try {
            if (LobStorageMap.isTemporaryLob(n)) {
                Iterator<Long> iterator2 = this.tempLobMap.keyIterator(0L);
                while (iterator2.hasNext()) {
                    long l = iterator2.next();
                    this.doRemoveLob(n, l);
                }
                this.tempLobMap.clear();
            } else {
                ArrayList<Long> arrayList = new ArrayList<Long>();
                for (Map.Entry<Long, BlobMeta> entry : this.lobMap.entrySet()) {
                    BlobMeta blobMeta = entry.getValue();
                    if (blobMeta.tableId != n) continue;
                    arrayList.add(entry.getKey());
                }
                Iterator<Map.Entry<Long, BlobMeta>> iterator3 = arrayList.iterator();
                while (iterator3.hasNext()) {
                    long l = (Long)((Object)iterator3.next());
                    this.doRemoveLob(n, l);
                }
            }
        }
        finally {
            this.mvStore.deregisterVersionUsage(txCounter);
        }
    }

    @Override
    public void removeLob(ValueLob valueLob) {
        LobDataDatabase lobDataDatabase = (LobDataDatabase)valueLob.getLobData();
        int n = lobDataDatabase.getTableId();
        long l = lobDataDatabase.getLobId();
        this.requestLobRemoval(n, l);
    }

    private void requestLobRemoval(int n, long l) {
        this.pendingLobRemovals.offer(new LobRemovalInfo(this.mvStore.getCurrentVersion(), l, n));
    }

    private boolean needCleanup() {
        return !this.pendingLobRemovals.isEmpty();
    }

    @Override
    public void close() {
        this.mvStore.setOldestVersionTracker(null);
        Utils.shutdownExecutor(this.cleanupExecutor);
        if (!this.mvStore.isClosed() && this.mvStore.isVersioningRequired()) {
            this.removeAllForTable(-1);
            this.cleanup(this.mvStore.getCurrentVersion() + 1L);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void cleanup(long l) {
        MVStore.TxCounter txCounter = this.mvStore.registerVersionUsage();
        try {
            LobRemovalInfo lobRemovalInfo;
            while ((lobRemovalInfo = this.pendingLobRemovals.poll()) != null && lobRemovalInfo.version < l) {
                this.doRemoveLob(lobRemovalInfo.mapId, lobRemovalInfo.lobId);
            }
            if (lobRemovalInfo != null) {
                this.pendingLobRemovals.offer(lobRemovalInfo);
            }
        }
        finally {
            this.mvStore.decrementVersionUsageCounter(txCounter);
        }
    }

    private void doRemoveLob(int n, long l) {
        byte[] byArray;
        Object object;
        byte[] byArray2;
        if (LobStorageMap.isTemporaryLob(n)) {
            byArray2 = this.tempLobMap.remove(l);
            if (byArray2 == null) {
                return;
            }
        } else {
            object = this.lobMap.remove(l);
            if (object == null) {
                return;
            }
            byArray2 = ((BlobMeta)object).streamStoreId;
        }
        object = new BlobReference(byArray2, l);
        Value value = this.refMap.remove(object);
        assert (value != null);
        object = new BlobReference(byArray2, 0L);
        BlobReference blobReference = this.refMap.ceilingKey((BlobReference)object);
        boolean bl = false;
        if (blobReference != null && Arrays.equals(byArray2, byArray = blobReference.streamStoreId)) {
            bl = true;
        }
        if (!bl) {
            this.streamStore.remove(byArray2);
        }
    }

    private static boolean isTemporaryLob(int n) {
        return n == -1 || n == -2 || n == -3;
    }

    private static void trace(String string) {
        System.out.println("[" + Thread.currentThread().getName() + "] LOB " + string);
    }

    private static final class LobRemovalInfo {
        final long version;
        final long lobId;
        final int mapId;

        LobRemovalInfo(long l, long l2, int n) {
            this.version = l;
            this.lobId = l2;
            this.mapId = n;
        }
    }

    public static final class BlobMeta {
        public final byte[] streamStoreId;
        final int tableId;
        final long byteCount;
        final long hash;

        public BlobMeta(byte[] byArray, int n, long l, long l2) {
            this.streamStoreId = byArray;
            this.tableId = n;
            this.byteCount = l;
            this.hash = l2;
        }

        public static final class Type
        extends BasicDataType<BlobMeta> {
            public static final Type INSTANCE = new Type();

            private Type() {
            }

            @Override
            public int getMemory(BlobMeta blobMeta) {
                return blobMeta.streamStoreId.length + 20;
            }

            @Override
            public void write(WriteBuffer writeBuffer, BlobMeta blobMeta) {
                writeBuffer.putVarInt(blobMeta.streamStoreId.length);
                writeBuffer.put(blobMeta.streamStoreId);
                writeBuffer.putVarInt(blobMeta.tableId);
                writeBuffer.putVarLong(blobMeta.byteCount);
                writeBuffer.putLong(blobMeta.hash);
            }

            @Override
            public BlobMeta read(ByteBuffer byteBuffer) {
                int n = DataUtils.readVarInt(byteBuffer);
                byte[] byArray = new byte[n];
                byteBuffer.get(byArray);
                int n2 = DataUtils.readVarInt(byteBuffer);
                long l = DataUtils.readVarLong(byteBuffer);
                long l2 = byteBuffer.getLong();
                return new BlobMeta(byArray, n2, l, l2);
            }

            public BlobMeta[] createStorage(int n) {
                return new BlobMeta[n];
            }
        }
    }

    public static final class BlobReference
    implements Comparable<BlobReference> {
        public final byte[] streamStoreId;
        public final long lobId;

        public BlobReference(byte[] byArray, long l) {
            this.streamStoreId = byArray;
            this.lobId = l;
        }

        @Override
        public int compareTo(BlobReference blobReference) {
            int n = Integer.compare(this.streamStoreId.length, blobReference.streamStoreId.length);
            if (n == 0) {
                for (int i = 0; n == 0 && i < this.streamStoreId.length; ++i) {
                    n = Byte.compare(this.streamStoreId[i], blobReference.streamStoreId[i]);
                }
                if (n == 0) {
                    n = Long.compare(this.lobId, blobReference.lobId);
                }
            }
            return n;
        }

        public static final class Type
        extends BasicDataType<BlobReference> {
            public static final Type INSTANCE = new Type();

            private Type() {
            }

            @Override
            public int getMemory(BlobReference blobReference) {
                return blobReference.streamStoreId.length + 8;
            }

            @Override
            public int compare(BlobReference blobReference, BlobReference blobReference2) {
                return blobReference == blobReference2 ? 0 : (blobReference == null ? 1 : (blobReference2 == null ? -1 : blobReference.compareTo(blobReference2)));
            }

            @Override
            public void write(WriteBuffer writeBuffer, BlobReference blobReference) {
                writeBuffer.putVarInt(blobReference.streamStoreId.length);
                writeBuffer.put(blobReference.streamStoreId);
                writeBuffer.putVarLong(blobReference.lobId);
            }

            @Override
            public BlobReference read(ByteBuffer byteBuffer) {
                int n = DataUtils.readVarInt(byteBuffer);
                byte[] byArray = new byte[n];
                byteBuffer.get(byArray);
                long l = DataUtils.readVarLong(byteBuffer);
                return new BlobReference(byArray, l);
            }

            public BlobReference[] createStorage(int n) {
                return new BlobReference[n];
            }
        }
    }

    private final class LobInputStream
    extends FilterInputStream {
        public LobInputStream(InputStream inputStream2) {
            super(inputStream2);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int read(byte[] byArray, int n, int n2) throws IOException {
            MVStore.TxCounter txCounter = LobStorageMap.this.mvStore.registerVersionUsage();
            try {
                int n3 = super.read(byArray, n, n2);
                return n3;
            }
            finally {
                LobStorageMap.this.mvStore.deregisterVersionUsage(txCounter);
            }
        }

        @Override
        public int read() throws IOException {
            MVStore.TxCounter txCounter = LobStorageMap.this.mvStore.registerVersionUsage();
            try {
                int n = super.read();
                return n;
            }
            finally {
                LobStorageMap.this.mvStore.deregisterVersionUsage(txCounter);
            }
        }
    }
}

