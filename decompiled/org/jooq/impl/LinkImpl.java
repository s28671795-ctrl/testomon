/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Link;
import org.jooq.Name;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.Schema;
import org.jooq.Traverser;
import org.jooq.impl.AbstractQueryPart;
import org.jooq.impl.DSL;
import org.jooq.impl.QOM;

@Pro
final class LinkImpl
extends AbstractQueryPart
implements Link {
    private final Schema schema;
    private final String name;

    LinkImpl(String name, Schema schema) {
        this.name = name;
        this.schema = schema;
    }

    LinkImpl(Name name) {
        throw new RuntimeException();
    }

    @Override
    public final void accept(Context<?> ctx) {
        ctx.visit(DSL.name(this.name()));
    }

    @Override
    public final Schema schema() {
        return this.schema;
    }

    @Override
    public final String name() {
        return this.name;
    }

    @Override
    public final Schema $schema() {
        return this.schema;
    }

    @Override
    public final Name $name() {
        return DSL.name(this.name);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.schema);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.name, this.schema, LinkImpl::new, replacer);
    }
}

