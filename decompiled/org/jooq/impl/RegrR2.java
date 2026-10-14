/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.math.BigDecimal;
import java.util.Set;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function2;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.SQLDialect;
import org.jooq.Traverser;
import org.jooq.impl.AbstractAggregateFunction;
import org.jooq.impl.DSL;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.tools.StringUtils;

final class RegrR2
extends AbstractAggregateFunction<BigDecimal>
implements QOM.RegrR2 {
    private static final Set<SQLDialect> NO_SUPPORT_NATIVE = SQLDialect.supportedUntil(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB_20, SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD_4_0, SQLDialect.H2_1_4_200, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.REDSHIFT, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER);
    @Pro
    private static final Set<SQLDialect> NO_SUPPORT_NATIVE_WINDOW = SQLDialect.supportedBy(SQLDialect.VERTICA);

    RegrR2(Field<? extends Number> y, Field<? extends Number> x) {
        super(false, Names.N_REGR_R2, SQLDataType.NUMERIC, Tools.nullSafeNotNull(y, SQLDataType.INTEGER), Tools.nullSafeNotNull(x, SQLDataType.INTEGER));
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (NO_SUPPORT_NATIVE.contains((Object)ctx.dialect())) {
            this.acceptEmulation(ctx);
        } else if (NO_SUPPORT_NATIVE_WINDOW.contains((Object)ctx.dialect()) && this.isWindow()) {
            this.acceptEmulation(ctx);
        } else {
            super.accept(ctx);
        }
    }

    private final void acceptEmulation(Context<?> ctx) {
        Field x = (Field)this.getArguments().get(0);
        Field y = (Field)this.getArguments().get(1);
        ctx.visit(DSL.when(this.fo(DSL.varPop(this.y(x, y))).eq(DSL.inline(BigDecimal.ZERO)), DSL.inline(null, this.d(ctx))).when(this.fo(DSL.varPop(this.x(x, y))).eq(DSL.inline(BigDecimal.ZERO)), (Field<Number>)DSL.inline(BigDecimal.ONE)).else_(DSL.square(this.fo(DSL.corr(x, y)))));
    }

    @Override
    public final Field<? extends Number> $y() {
        return (Field)this.getArguments().get(0);
    }

    @Override
    public final Field<? extends Number> $x() {
        return (Field)this.getArguments().get(1);
    }

    @Override
    public final QOM.RegrR2 $y(Field<? extends Number> newValue) {
        return this.$constructor().apply((Field<? extends Number>)newValue, (Field<? extends Number>)this.$x());
    }

    @Override
    public final QOM.RegrR2 $x(Field<? extends Number> newValue) {
        return this.$constructor().apply((Field<? extends Number>)this.$y(), (Field<? extends Number>)newValue);
    }

    public final Function2<? super Field<? extends Number>, ? super Field<? extends Number>, ? extends QOM.RegrR2> $constructor() {
        return (a1, a2) -> new RegrR2((Field<? extends Number>)a1, (Field<? extends Number>)a2);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$y(), this.$x(), (a1, a2) -> this.$constructor().apply((Field<? extends Number>)a1, (Field<? extends Number>)a2), replacer);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        QOM.traverse(traverser, this, this.$y(), this.$x());
        return super.$traverse(traverser);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.RegrR2) {
            QOM.RegrR2 o = (QOM.RegrR2)that;
            return StringUtils.equals(this.$y(), o.$y()) && StringUtils.equals(this.$x(), o.$x());
        }
        return super.equals(that);
    }
}

