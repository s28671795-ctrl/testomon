/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Domain;
import org.jooq.DropDomainCascadeStep;
import org.jooq.DropDomainFinalStep;
import org.jooq.Function3;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.SQLDialect;
import org.jooq.Traverser;
import org.jooq.impl.AbstractDDLQuery;
import org.jooq.impl.DDLStatementType;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;

final class DropDomainImpl
extends AbstractDDLQuery
implements QOM.DropDomain,
DropDomainCascadeStep,
DropDomainFinalStep {
    final Domain<?> domain;
    final boolean ifExists;
    QOM.Cascade cascade;
    private static final Set<SQLDialect> NO_SUPPORT_IF_EXISTS = SQLDialect.supportedBy(SQLDialect.FIREBIRD);

    DropDomainImpl(Configuration configuration, Domain<?> domain, boolean ifExists) {
        this(configuration, domain, ifExists, null);
    }

    DropDomainImpl(Configuration configuration, Domain<?> domain, boolean ifExists, QOM.Cascade cascade) {
        super(configuration);
        this.domain = domain;
        this.ifExists = ifExists;
        this.cascade = cascade;
    }

    @Override
    public final DropDomainImpl cascade() {
        this.cascade = QOM.Cascade.CASCADE;
        return this;
    }

    @Override
    public final DropDomainImpl restrict() {
        this.cascade = QOM.Cascade.RESTRICT;
        return this;
    }

    private final boolean supportsIfExists(Context<?> ctx) {
        return !NO_SUPPORT_IF_EXISTS.contains((Object)ctx.dialect());
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (this.ifExists && !this.supportsIfExists(ctx)) {
            Tools.tryCatch(ctx, DDLStatementType.DROP_DOMAIN, c -> this.accept0((Context<?>)c));
        } else {
            this.accept0(ctx);
        }
    }

    private final void accept0(Context<?> ctx) {
        switch (ctx.family()) {
            case SQLSERVER: {
                ctx.visit(Keywords.K_DROP).sql(' ').visit(Keywords.K_TYPE);
                break;
            }
            default: {
                ctx.visit(Keywords.K_DROP).sql(' ').visit(Keywords.K_DOMAIN);
            }
        }
        if (this.ifExists && this.supportsIfExists(ctx)) {
            ctx.sql(' ').visit(Keywords.K_IF_EXISTS);
        }
        ctx.sql(' ').visit(this.domain);
        DropDomainImpl.acceptCascade(ctx, this.cascade);
    }

    @Override
    public final Domain<?> $domain() {
        return this.domain;
    }

    @Override
    public final boolean $ifExists() {
        return this.ifExists;
    }

    @Override
    public final QOM.Cascade $cascade() {
        return this.cascade;
    }

    @Override
    public final QOM.DropDomain $domain(Domain<?> newValue) {
        return this.$constructor().apply(newValue, this.$ifExists(), this.$cascade());
    }

    @Override
    public final QOM.DropDomain $ifExists(boolean newValue) {
        return this.$constructor().apply(this.$domain(), newValue, this.$cascade());
    }

    @Override
    public final QOM.DropDomain $cascade(QOM.Cascade newValue) {
        return this.$constructor().apply(this.$domain(), this.$ifExists(), newValue);
    }

    public final Function3<? super Domain<?>, ? super Boolean, ? super QOM.Cascade, ? extends QOM.DropDomain> $constructor() {
        return (a1, a2, a3) -> new DropDomainImpl(this.configuration(), (Domain<?>)a1, (boolean)a2, (QOM.Cascade)((Object)a3));
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$domain(), this.$ifExists(), this.$cascade(), (a1, a2, a3) -> this.$constructor().apply((Domain<?>)a1, (Boolean)a2, (QOM.Cascade)((Object)a3)), replacer);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$domain());
    }
}

