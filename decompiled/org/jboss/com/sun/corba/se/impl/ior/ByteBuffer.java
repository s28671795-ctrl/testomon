/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.ior;

public class ByteBuffer {
    protected byte[] elementData;
    protected int elementCount;
    protected int capacityIncrement;

    public ByteBuffer(int initialCapacity, int capacityIncrement) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
        this.elementData = new byte[initialCapacity];
        this.capacityIncrement = capacityIncrement;
    }

    public ByteBuffer(int initialCapacity) {
        this(initialCapacity, 0);
    }

    public ByteBuffer() {
        this(200);
    }

    public void trimToSize() {
        int oldCapacity = this.elementData.length;
        if (this.elementCount < oldCapacity) {
            byte[] oldData = this.elementData;
            this.elementData = new byte[this.elementCount];
            System.arraycopy(oldData, 0, this.elementData, 0, this.elementCount);
        }
    }

    private void ensureCapacityHelper(int minCapacity) {
        int oldCapacity = this.elementData.length;
        if (minCapacity > oldCapacity) {
            int newCapacity;
            byte[] oldData = this.elementData;
            int n = newCapacity = this.capacityIncrement > 0 ? oldCapacity + this.capacityIncrement : oldCapacity * 2;
            if (newCapacity < minCapacity) {
                newCapacity = minCapacity;
            }
            this.elementData = new byte[newCapacity];
            System.arraycopy(oldData, 0, this.elementData, 0, this.elementCount);
        }
    }

    public int capacity() {
        return this.elementData.length;
    }

    public int size() {
        return this.elementCount;
    }

    public boolean isEmpty() {
        return this.elementCount == 0;
    }

    public void append(byte value) {
        this.ensureCapacityHelper(this.elementCount + 1);
        this.elementData[this.elementCount++] = value;
    }

    public void append(int value) {
        this.ensureCapacityHelper(this.elementCount + 4);
        this.doAppend(value);
    }

    private void doAppend(int value) {
        int current = value;
        for (int ctr = 0; ctr < 4; ++ctr) {
            this.elementData[this.elementCount + ctr] = (byte)(current & 0xFF);
            current >>= 8;
        }
        this.elementCount += 4;
    }

    public void append(String value) {
        byte[] data = value.getBytes();
        this.ensureCapacityHelper(this.elementCount + data.length + 4);
        this.doAppend(data.length);
        System.arraycopy(data, 0, this.elementData, this.elementCount, data.length);
        this.elementCount += data.length;
    }

    public byte[] toArray() {
        return this.elementData;
    }
}

