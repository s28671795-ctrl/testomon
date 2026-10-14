/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import org.jooq.Context;
import org.jooq.Pro;
import org.jooq.SQLDialect;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;

final class EmptyGroupingSet
extends AbstractField<Object>
implements QOM.EmptyGroupingSet {
    static final Set<SQLDialect> EMULATE_EMPTY_GROUP_BY_CONSTANT = SQLDialect.supportedUntil(SQLDialect.DERBY, SQLDialect.HSQLDB, SQLDialect.IGNITE);
    static final Set<SQLDialect> EMULATE_EMPTY_GROUP_BY_OTHER = SQLDialect.supportedUntil(SQLDialect.AURORA_MYSQL, SQLDialect.COCKROACHDB, SQLDialect.FIREBIRD, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_4, SQLDialect.REDSHIFT, SQLDialect.SQLITE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB);
    @Pro
    static final Set<SQLDialect> EMULATE_EMPTY_GROUP_BY_ANY;
    @Pro
    static final Set<SQLDialect> EMULATE_EMPTY_GROUP_BY_SUBQ;
    @Pro
    static final Set<SQLDialect> EMULATE_EMPTY_GROUP_BY_GRP_SETS;
    static final EmptyGroupingSet INSTANCE;

    private EmptyGroupingSet() {
        super(DSL.name("()"), SQLDataType.OTHER);
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (EMULATE_EMPTY_GROUP_BY_CONSTANT.contains((Object)ctx.dialect())) {
            ctx.sql('0');
        } else if (ctx.family() == SQLDialect.CUBRID) {
            ctx.sql("1 + 0");
        } else if (EMULATE_EMPTY_GROUP_BY_ANY.contains((Object)ctx.dialect())) {
            ctx.sql("empty_grouping_dummy_table.dual");
        } else if (EMULATE_EMPTY_GROUP_BY_GRP_SETS.contains((Object)ctx.dialect())) {
            ctx.visit(DSL.groupingSets(Arrays.asList(Tools.EMPTY_FIELD)));
        } else if (EMULATE_EMPTY_GROUP_BY_OTHER.contains((Object)ctx.dialect())) {
            ctx.sql('(').visit(DSL.select(DSL.one())).sql(')');
        } else {
            ctx.sql("()");
        }
    }

    static {
        EMULATE_EMPTY_GROUP_BY_SUBQ = SQLDialect.supportedBy(SQLDialect.ASE, SQLDialect.BIGQUERY, SQLDialect.INGRES, SQLDialect.SQLDATAWAREHOUSE);
        EMULATE_EMPTY_GROUP_BY_GRP_SETS = SQLDialect.supportedBy(SQLDialect.HANA, SQLDialect.SNOWFLAKE);
        EnumSet<SQLDialect> temp = EnumSet.of(SQLDialect.ACCESS, SQLDialect.INFORMIX);
        temp.addAll(EMULATE_EMPTY_GROUP_BY_SUBQ);
        EMULATE_EMPTY_GROUP_BY_ANY = SQLDialect.supportedBy(temp.toArray(new SQLDialect[0]));
        INSTANCE = new EmptyGroupingSet();
    }
}

