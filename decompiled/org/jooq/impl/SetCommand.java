/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Function3;
import org.jooq.Name;
import org.jooq.Param;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.SQLDialect;
import org.jooq.Traverser;
import org.jooq.conf.ParamType;
import org.jooq.impl.AbstractDDLQuery;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;

final class SetCommand
extends AbstractDDLQuery
implements QOM.SetCommand {
    final Name name;
    final Param<?> value;
    final boolean local;
    private static final Set<SQLDialect> NO_SUPPORT_BIND_VALUES = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES);

    SetCommand(Configuration configuration, Name name, Param<?> value, boolean local) {
        super(configuration);
        this.name = name;
        this.value = value;
        this.local = local;
    }

    @Override
    public final void accept(Context<?> ctx) {
        ctx.visit(Keywords.K_SET);
        if (this.local) {
            ctx.sql(' ').visit(Keywords.K_LOCAL);
        }
        ctx.sql(' ').visit(this.name).sql(" = ").paramTypeIf(ParamType.INLINED, NO_SUPPORT_BIND_VALUES.contains((Object)ctx.dialect()), c -> c.visit(this.value));
    }

    @Override
    public final Name $name() {
        return this.name;
    }

    @Override
    public final Param<?> $value() {
        return this.value;
    }

    @Override
    public final boolean $local() {
        return this.local;
    }

    @Override
    public final QOM.SetCommand $name(Name newValue) {
        return this.$constructor().apply(newValue, this.$value(), this.$local());
    }

    @Override
    public final QOM.SetCommand $value(Param<?> newValue) {
        return this.$constructor().apply(this.$name(), newValue, this.$local());
    }

    @Override
    public final QOM.SetCommand $local(boolean newValue) {
        return this.$constructor().apply(this.$name(), this.$value(), newValue);
    }

    public final Function3<? super Name, ? super Param<?>, ? super Boolean, ? extends QOM.SetCommand> $constructor() {
        return (a1, a2, a3) -> new SetCommand(this.configuration(), (Name)a1, (Param<?>)a2, (boolean)a3);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$name(), this.$value(), this.$local(), (a1, a2, a3) -> this.$constructor().apply((Name)a1, (Param<?>)a2, (Boolean)a3), replacer);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$name(), this.$value());
    }
}

