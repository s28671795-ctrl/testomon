/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.staxutils;

import java.util.ArrayList;
import java.util.EmptyStackException;

public class FastStack<T>
extends ArrayList<T> {
    private static final long serialVersionUID = -6459503295618120689L;

    public void push(T o) {
        this.add(o);
    }

    public T pop() {
        if (this.empty()) {
            throw new EmptyStackException();
        }
        return (T)this.remove(this.size() - 1);
    }

    public boolean empty() {
        return this.size() == 0;
    }

    public T peek() {
        if (this.empty()) {
            throw new EmptyStackException();
        }
        return (T)this.get(this.size() - 1);
    }
}

