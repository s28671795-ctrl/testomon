/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.network;

import com.google.inject.Inject;
import com.incoresoft.network.NetworkSettings;
import com.incoresoft.network.NetworkSettingsRepository;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public class NetworkSettingsManager {
    private final NetworkSettingsRepository networkSettingsRepository;

    @Inject
    public NetworkSettingsManager(@NotNull NetworkSettingsRepository networkSettingsRepository) {
        this.networkSettingsRepository = Objects.requireNonNull(networkSettingsRepository);
    }

    public NetworkSettings get() {
        return this.networkSettingsRepository.get();
    }

    public NetworkSettings update(NetworkSettings settings) {
        return this.networkSettingsRepository.update(settings);
    }
}

