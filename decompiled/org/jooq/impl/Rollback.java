/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Function1;
import org.jooq.Name;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.RollbackToSavepointStep;
import org.jooq.Traverser;
import org.jooq.impl.AbstractRowCountQuery;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;

final class Rollback
extends AbstractRowCountQuery
implements QOM.Rollback,
RollbackToSavepointStep {
    Name toSavepoint;

    Rollback(Configuration configuration) {
        this(configuration, null);
    }

    Rollback(Configuration configuration, Name toSavepoint) {
        super(configuration);
        this.toSavepoint = toSavepoint;
    }

    @Override
    public final Rollback toSavepoint(String toSavepoint) {
        return this.toSavepoint(DSL.name(toSavepoint));
    }

    @Override
    public final Rollback toSavepoint(Name toSavepoint) {
        this.toSavepoint = toSavepoint;
        return this;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: {
                ctx.visit(Keywords.K_ROLLBACK);
                if (this.toSavepoint == null) break;
                ctx.sql(' ').visit(Keywords.K_TRANSACTION).sql(' ').visit(this.toSavepoint);
                break;
            }
            default: {
                ctx.visit(Keywords.K_ROLLBACK);
                if (this.toSavepoint == null) break;
                ctx.sql(' ').visit(Keywords.K_TO).sql(' ').visit(Keywords.K_SAVEPOINT).sql(' ').visit(this.toSavepoint);
            }
        }
    }

    @Override
    public final Name $toSavepoint() {
        return this.toSavepoint;
    }

    @Override
    public final QOM.Rollback $toSavepoint(Name newValue) {
        return this.$constructor().apply(newValue);
    }

    public final Function1<? super Name, ? extends QOM.Rollback> $constructor() {
        return a1 -> new Rollback(this.configuration(), (Name)a1);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$toSavepoint(), a1 -> this.$constructor().apply((Name)a1), replacer);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$toSavepoint());
    }
}

