/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j;

import java.util.EventObject;
import org.pf4j.PluginManager;
import org.pf4j.PluginState;
import org.pf4j.PluginWrapper;

public class PluginStateEvent
extends EventObject {
    private PluginWrapper plugin;
    private PluginState oldState;

    public PluginStateEvent(PluginManager source2, PluginWrapper plugin, PluginState oldState) {
        super(source2);
        this.plugin = plugin;
        this.oldState = oldState;
    }

    @Override
    public PluginManager getSource() {
        return (PluginManager)super.getSource();
    }

    public PluginWrapper getPlugin() {
        return this.plugin;
    }

    public PluginState getPluginState() {
        return this.plugin.getPluginState();
    }

    public PluginState getOldState() {
        return this.oldState;
    }

    @Override
    public String toString() {
        return "PluginStateEvent [plugin=" + this.plugin.getPluginId() + ", newState=" + (Object)((Object)this.getPluginState()) + ", oldState=" + (Object)((Object)this.oldState) + ']';
    }
}

