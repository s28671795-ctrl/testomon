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
import org.jooq.Operator;
import org.jooq.Param;
import org.jooq.Pro;
import org.jooq.QuantifiedSelect;
import org.jooq.Record1;
import org.jooq.SQLDialect;
import org.jooq.SelectField;
import org.jooq.TableLike;
import org.jooq.impl.AbstractCondition;
import org.jooq.impl.AliasedSelect;
import org.jooq.impl.Array;
import org.jooq.impl.ArrayTable;
import org.jooq.impl.CompareCondition;
import org.jooq.impl.Convert;
import org.jooq.impl.DSL;
import org.jooq.impl.QOM;
import org.jooq.impl.QuantifiedArray;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.SelectQueryImpl;
import org.jooq.impl.Tools;
import org.jooq.impl.Transformations;
import org.jooq.tools.StringUtils;

final class EqQuantified<T>
extends AbstractCondition
implements QOM.EqQuantified<T> {
    final Field<T> arg1;
    final QuantifiedSelect<? extends Record1<T>> arg2;
    private static final Set<SQLDialect> NO_SUPPORT_QUANTIFIED_LIKE = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.REDSHIFT, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA);
    private static final Set<SQLDialect> NO_SUPPORT_QUANTIFIED_SIMILAR_TO = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB);
    private static final Set<SQLDialect> SUPPORTS_QUANTIFIED_ARRAYS = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES);

    EqQuantified(Field<T> arg1, QuantifiedSelect<? extends Record1<T>> arg2) {
        this.arg1 = Tools.nullSafeNotNull(arg1, SQLDataType.OTHER);
        this.arg2 = arg2;
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (ctx.family() == SQLDialect.JAVA) {
            this.acceptJava(ctx);
            return;
        }
        EqQuantified.acceptCompareCondition(ctx, this, this.arg1, Comparator.EQUALS, this.arg2);
    }

    @Deprecated
    static final <T> void acceptCompareCondition(Context<?> ctx, AbstractCondition condition, Field<T> arg1, Comparator op, QuantifiedSelect<? extends Record1<T>> arg2) {
        EqQuantified.acceptCompareCondition(ctx, condition, arg1, op, arg2, null);
    }

    @Deprecated
    static final <T> void acceptCompareCondition(Context<?> ctx, AbstractCondition condition, Field<T> arg1, Comparator op, QuantifiedSelect<? extends Record1<T>> arg2, Character escape) {
        SelectQueryImpl<?> s2;
        if (arg1.getDataType().isEmbeddable()) {
            ctx.visit(DSL.row(Tools.embeddedFields(arg1)).compare(op, arg2));
        } else if ((op == Comparator.EQUALS || op == Comparator.NOT_EQUALS) && arg2 instanceof QOM.QuantifiedSelect && (s2 = Transformations.subqueryWithLimit(((QOM.QuantifiedSelect)((Object)arg2)).$query())) != null && Transformations.NO_SUPPORT_IN_LIMIT.contains((Object)ctx.dialect())) {
            ctx.visit(arg1.compare(op, Tools.quantify(((QOM.QuantifiedSelect)((Object)arg2)).$quantifier(), DSL.select(DSL.asterisk()).from((TableLike<?>)s2.asTable("t")))));
        } else if (ctx.family() == SQLDialect.JAVA) {
            ctx.visit(arg1).sql('.');
            CompareCondition.acceptJava(ctx, op);
            ctx.sql('(').visit(arg2).sql(')');
            if (escape != null) {
                ctx.sql(".escape(").sql(Tools.characterLiteral(escape.charValue())).sql(')');
            }
        } else {
            boolean bl;
            boolean quantifiedArrayParam;
            if (arg2 instanceof QOM.QuantifiedArray) {
                QOM.QuantifiedArray a = (QOM.QuantifiedArray)((Object)arg2);
                v0 = a.$arg2() instanceof Param;
            } else {
                v0 = quantifiedArrayParam = false;
            }
            if (arg2 instanceof QOM.QuantifiedArray) {
                QOM.QuantifiedArray a = (QOM.QuantifiedArray)((Object)arg2);
                bl = a.$arg2() instanceof Array;
            } else {
                bl = false;
            }
            boolean quantifiedArray = bl;
            boolean emulateOperator = switch (op) {
                case Comparator.LIKE, Comparator.NOT_LIKE, Comparator.LIKE_IGNORE_CASE, Comparator.NOT_LIKE_IGNORE_CASE -> escape != null || NO_SUPPORT_QUANTIFIED_LIKE.contains((Object)ctx.dialect());
                case Comparator.SIMILAR_TO, Comparator.NOT_SIMILAR_TO -> escape != null || NO_SUPPORT_QUANTIFIED_SIMILAR_TO.contains((Object)ctx.dialect());
                default -> false;
            };
            if (quantifiedArrayParam && SUPPORTS_QUANTIFIED_ARRAYS.contains((Object)ctx.dialect()) && !emulateOperator) {
                EqQuantified.accept1(ctx, arg1, op, arg2);
            } else if (quantifiedArrayParam || quantifiedArray) {
                QOM.QuantifiedArray a = (QOM.QuantifiedArray)((Object)arg2);
                ctx.visit(DSL.condition(a.$quantifier() == QOM.Quantifier.ALL ? Operator.AND : Operator.OR, a.$array() instanceof Array ? Tools.map(((Array)a.$array()).$elements(), v -> EqQuantified.comparisonCondition(arg1, op, (Field)v, escape)) : Tools.map((Object[])((Param)a.$array()).getValue(), v -> v instanceof Field ? EqQuantified.comparisonCondition(arg1, op, (Field)v, escape) : EqQuantified.comparisonCondition(arg1, op, v, escape))));
            } else if (emulateOperator) {
                QOM.Quantifier q;
                SelectField t2;
                Condition c;
                Field<String> pattern = DSL.field(DSL.name("pattern"), SQLDataType.VARCHAR);
                Param<Boolean> lhs = switch (op) {
                    case Comparator.NOT_LIKE, Comparator.NOT_LIKE_IGNORE_CASE, Comparator.NOT_SIMILAR_TO -> {
                        c = EqQuantified.comparisonCondition(arg1, EqQuantified.inverse(op), pattern, escape);
                        yield DSL.inline(false);
                    }
                    case Comparator.LIKE, Comparator.LIKE_IGNORE_CASE, Comparator.SIMILAR_TO -> {
                        c = EqQuantified.comparisonCondition(arg1, op, pattern, escape);
                        yield DSL.inline(true);
                    }
                    default -> throw new IllegalStateException();
                };
                if (arg2 instanceof QuantifiedArray) {
                    QuantifiedArray a = (QuantifiedArray)arg2;
                    t2 = new ArrayTable(a.$array()).asTable("t", "pattern");
                    q = a.$quantifier();
                } else {
                    QOM.QuantifiedSelect qs = (QOM.QuantifiedSelect)((Object)arg2);
                    t2 = new AliasedSelect(qs.$query(), true, true, false, DSL.name("pattern")).as("t");
                    q = qs.$quantifier();
                }
                ctx.visit(lhs.eq(Tools.quantify(q, DSL.select(c).from((TableLike<?>)((Object)t2)))));
            } else {
                EqQuantified.accept1(ctx, arg1, op, arg2);
            }
        }
    }

    private static final void accept1(Context<?> ctx, Field<?> arg1, Comparator op, QuantifiedSelect<?> arg2) {
        switch (ctx.family()) {
            case JAVA: {
                ctx.visit(arg1);
                CompareCondition.acceptJava(ctx, op);
                ctx.sql('(').visit(arg2).sql(')');
                break;
            }
            default: {
                ctx.visit(arg1).sql(' ').visit(op.toKeyword()).sql(' ').visit(arg2);
            }
        }
    }

    private static final Comparator inverse(Comparator operator) {
        switch (operator) {
            case IN: {
                return Comparator.NOT_IN;
            }
            case NOT_IN: {
                return Comparator.IN;
            }
            case EQUALS: {
                return Comparator.NOT_EQUALS;
            }
            case NOT_EQUALS: {
                return Comparator.EQUALS;
            }
            case LESS: {
                return Comparator.GREATER_OR_EQUAL;
            }
            case LESS_OR_EQUAL: {
                return Comparator.GREATER;
            }
            case GREATER: {
                return Comparator.LESS_OR_EQUAL;
            }
            case GREATER_OR_EQUAL: {
                return Comparator.LESS;
            }
            case IS_DISTINCT_FROM: {
                return Comparator.IS_NOT_DISTINCT_FROM;
            }
            case IS_NOT_DISTINCT_FROM: {
                return Comparator.IS_DISTINCT_FROM;
            }
            case LIKE: {
                return Comparator.NOT_LIKE;
            }
            case NOT_LIKE: {
                return Comparator.LIKE;
            }
            case SIMILAR_TO: {
                return Comparator.NOT_SIMILAR_TO;
            }
            case NOT_SIMILAR_TO: {
                return Comparator.SIMILAR_TO;
            }
            case LIKE_IGNORE_CASE: {
                return Comparator.NOT_LIKE_IGNORE_CASE;
            }
            case NOT_LIKE_IGNORE_CASE: {
                return Comparator.LIKE_IGNORE_CASE;
            }
        }
        throw new IllegalStateException();
    }

    private static final Condition comparisonCondition(Field<?> arg1, Comparator op, Field<String> arg2, Character escape) {
        switch (op) {
            case LIKE: {
                return escape != null ? arg1.like(arg2, escape.charValue()) : arg1.like(arg2);
            }
            case NOT_LIKE: {
                return escape != null ? arg1.notLike(arg2, escape.charValue()) : arg1.notLike(arg2);
            }
            case SIMILAR_TO: {
                return escape != null ? arg1.similarTo(arg2, escape.charValue()) : arg1.similarTo(arg2);
            }
            case NOT_SIMILAR_TO: {
                return escape != null ? arg1.notSimilarTo(arg2, escape.charValue()) : arg1.notSimilarTo(arg2);
            }
            case LIKE_IGNORE_CASE: {
                return escape != null ? arg1.likeIgnoreCase(arg2, escape.charValue()) : arg1.likeIgnoreCase(arg2);
            }
            case NOT_LIKE_IGNORE_CASE: {
                return escape != null ? arg1.notLikeIgnoreCase(arg2, escape.charValue()) : arg1.notLikeIgnoreCase(arg2);
            }
        }
        return arg1.compare(op, arg2);
    }

    private static final Condition comparisonCondition(Field<?> arg1, Comparator op, Object arg2, Character escape) {
        switch (op) {
            case LIKE: {
                return escape != null ? arg1.like(Convert.convert(arg2, String.class), escape.charValue()) : arg1.like(Convert.convert(arg2, String.class));
            }
            case NOT_LIKE: {
                return escape != null ? arg1.notLike(Convert.convert(arg2, String.class), escape.charValue()) : arg1.notLike(Convert.convert(arg2, String.class));
            }
            case SIMILAR_TO: {
                return escape != null ? arg1.similarTo(Convert.convert(arg2, String.class), escape.charValue()) : arg1.similarTo(Convert.convert(arg2, String.class));
            }
            case NOT_SIMILAR_TO: {
                return escape != null ? arg1.notSimilarTo(Convert.convert(arg2, String.class), escape.charValue()) : arg1.notSimilarTo(Convert.convert(arg2, String.class));
            }
            case LIKE_IGNORE_CASE: {
                return escape != null ? arg1.likeIgnoreCase(Convert.convert(arg2, String.class), escape.charValue()) : arg1.likeIgnoreCase(Convert.convert(arg2, String.class));
            }
            case NOT_LIKE_IGNORE_CASE: {
                return escape != null ? arg1.notLikeIgnoreCase(Convert.convert(arg2, String.class), escape.charValue()) : arg1.notLikeIgnoreCase(Convert.convert(arg2, String.class));
            }
        }
        return arg1.compare(op, arg2);
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.visit(this.arg1).sql(".eq(").visit(this.arg2).sql(')');
    }

    @Override
    public final Field<T> $arg1() {
        return this.arg1;
    }

    @Override
    public final QuantifiedSelect<? extends Record1<T>> $arg2() {
        return this.arg2;
    }

    @Override
    public final QOM.EqQuantified<T> $arg1(Field<T> newValue) {
        return this.$constructor().apply(newValue, (QuantifiedSelect<Record1<T>>)this.$arg2());
    }

    @Override
    public final QOM.EqQuantified<T> $arg2(QuantifiedSelect<? extends Record1<T>> newValue) {
        return this.$constructor().apply((Field<T>)this.$arg1(), (QuantifiedSelect<Record1<T>>)newValue);
    }

    @Override
    public final Function2<? super Field<T>, ? super QuantifiedSelect<? extends Record1<T>>, ? extends QOM.EqQuantified<T>> $constructor() {
        return (a1, a2) -> new EqQuantified(a1, a2);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.EqQuantified) {
            QOM.EqQuantified o = (QOM.EqQuantified)that;
            return StringUtils.equals(this.$arg1(), o.$arg1()) && StringUtils.equals(this.$arg2(), o.$arg2());
        }
        return super.equals(that);
    }
}

