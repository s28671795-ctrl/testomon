/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jooq.Catalog;
import org.jooq.Check;
import org.jooq.Domain;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.Meta;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Sequence;
import org.jooq.SortField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UDT;
import org.jooq.UDTRecord;
import org.jooq.UniqueKey;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.AbstractMeta;
import org.jooq.impl.CatalogImpl;
import org.jooq.impl.DomainImpl;
import org.jooq.impl.IndexImpl;
import org.jooq.impl.Internal;
import org.jooq.impl.SchemaImpl;
import org.jooq.impl.SequenceImpl;
import org.jooq.impl.TableImpl;
import org.jooq.impl.Tools;
import org.jooq.impl.UDTImpl;

final class Snapshot
extends AbstractMeta {
    private Meta delegate;

    Snapshot(Meta meta) {
        super(meta.configuration());
        this.delegate = meta;
        this.getCatalogs();
        this.delegate = null;
        this.resolveReferences();
    }

    private final void resolveReferences() {
        for (Catalog catalog : this.getCatalogs()) {
            ((SnapshotCatalog)catalog).resolveReferences();
        }
    }

    @Override
    final List<Catalog> getCatalogs0() throws DataAccessException {
        return Tools.map(this.delegate.getCatalogs(), x$0 -> new SnapshotCatalog((Catalog)x$0));
    }

    private class SnapshotCatalog
    extends CatalogImpl {
        private final List<SnapshotSchema> schemas;

        SnapshotCatalog(Catalog catalog) {
            super(catalog.getQualifiedName(), catalog.getCommentPart());
            this.schemas = Tools.map(catalog.getSchemas(), s2 -> new SnapshotSchema(this, (Schema)s2));
        }

        private final void resolveReferences() {
            for (SnapshotSchema schema : this.schemas) {
                schema.resolveReferences();
            }
        }

        @Override
        public final List<Schema> getSchemas() {
            return Collections.unmodifiableList(this.schemas);
        }
    }

    private class SnapshotUDT<R extends UDTRecord<R>>
    extends UDTImpl<R> {
        SnapshotUDT(SnapshotSchema schema, UDT<R> udt) {
            super(udt.getName(), schema, udt.getPackage(), udt.isSynthetic());
        }
    }

    private class SnapshotSequence<T extends Number>
    extends SequenceImpl<T> {
        SnapshotSequence(SnapshotSchema schema, Sequence<T> sequence2) {
            super(sequence2.getQualifiedName(), schema, sequence2.getDataType(), false, sequence2.getStartWith(), sequence2.getIncrementBy(), sequence2.getMinvalue(), sequence2.getMaxvalue(), sequence2.getCycle(), sequence2.getCache());
        }
    }

    private class SnapshotTable<R extends Record>
    extends TableImpl<R> {
        private final List<Index> indexes;
        private final List<UniqueKey<R>> uniqueKeys;
        private UniqueKey<R> primaryKey;
        private final List<ForeignKey<R, ?>> foreignKeys;
        private final List<Check<R>> checks;

        SnapshotTable(SnapshotSchema schema, Table<R> table) {
            super(table.getQualifiedName(), schema, null, null, table.getCommentPart(), table.getOptions());
            for (Field<?> field2 : table.fields()) {
                SnapshotTable.createField(field2.getUnqualifiedName(), field2.getDataType(), this, field2.getComment());
            }
            this.indexes = Tools.map(table.getIndexes(), index -> new IndexImpl(index.getQualifiedName(), this, Tools.map(index.getFields(), field2 -> {
                Field<?> f = this.field(field2.getName());
                return f != null ? f.sort(field2.getOrder()) : field2;
            }, SortField[]::new), index.getWhere(), index.getUnique()));
            this.uniqueKeys = Tools.map(table.getUniqueKeys(), uk -> Internal.createUniqueKey(this, uk.getQualifiedName(), this.fields(uk.getFieldsArray()), uk.enforced()));
            UniqueKey<R> pk = table.getPrimaryKey();
            if (pk != null) {
                this.primaryKey = Internal.createUniqueKey(this, pk.getQualifiedName(), this.fields(pk.getFieldsArray()), pk.enforced());
            }
            this.foreignKeys = new ArrayList(table.getReferences());
            this.checks = new ArrayList<Check<R>>(table.getChecks());
        }

        @Deprecated
        private final TableField<R, ?>[] fields(TableField<R, ?>[] tableFields) {
            return Tools.map(tableFields, f -> (TableField)this.field(f.getName()), TableField[]::new);
        }

        final void resolveReferences() {
            for (int i = 0; i < this.foreignKeys.size(); ++i) {
                ForeignKey<R, ?> fk = this.foreignKeys.get(i);
                UniqueKey<?> uk = Snapshot.this.lookupUniqueKey(fk);
                if (uk == null) {
                    this.foreignKeys.remove(i);
                    continue;
                }
                this.foreignKeys.set(i, AbstractMeta.copyFK(this, uk, fk));
            }
        }

        @Override
        public final List<Index> getIndexes() {
            return Collections.unmodifiableList(this.indexes);
        }

        @Override
        public final List<UniqueKey<R>> getUniqueKeys() {
            return Collections.unmodifiableList(this.uniqueKeys);
        }

        @Override
        public final UniqueKey<R> getPrimaryKey() {
            return this.primaryKey;
        }

        @Override
        public final List<ForeignKey<R, ?>> getReferences() {
            return Collections.unmodifiableList(this.foreignKeys);
        }

        @Override
        public final List<Check<R>> getChecks() {
            return Collections.unmodifiableList(this.checks);
        }
    }

    private class SnapshotDomain<T>
    extends DomainImpl<T> {
        SnapshotDomain(SnapshotSchema schema, Domain<T> domain) {
            super(schema, domain.getQualifiedName(), domain.getDataType(), domain.getChecks().toArray(Tools.EMPTY_CHECK));
        }
    }

    private class SnapshotSchema
    extends SchemaImpl {
        private final List<SnapshotDomain<?>> domains;
        private final List<SnapshotTable<?>> tables;
        private final List<SnapshotSequence<?>> sequences;
        private final List<SnapshotUDT<?>> udts;

        SnapshotSchema(SnapshotCatalog catalog, Schema schema) {
            super(schema.getQualifiedName(), (Catalog)catalog, schema.getCommentPart());
            this.domains = Tools.map(schema.getDomains(), d -> new SnapshotDomain(this, d));
            this.tables = Tools.map(schema.getTables(), t2 -> new SnapshotTable(this, t2));
            this.sequences = Tools.map(schema.getSequences(), s2 -> new SnapshotSequence(this, s2));
            this.udts = Tools.map(schema.getUDTs(), u -> new SnapshotUDT(this, u));
        }

        final void resolveReferences() {
            for (SnapshotTable<?> table : this.tables) {
                table.resolveReferences();
            }
        }

        @Override
        public final List<Domain<?>> getDomains() {
            return Collections.unmodifiableList(this.domains);
        }

        @Override
        public final List<Table<?>> getTables() {
            return Collections.unmodifiableList(this.tables);
        }

        @Override
        public final List<Sequence<?>> getSequences() {
            return Collections.unmodifiableList(this.sequences);
        }

        @Override
        public final List<UDT<?>> getUDTs() {
            return Collections.unmodifiableList(this.udts);
        }
    }
}

