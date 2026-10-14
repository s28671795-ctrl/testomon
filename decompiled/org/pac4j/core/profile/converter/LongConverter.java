/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.profile.converter;

import org.pac4j.core.profile.converter.AbstractAttributeConverter;

public final class LongConverter
extends AbstractAttributeConverter {
    public LongConverter() {
        super(Long.class);
    }

    @Override
    protected Long internalConvert(Object attribute) {
        if (attribute instanceof Integer) {
            return (long)((Integer)attribute);
        }
        if (attribute instanceof String) {
            return Long.parseLong((String)attribute);
        }
        return null;
    }
}

