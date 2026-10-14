/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

public interface Log {
    public boolean isTraceEnabled();

    public void trace(Object var1);

    public void trace(Object var1, Object var2);

    public void trace(Object var1, Throwable var2);

    public void trace(Object var1, Object var2, Throwable var3);

    public boolean isDebugEnabled();

    public void debug(Object var1);

    public void debug(Object var1, Object var2);

    public void debug(Object var1, Throwable var2);

    public void debug(Object var1, Object var2, Throwable var3);

    public boolean isInfoEnabled();

    public void info(Object var1);

    public void info(Object var1, Object var2);

    public void info(Object var1, Throwable var2);

    public void info(Object var1, Object var2, Throwable var3);

    public boolean isWarnEnabled();

    public void warn(Object var1);

    public void warn(Object var1, Object var2);

    public void warn(Object var1, Throwable var2);

    public void warn(Object var1, Object var2, Throwable var3);

    public void error(Object var1);

    public void error(Object var1, Object var2);

    public void error(Object var1, Throwable var2);

    public void error(Object var1, Object var2, Throwable var3);

    public void log(Level var1, Object var2);

    public void log(Level var1, Object var2, Object var3);

    public void log(Level var1, Object var2, Throwable var3);

    public void log(Level var1, Object var2, Object var3, Throwable var4);

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

