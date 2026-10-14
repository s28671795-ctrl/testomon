/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Name;
import org.jooq.QueryPart;
import org.jooq.Schema;
import org.jooq.Traverser;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;

abstract class AbstractFunction<T>
extends AbstractField<T>
implements QOM.Function<T> {
    private final boolean applySchemaMapping;

    AbstractFunction(Name name, DataType<T> type, boolean applySchemaMapping) {
        super(name, type);
        this.applySchemaMapping = applySchemaMapping;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                ctx.sql(Tools.camelCase(this.getName())).sql('(').visit(this.arguments()).sql(')');
                break;
            }
            default: {
                AbstractFunction.acceptFunctionName(ctx, this.applySchemaMapping, this.getQualifiedName());
                ctx.sql('(').visit(this.arguments()).sql(')');
            }
        }
    }

    static void acceptFunctionName(Context<?> ctx, boolean applySchemaMapping, Name name) {
        if (applySchemaMapping && name.qualified()) {
            Schema mapped = Tools.getMappedSchema(ctx, DSL.schema(name.qualifier()));
            if (mapped != null) {
                ctx.visit(mapped.getQualifiedName().append(name.unqualifiedName()));
            } else {
                ctx.visit(name.unqualifiedName());
            }
        } else {
            ctx.visit(name);
        }
    }

    abstract QueryPart arguments();

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.arguments());
    }
}

