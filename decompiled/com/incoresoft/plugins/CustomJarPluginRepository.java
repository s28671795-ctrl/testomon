/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugins;

import java.io.File;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import org.pf4j.JarPluginRepository;

public class CustomJarPluginRepository
extends JarPluginRepository {
    public CustomJarPluginRepository(Path ... pluginsRoots) {
        super(pluginsRoots);
        this.setComparator(CustomJarPluginRepository.comparator());
    }

    public CustomJarPluginRepository(List<Path> pluginsRoots) {
        super(pluginsRoots);
        this.setComparator(CustomJarPluginRepository.comparator());
    }

    private static Comparator<File> comparator() {
        return Comparator.comparingLong(File::lastModified).reversed();
    }
}

