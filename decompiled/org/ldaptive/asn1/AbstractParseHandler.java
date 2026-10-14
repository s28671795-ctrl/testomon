/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.asn1;

import org.ldaptive.asn1.ParseHandler;

public abstract class AbstractParseHandler<T>
implements ParseHandler {
    private final T object;

    public AbstractParseHandler(T t2) {
        this.object = t2;
    }

    public T getObject() {
        return this.object;
    }
}

