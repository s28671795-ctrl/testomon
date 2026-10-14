/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.AlterIndexFinalStep;
import org.jooq.AlterIndexOnStep;
import org.jooq.AlterIndexStep;
import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
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

final class AlterIndexImpl
extends AbstractDDLQuery
implements QOM.AlterIndex,
AlterIndexOnStep,
AlterIndexStep,
AlterIndexFinalStep {
    final Index index;
    final boolean ifExists;
    Table<?> on;
    Index renameTo;
    private static final Clause[] CLAUSES = new Clause[]{Clause.ALTER_INDEX};
    private static final Set<SQLDialect> NO_SUPPORT_IF_EXISTS = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.ORACLE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER);
    private static final Set<SQLDialect> SUPPORT_RENAME_INDEX = SQLDialect.supportedBy(SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.HANA, SQLDialect.INFORMIX);

    AlterIndexImpl(Configuration configuration, Index index, boolean ifExists) {
        this(configuration, index, ifExists, null, null);
    }

    AlterIndexImpl(Configuration configuration, Index index, boolean ifExists, Table<?> on, Index renameTo) {
        super(configuration);
        this.index = index;
        this.ifExists = ifExists;
        this.on = on;
        this.renameTo = renameTo;
    }

    @Override
    public final AlterIndexImpl on(String on) {
        return this.on((Table)DSL.table(DSL.name(on)));
    }

    @Override
    public final AlterIndexImpl on(Name on) {
        return this.on((Table)DSL.table(on));
    }

    @Override
    public final AlterIndexImpl on(Table<?> on) {
        this.on = on;
        return this;
    }

    @Override
    public final AlterIndexImpl renameTo(String renameTo) {
        return this.renameTo(DSL.index(DSL.name(renameTo)));
    }

    @Override
    public final AlterIndexImpl renameTo(Name renameTo) {
        return this.renameTo(DSL.index(renameTo));
    }

    @Override
    public final AlterIndexImpl renameTo(Index renameTo) {
        this.renameTo = renameTo;
        return this;
    }

    private final boolean supportsIfExists(Context<?> ctx) {
        return !NO_SUPPORT_IF_EXISTS.contains((Object)ctx.dialect());
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (this.ifExists && !this.supportsIfExists(ctx)) {
            Tools.tryCatch(ctx, DDLStatementType.ALTER_INDEX, c -> this.accept0((Context<?>)c));
        } else {
            this.accept0(ctx);
        }
    }

    private final void accept0(Context<?> ctx) {
        boolean renameIndex = SUPPORT_RENAME_INDEX.contains((Object)ctx.dialect());
        switch (ctx.family()) {
            case AURORA_MYSQL: 
            case MEMSQL: 
            case MARIADB: 
            case MYSQL: {
                ctx.visit(Keywords.K_ALTER_TABLE).sql(' ').visit(this.on).sql(' ').visit(Keywords.K_RENAME_INDEX).sql(' ').qualify(false, c -> c.visit(this.index)).sql(' ').visit(Keywords.K_TO).sql(' ').qualify(false, c -> c.visit(this.renameTo));
                break;
            }
            case ASE: 
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: {
                boolean ase;
                ctx.visit(Keywords.K_EXEC).sql(' ').sql("sp_rename").sql(' ').sql('\'').stringLiteral(true);
                boolean bl = ase = ctx.family() == SQLDialect.ASE;
                if (this.on != null) {
                    ctx.visit(ase ? this.on.getQualifiedName().unquotedName() : this.on).sql('.').visit(ase ? this.index.getUnqualifiedName().unquotedName() : this.index.getUnqualifiedName());
                } else {
                    ctx.visit(ase ? this.index.getQualifiedName().unquotedName() : this.index.getQualifiedName());
                }
                ctx.stringLiteral(false).sql("', ").qualify(false, c -> c.visit(this.renameTo)).sql(", ").visit(ase ? DSL.inline("index") : DSL.inline("INDEX"));
                break;
            }
            default: {
                ctx.start(Clause.ALTER_INDEX_INDEX).visit(renameIndex ? Keywords.K_RENAME_INDEX : Keywords.K_ALTER_INDEX);
                if (this.ifExists && this.supportsIfExists(ctx)) {
                    ctx.sql(' ').visit(Keywords.K_IF_EXISTS);
                }
                ctx.sql(' ');
                if (this.on != null) {
                    ctx.visit(this.on).sql('.').visit(this.index.getUnqualifiedName());
                } else {
                    ctx.visit(this.index);
                }
                ctx.end(Clause.ALTER_INDEX_INDEX).formatIndentStart().formatSeparator();
                if (this.renameTo != null) {
                    ctx.start(Clause.ALTER_INDEX_RENAME).visit(renameIndex ? Keywords.K_TO : Keywords.K_RENAME_TO).sql(' ').qualify(false, c -> c.visit(this.renameTo)).end(Clause.ALTER_INDEX_RENAME);
                }
                ctx.formatIndentEnd();
            }
        }
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
    public final Index $renameTo() {
        return this.renameTo;
    }

    @Override
    public final QOM.AlterIndex $index(Index newValue) {
        return this.$constructor().apply(newValue, this.$ifExists(), this.$on(), this.$renameTo());
    }

    @Override
    public final QOM.AlterIndex $ifExists(boolean newValue) {
        return this.$constructor().apply(this.$index(), newValue, this.$on(), this.$renameTo());
    }

    @Override
    public final QOM.AlterIndex $on(Table<?> newValue) {
        return this.$constructor().apply(this.$index(), this.$ifExists(), newValue, this.$renameTo());
    }

    @Override
    public final QOM.AlterIndex $renameTo(Index newValue) {
        return this.$constructor().apply(this.$index(), this.$ifExists(), this.$on(), newValue);
    }

    public final Function4<? super Index, ? super Boolean, ? super Table<?>, ? super Index, ? extends QOM.AlterIndex> $constructor() {
        return (a1, a2, a3, a4) -> new AlterIndexImpl(this.configuration(), (Index)a1, (boolean)a2, (Table<?>)a3, (Index)a4);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$index(), this.$ifExists(), this.$on(), this.$renameTo(), (a1, a2, a3, a4) -> this.$constructor().apply((Index)a1, (Boolean)a2, (Table<?>)a3, (Index)a4), replacer);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$index(), this.$on(), this.$renameTo());
    }
}

