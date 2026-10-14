/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayDeque;
import java.util.Deque;
import org.jooq.Configuration;
import org.jooq.ConnectionProvider;
import org.jooq.TransactionContext;
import org.jooq.TransactionProvider;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DefaultConnectionProvider;
import org.jooq.impl.Tools;

public class DefaultTransactionProvider
implements TransactionProvider {
    private static final Savepoint IGNORED_SAVEPOINT = new DefaultSavepoint();
    private final ConnectionProvider connectionProvider;
    private final boolean nested;

    public DefaultTransactionProvider(ConnectionProvider connectionProvider) {
        this(connectionProvider, true);
    }

    public DefaultTransactionProvider(ConnectionProvider connectionProvider, boolean nested) {
        this.connectionProvider = connectionProvider;
        this.nested = nested;
    }

    public final boolean nested() {
        return this.nested;
    }

    final int nestingLevel(Configuration configuration) {
        return this.savepoints(configuration).size();
    }

    private final Deque<Savepoint> savepoints(Configuration configuration) {
        ArrayDeque savepoints = (ArrayDeque)configuration.data(Tools.SimpleDataKey.DATA_DEFAULT_TRANSACTION_PROVIDER_SAVEPOINTS);
        if (savepoints == null) {
            savepoints = new ArrayDeque();
            configuration.data(Tools.SimpleDataKey.DATA_DEFAULT_TRANSACTION_PROVIDER_SAVEPOINTS, savepoints);
        }
        return savepoints;
    }

    private final boolean autoCommit(Configuration configuration) {
        Boolean autoCommit = (Boolean)configuration.data(Tools.BooleanDataKey.DATA_DEFAULT_TRANSACTION_PROVIDER_AUTOCOMMIT);
        if (!Boolean.TRUE.equals(autoCommit)) {
            autoCommit = this.connection(configuration).getAutoCommit();
            configuration.data(Tools.BooleanDataKey.DATA_DEFAULT_TRANSACTION_PROVIDER_AUTOCOMMIT, autoCommit);
        }
        return autoCommit;
    }

    private final DefaultConnectionProvider connection(Configuration configuration) {
        DefaultConnectionProvider connectionWrapper = (DefaultConnectionProvider)configuration.data(Tools.SimpleDataKey.DATA_DEFAULT_TRANSACTION_PROVIDER_CONNECTION);
        if (connectionWrapper == null) {
            connectionWrapper = new DefaultConnectionProvider(this.connectionProvider.acquire());
            configuration.data(Tools.SimpleDataKey.DATA_DEFAULT_TRANSACTION_PROVIDER_CONNECTION, connectionWrapper);
        }
        return connectionWrapper;
    }

    @Override
    public final void begin(TransactionContext ctx) {
        Deque<Savepoint> savepoints = this.savepoints(ctx.configuration());
        boolean topLevel = savepoints.isEmpty();
        if (topLevel) {
            this.brace(ctx.configuration(), true);
        }
        savepoints.push(this.setSavepoint(ctx.configuration(), topLevel));
    }

    private final Savepoint setSavepoint(Configuration configuration, boolean topLevel) {
        if (topLevel || !this.nested()) {
            return IGNORED_SAVEPOINT;
        }
        return this.connection(configuration).setSavepoint();
    }

    @Override
    public final void commit(TransactionContext ctx) {
        Deque<Savepoint> savepoints = this.savepoints(ctx.configuration());
        Savepoint savepoint = savepoints.pop();
        if (savepoint != null && savepoint != IGNORED_SAVEPOINT) {
            try {
                this.connection(ctx.configuration()).releaseSavepoint(savepoint);
            }
            catch (DataAccessException dataAccessException) {
                // empty catch block
            }
        }
        if (savepoints.isEmpty()) {
            this.connection(ctx.configuration()).commit();
            this.brace(ctx.configuration(), false);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final void rollback(TransactionContext ctx) {
        Deque<Savepoint> savepoints = this.savepoints(ctx.configuration());
        Savepoint savepoint = null;
        if (!savepoints.isEmpty()) {
            savepoint = savepoints.pop();
        }
        try {
            if (savepoint == null) {
                this.connection(ctx.configuration()).rollback();
            } else if (savepoint == IGNORED_SAVEPOINT) {
                if (savepoints.isEmpty()) {
                    this.connection(ctx.configuration()).rollback();
                }
            } else {
                this.connection(ctx.configuration()).rollback(savepoint);
            }
        }
        finally {
            if (savepoints.isEmpty()) {
                this.brace(ctx.configuration(), false);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final void brace(Configuration configuration, boolean start2) {
        DefaultConnectionProvider connection = this.connection(configuration);
        try {
            boolean autoCommit = this.autoCommit(configuration);
            if (autoCommit) {
                connection.setAutoCommit(!start2);
            }
        }
        finally {
            if (!start2) {
                this.connectionProvider.release(connection.connection);
                configuration.data().remove(Tools.SimpleDataKey.DATA_DEFAULT_TRANSACTION_PROVIDER_CONNECTION);
            }
        }
    }

    private static class DefaultSavepoint
    implements Savepoint {
        private DefaultSavepoint() {
        }

        @Override
        public int getSavepointId() throws SQLException {
            return 0;
        }

        @Override
        public String getSavepointName() throws SQLException {
            return null;
        }
    }
}

