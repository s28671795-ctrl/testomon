/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.stream;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import org.simpleframework.xml.stream.OutputNode;

class OutputStack
extends ArrayList<OutputNode> {
    private final Set active;

    public OutputStack(Set active) {
        this.active = active;
    }

    public OutputNode pop() {
        int size = this.size();
        if (size <= 0) {
            return null;
        }
        return this.purge(size - 1);
    }

    public OutputNode top() {
        int size = this.size();
        if (size <= 0) {
            return null;
        }
        return (OutputNode)this.get(size - 1);
    }

    public OutputNode bottom() {
        int size = this.size();
        if (size <= 0) {
            return null;
        }
        return (OutputNode)this.get(0);
    }

    public OutputNode push(OutputNode value) {
        this.active.add(value);
        this.add(value);
        return value;
    }

    public OutputNode purge(int index) {
        OutputNode node = (OutputNode)this.remove(index);
        if (node != null) {
            this.active.remove(node);
        }
        return node;
    }

    @Override
    public Iterator<OutputNode> iterator() {
        return new Sequence();
    }

    private class Sequence
    implements Iterator<OutputNode> {
        private int cursor;

        public Sequence() {
            this.cursor = OutputStack.this.size();
        }

        @Override
        public OutputNode next() {
            if (this.hasNext()) {
                return (OutputNode)OutputStack.this.get(--this.cursor);
            }
            return null;
        }

        @Override
        public boolean hasNext() {
            return this.cursor > 0;
        }

        @Override
        public void remove() {
            OutputStack.this.purge(this.cursor);
        }
    }
}

