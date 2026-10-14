/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.engine;

import java.io.Serializable;
import org.hibernate.validator.spi.nodenameprovider.Property;
import org.hibernate.validator.spi.nodenameprovider.PropertyNodeNameProvider;

public class DefaultPropertyNodeNameProvider
implements PropertyNodeNameProvider,
Serializable {
    @Override
    public String getName(Property property) {
        return property.getName();
    }
}

