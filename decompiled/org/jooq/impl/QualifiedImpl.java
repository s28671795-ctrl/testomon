/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Catalog;
import org.jooq.Context;
import org.jooq.Name;
import org.jooq.Qualified;
import org.jooq.Schema;
import org.jooq.impl.AbstractNamed;
import org.jooq.impl.CommentImpl;
import org.jooq.impl.QOM;
import org.jooq.impl.SchemaImpl;
import org.jooq.impl.Tools;

final class QualifiedImpl
extends AbstractNamed
implements Qualified,
QOM.UTransient {
    QualifiedImpl(Name name) {
        super(name, CommentImpl.NO_COMMENT);
    }

    static final void acceptMappedSchemaPrefix(Context<?> ctx, Schema schema) {
        Schema mappedSchema = Tools.getMappedSchema(ctx, schema);
        if (mappedSchema != null && !"".equals(mappedSchema.getName())) {
            ctx.visit(mappedSchema).sql('.');
        }
    }

    @Override
    public final void accept(Context<?> ctx) {
        QualifiedImpl.acceptMappedSchemaPrefix(ctx, this.getSchema());
        ctx.visit(this.getUnqualifiedName());
    }

    @Override
    public final Catalog getCatalog() {
        Schema schema = this.getSchema();
        if (schema != null) {
            return schema.getCatalog();
        }
        return null;
    }

    @Override
    public final Schema getSchema() {
        if (this.getQualifiedName().qualified()) {
            return new SchemaImpl(this.getQualifiedName().qualifier());
        }
        return null;
    }

    @Override
    public final Schema $schema() {
        return this.getSchema();
    }
}

