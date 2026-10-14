/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal.util;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.inject.internal.util.CallerFinder;
import com.google.inject.internal.util.NewThrowableFinder;
import java.util.ArrayList;
import java.util.List;

public final class SourceProvider {
    private static final CallerFinder FINDER = SourceProvider.loadCallerFinder();
    public static final Object UNKNOWN_SOURCE = "[unknown source]";
    private final SourceProvider parent;
    private final ImmutableSet<String> classNamesToSkip;
    public static final SourceProvider DEFAULT_INSTANCE = new SourceProvider(ImmutableSet.of(SourceProvider.class.getName(), NewThrowableFinder.class.getName()));

    private SourceProvider(Iterable<String> classesToSkip) {
        this(null, classesToSkip);
    }

    private SourceProvider(SourceProvider parent, Iterable<String> classesToSkip) {
        this.parent = parent;
        ImmutableSet.Builder classNamesToSkipBuilder = ImmutableSet.builder();
        for (String classToSkip : classesToSkip) {
            if (parent != null && parent.shouldBeSkipped(classToSkip)) continue;
            classNamesToSkipBuilder.add(classToSkip);
        }
        this.classNamesToSkip = classNamesToSkipBuilder.build();
    }

    public SourceProvider plusSkippedClasses(Class<?> ... moreClassesToSkip) {
        return new SourceProvider(this, SourceProvider.asStrings(moreClassesToSkip));
    }

    private boolean shouldBeSkipped(String className) {
        return this.parent != null && this.parent.shouldBeSkipped(className) || this.classNamesToSkip.contains(className);
    }

    private static List<String> asStrings(Class<?> ... classes) {
        ArrayList<String> strings = Lists.newArrayList();
        for (Class<?> c : classes) {
            strings.add(c.getName());
        }
        return strings;
    }

    public StackTraceElement getCaller() {
        return FINDER.findCaller(this::shouldBeSkipped);
    }

    public Object getFromClassNames(List<String> moduleClassNames) {
        Preconditions.checkNotNull(moduleClassNames, "The list of module class names cannot be null.");
        for (String moduleClassName : moduleClassNames) {
            if (this.shouldBeSkipped(moduleClassName)) continue;
            return new StackTraceElement(moduleClassName, "configure", null, -1);
        }
        return UNKNOWN_SOURCE;
    }

    private static CallerFinder loadCallerFinder() {
        return new NewThrowableFinder();
    }
}

