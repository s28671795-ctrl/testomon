/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import org.ldaptive.Connection;
import org.ldaptive.ConnectionConfig;
import org.ldaptive.ConnectionFactory;
import org.ldaptive.transport.Transport;
import org.ldaptive.transport.TransportFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultConnectionFactory
implements ConnectionFactory {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private Transport transport;
    private ConnectionConfig config;

    public DefaultConnectionFactory() {
        this(TransportFactory.getTransport(DefaultConnectionFactory.class));
    }

    public DefaultConnectionFactory(Transport t2) {
        this.transport = t2;
    }

    public DefaultConnectionFactory(String ldapUrl) {
        this(new ConnectionConfig(ldapUrl));
    }

    public DefaultConnectionFactory(String ldapUrl, Transport t2) {
        this(new ConnectionConfig(ldapUrl), t2);
    }

    public DefaultConnectionFactory(ConnectionConfig cc) {
        this(cc, TransportFactory.getTransport(DefaultConnectionFactory.class));
    }

    public DefaultConnectionFactory(ConnectionConfig cc, Transport t2) {
        this.transport = t2;
        this.setConnectionConfig(cc);
    }

    @Override
    public ConnectionConfig getConnectionConfig() {
        return this.config;
    }

    public void setConnectionConfig(ConnectionConfig cc) {
        this.config = cc;
        this.config.makeImmutable();
    }

    public Transport getTransport() {
        return this.transport;
    }

    @Override
    public Connection getConnection() {
        return this.transport.create(this.config);
    }

    @Override
    public void close() {
        this.transport.close();
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "transport=" + this.transport + ", " + "config=" + this.config + "]";
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(Transport t2) {
        return new Builder(t2);
    }

    public static class Builder {
        private final DefaultConnectionFactory object;

        protected Builder() {
            this.object = new DefaultConnectionFactory();
        }

        protected Builder(Transport t2) {
            this.object = new DefaultConnectionFactory(t2);
        }

        public Builder config(ConnectionConfig cc) {
            this.object.setConnectionConfig(cc);
            return this;
        }

        public DefaultConnectionFactory build() {
            return this.object;
        }
    }
}

