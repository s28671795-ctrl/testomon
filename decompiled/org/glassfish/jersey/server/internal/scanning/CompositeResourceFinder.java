/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.scanning;

import java.io.InputStream;
import java.util.Deque;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.jersey.server.ResourceFinder;
import org.glassfish.jersey.server.internal.AbstractResourceFinderAdapter;
import org.glassfish.jersey.server.internal.LocalizationMessages;

public final class CompositeResourceFinder
extends AbstractResourceFinderAdapter {
    private static final Logger LOGGER = Logger.getLogger(CompositeResourceFinder.class.getName());
    private final Deque<ResourceFinder> stack = new LinkedList<ResourceFinder>();
    private ResourceFinder current = null;

    @Override
    public boolean hasNext() {
        if (this.current == null) {
            if (!this.stack.isEmpty()) {
                this.current = this.stack.pop();
            } else {
                return false;
            }
        }
        if (this.current.hasNext()) {
            return true;
        }
        if (!this.stack.isEmpty()) {
            this.current = this.stack.pop();
            return this.hasNext();
        }
        return false;
    }

    @Override
    public String next() {
        if (this.hasNext()) {
            return (String)this.current.next();
        }
        throw new NoSuchElementException();
    }

    @Override
    public InputStream open() {
        return this.current.open();
    }

    @Override
    public void close() {
        if (this.current != null) {
            this.stack.addFirst(this.current);
            this.current = null;
        }
        for (ResourceFinder finder : this.stack) {
            try {
                finder.close();
            }
            catch (RuntimeException e) {
                LOGGER.log(Level.CONFIG, LocalizationMessages.ERROR_CLOSING_FINDER(finder.getClass()), e);
            }
        }
        this.stack.clear();
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException();
    }

    public void push(ResourceFinder iterator2) {
        this.stack.push(iterator2);
    }
}

