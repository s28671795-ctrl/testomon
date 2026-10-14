/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.jmx;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import org.apache.activemq.broker.jmx.Log4JConfigViewMBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log4JConfigView
implements Log4JConfigViewMBean {
    private static final Logger LOG = LoggerFactory.getLogger(Log4JConfigView.class);

    @Override
    public String getRootLogLevel() throws Exception {
        ClassLoader cl = Log4JConfigView.getClassLoader();
        if (!Log4JConfigView.isLog4JAvailable(cl)) {
            return null;
        }
        Class<?> loggerClass = Log4JConfigView.getLoggerClass(cl);
        if (loggerClass == null) {
            return null;
        }
        Method getRootLogger = loggerClass.getMethod("getRootLogger", new Class[0]);
        Method getLevel = loggerClass.getMethod("getLevel", new Class[0]);
        Object rootLogger = getRootLogger.invoke(null, (Object[])null);
        return getLevel.invoke(rootLogger, (Object[])null).toString();
    }

    @Override
    public void setRootLogLevel(String level) throws Exception {
        ClassLoader cl = Log4JConfigView.getClassLoader();
        if (!Log4JConfigView.isLog4JAvailable(cl)) {
            return;
        }
        Class<?> loggerClass = Log4JConfigView.getLoggerClass(cl);
        Class<?> levelClass = Log4JConfigView.getLevelClass(cl);
        if (levelClass == null || loggerClass == null) {
            return;
        }
        String targetLevel = level.toUpperCase(Locale.US);
        Method getRootLogger = loggerClass.getMethod("getRootLogger", new Class[0]);
        Method setLevel = loggerClass.getMethod("setLevel", levelClass);
        Object rootLogger = getRootLogger.invoke(null, (Object[])null);
        Method toLevel = levelClass.getMethod("toLevel", String.class);
        Object newLevel = toLevel.invoke(null, targetLevel);
        if (newLevel != null && newLevel.toString().equals(targetLevel)) {
            LOG.debug("Set level {} for root logger.", (Object)level);
            setLevel.invoke(rootLogger, newLevel);
        }
    }

    @Override
    public List<String> getLoggers() throws Exception {
        ClassLoader cl = Log4JConfigView.getClassLoader();
        if (!Log4JConfigView.isLog4JAvailable(cl)) {
            return Collections.emptyList();
        }
        Class<?> logManagerClass = Log4JConfigView.getLogManagerClass(cl);
        Class<?> loggerClass = Log4JConfigView.getLoggerClass(cl);
        if (logManagerClass == null || loggerClass == null) {
            return Collections.emptyList();
        }
        Method getCurrentLoggers = logManagerClass.getMethod("getCurrentLoggers", new Class[0]);
        Method getName = loggerClass.getMethod("getName", new Class[0]);
        ArrayList<String> list = new ArrayList<String>();
        Enumeration loggers = (Enumeration)getCurrentLoggers.invoke(null, (Object[])null);
        while (loggers.hasMoreElements()) {
            Object logger2 = loggers.nextElement();
            if (logger2 == null) continue;
            list.add((String)getName.invoke(logger2, (Object[])null));
        }
        LOG.debug("Found {} loggers", (Object)list.size());
        return list;
    }

    @Override
    public String getLogLevel(String loggerName) throws Exception {
        ClassLoader cl = Log4JConfigView.getClassLoader();
        if (!Log4JConfigView.isLog4JAvailable(cl)) {
            return null;
        }
        Class<?> loggerClass = Log4JConfigView.getLoggerClass(cl);
        if (loggerClass == null) {
            return null;
        }
        Method getLogger = loggerClass.getMethod("getLogger", String.class);
        String logLevel = null;
        if (loggerName != null && !loggerName.isEmpty()) {
            Object logger2 = getLogger.invoke(null, loggerName);
            if (logger2 != null) {
                LOG.debug("Found level {} for logger: {}", (Object)logLevel, (Object)loggerName);
                Method getLevel = loggerClass.getMethod("getLevel", new Class[0]);
                Object level = getLevel.invoke(logger2, (Object[])null);
                if (level != null) {
                    logLevel = level.toString();
                } else {
                    Method getRootLogger = loggerClass.getMethod("getRootLogger", new Class[0]);
                    Object rootLogger = getRootLogger.invoke(null, (Object[])null);
                    logLevel = getLevel.invoke(rootLogger, (Object[])null).toString();
                }
            }
        } else {
            throw new IllegalArgumentException("Logger names cannot be null or empty strings");
        }
        return logLevel;
    }

    @Override
    public void setLogLevel(String loggerName, String level) throws Exception {
        Object newLevel;
        if (loggerName == null || loggerName.isEmpty()) {
            throw new IllegalArgumentException("Logger names cannot be null or empty strings");
        }
        if (level == null || level.isEmpty()) {
            throw new IllegalArgumentException("Level name cannot be null or empty strings");
        }
        ClassLoader cl = Log4JConfigView.getClassLoader();
        if (!Log4JConfigView.isLog4JAvailable(cl)) {
            return;
        }
        Class<?> loggerClass = Log4JConfigView.getLoggerClass(cl);
        Class<?> levelClass = Log4JConfigView.getLevelClass(cl);
        if (loggerClass == null || levelClass == null) {
            return;
        }
        String targetLevel = level.toUpperCase(Locale.US);
        Method getLogger = loggerClass.getMethod("getLogger", String.class);
        Method setLevel = loggerClass.getMethod("setLevel", levelClass);
        Method toLevel = levelClass.getMethod("toLevel", String.class);
        Object logger2 = getLogger.invoke(null, loggerName);
        if (logger2 != null && (newLevel = toLevel.invoke(null, targetLevel)) != null && newLevel.toString().equals(targetLevel)) {
            LOG.debug("Set level {} for logger: {}", (Object)level, (Object)loggerName);
            setLevel.invoke(logger2, newLevel);
        }
    }

    @Override
    public void reloadLog4jProperties() throws Throwable {
        Log4JConfigView.doReloadLog4jProperties();
    }

    public static void doReloadLog4jProperties() throws Throwable {
        try {
            ClassLoader cl = Log4JConfigView.class.getClassLoader();
            Class<?> logManagerClass = Log4JConfigView.getLogManagerClass(cl);
            if (logManagerClass == null) {
                LOG.debug("Could not locate log4j classes on classpath.");
                return;
            }
            Method resetConfiguration = logManagerClass.getMethod("resetConfiguration", new Class[0]);
            resetConfiguration.invoke(null, new Object[0]);
            String configurationOptionStr = System.getProperty("log4j.configuration");
            URL log4jprops = null;
            if (configurationOptionStr != null) {
                try {
                    log4jprops = new URL(configurationOptionStr);
                }
                catch (MalformedURLException ex) {
                    log4jprops = cl.getResource("log4j.properties");
                }
            } else {
                log4jprops = cl.getResource("log4j.properties");
            }
            if (log4jprops != null) {
                Class<?> propertyConfiguratorClass = cl.loadClass("org.apache.log4j.PropertyConfigurator");
                Method configure = propertyConfiguratorClass.getMethod("configure", URL.class);
                configure.invoke(null, log4jprops);
            }
        }
        catch (InvocationTargetException e) {
            throw e.getTargetException();
        }
    }

    public static boolean isLog4JAvailable() {
        return Log4JConfigView.isLog4JAvailable(Log4JConfigView.getClassLoader());
    }

    private static ClassLoader getClassLoader() {
        return Log4JConfigView.class.getClassLoader();
    }

    private static boolean isLog4JAvailable(ClassLoader cl) {
        if (Log4JConfigView.getLogManagerClass(cl) != null) {
            return true;
        }
        LOG.debug("Could not locate log4j classes on classpath.");
        return false;
    }

    private static Class<?> getLogManagerClass(ClassLoader cl) {
        Class<?> logManagerClass = null;
        try {
            logManagerClass = cl.loadClass("org.apache.log4j.LogManager");
        }
        catch (ClassNotFoundException classNotFoundException) {
            // empty catch block
        }
        return logManagerClass;
    }

    private static Class<?> getLoggerClass(ClassLoader cl) {
        Class<?> loggerClass = null;
        try {
            loggerClass = cl.loadClass("org.apache.log4j.Logger");
        }
        catch (ClassNotFoundException classNotFoundException) {
            // empty catch block
        }
        return loggerClass;
    }

    private static Class<?> getLevelClass(ClassLoader cl) {
        Class<?> levelClass = null;
        try {
            levelClass = cl.loadClass("org.apache.log4j.Level");
        }
        catch (ClassNotFoundException classNotFoundException) {
            // empty catch block
        }
        return levelClass;
    }
}

