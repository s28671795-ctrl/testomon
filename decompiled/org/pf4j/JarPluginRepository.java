/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.pf4j.BasePluginRepository;
import org.pf4j.util.JarFileFilter;

public class JarPluginRepository
extends BasePluginRepository {
    public JarPluginRepository(Path ... pluginsRoots) {
        this(Arrays.asList(pluginsRoots));
    }

    public JarPluginRepository(List<Path> pluginsRoots) {
        super(pluginsRoots, new JarFileFilter());
    }
}

