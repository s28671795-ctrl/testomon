/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.thread;

import org.apache.activemq.thread.TaskRunnerFactory;

@Deprecated
public final class DefaultThreadPools {
    private static final TaskRunnerFactory DEFAULT_TASK_RUNNER_FACTORY = new TaskRunnerFactory();

    private DefaultThreadPools() {
    }

    @Deprecated
    public static TaskRunnerFactory getDefaultTaskRunnerFactory() {
        return DEFAULT_TASK_RUNNER_FACTORY;
    }

    public static void shutdown() {
        DEFAULT_TASK_RUNNER_FACTORY.shutdown();
    }
}

