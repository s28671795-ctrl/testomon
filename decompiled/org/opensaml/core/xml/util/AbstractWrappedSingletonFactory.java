/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml.util;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.WeakHashMap;
import org.opensaml.core.xml.util.AbstractSingletonFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractWrappedSingletonFactory<Input, Output>
extends AbstractSingletonFactory<Input, Output> {
    private final Logger log = LoggerFactory.getLogger(AbstractWrappedSingletonFactory.class);
    private WeakHashMap<Input, WeakReference<Output>> map = new WeakHashMap();
    private HashSet<Output> outputSet;
    private boolean explicitRelease;

    public AbstractWrappedSingletonFactory() {
        this(false);
    }

    public AbstractWrappedSingletonFactory(boolean requireExplicitRelease) {
        this.explicitRelease = requireExplicitRelease;
        this.outputSet = new HashSet();
    }

    @Override
    public synchronized Output getInstance(Input input) {
        Object output = super.getInstance(input);
        if (this.explicitRelease && output != null) {
            this.log.trace("Explicit release was indicated, registering output instance to inhibit garbage collection");
            this.register(output);
        }
        return output;
    }

    public boolean isRequireExplicitRelease() {
        return this.explicitRelease;
    }

    public synchronized void release(Output output) {
        this.outputSet.remove(output);
    }

    public synchronized void releaseAll() {
        this.outputSet.clear();
    }

    protected synchronized void register(Output output) {
        this.outputSet.add(output);
    }

    @Override
    protected synchronized Output get(Input input) {
        WeakReference<Output> outputRef = this.map.get(input);
        if (outputRef != null) {
            this.log.trace("Input key mapped to a non-null WeakReference");
            if (outputRef.get() != null) {
                this.log.trace("WeakReference referent was non-null, returning referent");
                return (Output)outputRef.get();
            }
            this.log.trace("WeakReference referent was null, removing WeakReference entry from map");
            this.map.remove(input);
        }
        return null;
    }

    @Override
    protected synchronized void put(Input input, Output output) {
        this.map.put(input, new WeakReference<Output>(output));
    }
}

