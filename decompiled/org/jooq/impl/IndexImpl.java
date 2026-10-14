/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.jooq.Condition;
import org.jooq.Context;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.OrderField;
import org.jooq.SQLDialect;
import org.jooq.SortField;
import org.jooq.Table;
import org.jooq.impl.AbstractNamed;
import org.jooq.impl.CommentImpl;
import org.jooq.impl.DSL;
import org.jooq.impl.QOM;
import org.jooq.impl.QualifiedImpl;
import org.jooq.impl.Tools;

class IndexImpl
extends AbstractNamed
implements Index,
QOM.UNotYetImplemented {
    private static final Set<SQLDialect> NO_SUPPORT_INDEX_QUALIFICATION = SQLDialect.supportedBy(SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB);
    private final Table<?> table;
    private final SortField<?>[] fields;
    private final Condition where;
    private final boolean unique;

    IndexImpl(Name name) {
        this(name, null, Tools.EMPTY_SORTFIELD, null, false);
    }

    IndexImpl(Name name, Table<?> table, OrderField<?>[] fields, Condition where, boolean unique) {
        super(name.empty() ? name : IndexImpl.qualify(table, name), CommentImpl.NO_COMMENT);
        this.table = table;
        this.fields = Tools.sortFields(fields);
        this.where = where;
        this.unique = unique;
    }

    final SortField<?>[] $fields() {
        return this.fields;
    }

    final boolean $unique() {
        return this.unique;
    }

    final Condition $where() {
        return this.where;
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (NO_SUPPORT_INDEX_QUALIFICATION.contains((Object)ctx.dialect())) {
            ctx.visit(this.getUnqualifiedName());
        } else if (this.getTable() == null) {
            ctx.visit(new QualifiedImpl(this.getQualifiedName()));
        } else {
            ctx.visit(new QualifiedImpl(DSL.name(this.getTable().getQualifiedName().qualifier(), this.getUnqualifiedName())));
        }
    }

    @Override
    public final Table<?> getTable() {
        return this.table;
    }

    @Override
    public final List<SortField<?>> getFields() {
        return Arrays.asList(this.fields);
    }

    @Override
    public final Condition getWhere() {
        return this.where;
    }

    @Override
    public boolean getUnique() {
        return this.unique;
    }

    @Override
    public final Table<?> $table() {
        return this.table;
    }
}

