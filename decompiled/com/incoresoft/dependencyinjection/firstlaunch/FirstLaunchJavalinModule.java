/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dependencyinjection.firstlaunch;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.incoresoft.config.ConfigManager;
import com.incoresoft.config.exceptions.StorageConnectionException;
import com.incoresoft.dependencyinjection.exceptions.DatabaseInitializationException;
import com.incoresoft.dependencyinjection.exceptions.SettingsSavingException;
import com.incoresoft.errors.CoreErrors;
import com.incoresoft.http.server.JacksonJavalinJsonMapper;
import com.incoresoft.middleware.database.SQLConnectionException;
import com.incoresoft.middleware.http.errors.Error;
import com.incoresoft.middleware.http.exceptions.ValidationException;
import com.incoresoft.plugin.commons.http.answers.ErrorAnswer;
import com.incoresoft.plugin.commons.http.error.BaseError;
import io.javalin.Javalin;
import io.javalin.http.HttpStatus;
import io.javalin.http.staticfiles.Location;
import io.javalin.plugin.bundled.CorsPluginConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FirstLaunchJavalinModule
extends AbstractModule {
    private static final Logger log = LoggerFactory.getLogger(FirstLaunchJavalinModule.class);

    @Inject
    @Provides
    @Singleton
    public Javalin provideJavalin(ConfigManager<?> settings) {
        return Javalin.create(config -> {
            config.http.defaultContentType = "application/json";
            config.staticFiles.add("front/first-launch", Location.CLASSPATH);
            config.plugins.enableCors(cors -> cors.add(CorsPluginConfig::anyHost));
            config.accessManager((handler, ctx, permittedRoles) -> handler.handle(ctx));
            config.spaRoot.addFile("", "front/first-launch/index.html");
            config.jsonMapper(new JacksonJavalinJsonMapper());
        }).exception(Exception.class, (e, ctx) -> {
            log.error("Exception has occurred during http request.", e);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR);
            ctx.json(new ErrorAnswer((Error)BaseError.Error, new Object[0]));
        }).exception(SQLConnectionException.class, (e, ctx) -> ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorAnswer(e::getErrorKey, new Object[0]))).exception(DatabaseInitializationException.class, (e, ctx) -> ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorAnswer((Error)CoreErrors.DB_INITIALIZATION_FAILED, new Object[0]))).exception(SettingsSavingException.class, (e, ctx) -> ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorAnswer((Error)CoreErrors.SETTINGS_SAVING_FAILED, new Object[0]))).exception(ValidationException.class, (e, ctx) -> ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorAnswer((ValidationException)e))).exception(StorageConnectionException.class, (e, ctx) -> ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorAnswer((Error)CoreErrors.STORAGE_CONNECTION_FAILED, new Object[0])));
    }
}

