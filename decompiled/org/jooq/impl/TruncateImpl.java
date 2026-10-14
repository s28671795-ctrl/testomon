/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Function3;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Replacer;
import org.jooq.SQLDialect;
import org.jooq.Table;
import org.jooq.Traverser;
import org.jooq.Truncate;
import org.jooq.TruncateCascadeStep;
import org.jooq.TruncateFinalStep;
import org.jooq.TruncateIdentityStep;
import org.jooq.impl.AbstractDDLQuery;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;

final class TruncateImpl<R extends Record>
extends AbstractDDLQuery
implements QOM.Truncate<R>,
TruncateIdentityStep<R>,
TruncateCascadeStep<R>,
TruncateFinalStep<R>,
Truncate<R> {
    final Table<R> table;
    QOM.IdentityRestartOption restartIdentity;
    QOM.Cascade cascade;
    private static final Clause[] CLAUSES = new Clause[]{Clause.TRUNCATE};

    TruncateImpl(Configuration configuration, Table<R> table) {
        this(configuration, table, null, null);
    }

    TruncateImpl(Configuration configuration, Table<R> table, QOM.IdentityRestartOption restartIdentity, QOM.Cascade cascade) {
        super(configuration);
        this.table = table;
        this.restartIdentity = restartIdentity;
        this.cascade = cascade;
    }

    @Override
    public final TruncateImpl<R> restartIdentity() {
        this.restartIdentity = QOM.IdentityRestartOption.RESTART_IDENTITY;
        return this;
    }

    @Override
    public final TruncateImpl<R> continueIdentity() {
        this.restartIdentity = QOM.IdentityRestartOption.CONTINUE_IDENTITY;
        return this;
    }

    @Override
    public final TruncateImpl<R> cascade() {
        this.cascade = QOM.Cascade.CASCADE;
        return this;
    }

    @Override
    public final TruncateImpl<R> restrict() {
        this.cascade = QOM.Cascade.RESTRICT;
        return this;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case ACCESS: 
            case INGRES: 
            case TERADATA: 
            case FIREBIRD: 
            case IGNITE: 
            case SQLITE: {
                ctx.visit(DSL.delete(this.table));
                break;
            }
            default: {
                ctx.start(Clause.TRUNCATE_TRUNCATE).visit(Keywords.K_TRUNCATE).sql(' ').visit(Keywords.K_TABLE).sql(' ').visit(this.table);
                if (ctx.family() == SQLDialect.DB2) {
                    ctx.sql(' ').visit(Keywords.K_IMMEDIATE);
                }
                if (this.restartIdentity != null) {
                    ctx.formatSeparator().visit(this.restartIdentity.keyword);
                }
                if (this.cascade != null) {
                    if (ctx.family() == SQLDialect.ORACLE) {
                        if (this.cascade == QOM.Cascade.CASCADE) {
                            ctx.formatSeparator().visit(Keywords.K_CASCADE);
                        }
                    } else {
                        ctx.formatSeparator().visit(this.cascade == QOM.Cascade.CASCADE ? Keywords.K_CASCADE : Keywords.K_RESTRICT);
                    }
                }
                ctx.end(Clause.TRUNCATE_TRUNCATE);
            }
        }
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return CLAUSES;
    }

    @Override
    public final Table<R> $table() {
        return this.table;
    }

    @Override
    public final QOM.IdentityRestartOption $restartIdentity() {
        return this.restartIdentity;
    }

    @Override
    public final QOM.Cascade $cascade() {
        return this.cascade;
    }

    @Override
    public final QOM.Truncate<R> $table(Table<R> newValue) {
        return this.$constructor().apply(newValue, this.$restartIdentity(), this.$cascade());
    }

    @Override
    public final QOM.Truncate<R> $restartIdentity(QOM.IdentityRestartOption newValue) {
        return this.$constructor().apply(this.$table(), newValue, this.$cascade());
    }

    @Override
    public final QOM.Truncate<R> $cascade(QOM.Cascade newValue) {
        return this.$constructor().apply(this.$table(), this.$restartIdentity(), newValue);
    }

    public final Function3<? super Table<R>, ? super QOM.IdentityRestartOption, ? super QOM.Cascade, ? extends QOM.Truncate<R>> $constructor() {
        return (a1, a2, a3) -> new TruncateImpl<R>(this.configuration(), a1, (QOM.IdentityRestartOption)((Object)a2), (QOM.Cascade)((Object)a3));
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$table(), this.$restartIdentity(), this.$cascade(), (a1, a2, a3) -> this.$constructor().apply((Table<R>)a1, (QOM.IdentityRestartOption)((Object)a2), (QOM.Cascade)((Object)a3)), replacer);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$table());
    }
}

