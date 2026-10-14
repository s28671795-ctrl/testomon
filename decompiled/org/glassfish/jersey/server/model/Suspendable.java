/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.model;

import java.util.concurrent.TimeUnit;

public interface Suspendable {
    public boolean isSuspendDeclared();

    public boolean isManagedAsyncDeclared();

    public long getSuspendTimeout();

    public TimeUnit getSuspendTimeoutUnit();
}

