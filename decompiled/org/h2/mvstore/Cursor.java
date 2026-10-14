/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.h2.mvstore.CursorPos;
import org.h2.mvstore.MVMap;
import org.h2.mvstore.Page;
import org.h2.mvstore.RootReference;

public final class Cursor<K, V>
implements Iterator<K> {
    private final boolean reverse;
    private final K to;
    private CursorPos<K, V> cursorPos;
    private CursorPos<K, V> keeper;
    private K current;
    private K last;
    private V lastValue;
    private Page<K, V> lastPage;

    public Cursor(RootReference<K, V> rootReference, K k, K k2) {
        this(rootReference, k, k2, false);
    }

    public Cursor(RootReference<K, V> rootReference, K k, K k2, boolean bl) {
        this.lastPage = rootReference.root;
        this.cursorPos = Cursor.traverseDown(this.lastPage, k, bl);
        this.to = k2;
        this.reverse = bl;
    }

    @Override
    public boolean hasNext() {
        if (this.cursorPos != null) {
            int n;
            int n2 = n = this.reverse ? -1 : 1;
            while (this.current == null) {
                CursorPos<K, V> cursorPos;
                Page page = this.cursorPos.page;
                int n3 = this.cursorPos.index;
                if (this.reverse ? n3 < 0 : n3 >= Cursor.upperBound(page)) {
                    cursorPos = this.cursorPos;
                    this.cursorPos = this.cursorPos.parent;
                    if (this.cursorPos == null) {
                        return false;
                    }
                    cursorPos.parent = this.keeper;
                    this.keeper = cursorPos;
                } else {
                    while (!page.isLeaf()) {
                        page = page.getChildPage(n3);
                        int n4 = n3 = this.reverse ? Cursor.upperBound(page) - 1 : 0;
                        if (this.keeper == null) {
                            this.cursorPos = new CursorPos(page, n3, this.cursorPos);
                            continue;
                        }
                        cursorPos = this.keeper;
                        this.keeper = this.keeper.parent;
                        cursorPos.parent = this.cursorPos;
                        cursorPos.page = page;
                        cursorPos.index = n3;
                        this.cursorPos = cursorPos;
                    }
                    if (this.reverse ? n3 >= 0 : n3 < page.getKeyCount()) {
                        cursorPos = page.getKey(n3);
                        if (this.to != null && Integer.signum(page.map.getKeyType().compare(cursorPos, this.to)) == n) {
                            return false;
                        }
                        this.last = cursorPos;
                        this.current = this.last;
                        this.lastValue = page.getValue(n3);
                        this.lastPage = page;
                    }
                }
                this.cursorPos.index += n;
            }
        }
        return this.current != null;
    }

    @Override
    public K next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        this.current = null;
        return this.last;
    }

    public K getKey() {
        return this.last;
    }

    public V getValue() {
        return this.lastValue;
    }

    Page<K, V> getPage() {
        return this.lastPage;
    }

    public void skip(long l) {
        if (l < 10L) {
            while (l-- > 0L && this.hasNext()) {
                this.next();
            }
        } else if (this.hasNext()) {
            CursorPos cursorPos;
            assert (this.cursorPos != null);
            CursorPos<K, V> cursorPos2 = this.cursorPos;
            while ((cursorPos = cursorPos2.parent) != null) {
                cursorPos2 = cursorPos;
            }
            Page page = cursorPos2.page;
            MVMap mVMap = page.map;
            long l2 = mVMap.getKeyIndex(this.next());
            this.last = mVMap.getKey(l2 + (this.reverse ? -l : l));
            this.cursorPos = Cursor.traverseDown(page, this.last, this.reverse);
        }
    }

    static <K, V> CursorPos<K, V> traverseDown(Page<K, V> page, K k, boolean bl) {
        CursorPos<K, V> cursorPos = k != null ? CursorPos.traverseDown(page, k) : (bl ? page.getAppendCursorPos(null) : page.getPrependCursorPos(null));
        int n = cursorPos.index;
        if (n < 0) {
            n ^= 0xFFFFFFFF;
            if (bl) {
                --n;
            }
            cursorPos.index = n;
        }
        return cursorPos;
    }

    private static <K, V> int upperBound(Page<K, V> page) {
        return page.isLeaf() ? page.getKeyCount() : page.map.getChildPageCount(page);
    }
}

