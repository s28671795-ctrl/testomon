/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.util.log;

import org.eclipse.jetty.util.log.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
class Slf4jLogger
implements Logger {
    private final org.slf4j.Logger logger;

    Slf4jLogger(org.slf4j.Logger logger2) {
        this.logger = logger2;
    }

    @Override
    public void debug(String format, Object ... args2) {
        this.logger.debug(format, args2);
    }

    @Override
    public void debug(String msg, long value) {
        this.logger.debug(msg, (Object)value);
    }

    @Override
    public void debug(Throwable cause) {
        this.logger.debug(cause.getMessage(), cause);
    }

    @Override
    public void debug(String msg, Throwable thrown) {
        this.logger.debug(msg, thrown);
    }

    @Override
    public Logger getLogger(String name) {
        return new Slf4jLogger(LoggerFactory.getLogger(this.getName() + name));
    }

    @Override
    public void ignore(Throwable cause) {
        this.logger.trace("IGNORED", cause);
    }

    @Override
    public void info(String format, Object ... args2) {
        this.logger.info(format, args2);
    }

    @Override
    public void info(Throwable cause) {
        this.logger.info(cause.getMessage(), cause);
    }

    @Override
    public void info(String msg, Throwable thrown) {
        this.logger.info(msg, thrown);
    }

    @Override
    public boolean isDebugEnabled() {
        return this.logger.isDebugEnabled();
    }

    @Override
    @Deprecated
    public void setDebugEnabled(boolean enabled) {
    }

    @Override
    public void warn(Throwable cause) {
        this.logger.warn(cause.getMessage(), cause);
    }

    @Override
    public void warn(String msg, Throwable cause) {
        this.logger.warn(msg, cause);
    }

    @Override
    public String getName() {
        return this.logger.getName();
    }

    @Override
    public void warn(String format, Object ... args2) {
        this.logger.warn(format, args2);
    }
}

