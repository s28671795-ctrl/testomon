/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore.rtree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import org.h2.mvstore.CursorPos;
import org.h2.mvstore.MVMap;
import org.h2.mvstore.Page;
import org.h2.mvstore.RootReference;
import org.h2.mvstore.rtree.Spatial;
import org.h2.mvstore.rtree.SpatialDataType;
import org.h2.mvstore.type.DataType;

public final class MVRTreeMap<V>
extends MVMap<Spatial, V> {
    private final SpatialDataType keyType;
    private boolean quadraticSplit;

    public MVRTreeMap(Map<String, Object> map, SpatialDataType spatialDataType, DataType<V> dataType) {
        super(map, spatialDataType, dataType);
        this.keyType = spatialDataType;
        this.quadraticSplit = Boolean.parseBoolean(String.valueOf(map.get("quadraticSplit")));
    }

    private MVRTreeMap(MVRTreeMap<V> mVRTreeMap) {
        super(mVRTreeMap);
        this.keyType = mVRTreeMap.keyType;
        this.quadraticSplit = mVRTreeMap.quadraticSplit;
    }

    public MVRTreeMap<V> cloneIt() {
        return new MVRTreeMap<V>(this);
    }

    public RTreeCursor<V> findIntersectingKeys(Spatial spatial) {
        return new IntersectsRTreeCursor(this.getRootPage(), spatial, this.keyType);
    }

    public RTreeCursor<V> findContainedKeys(Spatial spatial) {
        return new ContainsRTreeCursor(this.getRootPage(), spatial, this.keyType);
    }

    private boolean contains(Page<Spatial, V> page, int n, Spatial spatial) {
        return this.keyType.contains(page.getKey(n), spatial);
    }

    @Override
    public V get(Page<Spatial, V> page, Spatial spatial) {
        int n = page.getKeyCount();
        if (!page.isLeaf()) {
            for (int i = 0; i < n; ++i) {
                V v;
                if (!this.contains(page, i, spatial) || (v = this.get(page.getChildPage(i), spatial)) == null) continue;
                return v;
            }
        } else {
            for (int i = 0; i < n; ++i) {
                if (!this.keyType.equals(page.getKey(i), spatial)) continue;
                return page.getValue(i);
            }
        }
        return null;
    }

    @Override
    public V remove(Object object) {
        return (V)this.operate((Spatial)object, (V)null, (MVMap.DecisionMaker<? super V>)MVMap.DecisionMaker.REMOVE);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public V operate(Spatial spatial, V v, MVMap.DecisionMaker<? super V> decisionMaker) {
        int n = 0;
        ArrayList<Page<Spatial, V>> arrayList = this.isPersistent() ? new ArrayList<Page<Spatial, V>>() : null;
        while (true) {
            RootReference rootReference = this.flushAndGetRoot();
            if (n++ == 0 && !rootReference.isLockedByCurrentThread()) {
                this.beforeWrite();
            }
            Page page = rootReference.root;
            if (arrayList != null && page.getTotalCount() > 0L) {
                arrayList.add(page);
            }
            page = page.copy();
            Object v2 = this.operate(page, spatial, v, decisionMaker, arrayList);
            if (!page.isLeaf() && page.getTotalCount() == 0L) {
                if (arrayList != null) {
                    arrayList.add(page);
                }
                page = this.createEmptyLeaf();
            } else if (page.getKeyCount() > this.store.getKeysPerPage() || (long)page.getMemory() > this.store.getMaxPageSize() && page.getKeyCount() > 3) {
                long l = page.getTotalCount();
                Page page2 = this.split(page);
                Spatial spatial2 = this.getBounds(page);
                Spatial spatial3 = this.getBounds(page2);
                Spatial[] object = (Spatial[])page.createKeyStorage(2);
                object[0] = spatial2;
                object[1] = spatial3;
                Page.PageReference<K, V>[] pageReferenceArray = Page.createRefStorage(3);
                pageReferenceArray[0] = new Page.PageReference(page);
                pageReferenceArray[1] = new Page.PageReference(page2);
                pageReferenceArray[2] = Page.PageReference.empty();
                page = Page.createNode(this, object, pageReferenceArray, l, 0);
                this.registerUnsavedMemory(page.getMemory());
            }
            if (arrayList == null) {
                if (MVRTreeMap.updateRoot(rootReference, page, n)) {
                    return v2;
                }
            } else {
                RootReference rootReference2 = this.tryLock(rootReference, n);
                if (rootReference2 != null) {
                    try {
                        long l = rootReference2.version;
                        int n2 = 0;
                        for (Page page2 : arrayList) {
                            if (page2.isRemoved()) continue;
                            n2 += page2.removePage(l);
                        }
                        this.registerUnsavedMemory(n2);
                    }
                    finally {
                        this.unlockRoot(page);
                    }
                    return v2;
                }
                arrayList.clear();
            }
            decisionMaker.reset();
        }
    }

    private V operate(Page<Spatial, V> page, Spatial spatial, V v, MVMap.DecisionMaker<? super V> decisionMaker, Collection<Page<Spatial, V>> collection) {
        V v2;
        if (page.isLeaf()) {
            int n = -1;
            int n2 = page.getKeyCount();
            for (int i = 0; i < n2; ++i) {
                if (!this.keyType.equals(page.getKey(i), spatial)) continue;
                n = i;
            }
            Object t2 = n < 0 ? null : (Object)page.getValue(n);
            MVMap.Decision decision = decisionMaker.decide(t2, v);
            switch (decision) {
                case REPEAT: 
                case ABORT: {
                    break;
                }
                case REMOVE: {
                    if (n < 0) break;
                    page.remove(n);
                    break;
                }
                case PUT: {
                    v = decisionMaker.selectValue(t2, v);
                    if (n < 0) {
                        page.insertLeaf(page.getKeyCount(), spatial, v);
                        break;
                    }
                    page.setKey(n, spatial);
                    page.setValue(n, v);
                }
            }
            return t2;
        }
        int n = -1;
        for (int i = 0; i < page.getKeyCount(); ++i) {
            if (!this.contains(page, i, spatial)) continue;
            Page<Spatial, V> page2 = page.getChildPage(i);
            if (this.get(page2, spatial) != null) {
                n = i;
                break;
            }
            if (n >= 0) continue;
            n = i;
        }
        if (n < 0) {
            float f = Float.MAX_VALUE;
            for (int i = 0; i < page.getKeyCount(); ++i) {
                Spatial spatial2 = page.getKey(i);
                float f2 = this.keyType.getAreaIncrease(spatial2, spatial);
                if (!(f2 < f)) continue;
                n = i;
                f = f2;
            }
        }
        Page<Spatial, V> page3 = page.getChildPage(n);
        if (collection != null) {
            collection.add(page3);
        }
        if ((page3 = page3.copy()).getKeyCount() > this.store.getKeysPerPage() || (long)page3.getMemory() > this.store.getMaxPageSize() && page3.getKeyCount() > 4) {
            Page<Spatial, V> page4 = this.split(page3);
            page.setKey(n, this.getBounds(page3));
            page.setChild(n, page3);
            page.insertNode(n, this.getBounds(page4), page4);
            v2 = this.operate(page, spatial, v, decisionMaker, collection);
        } else {
            v2 = this.operate(page3, spatial, v, decisionMaker, collection);
            Spatial spatial3 = page.getKey(n);
            if (!this.keyType.contains(spatial3, spatial)) {
                spatial3 = this.keyType.createBoundingBox(spatial3);
                this.keyType.increaseBounds(spatial3, spatial);
                page.setKey(n, spatial3);
            }
            if (page3.getTotalCount() > 0L) {
                page.setChild(n, page3);
            } else {
                page.remove(n);
            }
        }
        return v2;
    }

    private Spatial getBounds(Page<Spatial, V> page) {
        Spatial spatial = this.keyType.createBoundingBox(page.getKey(0));
        int n = page.getKeyCount();
        for (int i = 1; i < n; ++i) {
            this.keyType.increaseBounds(spatial, page.getKey(i));
        }
        return spatial;
    }

    @Override
    public V put(Spatial spatial, V v) {
        return (V)this.operate(spatial, v, MVMap.DecisionMaker.PUT);
    }

    public void add(Spatial spatial, V v) {
        this.operate(spatial, v, MVMap.DecisionMaker.PUT);
    }

    private Page<Spatial, V> split(Page<Spatial, V> page) {
        return this.quadraticSplit ? this.splitQuadratic(page) : this.splitLinear(page);
    }

    private Page<Spatial, V> splitLinear(Page<Spatial, V> page) {
        int n = page.getKeyCount();
        ArrayList<Spatial> arrayList = new ArrayList<Spatial>(n);
        for (int i = 0; i < n; ++i) {
            arrayList.add(page.getKey(i));
        }
        int[] nArray = this.keyType.getExtremes(arrayList);
        if (nArray == null) {
            return this.splitQuadratic(page);
        }
        Page<Spatial, V> page2 = this.newPage(page.isLeaf());
        Page<Spatial, V> page3 = this.newPage(page.isLeaf());
        MVRTreeMap.move(page, page2, nArray[0]);
        if (nArray[1] > nArray[0]) {
            nArray[1] = nArray[1] - 1;
        }
        MVRTreeMap.move(page, page3, nArray[1]);
        Spatial spatial = this.keyType.createBoundingBox(page2.getKey(0));
        Spatial spatial2 = this.keyType.createBoundingBox(page3.getKey(0));
        while (page.getKeyCount() > 0) {
            float f;
            Spatial spatial3 = page.getKey(0);
            float f2 = this.keyType.getAreaIncrease(spatial, spatial3);
            if (f2 < (f = this.keyType.getAreaIncrease(spatial2, spatial3))) {
                this.keyType.increaseBounds(spatial, spatial3);
                MVRTreeMap.move(page, page2, 0);
                continue;
            }
            this.keyType.increaseBounds(spatial2, spatial3);
            MVRTreeMap.move(page, page3, 0);
        }
        while (page3.getKeyCount() > 0) {
            MVRTreeMap.move(page3, page, 0);
        }
        return page2;
    }

    private Page<Spatial, V> splitQuadratic(Page<Spatial, V> page) {
        float f;
        Spatial spatial;
        Page<Spatial, V> page2 = this.newPage(page.isLeaf());
        Page<Spatial, V> page3 = this.newPage(page.isLeaf());
        float f2 = Float.MIN_VALUE;
        int n = 0;
        int n2 = 0;
        int n3 = page.getKeyCount();
        for (int i = 0; i < n3; ++i) {
            spatial = page.getKey(i);
            for (int j = 0; j < n3; ++j) {
                Spatial spatial2;
                if (i == j || !((f = this.keyType.getCombinedArea(spatial, spatial2 = page.getKey(j))) > f2)) continue;
                f2 = f;
                n = i;
                n2 = j;
            }
        }
        MVRTreeMap.move(page, page2, n);
        if (n < n2) {
            --n2;
        }
        MVRTreeMap.move(page, page3, n2);
        Spatial spatial3 = this.keyType.createBoundingBox(page2.getKey(0));
        spatial = this.keyType.createBoundingBox(page3.getKey(0));
        while (page.getKeyCount() > 0) {
            float f3 = 0.0f;
            float f4 = 0.0f;
            f = 0.0f;
            int n4 = 0;
            n3 = page.getKeyCount();
            for (int i = 0; i < n3; ++i) {
                float f5;
                Spatial spatial4 = page.getKey(i);
                float f6 = this.keyType.getAreaIncrease(spatial3, spatial4);
                float f7 = Math.abs(f6 - (f5 = this.keyType.getAreaIncrease(spatial, spatial4)));
                if (!(f7 > f3)) continue;
                f3 = f7;
                f4 = f6;
                f = f5;
                n4 = i;
            }
            if (f4 < f) {
                this.keyType.increaseBounds(spatial3, page.getKey(n4));
                MVRTreeMap.move(page, page2, n4);
                continue;
            }
            this.keyType.increaseBounds(spatial, page.getKey(n4));
            MVRTreeMap.move(page, page3, n4);
        }
        while (page3.getKeyCount() > 0) {
            MVRTreeMap.move(page3, page, 0);
        }
        return page2;
    }

    private Page<Spatial, V> newPage(boolean bl) {
        Page page = bl ? this.createEmptyLeaf() : this.createEmptyNode();
        this.registerUnsavedMemory(page.getMemory());
        return page;
    }

    private static <V> void move(Page<Spatial, V> page, Page<Spatial, V> page2, int n) {
        Spatial spatial = page.getKey(n);
        if (page.isLeaf()) {
            V v = page.getValue(n);
            page2.insertLeaf(0, spatial, v);
        } else {
            Page<Spatial, V> page3 = page.getChildPage(n);
            page2.insertNode(0, spatial, page3);
        }
        page.remove(n);
    }

    public void addNodeKeys(ArrayList<Spatial> arrayList, Page<Spatial, V> page) {
        if (page != null && !page.isLeaf()) {
            int n = page.getKeyCount();
            for (int i = 0; i < n; ++i) {
                arrayList.add(page.getKey(i));
                this.addNodeKeys(arrayList, page.getChildPage(i));
            }
        }
    }

    public boolean isQuadraticSplit() {
        return this.quadraticSplit;
    }

    public void setQuadraticSplit(boolean bl) {
        this.quadraticSplit = bl;
    }

    @Override
    protected int getChildPageCount(Page<Spatial, V> page) {
        return page.getRawChildPageCount() - 1;
    }

    @Override
    public String getType() {
        return "rtree";
    }

    public static class Builder<V>
    extends MVMap.BasicBuilder<MVRTreeMap<V>, Spatial, V> {
        private int dimensions = 2;

        public Builder() {
            this.setKeyType(new SpatialDataType(this.dimensions));
        }

        public Builder<V> dimensions(int n) {
            this.dimensions = n;
            this.setKeyType(new SpatialDataType(n));
            return this;
        }

        public Builder<V> valueType(DataType<? super V> dataType) {
            this.setValueType(dataType);
            return this;
        }

        @Override
        public MVRTreeMap<V> create(Map<String, Object> map) {
            return new MVRTreeMap(map, (SpatialDataType)this.getKeyType(), this.getValueType());
        }
    }

    private static final class ContainsRTreeCursor<V>
    extends RTreeCursor<V> {
        private final SpatialDataType keyType;

        public ContainsRTreeCursor(Page<Spatial, V> page, Spatial spatial, SpatialDataType spatialDataType) {
            super(page, spatial);
            this.keyType = spatialDataType;
        }

        @Override
        protected boolean check(boolean bl, Spatial spatial, Spatial spatial2) {
            return bl ? this.keyType.isInside(spatial, spatial2) : this.keyType.isOverlap(spatial, spatial2);
        }
    }

    private static final class IntersectsRTreeCursor<V>
    extends RTreeCursor<V> {
        private final SpatialDataType keyType;

        public IntersectsRTreeCursor(Page<Spatial, V> page, Spatial spatial, SpatialDataType spatialDataType) {
            super(page, spatial);
            this.keyType = spatialDataType;
        }

        @Override
        protected boolean check(boolean bl, Spatial spatial, Spatial spatial2) {
            return this.keyType.isOverlap(spatial, spatial2);
        }
    }

    public static abstract class RTreeCursor<V>
    implements Iterator<Spatial> {
        private final Spatial filter;
        private CursorPos<Spatial, V> pos;
        private Spatial current;
        private final Page<Spatial, V> root;
        private boolean initialized;

        protected RTreeCursor(Page<Spatial, V> page, Spatial spatial) {
            this.root = page;
            this.filter = spatial;
        }

        @Override
        public boolean hasNext() {
            if (!this.initialized) {
                this.pos = new CursorPos<Spatial, V>(this.root, 0, null);
                this.fetchNext();
                this.initialized = true;
            }
            return this.current != null;
        }

        public void skip(long l) {
            while (this.hasNext() && l-- > 0L) {
                this.fetchNext();
            }
        }

        @Override
        public Spatial next() {
            if (!this.hasNext()) {
                return null;
            }
            Spatial spatial = this.current;
            this.fetchNext();
            return spatial;
        }

        void fetchNext() {
            while (this.pos != null) {
                Page page = this.pos.page;
                if (page.isLeaf()) {
                    while (this.pos.index < page.getKeyCount()) {
                        Spatial spatial = (Spatial)page.getKey(this.pos.index++);
                        if (this.filter != null && !this.check(true, spatial, this.filter)) continue;
                        this.current = spatial;
                        return;
                    }
                } else {
                    boolean bl = false;
                    while (this.pos.index < page.getKeyCount()) {
                        int n;
                        ++this.pos.index;
                        Spatial spatial = (Spatial)page.getKey(n);
                        if (this.filter != null && !this.check(false, spatial, this.filter)) continue;
                        Page page2 = this.pos.page.getChildPage(n);
                        this.pos = new CursorPos(page2, 0, this.pos);
                        bl = true;
                        break;
                    }
                    if (bl) continue;
                }
                this.pos = this.pos.parent;
            }
            this.current = null;
        }

        protected abstract boolean check(boolean var1, Spatial var2, Spatial var3);
    }
}

