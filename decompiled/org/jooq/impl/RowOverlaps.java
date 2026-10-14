/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Function2;
import org.jooq.Row;
import org.jooq.Row2;
import org.jooq.SQLDialect;
import org.jooq.impl.AbstractCondition;
import org.jooq.impl.AbstractRow;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;

final class RowOverlaps<T1, T2>
extends AbstractCondition
implements QOM.RowOverlaps {
    private static final Set<SQLDialect> EMULATE_NON_STANDARD_OVERLAPS = SQLDialect.supportedUntil(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2_11, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE);
    private static final Set<SQLDialect> EMULATE_INTERVAL_OVERLAPS = SQLDialect.supportedBy(SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.HSQLDB, SQLDialect.INFORMIX);
    private final Row2<T1, T2> left;
    private final Row2<T1, T2> right;

    RowOverlaps(Row2<T1, T2> left, Row2<T1, T2> right) {
        this.left = (Row2)((Object)((AbstractRow)((Object)left)).convertTo(right));
        this.right = (Row2)((Object)((AbstractRow)((Object)right)).convertTo(left));
    }

    @Override
    public final void accept(Context<?> ctx) {
        boolean intervalOverlaps;
        Field<T1> left1 = this.left.field1();
        Field<T2> left2 = this.left.field2();
        Field<T1> right1 = this.right.field1();
        Field<T2> right2 = this.right.field2();
        DataType type0 = left1.getDataType();
        DataType type1 = left2.getDataType();
        boolean standardOverlaps = type0.isDateTime() && type1.isTemporal();
        boolean bl = intervalOverlaps = type0.isDateTime() && (type1.isInterval() || type1.isNumeric());
        if (!standardOverlaps || EMULATE_NON_STANDARD_OVERLAPS.contains((Object)ctx.dialect())) {
            if (intervalOverlaps) {
                ctx.visit(right1.le(left1.plus(left2)).and(left1.le(right1.plus(right2))));
            } else {
                ctx.visit(right1.le(Tools.castIfNeeded(left2, right1)).and(left1.le(Tools.castIfNeeded(right2, left1))));
            }
        } else if (intervalOverlaps && EMULATE_INTERVAL_OVERLAPS.contains((Object)ctx.dialect())) {
            ctx.visit(right1.le(left1.plus(left2)).and(left1.le(right1.plus(right2))));
        } else if (ctx.family() == SQLDialect.JAVA) {
            ctx.visit(this.left).sql(".overlaps(").visit(this.right).sql(')');
        } else {
            ctx.sql('(').visit(this.left).sql(' ').visit(Keywords.K_OVERLAPS).sql(' ').visit(this.right).sql(')');
        }
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return null;
    }

    @Override
    public final Row $arg1() {
        return this.left;
    }

    @Override
    public final Row $arg2() {
        return this.right;
    }

    @Override
    public final Function2<? super Row, ? super Row, ? extends QOM.RowOverlaps> $constructor() {
        return (r1, r2) -> new RowOverlaps((Row2)r1, (Row2)r2);
    }
}

