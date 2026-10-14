/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function3;
import org.jooq.Name;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Record1;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.Table;
import org.jooq.TableLike;
import org.jooq.TableOptions;
import org.jooq.conf.ParamType;
import org.jooq.impl.AbstractTable;
import org.jooq.impl.AutoAlias;
import org.jooq.impl.DSL;
import org.jooq.impl.FieldsImpl;
import org.jooq.impl.Internal;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.RecordImpl1;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableAlias;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;

final class GenerateSeries
extends AbstractTable<Record1<Integer>>
implements AutoAlias<Table<Record1<Integer>>>,
QOM.GenerateSeries<Integer> {
    private static final Set<SQLDialect> EMULATE_WITH_RECURSIVE = SQLDialect.supportedUntil(SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER2017, SQLDialect.SYBASE, SQLDialect.TERADATA);
    private static final Set<SQLDialect> EMULATE_SYSTEM_RANGE = SQLDialect.supportedBy(SQLDialect.H2);
    @Pro
    private static final Set<SQLDialect> EMULATE_CONNECT_BY = SQLDialect.supportedBy(SQLDialect.CUBRID, SQLDialect.EXASOL, SQLDialect.INFORMIX, SQLDialect.ORACLE);
    @Pro
    private static final Set<SQLDialect> EMULATE_GENERATE_ARRAY = SQLDialect.supportedBy(SQLDialect.BIGQUERY);
    @Pro
    private static final Set<SQLDialect> EMULATE_GENERATOR = SQLDialect.supportedBy(SQLDialect.SNOWFLAKE);
    @Pro
    private static final Set<SQLDialect> NO_IMPLICIT_TABLE_NAME = SQLDialect.supportedBy(SQLDialect.COCKROACHDB, SQLDialect.SQLSERVER2022);
    private final Field<Integer> from;
    private final Field<Integer> to;
    private final Field<Integer> step;
    private final Name name;

    GenerateSeries(Field<Integer> from, Field<Integer> to) {
        this(from, to, null);
    }

    GenerateSeries(Field<Integer> from, Field<Integer> to, Field<Integer> step) {
        this(from, to, step, Names.N_GENERATE_SERIES);
    }

    GenerateSeries(Field<Integer> from, Field<Integer> to, Field<Integer> step, Name name) {
        super(TableOptions.expression(), name);
        this.from = from;
        this.to = to;
        this.step = step;
        this.name = name;
    }

    @Override
    public Table<Record1<Integer>> as(Name alias) {
        return new TableAlias<Record1<Integer>>(new GenerateSeries(this.from, this.to, this.step, alias), alias);
    }

    @Override
    public Table<Record1<Integer>> as(Name alias, Name ... fieldAliases) {
        return new TableAlias<Record1<Integer>>(new GenerateSeries(this.from, this.to, this.step, alias), alias, fieldAliases);
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (EMULATE_WITH_RECURSIVE.contains((Object)ctx.dialect())) {
            Field<Integer> f = DSL.field(this.name, SQLDataType.INTEGER);
            Tools.visitSubquery(ctx, DSL.withRecursive(this.name, this.name).as(DSL.select(this.from).unionAll((Select)DSL.select(Internal.iadd(f, this.step == null ? DSL.inline(1) : this.step)).from(this.name).where(f.lt(this.to)))).select(f).from(this.name), 1);
        } else if (EMULATE_SYSTEM_RANGE.contains((Object)ctx.dialect())) {
            if (this.step == null) {
                ctx.visit(Names.N_SYSTEM_RANGE).sql('(').visit(this.from).sql(", ").visit(this.to).sql(')');
            } else {
                ctx.visit(Names.N_SYSTEM_RANGE).sql('(').visit(this.from).sql(", ").visit(this.to).sql(", ");
                ctx.paramType(ParamType.INLINED, c -> c.visit(this.step));
                ctx.sql(')');
            }
        } else if (EMULATE_CONNECT_BY.contains((Object)ctx.dialect())) {
            Tools.visitSubquery(ctx, this.step == null ? DSL.select(Internal.iadd(DSL.level(), Internal.isub(this.from, DSL.one())).as(this.name)).connectBy(DSL.level().le(Internal.isub(Internal.iadd(this.to, DSL.one()), this.from))) : DSL.select(Internal.iadd(Internal.imul(DSL.level(), this.step), Internal.isub(this.from, this.step)).as(this.name)).connectBy(Internal.imul(DSL.level(), this.step).le(Internal.isub(Internal.iadd(this.to, this.step), this.from))), 1);
        } else if (EMULATE_GENERATE_ARRAY.contains((Object)ctx.dialect())) {
            if (this.step == null) {
                ctx.visit(DSL.function(Names.N_UNNEST, this.getDataType(), DSL.function(Names.N_GENERATE_ARRAY, this.from.getDataType().getArrayDataType(), this.from, this.to)));
            } else {
                ctx.visit(DSL.function(Names.N_UNNEST, this.getDataType(), DSL.function(Names.N_GENERATE_ARRAY, this.from.getDataType().getArrayDataType(), this.from, this.to, this.step)));
            }
        } else if (EMULATE_GENERATOR.contains((Object)ctx.dialect())) {
            Tools.visitSubquery(ctx, this.step == null ? DSL.select(Internal.iadd(DSL.rownum(), Internal.isub(this.from, DSL.one())).as(this.name)).from((TableLike<?>)DSL.table("{0}({1}(rowcount => {2}))", new QueryPart[]{Keywords.K_TABLE, Names.N_GENERATOR, Internal.isub(this.to, Internal.isub(this.from, DSL.one()))})) : DSL.select(Internal.iadd(Internal.imul(DSL.rownum(), this.step), Internal.isub(this.from, this.step)).as(this.name)).from((TableLike<?>)DSL.table("{0}({1}(rowcount => {2}))", new QueryPart[]{Keywords.K_TABLE, Names.N_GENERATOR, Internal.idiv(Internal.isub(this.to, Internal.isub(this.from, this.step)), this.step)})), 1);
        } else if (ctx.family() == SQLDialect.JAVA) {
            if (this.step == null) {
                ctx.sql("generateSeries(").visit(Val.getJavaValue(this.from)).sql(", ").visit(Val.getJavaValue(this.to)).sql(')');
            } else {
                ctx.sql("generateSeries(").visit(Val.getJavaValue(this.from)).sql(", ").visit(Val.getJavaValue(this.to)).sql(", ").visit(Val.getJavaValue(this.step)).sql(')');
            }
        } else if (ctx.family() == SQLDialect.SQLSERVER) {
            Tools.visitSubquery(ctx, this.step == null ? DSL.select(DSL.asterisk()).from("{0}({1}, {2})", Names.N_GENERATE_SERIES, this.from, this.to) : DSL.select(DSL.asterisk()).from("{0}({1}, {2}, {3})", Names.N_GENERATE_SERIES, this.from, this.to, this.step), 1);
        } else if (this.step == null) {
            ctx.visit(Names.N_GENERATE_SERIES).sql('(').visit(this.from).sql(", ").visit(this.to).sql(')');
        } else {
            ctx.visit(Names.N_GENERATE_SERIES).sql('(').visit(this.from).sql(", ").visit(this.to).sql(", ").visit(this.step).sql(')');
        }
    }

    @Override
    public final Class<? extends Record1<Integer>> getRecordType() {
        return RecordImpl1.class;
    }

    @Override
    final FieldsImpl<Record1<Integer>> fields0() {
        return new FieldsImpl<Record1<Integer>>(DSL.field(DSL.name(this.name, Names.N_GENERATE_SERIES), Integer.class));
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return null;
    }

    @Override
    public final Table<Record1<Integer>> autoAlias(Context<?> ctx, Table<Record1<Integer>> t2) {
        if (EMULATE_WITH_RECURSIVE.contains((Object)ctx.dialect()) || EMULATE_CONNECT_BY.contains((Object)ctx.dialect())) {
            return t2.as(this.name);
        }
        if (EMULATE_SYSTEM_RANGE.contains((Object)ctx.dialect())) {
            return t2.as(this.name, this.name);
        }
        if (EMULATE_GENERATE_ARRAY.contains((Object)ctx.dialect())) {
            return t2.as(this.name, this.name);
        }
        if (EMULATE_GENERATOR.contains((Object)ctx.dialect())) {
            return t2.as(this.name, this.name);
        }
        if (NO_IMPLICIT_TABLE_NAME.contains((Object)ctx.dialect())) {
            return t2.as(this.name, this.name);
        }
        return null;
    }

    @Override
    public final Field<Integer> $arg1() {
        return this.from;
    }

    @Override
    public final Field<Integer> $arg2() {
        return this.to;
    }

    @Override
    public final Field<Integer> $arg3() {
        return this.step;
    }

    @Override
    public final Function3<? super Field<Integer>, ? super Field<Integer>, ? super Field<Integer>, ? extends QOM.GenerateSeries<Integer>> $constructor() {
        return (f1, f2, f3) -> new GenerateSeries((Field<Integer>)f1, (Field<Integer>)f2, (Field<Integer>)f3, this.name);
    }
}

