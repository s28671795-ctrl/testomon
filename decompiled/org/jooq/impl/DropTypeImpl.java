/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Collection;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.DropTypeFinalStep;
import org.jooq.DropTypeStep;
import org.jooq.Name;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.Traverser;
import org.jooq.impl.AbstractDDLQuery;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.Tools;

final class DropTypeImpl
extends AbstractDDLQuery
implements DropTypeStep,
QOM.DropType {
    private final QueryPartList<Name> type;
    private final boolean ifExists;
    private QOM.Cascade cascade;

    DropTypeImpl(Configuration configuration, Collection<?> type, boolean ifExists) {
        super(configuration);
        this.type = new QueryPartList<Name>((Iterable<Name>)Tools.names(type));
        this.ifExists = ifExists;
    }

    @Override
    public final DropTypeFinalStep cascade() {
        this.cascade = QOM.Cascade.CASCADE;
        return this;
    }

    @Override
    public final DropTypeFinalStep restrict() {
        this.cascade = QOM.Cascade.RESTRICT;
        return this;
    }

    @Override
    public final void accept(Context<?> ctx) {
        ctx.visit(Keywords.K_DROP).sql(' ').visit(Keywords.K_TYPE);
        if (this.ifExists) {
            ctx.sql(' ').visit(Keywords.K_IF_EXISTS);
        }
        ctx.sql(' ').visit(this.type);
        if (this.cascade == QOM.Cascade.CASCADE) {
            ctx.sql(' ').visit(Keywords.K_CASCADE);
        } else if (this.cascade == QOM.Cascade.RESTRICT) {
            ctx.sql(' ').visit(Keywords.K_RESTRICT);
        }
    }

    @Override
    public final QOM.UnmodifiableList<? extends Name> $names() {
        return QOM.unmodifiable(this.type);
    }

    @Override
    public final boolean $ifExists() {
        return this.ifExists;
    }

    @Override
    public final QOM.Cascade $cascade() {
        return this.cascade;
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.type);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.type, t2 -> {
            DropTypeImpl r = new DropTypeImpl(this.configuration(), (Collection<?>)t2, this.ifExists);
            r.cascade = this.cascade;
            return r;
        }, replacer);
    }
}

