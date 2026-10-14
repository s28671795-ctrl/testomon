/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.launch;

import com.alibaba.fastjson.parser.ParserConfig;
import com.google.inject.CreationException;
import com.google.inject.Injector;
import com.incoresoft.audit.AuditEventActionsManager;
import com.incoresoft.dependencyinjection.JOOQModule;
import com.incoresoft.loading.LoadingManager;
import com.incoresoft.loading.LoadingStage;
import com.incoresoft.loading.LoadingStatus;
import com.incoresoft.middleware.audit.AuditEventAction;
import com.incoresoft.middleware.audit.AuditEventCategory;
import com.incoresoft.middleware.database.DatabaseConnector;
import com.incoresoft.plugin.commons.validation.BeanValidatorImpl;
import com.incoresoft.plugin.commons.validation.GuiceValidatorFactory;
import com.incoresoft.vezha.MiddlewareApp;
import com.incoresoft.vezha.analytics.AnalyticsCoreScheduler;
import com.incoresoft.vezha.analytics.servers.ServersMonitor;
import com.incoresoft.vezha.config.VezhaConfigManager;
import com.incoresoft.vezha.config.models.VezhaConfig;
import com.incoresoft.vezha.dto.audit.deserializers.AuditEventActionDeserializer;
import com.incoresoft.vezha.dto.audit.deserializers.AuditEventCategoryDeserializer;
import com.incoresoft.vezha.license.LicenseUsageManager;
import com.incoresoft.vezha.metrics.MetricsManager;
import com.incoresoft.vezha.modules.AuthModule;
import com.incoresoft.vezha.modules.CorePluginsModule;
import com.incoresoft.vezha.modules.EventManagerGuiceModule;
import com.incoresoft.vezha.modules.IntegratedAuthModule;
import com.incoresoft.vezha.modules.MiddlewareGuiceModule;
import com.incoresoft.vezha.modules.ProtocolModule;
import com.incoresoft.vezha.modules.RedisModule;
import com.incoresoft.vezha.modules.StandaloneAuthModule;
import com.incoresoft.vezha.protocol.server.HttpServerInitActionsExecutor;
import com.incoresoft.vezha.protocol.server.JavalinManager;
import com.incoresoft.vezha.redis.RedisConnector;
import io.javalin.Javalin;
import io.javalin.security.AccessManager;
import java.lang.reflect.Type;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultLaunch {
    private static final Logger log = LoggerFactory.getLogger(DefaultLaunch.class);
    public static final String DI_CONTAINER_ERROR_MESSAGE = "DI container couldn't instantiate one or more classes. Stopping...";

    public static void launch(Injector settingsInjection, String[] args2, Runnable logAppLoaded) {
        try {
            DefaultLaunch.launchInternal(settingsInjection, args2, logAppLoaded);
        }
        catch (Throwable e) {
            log.error("Error during application launch", e);
            System.exit(1);
        }
    }

    public static void launchInternal(Injector settingsInjection, String[] args2, Runnable logAppLoaded) {
        VezhaConfigManager configManager = settingsInjection.getInstance(VezhaConfigManager.class);
        if (args2.length > 0 && Arrays.asList(args2).contains("debug")) {
            configManager.setDebug(true);
        }
        Injector protocolPluginsInjector = null;
        try {
            protocolPluginsInjector = settingsInjection.createChildInjector(new CorePluginsModule(), new RedisModule(((VezhaConfig)configManager.getConfig()).getRedis()), new ProtocolModule());
        }
        catch (CreationException e) {
            log.error(DI_CONTAINER_ERROR_MESSAGE, e);
            System.exit(1);
        }
        Javalin app = protocolPluginsInjector.getInstance(Javalin.class);
        LoadingManager loadingManager = protocolPluginsInjector.getInstance(LoadingManager.class);
        loadingManager.init(app);
        app.start(((VezhaConfig)configManager.getConfig()).getConnectionConfig().getHttpPort());
        String dbChangelogXml = "sql/changelog/db-changelog.xml";
        String vezhaDbChangelogXml = "sql/changelog/vezha-db-changelog.xml";
        DatabaseConnector databaseConnector = protocolPluginsInjector.getInstance(DatabaseConnector.class);
        loadingManager.onStageChanged(LoadingStage.CHECK_DB_STRUCTURE);
        boolean checkedStructure = databaseConnector.checkStructure(dbChangelogXml, MiddlewareApp.class.getClassLoader());
        if (!checkedStructure) {
            String errorMessage = "Could not check and update database structure";
            loadingManager.onCurrentStageStateChange(LoadingStage.WEB_APPLICATION, LoadingStatus.ERROR, errorMessage);
            return;
        }
        checkedStructure = databaseConnector.checkStructure(vezhaDbChangelogXml, MiddlewareApp.class.getClassLoader());
        if (!checkedStructure) {
            String errorMessage = "Could not check and update database structure";
            loadingManager.onCurrentStageStateChange(LoadingStage.WEB_APPLICATION, LoadingStatus.ERROR, errorMessage);
            return;
        }
        Injector jooqInjector = protocolPluginsInjector.createChildInjector(new JOOQModule(databaseConnector));
        AuthModule authModule = configManager.isVmsIntegrationEnabled() ? new IntegratedAuthModule() : new StandaloneAuthModule();
        Injector injector = null;
        try {
            injector = jooqInjector.createChildInjector(new EventManagerGuiceModule(), new MiddlewareGuiceModule(), authModule);
        }
        catch (CreationException e) {
            log.error(DI_CONTAINER_ERROR_MESSAGE, e);
            System.exit(0);
        }
        BeanValidatorImpl validator = injector.getInstance(BeanValidatorImpl.class);
        validator.setFactory(new GuiceValidatorFactory(injector));
        injector.getInstance(RedisConnector.class);
        injector.getInstance(LicenseUsageManager.class);
        AnalyticsCoreScheduler analyticsCoreApp = injector.getInstance(AnalyticsCoreScheduler.class);
        analyticsCoreApp.start(loadingManager);
        loadingManager.onCurrentStageStateChange(LoadingStage.WEB_APPLICATION, LoadingStatus.PROCESSING, null);
        ParserConfig.getGlobalInstance().putDeserializer((Type)((Object)AuditEventAction.class), new AuditEventActionDeserializer(injector.getInstance(AuditEventActionsManager.class)));
        ParserConfig.getGlobalInstance().putDeserializer((Type)((Object)AuditEventCategory.class), new AuditEventCategoryDeserializer(injector.getInstance(AuditEventActionsManager.class)));
        MiddlewareApp middlewareApp = injector.getInstance(MiddlewareApp.class);
        middlewareApp.start(loadingManager);
        JavalinManager javalinManager = injector.getInstance(JavalinManager.class);
        AccessManager accessManager = injector.getInstance(AccessManager.class);
        javalinManager.setAccessManager(accessManager);
        HttpServerInitActionsExecutor initActionsExecutor = injector.getInstance(HttpServerInitActionsExecutor.class);
        initActionsExecutor.init();
        MetricsManager metricsManager = injector.getInstance(MetricsManager.class);
        ServersMonitor serversMonitor = injector.getInstance(ServersMonitor.class);
        metricsManager.init(serversMonitor);
        if (analyticsCoreApp.isStarted()) {
            loadingManager.onAppLoaded(logAppLoaded);
        } else {
            loadingManager.onStageChanged(LoadingStage.DISCOVERING_SERVERS);
            analyticsCoreApp.setAnalyticsLoadingFinishCallback(() -> loadingManager.onAppLoaded(logAppLoaded));
        }
    }

    private DefaultLaunch() {
    }
}

