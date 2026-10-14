/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.apache.cxf.helpers.NSDecl;

public class NSStack {
    private static final String NS_PREFIX_PREFIX = "ns";
    private final List<List<NSDecl>> stack = new ArrayList<List<NSDecl>>();
    private List<NSDecl> top;
    private int size;
    private int nsPrefixCount = 1;

    public synchronized void push() {
        this.top = new ArrayList<NSDecl>();
        this.stack.add(this.top);
        ++this.size;
    }

    public synchronized void pop() {
        this.stack.remove(--this.size);
        this.top = null;
        if (this.size != 0) {
            this.top = this.stack.get(this.size - 1);
        }
    }

    public synchronized void add(String prefix, String uri) {
        this.top.add(new NSDecl(prefix, uri));
    }

    public synchronized String add(String uri) {
        String uniquePrefix = this.getPrefix(uri);
        if (uniquePrefix == null) {
            while (this.getURI(uniquePrefix = NS_PREFIX_PREFIX + this.nsPrefixCount++) != null) {
            }
            this.add(uniquePrefix, uri);
        }
        return uniquePrefix;
    }

    public synchronized String getPrefix(String uri) {
        for (int i = this.size - 1; i >= 0; --i) {
            List<NSDecl> scope = this.stack.get(i);
            ListIterator<NSDecl> lsIterator = scope.listIterator();
            while (lsIterator.hasNext()) {
                NSDecl nsd = lsIterator.next();
                if (!nsd.getUri().equals(uri)) continue;
                return nsd.getPrefix();
            }
        }
        return null;
    }

    public synchronized String getURI(String prefix) {
        for (int i = this.size - 1; i >= 0; --i) {
            List<NSDecl> scope = this.stack.get(i);
            ListIterator<NSDecl> lsIterator = scope.listIterator();
            while (lsIterator.hasNext()) {
                NSDecl nsd = lsIterator.next();
                if (!nsd.getPrefix().equals(prefix)) continue;
                return nsd.getUri();
            }
        }
        return null;
    }
}

