/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.windowsshare;

import com.google.inject.Inject;
import com.incoresoft.middleware.model.base.ItemManager;
import com.incoresoft.middleware.windowsshare.WindowsShareConfig;
import com.incoresoft.vezha.database.WindowsShareRepository;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WindowsShareConfigManager
implements ItemManager<WindowsShareConfig> {
    private final Map<Integer, WindowsShareConfig> configs = new ConcurrentHashMap<Integer, WindowsShareConfig>();
    private final WindowsShareRepository repository;

    @Inject
    public void init() {
        this.configs.putAll(this.repository.getAll().stream().collect(Collectors.toUnmodifiableMap(WindowsShareConfig::getId, Function.identity())));
    }

    @Override
    public WindowsShareConfig get(Integer id) {
        return this.configs.get(id);
    }

    @Override
    public Collection<? extends WindowsShareConfig> getAll() {
        return Collections.unmodifiableCollection(this.configs.values());
    }

    @Override
    public WindowsShareConfig add(WindowsShareConfig config) {
        WindowsShareConfig added = this.repository.add(config);
        this.configs.put(added.getId(), added);
        return added;
    }

    @Override
    public WindowsShareConfig update(WindowsShareConfig config) {
        WindowsShareConfig updated = this.repository.update(config);
        this.configs.put(updated.getId(), updated);
        return updated;
    }

    @Override
    public WindowsShareConfig delete(Integer id) {
        this.repository.delete(id);
        return this.configs.remove(id);
    }

    @Override
    public WindowsShareConfig delete(WindowsShareConfig config) {
        return this.delete(config.getId());
    }

    public boolean exists(int id) {
        return this.configs.containsKey(id);
    }

    @Inject
    public WindowsShareConfigManager(WindowsShareRepository repository) {
        this.repository = repository;
    }
}

