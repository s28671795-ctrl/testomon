/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.spi.internal;

import java.lang.reflect.InvocationHandler;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.internal.inject.ConfiguredValidator;
import org.glassfish.jersey.server.model.Invocable;

public interface ResourceMethodDispatcher {
    public Response dispatch(Object var1, ContainerRequest var2) throws ProcessingException;

    public static interface Provider {
        public ResourceMethodDispatcher create(Invocable var1, InvocationHandler var2, ConfiguredValidator var3);
    }
}

