/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.spi;

import javax.ws.rs.ConstrainedTo;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.RuntimeType;
import javax.ws.rs.core.Application;
import org.glassfish.jersey.spi.Contract;

@Contract
@ConstrainedTo(value=RuntimeType.SERVER)
public interface ContainerProvider {
    public <T> T createContainer(Class<T> var1, Application var2) throws ProcessingException;
}

