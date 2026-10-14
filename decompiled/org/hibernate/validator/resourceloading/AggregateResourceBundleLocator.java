/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.resourceloading;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import org.hibernate.validator.Incubating;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.Contracts;
import org.hibernate.validator.resourceloading.AggregateResourceBundle;
import org.hibernate.validator.resourceloading.DelegatingResourceBundleLocator;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;
import org.hibernate.validator.spi.resourceloading.ResourceBundleLocator;

public class AggregateResourceBundleLocator
extends DelegatingResourceBundleLocator {
    private final List<PlatformResourceBundleLocator> resourceBundleLocators;

    public AggregateResourceBundleLocator(List<String> bundleNames) {
        this(bundleNames, false, Collections.emptySet(), null);
    }

    public AggregateResourceBundleLocator(List<String> bundleNames, ResourceBundleLocator delegate) {
        this(bundleNames, false, Collections.emptySet(), delegate, null);
    }

    public AggregateResourceBundleLocator(List<String> bundleNames, ResourceBundleLocator delegate, ClassLoader classLoader) {
        this(bundleNames, false, Collections.emptySet(), delegate, classLoader);
    }

    @Incubating
    public AggregateResourceBundleLocator(List<String> bundleNames, boolean preloadResourceBundles, Set<Locale> localesToInitialize) {
        this(bundleNames, preloadResourceBundles, localesToInitialize, null);
    }

    @Incubating
    public AggregateResourceBundleLocator(List<String> bundleNames, boolean preloadResourceBundles, Set<Locale> localesToInitialize, ResourceBundleLocator delegate) {
        this(bundleNames, preloadResourceBundles, localesToInitialize, delegate, null);
    }

    @Incubating
    public AggregateResourceBundleLocator(List<String> bundleNames, boolean preloadResourceBundles, Set<Locale> localesToInitialize, ResourceBundleLocator delegate, ClassLoader classLoader) {
        super(delegate);
        Contracts.assertValueNotNull(bundleNames, "bundleNames");
        ArrayList<PlatformResourceBundleLocator> tmpBundleLocators = new ArrayList<PlatformResourceBundleLocator>(bundleNames.size());
        for (String bundleName : bundleNames) {
            tmpBundleLocators.add(new PlatformResourceBundleLocator(bundleName, preloadResourceBundles ? localesToInitialize : Collections.emptySet(), classLoader));
        }
        this.resourceBundleLocators = CollectionHelper.toImmutableList(tmpBundleLocators);
    }

    @Override
    public ResourceBundle getResourceBundle(Locale locale) {
        ArrayList<ResourceBundle> sourceBundles = new ArrayList<ResourceBundle>();
        for (PlatformResourceBundleLocator resourceBundleLocator : this.resourceBundleLocators) {
            ResourceBundle resourceBundle = resourceBundleLocator.getResourceBundle(locale);
            if (resourceBundle == null) continue;
            sourceBundles.add(resourceBundle);
        }
        ResourceBundle bundleFromDelegate = super.getResourceBundle(locale);
        if (bundleFromDelegate != null) {
            sourceBundles.add(bundleFromDelegate);
        }
        return sourceBundles.isEmpty() ? null : new AggregateResourceBundle(sourceBundles);
    }

    @Deprecated
    public static class AggregateBundle
    extends AggregateResourceBundle {
        public AggregateBundle(List<ResourceBundle> bundles) {
            super(bundles);
        }
    }
}

