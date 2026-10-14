/*
 * Decompiled with CFR 0.152.
 */
package com.sun.el.stream;

import java.util.NoSuchElementException;
import javax.el.LambdaExpression;

public class Optional {
    private static final Optional EMPTY = new Optional();
    private final Object value;

    Optional(Object value) {
        if (value == null) {
            throw new NullPointerException();
        }
        this.value = value;
    }

    Optional() {
        this.value = null;
    }

    public boolean isPresent() {
        return this.value != null;
    }

    public void ifPresent(LambdaExpression lambda) {
        if (this.value != null) {
            lambda.invoke(this.value);
        }
    }

    public Object get() {
        if (this.value == null) {
            throw new NoSuchElementException("No value present");
        }
        return this.value;
    }

    public Object orElse(Object other) {
        return this.value != null ? this.value : other;
    }

    public Object orElseGet(LambdaExpression other) {
        return this.value != null ? this.value : other.invoke(new Object[0]);
    }
}

