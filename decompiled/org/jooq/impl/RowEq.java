/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.Comparator;
import org.jooq.Condition;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function2;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Row;
import org.jooq.SQLDialect;
import org.jooq.impl.AbstractCondition;
import org.jooq.impl.AbstractRow;
import org.jooq.impl.CompareCondition;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.RowGe;
import org.jooq.impl.RowGt;
import org.jooq.impl.RowLe;
import org.jooq.impl.RowLt;
import org.jooq.impl.RowNe;
import org.jooq.impl.RowSubqueryCondition;
import org.jooq.impl.Tools;
import org.jooq.tools.StringUtils;

final class RowEq<T extends Row>
extends AbstractCondition
implements QOM.RowEq<T> {
    final T arg1;
    final T arg2;
    @Pro
    private static final Set<SQLDialect> EMULATE_RHS_SELECT = SQLDialect.supportedBy(SQLDialect.TERADATA);
    private static final Set<SQLDialect> EMULATE_EQ_AND_NE = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.INGRES, SQLDialect.MEMSQL, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA);
    private static final Set<SQLDialect> EMULATE_RANGES = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.BIGQUERY, SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MEMSQL, SQLDialect.ORACLE, SQLDialect.REDSHIFT, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA);

    RowEq(T arg1, T arg2) {
        this.arg1 = ((AbstractRow)arg1).convertTo((Row)arg2);
        this.arg2 = ((AbstractRow)arg2).convertTo((Row)arg1);
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (ctx.family() == SQLDialect.JAVA) {
            this.acceptJava(ctx);
            return;
        }
        RowEq.acceptCompareCondition(ctx, this, this.arg1, Comparator.EQUALS, this.arg2);
    }

    @Deprecated
    static final <T extends Row> void acceptCompareCondition(Context<?> ctx, AbstractCondition condition, T arg1, Comparator op, T arg2) {
        if (EMULATE_RHS_SELECT.contains((Object)ctx.dialect())) {
            ctx.visit(new RowSubqueryCondition(arg1, DSL.select(arg2.fields()), op));
        } else if ((op == Comparator.EQUALS || op == Comparator.NOT_EQUALS) && EMULATE_EQ_AND_NE.contains((Object)ctx.dialect())) {
            Field[] arg2Fields = arg2.fields();
            Condition result = DSL.and(Tools.map(arg1.fields(), (f, i) -> f.equal(arg2Fields[i])));
            if (op == Comparator.NOT_EQUALS) {
                result = result.not();
            }
            ctx.visit(result);
        } else if ((op == Comparator.GREATER || op == Comparator.GREATER_OR_EQUAL || op == Comparator.LESS || op == Comparator.LESS_OR_EQUAL) && EMULATE_RANGES.contains((Object)ctx.dialect())) {
            Comparator comp;
            Comparator comparator = op == Comparator.GREATER ? Comparator.GREATER : (op == Comparator.GREATER_OR_EQUAL ? Comparator.GREATER : (op == Comparator.LESS ? Comparator.LESS : (comp = op == Comparator.LESS_OR_EQUAL ? Comparator.LESS : null)));
            Comparator factored = op == Comparator.GREATER ? Comparator.GREATER_OR_EQUAL : (op == Comparator.GREATER_OR_EQUAL ? Comparator.GREATER_OR_EQUAL : (op == Comparator.LESS ? Comparator.LESS_OR_EQUAL : (op == Comparator.LESS_OR_EQUAL ? Comparator.LESS_OR_EQUAL : null)));
            Condition result = RowEq.emulate(arg1, arg2, comp, op);
            if (arg1.size() > 1) {
                result = arg1.field(0).compare(factored, arg2.field(0)).and(result);
            }
            ctx.visit(result);
        } else if (ctx.family() == SQLDialect.JAVA) {
            ctx.visit(arg1).sql('.');
            CompareCondition.acceptJava(ctx, op);
            ctx.sql('(').visit(arg2).sql(')');
        } else if (op == Comparator.NOT_EQUALS && ctx.family() == SQLDialect.DB2) {
            ctx.visit(Keywords.K_NOT).sql('(').visit(arg1).sql(" = ").visit(arg2).sql(')');
        } else {
            boolean extraParentheses = SQLDialect.ORACLE == ctx.family();
            ctx.visit(arg1).sql(' ').sql(op.toSQL()).sql(' ').sql(extraParentheses ? "(" : "").visit(arg2).sql(extraParentheses ? ")" : "");
        }
    }

    private static final Condition emulate(Row r1, Row r2, Comparator comp, Comparator last) {
        Condition result = r1.field(r1.size() - 1).compare(last, r2.field(r1.size() - 1));
        for (int i = r1.size() - 2; i >= 0; --i) {
            Field<?> e1 = r1.field(i);
            Field<?> e2 = r2.field(i);
            result = e1.compare(comp, e2).or(e1.eq(e2).and(result));
        }
        return result;
    }

    @Deprecated
    static final Comparator comparator(Condition condition) {
        if (condition instanceof RowEq) {
            return Comparator.EQUALS;
        }
        if (condition instanceof RowNe) {
            return Comparator.NOT_EQUALS;
        }
        if (condition instanceof RowGt) {
            return Comparator.GREATER;
        }
        if (condition instanceof RowGe) {
            return Comparator.GREATER_OR_EQUAL;
        }
        if (condition instanceof RowLt) {
            return Comparator.LESS;
        }
        if (condition instanceof RowLe) {
            return Comparator.LESS_OR_EQUAL;
        }
        return null;
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.visit((QueryPart)this.arg1).sql(".eq(").visit((QueryPart)this.arg2).sql(')');
    }

    @Override
    public final T $arg1() {
        return this.arg1;
    }

    @Override
    public final T $arg2() {
        return this.arg2;
    }

    @Override
    public final QOM.RowEq<T> $arg1(T newValue) {
        return this.$constructor().apply(newValue, (T)this.$arg2());
    }

    @Override
    public final QOM.RowEq<T> $arg2(T newValue) {
        return this.$constructor().apply((T)this.$arg1(), newValue);
    }

    @Override
    public final Function2<? super T, ? super T, ? extends QOM.RowEq<T>> $constructor() {
        return (a1, a2) -> new RowEq<Row>((Row)a1, (Row)a2);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.RowEq) {
            QOM.RowEq o = (QOM.RowEq)that;
            return StringUtils.equals(this.$arg1(), o.$arg1()) && StringUtils.equals(this.$arg2(), o.$arg2());
        }
        return super.equals(that);
    }
}

