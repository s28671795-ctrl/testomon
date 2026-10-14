/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Map;
import org.jooq.AssociativeArrayRecord;
import org.jooq.BindContext;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Pro;
import org.jooq.RenderContext;
import org.jooq.impl.AbstractParam;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.QualifiedImpl;
import org.jooq.impl.Tools;

@Pro
final class AssociativeArrayConstant<R extends AssociativeArrayRecord<?, ?>>
extends AbstractParam<R>
implements QOM.UNotYetImplemented {
    private final R array;
    private final R qualifier;

    AssociativeArrayConstant(R array, DataType<R> type) {
        super(array, type);
        this.qualifier = array != null ? array : Tools.newAssociativeArrayRecord(type.getType());
        this.array = array;
    }

    final DataType<?> getBaseType() {
        return this.qualifier.getDataType();
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (ctx instanceof RenderContext) {
            this.toSQL0((RenderContext)ctx);
        } else {
            this.bind0((BindContext)ctx);
        }
    }

    final void toSQL0(RenderContext ctx) {
        if (this.isInline(ctx)) {
            if (this.array == null) {
                ((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)ctx.visit(Keywords.K_CAST)).sql('(')).visit(Keywords.K_NULL)).sql(' ')).visit(Keywords.K_AS)).sql(' ');
                this.acceptName(ctx);
                ctx.sql(')');
            } else {
                this.acceptName(ctx);
                ctx.sql('(');
                String separator = "";
                for (Map.Entry entry : this.array.entrySet()) {
                    ctx.sql(separator);
                    ctx.visit(DSL.inline(entry.getKey(), this.qualifier.getIndexDataType()));
                    ctx.sql(" => ");
                    ctx.visit(DSL.inline(entry.getValue(), this.qualifier.getDataType()));
                    separator = ", ";
                }
                ctx.sql(')');
            }
        } else {
            ctx.sql('?');
        }
    }

    private final void acceptName(RenderContext ctx) {
        QualifiedImpl.acceptMappedSchemaPrefix(ctx, this.qualifier.getSchema());
        if (this.qualifier.getPackage() != null) {
            ((RenderContext)ctx.visit(this.qualifier.getPackage().getUnqualifiedName())).sql('.');
        }
        ctx.literal(this.qualifier.getName());
    }

    final void bind0(BindContext context) {
        context.bindValue(this.array, this);
    }
}

