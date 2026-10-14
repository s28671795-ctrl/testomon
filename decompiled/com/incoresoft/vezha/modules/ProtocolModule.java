/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.incoresoft.http.server.JavalinAvailabilityInterceptor;
import com.incoresoft.loading.AppLoadingListener;
import com.incoresoft.loading.LoadingManager;
import com.incoresoft.middleware.connector.OnStatusChangeListener;
import com.incoresoft.sessions.SessionHandlerImpl;
import com.incoresoft.vezha.loading.VezhaLoadingManager;
import com.incoresoft.vezha.protocol.server.HttpServerInitActionsExecutor;
import com.incoresoft.vezha.protocol.server.JavalinExceptionMapper;
import com.incoresoft.vezha.protocol.server.JavalinManager;
import com.incoresoft.vezha.protocol.server.VezhaSessionHandler;
import io.javalin.Javalin;
import org.eclipse.jetty.server.session.SessionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProtocolModule
extends AbstractModule {
    private static final Logger log = LoggerFactory.getLogger(ProtocolModule.class);

    @Override
    protected void configure() {
        this.bind(VezhaSessionHandler.class).asEagerSingleton();
        this.bind(SessionHandler.class).to(VezhaSessionHandler.class);
        this.bind(SessionHandlerImpl.class).to(VezhaSessionHandler.class);
        this.bind(JavalinExceptionMapper.class).asEagerSingleton();
        this.bind(VezhaLoadingManager.class).asEagerSingleton();
        this.bind(LoadingManager.class).to(VezhaLoadingManager.class);
        this.bind(AppLoadingListener.class).to(VezhaLoadingManager.class);
        this.bind(OnStatusChangeListener.class).to(LoadingManager.class);
        this.bind(JavalinManager.class).asEagerSingleton();
        this.bind(JavalinAvailabilityInterceptor.class).asEagerSingleton();
        this.bind(HttpServerInitActionsExecutor.class).asEagerSingleton();
    }

    @Provides
    @Singleton
    public Javalin provideJavalin(JavalinManager javalinManager) {
        return javalinManager.server();
    }
}

