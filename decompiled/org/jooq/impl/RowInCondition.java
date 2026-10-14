/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.Clause;
import org.jooq.Comparator;
import org.jooq.Condition;
import org.jooq.Context;
import org.jooq.Row;
import org.jooq.SQLDialect;
import org.jooq.impl.AbstractCondition;
import org.jooq.impl.AbstractInList;
import org.jooq.impl.AbstractRow;
import org.jooq.impl.DSL;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.QueryPartListView;
import org.jooq.impl.Tools;

final class RowInCondition
extends AbstractCondition
implements QOM.UNotYetImplemented {
    private static final Clause[] CLAUSES_IN = new Clause[]{Clause.CONDITION, Clause.CONDITION_IN};
    private static final Clause[] CLAUSES_IN_NOT = new Clause[]{Clause.CONDITION, Clause.CONDITION_NOT_IN};
    private static final Set<SQLDialect> EMULATE_IN = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.INGRES, SQLDialect.MEMSQL, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA);
    private final Row left;
    private final QueryPartList<? extends Row> right;
    private final boolean not;

    RowInCondition(Row left, QueryPartList<? extends Row> right, boolean not) {
        this.left = left;
        this.right = right;
        this.not = not;
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (EMULATE_IN.contains((Object)ctx.dialect())) {
            Condition result = DSL.or(Tools.map(this.right, r -> AbstractRow.compare(this.left, Comparator.EQUALS, r)));
            if (this.not) {
                result = result.not();
            }
            ctx.visit(result);
        } else if (ctx.family() == SQLDialect.JAVA) {
            ctx.visit(this.left).sql(this.not ? ".notIn(" : ".in(");
            if (this.right.isEmpty()) {
                ctx.sql("new Row" + (String)(this.left.size() >= 22 ? "N" : "" + this.left.size()) + "[0]");
            } else {
                ctx.visit(this.right);
            }
            ctx.sql(')');
        } else if (this.right.size() == 0) {
            if (this.not) {
                ctx.visit(DSL.trueCondition());
            } else {
                ctx.visit(DSL.falseCondition());
            }
        } else {
            ctx.visit(this.left).sql(' ').visit((this.not ? Comparator.NOT_IN : Comparator.IN).toKeyword()).sql(" (").visit(new QueryPartListView<Row>(AbstractInList.padded(ctx, this.right))).sql(')');
        }
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return null;
    }
}

