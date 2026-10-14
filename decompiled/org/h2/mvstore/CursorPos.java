/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore;

import org.h2.mvstore.Page;

public final class CursorPos<K, V> {
    public Page<K, V> page;
    public int index;
    public CursorPos<K, V> parent;

    public CursorPos(Page<K, V> page, int n, CursorPos<K, V> cursorPos) {
        this.page = page;
        this.index = n;
        this.parent = cursorPos;
    }

    static <K, V> CursorPos<K, V> traverseDown(Page<K, V> page, K k) {
        CursorPos<K, V> cursorPos = null;
        while (!page.isLeaf()) {
            int n = page.binarySearch(k) + 1;
            if (n < 0) {
                n = -n;
            }
            cursorPos = new CursorPos<K, V>(page, n, cursorPos);
            page = page.getChildPage(n);
        }
        return new CursorPos<K, V>(page, page.binarySearch(k), cursorPos);
    }

    int processRemovalInfo(long l) {
        int n = 0;
        CursorPos<K, V> cursorPos = this;
        while (cursorPos != null) {
            n += cursorPos.page.removePage(l);
            cursorPos = cursorPos.parent;
        }
        return n;
    }

    public String toString() {
        return "CursorPos{page=" + this.page + ", index=" + this.index + ", parent=" + this.parent + '}';
    }
}

