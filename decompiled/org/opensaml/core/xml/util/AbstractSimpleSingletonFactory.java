/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml.util;

import java.util.WeakHashMap;
import org.opensaml.core.xml.util.AbstractSingletonFactory;

public abstract class AbstractSimpleSingletonFactory<Input, Output>
extends AbstractSingletonFactory<Input, Output> {
    private WeakHashMap<Input, Output> map = new WeakHashMap();

    @Override
    protected synchronized Output get(Input input) {
        return this.map.get(input);
    }

    @Override
    protected synchronized void put(Input input, Output output) {
        this.map.put(input, output);
    }
}

