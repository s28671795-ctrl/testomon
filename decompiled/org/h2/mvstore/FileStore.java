/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiConsumer;
import java.util.function.IntSupplier;
import java.util.zip.ZipOutputStream;
import org.h2.mvstore.Chunk;
import org.h2.mvstore.Cursor;
import org.h2.mvstore.DataUtils;
import org.h2.mvstore.MVMap;
import org.h2.mvstore.MVStore;
import org.h2.mvstore.MVStoreException;
import org.h2.mvstore.Page;
import org.h2.mvstore.RootReference;
import org.h2.mvstore.WriteBuffer;
import org.h2.mvstore.cache.CacheLongKeyLIRS;
import org.h2.mvstore.type.StringDataType;
import org.h2.util.MathUtils;
import org.h2.util.Utils;

public abstract class FileStore<C extends Chunk<C>> {
    static final String HDR_H = "H";
    static final String HDR_BLOCK_SIZE = "blockSize";
    static final String HDR_FORMAT = "format";
    static final String HDR_CREATED = "created";
    static final String HDR_FORMAT_READ = "formatRead";
    static final String HDR_CHUNK = "chunk";
    static final String HDR_BLOCK = "block";
    static final String HDR_VERSION = "version";
    static final String HDR_CLEAN = "clean";
    static final String HDR_FLETCHER = "fletcher";
    public static final String META_ID_KEY = "meta.id";
    static final int BLOCK_SIZE = 4096;
    private static final int FORMAT_WRITE_MIN = 3;
    private static final int FORMAT_WRITE_MAX = 3;
    private static final int FORMAT_READ_MIN = 3;
    private static final int FORMAT_READ_MAX = 3;
    MVStore mvStore;
    private boolean closed;
    protected final AtomicLong readCount = new AtomicLong();
    protected final AtomicLong readBytes = new AtomicLong();
    protected final AtomicLong writeCount = new AtomicLong();
    protected final AtomicLong writeBytes = new AtomicLong();
    private String fileName;
    private int retentionTime = this.getDefaultRetentionTime();
    private final int maxPageSize;
    private long size;
    private boolean readOnly;
    private final ReentrantLock serializationLock = new ReentrantLock(true);
    private ThreadPoolExecutor serializationExecutor;
    private ThreadPoolExecutor bufferSaveExecutor;
    private final CacheLongKeyLIRS<Page<?, ?>> cache;
    private final CacheLongKeyLIRS<long[]> chunksToC;
    private final Queue<RemovedPageInfo> removedPages = new PriorityBlockingQueue<RemovedPageInfo>();
    protected volatile C lastChunk;
    private int lastChunkId;
    protected final ReentrantLock saveChunkLock = new ReentrantLock(true);
    final ConcurrentHashMap<Integer, C> chunks = new ConcurrentHashMap();
    protected final HashMap<String, Object> storeHeader = new HashMap();
    private long creationTime;
    private final Queue<WriteBuffer> writeBufferPool = new ArrayBlockingQueue<WriteBuffer>(4);
    private MVMap<String, String> layout;
    private final Deque<C> deadChunks = new ConcurrentLinkedDeque<C>();
    private final AtomicReference<BackgroundWriterThread> backgroundWriterThread = new AtomicReference();
    private final int autoCompactFillRate;
    private int autoCommitDelay;
    private long autoCompactLastFileOpCount;
    private long lastCommitTime;
    protected final boolean recoveryMode;
    public static final int PIPE_LENGTH = 3;
    private int serializationExecutorHWM;
    private int bufferSaveExecutorHWM;

    protected FileStore(Map<String, Object> map) {
        int n;
        Object object;
        this.recoveryMode = map.containsKey("recoveryMode");
        this.autoCompactFillRate = DataUtils.getConfigParam(map, "autoCompactFillRate", 90);
        CacheLongKeyLIRS.Config config = null;
        int n2 = DataUtils.getConfigParam(map, "cacheSize", 16);
        if (n2 > 0) {
            config = new CacheLongKeyLIRS.Config();
            config.maxMemory = (long)n2 * 1024L * 1024L;
            object = map.get("cacheConcurrency");
            if (object != null) {
                config.segmentCount = (Integer)object;
            }
        }
        this.cache = config == null ? null : new CacheLongKeyLIRS(config);
        object = new CacheLongKeyLIRS.Config();
        ((CacheLongKeyLIRS.Config)object).maxMemory = 0x100000L;
        this.chunksToC = new CacheLongKeyLIRS((CacheLongKeyLIRS.Config)object);
        int n3 = Integer.MAX_VALUE;
        if (this.cache != null && (n3 = 16384) > (n = (int)(this.cache.getMaxItemSize() >> 4))) {
            n3 = n;
        }
        this.maxPageSize = n3;
    }

    public abstract void open(String var1, boolean var2, char[] var3);

    public abstract FileStore<C> open(String var1, boolean var2);

    protected final void init(String string, boolean bl) {
        this.fileName = string;
        this.readOnly = bl;
    }

    public final void bind(MVStore mVStore) {
        if (this.mvStore != mVStore) {
            long l = this.layout == null ? 0L : this.layout.getRootPage().getPos();
            this.layout = new MVMap<String, String>(mVStore, 0, StringDataType.INSTANCE, StringDataType.INSTANCE);
            this.layout.setRootPos(l, mVStore.getCurrentVersion());
            this.mvStore = mVStore;
            mVStore.resetLastMapId(this.lastChunk == null ? 0 : ((Chunk)this.lastChunk).mapId);
            mVStore.setCurrentVersion(this.lastChunkVersion());
        }
    }

    public final void stop(long l) {
        if (l > 0L) {
            this.compactStore(l);
        }
        this.writeCleanShutdown();
        this.clearCaches();
    }

    public void close() {
        this.layout.close();
        this.closed = true;
        this.chunks.clear();
    }

    public final int getMetaMapId(IntSupplier intSupplier) {
        int n;
        String string = this.layout.get(META_ID_KEY);
        if (string == null) {
            n = intSupplier.getAsInt();
            this.layout.put(META_ID_KEY, Integer.toHexString(n));
        } else {
            n = DataUtils.parseHexInt(string);
        }
        return n;
    }

    public final Map<String, String> getLayoutMap() {
        return new TreeMap<String, String>(this.layout);
    }

    public final boolean isRegularMap(MVMap<?, ?> mVMap) {
        return mVMap != this.layout;
    }

    public final long getRootPos(int n) {
        String string = this.layout.get(MVMap.getMapRootKey(n));
        return string == null ? 0L : DataUtils.parseHexLong(string);
    }

    public final boolean deregisterMapRoot(int n) {
        return this.layout.remove(MVMap.getMapRootKey(n)) != null;
    }

    public final boolean hasChangesSince(long l) {
        return this.layout.hasChangesSince(l) && l > -1L;
    }

