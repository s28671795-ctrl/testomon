/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.jooq.Catalog;
import org.jooq.Clause;
import org.jooq.Comment;
import org.jooq.Context;
import org.jooq.Internal;
import org.jooq.Name;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.Schema;
import org.jooq.Traverser;
import org.jooq.impl.AbstractNamed;
import org.jooq.impl.DSL;
import org.jooq.impl.QOM;
import org.jooq.impl.SimpleQueryPart;
import org.jooq.impl.Tools;
import org.jooq.tools.StringUtils;

@Internal
public class CatalogImpl
extends AbstractNamed
implements Catalog,
SimpleQueryPart {
    private static final Clause[] CLAUSES = new Clause[]{Clause.CATALOG, Clause.CATALOG_REFERENCE};
    static final Catalog DEFAULT_CATALOG = new CatalogImpl("");

    public CatalogImpl(Name name) {
        this(name, null);
    }

    public CatalogImpl(String name) {
        this(name, null);
    }

    public CatalogImpl(Name name, Comment comment) {
        super(name, comment);
    }

    public CatalogImpl(String name, String comment) {
        super(DSL.name(name), DSL.comment(comment));
    }

    @Override
    public final void accept(Context<?> ctx) {
        Catalog mappedCatalog = Tools.getMappedCatalog(ctx, this);
        ctx.visit(mappedCatalog != null ? mappedCatalog.getUnqualifiedName() : this.getUnqualifiedName());
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return CLAUSES;
    }

    @Override
    public final Schema getSchema(String name) {
        return CatalogImpl.find(name, this.getSchemas());
    }

    @Override
    public final Schema getSchema(Name name) {
        return CatalogImpl.find(name, this.getSchemas());
    }

    @Override
    public List<Schema> getSchemas() {
        return Collections.emptyList();
    }

    @Override
    public final Stream<Schema> schemaStream() {
        return this.getSchemas().stream();
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.getQualifiedName());
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.getQualifiedName(), this.getCommentPart(), CatalogImpl::new, replacer);
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that instanceof CatalogImpl) {
            return StringUtils.equals(this.getName(), ((CatalogImpl)that).getName());
        }
        return super.equals(that);
    }
}

