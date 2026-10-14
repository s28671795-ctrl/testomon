/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.CharacterSet;
import org.jooq.Context;
import org.jooq.Name;
import org.jooq.impl.AbstractQueryPart;
import org.jooq.impl.QOM;

final class CharacterSetImpl
extends AbstractQueryPart
implements CharacterSet,
QOM.UNotYetImplemented {
    private final Name name;

    CharacterSetImpl(Name name) {
        this.name = name;
    }

    @Override
    public final void accept(Context<?> ctx) {
        ctx.visit(this.name);
    }

    @Override
    public final String getName() {
        return this.name.last();
    }
}

