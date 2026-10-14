/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.modules;

import com.google.inject.AbstractModule;
import com.incoresoft.vezha.models.settings.CustomSettingsManager;

public class SettingsModule
extends AbstractModule {
    @Override
    protected void configure() {
        this.bind(CustomSettingsManager.class).asEagerSingleton();
    }
}

