/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Param;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.SQLDialect;
import org.jooq.SortField;
import org.jooq.SortOrder;
import org.jooq.Traverser;
import org.jooq.impl.AbstractQueryPart;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.SimpleCheckQueryPart;
import org.jooq.impl.Tools;

final class SortFieldImpl<T>
extends AbstractQueryPart
implements SortField<T>,
SimpleCheckQueryPart {
    private static final Set<SQLDialect> NO_SUPPORT_NULLS = SQLDialect.supportedUntil(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.REDSHIFT, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE_3_28, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.VERTICA);
    final Field<T> field;
    final SortOrder order;
    QOM.NullOrdering nullOrdering;

    SortFieldImpl(Field<T> field2, SortOrder order) {
        this(field2, order, null);
    }

    SortFieldImpl(Field<T> field2, SortOrder order, QOM.NullOrdering nullOrdering) {
        this.field = field2;
        this.order = order;
        this.nullOrdering = nullOrdering;
    }

    @Override
    public boolean isSimple(Context<?> ctx) {
        return this.nullOrdering == null && Tools.isSimple(ctx, this.field);
    }

    @Override
    public final String getName() {
        return this.field.getName();
    }

    @Override
    public final SortOrder getOrder() {
        return this.order;
    }

    @Override
    public final SortField<T> nullsFirst() {
        this.nullOrdering = QOM.NullOrdering.NULLS_FIRST;
        return this;
    }

    @Override
    public final SortField<T> nullsLast() {
        this.nullOrdering = QOM.NullOrdering.NULLS_LAST;
        return this;
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (ctx.family() == SQLDialect.JAVA) {
            this.acceptJava(ctx);
            return;
        }
        if (this.nullOrdering != null) {
            if (NO_SUPPORT_NULLS.contains((Object)ctx.dialect())) {
                Param<Integer> ifNull = this.nullOrdering == QOM.NullOrdering.NULLS_FIRST ? DSL.zero() : DSL.one();
                Param<Integer> ifNotNull = this.nullOrdering == QOM.NullOrdering.NULLS_FIRST ? DSL.one() : DSL.zero();
                ctx.visit(DSL.nvl2(this.field, ifNotNull, ifNull)).sql(", ");
                this.acceptFieldAndOrder(ctx, false);
            } else {
                this.acceptFieldAndOrder(ctx, true);
            }
        } else {
            this.acceptFieldAndOrder(ctx, false);
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.visit(this.field).sql('.');
        switch (this.order) {
            case DESC: {
                ctx.sql("desc()");
                break;
            }
            default: {
                ctx.sql("asc()");
            }
        }
        if (this.nullOrdering == QOM.NullOrdering.NULLS_FIRST) {
            ctx.sql(".nullsFirst()");
        } else if (this.nullOrdering == QOM.NullOrdering.NULLS_LAST) {
            ctx.sql(".nullsLast()");
        }
    }

    private final void acceptFieldAndOrder(Context<?> ctx, boolean includeNulls) {
        String separator = "";
        for (Field<T> f : Tools.flatten(this.field)) {
            ctx.sql(separator).visit(f);
            if (this.order != SortOrder.DEFAULT) {
                ctx.sql(' ').visit(this.order.toKeyword());
            }
            if (includeNulls) {
                if (this.nullOrdering == QOM.NullOrdering.NULLS_FIRST) {
                    ctx.sql(' ').visit(Keywords.K_NULLS_FIRST);
                } else {
                    ctx.sql(' ').visit(Keywords.K_NULLS_LAST);
                }
            }
            separator = ", ";
        }
    }

    @Override
    public final Field<T> $field() {
        return this.field;
    }

    @Override
    public final <U> SortField<U> $field(Field<U> newField) {
        if (newField == this.field) {
            return this;
        }
        return new SortFieldImpl<U>(newField, this.order, this.nullOrdering);
    }

    @Override
    public final SortOrder $sortOrder() {
        return this.order;
    }

    @Override
    public final SortField<T> $sortOrder(SortOrder newOrder) {
        if (newOrder == this.order) {
            return this;
        }
        return new SortFieldImpl<T>(this.field, newOrder, this.nullOrdering);
    }

    @Override
    public final QOM.NullOrdering $nullOrdering() {
        return this.nullOrdering;
    }

    @Override
    public final SortField<T> $nullOrdering(QOM.NullOrdering newOrdering) {
        if (newOrdering == this.nullOrdering) {
            return this;
        }
        return new SortFieldImpl<T>(this.field, this.order, newOrdering);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.field);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.field, this.order, this.nullOrdering, SortFieldImpl::new, replacer);
    }
}

