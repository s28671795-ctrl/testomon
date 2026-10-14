/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler.util.iterator;

import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;
import org.codehaus.commons.compiler.util.iterator.EnumerationIterator;
import org.codehaus.commons.nullanalysis.Nullable;

public class TraversingIterator
implements Iterator<Object> {
    private final Stack<Iterator<?>> nest = new Stack();
    @Nullable
    private Object nextElement;
    private boolean nextElementRead;

    public TraversingIterator(Iterator<?> delegate) {
        this.nest.push(delegate);
    }

    @Override
    public boolean hasNext() {
        return this.nextElementRead || this.readNext();
    }

    @Override
    public Object next() {
        if (!this.nextElementRead && !this.readNext()) {
            throw new NoSuchElementException();
        }
        this.nextElementRead = false;
        assert (this.nextElement != null);
        return this.nextElement;
    }

    private boolean readNext() {
        while (!this.nest.empty()) {
            Iterator<?> it = this.nest.peek();
            if (!it.hasNext()) {
                this.nest.pop();
                continue;
            }
            Object o = it.next();
            if (o instanceof Iterator) {
                this.nest.push((Iterator)o);
                continue;
            }
            if (o instanceof Object[]) {
                this.nest.push(Arrays.asList((Object[])o).iterator());
                continue;
            }
            if (o instanceof Collection) {
                this.nest.push(((Collection)o).iterator());
                continue;
            }
            if (o instanceof Enumeration) {
                this.nest.push(new EnumerationIterator((Enumeration)o));
                continue;
            }
            this.nextElement = o;
            this.nextElementRead = true;
            return true;
        }
        return false;
    }

    @Override
    public void remove() {
        this.nest.peek().remove();
    }
}

