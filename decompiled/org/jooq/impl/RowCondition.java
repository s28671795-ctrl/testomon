/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.ArrayList;
import java.util.Set;
import org.jooq.Clause;
import org.jooq.Comparator;
import org.jooq.Condition;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Pro;
import org.jooq.Row;
import org.jooq.SQLDialect;
import org.jooq.impl.AbstractCondition;
import org.jooq.impl.AbstractRow;
import org.jooq.impl.CompareCondition;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.RowSubqueryCondition;
import org.jooq.impl.Tools;

final class RowCondition
extends AbstractCondition
implements QOM.UNotYetImplemented {
    private static final Clause[] CLAUSES = new Clause[]{Clause.CONDITION, Clause.CONDITION_COMPARISON};
    @Pro
    private static final Set<SQLDialect> EMULATE_RHS_SELECT = SQLDialect.supportedBy(SQLDialect.TERADATA);
    private static final Set<SQLDialect> EMULATE_EQ_AND_NE = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.INGRES, SQLDialect.MEMSQL, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA);
    private static final Set<SQLDialect> EMULATE_RANGES = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.BIGQUERY, SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MEMSQL, SQLDialect.ORACLE, SQLDialect.REDSHIFT, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA);
    private final Row left;
    private final Row right;
    private final Comparator comparator;
    private final boolean forceEmulation;

    RowCondition(Row left, Row right, Comparator comparator) {
        this(left, right, comparator, false);
    }

    RowCondition(Row left, Row right, Comparator comparator, boolean forceEmulation) {
        this.left = ((AbstractRow)left).convertTo(right);
        this.right = ((AbstractRow)right).convertTo(left);
        this.comparator = comparator;
        this.forceEmulation = forceEmulation;
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (EMULATE_RHS_SELECT.contains((Object)ctx.dialect())) {
            ctx.visit(new RowSubqueryCondition(this.left, DSL.select(this.right.fields()), this.comparator));
        } else if ((this.comparator == Comparator.EQUALS || this.comparator == Comparator.NOT_EQUALS) && (this.forceEmulation || EMULATE_EQ_AND_NE.contains((Object)ctx.dialect()))) {
            Field[] rightFields = this.right.fields();
            Condition result = DSL.and(Tools.map(this.left.fields(), (f, i) -> f.equal(rightFields[i])));
            if (this.comparator == Comparator.NOT_EQUALS) {
                result = result.not();
            }
            ctx.visit(result);
        } else if ((this.comparator == Comparator.GREATER || this.comparator == Comparator.GREATER_OR_EQUAL || this.comparator == Comparator.LESS || this.comparator == Comparator.LESS_OR_EQUAL) && (this.forceEmulation || EMULATE_RANGES.contains((Object)ctx.dialect()))) {
            Comparator order;
            Comparator comparator = this.comparator == Comparator.GREATER ? Comparator.GREATER : (this.comparator == Comparator.GREATER_OR_EQUAL ? Comparator.GREATER : (this.comparator == Comparator.LESS ? Comparator.LESS : (order = this.comparator == Comparator.LESS_OR_EQUAL ? Comparator.LESS : null)));
            Comparator factoredOrder = this.comparator == Comparator.GREATER ? Comparator.GREATER_OR_EQUAL : (this.comparator == Comparator.GREATER_OR_EQUAL ? Comparator.GREATER_OR_EQUAL : (this.comparator == Comparator.LESS ? Comparator.LESS_OR_EQUAL : (this.comparator == Comparator.LESS_OR_EQUAL ? Comparator.LESS_OR_EQUAL : null)));
            boolean equal = this.comparator == Comparator.GREATER_OR_EQUAL || this.comparator == Comparator.LESS_OR_EQUAL;
            Field<?>[] leftFields = this.left.fields();
            Field<?>[] rightFields = this.right.fields();
            ArrayList<Condition> outer = new ArrayList<Condition>(1 + leftFields.length);
            for (int i2 = 0; i2 < leftFields.length; ++i2) {
                ArrayList<Condition> inner = new ArrayList<Condition>(1 + i2);
                for (int j = 0; j < i2; ++j) {
                    inner.add(leftFields[j].equal(rightFields[j]));
                }
                inner.add(leftFields[i2].compare(equal && i2 == leftFields.length - 1 ? this.comparator : order, rightFields[i2]));
                outer.add(DSL.and(inner));
            }
            Condition result = DSL.or(outer);
            if (leftFields.length > 1) {
                result = leftFields[0].compare(factoredOrder, rightFields[0]).and(result);
            }
            ctx.visit(result);
        } else if (ctx.family() == SQLDialect.JAVA) {
            ctx.visit(this.left).sql('.');
            CompareCondition.acceptJava(ctx, this.comparator);
            ctx.sql('(').visit(this.right).sql(')');
        } else if (this.comparator == Comparator.NOT_EQUALS && ctx.family() == SQLDialect.DB2) {
            ctx.visit(Keywords.K_NOT).sql('(').visit(this.left).sql(" = ").visit(this.right).sql(')');
        } else {
            boolean extraParentheses = SQLDialect.ORACLE == ctx.family();
            ctx.visit(this.left).sql(' ').sql(this.comparator.toSQL()).sql(' ').sql(extraParentheses ? "(" : "").visit(this.right).sql(extraParentheses ? ")" : "");
        }
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return null;
    }
}

