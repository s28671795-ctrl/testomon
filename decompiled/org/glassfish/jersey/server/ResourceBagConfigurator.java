/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server;

import java.util.logging.Logger;
import org.glassfish.jersey.internal.BootstrapBag;
import org.glassfish.jersey.internal.BootstrapConfigurator;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.server.ResourceBag;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerBootstrapBag;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.model.Resource;

class ResourceBagConfigurator
implements BootstrapConfigurator {
    private static final Logger LOGGER = Logger.getLogger(ResourceBagConfigurator.class.getName());

    ResourceBagConfigurator() {
    }

    @Override
    public void init(InjectionManager injectionManager, BootstrapBag bootstrapBag) {
        Resource resource;
        ServerBootstrapBag serverBag = (ServerBootstrapBag)bootstrapBag;
        ResourceConfig runtimeConfig = serverBag.getRuntimeConfig();
        boolean disableValidation = ServerProperties.getValue(runtimeConfig.getProperties(), "jersey.config.server.resource.validation.disable", Boolean.FALSE, Boolean.class);
        ResourceBag.Builder resourceBagBuilder = new ResourceBag.Builder();
        for (Resource resource2 : runtimeConfig.getResources()) {
            resourceBagBuilder.registerProgrammaticResource(resource2);
        }
        for (Class clazz : runtimeConfig.getClasses()) {
            try {
                resource = Resource.from(clazz, disableValidation);
                if (resource == null) continue;
                resourceBagBuilder.registerResource(clazz, resource);
            }
            catch (IllegalArgumentException ex) {
                LOGGER.warning(ex.getMessage());
            }
        }
        for (Object object : runtimeConfig.getSingletons()) {
            try {
                resource = Resource.from(object.getClass(), disableValidation);
                if (resource == null) continue;
                resourceBagBuilder.registerResource(object, resource);
            }
            catch (IllegalArgumentException ex) {
                LOGGER.warning(ex.getMessage());
            }
        }
        serverBag.setResourceBag(resourceBagBuilder.build());
    }
}

