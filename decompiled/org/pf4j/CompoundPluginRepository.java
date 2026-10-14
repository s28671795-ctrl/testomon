/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.function.BooleanSupplier;
import org.pf4j.PluginRepository;

public class CompoundPluginRepository
implements PluginRepository {
    private List<PluginRepository> repositories = new ArrayList<PluginRepository>();

    public CompoundPluginRepository add(PluginRepository repository) {
        if (repository == null) {
            throw new IllegalArgumentException("null not allowed");
        }
        this.repositories.add(repository);
        return this;
    }

    public CompoundPluginRepository add(PluginRepository repository, BooleanSupplier condition) {
        if (condition.getAsBoolean()) {
            return this.add(repository);
        }
        return this;
    }

    @Override
    public List<Path> getPluginPaths() {
        LinkedHashSet<Path> paths = new LinkedHashSet<Path>();
        for (PluginRepository repository : this.repositories) {
            paths.addAll(repository.getPluginPaths());
        }
        return new ArrayList<Path>(paths);
    }

    @Override
    public boolean deletePluginPath(Path pluginPath) {
        for (PluginRepository repository : this.repositories) {
            if (!repository.deletePluginPath(pluginPath)) continue;
            return true;
        }
        return false;
    }
}

