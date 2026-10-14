/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.modules;

import com.incoresoft.config.SetupManager;
import com.incoresoft.dependencyinjection.firstlaunch.FirstLaunchModule;
import com.incoresoft.vezha.config.VezhaSetupManager;
import com.incoresoft.vezha.protocol.controllers.api.v2.SetupController;

public class VezhaFirstLaunchModule
extends FirstLaunchModule {
    @Override
    protected void configure() {
        super.configure();
        this.bind(SetupController.class).asEagerSingleton();
    }

    @Override
    protected Class<? extends SetupManager<?>> setupManagerClass() {
        return VezhaSetupManager.class;
    }
}

