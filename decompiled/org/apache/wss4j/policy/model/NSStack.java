/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class NSStack {
    private Mapping[] stack = new Mapping[32];
    private int top;
    private int iterator;
    private int currentDefaultNS = -1;

    NSStack() {
        this.stack[0] = null;
    }

    public void push() {
        ++this.top;
        if (this.top >= this.stack.length) {
            Mapping[] newstack = new Mapping[this.stack.length * 2];
            System.arraycopy(this.stack, 0, newstack, 0, this.stack.length);
            this.stack = newstack;
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
            return;
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

    private static class Mapping
    implements Serializable {
        private static final long serialVersionUID = 4598721541118599293L;
        private String namespaceURI;
        private int namespaceHash;
        private String prefix;
        private int prefixHash;

        Mapping(String namespaceURI, String prefix) {
            this.setPrefix(prefix);
            this.setNamespaceURI(namespaceURI);
        }

        public String getNamespaceURI() {
            return this.namespaceURI;
        }

        public int getNamespaceHash() {
            return this.namespaceHash;
        }

        public void setNamespaceURI(String namespaceURI) {
            this.namespaceURI = namespaceURI;
            this.namespaceHash = namespaceURI.hashCode();
        }

        public String getPrefix() {
            return this.prefix;
        }

        public int getPrefixHash() {
            return this.prefixHash;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
            this.prefixHash = prefix.hashCode();
        }
    }
}

