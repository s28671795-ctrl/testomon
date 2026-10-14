/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.jooq.Configuration;
import org.jooq.ConstraintEnforcementStep;
import org.jooq.DSLContext;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.RowN;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.exception.DetachedException;
import org.jooq.impl.AbstractKey;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.InlineDerivedTable;
import org.jooq.impl.QOM;
import org.jooq.impl.ResultImpl;
import org.jooq.impl.Tools;

final class ReferenceImpl<R extends Record, O extends Record>
extends AbstractKey<R>
implements ForeignKey<R, O>,
QOM.UEmpty {
    private final UniqueKey<O> uk;
    private final TableField<O, ?>[] ukFields;

    ReferenceImpl(Table<R> table, Name name, TableField<R, ?>[] fkFields, UniqueKey<O> uk, TableField<O, ?>[] ukFields, boolean enforced) {
        super(table, name, fkFields, enforced);
        this.uk = uk;
        this.ukFields = ukFields;
    }

    @Override
    public final UniqueKey<O> getKey() {
        return this.uk;
    }

    @Override
    public final List<TableField<O, ?>> getKeyFields() {
        return Arrays.asList(this.ukFields);
    }

    @Override
    public final TableField<O, ?>[] getKeyFieldsArray() {
        return this.ukFields;
    }

    @Override
    public final O fetchParent(R record) {
        return (O)Tools.filterOne(this.fetchParents(new Record[]{record}));
    }

    @Override
    @SafeVarargs
    public final Result<O> fetchParents(R ... records) {
        return this.fetchParents((Collection<? extends R>)Tools.list(records));
    }

    @Override
    public final Result<O> fetchParents(Collection<? extends R> records) {
        if (records == null || records.size() == 0) {
            return new ResultImpl((Configuration)new DefaultConfiguration(), this.uk.getFields());
        }
        return ReferenceImpl.extractDSLContext(records).selectFrom(this.parents(records)).fetch();
    }

    @Override
    public final Result<R> fetchChildren(O record) {
        return this.fetchChildren((Collection<? extends O>)Tools.list(record));
    }

    @Override
    @SafeVarargs
    public final Result<R> fetchChildren(O ... records) {
        return this.fetchChildren((Collection<? extends O>)Tools.list(records));
    }

    @Override
    public final Result<R> fetchChildren(Collection<? extends O> records) {
        if (records == null || records.size() == 0) {
            return new ResultImpl((Configuration)new DefaultConfiguration(), this.getFields());
        }
        return ReferenceImpl.extractDSLContext(records).selectFrom(this.children(records)).fetch();
    }

    @Override
    public final Table<O> parent(R record) {
        return this.parents((Collection<? extends R>)Tools.list(record));
    }

    @Override
    @SafeVarargs
    public final Table<O> parents(R ... records) {
        return this.parents((Collection<? extends R>)Tools.list(records));
    }

    @Override
    public final Table<O> parents(Collection<? extends R> records) {
        return ReferenceImpl.table(records, this.uk.getTable(), this.uk.getFieldsArray(), this.getFieldsArray());
    }

    @Override
    public final Table<R> children(O record) {
        return this.children((Collection<? extends O>)Tools.list(record));
    }

    @Override
    @SafeVarargs
    public final Table<R> children(O ... records) {
        return this.children((Collection<? extends O>)Tools.list(records));
    }

    @Override
    public final Table<R> children(Collection<? extends O> records) {
        return ReferenceImpl.table(records, this.getTable(), this.getFieldsArray(), this.uk.getFieldsArray());
    }

    private static <R1 extends Record, R2 extends Record> Table<R1> table(Collection<? extends R2> records, Table<R1> table, TableField<R1, ?>[] fields1, TableField<R2, ?>[] fields2) {
        TableField<R1, ?>[] f1 = ReferenceImpl.truncate(fields1, fields2);
        TableField<R2, ?>[] f2 = ReferenceImpl.truncate(fields2, fields1);
        return new InlineDerivedTable<R1>(table, f1.length == 1 ? f1[0].in((Collection<?>)ReferenceImpl.extractValues(records, f2[0])) : DSL.row(f1).in(ReferenceImpl.extractRows(records, f2)));
    }

    private static <R extends Record> TableField<R, ?>[] truncate(TableField<R, ?>[] fields1, TableField<?, ?>[] fields2) {
        if (fields1.length <= fields2.length) {
            return fields1;
        }
        return Arrays.copyOf(fields1, fields2.length);
    }

    private static <R extends Record> List<Object> extractValues(Collection<? extends R> records, TableField<R, ?> field2) {
        return Tools.map(records, r -> r.get(field2));
    }

    private static <R extends Record> List<RowN> extractRows(Collection<? extends R> records, TableField<R, ?>[] fields) {
        return Tools.map(records, r -> {
            Object[] values2 = Tools.map(fields, f -> r.get(f), Object[]::new);
            return DSL.row(values2);
        });
    }

    private static <R extends Record> DSLContext extractDSLContext(Collection<? extends R> records) {
        Record first = (Record)Tools.first(records);
        if (first != null) {
            return DSL.using(first.configuration());
        }
        throw new DetachedException("Supply at least one attachable record");
    }

    @Override
    final ConstraintEnforcementStep constraint0() {
        return DSL.constraint(this.getName()).foreignKey(this.getFieldsArray()).references(this.uk.getTable(), this.getKeyFieldsArray());
    }
}

