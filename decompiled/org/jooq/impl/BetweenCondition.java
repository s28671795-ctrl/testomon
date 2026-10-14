/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.BetweenAndStep;
import org.jooq.Clause;
import org.jooq.Condition;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function3;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.RowN;
import org.jooq.SQLDialect;
import org.jooq.impl.AbstractCondition;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;

final class BetweenCondition<T>
extends AbstractCondition
implements BetweenAndStep<T>,
QOM.Between<T> {
    private static final Clause[] CLAUSES_BETWEEN = new Clause[]{Clause.CONDITION, Clause.CONDITION_BETWEEN};
    private static final Clause[] CLAUSES_BETWEEN_SYMMETRIC = new Clause[]{Clause.CONDITION, Clause.CONDITION_BETWEEN_SYMMETRIC};
    private static final Clause[] CLAUSES_NOT_BETWEEN = new Clause[]{Clause.CONDITION, Clause.CONDITION_NOT_BETWEEN};
    private static final Clause[] CLAUSES_NOT_BETWEEN_SYMMETRIC = new Clause[]{Clause.CONDITION, Clause.CONDITION_NOT_BETWEEN_SYMMETRIC};
    private static final Set<SQLDialect> NO_SUPPORT_SYMMETRIC = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.BIGQUERY, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA);
    private final boolean symmetric;
    private final boolean not;
    final Field<T> field;
    final Field<T> minValue;
    Field<T> maxValue;

    BetweenCondition(Field<T> field2, Field<T> minValue, boolean not, boolean symmetric) {
        this.field = Tools.nullableIf(false, Tools.nullSafe(field2, minValue.getDataType()));
        this.minValue = Tools.nullableIf(false, Tools.nullSafe(minValue, field2.getDataType()));
        this.not = not;
        this.symmetric = symmetric;
    }

    @Override
    public final Condition and(T value) {
        return this.and((Field)DSL.val(value, this.field.getDataType()));
    }

    @Override
    public final Condition and(Field f) {
        if (this.maxValue == null) {
            this.maxValue = Tools.nullableIf(false, Tools.nullSafe(f, this.field.getDataType()));
            return this;
        }
        return super.and(f);
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return this.not ? (this.symmetric ? CLAUSES_NOT_BETWEEN_SYMMETRIC : CLAUSES_NOT_BETWEEN) : (this.symmetric ? CLAUSES_BETWEEN_SYMMETRIC : CLAUSES_BETWEEN);
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (this.field.getDataType().isEmbeddable() && this.minValue.getDataType().isEmbeddable() && this.maxValue.getDataType().isEmbeddable()) {
            RowN f = DSL.row(Tools.embeddedFields(this.field));
            RowN min2 = DSL.row(Tools.embeddedFields(this.minValue));
            RowN max = DSL.row(Tools.embeddedFields(this.maxValue));
            ctx.visit(this.not ? (this.symmetric ? f.notBetweenSymmetric(min2).and(max) : f.notBetween(min2).and(max)) : (this.symmetric ? f.betweenSymmetric(min2).and(max) : f.between(min2).and(max)));
        } else if (ctx.family() == SQLDialect.JAVA) {
            ctx.visit(this.field).sql('.').sql(this.not ? "notBetween" : "between");
            if (this.symmetric) {
                ctx.sql("Symmetric");
            }
            ctx.sql('(').visit(this.minValue).sql(").and(").visit(this.maxValue).sql(')');
        } else if (this.symmetric && NO_SUPPORT_SYMMETRIC.contains((Object)ctx.dialect())) {
            ctx.visit(this.not ? this.field.notBetween(this.minValue, this.maxValue).and(this.field.notBetween(this.maxValue, this.minValue)) : this.field.between(this.minValue, this.maxValue).or(this.field.between(this.maxValue, this.minValue)));
        } else {
            ctx.visit(this.field);
            if (this.not) {
                ctx.sql(' ').visit(Keywords.K_NOT);
            }
            ctx.sql(' ').visit(Keywords.K_BETWEEN);
            if (this.symmetric) {
                ctx.sql(' ').visit(Keywords.K_SYMMETRIC);
            }
            ctx.sql(' ').visit(this.minValue);
            ctx.sql(' ').visit(Keywords.K_AND);
            ctx.sql(' ').visit(this.maxValue);
        }
    }

    @Override
    public final Function3<? super Field<T>, ? super Field<T>, ? super Field<T>, ? extends QOM.Between<T>> $constructor() {
        return (f, min2, max) -> (QOM.Between)new BetweenCondition<T>(f, min2, this.not, this.symmetric).and((Field)max);
    }

    @Override
    public final Field<T> $arg1() {
        return this.field;
    }

    @Override
    public final Field<T> $arg2() {
        return this.minValue;
    }

    @Override
    public final Field<T> $arg3() {
        return this.maxValue;
    }

    @Override
    public final boolean $symmetric() {
        return this.symmetric;
    }

    @Override
    public final QOM.Between<T> $symmetric(boolean s2) {
        return (QOM.Between)new BetweenCondition<T>(this.$arg1(), this.$arg2(), this.not, s2).and((Field)this.$arg3());
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.field, this.minValue, this.maxValue, this.not ? (this.symmetric ? Field::notBetweenSymmetric : Field::notBetween) : (this.symmetric ? Field::betweenSymmetric : Field::between), replacer);
    }
}

