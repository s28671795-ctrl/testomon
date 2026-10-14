/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.engine.messageinterpolation;

import java.util.Locale;
import java.util.Set;
import org.hibernate.validator.spi.messageinterpolation.LocaleResolverContext;

public class DefaultLocaleResolverContext
implements LocaleResolverContext {
    private final Set<Locale> supportedLocales;
    private final Locale defaultLocale;

    public DefaultLocaleResolverContext(Set<Locale> supportedLocales, Locale defaultLocale) {
        this.supportedLocales = supportedLocales;
        this.defaultLocale = defaultLocale;
    }

    @Override
    public Set<Locale> getSupportedLocales() {
        return this.supportedLocales;
    }

    @Override
    public Locale getDefaultLocale() {
        return this.defaultLocale;
    }
}

