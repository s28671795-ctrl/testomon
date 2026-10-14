/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.profile.converter;

import java.util.List;
import org.pac4j.core.profile.converter.AttributeConverter;

public abstract class AbstractAttributeConverter
implements AttributeConverter {
    private final Class<? extends Object> clazz;

    protected AbstractAttributeConverter(Class<? extends Object> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Object convert(Object attribute) {
        Object t2 = null;
        if (attribute != null) {
            if (this.clazz.isAssignableFrom(attribute.getClass())) {
                t2 = attribute;
            } else if (attribute instanceof List) {
                List l = (List)attribute;
                if (l.size() > 0) {
                    Object element = l.get(0);
                    t2 = this.clazz.isAssignableFrom(element.getClass()) ? element : this.internalConvert(element);
                }
            } else {
                t2 = this.internalConvert(attribute);
            }
        }
        if (t2 != null) {
            return t2;
        }
        return this.defaultValue();
    }

    protected Object internalConvert(Object attribute) {
        return null;
    }

    protected Object defaultValue() {
        return null;
    }

    public Boolean accept(String typeName) {
        return this.clazz.getSimpleName().equals(typeName);
    }
}

