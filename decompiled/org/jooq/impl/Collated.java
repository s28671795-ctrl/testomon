/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Binding;
import org.jooq.Collation;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.Traverser;
import org.jooq.impl.AbstractField;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;

final class Collated
extends AbstractField<String>
implements QOM.Collated {
    private final Field<?> field;
    private final Collation collation;

    Collated(Field<?> field2, Collation collation) {
        super(field2.getQualifiedName(), Collated.type(field2).collation(collation), field2.getCommentPart(), Collated.binding(field2));
        this.field = field2;
        this.collation = collation;
    }

    private static final Binding<?, String> binding(Field<?> field2) {
        return field2.getType() == String.class ? field2.getBinding() : SQLDataType.VARCHAR.getBinding();
    }

    private static final DataType<String> type(Field<?> field2) {
        return field2.getType() == String.class ? field2.getDataType() : SQLDataType.VARCHAR;
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (ctx.configuration().data("org.jooq.ddl.ignore-storage-clauses") == null) {
            ctx.sql("((").visit(this.field).sql(") ").visit(Keywords.K_COLLATE).sql(' ').visit(this.collation).sql(')');
        } else {
            ctx.visit(this.field);
        }
    }

    @Override
    public final Field<?> $field() {
        return this.field;
    }

    @Override
    public final Collation $collation() {
        return this.collation;
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.field, this.collation);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.field, this.collation, Collated::new, replacer);
    }
}

