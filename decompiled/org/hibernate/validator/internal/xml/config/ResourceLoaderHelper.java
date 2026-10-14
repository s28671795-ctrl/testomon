/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.xml.config;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.security.AccessController;
import java.security.PrivilegedAction;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.internal.util.privilegedactions.GetClassLoader;

final class ResourceLoaderHelper {
    private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());

    private ResourceLoaderHelper() {
    }

    static InputStream getResettableInputStreamForPath(String path, ClassLoader externalClassLoader) {
        ClassLoader loader;
        String inputPath = path;
        if (inputPath.startsWith("/")) {
            inputPath = inputPath.substring(1);
        }
        InputStream inputStream2 = null;
        if (externalClassLoader != null) {
            LOG.debug("Trying to load " + path + " via user class loader");
            inputStream2 = externalClassLoader.getResourceAsStream(inputPath);
        }
        if (inputStream2 == null && (loader = ResourceLoaderHelper.run(GetClassLoader.fromContext())) != null) {
            LOG.debug("Trying to load " + path + " via TCCL");
            inputStream2 = loader.getResourceAsStream(inputPath);
        }
        if (inputStream2 == null) {
            LOG.debug("Trying to load " + path + " via Hibernate Validator's class loader");
            loader = ResourceLoaderHelper.class.getClassLoader();
            inputStream2 = loader.getResourceAsStream(inputPath);
        }
        if (inputStream2 == null) {
            return null;
        }
        if (inputStream2.markSupported()) {
            return inputStream2;
        }
        return new BufferedInputStream(inputStream2);
    }

    private static <T> T run(PrivilegedAction<T> action) {
        return System.getSecurityManager() != null ? AccessController.doPrivileged(action) : action.run();
    }
}

