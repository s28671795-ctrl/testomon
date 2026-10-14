/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.Clause;
import org.jooq.Comparator;
import org.jooq.Context;
import org.jooq.Name;
import org.jooq.Pro;
import org.jooq.QuantifiedSelect;
import org.jooq.QueryPartInternal;
import org.jooq.Record;
import org.jooq.RenderContext;
import org.jooq.Row;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.SelectFieldOrAsterisk;
import org.jooq.SelectOrderByStep;
import org.jooq.TableLike;
import org.jooq.impl.AbstractCondition;
import org.jooq.impl.AliasedSelect;
import org.jooq.impl.CompareCondition;
import org.jooq.impl.DSL;
import org.jooq.impl.QOM;
import org.jooq.impl.QuantifiedSelectImpl;
import org.jooq.impl.RowCondition;
import org.jooq.impl.SelectQueryImpl;
import org.jooq.impl.Tools;
import org.jooq.impl.Transformations;

final class RowSubqueryCondition
extends AbstractCondition
implements QOM.UNotYetImplemented {
    private static final Clause[] CLAUSES = new Clause[]{Clause.CONDITION, Clause.CONDITION_COMPARISON};
    private static final Set<SQLDialect> NO_SUPPORT_NATIVE = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.BIGQUERY, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MEMSQL, SQLDialect.ORACLE, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.VERTICA);
    private static final Set<SQLDialect> NO_SUPPORT_QUANTIFIED = SQLDialect.supportedBy(SQLDialect.ASE, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.INFORMIX, SQLDialect.MEMSQL, SQLDialect.ORACLE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER);
    private static final Set<SQLDialect> NO_SUPPORT_QUANTIFIED_OTHER_THAN_IN_NOT_IN = SQLDialect.supportedBy(SQLDialect.MARIADB, SQLDialect.MYSQL);
    @Pro
    private static final Set<SQLDialect> SUPPORT_NATIVE_FOR_EQ_NE = SQLDialect.supportedBy(SQLDialect.ORACLE, SQLDialect.REDSHIFT);
    private final Row left;
    private final Select<?> right;
    private final QuantifiedSelect<?> rightQuantified;
    private final Comparator comparator;

    RowSubqueryCondition(Row left, Select<?> right, Comparator comparator) {
        this.left = left;
        this.right = right;
        this.rightQuantified = null;
        this.comparator = comparator;
    }

    RowSubqueryCondition(Row left, QuantifiedSelect<?> right, Comparator comparator) {
        this.left = left;
        this.right = null;
        this.rightQuantified = right;
        this.comparator = comparator;
    }

    @Override
    public final void accept(Context<?> ctx) {
        ctx.visit(this.delegate(ctx));
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return null;
    }

    private static final boolean inOrNotIn(Comparator comparator, QOM.Quantifier quantifier) {
        return comparator == Comparator.EQUALS && quantifier == QOM.Quantifier.ANY || comparator == Comparator.NOT_EQUALS && quantifier == QOM.Quantifier.ALL;
    }

    private final QueryPartInternal delegate(Context<?> ctx) {
        if (this.rightQuantified != null) {
            QuantifiedSelectImpl q = (QuantifiedSelectImpl)this.rightQuantified;
            boolean inOrNotIn = RowSubqueryCondition.inOrNotIn(this.comparator, q.quantifier);
            if (NO_SUPPORT_QUANTIFIED.contains((Object)ctx.dialect()) || NO_SUPPORT_QUANTIFIED_OTHER_THAN_IN_NOT_IN.contains((Object)ctx.dialect()) && !inOrNotIn) {
                switch (this.comparator) {
                    case EQUALS: 
                    case NOT_EQUALS: {
                        if (inOrNotIn) {
                            return new RowSubqueryCondition(this.left, q.query, this.comparator == Comparator.EQUALS ? Comparator.IN : Comparator.NOT_IN);
                        }
                        return RowSubqueryCondition.emulationUsingExists(ctx, this.left, q.query, this.comparator == Comparator.EQUALS ? Comparator.NOT_EQUALS : Comparator.EQUALS, this.comparator == Comparator.EQUALS);
                    }
                }
                return RowSubqueryCondition.emulationUsingExists(ctx, this.left, q.query, q.quantifier == QOM.Quantifier.ALL ? this.comparator.inverse() : this.comparator, q.quantifier == QOM.Quantifier.ALL);
            }
            return new Native();
        }
        if (SUPPORT_NATIVE_FOR_EQ_NE.contains((Object)ctx.dialect()) && (this.comparator == Comparator.EQUALS || this.comparator == Comparator.NOT_EQUALS || this.comparator == Comparator.IN || this.comparator == Comparator.NOT_IN)) {
            return new Native();
        }
        if (NO_SUPPORT_NATIVE.contains((Object)ctx.dialect())) {
            return RowSubqueryCondition.emulationUsingExists(ctx, this.left, this.right, this.comparator == Comparator.GREATER || this.comparator == Comparator.GREATER_OR_EQUAL || this.comparator == Comparator.LESS || this.comparator == Comparator.LESS_OR_EQUAL ? this.comparator : Comparator.EQUALS, this.comparator == Comparator.NOT_IN || this.comparator == Comparator.NOT_EQUALS);
        }
        return new Native();
    }

    private static final QueryPartInternal emulationUsingExists(Context<?> ctx, Row row, Select<?> select, Comparator comparator, boolean notExists) {
        SelectOrderByStep<Record> subselect = RowSubqueryCondition.emulatedSubselect(ctx, row, select, comparator);
        return (QueryPartInternal)((Object)(notExists ? DSL.notExists(subselect) : DSL.exists(subselect)));
    }

    private static final SelectOrderByStep<Record> emulatedSubselect(Context<?> ctx, Row row, Select<?> s2, Comparator c) {
        RenderContext r;
        RenderContext render = ctx instanceof RenderContext ? (r = (RenderContext)ctx) : null;
        Row l = Tools.embeddedFieldsRow(row);
        Name table = DSL.name(render == null ? "t" : render.nextAlias());
        Name[] names = Tools.fieldNames(l.size());
        return DSL.select(new SelectFieldOrAsterisk[0]).from((TableLike<?>)((Object)new AliasedSelect(s2, true, true, false, names).as(table))).where(c == null ? DSL.noCondition() : new RowCondition(l, DSL.row(Tools.fieldsByName(table, names)), c, ctx.family() == SQLDialect.INFORMIX));
    }

    private class Native
    extends AbstractCondition
    implements QOM.UTransient {
        private Native() {
        }

        @Override
        public final void accept(Context<?> ctx) {
            SelectQueryImpl<?> s2;
            if ((RowSubqueryCondition.this.comparator == Comparator.IN || RowSubqueryCondition.this.comparator == Comparator.NOT_IN) && RowSubqueryCondition.this.right != null && (s2 = Transformations.subqueryWithLimit(RowSubqueryCondition.this.right)) != null && Transformations.NO_SUPPORT_IN_LIMIT.contains((Object)ctx.dialect())) {
                ctx.visit(new RowSubqueryCondition(RowSubqueryCondition.this.left, DSL.select(DSL.asterisk()).from((TableLike<?>)s2.asTable("t")), RowSubqueryCondition.this.comparator));
            } else if ((RowSubqueryCondition.this.comparator == Comparator.EQUALS || RowSubqueryCondition.this.comparator == Comparator.NOT_EQUALS) && RowSubqueryCondition.this.rightQuantified != null && (s2 = Transformations.subqueryWithLimit(RowSubqueryCondition.this.rightQuantified)) != null && Transformations.NO_SUPPORT_IN_LIMIT.contains((Object)ctx.dialect())) {
                ctx.visit(new RowSubqueryCondition(RowSubqueryCondition.this.left, Tools.quantify(((QuantifiedSelectImpl)RowSubqueryCondition.this.rightQuantified).quantifier, DSL.select(DSL.asterisk()).from((TableLike<?>)s2.asTable("t"))), RowSubqueryCondition.this.comparator));
            } else {
                this.accept0(ctx);
            }
        }

        final void accept0(Context<?> ctx) {
            switch (ctx.family()) {
                case JAVA: {
                    ctx.visit(RowSubqueryCondition.this.left).sql('.');
                    CompareCondition.acceptJava(ctx, RowSubqueryCondition.this.comparator);
                    ctx.sql('(');
                    if (RowSubqueryCondition.this.rightQuantified == null) {
                        Tools.visitSubquery(ctx, RowSubqueryCondition.this.right, 256, false);
                    } else {
                        ctx.visit(RowSubqueryCondition.this.rightQuantified);
                    }
                    ctx.sql(')');
                    break;
                }
                default: {
                    ctx.visit(RowSubqueryCondition.this.left).sql(' ').visit(RowSubqueryCondition.this.comparator.toKeyword()).sql(' ');
                    if (RowSubqueryCondition.this.rightQuantified == null) {
                        boolean extraParentheses = ctx.family() == SQLDialect.ORACLE;
                        ctx.sql(extraParentheses ? "((" : "(").data(Tools.BooleanDataKey.DATA_ROW_VALUE_EXPRESSION_PREDICATE_SUBQUERY, true, c -> Tools.visitSubquery(c, RowSubqueryCondition.this.right, 256, false)).sql(extraParentheses ? "))" : ")");
                        break;
                    }
                    ctx.data(Tools.BooleanDataKey.DATA_ROW_VALUE_EXPRESSION_PREDICATE_SUBQUERY, true, c -> c.visit(RowSubqueryCondition.this.rightQuantified));
                }
            }
        }

        @Override
        public final Clause[] clauses(Context<?> ctx) {
            return CLAUSES;
        }
    }
}

