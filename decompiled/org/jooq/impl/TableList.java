/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.QueryPart;
import org.jooq.SQLDialect;
import org.jooq.Table;
import org.jooq.impl.Dual;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.Tools;

final class TableList
extends QueryPartList<Table<?>> {
    private static final Set<SQLDialect> UNQUALIFY_FIELDS = SQLDialect.supportedBy(SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB);

    TableList() {
    }

    TableList(List<? extends Table<?>> wrappedList) {
        super(wrappedList);
    }

    @SafeVarargs
    TableList(Table<?> ... wrappedList) {
        super((QueryPart[])wrappedList);
    }

    @Override
    public final boolean rendersContent(Context<?> ctx) {
        return true;
    }

    @Override
    protected void acceptElement(Context<?> ctx, Table<?> part) {
        Tools.visitAutoAliased(ctx, part, Context::declareTables, (c, t2) -> super.acceptElement((Context<?>)c, t2));
    }

    @Override
    protected void toSQLEmptyList(Context<?> ctx) {
        ctx.visit(new Dual());
    }

    @Override
    public final boolean declaresTables() {
        return true;
    }

    final List<Field<?>> fields() {
        return Tools.flatMap(this, t2 -> Arrays.asList(t2.fieldsRow().fields()));
    }

    final void toSQLFields(Context<?> ctx) {
        ctx.qualify(!UNQUALIFY_FIELDS.contains((Object)ctx.dialect()) && ctx.qualify(), c -> {
            String sep = "";
            for (Table table : this) {
                for (Field<?> field2 : table.fieldsRow().fields()) {
                    ctx.sql(sep);
                    ctx.visit(field2);
                    sep = ", ";
                }
            }
        });
    }
}

