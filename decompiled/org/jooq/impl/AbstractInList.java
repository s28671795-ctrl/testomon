/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.AbstractList;
import java.util.List;
import java.util.Set;
import org.jooq.Condition;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function2;
import org.jooq.Param;
import org.jooq.QueryPart;
import org.jooq.RowN;
import org.jooq.SQLDialect;
import org.jooq.TransformProvider;
import org.jooq.conf.ParamType;
import org.jooq.impl.AbstractCondition;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.QueryPartListView;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;
import org.jooq.tools.StringUtils;

abstract class AbstractInList<T>
extends AbstractCondition {
    static final int IN_LIMIT = 1000;
    static final Set<SQLDialect> REQUIRES_IN_LIMIT = SQLDialect.supportedBy(SQLDialect.FIREBIRD, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER);
    static final Set<SQLDialect> NO_SUPPORT_EMPTY_LISTS = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2_1_4_200, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB);
    final Field<T> field;
    final QueryPartList<Field<T>> values;

    AbstractInList(Field<T> field2, List<? extends Field<?>> values2) {
        this.field = field2;
        this.values = new QueryPartList((Iterable<Field<?>>)values2);
    }

    abstract Function2<? super RowN, ? super RowN[], ? extends Condition> rowCondition();

    @Override
    public final void accept(Context<?> ctx) {
        if (this.field.getDataType().isEmbeddable()) {
            ctx.visit(this.rowCondition().apply(DSL.row(Tools.embeddedFields(this.field)), AbstractInList.rows(this.values)));
        } else if (this.field.getDataType().isMultiset() && !Boolean.TRUE.equals(ctx.data(Tools.BooleanDataKey.DATA_MULTISET_CONDITION))) {
            ctx.data(Tools.BooleanDataKey.DATA_MULTISET_CONDITION, true, c -> c.visit(this));
        } else if (Boolean.TRUE.equals(ctx.settings().isTransformInlineBindValuesForFieldComparisons())) {
            TransformProvider tp = ctx.configuration().transformProvider();
            if (tp.inlineBindValuesForFieldComparisons(this.field) && Tools.anyMatch(this.values, f -> f instanceof Param && !((Param)f).$inline())) {
                QueryPartList<Field<T>> v = new QueryPartList<Field<T>>(this.values);
                v.replaceAll(f -> f instanceof Param && !((Param)f).$inline() ? ((Param)f).$inline(true) : f);
                AbstractInList.accept1(ctx, this instanceof QOM.InList, this.field, v);
            } else {
                this.accept0(ctx);
            }
        } else {
            this.accept0(ctx);
        }
    }

    private final void accept0(Context<?> ctx) {
        AbstractInList.accept1(ctx, this instanceof QOM.InList, this.field, this.values);
    }

    private static final <T> void accept1(Context<?> ctx, boolean in, Field<T> field2, QueryPartList<Field<T>> values2) {
        if (ctx.family() == SQLDialect.JAVA) {
            ctx.visit(field2).sql(in ? ".in(" : ".notIn(");
            if (values2.isEmpty()) {
                ctx.sql("new Field[0]");
            } else {
                ctx.visit(QueryPartListView.wrap((QueryPart[])Val.getJavaValues(values2.toArray((E[])Tools.EMPTY_FIELD))));
            }
            ctx.sql(')');
        } else if (values2.size() == 0 && NO_SUPPORT_EMPTY_LISTS.contains((Object)ctx.dialect())) {
            if (in) {
                ctx.visit(DSL.falseCondition());
            } else {
                ctx.visit(DSL.trueCondition());
            }
        } else if (values2.size() > 1000) {
            switch (ctx.family()) {
                case INGRES: 
                case ORACLE: 
                case SQLDATAWAREHOUSE: 
                case SQLSERVER: 
                case FIREBIRD: {
                    ctx.sqlIndentStart('(');
                    for (int i = 0; i < values2.size(); i += 1000) {
                        if (i > 0) {
                            if (in) {
                                ctx.formatSeparator().visit(Keywords.K_OR).sql(' ');
                            } else {
                                ctx.formatSeparator().visit(Keywords.K_AND).sql(' ');
                            }
                        }
                        AbstractInList.toSQLSubValues(ctx, field2, in, AbstractInList.padded(ctx, values2.subList(i, Math.min(i + 1000, values2.size()))));
                    }
                    ctx.sqlIndentEnd(')');
                    break;
                }
                default: {
                    AbstractInList.toSQLSubValues(ctx, field2, in, values2);
                    break;
                }
            }
        } else {
            AbstractInList.toSQLSubValues(ctx, field2, in, AbstractInList.padded(ctx, values2));
        }
    }

    static final RowN[] rows(List<? extends Field<?>> values2) {
        return Tools.map(values2, v -> DSL.row(Tools.embeddedFields(v)), RowN[]::new);
    }

    static final <T> List<T> padded(Context<?> ctx, List<T> list) {
        return ctx.paramType() == ParamType.INDEXED && Boolean.TRUE.equals(ctx.settings().isInListPadding()) ? new PaddedList(list, REQUIRES_IN_LIMIT.contains((Object)ctx.dialect()) ? 1000 : Integer.MAX_VALUE, StringUtils.defaultIfNull(ctx.settings().getInListPadBase(), 2)) : list;
    }

    static final void toSQLSubValues(Context<?> ctx, Field<?> field2, boolean in, List<? extends Field<?>> subValues) {
        ctx.visit(field2).sql(' ').visit(in ? Keywords.K_IN : Keywords.K_NOT_IN).sql(" (");
        if (subValues.size() > 1) {
            ctx.formatIndentStart().formatNewLine();
        }
        String separator = "";
        for (Field<?> value : subValues) {
            ctx.sql(separator).formatNewLineAfterPrintMargin().visit(value);
            separator = ", ";
        }
        if (subValues.size() > 1) {
            ctx.formatIndentEnd().formatNewLine();
        }
        ctx.sql(')');
    }

    public final Field<T> $arg1() {
        return this.field;
    }

    public final QOM.UnmodifiableList<? extends Field<T>> $arg2() {
        return QOM.unmodifiable(this.values);
    }

    static final class PaddedList<T>
    extends AbstractList<T> {
        private final List<T> delegate;
        private final int realSize;
        private final int padSize;

        PaddedList(List<T> delegate, int maxPadding, int padBase) {
            int b = Math.max(2, padBase);
            this.delegate = delegate;
            this.realSize = delegate.size();
            this.padSize = Math.min(maxPadding, (int)Math.round(Math.pow(b, Math.ceil(Math.log(this.realSize) / Math.log(b)))));
        }

        @Override
        public T get(int index) {
            return index < this.realSize ? this.delegate.get(index) : this.delegate.get(this.realSize - 1);
        }

        @Override
        public int size() {
            return this.padSize;
        }
    }
}

