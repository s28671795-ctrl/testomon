/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.pf4j.PluginDescriptor;
import org.pf4j.PluginDescriptorFinder;
import org.pf4j.PluginRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompoundPluginDescriptorFinder
implements PluginDescriptorFinder {
    private static final Logger log = LoggerFactory.getLogger(CompoundPluginDescriptorFinder.class);
    private List<PluginDescriptorFinder> finders = new ArrayList<PluginDescriptorFinder>();

    public CompoundPluginDescriptorFinder add(PluginDescriptorFinder finder) {
        if (finder == null) {
            throw new IllegalArgumentException("null not allowed");
        }
        this.finders.add(finder);
        return this;
    }

    public int size() {
        return this.finders.size();
    }

    @Override
    public boolean isApplicable(Path pluginPath) {
        for (PluginDescriptorFinder finder : this.finders) {
            if (!finder.isApplicable(pluginPath)) continue;
            return true;
        }
        return false;
    }

    @Override
    public PluginDescriptor find(Path pluginPath) {
        for (PluginDescriptorFinder finder : this.finders) {
            if (finder.isApplicable(pluginPath)) {
                log.debug("'{}' is applicable for plugin '{}'", (Object)finder, (Object)pluginPath);
                try {
                    PluginDescriptor pluginDescriptor = finder.find(pluginPath);
                    if (pluginDescriptor == null) continue;
                    return pluginDescriptor;
                }
                catch (Exception e) {
                    if (this.finders.indexOf(finder) == this.finders.size() - 1) {
                        log.error(e.getMessage(), e);
                        continue;
                    }
                    log.debug(e.getMessage());
                    log.debug("Try to continue with the next finder");
                    continue;
                }
            }
            log.debug("'{}' is not applicable for plugin '{}'", (Object)finder, (Object)pluginPath);
        }
        throw new PluginRuntimeException("No PluginDescriptorFinder for plugin '{}'", pluginPath);
    }
}

