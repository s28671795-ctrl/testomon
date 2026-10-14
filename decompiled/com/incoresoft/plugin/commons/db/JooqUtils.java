/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.db;

import org.jooq.Log;
import org.jooq.conf.Settings;
import org.jooq.tools.JooqLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JooqUtils {
    private static final Logger log = LoggerFactory.getLogger(JooqUtils.class);
    public static Settings JOOQ_SETTINGS;

    private static Log.Level getCurrentThreshold() {
        Log.Level currentThreshold = Log.Level.INFO;
        if (log.isTraceEnabled()) {
            currentThreshold = Log.Level.TRACE;
        } else if (log.isDebugEnabled()) {
            currentThreshold = Log.Level.DEBUG;
        } else if (log.isInfoEnabled()) {
            currentThreshold = Log.Level.INFO;
        } else if (log.isWarnEnabled()) {
            currentThreshold = Log.Level.WARN;
        } else if (log.isErrorEnabled()) {
            currentThreshold = Log.Level.ERROR;
        }
        return currentThreshold;
    }

    static {
        JooqLogger.globalThreshold(JooqUtils.getCurrentThreshold());
        JOOQ_SETTINGS = new Settings().withRenderCatalog(false).withRenderSchema(false);
    }
}

