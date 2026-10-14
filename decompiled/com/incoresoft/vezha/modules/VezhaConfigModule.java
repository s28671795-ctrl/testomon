/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.modules;

import com.incoresoft.config.ConfigManager;
import com.incoresoft.core.models.CoreService;
import com.incoresoft.dependencyinjection.ConfigModule;
import com.incoresoft.vezha.config.VezhaConfigManager;
import com.incoresoft.vezha.core.VezhaCoreService;

public class VezhaConfigModule
extends ConfigModule {
    @Override
    protected CoreService coreService() {
        return new VezhaCoreService();
    }

    @Override
    public Class<? extends ConfigManager<?>> configManagerClass() {
        return VezhaConfigManager.class;
    }
}

