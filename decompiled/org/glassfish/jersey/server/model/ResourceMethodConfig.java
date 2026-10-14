/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.model;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import javax.ws.rs.RuntimeType;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.WriterInterceptor;
import org.glassfish.jersey.model.ContractProvider;
import org.glassfish.jersey.model.internal.CommonConfig;
import org.glassfish.jersey.model.internal.ComponentBag;
import org.glassfish.jersey.process.Inflector;
import org.glassfish.jersey.server.internal.LocalizationMessages;

class ResourceMethodConfig
extends CommonConfig {
    private static final Logger LOGGER = Logger.getLogger(ResourceMethodConfig.class.getName());
    private static final Set<Class<?>> allowedContracts;

    ResourceMethodConfig(Map<String, Object> properties) {
        super(RuntimeType.SERVER, ComponentBag.EXCLUDE_EMPTY);
        this.setProperties(properties);
    }

    @Override
    protected Inflector<ContractProvider.Builder, ContractProvider> getModelEnhancer(final Class<?> providerClass) {
        return new Inflector<ContractProvider.Builder, ContractProvider>(){

            @Override
            public ContractProvider apply(ContractProvider.Builder builder) {
                Iterator<Class<?>> it = builder.getContracts().keySet().iterator();
                while (it.hasNext()) {
                    Class<?> contract = it.next();
                    if (allowedContracts.contains(contract)) continue;
                    LOGGER.warning(LocalizationMessages.CONTRACT_CANNOT_BE_BOUND_TO_RESOURCE_METHOD(contract, providerClass));
                    it.remove();
                }
                return builder.build();
            }
        };
    }

    static {
        Set tempSet = Collections.newSetFromMap(new IdentityHashMap());
        tempSet.add(ContainerRequestFilter.class);
        tempSet.add(ContainerResponseFilter.class);
        tempSet.add(ReaderInterceptor.class);
        tempSet.add(WriterInterceptor.class);
        allowedContracts = Collections.unmodifiableSet(tempSet);
    }
}

