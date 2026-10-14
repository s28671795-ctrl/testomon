/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Collection;
import org.jooq.Configuration;
import org.jooq.Meta;
import org.jooq.MetaProvider;
import org.jooq.Table;
import org.jooq.impl.CatalogMetaImpl;
import org.jooq.impl.Tools;

public class TableMetaProvider
implements MetaProvider {
    private final Configuration configuration;
    private final Table<?>[] tables;

    public TableMetaProvider(Configuration configuration, Table<?> ... tables) {
        this.configuration = configuration;
        this.tables = tables;
    }

    public TableMetaProvider(Configuration configuration, Collection<? extends Table<?>> tables) {
        this(configuration, tables.toArray(Tools.EMPTY_TABLE));
    }

    @Override
    public Meta provide() {
        return CatalogMetaImpl.filterTables(this.configuration, this.tables);
    }
}

