/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.util.oracle;

import org.jooq.Internal;
import org.jooq.Pro;
import org.jooq.Schema;
import org.jooq.UDT;
import org.jooq.UDTRecord;
import org.jooq.util.oracle.Queue;

@Internal
@Pro
public class QueueImpl<R extends UDTRecord<R>>
implements Queue<R> {
    private final String name;
    private final Schema schema;
    private final UDT<R> type;

    public QueueImpl(String name, Schema schema, UDT<R> type) {
        this.name = name;
        this.schema = schema;
        this.type = type;
    }

    @Override
    public final String name() {
        return this.name;
    }

    @Override
    public final Schema schema() {
        return this.schema;
    }

    @Override
    public final UDT<R> type() {
        return this.type;
    }
}

