/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.protocol.server;

import com.google.inject.Inject;
import com.incoresoft.config.ConfigManager;
import com.incoresoft.http.server.UnrestrictedAccessManager;
import com.incoresoft.middleware.analytics.AnalyticsStatus;
import com.incoresoft.middleware.analytics.AnalyticsType;
import com.incoresoft.middleware.analytics.AnalyticsTypes;
import com.incoresoft.plugin.commons.http.FrontController;
import com.incoresoft.plugin.commons.params.models.GroupBy;
import com.incoresoft.plugin.commons.utils.ContentTypeUtils;
import com.incoresoft.plugin.commons.utils.DateTimeUtils;
import com.incoresoft.vezha.protocol.server.JsonMapperImpl;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.HandlerType;
import io.javalin.http.NotFoundResponse;
import io.javalin.http.staticfiles.Location;
import io.javalin.security.AccessManager;
import io.javalin.util.ConcurrencyUtil;
import io.javalin.validation.JavalinValidation;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.server.session.SessionHandler;

public class JavalinManager {
    private static final Map<String, String> cacheControl = Map.of("Cache-Control", "public, max-age=3600");
    private static final Set<String> filesExcludedFromCaching = Set.of("app.js");
    private final ConfigManager<?> configManager;
    private final SessionHandler sessionHandler;
    private final AtomicReference<AccessManager> accessManager = new AtomicReference();

    @Inject
    public void init() {
        this.accessManager.set(new UnrestrictedAccessManager());
    }

    public Javalin server() {
        ConcurrencyUtil.INSTANCE.setUseLoom(false);
        Javalin app = Javalin.create(this::initConfig);
        this.initResourceEndpoints(app);
        JavalinManager.initValidators();
        if (this.configManager.isDebugMode()) {
            app.addHandler(HandlerType.OPTIONS, "/*", ctx -> {
                String origin = ctx.headerMap().entrySet().stream().filter(it -> ((String)it.getKey()).equalsIgnoreCase("Origin")).map(Map.Entry::getValue).findAny().orElse("http://localhost:8000");
                ctx.header("Access-Control-Allow-Origin", origin);
                ctx.header("Access-Control-Allow-Credentials", "true");
                ctx.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, PATCH");
                ctx.header("Access-Control-Allow-Headers", "Content-Type, Authorization, X-Requested-With");
                ctx.header("Access-Control-Max-Age", "1728000");
                ctx.header("Content-Type", "text/plain charset=UTF-8");
                ctx.header("Content-Length", "0");
                ctx.status(204);
            });
        }
        return app;
    }

    private void initConfig(JavalinConfig config) {
        config.jetty.sessionHandler(() -> this.sessionHandler);
        config.http.maxRequestSize = 20000000L;
        config.http.defaultContentType = "application/json;charset=utf-8";
        config.plugins.enableCors(cors -> cors.add(it -> {
            it.allowCredentials = true;
            it.reflectClientOrigin = true;
        }));
        config.staticFiles.add(fileConfig -> {
            fileConfig.directory = "front/host";
            fileConfig.location = Location.CLASSPATH;
            fileConfig.headers = cacheControl;
        });
        config.staticFiles.add(fileConfig -> {
            fileConfig.hostedPath = "/loading";
            fileConfig.directory = "front/loading";
            fileConfig.location = Location.CLASSPATH;
            fileConfig.headers = cacheControl;
        });
        config.staticFiles.add(fileConfig -> {
            fileConfig.directory = "images";
            fileConfig.location = Location.EXTERNAL;
            fileConfig.headers = cacheControl;
        });
        config.staticFiles.add(fileConfig -> {
            fileConfig.hostedPath = "/sounds";
            fileConfig.directory = "sounds";
            fileConfig.location = Location.EXTERNAL;
            fileConfig.headers = cacheControl;
        });
        config.staticFiles.enableWebjars();
        config.showJavalinBanner = false;
        config.jsonMapper(new JsonMapperImpl());
        config.accessManager((handler, ctx, routeRoles) -> this.accessManager.get().manage(handler, ctx, routeRoles));
        config.jetty.wsFactoryConfig(servletFactory -> servletFactory.setIdleTimeout(Duration.ofDays(1L)));
        config.spaRoot.addFile("", "front/host/index.html");
        config.jetty.contextHandlerConfig(contextHandler -> contextHandler.setMaxFormKeys(2000));
    }

    private void initResourceEndpoints(Javalin app) {
        app.get("app.js", ctx -> {
            String path = ctx.path();
            ctx.contentType(ContentTypeUtils.guessContentType(path));
            Optional.ofNullable(FrontController.class.getClassLoader().getResourceAsStream("front/host" + path)).ifPresentOrElse(ctx::result, NotFoundResponse::new);
        });
        app.get("resources/core/*", ctx -> {
            String path = ctx.path().replaceFirst("/resources/core/", "");
            ctx.contentType(ContentTypeUtils.guessContentType(path));
            if (this.fileMustBeCached(path)) {
                cacheControl.forEach(ctx::header);
            }
            Optional.ofNullable(FrontController.class.getClassLoader().getResourceAsStream("front/core/" + path)).ifPresentOrElse(ctx::result, NotFoundResponse::new);
        });
        app.get("resources/styleguide/*", ctx -> {
            String path = ctx.path().replaceFirst("/resources/styleguide/", "");
            ctx.contentType(ContentTypeUtils.guessContentType(path));
            if (this.fileMustBeCached(path)) {
                cacheControl.forEach(ctx::header);
            }
            Optional.ofNullable(FrontController.class.getClassLoader().getResourceAsStream("front/styleguide/" + path)).ifPresentOrElse(ctx::result, NotFoundResponse::new);
        });
    }

    private boolean fileMustBeCached(String path) {
        return !filesExcludedFromCaching.contains(path);
    }

    private static void initValidators() {
        JavalinValidation.register(Integer.class, it -> StringUtils.isEmpty(it) ? null : Integer.valueOf(Integer.parseInt(it)));
        JavalinValidation.register(Long.class, it -> StringUtils.isEmpty(it) ? null : Long.valueOf(Long.parseLong(it)));
        JavalinValidation.register(AnalyticsType.class, it -> StringUtils.isEmpty(it) ? null : AnalyticsTypes.valueOf(it));
        JavalinValidation.register(AnalyticsStatus.class, it -> StringUtils.isEmpty(it) ? null : AnalyticsStatus.valueOf(it));
        JavalinValidation.register(Instant.class, s2 -> {
            try {
                return DateTimeUtils.parseDateTime(s2);
            }
            catch (DateTimeException e) {
                return null;
            }
        });
        JavalinValidation.register(GroupBy.class, s2 -> {
            try {
                return GroupBy.valueOf(s2);
            }
            catch (IllegalArgumentException e) {
                return null;
            }
        });
        JavalinValidation.register(UUID.class, it -> {
            try {
                return UUID.fromString(it);
            }
            catch (IllegalArgumentException e) {
                return null;
            }
        });
    }

    public void setAccessManager(AccessManager accessManager) {
        this.accessManager.set(accessManager);
    }

    @Inject
    public JavalinManager(ConfigManager<?> configManager, SessionHandler sessionHandler) {
        this.configManager = configManager;
        this.sessionHandler = sessionHandler;
    }
}

