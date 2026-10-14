/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiConsumer;
import java.util.function.LongConsumer;
import java.util.function.Predicate;
import org.h2.compress.CompressDeflate;
import org.h2.compress.CompressLZF;
import org.h2.compress.Compressor;
import org.h2.mvstore.DataUtils;
import org.h2.mvstore.FileStore;
import org.h2.mvstore.MVMap;
import org.h2.mvstore.MVStoreException;
import org.h2.mvstore.MVStoreTool;
import org.h2.mvstore.Page;
import org.h2.mvstore.RootReference;
import org.h2.mvstore.SingleFileStore;
import org.h2.mvstore.type.StringDataType;
import org.h2.store.fs.FileUtils;
import org.h2.util.Utils;

public class MVStore
implements AutoCloseable {
    private static final int STATE_OPEN = 0;
    private static final int STATE_STOPPING = 1;
    private static final int STATE_CLOSED = 2;
    static final long INITIAL_VERSION = -1L;
    private final ReentrantLock storeLock = new ReentrantLock(true);
    private final AtomicBoolean storeOperationInProgress = new AtomicBoolean();
    private volatile int state;
    private final FileStore<?> fileStore;
    private final boolean fileStoreShallBeClosed;
    private final int keysPerPage;
    private long updateCounter = 0L;
    private long updateAttemptCounter = 0L;
    private final MVMap<String, String> meta;
    private final ConcurrentHashMap<Integer, MVMap<?, ?>> maps = new ConcurrentHashMap();
    private final AtomicInteger lastMapId = new AtomicInteger();
    private int versionsToKeep = 5;
    private final int compressionLevel;
    private Compressor compressorFast;
    private Compressor compressorHigh;
    public final Thread.UncaughtExceptionHandler backgroundExceptionHandler;
    private volatile long currentVersion;
    private final AtomicLong oldestVersionToKeep = new AtomicLong();
    private final Deque<TxCounter> versions = new LinkedList<TxCounter>();
    private volatile TxCounter currentTxCounter = new TxCounter(this.currentVersion);
    private int unsavedMemory;
    private final int autoCommitMemory;
    private volatile boolean saveNeeded;
    private volatile boolean metaChanged;
    private volatile MVStoreException panicException;
    private long lastTimeAbsolute;
    private long leafCount;
    private long nonLeafCount;
    private volatile LongConsumer oldestVersionTracker;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    MVStore(Map<String, Object> map) {
        this.compressionLevel = DataUtils.getConfigParam(map, "compress", 0);
        String string = (String)map.get("fileName");
        FileStore fileStore = (FileStore)map.get("fileStore");
        boolean bl = false;
        if (fileStore == null) {
            if (string != null) {
                fileStore = new SingleFileStore(map);
                bl = true;
            }
            this.fileStoreShallBeClosed = true;
        } else {
            if (string != null) {
                throw new IllegalArgumentException("fileName && fileStore");
            }
            Boolean bl2 = (Boolean)map.get("fileStoreIsAdopted");
            this.fileStoreShallBeClosed = bl2 != null && bl2 != false;
        }
        this.fileStore = fileStore;
        this.keysPerPage = DataUtils.getConfigParam(map, "keysPerPage", 48);
        this.backgroundExceptionHandler = (Thread.UncaughtExceptionHandler)map.get("backgroundExceptionHandler");
        if (fileStore != null) {
            int n;
            int n2 = Math.max(1, Math.min(19, Utils.scaleForAvailableMemory(64))) * 1024;
            n2 = DataUtils.getConfigParam(map, "autoCommitBufferSize", n2);
            this.autoCommitMemory = n2 * 1024;
            char[] cArray = (char[])map.remove("encryptionKey");
            MVMap<String, String> mVMap = null;
            this.storeLock.lock();
            try {
                if (bl) {
                    n = map.containsKey("readOnly");
                    fileStore.open(string, n != 0, cArray);
                }
                fileStore.bind(this);
                mVMap = fileStore.start();
            }
            catch (MVStoreException mVStoreException) {
                this.panic(mVStoreException);
            }
            finally {
                if (cArray != null) {
                    Arrays.fill(cArray, '\u0000');
                }
                this.unlockAndCheckPanicCondition();
            }
            this.meta = mVMap;
            this.scrubMetaMap();
            n = DataUtils.getConfigParam(map, "autoCommitDelay", 1000);
            this.setAutoCommitDelay(n);
        } else {
            this.autoCommitMemory = 0;
            this.meta = this.openMetaMap();
        }
        this.onVersionChange(this.currentVersion);
    }

    public MVMap<String, String> openMetaMap() {
        int n = this.fileStore != null ? this.fileStore.getMetaMapId(this::getNextMapId) : 1;
        MVMap<String, String> mVMap = new MVMap<String, String>(this, n, StringDataType.INSTANCE, StringDataType.INSTANCE);
        mVMap.setRootPos(this.getRootPos(mVMap.getId()), this.currentVersion);
        return mVMap;
    }

    private void scrubMetaMap() {
        String string;
        String string22;
        HashSet<String> hashSet = new HashSet<String>();
        Iterator<String> iterator2 = this.meta.keyIterator("name.");
        while (iterator2.hasNext() && (string22 = iterator2.next()).startsWith("name.")) {
            int n;
            String string3;
            string = string22.substring("name.".length());
            if (string.equals(string3 = this.getMapName(n = DataUtils.parseHexInt(this.meta.get(string22))))) continue;
            hashSet.add(string22);
        }
        for (String string22 : hashSet) {
            this.meta.remove(string22);
            this.markMetaChanged();
        }
        iterator2 = this.meta.keyIterator("map.");
        while (iterator2.hasNext() && (string22 = iterator2.next()).startsWith("map.")) {
            string = DataUtils.getMapName(this.meta.get(string22));
            String string4 = string22.substring("map.".length());
            this.adjustLastMapId(DataUtils.parseHexInt(string4));
            if (string4.equals(this.meta.get("name." + string))) continue;
            this.meta.put("name." + string, string4);
            this.markMetaChanged();
        }
    }

    private void unlockAndCheckPanicCondition() {
        this.storeLock.unlock();
        MVStoreException mVStoreException = this.getPanicException();
        if (mVStoreException != null) {
            this.closeImmediately();
            throw mVStoreException;
        }
    }

    public void panic(MVStoreException mVStoreException) {
        if (this.isOpen()) {
            this.handleException(mVStoreException);
            this.panicException = mVStoreException;
        }
        throw mVStoreException;
    }

    public MVStoreException getPanicException() {
        return this.panicException;
    }

    public static MVStore open(String string) {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("fileName", string);
        return new MVStore(hashMap);
    }

    public <K, V> MVMap<K, V> openMap(String string) {
        return this.openMap(string, new MVMap.Builder());
    }

    public <M extends MVMap<K, V>, K, V> M openMap(String string, MVMap.MapBuilder<M, K, V> mapBuilder) {
        int n = this.getMapId(string);
        if (n >= 0) {
            MVMap<K, V> mVMap = this.getMap(n);
            if (mVMap == null) {
                mVMap = this.openMap(n, mapBuilder);
            }
            assert (mapBuilder.getKeyType() == null || mVMap.getKeyType().getClass().equals(mapBuilder.getKeyType().getClass()));
            assert (mapBuilder.getValueType() == null || mVMap.getValueType().getClass().equals(mapBuilder.getValueType().getClass()));
            return (M)mVMap;
        }
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        n = this.getNextMapId();
        assert (this.getMap(n) == null);
        hashMap.put("id", n);
        long l = this.currentVersion;
        hashMap.put("createVersion", l);
        Object object = mapBuilder.create(this, hashMap);
        String string2 = Integer.toHexString(n);
        this.meta.put(MVMap.getMapKey(n), ((MVMap)object).asString(string));
        String string3 = this.meta.putIfAbsent("name." + string, string2);
        if (string3 != null) {
            this.meta.remove(MVMap.getMapKey(n));
            return this.openMap(string, mapBuilder);
        }
        ((MVMap)object).setRootPos(0L, l);
        this.markMetaChanged();
        MVMap<?, ?> mVMap = this.maps.putIfAbsent(n, (MVMap<?, ?>)object);
        if (mVMap != null) {
            object = mVMap;
        }
        return object;
    }

    public <M extends MVMap<K, V>, K, V> M openMap(int n, MVMap.MapBuilder<M, K, V> mapBuilder) {
        MVMap<K, V> mVMap;
        while ((mVMap = this.getMap(n)) == null) {
            String string = this.meta.get(MVMap.getMapKey(n));
            DataUtils.checkArgument(string != null, "Missing map with id {0}", n);
            HashMap<String, Object> hashMap = new HashMap<String, Object>(DataUtils.parseMap(string));
            hashMap.put("id", (String)((Object)Integer.valueOf(n)));
            mVMap = mapBuilder.create(this, hashMap);
            long l = this.getRootPos(n);
            mVMap.setRootPos(l, this.currentVersion);
            if (this.maps.putIfAbsent(n, mVMap) != null) continue;
            break;
        }
        return (M)mVMap;
    }

    public <K, V> MVMap<K, V> getMap(int n) {
        this.checkNotClosed();
        MVMap<?, ?> mVMap = this.maps.get(n);
        return mVMap;
    }

    public Set<String> getMapNames() {
        String string;
        HashSet<String> hashSet = new HashSet<String>();
        this.checkNotClosed();
        Iterator<String> iterator2 = this.meta.keyIterator("name.");
        while (iterator2.hasNext() && (string = iterator2.next()).startsWith("name.")) {
            String string2 = string.substring("name.".length());
            hashSet.add(string2);
        }
        return hashSet;
    }

    public Map<String, String> getLayoutMap() {
        return this.fileStore == null ? null : this.fileStore.getLayoutMap();
    }

    private boolean isRegularMap(MVMap<?, ?> mVMap) {
        return mVMap != this.meta && (this.fileStore == null || this.fileStore.isRegularMap(mVMap));
    }

    public MVMap<String, String> getMetaMap() {
        this.checkNotClosed();
        return this.meta;
    }

    public boolean hasMap(String string) {
        return this.meta.containsKey("name." + string);
    }

    public boolean hasData(String string) {
        return this.hasMap(string) && this.getRootPos(this.getMapId(string)) != 0L;
    }

    void markMetaChanged() {
        this.metaChanged = true;
    }

    int getLastMapId() {
        return this.lastMapId.get();
    }

    private int getNextMapId() {
        return this.lastMapId.incrementAndGet();
    }

    void adjustLastMapId(int n) {
        if (n > this.lastMapId.get()) {
            this.lastMapId.set(n);
        }
    }

    void resetLastMapId(int n) {
        this.lastMapId.set(n);
    }

    @Override
    public void close() {
        this.closeStore(true, 0);
    }

    public void close(int n) {
        if (!this.isClosed()) {
            if (this.fileStore != null) {
                boolean bl;
                boolean bl2 = bl = n == -1;
                if (this.fileStore.isReadOnly()) {
                    bl = false;
                } else {
                    this.commit();
                }
                if (bl) {
                    n = 0;
                }
                this.closeStore(true, n);
                String string = this.fileStore.getFileName();
                if (bl && FileUtils.exists(string)) {
                    MVStoreTool.compact(string, true);
                }
            } else {
                this.close();
            }
        }
    }

    public void closeImmediately() {
        try {
            this.closeStore(false, 0);
        }
        catch (Throwable throwable) {
            this.handleException(throwable);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void closeStore(boolean bl, int n) {
        while (!this.isClosed()) {
            this.setAutoCommitDelay(-1);
            this.setOldestVersionTracker(null);
            this.storeLock.lock();
            try {
                if (this.state != 0) continue;
                this.state = 1;
                try {
                    try {
                        if (bl && this.fileStore != null && !this.fileStore.isReadOnly()) {
                            for (MVMap<?, ?> mVMap : this.maps.values()) {
                                if (!mVMap.isClosed()) continue;
                                this.fileStore.deregisterMapRoot(mVMap.getId());
                            }
                            this.setRetentionTime(0);
                            this.commit();
                            assert (this.oldestVersionToKeep.get() == this.currentVersion) : this.oldestVersionToKeep.get() + " != " + this.currentVersion;
                            this.fileStore.stop(n);
                        }
                        if (this.meta != null) {
                            this.meta.close();
                        }
                        for (MVMap<?, ?> mVMap : new ArrayList(this.maps.values())) {
                            mVMap.close();
                        }
                        this.maps.clear();
                    }
                    finally {
                        if (this.fileStore == null || !this.fileStoreShallBeClosed) continue;
                        this.fileStore.close();
                    }
                }
                finally {
                    this.state = 2;
                }
            }
            finally {
                this.storeLock.unlock();
            }
        }
    }

    public boolean isPersistent() {
        return this.fileStore != null;
    }

    public long tryCommit() {
        return this.tryCommit(mVStore -> true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private long tryCommit(Predicate<MVStore> predicate) {
        if (this.canStartStoreOperation() && this.storeLock.tryLock()) {
            try {
                if (predicate.test(this)) {
                    long l = this.store(false);
                    return l;
                }
            }
            finally {
                this.unlockAndCheckPanicCondition();
            }
        }
        return -1L;
    }

    public long commit() {
        return this.commit(mVStore -> true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private long commit(Predicate<MVStore> predicate) {
        if (this.canStartStoreOperation()) {
            this.storeLock.lock();
            try {
                if (predicate.test(this)) {
                    long l = this.store(true);
                    return l;
                }
            }
            finally {
                this.unlockAndCheckPanicCondition();
            }
        }
        return -1L;
    }

    private boolean canStartStoreOperation() {
        return !this.storeLock.isHeldByCurrentThread() || !this.storeOperationInProgress.get();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private long store(boolean bl) {
        assert (this.storeLock.isHeldByCurrentThread());
        if (this.isOpenOrStopping() && this.hasUnsavedChanges() && this.storeOperationInProgress.compareAndSet(false, true)) {
            try {
                long l = ++this.currentVersion;
                if (this.fileStore == null) {
                    this.setWriteVersion(this.currentVersion);
                } else {
                    if (this.fileStore.isReadOnly()) {
                        throw DataUtils.newMVStoreException(2, "This store is read-only", new Object[0]);
                    }
                    this.fileStore.dropUnusedChunks();
                    this.storeNow(bl);
                }
                long l2 = l;
                return l2;
            }
            finally {
                this.storeOperationInProgress.set(false);
            }
        }
        return -1L;
    }

    private void setWriteVersion(long l) {
        Iterator<MVMap<?, ?>> iterator2 = this.maps.values().iterator();
        while (iterator2.hasNext()) {
            MVMap<?, ?> mVMap = iterator2.next();
            assert (this.isRegularMap(mVMap));
            if (mVMap.setWriteVersion(l) != null) continue;
            iterator2.remove();
        }
        this.meta.setWriteVersion(l);
        this.onVersionChange(l);
    }

    void storeNow() {
        ++this.currentVersion;
        this.storeNow(true);
    }

    private void storeNow(boolean bl) {
        try {
            int n = this.unsavedMemory;
            long l = this.currentVersion;
            assert (this.storeLock.isHeldByCurrentThread());
            this.fileStore.storeIt(this.collectChangedMapRoots(l), l, bl);
            this.saveNeeded = false;
            this.unsavedMemory = Math.max(0, this.unsavedMemory - n);
        }
        catch (MVStoreException mVStoreException) {
            this.panic(mVStoreException);
        }
        catch (Throwable throwable) {
            this.panic(DataUtils.newMVStoreException(3, "{0}", throwable.toString(), throwable));
        }
    }

    private ArrayList<Page<?, ?>> collectChangedMapRoots(long l) {
        long l2 = l - 2L;
        ArrayList arrayList = new ArrayList();
        Object object = this.maps.values().iterator();
        while (object.hasNext()) {
            MVMap<?, ?> mVMap = object.next();
            RootReference<?, ?> rootReference = mVMap.setWriteVersion(l);
            if (rootReference == null) {
                object.remove();
                continue;
            }
            if (mVMap.getCreateVersion() >= l || mVMap.isVolatile() || !mVMap.hasChangesSince(l2)) continue;
            assert (rootReference.version <= l) : rootReference.version + " > " + l;
            arrayList.add(rootReference.root);
        }
        object = this.meta.setWriteVersion(l);
        if (this.meta.hasChangesSince(l2) || this.metaChanged) {
            assert (object != null && ((RootReference)object).version <= l) : object == null ? "null" : ((RootReference)object).version + " > " + l;
            arrayList.add(((RootReference)object).root);
        }
        return arrayList;
    }

    public long getTimeAbsolute() {
        long l = System.currentTimeMillis();
        if (this.lastTimeAbsolute != 0L && l < this.lastTimeAbsolute) {
            l = this.lastTimeAbsolute;
        } else {
            this.lastTimeAbsolute = l;
        }
        return l;
    }

    public boolean hasUnsavedChanges() {
        if (this.metaChanged) {
            return true;
        }
        long l = this.currentVersion - 1L;
        for (MVMap<?, ?> mVMap : this.maps.values()) {
            if (mVMap.isClosed() || !mVMap.hasChangesSince(l)) continue;
            return true;
        }
        return this.fileStore != null && this.fileStore.hasChangesSince(l);
    }

    public void executeFilestoreOperation(Runnable runnable2) {
        this.storeLock.lock();
        try {
            this.checkNotClosed();
            this.fileStore.executeFileStoreOperation(runnable2);
        }
        catch (MVStoreException mVStoreException) {
            this.panic(mVStoreException);
        }
        catch (Throwable throwable) {
            this.panic(DataUtils.newMVStoreException(3, "{0}", throwable.toString(), throwable));
        }
        finally {
            this.unlockAndCheckPanicCondition();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    <R> R tryExecuteUnderStoreLock(Callable<R> callable) throws InterruptedException {
        R r = null;
        if (this.storeLock.tryLock(10L, TimeUnit.MILLISECONDS)) {
            try {
                r = callable.call();
            }
            catch (MVStoreException mVStoreException) {
                this.panic(mVStoreException);
            }
            catch (Throwable throwable) {
                this.panic(DataUtils.newMVStoreException(3, "{0}", throwable.toString(), throwable));
            }
            finally {
                this.unlockAndCheckPanicCondition();
            }
        }
        return r;
    }

    public void sync() {
        this.checkOpen();
        FileStore<?> fileStore = this.fileStore;
        if (fileStore != null) {
            fileStore.sync();
        }
    }

    public void compactFile(int n) {
        if (this.fileStore != null) {
            this.setRetentionTime(0);
            this.storeLock.lock();
            try {
                this.fileStore.compactStore(n);
            }
            finally {
                this.unlockAndCheckPanicCondition();
            }
        }
    }

    public boolean compact(int n, int n2) {
        this.checkOpen();
        return this.fileStore != null && this.fileStore.compact(n, n2);
    }

    public int getFillRate() {
        return this.fileStore.getFillRate();
    }

    <K, V> Page<K, V> readPage(MVMap<K, V> mVMap, long l) {
        this.checkNotClosed();
        return this.fileStore.readPage(mVMap, l);
    }

    void accountForRemovedPage(long l, long l2, boolean bl, int n) {
        this.fileStore.accountForRemovedPage(l, l2, bl, n);
    }

    Compressor getCompressorFast() {
        if (this.compressorFast == null) {
            this.compressorFast = new CompressLZF();
        }
        return this.compressorFast;
    }

    Compressor getCompressorHigh() {
        if (this.compressorHigh == null) {
            this.compressorHigh = new CompressDeflate();
        }
        return this.compressorHigh;
    }

    int getCompressionLevel() {
        return this.compressionLevel;
    }

    public int getKeysPerPage() {
        return this.keysPerPage;
    }

    public long getMaxPageSize() {
        return this.fileStore == null ? Long.MAX_VALUE : this.fileStore.getMaxPageSize();
    }

    public int getCacheSize() {
        return this.fileStore == null ? 0 : this.fileStore.getCacheSize();
    }

    public int getCacheSizeUsed() {
        return this.fileStore == null ? 0 : this.fileStore.getCacheSizeUsed();
    }

    public void setCacheSize(int n) {
        if (this.fileStore != null) {
            this.fileStore.setCacheSize(Math.max(1, n / 1024));
        }
    }

    public boolean isSpaceReused() {
        return this.fileStore.isSpaceReused();
    }

    public void setReuseSpace(boolean bl) {
        this.fileStore.setReuseSpace(bl);
    }

    public int getRetentionTime() {
        return this.fileStore == null ? 0 : this.fileStore.getRetentionTime();
    }

    public void setRetentionTime(int n) {
        if (this.fileStore != null) {
            this.fileStore.setRetentionTime(n);
        }
    }

    public boolean isVersioningRequired() {
        return this.fileStore != null && !this.fileStore.isReadOnly() || this.versionsToKeep > 0;
    }

    public void setVersionsToKeep(int n) {
        this.versionsToKeep = n;
    }

    public long getVersionsToKeep() {
        return this.versionsToKeep;
    }

    long getOldestVersionToKeep() {
        return Math.min(this.oldestVersionToKeep.get(), Math.max(this.currentVersion - (long)this.versionsToKeep, -1L));
    }

    private void setOldestVersionToKeep(long l) {
        long l2;
        boolean bl;
        while (!(bl = l <= (l2 = this.oldestVersionToKeep.get()) || this.oldestVersionToKeep.compareAndSet(l2, l))) {
        }
        assert (l <= this.currentVersion) : l + " <= " + this.currentVersion;
        if (this.oldestVersionTracker != null) {
            this.oldestVersionTracker.accept(l);
        }
    }

    public void setOldestVersionTracker(LongConsumer longConsumer) {
        this.oldestVersionTracker = longConsumer;
    }

    private boolean isKnownVersion(long l) {
        long l2 = this.getCurrentVersion();
        if (l > l2 || l < 0L) {
            return false;
        }
        if (l == l2) {
            return true;
        }
        return this.fileStore == null || this.fileStore.isKnownVersion(l);
    }

    public void registerUnsavedMemory(int n) {
        assert (this.fileStore != null);
        this.unsavedMemory += n;
        if (this.needStore()) {
            this.saveNeeded = true;
        }
    }

    void registerUnsavedMemoryAndCommitIfNeeded(int n) {
        this.registerUnsavedMemory(n);
        if (this.saveNeeded) {
            this.commit();
        }
    }

    void beforeWrite(MVMap<?, ?> mVMap) {
        if (this.saveNeeded && this.isOpenOrStopping() && (this.storeLock.isHeldByCurrentThread() || !mVMap.getRoot().isLockedByCurrentThread()) && this.fileStore.isRegularMap(mVMap)) {
            this.saveNeeded = false;
            if (this.autoCommitMemory > 0 && this.needStore()) {
                if (this.requireStore() && !mVMap.isSingleWriter()) {
                    this.commit(MVStore::requireStore);
                } else {
                    this.tryCommit(MVStore::needStore);
                }
            }
        }
    }

    private boolean requireStore() {
        return 3 * this.unsavedMemory > 4 * this.autoCommitMemory;
    }

    private boolean needStore() {
        return this.autoCommitMemory > 0 && this.fileStore.shouldSaveNow(this.unsavedMemory, this.autoCommitMemory);
    }

    public int getStoreVersion() {
        this.checkOpen();
        String string = this.meta.get("setting.storeVersion");
        return string == null ? 0 : DataUtils.parseHexInt(string);
    }

    public void setStoreVersion(int n) {
        this.storeLock.lock();
        try {
            this.checkOpen();
            this.markMetaChanged();
            this.meta.put("setting.storeVersion", Integer.toHexString(n));
        }
        finally {
            this.storeLock.unlock();
        }
    }

    public void rollback() {
        this.rollbackTo(this.currentVersion);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void rollbackTo(long l) {
        this.storeLock.lock();
        try {
            TxCounter txCounter;
            this.currentVersion = l;
            this.checkOpen();
            DataUtils.checkArgument(this.isKnownVersion(l), "Unknown version {0}", l);
            while ((txCounter = this.versions.peekLast()) != null && txCounter.version >= l) {
                this.versions.removeLast();
            }
            this.currentTxCounter = new TxCounter(l);
            if (this.oldestVersionToKeep.get() > l) {
                this.oldestVersionToKeep.set(l);
            }
            if (this.fileStore != null) {
                this.fileStore.rollbackTo(l);
            }
            if (!this.meta.rollbackRoot(l)) {
                this.meta.setRootPos(this.getRootPos(this.meta.getId()), l - 1L);
            }
            this.metaChanged = false;
            for (MVMap<?, ?> mVMap : new ArrayList(this.maps.values())) {
                int n = mVMap.getId();
                if (mVMap.getCreateVersion() >= l) {
                    mVMap.close();
                    this.maps.remove(n);
                    continue;
                }
                if (mVMap.rollbackRoot(l)) continue;
                mVMap.setRootPos(this.getRootPos(n), l);
            }
            this.onVersionChange(this.currentVersion);
            assert (!this.hasUnsavedChanges());
        }
        finally {
            this.unlockAndCheckPanicCondition();
        }
    }

    private long getRootPos(int n) {
        return this.fileStore == null ? 0L : this.fileStore.getRootPos(n);
    }

    public long getCurrentVersion() {
        return this.currentVersion;
    }

    void setCurrentVersion(long l) {
        this.currentVersion = l;
    }

    public FileStore<?> getFileStore() {
        return this.fileStore;
    }

    public Map<String, Object> getStoreHeader() {
        return this.fileStore.getStoreHeader();
    }

    private void checkOpen() {
        if (!this.isOpen()) {
            throw DataUtils.newMVStoreException(4, "This store is closed", this.panicException);
        }
    }

    private void checkNotClosed() {
        if (!this.isOpenOrStopping()) {
            throw DataUtils.newMVStoreException(4, "This store is closed", this.panicException);
        }
    }

    public void renameMap(MVMap<?, ?> mVMap, String string) {
        this.checkOpen();
        DataUtils.checkArgument(this.isRegularMap(mVMap), "Renaming the meta map is not allowed", new Object[0]);
        int n = mVMap.getId();
        String string2 = this.getMapName(n);
        if (string2 != null && !string2.equals(string)) {
            String string3 = Integer.toHexString(n);
            String string4 = this.meta.putIfAbsent("name." + string, string3);
            DataUtils.checkArgument(string4 == null || string4.equals(string3), "A map named {0} already exists", string);
            this.meta.put(MVMap.getMapKey(n), mVMap.asString(string));
            this.meta.remove("name." + string2);
            this.markMetaChanged();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void removeMap(MVMap<?, ?> mVMap) {
        this.storeLock.lock();
        try {
            this.checkOpen();
            DataUtils.checkArgument(this.isRegularMap(mVMap), "Removing the meta map is not allowed", new Object[0]);
            RootReference<?, ?> rootReference = mVMap.clearIt();
            mVMap.close();
            this.updateCounter += rootReference.updateCounter;
            this.updateAttemptCounter += rootReference.updateAttemptCounter;
            int n = mVMap.getId();
            String string = this.getMapName(n);
            if (this.meta.remove(MVMap.getMapKey(n)) != null) {
                this.markMetaChanged();
            }
            if (this.meta.remove("name." + string) != null) {
                this.markMetaChanged();
            }
            if (!this.isVersioningRequired()) {
                this.maps.remove(n);
            }
        }
        finally {
            this.storeLock.unlock();
        }
    }

    void deregisterMapRoot(int n) {
        if (this.fileStore != null && this.fileStore.deregisterMapRoot(n)) {
            this.markMetaChanged();
        }
    }

    public void removeMap(String string) {
        int n = this.getMapId(string);
        if (n > 0) {
            MVMap mVMap = this.getMap(n);
            if (mVMap == null) {
                mVMap = this.openMap(string, MVStoreTool.getGenericMapBuilder());
            }
            this.removeMap(mVMap);
        }
    }

    public String getMapName(int n) {
        String string = this.meta.get(MVMap.getMapKey(n));
        return string == null ? null : DataUtils.getMapName(string);
    }

    private int getMapId(String string) {
        String string2 = this.meta.get("name." + string);
        return string2 == null ? -1 : DataUtils.parseHexInt(string2);
    }

    public void populateInfo(BiConsumer<String, String> biConsumer) {
        biConsumer.accept("info.UPDATE_FAILURE_PERCENT", String.format(Locale.ENGLISH, "%.2f%%", 100.0 * this.getUpdateFailureRatio()));
        biConsumer.accept("info.LEAF_RATIO", Integer.toString(this.getLeafRatio()));
        if (this.fileStore != null) {
            this.fileStore.populateInfo(biConsumer);
        }
    }

    boolean handleException(Throwable throwable) {
        if (this.backgroundExceptionHandler != null) {
            block3: {
                try {
                    this.backgroundExceptionHandler.uncaughtException(Thread.currentThread(), throwable);
                }
                catch (Throwable throwable2) {
                    if (throwable == throwable2) break block3;
                    throwable.addSuppressed(throwable2);
                }
            }
            return true;
        }
        return false;
    }

    boolean isOpen() {
        return this.state == 0;
    }

    public boolean isClosed() {
        if (this.isOpen()) {
            return false;
        }
        this.storeLock.lock();
        try {
            boolean bl = this.state == 2;
            return bl;
        }
        finally {
            this.storeLock.unlock();
        }
    }

    private boolean isOpenOrStopping() {
        return this.state <= 1;
    }

    public void setAutoCommitDelay(int n) {
        if (this.fileStore != null) {
            this.fileStore.setAutoCommitDelay(n);
        }
    }

    public int getAutoCommitDelay() {
        return this.fileStore == null ? 0 : this.fileStore.getAutoCommitDelay();
    }

    public int getAutoCommitMemory() {
        return this.autoCommitMemory;
    }

    public int getUnsavedMemory() {
        return this.unsavedMemory;
    }

    public boolean isReadOnly() {
        return this.fileStore != null && this.fileStore.isReadOnly();
    }

    private int getLeafRatio() {
        return (int)(this.leafCount * 100L / Math.max(1L, this.leafCount + this.nonLeafCount));
    }

    private double getUpdateFailureRatio() {
        long l = this.updateCounter;
        long l2 = this.updateAttemptCounter;
        RootReference<String, String> rootReference = this.meta.getRoot();
        l += rootReference.updateCounter;
        l2 += rootReference.updateAttemptCounter;
        for (MVMap<?, ?> mVMap : this.maps.values()) {
            RootReference<?, ?> rootReference2 = mVMap.getRoot();
            l += rootReference2.updateCounter;
            l2 += rootReference2.updateAttemptCounter;
        }
        return l2 == 0L ? 0.0 : 1.0 - (double)l / (double)l2;
    }

    public TxCounter registerVersionUsage() {
        TxCounter txCounter;
        while ((txCounter = this.currentTxCounter).incrementAndGet() <= 0) {
            assert (txCounter != this.currentTxCounter) : txCounter;
            txCounter.decrementAndGet();
        }
        return txCounter;
    }

    public void deregisterVersionUsage(TxCounter txCounter) {
        if (this.decrementVersionUsageCounter(txCounter)) {
            if (this.storeLock.isHeldByCurrentThread()) {
                this.dropUnusedVersions();
            } else if (this.storeLock.tryLock()) {
                try {
                    this.dropUnusedVersions();
                }
                finally {
                    this.storeLock.unlock();
                }
            }
        }
    }

    public boolean decrementVersionUsageCounter(TxCounter txCounter) {
        return txCounter != null && txCounter.decrementAndGet() <= 0;
    }

    void onVersionChange(long l) {
        this.metaChanged = false;
        TxCounter txCounter = this.currentTxCounter;
        assert (txCounter.get() >= 0);
        this.versions.add(txCounter);
        this.currentTxCounter = new TxCounter(l);
        txCounter.decrementAndGet();
        this.dropUnusedVersions();
    }

    private void dropUnusedVersions() {
        TxCounter txCounter;
        while ((txCounter = this.versions.peek()) != null && txCounter.get() < 0) {
            this.versions.poll();
        }
        long l = (txCounter != null ? txCounter : this.currentTxCounter).version;
        this.setOldestVersionToKeep(l);
    }

    public void countNewPage(boolean bl) {
        if (bl) {
            ++this.leafCount;
        } else {
            ++this.nonLeafCount;
        }
    }

    public static final class Builder {
        private final HashMap<String, Object> config;

        private Builder(HashMap<String, Object> hashMap) {
            this.config = hashMap;
        }

        public Builder() {
            this.config = new HashMap();
        }

        private Builder set(String string, Object object) {
            this.config.put(string, object);
            return this;
        }

        public Builder autoCommitDisabled() {
            return this.set("autoCommitDelay", 0);
        }

        public Builder autoCommitBufferSize(int n) {
            return this.set("autoCommitBufferSize", n);
        }

        public Builder autoCompactFillRate(int n) {
            return this.set("autoCompactFillRate", n);
        }

        public Builder fileName(String string) {
            return this.set("fileName", string);
        }

        public Builder encryptionKey(char[] cArray) {
            return this.set("encryptionKey", cArray);
        }

        public Builder readOnly() {
            return this.set("readOnly", 1);
        }

        public Builder keysPerPage(int n) {
            return this.set("keysPerPage", n);
        }

        public Builder recoveryMode() {
            return this.set("recoveryMode", 1);
        }

        public Builder cacheSize(int n) {
            return this.set("cacheSize", n);
        }

        public Builder cacheConcurrency(int n) {
            return this.set("cacheConcurrency", n);
        }

        public Builder compress() {
            return this.set("compress", 1);
        }

        public Builder compressHigh() {
            return this.set("compress", 2);
        }

        public Builder pageSplitSize(int n) {
            return this.set("pageSplitSize", n);
        }

        public Builder backgroundExceptionHandler(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
            return this.set("backgroundExceptionHandler", uncaughtExceptionHandler);
        }

        public Builder fileStore(FileStore<?> fileStore) {
            return this.set("fileStore", fileStore);
        }

        public Builder adoptFileStore(FileStore fileStore) {
            this.set("fileStoreIsAdopted", true);
            return this.set("fileStore", fileStore);
        }

        public MVStore open() {
            return new MVStore(this.config);
        }

        public String toString() {
            return DataUtils.appendMap(new StringBuilder(), this.config).toString();
        }

        public static Builder fromString(String string) {
            return new Builder(DataUtils.parseMap(string));
        }
    }

    public static final class TxCounter {
        public final long version;
        private volatile int counter;
        private static final AtomicIntegerFieldUpdater<TxCounter> counterUpdater = AtomicIntegerFieldUpdater.newUpdater(TxCounter.class, "counter");

        TxCounter(long l) {
            this.version = l;
        }

        int get() {
            return this.counter;
        }

        int incrementAndGet() {
            return counterUpdater.incrementAndGet(this);
        }

        int decrementAndGet() {
            return counterUpdater.decrementAndGet(this);
        }

        public String toString() {
            return "v=" + this.version + " / cnt=" + this.counter;
        }
    }
}

