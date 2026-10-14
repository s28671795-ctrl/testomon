/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.properties;

import org.hibernate.validator.internal.properties.Constrainable;
import org.hibernate.validator.internal.properties.PropertyAccessor;

public interface Property
extends Constrainable {
    public String getPropertyName();

    public String getResolvedPropertyName();

    public PropertyAccessor createAccessor();
}

