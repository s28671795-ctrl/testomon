/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.ArrayRecord;
import org.jooq.BindContext;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Pro;
import org.jooq.RenderContext;
import org.jooq.Row;
import org.jooq.RowCountQuery;
import org.jooq.SQLDialect;
import org.jooq.UDTRecord;
import org.jooq.impl.AbstractParam;
import org.jooq.impl.DSL;
import org.jooq.impl.QOM;
import org.jooq.impl.QualifiedImpl;
import org.jooq.impl.Tools;

@Pro
final class ArrayConstant<R extends ArrayRecord<?>>
extends AbstractParam<R>
implements QOM.UNotYetImplemented {
    private final R array;
    private final DataType<?> baseType;

    ArrayConstant(R array) {
        super(array, array.getArrayType());
        this.array = array;
        this.baseType = array.getDataType();
    }

    final DataType<?> getBaseType() {
        return this.baseType;
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
            if (ctx.family() == SQLDialect.SQLSERVER) {
                String alias = ctx.nextAlias();
                RowCountQuery declare = ctx.dsl().query("{declare} @" + alias + " " + this.array.getName());
                if (!this.array.isEmpty()) {
                    Tools.prependSQL((RenderContext)ctx.skipUpdateCount(), declare, ctx.dsl().insertInto(DSL.table("@" + alias)).select(DSL.selectFrom(DSL.values0(Tools.map(this.array, e -> ((UDTRecord)e).valuesRow(), Row[]::new)))));
                } else {
                    Tools.prependSQL(ctx, declare);
                }
                ctx.sql("@" + alias);
            } else {
                QualifiedImpl.acceptMappedSchemaPrefix(ctx, this.array.getSchema());
                if (this.array.getPackage() != null) {
                    ((RenderContext)ctx.visit(this.array.getPackage().getUnqualifiedName())).sql('.');
                }
                ctx.literal(this.array.getName());
                ctx.sql('(');
                String separator = "";
                for (Object object : this.array) {
                    ctx.sql(separator);
                    ctx.visit(DSL.inline(object, this.baseType));
                    separator = ", ";
                }
                ctx.sql(')');
            }
        } else {
            ctx.sql('?');
        }
    }

    final void bind0(BindContext context) {
        context.bindValue(this.array, this);
    }
}

