/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server;

import org.glassfish.jersey.internal.inject.AnnotationLiteral;
import org.glassfish.jersey.server.ManagedAsyncExecutor;

public final class ManagedAsyncExecutorLiteral
extends AnnotationLiteral<ManagedAsyncExecutor>
implements ManagedAsyncExecutor {
    public static final ManagedAsyncExecutor INSTANCE = new ManagedAsyncExecutorLiteral();

    private ManagedAsyncExecutorLiteral() {
    }
}

