/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j;

import java.io.FileFilter;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.pf4j.BasePluginRepository;
import org.pf4j.util.AndFileFilter;
import org.pf4j.util.DirectoryFileFilter;
import org.pf4j.util.HiddenFilter;
import org.pf4j.util.NameFileFilter;
import org.pf4j.util.NotFileFilter;
import org.pf4j.util.OrFileFilter;

public class DevelopmentPluginRepository
extends BasePluginRepository {
    public static final String MAVEN_BUILD_DIR = "target";
    public static final String GRADLE_BUILD_DIR = "build";

    public DevelopmentPluginRepository(Path ... pluginsRoots) {
        this(Arrays.asList(pluginsRoots));
    }

    public DevelopmentPluginRepository(List<Path> pluginsRoots) {
        super(pluginsRoots);
        AndFileFilter pluginsFilter = new AndFileFilter(new DirectoryFileFilter());
        pluginsFilter.addFileFilter(new NotFileFilter(this.createHiddenPluginFilter()));
        this.setFilter(pluginsFilter);
    }

    protected FileFilter createHiddenPluginFilter() {
        OrFileFilter hiddenPluginFilter = new OrFileFilter(new HiddenFilter());
        hiddenPluginFilter.addFileFilter(new NameFileFilter(MAVEN_BUILD_DIR)).addFileFilter(new NameFileFilter(GRADLE_BUILD_DIR));
        return hiddenPluginFilter;
    }
}

