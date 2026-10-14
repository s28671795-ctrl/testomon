/*
 * Decompiled with CFR 0.152.
 */
package liquibase.plugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import liquibase.Scope;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.plugin.Plugin;
import liquibase.plugin.PluginFactory;
import liquibase.servicelocator.ServiceLocator;

public abstract class AbstractPluginFactory<T extends Plugin>
implements PluginFactory {
    private Collection<T> allInstances;

    protected AbstractPluginFactory() {
    }

    protected abstract Class<T> getPluginClass();

    protected abstract int getPriority(T var1, Object ... var2);

    protected T getPlugin(Object ... args2) {
        Set<T> applicable = this.getPlugins(args2);
        if (applicable.isEmpty()) {
            return null;
        }
        return (T)((Plugin)applicable.iterator().next());
    }

    protected Set<T> getPlugins(Object ... args2) {
        Optional<T> forcedPlugin = this.getForcedPluginIfAvailable();
        if (forcedPlugin.isPresent()) {
            return new HashSet<Plugin>(Collections.singletonList((Plugin)forcedPlugin.get()));
        }
        TreeSet<Plugin> applicable = new TreeSet<Plugin>((o1, o2) -> {
            Integer o1Priority = this.getPriority(o1, args2);
            Integer o2Priority = this.getPriority(o2, args2);
            int i = o2Priority.compareTo(o1Priority);
            if (i == 0) {
                return o1.getClass().getName().compareTo(o2.getClass().getName());
            }
            return i;
        });
        for (Plugin plugin : this.findAllInstances()) {
            if (this.getPriority(plugin, args2) < 0) continue;
            applicable.add(plugin);
        }
        return applicable;
    }

    private Optional<T> getForcedPluginIfAvailable() {
        String pluginClassName = this.getPluginClass().getName();
        Class<Class> forcedPlugin = Scope.getCurrentScope().get("liquibase.plugin." + pluginClassName, Class.class);
        if (forcedPlugin != null) {
            for (Plugin plugin : this.findAllInstances()) {
                if (!plugin.getClass().equals(forcedPlugin)) continue;
                return Optional.of(plugin);
            }
            throw new UnexpectedLiquibaseException("No registered " + pluginClassName + " plugin " + forcedPlugin.getName());
        }
        return Optional.empty();
    }

    public synchronized void register(T plugin) {
        this.findAllInstances();
        this.allInstances.add(plugin);
    }

    protected synchronized Collection<T> findAllInstances() {
        if (this.allInstances == null) {
            this.allInstances = new ArrayList<T>();
            ServiceLocator serviceLocator = Scope.getCurrentScope().getServiceLocator();
            this.allInstances.addAll(serviceLocator.findInstances(this.getPluginClass()));
        }
        return this.allInstances;
    }

    protected synchronized void removeInstance(T instance) {
        if (this.allInstances == null) {
            return;
        }
        this.allInstances.remove(instance);
    }
}

