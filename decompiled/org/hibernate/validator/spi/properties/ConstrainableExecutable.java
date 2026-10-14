/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.spi.properties;

import org.hibernate.validator.Incubating;

@Incubating
public interface ConstrainableExecutable {
    public Class<?> getReturnType();

    public String getName();

    public Class<?>[] getParameterTypes();
}

