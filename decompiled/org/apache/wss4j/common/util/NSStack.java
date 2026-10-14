/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.util;

import java.util.ArrayList;
import java.util.List;
import org.apache.wss4j.common.util.Mapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NSStack {
    private static final Logger LOG = LoggerFactory.getLogger(NSStack.class);
    private Mapping[] stack;
    private int top;
    private int iterator;
    private int currentDefaultNS = -1;
    private final boolean traceEnabled = LOG.isTraceEnabled();

    public NSStack() {
        this.stack = new Mapping[32];
        this.stack[0] = null;
    }

    public void push() {
        ++this.top;
        if (this.top >= this.stack.length) {
            Mapping[] newstack = new Mapping[this.stack.length * 2];
            System.arraycopy(this.stack, 0, newstack, 0, this.stack.length);
            this.stack = newstack;
        }
        if (this.traceEnabled) {
            LOG.trace("NSPush (" + this.stack.length + ")");
        }
        this.stack[this.top] = null;
    }

    public void pop() {
        this.clearFrame();
        --this.top;
        if (this.top < this.currentDefaultNS) {
            this.currentDefaultNS = this.top;
            while (this.currentDefaultNS > 0 && (this.stack[this.currentDefaultNS] == null || this.stack[this.currentDefaultNS].getPrefix().length() != 0)) {
                --this.currentDefaultNS;
            }
        }
        if (this.top == 0) {
            if (this.traceEnabled) {
                LOG.trace("NSPop (empty)");
            }
            return;
        }
        if (this.traceEnabled) {
            LOG.trace("NSPop (" + this.stack.length + ")");
        }
    }

    public List<Mapping> cloneFrame() {
        if (this.stack[this.top] == null) {
            return null;
        }
        ArrayList<Mapping> clone = new ArrayList<Mapping>();
        Mapping map = this.topOfFrame();
        while (map != null) {
            clone.add(map);
            map = this.next();
        }
        return clone;
    }

    private void clearFrame() {
        while (this.stack[this.top] != null) {
            --this.top;
        }
    }

    public Mapping topOfFrame() {
        this.iterator = this.top;
        while (this.stack[this.iterator] != null) {
            --this.iterator;
        }
        ++this.iterator;
        return this.next();
    }

    public Mapping next() {
        if (this.iterator > this.top) {
            return null;
        }
        return this.stack[this.iterator++];
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void add(String namespaceURI, String prefix) {
        int idx = this.top;
        try {
            int cursor = this.top;
            while (this.stack[cursor] != null) {
                if (this.stack[cursor].getPrefix().equals(prefix)) {
                    this.stack[cursor].setNamespaceURI(namespaceURI);
                    idx = cursor;
                    return;
                }
                --cursor;
            }
            this.push();
            this.stack[this.top] = new Mapping(namespaceURI, prefix);
            idx = this.top;
        }
        finally {
            if (prefix.length() == 0) {
                this.currentDefaultNS = idx;
            }
        }
    }

    public String getPrefix(String namespaceURI, boolean noDefault) {
        if (namespaceURI == null || namespaceURI.equals("")) {
            return null;
        }
        int hash = namespaceURI.hashCode();
        if (!noDefault && this.currentDefaultNS > 0 && this.stack[this.currentDefaultNS] != null && namespaceURI.equals(this.stack[this.currentDefaultNS].getNamespaceURI())) {
            return "";
        }
        block0: for (int cursor = this.top; cursor > 0; --cursor) {
            Mapping map = this.stack[cursor];
            if (map == null || map.getNamespaceHash() != hash || !map.getNamespaceURI().equals(namespaceURI)) continue;
            String possiblePrefix = map.getPrefix();
            if (noDefault && possiblePrefix.length() == 0) continue;
            int ppHash = possiblePrefix.hashCode();
            int cursor2 = this.top;
            while (cursor2 != cursor) {
                map = this.stack[cursor2];
                if (map != null && ppHash == map.getPrefixHash() && possiblePrefix.equals(map.getPrefix())) continue block0;
                --cursor2;
            }
            return possiblePrefix;
        }
        return null;
    }

    public String getPrefix(String namespaceURI) {
        return this.getPrefix(namespaceURI, false);
    }

    public String getNamespaceURI(String prefix) {
        String pfix = prefix;
        if (pfix == null) {
            pfix = "";
        }
        int hash = pfix.hashCode();
        for (int cursor = this.top; cursor > 0; --cursor) {
            Mapping map = this.stack[cursor];
            if (map == null || map.getPrefixHash() != hash || !map.getPrefix().equals(pfix)) continue;
            return map.getNamespaceURI();
        }
        return null;
    }

    public void dump(String dumpPrefix) {
        for (int cursor = this.top; cursor > 0; --cursor) {
            Mapping map = this.stack[cursor];
            if (map == null) {
                LOG.trace(dumpPrefix + "stackFrame00");
                continue;
            }
            LOG.trace(dumpPrefix + map.getNamespaceURI() + " -> " + map.getPrefix());
        }
    }
}

