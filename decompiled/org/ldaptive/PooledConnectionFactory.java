/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import java.time.Duration;
import java.time.Instant;
import java.util.function.BiPredicate;
import org.ldaptive.Connection;
import org.ldaptive.ConnectionConfig;
import org.ldaptive.ConnectionFactory;
import org.ldaptive.ConnectionValidator;
import org.ldaptive.DefaultConnectionFactory;
import org.ldaptive.pool.BlockingConnectionPool;
import org.ldaptive.pool.ConnectionActivator;
import org.ldaptive.pool.ConnectionPassivator;
import org.ldaptive.pool.PoolException;
import org.ldaptive.pool.PruneStrategy;
import org.ldaptive.pool.ValidationException;
import org.ldaptive.pool.ValidationExceptionHandler;
import org.ldaptive.transport.Transport;
import org.ldaptive.transport.TransportFactory;

public class PooledConnectionFactory
extends BlockingConnectionPool
implements ConnectionFactory {
    private ValidationExceptionHandler validationExceptionHandler = new RetryValidationExceptionHandler();

    public PooledConnectionFactory() {
        this.setDefaultConnectionFactory(new DefaultConnectionFactory(TransportFactory.getTransport(PooledConnectionFactory.class)));
    }

    public PooledConnectionFactory(Transport t2) {
        this.setDefaultConnectionFactory(new DefaultConnectionFactory(t2));
    }

    public PooledConnectionFactory(String ldapUrl) {
        this.setDefaultConnectionFactory(new DefaultConnectionFactory(ldapUrl, TransportFactory.getTransport(PooledConnectionFactory.class)));
    }

    public PooledConnectionFactory(String ldapUrl, Transport t2) {
        this.setDefaultConnectionFactory(new DefaultConnectionFactory(ldapUrl, t2));
    }

    public PooledConnectionFactory(ConnectionConfig cc) {
        this.setDefaultConnectionFactory(new DefaultConnectionFactory(cc, TransportFactory.getTransport(PooledConnectionFactory.class)));
    }

    public PooledConnectionFactory(ConnectionConfig cc, Transport t2) {
        this.setDefaultConnectionFactory(new DefaultConnectionFactory(cc, t2));
    }

    @Override
    public ConnectionConfig getConnectionConfig() {
        return this.getDefaultConnectionFactory().getConnectionConfig();
    }

    public void setConnectionConfig(ConnectionConfig cc) {
        this.getDefaultConnectionFactory().setConnectionConfig(cc);
    }

    public ValidationExceptionHandler getValidationExceptionHandler() {
        return this.validationExceptionHandler;
    }

    public void setValidationExceptionHandler(ValidationExceptionHandler handler) {
        this.validationExceptionHandler = handler;
    }

    public Transport getTransport() {
        return this.getDefaultConnectionFactory().getTransport();
    }

    @Override
    public Connection getConnection() throws PoolException {
        try {
            return super.getConnection();
        }
        catch (ValidationException e) {
            if (this.validationExceptionHandler != null) {
                this.logger.warn("Connection could not be validated, invoking handler {}", (Object)this.validationExceptionHandler, (Object)e);
                Connection conn = (Connection)this.validationExceptionHandler.apply(e);
                if (conn != null) {
                    return conn;
                }
            } else {
                this.logger.warn("No validation exception handler is configured for {}", (Object)this);
            }
            throw e;
        }
    }

    @Override
    public void close() {
        super.close();
        this.getDefaultConnectionFactory().close();
    }

    @Override
    public String toString() {
        return "[" + super.toString() + ", " + "validationExceptionHandler=" + this.validationExceptionHandler + "]";
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(Transport t2) {
        return new Builder(t2);
    }

    public class RetryValidationExceptionHandler
    implements ValidationExceptionHandler {
        private final BiPredicate<Integer, Instant> continueCondition;

        public RetryValidationExceptionHandler() {
            this((count, time) -> count <= this$0.getMaxPoolSize() + 1 && !this$0.getBlockWaitTime().minus(Duration.between(time, Instant.now())).isNegative());
        }

        public RetryValidationExceptionHandler(BiPredicate<Integer, Instant> condition) {
            this.continueCondition = condition;
        }

        @Override
        public Connection apply(ValidationException e) {
            int count = 1;
            Instant time = Instant.now();
            while (this.continueCondition.test(count++, time)) {
                try {
                    return PooledConnectionFactory.super.getConnection();
                }
                catch (ValidationException ex) {
                    PooledConnectionFactory.this.logger.warn("Validation exception handler failed on retry {}", (Object)count, (Object)ex);
                }
                catch (Exception ex) {
                    PooledConnectionFactory.this.logger.warn("Validation exception handler failed", ex);
                    break;
                }
            }
            return null;
        }
    }

    public static class Builder {
        private final PooledConnectionFactory object;

        protected Builder() {
            this.object = new PooledConnectionFactory();
        }

        protected Builder(Transport t2) {
            this.object = new PooledConnectionFactory(t2);
        }

        public Builder config(ConnectionConfig cc) {
            this.object.setConnectionConfig(cc);
            return this;
        }

        public Builder min(int size) {
            this.object.setMinPoolSize(size);
            return this;
        }

        public Builder max(int size) {
            this.object.setMaxPoolSize(size);
            return this;
        }

        public Builder validateOnCheckIn(boolean b) {
            this.object.setValidateOnCheckIn(b);
            return this;
        }

        public Builder validateOnCheckOut(boolean b) {
            this.object.setValidateOnCheckOut(b);
            return this;
        }

        public Builder validatePeriodically(boolean b) {
            this.object.setValidatePeriodically(b);
            return this;
        }

        public Builder blockWaitTime(Duration time) {
            this.object.setBlockWaitTime(time);
            return this;
        }

        public Builder connectOnCreate(boolean connect2) {
            this.object.setConnectOnCreate(connect2);
            return this;
        }

        public Builder failFastInitialize(boolean failFast) {
            this.object.setFailFastInitialize(failFast);
            return this;
        }

        public Builder activator(ConnectionActivator activator) {
            this.object.setActivator(activator);
            return this;
        }

        public Builder passivator(ConnectionPassivator passivator) {
            this.object.setPassivator(passivator);
            return this;
        }

        public Builder validator(ConnectionValidator validator) {
            this.object.setValidator(validator);
            return this;
        }

        public Builder validationExceptionHandler(ValidationExceptionHandler handler) {
            this.object.setValidationExceptionHandler(handler);
            return this;
        }

        public Builder pruneStrategy(PruneStrategy strategy) {
            this.object.setPruneStrategy(strategy);
            return this;
        }

        public Builder name(String name) {
            this.object.setName(name);
            return this;
        }

        public PooledConnectionFactory build() {
            return this.object;
        }
    }
}

