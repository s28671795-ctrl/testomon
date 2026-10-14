/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.pf4j.BasePluginRepository;
import org.pf4j.util.AndFileFilter;
import org.pf4j.util.DirectoryFileFilter;
import org.pf4j.util.FileUtils;
import org.pf4j.util.HiddenFilter;
import org.pf4j.util.NotFileFilter;
import org.pf4j.util.OrFileFilter;
import org.pf4j.util.ZipFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultPluginRepository
extends BasePluginRepository {
    private static final Logger log = LoggerFactory.getLogger(DefaultPluginRepository.class);

    public DefaultPluginRepository(Path ... pluginsRoots) {
        this(Arrays.asList(pluginsRoots));
    }

    public DefaultPluginRepository(List<Path> pluginsRoots) {
        super(pluginsRoots);
        AndFileFilter pluginsFilter = new AndFileFilter(new DirectoryFileFilter());
        pluginsFilter.addFileFilter(new NotFileFilter(this.createHiddenPluginFilter()));
        this.setFilter(pluginsFilter);
    }

    @Override
    public List<Path> getPluginPaths() {
        this.extractZipFiles();
        return super.getPluginPaths();
    }

    @Override
    public boolean deletePluginPath(Path pluginPath) {
        FileUtils.optimisticDelete(FileUtils.findWithEnding(pluginPath, ".zip", ".ZIP", ".Zip"));
        return super.deletePluginPath(pluginPath);
    }

    protected FileFilter createHiddenPluginFilter() {
        return new OrFileFilter(new HiddenFilter());
    }

    private void extractZipFiles() {
        this.pluginsRoots.stream().flatMap(path -> this.streamFiles((Path)path, new ZipFileFilter())).map(File::toPath).forEach(this::expandIfZip);
    }

    private void expandIfZip(Path filePath) {
        try {
            FileUtils.expandIfZip(filePath);
        }
        catch (IOException e) {
            log.error("Cannot expand plugin zip '{}'", (Object)filePath);
            log.error(e.getMessage(), e);
        }
    }
}

