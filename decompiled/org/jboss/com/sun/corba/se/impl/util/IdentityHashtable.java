/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.util;

import java.util.Dictionary;
import java.util.Enumeration;
import org.jboss.com.sun.corba.se.impl.util.IdentityHashtableEntry;
import org.jboss.com.sun.corba.se.impl.util.IdentityHashtableEnumerator;

public final class IdentityHashtable
extends Dictionary<Object, Object> {
    private transient IdentityHashtableEntry[] table;
    private transient int count;
    private int threshold;
    private float loadFactor;

    public IdentityHashtable(int initialCapacity, float loadFactor) {
        if (initialCapacity <= 0 || (double)loadFactor <= 0.0) {
            throw new IllegalArgumentException();
        }
        this.loadFactor = loadFactor;
        this.table = new IdentityHashtableEntry[initialCapacity];
        this.threshold = (int)((float)initialCapacity * loadFactor);
    }

    public IdentityHashtable(int initialCapacity) {
        this(initialCapacity, 0.75f);
    }

    public IdentityHashtable() {
        this(101, 0.75f);
    }

    @Override
    public int size() {
        return this.count;
    }

    @Override
    public boolean isEmpty() {
        return this.count == 0;
    }

    @Override
    public Enumeration<Object> keys() {
        return new IdentityHashtableEnumerator(this.table, true);
    }

    @Override
    public Enumeration<Object> elements() {
        return new IdentityHashtableEnumerator(this.table, false);
    }

    public boolean contains(Object value) {
        if (value == null) {
            throw new NullPointerException();
        }
        IdentityHashtableEntry[] tab = this.table;
        int i = tab.length;
        while (i-- > 0) {
            IdentityHashtableEntry e = tab[i];
            while (e != null) {
                if (e.value == value) {
                    return true;
                }
                e = e.next;
            }
        }
        return false;
    }

    public boolean containsKey(Object key) {
        IdentityHashtableEntry[] tab = this.table;
        int hash = System.identityHashCode(key);
        int index = (hash & Integer.MAX_VALUE) % tab.length;
        IdentityHashtableEntry e = tab[index];
        while (e != null) {
            if (e.hash == hash && e.key == key) {
                return true;
            }
            e = e.next;
        }
        return false;
    }

    @Override
    public Object get(Object key) {
        IdentityHashtableEntry[] tab = this.table;
        int hash = System.identityHashCode(key);
        int index = (hash & Integer.MAX_VALUE) % tab.length;
        IdentityHashtableEntry e = tab[index];
        while (e != null) {
            if (e.hash == hash && e.key == key) {
                return e.value;
            }
            e = e.next;
        }
        return null;
    }

    protected void rehash() {
        int oldCapacity = this.table.length;
        IdentityHashtableEntry[] oldTable = this.table;
        int newCapacity = oldCapacity * 2 + 1;
        IdentityHashtableEntry[] newTable = new IdentityHashtableEntry[newCapacity];
        this.threshold = (int)((float)newCapacity * this.loadFactor);
        this.table = newTable;
        int i = oldCapacity;
        while (i-- > 0) {
            IdentityHashtableEntry old = oldTable[i];
            while (old != null) {
                IdentityHashtableEntry e = old;
                old = old.next;
                int index = (e.hash & Integer.MAX_VALUE) % newCapacity;
                e.next = newTable[index];
                newTable[index] = e;
            }
        }
    }

    @Override
    public Object put(Object key, Object value) {
        if (value == null) {
            throw new NullPointerException();
        }
        IdentityHashtableEntry[] tab = this.table;
        int hash = System.identityHashCode(key);
        int index = (hash & Integer.MAX_VALUE) % tab.length;
        IdentityHashtableEntry e = tab[index];
        while (e != null) {
            if (e.hash == hash && e.key == key) {
                Object old = e.value;
                e.value = value;
                return old;
            }
            e = e.next;
        }
        if (this.count >= this.threshold) {
            this.rehash();
            return this.put(key, value);
        }
        e = new IdentityHashtableEntry();
        e.hash = hash;
        e.key = key;
        e.value = value;
        e.next = tab[index];
        tab[index] = e;
        ++this.count;
        return null;
    }

    @Override
    public Object remove(Object key) {
        IdentityHashtableEntry[] tab = this.table;
        int hash = System.identityHashCode(key);
        int index = (hash & Integer.MAX_VALUE) % tab.length;
        IdentityHashtableEntry e = tab[index];
        IdentityHashtableEntry prev = null;
        while (e != null) {
            if (e.hash == hash && e.key == key) {
                if (prev != null) {
                    prev.next = e.next;
                } else {
                    tab[index] = e.next;
                }
                --this.count;
                return e.value;
            }
            prev = e;
            e = e.next;
        }
        return null;
    }

    public void clear() {
        IdentityHashtableEntry[] tab = this.table;
        int index = tab.length;
        while (--index >= 0) {
            tab[index] = null;
        }
        this.count = 0;
    }

    public String toString() {
        int max = this.size() - 1;
        StringBuffer buf = new StringBuffer();
        Enumeration<Object> k = this.keys();
        Enumeration<Object> e = this.elements();
        buf.append("{");
        for (int i = 0; i <= max; ++i) {
            String s1 = k.nextElement().toString();
            String s2 = e.nextElement().toString();
            buf.append(s1 + "=" + s2);
            if (i >= max) continue;
            buf.append(", ");
        }
        buf.append("}");
        return buf.toString();
    }
}

