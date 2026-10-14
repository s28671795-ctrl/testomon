/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.AlterTypeFinalStep;
import org.jooq.AlterTypeRenameValueToStep;
import org.jooq.AlterTypeStep;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function6;
import org.jooq.Name;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.Schema;
import org.jooq.Traverser;
import org.jooq.impl.AbstractDDLQuery;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;

final class AlterTypeImpl
extends AbstractDDLQuery
implements QOM.AlterType,
AlterTypeStep,
AlterTypeRenameValueToStep,
AlterTypeFinalStep {
    final Name type;
    Name renameTo;
    Schema setSchema;
    Field<String> addValue;
    Field<String> renameValue;
    Field<String> renameValueTo;

    AlterTypeImpl(Configuration configuration, Name type) {
        this(configuration, type, null, null, null, null, null);
    }

    AlterTypeImpl(Configuration configuration, Name type, Name renameTo, Schema setSchema, Field<String> addValue, Field<String> renameValue, Field<String> renameValueTo) {
        super(configuration);
        this.type = type;
        this.renameTo = renameTo;
        this.setSchema = setSchema;
        this.addValue = addValue;
        this.renameValue = renameValue;
        this.renameValueTo = renameValueTo;
    }

    @Override
    public final AlterTypeImpl renameTo(String renameTo) {
        return this.renameTo(DSL.name(renameTo));
    }

    @Override
    public final AlterTypeImpl renameTo(Name renameTo) {
        this.renameTo = renameTo;
        return this;
    }

    @Override
    public final AlterTypeImpl setSchema(String setSchema) {
        return this.setSchema(DSL.schema(DSL.name(setSchema)));
    }

    @Override
    public final AlterTypeImpl setSchema(Name setSchema) {
        return this.setSchema(DSL.schema(setSchema));
    }

    @Override
    public final AlterTypeImpl setSchema(Schema setSchema) {
        this.setSchema = setSchema;
        return this;
    }

    @Override
    public final AlterTypeImpl addValue(String addValue) {
        return this.addValue(Tools.field(addValue));
    }

    @Override
    public final AlterTypeImpl addValue(Field<String> addValue) {
        this.addValue = addValue;
        return this;
    }

    @Override
    public final AlterTypeImpl renameValue(String renameValue) {
        return this.renameValue(Tools.field(renameValue));
    }

    @Override
    public final AlterTypeImpl renameValue(Field<String> renameValue) {
        this.renameValue = renameValue;
        return this;
    }

    @Override
    public final AlterTypeImpl to(String renameValueTo) {
        return this.to(Tools.field(renameValueTo));
    }

    @Override
    public final AlterTypeImpl to(Field<String> renameValueTo) {
        this.renameValueTo = renameValueTo;
        return this;
    }

    @Override
    public final void accept(Context<?> ctx) {
        ctx.visit(Keywords.K_ALTER).sql(' ').visit(Keywords.K_TYPE).sql(' ').visit(this.type).sql(' ');
        if (this.renameTo != null) {
            ctx.visit(Keywords.K_RENAME_TO).sql(' ').qualify(false, c -> c.visit(this.renameTo));
        } else if (this.setSchema != null) {
            ctx.visit(Keywords.K_SET).sql(' ').visit(Keywords.K_SCHEMA).sql(' ').visit(this.setSchema);
        } else if (this.addValue != null) {
            ctx.visit(Keywords.K_ADD).sql(' ').visit(Keywords.K_VALUE).sql(' ').visit(this.addValue);
        } else if (this.renameValue != null) {
            ctx.visit(Keywords.K_RENAME).sql(' ').visit(Keywords.K_VALUE).sql(' ').visit(this.renameValue).sql(' ').visit(Keywords.K_TO).sql(' ').visit(this.renameValueTo);
        }
    }

    @Override
    public final Name $type() {
        return this.type;
    }

    @Override
    public final Name $renameTo() {
        return this.renameTo;
    }

    @Override
    public final Schema $setSchema() {
        return this.setSchema;
    }

    @Override
    public final Field<String> $addValue() {
        return this.addValue;
    }

    @Override
    public final Field<String> $renameValue() {
        return this.renameValue;
    }

    @Override
    public final Field<String> $renameValueTo() {
        return this.renameValueTo;
    }

    @Override
    public final QOM.AlterType $type(Name newValue) {
        return this.$constructor().apply(newValue, this.$renameTo(), this.$setSchema(), this.$addValue(), this.$renameValue(), this.$renameValueTo());
    }

    @Override
    public final QOM.AlterType $renameTo(Name newValue) {
        return this.$constructor().apply(this.$type(), newValue, this.$setSchema(), this.$addValue(), this.$renameValue(), this.$renameValueTo());
    }

    @Override
    public final QOM.AlterType $setSchema(Schema newValue) {
        return this.$constructor().apply(this.$type(), this.$renameTo(), newValue, this.$addValue(), this.$renameValue(), this.$renameValueTo());
    }

    @Override
    public final QOM.AlterType $addValue(Field<String> newValue) {
        return this.$constructor().apply(this.$type(), this.$renameTo(), this.$setSchema(), newValue, this.$renameValue(), this.$renameValueTo());
    }

    @Override
    public final QOM.AlterType $renameValue(Field<String> newValue) {
        return this.$constructor().apply(this.$type(), this.$renameTo(), this.$setSchema(), this.$addValue(), newValue, this.$renameValueTo());
    }

    @Override
    public final QOM.AlterType $renameValueTo(Field<String> newValue) {
        return this.$constructor().apply(this.$type(), this.$renameTo(), this.$setSchema(), this.$addValue(), this.$renameValue(), newValue);
    }

    public final Function6<? super Name, ? super Name, ? super Schema, ? super Field<String>, ? super Field<String>, ? super Field<String>, ? extends QOM.AlterType> $constructor() {
        return (a1, a2, a3, a4, a5, a6) -> new AlterTypeImpl(this.configuration(), (Name)a1, (Name)a2, (Schema)a3, (Field<String>)a4, (Field<String>)a5, (Field<String>)a6);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$type(), this.$renameTo(), this.$setSchema(), this.$addValue(), this.$renameValue(), this.$renameValueTo(), (a1, a2, a3, a4, a5, a6) -> this.$constructor().apply((Name)a1, (Name)a2, (Schema)a3, (Field<String>)a4, (Field<String>)a5, (Field<String>)a6), replacer);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$type(), this.$renameTo(), this.$setSchema(), this.$addValue(), this.$renameValue(), this.$renameValueTo());
    }
}

