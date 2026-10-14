/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.spi.messageinterpolation;

import java.util.Locale;
import org.hibernate.validator.Incubating;
import org.hibernate.validator.spi.messageinterpolation.LocaleResolverContext;

@Incubating
public interface LocaleResolver {
    public Locale resolve(LocaleResolverContext var1);
}

