/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.activemq.transport.LogWriter;
import org.apache.activemq.transport.TransportLoggerView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogWriterFinder {
    private static final Logger log = LoggerFactory.getLogger(TransportLoggerView.class);
    private final String path;
    private final ConcurrentMap classMap = new ConcurrentHashMap();

    public LogWriterFinder(String path) {
        this.path = path;
    }

    public LogWriter newInstance(String logWriterName) throws IllegalAccessException, InstantiationException, IOException, ClassNotFoundException {
        Class clazz = (Class)this.classMap.get(logWriterName);
        if (clazz == null) {
            clazz = this.newInstance(this.doFindLogWriterProperties(logWriterName));
            this.classMap.put(logWriterName, clazz);
        }
        return (LogWriter)clazz.newInstance();
    }

    private Class newInstance(Properties properties) throws ClassNotFoundException, IOException {
        Class<?> clazz;
        String className = properties.getProperty("class");
        if (className == null) {
            throw new IOException("Expected property is missing: class");
        }
        try {
            clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
        }
        catch (ClassNotFoundException e) {
            clazz = LogWriterFinder.class.getClassLoader().loadClass(className);
        }
        return clazz;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected Properties doFindLogWriterProperties(String logWriterName) throws IOException {
        InputStream in;
        String uri = this.path + logWriterName;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            classLoader = this.getClass().getClassLoader();
        }
        if ((in = classLoader.getResourceAsStream(uri)) == null && (in = LogWriterFinder.class.getClassLoader().getResourceAsStream(uri)) == null) {
            log.error("Could not find log writer for resource: " + uri);
            throw new IOException("Could not find log writer for resource: " + uri);
        }
        BufferedInputStream reader = null;
        Properties properties = new Properties();
        try {
            reader = new BufferedInputStream(in);
            properties.load(reader);
            Properties properties2 = properties;
            return properties2;
        }
        finally {
            try {
                reader.close();
            }
            catch (Exception exception) {}
        }
    }
}

