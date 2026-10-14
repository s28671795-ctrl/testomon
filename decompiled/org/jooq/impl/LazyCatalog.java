/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.List;
import java.util.stream.Stream;
import org.jooq.Catalog;
import org.jooq.Context;
import org.jooq.Internal;
import org.jooq.Name;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.Schema;
import org.jooq.Traverser;
import org.jooq.impl.AbstractNamed;
import org.jooq.impl.CommentImpl;
import org.jooq.impl.LazySupplier;

@Internal
public final class LazyCatalog
extends AbstractNamed
implements Catalog {
    final LazySupplier<Catalog> supplier;
    transient Catalog catalog;

    public LazyCatalog(Name name, LazySupplier<Catalog> supplier) {
        super(name, CommentImpl.NO_COMMENT);
        this.supplier = supplier;
    }

    private final Catalog catalog() {
        if (this.catalog == null) {
            try {
                this.catalog = (Catalog)this.supplier.get();
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return this.catalog;
    }

    @Override
    public final void accept(Context<?> ctx) {
        ctx.visit(this.catalog());
    }

    @Override
    public final List<Schema> getSchemas() {
        return this.catalog().getSchemas();
    }

    @Override
    public final Schema getSchema(String name) {
        return this.catalog().getSchema(name);
    }

    @Override
    public final Schema getSchema(Name name) {
        return this.catalog().getSchema(name);
    }

    @Override
    public final Stream<Schema> schemaStream() {
        return this.catalog().schemaStream();
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return this.catalog().$traverse(traverser);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return this.catalog().$replace(replacer);
    }
}

