/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal.util;

import com.google.inject.internal.util.CallerFinder;
import java.util.function.Predicate;

class NewThrowableFinder
implements CallerFinder {
    NewThrowableFinder() {
    }

    @Override
    public StackTraceElement findCaller(Predicate<String> shouldBeSkipped) {
        StackTraceElement[] stackTraceElements;
        for (StackTraceElement element : stackTraceElements = new Throwable().getStackTrace()) {
            String className = element.getClassName();
            if (shouldBeSkipped.test(className)) continue;
            return element;
        }
        throw new AssertionError();
    }
}

