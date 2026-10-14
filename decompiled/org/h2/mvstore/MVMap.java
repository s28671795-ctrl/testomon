/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore;

import java.util.AbstractList;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.h2.mvstore.Cursor;
import org.h2.mvstore.CursorPos;
import org.h2.mvstore.DataUtils;
import org.h2.mvstore.MVStore;
import org.h2.mvstore.Page;
import org.h2.mvstore.RootReference;
import org.h2.mvstore.type.DataType;
import org.h2.mvstore.type.ObjectDataType;
import org.h2.util.MemoryEstimator;

public class MVMap<K, V>
extends AbstractMap<K, V>
implements ConcurrentMap<K, V> {
    public final MVStore store;
    private final AtomicReference<RootReference<K, V>> root;
    private final int id;
    private final long createVersion;
    private final DataType<K> keyType;
    private final DataType<V> valueType;
    private final int keysPerPage;
    private final boolean singleWriter;
    private final K[] keysBuffer;
    private final V[] valuesBuffer;
    private final Object lock = new Object();
    private volatile boolean notificationRequested;
    private volatile boolean closed;
    private boolean readOnly;
    private boolean isVolatile;
    private final AtomicLong avgKeySize;
    private final AtomicLong avgValSize;

    protected MVMap(Map<String, Object> map, DataType<K> dataType, DataType<V> dataType2) {
        this((MVStore)map.get("store"), dataType, dataType2, DataUtils.readHexInt(map, "id", 0), DataUtils.readHexLong(map, "createVersion", 0L), new AtomicReference<RootReference<K, V>>(), ((MVStore)map.get("store")).getKeysPerPage(), map.containsKey("singleWriter") && (Boolean)map.get("singleWriter") != false);
        this.setInitialRoot(this.createEmptyLeaf(), this.store.getCurrentVersion());
    }

    protected MVMap(MVMap<K, V> mVMap) {
        this(mVMap.store, mVMap.keyType, mVMap.valueType, mVMap.id, mVMap.createVersion, new AtomicReference<RootReference<K, V>>(mVMap.root.get()), mVMap.keysPerPage, mVMap.singleWriter);
    }

    MVMap(MVStore mVStore, int n, DataType<K> dataType, DataType<V> dataType2) {
        this(mVStore, dataType, dataType2, n, 0L, new AtomicReference<RootReference<K, V>>(), mVStore.getKeysPerPage(), false);
        this.setInitialRoot(this.createEmptyLeaf(), mVStore.getCurrentVersion());
    }

    private MVMap(MVStore mVStore, DataType<K> dataType, DataType<V> dataType2, int n, long l, AtomicReference<RootReference<K, V>> atomicReference, int n2, boolean bl) {
        this.store = mVStore;
        this.id = n;
        this.createVersion = l;
        this.keyType = dataType;
        this.valueType = dataType2;
        this.root = atomicReference;
        this.keysPerPage = n2;
        this.keysBuffer = bl ? dataType.createStorage(n2) : null;
        this.valuesBuffer = bl ? dataType2.createStorage(n2) : null;
        this.singleWriter = bl;
        this.avgKeySize = dataType.isMemoryEstimationAllowed() ? new AtomicLong() : null;
        this.avgValSize = dataType2.isMemoryEstimationAllowed() ? new AtomicLong() : null;
    }

    protected MVMap<K, V> cloneIt() {
        return new MVMap<K, V>(this);
    }

    static String getMapRootKey(int n) {
        return "root." + Integer.toHexString(n);
    }

    static String getMapKey(int n) {
        return "map." + Integer.toHexString(n);
    }

    @Override
    public V put(K k, V v) {
        DataUtils.checkArgument(v != null, "The value may not be null", new Object[0]);
        return (V)this.operate(k, v, DecisionMaker.PUT);
    }

    public final K firstKey() {
        return this.getFirstLast(true);
    }

    public final K lastKey() {
        return this.getFirstLast(false);
    }

    public final K getKey(long l) {
        if (l < 0L || l >= this.sizeAsLong()) {
            return null;
        }
        Page<K, V> page = this.getRootPage();
        long l2 = 0L;
        while (true) {
            long l3;
            int n;
            if (page.isLeaf()) {
                if (l >= l2 + (long)page.getKeyCount()) {
                    return null;
                }
                K k = page.getKey((int)(l - l2));
                return k;
            }
            int n2 = this.getChildPageCount(page);
            for (n = 0; n < n2 && l >= (l3 = page.getCounts(n)) + l2; ++n) {
                l2 += l3;
            }
            if (n == n2) {
                return null;
            }
            page = page.getChildPage(n);
        }
    }

    public final List<K> keyList() {
        return new AbstractList<K>(){

            @Override
            public K get(int n) {
                return MVMap.this.getKey(n);
            }

            @Override
            public int size() {
                return MVMap.this.size();
            }

            @Override
            public int indexOf(Object object) {
                return (int)MVMap.this.getKeyIndex(object);
            }
        };
    }

    public final long getKeyIndex(K k) {
        Page<K, V> page = this.getRootPage();
        if (page.getTotalCount() == 0L) {
            return -1L;
        }
        long l = 0L;
        while (true) {
            int n = page.binarySearch(k);
            if (page.isLeaf()) {
                if (n < 0) {
                    l = -l;
                }
                return l + (long)n;
            }
            if (n++ < 0) {
                n = -n;
            }
            for (int i = 0; i < n; ++i) {
                l += page.getCounts(i);
            }
            page = page.getChildPage(n);
        }
    }

    private K getFirstLast(boolean bl) {
        Page<K, V> page = this.getRootPage();
        return this.getFirstLast(page, bl);
    }

    private K getFirstLast(Page<K, V> page, boolean bl) {
        if (page.getTotalCount() == 0L) {
            return null;
        }
        while (!page.isLeaf()) {
            page = page.getChildPage(bl ? 0 : this.getChildPageCount(page) - 1);
        }
        return page.getKey(bl ? 0 : page.getKeyCount() - 1);
    }

    public final K higherKey(K k) {
        return this.getMinMax(k, false, true);
    }

    public final K higherKey(RootReference<K, V> rootReference, K k) {
        return this.getMinMax(rootReference, k, false, true);
    }

    public final K ceilingKey(K k) {
        return this.getMinMax(k, false, false);
    }

    public final K floorKey(K k) {
        return this.getMinMax(k, true, false);
    }

    public final K lowerKey(K k) {
        return this.getMinMax(k, true, true);
    }

    public final K lowerKey(RootReference<K, V> rootReference, K k) {
        return this.getMinMax(rootReference, k, true, true);
    }

    private K getMinMax(K k, boolean bl, boolean bl2) {
        return this.getMinMax(this.flushAndGetRoot(), k, bl, bl2);
    }

    private K getMinMax(RootReference<K, V> rootReference, K k, boolean bl, boolean bl2) {
        return this.getMinMax(rootReference.root, k, bl, bl2);
    }

    private K getMinMax(Page<K, V> page, K k, boolean bl, boolean bl2) {
        int n = page.binarySearch(k);
        if (page.isLeaf()) {
            if (n < 0) {
                n = -n - (bl ? 2 : 1);
            } else if (bl2) {
                n += bl ? -1 : 1;
            }
            if (n < 0 || n >= page.getKeyCount()) {
                return null;
            }
            return page.getKey(n);
        }
        if (n++ < 0) {
            n = -n;
        }
        while (n >= 0 && n < this.getChildPageCount(page)) {
            K k2 = this.getMinMax(page.getChildPage(n), k, bl, bl2);
            if (k2 != null) {
                return k2;
            }
            n += bl ? -1 : 1;
        }
        return null;
    }

    @Override
    public final V get(Object object) {
        return this.get(this.getRootPage(), object);
    }

    public V get(Page<K, V> page, K k) {
        return Page.get(page, k);
    }

    @Override
    public final boolean containsKey(Object object) {
        return this.get(object) != null;
    }

    @Override
    public void clear() {
        this.clearIt();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    RootReference<K, V> clearIt() {
        Page<K, V> page = this.createEmptyLeaf();
        int n = 0;
        RootReference<K, V> rootReference;
        while ((rootReference = this.flushAndGetRoot()).getTotalCount() != 0L) {
            boolean bl = rootReference.isLockedByCurrentThread();
            if (!bl) {
                if (n++ == 0) {
                    this.beforeWrite();
                } else if (n > 3 || rootReference.isLocked()) {
                    rootReference = this.lockRoot(rootReference, n);
                    bl = true;
                }
            }
            Page page2 = rootReference.root;
            long l = rootReference.version;
            try {
                if (!bl && (rootReference = rootReference.updateRootPage(page, n)) == null) continue;
                if (this.isPersistent()) {
                    this.registerUnsavedMemory(page2.removeAllRecursive(l));
                }
                page2 = page;
                RootReference<K, V> rootReference2 = rootReference;
                return rootReference2;
            }
            finally {
                if (!bl) continue;
                this.unlockRoot(page2);
                continue;
            }
            break;
        }
        return rootReference;
    }

    protected final void registerUnsavedMemory(int n) {
        if (this.isPersistent()) {
            this.store.registerUnsavedMemory(n);
        }
    }

    final void close() {
        this.closed = true;
    }

    public final boolean isClosed() {
        return this.closed;
    }

    @Override
    public V remove(Object object) {
        return (V)this.operate(object, null, DecisionMaker.REMOVE);
    }

    @Override
    public final V putIfAbsent(K k, V v) {
        return (V)this.operate(k, v, DecisionMaker.IF_ABSENT);
    }

    @Override
    public boolean remove(Object object, Object object2) {
        EqualsDecisionMaker<Object> equalsDecisionMaker = new EqualsDecisionMaker<Object>(this.valueType, object2);
        this.operate(object, null, equalsDecisionMaker);
        return equalsDecisionMaker.getDecision() != Decision.ABORT;
    }

    static <X> boolean areValuesEqual(DataType<X> dataType, X x, X x2) {
        return x == x2 || x != null && x2 != null && dataType.compare(x, x2) == 0;
    }

    @Override
    public final boolean replace(K k, V v, V v2) {
        boolean bl;
        EqualsDecisionMaker<V> equalsDecisionMaker = new EqualsDecisionMaker<V>(this.valueType, v);
        V v3 = this.operate(k, v2, equalsDecisionMaker);
        boolean bl2 = bl = equalsDecisionMaker.getDecision() != Decision.ABORT;
        assert (!bl || MVMap.areValuesEqual(this.valueType, v, v3)) : v + " != " + v3;
        return bl;
    }

    @Override
    public final V replace(K k, V v) {
        return (V)this.operate(k, v, DecisionMaker.IF_PRESENT);
    }

    final int compare(K k, K k2) {
        return this.keyType.compare(k, k2);
    }

    public final DataType<K> getKeyType() {
        return this.keyType;
    }

    public final DataType<V> getValueType() {
        return this.valueType;
    }

    boolean isSingleWriter() {
        return this.singleWriter;
    }

    final Page<K, V> readPage(long l) {
        return this.store.readPage(this, l);
    }

    final void setRootPos(long l, long l2) {
        Page<K, V> page = this.readOrCreateRootPage(l);
        if (page.map != this) {
            assert (this.id == page.map.id);
            page = page.copy(this, false);
        }
        this.setInitialRoot(page, l2 - 1L);
        this.setWriteVersion(l2);
    }

    private Page<K, V> readOrCreateRootPage(long l) {
        Page<K, V> page = l == 0L ? this.createEmptyLeaf() : this.readPage(l);
        return page;
    }

    public final Iterator<K> keyIterator(K k) {
        return this.cursor(k, null, false);
    }

    public final Iterator<K> keyIteratorReverse(K k) {
        return this.cursor(k, null, true);
    }

    final boolean rewritePage(long l) {
        Page<K, V> page = this.readPage(l);
        if (page.getKeyCount() == 0) {
            return true;
        }
        assert (page.isSaved());
        K k = page.getKey(0);
        if (!this.isClosed()) {
            boolean bl;
            RewriteDecisionMaker rewriteDecisionMaker = new RewriteDecisionMaker(page.getPos());
            Object var6_5 = this.operate(k, null, rewriteDecisionMaker);
            boolean bl2 = bl = rewriteDecisionMaker.getDecision() != Decision.ABORT;
            assert (!bl || var6_5 != null);
            return bl;
        }
        return false;
    }

    public final Cursor<K, V> cursor(K k) {
        return this.cursor(k, null, false);
    }

    public final Cursor<K, V> cursor(K k, K k2, boolean bl) {
        return this.cursor(this.flushAndGetRoot(), k, k2, bl);
    }

    public Cursor<K, V> cursor(RootReference<K, V> rootReference, K k, K k2, boolean bl) {
        return new Cursor<K, V>(rootReference, k, k2, bl);
    }

    @Override
    public final Set<Map.Entry<K, V>> entrySet() {
        final RootReference<K, V> rootReference = this.flushAndGetRoot();
        return new AbstractSet<Map.Entry<K, V>>(){

            @Override
            public Iterator<Map.Entry<K, V>> iterator() {
                final Cursor cursor = MVMap.this.cursor(rootReference, null, null, false);
                return new Iterator<Map.Entry<K, V>>(){

                    @Override
                    public boolean hasNext() {
                        return cursor.hasNext();
                    }

                    @Override
                    public Map.Entry<K, V> next() {
                        Object k = cursor.next();
                        return new AbstractMap.SimpleImmutableEntry(k, cursor.getValue());
                    }
                };
            }

            @Override
            public int size() {
                return MVMap.this.size();
            }

            @Override
            public boolean contains(Object object) {
                return MVMap.this.containsKey(object);
            }
        };
    }

    @Override
    public Set<K> keySet() {
        final RootReference<K, V> rootReference = this.flushAndGetRoot();
        return new AbstractSet<K>(){

            @Override
            public Iterator<K> iterator() {
                return MVMap.this.cursor(rootReference, null, null, false);
            }

            @Override
            public int size() {
                return MVMap.this.size();
            }

            @Override
            public boolean contains(Object object) {
                return MVMap.this.containsKey(object);
            }
        };
    }

    public final String getName() {
        return this.store.getMapName(this.id);
    }

    public final MVStore getStore() {
        return this.store;
    }

    protected final boolean isPersistent() {
        return this.store.isPersistent() && !this.isVolatile;
    }

    public final int getId() {
        return this.id;
    }

    public final Page<K, V> getRootPage() {
        return this.flushAndGetRoot().root;
    }

    public RootReference<K, V> getRoot() {
        return this.root.get();
    }

    public RootReference<K, V> flushAndGetRoot() {
        RootReference<K, V> rootReference = this.getRoot();
        if (this.singleWriter && rootReference.getAppendCounter() > 0) {
            return this.flushAppendBuffer(rootReference, true);
        }
        return rootReference;
    }

    final void setInitialRoot(Page<K, V> page, long l) {
        this.root.set(new RootReference<K, V>(page, l));
    }

    final boolean compareAndSetRoot(RootReference<K, V> rootReference, RootReference<K, V> rootReference2) {
        return this.root.compareAndSet(rootReference, rootReference2);
    }

    final void rollbackTo(long l) {
        if (l > this.createVersion) {
            this.rollbackRoot(l);
        }
    }

    boolean rollbackRoot(long l) {
        RootReference rootReference;
        RootReference<K, V> rootReference2 = this.flushAndGetRoot();
        while (rootReference2.version >= l && (rootReference = rootReference2.previous) != null) {
            if (!this.root.compareAndSet(rootReference2, rootReference)) continue;
            rootReference2 = rootReference;
            this.closed = false;
        }
        this.setWriteVersion(l);
        return rootReference2.version < l;
    }

    protected static <K, V> boolean updateRoot(RootReference<K, V> rootReference, Page<K, V> page, int n) {
        return rootReference.updateRootPage(page, n) != null;
    }

    private void removeUnusedOldVersions(RootReference<K, V> rootReference) {
        rootReference.removeUnusedOldVersions(this.store.getOldestVersionToKeep());
    }

    public final boolean isReadOnly() {
        return this.readOnly;
    }

    public final void setVolatile(boolean bl) {
        this.isVolatile = bl;
    }

    public final boolean isVolatile() {
        return this.isVolatile;
    }

    protected final void beforeWrite() {
        assert (!this.getRoot().isLockedByCurrentThread()) : this.getRoot();
        if (this.closed) {
            int n = this.getId();
            String string = this.store.getMapName(n);
            throw DataUtils.newMVStoreException(4, "Map {0}({1}) is closed. {2}", string, n, this.store.getPanicException());
        }
        if (this.readOnly) {
            throw DataUtils.newUnsupportedOperationException("This map is read-only");
        }
        this.store.beforeWrite(this);
    }

    @Override
    public final int hashCode() {
        return this.id;
    }

    @Override
    public final boolean equals(Object object) {
        return this == object;
    }

    @Override
    public final int size() {
        long l = this.sizeAsLong();
        return l > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int)l;
    }

    public final long sizeAsLong() {
        return this.getRoot().getTotalCount();
    }

    @Override
    public boolean isEmpty() {
        return this.sizeAsLong() == 0L;
    }

    final long getCreateVersion() {
        return this.createVersion;
    }

    public final MVMap<K, V> openVersion(long l) {
        RootReference rootReference;
        if (this.readOnly) {
            throw DataUtils.newUnsupportedOperationException("This map is read-only; need to call the method on the writable map");
        }
        DataUtils.checkArgument(l >= this.createVersion, "Unknown version {0}; this map was created in version is {1}", l, this.createVersion);
        RootReference<K, V> rootReference2 = this.flushAndGetRoot();
        this.removeUnusedOldVersions(rootReference2);
        while ((rootReference = rootReference2.previous) != null && rootReference.version >= l) {
            rootReference2 = rootReference;
        }
        if (rootReference == null && l < this.store.getOldestVersionToKeep()) {
            throw DataUtils.newIllegalArgumentException("Unknown version {0}", l);
        }
        MVMap mVMap = this.openReadOnly(rootReference2.root, l);
        assert (mVMap.getVersion() <= l) : mVMap.getVersion() + " <= " + l;
        return mVMap;
    }

    final MVMap<K, V> openReadOnly(long l, long l2) {
        Page<K, V> page = this.readOrCreateRootPage(l);
        return this.openReadOnly(page, l2);
    }

    private MVMap<K, V> openReadOnly(Page<K, V> page, long l) {
        MVMap<K, V> mVMap = this.cloneIt();
        mVMap.readOnly = true;
        mVMap.setInitialRoot(page, l);
        return mVMap;
    }

    public final long getVersion() {
        return this.getRoot().getVersion();
    }

    final boolean hasChangesSince(long l) {
        return this.getRoot().hasChangesSince(l, this.isPersistent());
    }

    protected int getChildPageCount(Page<K, V> page) {
        return page.getRawChildPageCount();
    }

    public String getType() {
        return null;
    }

    protected String asString(String string) {
        String string2;
        StringBuilder stringBuilder = new StringBuilder();
        if (string != null) {
            DataUtils.appendMap(stringBuilder, "name", string);
        }
        if (this.createVersion != 0L) {
            DataUtils.appendMap(stringBuilder, "createVersion", this.createVersion);
        }
        if ((string2 = this.getType()) != null) {
            DataUtils.appendMap(stringBuilder, "type", string2);
        }
        return stringBuilder.toString();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    final RootReference<K, V> setWriteVersion(long l) {
        int n = 0;
        while (true) {
            RootReference<K, V> rootReference = this.flushAndGetRoot();
            if (rootReference.version >= l) {
                return rootReference;
            }
            if (this.isClosed() && rootReference.getVersion() + 1L < this.store.getOldestVersionToKeep()) {
                this.store.deregisterMapRoot(this.id);
                return null;
            }
            RootReference<K, V> rootReference2 = null;
            if (++n > 3 || rootReference.isLocked()) {
                rootReference2 = this.lockRoot(rootReference, n);
                rootReference = this.flushAndGetRoot();
            }
            try {
                if ((rootReference = rootReference.tryUnlockAndUpdateVersion(l, n)) == null) continue;
                rootReference2 = null;
                this.removeUnusedOldVersions(rootReference);
                RootReference<K, V> rootReference3 = rootReference;
                return rootReference3;
            }
            finally {
                if (rootReference2 == null) continue;
                this.unlockRoot();
                continue;
            }
            break;
        }
    }

    protected Page<K, V> createEmptyLeaf() {
        return Page.createEmptyLeaf(this);
    }

    protected Page<K, V> createEmptyNode() {
        return Page.createEmptyNode(this);
    }

    final void copyFrom(MVMap<K, V> mVMap) {
        MVStore.TxCounter txCounter = this.store.registerVersionUsage();
        try {
            this.beforeWrite();
            this.copy(mVMap.getRootPage(), null, 0);
        }
        finally {
            this.store.deregisterVersionUsage(txCounter);
        }
    }

    private void copy(Page<K, V> page, Page<K, V> page2, int n) {
        Page<K, V> page3 = page.copy(this, true);
        if (page2 == null) {
            this.setInitialRoot(page3, -1L);
        } else {
            page2.setChild(n, page3);
        }
        if (!page.isLeaf()) {
            for (int i = 0; i < this.getChildPageCount(page3); ++i) {
                if (page.getChildPagePos(i) == 0L) continue;
                this.copy(page.getChildPage(i), page3, i);
            }
            page3.setComplete();
        }
        this.store.registerUnsavedMemoryAndCommitIfNeeded(page3.getMemory());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private RootReference<K, V> flushAppendBuffer(RootReference<K, V> rootReference, boolean bl) {
        boolean bl2;
        boolean bl3 = bl2 = rootReference.isLockedByCurrentThread();
        int n = this.store.getKeysPerPage();
        try {
            int n2;
            int n3;
            IntValueHolder intValueHolder = new IntValueHolder();
            int n4 = 0;
            int n5 = n3 = bl ? 0 : n - 1;
            while ((n2 = rootReference.getAppendCounter()) > n3) {
                Object object;
                Object object2;
                if (!bl3) {
                    if ((rootReference = this.tryLock(rootReference, ++n4)) == null) {
                        rootReference = this.getRoot();
                        continue;
                    }
                    bl3 = true;
                }
                Page page = rootReference.root;
                long l = rootReference.version;
                CursorPos cursorPos = page.getAppendCursorPos(null);
                assert (cursorPos != null);
                assert (cursorPos.index < 0) : cursorPos.index;
                int n6 = -cursorPos.index - 1;
                assert (n6 == cursorPos.page.getKeyCount()) : n6 + " != " + cursorPos.page.getKeyCount();
                Page page2 = cursorPos.page;
                CursorPos cursorPos2 = cursorPos;
                cursorPos = cursorPos.parent;
                int n7 = 0;
                Page page3 = null;
                int n8 = n - page2.getKeyCount();
                if (n8 > 0) {
                    page2 = page2.copy();
                    if (n2 <= n8) {
                        page2.expand(n2, this.keysBuffer, this.valuesBuffer);
                    } else {
                        page2.expand(n8, this.keysBuffer, this.valuesBuffer);
                        n2 -= n8;
                        if (bl) {
                            object2 = page2.createKeyStorage(n2);
                            object = page2.createValueStorage(n2);
                            System.arraycopy(this.keysBuffer, n8, object2, 0, n2);
                            if (this.valuesBuffer != null) {
                                System.arraycopy(this.valuesBuffer, n8, object, 0, n2);
                            }
                            page3 = Page.createLeaf(this, object2, object, 0);
                        } else {
                            System.arraycopy(this.keysBuffer, n8, this.keysBuffer, 0, n2);
                            if (this.valuesBuffer != null) {
                                System.arraycopy(this.valuesBuffer, n8, this.valuesBuffer, 0, n2);
                            }
                            n7 = n2;
                        }
                    }
                } else {
                    cursorPos2 = cursorPos2.parent;
                    page3 = Page.createLeaf(this, Arrays.copyOf(this.keysBuffer, n2), this.valuesBuffer == null ? null : Arrays.copyOf(this.valuesBuffer, n2), 0);
                }
                intValueHolder.value = 0;
                if (page3 != null) {
                    assert (page3.map == this);
                    assert (page3.getKeyCount() > 0);
                    object2 = page3.getKey(0);
                    intValueHolder.value += page3.getMemory();
                    while (true) {
                        if (cursorPos == null) {
                            if (page2.getKeyCount() == 0) {
                                page2 = page3;
                                break;
                            }
                            object = page2.createKeyStorage(1);
                            object[0] = object2;
                            Page.PageReference<K, V>[] pageReferenceArray = Page.createRefStorage(2);
                            pageReferenceArray[0] = new Page.PageReference(page2);
                            pageReferenceArray[1] = new Page.PageReference(page3);
                            intValueHolder.value += page2.getMemory();
                            page2 = Page.createNode(this, object, pageReferenceArray, page2.getTotalCount() + page3.getTotalCount(), 0);
                            break;
                        }
                        object = page2;
                        page2 = cursorPos.page;
                        n6 = cursorPos.index;
                        cursorPos = cursorPos.parent;
                        page2 = page2.copy();
                        page2.setChild(n6, page3);
                        page2.insertNode(n6, object2, object);
                        n2 = page2.getKeyCount();
                        int n9 = n2 - (page2.isLeaf() ? 1 : 2);
                        if (n2 <= n && ((long)page2.getMemory() < this.store.getMaxPageSize() || n9 <= 0)) break;
                        object2 = page2.getKey(n9);
                        page3 = page2.split(n9);
                        intValueHolder.value += page2.getMemory() + page3.getMemory();
                    }
                }
                if ((rootReference = rootReference.updatePageAndLockedStatus(page2 = MVMap.replacePage(cursorPos, page2, intValueHolder), bl2 || this.isPersistent(), n7)) != null) {
                    boolean bl4 = bl3 = bl2 || this.isPersistent();
                    if (this.isPersistent() && cursorPos2 != null) {
                        this.registerUnsavedMemory(intValueHolder.value + cursorPos2.processRemovalInfo(l));
                    }
                    assert (rootReference.getAppendCounter() <= n3);
                    break;
                }
                rootReference = this.getRoot();
            }
        }
        finally {
            if (bl3 && !bl2) {
                rootReference = this.unlockRoot();
            }
        }
        return rootReference;
    }

    private static <K, V> Page<K, V> replacePage(CursorPos<K, V> cursorPos, Page<K, V> page, IntValueHolder intValueHolder) {
        int n;
        int n2 = n = page.isSaved() ? 0 : page.getMemory();
        while (cursorPos != null) {
            Page page2 = cursorPos.page;
            if (page2.getKeyCount() > 0) {
                Page<K, V> page3 = page;
                page = page2.copy();
                page.setChild(cursorPos.index, page3);
                n += page.getMemory();
            }
            cursorPos = cursorPos.parent;
        }
        intValueHolder.value += n;
        return page;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void append(K k, V v) {
        if (this.singleWriter) {
            this.beforeWrite();
            RootReference<K, V> rootReference = this.lockRoot(this.getRoot(), 1);
            int n = rootReference.getAppendCounter();
            try {
                if (n >= this.keysPerPage) {
                    rootReference = this.flushAppendBuffer(rootReference, false);
                    n = rootReference.getAppendCounter();
                    assert (n < this.keysPerPage);
                }
                this.keysBuffer[n] = k;
                if (this.valuesBuffer != null) {
                    this.valuesBuffer[n] = v;
                }
                ++n;
            }
            finally {
                this.unlockRoot(n);
            }
        } else {
            this.put(k, v);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void trimLast() {
        if (this.singleWriter) {
            boolean bl;
            RootReference<K, V> rootReference = this.getRoot();
            int n = rootReference.getAppendCounter();
            boolean bl2 = bl = n == 0;
            if (!bl) {
                rootReference = this.lockRoot(rootReference, 1);
                try {
                    n = rootReference.getAppendCounter();
                    boolean bl3 = bl = n == 0;
                    if (!bl) {
                        --n;
                    }
                }
                finally {
                    this.unlockRoot(n);
                }
            }
            if (bl) {
                Page page = rootReference.root.getAppendCursorPos(null).page;
                assert (page.isLeaf());
                assert (page.getKeyCount() > 0);
                Object k = page.getKey(page.getKeyCount() - 1);
                this.remove(k);
            }
        } else {
            this.remove(this.lastKey());
        }
    }

    @Override
    public final String toString() {
        return this.asString(null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public V operate(K k, V v, DecisionMaker<? super V> decisionMaker) {
        IntValueHolder intValueHolder = new IntValueHolder();
        int n = 0;
        block16: while (true) {
            RootReference rootReference;
            boolean bl;
            if (!(bl = (rootReference = this.flushAndGetRoot()).isLockedByCurrentThread())) {
                if (n++ == 0) {
                    this.beforeWrite();
                }
                if (n > 3 || rootReference.isLocked()) {
                    rootReference = this.lockRoot(rootReference, n);
                    bl = true;
                }
            }
            Page page = rootReference.root;
            long l = rootReference.version;
            intValueHolder.value = 0;
            try {
                CursorPos cursorPos = CursorPos.traverseDown(page, k);
                if (!bl && rootReference != this.getRoot()) continue;
                Object object = cursorPos.page;
                int n2 = cursorPos.index;
                CursorPos cursorPos2 = cursorPos;
                cursorPos = cursorPos.parent;
                V v2 = n2 < 0 ? null : (V)((Page)object).getValue(n2);
                Decision decision = decisionMaker.decide(v2, v, cursorPos2);
                block8 : switch (decision) {
                    case REPEAT: {
                        decisionMaker.reset();
                        continue block16;
                    }
                    case ABORT: {
                        if (!bl && rootReference != this.getRoot()) {
                            decisionMaker.reset();
                            continue block16;
                        }
                        V v3 = v2;
                        return v3;
                    }
                    case REMOVE: {
                        int n3;
                        if (n2 < 0) {
                            if (!bl && rootReference != this.getRoot()) {
                                decisionMaker.reset();
                                continue block16;
                            }
                            V v4 = null;
                            return v4;
                        }
                        if (((Page)object).getTotalCount() == 1L && cursorPos != null) {
                            do {
                                object = cursorPos.page;
                                n2 = cursorPos.index;
                                cursorPos = cursorPos.parent;
                            } while ((n3 = ((Page)object).getKeyCount()) == 0 && cursorPos != null);
                            if (n3 <= 1) {
                                if (n3 == 1) {
                                    assert (n2 <= 1);
                                    object = ((Page)object).getChildPage(1 - n2);
                                    break;
                                }
                                object = Page.createEmptyLeaf(this);
                                break;
                            }
                        }
                        object = ((Page)object).copy();
                        ((Page)object).remove(n2);
                        break;
                    }
                    case PUT: {
                        int n3;
                        v = decisionMaker.selectValue(v2, v);
                        object = ((Page)object).copy();
                        if (n2 < 0) {
                            ((Page)object).insertLeaf(-n2 - 1, k, v);
                            while ((n3 = ((Page)object).getKeyCount()) > this.store.getKeysPerPage() || (long)((Page)object).getMemory() > this.store.getMaxPageSize() && n3 > (((Page)object).isLeaf() ? 1 : 2)) {
                                K[] KArray;
                                long l2 = ((Page)object).getTotalCount();
                                int n4 = n3 >> 1;
                                Object k2 = ((Page)object).getKey(n4);
                                Page page2 = ((Page)object).split(n4);
                                intValueHolder.value += ((Page)object).getMemory() + page2.getMemory();
                                if (cursorPos == null) {
                                    KArray = ((Page)object).createKeyStorage(1);
                                    KArray[0] = k2;
                                    Page.PageReference<K, V>[] pageReferenceArray = Page.createRefStorage(2);
                                    pageReferenceArray[0] = new Page.PageReference(object);
                                    pageReferenceArray[1] = new Page.PageReference(page2);
                                    object = Page.createNode(this, KArray, pageReferenceArray, l2, 0);
                                    break block8;
                                }
                                KArray = object;
                                object = cursorPos.page;
                                n2 = cursorPos.index;
                                cursorPos = cursorPos.parent;
                                object = ((Page)object).copy();
                                ((Page)object).setChild(n2, page2);
                                ((Page)object).insertNode(n2, k2, KArray);
                            }
                            break;
                        }
                        ((Page)object).setValue(n2, v);
                    }
                }
                page = MVMap.replacePage(cursorPos, object, intValueHolder);
                if (!bl && (rootReference = rootReference.updateRootPage(page, n)) == null) {
                    decisionMaker.reset();
                    continue;
                }
                if (this.isPersistent()) {
                    this.registerUnsavedMemory(intValueHolder.value + cursorPos2.processRemovalInfo(l));
                }
                V v5 = v2;
                return v5;
            }
            finally {
                if (!bl) continue;
                this.unlockRoot(page);
                continue;
            }
            break;
        }
    }

    private RootReference<K, V> lockRoot(RootReference<K, V> rootReference, int n) {
        RootReference<K, V> rootReference2;
        while ((rootReference2 = this.tryLock(rootReference, n++)) == null) {
            rootReference = this.getRoot();
        }
        return rootReference2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected RootReference<K, V> tryLock(RootReference<K, V> rootReference, int n) {
        RootReference<K, V> rootReference2 = rootReference.tryLock(n);
        if (rootReference2 != null) {
            return rootReference2;
        }
        assert (!rootReference.isLockedByCurrentThread()) : rootReference;
        RootReference rootReference3 = rootReference.previous;
        int n2 = 1;
        if (rootReference3 != null) {
            long l = rootReference.updateAttemptCounter - rootReference3.updateAttemptCounter;
            assert (l >= 0L) : l;
            long l2 = rootReference.updateCounter - rootReference3.updateCounter;
            assert (l2 >= 0L) : l2;
            assert (l >= l2) : l + " >= " + l2;
            n2 += (int)((l + 1L) / (l2 + 1L));
        }
        if (n > 4) {
            if (n <= 12) {
                Thread.yield();
            } else {
                if (n <= 70 - 2 * n2) {
                    try {
                        Thread.sleep(n2);
                    }
                    catch (InterruptedException interruptedException) {
                        throw new RuntimeException(interruptedException);
                    }
                }
                Object object = this.lock;
                synchronized (object) {
                    this.notificationRequested = true;
                    try {
                        this.lock.wait(5L);
                    }
                    catch (InterruptedException interruptedException) {
                        // empty catch block
                    }
                }
            }
        }
        return null;
    }

    private RootReference<K, V> unlockRoot() {
        return this.unlockRoot(null, -1);
    }

    protected RootReference<K, V> unlockRoot(Page<K, V> page) {
        return this.unlockRoot(page, -1);
    }

    private void unlockRoot(int n) {
        this.unlockRoot(null, n);
    }

    private RootReference<K, V> unlockRoot(Page<K, V> page, int n) {
        RootReference<K, V> rootReference;
        RootReference<K, V> rootReference2;
        do {
            rootReference = this.getRoot();
            assert (rootReference.isLockedByCurrentThread());
        } while ((rootReference2 = rootReference.updatePageAndLockedStatus(page == null ? rootReference.root : page, false, n == -1 ? rootReference.getAppendCounter() : n)) == null);
        this.notifyWaiters();
        return rootReference2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void notifyWaiters() {
        if (this.notificationRequested) {
            Object object = this.lock;
            synchronized (object) {
                this.notificationRequested = false;
                this.lock.notify();
            }
        }
    }

    final boolean isMemoryEstimationAllowed() {
        return this.avgKeySize != null || this.avgValSize != null;
    }

    final int evaluateMemoryForKeys(K[] KArray, int n) {
        if (this.avgKeySize == null) {
            return MVMap.calculateMemory(this.keyType, KArray, n);
        }
        return MemoryEstimator.estimateMemory(this.avgKeySize, this.keyType, KArray, n);
    }

    final int evaluateMemoryForValues(V[] VArray, int n) {
        if (this.avgValSize == null) {
            return MVMap.calculateMemory(this.valueType, VArray, n);
        }
        return MemoryEstimator.estimateMemory(this.avgValSize, this.valueType, VArray, n);
    }

    private static <T> int calculateMemory(DataType<T> dataType, T[] TArray, int n) {
        int n2 = n * 8;
        for (int i = 0; i < n; ++i) {
            n2 += dataType.getMemory(TArray[i]);
        }
        return n2;
    }

    final int evaluateMemoryForKey(K k) {
        if (this.avgKeySize == null) {
            return this.keyType.getMemory(k);
        }
        return MemoryEstimator.estimateMemory(this.avgKeySize, this.keyType, k);
    }

    final int evaluateMemoryForValue(V v) {
        if (this.avgValSize == null) {
            return this.valueType.getMemory(v);
        }
        return MemoryEstimator.estimateMemory(this.avgValSize, this.valueType, v);
    }

    static int samplingPct(AtomicLong atomicLong) {
        return MemoryEstimator.samplingPct(atomicLong);
    }

    private static final class IntValueHolder {
        int value;

        IntValueHolder() {
        }
    }

    private static final class RewriteDecisionMaker<V>
    extends DecisionMaker<V> {
        private final long pagePos;
        private Decision decision;

        RewriteDecisionMaker(long l) {
            this.pagePos = l;
        }

        @Override
        public Decision decide(V v, V v2, CursorPos<?, ?> cursorPos) {
            assert (this.decision == null);
            this.decision = Decision.ABORT;
            if (!DataUtils.isLeafPosition(this.pagePos)) {
                while ((cursorPos = cursorPos.parent) != null) {
                    if (cursorPos.page.getPos() != this.pagePos) continue;
                    this.decision = this.decide(v, v2);
                    break;
                }
            } else if (cursorPos.page.getPos() == this.pagePos) {
                this.decision = this.decide(v, v2);
            }
            return this.decision;
        }

        @Override
        public Decision decide(V v, V v2) {
            this.decision = v == null ? Decision.ABORT : Decision.PUT;
            return this.decision;
        }

        @Override
        public <T extends V> T selectValue(T t2, T t3) {
            return t2;
        }

        @Override
        public void reset() {
            this.decision = null;
        }

        Decision getDecision() {
            return this.decision;
        }

        public String toString() {
            return "rewrite";
        }
    }

    private static final class EqualsDecisionMaker<V>
    extends DecisionMaker<V> {
        private final DataType<V> dataType;
        private final V expectedValue;
        private Decision decision;

        EqualsDecisionMaker(DataType<V> dataType, V v) {
            this.dataType = dataType;
            this.expectedValue = v;
        }

        @Override
        public Decision decide(V v, V v2) {
            assert (this.decision == null);
            this.decision = !MVMap.areValuesEqual(this.dataType, this.expectedValue, v) ? Decision.ABORT : (v2 == null ? Decision.REMOVE : Decision.PUT);
            return this.decision;
        }

        @Override
        public void reset() {
            this.decision = null;
        }

        Decision getDecision() {
            return this.decision;
        }

        public String toString() {
            return "equals_to " + this.expectedValue;
        }
    }

    public static abstract class DecisionMaker<V> {
        public static final DecisionMaker<Object> DEFAULT = new DecisionMaker<Object>(){

            @Override
            public Decision decide(Object object, Object object2) {
                return object2 == null ? Decision.REMOVE : Decision.PUT;
            }

            public String toString() {
                return "default";
            }
        };
        public static final DecisionMaker<Object> PUT = new DecisionMaker<Object>(){

            @Override
            public Decision decide(Object object, Object object2) {
                return Decision.PUT;
            }

            public String toString() {
                return "put";
            }
        };
        public static final DecisionMaker<Object> REMOVE = new DecisionMaker<Object>(){

            @Override
            public Decision decide(Object object, Object object2) {
                return Decision.REMOVE;
            }

            public String toString() {
                return "remove";
            }
        };
        static final DecisionMaker<Object> IF_ABSENT = new DecisionMaker<Object>(){

            @Override
            public Decision decide(Object object, Object object2) {
                return object == null ? Decision.PUT : Decision.ABORT;
            }

            public String toString() {
                return "if_absent";
            }
        };
        static final DecisionMaker<Object> IF_PRESENT = new DecisionMaker<Object>(){

            @Override
            public Decision decide(Object object, Object object2) {
                return object != null ? Decision.PUT : Decision.ABORT;
            }

            public String toString() {
                return "if_present";
            }
        };

        public Decision decide(V v, V v2, CursorPos<?, ?> cursorPos) {
            return this.decide(v, v2);
        }

        public abstract Decision decide(V var1, V var2);

        public <T extends V> T selectValue(T t2, T t3) {
            return t3;
        }

        public void reset() {
        }
    }

    public static enum Decision {
        ABORT,
        REMOVE,
        PUT,
        REPEAT;

    }

    public static class Builder<K, V>
    extends BasicBuilder<MVMap<K, V>, K, V> {
        private boolean singleWriter;

        public Builder<K, V> keyType(DataType<? super K> dataType) {
            this.setKeyType(dataType);
            return this;
        }

        public Builder<K, V> valueType(DataType<? super V> dataType) {
            this.setValueType(dataType);
            return this;
        }

        public Builder<K, V> singleWriter() {
            this.singleWriter = true;
            return this;
        }

        @Override
        protected MVMap<K, V> create(Map<String, Object> map) {
            map.put("singleWriter", this.singleWriter);
            Object object = map.get("type");
            if (object == null || object.equals("rtree")) {
                return new MVMap(map, this.getKeyType(), this.getValueType());
            }
            throw new IllegalArgumentException("Incompatible map type");
        }
    }

    public static abstract class BasicBuilder<M extends MVMap<K, V>, K, V>
    implements MapBuilder<M, K, V> {
        private DataType<K> keyType;
        private DataType<V> valueType;

        protected BasicBuilder() {
        }

        @Override
        public DataType<K> getKeyType() {
            return this.keyType;
        }

        @Override
        public DataType<V> getValueType() {
            return this.valueType;
        }

        @Override
        public void setKeyType(DataType<? super K> dataType) {
            this.keyType = dataType;
        }

        @Override
        public void setValueType(DataType<? super V> dataType) {
            this.valueType = dataType;
        }

        public BasicBuilder<M, K, V> keyType(DataType<? super K> dataType) {
            this.setKeyType(dataType);
            return this;
        }

        public BasicBuilder<M, K, V> valueType(DataType<? super V> dataType) {
            this.setValueType(dataType);
            return this;
        }

        @Override
        public M create(MVStore mVStore, Map<String, Object> map) {
            if (this.getKeyType() == null) {
                this.setKeyType(new ObjectDataType());
            }
            if (this.getValueType() == null) {
                this.setValueType(new ObjectDataType());
            }
            DataType<K> dataType = this.getKeyType();
            DataType<V> dataType2 = this.getValueType();
            map.put("store", mVStore);
            map.put("key", dataType);
            map.put("val", dataType2);
            return this.create(map);
        }

        protected abstract M create(Map<String, Object> var1);
    }

    public static interface MapBuilder<M extends MVMap<K, V>, K, V> {
        public M create(MVStore var1, Map<String, Object> var2);

        public DataType<K> getKeyType();

        public DataType<V> getValueType();

        public void setKeyType(DataType<? super K> var1);

        public void setValueType(DataType<? super V> var1);
    }
}

