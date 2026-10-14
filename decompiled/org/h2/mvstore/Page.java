/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import org.h2.compress.Compressor;
import org.h2.mvstore.CursorPos;
import org.h2.mvstore.DataUtils;
import org.h2.mvstore.FileStore;
import org.h2.mvstore.MVMap;
import org.h2.mvstore.MVStore;
import org.h2.mvstore.WriteBuffer;
import org.h2.util.Utils;

public abstract class Page<K, V>
implements Cloneable {
    public final MVMap<K, V> map;
    private volatile long pos;
    public int pageNo = -1;
    private int cachedCompare;
    private int memory;
    private int diskSpaceUsed;
    private K[] keys;
    private static final AtomicLongFieldUpdater<Page> posUpdater = AtomicLongFieldUpdater.newUpdater(Page.class, "pos");
    static final int PAGE_MEMORY_CHILD = 24;
    private static final int PAGE_MEMORY = 81;
    static final int PAGE_NODE_MEMORY = 121;
    static final int PAGE_LEAF_MEMORY = 113;
    private static final int IN_MEMORY = Integer.MIN_VALUE;
    private static final PageReference[] SINGLE_EMPTY = new PageReference[]{PageReference.EMPTY};

    Page(MVMap<K, V> mVMap) {
        this.map = mVMap;
    }

    Page(MVMap<K, V> mVMap, Page<K, V> page) {
        this(mVMap, page.keys);
        this.memory = page.memory;
    }

    Page(MVMap<K, V> mVMap, K[] KArray) {
        this.map = mVMap;
        this.keys = KArray;
    }

    static <K, V> Page<K, V> createEmptyLeaf(MVMap<K, V> mVMap) {
        return Page.createLeaf(mVMap, mVMap.getKeyType().createStorage(0), mVMap.getValueType().createStorage(0), 113);
    }

    static <K, V> Page<K, V> createEmptyNode(MVMap<K, V> mVMap) {
        return Page.createNode(mVMap, mVMap.getKeyType().createStorage(0), SINGLE_EMPTY, 0L, 153);
    }

    public static <K, V> Page<K, V> createNode(MVMap<K, V> mVMap, K[] KArray, PageReference<K, V>[] pageReferenceArray, long l, int n) {
        assert (KArray != null);
        NonLeaf<K, V> nonLeaf = new NonLeaf<K, V>(mVMap, KArray, pageReferenceArray, l);
        super.initMemoryAccount(n);
        return nonLeaf;
    }

    static <K, V> Page<K, V> createLeaf(MVMap<K, V> mVMap, K[] KArray, V[] VArray, int n) {
        assert (KArray != null);
        Leaf<K, V> leaf = new Leaf<K, V>(mVMap, KArray, VArray);
        super.initMemoryAccount(n);
        return leaf;
    }

    private void initMemoryAccount(int n) {
        if (!this.map.isPersistent()) {
            this.memory = Integer.MIN_VALUE;
        } else if (n == 0) {
            this.recalculateMemory();
        } else {
            this.addMemory(n);
            assert (n == this.getMemory());
        }
    }

    static <K, V> V get(Page<K, V> page, K k) {
        while (true) {
            int n = page.binarySearch(k);
            if (page.isLeaf()) {
                return n >= 0 ? (V)page.getValue(n) : null;
            }
            if (n++ < 0) {
                n = -n;
            }
            page = page.getChildPage(n);
        }
    }

    static <K, V> Page<K, V> read(ByteBuffer byteBuffer, long l, MVMap<K, V> mVMap) {
        boolean bl = (DataUtils.getPageType(l) & 1) == 0;
        Page page = bl ? new Leaf<K, V>(mVMap) : new NonLeaf<K, V>(mVMap);
        page.pos = l;
        super.read(byteBuffer);
        return page;
    }

    public final int getMapId() {
        return this.map.getId();
    }

    abstract Page<K, V> copy(MVMap<K, V> var1, boolean var2);

    public K getKey(int n) {
        return this.keys[n];
    }

    public abstract Page<K, V> getChildPage(int var1);

    public abstract long getChildPagePos(int var1);

    public abstract V getValue(int var1);

    public final int getKeyCount() {
        return this.keys.length;
    }

    public final boolean isLeaf() {
        return this.getNodeType() == 0;
    }

    public abstract int getNodeType();

    public final long getPos() {
        return this.pos;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        this.dump(stringBuilder);
        return stringBuilder.toString();
    }

    protected void dump(StringBuilder stringBuilder) {
        stringBuilder.append("id: ").append(System.identityHashCode(this)).append('\n');
        stringBuilder.append("pos: ").append(Long.toHexString(this.pos)).append('\n');
        if (this.isSaved()) {
            int n = DataUtils.getPageChunkId(this.pos);
            stringBuilder.append("chunk:").append(Long.toHexString(n));
            if (this.pageNo >= 0) {
                stringBuilder.append(",no:").append(Long.toHexString(this.pageNo));
            }
            stringBuilder.append('\n');
        }
    }

    public final Page<K, V> copy() {
        Object object = this.clone();
        ((Page)object).pos = 0L;
        ((Page)object).pageNo = -1;
        return object;
    }

    protected final Page<K, V> clone() {
        Page page;
        try {
            page = (Page)super.clone();
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new RuntimeException(cloneNotSupportedException);
        }
        return page;
    }

    int binarySearch(K k) {
        int n = this.map.getKeyType().binarySearch(k, this.keys, this.getKeyCount(), this.cachedCompare);
        this.cachedCompare = n < 0 ? ~n : n + 1;
        return n;
    }

    abstract Page<K, V> split(int var1);

    final K[] splitKeys(int n, int n2) {
        assert (n + n2 <= this.getKeyCount());
        K[] KArray = this.createKeyStorage(n);
        K[] KArray2 = this.createKeyStorage(n2);
        System.arraycopy(this.keys, 0, KArray, 0, n);
        System.arraycopy(this.keys, this.getKeyCount() - n2, KArray2, 0, n2);
        this.keys = KArray;
        return KArray2;
    }

    abstract void expand(int var1, K[] var2, V[] var3);

    final void expandKeys(int n, K[] KArray) {
        int n2 = this.getKeyCount();
        K[] KArray2 = this.createKeyStorage(n2 + n);
        System.arraycopy(this.keys, 0, KArray2, 0, n2);
        System.arraycopy(KArray, 0, KArray2, n2, n);
        this.keys = KArray2;
    }

    public abstract long getTotalCount();

    abstract long getCounts(int var1);

    public abstract void setChild(int var1, Page<K, V> var2);

    public final void setKey(int n, K k) {
        this.keys = (Object[])this.keys.clone();
        if (this.isPersistent()) {
            K k2 = this.keys[n];
            if (!this.map.isMemoryEstimationAllowed() || k2 == null) {
                int n2 = this.map.evaluateMemoryForKey(k);
                if (k2 != null) {
                    n2 -= this.map.evaluateMemoryForKey(k2);
                }
                this.addMemory(n2);
            }
        }
        this.keys[n] = k;
    }

    public abstract V setValue(int var1, V var2);

    public abstract void insertLeaf(int var1, K var2, V var3);

    public abstract void insertNode(int var1, K var2, Page<K, V> var3);

    final void insertKey(int n, K k) {
        int n2 = this.getKeyCount();
        assert (n <= n2) : n + " > " + n2;
        K[] KArray = this.createKeyStorage(n2 + 1);
        DataUtils.copyWithGap(this.keys, KArray, n2, n);
        this.keys = KArray;
        this.keys[n] = k;
        if (this.isPersistent()) {
            this.addMemory(8 + this.map.evaluateMemoryForKey(k));
        }
    }

    public void remove(int n) {
        Object object;
        int n2 = this.getKeyCount();
        if (n == n2) {
            --n;
        }
        if (this.isPersistent() && !this.map.isMemoryEstimationAllowed()) {
            object = this.getKey(n);
            this.addMemory(-8 - this.map.evaluateMemoryForKey((K[])object));
        }
        object = this.createKeyStorage(n2 - 1);
        DataUtils.copyExcept(this.keys, object, n2, n);
        this.keys = object;
    }

    private void read(ByteBuffer byteBuffer) {
        boolean bl;
        int n;
        int n2;
        int n3 = DataUtils.getPageChunkId(this.pos);
        int n4 = DataUtils.getPageOffset(this.pos);
        int n5 = byteBuffer.position();
        int n6 = byteBuffer.getInt();
        if (n6 > (n2 = byteBuffer.remaining() + 4) || n6 < 4) {
            throw DataUtils.newMVStoreException(6, "File corrupted in chunk {0}, expected page length 4..{1}, got {2}", n3, n2, n6);
        }
        short s2 = byteBuffer.getShort();
        if (s2 != (short)(n = DataUtils.getCheckValue(n3) ^ DataUtils.getCheckValue(n4) ^ DataUtils.getCheckValue(n6))) {
            throw DataUtils.newMVStoreException(6, "File corrupted in chunk {0}, expected check value {1}, got {2}", n3, n, s2);
        }
        this.pageNo = DataUtils.readVarInt(byteBuffer);
        if (this.pageNo < 0) {
            throw DataUtils.newMVStoreException(6, "File corrupted in chunk {0}, got negative page No {1}", n3, this.pageNo);
        }
        int n7 = DataUtils.readVarInt(byteBuffer);
        if (n7 != this.map.getId()) {
            throw DataUtils.newMVStoreException(6, "File corrupted in chunk {0}, expected map id {1}, got {2}", n3, this.map.getId(), n7);
        }
        int n8 = DataUtils.readVarInt(byteBuffer);
        this.keys = this.createKeyStorage(n8);
        byte by = byteBuffer.get();
        if (this.isLeaf() != ((by & 1) == 0)) {
            throw DataUtils.newMVStoreException(6, "File corrupted in chunk {0}, expected node type {1}, got {2}", n3, this.isLeaf() ? "0" : "1", by);
        }
        byteBuffer.limit(n5 + n6);
        if (!this.isLeaf()) {
            this.readPayLoad(byteBuffer);
        }
        boolean bl2 = bl = (by & 2) != 0;
        if (bl) {
            byte[] byArray;
            Compressor compressor = (by & 6) == 6 ? this.map.getStore().getCompressorHigh() : this.map.getStore().getCompressorFast();
            int n9 = DataUtils.readVarInt(byteBuffer);
            int n10 = byteBuffer.remaining();
            int n11 = 0;
            if (byteBuffer.hasArray()) {
                byArray = byteBuffer.array();
                n11 = byteBuffer.arrayOffset() + byteBuffer.position();
            } else {
                byArray = Utils.newBytes(n10);
                byteBuffer.get(byArray);
            }
            int n12 = n10 + n9;
            byteBuffer = ByteBuffer.allocate(n12);
            compressor.expand(byArray, n11, n10, byteBuffer.array(), byteBuffer.arrayOffset(), n12);
        }
        this.map.getKeyType().read(byteBuffer, this.keys, n8);
        if (this.isLeaf()) {
            this.readPayLoad(byteBuffer);
        }
        this.diskSpaceUsed = n6;
        this.recalculateMemory();
    }

    protected abstract void readPayLoad(ByteBuffer var1);

    public final boolean isSaved() {
        return DataUtils.isPageSaved(this.pos);
    }

    public final boolean isRemoved() {
        return DataUtils.isPageRemoved(this.pos);
    }

    private boolean markAsRemoved() {
        assert (this.getTotalCount() > 0L) : this;
        do {
            long l;
            if (DataUtils.isPageSaved(l = this.pos)) {
                return false;
            }
            assert (!DataUtils.isPageRemoved(l));
        } while (!posUpdater.compareAndSet(this, 0L, 1L));
        return true;
    }

    protected final int write(FileStore.PageSerializationManager pageSerializationManager) {
        int n;
        this.pageNo = pageSerializationManager.getPageNo();
        int n2 = this.getKeyCount();
        WriteBuffer writeBuffer = pageSerializationManager.getBuffer();
        int n3 = writeBuffer.position();
        writeBuffer.putInt(0).putShort((short)0).putVarInt(this.pageNo).putVarInt(this.map.getId()).putVarInt(n2);
        int n4 = writeBuffer.position();
        int n5 = this.isLeaf() ? 0 : 1;
        writeBuffer.put((byte)n5);
        int n6 = writeBuffer.position();
        this.writeChildren(writeBuffer, true);
        int n7 = writeBuffer.position();
        this.map.getKeyType().write(writeBuffer, this.keys, n2);
        this.writeValues(writeBuffer);
        MVStore mVStore = this.map.getStore();
        int n8 = writeBuffer.position() - n7;
        if (n8 > 16 && (n = mVStore.getCompressionLevel()) > 0) {
            int n9;
            byte[] byArray;
            int n92;
            Compressor compressor;
            if (n == 1) {
                compressor = mVStore.getCompressorFast();
                n92 = 2;
            } else {
                compressor = mVStore.getCompressorHigh();
                n92 = 6;
            }
            byte[] byArray2 = new byte[n8 * 2];
            ByteBuffer byteBuffer = writeBuffer.getBuffer();
            boolean bl = false;
            if (byteBuffer.hasArray()) {
                byArray = byteBuffer.array();
                n9 = byteBuffer.arrayOffset() + n7;
            } else {
                byArray = Utils.newBytes(n8);
                writeBuffer.position(n7).get(byArray);
            }
            int n10 = compressor.compress(byArray, n9, n8, byArray2, 0);
            int n11 = DataUtils.getVarIntLen(n8 - n10);
            if (n10 + n11 < n8) {
                writeBuffer.position(n4).put((byte)(n5 | n92));
                writeBuffer.position(n7).putVarInt(n8 - n10).put(byArray2, 0, n10);
            }
        }
        n = writeBuffer.position() - n3;
        long l = pageSerializationManager.getPagePosition(this.getMapId(), n3, n, n5);
        if (this.isSaved()) {
            throw DataUtils.newMVStoreException(3, "Page already stored", new Object[0]);
        }
        boolean bl2 = this.isRemoved();
        while (!posUpdater.compareAndSet(this, bl2 ? 1L : 0L, l)) {
            bl2 = this.isRemoved();
        }
        int n12 = DataUtils.getPageMaxLength(l);
        this.diskSpaceUsed = n12 != 0x200000 ? n12 : n;
        boolean bl = this.map.isSingleWriter();
        pageSerializationManager.onPageSerialized(this, bl2, n12, bl);
        return n6;
    }

    protected abstract void writeValues(WriteBuffer var1);

    protected abstract void writeChildren(WriteBuffer var1, boolean var2);

    abstract void writeUnsavedRecursive(FileStore.PageSerializationManager var1);

    abstract void releaseSavedPages();

    public abstract int getRawChildPageCount();

    protected final boolean isPersistent() {
        return this.memory != Integer.MIN_VALUE;
    }

    public final int getMemory() {
        if (this.isPersistent()) {
            return this.memory;
        }
        return 0;
    }

    public long getDiskSpaceUsed() {
        long l = 0L;
        if (this.isPersistent()) {
            l += (long)this.diskSpaceUsed;
            if (!this.isLeaf()) {
                for (int i = 0; i < this.getRawChildPageCount(); ++i) {
                    long l2 = this.getChildPagePos(i);
                    if (l2 == 0L) continue;
                    l += this.getChildPage(i).getDiskSpaceUsed();
                }
            }
        }
        return l;
    }

    final void addMemory(int n) {
        this.memory += n;
        assert (this.memory >= 0);
    }

    final void recalculateMemory() {
        assert (this.isPersistent());
        this.memory = this.calculateMemory();
    }

    protected int calculateMemory() {
        return this.map.evaluateMemoryForKeys(this.keys, this.getKeyCount());
    }

    public boolean isComplete() {
        return true;
    }

    public void setComplete() {
    }

    public final int removePage(long l) {
        if (this.isPersistent() && this.getTotalCount() > 0L) {
            MVStore mVStore = this.map.store;
            if (!this.markAsRemoved()) {
                long l2 = this.pos;
                mVStore.accountForRemovedPage(l2, l, this.map.isSingleWriter(), this.pageNo);
            } else {
                return -this.memory;
            }
        }
        return 0;
    }

    public abstract CursorPos<K, V> getPrependCursorPos(CursorPos<K, V> var1);

    public abstract CursorPos<K, V> getAppendCursorPos(CursorPos<K, V> var1);

    public abstract int removeAllRecursive(long var1);

    public final K[] createKeyStorage(int n) {
        return this.map.getKeyType().createStorage(n);
    }

    final V[] createValueStorage(int n) {
        return this.map.getValueType().createStorage(n);
    }

    public static <K, V> PageReference<K, V>[] createRefStorage(int n) {
        return new PageReference[n];
    }

    private static class Leaf<K, V>
    extends Page<K, V> {
        private V[] values;

        Leaf(MVMap<K, V> mVMap) {
            super(mVMap);
        }

        private Leaf(MVMap<K, V> mVMap, Leaf<K, V> leaf) {
            super(mVMap, leaf);
            this.values = leaf.values;
        }

        Leaf(MVMap<K, V> mVMap, K[] KArray, V[] VArray) {
            super(mVMap, KArray);
            this.values = VArray;
        }

        @Override
        public int getNodeType() {
            return 0;
        }

        @Override
        public Page<K, V> copy(MVMap<K, V> mVMap, boolean bl) {
            return new Leaf<K, V>(mVMap, this);
        }

        @Override
        public Page<K, V> getChildPage(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long getChildPagePos(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V getValue(int n) {
            return this.values == null ? null : (V)this.values[n];
        }

        @Override
        public Page<K, V> split(int n) {
            Object object;
            assert (!this.isSaved());
            int n2 = this.getKeyCount() - n;
            K[] KArray = this.splitKeys(n, n2);
            V[] VArray = this.createValueStorage(n2);
            if (this.values != null) {
                object = this.createValueStorage(n);
                System.arraycopy(this.values, 0, object, 0, n);
                System.arraycopy(this.values, n, VArray, 0, n2);
                this.values = object;
            }
            object = Leaf.createLeaf(this.map, KArray, VArray, 0);
            if (this.isPersistent()) {
                this.recalculateMemory();
            }
            return object;
        }

        @Override
        public void expand(int n, K[] KArray, V[] VArray) {
            int n2 = this.getKeyCount();
            this.expandKeys(n, KArray);
            if (this.values != null) {
                V[] VArray2 = this.createValueStorage(n2 + n);
                System.arraycopy(this.values, 0, VArray2, 0, n2);
                System.arraycopy(VArray, 0, VArray2, n2, n);
                this.values = VArray2;
            }
            if (this.isPersistent()) {
                this.recalculateMemory();
            }
        }

        @Override
        public long getTotalCount() {
            return this.getKeyCount();
        }

        @Override
        long getCounts(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setChild(int n, Page<K, V> page) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V setValue(int n, V v) {
            this.values = (Object[])this.values.clone();
            V v2 = this.setValueInternal(n, v);
            if (this.isPersistent() && !this.map.isMemoryEstimationAllowed()) {
                this.addMemory(this.map.evaluateMemoryForValue(v) - this.map.evaluateMemoryForValue(v2));
            }
            return v2;
        }

        private V setValueInternal(int n, V v) {
            V v2 = this.values[n];
            this.values[n] = v;
            return v2;
        }

        @Override
        public void insertLeaf(int n, K k, V v) {
            int n2 = this.getKeyCount();
            this.insertKey(n, k);
            if (this.values != null) {
                V[] VArray = this.createValueStorage(n2 + 1);
                DataUtils.copyWithGap(this.values, VArray, n2, n);
                this.values = VArray;
                this.setValueInternal(n, v);
                if (this.isPersistent()) {
                    this.addMemory(8 + this.map.evaluateMemoryForValue(v));
                }
            }
        }

        @Override
        public void insertNode(int n, K k, Page<K, V> page) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void remove(int n) {
            int n2 = this.getKeyCount();
            super.remove(n);
            if (this.values != null) {
                Object object;
                if (this.isPersistent()) {
                    if (this.map.isMemoryEstimationAllowed()) {
                        this.addMemory(-this.getMemory() / n2);
                    } else {
                        object = this.getValue(n);
                        this.addMemory(-8 - this.map.evaluateMemoryForValue(object));
                    }
                }
                object = this.createValueStorage(n2 - 1);
                DataUtils.copyExcept(this.values, object, n2, n);
                this.values = object;
            }
        }

        @Override
        public int removeAllRecursive(long l) {
            return this.removePage(l);
        }

        @Override
        public CursorPos<K, V> getPrependCursorPos(CursorPos<K, V> cursorPos) {
            return new CursorPos<K, V>(this, -1, cursorPos);
        }

        @Override
        public CursorPos<K, V> getAppendCursorPos(CursorPos<K, V> cursorPos) {
            int n = this.getKeyCount();
            return new CursorPos<K, V>(this, ~n, cursorPos);
        }

        @Override
        protected void readPayLoad(ByteBuffer byteBuffer) {
            int n = this.getKeyCount();
            this.values = this.createValueStorage(n);
            this.map.getValueType().read(byteBuffer, this.values, this.getKeyCount());
        }

        @Override
        protected void writeValues(WriteBuffer writeBuffer) {
            this.map.getValueType().write(writeBuffer, this.values, this.getKeyCount());
        }

        @Override
        protected void writeChildren(WriteBuffer writeBuffer, boolean bl) {
        }

        @Override
        void writeUnsavedRecursive(FileStore.PageSerializationManager pageSerializationManager) {
            if (!this.isSaved()) {
                this.write(pageSerializationManager);
            }
        }

        @Override
        void releaseSavedPages() {
        }

        @Override
        public int getRawChildPageCount() {
            return 0;
        }

        @Override
        protected int calculateMemory() {
            return super.calculateMemory() + 113 + (this.values == null ? 0 : this.map.evaluateMemoryForValues(this.values, this.getKeyCount()));
        }

        @Override
        public void dump(StringBuilder stringBuilder) {
            super.dump(stringBuilder);
            int n = this.getKeyCount();
            for (int i = 0; i < n; ++i) {
                if (i > 0) {
                    stringBuilder.append(" ");
                }
                stringBuilder.append(this.getKey(i));
                if (this.values == null) continue;
                stringBuilder.append(':');
                stringBuilder.append(this.getValue(i));
            }
        }
    }

    private static class IncompleteNonLeaf<K, V>
    extends NonLeaf<K, V> {
        private boolean complete;

        IncompleteNonLeaf(MVMap<K, V> mVMap, NonLeaf<K, V> nonLeaf) {
            super(mVMap, nonLeaf, IncompleteNonLeaf.constructEmptyPageRefs(nonLeaf.getRawChildPageCount()), nonLeaf.getTotalCount());
        }

        private static <K, V> PageReference<K, V>[] constructEmptyPageRefs(int n) {
            Object[] objectArray = IncompleteNonLeaf.createRefStorage(n);
            Arrays.fill(objectArray, PageReference.empty());
            return objectArray;
        }

        @Override
        void writeUnsavedRecursive(FileStore.PageSerializationManager pageSerializationManager) {
            if (this.complete) {
                super.writeUnsavedRecursive(pageSerializationManager);
            } else if (!this.isSaved()) {
                this.writeChildrenRecursive(pageSerializationManager);
            }
        }

        @Override
        public boolean isComplete() {
            return this.complete;
        }

        @Override
        public void setComplete() {
            this.recalculateTotalCount();
            this.complete = true;
        }

        @Override
        public void dump(StringBuilder stringBuilder) {
            super.dump(stringBuilder);
            stringBuilder.append(", complete:").append(this.complete);
        }
    }

    private static class NonLeaf<K, V>
    extends Page<K, V> {
        private PageReference<K, V>[] children;
        private long totalCount;

        NonLeaf(MVMap<K, V> mVMap) {
            super(mVMap);
        }

        NonLeaf(MVMap<K, V> mVMap, NonLeaf<K, V> nonLeaf, PageReference<K, V>[] pageReferenceArray, long l) {
            super(mVMap, nonLeaf);
            this.children = pageReferenceArray;
            this.totalCount = l;
        }

        NonLeaf(MVMap<K, V> mVMap, K[] KArray, PageReference<K, V>[] pageReferenceArray, long l) {
            super(mVMap, KArray);
            this.children = pageReferenceArray;
            this.totalCount = l;
        }

        @Override
        public int getNodeType() {
            return 1;
        }

        @Override
        public Page<K, V> copy(MVMap<K, V> mVMap, boolean bl) {
            return bl ? new IncompleteNonLeaf<K, V>(mVMap, this) : new NonLeaf<K, V>(mVMap, this, this.children, this.totalCount);
        }

        @Override
        public Page<K, V> getChildPage(int n) {
            PageReference<K, V> pageReference = this.children[n];
            Page<K, V> page = pageReference.getPage();
            if (page == null) {
                page = this.map.readPage(pageReference.getPos());
                assert (pageReference.getPos() == page.getPos());
                assert (pageReference.count == page.getTotalCount());
            }
            return page;
        }

        @Override
        public long getChildPagePos(int n) {
            return this.children[n].getPos();
        }

        @Override
        public V getValue(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Page<K, V> split(int n) {
            assert (!this.isSaved());
            int n2 = this.getKeyCount() - n;
            K[] KArray = this.splitKeys(n, n2 - 1);
            PageReference<K, V>[] pageReferenceArray = NonLeaf.createRefStorage(n + 1);
            PageReference<K, V>[] pageReferenceArray2 = NonLeaf.createRefStorage(n2);
            System.arraycopy(this.children, 0, pageReferenceArray, 0, n + 1);
            System.arraycopy(this.children, n + 1, pageReferenceArray2, 0, n2);
            this.children = pageReferenceArray;
            long l = 0L;
            for (PageReference pageReference : pageReferenceArray) {
                l += pageReference.count;
            }
            this.totalCount = l;
            l = 0L;
            for (PageReference pageReference : pageReferenceArray2) {
                l += pageReference.count;
            }
            Page page = NonLeaf.createNode(this.map, KArray, pageReferenceArray2, l, 0);
            if (this.isPersistent()) {
                this.recalculateMemory();
            }
            return page;
        }

        @Override
        public void expand(int n, Object[] objectArray, Object[] objectArray2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long getTotalCount() {
            assert (!this.isComplete() || this.totalCount == this.calculateTotalCount()) : "Total count: " + this.totalCount + " != " + this.calculateTotalCount();
            return this.totalCount;
        }

        private long calculateTotalCount() {
            long l = 0L;
            int n = this.getKeyCount();
            for (int i = 0; i <= n; ++i) {
                l += this.children[i].count;
            }
            return l;
        }

        void recalculateTotalCount() {
            this.totalCount = this.calculateTotalCount();
        }

        @Override
        long getCounts(int n) {
            return this.children[n].count;
        }

        @Override
        public void setChild(int n, Page<K, V> page) {
            assert (page != null);
            PageReference<K, V> pageReference = this.children[n];
            if (page != pageReference.getPage() || page.getPos() != pageReference.getPos()) {
                this.totalCount += page.getTotalCount() - pageReference.count;
                this.children = (PageReference[])this.children.clone();
                this.children[n] = new PageReference<K, V>(page);
            }
        }

        @Override
        public V setValue(int n, V v) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void insertLeaf(int n, K k, V v) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void insertNode(int n, K k, Page<K, V> page) {
            int n2 = this.getRawChildPageCount();
            this.insertKey(n, k);
            PageReference<K, V>[] pageReferenceArray = NonLeaf.createRefStorage(n2 + 1);
            DataUtils.copyWithGap(this.children, pageReferenceArray, n2, n);
            this.children = pageReferenceArray;
            this.children[n] = new PageReference<K, V>(page);
            this.totalCount += page.getTotalCount();
            if (this.isPersistent()) {
                this.addMemory(32);
            }
        }

        @Override
        public void remove(int n) {
            int n2 = this.getRawChildPageCount();
            super.remove(n);
            if (this.isPersistent()) {
                if (this.map.isMemoryEstimationAllowed()) {
                    this.addMemory(-this.getMemory() / n2);
                } else {
                    this.addMemory(-32);
                }
            }
            this.totalCount -= this.children[n].count;
            PageReference<K, V>[] pageReferenceArray = NonLeaf.createRefStorage(n2 - 1);
            DataUtils.copyExcept(this.children, pageReferenceArray, n2, n);
            this.children = pageReferenceArray;
        }

        @Override
        public int removeAllRecursive(long l) {
            int n = this.removePage(l);
            if (this.isPersistent()) {
                int n2 = this.map.getChildPageCount(this);
                for (int i = 0; i < n2; ++i) {
                    PageReference<K, V> pageReference = this.children[i];
                    Page<K, V> page = pageReference.getPage();
                    if (page != null) {
                        n += page.removeAllRecursive(l);
                        continue;
                    }
                    long l2 = pageReference.getPos();
                    assert (DataUtils.isPageSaved(l2));
                    if (DataUtils.isLeafPosition(l2)) {
                        this.map.store.accountForRemovedPage(l2, l, this.map.isSingleWriter(), -1);
                        continue;
                    }
                    n += this.map.readPage(l2).removeAllRecursive(l);
                }
            }
            return n;
        }

        @Override
        public CursorPos<K, V> getPrependCursorPos(CursorPos<K, V> cursorPos) {
            Page<K, V> page = this.getChildPage(0);
            return page.getPrependCursorPos(new CursorPos<K, V>(this, 0, cursorPos));
        }

        @Override
        public CursorPos<K, V> getAppendCursorPos(CursorPos<K, V> cursorPos) {
            int n = this.getKeyCount();
            Page<K, V> page = this.getChildPage(n);
            return page.getAppendCursorPos(new CursorPos<K, V>(this, n, cursorPos));
        }

        @Override
        protected void readPayLoad(ByteBuffer byteBuffer) {
            int n = this.getKeyCount();
            this.children = NonLeaf.createRefStorage(n + 1);
            long[] lArray = new long[n + 1];
            for (int i = 0; i <= n; ++i) {
                lArray[i] = byteBuffer.getLong();
            }
            long l = 0L;
            for (int i = 0; i <= n; ++i) {
                long l2 = DataUtils.readVarLong(byteBuffer);
                long l3 = lArray[i];
                assert (l3 != 0L ? l2 >= 0L : l2 == 0L);
                l += l2;
                this.children[i] = l3 == 0L ? PageReference.empty() : new PageReference(l3, l2);
            }
            this.totalCount = l;
        }

        @Override
        protected void writeValues(WriteBuffer writeBuffer) {
        }

        @Override
        protected void writeChildren(WriteBuffer writeBuffer, boolean bl) {
            int n;
            int n2 = this.getKeyCount();
            for (n = 0; n <= n2; ++n) {
                writeBuffer.putLong(this.children[n].getPos());
            }
            if (bl) {
                for (n = 0; n <= n2; ++n) {
                    writeBuffer.putVarLong(this.children[n].count);
                }
            }
        }

        @Override
        void writeUnsavedRecursive(FileStore.PageSerializationManager pageSerializationManager) {
            if (!this.isSaved()) {
                int n = this.write(pageSerializationManager);
                this.writeChildrenRecursive(pageSerializationManager);
                WriteBuffer writeBuffer = pageSerializationManager.getBuffer();
                int n2 = writeBuffer.position();
                writeBuffer.position(n);
                this.writeChildren(writeBuffer, false);
                writeBuffer.position(n2);
            }
        }

        void writeChildrenRecursive(FileStore.PageSerializationManager pageSerializationManager) {
            int n = this.getRawChildPageCount();
            for (int i = 0; i < n; ++i) {
                PageReference<K, V> pageReference = this.children[i];
                Page<K, V> page = pageReference.getPage();
                if (page == null) continue;
                page.writeUnsavedRecursive(pageSerializationManager);
                pageReference.resetPos();
            }
        }

        @Override
        void releaseSavedPages() {
            int n = this.getRawChildPageCount();
            for (int i = 0; i < n; ++i) {
                this.children[i].clearPageReference();
            }
        }

        @Override
        public int getRawChildPageCount() {
            return this.getKeyCount() + 1;
        }

        @Override
        protected int calculateMemory() {
            return super.calculateMemory() + 121 + this.getRawChildPageCount() * 32;
        }

        @Override
        public void dump(StringBuilder stringBuilder) {
            super.dump(stringBuilder);
            int n = this.getKeyCount();
            for (int i = 0; i <= n; ++i) {
                if (i > 0) {
                    stringBuilder.append(" ");
                }
                stringBuilder.append("[").append(Long.toHexString(this.children[i].getPos())).append("]");
                if (i >= n) continue;
                stringBuilder.append(" ").append(this.getKey(i));
            }
        }
    }

    public static final class PageReference<K, V> {
        static final PageReference EMPTY = new PageReference(null, 0L, 0L);
        private long pos;
        private Page<K, V> page;
        final long count;

        public static <X, Y> PageReference<X, Y> empty() {
            return EMPTY;
        }

        public PageReference(Page<K, V> page) {
            this(page, page.getPos(), page.getTotalCount());
        }

        PageReference(long l, long l2) {
            this(null, l, l2);
            assert (DataUtils.isPageSaved(l));
        }

        private PageReference(Page<K, V> page, long l, long l2) {
            this.page = page;
            this.pos = l;
            this.count = l2;
        }

        public Page<K, V> getPage() {
            return this.page;
        }

        void clearPageReference() {
            if (this.page != null) {
                this.page.releaseSavedPages();
                assert (this.page.isSaved() || !this.page.isComplete());
                if (this.page.isSaved()) {
                    assert (this.pos == this.page.getPos());
                    assert (this.count == this.page.getTotalCount()) : this.count + " != " + this.page.getTotalCount();
                    this.page = null;
                }
            }
        }

        long getPos() {
            return this.pos;
        }

        void resetPos() {
            Page<K, V> page = this.page;
            if (page != null && page.isSaved()) {
                this.pos = page.getPos();
                assert (this.count == page.getTotalCount());
            }
        }

        public String toString() {
            return "Cnt:" + this.count + ", pos:" + (this.pos == 0L ? "0" : DataUtils.getPageChunkId(this.pos) + (this.page == null ? "" : "/" + this.page.pageNo) + "-" + DataUtils.getPageOffset(this.pos) + ":" + DataUtils.getPageMaxLength(this.pos)) + ((this.page == null ? DataUtils.getPageType(this.pos) == 0 : this.page.isLeaf()) ? " leaf" : " node") + ", page:{" + this.page + "}";
        }
    }
}

