/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.Connection;
import java.util.ArrayDeque;
import java.util.Deque;
import org.jooq.Configuration;
import org.jooq.ConnectionProvider;
import org.jooq.TransactionContext;
import org.jooq.TransactionProvider;
import org.jooq.impl.DefaultConnectionProvider;
import org.jooq.impl.DefaultTransactionProvider;
import org.jooq.impl.Tools;

public class ThreadLocalTransactionProvider
implements TransactionProvider {
    final DefaultTransactionProvider delegateTransactionProvider;
    final ThreadLocalConnectionProvider localConnectionProvider;
    final ThreadLocal<Connection> localTxConnection;
    final ThreadLocal<Deque<Configuration>> localConfigurations;

    public ThreadLocalTransactionProvider(ConnectionProvider connectionProvider) {
        this(connectionProvider, true);
    }

    public ThreadLocalTransactionProvider(ConnectionProvider connectionProvider, boolean nested) {
        this.localConnectionProvider = new ThreadLocalConnectionProvider(connectionProvider);
        this.delegateTransactionProvider = new DefaultTransactionProvider(this.localConnectionProvider, nested);
        this.localConfigurations = new ThreadLocal();
        this.localTxConnection = new ThreadLocal();
    }

    @Override
    public void begin(TransactionContext ctx) {
        this.delegateTransactionProvider.begin(ctx);
        this.configurations().push(ctx.configuration());
        if (this.delegateTransactionProvider.nestingLevel(ctx.configuration()) == 1) {
            this.localTxConnection.set(((DefaultConnectionProvider)ctx.configuration().data((Object)Tools.SimpleDataKey.DATA_DEFAULT_TRANSACTION_PROVIDER_CONNECTION)).connection);
        }
    }

    @Override
    public void commit(TransactionContext ctx) {
        if (this.delegateTransactionProvider.nestingLevel(ctx.configuration()) == 1) {
            this.localTxConnection.remove();
        }
        this.configurations().pop();
        this.delegateTransactionProvider.commit(ctx);
    }

    @Override
    public void rollback(TransactionContext ctx) {
        if (this.delegateTransactionProvider.nestingLevel(ctx.configuration()) == 1) {
            this.localTxConnection.remove();
        }
        this.configurations().pop();
        this.delegateTransactionProvider.rollback(ctx);
    }

    Configuration configuration(Configuration fallback) {
        Deque<Configuration> configurations = this.configurations();
        return configurations.isEmpty() ? fallback : configurations.peek();
    }

    private Deque<Configuration> configurations() {
        Deque<Configuration> result = this.localConfigurations.get();
        if (result == null) {
            result = new ArrayDeque<Configuration>();
            this.localConfigurations.set(result);
        }
        return result;
    }

    final class ThreadLocalConnectionProvider
    implements ConnectionProvider {
        final ConnectionProvider delegateConnectionProvider;

        public ThreadLocalConnectionProvider(ConnectionProvider delegate) {
            this.delegateConnectionProvider = delegate;
        }

        @Override
        public final Connection acquire() {
            Connection local = ThreadLocalTransactionProvider.this.localTxConnection.get();
            if (local == null) {
                return this.delegateConnectionProvider.acquire();
            }
            return local;
        }

        @Override
        public final void release(Connection connection) {
            Connection local = ThreadLocalTransactionProvider.this.localTxConnection.get();
            if (local == null) {
                this.delegateConnectionProvider.release(connection);
            } else if (local != connection) {
                throw new IllegalStateException("A different connection was released than the thread-bound one that was expected");
            }
        }
    }
}

