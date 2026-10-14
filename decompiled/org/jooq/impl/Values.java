/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Function1;
import org.jooq.Name;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Row;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.SelectSelectStep;
import org.jooq.conf.ParamType;
import org.jooq.impl.AbstractAutoAliasTable;
import org.jooq.impl.DSL;
import org.jooq.impl.FieldsImpl;
import org.jooq.impl.Keywords;
import org.jooq.impl.NullCondition;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartListView;
import org.jooq.impl.RecordImplN;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;

final class Values<R extends Record>
extends AbstractAutoAliasTable<R>
implements QOM.Values<R> {
    static final Set<SQLDialect> NO_SUPPORT_VALUES = SQLDialect.supportedUntil(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.REDSHIFT, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA);
    static final Set<SQLDialect> REQUIRE_ROWTYPE_CAST = SQLDialect.supportedBy(SQLDialect.COCKROACHDB, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.SQLSERVER);
    static final Set<SQLDialect> REQUIRE_ROWTYPE_CAST_FIRST_ROW = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB);
    static final Set<SQLDialect> NO_SUPPORT_PARENTHESES = SQLDialect.supportedBy(SQLDialect.BIGQUERY);
    private final Row[] rows;
    private transient DataType<?>[] types;

    Values(Row[] rows) {
        this(rows, DSL.name("v"), Values.fieldAliases(Values.degree(rows)));
    }

    Values(Row[] rows, Name alias, Name[] fieldAliases) {
        super(alias, fieldAliases);
        this.rows = Values.assertNotEmpty(rows);
    }

    private static Name[] fieldAliases(int degree) {
        Name[] result = new Name[degree];
        for (int i = 0; i < result.length; ++i) {
            result[i] = DSL.name("c" + (i + 1));
        }
        return result;
    }

    private static final int degree(Row[] rows) {
        return Tools.isEmpty(rows) ? 0 : rows[0].size();
    }

    static final Row[] assertNotEmpty(Row[] rows) {
        if (Tools.isEmpty(rows)) {
            throw new IllegalArgumentException("Cannot create a VALUES() constructor with an empty set of rows");
        }
        return rows;
    }

    @Override
    final Values<R> construct(Name newAlias, Name[] newFieldAliases) {
        return new Values<R>(this.rows, newAlias, newFieldAliases);
    }

    @Override
    public final Class<? extends R> getRecordType() {
        return RecordImplN.class;
    }

    @Override
    final FieldsImpl<R> fields0() {
        return new FieldsImpl(Tools.map(this.fieldAliases, (n, i) -> DSL.field(this.alias.append((Name)n), this.rows[0].dataType(i))));
    }

    private final DataType<?>[] rowType() {
        if (this.types == null) {
            this.types = new DataType[this.rows[0].size()];
            block0: for (int i = 0; i < this.types.length; ++i) {
                this.types[i] = this.rows[0].dataType(i);
                if (this.types[i].getType() != Object.class) continue;
                for (int j = 1; j < this.rows.length; ++j) {
                    DataType<?> type = this.rows[j].dataType(i);
                    if (type.getType() == Object.class) continue;
                    this.types[i] = type;
                    continue block0;
                }
            }
        }
        return this.types;
    }

    private final Field<?>[] castToRowType(Field<?>[] fields) {
        Field[] result = new Field[fields.length];
        for (int i = 0; i < result.length; ++i) {
            DataType<?> type = this.rowType()[i];
            result[i] = fields[i].getDataType().equals(type) ? fields[i] : fields[i].cast(type);
        }
        return result;
    }

    private final Row castNullLiteralToRowType(Context<?> ctx, Row row) {
        if (Tools.anyMatch(row.fields(), f -> this.nullLiteralOrUntypedNullBind(ctx, (Field<?>)f))) {
            Field[] result = new Field[row.size()];
            for (int i = 0; i < result.length; ++i) {
                result[i] = this.nullLiteralOrUntypedNullBind(ctx, row.field(i)) && this.rowType()[i].getType() != Object.class ? row.field(i).cast(this.rowType()[i]) : row.field(i);
            }
            return DSL.row(result);
        }
        return row;
    }

    private final boolean nullLiteralOrUntypedNullBind(Context<?> ctx, Field<?> field2) {
        return Tools.isVal(field2) && ((Val)field2).getValue() == null && (((Val)field2).isInline(ctx) || field2.getType() == Object.class) || field2 instanceof NullCondition;
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (NO_SUPPORT_VALUES.contains((Object)ctx.dialect())) {
            Select<Record> selects = null;
            boolean cast = REQUIRE_ROWTYPE_CAST.contains((Object)ctx.dialect());
            for (Row row : this.rows) {
                SelectSelectStep<Record> select = DSL.select(cast ? this.castToRowType(row.fields()) : row.fields());
                selects = selects == null ? select : selects.unionAll(select);
            }
            Tools.visitSubquery(ctx, selects, 1, true);
        } else if (ctx.family() == SQLDialect.JAVA) {
            ctx.sql("values(").visit(QueryPartListView.wrap((QueryPart[])this.rows)).sql(')');
        } else {
            ctx.start(Clause.TABLE_VALUES);
            if (!NO_SUPPORT_PARENTHESES.contains((Object)ctx.dialect())) {
                ctx.sqlIndentStart('(');
            }
            ParamType paramType = ctx.paramType();
            if (ctx.family() == SQLDialect.EXASOL) {
                ctx.paramType(ParamType.INLINED);
            }
            if (ctx.family() == SQLDialect.BIGQUERY) {
                ctx.visit(Keywords.K_UNNEST).sql(" ([");
            } else if (ctx.family() == SQLDialect.INFORMIX) {
                ctx.visit(Keywords.K_TABLE).sql(" (").visit(Keywords.K_MULTISET).sql(" {");
            } else {
                ctx.visit(Keywords.K_VALUES);
            }
            if (this.rows.length > 1) {
                ctx.formatIndentStart().formatSeparator();
            } else {
                ctx.sql(' ');
            }
            for (int i = 0; i < this.rows.length; ++i) {
                if (i > 0) {
                    ctx.sql(',').formatSeparator();
                }
                if (ctx.family() == SQLDialect.MYSQL) {
                    ctx.visit(Keywords.K_ROW).sql(" ");
                } else if (ctx.family() == SQLDialect.BIGQUERY) {
                    ctx.visit(Keywords.K_STRUCT).sql(" ");
                }
                if (i == 0 && REQUIRE_ROWTYPE_CAST_FIRST_ROW.contains((Object)ctx.dialect())) {
                    ctx.visit(this.castNullLiteralToRowType(ctx, this.rows[i]));
                    continue;
                }
                if (REQUIRE_ROWTYPE_CAST.contains((Object)ctx.dialect())) {
                    ctx.visit(this.castNullLiteralToRowType(ctx, this.rows[i]));
                    continue;
                }
                ctx.visit(this.rows[i]);
            }
            if (this.rows.length > 1) {
                ctx.formatIndentEnd().formatNewLine();
            }
            if (ctx.family() == SQLDialect.BIGQUERY) {
                ctx.sql("])");
            } else if (ctx.family() == SQLDialect.INFORMIX) {
                ctx.sql("})");
            } else if (ctx.family() == SQLDialect.EXASOL) {
                ctx.paramType(paramType);
            }
            if (!NO_SUPPORT_PARENTHESES.contains((Object)ctx.dialect())) {
                ctx.sqlIndentEnd(')');
            }
            ctx.end(Clause.TABLE_VALUES);
        }
    }

    @Override
    public final Function1<? super QOM.UnmodifiableList<? extends Row>, ? extends QOM.Values<R>> $constructor() {
        return r -> new Values(r.toArray(Tools.EMPTY_ROW));
    }

    @Override
    public final QOM.UnmodifiableList<? extends Row> $arg1() {
        return QOM.unmodifiable((QueryPart[])this.rows);
    }
}

