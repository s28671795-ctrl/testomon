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

public class FactoryFinder {
    private static ObjectFactory objectFactory = new StandaloneObjectFactory();
    private final String path;

    public static ObjectFactory getObjectFactory() {
        return objectFactory;
    }

    public static void setObjectFactory(ObjectFactory objectFactory) {
        FactoryFinder.objectFactory = objectFactory;
    }

    public FactoryFinder(String path) {
        this.path = path;
    }

    public Object newInstance(String key) throws IllegalAccessException, InstantiationException, IOException, ClassNotFoundException {
        return objectFactory.create(this.path + key);
    }

    protected static class StandaloneObjectFactory
    implements ObjectFactory {
        final ConcurrentMap<String, Class> classMap = new ConcurrentHashMap<String, Class>();

        protected StandaloneObjectFactory() {
        }

        @Override
        public Object create(String path) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
            Class clazz = (Class)this.classMap.get(path);
            if (clazz == null) {
                clazz = StandaloneObjectFactory.loadClass(StandaloneObjectFactory.loadProperties(path));
                this.classMap.put(path, clazz);
            }
            return clazz.newInstance();
        }

        public static Class loadClass(Properties properties) throws ClassNotFoundException, IOException {
            String className = properties.getProperty("class");
            if (className == null) {
                throw new IOException("Expected property is missing: class");
            }
            Class<?> clazz = null;
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            if (loader != null) {
                try {
                    clazz = loader.loadClass(className);
                }
                catch (ClassNotFoundException classNotFoundException) {
                    // empty catch block
                }
            }
            if (clazz == null) {
                clazz = FactoryFinder.class.getClassLoader().loadClass(className);
            }
            return clazz;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public static Properties loadProperties(String uri) throws IOException {
            InputStream in;
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            if (classLoader == null) {
                classLoader = StandaloneObjectFactory.class.getClassLoader();
            }
            if ((in = classLoader.getResourceAsStream(uri)) == null && (in = FactoryFinder.class.getClassLoader().getResourceAsStream(uri)) == null) {
                throw new IOException("Could not find factory class for resource: " + uri);
            }
            BufferedInputStream reader = null;
            try {
                reader = new BufferedInputStream(in);
                Properties properties = new Properties();
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

    public static interface ObjectFactory {
        public Object create(String var1) throws IllegalAccessException, InstantiationException, IOException, ClassNotFoundException;
    }
}

