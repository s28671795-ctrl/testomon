/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.incoresoft.config.models.RedisConfig;
import com.incoresoft.loading.AppLoadingListener;
import com.incoresoft.loading.LoadingStage;
import com.incoresoft.loading.LoadingStatus;
import com.incoresoft.vezha.redis.RedisConnector;

public class RedisModule
extends AbstractModule {
    private final String redisHost;
    private final int redisPort;

    public RedisModule(RedisConfig config) {
        this.redisHost = config.getHost();
        this.redisPort = config.getPort();
    }

    @Provides
    @Inject
    @Singleton
    public RedisConnector provideRedisConnector(AppLoadingListener loadingListener) {
        loadingListener.onCurrentStageStateChange(LoadingStage.REDIS, LoadingStatus.PROCESSING, null);
        RedisConnector redisConnector = new RedisConnector(this.redisHost, this.redisPort);
        LoadingStatus status = redisConnector.isConnected() ? LoadingStatus.OK : LoadingStatus.ERROR;
        String message = null;
        if (status == LoadingStatus.ERROR) {
            message = redisConnector.getLastError().getMessage();
        }
        loadingListener.onCurrentStageStateChange(LoadingStage.REDIS, status, message);
        return redisConnector;
    }
}

