/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.Name;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.Traverser;
import org.jooq.User;
import org.jooq.impl.AbstractNamed;
import org.jooq.impl.CommentImpl;
import org.jooq.impl.QOM;
import org.jooq.impl.RoleImpl;

final class UserImpl
extends AbstractNamed
implements User {
    private static final Clause[] CLAUSES = new Clause[]{Clause.USER};

    UserImpl(Name name) {
        super(name, CommentImpl.NO_COMMENT);
    }

    @Override
    public final void accept(Context<?> ctx) {
        ctx.visit(this.getQualifiedName());
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return CLAUSES;
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$name());
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$name(), RoleImpl::new, replacer);
    }
}

