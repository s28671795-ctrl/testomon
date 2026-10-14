/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.DropViewFinalStep;
import org.jooq.Function2;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.SQLDialect;
import org.jooq.Table;
import org.jooq.Traverser;
import org.jooq.impl.AbstractDDLQuery;
import org.jooq.impl.DDLStatementType;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;

final class DropViewImpl
extends AbstractDDLQuery
implements QOM.DropView,
DropViewFinalStep {
    final Table<?> view;
    final boolean ifExists;
    private static final Clause[] CLAUSES = new Clause[]{Clause.DROP_VIEW};
    private static final Set<SQLDialect> NO_SUPPORT_IF_EXISTS = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.ORACLE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER);

    DropViewImpl(Configuration configuration, Table<?> view, boolean ifExists) {
        super(configuration);
        this.view = view;
        this.ifExists = ifExists;
    }

    private final boolean supportsIfExists(Context<?> ctx) {
        return !NO_SUPPORT_IF_EXISTS.contains((Object)ctx.dialect());
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (this.ifExists && !this.supportsIfExists(ctx)) {
            Tools.tryCatch(ctx, DDLStatementType.DROP_VIEW, c -> this.accept0((Context<?>)c));
        } else {
            this.accept0(ctx);
        }
    }

    private void accept0(Context<?> ctx) {
        ctx.start(Clause.DROP_VIEW_TABLE).visit(Keywords.K_DROP_VIEW).sql(' ');
        if (this.ifExists && this.supportsIfExists(ctx)) {
            ctx.visit(Keywords.K_IF_EXISTS).sql(' ');
        }
        if (ctx.family() == SQLDialect.SQLSERVER) {
            ctx.qualifyCatalog(false, c -> c.visit(this.view));
        } else {
            ctx.visit(this.view);
        }
        ctx.end(Clause.DROP_VIEW_TABLE);
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return CLAUSES;
    }

    @Override
    public final Table<?> $view() {
        return this.view;
    }

    @Override
    public final boolean $ifExists() {
        return this.ifExists;
    }

    @Override
    public final QOM.DropView $view(Table<?> newValue) {
        return this.$constructor().apply(newValue, this.$ifExists());
    }

    @Override
    public final QOM.DropView $ifExists(boolean newValue) {
        return this.$constructor().apply(this.$view(), newValue);
    }

    public final Function2<? super Table<?>, ? super Boolean, ? extends QOM.DropView> $constructor() {
        return (a1, a2) -> new DropViewImpl(this.configuration(), (Table<?>)a1, (boolean)a2);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$view(), this.$ifExists(), (a1, a2) -> this.$constructor().apply((Table<?>)a1, (Boolean)a2), replacer);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$view());
    }
}

