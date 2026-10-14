/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Comment;
import org.jooq.Context;
import org.jooq.impl.AbstractQueryPart;
import org.jooq.impl.DSL;
import org.jooq.impl.QOM;

final class CommentImpl
extends AbstractQueryPart
implements Comment,
QOM.UEmpty {
    static final CommentImpl NO_COMMENT = new CommentImpl("");
    private final String comment;

    CommentImpl(String comment) {
        this.comment = comment == null ? "" : comment;
    }

    @Override
    public final void accept(Context<?> ctx) {
        ctx.visit(DSL.inline(this.comment));
    }

    @Override
    public final String getComment() {
        return this.comment;
    }

    @Override
    public final String $comment() {
        return this.comment;
    }
}

