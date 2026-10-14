/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dependencyinjection;

import com.google.inject.AbstractModule;
import com.incoresoft.cleaner.CleanerTasksRepositoryImpl;
import com.incoresoft.dependencyinjection.ControllersModule;
import com.incoresoft.dependencyinjection.LicenseModule;
import com.incoresoft.dependencyinjection.ManagersModule;
import com.incoresoft.dependencyinjection.VezhaPluginsModule;

public class CompositeModule
extends AbstractModule {
    private final Class<? extends CleanerTasksRepositoryImpl> cleanerImplClass;

    public CompositeModule() {
        this(CleanerTasksRepositoryImpl.class);
    }

    public CompositeModule(Class<? extends CleanerTasksRepositoryImpl> cleanerImplClass) {
        this.cleanerImplClass = cleanerImplClass;
    }

    @Override
    protected void configure() {
        this.install(new VezhaPluginsModule());
        this.install(new ManagersModule(this.cleanerImplClass));
        this.install(new LicenseModule());
        this.install(new ControllersModule());
    }
}

