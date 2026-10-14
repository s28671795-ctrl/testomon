/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.spi.messageinterpolation;

import java.util.Locale;
import java.util.Set;
import org.hibernate.validator.Incubating;

@Incubating
public interface LocaleResolverContext {
    public Set<Locale> getSupportedLocales();

    public Locale getDefaultLocale();
}

