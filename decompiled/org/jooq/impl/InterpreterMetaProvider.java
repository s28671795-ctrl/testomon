/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.io.Reader;
import java.util.Scanner;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Meta;
import org.jooq.MetaProvider;
import org.jooq.Query;
import org.jooq.Source;
import org.jooq.impl.DSL;
import org.jooq.impl.Interpreter;
import org.jooq.impl.ParserException;
import org.jooq.impl.Tools;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.jdbc.JDBCUtils;

final class InterpreterMetaProvider
implements MetaProvider {
    private static final JooqLogger log = JooqLogger.getLogger(InterpreterMetaProvider.class);
    private final Configuration configuration;
    private final Source[] sources;
    private final Query[] queries;

    public InterpreterMetaProvider(Configuration configuration, Source ... sources) {
        this.configuration = Tools.configuration(configuration);
        this.sources = sources;
        this.queries = null;
    }

    public InterpreterMetaProvider(Configuration configuration, Query ... queries) {
        this.configuration = Tools.configuration(configuration);
        this.sources = null;
        this.queries = queries;
    }

    @Override
    public Meta provide() {
        Interpreter interpreter = new Interpreter(this.configuration);
        Configuration localConfiguration = this.configuration.derive();
        DSLContext ctx = DSL.using(localConfiguration);
        if (this.sources != null) {
            for (Source source2 : this.sources) {
                this.loadSource(ctx, source2, interpreter);
            }
        } else {
            for (Query query : this.queries) {
                interpreter.accept(query);
            }
        }
        return interpreter.meta();
    }

    private final void loadSource(DSLContext ctx, Source source2, Interpreter interpreter) {
        Reader reader = null;
        try {
            reader = source2.reader();
            Scanner s2 = new Scanner(reader).useDelimiter("\\A");
            for (Query query : ctx.parser().parse(s2.hasNext() ? s2.next() : "")) {
                interpreter.accept(query);
            }
        }
        catch (ParserException e) {
            try {
                log.error((Object)("An exception occurred while parsing a DDL script: " + e.getMessage() + ". Please report this error to https://jooq.org/bug"), e);
                throw e;
            }
            catch (Throwable throwable) {
                JDBCUtils.safeClose(reader);
                throw throwable;
            }
        }
        JDBCUtils.safeClose(reader);
    }
}

