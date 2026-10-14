/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j;

import org.pf4j.PluginStateEvent;
import org.pf4j.PluginStateListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingPluginStateListener
implements PluginStateListener {
    private static final Logger log = LoggerFactory.getLogger(LoggingPluginStateListener.class);

    @Override
    public void pluginStateChanged(PluginStateEvent event) {
        log.debug("The state of plugin '{}' has changed from '{}' to '{}'", new Object[]{event.getPlugin().getPluginId(), event.getOldState(), event.getPluginState()});
    }
}

