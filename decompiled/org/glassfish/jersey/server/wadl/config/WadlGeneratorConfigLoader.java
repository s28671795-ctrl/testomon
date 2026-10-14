/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.wadl.config;

import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.ws.rs.ProcessingException;
import org.glassfish.jersey.internal.util.ReflectionHelper;
import org.glassfish.jersey.server.internal.LocalizationMessages;
import org.glassfish.jersey.server.wadl.config.WadlGeneratorConfig;
import org.glassfish.jersey.server.wadl.config.WadlGeneratorDescription;

public class WadlGeneratorConfigLoader {
    public static WadlGeneratorConfig loadWadlGeneratorsFromConfig(Map<String, Object> properties) {
        Object wadlGeneratorConfigProperty = properties.get("jersey.config.server.wadl.generatorConfig");
        if (wadlGeneratorConfigProperty == null) {
            return new WadlGeneratorConfig(){

                @Override
                public List<WadlGeneratorDescription> configure() {
                    return Collections.emptyList();
                }
            };
        }
        try {
            Class<WadlGeneratorConfig> configClazz;
            if (wadlGeneratorConfigProperty instanceof WadlGeneratorConfig) {
                return (WadlGeneratorConfig)wadlGeneratorConfigProperty;
            }
            if (wadlGeneratorConfigProperty instanceof Class) {
                configClazz = ((Class)wadlGeneratorConfigProperty).asSubclass(WadlGeneratorConfig.class);
            } else if (wadlGeneratorConfigProperty instanceof String) {
                configClazz = AccessController.doPrivileged(ReflectionHelper.classForNameWithExceptionPEA((String)wadlGeneratorConfigProperty)).asSubclass(WadlGeneratorConfig.class);
            } else {
                throw new ProcessingException(LocalizationMessages.ERROR_WADL_GENERATOR_CONFIG_LOADER_PROPERTY("jersey.config.server.wadl.generatorConfig", wadlGeneratorConfigProperty.getClass().getName()));
            }
            return configClazz.newInstance();
        }
        catch (PrivilegedActionException pae) {
            throw new ProcessingException(LocalizationMessages.ERROR_WADL_GENERATOR_CONFIG_LOADER("jersey.config.server.wadl.generatorConfig"), pae.getCause());
        }
        catch (Exception e) {
            throw new ProcessingException(LocalizationMessages.ERROR_WADL_GENERATOR_CONFIG_LOADER("jersey.config.server.wadl.generatorConfig"), e);
        }
    }
}

