/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.encoding;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import org.jboss.com.sun.corba.se.impl.encoding.ByteBufferWithInfo;

public class BufferQueue {
    private LinkedList<ByteBufferWithInfo> list = new LinkedList();

    public void enqueue(ByteBufferWithInfo item) {
        this.list.addLast(item);
    }

    public ByteBufferWithInfo dequeue() throws NoSuchElementException {
        return this.list.removeFirst();
    }

    public int size() {
        return this.list.size();
    }

    public void push(ByteBufferWithInfo item) {
        this.list.addFirst(item);
    }
}

