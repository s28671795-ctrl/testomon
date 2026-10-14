/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.profile.converter;

import java.util.List;
import java.util.Objects;
import org.pac4j.core.profile.converter.AttributeConverter;

public class ChainingConverter
implements AttributeConverter {
    private final List<AttributeConverter> converters;

    public ChainingConverter(List<AttributeConverter> converters2) {
        this.converters = converters2;
    }

    @Override
    public Object convert(Object o) {
        return this.converters.stream().map(c -> c.convert(o)).filter(Objects::nonNull).findFirst().orElse(null);
    }
}

