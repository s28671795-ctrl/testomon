/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import org.jooq.Catalog;
import org.jooq.Configuration;
import org.jooq.DDLExportConfiguration;
import org.jooq.Queries;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.UniqueKey;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.AbstractMeta;
import org.jooq.impl.DDL;
import org.jooq.impl.InformationSchemaExport;
import org.jooq.util.xml.jaxb.InformationSchema;

final class TableMetaImpl
extends AbstractMeta {
    private final Table<?>[] tables;

    TableMetaImpl(Configuration configuration, Table<?>[] tables) {
        super(configuration);
        this.tables = tables;
    }

    @Override
    final List<Catalog> getCatalogs0() {
        LinkedHashSet<Catalog> result = new LinkedHashSet<Catalog>();
        for (Table<?> table : this.tables) {
            if (table.getSchema() == null || table.getSchema().getCatalog() == null) continue;
            result.add(table.getSchema().getCatalog());
        }
        return new ArrayList<Catalog>(result);
    }

    @Override
    final List<Schema> getSchemas0() {
        LinkedHashSet<Schema> result = new LinkedHashSet<Schema>();
        for (Table<?> table : this.tables) {
            if (table.getSchema() == null) continue;
            result.add(table.getSchema());
        }
        return new ArrayList<Schema>(result);
    }

    @Override
    final List<Table<?>> getTables0() {
        return Collections.unmodifiableList(Arrays.asList(this.tables));
    }

    @Override
    final List<UniqueKey<?>> getPrimaryKeys0() {
        ArrayList result = new ArrayList();
        for (Table<?> table : this.tables) {
            if (table.getPrimaryKey() == null) continue;
            result.add(table.getPrimaryKey());
        }
        return result;
    }

    @Override
    public Queries ddl(DDLExportConfiguration exportConfiguration) throws DataAccessException {
        return new DDL(this.dsl(), exportConfiguration).queries(this.tables);
    }

    @Override
    public InformationSchema informationSchema() throws DataAccessException {
        return InformationSchemaExport.exportTables(this.configuration(), this.getTables());
    }
}

