/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.Traverser;
import org.jooq.WindowDefinition;
import org.jooq.WindowSpecification;
import org.jooq.impl.AbstractWindowFunction;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;

final class FirstValue<T>
extends AbstractWindowFunction<T>
implements QOM.FirstValue<T> {
    final Field<T> field;

    FirstValue(Field<T> field2) {
        super(Names.N_FIRST_VALUE, field2.getDataType().null_());
        this.field = field2;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                ctx.sql("firstValue(").visit(this.field).sql(')');
                this.acceptNullTreatment(ctx);
                break;
            }
            default: {
                ctx.visit(Names.N_FIRST_VALUE).sql('(').visit(this.field);
                this.acceptNullTreatmentDB2(ctx);
                this.acceptNullTreatmentVertica(ctx);
                ctx.sql(')');
                this.acceptNullTreatment(ctx);
            }
        }
        this.acceptOverClause(ctx);
    }

    @Override
    public final Field<T> $field() {
        return this.field;
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.field, this.$windowSpecification() != null ? this.$windowSpecification() : this.$windowDefinition());
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.field, this.$windowSpecification(), this.$windowDefinition(), (f, s2, d) -> new FirstValue<T>(f).$windowSpecification((WindowSpecification)s2).$windowDefinition((WindowDefinition)d).$nullTreatment(this.nullTreatment), replacer);
    }
}

