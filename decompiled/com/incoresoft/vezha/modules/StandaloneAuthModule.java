/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.modules;

import com.incoresoft.vezha.auth.AuthUserRetriever;
import com.incoresoft.vezha.auth.StandaloneAuthAccessManager;
import com.incoresoft.vezha.auth.StandaloneAuthUserRetriever;
import com.incoresoft.vezha.models.streams.strategies.StandaloneStreamDeletionStrategy;
import com.incoresoft.vezha.models.streams.strategies.StreamDeletionStrategy;
import com.incoresoft.vezha.modules.AuthModule;
import io.javalin.security.AccessManager;

public class StandaloneAuthModule
extends AuthModule {
    @Override
    protected void configure() {
        this.bind(AuthUserRetriever.class).to(StandaloneAuthUserRetriever.class);
        this.bind(AccessManager.class).to(StandaloneAuthAccessManager.class);
        this.bind(StreamDeletionStrategy.class).to(StandaloneStreamDeletionStrategy.class).asEagerSingleton();
    }
}

