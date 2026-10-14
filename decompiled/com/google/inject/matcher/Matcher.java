/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.matcher;

import com.google.inject.matcher.Matchers;

public interface Matcher<T> {
    public boolean matches(T var1);

    default public Matcher<T> and(Matcher<? super T> other) {
        return new Matchers.AndMatcher<T>(this, other);
    }

    default public Matcher<T> or(Matcher<? super T> other) {
        return new Matchers.OrMatcher<T>(this, other);
    }
}

