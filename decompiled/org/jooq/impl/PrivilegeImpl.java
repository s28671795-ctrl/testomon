/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.Keyword;
import org.jooq.Privilege;
import org.jooq.impl.AbstractQueryPart;
import org.jooq.impl.QOM;
import org.jooq.impl.SimpleQueryPart;

final class PrivilegeImpl
extends AbstractQueryPart
implements Privilege,
SimpleQueryPart,
QOM.UEmpty {
    private static final Clause[] CLAUSES = new Clause[]{Clause.PRIVILEGE};
    private final Keyword privilege;

    PrivilegeImpl(Keyword privilege) {
        this.privilege = privilege;
    }

    @Override
    public final void accept(Context<?> ctx) {
        ctx.visit(this.privilege);
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return CLAUSES;
    }
}

