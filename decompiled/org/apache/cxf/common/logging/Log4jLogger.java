/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Appender
 *  org.apache.log4j.AppenderSkeleton
 *  org.apache.log4j.Level
 *  org.apache.log4j.LogManager
 *  org.apache.log4j.Logger
 *  org.apache.log4j.Priority
 *  org.apache.log4j.spi.LoggingEvent
 */
package org.apache.cxf.common.logging;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import org.apache.cxf.common.logging.AbstractDelegatingLogger;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.log4j.Appender;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.apache.log4j.spi.LoggingEvent;

public class Log4jLogger
extends AbstractDelegatingLogger {
    private static final Map<Level, org.apache.log4j.Level> TO_LOG4J = new HashMap<Level, org.apache.log4j.Level>();
    private static final org.apache.log4j.Level TRACE;
    private final Logger log;

    public Log4jLogger(String name, String resourceBundleName) {
        super(name, resourceBundleName);
        this.log = LogManager.getLogger((String)name);
    }

    @Override
    public Level getLevel() {
        org.apache.log4j.Level l = this.log.getEffectiveLevel();
        if (l != null) {
            return this.fromL4J(l);
        }
        return null;
    }

    @Override
    public void setLevel(Level newLevel) {
        this.log.setLevel(TO_LOG4J.get(newLevel));
    }

    @Override
    public synchronized void addHandler(Handler handler) {
        this.log.addAppender((Appender)new HandlerWrapper(handler));
    }

    @Override
    public synchronized void removeHandler(Handler handler) {
        this.log.removeAppender("HandlerWrapper-" + handler.hashCode());
    }

    @Override
    public synchronized Handler[] getHandlers() {
        ArrayList<Handler> ret = new ArrayList<Handler>();
        Enumeration en = this.log.getAllAppenders();
        while (en.hasMoreElements()) {
            Appender ap = (Appender)en.nextElement();
            if (!(ap instanceof HandlerWrapper)) continue;
            ret.add(((HandlerWrapper)ap).getHandler());
        }
        return ret.toArray(new Handler[0]);
    }

    @Override
    protected void internalLogFormatted(String msg, LogRecord record) {
        this.log.log(AbstractDelegatingLogger.class.getName(), (Priority)TO_LOG4J.get(record.getLevel()), (Object)msg, record.getThrown());
    }

    private Level fromL4J(org.apache.log4j.Level l) {
        Level l2 = null;
        switch (l.toInt()) {
            case -2147483648: {
                l2 = Level.ALL;
                break;
            }
            case 50000: {
                l2 = Level.SEVERE;
                break;
            }
            case 40000: {
                l2 = Level.SEVERE;
                break;
            }
            case 30000: {
                l2 = Level.WARNING;
                break;
            }
            case 20000: {
                l2 = Level.INFO;
                break;
            }
            case 10000: {
                l2 = Level.FINE;
                break;
            }
            case 0x7FFFFFFF: {
                l2 = Level.OFF;
                break;
            }
            default: {
                if (l.toInt() != TRACE.toInt()) break;
                l2 = Level.FINEST;
            }
        }
        return l2;
    }

    private static void getFullInfoForLogUtils(LogRecord lr, String cname) {
        StackTraceElement[] el = Thread.currentThread().getStackTrace();
        for (int x = el.length - 2; x >= 0; --x) {
            if (!LogUtils.class.getName().equals(el[x].getClassName()) && !cname.equals(el[x].getClassName())) continue;
            lr.setSourceClassName(el[x + 1].getClassName());
            lr.setSourceMethodName(el[x + 1].getMethodName());
            return;
        }
    }

    static {
        org.apache.log4j.Level t2 = org.apache.log4j.Level.DEBUG;
        try {
            Field f = org.apache.log4j.Level.class.getField("TRACE");
            t2 = (org.apache.log4j.Level)f.get(null);
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        TRACE = t2;
        TO_LOG4J.put(Level.ALL, org.apache.log4j.Level.ALL);
        TO_LOG4J.put(Level.SEVERE, org.apache.log4j.Level.ERROR);
        TO_LOG4J.put(Level.WARNING, org.apache.log4j.Level.WARN);
        TO_LOG4J.put(Level.INFO, org.apache.log4j.Level.INFO);
        TO_LOG4J.put(Level.CONFIG, org.apache.log4j.Level.DEBUG);
        TO_LOG4J.put(Level.FINE, org.apache.log4j.Level.DEBUG);
        TO_LOG4J.put(Level.FINER, TRACE);
        TO_LOG4J.put(Level.FINEST, TRACE);
        TO_LOG4J.put(Level.OFF, org.apache.log4j.Level.OFF);
    }

    private class HandlerWrapper
    extends AppenderSkeleton {
        Handler handler;

        HandlerWrapper(Handler h2) {
            this.handler = h2;
            this.name = "HandlerWrapper-" + h2.hashCode();
        }

        public Handler getHandler() {
            return this.handler;
        }

        protected void append(LoggingEvent event) {
            String rbname;
            LogRecord lr = new LogRecord(Log4jLogger.this.fromL4J(event.getLevel()), event.getMessage().toString());
            lr.setLoggerName(event.getLoggerName());
            if (event.getThrowableInformation() != null) {
                lr.setThrown(event.getThrowableInformation().getThrowable());
            }
            if ((rbname = Log4jLogger.this.getResourceBundleName()) != null) {
                lr.setResourceBundleName(rbname);
                lr.setResourceBundle(Log4jLogger.this.getResourceBundle());
            }
            Log4jLogger.getFullInfoForLogUtils(lr, event.fqnOfCategoryClass);
            this.handler.publish(lr);
        }

        public void close() {
            this.handler.close();
            this.closed = true;
        }

        public boolean requiresLayout() {
            return false;
        }

        public Priority getThreshold() {
            return (Priority)TO_LOG4J.get(this.handler.getLevel());
        }

        public boolean isAsSevereAsThreshold(Priority priority) {
            Priority p = this.getThreshold();
            return p == null || priority.isGreaterOrEqual(p);
        }
    }
}

