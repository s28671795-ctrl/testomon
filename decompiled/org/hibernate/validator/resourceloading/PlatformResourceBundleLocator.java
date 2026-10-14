/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.resourceloading;

import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;
import org.hibernate.validator.Incubating;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.Contracts;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.internal.util.logging.Messages;
import org.hibernate.validator.internal.util.privilegedactions.GetClassLoader;
import org.hibernate.validator.internal.util.privilegedactions.GetMethod;
import org.hibernate.validator.internal.util.privilegedactions.GetResources;
import org.hibernate.validator.resourceloading.AggregateResourceBundle;
import org.hibernate.validator.spi.resourceloading.ResourceBundleLocator;

public class PlatformResourceBundleLocator
implements ResourceBundleLocator {
    private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
    private static final boolean RESOURCE_BUNDLE_CONTROL_INSTANTIABLE = PlatformResourceBundleLocator.determineAvailabilityOfResourceBundleControl();
    private final String bundleName;
    private final ClassLoader classLoader;
    private final boolean aggregate;
    private final Map<Locale, ResourceBundle> preloadedResourceBundles;

    public PlatformResourceBundleLocator(String bundleName) {
        this(bundleName, Collections.emptySet());
    }

    public PlatformResourceBundleLocator(String bundleName, ClassLoader classLoader) {
        this(bundleName, Collections.emptySet(), classLoader);
    }

    public PlatformResourceBundleLocator(String bundleName, ClassLoader classLoader, boolean aggregate) {
        this(bundleName, Collections.emptySet(), classLoader, aggregate);
    }

    @Incubating
    public PlatformResourceBundleLocator(String bundleName, Set<Locale> localesToInitialize) {
        this(bundleName, localesToInitialize, null);
    }

    @Incubating
    public PlatformResourceBundleLocator(String bundleName, Set<Locale> localesToInitialize, ClassLoader classLoader) {
        this(bundleName, localesToInitialize, classLoader, false);
    }

    @Incubating
    public PlatformResourceBundleLocator(String bundleName, Set<Locale> localesToInitialize, ClassLoader classLoader, boolean aggregate) {
        Contracts.assertNotNull(bundleName, "bundleName");
        this.bundleName = bundleName;
        this.classLoader = classLoader;
        boolean bl = this.aggregate = aggregate && RESOURCE_BUNDLE_CONTROL_INSTANTIABLE;
        if (!localesToInitialize.isEmpty()) {
            HashMap<Locale, ResourceBundle> tmpPreloadedResourceBundles = CollectionHelper.newHashMap(localesToInitialize.size());
            for (Locale localeToPreload : localesToInitialize) {
                tmpPreloadedResourceBundles.put(localeToPreload, this.doGetResourceBundle(localeToPreload));
            }
            this.preloadedResourceBundles = CollectionHelper.toImmutableMap(tmpPreloadedResourceBundles);
        } else {
            this.preloadedResourceBundles = Collections.emptyMap();
        }
    }

    @Override
    public ResourceBundle getResourceBundle(Locale locale) {
        if (!this.preloadedResourceBundles.isEmpty()) {
            if (this.preloadedResourceBundles.containsKey(locale)) {
                return this.preloadedResourceBundles.get(locale);
            }
            throw LOG.uninitializedLocale(locale);
        }
        return this.doGetResourceBundle(locale);
    }

    private ResourceBundle doGetResourceBundle(Locale locale) {
        ClassLoader classLoader;
        ResourceBundle rb = null;
        if (this.classLoader != null) {
            rb = this.loadBundle(this.classLoader, locale, this.bundleName + " not found by user-provided classloader");
        }
        if (rb == null && (classLoader = PlatformResourceBundleLocator.run(GetClassLoader.fromContext())) != null) {
            rb = this.loadBundle(classLoader, locale, this.bundleName + " not found by thread context classloader");
        }
        if (rb == null) {
            classLoader = PlatformResourceBundleLocator.run(GetClassLoader.fromClass(PlatformResourceBundleLocator.class));
            rb = this.loadBundle(classLoader, locale, this.bundleName + " not found by validator classloader");
        }
        if (rb != null) {
            LOG.debugf("%s found.", (Object)this.bundleName);
        } else {
            LOG.debugf("%s not found.", (Object)this.bundleName);
        }
        return rb;
    }

    private ResourceBundle loadBundle(ClassLoader classLoader, Locale locale, String message) {
        ResourceBundle rb = null;
        try {
            rb = this.aggregate ? ResourceBundle.getBundle(this.bundleName, locale, classLoader, AggregateResourceBundleControl.CONTROL) : ResourceBundle.getBundle(this.bundleName, locale, classLoader);
        }
        catch (MissingResourceException e) {
            LOG.trace(message);
        }
        return rb;
    }

    private static <T> T run(PrivilegedAction<T> action) {
        return System.getSecurityManager() != null ? AccessController.doPrivileged(action) : action.run();
    }

    private static boolean determineAvailabilityOfResourceBundleControl() {
        try {
            ResourceBundle.Control dummyControl = AggregateResourceBundleControl.CONTROL;
            if (dummyControl == null) {
                return false;
            }
            Method getModule = PlatformResourceBundleLocator.run(GetMethod.action(Class.class, "getModule"));
            if (getModule == null) {
                return true;
            }
            Object module = getModule.invoke(PlatformResourceBundleLocator.class, new Object[0]);
            Method isNamedMethod = PlatformResourceBundleLocator.run(GetMethod.action(module.getClass(), "isNamed"));
            boolean isNamed = (Boolean)isNamedMethod.invoke(module, new Object[0]);
            return !isNamed;
        }
        catch (Throwable e) {
            LOG.info(Messages.MESSAGES.unableToUseResourceBundleAggregation());
            return false;
        }
    }

    private static class AggregateResourceBundleControl
    extends ResourceBundle.Control {
        private static final ResourceBundle.Control CONTROL = new AggregateResourceBundleControl();

        private AggregateResourceBundleControl() {
        }

        @Override
        public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload) throws IllegalAccessException, InstantiationException, IOException {
            if (!"java.properties".equals(format)) {
                return super.newBundle(baseName, locale, format, loader, reload);
            }
            String resourceName = this.toBundleName(baseName, locale) + ".properties";
            List<ResourceBundle> resourceBundles = AggregateResourceBundleControl.load(resourceName, loader);
            return resourceBundles.isEmpty() ? null : new AggregateResourceBundle(resourceBundles);
        }

        private static List<ResourceBundle> load(String resourceName, ClassLoader loader) throws IOException {
            ArrayList<ResourceBundle> resourceBundles = new ArrayList<ResourceBundle>();
            Enumeration urls2 = (Enumeration)PlatformResourceBundleLocator.run(GetResources.action(loader, resourceName));
            while (urls2.hasMoreElements()) {
                URL url2 = (URL)urls2.nextElement();
                InputStream propertyStream = url2.openStream();
                try {
                    resourceBundles.add(new PropertyResourceBundle(propertyStream));
                }
                finally {
                    if (propertyStream == null) continue;
                    propertyStream.close();
                }
            }
            return resourceBundles;
        }
    }
}

