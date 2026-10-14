/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.jooq.Condition;
import org.jooq.Context;
import org.jooq.Label;
import org.jooq.LoopStep;
import org.jooq.Name;
import org.jooq.Pro;
import org.jooq.RepeatUntilStep;
import org.jooq.SQLDialect;
import org.jooq.Statement;
import org.jooq.Variable;
import org.jooq.impl.AbstractStatement;
import org.jooq.impl.BlockImpl;
import org.jooq.impl.CustomStatement;
import org.jooq.impl.DSL;
import org.jooq.impl.ForImpl;
import org.jooq.impl.Internal;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.ScopeMarker;
import org.jooq.impl.Tools;

@Pro
final class LoopImpl
extends AbstractStatement
implements LoopStep,
RepeatUntilStep,
QOM.UNotYetImplemented {
    private static final Set<SQLDialect> USE_BEGIN_END_INSTEAD_OF_LOOP = SQLDialect.supportedBy(SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER);
    private static final Set<SQLDialect> USE_DO_END_WHILE_INSTEAD_OF_LOOP = SQLDialect.supportedBy(SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL);
    private static final Set<SQLDialect> USE_DO_BEGIN_END_INSTEAD_OF_LOOP = SQLDialect.supportedBy(SQLDialect.FIREBIRD);
    private static final Set<SQLDialect> USE_DO_END_FOR_INSTEAD_OF_LOOP = SQLDialect.supportedBy(SQLDialect.HANA, SQLDialect.MARIADB);
    private static final Set<SQLDialect> EMULATE_REVERSE = SQLDialect.supportedBy(SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE);
    private static final Set<SQLDialect> EMULATE_INFINITE_LOOP = SQLDialect.supportedBy(SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER);
    private static final Set<SQLDialect> EMULATE_INDEXED_FOR_LOOP = SQLDialect.supportedBy(SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.MYSQL, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER);
    private static final Set<SQLDialect> EMULATE_INDEXED_FOR_BY_LOOP = SQLDialect.supportedBy(SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.ORACLE);
    private static final Set<SQLDialect> EMULATE_REPEAT = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB);
    private final ForImpl<?> for_;
    private final Condition condition;
    private final Collection<? extends Statement> statements;
    private final boolean repeat;

    LoopImpl(ForImpl<?> for_, Condition condition, Collection<? extends Statement> statements, boolean repeat) {
        this.for_ = for_;
        this.condition = condition;
        this.statements = statements;
        this.repeat = repeat;
    }

    @Override
    public final Statement loop(Statement ... s2) {
        return this.loop(Arrays.asList(s2));
    }

    @Override
    public final Statement loop(Collection<? extends Statement> s2) {
        return new LoopImpl(this.for_, this.condition, s2, this.repeat);
    }

    @Override
    public final Statement until(Condition c) {
        return new LoopImpl(this.for_, c, this.statements, this.repeat);
    }

    @Override
    public final void accept(Context<?> ctx) {
        Tools.increment(ctx.data(), Tools.SimpleDataKey.DATA_BLOCK_NESTING, () -> {
            switch (ctx.family()) {
                case H2: {
                    this.acceptH2(ctx);
                    break;
                }
                default: {
                    ctx.data(Tools.ExtendedDataKey.DATA_SCOPE_LOOP, new ScopeLoop(DSL.name(ctx.nextAlias())), c -> {
                        ctx.scopeStart().scopeRegisterAndMark(ScopeMarker.SCOPE_LOOP.beforeFirst, true);
                        this.acceptDefault(ctx);
                        ctx.scopeEnd();
                    });
                }
            }
        });
    }

    private final void acceptH2(Context<?> ctx) {
        if (this.repeat) {
            ctx.sql("do {").formatIndentStart().formatSeparator().visit(DSL.statements(this.statements)).formatIndentEnd().formatSeparator().sql('}').formatSeparator().sql("while (!(").visit(this.condition).sql("))");
        } else if (this.condition != null) {
            ctx.sql("while (").visit(this.condition).sql(") {").formatIndentStart().formatSeparator().visit(DSL.statements(this.statements)).formatIndentEnd().formatSeparator().sql('}');
        } else if (this.for_ != null) {
            String type = BlockImpl.variableType(this.for_.index);
            String name = this.for_.index.getName();
            ctx.sql("for (").sql(type).sql(" ").sql(this.for_.index.getName()).sql(" = ").visit(this.for_.start).sql("; ").sql(name).sql(this.for_.reverse ? " >= " : " <= ").visit(this.for_.stop).sql("; ").sql(this.for_.index.getName());
            if (this.for_.step == null) {
                ctx.sql(this.for_.reverse ? "--" : "++");
            } else {
                ctx.sql(" = ").sql(this.for_.index.getName()).sql(this.for_.reverse ? " - " : " + ").visit(this.for_.step);
            }
            ctx.sql(") {").formatIndentStart().formatSeparator().visit(DSL.statements(this.statements)).formatIndentEnd().formatSeparator().sql('}');
        } else {
            ctx.sql("for (;;) {").formatIndentStart().formatSeparator().visit(DSL.statements(this.statements)).formatIndentEnd().formatSeparator().sql('}');
        }
    }

    private final void acceptDefault(Context<?> ctx) {
        if (this.repeat) {
            if (EMULATE_REPEAT.contains((Object)ctx.dialect())) {
                ctx.visit(this.emulateRepeatWithWhile(ctx));
            } else {
                ctx.visit(Keywords.K_REPEAT).formatIndentStart().formatSeparator().visit(DSL.statements(this.statements)).formatIndentEnd().formatSeparator().visit(Keywords.K_UNTIL).sql(' ').visit(this.condition).sql(' ').visit(Keywords.K_END).sql(' ').visit(Keywords.K_REPEAT);
            }
        } else if (this.condition != null) {
            ctx.visit(Keywords.K_WHILE).sql(' ');
            if (ctx.family() == SQLDialect.FIREBIRD) {
                ctx.sql('(').visit(this.condition).sql(')');
            } else {
                ctx.visit(this.condition);
            }
            ctx.sql(' ');
            this.acceptLoop(ctx);
        } else if (this.for_ != null) {
            if (EMULATE_INDEXED_FOR_LOOP.contains((Object)ctx.dialect())) {
                ctx.visit(this.emulateForWithWhile(ctx));
            } else if (this.for_.step != null && EMULATE_INDEXED_FOR_BY_LOOP.contains((Object)ctx.dialect())) {
                ctx.visit(this.emulateForWithWhile(ctx));
            } else if (ctx.family() == SQLDialect.INFORMIX) {
                ctx.visit(this.emulateForInformix());
            } else if (ctx.family() == SQLDialect.HANA) {
                ctx.visit(DSL.begin(DSL.declare(this.for_.index), this.emulateForNative()));
            } else {
                ctx.visit(this.emulateForNative());
            }
        } else if (EMULATE_INFINITE_LOOP.contains((Object)ctx.dialect())) {
            ctx.visit(DSL.while_(DSL.trueCondition()).loop(this.statements));
        } else {
            this.acceptLoop(ctx);
        }
    }

    private final Statement emulateForNative() {
        return CustomStatement.of(ctx -> {
            ctx.visit(Keywords.K_FOR).sql(' ').visit(this.for_.index).sql(' ').visit(Keywords.K_IN).sql(' ');
            if (this.for_.reverse) {
                ctx.visit(Keywords.K_REVERSE).sql(' ');
            }
            if (this.for_.reverse && EMULATE_REVERSE.contains((Object)ctx.dialect())) {
                ctx.visit(this.for_.stop).sql(" .. ").visit(this.for_.start);
            } else {
                ctx.visit(this.for_.start).sql(" .. ").visit(this.for_.stop);
            }
            if (this.for_.step != null) {
                ctx.sql(' ').visit(Keywords.K_BY).sql(' ').visit(this.for_.step);
            }
            ctx.sql(' ');
            this.acceptLoop((Context<?>)ctx);
        });
    }

    private final Statement emulateForInformix() {
        Variable i = this.for_.index;
        return DSL.begin(DSL.declare(i).set(this.for_.start), CustomStatement.of(ctx -> {
            ctx.visit(Keywords.K_FOR).sql(' ').visit(this.for_.index).sql(' ').visit(Keywords.K_IN).sql(' ');
            ctx.sql("(").visit(this.for_.start).sql(' ').visit(Keywords.K_TO).sql(' ').visit(this.for_.stop);
            if (this.for_.step != null) {
                ctx.sql(' ').visit(Keywords.K_STEP).sql(' ').visit(this.for_.reverse ? this.for_.step.neg() : this.for_.step);
            }
            ctx.sql(") ");
            this.acceptLoop((Context<?>)ctx);
        }));
    }

    private final Statement emulateForWithWhile(Context<?> ctx) {
        Variable i = this.for_.index;
        Statement[] statementArray = new Statement[2];
        statementArray[0] = DSL.declare(i).set(this.for_.start);
        Statement[] statementArray2 = new Statement[2];
        statementArray2[0] = DSL.statements(this.statements);
        statementArray2[1] = this.for_.reverse ? (this.for_.step != null ? i.set(Internal.isub(i, this.for_.step)) : i.set(Internal.isub(i, DSL.one()))) : (this.for_.step != null ? i.set(Internal.iadd(i, this.for_.step)) : i.set(Internal.iadd(i, DSL.one())));
        statementArray[1] = DSL.while_(this.for_.reverse ? i.ge(this.for_.stop) : i.le(this.for_.stop)).loop(statementArray2);
        List<Statement> list = Arrays.asList(statementArray);
        return ctx.family() == SQLDialect.FIREBIRD ? DSL.statements(list) : DSL.begin(list);
    }

    private final Statement emulateRepeatWithWhile(Context<?> ctx) {
        if (ctx.family() == SQLDialect.HANA) {
            return DSL.loop(DSL.statements(this.statements), DSL.exitWhen(this.condition));
        }
        Label label = DSL.label(ctx.nextAlias());
        return label.label(DSL.loop(DSL.statements(this.statements), DSL.exit(label).when(this.condition)));
    }

    private void acceptLoop(Context<?> ctx) {
        boolean b1 = USE_BEGIN_END_INSTEAD_OF_LOOP.contains((Object)ctx.dialect());
        boolean b2 = USE_DO_END_WHILE_INSTEAD_OF_LOOP.contains((Object)ctx.dialect()) && (this.condition != null || EMULATE_INDEXED_FOR_LOOP.contains((Object)ctx.dialect()) && this.for_ != null);
        boolean b3 = USE_DO_END_FOR_INSTEAD_OF_LOOP.contains((Object)ctx.dialect()) && this.for_ != null;
        boolean b4 = USE_DO_BEGIN_END_INSTEAD_OF_LOOP.contains((Object)ctx.dialect());
        if (b1) {
            ctx.visit(Keywords.K_BEGIN);
        } else if (b2 || b3) {
            ctx.visit(Keywords.K_DO);
        } else if (b4) {
            ctx.visit(Keywords.K_DO).sql(' ').visit(Keywords.K_BEGIN);
        } else {
            ctx.visit(Keywords.K_LOOP);
        }
        ctx.formatIndentStart().formatSeparator().visit(DSL.statements(this.statements)).formatIndentEnd().formatSeparator();
        if (b1 || b4) {
            ctx.visit(Keywords.K_END);
        } else if (b2) {
            ctx.visit(Keywords.K_END).sql(' ').visit(Keywords.K_WHILE);
        } else if (b3) {
            ctx.visit(Keywords.K_END).sql(' ').visit(Keywords.K_FOR);
        } else {
            ctx.visit(Keywords.K_END).sql(' ').visit(Keywords.K_LOOP);
        }
    }

    @Pro
    static class ScopeLoop
    implements ScopeMarker.ScopeContent {
        final Name label;
        boolean active;

        ScopeLoop(Name label) {
            this.label = label;
        }

        Name activateLabel() {
            this.active = true;
            return this.label;
        }

        @Override
        public boolean isEmpty() {
            return !this.active;
        }

        public String toString() {
            return this.label.toString();
        }
    }
}

