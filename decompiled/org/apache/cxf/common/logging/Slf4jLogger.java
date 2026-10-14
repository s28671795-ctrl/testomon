/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.logging;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import org.apache.cxf.common.logging.AbstractDelegatingLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.LocationAwareLogger;

public class Slf4jLogger
extends AbstractDelegatingLogger {
    private static final String FQCN = AbstractDelegatingLogger.class.getName();
    private final Logger logger;
    private LocationAwareLogger locationAwareLogger;

    public Slf4jLogger(String name, String resourceBundleName) {
        super(name, resourceBundleName);
        this.logger = LoggerFactory.getLogger(name);
        if (this.logger instanceof LocationAwareLogger) {
            this.locationAwareLogger = (LocationAwareLogger)this.logger;
        }
    }

    @Override
    protected boolean supportsHandlers() {
        return true;
    }

    @Override
    public Level getLevel() {
        Level level = this.logger.isTraceEnabled() ? Level.FINEST : (this.logger.isDebugEnabled() ? Level.FINER : (this.logger.isInfoEnabled() ? Level.INFO : (this.logger.isWarnEnabled() ? Level.WARNING : (this.logger.isErrorEnabled() ? Level.SEVERE : Level.OFF))));
        return level;
    }

    @Override
    public boolean isLoggable(Level level) {
        int i = level.intValue();
        if (i == Level.OFF.intValue()) {
            return false;
        }
        if (i >= Level.SEVERE.intValue()) {
            return this.logger.isErrorEnabled();
        }
        if (i >= Level.WARNING.intValue()) {
            return this.logger.isWarnEnabled();
        }
        if (i >= Level.INFO.intValue()) {
            return this.logger.isInfoEnabled();
        }
        if (i >= Level.FINER.intValue()) {
            return this.logger.isDebugEnabled();
        }
        return this.logger.isTraceEnabled();
    }

    @Override
    protected void internalLogFormatted(String msg, LogRecord record) {
        Level level = record.getLevel();
        Throwable t2 = record.getThrown();
        Handler[] targets = this.getHandlers();
        if (targets != null) {
            for (Handler h2 : targets) {
                h2.publish(record);
            }
        }
        if (!this.getUseParentHandlers()) {
            return;
        }
        if (Level.FINE.equals(level)) {
            if (this.locationAwareLogger == null) {
                this.logger.debug(msg, t2);
            } else {
                this.locationAwareLogger.log(null, FQCN, 10, msg, null, t2);
            }
        } else if (Level.INFO.equals(level)) {
            if (this.locationAwareLogger == null) {
                this.logger.info(msg, t2);
            } else {
                this.locationAwareLogger.log(null, FQCN, 20, msg, null, t2);
            }
        } else if (Level.WARNING.equals(level)) {
            if (this.locationAwareLogger == null) {
                this.logger.warn(msg, t2);
            } else {
                this.locationAwareLogger.log(null, FQCN, 30, msg, null, t2);
            }
        } else if (Level.FINER.equals(level)) {
            if (this.locationAwareLogger == null) {
                this.logger.trace(msg, t2);
            } else {
                this.locationAwareLogger.log(null, FQCN, 10, msg, null, t2);
            }
        } else if (Level.FINEST.equals(level)) {
            if (this.locationAwareLogger == null) {
                this.logger.trace(msg, t2);
            } else {
                this.locationAwareLogger.log(null, FQCN, 0, msg, null, t2);
            }
        } else if (Level.ALL.equals(level)) {
            if (this.locationAwareLogger == null) {
                this.logger.error(msg, t2);
            } else {
                this.locationAwareLogger.log(null, FQCN, 40, msg, null, t2);
            }
        } else if (Level.SEVERE.equals(level)) {
            if (this.locationAwareLogger == null) {
                this.logger.error(msg, t2);
            } else {
                this.locationAwareLogger.log(null, FQCN, 40, msg, null, t2);
            }
        } else if (Level.CONFIG.equals(level)) {
            if (this.locationAwareLogger == null) {
                this.logger.debug(msg, t2);
            } else {
                this.locationAwareLogger.log(null, FQCN, 10, msg, null, t2);
            }
        } else if (Level.OFF.equals(level)) {
            // empty if block
        }
    }
}

