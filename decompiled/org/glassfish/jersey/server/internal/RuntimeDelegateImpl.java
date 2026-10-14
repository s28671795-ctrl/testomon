/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal;

import javax.ws.rs.core.Application;
import org.glassfish.jersey.internal.AbstractRuntimeDelegate;
import org.glassfish.jersey.message.internal.MessagingBinders;
import org.glassfish.jersey.server.ContainerFactory;

public class RuntimeDelegateImpl
extends AbstractRuntimeDelegate {
    public RuntimeDelegateImpl() {
        super(new MessagingBinders.HeaderDelegateProviders().getHeaderDelegateProviders());
    }

    @Override
    public <T> T createEndpoint(Application application, Class<T> endpointType) throws IllegalArgumentException, UnsupportedOperationException {
        if (application == null) {
            throw new IllegalArgumentException("application is null.");
        }
        return ContainerFactory.createContainer(endpointType, application);
    }
}

