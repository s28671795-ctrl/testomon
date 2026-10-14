/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.sql.PreparedStatement;
import java.text.DecimalFormat;
import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.BindContext;
import org.jooq.Clause;
import org.jooq.Condition;
import org.jooq.ExecuteScope;
import org.jooq.Field;
import org.jooq.LanguageContext;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.RenderContext;
import org.jooq.conf.ParamType;
import org.jooq.exception.DataAccessException;

public interface Context<C extends Context<C>>
extends ExecuteScope {
    @NotNull
    public C visit(QueryPart var1) throws DataAccessException;

    @NotNull
    public C visit(Condition var1) throws DataAccessException;

    @NotNull
    public C visit(Field<?> var1) throws DataAccessException;

    @NotNull
    public C visitSubquery(QueryPart var1) throws DataAccessException;

    @NotNull
    public C start(Clause var1);

    @NotNull
    public C end(Clause var1);

    @NotNull
    public C data(Object var1, Object var2, Consumer<? super C> var3);

    public boolean declareFields();

    @NotNull
    public C declareFields(boolean var1);

    @NotNull
    public C declareFields(boolean var1, Consumer<? super C> var2);

    public boolean declareTables();

    @NotNull
    public C declareTables(boolean var1);

    @NotNull
    public C declareTables(boolean var1, Consumer<? super C> var2);

    public boolean declareAliases();

    @NotNull
    public C declareAliases(boolean var1);

    @NotNull
    public C declareAliases(boolean var1, Consumer<? super C> var2);

    public boolean declareWindows();

    @NotNull
    public C declareWindows(boolean var1);

    @NotNull
    public C declareWindows(boolean var1, Consumer<? super C> var2);

    @Pro
    public boolean declareParameters();

    @Pro
    @NotNull
    public C declareParameters(boolean var1);

    @Pro
    @NotNull
    public C declareParameters(boolean var1, Consumer<? super C> var2);

    public boolean declareCTE();

    @NotNull
    public C declareCTE(boolean var1);

    @NotNull
    public C declareCTE(boolean var1, Consumer<? super C> var2);

    @Nullable
    public QueryPart topLevel();

    @NotNull
    public C topLevel(QueryPart var1);

    @Nullable
    public QueryPart topLevelForLanguageContext();

    @NotNull
    public C topLevelForLanguageContext(QueryPart var1);

    public boolean subquery();

    @NotNull
    public C subquery(boolean var1);

    public boolean derivedTableSubquery();

    @NotNull
    public C derivedTableSubquery(boolean var1);

    public boolean setOperationSubquery();

    @NotNull
    public C setOperationSubquery(boolean var1);

    public boolean predicandSubquery();

    @NotNull
    public C predicandSubquery(boolean var1);

    public int subqueryLevel();

    public int scopeLevel();

    @NotNull
    public C scopeStart();

    @NotNull
    public C scopeMarkStart(QueryPart var1);

    @NotNull
    public C scopeRegister(QueryPart var1);

    @NotNull
    public C scopeRegisterAndMark(QueryPart var1, boolean var2);

    @NotNull
    public C scopeRegister(QueryPart var1, boolean var2);

    @NotNull
    public C scopeRegister(QueryPart var1, boolean var2, QueryPart var3);

    @NotNull
    public QueryPart scopeMapping(QueryPart var1);

    @NotNull
    public C scopeMarkEnd(QueryPart var1);

    @NotNull
    public C scopeEnd();

    public boolean stringLiteral();

    @NotNull
    public C stringLiteral(boolean var1);

    public int nextIndex();

    public int peekIndex();

    @NotNull
    public C skipUpdateCount();

    @NotNull
    public C skipUpdateCounts(int var1);

    public int skipUpdateCounts();

    @Nullable
    public PreparedStatement statement();

    @NotNull
    public BindContext bindValue(Object var1, Field<?> var2) throws DataAccessException;

    @NotNull
    public String peekAlias();

    @NotNull
    public String nextAlias();

    @NotNull
    public String render();

    @NotNull
    public String render(QueryPart var1);

    @Deprecated(forRemoval=true, since="3.10")
    @NotNull
    public C keyword(String var1);

    @NotNull
    public C sql(String var1);

    @NotNull
    public C sql(String var1, boolean var2);

    @NotNull
    public C sqlIndentStart(String var1);

    @NotNull
    public C sqlIndentEnd(String var1);

    @NotNull
    public C sqlIndentStart();

    @NotNull
    public C sqlIndentEnd();

    @NotNull
    public C sql(char var1);

    @NotNull
    public C sqlIndentStart(char var1);

    @NotNull
    public C sqlIndentEnd(char var1);

    @NotNull
    public C sql(int var1);

    @NotNull
    public C sql(long var1);

    public DecimalFormat floatFormat();

    @NotNull
    public C sql(float var1);

    public DecimalFormat doubleFormat();

    @NotNull
    public C sql(double var1);

    @NotNull
    public C format(boolean var1);

    public boolean format();

    @NotNull
    public C formatNewLine();

    @NotNull
    public C formatNewLineAfterPrintMargin();

    @NotNull
    public C formatSeparator();

    @NotNull
    public C separatorRequired(boolean var1);

    public boolean separatorRequired();

    @NotNull
    public C formatIndentStart();

    @NotNull
    public C formatIndentStart(int var1);

    @Deprecated(forRemoval=true, since="3.14")
    @NotNull
    public C formatIndentLockStart();

    @NotNull
    public C formatIndentEnd();

    @NotNull
    public C formatIndentEnd(int var1);

    @Deprecated(forRemoval=true, since="3.14")
    @NotNull
    public C formatIndentLockEnd();

    @NotNull
    public C formatPrintMargin(int var1);

    @Deprecated(forRemoval=true, since="3.10")
    @NotNull
    public C literal(String var1);

    public boolean quote();

    @NotNull
    public C quote(boolean var1);

    @NotNull
    public C quote(boolean var1, Consumer<? super C> var2);

    public boolean qualify();

    @NotNull
    public C qualify(boolean var1);

    @NotNull
    public C qualify(boolean var1, Consumer<? super C> var2);

    public boolean qualifySchema();

    @NotNull
    public C qualifySchema(boolean var1);

    @NotNull
    public C qualifySchema(boolean var1, Consumer<? super C> var2);

    public boolean qualifyCatalog();

    @NotNull
    public C qualifyCatalog(boolean var1);

    @NotNull
    public C qualifyCatalog(boolean var1, Consumer<? super C> var2);

    @NotNull
    public ParamType paramType();

    @NotNull
    public C paramType(ParamType var1);

    @NotNull
    public C visit(QueryPart var1, ParamType var2);

    @NotNull
    public C paramTypeIf(ParamType var1, boolean var2);

    @NotNull
    public C paramType(ParamType var1, Consumer<? super C> var2);

    @NotNull
    public C paramTypeIf(ParamType var1, boolean var2, Consumer<? super C> var3);

    @NotNull
    public LanguageContext languageContext();

    @NotNull
    public C languageContext(LanguageContext var1);

    @NotNull
    public C languageContext(LanguageContext var1, Consumer<? super C> var2);

    @NotNull
    public C languageContext(LanguageContext var1, QueryPart var2, Consumer<? super C> var3);

    @NotNull
    public C languageContextIf(LanguageContext var1, boolean var2);

    @NotNull
    public RenderContext.CastMode castMode();

    @NotNull
    public C castMode(RenderContext.CastMode var1);

    @NotNull
    public C castMode(RenderContext.CastMode var1, Consumer<? super C> var2);

    @NotNull
    public C castModeIf(RenderContext.CastMode var1, boolean var2);
}

