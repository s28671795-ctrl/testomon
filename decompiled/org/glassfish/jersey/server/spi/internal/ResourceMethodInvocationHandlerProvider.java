/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.spi.internal;

import java.lang.reflect.InvocationHandler;
import org.glassfish.jersey.server.model.Invocable;

public interface ResourceMethodInvocationHandlerProvider {
    public InvocationHandler create(Invocable var1);
}

