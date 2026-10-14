/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.messageinterpolation;

import java.lang.invoke.MethodHandles;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.Locale;
import java.util.Set;
import javax.el.ELManager;
import javax.el.ExpressionFactory;
import javax.validation.MessageInterpolator;
import org.hibernate.validator.Incubating;
import org.hibernate.validator.internal.engine.messageinterpolation.DefaultLocaleResolver;
import org.hibernate.validator.internal.engine.messageinterpolation.InterpolationTerm;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.internal.util.privilegedactions.GetClassLoader;
import org.hibernate.validator.internal.util.privilegedactions.SetContextClassLoader;
import org.hibernate.validator.messageinterpolation.AbstractMessageInterpolator;
import org.hibernate.validator.spi.messageinterpolation.LocaleResolver;
import org.hibernate.validator.spi.resourceloading.ResourceBundleLocator;

public class ResourceBundleMessageInterpolator
extends AbstractMessageInterpolator {
    private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
    private final ExpressionFactory expressionFactory;

    public ResourceBundleMessageInterpolator() {
        this(Collections.emptySet(), Locale.getDefault(), new DefaultLocaleResolver(), false);
    }

    public ResourceBundleMessageInterpolator(ResourceBundleLocator userResourceBundleLocator) {
        this(userResourceBundleLocator, Collections.emptySet(), Locale.getDefault(), new DefaultLocaleResolver(), false);
    }

    public ResourceBundleMessageInterpolator(ResourceBundleLocator userResourceBundleLocator, ResourceBundleLocator contributorResourceBundleLocator) {
        this(userResourceBundleLocator, contributorResourceBundleLocator, Collections.emptySet(), Locale.getDefault(), new DefaultLocaleResolver(), false);
    }

    public ResourceBundleMessageInterpolator(ResourceBundleLocator userResourceBundleLocator, ResourceBundleLocator contributorResourceBundleLocator, boolean cachingEnabled) {
        this(userResourceBundleLocator, contributorResourceBundleLocator, Collections.emptySet(), Locale.getDefault(), new DefaultLocaleResolver(), false, cachingEnabled);
    }

    public ResourceBundleMessageInterpolator(ResourceBundleLocator userResourceBundleLocator, boolean cachingEnabled) {
        this(userResourceBundleLocator, null, Collections.emptySet(), Locale.getDefault(), new DefaultLocaleResolver(), false, cachingEnabled);
    }

    public ResourceBundleMessageInterpolator(ResourceBundleLocator userResourceBundleLocator, boolean cachingEnabled, ExpressionFactory expressionFactory) {
        this(userResourceBundleLocator, null, Collections.emptySet(), Locale.getDefault(), new DefaultLocaleResolver(), false, cachingEnabled);
    }

    @Incubating
    public ResourceBundleMessageInterpolator(Set<Locale> locales, Locale defaultLocale, LocaleResolver localeResolver, boolean preloadResourceBundles) {
        super(locales, defaultLocale, localeResolver, preloadResourceBundles);
        this.expressionFactory = ResourceBundleMessageInterpolator.buildExpressionFactory();
    }

    @Incubating
    public ResourceBundleMessageInterpolator(ResourceBundleLocator userResourceBundleLocator, Set<Locale> locales, Locale defaultLocale, LocaleResolver localeResolver, boolean preloadResourceBundles) {
        super(userResourceBundleLocator, locales, defaultLocale, localeResolver, preloadResourceBundles);
        this.expressionFactory = ResourceBundleMessageInterpolator.buildExpressionFactory();
    }

    @Incubating
    public ResourceBundleMessageInterpolator(ResourceBundleLocator userResourceBundleLocator, ResourceBundleLocator contributorResourceBundleLocator, Set<Locale> locales, Locale defaultLocale, LocaleResolver localeResolver, boolean preloadResourceBundles) {
        super(userResourceBundleLocator, contributorResourceBundleLocator, locales, defaultLocale, localeResolver, preloadResourceBundles);
        this.expressionFactory = ResourceBundleMessageInterpolator.buildExpressionFactory();
    }

    @Incubating
    public ResourceBundleMessageInterpolator(ResourceBundleLocator userResourceBundleLocator, ResourceBundleLocator contributorResourceBundleLocator, Set<Locale> locales, Locale defaultLocale, LocaleResolver localeResolver, boolean preloadResourceBundles, boolean cachingEnabled) {
        super(userResourceBundleLocator, contributorResourceBundleLocator, locales, defaultLocale, localeResolver, preloadResourceBundles, cachingEnabled);
        this.expressionFactory = ResourceBundleMessageInterpolator.buildExpressionFactory();
    }

    @Incubating
    public ResourceBundleMessageInterpolator(ResourceBundleLocator userResourceBundleLocator, Set<Locale> locales, Locale defaultLocale, LocaleResolver localeResolver, boolean preloadResourceBundles, boolean cachingEnabled) {
        super(userResourceBundleLocator, null, locales, defaultLocale, localeResolver, preloadResourceBundles, cachingEnabled);
        this.expressionFactory = ResourceBundleMessageInterpolator.buildExpressionFactory();
    }

    @Incubating
    public ResourceBundleMessageInterpolator(ResourceBundleLocator userResourceBundleLocator, Set<Locale> locales, Locale defaultLocale, LocaleResolver localeResolver, boolean preloadResourceBundles, boolean cachingEnabled, ExpressionFactory expressionFactory) {
        super(userResourceBundleLocator, null, locales, defaultLocale, localeResolver, preloadResourceBundles, cachingEnabled);
        this.expressionFactory = expressionFactory;
    }

    @Override
    protected String interpolate(MessageInterpolator.Context context, Locale locale, String term) {
        InterpolationTerm expression = new InterpolationTerm(term, locale, this.expressionFactory);
        return expression.interpolate(context);
    }

    private static ExpressionFactory buildExpressionFactory() {
        if (ResourceBundleMessageInterpolator.canLoadExpressionFactory()) {
            ExpressionFactory expressionFactory = ELManager.getExpressionFactory();
            LOG.debug("Loaded expression factory via original TCCL");
            return expressionFactory;
        }
        ClassLoader originalContextClassLoader = ResourceBundleMessageInterpolator.run(GetClassLoader.fromContext());
        try {
            ResourceBundleMessageInterpolator.run(SetContextClassLoader.action(ResourceBundleMessageInterpolator.class.getClassLoader()));
            if (ResourceBundleMessageInterpolator.canLoadExpressionFactory()) {
                ExpressionFactory expressionFactory = ELManager.getExpressionFactory();
                LOG.debug("Loaded expression factory via HV classloader");
                ExpressionFactory expressionFactory2 = expressionFactory;
                return expressionFactory2;
            }
            ResourceBundleMessageInterpolator.run(SetContextClassLoader.action(ELManager.class.getClassLoader()));
            if (ResourceBundleMessageInterpolator.canLoadExpressionFactory()) {
                ExpressionFactory expressionFactory = ELManager.getExpressionFactory();
                LOG.debug("Loaded expression factory via EL classloader");
                ExpressionFactory expressionFactory3 = expressionFactory;
                return expressionFactory3;
            }
        }
        catch (Throwable e) {
            throw LOG.getUnableToInitializeELExpressionFactoryException(e);
        }
        finally {
            ResourceBundleMessageInterpolator.run(SetContextClassLoader.action(originalContextClassLoader));
        }
        throw LOG.getUnableToInitializeELExpressionFactoryException(null);
    }

    private static boolean canLoadExpressionFactory() {
        try {
            ExpressionFactory.newInstance();
            return true;
        }
        catch (Throwable e) {
            LOG.debugv(e, "Failed to load expression factory via classloader {0}", (Object)ResourceBundleMessageInterpolator.run(GetClassLoader.fromContext()));
            return false;
        }
    }

    private static <T> T run(PrivilegedAction<T> action) {
        return System.getSecurityManager() != null ? AccessController.doPrivileged(action) : action.run();
    }
}

