/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Block;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.PlainSQL;
import org.jooq.Pro;
import org.jooq.Queries;
import org.jooq.Query;
import org.jooq.ResultQuery;
import org.jooq.Row;
import org.jooq.Select;
import org.jooq.Statement;
import org.jooq.Support;
import org.jooq.Table;
import org.jooq.impl.ParserException;

public interface Parser {
    @Support
    @PlainSQL
    @NotNull
    public Queries parse(String var1) throws ParserException;

    @Support
    @PlainSQL
    @NotNull
    public Queries parse(String var1, Object ... var2) throws ParserException;

    @Support
    @PlainSQL
    @Nullable
    public Query parseQuery(String var1) throws ParserException;

    @Support
    @PlainSQL
    @Nullable
    public Query parseQuery(String var1, Object ... var2) throws ParserException;

    @Support
    @PlainSQL
    @Nullable
    public Statement parseStatement(String var1) throws ParserException;

    @Support
    @PlainSQL
    @Nullable
    public Statement parseStatement(String var1, Object ... var2) throws ParserException;

    @Support
    @PlainSQL
    @NotNull
    @Pro
    public Block parseStatements(String var1) throws ParserException;

    @Support
    @PlainSQL
    @NotNull
    @Pro
    public Block parseStatements(String var1, Object ... var2) throws ParserException;

    @Support
    @PlainSQL
    @Nullable
    public ResultQuery<?> parseResultQuery(String var1) throws ParserException;

    @Support
    @PlainSQL
    @Nullable
    public ResultQuery<?> parseResultQuery(String var1, Object ... var2) throws ParserException;

    @Support
    @PlainSQL
    @Nullable
    public Select<?> parseSelect(String var1) throws ParserException;

    @Support
    @PlainSQL
    @Nullable
    public Select<?> parseSelect(String var1, Object ... var2) throws ParserException;

    @Support
    @PlainSQL
    @NotNull
    public Table<?> parseTable(String var1) throws ParserException;

    @Support
    @PlainSQL
    @NotNull
    public Table<?> parseTable(String var1, Object ... var2) throws ParserException;

    @Support
    @PlainSQL
    @NotNull
    public Field<?> parseField(String var1) throws ParserException;

    @Support
    @PlainSQL
    @NotNull
    public Field<?> parseField(String var1, Object ... var2) throws ParserException;

    @Support
    @PlainSQL
    @NotNull
    public Row parseRow(String var1) throws ParserException;

    @Support
    @PlainSQL
    @NotNull
    public Row parseRow(String var1, Object ... var2) throws ParserException;

    @Support
    @PlainSQL
    @NotNull
    public Condition parseCondition(String var1) throws ParserException;

    @Support
    @PlainSQL
    @NotNull
    public Condition parseCondition(String var1, Object ... var2) throws ParserException;

    @Support
    @PlainSQL
    @NotNull
    public Name parseName(String var1) throws ParserException;

    @Support
    @PlainSQL
    @NotNull
    public Name parseName(String var1, Object ... var2) throws ParserException;
}

