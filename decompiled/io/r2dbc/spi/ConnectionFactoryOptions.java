/*
 * Decompiled with CFR 0.152.
 */
package io.r2dbc.spi;

import io.r2dbc.spi.Assert;
import io.r2dbc.spi.ConnectionUrlParser;
import io.r2dbc.spi.NoSuchOptionException;
import io.r2dbc.spi.Nullable;
import io.r2dbc.spi.Option;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

public final class ConnectionFactoryOptions {
    public static final Option<Duration> CONNECT_TIMEOUT = Option.valueOf("connectTimeout");
    public static final Option<String> DATABASE = Option.valueOf("database");
    public static final Option<String> DRIVER = Option.valueOf("driver");
    public static final Option<String> HOST = Option.valueOf("host");
    public static final Option<Duration> LOCK_WAIT_TIMEOUT = Option.valueOf("lockWaitTimeout");
    public static final Option<CharSequence> PASSWORD = Option.sensitiveValueOf("password");
    public static final Option<Integer> PORT = Option.valueOf("port");
    public static final Option<String> PROTOCOL = Option.valueOf("protocol");
    public static final Option<Boolean> SSL = Option.valueOf("ssl");
    public static final Option<Duration> STATEMENT_TIMEOUT = Option.valueOf("statementTimeout");
    public static final Option<String> USER = Option.valueOf("user");
    private final Map<Option<?>, Object> options;

    private ConnectionFactoryOptions(Map<Option<?>, Object> options) {
        this.options = Assert.requireNonNull(options, "options must not be null");
    }

    public static Builder builder() {
        return new Builder();
    }

    public static ConnectionFactoryOptions parse(CharSequence url2) {
        return ConnectionUrlParser.parseQuery(Assert.requireNonNull(url2, "R2DBC Connection URL must not be null"));
    }

    public Builder mutate() {
        return new Builder().from(this);
    }

    public Object getRequiredValue(Option<?> option) {
        Object value = this.getValue(option);
        if (value != null) {
            return value;
        }
        throw new NoSuchOptionException(String.format("No value found for %s", option.name()), option);
    }

    @Nullable
    public Object getValue(Option<?> option) {
        Assert.requireNonNull(option, "option must not be null");
        return this.options.get(option);
    }

    public boolean hasOption(Option<?> option) {
        Assert.requireNonNull(option, "option must not be null");
        return this.options.containsKey(option);
    }

    public String toString() {
        return "ConnectionFactoryOptions{options=" + ConnectionFactoryOptions.toString(this.options) + '}';
    }

    private static String toString(Map<Option<?>, Object> options) {
        ArrayList<String> o = new ArrayList<String>(options.size());
        for (Map.Entry<Option<?>, Object> entry : options.entrySet()) {
            String key = entry.getKey().name();
            Object value = entry.getKey().sensitive() ? "REDACTED" : entry.getValue();
            o.add(String.format("%s=%s", key, value));
        }
        return String.format("{%s}", String.join((CharSequence)", ", o));
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        ConnectionFactoryOptions that = (ConnectionFactoryOptions)o;
        return this.options.equals(that.options);
    }

    public int hashCode() {
        return Objects.hash(this.options);
    }

    public static final class Builder {
        private final Map<Option<?>, Object> options = new HashMap();

        private Builder() {
        }

        public ConnectionFactoryOptions build() {
            return new ConnectionFactoryOptions(new HashMap(this.options));
        }

        public Builder from(ConnectionFactoryOptions connectionFactoryOptions) {
            return this.from(connectionFactoryOptions, it -> true);
        }

        public Builder from(ConnectionFactoryOptions connectionFactoryOptions, Predicate<Option<?>> filter2) {
            Assert.requireNonNull(connectionFactoryOptions, "connectionFactoryOptions must not be null");
            Assert.requireNonNull(filter2, "filter must not be null");
            connectionFactoryOptions.options.forEach((k, v) -> {
                if (filter2.test((Option<?>)k)) {
                    this.options.put((Option<?>)k, v);
                }
            });
            return this;
        }

        public <T> Builder option(Option<T> option, T value) {
            Assert.requireNonNull(option, "option must not be null");
            Assert.requireNonNull(value, "value must not be null");
            this.options.put(option, value);
            return this;
        }

        public String toString() {
            return "Builder{options=" + ConnectionFactoryOptions.toString(this.options) + '}';
        }
    }
}

