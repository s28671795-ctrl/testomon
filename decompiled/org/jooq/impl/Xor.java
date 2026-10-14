/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.Clause;
import org.jooq.Condition;
import org.jooq.Context;
import org.jooq.Function2;
import org.jooq.Operator;
import org.jooq.Pro;
import org.jooq.SQLDialect;
import org.jooq.impl.AbstractCondition;
import org.jooq.impl.Expression;
import org.jooq.impl.QOM;
import org.jooq.tools.StringUtils;

final class Xor
extends AbstractCondition
implements QOM.Xor {
    final Condition arg1;
    final Condition arg2;
    private static final Clause[] CLAUSES = new Clause[]{Clause.CONDITION, Clause.CONDITION_XOR};
    private static final Set<SQLDialect> NO_SUPPORT_NATIVE = SQLDialect.supportedBy(SQLDialect.ASE, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.MEMSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.YUGABYTEDB);

    Xor(Condition arg1, Condition arg2) {
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    @Override
    final boolean parenthesised(Context<?> ctx) {
        return !NO_SUPPORT_NATIVE.contains((Object)ctx.dialect());
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (ctx.family() == SQLDialect.JAVA) {
            this.acceptJava(ctx);
            return;
        }
        if (NO_SUPPORT_NATIVE.contains((Object)ctx.dialect())) {
            ctx.visit(this.arg1.ne(this.arg2));
        } else {
            ctx.sqlIndentStart('(');
            Expression.acceptAssociative(ctx, this, q -> new Expression.Expr<Condition>(q.arg1, Operator.XOR.toKeyword(), q.arg2), Context::formatSeparator);
            ctx.sqlIndentEnd(')');
        }
    }

    @Override
    final boolean isNullable() {
        return ((AbstractCondition)this.arg1).isNullable() || ((AbstractCondition)this.arg2).isNullable();
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return CLAUSES;
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.visit(this.arg1).sql(".xor(").visit(this.arg2).sql(')');
    }

    @Override
    public final Condition $arg1() {
        return this.arg1;
    }

    @Override
    public final Condition $arg2() {
        return this.arg2;
    }

    @Override
    public final QOM.Xor $arg1(Condition newValue) {
        return new Xor(newValue, this.$arg2());
    }

    @Override
    public final QOM.Xor $arg2(Condition newValue) {
        return new Xor(this.$arg1(), newValue);
    }

    @Override
    public final Function2<? super Condition, ? super Condition, ? extends QOM.Xor> $constructor() {
        return (a1, a2) -> new Xor((Condition)a1, (Condition)a2);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Xor) {
            QOM.Xor o = (QOM.Xor)that;
            return StringUtils.equals(this.$arg1(), o.$arg1()) && StringUtils.equals(this.$arg2(), o.$arg2());
        }
        return super.equals(that);
    }
}

