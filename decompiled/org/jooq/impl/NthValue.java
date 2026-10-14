/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.SQLDialect;
import org.jooq.Traverser;
import org.jooq.WindowDefinition;
import org.jooq.WindowSpecification;
import org.jooq.impl.AbstractWindowFunction;
import org.jooq.impl.FirstValue;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;

final class NthValue<T>
extends AbstractWindowFunction<T>
implements QOM.NthValue<T> {
    final Field<T> field;
    final Field<Integer> offset;

    NthValue(Field<T> field2, Field<Integer> offset) {
        super(Names.N_NTH_VALUE, field2.getDataType().null_());
        this.field = field2;
        this.offset = offset;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                ctx.sql("nthValue(").visit(this.field).sql(", ").visit(this.offset).sql(')');
                this.acceptFromFirstOrLast(ctx);
                this.acceptNullTreatment(ctx);
                break;
            }
            default: {
                ctx.visit(Names.N_NTH_VALUE).sql('(').visit(this.field).sql(", ").visit(this.offset);
                this.acceptNullTreatmentVertica(ctx);
                ctx.sql(')');
                if (ctx.family() != SQLDialect.DB2) {
                    this.acceptFromFirstOrLast(ctx);
                }
                this.acceptNullTreatmentStandard(ctx);
                if (ctx.family() != SQLDialect.DB2) break;
                this.acceptFromFirstOrLast(ctx);
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
        return QOM.replace(this, this.field, this.$windowSpecification(), this.$windowDefinition(), (f, s2, d) -> new FirstValue(f).$windowSpecification((WindowSpecification)s2).$windowDefinition((WindowDefinition)d).$fromFirstOrLast(this.fromFirstOrLast).$nullTreatment(this.nullTreatment), replacer);
    }
}

