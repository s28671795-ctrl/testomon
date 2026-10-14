/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dependencyinjection;

import com.google.inject.AbstractModule;
import com.incoresoft.database.LicenseRepository;
import com.incoresoft.license.LicenseDailyChecker;
import com.incoresoft.license.LicenseManager;
import com.incoresoft.license.utils.LicenseValidationManager;

public class LicenseModule
extends AbstractModule {
    @Override
    protected void configure() {
        this.bind(LicenseValidationManager.class).asEagerSingleton();
        this.bind(LicenseManager.class).asEagerSingleton();
        this.bind(LicenseRepository.class).asEagerSingleton();
        this.bind(LicenseDailyChecker.class).asEagerSingleton();
    }
}

