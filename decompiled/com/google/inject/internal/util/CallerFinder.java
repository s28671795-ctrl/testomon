/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal.util;

import java.util.function.Predicate;

interface CallerFinder {
    public StackTraceElement findCaller(Predicate<String> var1);
}

