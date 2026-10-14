/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.protocol.server;

import com.google.inject.Inject;
import com.incoresoft.config.ConfigManager;
import com.incoresoft.database.settings.DatabaseUrlParsingManager;
import com.incoresoft.middleware.database.DatabaseConnector;
import com.incoresoft.sessions.SessionHandlerImpl;
import org.eclipse.jetty.http.HttpCookie;

public class VezhaSessionHandler
extends SessionHandlerImpl {
    @Inject
    public VezhaSessionHandler(ConfigManager<?> configManager, DatabaseConnector databaseConnector, DatabaseUrlParsingManager databaseUrlParsingManager) {
        super(configManager, databaseConnector, databaseUrlParsingManager);
        this.setSessionCookie("vezha_sid");
        this.setSecureRequestOnly(false);
        this.setSameSite(HttpCookie.SameSite.LAX);
    }
}

