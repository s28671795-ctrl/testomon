/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.common.collect.Lists;
import com.google.inject.internal.CreationListener;
import com.google.inject.internal.Errors;
import java.util.List;

class ProcessedBindingData {
    private final List<CreationListener> creationListeners = Lists.newArrayList();
    private final List<Runnable> uninitializedBindings = Lists.newArrayList();
    private final List<Runnable> delayedUninitializedBindings = Lists.newArrayList();

    ProcessedBindingData() {
    }

    void addCreationListener(CreationListener listener) {
        this.creationListeners.add(listener);
    }

    void addUninitializedBinding(Runnable runnable2) {
        this.uninitializedBindings.add(runnable2);
    }

    void addDelayedUninitializedBinding(Runnable runnable2) {
        this.delayedUninitializedBindings.add(runnable2);
    }

    void initializeBindings() {
        for (Runnable initializer : this.uninitializedBindings) {
            initializer.run();
        }
    }

    void runCreationListeners(Errors errors2) {
        for (CreationListener creationListener : this.creationListeners) {
            creationListener.notify(errors2);
        }
    }

    void initializeDelayedBindings() {
        for (Runnable initializer : this.delayedUninitializedBindings) {
            initializer.run();
        }
    }
}

