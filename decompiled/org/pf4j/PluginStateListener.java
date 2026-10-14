/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j;

import java.util.EventListener;
import org.pf4j.PluginStateEvent;

public interface PluginStateListener
extends EventListener {
    public void pluginStateChanged(PluginStateEvent var1);
}

