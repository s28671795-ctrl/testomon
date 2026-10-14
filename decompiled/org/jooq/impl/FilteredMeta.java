/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import org.jooq.Catalog;
import org.jooq.Check;
import org.jooq.Domain;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.Meta;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Sequence;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UDT;
import org.jooq.UniqueKey;
import org.jooq.impl.AbstractMeta;
import org.jooq.impl.CatalogImpl;
import org.jooq.impl.Internal;
import org.jooq.impl.SchemaImpl;
import org.jooq.impl.TableImpl;
import org.jooq.impl.Tools;

final class FilteredMeta
extends AbstractMeta {
    private final AbstractMeta meta;
    private final Predicate<? super Catalog> catalogFilter;
    private final Predicate<? super Schema> schemaFilter;
    private final Predicate<? super Table<?>> tableFilter;
    private final Predicate<? super Domain<?>> domainFilter;
    private final Predicate<? super Sequence<?>> sequenceFilter;
    private final Predicate<? super UniqueKey<?>> primaryKeyFilter;
    private final Predicate<? super UniqueKey<?>> uniqueKeyFilter;
    private final Predicate<? super ForeignKey<?, ?>> foreignKeyFilter;
    private final Predicate<? super Index> indexFilter;

    FilteredMeta(AbstractMeta meta, Predicate<? super Catalog> catalogFilter, Predicate<? super Schema> schemaFilter, Predicate<? super Table<?>> tableFilter, Predicate<? super Domain<?>> domainFilter, Predicate<? super Sequence<?>> sequenceFilter, Predicate<? super UniqueKey<?>> primaryKeyFilter, Predicate<? super UniqueKey<?>> uniqueKeyFilter, Predicate<? super ForeignKey<?, ?>> foreignKeyFilter, Predicate<? super Index> indexFilter) {
        super(meta.configuration());
        this.meta = meta;
        this.catalogFilter = catalogFilter;
        this.schemaFilter = schemaFilter;
        this.tableFilter = tableFilter;
        this.domainFilter = domainFilter;
        this.sequenceFilter = sequenceFilter;
        this.primaryKeyFilter = primaryKeyFilter;
        this.uniqueKeyFilter = uniqueKeyFilter;
        this.foreignKeyFilter = foreignKeyFilter;
        this.indexFilter = indexFilter;
    }

    @Override
    final List<Catalog> getCatalogs0() {
        ArrayList<Catalog> result = new ArrayList<Catalog>();
        for (Catalog c : this.meta.getCatalogs()) {
            if (this.catalogFilter != null && !this.catalogFilter.test(c)) continue;
            result.add(new FilteredCatalog(c));
        }
        return result;
    }

    @Override
    public final Meta filterCatalogs(Predicate<? super Catalog> filter2) {
        return new FilteredMeta(this.meta, this.catalogFilter != null ? new And<Catalog>(this.catalogFilter, filter2) : filter2, this.schemaFilter, this.tableFilter, this.domainFilter, this.sequenceFilter, this.primaryKeyFilter, this.uniqueKeyFilter, this.foreignKeyFilter, this.indexFilter);
    }

    @Override
    public final Meta filterSchemas(Predicate<? super Schema> filter2) {
        return new FilteredMeta(this.meta, this.catalogFilter, this.schemaFilter != null ? new And<Schema>(this.schemaFilter, filter2) : filter2, this.tableFilter, this.domainFilter, this.sequenceFilter, this.primaryKeyFilter, this.uniqueKeyFilter, this.foreignKeyFilter, this.indexFilter);
    }

    @Override
    public final Meta filterTables(Predicate<? super Table<?>> filter2) {
        return new FilteredMeta(this.meta, this.catalogFilter, this.schemaFilter, this.tableFilter != null ? new And(this.tableFilter, filter2) : filter2, this.domainFilter, this.sequenceFilter, this.primaryKeyFilter, this.uniqueKeyFilter, this.foreignKeyFilter, this.indexFilter);
    }

    @Override
    public final Meta filterDomains(Predicate<? super Domain<?>> filter2) {
        return new FilteredMeta(this.meta, this.catalogFilter, this.schemaFilter, this.tableFilter, this.domainFilter != null ? new And(this.domainFilter, filter2) : filter2, this.sequenceFilter, this.primaryKeyFilter, this.uniqueKeyFilter, this.foreignKeyFilter, this.indexFilter);
    }

    @Override
    public final Meta filterSequences(Predicate<? super Sequence<?>> filter2) {
        return new FilteredMeta(this.meta, this.catalogFilter, this.schemaFilter, this.tableFilter, this.domainFilter, this.sequenceFilter != null ? new And(this.sequenceFilter, filter2) : filter2, this.primaryKeyFilter, this.uniqueKeyFilter, this.foreignKeyFilter, this.indexFilter);
    }

    @Override
    public final Meta filterPrimaryKeys(Predicate<? super UniqueKey<?>> filter2) {
        return new FilteredMeta(this.meta, this.catalogFilter, this.schemaFilter, this.tableFilter, this.domainFilter, this.sequenceFilter, this.primaryKeyFilter != null ? new And(this.primaryKeyFilter, filter2) : filter2, this.uniqueKeyFilter, this.foreignKeyFilter, this.indexFilter);
    }

    @Override
    public final Meta filterUniqueKeys(Predicate<? super UniqueKey<?>> filter2) {
        return new FilteredMeta(this.meta, this.catalogFilter, this.schemaFilter, this.tableFilter, this.domainFilter, this.sequenceFilter, this.primaryKeyFilter, this.uniqueKeyFilter != null ? new And(this.uniqueKeyFilter, filter2) : filter2, this.foreignKeyFilter, this.indexFilter);
    }

    @Override
    public final Meta filterForeignKeys(Predicate<? super ForeignKey<?, ?>> filter2) {
        return new FilteredMeta(this.meta, this.catalogFilter, this.schemaFilter, this.tableFilter, this.domainFilter, this.sequenceFilter, this.primaryKeyFilter, this.uniqueKeyFilter, this.foreignKeyFilter != null ? new And(this.foreignKeyFilter, filter2) : filter2, this.indexFilter);
    }

    @Override
    public final Meta filterIndexes(Predicate<? super Index> filter2) {
        return new FilteredMeta(this.meta, this.catalogFilter, this.schemaFilter, this.tableFilter, this.domainFilter, this.sequenceFilter, this.primaryKeyFilter, this.uniqueKeyFilter, this.foreignKeyFilter, (Predicate<? super Index>)(this.indexFilter != null ? new And<Index>(this.indexFilter, filter2) : filter2));
    }

    private class FilteredCatalog
    extends CatalogImpl {
        private final Catalog delegate;
        private transient List<Schema> schemas;

        private FilteredCatalog(Catalog delegate) {
            super(delegate.getQualifiedName(), delegate.getCommentPart());
            this.delegate = delegate;
        }

        @Override
        public final List<Schema> getSchemas() {
            if (this.schemas == null) {
                this.schemas = new ArrayList<Schema>();
                for (Schema s2 : this.delegate.getSchemas()) {
                    if (FilteredMeta.this.schemaFilter != null && !FilteredMeta.this.schemaFilter.test(s2)) continue;
                    this.schemas.add(new FilteredSchema(this, s2));
                }
            }
            return Collections.unmodifiableList(this.schemas);
        }
    }

    private static class And<Q extends QueryPart>
    implements Predicate<Q> {
        private final Predicate<? super Q> p1;
        private final Predicate<? super Q> p2;

        And(Predicate<? super Q> p1, Predicate<? super Q> p2) {
            this.p1 = p1;
            this.p2 = p2;
        }

        @Override
        public final boolean test(Q q) {
            return this.p1.test(q) && this.p2.test(q);
        }
    }

    private class FilteredTable<R extends Record>
    extends TableImpl<R> {
        private final Table<R> delegate;
        private transient List<Index> indexes;
        private transient UniqueKey<R> primaryKey;
        private transient List<UniqueKey<R>> uniqueKeys;
        private transient List<ForeignKey<R, ?>> references;

        private FilteredTable(FilteredSchema schema, Table<R> delegate) {
            super(delegate.getQualifiedName(), schema, null, null, delegate.getCommentPart(), delegate.getOptions());
            this.delegate = delegate;
            for (Field<?> field2 : delegate.fields()) {
                FilteredTable.createField(field2.getQualifiedName(), field2.getDataType(), this, field2.getComment());
            }
        }

        @Override
        public final List<Index> getIndexes() {
            if (this.indexes == null) {
                this.indexes = new ArrayList<Index>();
                for (Index index : this.delegate.getIndexes()) {
                    if (FilteredMeta.this.indexFilter != null && !FilteredMeta.this.indexFilter.test(index)) continue;
                    this.indexes.add(index);
                }
            }
            return Collections.unmodifiableList(this.indexes);
        }

        private final void initKeys() {
            if (this.uniqueKeys == null) {
                this.uniqueKeys = new ArrayList<UniqueKey<R>>();
                for (UniqueKey<R> uk : this.delegate.getUniqueKeys()) {
                    if (FilteredMeta.this.uniqueKeyFilter != null && !FilteredMeta.this.uniqueKeyFilter.test(uk)) continue;
                    this.uniqueKeys.add(this.key(uk));
                }
                UniqueKey<R> pk = this.delegate.getPrimaryKey();
                if (pk != null && (FilteredMeta.this.primaryKeyFilter == null || FilteredMeta.this.primaryKeyFilter.test(pk))) {
                    this.primaryKey = this.key(pk);
                }
            }
        }

        private final UniqueKey<R> key(UniqueKey<R> key) {
            return Internal.createUniqueKey(this, key.getName(), Tools.map(key.getFieldsArray(), f -> (TableField)this.field(f), TableField[]::new), key.enforced());
        }

        @Override
        public final UniqueKey<R> getPrimaryKey() {
            this.initKeys();
            return this.primaryKey;
        }

        @Override
        public final List<UniqueKey<R>> getUniqueKeys() {
            this.initKeys();
            return Collections.unmodifiableList(this.uniqueKeys);
        }

        @Override
        public final List<ForeignKey<R, ?>> getReferences() {
            if (this.references == null) {
                this.references = new ArrayList();
                for (ForeignKey<R, ?> fk : this.delegate.getReferences()) {
                    UniqueKey<?> uk;
                    if (FilteredMeta.this.foreignKeyFilter != null && !FilteredMeta.this.foreignKeyFilter.test(fk) || (uk = FilteredMeta.this.lookupUniqueKey(fk)) == null || uk.isPrimary() && FilteredMeta.this.primaryKeyFilter != null && !FilteredMeta.this.primaryKeyFilter.test(uk) || !uk.isPrimary() && FilteredMeta.this.uniqueKeyFilter != null && !FilteredMeta.this.uniqueKeyFilter.test(uk)) continue;
                    this.references.add(AbstractMeta.copyFK(this, uk, fk));
                }
            }
            return Collections.unmodifiableList(this.references);
        }

        @Override
        public final List<Check<R>> getChecks() {
            return this.delegate.getChecks();
        }
    }

    private class FilteredSchema
    extends SchemaImpl {
        private final Schema delegate;
        private transient List<Domain<?>> domains;
        private transient List<Table<?>> tables;
        private transient List<Sequence<?>> sequences;

        private FilteredSchema(FilteredCatalog catalog, Schema delegate) {
            super(delegate.getQualifiedName(), (Catalog)catalog, delegate.getCommentPart());
            this.delegate = delegate;
        }

        @Override
        public final List<Domain<?>> getDomains() {
            if (this.domains == null) {
                this.domains = new ArrayList();
                for (Domain<?> d : this.delegate.getDomains()) {
                    if (FilteredMeta.this.domainFilter != null && !FilteredMeta.this.domainFilter.test(d)) continue;
                    this.domains.add(d);
                }
            }
            return Collections.unmodifiableList(this.domains);
        }

        @Override
        public final List<Table<?>> getTables() {
            if (this.tables == null) {
                this.tables = new ArrayList();
                for (Table<?> t2 : this.delegate.getTables()) {
                    if (FilteredMeta.this.tableFilter != null && !FilteredMeta.this.tableFilter.test(t2)) continue;
                    this.tables.add(new FilteredTable(this, t2));
                }
            }
            return Collections.unmodifiableList(this.tables);
        }

        @Override
        public final List<Sequence<?>> getSequences() {
            if (this.sequences == null) {
                this.sequences = new ArrayList();
                for (Sequence<?> t2 : this.delegate.getSequences()) {
                    if (FilteredMeta.this.sequenceFilter != null && !FilteredMeta.this.sequenceFilter.test(t2)) continue;
                    this.sequences.add(t2);
                }
            }
            return Collections.unmodifiableList(this.sequences);
        }

        @Override
        public final List<UDT<?>> getUDTs() {
            return this.delegate.getUDTs();
        }
    }
}

