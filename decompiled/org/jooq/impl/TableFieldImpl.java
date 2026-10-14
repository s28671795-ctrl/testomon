/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jooq.Binding;
import org.jooq.Clause;
import org.jooq.Comment;
import org.jooq.Context;
import org.jooq.DMLQuery;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.GeneratorStatementType;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.RowId;
import org.jooq.SQLDialect;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableLike;
import org.jooq.Update;
import org.jooq.impl.AbstractContext;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultGeneratorContext;
import org.jooq.impl.DefaultMetaProvider;
import org.jooq.impl.JoinTable;
import org.jooq.impl.QOM;
import org.jooq.impl.QualifiedRowid;
import org.jooq.impl.SchemaImpl;
import org.jooq.impl.ScopeMappable;
import org.jooq.impl.SimpleQueryPart;
import org.jooq.impl.TableImpl;
import org.jooq.impl.Tools;
import org.jooq.impl.TypedReference;
import org.jooq.impl.UpdateQueryImpl;
import org.jooq.tools.StringUtils;

class TableFieldImpl<R extends Record, T>
extends AbstractField<T>
implements TableField<R, T>,
SimpleQueryPart,
QOM.UNotYetImplemented,
TypedReference<T>,
ScopeMappable {
    private static final Clause[] CLAUSES = new Clause[]{Clause.FIELD, Clause.FIELD_REFERENCE};
    private final Table<R> table;

    TableFieldImpl(Name name, DataType<T> type, Comment comment) {
        this(name, type, TableFieldImpl.table(name), comment, type.getBinding());
    }

    TableFieldImpl(Name name, DataType<T> type, Table<R> table, Comment comment, Binding<?, T> binding) {
        super(TableFieldImpl.qualify(table, name), type, comment, binding);
        this.table = table;
    }

    private static final Table<Record> table(Name name) {
        return name.qualified() ? DSL.table(name.qualifier()) : null;
    }

    @Override
    public final Table<R> getTable() {
        return this.table;
    }

    @Override
    final boolean parenthesised(Context<?> ctx) {
        return true;
    }

    @Override
    public boolean declaresFields() {
        return this.getDataType().computedOnClientVirtual() || super.declaresFields();
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return CLAUSES;
    }

    @Override
    public final void accept(Context<?> ctx) {
        Table<?> root;
        if (ctx.family() == SQLDialect.JAVA) {
            if (DefaultMetaProvider.meta(ctx.configuration()).getTables(this.getQualifiedName().qualifier()).isEmpty()) {
                ctx.sql("field(").visit(this.getQualifiedName()).sql(")");
            } else {
                ctx.sql(Stream.of(this.getQualifiedName().getName()).map(n -> ((AbstractContext)ctx).applyNameCase((String)n)).collect(Collectors.joining(".")));
            }
            return;
        }
        if (this.getDataType().computedOnClientVirtual(ctx.configuration())) {
            Field computed = (Field)this.getDataType().generatedAlwaysAsGenerator().apply(new DefaultGeneratorContext(ctx.configuration(), ctx.data(), null, this.table, this, GeneratorStatementType.SELECT));
            if (ctx.declareAliases() && ctx.declareFields()) {
                ctx.visit((Field<?>)computed.as(this.getUnqualifiedName()));
            } else {
                ctx.visit(computed);
            }
        } else if (this.getType() == RowId.class) {
            if (this.table != null) {
                ctx.visit(new QualifiedRowid(this.table));
            } else {
                ctx.visit(DSL.table(DSL.name("")).rowid());
            }
        } else if (ctx.topLevelForLanguageContext() instanceof DMLQuery && this.table instanceof TableImpl && (root = ((TableImpl)this.table).rootChild()) != null && (!ctx.subquery() || root.equals(ctx.data(Tools.SimpleDataKey.DATA_DML_TARGET_TABLE)))) {
            if (ctx.topLevelForLanguageContext() instanceof Update && !UpdateQueryImpl.NO_SUPPORT_UPDATE_JOIN.contains((Object)ctx.dialect())) {
                this.accept1(ctx);
            } else {
                TableImpl t2 = (TableImpl)this.table;
                Table parent = (Table)t2.alias.wrapped;
                Field parentField = parent.field(this);
                ctx.visit(DSL.field(DSL.select(parentField).from((TableLike<?>)parent).where(JoinTable.onKey0(t2.childPath, t2.child, parent))));
            }
        } else {
            this.accept1(ctx);
        }
    }

    private final void accept1(Context<?> ctx) {
        ctx.data(Tools.BooleanDataKey.DATA_OMIT_CLAUSE_EVENT_EMISSION, true, c -> {
            if (c.qualify() && this.getTable() != null && !Boolean.FALSE.equals(ctx.data(Tools.ExtendedDataKey.DATA_RENDER_TABLE))) {
                c.visit(this.getTable()).sql('.');
            }
            c.visit(this.getUnqualifiedName());
        });
    }

    @Override
    public int hashCode() {
        if (this.getTable() == null) {
            return this.getUnqualifiedName().hashCode();
        }
        return StringUtils.defaultIfNull(this.getTable().getSchema(), SchemaImpl.DEFAULT_SCHEMA.get()).getQualifiedName().append(this.getTable().getUnqualifiedName()).append(this.getUnqualifiedName()).hashCode();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that instanceof TableField) {
            TableField other = (TableField)that;
            return StringUtils.equals(this.getTable(), other.getTable()) && StringUtils.equals(this.getName(), other.getName());
        }
        return super.equals(that);
    }
}

