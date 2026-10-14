/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.sessions;

import com.google.inject.Inject;
import com.incoresoft.config.ConfigManager;
import com.incoresoft.config.models.DatabaseConfig;
import com.incoresoft.database.settings.DatabaseUrlParsingManager;
import com.incoresoft.middleware.database.DBType;
import com.incoresoft.middleware.database.DatabaseConnector;
import com.incoresoft.sessions.SessionCacheImpl;
import com.incoresoft.sessions.store.SqlServerDatabaseAdaptor;
import jakarta.servlet.http.HttpSession;
import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import org.eclipse.jetty.http.HttpCookie;
import org.eclipse.jetty.server.session.DatabaseAdaptor;
import org.eclipse.jetty.server.session.JDBCSessionDataStore;
import org.eclipse.jetty.server.session.JDBCSessionDataStoreFactory;
import org.eclipse.jetty.server.session.Session;
import org.eclipse.jetty.server.session.SessionHandler;

public class SessionHandlerImpl
extends SessionHandler {
    public static final Duration SESSION_MAX_AGE = Duration.ofDays(1L);
    public static final Duration SESSION_REFRESH_AGE = Duration.ofMinutes(15L);
    protected final SessionCacheImpl sessionCache;

    @Inject
    public SessionHandlerImpl(ConfigManager<?> configManager, DatabaseConnector databaseConnector, DatabaseUrlParsingManager databaseUrlParsingManager) {
        this.setHttpOnly(false);
        this.setSessionCookie("sid");
        if (configManager.isDebugMode()) {
            this.setSameSite(HttpCookie.SameSite.NONE);
            this.setSecureRequestOnly(true);
        } else {
            this.setSameSite(HttpCookie.SameSite.LAX);
        }
        this.setRefreshCookieAge((int)SESSION_REFRESH_AGE.toSeconds());
        this.getSessionCookieConfig().setMaxAge((int)SESSION_MAX_AGE.toSeconds());
        this.setMaxInactiveInterval((int)SESSION_MAX_AGE.toSeconds());
        this.sessionCache = new SessionCacheImpl(this, new ConcurrentHashMap<String, Session>());
        DatabaseConfig databaseConfig = configManager.getConfig().getDatabase();
        JDBCSessionDataStoreFactory sessionStoreFactory = SessionHandlerImpl.getJdbcDataStoreFactory(databaseConnector, databaseConfig, databaseUrlParsingManager);
        this.sessionCache.setSessionDataStore(sessionStoreFactory.getSessionDataStore(this));
        this.setSessionCache(this.sessionCache);
    }

    public void invalidateUserSessions(HttpSession current, int userId) {
        this.sessionCache.getSessions().stream().filter(it -> !Objects.equals(it.getId(), current.getId())).filter(it -> Objects.equals(it.getAttribute("user_id"), userId)).map(Session::getId).forEach(this::invalidate);
    }

    private static JDBCSessionDataStoreFactory getJdbcDataStoreFactory(DatabaseConnector databaseConnector, DatabaseConfig databaseConfig, DatabaseUrlParsingManager databaseUrlParsingManager) {
        DatabaseAdaptor databaseAdaptor = databaseConnector.getDBType() == DBType.sqlserver ? new SqlServerDatabaseAdaptor() : new DatabaseAdaptor();
        databaseAdaptor.setDatasource(databaseConnector.getDataSource());
        JDBCSessionDataStoreFactory jdbcSessionDataStoreFactory = new JDBCSessionDataStoreFactory();
        jdbcSessionDataStoreFactory.setDatabaseAdaptor(databaseAdaptor);
        jdbcSessionDataStoreFactory.setSessionTableSchema(SessionHandlerImpl.getSessionTableSchema(databaseConnector, databaseConfig, databaseUrlParsingManager));
        return jdbcSessionDataStoreFactory;
    }

    private static JDBCSessionDataStore.SessionTableSchema getSessionTableSchema(DatabaseConnector databaseConnector, DatabaseConfig databaseConfig, DatabaseUrlParsingManager databaseUrlParsingManager) {
        JDBCSessionDataStore.SessionTableSchema schema = new JDBCSessionDataStore.SessionTableSchema();
        if (databaseConnector.getDBType() == DBType.sqlserver) {
            schema.setSchemaName("dbo");
        } else if (databaseConnector.getDBType() == DBType.mysql) {
            schema.setCatalogName(databaseUrlParsingManager.parseUrl(databaseConfig.getUrl()).getDatabaseName());
        } else if (databaseConnector.getDBType() == DBType.postgresql) {
            schema.setSchemaName(databaseConfig.getSchemaName());
        }
        return schema;
    }
}

