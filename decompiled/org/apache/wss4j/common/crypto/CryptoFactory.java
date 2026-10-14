/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.Properties;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.crypto.Merlin;
import org.apache.wss4j.common.crypto.PasswordEncryptor;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.util.Loader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class CryptoFactory {
    private static final Logger LOG = LoggerFactory.getLogger(CryptoFactory.class);

    public static Crypto getInstance() throws WSSecurityException {
        return CryptoFactory.getInstance("crypto.properties");
    }

    public static Crypto getInstance(Properties properties) throws WSSecurityException {
        if (properties == null) {
            LOG.debug("Cannot load Crypto instance as properties object is null");
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "empty", new Object[]{"Cannot load Crypto instance as properties object is null"});
        }
        return CryptoFactory.getInstance(properties, Loader.getClassLoader(CryptoFactory.class), null);
    }

    public static Crypto getInstance(Properties properties, ClassLoader classLoader, PasswordEncryptor passwordEncryptor) throws WSSecurityException {
        if (properties == null) {
            LOG.debug("Cannot load Crypto instance as properties object is null");
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "empty", new Object[]{"Cannot load Crypto instance as properties object is null"});
        }
        String cryptoClassName = properties.getProperty("org.apache.wss4j.crypto.provider");
        if (cryptoClassName == null) {
            cryptoClassName = properties.getProperty("org.apache.ws.security.crypto.provider");
        }
        Class<Crypto> cryptoClass = null;
        if (cryptoClassName == null || cryptoClassName.equals("org.apache.wss4j.common.crypto.Merlin") || cryptoClassName.equals("org.apache.ws.security.components.crypto.Merlin")) {
            try {
                return new Merlin(properties, classLoader, passwordEncryptor);
            }
            catch (Exception e) {
                LOG.debug("Unable to instantiate Merlin", e);
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e, "empty", new Object[]{"Cannot create Crypto class " + cryptoClassName});
            }
        }
        try {
            cryptoClass = Loader.loadClass(cryptoClassName, Crypto.class);
        }
        catch (ClassNotFoundException ex) {
            LOG.debug(ex.getMessage(), ex);
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, ex, "empty", new Object[]{cryptoClassName + " Not Found"});
        }
        return CryptoFactory.loadClass(cryptoClass, properties, classLoader);
    }

    public static Crypto getInstance(Class<? extends Crypto> cryptoClass, Map<Object, Object> map) throws WSSecurityException {
        return CryptoFactory.loadClass(cryptoClass, map, Loader.getClassLoader(CryptoFactory.class));
    }

    public static Crypto getInstance(String propFilename) throws WSSecurityException {
        return CryptoFactory.getInstance(propFilename, Loader.getClassLoader(CryptoFactory.class));
    }

    public static Crypto getInstance(String propFilename, ClassLoader customClassLoader) throws WSSecurityException {
        Properties properties = CryptoFactory.getProperties(propFilename, customClassLoader);
        return CryptoFactory.getInstance(properties, customClassLoader, null);
    }

    private static Crypto loadClass(Class<? extends Crypto> cryptoClass, Map<Object, Object> map, ClassLoader loader) throws WSSecurityException {
        LOG.debug("Using Crypto Engine [{}]", (Object)cryptoClass);
        try {
            Constructor<? extends Crypto> c = null;
            try {
                Class[] classes = new Class[]{Map.class, ClassLoader.class};
                c = cryptoClass.getConstructor(classes);
                return c.newInstance(map, loader);
            }
            catch (NoSuchMethodException ex) {
                Class[] classes = new Class[]{Properties.class, Map.class, PasswordEncryptor.class};
                c = cryptoClass.getConstructor(classes);
                return c.newInstance(map, loader, null);
            }
        }
        catch (Exception e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Unable to instantiate: " + cryptoClass.getName(), e);
            }
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e, "empty", new Object[]{cryptoClass + " cannot create instance"});
        }
    }

    private static Crypto loadClass(Class<? extends Crypto> cryptoClass, Properties map, ClassLoader loader) throws WSSecurityException {
        LOG.debug("Using Crypto Engine [{}]", (Object)cryptoClass);
        try {
            Constructor<? extends Crypto> c = null;
            try {
                Class[] classes = new Class[]{Properties.class, ClassLoader.class};
                c = cryptoClass.getConstructor(classes);
                return c.newInstance(map, loader);
            }
            catch (NoSuchMethodException ex) {
                Class[] classes = new Class[]{Properties.class, ClassLoader.class, PasswordEncryptor.class};
                c = cryptoClass.getConstructor(classes);
                return c.newInstance(map, loader, null);
            }
        }
        catch (Exception e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Unable to instantiate: " + cryptoClass.getName(), e);
            }
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e, "empty", new Object[]{cryptoClass + " cannot create instance"});
        }
    }

    public static Properties getProperties(String propFilename, ClassLoader loader) throws WSSecurityException {
        Properties properties = new Properties();
        try {
            InputStream is = Loader.loadInputStream(loader, propFilename);
            if (is == null) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "resourceNotFound", new Object[]{propFilename});
            }
            properties.load(is);
            is.close();
        }
        catch (IOException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Cannot find resource: " + propFilename, e);
            }
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e, "resourceNotFound", new Object[]{propFilename});
        }
        return properties;
    }
}

