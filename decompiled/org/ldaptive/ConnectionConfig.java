/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import org.ldaptive.AbstractConfig;
import org.ldaptive.ActivePassiveConnectionStrategy;
import org.ldaptive.ClosedRetryMetadata;
import org.ldaptive.ConnectionInitializer;
import org.ldaptive.ConnectionStrategy;
import org.ldaptive.ConnectionValidator;
import org.ldaptive.RetryMetadata;
import org.ldaptive.ssl.SslConfig;

public class ConnectionConfig
extends AbstractConfig {
    public static final Predicate<RetryMetadata> ONE_RECONNECT_ATTEMPT = metadata -> metadata instanceof ClosedRetryMetadata && metadata.getAttempts() == 0;
    public static final Predicate<RetryMetadata> INFINITE_RECONNECT_ATTEMPTS = metadata -> {
        if (metadata instanceof ClosedRetryMetadata) {
            if (metadata.getAttempts() > 0) {
                try {
                    Thread.sleep(Duration.ofSeconds(5L).toMillis());
                }
                catch (InterruptedException interruptedException) {
                    // empty catch block
                }
            }
            return true;
        }
        return false;
    };
    public static final Predicate<RetryMetadata> INFINITE_RECONNECT_ATTEMPTS_WITH_BACKOFF = metadata -> {
        if (metadata instanceof ClosedRetryMetadata) {
            if (metadata.getAttempts() > 0) {
                try {
                    Thread.sleep(Duration.ofSeconds(5L).multipliedBy(metadata.getAttempts()).toMillis());
                }
                catch (InterruptedException interruptedException) {
                    // empty catch block
                }
            }
            return true;
        }
        return false;
    };
    private String ldapUrl;
    private Duration connectTimeout = Duration.ofMinutes(1L);
    private Duration responseTimeout = Duration.ofMinutes(1L);
    private Duration reconnectTimeout = Duration.ofMinutes(2L);
    private boolean autoReconnect = true;
    private Predicate<RetryMetadata> autoReconnectCondition = ONE_RECONNECT_ATTEMPT;
    private boolean autoReplay = true;
    private SslConfig sslConfig;
    private boolean useStartTLS;
    private ConnectionInitializer[] connectionInitializers;
    private ConnectionStrategy connectionStrategy = new ActivePassiveConnectionStrategy();
    private ConnectionValidator connectionValidator;
    private final Map<String, Object> transportOptions = new HashMap<String, Object>();

    public ConnectionConfig() {
    }

    public ConnectionConfig(String url2) {
        this.setLdapUrl(url2);
    }

    public String getLdapUrl() {
        return this.ldapUrl;
    }

    public void setLdapUrl(String url2) {
        this.checkImmutable();
        this.checkStringInput(url2, true);
        this.logger.trace("setting ldapUrl: {}", (Object)url2);
        this.ldapUrl = url2;
    }

    public Duration getConnectTimeout() {
        return this.connectTimeout;
    }

    public void setConnectTimeout(Duration time) {
        this.checkImmutable();
        if (time == null || time.isNegative()) {
            throw new IllegalArgumentException("Connect timeout cannot be null or negative");
        }
        this.logger.trace("setting connectTimeout: {}", (Object)time);
        this.connectTimeout = time;
    }

    public Duration getResponseTimeout() {
        return this.responseTimeout;
    }

    public void setResponseTimeout(Duration time) {
        this.checkImmutable();
        if (time == null || time.isNegative()) {
            throw new IllegalArgumentException("Connect timeout cannot be null or negative");
        }
        this.logger.trace("setting responseTimeout: {}", (Object)time);
        this.responseTimeout = time;
    }

    public Duration getReconnectTimeout() {
        return this.reconnectTimeout;
    }

    public void setReconnectTimeout(Duration time) {
        this.checkImmutable();
        if (time == null || time.isNegative()) {
            throw new IllegalArgumentException("Reconnect timeout cannot be null or negative");
        }
        this.logger.trace("setting reconnectTimeout: {}", (Object)time);
        this.reconnectTimeout = time;
    }

    public boolean getAutoReconnect() {
        return this.autoReconnect;
    }

    public void setAutoReconnect(boolean b) {
        this.checkImmutable();
        this.logger.trace("setting autoReconnect: {}", (Object)b);
        this.autoReconnect = b;
    }

    public Predicate<RetryMetadata> getAutoReconnectCondition() {
        return this.autoReconnectCondition;
    }

    public void setAutoReconnectCondition(Predicate<RetryMetadata> predicate) {
        this.checkImmutable();
        this.logger.trace("setting autoReconnectCondition: {}", (Object)predicate);
        this.autoReconnectCondition = predicate;
    }

    public boolean getAutoReplay() {
        return this.autoReplay;
    }

    public void setAutoReplay(boolean b) {
        this.checkImmutable();
        this.logger.trace("setting autoReplay: {}", (Object)b);
        this.autoReplay = b;
    }

    public SslConfig getSslConfig() {
        return this.sslConfig;
    }

    public void setSslConfig(SslConfig config) {
        this.checkImmutable();
        this.logger.trace("setting sslConfig: {}", (Object)config);
        this.sslConfig = config;
    }

    public boolean getUseStartTLS() {
        return this.useStartTLS;
    }

    public void setUseStartTLS(boolean b) {
        this.checkImmutable();
        this.logger.trace("setting useStartTLS: {}", (Object)b);
        this.useStartTLS = b;
    }

    public ConnectionInitializer[] getConnectionInitializers() {
        return this.connectionInitializers;
    }

    public void setConnectionInitializers(ConnectionInitializer ... initializers) {
        this.checkImmutable();
        this.checkArrayContainsNull(initializers);
        this.logger.trace("setting connectionInitializers: {}", (Object)Arrays.toString(initializers));
        this.connectionInitializers = initializers;
    }

    public ConnectionStrategy getConnectionStrategy() {
        return this.connectionStrategy;
    }

    public void setConnectionStrategy(ConnectionStrategy strategy) {
        this.checkImmutable();
        this.logger.trace("setting connectionStrategy: {}", (Object)strategy);
        this.connectionStrategy = strategy;
    }

    public ConnectionValidator getConnectionValidator() {
        return this.connectionValidator;
    }

    public void setConnectionValidator(ConnectionValidator validator) {
        this.checkImmutable();
        this.logger.trace("setting connectionValidator: {}", (Object)validator);
        this.connectionValidator = validator;
    }

    public Map<String, ?> getTransportOptions() {
        return this.transportOptions;
    }

    public void setTransportOptions(Map<String, ?> options) {
        this.checkImmutable();
        this.logger.trace("setting transport options: {}", (Object)options);
        this.transportOptions.putAll(options);
    }

    public Object getTransportOption(String id) {
        return this.transportOptions.get(id);
    }

    public void setTransportOption(String id, Object value) {
        this.checkImmutable();
        this.logger.trace("setting transport options: {}={}", (Object)id, value);
        this.transportOptions.put(id, value);
    }

    public static ConnectionConfig copy(ConnectionConfig config) {
        ConnectionConfig cc = new ConnectionConfig();
        cc.setLdapUrl(config.getLdapUrl());
        cc.setConnectTimeout(config.getConnectTimeout());
        cc.setResponseTimeout(config.getResponseTimeout());
        cc.setReconnectTimeout(config.getReconnectTimeout());
        cc.setAutoReconnect(config.getAutoReconnect());
        cc.setAutoReconnectCondition(config.getAutoReconnectCondition());
        cc.setAutoReplay(config.getAutoReplay());
        cc.setSslConfig(config.getSslConfig() != null ? SslConfig.copy(config.getSslConfig()) : null);
        cc.setUseStartTLS(config.getUseStartTLS());
        cc.setConnectionInitializers(config.getConnectionInitializers());
        cc.setConnectionStrategy(config.getConnectionStrategy() != null ? config.getConnectionStrategy().newInstance() : null);
        cc.setConnectionValidator(config.getConnectionValidator());
        cc.setTransportOptions(config.getTransportOptions());
        return cc;
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "ldapUrl=" + this.ldapUrl + ", " + "connectTimeout=" + this.connectTimeout + ", " + "responseTimeout=" + this.responseTimeout + ", " + "reconnectTimeout=" + this.reconnectTimeout + ", " + "autoReconnect=" + this.autoReconnect + ", " + "autoReconnectCondition=" + this.autoReconnectCondition + ", " + "autoReplay=" + this.autoReplay + ", " + "sslConfig=" + this.sslConfig + ", " + "useStartTLS=" + this.useStartTLS + ", " + "connectionInitializers=" + Arrays.toString(this.connectionInitializers) + ", " + "connectionStrategy=" + this.connectionStrategy + ", " + "connectionValidator=" + this.connectionValidator + ", " + "transportOptions=" + this.transportOptions + "]";
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final ConnectionConfig object = new ConnectionConfig();

        protected Builder() {
        }

        public Builder url(String url2) {
            this.object.setLdapUrl(url2);
            return this;
        }

        public Builder connectTimeout(Duration timeout2) {
            this.object.setConnectTimeout(timeout2);
            return this;
        }

        public Builder responseTimeout(Duration timeout2) {
            this.object.setResponseTimeout(timeout2);
            return this;
        }

        public Builder reconnectTimeout(Duration timeout2) {
            this.object.setReconnectTimeout(timeout2);
            return this;
        }

        public Builder autoReconnect(boolean b) {
            this.object.setAutoReconnect(b);
            return this;
        }

        public Builder autoReconnectCondition(Predicate<RetryMetadata> predicate) {
            this.object.setAutoReconnectCondition(predicate);
            return this;
        }

        public Builder autoReplay(boolean b) {
            this.object.setAutoReplay(b);
            return this;
        }

        public Builder sslConfig(SslConfig config) {
            this.object.setSslConfig(config);
            return this;
        }

        public Builder useStartTLS(boolean b) {
            this.object.setUseStartTLS(b);
            return this;
        }

        public Builder connectionInitializers(ConnectionInitializer ... initializers) {
            this.object.setConnectionInitializers(initializers);
            return this;
        }

        public Builder connectionStrategy(ConnectionStrategy strategy) {
            this.object.setConnectionStrategy(strategy);
            return this;
        }

        public Builder connectionValidator(ConnectionValidator validator) {
            this.object.setConnectionValidator(validator);
            return this;
        }

        public Builder transportOption(String id, Object value) {
            this.object.setTransportOption(id, value);
            return this;
        }

        public ConnectionConfig build() {
            return this.object;
        }
    }
}

