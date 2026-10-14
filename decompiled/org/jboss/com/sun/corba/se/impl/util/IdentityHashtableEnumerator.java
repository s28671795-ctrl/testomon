/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.util;

import java.util.Enumeration;
import java.util.NoSuchElementException;
import org.jboss.com.sun.corba.se.impl.util.IdentityHashtableEntry;

class IdentityHashtableEnumerator
implements Enumeration<Object> {
    boolean keys;
    int index;
    IdentityHashtableEntry[] table;
    IdentityHashtableEntry entry;

    IdentityHashtableEnumerator(IdentityHashtableEntry[] table, boolean keys2) {
        this.table = table;
        this.keys = keys2;
        this.index = table.length;
    }

    @Override
    public boolean hasMoreElements() {
        if (this.entry != null) {
            return true;
        }
        while (this.index-- > 0) {
            this.entry = this.table[this.index];
            if (this.entry == null) continue;
            return true;
        }
        return false;
    }

    @Override
    public Object nextElement() {
        if (this.entry == null) {
            while (this.index-- > 0 && (this.entry = this.table[this.index]) == null) {
            }
        }
        if (this.entry != null) {
            IdentityHashtableEntry e = this.entry;
            this.entry = e.next;
            return this.keys ? e.key : e.value;
        }
        throw new NoSuchElementException("IdentityHashtableEnumerator");
    }
}

