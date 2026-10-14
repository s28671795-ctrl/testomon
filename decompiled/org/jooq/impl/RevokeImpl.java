/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Collection;
import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Function5;
import org.jooq.Name;
import org.jooq.Privilege;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.RevokeFinalStep;
import org.jooq.RevokeFromStep;
import org.jooq.RevokeOnStep;
import org.jooq.Role;
import org.jooq.SQLDialect;
import org.jooq.Table;
import org.jooq.Traverser;
import org.jooq.User;
import org.jooq.impl.AbstractDDLQuery;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.QueryPartListView;

final class RevokeImpl
extends AbstractDDLQuery
implements QOM.Revoke,
RevokeOnStep,
RevokeFromStep,
RevokeFinalStep {
    final QueryPartListView<? extends Privilege> privileges;
    final boolean grantOptionFor;
    Table<?> on;
    Role from;
    boolean fromPublic;
    private static final Clause[] CLAUSE = new Clause[]{Clause.REVOKE};

    RevokeImpl(Configuration configuration, Collection<? extends Privilege> privileges, boolean grantOptionFor) {
        this(configuration, privileges, grantOptionFor, null, null, false);
    }

    RevokeImpl(Configuration configuration, Collection<? extends Privilege> privileges, boolean grantOptionFor, Table<?> on, Role from, boolean fromPublic) {
        super(configuration);
        this.privileges = new QueryPartList<Privilege>((Iterable<? extends Privilege>)privileges);
        this.grantOptionFor = grantOptionFor;
        this.on = on;
        this.from = from;
        this.fromPublic = fromPublic;
    }

    @Override
    public final RevokeImpl on(String on) {
        return this.on((Table)DSL.table(DSL.name(on)));
    }

    @Override
    public final RevokeImpl on(Name on) {
        return this.on((Table)DSL.table(on));
    }

    @Override
    public final RevokeImpl on(Table<?> on) {
        this.on = on;
        return this;
    }

    @Override
    public final RevokeImpl from(User from) {
        return this.from(DSL.role(from.getQualifiedName()));
    }

    @Override
    public final RevokeImpl from(Role from) {
        this.from = from;
        return this;
    }

    @Override
    public final RevokeImpl fromPublic() {
        this.fromPublic = true;
        return this;
    }

    @Override
    public final void accept(Context<?> ctx) {
        ctx.start(Clause.REVOKE_PRIVILEGE).visit(Keywords.K_REVOKE).sql(' ');
        if (this.grantOptionFor) {
            ctx.visit(Keywords.K_GRANT_OPTION_FOR).sql(' ');
        }
        ctx.visit(this.privileges).end(Clause.REVOKE_PRIVILEGE).sql(' ').start(Clause.REVOKE_ON).visit(Keywords.K_ON).sql(' ').visit(this.on).end(Clause.REVOKE_ON).sql(' ').start(Clause.REVOKE_FROM).visit(Keywords.K_FROM).sql(' ');
        if (this.from != null) {
            ctx.visit(this.from);
        } else if (this.fromPublic) {
            ctx.visit(Keywords.K_PUBLIC);
        }
        if (ctx.family() == SQLDialect.HSQLDB) {
            ctx.sql(' ').visit(Keywords.K_RESTRICT);
        }
        ctx.end(Clause.REVOKE_FROM);
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return CLAUSE;
    }

    @Override
    public final QOM.UnmodifiableList<? extends Privilege> $privileges() {
        return QOM.unmodifiable(this.privileges);
    }

    @Override
    public final boolean $grantOptionFor() {
        return this.grantOptionFor;
    }

    @Override
    public final Table<?> $on() {
        return this.on;
    }

    @Override
    public final Role $from() {
        return this.from;
    }

    @Override
    public final boolean $fromPublic() {
        return this.fromPublic;
    }

    @Override
    public final QOM.Revoke $privileges(Collection<? extends Privilege> newValue) {
        return this.$constructor().apply(newValue, this.$grantOptionFor(), this.$on(), this.$from(), this.$fromPublic());
    }

    @Override
    public final QOM.Revoke $grantOptionFor(boolean newValue) {
        return this.$constructor().apply(this.$privileges(), newValue, this.$on(), this.$from(), this.$fromPublic());
    }

    @Override
    public final QOM.Revoke $on(Table<?> newValue) {
        return this.$constructor().apply(this.$privileges(), this.$grantOptionFor(), newValue, this.$from(), this.$fromPublic());
    }

    @Override
    public final QOM.Revoke $from(Role newValue) {
        return this.$constructor().apply(this.$privileges(), this.$grantOptionFor(), this.$on(), newValue, this.$fromPublic());
    }

    @Override
    public final QOM.Revoke $fromPublic(boolean newValue) {
        return this.$constructor().apply(this.$privileges(), this.$grantOptionFor(), this.$on(), this.$from(), newValue);
    }

    public final Function5<? super Collection<? extends Privilege>, ? super Boolean, ? super Table<?>, ? super Role, ? super Boolean, ? extends QOM.Revoke> $constructor() {
        return (a1, a2, a3, a4, a5) -> new RevokeImpl(this.configuration(), (Collection<? extends Privilege>)a1, (boolean)a2, (Table<?>)a3, (Role)a4, (boolean)a5);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$privileges(), this.$grantOptionFor(), this.$on(), this.$from(), this.$fromPublic(), (a1, a2, a3, a4, a5) -> this.$constructor().apply((Collection<Privilege>)a1, (Boolean)a2, (Table<?>)a3, (Role)a4, (Boolean)a5), replacer);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$privileges(), this.$on(), this.$from());
    }
}

