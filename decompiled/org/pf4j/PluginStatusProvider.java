/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j;

public interface PluginStatusProvider {
    public boolean isPluginDisabled(String var1);

    public void disablePlugin(String var1);

    public void enablePlugin(String var1);
}

