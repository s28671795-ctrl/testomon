/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Collection;
import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Function5;
import org.jooq.GrantFinalStep;
import org.jooq.GrantOnStep;
import org.jooq.GrantToStep;
import org.jooq.GrantWithGrantOptionStep;
import org.jooq.Name;
import org.jooq.Privilege;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.Role;
import org.jooq.Table;
import org.jooq.Traverser;
import org.jooq.User;
import org.jooq.impl.AbstractDDLQuery;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartCollectionView;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.QueryPartListView;

final class GrantImpl
extends AbstractDDLQuery
implements QOM.Grant,
GrantOnStep,
GrantToStep,
GrantWithGrantOptionStep,
GrantFinalStep {
    final QueryPartListView<? extends Privilege> privileges;
    Table<?> on;
    Role to;
    boolean toPublic;
    boolean withGrantOption;
    private static final Clause[] CLAUSE = new Clause[]{Clause.GRANT};

    GrantImpl(Configuration configuration, Collection<? extends Privilege> privileges) {
        this(configuration, privileges, null, null, false, false);
    }

    GrantImpl(Configuration configuration, Collection<? extends Privilege> privileges, Table<?> on, Role to, boolean toPublic, boolean withGrantOption) {
        super(configuration);
        this.privileges = new QueryPartList<Privilege>((Iterable<? extends Privilege>)privileges);
        this.on = on;
        this.to = to;
        this.toPublic = toPublic;
        this.withGrantOption = withGrantOption;
    }

    @Override
    public final GrantImpl on(String on) {
        return this.on((Table)DSL.table(DSL.name(on)));
    }

    @Override
    public final GrantImpl on(Name on) {
        return this.on((Table)DSL.table(on));
    }

    @Override
    public final GrantImpl on(Table<?> on) {
        this.on = on;
        return this;
    }

    @Override
    public final GrantImpl to(User to) {
        return this.to(DSL.role(to.getQualifiedName()));
    }

    @Override
    public final GrantImpl to(Role to) {
        this.to = to;
        return this;
    }

    @Override
    public final GrantImpl toPublic() {
        this.toPublic = true;
        return this;
    }

    @Override
    public final GrantImpl withGrantOption() {
        this.withGrantOption = true;
        return this;
    }

    @Override
    public final void accept(Context<?> ctx) {
        ctx.start(Clause.GRANT_PRIVILEGE).visit(Keywords.K_GRANT).sql(' ').visit(QueryPartCollectionView.wrap(this.privileges)).end(Clause.GRANT_PRIVILEGE).sql(' ').start(Clause.GRANT_ON).visit(Keywords.K_ON).sql(' ').visit(this.on).end(Clause.GRANT_ON).sql(' ').start(Clause.GRANT_TO).visit(Keywords.K_TO).sql(' ');
        if (this.to != null) {
            ctx.visit(this.to);
        } else if (this.toPublic) {
            ctx.visit(Keywords.K_PUBLIC);
        }
        if (this.withGrantOption) {
            ctx.sql(' ').visit(Keywords.K_WITH_GRANT_OPTION);
        }
        ctx.end(Clause.GRANT_TO);
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
    public final Table<?> $on() {
        return this.on;
    }

    @Override
    public final Role $to() {
        return this.to;
    }

    @Override
    public final boolean $toPublic() {
        return this.toPublic;
    }

    @Override
    public final boolean $withGrantOption() {
        return this.withGrantOption;
    }

    @Override
    public final QOM.Grant $privileges(Collection<? extends Privilege> newValue) {
        return this.$constructor().apply(newValue, this.$on(), this.$to(), this.$toPublic(), this.$withGrantOption());
    }

    @Override
    public final QOM.Grant $on(Table<?> newValue) {
        return this.$constructor().apply(this.$privileges(), newValue, this.$to(), this.$toPublic(), this.$withGrantOption());
    }

    @Override
    public final QOM.Grant $to(Role newValue) {
        return this.$constructor().apply(this.$privileges(), this.$on(), newValue, this.$toPublic(), this.$withGrantOption());
    }

    @Override
    public final QOM.Grant $toPublic(boolean newValue) {
        return this.$constructor().apply(this.$privileges(), this.$on(), this.$to(), newValue, this.$withGrantOption());
    }

    @Override
    public final QOM.Grant $withGrantOption(boolean newValue) {
        return this.$constructor().apply(this.$privileges(), this.$on(), this.$to(), this.$toPublic(), newValue);
    }

    public final Function5<? super Collection<? extends Privilege>, ? super Table<?>, ? super Role, ? super Boolean, ? super Boolean, ? extends QOM.Grant> $constructor() {
        return (a1, a2, a3, a4, a5) -> new GrantImpl(this.configuration(), (Collection<? extends Privilege>)a1, (Table<?>)a2, (Role)a3, (boolean)a4, (boolean)a5);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$privileges(), this.$on(), this.$to(), this.$toPublic(), this.$withGrantOption(), (a1, a2, a3, a4, a5) -> this.$constructor().apply((Collection<Privilege>)a1, (Table<?>)a2, (Role)a3, (Boolean)a4, (Boolean)a5), replacer);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$privileges(), this.$on(), this.$to());
    }
}

