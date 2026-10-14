/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Name;
import org.jooq.QueryPart;
import org.jooq.QueryPartInternal;
import org.jooq.Replacer;
import org.jooq.Traverser;
import org.jooq.impl.AbstractName;
import org.jooq.impl.LazySupplier;

final class LazyName
extends AbstractName {
    final LazySupplier<Name> supplier;
    transient Name name;

    LazyName(LazySupplier<Name> supplier) {
        this.supplier = supplier;
    }

    private final Name name() {
        if (this.name == null) {
            this.name = (Name)this.supplier.get();
        }
        return this.name;
    }

    @Override
    public final void accept(Context<?> ctx) {
        ((QueryPartInternal)((Object)this.name())).accept(ctx);
    }

    @Override
    public final String first() {
        return this.name().first();
    }

    @Override
    public final String last() {
        return this.name().last();
    }

    @Override
    public final boolean empty() {
        return this.name().empty();
    }

    @Override
    public final boolean qualified() {
        return this.name().qualified();
    }

    @Override
    public final boolean qualifierQualified() {
        return this.name().qualifierQualified();
    }

    @Override
    public final Name qualifier() {
        return this.name().qualifier();
    }

    @Override
    public final Name unqualifiedName() {
        return this.name().unqualifiedName();
    }

    @Override
    public final Name.Quoted quoted() {
        return this.name().quoted();
    }

    @Override
    public final Name quotedName() {
        return this.name().quotedName();
    }

    @Override
    public final Name unquotedName() {
        return this.name().unquotedName();
    }

    @Override
    public final Name[] parts() {
        return this.name().parts();
    }

    @Override
    public final String[] getName() {
        return this.name().getName();
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return this.name().$traverse(traverser);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return this.name().$replace(replacer);
    }

    @Override
    public int hashCode() {
        return this.name().hashCode();
    }
}

