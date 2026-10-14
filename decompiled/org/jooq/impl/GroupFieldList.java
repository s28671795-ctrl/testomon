/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.GroupField;
import org.jooq.QueryPart;
import org.jooq.SQLDialect;
import org.jooq.Table;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.NoField;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.QueryPartListView;

final class GroupFieldList
extends QueryPartList<GroupField> {
    static final Set<SQLDialect> NO_SUPPORT_GROUP_BY_TABLE = SQLDialect.supportedBy(SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB);
    static final Set<SQLDialect> NO_SUPPORT_GROUP_FUNCTIONAL_DEP = SQLDialect.supportedBy(SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.ORACLE, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER);

    GroupFieldList() {
    }

    GroupFieldList(Iterable<? extends GroupField> wrappedList) {
        super(wrappedList);
    }

    GroupFieldList(GroupField[] wrappedList) {
        super((QueryPart[])wrappedList);
    }

    @Override
    final boolean canAdd(GroupField e) {
        return super.canAdd(e) && !(e instanceof NoField);
    }

    @Override
    public final boolean rendersContent(Context<?> ctx) {
        return true;
    }

    @Override
    protected final void toSQLEmptyList(Context<?> ctx) {
        ctx.visit(DSL.emptyGroupingSet());
    }

    @Override
    protected final void acceptElement(Context<?> ctx, GroupField part) {
        if (part instanceof Table) {
            Table t2 = (Table)part;
            if (NO_SUPPORT_GROUP_BY_TABLE.contains((Object)ctx.dialect())) {
                QueryPart[] f = this.fields(ctx, t2);
                if (f.length > 1) {
                    ctx.visit(QueryPartListView.wrap((QueryPart[])f));
                } else if (f.length == 1) {
                    ctx.visit((Field<?>)f[0]);
                } else {
                    super.acceptElement(ctx, part);
                }
            } else {
                super.acceptElement(ctx, part);
            }
        } else {
            super.acceptElement(ctx, part);
        }
    }

    private final Field<?>[] fields(Context<?> ctx, Table<?> t2) {
        UniqueKey<?> pk = t2.getPrimaryKey();
        if (pk == null || NO_SUPPORT_GROUP_FUNCTIONAL_DEP.contains((Object)ctx.dialect())) {
            return t2.fields();
        }
        return t2.fields(pk.getFieldsArray());
    }
}

