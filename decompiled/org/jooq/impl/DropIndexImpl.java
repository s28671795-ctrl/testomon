/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.DropIndexCascadeStep;
import org.jooq.DropIndexFinalStep;
import org.jooq.DropIndexOnStep;
import org.jooq.Function4;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.SQLDialect;
import org.jooq.Table;
import org.jooq.Traverser;
import org.jooq.impl.AbstractDDLQuery;
import org.jooq.impl.DDLStatementType;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;

final class DropIndexImpl
extends AbstractDDLQuery
implements QOM.DropIndex,
DropIndexOnStep,
DropIndexCascadeStep,
DropIndexFinalStep {
    final Index index;
    final boolean ifExists;
    Table<?> on;
    QOM.Cascade cascade;
    private static final Clause[] CLAUSES = new Clause[]{Clause.DROP_INDEX};
    private static final Set<SQLDialect> NO_SUPPORT_IF_EXISTS = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.ORACLE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER);
    private static final Set<SQLDialect> REQUIRES_ON = SQLDialect.supportedBy(SQLDialect.AURORA_MYSQL, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.TERADATA);

    DropIndexImpl(Configuration configuration, Index index, boolean ifExists) {
        this(configuration, index, ifExists, null, null);
    }

    DropIndexImpl(Configuration configuration, Index index, boolean ifExists, Table<?> on, QOM.Cascade cascade) {
        super(configuration);
        this.index = index;
        this.ifExists = ifExists;
        this.on = on;
        this.cascade = cascade;
    }

    @Override
    public final DropIndexImpl on(String on) {
        return this.on((Table)DSL.table(DSL.name(on)));
    }

    @Override
    public final DropIndexImpl on(Name on) {
        return this.on((Table)DSL.table(on));
    }

    @Override
    public final DropIndexImpl on(Table<?> on) {
        this.on = on;
        return this;
    }

    @Override
    public final DropIndexImpl cascade() {
        this.cascade = QOM.Cascade.CASCADE;
        return this;
    }

    @Override
    public final DropIndexImpl restrict() {
        this.cascade = QOM.Cascade.RESTRICT;
        return this;
    }

    private final boolean supportsIfExists(Context<?> ctx) {
        return !NO_SUPPORT_IF_EXISTS.contains((Object)ctx.dialect());
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (this.ifExists && !this.supportsIfExists(ctx)) {
            Tools.tryCatch(ctx, DDLStatementType.DROP_INDEX, c -> this.accept0((Context<?>)c));
        } else {
            this.accept0(ctx);
        }
    }

    private void accept0(Context<?> ctx) {
        ctx.visit(Keywords.K_DROP_INDEX).sql(' ');
        if (this.ifExists && this.supportsIfExists(ctx)) {
            ctx.visit(Keywords.K_IF_EXISTS).sql(' ');
        }
        if (this.on != null && ctx.family() == SQLDialect.ASE) {
            ctx.visit(this.on).sql('.');
        }
        ctx.visit(this.index);
        if (REQUIRES_ON.contains((Object)ctx.dialect())) {
            if (this.on != null) {
                ctx.sql(' ').visit(Keywords.K_ON).sql(' ').visit(this.on);
            } else if (this.index.getTable() != null) {
                ctx.sql(' ').visit(Keywords.K_ON).sql(' ').visit(this.index.getTable());
            }
        }
        DropIndexImpl.acceptCascade(ctx, this.cascade);
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return CLAUSES;
    }

    @Override
    public final Index $index() {
        return this.index;
    }

    @Override
    public final boolean $ifExists() {
        return this.ifExists;
    }

    @Override
    public final Table<?> $on() {
        return this.on;
    }

    @Override
    public final QOM.Cascade $cascade() {
        return this.cascade;
    }

    @Override
    public final QOM.DropIndex $index(Index newValue) {
        return this.$constructor().apply(newValue, this.$ifExists(), this.$on(), this.$cascade());
    }

    @Override
    public final QOM.DropIndex $ifExists(boolean newValue) {
        return this.$constructor().apply(this.$index(), newValue, this.$on(), this.$cascade());
    }

    @Override
    public final QOM.DropIndex $on(Table<?> newValue) {
        return this.$constructor().apply(this.$index(), this.$ifExists(), newValue, this.$cascade());
    }

    @Override
    public final QOM.DropIndex $cascade(QOM.Cascade newValue) {
        return this.$constructor().apply(this.$index(), this.$ifExists(), this.$on(), newValue);
    }

    public final Function4<? super Index, ? super Boolean, ? super Table<?>, ? super QOM.Cascade, ? extends QOM.DropIndex> $constructor() {
        return (a1, a2, a3, a4) -> new DropIndexImpl(this.configuration(), (Index)a1, (boolean)a2, (Table<?>)a3, (QOM.Cascade)((Object)a4));
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$index(), this.$ifExists(), this.$on(), this.$cascade(), (a1, a2, a3, a4) -> this.$constructor().apply((Index)a1, (Boolean)a2, (Table<?>)a3, (QOM.Cascade)((Object)a4)), replacer);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$index(), this.$on());
    }
}

