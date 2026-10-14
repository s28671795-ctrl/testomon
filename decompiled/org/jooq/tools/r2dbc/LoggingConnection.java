/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.tools.r2dbc;

import io.r2dbc.spi.Batch;
import io.r2dbc.spi.Connection;
import io.r2dbc.spi.IsolationLevel;
import io.r2dbc.spi.Statement;
import io.r2dbc.spi.TransactionDefinition;
import io.r2dbc.spi.ValidationDepth;
import java.time.Duration;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.r2dbc.DefaultConnection;
import org.jooq.tools.r2dbc.LoggingBatch;
import org.jooq.tools.r2dbc.LoggingStatement;
import org.reactivestreams.Publisher;

public class LoggingConnection
extends DefaultConnection {
    private static final JooqLogger log = JooqLogger.getLogger(LoggingConnection.class);

    public LoggingConnection(Connection delegate) {
        super(delegate);
    }

    @Override
    public Publisher<Void> beginTransaction() {
        return s2 -> {
            if (log.isDebugEnabled()) {
                log.debug("Connection::beginTransaction");
            }
            this.getDelegate().beginTransaction().subscribe(s2);
        };
    }

    @Override
    public Publisher<Void> beginTransaction(TransactionDefinition definition) {
        return s2 -> {
            if (log.isDebugEnabled()) {
                log.debug((Object)"Connection::beginTransaction", definition);
            }
            this.getDelegate().beginTransaction(definition).subscribe(s2);
        };
    }

    @Override
    public Publisher<Void> close() {
        return s2 -> {
            if (log.isDebugEnabled()) {
                log.debug("Connection::close");
            }
            this.getDelegate().close().subscribe(s2);
        };
    }

    @Override
    public Publisher<Void> commitTransaction() {
        return s2 -> {
            if (log.isDebugEnabled()) {
                log.debug("Connection::commitTransaction");
            }
            this.getDelegate().commitTransaction().subscribe(s2);
        };
    }

    @Override
    public Batch createBatch() {
        return new LoggingBatch(this.getDelegate().createBatch());
    }

    @Override
    public Publisher<Void> createSavepoint(String name) {
        return s2 -> {
            if (log.isDebugEnabled()) {
                log.debug((Object)"Connection::createSavepoint", name);
            }
            this.getDelegate().createSavepoint(name).subscribe(s2);
        };
    }

    @Override
    public Statement createStatement(String sql) {
        if (log.isDebugEnabled()) {
            log.debug((Object)"Connection::createStatement", sql);
        }
        return new LoggingStatement(this.getDelegate().createStatement(sql));
    }

    @Override
    public Publisher<Void> releaseSavepoint(String name) {
        return s2 -> {
            if (log.isDebugEnabled()) {
                log.debug((Object)"Connection::releaseSavepoint", name);
            }
            this.getDelegate().releaseSavepoint(name).subscribe(s2);
        };
    }

    @Override
    public Publisher<Void> rollbackTransaction() {
        return s2 -> {
            if (log.isDebugEnabled()) {
                log.debug("Connection::rollbackTransaction");
            }
            this.getDelegate().rollbackTransaction().subscribe(s2);
        };
    }

    @Override
    public Publisher<Void> rollbackTransactionToSavepoint(String name) {
        return s2 -> {
            if (log.isDebugEnabled()) {
                log.debug((Object)"Connection::rollbackTransactionToSavepoint", name);
            }
            this.getDelegate().rollbackTransactionToSavepoint(name).subscribe(s2);
        };
    }

    @Override
    public Publisher<Void> setAutoCommit(boolean autoCommit) {
        return s2 -> {
            if (log.isDebugEnabled()) {
                log.debug((Object)"Connection::setAutoCommit", autoCommit);
            }
            this.getDelegate().setAutoCommit(autoCommit).subscribe(s2);
        };
    }

    @Override
    public Publisher<Void> setLockWaitTimeout(Duration timeout2) {
        return s2 -> {
            if (log.isDebugEnabled()) {
                log.debug((Object)"Connection::setLockWaitTimeout", timeout2);
            }
            this.getDelegate().setLockWaitTimeout(timeout2).subscribe(s2);
        };
    }

    @Override
    public Publisher<Void> setStatementTimeout(Duration timeout2) {
        return s2 -> {
            if (log.isDebugEnabled()) {
                log.debug((Object)"Connection::setStatementTimeout", timeout2);
            }
            this.getDelegate().setStatementTimeout(timeout2).subscribe(s2);
        };
    }

    @Override
    public Publisher<Void> setTransactionIsolationLevel(IsolationLevel isolationLevel) {
        return s2 -> {
            if (log.isDebugEnabled()) {
                log.debug((Object)"Connection::setTransactionIsolationLevel", isolationLevel);
            }
            this.getDelegate().setTransactionIsolationLevel(isolationLevel).subscribe(s2);
        };
    }

    @Override
    public Publisher<Boolean> validate(ValidationDepth depth) {
        return s2 -> {
            if (log.isDebugEnabled()) {
                log.debug((Object)"Connection::validate", (Object)depth);
            }
            this.getDelegate().validate(depth).subscribe(s2);
        };
    }
}

