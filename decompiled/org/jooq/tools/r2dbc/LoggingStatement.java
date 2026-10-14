/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.tools.r2dbc;

import io.r2dbc.spi.Result;
import io.r2dbc.spi.Statement;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.r2dbc.DefaultStatement;
import org.reactivestreams.Publisher;

public class LoggingStatement
extends DefaultStatement {
    private static final JooqLogger log = JooqLogger.getLogger(LoggingStatement.class);

    public LoggingStatement(Statement delegate) {
        super(delegate);
    }

    @Override
    public Statement add() {
        if (log.isDebugEnabled()) {
            log.debug("Statement::add");
        }
        this.getDelegate().add();
        return this;
    }

    @Override
    public Statement bind(int index, Object value) {
        if (log.isTraceEnabled()) {
            log.trace((Object)"Statement::bind", "index = " + index + ", value = " + String.valueOf(value));
        }
        this.getDelegate().bind(index, value);
        return this;
    }

    @Override
    public Statement bind(String name, Object value) {
        if (log.isTraceEnabled()) {
            log.trace((Object)"Statement::bind", "name = " + name + ", value = " + String.valueOf(value));
        }
        this.getDelegate().bind(name, value);
        return this;
    }

    @Override
    public Statement bindNull(int index, Class<?> type) {
        if (log.isTraceEnabled()) {
            log.trace((Object)"Statement::bindNull", "index = " + index + ", type = " + String.valueOf(type));
        }
        this.getDelegate().bindNull(index, type);
        return this;
    }

    @Override
    public Statement bindNull(String name, Class<?> type) {
        if (log.isTraceEnabled()) {
            log.trace((Object)"Statement::bindNull", "name = " + name + ", type = " + String.valueOf(type));
        }
        this.getDelegate().bindNull(name, type);
        return this;
    }

    @Override
    public Publisher<? extends Result> execute() {
        return s2 -> {
            if (log.isDebugEnabled()) {
                log.debug("Statement::execute");
            }
            this.getDelegate().execute().subscribe(s2);
        };
    }
}

