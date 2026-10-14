/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.profile.converter;

import java.util.Locale;
import org.pac4j.core.profile.converter.AbstractAttributeConverter;

public final class LocaleConverter
extends AbstractAttributeConverter {
    public LocaleConverter() {
        super(Locale.class);
    }

    @Override
    protected Locale internalConvert(Object attribute) {
        if (attribute instanceof String) {
            String s2 = ((String)attribute).replaceAll("-", "_");
            String[] parts = s2.split("_");
            int length = parts.length;
            if (length == 2) {
                return new Locale(parts[0], parts[1]);
            }
            if (length == 1) {
                return new Locale(parts[0]);
            }
        }
        return null;
    }
}

