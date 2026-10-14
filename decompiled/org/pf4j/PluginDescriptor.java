/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j;

import java.util.List;
import org.pf4j.PluginDependency;

public interface PluginDescriptor {
    public String getPluginId();

    public String getPluginDescription();

    public String getPluginClass();

    public String getVersion();

    public String getRequires();

    public String getProvider();

    public String getLicense();

    public List<PluginDependency> getDependencies();
}

