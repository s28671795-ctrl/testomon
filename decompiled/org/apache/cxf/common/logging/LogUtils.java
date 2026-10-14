/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.logging;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import org.apache.cxf.common.logging.AbstractDelegatingLogger;
import org.apache.cxf.common.logging.JDKBugHacks;
import org.apache.cxf.common.util.StringUtils;

public final class LogUtils {
    private static final String KEY = "org.apache.cxf.Logger";
    private static final Object[] NO_PARAMETERS;
    private static Class<?> loggerClass;

    private LogUtils() {
    }

    public static void setLoggerClass(Class<? extends AbstractDelegatingLogger> cls) {
        loggerClass = cls;
    }

    public static Logger getLogger(Class<?> cls) {
        return LogUtils.createLogger(cls, null, cls.getName());
    }

    public static Logger getLogger(Class<?> cls, String resourcename) {
        return LogUtils.createLogger(cls, resourcename, cls.getName());
    }

    public static Logger getLogger(Class<?> cls, String resourcename, String loggerName) {
        return LogUtils.createLogger(cls, resourcename, loggerName);
    }

    public static Logger getL7dLogger(Class<?> cls) {
        return LogUtils.createLogger(cls, null, cls.getName());
    }

    public static Logger getL7dLogger(Class<?> cls, String resourcename) {
        return LogUtils.createLogger(cls, resourcename, cls.getName());
    }

    public static Logger getL7dLogger(Class<?> cls, String resourcename, String loggerName) {
        return LogUtils.createLogger(cls, resourcename, loggerName);
    }

