/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.profile.converter;

import org.pac4j.core.profile.converter.AbstractAttributeConverter;

public final class BooleanConverter
extends AbstractAttributeConverter {
    public BooleanConverter() {
        super(Boolean.class);
    }

    @Override
    protected Boolean internalConvert(Object attribute) {
        if (attribute instanceof String) {
            return "1".equals(attribute) || "true".equals(attribute);
        }
        if (attribute instanceof Number) {
            return Integer.valueOf(1).equals(attribute);
        }
        return null;
    }

    @Override
    protected Boolean defaultValue() {
        return Boolean.FALSE;
    }
}

