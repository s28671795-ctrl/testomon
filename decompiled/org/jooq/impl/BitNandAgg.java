/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function1;
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

final class BitNandAgg<T extends Number>
extends AbstractAggregateFunction<T>
implements QOM.BitNandAgg<T> {
    public static final Set<SQLDialect> NO_SUPPORT_NATIVE = SQLDialect.supportedUntil(SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2_1_4_200, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB);

    BitNandAgg(Field<T> value) {
        super(false, Names.N_BIT_NAND_AGG, Tools.nullSafeDataType(value), Tools.nullSafeNotNull(value, SQLDataType.INTEGER));
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (NO_SUPPORT_NATIVE.contains((Object)ctx.dialect())) {
            ctx.visit(DSL.bitNot(this.fo(DSL.bitAndAgg((Field)this.getArguments().get(0)))));
        } else {
            super.accept(ctx);
        }
    }

    @Override
    public final Field<T> $value() {
        return (Field)this.getArguments().get(0);
    }

    @Override
    public final QOM.BitNandAgg<T> $value(Field<T> newValue) {
        return this.$constructor().apply(newValue);
    }

    public final Function1<? super Field<T>, ? extends QOM.BitNandAgg<T>> $constructor() {
        return a1 -> new BitNandAgg(a1);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$value(), a1 -> this.$constructor().apply((Field<T>)a1), replacer);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        QOM.traverse(traverser, this, this.$value());
        return super.$traverse(traverser);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.BitNandAgg) {
            QOM.BitNandAgg o = (QOM.BitNandAgg)that;
            return StringUtils.equals(this.$value(), o.$value());
        }
        return super.equals(that);
    }
}

