/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.profile.converter;

import org.pac4j.core.profile.converter.AbstractAttributeConverter;

public final class IntegerConverter
extends AbstractAttributeConverter {
    public IntegerConverter() {
        super(Integer.class);
    }

    @Override
    protected Integer internalConvert(Object attribute) {
        if (attribute instanceof String) {
            return Integer.parseInt((String)attribute);
        }
        return null;
    }
}

