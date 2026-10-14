/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.spi.nodenameprovider;

import org.hibernate.validator.Incubating;
import org.hibernate.validator.spi.nodenameprovider.Property;

@Incubating
public interface PropertyNodeNameProvider {
    public String getName(Property var1);
}

