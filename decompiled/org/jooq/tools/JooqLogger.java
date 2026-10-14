/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.tools;

import java.util.concurrent.atomic.AtomicInteger;
import org.jooq.Log;
import org.jooq.tools.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class JooqLogger
implements Log {
    private static volatile Log.Level globalThreshold = Log.Level.TRACE;
    private Logger slf4j;
    private java.util.logging.Logger util;
    private boolean supportsTrace = true;
    private boolean supportsDebug = true;
    private boolean supportsInfo = true;
    private boolean supportsWarn = true;
    private final AtomicInteger limitMessages;

    public JooqLogger(int limitMessages) {
        this.limitMessages = limitMessages >= 0 ? new AtomicInteger(limitMessages) : null;
    }

    public static JooqLogger getLogger(Class<?> clazz) {
        return JooqLogger.getLogger(clazz, null, -1);
    }

    public static JooqLogger getLogger(String name) {
        return JooqLogger.getLogger(null, name, -1);
    }

    public static JooqLogger getLogger(Class<?> clazz, int limitMessages) {
        return JooqLogger.getLogger(clazz, null, limitMessages);
    }

    public static JooqLogger getLogger(String name, int limitMessages) {
        return JooqLogger.getLogger(null, name, limitMessages);
    }

    public static JooqLogger getLogger(Class<?> clazz, String nameSuffix, int limitMessages) {
        String name = clazz != null && nameSuffix != null ? clazz.getName() + "." + nameSuffix : (clazz != null ? clazz.getName() : nameSuffix);
        JooqLogger result = new JooqLogger(limitMessages);
        try {
            result.slf4j = LoggerFactory.getLogger(name);
        }
        catch (Throwable e2) {
            result.util = java.util.logging.Logger.getLogger(name);
        }
        try {
            result.isInfoEnabled();
        }
        catch (Throwable e) {
            result.supportsInfo = false;
        }
        try {
            result.isDebugEnabled();
        }
        catch (Throwable e) {
            result.supportsDebug = false;
        }
        try {
            result.isTraceEnabled();
        }
        catch (Throwable e) {
            result.supportsTrace = false;
        }
        return result;
    }

    private final void decrementLimitAndDo(Runnable runnable2) {
        if (this.limitMessages == null || this.limitMessages.getAndUpdate(i -> Math.max(i - 1, 0)) > 0) {
            runnable2.run();
        }
    }

    @Override
    public boolean isTraceEnabled() {
        if (!globalThreshold.supports(Log.Level.TRACE)) {
            return false;
        }
        if (!this.supportsTrace) {
            return false;
        }
        if (this.limitMessages != null && this.limitMessages.get() == 0) {
            return false;
        }
        if (this.slf4j != null) {
            return this.slf4j.isTraceEnabled();
        }
        return this.util.isLoggable(java.util.logging.Level.FINER);
    }

    @Override
    public void trace(Object message) {
        this.trace(message, (Object)null);
    }

    @Override
    public void trace(Object message, Object details) {
        this.decrementLimitAndDo(() -> {
            if (!globalThreshold.supports(Log.Level.TRACE)) {
                return;
            }
            if (this.slf4j != null) {
                this.slf4j.trace(this.getMessage(message, details));
            } else {
                this.util.finer(this.getMessage(message, details));
            }
        });
    }

    @Override
    public void trace(Object message, Throwable throwable) {
        this.trace(message, null, throwable);
    }

    @Override
    public void trace(Object message, Object details, Throwable throwable) {
        this.decrementLimitAndDo(() -> {
            if (!globalThreshold.supports(Log.Level.TRACE)) {
                return;
            }
            if (this.slf4j != null) {
                this.slf4j.trace(this.getMessage(message, details), throwable);
            } else {
                this.util.log(java.util.logging.Level.FINER, this.getMessage(message, details), throwable);
            }
        });
    }

    @Override
    public boolean isDebugEnabled() {
        if (!globalThreshold.supports(Log.Level.DEBUG)) {
            return false;
        }
        if (!this.supportsDebug) {
            return false;
        }
        if (this.limitMessages != null && this.limitMessages.get() == 0) {
            return false;
        }
        if (this.slf4j != null) {
            return this.slf4j.isDebugEnabled();
        }
        return this.util.isLoggable(java.util.logging.Level.FINE);
    }

    @Override
    public void debug(Object message) {
        this.debug(message, (Object)null);
    }

    @Override
    public void debug(Object message, Object details) {
        this.decrementLimitAndDo(() -> {
            if (!globalThreshold.supports(Log.Level.DEBUG)) {
                return;
            }
            if (this.slf4j != null) {
                this.slf4j.debug(this.getMessage(message, details));
            } else {
                this.util.fine(this.getMessage(message, details));
            }
        });
    }

    @Override
    public void debug(Object message, Throwable throwable) {
        this.debug(message, null, throwable);
    }

    @Override
    public void debug(Object message, Object details, Throwable throwable) {
        this.decrementLimitAndDo(() -> {
            if (!globalThreshold.supports(Log.Level.DEBUG)) {
                return;
            }
            if (this.slf4j != null) {
                this.slf4j.debug(this.getMessage(message, details), throwable);
            } else {
                this.util.log(java.util.logging.Level.FINE, this.getMessage(message, details), throwable);
            }
        });
    }

    @Override
    public boolean isInfoEnabled() {
        if (!globalThreshold.supports(Log.Level.INFO)) {
            return false;
        }
        if (!this.supportsInfo) {
            return false;
        }
        if (this.limitMessages != null && this.limitMessages.get() == 0) {
            return false;
        }
        if (this.slf4j != null) {
            return this.slf4j.isInfoEnabled();
        }
        return this.util.isLoggable(java.util.logging.Level.INFO);
    }

    @Override
    public void info(Object message) {
        this.info(message, (Object)null);
    }

    @Override
    public void info(Object message, Object details) {
        this.decrementLimitAndDo(() -> {
            if (!globalThreshold.supports(Log.Level.INFO)) {
                return;
            }
            if (this.slf4j != null) {
                this.slf4j.info(this.getMessage(message, details));
            } else {
                this.util.info(this.getMessage(message, details));
            }
        });
    }

    @Override
    public void info(Object message, Throwable throwable) {
        this.info(message, null, throwable);
    }

    @Override
    public void info(Object message, Object details, Throwable throwable) {
        this.decrementLimitAndDo(() -> {
            if (!globalThreshold.supports(Log.Level.INFO)) {
                return;
            }
            if (this.slf4j != null) {
                this.slf4j.info(this.getMessage(message, details), throwable);
            } else {
                this.util.log(java.util.logging.Level.INFO, this.getMessage(message, details), throwable);
            }
        });
    }

    @Override
    public boolean isWarnEnabled() {
        if (!globalThreshold.supports(Log.Level.WARN)) {
            return false;
        }
        if (!this.supportsWarn) {
            return false;
        }
        if (this.limitMessages != null && this.limitMessages.get() == 0) {
            return false;
        }
        if (this.slf4j != null) {
            return this.slf4j.isWarnEnabled();
        }
        return this.util.isLoggable(java.util.logging.Level.WARNING);
    }

    @Override
    public void warn(Object message) {
        this.warn(message, (Object)null);
    }

    @Override
    public void warn(Object message, Object details) {
        this.decrementLimitAndDo(() -> {
            if (!globalThreshold.supports(Log.Level.WARN)) {
                return;
            }
            if (this.slf4j != null) {
                this.slf4j.warn(this.getMessage(message, details));
            } else {
                this.util.warning(this.getMessage(message, details));
            }
        });
    }

    @Override
    public void warn(Object message, Throwable throwable) {
        this.warn(message, null, throwable);
    }

    @Override
    public void warn(Object message, Object details, Throwable throwable) {
        this.decrementLimitAndDo(() -> {
            if (!globalThreshold.supports(Log.Level.WARN)) {
                return;
            }
            if (this.slf4j != null) {
                this.slf4j.warn(this.getMessage(message, details), throwable);
            } else {
                this.util.log(java.util.logging.Level.WARNING, this.getMessage(message, details), throwable);
            }
        });
    }

    @Override
    public void error(Object message) {
        this.error(message, (Object)null);
    }

    @Override
    public void error(Object message, Object details) {
        this.decrementLimitAndDo(() -> {
            if (!globalThreshold.supports(Log.Level.ERROR)) {
                return;
            }
            if (this.slf4j != null) {
                this.slf4j.error(this.getMessage(message, details));
            } else {
                this.util.severe(this.getMessage(message, details));
            }
        });
    }

    @Override
    public void error(Object message, Throwable throwable) {
        this.error(message, null, throwable);
    }

    @Override
    public void error(Object message, Object details, Throwable throwable) {
        this.decrementLimitAndDo(() -> {
            if (!globalThreshold.supports(Log.Level.ERROR)) {
                return;
            }
            if (this.slf4j != null) {
                this.slf4j.error(this.getMessage(message, details), throwable);
            } else {
                this.util.log(java.util.logging.Level.SEVERE, this.getMessage(message, details), throwable);
            }
        });
    }

    @Override
    public void log(Log.Level level, Object message) {
        this.log(level, message, (Object)null);
    }

    @Override
    public void log(Log.Level level, Object message, Object details) {
        switch (level) {
            case TRACE: {
                this.trace(message, details);
                break;
            }
            case DEBUG: {
                this.debug(message, details);
                break;
            }
            case INFO: {
                this.info(message, details);
                break;
            }
            case WARN: {
                this.warn(message, details);
                break;
            }
            case ERROR: 
            case FATAL: {
                this.error(message, details);
            }
        }
    }

    @Override
    public void log(Log.Level level, Object message, Throwable throwable) {
        this.log(level, message, null, throwable);
    }

    @Override
    public void log(Log.Level level, Object message, Object details, Throwable throwable) {
        switch (level) {
            case TRACE: {
                this.trace(message, details, throwable);
                break;
            }
            case DEBUG: {
                this.debug(message, details, throwable);
                break;
            }
            case INFO: {
                this.info(message, details, throwable);
                break;
            }
            case WARN: {
                this.warn(message, details, throwable);
                break;
            }
            case ERROR: 
            case FATAL: {
                this.error(message, details, throwable);
            }
        }
    }

    private String getMessage(Object message, Object details) {
        StringBuilder sb = new StringBuilder();
        sb.append(StringUtils.rightPad(String.valueOf(message), 25));
        if (details != null) {
            sb.append(": ");
            sb.append(details);
        }
        return sb.toString();
    }

    public static void globalThreshold(Level level) {
        switch (level) {
            case TRACE: {
                JooqLogger.globalThreshold(Log.Level.TRACE);
                break;
            }
            case DEBUG: {
                JooqLogger.globalThreshold(Log.Level.DEBUG);
                break;
            }
            case INFO: {
                JooqLogger.globalThreshold(Log.Level.INFO);
                break;
            }
            case WARN: {
                JooqLogger.globalThreshold(Log.Level.WARN);
                break;
            }
            case ERROR: {
                JooqLogger.globalThreshold(Log.Level.ERROR);
                break;
            }
            case FATAL: {
                JooqLogger.globalThreshold(Log.Level.FATAL);
            }
        }
    }

    public static void globalThreshold(Log.Level level) {
        globalThreshold = level;
    }

    public static void initSimpleFormatter() {
        if (System.getProperty("java.util.logging.SimpleFormatter.format") == null) {
            System.setProperty("java.util.logging.SimpleFormatter.format", "%1$tH:%1$tM:%1$tS %4$s %5$s%6$s%n");
        }
    }

    @Deprecated
    public static enum Level {
        TRACE,
        DEBUG,
        INFO,
        WARN,
        ERROR,
        FATAL;


        public boolean supports(Level level) {
            return this.ordinal() <= level.ordinal();
        }
    }
}

