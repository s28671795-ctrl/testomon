/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.DropSequenceFinalStep;
import org.jooq.Function2;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.SQLDialect;
import org.jooq.Sequence;
import org.jooq.Traverser;
import org.jooq.impl.AbstractDDLQuery;
import org.jooq.impl.DDLStatementType;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;

final class DropSequenceImpl
extends AbstractDDLQuery
implements QOM.DropSequence,
DropSequenceFinalStep {
    final Sequence<?> sequence;
    final boolean ifExists;
    private static final Clause[] CLAUSES = new Clause[]{Clause.DROP_SEQUENCE};
    private static final Set<SQLDialect> NO_SUPPORT_IF_EXISTS = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.ORACLE, SQLDialect.SQLSERVER, SQLDialect.SYBASE);

    DropSequenceImpl(Configuration configuration, Sequence<?> sequence2, boolean ifExists) {
        super(configuration);
        this.sequence = sequence2;
        this.ifExists = ifExists;
    }

    private final boolean supportsIfExists(Context<?> ctx) {
        return !NO_SUPPORT_IF_EXISTS.contains((Object)ctx.dialect());
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (this.ifExists && !this.supportsIfExists(ctx)) {
            Tools.tryCatch(ctx, DDLStatementType.DROP_SEQUENCE, c -> this.accept0((Context<?>)c));
        } else {
            this.accept0(ctx);
        }
    }

    private void accept0(Context<?> ctx) {
        ctx.start(Clause.DROP_SEQUENCE_SEQUENCE).visit(Keywords.K_DROP).sql(' ').visit(ctx.family() == SQLDialect.CUBRID ? Keywords.K_SERIAL : Keywords.K_SEQUENCE).sql(' ');
        if (this.ifExists && this.supportsIfExists(ctx)) {
            ctx.visit(Keywords.K_IF_EXISTS).sql(' ');
        }
        switch (ctx.family()) {
            case SQLSERVER: {
                ctx.qualifyCatalog(false, c -> c.visit(this.sequence));
                break;
            }
            default: {
                ctx.visit(this.sequence);
            }
        }
        if (ctx.family() == SQLDialect.DERBY) {
            ctx.sql(' ').visit(Keywords.K_RESTRICT);
        }
        ctx.end(Clause.DROP_SEQUENCE_SEQUENCE);
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return CLAUSES;
    }

    @Override
    public final Sequence<?> $sequence() {
        return this.sequence;
    }

    @Override
    public final boolean $ifExists() {
        return this.ifExists;
    }

    @Override
    public final QOM.DropSequence $sequence(Sequence<?> newValue) {
        return this.$constructor().apply(newValue, this.$ifExists());
    }

    @Override
    public final QOM.DropSequence $ifExists(boolean newValue) {
        return this.$constructor().apply(this.$sequence(), newValue);
    }

    public final Function2<? super Sequence<?>, ? super Boolean, ? extends QOM.DropSequence> $constructor() {
        return (a1, a2) -> new DropSequenceImpl(this.configuration(), (Sequence<?>)a1, (boolean)a2);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$sequence(), this.$ifExists(), (a1, a2) -> this.$constructor().apply((Sequence<?>)a1, (Boolean)a2), replacer);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$sequence());
    }
}

