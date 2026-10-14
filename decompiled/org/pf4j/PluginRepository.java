/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j;

import java.nio.file.Path;
import java.util.List;

public interface PluginRepository {
    public List<Path> getPluginPaths();

    public boolean deletePluginPath(Path var1);
}

