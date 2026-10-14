/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.io.Serializable;
import java.util.concurrent.Executor;
import org.jooq.ExecutorProvider;
import org.jooq.impl.DefaultExecutor;

public class DefaultExecutorProvider
implements ExecutorProvider,
Serializable {
    @Override
    public final Executor provide() {
        return new DefaultExecutor();
    }
}

