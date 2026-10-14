/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.modules;

import com.incoresoft.vezha.auth.AuthUserRetriever;
import com.incoresoft.vezha.auth.IntegratedAuthAccessManager;
import com.incoresoft.vezha.auth.IntegratedAuthUserRetriever;
import com.incoresoft.vezha.auth.JwtTokenDecoder;
import com.incoresoft.vezha.models.streams.strategies.IntegratedStreamDeletionStrategy;
import com.incoresoft.vezha.models.streams.strategies.StreamDeletionStrategy;
import com.incoresoft.vezha.modules.AuthModule;
import io.javalin.security.AccessManager;

public class IntegratedAuthModule
extends AuthModule {
    @Override
    protected void configure() {
        this.bind(JwtTokenDecoder.class).asEagerSingleton();
        this.bind(AuthUserRetriever.class).to(IntegratedAuthUserRetriever.class);
        this.bind(AccessManager.class).to(IntegratedAuthAccessManager.class);
        this.bind(StreamDeletionStrategy.class).to(IntegratedStreamDeletionStrategy.class).asEagerSingleton();
    }
}

