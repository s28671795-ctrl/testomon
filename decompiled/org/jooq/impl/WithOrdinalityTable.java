/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.List;
import java.util.Set;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Replacer;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.Table;
import org.jooq.TableLike;
import org.jooq.Traverser;
import org.jooq.impl.AbstractTable;
import org.jooq.impl.ArrayOfValues;
import org.jooq.impl.ArrayTable;
import org.jooq.impl.AutoAlias;
import org.jooq.impl.DSL;
import org.jooq.impl.FieldsImpl;
import org.jooq.impl.FunctionTable;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.RecordImplN;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.impl.Tools;

final class WithOrdinalityTable<R extends Record>
extends AbstractTable<R>
implements AutoAlias<Table<R>>,
QOM.WithOrdinalityTable<R> {
    static final Set<SQLDialect> NO_SUPPORT_STANDARD = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.VERTICA);
    static final Set<SQLDialect> NO_SUPPORT_TVF = SQLDialect.supportedBy(SQLDialect.BIGQUERY, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.TERADATA);
    static final Set<SQLDialect> NO_SUPPORT_TABLE_EXPRESSIONS = SQLDialect.supportedBy(SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB);
    final AbstractTable<?> delegate;

    WithOrdinalityTable(AbstractTable<?> delegate) {
        super(delegate.getOptions(), delegate.getQualifiedName(), delegate.getSchema());
        this.delegate = delegate;
    }

    @Override
    public final boolean declaresTables() {
        return true;
    }

    @Override
    public final Class<? extends R> getRecordType() {
        return RecordImplN.class;
    }

    @Override
    public final List<ForeignKey<R, ?>> getReferences() {
        return this.delegate.getReferences();
    }

    @Override
    final FieldsImpl<R> fields0() {
        FieldsImpl r = new FieldsImpl(this.delegate.fields0().fields);
        for (int i = 0; i < r.fields.length; ++i) {
            r.fields[i] = DSL.field(this.delegate.getUnqualifiedName().append(r.fields[i].getUnqualifiedName()), r.fields[i].getDataType());
        }
        r.add(DSL.field(Names.N_ORDINAL, SQLDataType.BIGINT));
        return r;
    }

    @Override
    public final Table<R> autoAlias(Context<?> ctx, Table<R> t2) {
        if (t2 != this && t2 instanceof AutoAlias) {
            AutoAlias a = (AutoAlias)((Object)t2);
            return a.autoAlias(ctx, t2);
        }
        if (t2 instanceof QOM.Aliasable) {
            Object[] fields;
            QOM.Aliasable a = (QOM.Aliasable)((Object)t2);
            Name alias = a.$alias();
            if (alias == null) {
                alias = ((Table)a.$aliased()).getUnqualifiedName();
            }
            if (Tools.isEmpty(fields = t2.fields())) {
                return t2.as(alias);
            }
            return t2.as((Table<?>)DSL.table(alias), (Field<?>[])fields);
        }
        return null;
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (this.delegate instanceof ArrayTable || this.delegate instanceof ArrayOfValues) {
            if (NO_SUPPORT_STANDARD.contains((Object)ctx.dialect())) {
                this.acceptEmulation(ctx);
            } else {
                this.acceptStandard(ctx);
            }
        } else if (this.delegate instanceof FunctionTable && NO_SUPPORT_TVF.contains((Object)ctx.dialect())) {
            if (NO_SUPPORT_TVF.contains((Object)ctx.dialect())) {
                this.acceptEmulation(ctx);
            } else {
                this.acceptStandard(ctx);
            }
        } else if (this.delegate instanceof TableImpl && ((TableImpl)this.delegate).parameters != null) {
            if (NO_SUPPORT_TVF.contains((Object)ctx.dialect())) {
                this.acceptEmulation(ctx);
            } else {
                this.acceptStandard(ctx);
            }
        } else if (NO_SUPPORT_TABLE_EXPRESSIONS.contains((Object)ctx.dialect())) {
            this.acceptEmulation(ctx);
        } else if (ctx.family() == SQLDialect.BIGQUERY) {
            this.acceptBigQueryEmulation(ctx);
        } else {
            Select<?> s2 = Tools.extractSelectFromDerivedTable(this.delegate);
            if (s2 != null) {
                Tools.visitSubquery(ctx, s2, 1, true);
                ctx.sql(' ').visit(Keywords.K_WITH).sql(' ').visit(Keywords.K_ORDINALITY);
            } else {
                this.acceptStandard(ctx);
            }
        }
    }

    private final void acceptStandard(Context<?> ctx) {
        ctx.visit(this.delegate).sql(' ').visit(Keywords.K_WITH).sql(' ').visit(Keywords.K_ORDINALITY);
    }

    private final void acceptEmulation(Context<?> ctx) {
        Tools.visitSubquery(ctx, switch (ctx.family()) {
            case SQLDialect.ORACLE -> DSL.select(this.delegate.fields()).select(DSL.rownum().as(Names.N_ORDINAL)).from((TableLike<?>)this.delegate);
            default -> DSL.select(this.delegate.fields()).select(DSL.rowNumber().over().as(Names.N_ORDINAL)).from((TableLike<?>)this.delegate);
        }, 1, true);
    }

    @Pro
    private final void acceptBigQueryEmulation(Context<?> ctx) {
        Tools.visitSubquery(ctx, DSL.select(this.delegate.fields()).select(DSL.field(Names.N_OFFSET).plus(DSL.one()).as(Names.N_ORDINAL)).from("{0} with offset", this.delegate), 1, true);
    }

    @Override
    public final Table<?> $table() {
        return this.delegate;
    }

    @Override
    public final WithOrdinalityTable<?> $table(Table<?> newTable) {
        return new WithOrdinalityTable<R>((AbstractTable)newTable);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.delegate);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.delegate, WithOrdinalityTable::new, replacer);
    }

    @Override
    public final Table<R> $aliased() {
        return new WithOrdinalityTable<R>((AbstractTable)this.delegate.$aliased());
    }

    @Override
    public final Name $alias() {
        return this.delegate.$alias();
    }

    static {
        NO_SUPPORT_TVF.addAll(NO_SUPPORT_STANDARD);
        NO_SUPPORT_TABLE_EXPRESSIONS.addAll(NO_SUPPORT_TVF);
    }
}

