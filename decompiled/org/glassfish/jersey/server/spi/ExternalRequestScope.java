/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.spi;

import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.server.spi.ExternalRequestContext;

public interface ExternalRequestScope<T>
extends AutoCloseable {
    public ExternalRequestContext<T> open(InjectionManager var1);

    public void suspend(ExternalRequestContext<T> var1, InjectionManager var2);

    public void resume(ExternalRequestContext<T> var1, InjectionManager var2);

    @Override
    public void close();
}