    public final long lastChunkVersion() {
        C c = this.lastChunk;
        return c == null ? 0L : ((Chunk)c).version;
    }

    public final long getMaxPageSize() {
        return this.maxPageSize;
    }

    public final int getRetentionTime() {
        return this.retentionTime;
    }

    public final void setRetentionTime(int n) {
        this.retentionTime = n;
    }

    public abstract boolean shouldSaveNow(int var1, int var2);

    public final int getAutoCommitDelay() {
        return this.autoCommitDelay;
    }

    public final void setAutoCommitDelay(int n) {
        if (this.autoCommitDelay != n) {
            this.autoCommitDelay = n;
            if (!this.isReadOnly()) {
                int n2;
                BackgroundWriterThread backgroundWriterThread;
                this.stopBackgroundThread(n >= 0);
                if (n > 0 && this.mvStore.isOpen() && this.backgroundWriterThread.compareAndSet(null, backgroundWriterThread = new BackgroundWriterThread(this, n2 = Math.max(1, n / 10), this.toString()))) {
                    backgroundWriterThread.start();
                    this.serializationExecutor = Utils.createSingleThreadExecutor("H2-serialization");
                    this.bufferSaveExecutor = Utils.createSingleThreadExecutor("H2-save");
                }
            }
        }
    }

