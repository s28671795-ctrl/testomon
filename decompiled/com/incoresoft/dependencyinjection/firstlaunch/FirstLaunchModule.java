/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dependencyinjection.firstlaunch;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.incoresoft.config.SetupManager;
import com.incoresoft.core.ApplicationReloadManager;
import com.incoresoft.dependencyinjection.firstlaunch.FirstLaunchJavalinModule;
import com.incoresoft.plugin.commons.validation.BeanValidator;
import com.incoresoft.plugin.commons.validation.BeanValidatorImpl;

public abstract class FirstLaunchModule
extends AbstractModule {
    @Override
    protected void configure() {
        this.install(new FirstLaunchJavalinModule());
        this.bind(ApplicationReloadManager.class).asEagerSingleton();
        this.bind(BeanValidatorImpl.class).asEagerSingleton();
        this.bind(BeanValidator.class).to(BeanValidatorImpl.class);
        this.bind(this.setupManagerClass()).asEagerSingleton();
        this.bind(SetupManager.class).to(this.setupManagerClass());
        this.bind(new TypeLiteral<SetupManager<?>>(this){}).to(this.setupManagerClass());
    }

    protected abstract Class<? extends SetupManager<?>> setupManagerClass();
}

