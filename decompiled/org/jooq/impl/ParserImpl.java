/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Block;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Meta;
import org.jooq.Name;
import org.jooq.Parser;
import org.jooq.Pro;
import org.jooq.Queries;
import org.jooq.Query;
import org.jooq.ResultQuery;
import org.jooq.Row;
import org.jooq.Select;
import org.jooq.Statement;
import org.jooq.Table;
import org.jooq.conf.ParseWithMetaLookups;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultParseContext;
import org.jooq.impl.Tools;

final class ParserImpl
implements Parser {
    private final DSLContext dsl;
    private final ParseWithMetaLookups metaLookups;
    private final Meta meta;

    ParserImpl(Configuration configuration) {
        this.dsl = DSL.using(configuration);
        this.metaLookups = configuration.settings().getParseWithMetaLookups();
        this.meta = this.metaLookups == ParseWithMetaLookups.IGNORE_ON_FAILURE || this.metaLookups == ParseWithMetaLookups.THROW_ON_FAILURE ? this.dsl.meta() : null;
    }

    private final DefaultParseContext ctx(String sql, Object ... bindings) {
        return new DefaultParseContext(this.dsl, this.meta, this.metaLookups, sql, bindings);
    }

    @Override
    public final Queries parse(String sql) {
        return this.parse(sql, Tools.EMPTY_OBJECT);
    }

    @Override
    public final Queries parse(String sql, Object ... bindings) {
        return this.ctx(sql, bindings).parse();
    }

    @Override
    public final Query parseQuery(String sql) {
        return this.parseQuery(sql, Tools.EMPTY_OBJECT);
    }

    @Override
    public final Query parseQuery(String sql, Object ... bindings) {
        return this.ctx(sql, bindings).parseQuery0();
    }

    @Override
    public final Statement parseStatement(String sql) {
        return this.parseStatement(sql, Tools.EMPTY_OBJECT);
    }

    @Override
    public final Statement parseStatement(String sql, Object ... bindings) {
        return this.ctx(sql, bindings).parseStatementAndSemicolonIf();
    }

    @Override
    @Pro
    public final Block parseStatements(String sql) {
        return this.parseStatements(sql, Tools.EMPTY_OBJECT);
    }

    @Override
    @Pro
    public final Block parseStatements(String sql, Object ... bindings) {
        return this.ctx(sql, bindings).parseStatements0();
    }

    @Override
    public final ResultQuery<?> parseResultQuery(String sql) {
        return this.parseResultQuery(sql, Tools.EMPTY_OBJECT);
    }

    @Override
    public final ResultQuery<?> parseResultQuery(String sql, Object ... bindings) {
        return this.ctx(sql, bindings).parseResultQuery0();
    }

    @Override
    public final Select<?> parseSelect(String sql) {
        return this.parseSelect(sql, Tools.EMPTY_OBJECT);
    }

    @Override
    public final Select<?> parseSelect(String sql, Object ... bindings) {
        return this.ctx(sql, bindings).parseSelect0();
    }

    @Override
    public final Table<?> parseTable(String sql) {
        return this.parseTable(sql, Tools.EMPTY_OBJECT);
    }

    @Override
    public final Table<?> parseTable(String sql, Object ... bindings) {
        return this.ctx(sql, bindings).parseTable0();
    }

    @Override
    public final Field<?> parseField(String sql) {
        return this.parseField(sql, Tools.EMPTY_OBJECT);
    }

    @Override
    public final Field<?> parseField(String sql, Object ... bindings) {
        return this.ctx(sql, bindings).parseField0();
    }

    @Override
    public final Row parseRow(String sql) {
        return this.parseRow(sql, Tools.EMPTY_OBJECT);
    }

    @Override
    public final Row parseRow(String sql, Object ... bindings) {
        return this.ctx(sql, bindings).parseRow0();
    }

    @Override
    public final Condition parseCondition(String sql) {
        return this.parseCondition(sql, Tools.EMPTY_OBJECT);
    }

    @Override
    public final Condition parseCondition(String sql, Object ... bindings) {
        return this.ctx(sql, bindings).parseCondition0();
    }

    @Override
    public final Name parseName(String sql) {
        return this.parseName(sql, Tools.EMPTY_OBJECT);
    }

    @Override
    public final Name parseName(String sql, Object ... bindings) {
        return this.ctx(sql, bindings).parseName0();
    }
}

