/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.CreateTypeFinalStep;
import org.jooq.CreateTypeStep;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.Traverser;
import org.jooq.conf.ParamType;
import org.jooq.impl.AbstractDDLQuery;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;

final class CreateTypeImpl
extends AbstractDDLQuery
implements CreateTypeStep,
CreateTypeFinalStep,
QOM.CreateType {
    private final Name type;
    private final QueryPartList<Field<String>> values;

    CreateTypeImpl(Configuration configuration, Name type) {
        super(configuration);
        this.type = type;
        this.values = new QueryPartList();
    }

    @Override
    public final CreateTypeFinalStep asEnum() {
        return this.asEnum(Collections.emptyList());
    }

    @Override
    public final CreateTypeFinalStep asEnum(String ... v) {
        return this.asEnum(Tools.map(v, s2 -> DSL.inline(s2)));
    }

    @Override
    @SafeVarargs
    public final CreateTypeFinalStep asEnum(Field<String> ... v) {
        return this.asEnum(Arrays.asList(v));
    }

    @Override
    public final CreateTypeFinalStep asEnum(Collection<?> v) {
        this.values.addAll((Collection<Field<String>>)Tools.fields(v, SQLDataType.VARCHAR));
        return this;
    }

    @Override
    public final void accept(Context<?> ctx) {
        ctx.visit(Keywords.K_CREATE).sql(' ').visit(Keywords.K_TYPE).sql(' ').visit(this.type).sql(' ').visit(Keywords.K_AS).sql(' ').visit(Keywords.K_ENUM).sql(" (").visit(this.values, ParamType.INLINED).sql(')');
    }

    @Override
    public final Name $name() {
        return this.type;
    }

    @Override
    public final QOM.UnmodifiableList<? extends Field<String>> $values() {
        return QOM.unmodifiable(this.values);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.type, this.values);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.type, this.values, (t2, v) -> new CreateTypeImpl(this.configuration(), (Name)t2).asEnum((Collection<?>)v), replacer);
    }
}

