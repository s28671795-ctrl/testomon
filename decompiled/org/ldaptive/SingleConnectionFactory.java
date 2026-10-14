/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.ldaptive.Connection;
import org.ldaptive.ConnectionConfig;
import org.ldaptive.DefaultConnectionFactory;
import org.ldaptive.LdapException;
import org.ldaptive.LdapUtils;
import org.ldaptive.transport.Transport;
import org.ldaptive.transport.TransportFactory;

public class SingleConnectionFactory
extends DefaultConnectionFactory {
    private Connection connection;
    private Connection proxy;
    private boolean initialized;
    private boolean failFastInitialize = true;
    private boolean nonBlockingInitialize;
    private ExecutorService initializeExecutor;

    public SingleConnectionFactory() {
        super(TransportFactory.getTransport(SingleConnectionFactory.class));
    }

    public SingleConnectionFactory(Transport t2) {
        super(t2);
    }

    public SingleConnectionFactory(String ldapUrl) {
        super(ldapUrl, TransportFactory.getTransport(SingleConnectionFactory.class));
    }

    public SingleConnectionFactory(String ldapUrl, Transport t2) {
        super(ldapUrl, t2);
    }

    public SingleConnectionFactory(ConnectionConfig cc) {
        super(cc, TransportFactory.getTransport(SingleConnectionFactory.class));
    }

    public SingleConnectionFactory(ConnectionConfig cc, Transport t2) {
        super(cc, t2);
    }

    public boolean getFailFastInitialize() {
        return this.failFastInitialize;
    }

    public void setFailFastInitialize(boolean b) {
        this.failFastInitialize = b;
    }

    public boolean getNonBlockingInitialize() {
        return this.nonBlockingInitialize;
    }

    public void setNonBlockingInitialize(boolean b) {
        this.nonBlockingInitialize = b;
    }

    public boolean isInitialized() {
        return this.initialized;
    }

    public synchronized void initialize() throws LdapException {
        if (this.initialized) {
            throw new IllegalStateException("Connection factory is already initialized");
        }
        if (this.nonBlockingInitialize) {
            if (this.initializeExecutor == null) {
                this.initializeExecutor = Executors.newCachedThreadPool(r -> {
                    Thread t2 = new Thread(r, this.getClass().getSimpleName() + "@" + this.hashCode());
                    t2.setDaemon(true);
                    return t2;
                });
            }
            this.initializeExecutor.execute(() -> {
                try {
                    this.initializeInternal();
                }
                catch (LdapException e) {
                    this.logger.debug("Execution of initialize failed", e);
                }
            });
        } else {
            this.initializeInternal();
        }
    }

    private synchronized void initializeInternal() throws LdapException {
        if (!this.initialized) {
            try {
                this.connection = super.getConnection();
                this.connection.open();
                this.proxy = (Connection)Proxy.newProxyInstance(Connection.class.getClassLoader(), new Class[]{Connection.class}, new ConnectionProxy(this.connection));
                this.initialized = true;
                this.logger.info("Factory initialized {}", (Object)this);
            }
            catch (LdapException e) {
                if (this.failFastInitialize) {
                    throw e;
                }
                this.logger.warn("Could not initialize connection factory", e);
            }
        } else {
            this.logger.debug("Factory already initialized");
        }
    }

    @Override
    public Connection getConnection() {
        if (!this.initialized) {
            throw new IllegalStateException("Connection factory is not initialized");
        }
        return this.proxy;
    }

    @Override
    public synchronized void close() {
        if (this.connection != null) {
            this.connection.close();
        }
        if (this.initializeExecutor != null) {
            try {
                this.initializeExecutor.shutdown();
            }
            finally {
                this.initializeExecutor = null;
            }
        }
        super.close();
        this.initialized = false;
    }

    @Override
    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "transport=" + this.getTransport() + ", " + "config=" + this.getConnectionConfig() + ", " + "failFastInitialize=" + this.failFastInitialize + ", " + "nonBlockingInitialize=" + this.nonBlockingInitialize + ", " + "initialized=" + this.initialized + "]";
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(Transport t2) {
        return new Builder(t2);
    }

    public static class Builder
    extends DefaultConnectionFactory.Builder {
        private final SingleConnectionFactory object;

        protected Builder() {
            this.object = new SingleConnectionFactory();
        }

        protected Builder(Transport transport) {
            this.object = new SingleConnectionFactory(transport);
        }

        @Override
        public Builder config(ConnectionConfig cc) {
            this.object.setConnectionConfig(cc);
            return this;
        }

        public Builder failFastInitialize(boolean failFast) {
            this.object.setFailFastInitialize(failFast);
            return this;
        }

        public Builder nonBlockingInitialize(boolean nonBlocking) {
            this.object.setNonBlockingInitialize(nonBlocking);
            return this;
        }

        @Override
        public SingleConnectionFactory build() {
            return this.object;
        }
    }

    protected static class ConnectionProxy
    implements InvocationHandler {
        private static final int HASH_CODE_SEED = 509;
        private final Connection conn;

        public ConnectionProxy(Connection c) {
            this.conn = c;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (o instanceof ConnectionProxy) {
                ConnectionProxy v = (ConnectionProxy)o;
                return LdapUtils.areEqual(this.conn, v.conn);
            }
            return false;
        }

        public int hashCode() {
            return LdapUtils.computeHashCode(509, this.conn);
        }

        @Override
        public Object invoke(Object proxy, Method method2, Object[] args2) throws Throwable {
            Object retValue = null;
            if (!"open".equals(method2.getName()) && !"close".equals(method2.getName())) {
                try {
                    retValue = method2.invoke((Object)this.conn, args2);
                }
                catch (InvocationTargetException e) {
                    throw e.getTargetException();
                }
            }
            return retValue;
        }
    }
}

