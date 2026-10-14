/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j;

import java.nio.file.Path;
import org.pf4j.PluginDescriptor;

public interface PluginDescriptorFinder {
    public boolean isApplicable(Path var1);

    public PluginDescriptor find(Path var1);
}

