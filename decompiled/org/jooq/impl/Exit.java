/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.Condition;
import org.jooq.Context;
import org.jooq.ExitWhenStep;
import org.jooq.Field;
import org.jooq.Label;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.SQLDialect;
import org.jooq.Statement;
import org.jooq.Traverser;
import org.jooq.impl.AbstractStatement;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.LoopImpl;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;

@Pro
final class Exit
extends AbstractStatement
implements ExitWhenStep,
QOM.Exit {
    private static final Set<SQLDialect> NO_SUPPORT_LABELLESS = SQLDialect.supportedBy(SQLDialect.DB2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL);
    static final Exit INSTANCE = new Exit(null, null);
    private final Label label;
    private final Condition condition;

    Exit(Label label, Condition condition) {
        this.label = label;
        this.condition = condition;
    }

    @Override
    public final Statement when(Condition newCondition) {
        return new Exit(this.label, newCondition);
    }

    @Override
    public final Statement when(Field<Boolean> newCondition) {
        return this.when(DSL.condition(newCondition));
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case H2: {
                if (this.condition != null) {
                    ctx.visit(DSL.if_(this.condition).then(this.label != null ? DSL.exit(this.label) : DSL.exit()));
                    break;
                }
                if (this.label != null) {
                    ctx.sql("break ").visit(this.label);
                    break;
                }
                ctx.sql("break");
                break;
            }
            case BIGQUERY: 
            case HANA: 
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: {
                if (this.condition != null) {
                    ctx.visit(DSL.if_(this.condition).then(this.label != null ? DSL.exit(this.label) : DSL.exit()));
                    break;
                }
                ctx.visit(Keywords.K_BREAK);
                break;
            }
            case FIREBIRD: 
            case HSQLDB: 
            case MARIADB: 
            case MYSQL: 
            case DB2: {
                LoopImpl.ScopeLoop loop;
                if (this.label == null && NO_SUPPORT_LABELLESS.contains((Object)ctx.dialect()) && (loop = (LoopImpl.ScopeLoop)ctx.data(Tools.ExtendedDataKey.DATA_SCOPE_LOOP)) != null) {
                    Exit.accept0(ctx, this.condition, DSL.label(loop.activateLabel()));
                    break;
                }
                Exit.accept0(ctx, this.condition, this.label);
                break;
            }
            default: {
                ctx.visit(ctx.family() == SQLDialect.DB2 ? Keywords.K_LEAVE : Keywords.K_EXIT);
                if (this.label != null) {
                    ctx.sql(' ').visit(this.label);
                }
                if (this.condition == null) break;
                ctx.sql(' ').visit(Keywords.K_WHEN).sql(' ').visit(this.condition);
            }
        }
    }

    private static final void accept0(Context<?> ctx, Condition condition, Label label) {
        if (condition != null) {
            ctx.visit(DSL.if_(condition).then(label != null ? DSL.exit(label) : DSL.exit()));
        } else if (label != null) {
            ctx.visit(Keywords.K_LEAVE).sql(' ').visit(label);
        } else {
            ctx.visit(Keywords.K_LEAVE);
        }
    }

    @Override
    public final Label $label() {
        return this.label;
    }

    @Override
    public final Condition $condition() {
        return this.condition;
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.label, this.condition);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.label, this.condition, Exit::new, replacer);
    }
}

