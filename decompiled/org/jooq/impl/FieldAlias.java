/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jetbrains.annotations.NotNull;
import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.Traverser;
import org.jooq.impl.AbstractField;
import org.jooq.impl.Alias;
import org.jooq.impl.QOM;
import org.jooq.impl.SimpleCheckQueryPart;

final class FieldAlias<T>
extends AbstractField<T>
implements QOM.FieldAlias<T>,
SimpleCheckQueryPart {
    private final Alias<Field<T>> alias;

    FieldAlias(Field<T> field2, Name alias) {
        super(alias, field2.getDataType());
        this.alias = new Alias<FieldAlias>((FieldAlias)field2, this, alias);
    }

    @Override
    public final boolean isSimple(Context<?> ctx) {
        return !ctx.declareFields();
    }

    @Override
    public final void accept(Context<?> ctx) {
        ctx.visit(this.alias);
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return null;
    }

    @Override
    public final Field<T> as(Name as) {
        return this.alias.wrapped().as(as);
    }

    @Override
    public final boolean declaresFields() {
        return true;
    }

    @Override
    public Name getQualifiedName() {
        return this.getUnqualifiedName();
    }

    Field<T> getAliasedField() {
        if (this.alias != null) {
            return this.alias.wrapped();
        }
        return null;
    }

    @Override
    public final Field<T> $field() {
        return this.alias.wrapped();
    }

    @Override
    public final Field<?> $aliased() {
        return this.alias.wrapped();
    }

    @Override
    @NotNull
    public final Name $alias() {
        return this.getQualifiedName();
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$field(), this.$alias());
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$field(), this.$alias(), FieldAlias::new, replacer);
    }
}

