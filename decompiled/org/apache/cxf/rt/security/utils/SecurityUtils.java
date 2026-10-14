/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.rt.security.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Logger;
import javax.security.auth.callback.CallbackHandler;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.PropertyUtils;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.resource.ResourceManager;

public final class SecurityUtils {
    private static final Logger LOG = LogUtils.getL7dLogger(SecurityUtils.class);

    private SecurityUtils() {
    }

    public static CallbackHandler getCallbackHandler(Object o) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        CallbackHandler handler = null;
        if (o instanceof CallbackHandler) {
            handler = (CallbackHandler)o;
        } else if (o instanceof String) {
            handler = (CallbackHandler)ClassLoaderUtils.loadClass((String)o, SecurityUtils.class).newInstance();
        }
        return handler;
    }

    public static URL getConfigFileURL(Message message, String configFileKey, String configFileDefault) {
        Object o = message.getContextualProperty(configFileKey);
        if (o == null) {
            o = configFileDefault;
        }
        return SecurityUtils.loadResource(message, o);
    }

    public static URL loadResource(Object o) {
        return SecurityUtils.loadResource((Message)null, o);
    }

    public static URL loadResource(Message message, Object o) {
        Message msg = message;
        if (msg == null) {
            msg = PhaseInterceptorChain.getCurrentMessage();
        }
        ResourceManager manager = null;
        if (msg != null && msg.getExchange() != null && msg.getExchange().getBus() != null) {
            manager = msg.getExchange().getBus().getExtension(ResourceManager.class);
        }
        return SecurityUtils.loadResource(manager, o);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static URL loadResource(ResourceManager manager, Object o) {
        if (o instanceof String) {
            URL url2 = ClassLoaderUtils.getResource((String)o, SecurityUtils.class);
            if (url2 != null) {
                return url2;
            }
            ClassLoaderUtils.ClassLoaderHolder orig = null;
            try {
                if (manager != null) {
                    ClassLoader loader = manager.resolveResource((String)o, ClassLoader.class);
                    if (loader != null) {
                        orig = ClassLoaderUtils.setThreadContextClassloader(loader);
                    }
                    url2 = manager.resolveResource((String)o, URL.class);
                }
                if (url2 == null) {
                    try {
                        url2 = new URL((String)o);
                    }
                    catch (IOException loader) {
                        // empty catch block
                    }
                }
                if (url2 == null) {
                    try {
                        URI propResourceUri = URI.create((String)o);
                        if (propResourceUri.getScheme() != null) {
                            url2 = propResourceUri.toURL();
                        } else {
                            File f = new File(propResourceUri.toString());
                            if (f.exists()) {
                                url2 = f.toURI().toURL();
                            }
                        }
                    }
                    catch (IOException iOException) {
                        // empty catch block
                    }
                }
                URL uRL = url2;
                return uRL;
            }
            finally {
                if (orig != null) {
                    orig.reset();
                }
            }
        }
        if (o instanceof URL) {
            return (URL)o;
        }
        return null;
    }

    public static Properties loadProperties(Object o) {
        return SecurityUtils.loadProperties(null, o);
    }

    public static Properties loadProperties(ResourceManager manager, Object o) {
        if (o instanceof Properties) {
            return (Properties)o;
        }
        URL url2 = null;
        if (o instanceof String) {
            url2 = SecurityUtils.loadResource(manager, o);
        } else if (o instanceof URL) {
            url2 = (URL)o;
        }
        if (url2 != null) {
            Properties properties = new Properties();
            try {
                InputStream ins = url2.openStream();
                properties.load(ins);
                ins.close();
            }
            catch (IOException e) {
                LOG.fine(e.getMessage());
                properties = null;
            }
            return properties;
        }
        return null;
    }

    public static Object getSecurityPropertyValue(String property, Message message) {
        Object value = message.getContextualProperty(property);
        if (value != null) {
            return value;
        }
        return message.getContextualProperty("ws-" + property);
    }

    public static boolean getSecurityPropertyBoolean(String property, Message message, boolean defaultValue) {
        Object value = SecurityUtils.getSecurityPropertyValue(property, message);
        if (value != null) {
            return PropertyUtils.isTrue(value);
        }
        return defaultValue;
    }
}

