/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import org.pf4j.ExtensionFactory;
import org.pf4j.PluginState;
import org.pf4j.PluginStateListener;
import org.pf4j.PluginWrapper;
import org.pf4j.RuntimeMode;
import org.pf4j.VersionManager;

public interface PluginManager {
    public List<PluginWrapper> getPlugins();

    public List<PluginWrapper> getPlugins(PluginState var1);

    public List<PluginWrapper> getResolvedPlugins();

    public List<PluginWrapper> getUnresolvedPlugins();

    public List<PluginWrapper> getStartedPlugins();

    public PluginWrapper getPlugin(String var1);

    public void loadPlugins();

    public String loadPlugin(Path var1);

    public void startPlugins();

    public PluginState startPlugin(String var1);

    public void stopPlugins();

    public PluginState stopPlugin(String var1);

    public void unloadPlugins();

    public boolean unloadPlugin(String var1);

    public boolean disablePlugin(String var1);

    public boolean enablePlugin(String var1);

    public boolean deletePlugin(String var1);

    public ClassLoader getPluginClassLoader(String var1);

    public List<Class<?>> getExtensionClasses(String var1);

    public <T> List<Class<? extends T>> getExtensionClasses(Class<T> var1);

    public <T> List<Class<? extends T>> getExtensionClasses(Class<T> var1, String var2);

    public <T> List<T> getExtensions(Class<T> var1);

    public <T> List<T> getExtensions(Class<T> var1, String var2);

    public List getExtensions(String var1);

    public Set<String> getExtensionClassNames(String var1);

    public ExtensionFactory getExtensionFactory();

    public RuntimeMode getRuntimeMode();

    default public boolean isDevelopment() {
        return RuntimeMode.DEVELOPMENT.equals((Object)this.getRuntimeMode());
    }

    default public boolean isNotDevelopment() {
        return !this.isDevelopment();
    }

    public PluginWrapper whichPlugin(Class<?> var1);

    public void addPluginStateListener(PluginStateListener var1);

    public void removePluginStateListener(PluginStateListener var1);

    public void setSystemVersion(String var1);

    public String getSystemVersion();

    @Deprecated
    public Path getPluginsRoot();

    public List<Path> getPluginsRoots();

    public VersionManager getVersionManager();
}

