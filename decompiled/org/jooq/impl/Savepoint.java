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
import org.jooq.Traverser;
import org.jooq.impl.AbstractRowCountQuery;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;

final class Savepoint
extends AbstractRowCountQuery
implements QOM.Savepoint {
    final Name name;

    Savepoint(Configuration configuration, Name name) {
        super(configuration);
        this.name = name;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case DB2: {
                ctx.visit(Keywords.K_SAVEPOINT).sql(' ').visit(this.name).sql(' ').visit(Keywords.K_ON).sql(' ').visit(Keywords.K_ROLLBACK).sql(' ').visit(Keywords.K_RETAIN).sql(' ').visit(Keywords.K_CURSORS);
                break;
            }
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: {
                ctx.visit(Keywords.K_SAVE).sql(' ').visit(Keywords.K_TRANSACTION).sql(' ').visit(this.name);
                break;
            }
            default: {
                ctx.visit(Keywords.K_SAVEPOINT).sql(' ').visit(this.name);
            }
        }
    }

    @Override
    public final Name $name() {
        return this.name;
    }

    @Override
    public final QOM.Savepoint $name(Name newValue) {
        return this.$constructor().apply(newValue);
    }

    public final Function1<? super Name, ? extends QOM.Savepoint> $constructor() {
        return a1 -> new Savepoint(this.configuration(), (Name)a1);
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

