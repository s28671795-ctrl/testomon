/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class PropertiesLoaderUtils {
    private PropertiesLoaderUtils() {
    }

    public static Properties loadAllProperties(String resourceName, ClassLoader classLoader) throws IOException {
        return PropertiesLoaderUtils.loadAllProperties(resourceName, classLoader, null, null, null);
    }

    public static Properties loadAllProperties(String resourceName, ClassLoader classLoader, Logger logger2, Level level, String msg) throws IOException {
        Properties properties = new Properties();
        Enumeration<URL> urls2 = (classLoader != null ? classLoader : PropertiesLoaderUtils.class.getClassLoader()).getResources(resourceName);
        while (urls2.hasMoreElements()) {
            URL url2 = urls2.nextElement();
            if (logger2 != null) {
                logger2.log(level, msg, url2.toString());
            }
            InputStream is = url2.openStream();
            Throwable throwable = null;
            try {
                properties.loadFromXML(new BufferedInputStream(is));
            }
            catch (Throwable throwable2) {
                throwable = throwable2;
                throw throwable2;
            }
            finally {
                if (is == null) continue;
                if (throwable != null) {
                    try {
                        is.close();
                    }
                    catch (Throwable throwable3) {
                        throwable.addSuppressed(throwable3);
                    }
                    continue;
                }
                is.close();
            }
        }
        return properties;
    }
}

