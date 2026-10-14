/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import org.jooq.Block;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.DDLQuery;
import org.jooq.Keyword;
import org.jooq.LanguageContext;
import org.jooq.Name;
import org.jooq.Pro;
import org.jooq.Query;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.SQLDialect;
import org.jooq.Statement;
import org.jooq.Traverser;
import org.jooq.Variable;
import org.jooq.conf.ParamType;
import org.jooq.impl.AbstractRowCountQuery;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultRenderContext;
import org.jooq.impl.Keywords;
import org.jooq.impl.NullStatement;
import org.jooq.impl.QOM;
import org.jooq.impl.ScopeMarker;
import org.jooq.impl.Tools;

final class BlockImpl
extends AbstractRowCountQuery
implements Block {
    private static final Set<SQLDialect> REQUIRES_EXECUTE_IMMEDIATE_ON_DDL = SQLDialect.supportedBy(SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.ORACLE);
    private static final Set<SQLDialect> SUPPORTS_NULL_STATEMENT = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB);
    @Pro
    static final Set<SQLDialect> REQUIRES_TOP_LEVEL_BEGIN_END = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB);
    @Pro
    static final Set<SQLDialect> SUPPORTS_DECLARE_BLOCK_KEYWORD = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB);
    @Pro
    static final Set<SQLDialect> REQUIRES_DECLARE_BLOCK = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB);
    @Pro
    static final Set<SQLDialect> NO_SUPPORT_NESTED_DECLARATIONS = SQLDialect.supportedBy(SQLDialect.EXASOL, SQLDialect.FIREBIRD);
    @Pro
    static final Set<SQLDialect> NO_SUPPORT_BLOCK_SCOPE = SQLDialect.supportedBy(SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER);
    @Pro
    static final Set<SQLDialect> REQUIRE_LABEL_FOR_RETURN = SQLDialect.supportedBy(SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL);
    final Collection<? extends Statement> statements;
    final boolean alwaysWrapInBeginEnd;
    static final String STATEMENT_VARIABLES = "org.jooq.impl.BlockImpl.statement-variables";
    static final Package JAVA_LANG = String.class.getPackage();

    BlockImpl(Configuration configuration, Collection<? extends Statement> statements, boolean alwaysWrapInBeginEnd) {
        super(configuration);
        this.statements = Tools.collect(Tools.filter(statements, s2 -> !(s2 instanceof NullStatement)));
        this.alwaysWrapInBeginEnd = alwaysWrapInBeginEnd;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case FIREBIRD: {
                if (Tools.increment(ctx.data(), Tools.SimpleDataKey.DATA_BLOCK_NESTING)) {
                    ctx.paramType(ParamType.INLINED).visit(Keywords.K_EXECUTE_BLOCK).sql(' ').visit(Keywords.K_AS);
                    ctx.data(Tools.BooleanDataKey.DATA_FORCE_STATIC_STATEMENT, true);
                    BlockImpl.scopeDeclarations(ctx.formatIndentStart(), c -> this.accept0((Context<?>)c.formatIndentEnd().formatSeparator()));
                } else {
                    this.accept0(ctx);
                }
                Tools.decrement(ctx.data(), Tools.SimpleDataKey.DATA_BLOCK_NESTING);
                break;
            }
            case AURORA_POSTGRES: 
            case POSTGRES: 
            case YUGABYTEDB: {
                BlockImpl.bodyAsString(ctx, Keywords.K_DO, c -> this.accept0((Context<?>)c));
                break;
            }
            case H2: {
                String name = BlockImpl.randomName();
                if (Tools.increment(ctx.data(), Tools.SimpleDataKey.DATA_BLOCK_NESTING)) {
                    ctx.paramType(ParamType.INLINED).visit(Keywords.K_CREATE).sql(' ').visit(Keywords.K_ALIAS).sql(' ').sql(name).sql(' ').visit(Keywords.K_AS).sql(" $$").formatIndentStart().formatSeparator().sql("void x(Connection c) throws SQLException ");
                    ctx.data(Tools.BooleanDataKey.DATA_FORCE_STATIC_STATEMENT, true);
                }
                this.accept0(ctx);
                if (!Tools.decrement(ctx.data(), Tools.SimpleDataKey.DATA_BLOCK_NESTING)) break;
                ctx.formatIndentEnd().formatSeparator().sql("$$;").formatSeparator().visit(Keywords.K_CALL).sql(' ').sql(name).sql("();").formatSeparator().visit(Keywords.K_DROP).sql(' ').visit(Keywords.K_ALIAS).sql(' ').sql(name).sql(';');
                break;
            }
            case HANA: {
                if (Tools.increment(ctx.data(), Tools.SimpleDataKey.DATA_BLOCK_NESTING)) {
                    ctx.paramType(ParamType.INLINED).visit(Keywords.K_DO).sql(' ');
                    ctx.data(Tools.BooleanDataKey.DATA_FORCE_STATIC_STATEMENT, true);
                }
                this.accept0(ctx);
                Tools.decrement(ctx.data(), Tools.SimpleDataKey.DATA_BLOCK_NESTING);
                break;
            }
            case MYSQL: {
                if (Tools.increment(ctx.data(), Tools.SimpleDataKey.DATA_BLOCK_NESTING)) {
                    String name = BlockImpl.randomName();
                    ctx.visit(DSL.statements(DSL.createProcedure(name).modifiesSQLData().as((Statement)this), DSL.call(name), DSL.dropProcedure(name)));
                    break;
                }
                this.accept0(ctx);
                break;
            }
            default: {
                Tools.increment(ctx.data(), Tools.SimpleDataKey.DATA_BLOCK_NESTING);
                this.accept0(ctx);
                Tools.decrement(ctx.data(), Tools.SimpleDataKey.DATA_BLOCK_NESTING);
            }
        }
    }

    static final void scopeDeclarations(Context<?> ctx, Consumer<? super Context<?>> runnable2) {
        if (!ctx.configuration().commercial()) {
            runnable2.accept(ctx);
            return;
        }
        boolean routine = ctx.data(Tools.ExtendedDataKey.DATA_TOP_SCOPE_ROUTINE_NAME) != null;
        boolean trigger = ctx.data(Tools.ExtendedDataKey.DATA_TOP_SCOPE_TRIGGER_NAME) != null;
        ctx.data(Tools.ExtendedDataKey.DATA_SCOPE_DECLARATIONS, new ScopeDeclarations(!routine, routine && !trigger, trigger, ctx.scopeLevel() + 1), c -> {
            c.scopeStart().scopeRegisterAndMark(ScopeMarker.SCOPE_DECLARATIONS.beforeFirst, true);
            runnable2.accept((Context<?>)c);
            c.scopeEnd();
        });
    }

    static final void bodyAsString(Context<?> ctx, Keyword keyword, Consumer<? super Context<?>> runnable2) {
        ParamType previous = ctx.paramType();
        if (Tools.increment(ctx.data(), Tools.SimpleDataKey.DATA_BLOCK_NESTING)) {
            ctx.paramType(ParamType.INLINED);
            if (keyword != null) {
                ctx.visit(keyword).sql(' ');
            }
            ctx.sql("$$").formatSeparator().data(Tools.BooleanDataKey.DATA_FORCE_STATIC_STATEMENT, true);
        }
        runnable2.accept(ctx);
        if (Tools.decrement(ctx.data(), Tools.SimpleDataKey.DATA_BLOCK_NESTING)) {
            ctx.formatSeparator().sql("$$").paramType(previous);
        }
    }

    private static final String randomName() {
        return "block_" + System.currentTimeMillis() + "_" + (long)(1.0E7 * Math.random());
    }

    private final void accept0(Context<?> ctx) {
        boolean wrapInBeginEnd;
        boolean bl = wrapInBeginEnd = this.alwaysWrapInBeginEnd || REQUIRES_TOP_LEVEL_BEGIN_END.contains((Object)ctx.dialect()) && Tools.toplevel(ctx.data(), Tools.SimpleDataKey.DATA_BLOCK_NESTING);
        if (wrapInBeginEnd) {
            boolean topLevel = ctx.scopeLevel() == -1;
            LanguageContext language2 = ctx.languageContext();
            if (topLevel && language2 == LanguageContext.QUERY) {
                ctx.languageContext(LanguageContext.BLOCK);
            }
            boolean noNestedDeclarations = NO_SUPPORT_NESTED_DECLARATIONS.contains((Object)ctx.dialect());
            boolean noBlockScope = NO_SUPPORT_BLOCK_SCOPE.contains((Object)ctx.dialect());
            boolean declareBlockKeyword = SUPPORTS_DECLARE_BLOCK_KEYWORD.contains((Object)ctx.dialect());
            boolean requireLabelForReturn = REQUIRE_LABEL_FOR_RETURN.contains((Object)ctx.dialect());
            if (declareBlockKeyword) {
                BlockImpl.scopeDeclarations(ctx, c -> {
                    BlockImpl.begin(c, topLevel);
                    this.accept1((Context<?>)c);
                    BlockImpl.end(c, topLevel);
                });
            } else if (topLevel && requireLabelForReturn && ctx.data(Tools.ExtendedDataKey.DATA_TOP_SCOPE_ROUTINE_NAME) != null) {
                ctx.data(Tools.ExtendedDataKey.DATA_TOP_LEVEL_BLOCK, new TopLevelBlock());
                ctx.scopeStart().scopeRegisterAndMark(ScopeMarker.SCOPE_TOP_LEVEL_BLOCK.beforeFirst, true);
                BlockImpl.begin(ctx, topLevel);
                BlockImpl.scopeDeclarations(ctx, c -> this.accept1((Context<?>)c));
                BlockImpl.end(ctx, topLevel);
                ctx.scopeEnd();
            } else if ((noNestedDeclarations || noBlockScope) && !topLevel) {
                BlockImpl.begin(ctx, topLevel);
                this.accept1(ctx);
                BlockImpl.end(ctx, topLevel);
            } else {
                BlockImpl.begin(ctx, topLevel);
                BlockImpl.scopeDeclarations(ctx, c -> this.accept1((Context<?>)c));
                BlockImpl.end(ctx, topLevel);
            }
            if (topLevel && language2 == LanguageContext.QUERY) {
                ctx.languageContext(language2);
            }
        } else {
            this.accept1(ctx);
        }
    }

    private final void accept1(Context<?> ctx) {
        if (this.statements.isEmpty()) {
            switch (ctx.family()) {
                case ORACLE: 
                case SQLDATAWAREHOUSE: 
                case SQLSERVER: 
                case ASE: {
                    ctx.formatSeparator().visit(NullStatement.INSTANCE);
                    break;
                }
            }
        } else {
            for (Statement statement : this.statements) {
                if (statement instanceof NullStatement && !SUPPORTS_NULL_STATEMENT.contains((Object)ctx.dialect())) continue;
                if (statement instanceof Query && !(statement instanceof Block)) {
                    ctx.languageContext(LanguageContext.QUERY, statement, c -> BlockImpl.accept2(c, s2));
                    continue;
                }
                BlockImpl.accept2(ctx, statement);
            }
        }
    }

    private static final void accept2(Context<?> ctx, Statement s2) {
        int n;
        boolean executeImmediate;
        int n2;
        ctx.formatSeparator();
        if (ctx instanceof DefaultRenderContext) {
            DefaultRenderContext d = (DefaultRenderContext)ctx;
            n2 = d.sql.length();
        } else {
            n2 = 0;
        }
        int position = n2;
        boolean bl = executeImmediate = s2 instanceof DDLQuery && REQUIRES_EXECUTE_IMMEDIATE_ON_DDL.contains((Object)ctx.dialect());
        if (executeImmediate) {
            ctx.visit(ctx.family() == SQLDialect.FIREBIRD ? Keywords.K_EXECUTE_STATEMENT : Keywords.K_EXECUTE_IMMEDIATE).sql(" '").stringLiteral(true);
        }
        if (ctx.family() == SQLDialect.H2 && s2 instanceof Query && !(s2 instanceof Block)) {
            ArrayList list = new ArrayList();
            ctx.data(STATEMENT_VARIABLES, list);
            ctx.sqlIndentStart("try (PreparedStatement s = c.prepareStatement(").sql('\"').sql(ctx.render(s2).replace("\"", "\\\"").replace("\n", "\\n\" +\n\"")).sql('\"').sqlIndentEnd(")) {").formatIndentStart().formatSeparator();
            for (int j = 0; j < list.size(); ++j) {
                ctx.sql("s.setObject(" + (j + 1) + ", ").sql(((Variable)list.get(j)).getName()).sql(");").formatSeparator();
            }
            ctx.sql("s.execute();").formatIndentEnd().formatSeparator().sql('}');
            ctx.data().remove(STATEMENT_VARIABLES);
        } else {
            ctx.visit(s2);
        }
        if (executeImmediate) {
            ctx.stringLiteral(false).sql('\'');
        }
        if (ctx instanceof DefaultRenderContext) {
            DefaultRenderContext d = (DefaultRenderContext)ctx;
            n = d.sql.length();
        } else {
            n = 0;
        }
        if (position < n) {
            BlockImpl.semicolonAfterStatement(ctx, s2);
        }
    }

    private static final void semicolonAfterStatement(Context<?> ctx, Statement s2) {
        boolean h2;
        DefaultRenderContext d;
        if (s2 instanceof Block) {
            return;
        }
        if (ctx.family() == SQLDialect.ASE) {
            return;
        }
        DefaultRenderContext r = ctx instanceof DefaultRenderContext ? (d = (DefaultRenderContext)ctx) : null;
        boolean fb = ctx.family() == SQLDialect.FIREBIRD;
        boolean bl = h2 = ctx.family() == SQLDialect.H2;
        if (r == null || r.sql.length() == 0 || fb && r.sql.length() >= 3 && BlockImpl.lastTokenIsNot(r.sql, ";") && BlockImpl.lastTokenIsNot(r.sql, "end") && BlockImpl.lastTokenIsNot(r.sql, "begin") && BlockImpl.lastTokenIsNot(r.sql, "atomic") || h2 && BlockImpl.lastTokenIsNot(r.sql, ";") && BlockImpl.lastTokenIsNot(r.sql, "}") || !fb && !h2 && BlockImpl.lastTokenIsNot(r.sql, ";")) {
            ctx.sql(';');
        }
    }

    @Pro
    private static final boolean lastTokenIsNot(StringBuilder sql, String token) {
        int i;
        int whitespace = 0;
        int length = sql.length();
        while ((i = length - whitespace - 1) >= 0 && Character.isWhitespace(sql.charAt(i))) {
            ++whitespace;
        }
        if (length < whitespace + token.length()) {
            return true;
        }
        return !sql.subSequence(length - whitespace - token.length(), length - whitespace).toString().equalsIgnoreCase(token);
    }

    private static final void begin(Context<?> ctx, boolean topLevel) {
        if (ctx.family() == SQLDialect.H2) {
            ctx.sql('{');
        } else if (ctx.family() == SQLDialect.EXASOL && !topLevel) {
            ctx.visit(Keywords.K_IF).sql(' ').visit(Keywords.K_TRUE).sql(' ').visit(Keywords.K_THEN);
        } else {
            ctx.visit(Keywords.K_BEGIN);
        }
        if (ctx.family() == SQLDialect.MARIADB && Tools.toplevel(ctx.data(), Tools.SimpleDataKey.DATA_BLOCK_NESTING)) {
            ctx.sql(' ').visit(Keywords.K_NOT).sql(' ').visit(Keywords.K_ATOMIC);
        } else if (ctx.family() == SQLDialect.HSQLDB) {
            ctx.sql(' ').visit(Keywords.K_ATOMIC);
        }
        ctx.formatIndentStart();
    }

    private static final void end(Context<?> ctx, boolean topLevel) {
        ctx.formatIndentEnd().formatSeparator();
        if (ctx.family() == SQLDialect.H2) {
            ctx.sql('}');
        } else if (ctx.family() == SQLDialect.EXASOL && !topLevel) {
            ctx.visit(Keywords.K_END).sql(' ').visit(Keywords.K_IF);
        } else {
            ctx.visit(Keywords.K_END);
        }
        switch (ctx.family()) {
            case FIREBIRD: 
            case H2: 
            case ASE: {
                break;
            }
            case DB2: {
                if (Tools.toplevel(ctx.data(), Tools.SimpleDataKey.DATA_BLOCK_NESTING)) break;
                ctx.sql(';');
                break;
            }
            default: {
                ctx.sql(';');
            }
        }
    }

    static final String variableType(Variable<?> variable) {
        Class type = variable.getDataType().getType();
        if (JAVA_LANG == type.getPackage()) {
            return type.getSimpleName();
        }
        return type.getName();
    }

    @Override
    public final QOM.UnmodifiableList<? extends Statement> $statements() {
        return QOM.unmodifiable(this.statements);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$statements());
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.statements, s2 -> new BlockImpl(this.configuration(), (Collection<? extends Statement>)s2, this.alwaysWrapInBeginEnd), replacer);
    }

    @Pro
    static class ScopeDeclarations
    implements ScopeMarker.ScopeContent {
        final Map<Variable<?>, Statement> variables = new LinkedHashMap();
        final boolean anonymousBlock;
        final boolean routine;
        final boolean trigger;
        final int scopeLevel;

        ScopeDeclarations(boolean anonymousBlock, boolean routine, boolean trigger, int scopeLevel) {
            this.anonymousBlock = anonymousBlock;
            this.routine = routine;
            this.trigger = trigger;
            this.scopeLevel = scopeLevel;
        }

        @Override
        public boolean isEmpty() {
            return this.variables.isEmpty();
        }

        public String toString() {
            return this.variables.toString();
        }
    }

    @Pro
    static class TopLevelBlock
    implements ScopeMarker.ScopeContent {
        Name label;

        TopLevelBlock() {
        }

        @Override
        public boolean isEmpty() {
            return this.label == null;
        }

        public String toString() {
            return this.label.toString();
        }
    }
}

