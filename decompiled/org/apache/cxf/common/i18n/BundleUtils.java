/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.i18n;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.apache.cxf.common.util.PackageUtils;

public final class BundleUtils {
    private static final String MESSAGE_BUNDLE = ".Messages";

    private BundleUtils() {
    }

    public static String getBundleName(Class<?> cls) {
        return PackageUtils.getPackageName(cls) + MESSAGE_BUNDLE;
    }

    public static String getBundleName(Class<?> cls, String name) {
        return PackageUtils.getPackageName(cls) + "." + name;
    }

    public static ResourceBundle getBundle(Class<?> cls) {
        try {
            ClassLoader loader = BundleUtils.getClassLoader(cls);
            if (loader == null) {
                return ResourceBundle.getBundle(BundleUtils.getBundleName(cls), Locale.getDefault());
            }
            return ResourceBundle.getBundle(BundleUtils.getBundleName(cls), Locale.getDefault(), loader);
        }
        catch (MissingResourceException ex) {
            ClassLoader loader = BundleUtils.getContextClassLoader();
            if (loader == null) {
                return ResourceBundle.getBundle(BundleUtils.getBundleName(cls), Locale.getDefault());
            }
            return ResourceBundle.getBundle(BundleUtils.getBundleName(cls), Locale.getDefault(), loader);
        }
    }

    public static ResourceBundle getBundle(Class<?> cls, String name) {
        try {
            ClassLoader loader = BundleUtils.getClassLoader(cls);
            if (loader == null) {
                return ResourceBundle.getBundle(BundleUtils.getBundleName(cls, name), Locale.getDefault());
            }
            return ResourceBundle.getBundle(BundleUtils.getBundleName(cls, name), Locale.getDefault(), loader);
        }
        catch (MissingResourceException ex) {
            ClassLoader loader = BundleUtils.getContextClassLoader();
            if (loader == null) {
                return ResourceBundle.getBundle(BundleUtils.getBundleName(cls, name), Locale.getDefault());
            }
            return ResourceBundle.getBundle(BundleUtils.getBundleName(cls, name), Locale.getDefault(), loader);
        }
    }

    public static String getFormattedString(ResourceBundle b, String key, Object ... params) {
        return MessageFormat.format(b.getString(key), params);
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
}

