/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.Timestamp;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Pattern;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.DatePart;
import org.jooq.Field;
import org.jooq.Function2;
import org.jooq.Param;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.SQLDialect;
import org.jooq.Typed;
import org.jooq.conf.RenderOptionalKeyword;
import org.jooq.conf.TransformUnneededArithmeticExpressions;
import org.jooq.exception.DataTypeException;
import org.jooq.impl.AbstractField;
import org.jooq.impl.AbstractParamX;
import org.jooq.impl.AbstractTransformable;
import org.jooq.impl.Convert;
import org.jooq.impl.DSL;
import org.jooq.impl.DateAdd;
import org.jooq.impl.ExpressionOperator;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.Transformable;
import org.jooq.impl.Val;
import org.jooq.types.DayToSecond;
import org.jooq.types.Interval;
import org.jooq.types.YearToMonth;
import org.jooq.types.YearToSecond;

final class Expression<T>
extends AbstractTransformable<T>
implements QOM.UOperator2<Field<T>, Field<?>, Expression<T>> {
    static final Set<SQLDialect> HASH_OP_FOR_BIT_XOR = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB);
    static final Set<SQLDialect> SUPPORT_YEAR_TO_SECOND = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB);
    private final ExpressionOperator operator;
    private final boolean internal;
    private final Field<T> lhs;
    private final Field<?> rhs;
    private static final Pattern TRUNC_TO_MICROS = Pattern.compile("([^.]*\\.\\d{0,6})\\d{0,3}");

    Expression(ExpressionOperator operator, boolean internal, Field<T> lhs, Field<?> rhs) {
        super(DSL.name(operator.toSQL()), lhs.getDataType());
        this.operator = operator;
        this.internal = internal;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    static final DataType<?> getExpressionDataType(AbstractField<?> l, ExpressionOperator operator, AbstractField<?> r) {
        DataType<?> lt = l.getExpressionDataType();
        DataType<?> rt = r.getExpressionDataType();
        switch (operator) {
            case MULTIPLY: 
            case DIVIDE: {
                return rt.isInterval() ? rt : lt;
            }
            case ADD: {
                return lt.isInterval() ? rt : lt;
            }
        }
        return lt;
    }

    @Override
    final DataType<?> getExpressionDataType() {
        return Expression.getExpressionDataType((AbstractField)this.lhs, this.operator, (AbstractField)this.rhs);
    }

    @Override
    final void accept0(Context<?> ctx) {
        SQLDialect family = ctx.family();
        if (family == SQLDialect.JAVA) {
            this.acceptJava(ctx);
            return;
        }
        if ((ExpressionOperator.ADD == this.operator || ExpressionOperator.SUBTRACT == this.operator) && this.lhs.getDataType().isDateTime() && (this.rhs.getDataType().isNumeric() || this.rhs.getDataType().isInterval())) {
            ctx.visit(new DateExpression<T>(this.lhs, this.operator, this.rhs));
        } else {
            ctx.sql('(').visit(this.lhs).sql(' ').sql(this.operator.toSQL()).sql(' ').visit(this.rhs).sql(')');
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.visit(this.lhs).sql('.');
        switch (this.operator) {
            case ADD: {
                ctx.sql("plus");
                break;
            }
            case CONCAT: {
                ctx.sql("concat");
                break;
            }
            case DIVIDE: {
                ctx.sql("div");
                break;
            }
            case MULTIPLY: {
                ctx.sql("times");
                break;
            }
            case SUBTRACT: {
                ctx.sql("minus");
            }
        }
        ctx.sql('(').visit(Val.getJavaValue(this.rhs)).sql(')');
    }

    @Override
    public final Field<T> transform(TransformUnneededArithmeticExpressions transform) {
        return Expression.transform(this, this.lhs, this.operator, this.rhs, this.internal, transform);
    }

    static final <T, R extends QOM.UOperator2<Field<T>, Field<T>, R>> Field<T> transform(R expression, Field<T> lhs, ExpressionOperator operator, Field<T> rhs, boolean internal, TransformUnneededArithmeticExpressions transform) {
        Field<T> newRhs;
        Field<T> newLhs;
        if (lhs instanceof Transformable) {
            Transformable t2 = (Transformable)lhs;
            v0 = t2.transform(transform);
        } else {
            v0 = newLhs = lhs;
        }
        if (rhs instanceof Transformable) {
            Transformable t3 = (Transformable)rhs;
            v1 = t3.transform(transform);
        } else {
            v1 = newRhs = rhs;
        }
        if (internal || transform == TransformUnneededArithmeticExpressions.ALWAYS) {
            boolean iRhs;
            Param p;
            Param p2;
            if (Expression.isIdentity(operator, newRhs)) {
                return newLhs;
            }
            if (operator.commutative() && Expression.isIdentity(operator, newLhs)) {
                return newRhs;
            }
            Param pLhs = newLhs instanceof Param ? (p2 = (Param)newLhs) : null;
            Param pRhs = newRhs instanceof Param ? (p = (Param)newRhs) : null;
            boolean iLhs = pLhs != null && pLhs.isInline();
            boolean bl = iRhs = pRhs != null && pRhs.isInline();
            if (iLhs && pLhs.getValue() == null) {
                return pLhs;
            }
            if (iRhs && pRhs.getValue() == null) {
                return pRhs;
            }
            if (iLhs && operator == ExpressionOperator.MULTIPLY && Convert.convert(pLhs.getValue(), Double.TYPE) == 0.0) {
                return pLhs;
            }
            if (iRhs && operator == ExpressionOperator.MULTIPLY && Convert.convert(pRhs.getValue(), Double.TYPE) == 0.0) {
                return pRhs;
            }
            if (iLhs && iRhs) {
                DataType ld = pLhs.getDataType();
                DataType rd = pRhs.getDataType();
                if (ld.isNumeric() && ld.equals(rd)) {
                    switch (operator) {
                        case ADD: {
                            return DSL.inline((Object)(Convert.convert(pLhs.getValue(), Double.TYPE) + Convert.convert(pRhs.getValue(), Double.TYPE)), ld);
                        }
                        case SUBTRACT: {
                            return DSL.inline((Object)(Convert.convert(pLhs.getValue(), Double.TYPE) - Convert.convert(pRhs.getValue(), Double.TYPE)), ld);
                        }
                        case MULTIPLY: {
                            return DSL.inline((Object)(Convert.convert(pLhs.getValue(), Double.TYPE) * Convert.convert(pRhs.getValue(), Double.TYPE)), ld);
                        }
                        case DIVIDE: {
                            return DSL.inline((Object)(Convert.convert(pLhs.getValue(), Double.TYPE) / Convert.convert(pRhs.getValue(), Double.TYPE)), ld);
                        }
                    }
                }
            }
        }
        if (newLhs != lhs || newRhs != rhs) {
            return (Field)expression.$constructor().apply(newLhs, newRhs);
        }
        return (Field)((Object)expression);
    }

    @Pro
    static final boolean isIdentity(ExpressionOperator operator, Field<?> field2) {
        if (!(field2 instanceof Param)) {
            return false;
        }
        Param param2 = (Param)field2;
        switch (operator) {
            case ADD: 
            case SUBTRACT: {
                return field2.getDataType().isNumeric() && param2.isInline() && param2.getValue() != null && Convert.convert(param2.getValue(), Double.TYPE) == 0.0;
            }
            case MULTIPLY: 
            case DIVIDE: {
                return field2.getDataType().isNumeric() && param2.isInline() && param2.getValue() != null && Convert.convert(param2.getValue(), Double.TYPE) == 1.0;
            }
        }
        return false;
    }

    private final Field<Number> lhsAsNumber() {
        return this.lhs;
    }

    private final Field<Number> rhsAsNumber() {
        return this.rhs;
    }

    static final <Q1 extends QueryPart, Q2 extends Q1> void acceptAssociative(Context<?> ctx, Q2 exp, Function<? super Q2, ? extends Expr<Q1>> expProvider, Consumer<? super Context<?>> formatSeparator) {
        Object o;
        Expr<Q1> e = expProvider.apply(exp);
        Class<?> expType = exp.getClass();
        ArrayList<Object> elements = new ArrayList<Object>();
        ArrayDeque<Expr<Object>> queue = new ArrayDeque<Expr<Object>>();
        queue.push(e);
        while ((o = queue.pollFirst()) != null) {
            if (o instanceof Expr) {
                boolean associativity;
                Expr p = (Expr)o;
                boolean bl = associativity = (!(p.lhs instanceof Typed) || !(p.rhs instanceof Typed) || ((Typed)p.lhs).getDataType().equals(((Typed)p.rhs).getDataType())) && !RenderOptionalKeyword.ON.equals((Object)ctx.settings().getRenderOptionalAssociativityParentheses());
                if (associativity && expType.isInstance(p.rhs)) {
                    queue.push(expProvider.apply(p.rhs));
                } else {
                    queue.push((Expr<Object>)p.rhs);
                }
                if (associativity && expType.isInstance(p.lhs)) {
                    queue.push(expProvider.apply(p.lhs));
                    continue;
                }
                elements.add(p.lhs);
                continue;
            }
            elements.add((QueryPart)o);
        }
        for (int i = 0; i < elements.size(); ++i) {
            if (i > 0) {
                formatSeparator.accept(ctx);
                ctx.visit(e.op).sql(' ');
            }
            ctx.visit((QueryPart)elements.get(i));
        }
    }

    @Override
    public final Field<T> $arg1() {
        return this.lhs;
    }

    @Override
    public final Field<?> $arg2() {
        return this.rhs;
    }

    @Override
    public final Expression<T> $arg1(Field<T> newValue) {
        return this.$constructor().apply(newValue, (Field<?>)this.$arg2());
    }

    @Override
    public final Expression<T> $arg2(Field<?> newValue) {
        return this.$constructor().apply((Field<T>)this.$arg1(), newValue);
    }

    @Override
    public final Function2<? super Field<T>, ? super Field<?>, ? extends Expression<T>> $constructor() {
        return (a1, a2) -> new Expression<T>(this.operator, this.internal, a1, (Field<?>)a2);
    }

    private static class DateExpression<T>
    extends AbstractField<T>
    implements QOM.UTransient {
        private final Field<T> lhs;
        private final ExpressionOperator operator;
        private final Field<?> rhs;

        DateExpression(Field<T> lhs, ExpressionOperator operator, Field<?> rhs) {
            super(DSL.name(operator.toSQL()), lhs.getDataType());
            this.lhs = lhs;
            this.operator = operator;
            this.rhs = rhs;
        }

        private final <U> Field<U> p(U u) {
            Param<U> result = DSL.val(u);
            if (((Param)this.rhs).isInline()) {
                ((AbstractParamX)result).setInline0(true);
            }
            return result;
        }

        @Override
        public final void accept(Context<?> ctx) {
            if (this.rhs.getType() == YearToSecond.class && !SUPPORT_YEAR_TO_SECOND.contains((Object)ctx.dialect())) {
                this.acceptYTSExpression(ctx);
            } else if (this.rhs.getDataType().isInterval()) {
                this.acceptIntervalExpression(ctx);
            } else {
                this.acceptNumberExpression(ctx);
            }
        }

        private final void acceptYTSExpression(Context<?> ctx) {
            if (this.rhs instanceof Param) {
                YearToSecond yts = this.rhsAsYTS();
                ctx.visit(new DateExpression<T>(new DateExpression<T>(this.lhs, this.operator, this.p(yts.getYearToMonth())), this.operator, this.p(yts.getDayToSecond())));
            } else {
                this.acceptIntervalExpression(ctx);
            }
        }

        private final void acceptIntervalExpression(Context<?> ctx) {
            SQLDialect family = ctx.family();
            int sign = this.operator == ExpressionOperator.ADD ? 1 : -1;
            switch (family) {
                case AURORA_MYSQL: 
                case MEMSQL: 
                case CUBRID: 
                case MARIADB: 
                case MYSQL: {
                    Interval interval = this.rhsAsInterval();
                    if (this.operator == ExpressionOperator.SUBTRACT) {
                        interval = interval.neg();
                    }
                    if (this.rhs.getType() == YearToMonth.class) {
                        ctx.visit(Names.N_DATE_ADD).sql('(').visit(this.lhs).sql(", ").visit(Keywords.K_INTERVAL).sql(' ').visit(Tools.field((Object)interval, SQLDataType.VARCHAR)).sql(' ').visit(Keywords.K_YEAR_MONTH).sql(')');
                        break;
                    }
                    if (family == SQLDialect.CUBRID) {
                        ctx.visit(Names.N_DATE_ADD).sql('(').visit(this.lhs).sql(", ").visit(Keywords.K_INTERVAL).sql(' ').visit(Tools.field((Object)interval, SQLDataType.VARCHAR)).sql(' ').visit(Keywords.K_DAY_MILLISECOND).sql(')');
                        break;
                    }
                    ctx.visit(Names.N_DATE_ADD).sql('(').visit(this.lhs).sql(", ").visit(Keywords.K_INTERVAL).sql(' ').visit(Tools.field((Object)TRUNC_TO_MICROS.matcher(String.valueOf(interval)).replaceAll("$1"), SQLDataType.VARCHAR)).sql(' ').visit(Keywords.K_DAY_MICROSECOND).sql(')');
                    break;
                }
                case DERBY: {
                    boolean needsCast;
                    boolean bl = needsCast = this.getDataType().getType() != Timestamp.class;
                    if (needsCast) {
                        ctx.visit(Keywords.K_CAST).sql('(');
                    }
                    if (this.rhs.getType() == YearToMonth.class) {
                        ctx.sql("{fn ").visit(Names.N_TIMESTAMPADD).sql('(').visit(Names.N_SQL_TSI_MONTH).sql(", ").visit(this.p(sign * this.rhsAsYTM().intValue())).sql(", ").visit(this.lhs).sql(") }");
                    } else {
                        ctx.sql("{fn ").visit(Names.N_TIMESTAMPADD).sql('(').visit(Names.N_SQL_TSI_SECOND).sql(", ").visit(this.p((long)sign * (long)this.rhsAsDTS().getTotalSeconds())).sql(", {fn ").visit(Names.N_TIMESTAMPADD).sql('(').visit(ctx.family() == SQLDialect.DERBY ? Names.N_SQL_TSI_FRAC_SECOND : Names.N_SQL_TSI_MILLI_SECOND).sql(", ").visit(this.p((long)sign * (long)this.rhsAsDTS().getMilli() * (ctx.family() == SQLDialect.DERBY ? 1000000L : 1L))).sql(", ").visit(this.lhs).sql(") }) }");
                    }
                    if (!needsCast) break;
                    ctx.sql(' ').visit(Keywords.K_AS).sql(' ').visit(DSL.keyword(this.getDataType().getCastTypeName(ctx.configuration()))).sql(')');
                    break;
                }
                case FIREBIRD: {
                    if (this.rhs.getType() == YearToMonth.class) {
                        ctx.visit(Names.N_DATEADD).sql('(').visit(Keywords.K_MONTH).sql(", ").visit(this.p(sign * this.rhsAsYTM().intValue())).sql(", ").visit(this.lhs).sql(')');
                        break;
                    }
                    if (this.rhsAsDTS().getMilli() > 0) {
                        ctx.visit(Names.N_DATEADD).sql('(').visit(Keywords.K_MILLISECOND).sql(", ").visit(this.p((long)sign * (long)this.rhsAsDTS().getMilli())).sql(", ").visit(Names.N_DATEADD).sql('(').visit(Keywords.K_SECOND).sql(", ").visit(this.p((long)sign * (long)this.rhsAsDTS().getTotalSeconds())).sql(", ").visit(this.lhs).sql(')').sql(')');
                        break;
                    }
                    ctx.visit(Names.N_DATEADD).sql('(').visit(Keywords.K_SECOND).sql(", ").visit(this.p((long)sign * (long)this.rhsAsDTS().getTotalSeconds())).sql(", ").visit(this.lhs).sql(')');
                    break;
                }
                case SQLITE: {
                    boolean ytm = this.rhs.getType() == YearToMonth.class;
                    Field<Object> interval = this.p(ytm ? (double)this.rhsAsYTM().intValue() : this.rhsAsDTS().getTotalSeconds());
                    if (sign < 0) {
                        interval = interval.neg();
                    }
                    interval = interval.concat(DSL.inline(ytm ? " months" : " seconds"));
                    ctx.visit(Names.N_STRFTIME).sql("('%Y-%m-%d %H:%M:%f', ").visit(this.lhs).sql(", ").visit(interval).sql(')');
                    break;
                }
                case ACCESS: {
                    if (this.rhs.getType() == YearToMonth.class) {
                        ctx.visit(DSL.timestampAdd(this.lhs, this.p(sign * this.rhsAsYTM().intValue()), DatePart.MONTH));
                        break;
                    }
                    ctx.visit(DSL.timestampAdd(this.lhs, this.p((double)sign * this.rhsAsDTS().getTotalSeconds()), DatePart.SECOND));
                    break;
                }
                case ASE: 
                case SQLDATAWAREHOUSE: 
                case SQLSERVER: 
                case SYBASE: {
                    if (this.rhs.getType() == YearToMonth.class) {
                        ctx.visit(Names.N_DATEADD).sql('(').visit(DSL.keyword("mm")).sql(", ").visit(this.p(sign * this.rhsAsYTM().intValue())).sql(", ").visit(this.lhs).sql(')');
                        break;
                    }
                    Field<Timestamp> lhsAsTS = Tools.castIfNeeded(this.lhs, Timestamp.class);
                    DayToSecond interval = this.rhsAsDTS();
                    if (interval.getNano() != 0) {
                        if (family == SQLDialect.SQLSERVER || family == SQLDialect.SQLDATAWAREHOUSE) {
                            ctx.visit(Names.N_DATEADD).sql('(').visit(DSL.keyword("ss")).sql(", ").visit(this.p((long)sign * (long)interval.getTotalSeconds())).sql(", ").visit(Names.N_DATEADD).sql('(').visit(DSL.keyword("ns")).sql(", ").visit(this.p(sign * interval.getNano())).sql(", ").visit(lhsAsTS).sql("))");
                            break;
                        }
                        ctx.visit(Names.N_DATEADD).sql('(').visit(DSL.keyword("ss")).sql(", ").visit(this.p((long)sign * (long)interval.getTotalSeconds())).sql(", ").visit(Names.N_DATEADD).sql('(').visit(DSL.keyword("us")).sql(", ").visit(this.p(sign * interval.getMicro())).sql(", ").visit(lhsAsTS).sql("))");
                        break;
                    }
                    ctx.visit(Names.N_DATEADD).sql('(').visit(DSL.keyword("ss")).sql(", ").visit(this.p((long)sign * (long)interval.getTotalSeconds())).sql(", ").visit(lhsAsTS).sql(')');
                    break;
                }
                case HANA: {
                    if (this.rhs.getType() == YearToMonth.class) {
                        Field<Integer> m4 = this.p(this.rhsAsYTM().intValue());
                        ctx.visit(Names.N_ADD_MONTHS).sql('(').visit(this.lhs).sql(", ").visit(this.operator == ExpressionOperator.ADD ? m4 : m4.neg()).sql(')');
                        break;
                    }
                    DayToSecond dts1 = this.rhsAsDTS();
                    DayToSecond dts2 = new DayToSecond(0, dts1.getHours(), dts1.getMinutes(), dts1.getSeconds(), dts1.getNano());
                    if (dts1.getSign() < 0) {
                        dts2 = dts2.neg();
                    }
                    Field<Integer> d = this.p(dts1.getDays());
                    Field<Double> m5 = this.p(dts2.getTotalMilli() / 1000.0);
                    ctx.visit(Names.N_ADD_SECONDS).sql('(').visit(Names.N_ADD_DAYS).sql('(').visit(this.lhs).sql(", ").visit(this.operator != ExpressionOperator.ADD ^ dts1.getSign() < 0 ? d.neg() : d).sql(')').sql(", ").visit(this.operator == ExpressionOperator.ADD ? m5 : m5.neg()).sql(')');
                    break;
                }
                case INFORMIX: {
                    if (this.rhs.getType() == YearToMonth.class) {
                        if (this.operator == ExpressionOperator.ADD) {
                            ctx.visit(this.lhs.add(DSL.field("{0} {units month}", this.p(this.rhsAsYTM().intValue()))));
                            break;
                        }
                        ctx.visit(this.lhs.sub(DSL.field("{0} {units month}", this.p(this.rhsAsYTM().intValue()))));
                        break;
                    }
                    DataType type = this.lhs.getDataType();
                    if (this.operator == ExpressionOperator.ADD) {
                        ctx.visit(Tools.castIfNeeded(Tools.castIfNeeded(this.lhs, Timestamp.class).add(DSL.field("{0} units fraction", this.p(this.rhsAsDTS().getTotalMilli() / 1000.0))), type));
                        break;
                    }
                    ctx.visit(Tools.castIfNeeded(Tools.castIfNeeded(this.lhs, Timestamp.class).sub(DSL.field("{0} units fraction", this.p(this.rhsAsDTS().getTotalMilli() / 1000.0))), type));
                    break;
                }
                case DB2: {
                    if (this.rhs.getType() == YearToMonth.class) {
                        if (this.operator == ExpressionOperator.ADD) {
                            ctx.visit(this.lhs.add(DSL.field("{0} month", this.p(this.rhsAsYTM().intValue()))));
                            break;
                        }
                        ctx.visit(this.lhs.sub(DSL.field("{0} month", this.p(this.rhsAsYTM().intValue()))));
                        break;
                    }
                    DataType type = this.lhs.getDataType();
                    if (this.operator == ExpressionOperator.ADD) {
                        ctx.visit(Tools.castIfNeeded(Tools.castIfNeeded(this.lhs, Timestamp.class).add(DSL.field("{0} microseconds", this.p(this.rhsAsDTS().getTotalMicro()))), type));
                        break;
                    }
                    ctx.visit(Tools.castIfNeeded(Tools.castIfNeeded(this.lhs, Timestamp.class).sub(DSL.field("{0} microseconds", this.p(this.rhsAsDTS().getTotalMicro()))), type));
                    break;
                }
                case REDSHIFT: {
                    if (this.rhs.getType() == YearToMonth.class) {
                        if (this.operator == ExpressionOperator.ADD) {
                            ctx.visit(this.lhs.add(DSL.field("({0} || ' month')::interval", this.p(this.rhsAsYTM().intValue()))));
                            break;
                        }
                        ctx.visit(this.lhs.sub(DSL.field("({0} || ' month')::interval", this.p(this.rhsAsYTM().intValue()))));
                        break;
                    }
                    DataType type = this.lhs.getDataType();
                    if (this.operator == ExpressionOperator.ADD) {
                        ctx.visit(Tools.castIfNeeded(Tools.castIfNeeded(this.lhs, SQLDataType.TIMESTAMP).add(DSL.field("({0} || ' second')::interval", this.p(this.rhsAsDTS().getTotalSeconds()))), type));
                        break;
                    }
                    ctx.visit(Tools.castIfNeeded(Tools.castIfNeeded(this.lhs, SQLDataType.TIMESTAMP).sub(DSL.field("({0} || ' second')::interval", this.p(this.rhsAsDTS().getTotalSeconds()))), type));
                    break;
                }
                case BIGQUERY: {
                    if (this.rhs.getType() == YearToMonth.class) {
                        if (this.operator == ExpressionOperator.ADD) {
                            ctx.visit(Names.N_TIMESTAMP_ADD).sql('(').visit(this.lhs).sql(", ").visit(Keywords.K_INTERVAL).sql(' ').visit(this.p(this.rhsAsYTM().intValue())).sql(' ').visit(Keywords.K_MONTH).sql(')');
                            break;
                        }
                        ctx.visit(Names.N_TIMESTAMP_SUB).sql('(').visit(this.lhs).sql(", ").visit(Keywords.K_INTERVAL).sql(' ').visit(this.p(this.rhsAsYTM().intValue())).sql(' ').visit(Keywords.K_MONTH).sql(')');
                        break;
                    }
                    if (this.operator == ExpressionOperator.ADD) {
                        ctx.visit(Names.N_TIMESTAMP_ADD).sql('(').visit(Tools.castIfNeeded(this.lhs, SQLDataType.TIMESTAMP)).sql(", ").visit(Keywords.K_INTERVAL).sql(' ').visit(this.p((long)this.rhsAsDTS().getTotalMilli())).sql(' ').visit(Keywords.K_MILLISECOND).sql(')');
                        break;
                    }
                    ctx.visit(Names.N_TIMESTAMP_SUB).sql('(').visit(Tools.castIfNeeded(this.lhs, SQLDataType.TIMESTAMP)).sql(", ").visit(Keywords.K_INTERVAL).sql(' ').visit(this.p((long)this.rhsAsDTS().getTotalMilli())).sql(' ').visit(Keywords.K_MILLISECOND).sql(')');
                    break;
                }
                case EXASOL: {
                    Field<?> r = DSL.inlined(this.rhs);
                    if (this.rhs.getType() == YearToMonth.class) {
                        if (this.operator == ExpressionOperator.ADD) {
                            ctx.visit(this.lhs).sql(" + ").visit(Keywords.K_INTERVAL).sql(' ').visit(r).sql(' ').visit(Keywords.K_YEAR_TO_MONTH);
                            break;
                        }
                        ctx.visit(this.lhs).sql(" - ").visit(Keywords.K_INTERVAL).sql(' ').visit(r).sql(' ').visit(Keywords.K_YEAR_TO_MONTH);
                        break;
                    }
                    if (this.operator == ExpressionOperator.ADD) {
                        ctx.visit(this.lhs).sql(" + ").visit(Keywords.K_INTERVAL).sql(' ').visit(r).sql(' ').visit(Keywords.K_DAY).sql("(9) ").visit(Keywords.K_TO).sql(' ').visit(Keywords.K_SECOND).sql("(9)");
                        break;
                    }
                    ctx.visit(this.lhs).sql(" - ").visit(Keywords.K_INTERVAL).sql(' ').visit(r).sql(' ').visit(Keywords.K_DAY).sql("(9) ").visit(Keywords.K_TO).sql(' ').visit(Keywords.K_SECOND).sql("(9)");
                    break;
                }
                case TERADATA: {
                    Field<?> r = this.rhs;
                    if (this.rhs.getType() == YearToMonth.class) {
                        if (this.operator == ExpressionOperator.ADD) {
                            ctx.visit(this.lhs).sql(" + ").visit(Keywords.K_INTERVAL).sql(' ').visit(r).sql(' ').visit(Keywords.K_YEAR_TO_MONTH);
                            break;
                        }
                        ctx.visit(this.lhs).sql(" - ").visit(Keywords.K_INTERVAL).sql(' ').visit(r).sql(' ').visit(Keywords.K_YEAR_TO_MONTH);
                        break;
                    }
                    if (this.operator == ExpressionOperator.ADD) {
                        ctx.visit(this.lhs).sql(" + ").visit(Keywords.K_INTERVAL).sql(' ').visit(r).sql(' ').visit(Keywords.K_DAY_TO_SECOND);
                        break;
                    }
                    ctx.visit(this.lhs).sql(" - ").visit(Keywords.K_INTERVAL).sql(' ').visit(r).sql(' ').visit(Keywords.K_DAY_TO_SECOND);
                    break;
                }
                default: {
                    ctx.sql('(').visit(this.lhs).sql(' ').sql(this.operator.toSQL()).sql(' ').visit(this.rhs).sql(')');
                }
            }
        }

        private final void acceptNumberExpression(Context<?> ctx) {
            switch (ctx.family()) {
                case ACCESS: {
                    if (this.operator == ExpressionOperator.ADD) {
                        ctx.visit(DSL.timestampAdd(this.lhs, this.rhsAsNumber(), DatePart.DAY));
                        break;
                    }
                    ctx.visit(DSL.timestampAdd(this.lhs, this.rhsAsNumber().neg(), DatePart.DAY));
                    break;
                }
                case HANA: {
                    if (this.operator == ExpressionOperator.ADD) {
                        ctx.visit(Names.N_ADD_DAYS).sql('(').visit(this.lhs).sql(", ").visit(this.rhsAsNumber()).sql(')');
                        break;
                    }
                    ctx.visit(Names.N_ADD_DAYS).sql('(').visit(this.lhs).sql(", ").visit(this.rhsAsNumber().neg()).sql(')');
                    break;
                }
                case BIGQUERY: {
                    if (this.operator == ExpressionOperator.ADD) {
                        ctx.visit(Names.N_TIMESTAMP_ADD).sql('(').visit(this.lhs).sql(", ").visit(Keywords.K_INTERVAL).sql(' ').visit(Tools.castIfNeeded(this.rhsAsNumber(), SQLDataType.INTEGER)).sql(' ').visit(Keywords.K_DAY).sql(')');
                        break;
                    }
                    ctx.visit(Names.N_TIMESTAMP_SUB).sql('(').visit(this.lhs).sql(", ").visit(Keywords.K_INTERVAL).sql(' ').visit(Tools.castIfNeeded(this.rhsAsNumber(), SQLDataType.INTEGER)).sql(' ').visit(Keywords.K_DAY).sql(')');
                    break;
                }
                case FIREBIRD: 
                case ASE: 
                case SQLDATAWAREHOUSE: 
                case SQLSERVER: 
                case SYBASE: 
                case SNOWFLAKE: {
                    if (this.operator == ExpressionOperator.ADD) {
                        ctx.visit(Names.N_DATEADD).sql('(').visit(Keywords.K_DAY).sql(", ").visit(this.rhsAsNumber()).sql(", ").visit(this.lhs).sql(')');
                        break;
                    }
                    ctx.visit(Names.N_DATEADD).sql('(').visit(Keywords.K_DAY).sql(", ").visit(this.rhsAsNumber().neg()).sql(", ").visit(this.lhs).sql(')');
                    break;
                }
                case DB2: 
                case HSQLDB: {
                    if (this.operator == ExpressionOperator.ADD) {
                        ctx.visit(this.lhs.add(DSL.field("({0}) day", this.rhsAsNumber())));
                        break;
                    }
                    ctx.visit(this.lhs.sub(DSL.field("({0}) day", this.rhsAsNumber())));
                    break;
                }
                case DERBY: {
                    boolean needsCast;
                    boolean bl = needsCast = this.getDataType().getType() != Timestamp.class;
                    if (needsCast) {
                        ctx.visit(Keywords.K_CAST).sql('(');
                    }
                    if (this.operator == ExpressionOperator.ADD) {
                        ctx.sql("{fn ").visit(Names.N_TIMESTAMPADD).sql('(').visit(DSL.keyword("sql_tsi_day")).sql(", ").visit(this.rhsAsNumber()).sql(", ").visit(this.lhs).sql(") }");
                    } else {
                        ctx.sql("{fn ").visit(Names.N_TIMESTAMPADD).sql('(').visit(DSL.keyword("sql_tsi_day")).sql(", ").visit(this.rhsAsNumber().neg()).sql(", ").visit(this.lhs).sql(") }");
                    }
                    if (!needsCast) break;
                    ctx.sql(' ').visit(Keywords.K_AS).sql(' ').visit(DSL.keyword(this.getDataType().getCastTypeName(ctx.configuration()))).sql(')');
                    break;
                }
                case AURORA_MYSQL: 
                case MEMSQL: 
                case CUBRID: 
                case MARIADB: 
                case MYSQL: {
                    if (this.operator == ExpressionOperator.ADD) {
                        ctx.visit(Names.N_DATE_ADD).sql('(').visit(this.lhs).sql(", ").visit(Keywords.K_INTERVAL).sql(' ').visit(this.rhsAsNumber()).sql(' ').visit(Keywords.K_DAY).sql(')');
                        break;
                    }
                    ctx.visit(Names.N_DATE_ADD).sql('(').visit(this.lhs).sql(", ").visit(Keywords.K_INTERVAL).sql(' ').visit(this.rhsAsNumber().neg()).sql(' ').visit(Keywords.K_DAY).sql(')');
                    break;
                }
                case INFORMIX: {
                    if (this.operator == ExpressionOperator.ADD) {
                        ctx.sql('(').visit(this.lhs).sql(" + ").visit(this.rhsAsNumber()).sql(' ').visit(DSL.keyword("units day")).sql(')');
                        break;
                    }
                    ctx.sql('(').visit(this.lhs).sql(" - ").visit(this.rhsAsNumber()).sql(' ').visit(DSL.keyword("units day")).sql(')');
                    break;
                }
                case INGRES: {
                    if (this.operator == ExpressionOperator.ADD) {
                        ctx.visit(this.lhs.add(DSL.field("{date}({0} || ' days')", Object.class, this.rhsAsNumber())));
                        break;
                    }
                    ctx.visit(this.lhs.sub(DSL.field("{date}({0} || ' days')", Object.class, this.rhsAsNumber())));
                    break;
                }
                case REDSHIFT: 
                case AURORA_POSTGRES: 
                case COCKROACHDB: 
                case POSTGRES: 
                case YUGABYTEDB: {
                    if (this.operator == ExpressionOperator.ADD) {
                        ctx.visit(new DateAdd<T>(this.lhs, this.rhsAsNumber(), DatePart.DAY));
                        break;
                    }
                    ctx.visit(new DateAdd<T>(this.lhs, this.rhsAsNumber().neg(), DatePart.DAY));
                    break;
                }
                case SQLITE: {
                    if (this.operator == ExpressionOperator.ADD) {
                        ctx.visit(Names.N_STRFTIME).sql("('%Y-%m-%d %H:%M:%f', ").visit(this.lhs).sql(", ").visit(this.rhsAsNumber().concat(DSL.inline(" day"))).sql(')');
                        break;
                    }
                    ctx.visit(Names.N_STRFTIME).sql("('%Y-%m-%d %H:%M:%f', ").visit(this.lhs).sql(", ").visit(this.rhsAsNumber().neg().concat(DSL.inline(" day"))).sql(')');
                    break;
                }
                case TERADATA: {
                    if (this.operator == ExpressionOperator.ADD) {
                        ctx.visit(this.lhs).sql(" + ").visit(Keywords.K_CAST).sql('(').visit(this.rhs).sql(" || ' 00:00:00' ").visit(Keywords.K_AS).sql(' ').visit(Keywords.K_INTERVAL).sql(' ').visit(Keywords.K_DAY_TO_SECOND).sql(')');
                        break;
                    }
                    ctx.visit(this.lhs).sql(" - ").visit(Keywords.K_CAST).sql('(').visit(this.rhs).sql(" || ' 00:00:00' ").visit(Keywords.K_AS).sql(' ').visit(Keywords.K_INTERVAL).sql(' ').visit(Keywords.K_DAY_TO_SECOND).sql(')');
                    break;
                }
                default: {
                    ctx.sql('(').visit(this.lhs).sql(' ').sql(this.operator.toSQL()).sql(' ').visit(this.rhs).sql(')');
                }
            }
        }

        private final YearToSecond rhsAsYTS() {
            try {
                return (YearToSecond)((Param)this.rhs).getValue();
            }
            catch (ClassCastException e) {
                throw new DataTypeException("Cannot perform datetime arithmetic with a non-numeric, non-interval data type on the right hand side of the expression: " + String.valueOf(this.rhs), e);
            }
        }

        private final YearToMonth rhsAsYTM() {
            try {
                return (YearToMonth)((Param)this.rhs).getValue();
            }
            catch (ClassCastException e) {
                throw new DataTypeException("Cannot perform datetime arithmetic with a non-numeric, non-interval data type on the right hand side of the expression: " + String.valueOf(this.rhs), e);
            }
        }

        private final DayToSecond rhsAsDTS() {
            try {
                return (DayToSecond)((Param)this.rhs).getValue();
            }
            catch (ClassCastException e) {
                throw new DataTypeException("Cannot perform datetime arithmetic with a non-numeric, non-interval data type on the right hand side of the expression: " + String.valueOf(this.rhs), e);
            }
        }

        private final Interval rhsAsInterval() {
            try {
                return (Interval)((Param)this.rhs).getValue();
            }
            catch (ClassCastException e) {
                throw new DataTypeException("Cannot perform datetime arithmetic with a non-numeric, non-interval data type on the right hand side of the expression: " + String.valueOf(this.rhs), e);
            }
        }

        private final Field<Number> rhsAsNumber() {
            return this.rhs;
        }
    }

    record Expr<Q extends QueryPart>(Q lhs, QueryPart op, Q rhs) {
    }
}

