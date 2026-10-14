/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.DropTableFinalStep;
import org.jooq.DropTableStep;
import org.jooq.Function4;
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

final class DropTableImpl
extends AbstractDDLQuery
implements QOM.DropTable,
DropTableStep,
DropTableFinalStep {
    final boolean temporary;
    final Table<?> table;
    final boolean ifExists;
    QOM.Cascade cascade;
    private static final Clause[] CLAUSES = new Clause[]{Clause.DROP_TABLE};
    private static final Set<SQLDialect> NO_SUPPORT_IF_EXISTS = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.ORACLE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER);
    private static final Set<SQLDialect> TEMPORARY_SEMANTIC = SQLDialect.supportedBy(SQLDialect.AURORA_MYSQL, SQLDialect.MARIADB, SQLDialect.MYSQL);

    DropTableImpl(Configuration configuration, boolean temporary, Table<?> table, boolean ifExists) {
        this(configuration, temporary, table, ifExists, null);
    }

    DropTableImpl(Configuration configuration, boolean temporary, Table<?> table, boolean ifExists, QOM.Cascade cascade) {
        super(configuration);
        this.temporary = temporary;
        this.table = table;
        this.ifExists = ifExists;
        this.cascade = cascade;
    }

    @Override
    public final DropTableImpl cascade() {
        this.cascade = QOM.Cascade.CASCADE;
        return this;
    }

    @Override
    public final DropTableImpl restrict() {
        this.cascade = QOM.Cascade.RESTRICT;
        return this;
    }

    private final boolean supportsIfExists(Context<?> ctx) {
        return !NO_SUPPORT_IF_EXISTS.contains((Object)ctx.dialect());
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (this.ifExists && !this.supportsIfExists(ctx)) {
            Tools.tryCatch(ctx, DDLStatementType.DROP_TABLE, c -> this.accept0((Context<?>)c));
        } else {
            this.accept0(ctx);
        }
    }

    private void accept0(Context<?> ctx) {
        ctx.start(Clause.DROP_TABLE_TABLE);
        if (this.temporary && TEMPORARY_SEMANTIC.contains((Object)ctx.dialect())) {
            ctx.visit(Keywords.K_DROP).sql(' ').visit(Keywords.K_TEMPORARY).sql(' ').visit(Keywords.K_TABLE).sql(' ');
        } else {
            ctx.visit(Keywords.K_DROP_TABLE).sql(' ');
        }
        if (this.ifExists && this.supportsIfExists(ctx)) {
            ctx.visit(Keywords.K_IF_EXISTS).sql(' ');
        }
        ctx.visit(this.table);
        this.acceptCascade(ctx);
        ctx.end(Clause.DROP_TABLE_TABLE);
    }

    private final void acceptCascade(Context<?> ctx) {
        switch (ctx.family()) {
            case ORACLE: {
                if (this.cascade != QOM.Cascade.CASCADE) break;
                ctx.sql(' ').visit(Keywords.K_CASCADE).sql(' ').visit(Keywords.K_CONSTRAINTS);
                break;
            }
            default: {
                DropTableImpl.acceptCascade(ctx, this.cascade);
            }
        }
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return CLAUSES;
    }

    @Override
    public final boolean $temporary() {
        return this.temporary;
    }

    @Override
    public final Table<?> $table() {
        return this.table;
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
    public final QOM.DropTable $temporary(boolean newValue) {
        return this.$constructor().apply(newValue, this.$table(), this.$ifExists(), this.$cascade());
    }

    @Override
    public final QOM.DropTable $table(Table<?> newValue) {
        return this.$constructor().apply(this.$temporary(), newValue, this.$ifExists(), this.$cascade());
    }

    @Override
    public final QOM.DropTable $ifExists(boolean newValue) {
        return this.$constructor().apply(this.$temporary(), this.$table(), newValue, this.$cascade());
    }

    @Override
    public final QOM.DropTable $cascade(QOM.Cascade newValue) {
        return this.$constructor().apply(this.$temporary(), this.$table(), this.$ifExists(), newValue);
    }

    public final Function4<? super Boolean, ? super Table<?>, ? super Boolean, ? super QOM.Cascade, ? extends QOM.DropTable> $constructor() {
        return (a1, a2, a3, a4) -> new DropTableImpl(this.configuration(), (boolean)a1, (Table<?>)a2, (boolean)a3, (QOM.Cascade)((Object)a4));
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$temporary(), this.$table(), this.$ifExists(), this.$cascade(), (a1, a2, a3, a4) -> this.$constructor().apply((Boolean)a1, (Table<?>)a2, (Boolean)a3, (QOM.Cascade)((Object)a4)), replacer);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$table());
    }
}

