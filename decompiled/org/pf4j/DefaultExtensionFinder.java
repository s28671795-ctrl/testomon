/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.pf4j.ExtensionFinder;
import org.pf4j.ExtensionWrapper;
import org.pf4j.LegacyExtensionFinder;
import org.pf4j.PluginManager;
import org.pf4j.PluginStateEvent;
import org.pf4j.PluginStateListener;
import org.pf4j.ServiceProviderExtensionFinder;

public class DefaultExtensionFinder
implements ExtensionFinder,
PluginStateListener {
    protected PluginManager pluginManager;
    protected List<ExtensionFinder> finders = new ArrayList<ExtensionFinder>();

    public DefaultExtensionFinder(PluginManager pluginManager) {
        this.pluginManager = pluginManager;
        this.add(new LegacyExtensionFinder(pluginManager));
    }

    @Override
    public <T> List<ExtensionWrapper<T>> find(Class<T> type) {
        ArrayList<ExtensionWrapper<T>> extensions = new ArrayList<ExtensionWrapper<T>>();
        for (ExtensionFinder finder : this.finders) {
            extensions.addAll(finder.find(type));
        }
        return extensions;
    }

    @Override
    public <T> List<ExtensionWrapper<T>> find(Class<T> type, String pluginId) {
        ArrayList<ExtensionWrapper<T>> extensions = new ArrayList<ExtensionWrapper<T>>();
        for (ExtensionFinder finder : this.finders) {
            extensions.addAll(finder.find(type, pluginId));
        }
        return extensions;
    }

    @Override
    public List<ExtensionWrapper> find(String pluginId) {
        ArrayList<ExtensionWrapper> extensions = new ArrayList<ExtensionWrapper>();
        for (ExtensionFinder finder : this.finders) {
            extensions.addAll(finder.find(pluginId));
        }
        return extensions;
    }

    @Override
    public Set<String> findClassNames(String pluginId) {
        HashSet<String> classNames = new HashSet<String>();
        for (ExtensionFinder finder : this.finders) {
            classNames.addAll(finder.findClassNames(pluginId));
        }
        return classNames;
    }

    @Override
    public void pluginStateChanged(PluginStateEvent event) {
        for (ExtensionFinder finder : this.finders) {
            if (!(finder instanceof PluginStateListener)) continue;
            ((PluginStateListener)((Object)finder)).pluginStateChanged(event);
        }
    }

    public DefaultExtensionFinder addServiceProviderExtensionFinder() {
        return this.add(new ServiceProviderExtensionFinder(this.pluginManager));
    }

    public DefaultExtensionFinder add(ExtensionFinder finder) {
        this.finders.add(finder);
        return this;
    }
}

