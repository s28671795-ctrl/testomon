/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import org.pf4j.Plugin;
import org.pf4j.PluginFactory;
import org.pf4j.PluginWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultPluginFactory
implements PluginFactory {
    private static final Logger log = LoggerFactory.getLogger(DefaultPluginFactory.class);

    @Override
    public Plugin create(PluginWrapper pluginWrapper) {
        Class<?> pluginClass;
        String pluginClassName = pluginWrapper.getDescriptor().getPluginClass();
        log.debug("Create instance for plugin '{}'", (Object)pluginClassName);
        try {
            pluginClass = pluginWrapper.getPluginClassLoader().loadClass(pluginClassName);
        }
        catch (ClassNotFoundException e) {
            log.error(e.getMessage(), e);
            return null;
        }
        int modifiers = pluginClass.getModifiers();
        if (Modifier.isAbstract(modifiers) || Modifier.isInterface(modifiers) || !Plugin.class.isAssignableFrom(pluginClass)) {
            log.error("The plugin class '{}' is not valid", (Object)pluginClassName);
            return null;
        }
        try {
            Constructor<?> constructor = pluginClass.getConstructor(PluginWrapper.class);
            return (Plugin)constructor.newInstance(pluginWrapper);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}

