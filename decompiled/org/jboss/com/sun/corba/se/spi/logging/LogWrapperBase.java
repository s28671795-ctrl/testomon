/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.logging;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public abstract class LogWrapperBase {
    protected Logger logger;
    protected String loggerName;

    protected LogWrapperBase(Logger logger2) {
        this.logger = logger2;
        this.loggerName = logger2.getName();
    }

    protected void doLog(Level level, String key, Object[] params, Class<?> wrapperClass, Throwable thr) {
        LogRecord lrec = new LogRecord(level, key);
        if (params != null) {
            lrec.setParameters(params);
        }
        this.inferCaller(wrapperClass, lrec);
        lrec.setThrown(thr);
        lrec.setLoggerName(this.loggerName);
        lrec.setResourceBundle(this.logger.getResourceBundle());
        this.logger.log(lrec);
    }

    private void inferCaller(Class<?> wrapperClass, LogRecord lrec) {
        String cname;
        int ix;
        StackTraceElement[] stack = new Throwable().getStackTrace();
        StackTraceElement frame = null;
        String wcname = wrapperClass.getName();
        String baseName = LogWrapperBase.class.getName();
        for (ix = 0; ix < stack.length && ((cname = (frame = stack[ix]).getClassName()).equals(wcname) || cname.equals(baseName)); ++ix) {
        }
        if (ix < stack.length) {
            lrec.setSourceClassName(frame.getClassName());
            lrec.setSourceMethodName(frame.getMethodName());
        }
    }

    protected void doLog(Level level, String key, Class<?> wrapperClass, Throwable thr) {
        this.doLog(level, key, null, wrapperClass, thr);
    }
}

