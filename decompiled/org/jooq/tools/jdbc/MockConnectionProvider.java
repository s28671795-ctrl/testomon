/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.tools.jdbc;

import java.sql.Connection;
import org.jooq.ConnectionProvider;
import org.jooq.tools.jdbc.MockConnection;
import org.jooq.tools.jdbc.MockDataProvider;

public class MockConnectionProvider
implements ConnectionProvider {
    private final ConnectionProvider delegate;
    private final MockDataProvider provider;

    public MockConnectionProvider(ConnectionProvider delegate, MockDataProvider provider) {
        this.delegate = delegate;
        this.provider = provider;
    }

    @Override
    public final Connection acquire() {
        return new MockConnectionWrapper(this.delegate.acquire());
    }

    @Override
    public final void release(Connection connection) {
        if (!(connection instanceof MockConnectionWrapper)) {
            throw new IllegalArgumentException("Argument connection must be a MockConnectionWrapper");
        }
        MockConnectionWrapper w = (MockConnectionWrapper)connection;
        this.delegate.release(w.connection);
    }

    private class MockConnectionWrapper
    extends MockConnection {
        final Connection connection;

        public MockConnectionWrapper(Connection connection) {
            super(MockConnectionProvider.this.provider);
            this.connection = connection;
        }
    }
}

