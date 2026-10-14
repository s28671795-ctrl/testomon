/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j;

import org.pf4j.PluginWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Plugin {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    protected PluginWrapper wrapper;

    public Plugin(PluginWrapper wrapper) {
        if (wrapper == null) {
            throw new IllegalArgumentException("Wrapper cannot be null");
        }
        this.wrapper = wrapper;
    }

    public final PluginWrapper getWrapper() {
        return this.wrapper;
    }

    public void start() {
    }

    public void stop() {
    }

    public void delete() {
    }
}

