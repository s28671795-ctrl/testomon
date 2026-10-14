/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import org.jooq.Condition;
import org.jooq.Context;
import org.jooq.IfElseStep;
import org.jooq.IfThenStep;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.SQLDialect;
import org.jooq.Statement;
import org.jooq.impl.AbstractStatement;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;

@Pro
final class IfImpl
extends AbstractStatement
implements IfThenStep,
IfElseStep,
QOM.UNotYetImplemented {
    private static final Set<SQLDialect> SUPPORT_ELSEIF = SQLDialect.supportedBy(SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL);
    private static final Set<SQLDialect> SUPPORT_ELIF = SQLDialect.supportedBy(SQLDialect.INFORMIX);
    private final IfImpl previous;
    private final Condition condition;
    private final Collection<? extends Statement> ifStatements;
    private final Collection<? extends Statement> elseStatements;

    IfImpl(IfImpl previous, Condition condition, Collection<? extends Statement> ifStatements, Collection<? extends Statement> elseStatements) {
        this.previous = previous;
        this.condition = condition;
        this.ifStatements = ifStatements;
        this.elseStatements = elseStatements;
    }

    @Override
    public final IfImpl then(Statement ... statements) {
        return this.then(Arrays.asList(statements));
    }

    @Override
    public final IfImpl then(Collection<? extends Statement> statements) {
        return new IfImpl(this.previous, this.condition, statements, null);
    }

    @Override
    public final IfImpl else_(Statement ... s2) {
        return this.else_(Arrays.asList(s2));
    }

    @Override
    public final IfImpl else_(Collection<? extends Statement> statements) {
        return new IfImpl(this.previous, this.condition, this.ifStatements, statements);
    }

    @Override
    public final IfImpl elsif(Condition c) {
        return new IfImpl(this, c, null, null);
    }

    @Override
    public final void accept(Context<?> ctx) {
        Tools.increment(ctx.data(), Tools.SimpleDataKey.DATA_BLOCK_NESTING, () -> {
            IfImpl.accept0(ctx, this);
            switch (ctx.family()) {
                case H2: {
                    if (this.elseStatements == null) break;
                    ctx.sql(" else ").visit(DSL.begin(this.elseStatements));
                    break;
                }
                case FIREBIRD: 
                case SQLDATAWAREHOUSE: 
                case SQLSERVER: {
                    if (this.elseStatements == null) break;
                    ctx.visit(Keywords.K_ELSE).formatIndentStart().formatSeparator().visit(this.elseStatements.size() == 1 ? (QueryPart)this.elseStatements.iterator().next() : DSL.begin(this.elseStatements)).formatIndentEnd().formatSeparator();
                    break;
                }
                default: {
                    if (this.elseStatements != null) {
                        ctx.visit(Keywords.K_ELSE).formatIndentStart().formatSeparator().visit(DSL.statements(this.elseStatements)).formatIndentEnd().formatSeparator();
                    }
                    ctx.visit(Keywords.K_END_IF);
                }
            }
        });
    }

    private static final void accept0(Context<?> ctx, IfImpl if_) {
        switch (ctx.family()) {
            case H2: {
                if (if_.previous != null) {
                    IfImpl.accept0(ctx, if_.previous);
                    ctx.sql(" else ");
                }
                ctx.sql("if (").visit(if_.condition).sql(") ").visit(DSL.begin(if_.ifStatements));
                break;
            }
            case FIREBIRD: 
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: {
                if (if_.previous != null) {
                    IfImpl.accept0(ctx, if_.previous);
                    ctx.visit(Keywords.K_ELSE).sql(' ');
                }
                ctx.visit(Keywords.K_IF).sql(' ');
                if (ctx.family() == SQLDialect.FIREBIRD) {
                    ctx.sql('(').visit(if_.condition).sql(')').sql(' ').visit(Keywords.K_THEN);
                } else {
                    ctx.visit(if_.condition);
                }
                ctx.formatIndentStart().formatSeparator().visit(if_.ifStatements.size() == 1 ? DSL.statements(if_.ifStatements.iterator().next()) : DSL.begin(if_.ifStatements)).formatIndentEnd().formatSeparator();
                break;
            }
            default: {
                if (if_.previous != null) {
                    IfImpl.accept0(ctx, if_.previous);
                }
                ctx.visit(if_.previous == null ? Keywords.K_IF : (SUPPORT_ELSEIF.contains((Object)ctx.dialect()) ? Keywords.K_ELSEIF : (SUPPORT_ELIF.contains((Object)ctx.dialect()) ? Keywords.K_ELIF : Keywords.K_ELSIF))).sql(' ').visit(if_.condition).sql(' ').visit(Keywords.K_THEN).formatIndentStart().formatSeparator().visit(DSL.statements(if_.ifStatements)).formatIndentEnd().formatSeparator();
            }
        }
    }
}

