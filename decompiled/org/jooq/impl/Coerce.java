/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.Traverser;
import org.jooq.impl.AbstractField;
import org.jooq.impl.AutoAlias;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;
import org.jooq.tools.StringUtils;

final class Coerce<T>
extends AbstractField<T>
implements AutoAlias<Field<T>>,
QOM.Coerce<T> {
    final AbstractField<?> field;

    public Coerce(Field<?> field2, DataType<T> type) {
        super(field2.getQualifiedName(), type);
        this.field = (AbstractField)Tools.uncoerce(field2);
    }

    @Override
    public final void accept(Context<?> ctx) {
        ctx.visit(this.field);
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return this.field.clauses(ctx);
    }

    @Override
    boolean isPossiblyNullable() {
        return this.field.isPossiblyNullable();
    }

    @Override
    public final Field<T> as(Name alias) {
        return this.field.as(alias).coerce(this.getDataType());
    }

    @Override
    public final boolean rendersContent(Context<?> ctx) {
        return this.field.rendersContent(ctx);
    }

    @Override
    public final boolean declaresFields() {
        return this.field.declaresFields();
    }

    @Override
    public final boolean declaresTables() {
        return this.field.declaresTables();
    }

    @Override
    public final boolean declaresWindows() {
        return this.field.declaresWindows();
    }

    @Override
    public final boolean declaresCTE() {
        return this.field.declaresCTE();
    }

    @Override
    public final boolean declaresParameters() {
        return this.field.declaresParameters();
    }

    @Override
    public final boolean generatesCast() {
        return this.field.generatesCast();
    }

    @Override
    public final Field<T> autoAlias(Context<?> ctx, Field<T> f) {
        if (this.field instanceof AutoAlias) {
            return ((Field)((AutoAlias)((Object)this.field)).autoAlias(ctx, this.field)).coerce(this.getDataType());
        }
        return f;
    }

    @Override
    public final Field<?> $aliased() {
        return this.field.$aliased().coerce(this.getDataType());
    }

    @Override
    public final Name $alias() {
        return this.field.$alias();
    }

    @Override
    public final Field<?> $field() {
        return this.field;
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$field(), this.$dataType(), Coerce::new, replacer);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$field(), this.$dataType());
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof Coerce) {
            Coerce o = (Coerce)that;
            return StringUtils.equals(this.$field(), o.$field());
        }
        return super.equals(that);
    }
}

