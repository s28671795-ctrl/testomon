/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.launch;

import com.google.inject.Injector;
import com.incoresoft.config.SetupManager;
import com.incoresoft.dependencyinjection.firstlaunch.FirstLaunchModule;
import com.incoresoft.plugin.commons.validation.BeanValidatorImpl;
import com.incoresoft.plugin.commons.validation.GuiceValidatorFactory;
import io.javalin.Javalin;

public class FirstLaunch {
    public static void launch(Injector settingsInjector, int httpPort, int httpsPort, int natsPort, FirstLaunchModule firstLaunchModule) {
        Injector injector = settingsInjector.createChildInjector(firstLaunchModule);
        BeanValidatorImpl validator = injector.getInstance(BeanValidatorImpl.class);
        validator.setFactory(new GuiceValidatorFactory(injector));
        SetupManager setupManager = injector.getInstance(SetupManager.class);
        setupManager.setHttpPort(httpPort);
        setupManager.setHttpsPort(httpsPort);
        setupManager.setNatsPort(natsPort);
        Javalin javalin = injector.getInstance(Javalin.class);
        javalin.start(httpPort);
    }
}

