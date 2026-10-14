/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.List;
import org.jooq.Catalog;
import org.jooq.Schema;
import org.jooq.Sequence;
import org.jooq.Table;
import org.jooq.UDT;
import org.jooq.impl.SchemaImpl;

final class RenamedSchema
extends SchemaImpl {
    private final Schema delegate;

    RenamedSchema(Catalog catalog, Schema delegate, String rename) {
        super(rename, catalog);
        this.delegate = delegate;
    }

    @Override
    public final List<Table<?>> getTables() {
        return this.delegate.getTables();
    }

    @Override
    public final List<UDT<?>> getUDTs() {
        return this.delegate.getUDTs();
    }

    @Override
    public final List<Sequence<?>> getSequences() {
        return this.delegate.getSequences();
    }
}

