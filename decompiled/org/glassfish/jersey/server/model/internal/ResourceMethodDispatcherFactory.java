/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.model.internal;

import java.lang.reflect.InvocationHandler;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Singleton;
import org.glassfish.jersey.server.internal.LocalizationMessages;
import org.glassfish.jersey.server.internal.inject.ConfiguredValidator;
import org.glassfish.jersey.server.model.Invocable;
import org.glassfish.jersey.server.spi.internal.ResourceMethodDispatcher;

@Singleton
public final class ResourceMethodDispatcherFactory
implements ResourceMethodDispatcher.Provider {
    private static final Logger LOGGER = Logger.getLogger(ResourceMethodDispatcherFactory.class.getName());
    private final Collection<ResourceMethodDispatcher.Provider> providers;

    ResourceMethodDispatcherFactory(Collection<ResourceMethodDispatcher.Provider> providers) {
        this.providers = providers;
    }

    @Override
    public ResourceMethodDispatcher create(Invocable resourceMethod, InvocationHandler handler, ConfiguredValidator validator) {
        for (ResourceMethodDispatcher.Provider provider : this.providers) {
            try {
                ResourceMethodDispatcher dispatcher = provider.create(resourceMethod, handler, validator);
                if (dispatcher == null) continue;
                return dispatcher;
            }
            catch (Exception e) {
                LOGGER.log(Level.SEVERE, LocalizationMessages.ERROR_PROCESSING_METHOD(resourceMethod, provider.getClass().getName()), e);
            }
        }
        return null;
    }
}

