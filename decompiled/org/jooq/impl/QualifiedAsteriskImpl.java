/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Arrays;
import java.util.Collection;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.QualifiedAsterisk;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.Table;
import org.jooq.Traverser;
import org.jooq.impl.AbstractQueryPart;
import org.jooq.impl.AsteriskImpl;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.Tools;

final class QualifiedAsteriskImpl
extends AbstractQueryPart
implements QualifiedAsterisk {
    private final Table<?> table;
    final QueryPartList<Field<?>> fields;

    QualifiedAsteriskImpl(Table<?> table) {
        this(table, null);
    }

    QualifiedAsteriskImpl(Table<?> table, QueryPartList<Field<?>> fields) {
        this.table = table;
        this.fields = fields == null ? new QueryPartList() : fields;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                ctx.visit(this.table).sql(".asterisk()");
                if (this.fields.isEmpty()) break;
                ctx.sql(".except(").visit(this.fields).sql(')');
                break;
            }
            default: {
                ctx.visit(this.table).sql('.').visit(AsteriskImpl.INSTANCE.get());
                if (this.fields.isEmpty()) break;
                if (AsteriskImpl.NO_SUPPORT_QUALIFIED_EXCEPT.contains((Object)ctx.dialect())) {
                    ctx.sql(' ').visit(AsteriskImpl.keyword(ctx)).sql(" (").qualify(false, c -> c.visit(this.fields)).sql(')');
                    break;
                }
                ctx.sql(' ').visit(Keywords.K_EXCEPT).sql(" (").visit(this.fields).sql(')');
            }
        }
    }

    @Override
    public final Table<?> qualifier() {
        return this.table;
    }

    @Override
    public final QualifiedAsterisk except(String ... fieldNames) {
        return this.except(Tools.fieldsByName(fieldNames));
    }

    @Override
    public final QualifiedAsterisk except(Name ... fieldNames) {
        return this.except(Tools.fieldsByName(fieldNames));
    }

    @Override
    public final QualifiedAsterisk except(Field<?> ... f) {
        QueryPartList list = new QueryPartList();
        list.addAll((Collection<Field<?>>)this.fields);
        list.addAll((Collection<Field<?>>)Arrays.asList(f));
        return new QualifiedAsteriskImpl(this.table, list);
    }

    @Override
    public final Table<?> $table() {
        return this.table;
    }

    @Override
    public final QOM.UnmodifiableList<? extends Field<?>> $except() {
        return QOM.unmodifiable(this.fields);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.fields);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.table, this.fields, QualifiedAsteriskImpl::new, replacer);
    }
}

