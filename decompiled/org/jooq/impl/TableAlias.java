/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.List;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;
import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Replacer;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.Traverser;
import org.jooq.UniqueKey;
import org.jooq.impl.AbstractTable;
import org.jooq.impl.Alias;
import org.jooq.impl.FieldsImpl;
import org.jooq.impl.QOM;
import org.jooq.impl.SimpleCheckQueryPart;
import org.jooq.impl.TableFieldImpl;
import org.jooq.impl.TableImpl;
import org.jooq.impl.Tools;

final class TableAlias<R extends Record>
extends AbstractTable<R>
implements QOM.TableAlias<R>,
SimpleCheckQueryPart {
    final Alias<Table<R>> alias;
    transient FieldsImpl<R> aliasedFields;

    TableAlias(Table<R> table, Name alias) {
        this(table, alias, null, (Context<?> c) -> false);
    }

    TableAlias(Table<R> table, Name alias, Predicate<Context<?>> wrapInParentheses) {
        this(table, alias, null, wrapInParentheses);
    }

    TableAlias(Table<R> table, Name alias, Name[] fieldAliases) {
        this(table, alias, fieldAliases, (Context<?> c) -> false);
    }

    TableAlias(Table<R> table, Name alias, Name[] fieldAliases, Predicate<Context<?>> wrapInParentheses) {
        super(table.getOptions(), alias, table.getSchema());
        this.alias = new Alias<TableAlias>((TableAlias)table, this, alias, fieldAliases, wrapInParentheses);
    }

    @Override
    public final boolean isSimple(Context<?> ctx) {
        return !ctx.declareTables();
    }

    private final FieldsImpl<R> initFieldAliases() {
        List<Field> result = Tools.map(this.alias.wrapped().fieldsRow().fields(), (f, i) -> new TableFieldImpl(this.alias.fieldAliases != null && this.alias.fieldAliases.length > i ? this.alias.fieldAliases[i] : f.getUnqualifiedName(), Tools.removeGenerator(Tools.CONFIG.get(), f.getDataType()), this, f.getCommentPart(), f.getBinding()));
        return new FieldsImpl(result);
    }

    Table<R> getAliasedTable() {
        if (this.alias != null) {
            return this.alias.wrapped();
        }
        return null;
    }

    @Override
    public final UniqueKey<R> getPrimaryKey() {
        return this.alias.wrapped().getPrimaryKey();
    }

    @Override
    public final List<UniqueKey<R>> getUniqueKeys() {
        return this.alias.wrapped().getUniqueKeys();
    }

    @Override
    public final List<ForeignKey<R, ?>> getReferences() {
        return this.alias.wrapped().getReferences();
    }

    @Override
    public final TableField<R, ?> getRecordVersion() {
        return this.alias.wrapped().getRecordVersion();
    }

    @Override
    public final TableField<R, ?> getRecordTimestamp() {
        return this.alias.wrapped().getRecordTimestamp();
    }

    @Override
    public final void accept(Context<?> ctx) {
        ctx.visit(this.alias);
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return null;
    }

    @Override
    public final Table<R> as(Name as) {
        return this.alias.wrapped().as(as);
    }

    @Override
    public final Table<R> as(Name as, Name ... fieldAliases) {
        return this.alias.wrapped().as(as, fieldAliases);
    }

    @Override
    public final boolean declaresTables() {
        return true;
    }

    @Override
    final FieldsImpl<R> fields0() {
        if (this.aliasedFields == null) {
            this.aliasedFields = this.initFieldAliases();
        }
        return this.aliasedFields;
    }

    @Override
    public Name getQualifiedName() {
        return this.getUnqualifiedName();
    }

    @Override
    public Class<? extends R> getRecordType() {
        return this.alias.wrapped().getRecordType();
    }

    @Override
    public final Table<R> $table() {
        return this.alias.wrapped();
    }

    @Override
    public final Table<R> $aliased() {
        return this.$table();
    }

    @Override
    @NotNull
    public final Name $alias() {
        return this.getUnqualifiedName();
    }

    public final <T> T $traverse(Traverser<?, T> traverser) {
        return QOM.traverse(traverser, this, this.$table(), this.$alias());
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$table(), this.$alias(), (t2, n) -> new TableAlias<R>(t2, (Name)n, this.alias.fieldAliases, this.alias.wrapInParentheses), replacer);
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that instanceof TableAlias) {
            TableAlias t2 = (TableAlias)that;
            return this.getUnqualifiedName().equals(t2.getUnqualifiedName());
        }
        if (that instanceof TableImpl) {
            TableImpl t3 = (TableImpl)that;
            if (t3.$alias() != null) {
                return this.getUnqualifiedName().equals(t3.$alias());
            }
            if (t3.getSchema() == null) {
                return this.getUnqualifiedName().equals(t3.getQualifiedName());
            }
        }
        return super.equals(that);
    }
}

