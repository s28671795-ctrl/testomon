/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.function.Consumer;
import org.jooq.Record;

@Deprecated(forRemoval=true, since="3.15")
@FunctionalInterface
public interface RecordHandler<R extends Record>
extends Consumer<R> {
    public void next(R var1);

    @Override
    default public void accept(R record) {
        this.next(record);
    }
}

