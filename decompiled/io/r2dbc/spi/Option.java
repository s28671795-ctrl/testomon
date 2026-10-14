/*
 * Decompiled with CFR 0.152.
 */
package io.r2dbc.spi;

import io.r2dbc.spi.Assert;
import io.r2dbc.spi.ConstantPool;
import io.r2dbc.spi.Nullable;
import java.util.Objects;

public final class Option<T> {
    private static final ConstantPool<Option<?>> CONSTANTS = new ConstantPool<Option<?>>(){

        @Override
        Option<?> createConstant(String name, boolean sensitive) {
            return new Option(name, sensitive);
        }
    };
    private final String name;
    private final boolean sensitive;

    private Option(String name, boolean sensitive) {
        this.name = name;
        this.sensitive = sensitive;
    }

    public static <T> Option<T> sensitiveValueOf(String name) {
        Assert.requireNonNull(name, "name must not be null");
        Assert.requireNonEmpty(name, "name must not be empty");
        return CONSTANTS.valueOf(name, true);
    }

    public static <T> Option<T> valueOf(String name) {
        Assert.requireNonNull(name, "name must not be null");
        Assert.requireNonEmpty(name, "name must not be empty");
        return CONSTANTS.valueOf(name, false);
    }

    @Nullable
    public T cast(@Nullable Object obj) {
        if (obj == null) {
            return null;
        }
        return (T)obj;
    }

    public String name() {
        return this.name;
    }

    public String toString() {
        return "Option{name='" + this.name + '\'' + ", sensitive=" + this.sensitive + '}';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Option option = (Option)o;
        return this.sensitive == option.sensitive && this.name.equals(option.name);
    }

    public int hashCode() {
        return Objects.hash(this.name, this.sensitive);
    }

    boolean sensitive() {
        return this.sensitive;
    }
}

