/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Condition;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.LanguageContext;
import org.jooq.Name;
import org.jooq.SQLDialect;
import org.jooq.SQLDialectCategory;
import org.jooq.Scope;
import org.jooq.SortField;
import org.jooq.Table;
import org.jooq.impl.ParserException;

public interface ParseContext
extends Scope {
    @NotNull
    public SQLDialect parseDialect();

    @NotNull
    public SQLDialect parseFamily();

    @NotNull
    public SQLDialectCategory parseCategory();

    @NotNull
    public LanguageContext languageContext();

    public char @NotNull [] characters();

    @NotNull
    public ParseContext characters(char[] var1);

    public char character();

    public char character(int var1);

    public int position();

    public boolean position(int var1);

    public boolean peek(char var1);

    public boolean peek(String var1);

    public boolean peekKeyword(String var1);

    public boolean peekKeyword(String ... var1);

    public boolean parse(char var1) throws ParserException;

    public boolean parseIf(char var1);

    public boolean parse(String var1) throws ParserException;

    public boolean parseIf(String var1);

    public boolean parseKeyword(String var1) throws ParserException;

    public boolean parseKeywordIf(String var1);

    public boolean parseKeywordIf(String ... var1);

    public boolean parseKeyword(String ... var1) throws ParserException;

    @NotNull
    public Name parseIdentifier() throws ParserException;

    @Nullable
    public Name parseIdentifierIf();

    @NotNull
    public Name parseName() throws ParserException;

    @Nullable
    public Name parseNameIf();

    public boolean parseFunctionNameIf(String var1);

    public boolean parseFunctionNameIf(String ... var1);

    @NotNull
    public String parseStringLiteral() throws ParserException;

    @Nullable
    public String parseStringLiteralIf();

    @NotNull
    public Long parseUnsignedIntegerLiteral() throws ParserException;

    @Nullable
    public Long parseUnsignedIntegerLiteralIf();

    @NotNull
    public Long parseSignedIntegerLiteral() throws ParserException;

    @Nullable
    public Long parseSignedIntegerLiteralIf();

    @NotNull
    public DataType<?> parseDataType() throws ParserException;

    @NotNull
    public Field<?> parseField() throws ParserException;

    @NotNull
    public SortField<?> parseSortField() throws ParserException;

    @NotNull
    public Condition parseCondition() throws ParserException;

    @NotNull
    public Table<?> parseTable() throws ParserException;

    @NotNull
    public <T> List<T> parseList(String var1, Function<? super ParseContext, ? extends T> var2);

    @NotNull
    public <T> List<T> parseList(Predicate<? super ParseContext> var1, Function<? super ParseContext, ? extends T> var2);

    public <T> T parseParenthesised(Function<? super ParseContext, ? extends T> var1);

    public <T> T parseParenthesised(char var1, Function<? super ParseContext, ? extends T> var2, char var3);

    public <T> T parseParenthesised(String var1, Function<? super ParseContext, ? extends T> var2, String var3);

    @NotNull
    public ParserException exception(String var1);
}

