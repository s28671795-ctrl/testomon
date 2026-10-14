/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.messageinterpolation;

import java.lang.invoke.MethodHandles;
import java.util.Collections;
import java.util.Locale;
import java.util.Set;
import javax.validation.MessageInterpolator;
import org.hibernate.validator.Incubating;
import org.hibernate.validator.internal.engine.messageinterpolation.DefaultLocaleResolver;
import org.hibernate.validator.internal.engine.messageinterpolation.InterpolationTerm;
import org.hibernate.validator.internal.engine.messageinterpolation.ParameterTermResolver;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.messageinterpolation.AbstractMessageInterpolator;
import org.hibernate.validator.spi.messageinterpolation.LocaleResolver;

public class ParameterMessageInterpolator
extends AbstractMessageInterpolator {
    private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());

    public ParameterMessageInterpolator() {
        this(Collections.emptySet(), Locale.getDefault(), new DefaultLocaleResolver(), false);
    }

    @Incubating
    public ParameterMessageInterpolator(Set<Locale> locales, Locale defaultLocale, boolean preloadResourceBundles) {
        this(locales, defaultLocale, new DefaultLocaleResolver(), preloadResourceBundles);
    }

    @Incubating
    public ParameterMessageInterpolator(Set<Locale> locales, Locale defaultLocale, LocaleResolver localeResolver, boolean preloadResourceBundles) {
        super(locales, defaultLocale, localeResolver, preloadResourceBundles);
    }

    @Override
    protected String interpolate(MessageInterpolator.Context context, Locale locale, String term) {
        if (InterpolationTerm.isElExpression(term)) {
            LOG.warnElIsUnsupported(term);
            return term;
        }
        ParameterTermResolver parameterTermResolver = new ParameterTermResolver();
        return parameterTermResolver.interpolate(context, term);
    }
}