    public final boolean isKnownVersion(long l) {
        if (this.chunks.isEmpty()) {
            return true;
        }
        C c = this.getChunkForVersion(l);
        if (c == null) {
            return false;
        }
        try {
            MVMap<String, String> mVMap = this.getLayoutMap(l);
            for (Chunk chunk : this.getChunksFromLayoutMap(mVMap)) {
                String string = Chunk.getMetaKey(chunk.id);
                if (this.layout.containsKey(string) || this.isValidChunk(chunk)) continue;
                return false;
            }
        }
        catch (MVStoreException mVStoreException) {
            return false;
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void rollbackTo(long l) {
        block9: {
            Object object;
            if (l == 0L) {
                object = this.layout.get(META_ID_KEY);
                this.layout.setInitialRoot(this.layout.createEmptyLeaf(), -1L);
                this.layout.put(META_ID_KEY, (String)object);
            } else if (!this.layout.rollbackRoot(l)) {
                object = this.getLayoutMap(l);
                this.layout.setInitialRoot(((MVMap)object).getRootPage(), l);
            }
            this.serializationLock.lock();
            try {
                object = this.getChunkForVersion(l);
                if (object == null) break block9;
                this.saveChunkLock.lock();
                try {
                    this.deadChunks.clear();
                    this.setLastChunk(object);
                    this.adjustStoreToLastChunk();
                }
                finally {
                    this.saveChunkLock.unlock();
                }
            }
            finally {
                this.serializationLock.unlock();
            }
        }
        this.removedPages.clear();
        this.clearCaches();
    }

    protected final void initializeCommonHeaderAttributes(long l) {
        this.setLastChunk(null);
        this.creationTime = l;
        this.storeHeader.put(HDR_H, 2);
        this.storeHeader.put(HDR_BLOCK_SIZE, 4096);
        this.storeHeader.put(HDR_FORMAT, 3);
        this.storeHeader.put(HDR_CREATED, this.creationTime);
    }

    protected final void processCommonHeaderAttributes() {
        this.creationTime = DataUtils.readHexLong(this.storeHeader, HDR_CREATED, 0L);
        long l = System.currentTimeMillis();
        int n = 1970 + (int)(l / 31557600000L);
        if (n < 2014) {
            this.creationTime = l - (long)this.getRetentionTime();
        } else if (l < this.creationTime) {
            this.creationTime = l;
            this.storeHeader.put(HDR_CREATED, this.creationTime);
        }
        int n2 = DataUtils.readHexInt(this.storeHeader, HDR_BLOCK_SIZE, 4096);
        if (n2 != 4096) {
            throw DataUtils.newMVStoreException(5, "Block size {0} is currently not supported", n2);
        }
        long l2 = DataUtils.readHexLong(this.storeHeader, HDR_FORMAT, 1L);
        if (!this.isReadOnly()) {
            if (l2 > 3L) {
                throw this.getUnsupportedWriteFormatException(l2, 3, "The write format {0} is larger than the supported format {1}");
            }
            if (l2 < 3L) {
                throw this.getUnsupportedWriteFormatException(l2, 3, "The write format {0} is smaller than the supported format {1}");
            }
        }
        if ((l2 = DataUtils.readHexLong(this.storeHeader, HDR_FORMAT_READ, l2)) > 3L) {
            throw DataUtils.newMVStoreException(5, "The read format {0} is larger than the supported format {1}", l2, 3);
        }
        if (l2 < 3L) {
            throw DataUtils.newMVStoreException(5, "The read format {0} is smaller than the supported format {1}", l2, 3);
        }
    }

    private long getTimeSinceCreation() {
        return Math.max(0L, this.mvStore.getTimeAbsolute() - this.getCreationTime());
    }

    private MVMap<String, String> getLayoutMap(long l) {
        C c = this.getChunkForVersion(l);
        DataUtils.checkArgument(c != null, "Unknown version {0}", l);
        return this.layout.openReadOnly(((Chunk)c).layoutRootPos, l);
    }

    private C getChunkForVersion(long l) {
        Chunk chunk = null;
        for (Chunk chunk2 : this.chunks.values()) {
            if (chunk2.version > l || chunk != null && chunk2.id <= chunk.id) continue;
            chunk = chunk2;
        }
        return (C)chunk;
    }

    private void scrubLayoutMap(MVMap<String, String> mVMap) {
        String string;
        HashSet<String> hashSet = new HashSet<String>();
        for (String string2 : new String[]{"name.", "map."}) {
            String string3;
            Iterator<String> iterator2 = this.layout.keyIterator(string2);
            while (iterator2.hasNext() && (string3 = iterator2.next()).startsWith(string2)) {
                mVMap.putIfAbsent(string3, this.layout.get(string3));
                this.mvStore.markMetaChanged();
                hashSet.add(string3);
            }
        }
        Iterator<String> iterator3 = this.layout.keyIterator("root.");
        while (iterator3.hasNext() && (string = (String)iterator3.next()).startsWith("root.")) {
            String string4 = string.substring(string.lastIndexOf(46) + 1);
            if (mVMap.containsKey("map." + string4) || DataUtils.parseHexInt(string4) == mVMap.getId()) continue;
            hashSet.add(string);
        }
        for (String string5 : hashSet) {
            this.layout.remove(string5);
        }
    }

    protected final boolean hasPersistentData() {
        return this.lastChunk != null;
    }

    protected final boolean isIdle() {
        return this.autoCompactLastFileOpCount == this.getWriteCount() + this.getReadCount();
    }

    protected final void setLastChunk(C c) {
        this.lastChunk = c;
        this.chunks.clear();
        this.lastChunkId = 0;
        long l = 0L;
        if (c != null) {
            this.lastChunkId = ((Chunk)c).id;
            l = ((Chunk)c).layoutRootPos;
            this.chunks.put(((Chunk)c).id, c);
        }
        this.layout.setRootPos(l, this.lastChunkVersion());
    }

    protected final void registerDeadChunk(C c) {
        this.deadChunks.offer(c);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void dropUnusedChunks() {
        if (!this.deadChunks.isEmpty()) {
            Chunk chunk;
            long l = this.mvStore.getOldestVersionToKeep();
            long l2 = this.getTimeSinceCreation();
            ArrayList<Chunk> arrayList = new ArrayList<Chunk>();
            while ((chunk = (Chunk)this.deadChunks.poll()) != null && (this.isSeasonedChunk(chunk, l2) && FileStore.canOverwriteChunk(chunk, l) || !this.deadChunks.offerFirst(chunk))) {
                if (this.chunks.remove(chunk.id) == null) continue;
                long[] lArray = this.cleanToCCache(chunk);
                if (lArray != null && this.cache != null) {
                    for (long l3 : lArray) {
                        long l4 = DataUtils.composePagePos(chunk.id, l3);
                        this.cache.remove(l4);
                    }
                }
                if (this.layout.remove(Chunk.getMetaKey(chunk.id)) != null) {
                    this.mvStore.markMetaChanged();
                }
                if (!chunk.isAllocated()) continue;
                arrayList.add(chunk);
            }
            if (!arrayList.isEmpty()) {
                this.saveChunkLock.lock();
                try {
                    this.freeChunkSpace(arrayList);
                }
                finally {
                    this.saveChunkLock.unlock();
                }
            }
        }
    }

    private static <C extends Chunk<C>> boolean canOverwriteChunk(C c, long l) {
        return !c.isLive() && c.unusedAtVersion < l;
    }

    private boolean isSeasonedChunk(C c, long l) {
        int n = this.getRetentionTime();
        return n < 0 || ((Chunk)c).time + (long)n <= l;
    }

    private boolean isRewritable(C c, long l) {
        return ((Chunk)c).isRewritable() && this.isSeasonedChunk(c, l);
    }

    protected abstract void writeFully(C var1, long var2, ByteBuffer var4);

    public abstract ByteBuffer readFully(C var1, long var2, int var4);

    protected final ByteBuffer readFully(FileChannel fileChannel, long l, int n) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(n);
        DataUtils.readFully(fileChannel, l, byteBuffer);
        this.readCount.incrementAndGet();
        this.readBytes.addAndGet(n);
        return byteBuffer;
    }

    protected abstract void allocateChunkSpace(C var1, WriteBuffer var2);

    protected abstract void writeChunk(C var1, WriteBuffer var2);

    protected abstract void writeCleanShutdownMark();

    protected abstract void adjustStoreToLastChunk();

    public Map<String, Object> getStoreHeader() {
        return this.storeHeader;
    }

    private C createChunk(long l, long l2) {
        int n = this.findNewChunkId();
        C c = this.createChunk(n);
        ((Chunk)c).time = l;
        ((Chunk)c).version = l2;
        ((Chunk)c).occupancy = new BitSet();
        return c;
    }

    protected abstract C createChunk(int var1);

    public abstract C createChunk(String var1);

    protected abstract C createChunk(Map<String, String> var1);

    private int findNewChunkId() {
        Chunk chunk;
        int n;
        while ((n = ++this.lastChunkId & 0x3FFFFFF) != this.lastChunkId && (chunk = (Chunk)this.chunks.get(n)) != null) {
            if (chunk.isSaved()) continue;
            throw DataUtils.newMVStoreException(3, "Last block {0} not stored, possibly due to out-of-memory", chunk);
        }
        return n;
    }

    protected void writeCleanShutdown() {
        if (!this.isReadOnly()) {
            this.saveChunkLock.lock();
            try {
                this.writeCleanShutdownMark();
                this.sync();
                assert (this.validateFileLength("on close"));
            }
            finally {
                this.saveChunkLock.unlock();
            }
        }
    }

    public void saveChunkMetadataChanges(C c) {
        assert (this.serializationLock.isHeldByCurrentThread());
        while (!((Chunk)c).isAllocated()) {
            this.saveChunkLock.lock();
            try {
                if (((Chunk)c).isAllocated()) {
                    break;
                }
            }
            finally {
                this.saveChunkLock.unlock();
            }
            Thread.yield();
        }
        this.layout.put(Chunk.getMetaKey(((Chunk)c).id), ((Chunk)c).asString());
    }

    protected abstract void freeChunkSpace(Iterable<C> var1);

    protected abstract boolean validateFileLength(String var1);

    public boolean compact(int n, int n2) {
        if (this.hasPersistentData() && n > 0 && this.getChunksFillRate() < n) {
            try {
                Boolean bl = this.mvStore.tryExecuteUnderStoreLock(() -> this.rewriteChunks(n2, 100));
                return bl != null && bl != false;
            }
            catch (InterruptedException interruptedException) {
                throw new RuntimeException(interruptedException);
            }
        }
        return false;
    }

    public void compactStore(long l) {
        this.compactStore(this.autoCompactFillRate, l, 0x1000000, this.mvStore);
    }

    protected abstract void compactStore(int var1, long var2, int var4, MVStore var5);

    protected abstract void doHousekeeping(MVStore var1) throws InterruptedException;

    public MVMap<String, String> start() {
        if (this.size() == 0L) {
            this.initializeCommonHeaderAttributes(this.mvStore.getTimeAbsolute());
            this.initializeStoreHeader(this.mvStore.getTimeAbsolute());
        } else {
            this.saveChunkLock.lock();
            try {
                this.readStoreHeader(this.recoveryMode);
            }
            finally {
                this.saveChunkLock.unlock();
            }
        }
        this.lastCommitTime = this.getTimeSinceCreation();
        this.mvStore.resetLastMapId(this.lastMapId());
        this.mvStore.setCurrentVersion(this.lastChunkVersion());
        MVMap<String, String> mVMap = this.mvStore.openMetaMap();
        this.scrubLayoutMap(mVMap);
        return mVMap;
    }

    protected abstract void initializeStoreHeader(long var1);

    protected abstract void readStoreHeader(boolean var1);

    private int lastMapId() {
        C c = this.lastChunk;
        return c == null ? 0 : ((Chunk)c).mapId;
    }

    private MVStoreException getUnsupportedWriteFormatException(long l, int n, String string) {
        if ((l = DataUtils.readHexLong(this.storeHeader, HDR_FORMAT_READ, l)) >= 3L && l <= 3L) {
            string = string + ", and the file was not opened in read-only mode";
        }
        return DataUtils.newMVStoreException(5, string, l, n);
    }

    protected final C discoverChunk(long l) {
        long l2 = Long.MAX_VALUE;
        C c = null;
        while (l != l2) {
            if (l == 2L) {
                return null;
            }
            C c2 = this.readChunkFooter(l);
            if (c2 != null) {
                l2 = Long.MAX_VALUE;
                c2 = this.readChunkHeaderOptionally(((Chunk)c2).block, ((Chunk)c2).id);
                if (c2 != null) {
                    c = c2;
                    l2 = ((Chunk)c2).block;
                }
            }
            if (--l <= l2 || this.readChunkHeaderOptionally(l) == null) continue;
            l2 = Long.MAX_VALUE;
        }
        return c;
    }

    protected final boolean findLastChunkWithCompleteValidChunkSet(Comparator<C> comparator, Map<Long, C> map, boolean bl) {
        Chunk[] chunkArray = this.createChunksArray(map.size());
        Chunk[] chunkArray2 = map.values().toArray(chunkArray);
        Arrays.sort(chunkArray2, comparator);
        HashMap<Integer, Chunk> hashMap = new HashMap<Integer, Chunk>();
        for (Chunk chunk : chunkArray2) {
            hashMap.put(chunk.id, chunk);
        }
        for (Chunk chunk : chunkArray2) {
            boolean bl2 = true;
            try {
                this.setLastChunk(chunk);
                for (Chunk chunk2 : this.getChunksFromLayoutMap()) {
                    Chunk chunk3 = (Chunk)map.get(chunk2.block);
                    if (chunk3 == null || chunk3.id != chunk2.id) {
                        chunk3 = (Chunk)hashMap.get(chunk2.id);
                        if (chunk3 != null) {
                            chunk2.block = chunk3.block;
                        } else if (chunk2.isLive() && (bl || this.readChunkHeaderAndFooter(chunk2.block, chunk2.id) == null)) {
                            bl2 = false;
                            break;
                        }
                    }
                    if (chunk2.isLive()) continue;
                    chunk2.block = 0L;
                    chunk2.len = 0;
                    if (chunk2.unused == 0L) {
                        chunk2.unused = this.getCreationTime();
                    }
                    if (chunk2.unusedAtVersion != 0L) continue;
                    chunk2.unusedAtVersion = -1L;
                }
            }
            catch (Exception exception) {
                bl2 = false;
            }
            if (!bl2) continue;
            return true;
        }
        return false;
    }

    private C[] createChunksArray(int n) {
        return new Chunk[n];
    }

    private C readChunkHeader(long l) {
        long l2 = l * 4096L;
        ByteBuffer byteBuffer = this.readFully((Chunk)null, l2, 1024);
        Throwable throwable = null;
        try {
            C c = this.createChunk(Chunk.readChunkHeader(byteBuffer));
            if (((Chunk)c).block == 0L) {
                ((Chunk)c).block = l;
            }
            if (((Chunk)c).block == l) {
                return c;
            }
        }
        catch (MVStoreException mVStoreException) {
            throwable = mVStoreException.getCause();
        }
        catch (Throwable throwable2) {
            throwable = throwable2;
        }
        throw DataUtils.newMVStoreException(6, "File corrupt reading chunk at position {0}", l2, throwable);
    }

    protected Iterable<C> getChunksFromLayoutMap() {
        return this.getChunksFromLayoutMap(this.layout);
    }

    private Iterable<C> getChunksFromLayoutMap(final MVMap<String, String> mVMap) {
        return () -> new Iterator<C>(){
            private final Cursor cursor;
            private Chunk nextChunk;
            {
                this.cursor = mVMap.cursor("chunk.");
            }

            @Override
            public boolean hasNext() {
                if (this.nextChunk == null && this.cursor.hasNext() && ((String)this.cursor.next()).startsWith("chunk.")) {
                    this.nextChunk = FileStore.this.createChunk((String)this.cursor.getValue());
                    Chunk chunk = FileStore.this.chunks.putIfAbsent(this.nextChunk.id, this.nextChunk);
                    if (chunk != null) {
                        this.nextChunk = chunk;
                    }
                }
                return this.nextChunk != null;
            }

            @Override
            public C next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                Chunk chunk = this.nextChunk;
                this.nextChunk = null;
                return chunk;
            }
        };
    }

    private boolean isValidChunk(C c) {
        return this.readChunkHeaderAndFooter(((Chunk)c).block, ((Chunk)c).id) != null;
    }

    protected final C readChunkHeaderAndFooter(long l, int n) {
        C c;
        C c2 = this.readChunkHeaderOptionally(l, n);
        if (c2 != null && ((c = this.readChunkFooter(l + (long)((Chunk)c2).len)) == null || ((Chunk)c).id != n || ((Chunk)c).block != ((Chunk)c2).block)) {
            return null;
        }
        return c2;
    }

    protected final C readChunkHeaderOptionally(long l, int n) {
        C c = this.readChunkHeaderOptionally(l);
        return c == null || ((Chunk)c).id != n ? null : (C)c;
    }

    protected final C readChunkHeaderOptionally(long l) {
        try {
            C c = this.readChunkHeader(l);
            return ((Chunk)c).block != l ? null : (C)c;
        }
        catch (Exception exception) {
            return null;
        }
    }

    protected final C readChunkFooter(long l) {
        try {
            long l2 = l * 4096L - 128L;
            if (l2 < 0L) {
                return null;
            }
            ByteBuffer byteBuffer = this.readFully((Chunk)null, l2, 128);
            byte[] byArray = new byte[128];
            byteBuffer.get(byArray);
            HashMap<String, String> hashMap = DataUtils.parseChecksummedMap(byArray);
            if (hashMap != null) {
                C c = this.createChunk(hashMap);
                if (((Chunk)c).block == 0L) {
                    ((Chunk)c).block = l - (long)((Chunk)c).len;
                }
                return c;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return null;
    }

    public WriteBuffer getWriteBuffer() {
        WriteBuffer writeBuffer = this.writeBufferPool.poll();
        if (writeBuffer != null) {
            writeBuffer.clear();
        } else {
            writeBuffer = new WriteBuffer();
        }
        return writeBuffer;
    }

    public void releaseWriteBuffer(WriteBuffer writeBuffer) {
        if (writeBuffer.capacity() <= 0x400000) {
            this.writeBufferPool.offer(writeBuffer);
        }
    }

    public long getCreationTime() {
        return this.creationTime;
    }

    protected final int getAutoCompactFillRate() {
        return this.autoCompactFillRate;
    }

    public void sync() {
    }

    public abstract int getFillRate();

    protected abstract void shrinkStoreIfPossible(int var1);

    public long size() {
        return this.size;
    }

    protected final void setSize(long l) {
        this.size = l;
    }

    public long getWriteCount() {
        return this.writeCount.get();
    }

    private long getWriteBytes() {
        return this.writeBytes.get();
    }

    public long getReadCount() {
        return this.readCount.get();
    }

    public long getReadBytes() {
        return this.readBytes.get();
    }

    public boolean isReadOnly() {
        return this.readOnly;
    }

    public int getDefaultRetentionTime() {
        return 45000;
    }

    public void clear() {
        this.saveChunkLock.lock();
        try {
            this.deadChunks.clear();
            this.lastChunk = null;
            this.readCount.set(0L);
            this.readBytes.set(0L);
            this.writeCount.set(0L);
            this.writeBytes.set(0L);
        }
        finally {
            this.saveChunkLock.unlock();
        }
    }

    public String getFileName() {
        return this.fileName;
    }

    protected final MVStore getMvStore() {
        return this.mvStore;
    }

    protected abstract void markUsed(long var1, int var3);

    public abstract void backup(ZipOutputStream var1) throws IOException;

    protected final ConcurrentMap<Integer, C> getChunks() {
        return this.chunks;
    }

    protected Collection<C> getRewriteCandidates() {
        return null;
    }

    public boolean isSpaceReused() {
        return true;
    }

    public void setReuseSpace(boolean bl) {
    }

    protected final void store() {
        this.serializationLock.unlock();
        try {
            this.mvStore.storeNow();
        }
        finally {
            this.serializationLock.lock();
        }
    }

    final void storeIt(ArrayList<Page<?, ?>> arrayList, long l, boolean bl) throws ExecutionException {
        this.lastCommitTime = this.getTimeSinceCreation();
        this.serializationExecutorHWM = FileStore.submitOrRun(this.serializationExecutor, () -> this.serializeAndStore(bl, arrayList, this.lastCommitTime, l), bl, 3, this.serializationExecutorHWM);
    }

    private static int submitOrRun(ThreadPoolExecutor threadPoolExecutor, Runnable runnable2, boolean bl, int n, int n2) throws ExecutionException {
        if (threadPoolExecutor != null) {
            try {
                Future<?> future = threadPoolExecutor.submit(runnable2);
                int n3 = threadPoolExecutor.getQueue().size();
                if (n3 > n2) {
                    n2 = n3;
                }
                if (bl || n3 > n) {
                    try {
                        future.get();
                    }
                    catch (InterruptedException interruptedException) {
                        // empty catch block
                    }
                }
                return n2;
            }
            catch (RejectedExecutionException rejectedExecutionException) {
                assert (threadPoolExecutor.isShutdown());
                Utils.shutdownExecutor(threadPoolExecutor);
            }
        }
        runnable2.run();
        return n2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void serializeAndStore(boolean bl, ArrayList<Page<?, ?>> arrayList, long l, long l2) {
        this.serializationLock.lock();
        try {
            WriteBuffer writeBuffer;
            C c;
            Chunk chunk = null;
            int n = this.lastChunkId;
            if (n != 0) {
                chunk = (Chunk)this.chunks.get(n &= 0x3FFFFFF);
                assert (chunk != null) : this.lastChunkId + " (" + n + ") " + this.chunks;
                l = Math.max(chunk.time, l);
            }
            try {
                c = this.createChunk(l, l2);
                writeBuffer = this.getWriteBuffer();
                this.serializeToBuffer(writeBuffer, arrayList, c, chunk);
                this.chunks.put(((Chunk)c).id, c);
            }
            catch (Throwable throwable) {
                this.lastChunkId = n;
                throw throwable;
            }
            this.bufferSaveExecutorHWM = FileStore.submitOrRun(this.bufferSaveExecutor, () -> this.storeBuffer(c, writeBuffer), bl, 5, this.bufferSaveExecutorHWM);
            for (Page<?, ?> page : arrayList) {
                page.releaseSavedPages();
            }
        }
        catch (MVStoreException mVStoreException) {
            this.mvStore.panic(mVStoreException);
        }
        catch (Throwable throwable) {
            this.mvStore.panic(DataUtils.newMVStoreException(3, "{0}", throwable.toString(), throwable));
        }
        finally {
            this.serializationLock.unlock();
        }
    }

    private void serializeToBuffer(WriteBuffer writeBuffer, ArrayList<Page<?, ?>> arrayList, C c, C c2) {
        int n = ((Chunk)c).estimateHeaderSize();
        writeBuffer.position(n);
        ((Chunk)c).next = n;
        long l = ((Chunk)c).version;
        PageSerializationManager pageSerializationManager = new PageSerializationManager(this, c, writeBuffer);
        for (Page<?, ?> page2 : arrayList) {
            String n2 = MVMap.getMapRootKey(page2.getMapId());
            if (page2.getTotalCount() == 0L) {
                this.layout.remove(n2);
                continue;
            }
            page2.writeUnsavedRecursive(pageSerializationManager);
            long n3 = page2.getPos();
            this.layout.put(n2, Long.toHexString(n3));
        }
        this.acceptChunkOccupancyChanges(((Chunk)c).time, l);
        if (c2 != null && !this.layout.containsKey(Chunk.getMetaKey(((Chunk)c2).id))) {
            this.saveChunkMetadataChanges(c2);
        }
        RootReference<String, String> rootReference = this.layout.setWriteVersion(l);
        assert (rootReference != null);
        assert (rootReference.version == l) : rootReference.version + " != " + l;
        this.acceptChunkOccupancyChanges(((Chunk)c).time, l);
        this.mvStore.onVersionChange(l);
        Page page = rootReference.root;
        page.writeUnsavedRecursive(pageSerializationManager);
        ((Chunk)c).layoutRootPos = page.getPos();
        arrayList.add(page);
        ((Chunk)c).mapId = this.mvStore.getLastMapId();
        ((Chunk)c).tocPos = writeBuffer.position();
        pageSerializationManager.serializeToC();
        int n2 = writeBuffer.position();
        int n3 = MathUtils.roundUpInt(n2 + 128, 4096);
        writeBuffer.limit(n3);
        ((Chunk)c).len = writeBuffer.limit() / 4096;
        ((Chunk)c).buffer = writeBuffer.getBuffer();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void storeBuffer(C c, WriteBuffer writeBuffer) {
        this.saveChunkLock.lock();
        try {
            if (this.closed) {
                throw DataUtils.newMVStoreException(2, "This fileStore is closed", new Object[0]);
            }
            int n = (int)((Chunk)c).next;
            this.allocateChunkSpace(c, writeBuffer);
            writeBuffer.position(0);
            ((Chunk)c).writeChunkHeader(writeBuffer, n);
            writeBuffer.position(writeBuffer.limit() - 128);
            writeBuffer.put(((Chunk)c).getFooterBytes());
            writeBuffer.position(0);
            this.writeChunk(c, writeBuffer);
            this.lastChunk = c;
        }
        catch (MVStoreException mVStoreException) {
            this.mvStore.panic(mVStoreException);
        }
        catch (Throwable throwable) {
            this.mvStore.panic(DataUtils.newMVStoreException(3, "{0}", throwable.toString(), throwable));
        }
        finally {
            this.saveChunkLock.unlock();
            ((Chunk)c).buffer = null;
            this.releaseWriteBuffer(writeBuffer);
        }
    }

    private void acceptChunkOccupancyChanges(long l, long l2) {
        assert (this.serializationLock.isHeldByCurrentThread());
        if (this.hasPersistentData()) {
            HashSet<Chunk> hashSet = new HashSet<Chunk>();
            while (true) {
                RemovedPageInfo removedPageInfo;
                if ((removedPageInfo = this.removedPages.peek()) != null && removedPageInfo.version < l2) {
                    removedPageInfo = this.removedPages.poll();
                    assert (removedPageInfo != null);
                    assert (removedPageInfo.version < l2) : removedPageInfo + " < " + l2;
                    int n = removedPageInfo.getPageChunkId();
                    Chunk chunk = (Chunk)this.chunks.get(n);
                    assert (!this.mvStore.isOpen() || chunk != null) : n;
                    if (chunk == null) continue;
                    hashSet.add(chunk);
                    if (!chunk.accountForRemovedPage(removedPageInfo.getPageNo(), removedPageInfo.getPageLength(), removedPageInfo.isPinned(), l, removedPageInfo.version)) continue;
                    this.registerDeadChunk(chunk);
                    continue;
                }
                if (hashSet.isEmpty()) {
                    return;
                }
                for (Chunk chunk : hashSet) {
                    this.saveChunkMetadataChanges(chunk);
                }
                hashSet.clear();
            }
        }
    }

    public int getChunksFillRate() {
        return this.getChunksFillRate(true);
    }

    int getRewritableChunksFillRate() {
        return this.getChunksFillRate(false);
    }

    private int getChunksFillRate(boolean bl) {
        long l = 1L;
        long l2 = 1L;
        long l3 = this.getTimeSinceCreation();
        for (Chunk chunk : this.chunks.values()) {
            if (!bl && !this.isRewritable(chunk, l3)) continue;
            assert (chunk.maxLen >= 0L);
            l += chunk.maxLen;
            l2 += chunk.maxLenLive;
        }
        int n = (int)(100L * l2 / l);
        return n;
    }

    private int getChunkCount() {
        return this.chunks.size();
    }

    private int getPageCount() {
        int n = 0;
        for (Chunk chunk : this.chunks.values()) {
            n += chunk.pageCount;
        }
        return n;
    }

    private int getLivePageCount() {
        int n = 0;
        for (Chunk chunk : this.chunks.values()) {
            n += chunk.pageCountLive;
        }
        return n;
    }

    void cachePage(Page<?, ?> page) {
        if (this.cache != null) {
            this.cache.put(page.getPos(), page, page.getMemory());
        }
    }

    public int getCacheSize() {
        if (this.cache == null) {
            return 0;
        }
        return (int)(this.cache.getMaxMemory() >> 20);
    }

    public int getCacheSizeUsed() {
        if (this.cache == null) {
            return 0;
        }
        return (int)(this.cache.getUsedMemory() >> 20);
    }

    public void setCacheSize(int n) {
        long l = (long)n * 1024L * 1024L;
        if (this.cache != null) {
            this.cache.setMaxMemory(l);
            this.cache.clear();
        }
    }

    void cacheToC(C c, long[] lArray) {
        this.chunksToC.put(((Chunk)c).id, lArray, (long)lArray.length * 8L + 24L);
    }

    private long[] cleanToCCache(C c) {
        return this.chunksToC.remove(((Chunk)c).id);
    }

    public void populateInfo(BiConsumer<String, String> biConsumer) {
        biConsumer.accept("info.FILE_WRITE", Long.toString(this.getWriteCount()));
        biConsumer.accept("info.FILE_WRITE_BYTES", Long.toString(this.getWriteBytes()));
        biConsumer.accept("info.FILE_READ", Long.toString(this.getReadCount()));
        biConsumer.accept("info.FILE_READ_BYTES", Long.toString(this.getReadBytes()));
        biConsumer.accept("info.FILL_RATE", Integer.toString(this.getFillRate()));
        biConsumer.accept("info.CHUNKS_FILL_RATE", Integer.toString(this.getChunksFillRate()));
        biConsumer.accept("info.CHUNKS_FILL_RATE_RW", Integer.toString(this.getRewritableChunksFillRate()));
        biConsumer.accept("info.FILE_SIZE", Long.toString(this.size()));
        biConsumer.accept("info.CHUNK_COUNT", Long.toString(this.getChunkCount()));
        biConsumer.accept("info.PAGE_COUNT", Long.toString(this.getPageCount()));
        biConsumer.accept("info.PAGE_COUNT_LIVE", Long.toString(this.getLivePageCount()));
        biConsumer.accept("info.PAGE_SIZE", Long.toString(this.getMaxPageSize()));
        biConsumer.accept("info.CACHE_MAX_SIZE", Integer.toString(this.getCacheSize()));
        biConsumer.accept("info.CACHE_SIZE", Integer.toString(this.getCacheSizeUsed()));
        biConsumer.accept("info.CACHE_HIT_RATIO", Integer.toString(this.getCacheHitRatio()));
        biConsumer.accept("info.TOC_CACHE_HIT_RATIO", Integer.toString(this.getTocCacheHitRatio()));
    }

    public int getCacheHitRatio() {
        return FileStore.getCacheHitRatio(this.cache);
    }

    public int getTocCacheHitRatio() {
        return FileStore.getCacheHitRatio(this.chunksToC);
    }

    private static int getCacheHitRatio(CacheLongKeyLIRS<?> cacheLongKeyLIRS) {
        if (cacheLongKeyLIRS == null) {
            return 0;
        }
        long l = cacheLongKeyLIRS.getHits();
        return (int)(100L * l / (l + cacheLongKeyLIRS.getMisses() + 1L));
    }

    boolean isBackgroundThread() {
        return Thread.currentThread() == this.backgroundWriterThread.get();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void stopBackgroundThread(boolean bl) {
        BackgroundWriterThread backgroundWriterThread;
        while ((backgroundWriterThread = this.backgroundWriterThread.get()) != null) {
            if (!this.backgroundWriterThread.compareAndSet(backgroundWriterThread, null)) continue;
            if (backgroundWriterThread != Thread.currentThread()) {
                Object object = backgroundWriterThread.sync;
                synchronized (object) {
                    backgroundWriterThread.sync.notifyAll();
                }
                if (bl) {
                    try {
                        backgroundWriterThread.join();
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
            }
            this.shutdownExecutors();
            break;
        }
    }

    private void shutdownExecutors() {
        Utils.shutdownExecutor(this.serializationExecutor);
        this.serializationExecutor = null;
        Utils.shutdownExecutor(this.bufferSaveExecutor);
        this.bufferSaveExecutor = null;
    }

    private Iterable<C> findOldChunks(int n, int n2) {
        assert (this.hasPersistentData());
        long l = this.getTimeSinceCreation();
        PriorityQueue<Chunk> priorityQueue = new PriorityQueue<Chunk>(this.chunks.size() / 4 + 1, (chunk, chunk2) -> {
            int n = Integer.compare(chunk2.collectPriority, chunk.collectPriority);
            if (n == 0) {
                n = Long.compare(chunk2.maxLenLive, chunk.maxLenLive);
            }
            return n;
        });
        long l2 = 0L;
        long l3 = this.lastChunkVersion() + 1L;
        Collection<C> collection = this.getRewriteCandidates();
        if (collection == null) {
            collection = this.chunks.values();
        }
        for (Chunk chunk3 : collection) {
            Chunk chunk4;
            int n3 = chunk3.getFillRate();
            if (!this.isRewritable(chunk3, l) || n3 > n2) continue;
            long l4 = Math.max(1L, l3 - chunk3.version);
            chunk3.collectPriority = (int)((long)(n3 * 1000) / l4);
            l2 += chunk3.maxLenLive;
            priorityQueue.offer(chunk3);
            while (l2 > (long)n && (chunk4 = (Chunk)priorityQueue.poll()) != null) {
                l2 -= chunk4.maxLenLive;
            }
        }
        return priorityQueue.isEmpty() ? null : priorityQueue;
    }

    void writeInBackground() {
        block5: {
            try {
                if (this.mvStore.isOpen() && !this.isReadOnly()) {
                    long l = this.getTimeSinceCreation();
                    if (l > this.lastCommitTime + (long)this.autoCommitDelay) {
                        this.mvStore.tryCommit();
                    }
                    this.doHousekeeping(this.mvStore);
                    this.autoCompactLastFileOpCount = this.getWriteCount() + this.getReadCount();
                }
            }
            catch (InterruptedException interruptedException) {
            }
            catch (Throwable throwable) {
                if (this.mvStore.handleException(throwable)) break block5;
                throw throwable;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected boolean rewriteChunks(int n, int n2) {
        this.serializationLock.lock();
        try {
            MVStore.TxCounter txCounter = this.mvStore.registerVersionUsage();
            try {
                this.acceptChunkOccupancyChanges(this.getTimeSinceCreation(), this.mvStore.getCurrentVersion());
                Iterable<C> iterable = this.findOldChunks(n, n2);
                if (iterable != null) {
                    HashSet<Integer> hashSet = FileStore.createIdSet(iterable);
                    boolean bl = !hashSet.isEmpty() && this.compactRewrite(hashSet) > 0;
                    return bl;
                }
            }
            finally {
                this.mvStore.deregisterVersionUsage(txCounter);
            }
            boolean bl = false;
            return bl;
        }
        finally {
            this.serializationLock.unlock();
        }
    }

    private static <C extends Chunk<C>> HashSet<Integer> createIdSet(Iterable<C> iterable) {
        HashSet<Integer> hashSet = new HashSet<Integer>();
        for (Chunk chunk : iterable) {
            hashSet.add(chunk.id);
        }
        return hashSet;
    }

    public void executeFileStoreOperation(Runnable runnable2) {
        Utils.flushExecutor(this.serializationExecutor);
        this.serializationLock.lock();
        try {
            Utils.flushExecutor(this.bufferSaveExecutor);
            runnable2.run();
        }
        finally {
            this.serializationLock.unlock();
        }
    }

    private int compactRewrite(Set<Integer> set) {
        this.acceptChunkOccupancyChanges(this.getTimeSinceCreation(), this.mvStore.getCurrentVersion());
        int n = this.rewriteChunks(set, false);
        this.acceptChunkOccupancyChanges(this.getTimeSinceCreation(), this.mvStore.getCurrentVersion());
        return n += this.rewriteChunks(set, true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private int rewriteChunks(Set<Integer> set, boolean bl) {
        int n = 0;
        for (int n2 : set) {
            Chunk chunk = (Chunk)this.chunks.get(n2);
            long[] lArray = this.getToC(chunk);
            if (lArray == null) continue;
            int n3 = 0;
            while ((n3 = chunk.occupancy.nextClearBit(n3)) < chunk.pageCount) {
                MVMap<String, String> mVMap;
                long l = lArray[n3];
                int n4 = DataUtils.getPageMapId(l);
                MVMap<String, String> mVMap2 = this.mvStore.getMetaMap();
                MVMap<String, String> mVMap3 = n4 == this.layout.getId() ? this.layout : (mVMap = n4 == mVMap2.getId() ? mVMap2 : this.mvStore.getMap(n4));
                if (mVMap != null && !mVMap.isClosed()) {
                    assert (!mVMap.isSingleWriter());
                    if (bl || DataUtils.isLeafPosition(l)) {
                        long l2 = DataUtils.composePagePos(n2, l);
                        this.serializationLock.unlock();
                        try {
                            if (mVMap.rewritePage(l2)) {
                                ++n;
                                if (n4 == mVMap2.getId()) {
                                    this.mvStore.markMetaChanged();
                                }
                            }
                        }
                        finally {
                            this.serializationLock.lock();
                        }
                    }
                }
                ++n3;
            }
        }
        return n;
    }

    <K, V> Page<K, V> readPage(MVMap<K, V> mVMap, long l) {
        try {
            if (!DataUtils.isPageSaved(l)) {
                throw DataUtils.newMVStoreException(6, "Position 0", new Object[0]);
            }
            Page<K, V> page = this.readPageFromCache(l);
            if (page == null) {
                MVStoreException mVStoreException;
                boolean bl;
                C c = this.getChunk(l);
                int n = DataUtils.getPageOffset(l);
                do {
                    mVStoreException = null;
                    ByteBuffer byteBuffer = ((Chunk)c).buffer;
                    boolean bl2 = bl = byteBuffer == null;
                    if (bl) {
                        byteBuffer = ((Chunk)c).readBufferForPage(this, n, l);
                    } else {
                        byteBuffer = byteBuffer.duplicate();
                        byteBuffer.position(n);
                        byteBuffer = byteBuffer.slice();
                    }
                    try {
                        page = Page.read(byteBuffer, l, mVMap);
                    }
                    catch (MVStoreException mVStoreException2) {
                        mVStoreException = mVStoreException2;
                    }
                    catch (Exception exception) {
                        mVStoreException = DataUtils.newMVStoreException(6, "Unable to read the page at position 0x{0}, chunk {1}, offset 0x{3}", Long.toHexString(l), c, Long.toHexString(n), exception);
                    }
                } while (!bl);
                if (mVStoreException != null) {
                    throw mVStoreException;
                }
                this.cachePage(page);
            }
            return page;
        }
        catch (MVStoreException mVStoreException) {
            if (this.recoveryMode) {
                return mVMap.createEmptyLeaf();
            }
            throw mVStoreException;
        }
    }

    private C getChunk(long l) {
        int n = DataUtils.getPageChunkId(l);
        Chunk chunk = (Chunk)this.chunks.get(n);
        if (chunk == null) {
            String string = this.layout.get(Chunk.getMetaKey(n));
            if (string == null) {
                throw DataUtils.newMVStoreException(9, "Chunk {0} not found", n);
            }
            chunk = this.createChunk(string);
            if (!chunk.isSaved()) {
                throw DataUtils.newMVStoreException(6, "Chunk {0} is invalid", n);
            }
            this.chunks.put(chunk.id, chunk);
        }
        return (C)chunk;
    }

    private int calculatePageNo(long l) {
        int n = -1;
        C c = this.getChunk(l);
        long[] lArray = this.getToC(c);
        if (lArray != null) {
            int n2 = DataUtils.getPageOffset(l);
            int n3 = 0;
            int n4 = lArray.length - 1;
            while (n3 <= n4) {
                int n5 = n3 + n4 >>> 1;
                long l2 = DataUtils.getPageOffset(lArray[n5]);
                if (l2 < (long)n2) {
                    n3 = n5 + 1;
                    continue;
                }
                if (l2 > (long)n2) {
                    n4 = n5 - 1;
                    continue;
                }
                n = n5;
                break;
            }
        }
        return n;
    }

    private void clearCaches() {
        if (this.cache != null) {
            this.cache.clear();
        }
        if (this.chunksToC != null) {
            this.chunksToC.clear();
        }
        this.removedPages.clear();
    }

    private long[] getToC(C c) {
        if (((Chunk)c).tocPos == 0) {
            return null;
        }
        long[] lArray = this.chunksToC.get(((Chunk)c).id);
        if (lArray == null) {
            lArray = ((Chunk)c).readToC(this);
            this.cacheToC(c, lArray);
        }
        assert (lArray.length == ((Chunk)c).pageCount) : lArray.length + " != " + ((Chunk)c).pageCount;
        return lArray;
    }

    private <K, V> Page<K, V> readPageFromCache(long l) {
        return this.cache == null ? null : this.cache.get(l);
    }

    public void accountForRemovedPage(long l, long l2, boolean bl, int n) {
        assert (DataUtils.isPageSaved(l));
        if (n < 0) {
            n = this.calculatePageNo(l);
        }
        RemovedPageInfo removedPageInfo = new RemovedPageInfo(l, bl, l2, n);
        this.removedPages.add(removedPageInfo);
    }

    private static final class BackgroundWriterThread
    extends Thread {
        public final Object sync = new Object();
        private final FileStore<?> store;
        private final int sleep;

        BackgroundWriterThread(FileStore<?> fileStore, int n, String string) {
            super("MVStore background writer " + string);
            this.store = fileStore;
            this.sleep = n;
            this.setDaemon(true);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void run() {
            while (this.store.isBackgroundThread()) {
                Object object = this.sync;
                synchronized (object) {
                    try {
                        this.sync.wait(this.sleep);
                    }
                    catch (InterruptedException interruptedException) {
                        // empty catch block
                    }
                }
                if (!this.store.isBackgroundThread()) break;
                this.store.writeInBackground();
            }
        }
    }

    private static final class RemovedPageInfo
    implements Comparable<RemovedPageInfo> {
        final long version;
        final long removedPageInfo;

        RemovedPageInfo(long l, boolean bl, long l2, int n) {
            this.removedPageInfo = RemovedPageInfo.createRemovedPageInfo(l, bl, n);
            this.version = l2;
        }

        @Override
        public int compareTo(RemovedPageInfo removedPageInfo) {
            return Long.compare(this.version, removedPageInfo.version);
        }

        int getPageChunkId() {
            return DataUtils.getPageChunkId(this.removedPageInfo);
        }

        int getPageNo() {
            return DataUtils.getPageOffset(this.removedPageInfo);
        }

        int getPageLength() {
            return DataUtils.getPageMaxLength(this.removedPageInfo);
        }

        boolean isPinned() {
            return (this.removedPageInfo & 1L) == 1L;
        }

        private static long createRemovedPageInfo(long l, boolean bl, int n) {
            assert (n >= 0);
            assert (n >> 26 == 0);
            long l2 = l & 0xFFFFFFC00000003EL | (long)n << 6;
            if (bl) {
                l2 |= 1L;
            }
            return l2;
        }

        public String toString() {
            return "RemovedPageInfo{version=" + this.version + ", chunk=" + this.getPageChunkId() + ", pageNo=" + this.getPageNo() + ", len=" + this.getPageLength() + (this.isPinned() ? ", pinned" : "") + '}';
        }
    }

    public static final class PageSerializationManager {
        private final C chunk;
        private final WriteBuffer buff;
        private final List<Long> toc = new ArrayList<Long>();
        final /* synthetic */ FileStore this$0;

        PageSerializationManager(C c, WriteBuffer writeBuffer) {
            this.this$0 = var1_1;
            this.chunk = c;
            this.buff = writeBuffer;
        }

        public WriteBuffer getBuffer() {
            return this.buff;
        }

        private int getChunkId() {
            return ((Chunk)this.chunk).id;
        }

        public int getPageNo() {
            return this.toc.size();
        }

        public long getPagePosition(int n, int n2, int n3, int n4) {
            long l = DataUtils.composeTocElement(n, n2, n3, n4);
            this.toc.add(l);
            long l2 = DataUtils.composePagePos(((Chunk)this.chunk).id, l);
            int n5 = this.getChunkId();
            int n6 = DataUtils.getCheckValue(n5) ^ DataUtils.getCheckValue(n2) ^ DataUtils.getCheckValue(n3);
            this.buff.putInt(n2, n3).putShort(n2 + 4, (short)n6);
            return l2;
        }

        public void onPageSerialized(Page<?, ?> page, boolean bl, int n, boolean bl2) {
            this.this$0.cachePage(page);
            if (!page.isLeaf()) {
                this.this$0.cachePage(page);
            }
            ((Chunk)this.chunk).accountForWrittenPage(n, bl2);
            if (bl) {
                this.this$0.accountForRemovedPage(page.getPos(), ((Chunk)this.chunk).version + 1L, bl2, page.pageNo);
            }
        }

        public void serializeToC() {
            long[] lArray = new long[this.toc.size()];
            int n = 0;
            for (long l : this.toc) {
                lArray[n++] = l;
                this.buff.putLong(l);
                this.this$0.mvStore.countNewPage(DataUtils.isLeafPosition(l));
            }
            this.this$0.cacheToC(this.chunk, lArray);
        }
    }
}

