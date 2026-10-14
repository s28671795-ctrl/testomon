/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.pf4j.PluginRepository;
import org.pf4j.PluginRuntimeException;
import org.pf4j.util.FileUtils;

public class BasePluginRepository
implements PluginRepository {
    protected final List<Path> pluginsRoots;
    protected FileFilter filter;
    protected Comparator<File> comparator;

    public BasePluginRepository(Path ... pluginsRoots) {
        this(Arrays.asList(pluginsRoots));
    }

    public BasePluginRepository(List<Path> pluginsRoots) {
        this(pluginsRoots, null);
    }

    public BasePluginRepository(List<Path> pluginsRoots, FileFilter filter2) {
        this.pluginsRoots = pluginsRoots;
        this.filter = filter2;
        this.comparator = Comparator.comparingLong(File::lastModified);
    }

    public void setFilter(FileFilter filter2) {
        this.filter = filter2;
    }

    public void setComparator(Comparator<File> comparator) {
        this.comparator = comparator;
    }

    @Override
    public List<Path> getPluginPaths() {
        return this.pluginsRoots.stream().flatMap(path -> this.streamFiles((Path)path, this.filter)).sorted(this.comparator).map(File::toPath).collect(Collectors.toList());
    }

    @Override
    public boolean deletePluginPath(Path pluginPath) {
        if (!this.filter.accept(pluginPath.toFile())) {
            return false;
        }
        try {
            FileUtils.delete(pluginPath);
            return true;
        }
        catch (NoSuchFileException e) {
            return false;
        }
        catch (IOException e) {
            throw new PluginRuntimeException(e);
        }
    }

    protected Stream<File> streamFiles(Path directory, FileFilter filter2) {
        File[] files = directory.toFile().listFiles(filter2);
        return files != null ? Arrays.stream(files) : Stream.empty();
    }
}

