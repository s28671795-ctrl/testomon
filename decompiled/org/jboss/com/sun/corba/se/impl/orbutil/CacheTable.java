/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.orbutil;

import org.jboss.com.sun.corba.se.impl.logging.ORBUtilSystemException;
import org.jboss.com.sun.corba.se.spi.orb.ORB;

public class CacheTable {
    private boolean noReverseMap;
    static final int INITIAL_SIZE = 16;
    static final int MAX_SIZE = 0x40000000;
    int size;
    int entryCount;
    private Entry[] map;
    private Entry[] rmap;
    private ORBUtilSystemException wrapper;

    private CacheTable() {
    }

    public CacheTable(ORB orb, boolean u) {
        this.wrapper = ORBUtilSystemException.get(orb, "rpc.encoding");
        this.noReverseMap = u;
        this.size = 16;
        this.entryCount = 0;
        this.initTables();
    }

    private void initTables() {
        this.map = new Entry[this.size];
        this.rmap = this.noReverseMap ? null : new Entry[this.size];
    }

    private void grow() {
        if (this.size == 0x40000000) {
            return;
        }
        Entry[] oldMap = this.map;
        int oldSize = this.size;
        this.size <<= 1;
        this.initTables();
        for (int i = 0; i < oldSize; ++i) {
            Entry e = oldMap[i];
            while (e != null) {
                this.put_table(e.key, e.val);
                e = e.next;
            }
        }
    }

    private int moduloTableSize(int h2) {
        h2 += ~(h2 << 9);
        h2 ^= h2 >>> 14;
        h2 += h2 << 4;
        h2 ^= h2 >>> 10;
        return h2 & this.size - 1;
    }

    private int hash(Object key) {
        return this.moduloTableSize(System.identityHashCode(key));
    }

    private int hash(int val) {
        return this.moduloTableSize(val);
    }

    public final void put(Object key, int val) {
        if (this.put_table(key, val)) {
            ++this.entryCount;
            if (this.entryCount > this.size * 3 / 4) {
                this.grow();
            }
        }
    }

    private boolean put_table(Object key, int val) {
        int index = this.hash(key);
        Entry e = this.map[index];
        while (e != null) {
            if (e.key == key) {
                if (e.val != val) {
                    throw this.wrapper.duplicateIndirectionOffset();
                }
                return false;
            }
            e = e.next;
        }
        Entry newEntry = new Entry(key, val);
        newEntry.next = this.map[index];
        this.map[index] = newEntry;
        if (!this.noReverseMap) {
            int rindex = this.hash(val);
            newEntry.rnext = this.rmap[rindex];
            this.rmap[rindex] = newEntry;
        }
        return true;
    }

    public final boolean containsKey(Object key) {
        return this.getVal(key) != -1;
    }

    public final int getVal(Object key) {
        int index = this.hash(key);
        Entry e = this.map[index];
        while (e != null) {
            if (e.key == key) {
                return e.val;
            }
            e = e.next;
        }
        return -1;
    }

    public final boolean containsVal(int val) {
        return this.getKey(val) != null;
    }

    public final boolean containsOrderedVal(int val) {
        return this.containsVal(val);
    }

    public final Object getKey(int val) {
        int index = this.hash(val);
        Entry e = this.rmap[index];
        while (e != null) {
            if (e.val == val) {
                return e.key;
            }
            e = e.rnext;
        }
        return null;
    }

    public void done() {
        this.map = null;
        this.rmap = null;
    }

    class Entry {
        Object key;
        int val;
        Entry next;
        Entry rnext;

        public Entry(Object k, int v) {
            this.key = k;
            this.val = v;
            this.next = null;
            this.rnext = null;
        }
    }
}

