/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.util.log;

import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.log.Slf4jLogger;
import org.slf4j.LoggerFactory;

public class Log {
    @Deprecated
    public static final String EXCEPTION = "EXCEPTION";

    @Deprecated
    public static Logger getLogger(Class<?> clazz) {
        return new Slf4jLogger(LoggerFactory.getLogger(clazz));
    }

    @Deprecated
    public static Logger getLogger(String name) {
        return new Slf4jLogger(LoggerFactory.getLogger(name));
    }

    @Deprecated
    public static Logger getRootLogger() {
        return new Slf4jLogger(LoggerFactory.getLogger(""));
    }

    @Deprecated
    public static Logger getLog() {
        return Log.getRootLogger();
    }

    @Deprecated
    public static void setLog(Logger log) {
    }
}

