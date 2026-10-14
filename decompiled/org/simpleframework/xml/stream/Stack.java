/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.stream;

import java.util.ArrayList;

class Stack<T>
extends ArrayList<T> {
    public Stack(int size) {
        super(size);
    }

    public T pop() {
        int size = this.size();
        if (size <= 0) {
            return null;
        }
        return (T)this.remove(size - 1);
    }

    public T top() {
        int size = this.size();
        if (size <= 0) {
            return null;
        }
        return (T)this.get(size - 1);
    }

    public T bottom() {
        int size = this.size();
        if (size <= 0) {
            return null;
        }
        return (T)this.get(0);
    }

    public T push(T value) {
        this.add(value);
        return value;
    }
}