    /*
     * Exception decompiling
     */
    protected static Logger createLogger(Class<?> cls, String name, String loggerName) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 28[SIMPLE_IF_TAKEN]
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:435)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:484)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    private static void setContextClassLoader(final ClassLoader classLoader) {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            AccessController.doPrivileged(new PrivilegedAction<Object>(){

                @Override
                public Object run() {
                    Thread.currentThread().setContextClassLoader(classLoader);
                    return null;
                }
            });
        } else {
            Thread.currentThread().setContextClassLoader(classLoader);
        }
    }

    private static ClassLoader getContextClassLoader() {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            return AccessController.doPrivileged(new PrivilegedAction<ClassLoader>(){

                @Override
                public ClassLoader run() {
                    return Thread.currentThread().getContextClassLoader();
                }
            });
        }
        return Thread.currentThread().getContextClassLoader();
    }

    private static ClassLoader getClassLoader(final Class<?> clazz) {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            return AccessController.doPrivileged(new PrivilegedAction<ClassLoader>(){

                @Override
                public ClassLoader run() {
                    return clazz.getClassLoader();
                }
            });
        }
        return clazz.getClassLoader();
    }

    public static void log(Logger logger2, Level level, String message, Throwable throwable, Object parameter) {
        if (logger2.isLoggable(level)) {
            String formattedMessage = MessageFormat.format(LogUtils.localize(logger2, message), parameter);
            LogUtils.doLog(logger2, level, formattedMessage, throwable);
        }
    }

    public static void log(Logger logger2, Level level, String message, Throwable throwable, Object ... parameters) {
        if (logger2.isLoggable(level)) {
            String formattedMessage = MessageFormat.format(LogUtils.localize(logger2, message), parameters);
            LogUtils.doLog(logger2, level, formattedMessage, throwable);
        }
    }

    public static void log(Logger logger2, Level level, String message) {
        LogUtils.log(logger2, level, message, NO_PARAMETERS);
    }

    public static void log(Logger logger2, Level level, String message, Throwable throwable) {
        LogUtils.log(logger2, level, message, throwable, NO_PARAMETERS);
    }

    public static void log(Logger logger2, Level level, String message, Object parameter) {
        LogUtils.log(logger2, level, message, new Object[]{parameter});
    }

    public static void log(Logger logger2, Level level, String message, Object[] parameters) {
        if (logger2.isLoggable(level)) {
            String msg = LogUtils.localize(logger2, message);
            try {
                msg = MessageFormat.format(msg, parameters);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                // empty catch block
            }
            LogUtils.doLog(logger2, level, msg, null);
        }
    }

    private static void doLog(Logger log, Level level, String msg, Throwable t2) {
        LogRecord record = new LogRecord(level, msg);
        record.setLoggerName(log.getName());
        record.setResourceBundleName(log.getResourceBundleName());
        record.setResourceBundle(log.getResourceBundle());
        if (t2 != null) {
            record.setThrown(t2);
        }
        StackTraceElement[] stack = new Throwable().getStackTrace();
        String cname = LogUtils.class.getName();
        for (int x = 0; x < stack.length; ++x) {
            StackTraceElement frame = stack[x];
            if (frame.getClassName().equals(cname)) continue;
            record.setSourceClassName(frame.getClassName());
            record.setSourceMethodName(frame.getMethodName());
            break;
        }
        log.log(record);
    }

    private static String localize(Logger logger2, String message) {
        ResourceBundle bundle2 = logger2.getResourceBundle();
        try {
            return bundle2 != null ? bundle2.getString(message) : message;
        }
        catch (MissingResourceException ex) {
            return message;
        }
    }

    static {
        block32: {
            NO_PARAMETERS = new Object[0];
            JDKBugHacks.doHacks();
            try {
                String cname = null;
                try {
                    cname = AccessController.doPrivileged(new PrivilegedAction<String>(){

                        @Override
                        public String run() {
                            return System.getProperty(LogUtils.KEY);
                        }
                    });
                }
                catch (Throwable throwable) {
                    // empty catch block
                }
                if (StringUtils.isEmpty(cname)) {
                    InputStream ins = Thread.currentThread().getContextClassLoader().getResourceAsStream("META-INF/cxf/org.apache.cxf.Logger");
                    if (ins == null) {
                        ins = ClassLoader.getSystemResourceAsStream("META-INF/cxf/org.apache.cxf.Logger");
                    }
                    if (ins != null) {
                        try (BufferedReader din = new BufferedReader(new InputStreamReader(ins));){
                            cname = din.readLine();
                        }
                    }
                }
                if (StringUtils.isEmpty(cname)) {
                    try {
                        Class.forName("org.slf4j.impl.StaticLoggerBinder");
                        Class<?> cls = Class.forName("org.slf4j.LoggerFactory");
                        Class<?> fcls = cls.getMethod("getILoggerFactory", new Class[0]).invoke(null, new Object[0]).getClass();
                        String clsName = fcls.getName();
                        if (clsName.contains("NOPLogger")) {
                            cname = null;
                        } else if (clsName.contains("Log4j")) {
                            cname = "org.apache.cxf.common.logging.Log4jLogger";
                        } else if (clsName.contains("JCL")) {
                            cls = Class.forName("org.apache.commons.logging.LogFactory");
                            fcls = cls.getMethod("getFactory", new Class[0]).invoke(null, new Object[0]).getClass();
                            if (fcls.getName().contains("Log4j")) {
                                cname = "org.apache.cxf.common.logging.Log4jLogger";
                            }
                        } else {
                            cname = clsName.contains("JDK14") || clsName.contains("pax.logging") ? null : "org.apache.cxf.common.logging.Slf4jLogger";
                        }
                    }
                    catch (Throwable cls) {
                        // empty catch block
                    }
                }
                if (StringUtils.isEmpty(cname)) break block32;
                try {
                    loggerClass = Class.forName(cname.trim(), true, Thread.currentThread().getContextClassLoader());
                }
                catch (Throwable ex) {
                    loggerClass = Class.forName(cname.trim());
                }
                LogUtils.getLogger(LogUtils.class).fine("Using " + loggerClass.getName() + " for logging.");
            }
            catch (Throwable ex) {
                loggerClass = null;
            }
        }
    }
}

