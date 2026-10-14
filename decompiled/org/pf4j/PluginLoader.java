/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j;

import java.nio.file.Path;
import org.pf4j.PluginDescriptor;

public interface PluginLoader {
    public boolean isApplicable(Path var1);

    public ClassLoader loadPlugin(Path var1, PluginDescriptor var2);
}

