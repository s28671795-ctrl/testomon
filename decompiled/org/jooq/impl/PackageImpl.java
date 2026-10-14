/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Catalog;
import org.jooq.Context;
import org.jooq.Internal;
import org.jooq.Package;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.Schema;
import org.jooq.Traverser;
import org.jooq.impl.AbstractNamed;
import org.jooq.impl.CommentImpl;
import org.jooq.impl.DSL;
import org.jooq.impl.QOM;

@Internal
public class PackageImpl
extends AbstractNamed
implements Package {
    private Schema schema;

    public PackageImpl(String name, Schema schema) {
        super(PackageImpl.qualify(schema, DSL.name(name)), CommentImpl.NO_COMMENT);
        this.schema = schema;
    }

    @Override
    public final Catalog getCatalog() {
        return this.getSchema() == null ? null : this.getSchema().getCatalog();
    }

    @Override
    public final Schema getSchema() {
        if (this.schema == null) {
            this.schema = this.getQualifiedName().qualified() ? DSL.schema(this.getQualifiedName().qualifier()) : null;
        }
        return this.schema;
    }

    @Override
    public final void accept(Context<?> ctx) {
        ctx.visit(this.getUnqualifiedName());
    }

    @Override
    public final Schema $schema() {
        return this.schema;
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.schema);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.schema, s2 -> new PackageImpl(this.getName(), (Schema)s2), replacer);
    }
}

