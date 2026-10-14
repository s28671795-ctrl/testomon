/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.matcher;

import com.google.inject.matcher.Matcher;

@Deprecated
public abstract class AbstractMatcher<T>
implements Matcher<T> {
    @Override
    public Matcher<T> and(Matcher<? super T> other) {
        return Matcher.super.and(other);
    }

    @Override
    public Matcher<T> or(Matcher<? super T> other) {
        return Matcher.super.or(other);
    }
}

