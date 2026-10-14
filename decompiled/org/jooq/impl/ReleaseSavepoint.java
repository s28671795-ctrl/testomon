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
import org.jooq.Statement;
import org.jooq.Traverser;
import org.jooq.impl.AbstractRowCountQuery;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;

final class ReleaseSavepoint
extends AbstractRowCountQuery
implements QOM.ReleaseSavepoint {
    final Name name;

    ReleaseSavepoint(Configuration configuration, Name name) {
        super(configuration);
        this.name = name;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: {
                if (ctx.data(Tools.SimpleDataKey.DATA_BLOCK_NESTING) != null) break;
                ctx.visit(DSL.begin(new Statement[0]));
                break;
            }
            default: {
                ctx.visit(Keywords.K_RELEASE).sql(' ').visit(Keywords.K_SAVEPOINT).sql(' ').visit(this.name);
            }
        }
    }

    @Override
    public final Name $name() {
        return this.name;
    }

    @Override
    public final QOM.ReleaseSavepoint $name(Name newValue) {
        return this.$constructor().apply(newValue);
    }

    public final Function1<? super Name, ? extends QOM.ReleaseSavepoint> $constructor() {
        return a1 -> new ReleaseSavepoint(this.configuration(), (Name)a1);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$name(), a1 -> this.$constructor().apply((Name)a1), replacer);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$name());
    }
}

